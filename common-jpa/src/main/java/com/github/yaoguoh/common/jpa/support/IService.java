package com.github.yaoguoh.common.jpa.support;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * 通用接口
 *
 * @param <T>  the type parameter
 * @param <ID> the type parameter
 * @author WYG
 */
public interface IService<T, ID> {

    /**
     * 保存一个实体, null的属性不会保存, 会使用数据库默认值
     *
     * @param domain the domain
     * @return the t
     */
    @Transactional(rollbackFor = Exception.class)
    T save(T domain);

    /**
     * 批量保存  @param list the list
     *
     * @param collection the collection
     * @return the list
     */
    @Transactional(rollbackFor = Exception.class)
    List<T> batchSave(Collection<T> collection);

    /**
     * 根据实体属性作为条件进行删除, 查询条件使用等号  @param domain the domain
     *
     * @param domain the domain
     */
    @Transactional(rollbackFor = Exception.class)
    void delete(T domain);

    /**
     * 批量删除  @param list the list
     *
     * @param collection the collection
     */
    @Transactional(rollbackFor = Exception.class)
    void batchDelete(Collection<T> collection);

    /**
     * 根据主键字段进行删除, 方法参数必须包含完整的主键属性
     *
     * @param id the id
     */
    @Transactional(rollbackFor = Exception.class)
    void deleteById(ID id);

    /**
     * 根据主键字段进行删除, 方法参数必须包含完整的主键属性
     *
     * @param collection the collection
     */
    @Transactional(rollbackFor = Exception.class)
    void deleteById(Collection<ID> collection);

    /**
     * 根据主键更新属性不为null的值  @param entity the entity
     *
     * @param domain the domain
     * @return the t
     */
    @Transactional(rollbackFor = Exception.class)
    T update(T domain);

    /**
     * 查询全部结果, findAll()方法能达到同样的效果
     *
     * @return the list
     */
    List<T> findAll();

    /**
     * Find all list. 查询全部带排序
     *
     * @param sort the sort
     * @return the page
     */
    List<T> findAll(Sort sort);

    /**
     * 根据Pageable进行分页查询
     *
     * @param pageable the pageable
     * @return the page
     */
    Page<T> findAll(Pageable pageable);

    /**
     * 根据主键字段进行查询, 方法参数必须包含完整的主键属性, 查询条件使用等号
     *
     * @param id the id
     * @return the t
     */
    T findById(ID id);

    /**
     * 根据实体ID集合进行查询
     *
     * @param collection the collection
     * @return the list
     */
    List<T> findAllById(Collection<ID> collection);

    /**
     * 根据实体中的属性进行查询, 只能有一个返回值, 有多个结果是抛出异常, 查询条件使用等号
     *
     * @param example the example
     * @return the t
     */
    T findOneByExample(Example<T> example);

    /**
     * 根据实体中的属性值进行查询, 查询条件使用等号
     *
     * @param example the example
     * @return the list
     */
    List<T> findAllByExample(Example<T> example);

    /**
     * 根据实体属性和Pageable进行分页查询
     *
     * @param example  the example
     * @param pageable the pageable
     * @return the page
     */
    Page<T> findAllByExample(Example<T> example, Pageable pageable);

    /**
     * Find all by example like and pageable page. 通过实体属性模糊查询（分页）
     *
     * @param domain   the domain
     * @param pageable the pageable
     * @return the page
     */
    Page<T> findAllByExampleLike(T domain, Pageable pageable);

    /**
     * 查询总数
     *
     * @return the long
     */
    Long findCount();

    /**
     * 根据实体中的属性查询总数, 查询条件使用等号
     *
     * @param example the example
     * @return the long
     */
    Long findCountByExample(Example<T> example);

    /**
     * 按创建时间范围统计
     *
     * @param greaterThan the greater than
     * @param lessThan    the less than
     * @return the long
     */
    Long createdRangeCount(LocalDateTime greaterThan, LocalDateTime lessThan);

}
