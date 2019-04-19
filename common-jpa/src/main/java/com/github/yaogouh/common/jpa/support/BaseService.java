package com.github.yaogouh.jpa.support;

import com.github.yaogouh.base.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * The class Base service.
 *
 * @param <T>  the type parameter
 * @param <ID> the type parameter
 * @author WYG
 */
@Slf4j
public abstract class BaseService<T, ID> implements IService<T, ID> {

    /**
     * The Jpa repository.
     */
    @Autowired
    protected JpaRepository<T, ID> jpaRepository;

    /**
     * 根据主键字段进行查询, 方法参数必须包含完整的主键属性, 查询条件使用等号
     *
     * @param id the id
     * @return the t
     */
    @Override
    public T findById(ID id) {
        log.info("findById - 通过Id查询实体. id={}", id);

        Optional<T> optional = jpaRepository.findById(id);
        return optional.orElseThrow(() -> new BusinessException("实体不存在!"));
    }

    /**
     * 根据实体中的属性进行查询, 只能有一个返回值, 有多个结果是抛出异常, 查询条件使用等号
     *
     * @param example the example
     * @return the t
     */
    @Override
    public T findOneByExample(Example<T> example) {
        log.info("findOneByExample - 根据实体中的属性进行查询. example={}", example);

        Optional<T> optional = jpaRepository.findOne(example);
        return optional.orElseThrow(() -> new BusinessException("实体不存在"));
    }

    /**
     * 查询全部结果, findAll()方法能达到同样的效果
     *
     * @return the list
     */
    @Override
    public List<T> findAll() {
        log.info("findAll - 查询全部结果");
        return jpaRepository.findAll();
    }

    /**
     * 根据实体中的属性值进行查询, 查询条件使用等号
     *
     * @param example the example
     * @return the list
     */
    @Override
    public List<T> findAllByExample(Example<T> example) {
        log.info("findAllByExample - 根据实体中的属性值进行查询. ");
        return jpaRepository.findAll(example);
    }

    /**
     * 根据实体ID集合进行查询
     *
     * @param list the list
     * @return the list
     */
    @Override
    public List<T> findAllByIdList(List<ID> list) {
        log.info("findAllByIdList - 根据实体ID集合进行查询. list={}", list);
        return jpaRepository.findAllById(list);
    }

    /**
     * 查询总数
     *
     * @return the int
     */
    @Override
    public Long findCount() {
        log.info("findCount - 查询总数. ");
        return jpaRepository.count();
    }

    /**
     * 根据实体中的属性查询总数, 查询条件使用等号
     *
     * @param example the example
     * @return the int
     */
    @Override
    public Long findCountByExample(Example<T> example) {
        log.info("findCountByExample - 根据实体中的属性查询总数, 查询条件使用等号. example={}", example);
        return jpaRepository.count(example);
    }

    /**
     * 保存一个实体, null的属性不会保存, 会使用数据库默认值
     *
     * @param domain the domain
     * @return the int
     */
    @Override
    public T save(T domain) {
        log.info("save - 保存实体. domain={}", domain);
        return jpaRepository.save(domain);
    }

    /**
     * 批量保存
     *
     * @param list the list
     * @return the int
     */
    @Override
    public List<T> batchSave(List<T> list) {
        log.info("batchSave - 批量保存. list={}", list);
        return jpaRepository.saveAll(list);
    }

    /**
     * 根据主键更新属性不为null的值
     *
     * @param domain the domain
     * @return the int
     */
    @Override
    public T update(T domain) {
        log.info("update - 更新实体. domain={}", domain);
        return jpaRepository.saveAndFlush(domain);
    }

    /**
     * 根据实体属性作为条件进行删除, 查询条件使用等号  @param domain the domain
     *
     * @param domain the domain
     */
    @Override
    public void delete(T domain) {
        log.info("delete - 根据实体属性作为条件进行删除. domain={}", domain);
        jpaRepository.delete(domain);
    }

    /**
     * 批量删除
     *
     * @param list the list
     */
    @Override
    public void batchDelete(List<T> list) {
        log.info("batchDelete - 批量删除. list={}", list);
        jpaRepository.deleteAll(list);
    }

    /**
     * 根据主键字段进行删除, 方法参数必须包含完整的主键属性
     *
     * @param id the id
     */
    @Override
    public void deleteById(ID id) {
        log.info("deleteById - 根据主键字段进行删除. id={}", id);
        jpaRepository.deleteById(id);
    }

    /**
     * 根据主键字段进行批量删除
     *
     * @param list the id list
     */
    @Override
    public void deleteByIdList(List<ID> list) {
        log.info("deleteByIdList - 根据主键字段进行删除. list={}", list);
        List<T> tList = jpaRepository.findAllById(list);
        jpaRepository.deleteAll(tList);
    }

    /**
     * 根据Pageable进行分页查询
     *
     * @param pageable the pageable
     * @return the page
     */
    @Override
    public Page<T> findAllByPageable(Pageable pageable) {
        log.info("findAllByPageable - 根据Pageable进行分页查询. pageable={}", pageable);
        return jpaRepository.findAll(pageable);
    }

    /**
     * 根据实体属性和Pageable进行分页查询
     *
     * @param example  the example
     * @param pageable the pageable
     * @return the page
     */
    @Override
    public Page<T> findAllByExampleAndPageable(Example<T> example, Pageable pageable) {
        log.info("findAllByExampleAndPageable - 根据实体属性和Pageable进行分页查询. example={}, pageable={}", example, pageable);
        return jpaRepository.findAll(example, pageable);
    }
}
