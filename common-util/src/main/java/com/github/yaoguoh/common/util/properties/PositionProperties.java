package com.github.yaoguoh.common.util.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 定位接口配置类
 *
 * @author yaoguohh
 */
@Data
@ConfigurationProperties(prefix = "position")
public class PositionProperties {

    private Amap amap = new Amap();

    private Taobao taobao = new Taobao();

    /**
     * 高德通过IP查询地址接口配置
     */
    @Data
    public static class Amap {
        /**
         * 请求服务权限标识
         */
        private String key;
        /**
         * 服务器地址
         */
        private String address = "https://restapi.amap.com/v3/ip";
    }

    /**
     * 淘宝IP查询地址接口配置
     */
    @Data
    public static class Taobao {
        /**
         * 请求服务权限标识
         */
        private String accessKey = "alibaba-inc";
        /**
         * 服务器地址
         */
        private String address   = "https://ip.taobao.com/outGetIpInfo";
    }
}
