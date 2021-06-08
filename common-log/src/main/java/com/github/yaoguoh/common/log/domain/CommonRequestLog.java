package com.github.yaoguoh.common.log.domain;

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
@Table(name = "common_request_log")
@NoArgsConstructor
@AllArgsConstructor
public class CommonRequestLog extends BaseDomain {

    private static final long serialVersionUID = -7646436886248178474L;

    /**
     * 操作地址
     */
    private String ip;
    /**
     * 请求url
     */
    @Column(name = "`url`", columnDefinition = "Text")
    private String url;
    /**
     * HTTP 请求方式
     */
    private String httpMethod;
    /**
     * 请求参数
     */
    @Column(name = "request_param", columnDefinition = "Text")
    private String requestParam;
    /**
     * JAVA 方法
     */
    private String classMethod;
    /**
     * 返回数据
     */
    @Column(name = "result", columnDefinition = "Text")
    private String result;
    /**
     * 用时
     */
    @Column(name = "time_cost")
    private Long   timeCost;
}
