package com.github.yaoguoh.common.util.result;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;


/**
 * The type Result.
 *
 * @param <T> the type parameter
 * @author WYG
 */
@Data
public class Result<T> implements Serializable {

    /**
     * 成功码.
     */
    public static final  int    SUCCESS_CODE             = 200;
    /**
     * 成功信息.
     */
    public static final  String SUCCESS_MESSAGE          = "success";
    /**
     * 错误码.
     */
    public static final  int    ERROR_CODE               = 500;
    /**
     * 错误信息.
     */
    public static final  String ERROR_MESSAGE            = "server error";
    /**
     * 错误码：参数非法
     */
    public static final  int    ILLEGAL_ARGUMENT_CODE    = 100;
    /**
     * 错误信息：参数非法
     */
    public static final  String ILLEGAL_ARGUMENT_MESSAGE = "illegal argument";
    /**
     * 序列化标识
     */
    private static final long   serialVersionUID         = 4893280118017319089L;

    /**
     * 编号.
     */
    private int code;

    /**
     * 信息.
     */
    private String message;

    /**
     * 结果数据
     */
    private T result;

    /**
     * Instantiates a new result. default code=200
     */
    Result() {
        this(SUCCESS_CODE, SUCCESS_MESSAGE);
    }

    /**
     * Instantiates a new result.
     *
     * @param code    the code
     * @param message the message
     */
    Result(int code, String message) {
        this(code, message, null);
    }

    /**
     * Instantiates a new result.
     *
     * @param code    the code
     * @param message the message
     * @param result  the result
     */
    Result(int code, String message, T result) {
        super();
        this.code(code).message(message).result(result);
    }

    /**
     * Sets the 编号 , 返回自身的引用.
     *
     * @param code the new 编号
     * @return the result
     */
    private Result<T> code(int code) {
        this.setCode(code);
        return this;
    }

    /**
     * Sets the 信息 , 返回自身的引用.
     *
     * @param message the new 信息
     * @return the result
     */
    private Result<T> message(String message) {
        this.setMessage(message);
        return this;
    }

    /**
     * Sets the 结果数据 , 返回自身的引用.
     *
     * @param result the new 结果数据
     * @return the result
     */
    public Result<T> result(T result) {
        this.setResult(result);
        return this;
    }

    /**
     * 判断是否成功： 依据 Result.SUCCESS_CODE == this.code
     *
     * @return code =200,true;否则 false.
     */
    @JSONField(serialize = false)
    public boolean success() {
        return Result.SUCCESS_CODE == this.code;
    }

    /**
     * 判断是否成功： 依据 Result.SUCCESS_CODE != this.code
     *
     * @return code !=200,true;否则 false.
     */
    @JSONField(serialize = false)
    public boolean error() {
        return !success();
    }

}
