package com.hhwy.pm.qqch.preparation.contractPlan.masterContract.domain.vo;

import com.hhwy.pm.qqch.preparation.contractPlan.masterContract.domain.QqchKeyInventoryContent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author han
 * @date 2023-08-03 13:34:18
 * @remark qqch_key_inventory_content
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KeyInventoryContentItemClassifyQueryVo {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：事项分类（字典项：item_classify）
     */
    private String itemClassify;
    /**
     * 字段描述：版本
     */
    private BigDecimal version;
    /**
     * 页面数据集合
     */
    List<QqchKeyInventoryContent> list;
}
