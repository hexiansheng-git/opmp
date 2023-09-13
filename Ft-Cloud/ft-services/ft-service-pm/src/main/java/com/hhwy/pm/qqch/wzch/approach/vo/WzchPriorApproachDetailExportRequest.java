package com.hhwy.pm.qqch.wzch.approach.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 * 优先进场物资详情导出对象
 * @author HCT
 */
@Data
public class WzchPriorApproachDetailExportRequest implements Serializable {
    private Long priorApproachId;
    private List<Long> idList;
}
