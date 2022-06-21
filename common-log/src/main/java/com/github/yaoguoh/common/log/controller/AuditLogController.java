package com.github.yaoguoh.common.log.controller;

import com.github.yaoguoh.common.log.model.domain.AuditLog;
import com.github.yaoguoh.common.log.model.domain.AuditLog_;
import com.github.yaoguoh.common.log.model.dto.QueryAuditLogDTO;
import com.github.yaoguoh.common.log.service.AuditLogService;
import com.github.yaoguoh.common.util.result.Result;
import com.github.yaoguoh.common.util.result.ResultGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The type Log controller.
 *
 * @author yaoguohh
 */
@Tag(name = "审计日志 REST API")
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = "/log/audit")
public class AuditLogController {

    private final AuditLogService auditLogService;

    /**
     * Find all result.
     *
     * @param queryAuditLogDTO the query audit log dto
     * @param pageable         the pageable
     * @return the result
     */
    @Operation(summary = "查询审计日志(分页)")
    @GetMapping(value = "/page")
    public Result<Page<AuditLog>> findAll(QueryAuditLogDTO queryAuditLogDTO, @PageableDefault(size = 20, sort = {AuditLog_.CREATED_DATE}, direction = Sort.Direction.DESC) Pageable pageable) {
        log.debug("findAll - 查询审计日志(分页). QueryAuditLogDTO = [{}]", queryAuditLogDTO);

        return ResultGenerator.ok(auditLogService.findAll(queryAuditLogDTO, pageable));
    }

    /**
     * Find modules result.
     *
     * @return the result
     */
    @Operation(summary = "查询操作模块")
    @GetMapping(value = "/modules")
    public Result<List<String>> findModules() {
        log.debug("findModules - 查询操作模块.");

        return ResultGenerator.ok(auditLogService.findModules());
    }

}
