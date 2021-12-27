package com.github.yaoguoh.common.doc.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * The type Swagger properties.
 *
 * @author WYG
 */
@Data
@ConfigurationProperties("doc.info")
public class DocProperties {

    /**
     * 分组名称
     */
    private String group       = "default";
    /**
     * 标题
     */
    private String title       = "API";
    /**
     * 描述
     */
    private String description = "RESTFUL API";
    /**
     * 版本
     */
    private String version     = "v2.0.0";
    /**
     * 接口调用地址
     */
    private String serverUrl;
    /**
     * 执照
     */
    private String license     = "Apache 2.0";
    /**
     * 执照地址
     */
    private String licenseUrl  = "https://www.apache.org/licenses/LICENSE-2.0.html";
}
