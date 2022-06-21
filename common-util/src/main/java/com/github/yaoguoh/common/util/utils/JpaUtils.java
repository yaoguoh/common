package com.github.yaoguoh.common.util.utils;

/**
 * The type Jpa utils.
 *
 * @author yaoguohh
 */
public class JpaUtils {

    private JpaUtils() {
        throw new IllegalStateException("Utility class");
    }


    /**
     * Like string.
     *
     * @param param the param
     * @return the string
     */
    public static String like(String param) {

        return "%" + param + "%";
    }
}
