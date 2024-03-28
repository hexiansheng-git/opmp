package com.hhwy.sp.buildSchemeManage.sgjsBuildScheme.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.sp.buildSchemeManage.sgjsBuildScheme.sgjsBuildSchemeExpertSuggest.domain.SgjsBuildSchemeExpertSuggest;
import com.hhwy.sp.buildSchemeManage.sgjsBuildSchemeList.domain.SgjsBuildSchemeList;
import com.hhwy.utils.common.CommonBaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author fsd
 * @date 2024-03-20 09:54:05
 * @remark sgjs_build_scheme
 */
@Data
public class SgjsBuildScheme extends CommonBaseEntity {
    private static final long serialVersionUID = 1L;

    //方案清单
    private List<SgjsBuildSchemeList> children;
    //专家意见
    private List<SgjsBuildSchemeExpertSuggest> expertSuggest;

    /**
     * 字段描述：
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long id;
    /**
     * 字段描述：当前状态
     */
    @JsonProperty
    @Excel(name = "当前状态")
    private String taskStatus;
    /**
     * 字段描述：国别
     */
    @JsonProperty
    @Excel(name = "国别")
    private String countryCode;
    /**
     * 字段描述：国家名称
     */
    @JsonProperty
    @Excel(name = "国家名称")
    private String countryName;
    /**
     * 字段描述：中标资质
     */
    @JsonProperty
    @Excel(name = "中标资质")
    private String winCertificate;
    /**
     * 字段描述：业务领域及产品
     */
    @JsonProperty
    @Excel(name = "业务领域及产品")
    private String businessAreasAndProducts;
    /**
     * 字段描述：0无效 1有效
     */
    @JsonProperty
    @Excel(name = "0无效 1有效")
    private String valid;
    /**
     * 字段描述：版本
     */
    @JsonProperty
    @Excel(name = "版本")
    private BigDecimal version;
    /**
     * 字段描述：版本字符
     */
    @JsonProperty
    @Excel(name = "版本字符")
    private String versionStr;
    /**
     * 字段描述：项目总工id，多个逗号分割
     */
    @JsonProperty
    @Excel(name = "项目总工id，多个逗号分割")
    private String leadEngineer;
    /**
     * 字段描述：项目总工姓名，多个逗号分割
     */
    @JsonProperty
    @Excel(name = "项目总工姓名，多个逗号分割")
    private String leadEngineerName;
    /**
     * 字段描述：项目总工联系方式，多个逗号分割
     */
    @JsonProperty
    @Excel(name = "项目总工联系方式，多个逗号分割")
    private String leadEngineerPhoneNum;
    /**
     * 字段描述：清单序列号
     */
    @JsonProperty
    @Excel(name = "施工方案清单编码")
    private String listSerialNum;
    /**
     * 字段描述：清单提交日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @JsonProperty
    @Excel(name = "清单提交日期", dateFormat = "yyyy-MM-dd")
    private Date submisionDate;
    /**
     * 字段描述：发起人id
     */
    @JsonProperty
    @Excel(name = "发起人id")
    private String submisionPerson;
    /**
     * 字段描述：发起人姓名
     */
    @JsonProperty
    @Excel(name = "发起人姓名")
    private String submisionPersonName;
    /**
     * 字段描述：方案清单项目内部审核记录表(附件)
     */
    @JsonProperty
    @Excel(name = "方案清单项目内部审核记录表(附件)")
    private String auditRecordFile;
    /**
     * 字段描述：项目简介(附件)
     */
    @JsonProperty
    @Excel(name = "项目简介(附件)")
    private String projectSummaryFile;
    /**
     * 字段描述：变更发起日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @JsonProperty
    @Excel(name = "变更发起日期", dateFormat = "yyyy-MM-dd")
    private Date changeDate;
    /**
     * 字段描述：变更发起人
     */
    @JsonProperty
    @Excel(name = "变更发起人")
    private String changePerson;
    /**
     * 字段描述：变更发起人姓名
     */
    @JsonProperty
    @Excel(name = "变更发起人姓名")
    private String changePersonName;
    /**
     * 字段描述：更新说明
     */
    @JsonProperty
    @Excel(name = "更新说明")
    private String changeSummary;
    /**
     * 字段描述：备注
     */
    @JsonProperty
    @Excel(name = "备注")
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
     * 字段描述：PJ码
     */
    @JsonProperty
    @Excel(name = "PJ码")
    private String projectCode;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
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
     * 字段描述：专业领域名称
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
     * 字段描述：//0不通过 1通过
     */
    @JsonProperty
    @Excel(name = "预留字段3")
    private String ptVar3;
    /**
     * 字段描述：项目领域类型标识 1房建 2非房建
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
