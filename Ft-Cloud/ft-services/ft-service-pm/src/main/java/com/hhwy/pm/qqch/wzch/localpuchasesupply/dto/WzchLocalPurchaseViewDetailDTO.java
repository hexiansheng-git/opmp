package com.hhwy.pm.qqch.wzch.localpuchasesupply.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 采购供应策划材料视角物资详情对象 wzch_purchase_supply_detail
 *
 * @author mls
 * @date 2022-11-17
 */
@ToString
@Data
public class WzchLocalPurchaseViewDetailDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String projectId;

    private String planPurchaseDateGroup;

    private Boolean canDelete = true;

    private List<PurchaseView> children;

    @ToString
    @Data
    public static class PurchaseView implements Serializable {
        @JsonSerialize(using = ToStringSerializer.class)
        private Long id;
        private String source;
        private List<WzchLocalPurchaseSupplyDetailDTO> children;
    }

    public static void main(String[] args) {
        Long lll = 1626448889991286780L;
        Long llj = 1588328994132115L;
        System.out.println("lll<<0 = " + (lll >> 10));
    }


}
