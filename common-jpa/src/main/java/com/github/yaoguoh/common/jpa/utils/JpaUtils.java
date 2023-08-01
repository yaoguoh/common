package com.github.yaoguoh.common.jpa.utils;

import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang3.ObjectUtils;

import java.util.List;
import java.util.function.BiFunction;

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

    /**
     * Add predicate if not empty.
     *
     * @param <T>               the type parameter
     * @param predicates        the predicates
     * @param field             the field
     * @param value             the value
     * @param predicateFunction the predicate function
     */
    public static <T> void addPredicateIfNotEmpty(List<Predicate> predicates, Expression<T> field, T value, BiFunction<Expression<T>, T, Predicate> predicateFunction) {
        if (ObjectUtils.isNotEmpty(value)) {
            predicates.add(predicateFunction.apply(field, value));
        }
    }
}
