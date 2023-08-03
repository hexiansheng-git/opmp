package com.hhwy.pm.qqch.preparation.finance.policy.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.common.core.web.domain.BaseEntity;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * @author zhenglili
 * @date 2023-08-03 13:45:08
 * @remark qqch_local_bank_situation
 */
@Data
public class QqchLocalBankSituation extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：主键
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "主键")
    private Long id;
    /**
     * 字段描述：银行编码
     */
    @JsonProperty
    @Excel(name = "银行编码")
    private String bankCode;
    /**
     * 字段描述：银行名称
     */
    @JsonProperty
    @Excel(name = "银行名称")
    private String bankName;
    /**
     * 字段描述：性质（字典类型bank_nature）
     */
    @JsonProperty
    @Excel(name = "性质（字典类型bank_nature）")
    private String bankNature;
    /**
     * 字段描述：授信额度
     */
    @JsonProperty
    @Excel(name = "授信额度")
    private BigDecimal creditLimit;
    /**
     * 字段描述：保函费率%
     */
    @JsonProperty
    @Excel(name = "保函费率%")
    private BigDecimal guaranteeRate;
    /**
     * 字段描述：信誉及服务（字典类型reputation_service）
     */
    @JsonProperty
    @Excel(name = "信誉及服务（字典类型reputation_service）")
    private String reputationService;
    /**
     * 字段描述：服务效率（字典类型service_efficiency）
     */
    @JsonProperty
    @Excel(name = "服务效率（字典类型service_efficiency）")
    private String serviceEfficiency;
    /**
     * 字段描述：存取现金
     */
    @JsonProperty
    @Excel(name = "存取现金")
    private String accessCash;
    /**
     * 字段描述：业务往来
     */
    @JsonProperty
    @Excel(name = "业务往来")
    private String businessDealing;
    /**
     * 字段描述：存款利率%
     */
    @JsonProperty
    @Excel(name = "存款利率%")
    private BigDecimal depositRate;
    /**
     * 字段描述：贷款利率%
     */
    @JsonProperty
    @Excel(name = "贷款利率%")
    private BigDecimal lendRate;
    /**
     * 字段描述：提供的服务
     */
    @JsonProperty
    @Excel(name = "提供的服务")
    private String provideService;
    /**
     * 字段描述：是否选择来的数据 1-是 0-否
     */
    @JsonProperty
    @Excel(name = "是否选择来的数据 1-是 0-否")
    private String isSelect;
    /**
     * 字段描述：版本
     */
    @JsonProperty
    @Excel(name = "版本")
    private BigDecimal version;
    /**
     * 字段描述：是否有效 1-有效 0-失效
     */
    @JsonProperty
    @Excel(name = "是否有效 1-有效 0-失效")
    private String valid;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    @Excel(name = "数据创建系统时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    @Excel(name = "数据修改系统时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    @Excel(name = "数据删除系统时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date delTime;
    /**
     * 字段描述：删除标识：0未删除；1已删除
     */
    @JsonProperty
    @Excel(name = "删除标识：0未删除；1已删除")
    private String delFlag;
    /**
     * 字段描述：预留字段1
     */
    @JsonProperty
    @Excel(name = "预留字段1")
    private String ptVar1;
    /**
     * 字段描述：预留字段2
     */
    @JsonProperty
    @Excel(name = "预留字段2")
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
}
