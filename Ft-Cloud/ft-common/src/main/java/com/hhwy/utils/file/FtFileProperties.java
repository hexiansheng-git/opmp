package com.hhwy.utils.file;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 */
@Component
@ConfigurationProperties(prefix = "file")
@Data
@RefreshScope
public class FtFileProperties {
    /**
     * 请求地址
     */

    private String url;
    /**
     * 根据附件组id复制数据
     */
    private String copyFilesByGroupIdPath;
    /**
     * 根据附件组id获取所有附件
     */
    private String listPath;

    /**
     * 根据多个附件组id获取所有附件
     */
    private String fileInfoByGroupIdsPath;

    /**
     * 根据附件组id集合获取附件数量
     */
    private String countByGroupIdsPath;


}
