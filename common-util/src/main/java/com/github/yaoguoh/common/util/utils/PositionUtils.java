package com.github.yaoguoh.common.util.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.yaoguoh.common.util.model.vo.AmapPositionVO;
import com.github.yaoguoh.common.util.model.vo.TaobaoPositionVO;
import com.github.yaoguoh.common.util.properties.PositionProperties;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.lionsoul.ip2region.xdb.Searcher;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Optional;


/**
 * 获取地址工具类
 *
 * @author ruoyi
 */
@Slf4j
@Component
@EnableConfigurationProperties({PositionProperties.class})
public class PositionUtils {
    private final PositionProperties positionProperties;

    private String ip2regionPath;
    private byte[] ip2regionIndex;

    /**
     * Instantiates a new Position utils.
     *
     * @param positionProperties the position properties
     */
    public PositionUtils(PositionProperties positionProperties) {
        this.positionProperties = positionProperties;
    }

    /**
     * Load.
     */
    @PostConstruct
    public void load() {
        PositionProperties.Ip2region ip2region = positionProperties.getIp2region();
        try {
            File file = FileUtil.file(ip2region.getSavePath());
            if (!FileUtil.exist(file)) {
                log.info("Start loading Ip2region.xdb file");
                HttpUtil.downloadFile(ip2region.getXdbUrl(), file);
            }
            // 预加载 ip2region.xdb｜VectorIndex 缓存
            ip2regionPath = file.getPath();
            ip2regionIndex = Searcher.loadVectorIndexFromFile(ip2regionPath);
            log.info("Ip2region load vector index success");
        } catch (IOException e) {
            log.error("failed to load vector index from [{}]", ip2regionPath);
        }
    }

    /**
     * Amap address optional.
     *
     * @param ip the ip
     * @return the amap address vo
     */
    @Cacheable(cacheNames = "amap_position", key = "#ip")
    public Optional<AmapPositionVO> amapPosition(String ip) {
        PositionProperties.Amap amap = positionProperties.getAmap();
        try {
            String         url            = amap.getAddress() + "?key=" + amap.getKey() + "&ip=" + ip;
            String         result         = HttpUtil.get(url, amap.getTimeout());
            AmapPositionVO amapPositionVO = JSON.parseObject(result).toJavaObject(AmapPositionVO.class);
            if (Integer.valueOf(1).equals(amapPositionVO.getStatus())) {
                return Optional.of(amapPositionVO);
            }
        } catch (Exception e) {
            log.error("AMAP 获取定位失败. Error = [{}]", e.getMessage());
        }
        return Optional.empty();
    }

    /**
     * Amap address optional.
     *
     * @param ip the ip
     * @return the amap address vo
     */
    @Cacheable(cacheNames = "tao_position", key = "#ip")
    public Optional<TaobaoPositionVO> taobaoPosition(String ip) {
        PositionProperties.Taobao taobao = positionProperties.getTaobao();
        try {
            String     url        = taobao.getAddress() + "?accessKey=" + taobao.getAccessKey() + "&ip=" + ip;
            String     result     = HttpUtil.get(url, taobao.getTimeout());
            JSONObject jsonObject = JSON.parseObject(result);
            if (ObjectUtils.isEmpty(jsonObject) && Integer.valueOf(0).equals(jsonObject.getInteger("code"))) {
                return Optional.of(jsonObject.getJSONObject("data").toJavaObject(TaobaoPositionVO.class));
            }
        } catch (Exception e) {
            log.warn("Taobao 获取定位失败. Error = [{}]", e.getMessage());
        }
        return Optional.empty();
    }

    /**
     * Amap address optional.
     *
     * @param ip the ip
     * @return the amap address vo
     */
    @Cacheable(cacheNames = "ip2region_position", key = "#ip")
    public Optional<String> ip2regionPosition(String ip) {
        try {
            Searcher searcher = Searcher.newWithVectorIndex(ip2regionPath, ip2regionIndex);
            return Optional.of(searcher.search(ip));
        } catch (Exception e) {
            log.warn("Ip2region 获取定位失败. failed to create content cached searcher: {}", e.getMessage());
        }
        return Optional.empty();
    }
}
