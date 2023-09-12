package com.hhwy.pm.qqch.wzch.demand.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 物资总需列表导出对象请求
 * @author HCT
 */
@Data
public class WzchTotalDemandExportRequest implements Serializable {

    private List<Long> ids;
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
