package com.github.yaogouh.common.exception;


import com.github.yaoguoh.common.util.result.Result;
import com.github.yaoguoh.common.util.result.ResultGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


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
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BusinessException.class)
    public @ResponseBody
    Result<Object> handleBusinessException(BusinessException e) {
        log.error("handleBusinessException {}", e.getMessage());
        e.printStackTrace();

        return ResultGenerator.wrap(e.getCode(), e.getMessage());
    }

    /**
     * Handle bind exception result.
     *
     * @param e the e
     * @return the result
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public @ResponseBody
    Result<Object> handleBindException(BindException e) {
        log.error("BindException {}", e.getMessage());
        e.printStackTrace();

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
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public @ResponseBody
    Result<Object> handleException(RuntimeException e) {
        log.error("Exception {}", e.getMessage());
        e.printStackTrace();

        return ResultGenerator.wrap(500, e.getMessage());
    }
}
