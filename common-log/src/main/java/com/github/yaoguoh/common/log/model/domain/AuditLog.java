package com.github.yaoguoh.common.log.model.domain;

import com.github.yaoguoh.common.log.enums.BusinessType;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 通用审计日志表
 *
 * @author WYG
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "common_audit_log")
@EntityListeners(AuditingEntityListener.class)
public class AuditLog implements Serializable {

    /**
     * The Id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long         id;
    /**
     * 服务名称
     */
    private String       provider;
    /**
     * 操作模块
     */
    private String       module;
    /**
     * 业务了类型
     */
    @Enumerated(EnumType.STRING)
    private BusinessType businessType;
    /**
     * 请求url
     */
    @Column(name = "`url`", length = 2550)
    private String       url;
    /**
     * 请求参数
     */
    @Column(length = 2550)
    private String       param;
    /**
     * HTTP 请求方式
     */
    private String       httpMethod;
    /**
     * JAVA 方法
     */
    private String       classMethod;
    /**
     * 操作人IP
     */
    private String       operatorIp;
    /**
     * 操作人地址
     */
    private String       operatorAddress;
    /**
     * 返回数据
     */
    @Column(length = 2550)
    private String       result;
    /**
     * 执行用时
     */
    private Long         processTime;
    /**
     * 操作状态（true 正常 false异常）
     */
    private Boolean      status;
    /**
     * 错误信息
     */
    @Column(length = 2550)
    private String       errorMessage;

    /**
     * 创建人
     */
    @Column(name = "created_by", updatable = false)
    @CreatedBy
    private String createdBy;

    /**
     * 创建时间
     */
    @Column(name = "created_date", updatable = false)
    @CreatedDate
    private LocalDateTime createdDate;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        AuditLog that = (AuditLog) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return this.getClass().hashCode();
    }
}
