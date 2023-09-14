package com.hhwy.pm.qqch.wzch.priorpurchase.dto;

import com.hhwy.pm.qqch.wzch.priorpurchase.domain.WzchPriorPurchase;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * 优先进场物资设备采购策划DTO
 *
 * @author Administrator
 */
@Data
@ToString
public class WzchPriorPurchaseDTO extends WzchPriorPurchase {


    /**
     * 物资详情
     */
    private List<WzchPriorPurchaseDetailDTO> detailList;


    private String actCode;



}
