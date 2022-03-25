package com.github.yaoguoh.common.log.repository;

import com.github.yaoguoh.common.log.model.domain.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * The interface Common log repository.
 *
 * @author yaoguohh
 */
public interface AuditLogRepository extends JpaRepository<AuditLog, Long>, JpaSpecificationExecutor<AuditLog> {

}
