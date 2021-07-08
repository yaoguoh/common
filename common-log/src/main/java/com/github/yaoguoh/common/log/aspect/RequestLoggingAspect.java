package com.github.yaoguoh.common.log.aspect;

import com.alibaba.fastjson.JSON;
import com.github.yaoguoh.common.log.annotation.RequestLogging;
import com.github.yaoguoh.common.log.domain.CommonRequestLog;
import com.github.yaoguoh.common.log.service.AsyncLogService;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

/**
 * 操作日志记录处理
 *
 * @author WYG
 */
@Slf4j
@Aspect
@Component
@AllArgsConstructor
public class RequestLoggingAspect {

    private final AsyncLogService asyncLogService;

    /**
     * 配置织入点
     */
    @Pointcut("@annotation(com.github.yaoguoh.common.log.annotation.RequestLogging)")
    public void logPointCut() {
        // do something
    }

    /**
     * Do around object.
     *
     * @param proceedingJoinPoint the proceeding join point
     * @return the object
     * @throws Throwable the throwable
     */
    @Around("logPointCut()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long                     start           = System.currentTimeMillis();
        ServletRequestAttributes attributes      = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest       request         = Objects.requireNonNull(attributes).getRequest();
        Signature                signature       = proceedingJoinPoint.getSignature();
        MethodSignature          methodSignature = (MethodSignature) signature;
        Object                   result          = proceedingJoinPoint.proceed();

        final CommonRequestLog commonRequestLog = CommonRequestLog.builder()
                .ip(request.getRemoteAddr())
                .url(request.getRequestURL().toString())
                .httpMethod(request.getMethod())
                .requestParam(JSON.toJSONString(this.getRequestParams(proceedingJoinPoint)))
                .classMethod(String.format("%s.%s", signature.getDeclaringTypeName(), signature.getName()))
                .result(JSON.toJSONString(result))
                .timeCost(System.currentTimeMillis() - start)
                .build();
        log.debug("REQUEST INFO: {}", JSON.toJSONString(commonRequestLog));

        RequestLogging requestLogging = methodSignature.getMethod().getAnnotation(RequestLogging.class);
        if (requestLogging.save()) {
            asyncLogService.saveLog(commonRequestLog);
        }
        return result;
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint 切点
     * @param exception the exception
     */
    @AfterThrowing(value = "logPointCut()", throwing = "exception")
    public void doAfterThrowing(JoinPoint joinPoint, Exception exception) {
        ServletRequestAttributes attributes      = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest       request         = Objects.requireNonNull(attributes).getRequest();
        Signature                signature       = joinPoint.getSignature();
        MethodSignature          methodSignature = (MethodSignature) signature;

        final CommonRequestLog commonRequestLog = CommonRequestLog.builder()
                .ip(request.getRemoteAddr())
                .url(request.getRequestURL().toString())
                .httpMethod(request.getMethod())
                .requestParam(JSON.toJSONString(this.getRequestParams(joinPoint)))
                .classMethod(String.format("%s.%s", signature.getDeclaringTypeName(), signature.getName()))
                .result(StringUtils.substring(exception.getMessage(), 0, 2000))
                .build();
        log.debug("REQUEST INFO : {}", JSON.toJSONString(commonRequestLog));

        RequestLogging requestLogging = methodSignature.getMethod().getAnnotation(RequestLogging.class);
        if (requestLogging.save()) {
            asyncLogService.saveLog(commonRequestLog);
        }
    }

    private Map<String, Object> getRequestParams(JoinPoint joinPoint) {
        Map<String, Object> requestParams = Maps.newHashMap();
        // 参数名
        String[] paramNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        // 参数值
        Object[] paramValues = joinPoint.getArgs();
        for (int i = 0; i < paramNames.length; i++) {
            Object value = paramValues[i];
            // 不支持序列化参数过滤
            if (value instanceof BindingResult || value instanceof ServletRequest || value instanceof ServletResponse) {
                continue;
            }
            // 文件对象获取文件名
            if (value instanceof MultipartFile) {
                MultipartFile file = (MultipartFile) value;
                value = file.getOriginalFilename();
            }
            requestParams.put(paramNames[i], value);
        }
        return requestParams;
    }

}
