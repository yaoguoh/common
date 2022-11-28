package com.github.yaoguoh.common.jpa.support;

import com.github.yaoguoh.common.util.result.Result;
import com.github.yaoguoh.common.util.result.ResultGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The class Base controller.
 *
 * @param <T> the type parameter
 * @param <I> the type parameter
 * @author WYG
 */
@Slf4j
public abstract class BaseController<T, I> extends BaseQueryController<T, I> {

    @Autowired
    private IService<T, I> service;

    /**
     * 保存一个, null的属性不会保存, 会使用数据库默认值
     *
     * @param domain the domain
     * @return the int
     */
    @Operation(summary = "新建")
    @PostMapping(value = "/create")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Result<T> save(@RequestBody T domain) {
        log.debug("save - 新建. domain={}", domain);

        return ResultGenerator.ok(service.save(domain));
    }

    /**
     * 批量保存
     *
     * @param list the list
     * @return the int
     */
    @Operation(summary = "批量保存(可用于更新)")
    @PostMapping(value = "/batch/create")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Result<List<T>> batchSave(@RequestBody List<T> list) {
        log.debug("batchSave - 批量保存. list={}", list);

        return ResultGenerator.ok(service.batchSave(list));
    }

    /**
     * 修改
     *
     * @param domain the domain
     * @return the int
     */
    @Operation(summary = "更新")
    @PutMapping(value = "/update")
    public Result<T> update(@RequestBody T domain) {
        log.debug("update - 更新. domain={}", domain);

        return ResultGenerator.ok(service.update(domain));
    }


    /**
     * 根据主键字段进行删除, 方法参数必须包含完整的主键属性
     *
     * @param id the id
     * @return the result
     */
    @Operation(summary = "根据ID删除")
    @DeleteMapping(value = "/{id}")
    public Result<Object> deleteById(@Parameter(name = "id", description = "ID", example = "0", required = true) @PathVariable I id) {
        log.debug("deleteById - 根据主键字段进行删除. id={}", id);

        service.deleteById(id);
        return ResultGenerator.ok();
    }

    /**
     * 根据ID集合批量删除
     *
     * @param list the list
     * @return the result
     */
    @Operation(summary = "根据ID集合批量删除")
    @DeleteMapping(value = "/all")
    public Result<Object> deleteById(@RequestBody List<I> list) {
        log.debug("deleteById - 通过ID集合批量删除. list={}", list);

        service.deleteById(list);
        return ResultGenerator.ok();
    }
}
