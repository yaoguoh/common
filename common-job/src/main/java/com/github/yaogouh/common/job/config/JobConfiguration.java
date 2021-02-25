package com.github.yaogouh.common.job.config;

import com.github.yaogouh.common.job.properties.JobProperties;
import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * The type Job configuration.
 *
 * @author WYG
 */
@Slf4j
@AllArgsConstructor
@EnableConfigurationProperties(JobProperties.class)
public class JobConfiguration {

    private final JobProperties jobProperties;

    /**
     * Xxl job executor xxl job spring executor.
     *
     * @return the xxl job spring executor
     */
    @Bean
    public XxlJobSpringExecutor xxlJobExecutor() {
        log.info(">>>>>>>>>>> xxl-job config init. <<<<<<<<<<<<");
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        // xxl-job admin config
        xxlJobSpringExecutor.setAdminAddresses(jobProperties.getAdminAddresses());
        xxlJobSpringExecutor.setAccessToken(jobProperties.getAccessToken());
        // executor config
        JobProperties.Executor executor = jobProperties.getExecutor();
        xxlJobSpringExecutor.setAppname(executor.getAppName());
        xxlJobSpringExecutor.setAddress(executor.getAddress());
        xxlJobSpringExecutor.setIp(executor.getIp());
        xxlJobSpringExecutor.setPort(executor.getPort());
        xxlJobSpringExecutor.setLogPath(executor.getLogPath());
        xxlJobSpringExecutor.setLogRetentionDays(executor.getLogRetentionDays());

        return xxlJobSpringExecutor;
    }
}
