package com.hhwy.pm.qqch.preparation.contractPlan.masterContract.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author han
 * @date 2023-08-03 13:34:18
 * @remark qqch_key_inventory_content
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KeyInventoryContentItemClassifyVo {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：总价差值合计
     */
    private BigDecimal totalPriceDifferenceTotal;

    private List<KeyInventoryContentItemClassify> list = new ArrayList<>();
}
