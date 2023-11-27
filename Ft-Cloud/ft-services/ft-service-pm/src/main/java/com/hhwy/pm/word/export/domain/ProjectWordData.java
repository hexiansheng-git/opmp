package com.hhwy.pm.word.export.domain;

import com.deepoove.poi.data.PictureRenderData;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hhwy.pm.xmsl.project.domain.XmslProjectEngineeringAmount;
import com.hhwy.pm.xmsl.project.domain.XmslProjectMaterialsAmount;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
public class ProjectWordData {
    /**
     * 字段描述：项目名称（中文）
     */
    @JsonProperty
    private String projectName;
    /**
     * 字段描述：里程
     */
    @JsonProperty
    private String mileage;
    /**
     * 字段描述：合同价格
     */
    @JsonProperty
    private String contractPrice;
    /**
     * 字段描述：合同汇率
     */
    @JsonProperty
    private String contractExchangeRate;
    /**
     * 字段描述：业主单位
     */
    @JsonProperty
    private String proprietorUnit;
    /**
     * 字段描述：监理单位
     */
    @JsonProperty
    private String supervisorUnit;
    /**
     * 字段描述：管理公司
     */
    @JsonProperty
    private String managementCompany;
    /**
     * 字段描述：资金来源
     */
    @JsonProperty
    private String capitalSource;
    /**
     * 字段描述：合同类型
     */
    @JsonProperty
    private String contractType;
    /**
     * 字段描述：合同工期
     */
    @JsonProperty
    private String contractSchedule;
    /**
     * 字段描述：项目开竣工日期
     */
    private String startCompletionDate;
    /**
     * 字段描述：缺陷责任期
     */
    @JsonProperty
    private String defectsLiabilityPeriod;
    /**
     * 字段描述：执行规范标准
     */
    @JsonProperty
    private String executiveSpecificationStandard;
    /**
     * 字段描述：合同语言
     */
    @JsonProperty
    private String contractLang;
    /**
     * 字段描述：支付货币
     */
    @JsonProperty
    private String paymentCurrency;
    /**
     * 字段描述：调价方式
     */
    @JsonProperty
    private String priceAdjustmentMode;
    /**
     * 字段描述：预付款比例
     */
    @JsonProperty
    private BigDecimal prepaymentRatio;
    /**
     * 字段描述：履约保函比例
     */
    @JsonProperty
    private BigDecimal performanceSecurityRatio;
    /**
     * 字段描述：保留金比例
     */
    @JsonProperty
    private BigDecimal retentionMoneyRatio;
    /**
     * 字段描述：质量保证金比例
     */
    @JsonProperty
    private BigDecimal qualityGuaranteeDepositRatio;
    /**
     * 字段描述：主要材料
     */
    private List<XmslProjectMaterialsAmount> materialsAmountList;
    /**
     * 字段描述：主要工程
     */
    private List<XmslProjectEngineeringAmount> engineeringAmountList;
    /**
     * 字段描述：工程地理位置附件集合
     */
    private List<Map<String,PictureRenderData>> locationPictureList;
    /**
     * 字段描述：工程结构形式附件集合
     */
    private List<Map<String,PictureRenderData>> structurePictureList;
}
