package com.github.yaoguoh.common.jpa.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * The type Base listener domain.
 *
 * @author WYG
 */
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseListenerDomain implements Serializable {

    /**
     * 创建人
     */
    @Column(name = "created_by", updatable = false)
    @CreatedBy
    protected String createdBy;

    /**
     * 最后修改人
     */
    @Column(name = "last_modified_by")
    @LastModifiedBy
    protected String lastModifiedBy;

    /**
     * 创建时间
     */
    @Column(name = "created_date", updatable = false)
    @CreatedDate
    protected LocalDateTime createdDate;

    /**
     * 最后修改时间
     */
    @Column(name = "last_modified_date")
    @LastModifiedDate
    protected LocalDateTime lastModifiedDate;
}
