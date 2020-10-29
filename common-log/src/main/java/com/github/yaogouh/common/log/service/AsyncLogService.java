package com.github.yaogouh.common.log.service;

import com.github.yaogouh.common.log.domain.SystemOperateLog;
import com.github.yaogouh.common.log.repository.SystemOperateLogRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 异步调用日志服务
 *
 * @author WYG
 */
@Service
@AllArgsConstructor
public class AsyncLogService {
    private final SystemOperateLogRepository systemOperateLogRepository;

    /**
     * 保存系统日志记录
     *
     * @param systemOperateLog the system operate log
     */
    @Async
    public void saveLog(SystemOperateLog systemOperateLog) {
        systemOperateLogRepository.save(systemOperateLog);
    }
}
