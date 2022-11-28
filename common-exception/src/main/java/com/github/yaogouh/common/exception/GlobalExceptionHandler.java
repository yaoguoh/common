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

        StringBuilder stringBuilder = new StringBuilder();
        for (FieldError fieldError : e.getFieldErrors()) {
            stringBuilder.append(fieldError.getDefaultMessage());
        }
        return ResultGenerator.wrap(HttpStatus.BAD_REQUEST.value(), stringBuilder.toString());
    }

    /**
     * Response status exception result.
     *
     * @param e the e
     * @return the response entity
     */
    @ExceptionHandler(ResponseStatusException.class)
    public @ResponseBody
    ResponseEntity<Result<Object>> handleResponseStatusException(ResponseStatusException e) {
        log.error("Exception {}", e.getMessage());

        return ResponseEntity
                .status(e.getStatus())
                .body(ResultGenerator.wrap(e.getStatus().value(), e.getReason()));
    }

    /**
     * Global exception handler
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
