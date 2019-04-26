package com.github.yaoguoh.common.elasticsearch.support;

import com.idata.brain.common.elasticsearch.common.index.GetResponseResolver;
import com.idata.brain.common.elasticsearch.common.index.IndexRequestFactory;
import com.idata.brain.common.elasticsearch.common.index.JsonBuilderFactory;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;

/**
 * es的抽象访问类
 *
 * @param <T>  the type parameter
 * @param <ID> the type parameter
 * @author dqq
 */
public abstract class BaseESRepository<T, ID extends Serializable> implements IESRepository<T, ID> {

    @Autowired
    private RestHighLevelClient client;

    @Override
    public IndexResponse save(T t) throws IOException {
        XContentBuilder builder = new JsonBuilderFactory<T>()
                .createXContentBuilder(t);
        IndexRequest indexRequest = new IndexRequestFactory<T>()
                .createRequest(t)
                .source(builder);
        return client.index(indexRequest, RequestOptions.DEFAULT);
    }

    public IndexResponse save(T t, String route) throws IOException {
        XContentBuilder builder = new JsonBuilderFactory<T>()
                .createXContentBuilder(t);
        IndexRequest indexRequest = new IndexRequestFactory<T>()
                .createRequest(t, route)
                .source(builder);
        return client.index(indexRequest, RequestOptions.DEFAULT);
    }

    @Override
    public Optional<T> findById(T t, ID id) throws IOException {
        IndexRequestFactory<T> indexRequestFactory = new IndexRequestFactory<>();
        GetRequest getRequest = new GetRequest(indexRequestFactory.getIndexName(t), indexRequestFactory.getType(t), (String) id)
                .routing(indexRequestFactory.getRoute(t));
        GetResponse response = client.get(getRequest, RequestOptions.DEFAULT);
        return new GetResponseResolver<>(t)
                .resolve(response);
    }

    @Override
    public void delete(T t, ID id) throws IOException {
        IndexRequestFactory<T> indexRequestFactory = new IndexRequestFactory<>();
        DeleteRequest deleteRequest = new DeleteRequest(indexRequestFactory.getIndexName(t), indexRequestFactory.getType(t), (String) id)
                .routing(indexRequestFactory.getRoute(t));
        client.delete(deleteRequest, RequestOptions.DEFAULT);
    }
}