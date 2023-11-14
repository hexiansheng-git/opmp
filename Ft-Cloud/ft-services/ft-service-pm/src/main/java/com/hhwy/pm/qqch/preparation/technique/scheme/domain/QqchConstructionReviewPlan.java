package com.hhwy.pm.qqch.preparation.technique.scheme.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author zhenglili
 * @date 2023-07-17 15:32:17
 * @remark qqch_construction_review_plan
 */
@Data
public class QqchConstructionReviewPlan extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：主键id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "主键id")
    private Long id;
    /**
     * 字段描述：方案等级
     */
    @JsonProperty
    @Excel(name = "方案等级")
    private String schemeLevel;
    /**
     * 字段描述：方案编号
     */
    @JsonProperty
    @Excel(name = "方案编号")
    private String schemeCode;
    /**
     * 字段描述：方案名称
     */
    @JsonProperty
    @Excel(name = "方案名称")
    private String schemeName;
    /**
     * 字段描述：关联WBS编码
     */
    @JsonProperty
    @Excel(name = "关联WBS编码")
    private String wbsCode;
    /**
     * 字段描述：关联WBS
     */
    @JsonProperty
    @Excel(name = "关联WBS")
    private String wbsName;
    /**
     * 字段描述：方案等级说明
     */
    @JsonProperty
    @Excel(name = "方案等级说明")
    private String schemeLevelDescription;
    /**
     * 字段描述：编制主体
     */
    @JsonProperty
    @Excel(name = "编制主体")
    private String preparationMainBody;
    /**
     * 字段描述：项目总工及联系方式
     */
    @JsonProperty
    @Excel(name = "项目总工及联系方式")
    private String contactInfo;
    /**
     * 字段描述：计划编制时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "计划编制时间", dateFormat = "yyyy-MM-dd")
    private Date planPreparationTime;
    /**
     * 字段描述：计划实施时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "计划实施时间", dateFormat = "yyyy-MM-dd")
    private Date planImplementTime;
    /**
     * 字段描述：清单通过时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "清单通过时间", dateFormat = "yyyy-MM-dd")
    private Date listPassTime;
    /**
     * 字段描述：评审主体
     */
    @JsonProperty
    @Excel(name = "评审主体")
    private String reviewMainBody;
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
     * 字段描述：备注
     */
    @JsonProperty
    @Excel(name = "备注")
    private String remark;
    /**
     * 字段描述：排序
     */
    @JsonProperty
    private Integer sort;
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

    private List<QqchConstructionReviewPlan> children;
}
