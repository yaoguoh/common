package com.github.yaoguoh.common.util.utils;

import java.text.NumberFormat;

/**
 * The type Number utils.
 *
 * @author yaoguohh
 */
public class NumberUtils {

    private NumberUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Number format string.
     *
     * @param number               the number 需处理数值
     * @param maximumIntegerDigits the maximum integer digits 设置最大整数位数
     * @param minimumIntegerDigits the minimum integer digits 设置最小整数位数
     * @return the string
     */
    public static String numberFormat(long number, int maximumIntegerDigits, int minimumIntegerDigits) {
        NumberFormat numberFormat = NumberFormat.getInstance();
        // 设置是否使用分组
        numberFormat.setGroupingUsed(false);
        // 设置最大整数位数
        numberFormat.setMaximumIntegerDigits(maximumIntegerDigits);
        // 设置最小整数位数
        numberFormat.setMinimumIntegerDigits(minimumIntegerDigits);
        return numberFormat.format(number);
    }
}
