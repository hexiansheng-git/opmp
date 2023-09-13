package com.hhwy.pm.qqch.wzch.approach.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 优先进场物资导出请求对象
 * @author HCT
 */
@Data
public class WzchPriorApproachExportRequest implements Serializable {
    private String title;
    private String regionName;
    private String projectName;
    private List<Long> idList;
}
