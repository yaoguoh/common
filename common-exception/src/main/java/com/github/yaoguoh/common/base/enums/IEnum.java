package com.github.yaoguoh.common.base.enums;

/**
 * 枚举跟接口（泛型使用）
 *
 * @author WYG
 */
public interface IEnum {
    /**
     * 状态码
     *
     * @return 状态码 code
     */
    Integer getCode();

    /**
     * 返回信息
     *
     * @return 返回信息 message
     */
    String getMessage();
}
