package com.github.yaoguoh.common.elasticsearch.common.index;

import org.elasticsearch.action.search.SearchRequest;

import java.util.List;

/**
 * 构建ES Search request 工厂类.
 *
 * @param <T> the type parameter
 * @author dqq
 */
public class SearchRequestFactory<T> extends AbstractRequestFactory<T> {

    /**
     * Instantiates a new Search request factory.
     */
    public SearchRequestFactory(){}

    @Override
    public SearchRequest createRequest(T t) {
        return new SearchRequest(getIndexName(t)).types(getType(t)).routing(getRoute(t));
    }

    /**
     * 忽略 路由值route 构造 SearchRequest.
     *
     * @param t the t
     * @return the search request
     */
    public SearchRequest createRequestWithoutRoute(T t) {
        return new SearchRequest(getIndexName(t)).types(getType(t));
    }

    /**
     * 指定 路由值route 构造 SearchRequest.
     *
     * @param t      the t
     * @param routes the routes
     * @return the search request
     */
    public SearchRequest createRequestWithRoute(T t, String[] routes) {
        return new SearchRequest(getIndexName(t)).types(getType(t)).routing(routes);
    }
}
