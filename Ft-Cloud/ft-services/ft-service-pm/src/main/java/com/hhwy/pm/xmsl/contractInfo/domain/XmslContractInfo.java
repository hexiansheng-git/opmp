package com.hhwy.pm.xmsl.contractInfo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.utils.common.CommonBaseEntity;
import com.hhwy.utils.validation.ValidationGroups;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author ldd
 * @date 2023-07-10 13:57:39
 * @remark  合同信息--主合同信息实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class XmslContractInfo extends CommonBaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：主键
     */
    @NotNull(message = "主键不能为空",groups = {ValidationGroups.Update.class})
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long id;
    /**
     * 字段描述：合同编号
     */
    @NotBlank(message = "合同编号不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    @JsonProperty
    @Excel(name = "合同编号")
    private String code;
    /**
     * 字段描述：合同名称
     */
    @JsonProperty
    @Excel(name = "合同名称")
    private String name;
    /**
     * 字段描述：项目编号
     */
    @JsonProperty
    @Excel(name = "项目编号")
    private String projectCode;
    /**
     * 字段描述：项目名称(外文)
     */
    @JsonProperty
    @Excel(name = "项目名称(外文)")
    private String projectNameYw;
    /**
     * 字段描述：中标日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "中标日期", dateFormat = "yyyy-MM-dd")
    private Date winDate;
    /**
     * 字段描述：合同签订日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "合同签订日期", dateFormat = "yyyy-MM-dd")
    private Date signDate;
    /**
     * 字段描述：品牌名称
     */
    @NotBlank(message = "品牌名称不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    @JsonProperty
    @Excel(name = "品牌名称")
    private String brandName;
    /**
     * 字段描述：项目分类（字典项 project_category）
     */
    @NotBlank(message = "项目分类不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    @JsonProperty
    @Excel(name = "项目分类（字典项 project_category）")
    private String projectCategory;

    /**
     * 字段描述：项目类型 （字典项 project_type）
     */
    @JsonProperty
    private String projectType;

    /**
     * 字段描述：业务领域及产品
     */
    @JsonProperty
    @Excel(name = "业务领域及产品")
    private String businessAreasAndProducts;
    /**
     * 字段描述：资金来源
     */
    @JsonProperty
    @Excel(name = "资金来源")
    private String capitalSource;
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
     * 字段描述：设计单位
     */
    @JsonProperty
    @Excel(name = "设计单位")
    private String designUnit;
    /**
     * 字段描述：详细地址
     */
    @JsonProperty
    @Excel(name = "详细地址")
    private String detailedAddress;
    /**
     * 字段描述：合同价格
     */
    @JsonProperty
    @Excel(name = "合同价格")
    private String contractPrice;
    /**
     * 字段描述：承包方式
     */
    @JsonProperty
    @Excel(name = "承包方式")
    private String contractingMethod;

    /**
     * 字段描述：合同类型(字典项 contract_type)
     */
    @NotBlank(message = "合同类型不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    @JsonProperty
    @Excel(name = "合同类型(字典项 contract_type)")
    private String contractType;
    /**
     * 字段描述：合同属性(字典项 contract_attribute)
     */
    @NotBlank(message = "合同属性不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    @JsonProperty
    @Excel(name = "合同属性(字典项 contract_attribute)")
    private String contractAttribute;
    /**
     * 字段描述：所属时区（字典项 time_zone ）
     */
    @NotBlank(message = "所属时区不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    @JsonProperty
    @Excel(name = "所属时区（字典项 time_zone ）")
    private String timeZone;
    /**
     * 字段描述：编制人id
     */
    @JsonProperty
    @Excel(name = "编制人id")
    private String operateUserId;
    /**
     * 字段描述：编制人
     */
    @JsonProperty
    @Excel(name = "编制人")
    private String operateUserName;
    /**
     * 字段描述：编制日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "编制日期", dateFormat = "yyyy-MM-dd")
    private Date operateTime;
    /**
     * 字段描述：计量批复时限（天）
     */
    @JsonProperty
    @Excel(name = "计量批复时限（天）")
    private String meteringTime;
    /**
     * 字段描述：项目规模
     */
    @JsonProperty
    @Excel(name = "项目规模")
    private String projectScale;
    /**
     * 字段描述：合同工期（月)
     */
    @NotBlank(message = "合同工期（月)不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    @JsonProperty
    @Excel(name = "合同工期（月)")
    private String duration;
    /**
     * 字段描述：合同开工日期
     */
    @NotNull(message = "合同开工日期不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "合同开工日期", dateFormat = "yyyy-MM-dd")
    private Date startTime;
    /**
     * 字段描述：合同交工日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "合同交工日期", dateFormat = "yyyy-MM-dd")
    private Date handoverTime;
    /**
     * 字段描述：合同竣工日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "合同竣工日期", dateFormat = "yyyy-MM-dd")
    private Date completedTime;
    /**
     * 字段描述：缺陷责任（月）
     */
    @JsonProperty
    @Excel(name = "缺陷责任（月）")
    private String defectLiability;
    /**
     * 字段描述：合同不含税金额
     */
    @JsonProperty
    @Excel(name = "合同不含税金额")
    private BigDecimal excludingAmout;
    /**
     * 字段描述：税率
     */
    @NotNull(message = "税率不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    @JsonProperty
    @Excel(name = "税率")
    private BigDecimal taxRate;
    /**
     * 字段描述：税金
     */
    @JsonProperty
    @Excel(name = "税金")
    private BigDecimal taxes;
    /**
     * 字段描述：合同含税金额
     */
    @JsonProperty
    @Excel(name = "合同含税金额")
    private BigDecimal includingAmout;
    /**
     * 字段描述：有效合同金额
     */
    @JsonProperty
    @Excel(name = "有效合同金额")
    private BigDecimal effectiveAmout;
    /**
     * 字段描述：有效合同金额-美元
     */
    @JsonProperty
    @Excel(name = "有效合同金额-美元")
    private BigDecimal effectiveAmoutDollar;
    /**
     * 字段描述：清单标价货币(编码)
     */
    @JsonProperty
    @Excel(name = "清单标价货币(编码)")
    private String listCurrencyCode;
    /**
     * 字段描述：清单标价货币(名称)
     */
    @JsonProperty
    @Excel(name = "清单标价货币(名称)")
    private String listCurrencyName;
    /**
     * 字段描述：最低计量支付金额
     */
    @JsonProperty
    @Excel(name = "最低计量支付金额")
    private BigDecimal minPayAmount;
    /**
     * 字段描述：计量账单审核时长（天)
     */
    @JsonProperty
    @Excel(name = "计量账单审核时长（天)")
    private String billProcessDuration;
    /**
     * 字段描述：附件组id
     */
    @JsonProperty
    @Excel(name = "附件组id")
    private String fileGroupId;
    /**
     * 字段描述：备注/描述
     */
    @JsonProperty
    @Excel(name = "备注/描述")
    private String remark;
    /**
     * 字段描述：所属区域id
     */
    @JsonSerialize(using = ToStringSerializer.class)
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
     * 字段描述：项目id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "项目id")
    private Long projectId;
    /**
     * 字段描述：项目名称
     */
    @JsonProperty
    @Excel(name = "项目名称")
    private String projectName;
    /**
     * 字段描述：部门id
     */
    @JsonSerialize(using = ToStringSerializer.class)
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
    /**
     * 字段描述：数据创建系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "数据创建系统时间", dateFormat = "yyyy-MM-dd")
    private Date createTime;
    /**
     * 字段描述：数据修改者id
     */
    @JsonProperty
    @Excel(name = "数据修改者id")
    private String updateUser;
    /**
     * 字段描述：数据修改系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "数据修改系统时间", dateFormat = "yyyy-MM-dd")
    private Date updateTime;
    /**
     * 字段描述：数据删除者
     */
    @JsonProperty
    @Excel(name = "数据删除者")
    private String delUser;
    /**
     * 字段描述：数据删除系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "数据删除系统时间", dateFormat = "yyyy-MM-dd")
    private Date delTime;
    /**
     * 字段描述：删除标识：0未删除；1已删除
     */
    @JsonProperty
    @Excel(name = "删除标识：0未删除；1已删除")
    private String delFlag;
    /**
     * 字段描述：预留字段1
     *
     * 调整时返回生效版本id
     */
    @JsonProperty
    @Excel(name = "调整时返回生效版本id")
    private String ptVar1;
    /**
     * 字段描述：预留字段2
     *
     * 业务领域及产品 编号
     */
    @JsonProperty
    @Excel(name = "业务领域及产品 编号")
    private String ptVar2;
    /**
     * 字段描述：预留字段3
     */
    @JsonProperty
    @Excel(name = "预留字段3")
    private String ptVar3;
    /**
     * 字段描述：预留字段4
     */
    @JsonProperty
    @Excel(name = "预留字段4")
    private String ptVar4;
    /**
     * 字段描述：预留字段5
     */
    @JsonProperty
    @Excel(name = "预留字段5")
    private String ptVar5;
    /**
     * 字段描述：流程状态（5已完成）
     */
    @JsonProperty
    @Excel(name = "流程状态（5已完成）")
    private String taskStatus;
    /**
     * 字段描述：是否有效（0无效 1有效）
     */
    @JsonProperty
    @Excel(name = "是否有效（0无效 1有效）")
    private String valid;

    /**
     * 字段描述：发布人id
     */
    @JsonProperty
    private String issuePersonId;

    /**
     * 字段描述：发布人
     */
    @JsonProperty
    private String issuePersonName;

    /**
     * 字段描述：发布日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    private Date issueDate;


    private  BigDecimal version;

    //投保险种
    private List<XmslContractInsure> xmslContractInsureList;
    //签订信息
    private List<XmslContractSign> xmslContractSignList;
    //项目支付信息
    private List<XmslContractPayinfo> xmslContractPayinfoList;
    }
