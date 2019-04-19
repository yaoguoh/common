package com.github.yaogouh.base.exception;

import com.github.yaogouh.base.enums.IEnum;

/**
 * 业务异常.
 *
 * @author WYG
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 3160241586346324994L;

    /**
     * 异常码
     */
    protected int code;


    /**
     * Instantiates a new Business exception.
     */
    public BusinessException() {
    }

    /**
     * Instantiates a new Business exception.
     *
     * @param cause the cause
     */
    public BusinessException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new Business exception.
     *
     * @param message the message
     */
    public BusinessException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Business exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Business exception.
     *
     * @param code    the code
     * @param message the message
     */
    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * Instantiates a new Business exception.
     *
     * @param code      the code
     * @param msgFormat the msg format
     * @param args      the args
     */
    public BusinessException(int code, String msgFormat, Object... args) {
        super(String.format(msgFormat, args));
        this.code = code;
    }

    /**
     * Instantiates a new Business exception.
     *
     * @param codeEnum the code enum
     * @param args     the args
     */
    public BusinessException(IEnum codeEnum, Object... args) {
        super(String.format(codeEnum.getMessage(), args));
        this.code = codeEnum.getCode();
    }

    /**
     * Gets code.
     *
     * @return the code
     */
    public int getCode() {
        return code;
    }

    /**
     * Sets code.
     *
     * @param code the code
     */
    public void setCode(int code) {
        this.code = code;
    }
}
