package com.github.yaogouh.common.exception;


import com.github.yaoguoh.common.util.result.Result;
import com.github.yaoguoh.common.util.result.ResultGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * The type Global exception handler.
 *
 * @author yaoguoh
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handle business exception result.
     *
     * @param e the e
     * @return the result
     */
    @ExceptionHandler(BusinessException.class)
    public @ResponseBody
    Result<Object> handleBusinessException(Exception e) {
        log.error("handleBusinessException {}", e.getMessage());

        if (e instanceof BusinessException) {
            return ResultGenerator.wrap(((BusinessException) e).getCode(), e.getMessage());
        }
        return ResultGenerator.error(e.getMessage());
    }

    /**
     * Handle bind exception result.
     *
     * @param e the e
     * @return the result
     */
    @ExceptionHandler(BindException.class)
    public @ResponseBody
    Result<Object> handleBindException(BindException e) {
        log.error("BindException {}", e.getMessage());

        StringBuilder stringBuilder = new StringBuilder();
        for (FieldError fieldError : e.getFieldErrors()) {
            stringBuilder.append(fieldError.getDefaultMessage());
        }
        return ResultGenerator.wrap(400, stringBuilder.toString());
    }

    /**
     * 全局异常处理器，线上环境使用
     *
     * @param e the e
     * @return the result
     */
    @ExceptionHandler(Exception.class)
    public @ResponseBody
    Result<Object> handleException(Exception e) {
        log.error("Exception {}", e.getMessage(), e);

        return ResultGenerator.wrap(500, e.getMessage());
    }
}
