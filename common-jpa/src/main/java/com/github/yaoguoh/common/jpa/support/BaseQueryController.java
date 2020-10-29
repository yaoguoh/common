package com.github.yaoguoh.common.jpa.support;

import com.github.yaoguoh.common.util.result.Result;
import com.github.yaoguoh.common.util.result.ResultGenerator;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import springfox.documentation.annotations.ApiIgnore;

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
     * 根据主键字段进行查询, 方法参数必须包含完整的主键属性, 查询条件使用等号
     *
     * @param id the id
     * @return the t
     */
    @ApiOperation(value = "根据ID字段进行查询")
    @GetMapping("/{id}")
    public Result<T> findById(@ApiParam(name = "id", value = "实体ID", required = true) @PathVariable I id) {
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
    public Result<T> findOneByExample(T example) {
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
    public Result<List<T>> findAllByIdList(List<I> list) {
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
     * 根据Pageable进行分页查询
     *
     * @param pageable the pageable
     * @return the page
     */
    @ApiOperation(value = "根据Pageable进行分页查询")
    @GetMapping(value = "/list/by/pageable")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataTypeClass = Integer.class, paramType = "query",
                    value = "要检索的结果页(从0开始).", defaultValue = "0"),
            @ApiImplicitParam(name = "size", dataTypeClass = Integer.class, paramType = "query",
                    value = "每页记录数.", defaultValue = "20"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataTypeClass = Sort.class, paramType = "query",
                    value = "排序的标准格式格式(属性,asc|desc). " +
                            "默认排序顺序为升序. " +
                            "支持多排序条件.")
    })
    public Result<Page<T>> findByPageable(@ApiIgnore("忽略，因为`swagger`参数显示界面错误.") Pageable pageable) {
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
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataTypeClass = Integer.class, paramType = "query",
                    value = "要检索的结果页(从0开始).", defaultValue = "0"),
            @ApiImplicitParam(name = "size", dataTypeClass = Integer.class, paramType = "query",
                    value = "每页记录数.", defaultValue = "20"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataTypeClass = Sort.class, paramType = "query",
                    value = "排序的标准格式格式(属性,asc|desc). " +
                            "默认排序顺序为升序. " +
                            "支持多排序条件.")
    })
    public Result<Page<T>> findByExampleAndPageable(T example, @ApiIgnore("忽略，因为`swagger`参数显示界面错误.") Pageable pageable) {
        log.info("findAllByExampleAndPageable - 根据实体属性和Pageable进行分页查询. example={}, pageable={}", example, pageable);

        return ResultGenerator.ok(service.findAllByExampleAndPageable(Example.of(example), pageable));
    }
}
