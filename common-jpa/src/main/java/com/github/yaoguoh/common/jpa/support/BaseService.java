package com.github.yaoguoh.common.jpa.support;

import com.github.yaogouh.common.exception.BusinessException;
import com.github.yaoguoh.common.jpa.domain.BaseListenerDomain_;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import jakarta.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
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
     * The Jpa specification executor.
     */
    @Autowired
    protected JpaSpecificationExecutor<T> jpaSpecificationExecutor;

    @Override
    public T save(T domain) {
        log.debug("save - 保存实体. domain={}", domain);

        return jpaRepository.save(domain);
    }

    @Override
    public List<T> batchSave(Collection<T> collection) {
        log.debug("batchSave - 批量保存. Collection={}", collection);

        return jpaRepository.saveAll(collection);
    }

    @Override
    public void delete(T domain) {
        log.debug("delete - 根据实体属性作为条件进行删除. domain={}", domain);

        jpaRepository.delete(domain);
    }

    @Override
    public void batchDelete(Collection<T> collection) {
        log.debug("batchDelete - 批量删除. Collection={}", collection);

        jpaRepository.deleteAll(collection);
    }

    @Override
    public void deleteById(ID id) {
        log.debug("deleteById - 根据主键字段进行删除. id={}", id);

        jpaRepository.deleteById(id);
    }

    @Override
    public void deleteById(Collection<ID> collection) {
        log.debug("deleteById - 根据主键字段进行删除. Collection={}", collection);

        List<T> tList = jpaRepository.findAllById(collection);
        jpaRepository.deleteAll(tList);
    }

    @Override
    public T update(T domain) {
        log.debug("update - 更新实体. domain={}", domain);

        return jpaRepository.saveAndFlush(domain);
    }


    @Override
    public List<T> findAll() {
        log.debug("findAll - 查询全部结果");

        return jpaRepository.findAll();
    }

    @Override
    public List<T> findAll(Sort sort) {
        log.debug("findAll - 查询全部结,按Sort排序. sort={}", sort);

        return jpaRepository.findAll(sort);
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        log.debug("findAll - 根据Pageable进行分页查询. pageable={}", pageable);

        return jpaRepository.findAll(pageable);
    }

    @Override
    public T findById(ID id) {
        log.debug("findById - 通过Id查询实体. id={}", id);

        Optional<T> optional = jpaRepository.findById(id);
        return optional.orElseThrow(() -> new BusinessException("实体不存在!"));
    }

    @Override
    public List<T> findAllById(Collection<ID> collection) {
        log.debug("findAllById - 根据实体ID集合进行查询. Collection={}", collection);

        return jpaRepository.findAllById(collection);
    }

    @Override
    public T findOneByExample(Example<T> example) {
        log.debug("findOneByExample - 根据实体中的属性进行查询. example={}", example);

        Optional<T> optional = jpaRepository.findOne(example);
        return optional.orElseThrow(() -> new BusinessException("实体不存在"));
    }

    @Override
    public List<T> findAllByExample(Example<T> example) {
        log.debug("findAllByExample - 根据实体中的属性值进行查询. ");

        return jpaRepository.findAll(example);
    }

    @Override
    public Page<T> findAllByExample(Example<T> example, Pageable pageable) {
        log.debug("findAllByExample - 根据实体属性和Pageable进行分页查询. example={}, pageable={}", example, pageable);

        return jpaRepository.findAll(example, pageable);
    }

    @Override
    public Page<T> findAllByExampleLike(T domain, Pageable pageable) {
        log.debug("findAllByExampleLike - 通过实体属性模糊查询（分页）. domain={}, pageable={}", domain, pageable);

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

    @Override
    public Long findCount() {
        log.debug("findCount - 查询总数. ");

        return jpaRepository.count();
    }

    @Override
    public Long findCountByExample(Example<T> example) {
        log.debug("findCountByExample - 根据实体中的属性查询总数, 查询条件使用等号. example={}", example);

        return jpaRepository.count(example);
    }

    @Override
    public Long createdRangeCount(LocalDateTime greaterThan, LocalDateTime lessThan) {
        Specification<T> specification = (root, criteriaQuery, criteriaBuilder) -> {
            // 所有的断言及条件
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(BaseListenerDomain_.CREATED_DATE).as(LocalDateTime.class), greaterThan));
            predicates.add(criteriaBuilder.lessThan(root.get(BaseListenerDomain_.CREATED_DATE).as(LocalDateTime.class), lessThan));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        return jpaSpecificationExecutor.count(specification);
    }
}
