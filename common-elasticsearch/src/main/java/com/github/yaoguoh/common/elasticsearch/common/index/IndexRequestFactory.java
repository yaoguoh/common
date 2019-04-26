package com.github.yaoguoh.common.elasticsearch.common.index;

import com.idata.brain.common.elasticsearch.enums.ChildType;
import org.elasticsearch.action.index.IndexRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * 构造ES Index Request 工厂类
 *
 * @param <T> the type parameter
 * @author dqq
 */
public class IndexRequestFactory<T> extends AbstractRequestFactory<T> {

    /**
     * Instantiates a new Index request factory.
     */
    public IndexRequestFactory() {
    }

    /**
     * 解析实体，新建构造索引请求.
     *
     * @param t the t 实体
     * @return the index request IndexRequest
     */
    @Override
    public IndexRequest createRequest(T t) {
        return new IndexRequest(getIndexName(t), getType(t), getIndexId(t))
                .routing(getRoute(t));
    }

    /**
     * 解析实体，新建构造索引请求.
     *
     * @param t     the t
     * @param route the route
     * @return the index request
     */
    public IndexRequest createRequest(T t, String route) {
        return new IndexRequest(getIndexName(t), getType(t), getIndexId(t))
                .routing(route);
    }

    /**
     * 构建父子关系 join mapping.
     *
     * @param type     the type
     * @param parentId the parent id
     * @return the map
     */
    public static Map<String, Object> createJoinMapping(ChildType type, String parentId){
        Map<String, Object> map = new HashMap<>();
        map.put("name", type.getValue());
        map.put("parent", parentId);
        return map;
    }

    /**
     * 构建父子关系 join mapping.
     *
     * @param type     the type
     * @param parentId the parent id
     * @return the map
     */
    public static Map<String, Object> createJoinMapping(String type, String parentId){
        Map<String, Object> map = new HashMap<>();
        map.put("name", type);
        map.put("parent", parentId);
        return map;
    }
}
