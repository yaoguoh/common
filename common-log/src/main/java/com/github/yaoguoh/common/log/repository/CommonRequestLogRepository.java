package com.github.yaoguoh.common.log.repository;

import com.github.yaoguoh.common.log.domain.CommonRequestLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * The interface Common request log repository.
 *
 * @author WYG
 */
public interface CommonRequestLogRepository extends JpaRepository<CommonRequestLog, Long>, JpaSpecificationExecutor<CommonRequestLog> {

}
