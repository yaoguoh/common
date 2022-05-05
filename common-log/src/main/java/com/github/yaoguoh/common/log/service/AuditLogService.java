package com.github.yaoguoh.common.log.service;

import com.github.yaoguoh.common.jpa.support.IService;
import com.github.yaoguoh.common.log.model.domain.AuditLog;
import com.github.yaoguoh.common.log.model.dto.QueryAuditLogDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

/**
 * The interface Audit log service.
 *
 * @author yaoguohh
 */
public interface AuditLogService extends IService<AuditLog, Long> {

    /**
     * Find all list.
     *
     * @param queryAuditLogDTO the query audit log dto
     * @return the list
     */
    List<AuditLog> findAll(QueryAuditLogDTO queryAuditLogDTO);

    /**
     * Find all page.
     *
     * @param queryAuditLogDTO the query audit log dto
     * @param pageable         the pageable
     * @return the page
     */
    Page<AuditLog> findAll(QueryAuditLogDTO queryAuditLogDTO, Pageable pageable);

    /**
     * Save log.
     *
     * @param auditLog the audit log
     */
    @Async
    void saveLog(AuditLog auditLog);

    /**
     * Find modules list.
     *
     * @return the list
     */
    List<String> findModules();
}
