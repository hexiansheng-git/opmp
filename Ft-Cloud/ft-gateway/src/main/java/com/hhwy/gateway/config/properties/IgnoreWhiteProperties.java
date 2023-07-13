package com.hhwy.gateway.config.properties;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * 放行白名单配置
 *
 * @author hhwy
 */
@Configuration
@RefreshScope
@ConfigurationProperties(prefix = "ignore")
public class IgnoreWhiteProperties {
    /**
     * 放行白名单配置，网关不校验此处的白名单
     */
    private List<String> whites = new ArrayList<>();

    /**
     * 进行权限校验，但不重置token过期时间的url配置列表
     */
    private List<String> inessential = new ArrayList<>();

    public List<String> getWhites() {
        return whites;
    }

    public void setWhites(List<String> whites) {
        this.whites = whites;
    }

    public List<String> getInessential() {
        return inessential;
    }

    public void setInessential(List<String> inessential) {
        this.inessential = inessential;
    }
}
