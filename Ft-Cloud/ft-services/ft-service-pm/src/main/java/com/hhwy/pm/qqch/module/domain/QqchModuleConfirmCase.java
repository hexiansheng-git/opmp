package com.hhwy.pm.qqch.module.domain;

import com.hhwy.common.core.web.domain.BaseEntity;
import java.util.Date;
import java.util.List;

import com.hhwy.common.core.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author han
 * @date 2023-07-11 15:30:26
 * @remark
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QqchModuleConfirmCase extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：主键id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "主键id")
    private Long id;
    /**
     * 字段描述：模块标识（页面唯一标识）1： 2： ...
     */
    @JsonProperty
    @Excel(name = "模块标识（页面唯一标识）1： 2： ...")
    private String moduleIdentity;
    private List<String> moduleIdentityList;
    /**
     * 字段描述：模块名称
     */
    @JsonProperty
    @Excel(name = "模块名称")
    private String moduleName;
    /**
     * 字段描述：阶段标识（1：第一阶段，2：第二阶段，3：第三阶段）
     */
    @JsonProperty
    @Excel(name = "阶段标识（1：第一阶段，2：第二阶段，3：第三阶段）")
    private String stageIdentity;
    /**
     * 字段描述：阶段名称
     */
    @JsonProperty
    @Excel(name = "阶段名称")
    private String stageName;
    /**
     * 字段描述：确认状态（0：未确认，1：已确认）
     */
    @JsonProperty
    @Excel(name = "确认状态（0：未确认，1：已确认）")
    private String confirmStatus;
    /**
     * 字段描述：确认人id
     */
    @JsonProperty
    @Excel(name = "确认人id")
    private String confirmUser;
    private List<String> confirmUserList;
    /**
     * 字段描述：确认人名称
     */
    @JsonProperty
    @Excel(name = "确认人名称")
    private String confirmUserName;
    /**
     * 字段描述：确认时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    @Excel(name = "确认时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date confirmTime;
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
