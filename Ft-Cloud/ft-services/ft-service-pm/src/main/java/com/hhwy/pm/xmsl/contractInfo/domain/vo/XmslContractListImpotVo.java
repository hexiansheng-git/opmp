package com.hhwy.pm.xmsl.contractInfo.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hhwy.common.core.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class XmslContractListImpotVo {

    @Excel(name = "层级码")
    private String innerCode;
    @Excel(name = "父层级码")
    private String parentInnerCode;

    /**
     * 字段描述：树id
     */
    @JsonProperty
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long treeId;
    /**
     * 字段描述：父id
     */
    @JsonProperty
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long parentTreeId;

    /**
     * 字段描述：清单编号
     */
    @JsonProperty
    @Excel(name = "清单编号")
    private String code;
    /**
     * 字段描述：清单中文名称
     */
    @JsonProperty
    @Excel(name = "清单中文名称")
    private String chineseName;
    /**
     * 字段描述：清单外文名称
     */
    @JsonProperty
    @Excel(name = "清单外文名称")
    private String foreignName;
    /**
     * 字段描述：清单类型(字典项（list_type）)
     */
    @JsonProperty
    @Excel(name = "清单类型")
    private String listType;
    /**
     * 字段描述：单位编码
     */
    @JsonProperty
    private String unitCode;
    /**
     * 字段描述：单位
     */
    @JsonProperty
    @Excel(name = "单位")
    private String unit;
    /**
     * 字段描述：中标数量
     */
    @JsonProperty
    @Excel(name = "中标数量")
    private BigDecimal winNum;
    /**
     * 字段描述：中标单价（不含税）
     */
    @JsonProperty
    @Excel(name = "中标单价（不含税）")
    private BigDecimal winUnitPrice;
    /**
     * 字段描述：中标金额（不含税）
     */
    @JsonProperty
    @Excel(name = "中标金额（不含税）")
    private BigDecimal winAmount;
    /**
     * 字段描述：变更数量
     */
    @JsonProperty
    @Excel(name = "变更数量")
    private BigDecimal changeNum;
    /**
     * 字段描述：变更单价（不含税）
     */
    @JsonProperty
    @Excel(name = "变更单价（不含税）")
    private BigDecimal changeUnitPrice;
    /**
     * 字段描述：变更金额（不含税）
     */
    @JsonProperty
    @Excel(name = "变更金额（不含税）")
    private BigDecimal changeAmount;
    /**
     * 字段描述：变更后数量
     */
    @JsonProperty
    @Excel(name = "变更后数量")
    private BigDecimal afterNum;
    /**
     * 字段描述：变更后单价（不含税）
     */
    @JsonProperty
    @Excel(name = "变更后单价（不含税）")
    private BigDecimal afterUnitPrice;
    /**
     * 字段描述：变更后金额（不含税）
     */
    @JsonProperty
    @Excel(name = "变更后金额（不含税）")
    private BigDecimal afterAmount;
}
