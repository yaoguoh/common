package com.github.yaoguoh.common.util.model.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Taobao position vo.
 *
 * @author yaoguohh
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaobaoPositionVO {
    /**
     * 国家名称
     */
    private String country;
    /**
     * 行政区名称
     */
    private String region;
    /**
     * 城市名称
     */
    private String city;
    /**
     * 运营商
     */
    private String isp;
}
