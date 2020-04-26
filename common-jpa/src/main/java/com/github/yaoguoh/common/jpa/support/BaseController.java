package com.github.yaoguoh.common.jpa.support;

import com.github.yaoguoh.common.jpa.domain.BaseDomain;
import com.github.yaoguoh.common.util.result.Result;
import com.github.yaoguoh.common.util.result.ResultGenerator;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
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
    @GetMapping("/{id}")
    @ApiOperation(value = "根据ID字段进行查询")
    @ApiImplicitParam(name = "id", value = "实体ID", required = true, dataType = "String", paramType = "path")
    public Result<Object> findById(@PathVariable ID id) {
        log.info("findById - 通过Id查询实体. id={}", id);
        return ResultGenerator.ok(service.findById(id));
    }

    /**
     * 根据实体中的属性进行查询, 只能有一个返回值, 有多个结果是抛出异常, 查询条件使用等号
     *
     * @param example the example
     * @return the t
     */
    @GetMapping("/example")
    @ApiOperation(value = "根据实体中的属性进行查询")
    @ApiImplicitParam(name = "example", value = "实体属性", required = true)
    public Result<Object> findOneByExample(T example) {
        log.info("findOneByExample - 根据实体中的属性进行查询. example={}", example);
        return ResultGenerator.ok(service.findOneByExample(Example.of(example)));
    }

    /**
     * 查询全部结果, findAll()方法能达到同样的效果
     *
     * @return the list
     */
    @GetMapping("/all")
    @ApiOperation(value = "查询全部")
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
    @GetMapping("/list/by/example")
    @ApiOperation(value = "根据实体中的属性进行查询")
    @ApiImplicitParam(name = "example", value = "实体属性", required = true)
    public Result<List<T>> findAllByExample(Example<T> example) {
        log.info("findAllByExample - 根据实体中的属性值进行查询. ");
        return ResultGenerator.ok(service.findAllByExample(example));
    }

    /**
     * 根据实体ID集合进行查询
     *
     * @param list the list
     * @return the list
     */
    @GetMapping("/list/by/id")
    @ApiOperation(value = "根据实体ID集合进行查询")
    @ApiImplicitParam(name = "example", value = "实体属性", required = true)
    public Result<List<T>> findAllByIdList(List<ID> list) {
        log.info("findAllByIdList - 根据实体ID集合进行查询. list={}", list);

        return ResultGenerator.ok(service.findAllByIdList(list));
    }

    /**
     * 查询总数
     *
     * @return the int
     */
    @GetMapping("/count")
    @ApiOperation(value = "查询总数")
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
    @GetMapping("/count/by/example")
    @ApiOperation(value = "根据实体中的属性查询总数")
    @ApiImplicitParam(name = "example", value = "实体属性", required = true)
    public Result<Long> findCountByExample(Example<T> example) {
        log.info("findCountByExample - 根据实体中的属性查询总数, 查询条件使用等号. example={}", example);

        return ResultGenerator.ok(service.findCountByExample(example));
    }

    /**
     * 保存一个实体, null的属性不会保存, 会使用数据库默认值
     *
     * @param domain the domain
     * @return the int
     */
    @PostMapping(value = "/create")
    @ResponseStatus(value = HttpStatus.CREATED)
    @ApiOperation(value = "新建实体")
    @ApiImplicitParam(name = "domain", value = "实体", required = true)
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
    @PostMapping(value = "/batch/creat")
    @ResponseStatus(value = HttpStatus.CREATED)
    @ApiOperation(value = "批量新建实体")
    @ApiImplicitParam(name = "list", value = "实体集合", required = true)
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
    @PutMapping(value = "/update")
    @ApiOperation(value = "更新实体")
    @ApiImplicitParam(name = "domain", value = "实体", required = true)
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
    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "通过ID删除实体")
    @ApiImplicitParam(name = "id", value = "实体ID", required = true, dataType = "String", paramType = "path")
    public Result<Object> deleteById(@PathVariable ID id) {
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
    @DeleteMapping(value = "/list")
    @ApiOperation(value = "通过ID集合批量删除")
    @ApiImplicitParam(name = "id", value = "实体ID", required = true)
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
    @GetMapping(value = "/list/by/pageable")
    @ApiOperation(value = "根据Pageable进行分页查询")
    @ApiImplicitParam(name = "pageable", value = "分页条件", required = true)
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
    @GetMapping(value = "/list/by/example/pageable")
    @ApiOperation(value = "根据实体属性和Pageable进行分页查询")
    @ApiImplicitParam(name = "pageable", value = "分页条件", required = true)
    public Result<Page<T>> findByExampleAndPageable(Example<T> example, Pageable pageable) {
        log.info("findAllByExampleAndPageable - 根据实体属性和Pageable进行分页查询. example={}, pageable={}", example, pageable);

        return ResultGenerator.ok(service.findAllByExampleAndPageable(example, pageable));
    }
}
