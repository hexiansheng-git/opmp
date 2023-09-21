package com.hhwy.pm.qqch.preparation.quality.qqchEmergencyExerciseControl.domain;

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

/**
 * @author ldd
 * @date 2023-08-10 18:39:07
 * @remark qqch_emergency_exercise_control
 *
 *  8.10.2 应急演练管控
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QqchEmergencyExerciseControl extends CommonBaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：主键
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "主键")
    private Long id;
    /**
     * 字段描述：应急预案管控id
     */
    @NotNull(message = "应急预案管控id不能为空", groups = {ValidationGroups.Update.class})
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "应急预案管控id")
    private Long planId;
    /**
     * 字段描述：应急预案管控名称
     */
    @NotBlank(message = "应急预案管控名称不能为空", groups = {ValidationGroups.Update.class})
    @JsonProperty
    @Excel(name = "应急预案管控名称")
    private String planName;
    /**
     * 字段描述：应急演练名称
     */
    @NotBlank(message = "应急演练名称不能为空", groups = {ValidationGroups.Save.class,ValidationGroups.Update.class})
    @JsonProperty
    @Excel(name = "应急演练名称")
    private String name;
    /**
     * 字段描述：演练形式
     */
    @NotBlank(message = "演练形式不能为空", groups = {ValidationGroups.Save.class,ValidationGroups.Update.class})
    @JsonProperty
    @Excel(name = "演练形式")
    private String exerciseType;
    /**
     * 字段描述：演练内容
     */
    @NotBlank(message = "演练内容不能为空", groups = {ValidationGroups.Save.class,ValidationGroups.Update.class})
    @JsonProperty
    @Excel(name = "演练内容")
    private String content;
    /**
     * 字段描述：危大工程id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "危大工程id")
    private String dangerousEngineerId;
    /**
     * 字段描述：危大工程
     */
    @JsonProperty
    @Excel(name = "危大工程")
    private String dangerousEngineerName;
    /**
     * 字段描述：演练时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "演练时间", dateFormat = "yyyy-MM-dd")
    private String ydate;
    /**
     * 字段描述：演练地点
     */
    @JsonProperty
    @Excel(name = "演练地点")
    private String address;
    /**
     * 字段描述：演练队伍id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "演练队伍id")
    private String teamId;
    /**
     * 字段描述：演练队伍
     */
    @JsonProperty
    @Excel(name = "演练队伍")
    private String teamName;
    /**
     * 字段描述：响应级别
     */
    @JsonProperty
    @Excel(name = "响应级别")
    private String responseLevel;
    /**
     * 字段描述：参与单位
     */
    @JsonProperty
    @Excel(name = "参与单位")
    private String parkDept;
    /**
     * 字段描述：该国家是否重复
     */
    @JsonProperty
    @Excel(name = "该国家是否重复")
    private String thisCountryDuplicated;
    /**
     * 字段描述：应急预案附件
     */
    @JsonProperty
    @Excel(name = "应急预案附件")
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
    /**
     * 字段描述：流程状态（5已完成）
     */
    @JsonProperty
    @Excel(name = "流程状态（5已完成）")
    private String taskStatus;
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

}
