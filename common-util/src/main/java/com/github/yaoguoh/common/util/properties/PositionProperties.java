package com.github.yaoguoh.common.util.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 定位接口配置类
 *
 * @author yaoguohh
 */
@Data
@ConfigurationProperties(prefix = "util.position")
public class PositionProperties {

    private Amap      amap      = new Amap();
    private Taobao    taobao    = new Taobao();
    private Ip2region ip2region = new Ip2region();

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
        /**
         * 请求超时时间（毫秒）
         */
        private int    timeout = 3000;
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
        /**
         * 请求超时时间（毫秒）
         */
        private int    timeout   = 3000;
    }

    /**
     * ip2region
     */
    @Data
    public static class Ip2region {
        /**
         * IP数据Url
         */
        private String xdbUrl   = "https://github.com/lionsoul2014/ip2region/raw/master/data/ip2region.xdb";
        /**
         * 保存数据到本地文件目录
         */
        private String savePath = "data/ip2region.xdb";
    }
}
