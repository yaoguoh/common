package com.github.yaogouh.jpa.domain;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * The type Base domain.
 *
 * @author WYG
 */
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseDomain implements Serializable {

    private static final long serialVersionUID = 1191451105487961993L;

    /**
     * The Id.
     */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    protected Long id;

    /**
     * 创建时间
     */
    @Column(name = "created_date", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    protected Date createdDate;

    /**
     * 创建者
     */
    @Column(name = "created_by", updatable = false)
    @CreatedBy
    protected String createdBy;

    /**
     * 最后修改时间
     */
    @Column(name = "last_modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    protected Date lastModifiedDate;

    /**
     * 最后修改者
     */
    @Column(name = "last_modified_by")
    @LastModifiedBy
    protected String lastModifiedBy;
}
