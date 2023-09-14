package com.hhwy.pm.qqch.wzch.puchasesupply.dto;

import com.hhwy.pm.qqch.wzch.puchasesupply.domain.WzchPurchaseSupply;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author mls
 */
@Data
@ToString
public class WzchPurchaseSupplyDTO extends WzchPurchaseSupply {

    /**
     * 物资详情
     */
    private List<WzchPurchaseSupplyDetailDTO> detailList;

    /**
     * 版本号 v1.0
     */
    private String versionCodeStr;



    private String actCode;



}
