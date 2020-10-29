package com.github.yaogouh.common.log.domain;

import com.github.yaoguoh.common.jpa.domain.BaseDomain;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 操作日志记录表 operate_log
 *
 * @author WYG
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Builder
@Table(name = "common_system_operate_log")
@NoArgsConstructor
@AllArgsConstructor
public class SystemOperateLog extends BaseDomain {

    private static final long serialVersionUID = -7646436886248178474L;

    /**
     * 操作状态（0 成功 1 失败）
     */
    private Integer status;
    /**
     * 操作模块
     */
    private String  title;
    /**
     * 操作类型（0新增 1修改 2删除 3导出 4导入 5其它）
     */
    private Integer targetType;
    /**
     * JAVA方法
     */
    private String  method;
    /**
     * 请求url
     */
    @Column(name = "`url`", columnDefinition = "Text")
    private String  url;
    /**
     * 操作地址
     */
    private String  ip;
    /**
     * 请求方式
     */
    private String  requestMethod;
    /**
     * 请求参数
     */
    @Column(name = "request_param", columnDefinition = "Text")
    private String  requestParam;
    /**
     * 返回参数
     */
    @Column(name = "result", columnDefinition = "Text")
    private String  result;
    /**
     * 错误消息
     */
    @Column(name = "error_message", columnDefinition = "Text")
    private String  errorMessage;
}
