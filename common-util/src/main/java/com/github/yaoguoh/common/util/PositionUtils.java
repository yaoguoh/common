package com.github.yaoguoh.common.util;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.yaoguoh.common.util.model.vo.AmapPositionVO;
import com.github.yaoguoh.common.util.model.vo.TaobaoPositionVO;
import com.github.yaoguoh.common.util.properties.PositionProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Optional;


/**
 * 获取地址工具类
 *
 * @author ruoyi
 */
@Slf4j
@Component
@AllArgsConstructor
@EnableConfigurationProperties({PositionProperties.class})
public class PositionUtils {

    private static final String TAOBAO_RESULT_CODE = "code";
    private static final String TAOBAO_RESULT_DATA = "data";
    
    private final PositionProperties positionProperties;

    /**
     * Amap address optional.
     *
     * @param ip the ip
     * @return the amap address vo
     */
    public Optional<AmapPositionVO> amapPosition(String ip) {
        PositionProperties.Amap amap = positionProperties.getAmap();
        try {
            String         url            = amap.getAddress() + "?key=" + amap.getKey() + "&ip=" + ip;
            String         result         = HttpUtil.get(url);
            AmapPositionVO amapPositionVO = JSON.parseObject(result).toJavaObject(AmapPositionVO.class);
            if (Integer.valueOf(1).equals(amapPositionVO.getStatus())) {
                return Optional.of(amapPositionVO);
            }
        } catch (Exception e) {
            log.error("获取定位失败. Error = [{}]", e.getMessage());
        }
        return Optional.empty();
    }

    /**
     * Amap address optional.
     *
     * @param ip the ip
     * @return the amap address vo
     */
    public Optional<TaobaoPositionVO> taobaoPosition(String ip) {
        PositionProperties.Taobao taobao = positionProperties.getTaobao();
        try {
            String     url        = taobao.getAddress() + "?accessKey=" + taobao.getAccessKey() + "&ip=" + ip;
            String     result     = HttpUtil.get(url);
            JSONObject jsonObject = JSON.parseObject(result);
            if (Integer.valueOf(0).equals(jsonObject.getInteger(TAOBAO_RESULT_CODE))) {
                return Optional.of(jsonObject.getJSONObject(TAOBAO_RESULT_DATA).toJavaObject(TaobaoPositionVO.class));
            }
        } catch (Exception e) {
            log.error("获取定位失败. Error = [{}]", e.getMessage());
        }
        return Optional.empty();
    }
}
