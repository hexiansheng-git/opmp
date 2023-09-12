package com.hhwy.pm.qqch.wzch.demand.vo;

import lombok.Data;
import lombok.ToString;

/**
 *
 * @author HCT
 */
@Data
@ToString
public class WzchTotalDemandVo {
    /**
     * 标题
     */
    private String title;
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 是否有效 0-失效 1-有效
     */
    private String valid;
    /**
     * 区域id
     */
    private Long regionId;
}
