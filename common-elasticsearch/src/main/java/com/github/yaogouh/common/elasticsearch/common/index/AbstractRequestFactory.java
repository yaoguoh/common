package com.github.yaogouh.elasticsearch.common.index;

import com.github.yaogouh.elasticsearch.annotation.Document;
import com.github.yaogouh.elasticsearch.annotation.Id;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ReflectionUtils;

/**
 * The type Abstract request factory.
 *
 * @param <T> the type parameter
 * @author dqq
 */
public abstract class AbstractRequestFactory<T> implements RequestFactory<T> {

    /**
     * Gets index id.
     *
     * @param t the t
     * @return the index id
     */
    public String getIndexId(T t) {
        Class<?> aClass = t.getClass();
        final String[] idValue = {""};
        ReflectionUtils.doWithFields(aClass, field -> {
            if (!field.isAccessible()) {
                ReflectionUtils.makeAccessible(field);
            }
            Id id = field.getAnnotation(Id.class);
            if (!ObjectUtils.isEmpty(id)) {
                Object o = field.get(t);
                if (!ObjectUtils.isEmpty(o)) {
                    idValue[0] = o.toString();
                }
            }
        });
        return idValue[0];
    }

    /**
     * Gets index name.
     *
     * @param t the t
     * @return the index name
     */
    public String getIndexName(T t) {
        Class<?> aClass = t.getClass();
        Document document = aClass.getAnnotation(Document.class);
        return document.indexName();
    }

    /**
     * Gets type.
     *
     * @param t the t
     * @return the type
     */
    public String getType(T t) {
        Class<?> aClass = t.getClass();
        Document document = aClass.getAnnotation(Document.class);
        return document.type();
    }

    /**
     * Gets route.
     *
     * @param t the t
     * @return the route
     */
    public String getRoute(T t) {
        Class<?> aClass = t.getClass();
        Document document = aClass.getAnnotation(Document.class);
        return document.route();
    }
}
