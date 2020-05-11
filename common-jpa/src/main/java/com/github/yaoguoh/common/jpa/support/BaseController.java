package com.github.yaoguoh.common.jpa.support;

import com.github.yaoguoh.common.jpa.domain.BaseDomain;
import com.github.yaoguoh.common.util.result.Result;
import com.github.yaoguoh.common.util.result.ResultGenerator;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

/**
 * The class Base controller.
 *
 * @param <T>  the type parameter
 * @param <ID> the type parameter
 * @author WYG
 */
@Slf4j
public abstract class BaseController<T extends BaseDomain, ID extends Serializable> {

    @Autowired
    private IService<T, ID> service;

    /**
     * 根据主键字段进行查询, 方法参数必须包含完整的主键属性, 查询条件使用等号
     *
     * @param id the id
     * @return the t
     */
    @ApiOperation(value = "根据ID字段进行查询")
    @GetMapping("/{id}")
    public Result<Object> findById(@ApiParam(name = "id", value = "实体ID", required = true, example = "0") @PathVariable ID id) {
        log.info("findById - 通过Id查询实体. id={}", id);
        return ResultGenerator.ok(service.findById(id));
    }

    /**
     * 根据实体中的属性进行查询, 只能有一个返回值, 有多个结果是抛出异常, 查询条件使用等号
     *
     * @param example the example
     * @return the t
     */
    @ApiOperation(value = "根据实体中的属性进行查询")
    @GetMapping("/example")
    public Result<Object> findOneByExample(T example) {
        log.info("findOneByExample - 根据实体中的属性进行查询. example={}", example);
        return ResultGenerator.ok(service.findOneByExample(Example.of(example)));
    }

    /**
     * 查询全部结果, findAll()方法能达到同样的效果
     *
     * @return the list
     */
    @ApiOperation(value = "查询全部")
    @GetMapping("/all")
    public Result<List<T>> findAll() {
        log.info("findAll - 查询全部结果");
        return ResultGenerator.ok(service.findAll());
    }

    /**
     * 根据实体中的属性值进行查询, 查询条件使用等号
     *
     * @param example the example
     * @return the list
     */
    @ApiOperation(value = "根据实体中的属性进行查询")
    @GetMapping("/list/by/example")
    public Result<List<T>> findAllByExample(T example) {
        log.info("findAllByExample - 根据实体中的属性值进行查询. ");
        return ResultGenerator.ok(service.findAllByExample(Example.of(example)));
    }

    /**
     * 根据实体ID集合进行查询
     *
     * @param list the list
     * @return the list
     */
    @ApiOperation(value = "根据实体ID集合进行查询")
    @GetMapping("/list/by/id")
    public Result<List<T>> findAllByIdList(List<ID> list) {
        log.info("findAllByIdList - 根据实体ID集合进行查询. list={}", list);

        return ResultGenerator.ok(service.findAllByIdList(list));
    }

    /**
     * 查询总数
     *
     * @return the int
     */
    @ApiOperation(value = "查询总数")
    @GetMapping("/count")
    public Result<Long> findCount() {
        log.info("findCount - 查询总数. ");

        return ResultGenerator.ok(service.findCount());
    }

    /**
     * 根据实体中的属性查询总数, 查询条件使用等号
     *
     * @param example the example
     * @return the int
     */
    @ApiOperation(value = "根据实体中的属性查询总数")
    @GetMapping("/count/by/example")
    public Result<Long> findCountByExample(T example) {
        log.info("findCountByExample - 根据实体中的属性查询总数, 查询条件使用等号. example={}", example);

        return ResultGenerator.ok(service.findCountByExample(Example.of(example)));
    }

    /**
     * 保存一个实体, null的属性不会保存, 会使用数据库默认值
     *
     * @param domain the domain
     * @return the int
     */
    @ApiOperation(value = "新建实体")
    @PostMapping(value = "/create")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Result<Object> save(@RequestBody T domain) {
        log.info("save - 新建实体. domain={}", domain);

        service.save(domain);
        return ResultGenerator.ok();
    }

    /**
     * 批量保存
     *
     * @param list the list
     * @return the int
     */
    @ApiOperation(value = "批量新建实体")
    @PostMapping(value = "/batch/creat")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Result<Object> batchSave(@RequestBody List<T> list) {
        log.info("batchSave - 批量保存. list={}", list);

        service.batchSave(list);
        return ResultGenerator.ok();
    }

    /**
     * 修改实体
     *
     * @param domain the domain
     * @return the int
     */
    @ApiOperation(value = "更新实体")
    @PutMapping(value = "/update")
    public Result<Object> update(@RequestBody T domain) {
        log.info("update - 更新实体. domain={}", domain);

        service.update(domain);
        return ResultGenerator.ok();
    }


    /**
     * 根据主键字段进行删除, 方法参数必须包含完整的主键属性
     *
     * @param id the id
     * @return the wrapper
     */
    @ApiOperation(value = "通过ID删除实体")
    @DeleteMapping(value = "/{id}")
    public Result<Object> deleteById(@ApiParam(name = "id", value = "实体ID", required = true, example = "0") @PathVariable ID id) {
        log.info("deleteById - 根据主键字段进行删除. id={}", id);
        service.deleteById(id);
        return ResultGenerator.ok();
    }

    /**
     * 通过ID集合批量删除
     *
     * @param list the list
     * @return the wrapper
     */
    @ApiOperation(value = "通过ID集合批量删除")
    @DeleteMapping(value = "/list")
    public Result<Object> deleteById(@RequestBody List<ID> list) {
        log.info("deleteById - 通过ID集合批量删除. list={}", list);
        service.deleteByIdList(list);
        return ResultGenerator.ok();
    }

    /**
     * 根据Pageable进行分页查询
     *
     * @param pageable the pageable
     * @return the page
     */
    @ApiOperation(value = "根据Pageable进行分页查询")
    @GetMapping(value = "/list/by/pageable")
    public Result<Page<T>> findByPageable(Pageable pageable) {
        log.info("findAllByPageable - 根据Pageable进行分页查询. pageable={}", pageable);

        return ResultGenerator.ok(service.findAllByPageable(pageable));
    }

    /**
     * 根据实体属性和Pageable进行分页查询
     *
     * @param example  the example
     * @param pageable the pageable
     * @return the page
     */
    @ApiOperation(value = "根据实体属性和Pageable进行分页查询")
    @GetMapping(value = "/list/by/example/pageable")
    public Result<Page<T>> findByExampleAndPageable(T example, Pageable pageable) {
        log.info("findAllByExampleAndPageable - 根据实体属性和Pageable进行分页查询. example={}, pageable={}", example, pageable);

        return ResultGenerator.ok(service.findAllByExampleAndPageable(Example.of(example), pageable));
    }
}
