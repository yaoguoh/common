package com.github.yaoguoh.common.jpa.support;

import com.github.yaoguoh.common.util.result.Result;
import com.github.yaoguoh.common.util.result.ResultGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * The class Base controller.
 *
 * @param <T> the type parameter
 * @param <I> the type parameter
 * @author WYG
 */
@Slf4j
public abstract class BaseQueryController<T, I> {

    @Autowired
    private IService<T, I> service;

    /**
     * 查询全部结果, findAll()方法能达到同样的效果
     *
     * @return the list
     */
    @Operation(summary = "查询全部")
    @GetMapping("/all")
    public Result<List<T>> findAll() {
        log.debug("findAll - 查询全部结果");

        return ResultGenerator.ok(service.findAll());
    }

    /**
     * 分页查询
     *
     * @param pageable the pageable
     * @return the page
     */
    @Operation(summary = "分页查询")
    @GetMapping(value = "/page")
    public Result<Page<T>> findAll(Pageable pageable) {
        log.debug("findAll - 分页查询. pageable={}", pageable);

        return ResultGenerator.ok(service.findAll(pageable));
    }

    /**
     * 根据主键字段进行查询, 方法参数必须包含完整的主键属性, 查询条件使用等号
     *
     * @param id the id
     * @return the t
     */
    @Operation(summary = "根据ID字段进行查询")
    @GetMapping("/{id}")
    public Result<T> findById(@Parameter(name = "id", description = "ID", example = "0", required = true) @PathVariable I id) {
        log.debug("findById - 根据Id查询. id={}", id);

        return ResultGenerator.ok(service.findById(id));
    }

    /**
     * 根据ID集合进行查询
     *
     * @param list the list
     * @return the list
     */
    @Operation(summary = "根据ID集合进行查询")
    @GetMapping("/all/by/id")
    public Result<List<T>> findAllById(List<I> list) {
        log.debug("findAllById - 根据ID集合进行查询. list={}", list);

        return ResultGenerator.ok(service.findAllById(list));
    }

    /**
     * 根据属性值进行查询, 查询条件使用等号
     *
     * @param example the example
     * @return the list
     */
    @Operation(summary = "根据属性进行查询")
    @GetMapping("/all/by/example")
    public Result<List<T>> findAllByExample(T example) {
        log.debug("findAllByExample - 根据中的属性值进行查询. ");

        return ResultGenerator.ok(service.findAllByExample(Example.of(example)));
    }

    /**
     * 根据属性分页查询
     *
     * @param example  the example
     * @param pageable the pageable
     * @return the page
     */
    @Operation(summary = "根据属性分页查询")
    @GetMapping(value = "/page/by/example")
    public Result<Page<T>> findAllByExample(T example, Pageable pageable) {
        log.debug("findAllByExample - 根据属性分页查询. example={}, pageable={}", example, pageable);

        return ResultGenerator.ok(service.findAllByExample(Example.of(example), pageable));
    }

    /**
     * 查询总数
     *
     * @return the int
     */
    @Operation(summary = "查询总数")
    @GetMapping("/count")
    public Result<Long> findCount() {
        log.debug("findCount - 查询总数. ");

        return ResultGenerator.ok(service.findCount());
    }

    /**
     * 根据中的属性查询总数, 查询条件使用等号
     *
     * @param example the example
     * @return the int
     */
    @Operation(summary = "根据中的属性查询总数")
    @GetMapping("/count/by/example")
    public Result<Long> findCountByExample(T example) {
        log.debug("findCountByExample - 根据中的属性查询总数, 查询条件使用等号. example={}", example);

        return ResultGenerator.ok(service.findCountByExample(Example.of(example)));
    }
}
