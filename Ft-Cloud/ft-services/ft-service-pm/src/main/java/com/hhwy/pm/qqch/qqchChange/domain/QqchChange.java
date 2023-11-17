package com.hhwy.pm.qqch.qqchChange.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.common.core.web.domain.BaseEntity;
import com.hhwy.utils.common.CommonBaseEntity;
import com.hhwy.utils.validation.ValidationGroups;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author wk
 * @date 2023-11-06 17:41:43
 * @remark qqch_change
 */
@Data
public class QqchChange extends CommonBaseEntity {
    private static final long serialVersionUID = 1L;

    public QqchChange() {
    }

    public QqchChange(BigDecimal version) {
        this.version = version;
    }

    /**
     * 字段描述：主键id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "主键id")
    @NotNull(message = "id不能为空",groups = {ValidationGroups.Delete.class})
    private Long id;
    /**
     * 字段描述：变更编制负责人
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "变更编制负责人")
    private Long changeUser;
    /**
     * 字段描述：变更编制负责人名称
     */
    @JsonProperty
    @Excel(name = "变更编制负责人名称")
    private String changeUserName;
    /**
     * 字段描述：编制完成时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    @Excel(name = "编制完成时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date editFinishDate;
    /**
     * 字段描述：评审完成日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    @Excel(name = "评审完成日期", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date reviewFinishDate;
    /**
     * 字段描述：要求完成日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "要求完成日期", dateFormat = "yyyy-MM-dd")
    private Date reqFinishDate;
    /**
     * 字段描述：变更提交日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "变更提交日期", dateFormat = "yyyy-MM-dd")
    private Date changeSubmitDate;
    /**
     * 字段描述：变更条件
     */
    @JsonProperty
    @Excel(name = "变更条件")
    @NotBlank(message = "变更条件不能为空",groups = {ValidationGroups.Save.class})
    private String changeCondition;
    /**
     * 字段描述：变更原因
     */
    @JsonProperty
    @Excel(name = "变更原因")
    @NotBlank(message = "变更原因不能为空",groups = {ValidationGroups.Save.class})
    private String changeReason;
    /**
     * 字段描述：附件
     */
    @JsonProperty
    @Excel(name = "附件")
    private String filegroupid;
    /**
     * 字段描述：策划项数量
     */
    @JsonProperty
    @Excel(name = "策划项数量")
    private Integer planNum;
    /**
     * 字段描述：编制完成数量
     */
    @JsonProperty
    @Excel(name = "编制完成数量")
    private Integer finishNum;
    /**
     * 字段描述：完成率%
     */
    @JsonProperty
    @Excel(name = "完成率%")
    private BigDecimal finishRatio;
    /**
     * 字段描述：紧急程度 0特急 1紧急 2 正常
     */
    @JsonProperty
    @Excel(name = "紧急程度 0特急 1紧急 2 正常")
    private String exigencyStatus;
    /**
     * 字段描述：备注/描述
     */
    @JsonProperty
    @Excel(name = "备注/描述")
    private String remark;
    /**
     * 字段描述：版本号
     */
    @JsonProperty
    @Excel(name = "版本号")
    private BigDecimal version;

    private String versionStr;
    /**
     * 字段描述：是否生效,0:否，1:是
     */
    @JsonProperty
    @Excel(name = "是否生效,0:否，1:是")
    private Integer valid;
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
    private String projectCode;
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
    @Excel(name = "数据创建系统时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 字段描述：数据修改者id
     */
    @JsonProperty
    @Excel(name = "数据修改者id")
    private String updateUser;
    /**
     * 字段描述：数据创建者名称
     */
    @JsonProperty
    @Excel(name = "数据创建者名称")
    private String updateUserName;
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



    public String getVersionStr() {
        return version==null?null:"V"+version.setScale(1);
    }
}
