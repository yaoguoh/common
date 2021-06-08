package com.github.yaoguoh.common.log.service;

import com.github.yaoguoh.common.log.domain.CommonRequestLog;
import com.github.yaoguoh.common.log.repository.CommonRequestLogRepository;
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
    private final CommonRequestLogRepository commonRequestLogRepository;

    /**
     * 保存系统日志记录
     *
     * @param commonRequestLog the system operate log
     */
    @Async
    public void saveLog(CommonRequestLog commonRequestLog) {
        commonRequestLogRepository.save(commonRequestLog);
    }
}
