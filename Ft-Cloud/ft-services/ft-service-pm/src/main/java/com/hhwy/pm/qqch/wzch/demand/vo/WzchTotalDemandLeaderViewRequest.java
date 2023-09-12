package com.hhwy.pm.qqch.wzch.demand.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 物资总需请求对象-领导视角
 * @author HCT
 */
@Data
public class WzchTotalDemandLeaderViewRequest implements Serializable {

    /** 物资设备名称 */
    private String materialName;
    /** 规格型号 */
    private String materialSpec;
    /** 执行标准列表 */
    private List<String> materialStandardList;

    private Integer pageNum;

    private Integer pageSize;

}
