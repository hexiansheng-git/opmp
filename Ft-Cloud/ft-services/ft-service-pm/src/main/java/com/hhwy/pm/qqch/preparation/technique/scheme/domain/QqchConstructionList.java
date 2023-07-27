package com.hhwy.pm.qqch.preparation.technique.scheme.domain;

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
 * @date 2023-07-13 14:40:32
 * @remark 3.4.2施工方案清单
 */
@Data
public class QqchConstructionList extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：主键id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long id;
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
    @Excel(name = "关联WBS编号")
    private String wbsCode;
    /**
     * 字段描述：关联WBS
     */
    @JsonProperty
    @Excel(name = "关联WBS")
    private String wbsName;
    /**
     * 字段描述：方案类型（字典类型scheme_type）
     */
    @JsonProperty
    @Excel(name = "方案类型", dictType = "scheme_type")
    private String schemeType;
    /**
     * 字段描述：方案分级（字典类型scheme_level）
     */
    @JsonProperty
    @Excel(name = "方案分级", dictType = "scheme_level")
    private String schemeLevel;
    /**
     * 字段描述：危大等级（字典类型danger_level）
     */
    @JsonProperty
    @Excel(name = "危大等级", dictType = "danger_level")
    private String dangerLevel;
    /**
     * 字段描述：设计变更是否引起施工方案变更 0:否；1:是
     */
    @JsonProperty
    @Excel(name = "设计变更是否引起施工方案变更", dictType = "common_yes")
    private String isChange;
    /**
     * 字段描述：计划编制时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    @Excel(name = "计划编制时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date planPreparationTime;
    /**
     * 字段描述：计划实施时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    @Excel(name = "计划实施时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date planImplementTime;
    /**
     * 字段描述：清单通过时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    @Excel(name = "清单通过时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date listPassTime;
    /**
     * 字段描述：项目联系人
     */
    @JsonProperty
    @Excel(name = "项目联系人")
    private String contactPerson;
    /**
     * 字段描述：版本
     */
    @JsonProperty
    private BigDecimal version;
    /**
     * 字段描述：是否有效 1-有效 0-失效
     */
    @JsonProperty
    private String valid;
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
    private Long regionId;
    /**
     * 字段描述：所属区域名称
     */
    @JsonProperty
    private String regionName;
    /**
     * 字段描述：项目id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long projectId;
    /**
     * 字段描述：项目名称
     */
    @JsonProperty
    private String projectName;
    /**
     * 字段描述：部门id
     */
    @JsonSerialize(using = ToStringSerializer.class)
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
    /**
     * 字段描述：数据创建系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    private Date createTime;
    /**
     * 字段描述：数据修改者id
     */
    @JsonProperty
    private String updateUser;
    /**
     * 字段描述：数据修改系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    private Date updateTime;
    /**
     * 字段描述：数据删除者
     */
    @JsonProperty
    private String delUser;
    /**
     * 字段描述：数据删除系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    private Date delTime;
    /**
     * 字段描述：删除标识：0未删除；1已删除
     */
    @JsonProperty
    private String delFlag;
    /**
     * 字段描述：预留字段1
     */
    @JsonProperty
    private String ptVar1;
    /**
     * 字段描述：预留字段2
     */
    @JsonProperty
    private String ptVar2;
    /**
     * 字段描述：预留字段3
     */
    @JsonProperty
    private String ptVar3;
    /**
     * 字段描述：预留字段4
     */
    @JsonProperty
    private String ptVar4;
    /**
     * 字段描述：预留字段5
     */
    @JsonProperty
    private String ptVar5;
}
