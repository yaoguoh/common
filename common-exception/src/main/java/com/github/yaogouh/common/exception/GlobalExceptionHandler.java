package com.github.yaogouh.common.exception;


import com.github.yaoguoh.common.util.result.Result;
import com.github.yaoguoh.common.util.result.ResultGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Global exception handler.
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
    public @ResponseBody Result<Object> handleBusinessException(BusinessException e) {
        return logAndWrapException(HttpStatus.BAD_REQUEST, e.getCode(), e.getMessage(), e);
    }

    /**
     * Handle bind exception result.
     *
     * @param e the e
     * @return the result
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public @ResponseBody Result<Object> handleBindException(BindException e) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : e.getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return logAndWrapException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(), errors.toString(), e);
    }

    /**
     * Handle response status exception response entity.
     *
     * @param e the e
     * @return the response entity
     */
    @ExceptionHandler(ResponseStatusException.class)
    public @ResponseBody ResponseEntity<Result<Object>> handleResponseStatusException(ResponseStatusException e) {
        log.error("ResponseStatusException {}", e.getMessage(), e);
        return ResponseEntity
                .status(e.getStatusCode())
                .body(ResultGenerator.wrap(e.getStatusCode().value(), e.getReason()));
    }

    /**
     * Handle exception result.
     *
     * @param e the e
     * @return the result
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public @ResponseBody Result<Object> handleException(Exception e) {
        return logAndWrapException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), e);
    }

    private Result<Object> logAndWrapException(HttpStatus status, int code, String message, Exception e) {
        log.error("{} {}", status, message, e);
        return ResultGenerator.wrap(code, message);
    }
}
