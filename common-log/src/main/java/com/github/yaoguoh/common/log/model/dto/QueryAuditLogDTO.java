package com.github.yaoguoh.common.log.model.dto;


import com.github.yaoguoh.common.log.enums.BusinessType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * The type Query audit log dto.
 *
 * @author yaoguohh
 */
@Data
@Schema(description = "统计查询DTO")
public class QueryAuditLogDTO {

    @Schema(description = "服务名称")
    private String provider;

    @Schema(description = "操作模块")
    private String module;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "开始时间（时间范围查询）")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startTime;

    @Schema(description = "结束时间（时间范围查询）")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime endTime;

    @Schema(description = "业务类型")
    private BusinessType businessType;

    @Schema(description = "操作状态（ture：成功 ｜ false：失败）")
    private Boolean status;
}
