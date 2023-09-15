package com.hhwy.pm.qqch.wzch.localpuchasesupply.dto;

import com.hhwy.pm.qqch.wzch.localpuchasesupply.domain.WzchLocalPurchaseSupply;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author mls
 */
@Data
@ToString
public class WzchLocalPurchaseSupplyDTO extends WzchLocalPurchaseSupply {

    /**
     * 物资详情
     */
    private List<WzchLocalPurchaseSupplyDetailDTO> detailList;

    /**
     * 版本号 v1.0
     */
    private String versionCodeStr;



    private String actCode;



}
