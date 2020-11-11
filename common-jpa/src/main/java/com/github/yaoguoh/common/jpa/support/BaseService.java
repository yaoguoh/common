package com.github.yaoguoh.common.jpa.support;

import com.github.yaogouh.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
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

    @Override
    public T findById(ID id) {
        log.info("findById - 通过Id查询实体. id={}", id);

        Optional<T> optional = jpaRepository.findById(id);
        return optional.orElseThrow(() -> new BusinessException("实体不存在!"));
    }

    @Override
    public T findOneByExample(Example<T> example) {
        log.info("findOneByExample - 根据实体中的属性进行查询. example={}", example);

        Optional<T> optional = jpaRepository.findOne(example);
        return optional.orElseThrow(() -> new BusinessException("实体不存在"));
    }

    @Override
    public List<T> findAll() {
        log.info("findAll - 查询全部结果");

        return jpaRepository.findAll();
    }

    @Override
    public List<T> findAllByExample(Example<T> example) {
        log.info("findAllByExample - 根据实体中的属性值进行查询. ");

        return jpaRepository.findAll(example);
    }

    @Override
    public List<T> findAllByIdList(List<ID> list) {
        log.info("findAllByIdList - 根据实体ID集合进行查询. list={}", list);

        return jpaRepository.findAllById(list);
    }

    @Override
    public Long findCount() {
        log.info("findCount - 查询总数. ");

        return jpaRepository.count();
    }

    @Override
    public Long findCountByExample(Example<T> example) {
        log.info("findCountByExample - 根据实体中的属性查询总数, 查询条件使用等号. example={}", example);

        return jpaRepository.count(example);
    }

    @Override
    public T save(T domain) {
        log.info("save - 保存实体. domain={}", domain);

        return jpaRepository.save(domain);
    }

    @Override
    public List<T> batchSave(List<T> list) {
        log.info("batchSave - 批量保存. list={}", list);

        return jpaRepository.saveAll(list);
    }

    @Override
    public T update(T domain) {
        log.info("update - 更新实体. domain={}", domain);

        return jpaRepository.saveAndFlush(domain);
    }

    @Override
    public void delete(T domain) {
        log.info("delete - 根据实体属性作为条件进行删除. domain={}", domain);

        jpaRepository.delete(domain);
    }

    @Override
    public void batchDelete(List<T> list) {
        log.info("batchDelete - 批量删除. list={}", list);

        jpaRepository.deleteAll(list);
    }

    @Override
    public void deleteById(ID id) {
        log.info("deleteById - 根据主键字段进行删除. id={}", id);

        jpaRepository.deleteById(id);
    }

    @Override
    public void deleteByIdList(List<ID> list) {
        log.info("deleteByIdList - 根据主键字段进行删除. list={}", list);

        List<T> tList = jpaRepository.findAllById(list);
        jpaRepository.deleteAll(tList);
    }

    @Override
    public Page<T> findAllByPageable(Pageable pageable) {
        log.info("findAllByPageable - 根据Pageable进行分页查询. pageable={}", pageable);

        return jpaRepository.findAll(pageable);
    }

    @Override
    public Page<T> findAllByExampleAndPageable(Example<T> example, Pageable pageable) {
        log.info("findAllByExampleAndPageable - 根据实体属性和Pageable进行分页查询. example={}, pageable={}", example, pageable);

        return jpaRepository.findAll(example, pageable);
    }

    @Override
    public Page<T> findAllByExampleLikeAndPageable(T domain, Pageable pageable) {
        log.info("findAllByExampleLikeAndPageable - 通过实体属性模糊查询（分页）. domain={}, pageable={}", domain, pageable);

        //创建匹配器，即如何使用查询条件 构建对象
        ExampleMatcher matcher = ExampleMatcher.matching()
                //改变默认字符串匹配方式：模糊查询
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                //改变默认大小写忽略方式：忽略大小写
                .withIgnoreCase(true);

        //创建实例
        Example<T> systemExample = Example.of(domain, matcher);
        //返回查询结果
        return jpaRepository.findAll(systemExample, pageable);
    }
}
