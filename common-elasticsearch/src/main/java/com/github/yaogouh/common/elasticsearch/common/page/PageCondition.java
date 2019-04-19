package com.github.yaogouh.elasticsearch.common.page;

import lombok.Data;

/**
 * <p> 分页请求参数 </p>
 *
 * @author WYG
 */
@Data
public class PageCondition {
    /**
     * 当前页码
     */
    private Integer currentPage;

    /**
     * 每页条数
     */
    private Integer pageSize;

    /**
     * 获取起始索引值.
     *
     * @return the int
     */
    public int getStartRow(){
        return (currentPage - 1) * pageSize;
    }
}
