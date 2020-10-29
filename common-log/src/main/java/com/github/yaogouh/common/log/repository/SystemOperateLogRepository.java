package com.github.yaogouh.common.log.repository;

import com.github.yaogouh.common.log.domain.SystemOperateLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * The interface System operate log repository.
 *
 * @author WYG
 */
public interface SystemOperateLogRepository extends JpaRepository<SystemOperateLog, Long>, JpaSpecificationExecutor<SystemOperateLog> {

}
