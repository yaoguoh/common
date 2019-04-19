package com.github.yaoguoh.common.elasticsearch.common.index;


import cn.hutool.core.bean.BeanUtil;
import org.elasticsearch.search.SearchHit;
import org.springframework.util.ObjectUtils;

import java.util.*;

/**
 * 将Hit 转换为 实体的工具类.
 *
 * @param <T> the type parameter
 * @author dqq
 */
public class HitResolver<T> {

    /**
     * 将查询命中转换为对应的实体.
     *
     * @param hit the hit
     * @param t   the t
     * @return the optional
     */
    public Optional<T> resolve(SearchHit hit, T t) {
        Map<String, Object> sourceAsMap = hit.getSourceAsMap();
        sourceAsMap.put("id", hit.getId());
        Object o = BeanUtil.mapToBean(sourceAsMap, t.getClass(), false);
        if (!ObjectUtils.isEmpty(o)){
            t = (T) o;
        }
        return Optional.of(t);
    }
//        Class<?> aClass = t.getClass();
//        ReflectionUtils.doWithLocalMethods(aClass, method ->
//                Optional.of(method).filter(s ->
//                        method.getName().contains("set"))
//                        .ifPresent(method1 -> ReflectionUtils.doWithFields(aClass, field -> {
//                            if (!field.isAccessible()) {
//                                ReflectionUtils.makeAccessible(field);
//                            }
//                            if (StringUtils.equalsIgnoreCase(method1.getName(), "set" + field.getName())) {
//                                Id idAnnotation = field.getAnnotation(Id.class);
//                                Optional.ofNullable(idAnnotation).ifPresent(id -> {
//                                    try {
//                                        method1.invoke(t, hit.getId());
//                                    } catch (IllegalAccessException | InvocationTargetException e) {
//                                        e.printStackTrace();
//                                    }
//                                });
//                                if (ObjectUtils.isEmpty(idAnnotation)) {
//                                    Field fieldAnnotation = field.getAnnotation(Field.class);
//                                    String key;
//                                    if (ObjectUtils.isEmpty(fieldAnnotation)) {
//                                        key = field.getName();
//                                    } else {
//                                        key = fieldAnnotation.value();
//                                    }
//                                    try {
//                                        if (method1.getParameterTypes()[0] == LocalDateTime.class) {
//                                            String time = (String) sourceAsMap.get(key);
//                                            LocalDateTime createTime = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME);
//                                            method1.invoke(t, createTime);
//                                        } else {
//                                            method1.invoke(t, sourceAsMap.get(key));
//                                        }
//                                    } catch (InvocationTargetException e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//                            }
//                        })));

}
