package com.hhwy.pm.xmsl.project.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.utils.validation.ValidationGroups;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
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
     * 字段描述：项目id
     */
    @JsonProperty
    @Excel(name = "项目id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long projectId;
    /**
     * 字段描述：项目编码
     */
    @JsonProperty
    @Excel(name = "项目id")
    private String projectCode;
    /**
     * 字段描述：项目名称（中文）
     */
    @JsonProperty
    @Excel(name = "项目名称")
    @NotBlank(message = "请选择项目！",groups = {ValidationGroups.Save.class,ValidationGroups.Update.class})
    private String projectName;
    /**
     * 字段描述：项目简称（中文）
     */
    @JsonProperty
    @Excel(name = "项目简称（中文）")
    private String projectShortName;
    /**
     * 字段描述：项目名称（外文）
     */
    @JsonProperty
    @Excel(name = "项目名称（外文）")
    private String projectNameForeignLang;
    /**
     * 字段描述：里程
     */
    @JsonProperty
    @Excel(name = "里程")
    private String mileage;
    /**
     * 字段描述：合同价格
     */
    @JsonProperty
    @Excel(name = "合同价格")
    private String contractPrice;
    /**
     * 字段描述：合同汇率
     */
    @JsonProperty
    @Excel(name = "合同汇率")
    private String contractExchangeRate;
    /**
     * 字段描述：业主单位
     */
    @JsonProperty
    @Excel(name = "业主单位")
    private String proprietorUnit;
    /**
     * 字段描述：监理单位
     */
    @JsonProperty
    @Excel(name = "监理单位")
    private String supervisorUnit;
    /**
     * 字段描述：管理公司
     */
    @JsonProperty
    @Excel(name = "管理公司")
    private String managementCompany;
    /**
     * 字段描述：资金来源
     */
    @JsonProperty
    @Excel(name = "资金来源")
    private String capitalSource;
    /**
     * 字段描述：合同类型
     */
    @JsonProperty
    @Excel(name = "合同类型")
    private String contractType;
    /**
     * 字段描述：合同工期
     */
    @JsonProperty
    @Excel(name = "合同工期")
    private String contractSchedule;
    /**
     * 字段描述：开工日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "开工日期")
    private Date startDate;
    /**
     * 字段描述：竣工日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "竣工日期")
    private Date completionDate;
    /**
     * 字段描述：缺陷责任期
     */
    @JsonProperty
    @Excel(name = "缺陷责任期")
    private String defectsLiabilityPeriod;
    /**
     * 字段描述：执行规范标准
     */
    @JsonProperty
    @Excel(name = "执行规范标准")
    private String executiveSpecificationStandard;
    /**
     * 字段描述：合同语言
     */
    @JsonProperty
    @Excel(name = "合同语言")
    private String contractLang;
    /**
     * 字段描述：项目重要性等级
     */
    @JsonProperty
    @Excel(name = "项目重要性等级")
    private String weightedGrade;
    /**
     * 字段描述：支付货币
     */
    @JsonProperty
    @Excel(name = "支付货币")
    private String paymentCurrency;
    /**
     * 字段描述：调价方式
     */
    @JsonProperty
    @Excel(name = "调价方式")
    private String priceAdjustmentMode;
    /**
     * 字段描述：预付款比例
     */
    @JsonProperty
    @Excel(name = "预付款比例")
    private BigDecimal prepaymentRatio;
    /**
     * 字段描述：履约保函比例
     */
    @JsonProperty
    @Excel(name = "履约保函比例")
    private BigDecimal performanceSecurityRatio;
    /**
     * 字段描述：保留金比例
     */
    @JsonProperty
    @Excel(name = "保留金比例")
    private BigDecimal retentionMoneyRatio;
    /**
     * 字段描述：质量保证金比例
     */
    @JsonProperty
    @Excel(name = "质量保证金比例")
    private BigDecimal qualityGuaranteeDepositRatio;
    /**
     * 字段描述：中标日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "中标日期")
    private Date winTheBiddingDate;
    /**
     * 字段描述：业务领域及产品
     */
    @JsonProperty
    @Excel(name = "业务领域及产品")
    private String businessAreasAndProducts;
    /**
     * 字段描述：承包方式
     */
    @JsonProperty
    @Excel(name = "承包方式")
    private String contractingMethod;
    /**
     * 字段描述：项目类型
     */
    @JsonProperty
    @Excel(name = "项目类型")
    private String projectType;
    /**
     * 字段描述：项目所在地（国）
     */
    @JsonProperty
    @Excel(name = "项目所在地（国）")
    private String projectLocation;
    /**
     * 字段描述：所属机构
     */
    @JsonProperty
    @Excel(name = "所属机构")
    private String subsidiaryOrgan;
    /**
     * 字段描述：项目经理
     */
    @JsonProperty
    @Excel(name = "项目经理")
    private String projectManager;
    /**
     * 字段描述：联系方式（国内）
     */
    @JsonProperty
    @Excel(name = "联系方式（国内）")
    private String internalContactWay;
    /**
     * 字段描述：联系方式（国外）
     */
    @JsonProperty
    @Excel(name = "联系方式（国外）")
    private String foreignContactWay;
    /**
     * 字段描述：中标单位
     */
    @JsonProperty
    @Excel(name = "中标单位")
    private String winTheBiddingUnit;
    /**
     * 字段描述：设计单位
     */
    @JsonProperty
    @Excel(name = "设计单位")
    private String designUnit;
    /**
     * 字段描述：编制人
     */
    @JsonProperty
    @Excel(name = "编制人")
    private String establishPersonnel;
    /**
     * 字段描述：编制日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "编制日期")
    private Date establishDate;
    /**
     * 字段描述：详细地址
     */
    @JsonProperty
    @Excel(name = "详细地址")
    private String detailedAddress;
    /**
     * 字段描述：项目规模
     */
    @JsonProperty
    @Excel(name = "项目规模")
    private String projectScale;
    /**
     * 字段描述：项目背景
     */
    @JsonProperty
    @Excel(name = "项目背景")
    private String projectContext;
    /**
     * 字段描述：备注/描述
     */
    @JsonProperty
    @Excel(name = "备注/描述")
    private String remark;
    /**
     * 字段描述：所属区域id
     */
    @JsonProperty
    @Excel(name = "所属区域id")
    private Long regionId;
    /**
     * 字段描述：所属区域名称
     */
    @JsonProperty
    @Excel(name = "所属区域名称")
    private String regionName;
    /**
     * 字段描述：部门id
     */
    @JsonProperty
    @Excel(name = "部门id")
    private Long deptId;
    /**
     * 字段描述：数据创建者id
     */
    @JsonProperty
    @Excel(name = "数据创建者id")
    private String createUser;
    /**
     * 字段描述：数据创建者名称
     */
    @JsonProperty
    @Excel(name = "数据创建者名称")
    private String createUserName;
}
