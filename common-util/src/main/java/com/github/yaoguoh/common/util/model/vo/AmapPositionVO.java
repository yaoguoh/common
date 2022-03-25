package com.github.yaoguoh.common.util.model.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Amap address vo.
 *
 * @author yaoguohh
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AmapPositionVO {

    /**
     * 返回结果状态值
     * <p>
     * 值为0或1,0表示失败；1表示成功
     */
    private Integer status;
    /**
     * 返回状态说明
     * <p>
     * 返回状态说明，status为0时，info返回错误原因，否则返回“OK”。
     */
    private String  info;
    /**
     * 省份名称
     * <p>
     * 若为直辖市则显示直辖市名称；
     * <p>
     * 如果在局域网 IP网段内，则返回“局域网”；
     * <p>
     * 非法IP以及国外IP则返回空
     */
    private String  province;
    /**
     * 城市名称
     * <p>
     * 若为直辖市则显示直辖市名称；
     * <p>
     * 如果为局域网网段内IP或者非法IP或国外IP，则返回空
     */
    private String  city;
    /**
     * 所在城市矩形区域范围
     * <p>
     * 所在城市范围的左下右上对标对
     */
    private String  rectangle;
}
