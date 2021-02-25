package com.github.yaogouh.common.job.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * The type Swagger properties.
 *
 * @author WYG
 */
@Data
@ConfigurationProperties("job")
public class JobProperties {

    /**
     * xxl-job admin address list, such as "http://address" or "http://address01,http://address02"
     */
    private String   adminAddresses;
    /**
     * xxl-job, access token
     */
    private String   accessToken;
    /**
     * executor
     */
    private Executor executor = new Executor();

    /**
     * The type Executor.
     */
    @Data
    @NoArgsConstructor
    public static class Executor {
        /**
         * xxl-job executor app name
         */
        private String appName;
        /**
         * xxl-job executor registry-address: default use address to registry , otherwise use ip:port if address is null
         */
        private String address;
        /**
         * xxl-job executor server ip
         */
        private String ip;
        /**
         * xxl-job executor server port
         */
        private int    port;
        /**
         * xxl-job executor log-path
         */
        private String logPath;
        /**
         * xxl-job executor log-retention-days, default 30 day.
         */
        private int    logRetentionDays = 30;
    }
}
