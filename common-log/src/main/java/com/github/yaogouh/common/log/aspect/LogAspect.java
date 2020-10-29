package com.github.yaogouh.common.log.aspect;

import cn.hutool.extra.servlet.ServletUtil;
import com.github.yaogouh.common.log.annotation.Log;
import com.github.yaogouh.common.log.domain.SystemOperateLog;
import com.github.yaogouh.common.log.enums.Status;
import com.github.yaogouh.common.log.service.AsyncLogService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 操作日志记录处理
 *
 * @author WYG
 */
@Slf4j
@Aspect
@Component
@AllArgsConstructor
public class LogAspect {

    private final AsyncLogService asyncLogService;

    /**
     * 配置织入点
     */
    @Pointcut("@annotation(com.github.yaogouh.common.log.annotation.Log)")
    public void logPointCut() {
        // do something
    }

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     * @param result    the json result
     */
    @AfterReturning(pointcut = "logPointCut()", returning = "result")
    public void doAfterReturning(JoinPoint joinPoint, Object result) {
        this.handleLog(joinPoint, null, result);
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint 切点
     * @param exception the exception
     */
    @AfterThrowing(value = "logPointCut()", throwing = "exception")
    public void doAfterThrowing(JoinPoint joinPoint, Exception exception) {
        this.handleLog(joinPoint, exception, null);
    }

    /**
     * Handle log.
     *
     * @param joinPoint the join point
     * @param exception the exception
     * @param result    the json result
     */
    protected void handleLog(final JoinPoint joinPoint, final Exception exception, Object result) {
        try {
            ServletRequestAttributes attributes         = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest       httpServletRequest = Objects.requireNonNull(attributes).getRequest();
            Signature                signature          = joinPoint.getSignature();
            MethodSignature          methodSignature    = (MethodSignature) signature;
            Log                      log                = methodSignature.getMethod().getAnnotation(Log.class);

            String className     = joinPoint.getTarget().getClass().getName();
            String methodName    = signature.getName();
            String requestMethod = httpServletRequest.getMethod();
            SystemOperateLog.SystemOperateLogBuilder systemOperateLogBuilder = SystemOperateLog.builder()
                    // 设置操作状态
                    .status(Status.SUCCESS.ordinal())
                    // 请求的IP地址
                    .ip(ServletUtil.getClientIP(httpServletRequest))
                    // 设置方法名称
                    .method(className + "." + methodName + "()")
                    // 设置action动作
                    .targetType(log.targetType().ordinal())
                    // 设置标题
                    .title(log.description())
                    // 设置请求方式
                    .requestMethod(requestMethod);
            // 异常记录
            if (ObjectUtils.isNotEmpty(exception)) {
                systemOperateLogBuilder
                        .status(Status.FAIL.ordinal())
                        .errorMessage(StringUtils.substring(exception.getMessage(), 0, 2000));
            }
            // 返回内容
            if (ObjectUtils.isNotEmpty(result)) {
                systemOperateLogBuilder
                        .result(result.toString());
            }
            // 是否需要保存request，参数和值
            if (log.saveRequestParam()) {
                systemOperateLogBuilder.requestParam(Arrays.stream(joinPoint.getArgs()).map(Object::toString).collect(Collectors.joining(";")));
            }
            // 保存至数据库
            asyncLogService.saveLog(systemOperateLogBuilder.build());
        } catch (Exception e) {
            // 记录本地异常日志
            log.error("异常信息: {}", e.getMessage());
            e.printStackTrace();
        }
    }
}
