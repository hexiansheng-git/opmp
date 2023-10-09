package com.hhwy.pm.xmsl.project.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author han
 * @date 2023-07-03 09:48:24
 * @remark 项目基本信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectBasicInfo {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：主键id
     */
    @JsonProperty
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * 字段描述：项目id
     */
    @JsonProperty
    @JsonSerialize(using = ToStringSerializer.class)
    private Long projectId;
    /**
     * 字段描述：项目编码
     */
    @JsonProperty
    private String projectCode;
    /**
     * 字段描述：项目名称（中文）
     */
    @JsonProperty
    private String projectName;
    /**
     * 字段描述：项目简称（中文）
     */
    @JsonProperty
    private String projectShortName;
    /**
     * 字段描述：项目名称（外文）
     */
    @JsonProperty
    private String projectNameForeignLang;
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
     * 字段描述：开工日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    private Date startDate;
    /**
     * 字段描述：竣工日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    private Date completionDate;
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
     * 字段描述：项目重要性等级
     */
    @JsonProperty
    private String weightedGrade;
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
     * 字段描述：中标日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    private Date winTheBiddingDate;
    /**
     * 字段描述：业务领域及产品
     */
    @JsonProperty
    private String businessAreasAndProducts;
    /**
     * 字段描述：业务领域及产品描述
     */
    @JsonProperty
    @Excel(name = "业务领域及产品")
    private String businessAreasAndProductsLabel;
    /**
     * 字段描述：承包方式
     */
    @JsonProperty
    private String contractingMethod;
    /**
     * 字段描述：项目类型
     */
    @JsonProperty
    private String projectType;
    /**
     * 字段描述：项目分类
     */
    @JsonProperty
    @Excel(name = "项目分类")
    private String projectCategory;
    /**
     * 字段描述：项目所在地（国）
     */
    @JsonProperty
    private String projectLocation;
    /**
     * 字段描述：所属机构
     */
    @JsonProperty
    private String subsidiaryOrgan;
    /**
     * 字段描述：项目经理
     */
    @JsonProperty
    private String projectManager;
    /**
     * 字段描述：联系方式（国内）
     */
    @JsonProperty
    private String internalContactWay;
    /**
     * 字段描述：联系方式（国外）
     */
    @JsonProperty
    private String foreignContactWay;
    /**
     * 字段描述：中标单位
     */
    @JsonProperty
    private String winTheBiddingUnit;
    /**
     * 字段描述：设计单位
     */
    @JsonProperty
    private String designUnit;
    /**
     * 字段描述：编制人
     */
    @JsonProperty
    private String establishPersonnel;
    /**
     * 字段描述：编制日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    private Date establishDate;
    /**
     * 字段描述：详细地址
     */
    @JsonProperty
    private String detailedAddress;
    /**
     * 字段描述：项目规模
     */
    @JsonProperty
    private String projectScale;
    /**
     * 字段描述：项目背景
     */
    @JsonProperty
    private String projectContext;
    /**
     * 字段描述：当地币种
     */
    @JsonProperty
    @Excel(name = "当地币种")
    private String localCurrency;
    /**
     * 字段描述：币种编码
     */
    @JsonProperty
    @Excel(name = "当地币种编码")
    private String localCurrencyCode;
    /**
     * 字段描述：当地币种
     */
    @JsonProperty
    @Excel(name = "合同币种")
    private String contractCurrency;
    /**
     * 字段描述：币种编码
     */
    @JsonProperty
    @Excel(name = "合同币种编码")
    private String contractCurrencyCode;
    /**
     * 字段描述：备注/描述
     */
    @JsonProperty
    private String remark;
    /**
     * 字段描述：项目状态
     */
    @JsonProperty
    private String projectStatus;
    /**
     * 字段描述：项目完工时间（完工，初验，终验都算完工）
     */
    @JsonProperty
    private Date projectCompleteDate;
    /**
     * 字段描述：项目初验时间
     */
    @JsonProperty
    private Date projectInitialInspectionDate;
    /**
     * 字段描述：所属区域id
     */
    @JsonProperty
    private Long regionId;
    /**
     * 字段描述：所属区域名称
     */
    @JsonProperty
    private String regionName;
    /**
     * 字段描述：部门id
     */
    @JsonProperty
    private Long deptId;
    /**
     * 字段描述：数据创建者id
     */
    @JsonProperty
    private String createUser;
    /**
     * 字段描述：数据创建者名称
     */
    @JsonProperty
    private String createUserName;
}
