package com.github.yaoguoh.common.log.service.impl;

import com.github.yaoguoh.common.jpa.support.BaseService;
import com.github.yaoguoh.common.log.model.domain.AuditLog;
import com.github.yaoguoh.common.log.model.domain.AuditLog_;
import com.github.yaoguoh.common.log.model.dto.QueryAuditLogDTO;
import com.github.yaoguoh.common.log.repository.AuditLogRepository;
import com.github.yaoguoh.common.log.service.AuditLogService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 异步调用日志服务
 *
 * @author WYG
 */
@Service
@AllArgsConstructor
public class AuditLogServiceImpl extends BaseService<AuditLog, Long> implements AuditLogService {

    private final AuditLogRepository auditLogRepository;

    @Override
    public List<AuditLog> findAll(QueryAuditLogDTO queryAuditLogDTO) {

        return auditLogRepository.findAll(this.buildSpecification(queryAuditLogDTO));
    }

    @Override
    public Page<AuditLog> findAll(QueryAuditLogDTO queryAuditLogDTO, Pageable pageable) {

        return auditLogRepository.findAll(this.buildSpecification(queryAuditLogDTO), pageable);
    }

    /**
     * 保存系统日志记录
     *
     * @param auditLog the system operate log
     */
    @Async
    @Override
    public void saveLog(AuditLog auditLog) {
        auditLogRepository.save(auditLog);
    }

    private Specification<AuditLog> buildSpecification(QueryAuditLogDTO queryAuditLogDTO) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            // 所有的断言及条件
            List<Predicate> predicates = new ArrayList<>();
            // 服务名称
            if (StringUtils.isNotBlank(queryAuditLogDTO.getProvider())) {
                predicates.add(criteriaBuilder.like(root.get(AuditLog_.provider), queryAuditLogDTO.getProvider()));
            }
            // 操作模块
            if (StringUtils.isNotBlank(queryAuditLogDTO.getModule())) {
                predicates.add(criteriaBuilder.like(root.get(AuditLog_.module), queryAuditLogDTO.getModule()));
            }
            // 最后处理人
            if (StringUtils.isNotBlank(queryAuditLogDTO.getUsername())) {
                predicates.add(criteriaBuilder.like(root.get(AuditLog_.createdBy), queryAuditLogDTO.getUsername()));
            }
            // 业务类型
            if (ObjectUtils.isNotEmpty(queryAuditLogDTO.getBusinessType())) {
                predicates.add(criteriaBuilder.equal(root.get(AuditLog_.businessType), queryAuditLogDTO.getBusinessType().ordinal()));
            }
            // 操作状态
            if (ObjectUtils.isNotEmpty(queryAuditLogDTO.getStatus())) {
                predicates.add(criteriaBuilder.equal(root.get(AuditLog_.status), queryAuditLogDTO.getStatus()));
            }
            // 时间范围查询
            if (ObjectUtils.isNotEmpty(queryAuditLogDTO.getStartTime())) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(AuditLog_.createdDate).as(LocalDateTime.class), queryAuditLogDTO.getStartTime()));
            }
            if (ObjectUtils.isNotEmpty(queryAuditLogDTO.getEndTime())) {
                predicates.add(criteriaBuilder.lessThan(root.get(AuditLog_.createdDate).as(LocalDateTime.class), queryAuditLogDTO.getEndTime()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
