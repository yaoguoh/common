package com.github.yaogouh.elasticsearch.common.index;

import cn.hutool.core.bean.BeanUtil;
import org.elasticsearch.action.get.GetResponse;
import org.springframework.util.ObjectUtils;

import java.util.Map;
import java.util.Optional;

/**
 * The type Get response resolver.
 *
 * @param <T> the type parameter
 * @author dqq
 */
public class GetResponseResolver<T> {

    private T t;

    /**
     * Instantiates a new Get response resolver.
     *
     * @param t the t
     */
    public GetResponseResolver(T t){
        this.t = t;
    }

    /**
     * 将 GetResponse 转换为 实体.
     *
     * @param response the response
     * @return the optional
     */
    public Optional<T> resolve(GetResponse response) {
        Map<String, Object> source = response.getSourceAsMap();
        source.put("id", response.getId());
        Object o = BeanUtil.mapToBean(source, t.getClass(), false);
        if (!ObjectUtils.isEmpty(o)){
            t = (T) o;
        }
        return Optional.of(t);
    }
//        ReflectionUtils.doWithFields(aClass, field -> {
//            if (!field.isAccessible()) {
//                ReflectionUtils.makeAccessible(field);
//            }
//            JsonProperty fieldAnnotation = field.getAnnotation(JsonProperty.class);
//            if (!ObjectUtils.isEmpty(fieldAnnotation) && !StringUtils.isEmpty(fieldAnnotation.value())) {
//                Object o = source.get(fieldAnnotation.value());
//                if (!ObjectUtils.isEmpty(o)) {
//                    field.set(t, o);
//                }
//            } else {
//                Object o = source.get(field.getName());
//                if (!ObjectUtils.isEmpty(o)) {
//                    field.set(t, o);
//                }
//            }
//            Id id = field.getAnnotation(Id.class);
//            if (!ObjectUtils.isEmpty(id)) {
//                String o = response.getId();
//                field.set(t, o);
//            }
//        });
}
