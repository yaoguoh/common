package com.github.yaogouh.elasticsearch.common.route;

import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * ES 路由生成器 builder.
 *
 * @author dqq
 */
public class RouteBuilder {

    /**
     * 通过时间构建 路由值(route).
     *
     * @param now the now
     * @return the string
     */
    public static String build(LocalDate now){
        return "" + now.getYear() + now.getMonthValue();
    }

    /**
     * 通过时间段构建 路由值(route).
     *
     * @param start the start
     * @param end   the end
     * @return the list
     */
    public static String[] build(LocalDate start, LocalDate end){
        List<String> list = new ArrayList<>();
        LocalDate mid = start;
        while (end.isAfter(mid) || end.isEqual(mid)) {
            list.add("" + mid.getYear() + mid.getMonthValue());
            mid = mid.plusMonths(1);
        }
        if (!ObjectUtils.isEmpty(list)) {
            String[] arr = new String[list.size()];
            for (int i = 0; i < arr.length; i++){
                arr[i] = list.get(i);
            }
            return arr;
        }
        return null;
    }
}
