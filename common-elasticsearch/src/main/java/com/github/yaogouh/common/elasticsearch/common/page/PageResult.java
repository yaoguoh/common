package com.github.yaogouh.common.elasticsearch.common.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p> 通用分页参数返回 </p>
 *
 * @param <T> the type parameter
 * @author WYG
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> implements Serializable {
    private static final long serialVersionUID = 3420391142991247367L;

    /**
     * 当前页数据
     */
    private List<T> rows;

    /**
     * 总条数
     */
    private Long total;

    /**
     * Of page result.
     *
     * @param <T>   the type parameter
     * @param rows  the rows
     * @param total the total
     * @return the page result
     */
    public static <T> PageResult of(List<T> rows, Long total) {
        return new PageResult<>(rows, total);
    }
}
