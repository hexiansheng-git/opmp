package com.hhwy.pm.qqch.preparation.contractPlan.subcontractPlan.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author han
 * @date 2023-08-03 13:35:47
 * @remark 分包清单汇总Vo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubpackageInventoryCollectVo {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：主键
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long id;
    /**
     * 字段描述：父id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long pid;
    /**
     * 字段描述：分包清单编码
     */
    @JsonProperty
    private String subpackageInventoryCode;
    /**
     * 字段描述：分包清单名称
     */
    @JsonProperty
    private String subpackageInventoryName;
    /**
     * 字段描述：班组名称（取分包招标策划名称）
     */
    @JsonProperty
    private String className;
    /**
     * 字段描述：单位
     */
    @JsonProperty
    private String units;
    /**
     * 字段描述：标后预算分包单价
     */
    @JsonProperty
    private BigDecimal SubpackageUnivalence;
    /**
     * 字段描述：成本数据库清单组价
     */
    @JsonProperty
    private BigDecimal InventoryGroupPrice;
    /**
     * 字段描述：分包指导价
     */
    @JsonProperty
    private BigDecimal subpackageGuidePrice;

    private List<SubpackageInventoryCollectVo> children;
}
