package com.github.yaogouh.common.elasticsearch.support;

import com.github.yaogouh.common.elasticsearch.common.index.SearchRequestFactory;
import com.github.yaogouh.common.elasticsearch.common.page.PageCondition;
import com.github.yaogouh.common.elasticsearch.common.sort.Sort;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.join.query.HasParentQueryBuilder;
import org.elasticsearch.join.query.JoinQueryBuilders;
import org.elasticsearch.join.query.ParentIdQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.Serializable;


/**
 * The interface Base es relationship repository.
 *
 * @author dqq
 */
public abstract class BaseESRelationshipRepository<T, ID extends Serializable> extends BaseESRepository<T, ID>
        implements IESRelationshipReposiory<T, ID> {

    @Autowired
    private RestHighLevelClient client;

    @Override
    public BulkResponse saveWithFatherSonRelationship(T t, Boolean isSaveParent) throws IOException {
        if (isSaveParent) {
            save(t);
        }
        return saveWithFatherSonRelationship(t);
    }

    @Override
    public SearchResponse findByParentId(T t, String parentType) throws IOException {
        SearchRequestFactory<T> searchRequestFactory = new SearchRequestFactory<>();
        SearchSourceBuilder searchSourceBuilder = createSearchSourceForParentQuery(searchRequestFactory, t, parentType);
        SearchRequest searchRequest = searchRequestFactory.createRequest(t)
                .source(searchSourceBuilder);
        return client.search(searchRequest, RequestOptions.DEFAULT);
    }

    @Override
    public SearchResponse findByParentId(PageCondition pageCondition, T t, String parentType) throws IOException {
        SearchRequestFactory<T> searchRequestFactory = new SearchRequestFactory<>();
        SearchSourceBuilder searchSourceBuilder = createSearchSourceForParentQuery(searchRequestFactory, t, parentType)
                .from(pageCondition.getStartRow())
                .size(pageCondition.getPageSize());
        SearchRequest searchRequest = searchRequestFactory.createRequest(t)
                .source(searchSourceBuilder);
        return client.search(searchRequest, RequestOptions.DEFAULT);
    }

    @Override
    public SearchResponse findByParentId(PageCondition pageCondition, Sort sort, T t, String parentType) throws IOException {
        SearchRequestFactory<T> searchRequestFactory = new SearchRequestFactory<>();
        SearchSourceBuilder searchSourceBuilder = createSearchSourceForParentQuery(searchRequestFactory, t, parentType)
                .from(pageCondition.getStartRow())
                .size(pageCondition.getPageSize());
        sort.getOrders().forEach(order ->
                searchSourceBuilder.sort(order.getProperty(), order.getSortOrder()));
        SearchRequest searchRequest = searchRequestFactory.createRequest(t)
                .source(searchSourceBuilder);
        return client.search(searchRequest, RequestOptions.DEFAULT);
    }

    private SearchSourceBuilder createSearchSourceForParentQuery(SearchRequestFactory<T> searchRequestFactory, T t, String parentType) {
        MatchQueryBuilder query = QueryBuilders.matchQuery("_id", searchRequestFactory.getIndexId(t));
        HasParentQueryBuilder hasParentQuery = JoinQueryBuilders.hasParentQuery(parentType, query, false);
        return new SearchSourceBuilder().query(hasParentQuery);
    }

    @Override
    public SearchResponse findByParentIdQuery(T t, String childType) throws IOException {
        SearchRequestFactory<T> searchRequestFactory = new SearchRequestFactory<>();
        ParentIdQueryBuilder parentIdQuery = JoinQueryBuilders.parentId(childType, searchRequestFactory.getIndexId(t));
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder()
                .query(parentIdQuery);
        SearchRequest searchRequest = searchRequestFactory.createRequest(t)
                .source(searchSourceBuilder);
        return client.search(searchRequest, RequestOptions.DEFAULT);
    }

    @Override
    public SearchResponse findByParentIdQuery(PageCondition pageCondition, Sort sort, T t, String childType) throws IOException {
        SearchRequestFactory<T> searchRequestFactory = new SearchRequestFactory<>();
        ParentIdQueryBuilder parentIdQuery = JoinQueryBuilders.parentId(childType, searchRequestFactory.getIndexId(t));
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder()
                .query(parentIdQuery)
                .from(pageCondition.getStartRow())
                .size(pageCondition.getPageSize());
        sort.getOrders().forEach(order ->
                searchSourceBuilder.sort(order.getProperty(), order.getSortOrder()));
        SearchRequest searchRequest = searchRequestFactory.createRequest(t)
                .source(searchSourceBuilder);
        return client.search(searchRequest, RequestOptions.DEFAULT);
    }
}
