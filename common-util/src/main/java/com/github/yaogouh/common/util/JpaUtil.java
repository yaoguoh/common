package com.github.yaogouh.common.util;

/**
 * Jpa 查询工具类
 *
 * @author WYG
 */
public class JpaUtil {
    private static final String PERCENT = "%";

    /**
     * 模糊查询前后添加百分号
     *
     * @param str the str 字符串
     * @return the string 前后添加百分号后字符串
     */
    public static String addPercent(String str) {
        StringBuilder stringBuilder = new StringBuilder(str);
        stringBuilder.insert(0, PERCENT);
        stringBuilder.append(PERCENT);
        return stringBuilder.toString();
    }
}
