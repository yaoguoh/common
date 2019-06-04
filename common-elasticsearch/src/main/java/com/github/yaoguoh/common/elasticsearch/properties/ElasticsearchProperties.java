package com.github.yaoguoh.common.elasticsearch.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * elasticsearch 配置
 *
 * @author WYG
 */
@Data
@ConfigurationProperties("elasticsearch")
public class ElasticsearchProperties {
    /**
     * XPack 用户名
     */
    private String  xpackUsername;
    /**
     * XPack 密码
     */
    private String  xpackPassword;
    /**
     * 密钥库
     */
    private String  keystore;
    /**
     * 密钥库密码
     */
    private String  keystorePassword;
    /**
     * 主机地址
     */
    private String  host;
    /**
     * 端口
     */
    private Integer port;
    /**
     * 协议
     */
    private String  scheme;
}
