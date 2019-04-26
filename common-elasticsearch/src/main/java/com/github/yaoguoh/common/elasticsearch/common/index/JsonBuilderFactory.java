package com.github.yaoguoh.common.elasticsearch.common.index;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.yaoguoh.common.elasticsearch.annotation.Ignore;
import com.github.yaoguoh.common.elasticsearch.annotation.Join;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * 构建XContentBuilder的工厂类.
 *
 * @param <T> the type parameter
 * @author dqq
 */
@Slf4j
public class JsonBuilderFactory<T> {

    /**
     * Instantiates a new Json builder factory.
     */
    public JsonBuilderFactory() {
    }

    /**
     * 构造实体的XContentBuilder
     *
     * @param t the t
     * @return es的json对象 x content builder
     * @throws IOException the io exception
     */
    public XContentBuilder createXContentBuilder(T t) throws IOException {
        Class<?> aClass = t.getClass();
        XContentBuilder jsonBuilder = XContentFactory
                .jsonBuilder()
                .startObject();
        ReflectionUtils.doWithFields(aClass, field -> {
            if (!field.isAccessible()) {
                ReflectionUtils.makeAccessible(field);
            }
            JsonProperty field1 = field.getAnnotation(JsonProperty.class);
            String fieldName = field.getName();
            if (!ObjectUtils.isEmpty(field1) && !StringUtils.isEmpty(field1.value())) {
                fieldName = field1.value();
            }
            try {
                Object o = field.get(t);
                if (o instanceof LocalDateTime) {
                    jsonBuilder.timeField(fieldName, o);
                } else {
                    jsonBuilder.field(fieldName, o);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, field -> {
            Ignore ignore = field.getAnnotation(Ignore.class);
            if (!ObjectUtils.isEmpty(ignore) && ignore.isIgnore()) {
                return false;
            }
            Join join = field.getAnnotation(Join.class);
            return ObjectUtils.isEmpty(join);
        });
        return jsonBuilder.endObject();
    }

    /**
     * 构造父子关系中子类型的XContentBuilder
     *
     * @param t      the t
     * @param isJoin the is join
     * @return x content builder
     * @throws IOException the io exception
     */
    public XContentBuilder createXContentBuilder(T t, Boolean isJoin) throws IOException {
        if (!isJoin) {
            return createXContentBuilder(t);
        }
        return null;
    }
}
