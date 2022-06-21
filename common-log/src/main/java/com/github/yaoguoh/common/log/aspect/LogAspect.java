package com.github.yaoguoh.common.log.aspect;

import cn.hutool.extra.servlet.ServletUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.yaoguoh.common.log.annotation.Log;
import com.github.yaoguoh.common.log.model.domain.AuditLog;
import com.github.yaoguoh.common.log.service.AuditLogService;
import com.github.yaoguoh.common.util.utils.PositionUtils;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.env.Environment;
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
@Aspect
@Component
@AllArgsConstructor
public class LogAspect {

    private static final String SPRING_APPLICATION_NAME = "spring.application.name";

    private final ObjectMapper    objectMapper;
    private final PositionUtils   positionUtils;
    private final AuditLogService auditLogService;
    private final Environment     environment;

    /**
     * Do around object.
     *
     * @param proceedingJoinPoint the proceeding join point
     * @param log                 the log
     * @return the object
     * @throws Throwable the throwable
     */
    @Around("@annotation(log)")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint, Log log) throws Throwable {
        // before
        long                     start      = System.currentTimeMillis();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest       request    = Objects.requireNonNull(attributes).getRequest();
        Signature                signature  = proceedingJoinPoint.getSignature();
        // proceed
        Object proceedResult = proceedingJoinPoint.proceed();
        // after
        String ip = ServletUtil.getClientIP(request);
        AuditLog.AuditLogBuilder auditLogBuilder = AuditLog.builder()
                .provider(environment.getProperty(SPRING_APPLICATION_NAME))
                .module(log.module())
                .businessType(log.businessType().ordinal())
                .url(request.getRequestURL().toString())
                .httpMethod(request.getMethod())
                .classMethod(String.format("%s.%s", signature.getDeclaringTypeName(), signature.getName()))
                .operatorIp(ip)
                .operatorAddress(this.address(ip))
                .status(true)
                .result(objectMapper.writeValueAsString(proceedResult))
                .processTime(System.currentTimeMillis() - start);
        if (log.saveRequestData()) {
            auditLogBuilder.param(objectMapper.writeValueAsString(this.getRequestParams(proceedingJoinPoint)));
        }
        if (log.saveResponseData()) {
            auditLogBuilder.result(objectMapper.writeValueAsString(proceedResult));
        }
        auditLogService.saveLog(auditLogBuilder.build());
        return proceedResult;
    }


    /**
     * 拦截异常操作
     *
     * @param joinPoint 切点
     * @param exception the exception
     * @param log       the log
     */
    @SneakyThrows
    @AfterThrowing(value = "@annotation(log)", throwing = "exception")
    public void doAfterThrowing(JoinPoint joinPoint, Exception exception, Log log) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest       request    = Objects.requireNonNull(attributes).getRequest();
        Signature                signature  = joinPoint.getSignature();
        // position
        String ip = ServletUtil.getClientIP(request);
        final AuditLog commonRequestAuditLog = AuditLog.builder()
                .provider(environment.getProperty(SPRING_APPLICATION_NAME))
                .module(log.module())
                .businessType(log.businessType().ordinal())
                .url(request.getRequestURL().toString())
                .param(objectMapper.writeValueAsString(this.getRequestParams(joinPoint)))
                .httpMethod(request.getMethod())
                .classMethod(String.format("%s.%s", signature.getDeclaringTypeName(), signature.getName()))
                .operatorIp(ip)
                .operatorAddress(this.address(ip))
                .status(false)
                .errorMessage(StringUtils.substring(exception.getMessage(), 0, 2000))
                .build();
        auditLogService.saveLog(commonRequestAuditLog);
    }

    private Map<String, Object> getRequestParams(JoinPoint joinPoint) {
        Map<String, Object> requestParams = Maps.newHashMap();
        // 参数名
        String[] paramNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        // 参数值
        Object[] paramValues = joinPoint.getArgs();
        for (int i = 0; i < paramNames.length; i++) {
            Object o = paramValues[i];
            // 文件对象获取文件名
            if (o instanceof ServletRequest || o instanceof ServletResponse || o instanceof BindingResult) {
                continue;
            }
            if (o instanceof MultipartFile) {
                MultipartFile file = (MultipartFile) o;
                o = file.getOriginalFilename();
            }
            requestParams.put(paramNames[i], o);
        }
        return requestParams;
    }

    private String address(String ip) {
        return positionUtils.taobaoPosition(ip)
                .map(vo -> String.format("%s %s %s",
                        StringUtils.defaultIfBlank(vo.getCountry(), ""),
                        StringUtils.defaultIfBlank(vo.getRegion(), ""),
                        StringUtils.defaultIfBlank(vo.getCity(), "")))
                .orElse("");
    }

}
