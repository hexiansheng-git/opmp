package com.hhwy.pm.xmsl.project.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.common.core.web.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author han
 * @date 2023-07-03 09:48:31
 * @remark 主要涵洞结构形式
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class XmslProjectCulvertStructure extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：主键id
     */
    @JsonProperty
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * 字段描述：主表id
     */
    @JsonProperty
    @JsonSerialize(using = ToStringSerializer.class)
    private Long projectBasicInfoId;
    /**
     * 字段描述：项目id
     */
    @JsonProperty
    @JsonSerialize(using = ToStringSerializer.class)
    private Long projectId;
    /**
     * 字段描述：项目名称
     */
    @JsonProperty
    private String projectName;
    /**
     * 字段描述：类型
     */
    @JsonProperty
    @Excel(name = "类型")
    private String type;
    /**
     * 字段描述：孔数
     */
    @JsonProperty
    @Excel(name = "孔数")
    private Integer holeCount;
    /**
     * 字段描述：跨径
     */
    @JsonProperty
    @Excel(name = "跨径")
    private BigDecimal span;
    /**
     * 字段描述：净高
     */
    @JsonProperty
    @Excel(name = "净高")
    private BigDecimal clearHeight;
    /**
     * 字段描述：涵身结构
     */
    @JsonProperty
    @Excel(name = "涵身结构")
    private String culvertStructure;
    /**
     * 字段描述：进出口形式
     */
    @JsonProperty
    @Excel(name = "进出口形式")
    private String importExportForm;
    /**
     * 字段描述：数量
     */
    @JsonProperty
    @Excel(name = "数量")
    private Integer count;
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
