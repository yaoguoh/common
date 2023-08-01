package com.github.yaoguoh.common.util.result;

import org.apache.commons.lang3.StringUtils;

/**
 * The type Result generator.
 *
 * @author WYG
 */
public class ResultGenerator {

    private ResultGenerator() {
    }

    /**
     * Wrap result.
     *
     * @param <E>     the type parameter
     * @param code    the code
     * @param message the message
     * @param o       the o
     * @return the result
     */
    public static <E> Result<E> wrap(int code, String message, E o) {
        return new Result<>(code, message, o);
    }

    /**
     * Wrap result.
     *
     * @param <E>     the type parameter
     * @param code    the code
     * @param message the message
     * @return the result
     */
    public static <E> Result<E> wrap(int code, String message) {
        return wrap(code, message, null);
    }

    /**
     * Wrap result.
     *
     * @param <E>  the type parameter
     * @param code the code
     * @return the result
     */
    public static <E> Result<E> wrap(int code) {
        return wrap(code, null);
    }

    /**
     * Wrap result.
     *
     * @param <E> the type parameter
     * @param e   the e
     * @return the result
     */
    public static <E> Result<E> wrap(Exception e) {
        return new Result<>(Result.ERROR_CODE, e.getMessage());
    }

    /**
     * Un wrap e.
     *
     * @param <E>    the type parameter
     * @param result the result
     * @return the e
     */
    public static <E> E unWrap(Result<E> result) {
        return result.getResult();
    }

    /**
     * Illegal argument result.
     *
     * @param <E> the type parameter
     * @return the result
     */
    public static <E> Result<E> illegalArgument() {
        return wrap(Result.ILLEGAL_ARGUMENT_CODE, Result.ILLEGAL_ARGUMENT_MESSAGE);
    }

    /**
     * Illegal argument result.
     *
     * @param <E>     the type parameter
     * @param message the message
     * @return the result
     */
    public static <E> Result<E> illegalArgument(String message) {
        return wrap(Result.ILLEGAL_ARGUMENT_CODE, message);
    }

    /**
     * Error result.
     *
     * @param <E> the type parameter
     * @return the result
     */
    public static <E> Result<E> error() {
        return wrap(Result.ERROR_CODE, Result.ERROR_MESSAGE);
    }


    /**
     * Error result.
     *
     * @param <E>     the type parameter
     * @param message the message
     * @return the result
     */
    public static <E> Result<E> error(String message) {
        return wrap(Result.ERROR_CODE, StringUtils.isBlank(message) ? Result.ERROR_MESSAGE : message);
    }

    /**
     * Ok result.
     *
     * @param <E> the type parameter
     * @return the result
     */
    public static <E> Result<E> ok() {
        return new Result<>();
    }

    /**
     * Ok result.
     *
     * @param <E> the type parameter
     * @param o   the o
     * @return the result
     */
    public static <E> Result<E> ok(E o) {
        return new Result<>(Result.SUCCESS_CODE, Result.SUCCESS_MESSAGE, o);
    }

    /**
     * Success result.
     *
     * @param <E> the type parameter
     * @return the result
     */
    public static <E> Result<E> success() {
        return new Result<>();
    }

    /**
     * Success result.
     *
     * @param <E> the type parameter
     * @param o   the o
     * @return the result
     */
    public static <E> Result<E> success(E o) {
        return new Result<>(Result.SUCCESS_CODE, Result.SUCCESS_MESSAGE, o);
    }
}
