package com.hhwy.pm.xmsl.project.domain;

import com.hhwy.common.core.web.domain.BaseEntity;
import java.util.Date;
import com.hhwy.common.core.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author han
 * @date 2023-07-03 09:48:28
 * @remark 主要桥梁结构形式
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class XmslProjectBridgeStructure extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：主键id
     */
    @JsonProperty
    @Excel(name = "主键id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    /**
     * 字段描述：主表id
     */
    @JsonProperty
    @Excel(name = "主表id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long projectBasicInfoId;
    /**
     * 字段描述：项目id
     */
    @JsonProperty
    @Excel(name = "项目id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long projectId;
    /**
     * 字段描述：项目名称
     */
    @JsonProperty
    @Excel(name = "项目名称")
    private String projectName;
    /**
     * 字段描述：桩号
     */
    @JsonProperty
    @Excel(name = "桩号")
    private String stakeMark;
    /**
     * 字段描述：长度(m)
     */
    @JsonProperty
    @Excel(name = "长度(m)")
    private String length;
    /**
     * 字段描述：跨度（m）
     */
    @JsonProperty
    @Excel(name = "跨度（m）")
    private String span;
    /**
     * 字段描述：净高（m）
     */
    @JsonProperty
    @Excel(name = "净高（m）")
    private String clearHeight;
    /**
     * 字段描述：基础形式
     */
    @JsonProperty
    @Excel(name = "基础形式")
    private String baseForm;
    /**
     * 字段描述：下部结构
     */
    @JsonProperty
    @Excel(name = "下部结构")
    private String substruction;
    /**
     * 字段描述：上部结构
     */
    @JsonProperty
    @Excel(name = "上部结构")
    private String superstructure;
    /**
     * 字段描述：所属区域id
     */
    @JsonProperty
    @Excel(name = "所属区域id")
    private Integer regionId;
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
    private Integer deptId;
    /**
     * 字段描述：数据创建者id
     */
    @JsonProperty
    @Excel(name = "数据创建者id")
    private String createUser;
    /**
     * 字段描述：数据创建系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    @Excel(name = "数据创建系统时间")
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
    @Excel(name = "数据修改系统时间")
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
    @Excel(name = "数据删除系统时间")
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
