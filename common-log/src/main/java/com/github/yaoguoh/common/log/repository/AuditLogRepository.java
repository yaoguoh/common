package com.github.yaoguoh.common.log.repository;

import com.github.yaoguoh.common.log.model.domain.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * The interface Common log repository.
 *
 * @author yaoguohh
 */
public interface AuditLogRepository extends JpaRepository<AuditLog, Long>, JpaSpecificationExecutor<AuditLog> {

    /**
     * Find modules list.
     *
     * @return the list
     */
    @Query("SELECT auditLog.module FROM AuditLog auditLog GROUP BY auditLog.module")
    List<String> findModules();
}
