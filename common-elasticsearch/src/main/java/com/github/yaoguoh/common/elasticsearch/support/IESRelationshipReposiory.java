package com.github.yaoguoh.common.elasticsearch.support;

import com.github.yaoguoh.common.elasticsearch.common.page.PageCondition;
import com.github.yaoguoh.common.elasticsearch.common.sort.Sort;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.search.SearchResponse;

import java.io.IOException;
import java.io.Serializable;

/**
 * The interface Ies relationship reposiory.
 *
 * @param <T>  the type parameter
 * @param <ID> the type parameter
 * @author dqq
 */
public interface IESRelationshipReposiory<T, ID extends Serializable> extends IESRepository<T, ID> {

    /**
     * 批量保存父子关系.
     *
     * @param t the t
     * @return the bulk response BulkResponse
     * @throws IOException the io exception
     */
    BulkResponse saveWithFatherSonRelationship(T t) throws IOException;

    /**
     * 批量保存父子关系.
     *
     * @param t            the t
     * @param isSaveParent the is save parent 是否先新建/更新父索引
     * @return the bulk response BulkResponse
     * @throws IOException the io exception
     */
    BulkResponse saveWithFatherSonRelationship(T t, Boolean isSaveParent) throws IOException;

    /**
     * hasParent查询，通过父文档ID查询子文档.
     *
     * @param t          the t
     * @param parentType the parent type 父类型
     * @return the search response
     * @throws IOException the io exception
     */
    SearchResponse findByParentId(T t, String parentType) throws IOException;

    /**
     * hasParent查询，通过父文档ID查询子文档，增加分页功能.
     *
     * @param pageCondition the page condition
     * @param t             the t
     * @param parentType    the parent type
     * @return the search response
     * @throws IOException the io exception
     */
    SearchResponse findByParentId(PageCondition pageCondition, T t, String parentType) throws IOException;

    /**
     * hasParent查询，通过父文档ID查询子文档，增加分页，排序功能.
     *
     * @param pageCondition the page condition
     * @param sort          the sort
     * @param t             the t
     * @param parentType    the parent type
     * @return the search response
     * @throws IOException the io exception
     */
    SearchResponse findByParentId(PageCondition pageCondition, Sort sort, T t, String parentType) throws IOException;

    /**
     * parentId查询，查询子文档，增加分页，排序功能.
     *
     * @param pageCondition the page condition
     * @param sort          the sort
     * @param t             the t
     * @param childType     the child type
     * @return the search response
     * @throws IOException the io exception
     */
    SearchResponse findByParentIdQuery(PageCondition pageCondition, Sort sort, T t, String childType) throws IOException;

    /**
     * parentId查询，查询子文档.
     *
     * @param t         the t
     * @param childType the child type
     * @return the search response
     * @throws IOException the io exception
     */
    SearchResponse findByParentIdQuery(T t, String childType) throws IOException;
}
