package com.github.yaoguoh.common.log.controller;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.github.yaoguoh.common.log.annotation.Log;
import com.github.yaoguoh.common.log.enums.BusinessType;
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
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * The type Log controller.
 *
 * @author yaoguohh
 */
@Tag(name = "LOG REST API")
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = "/log")
public class LogController {

    private final AuditLogService auditLogService;

    /**
     * Find all result.
     *
     * @param queryAuditLogDTO the query audit log dto
     * @param pageable         the pageable
     * @return the result
     */
    @Operation(summary = "查询审计日志(分页)")
    @GetMapping(value = "/audit/page")
    public Result<Page<AuditLog>> findAll(QueryAuditLogDTO queryAuditLogDTO, @PageableDefault(size = 20, sort = {AuditLog_.CREATED_DATE}, direction = Sort.Direction.DESC) Pageable pageable) {
        log.debug("findAll - 查询审计日志(分页). QueryAuditLogDTO = [{}]", queryAuditLogDTO);

        return ResultGenerator.ok(auditLogService.findAll(queryAuditLogDTO, pageable));
    }

    /**
     * 导出审计日志
     *
     * @param queryAuditLogDTO the query audit log dto
     * @param response         the response
     */
    @Log(module = "审计日志", businessType = BusinessType.EXPORT)
    @Operation(summary = "导出审计日志")
    @PostMapping(value = "/audit/export")
    public void export(@RequestBody QueryAuditLogDTO queryAuditLogDTO, HttpServletResponse response) {
        log.debug("export - 导出审计日志. QueryAuditLogDTO = [{}]", queryAuditLogDTO);

        List<AuditLog> summaryList = auditLogService.findAll(queryAuditLogDTO);
        try (ExcelWriter writer = ExcelUtil.getWriter(true); ServletOutputStream outputStream = response.getOutputStream()) {
            writer.write(summaryList, true);
            String filename = "attachment;filename=AUDIT_LOG" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm")) + ".xlsx";
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, filename);
            writer.flush(outputStream, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
