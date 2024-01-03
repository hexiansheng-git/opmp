package com.hhwy.pm.qqch.preparation.quality.duty.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.common.core.web.domain.BaseEntity;
import com.hhwy.utils.validation.ValidationGroups;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author zhenglili
 * @date 2023-08-03 14:29:43
 * @remark qqch_person_control_plan
 */
@Data
public class QqchPersonControlPlan extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：主键
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "主键")
    private Long id;
    /**
     * 字段描述：父id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long pid;
    /**
     * 字段描述：人员id
     */
    @JsonProperty
    @Excel(name = "人员id")
    private String personId;
    /**
     * 字段描述：人员姓名
     */
    @JsonProperty
    @Excel(name = "人员姓名")
    @NotBlank(message = "人员姓名不能为空", groups = {ValidationGroups.Save.class})
    private String personName;
    /**
     * 字段描述：职务id
     */
    @JsonProperty
    @Excel(name = "职务id")
    private String dutiesId;
    /**
     * 字段描述：职务
     */
    @JsonProperty
    @Excel(name = "职务")
    private String duties;
    /**
     * 字段描述：证件名称
     */
    @JsonProperty
    @Excel(name = "证件名称")
    private String certificateName;
    /**
     * 字段描述：证件号码
     */
    @JsonProperty
    @Excel(name = "证件号码")
    private String certificateNo;
    /**
     * 字段描述：签发日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "签发日期", dateFormat = "yyyy-MM-dd")
    private Date issueDate;
    /**
     * 字段描述：有限期限(年）
     */
    @JsonProperty
    @Excel(name = "有限期限(年）")
    private Double limitPeriod;
    /**
     * 字段描述：消息提醒(字典类型message_warn)
     */
    @JsonProperty
    @Excel(name = "消息提醒(字典类型message_warn)")
    private String messageWarn;
    /**
     * 字段描述：管控措施
     */
    @JsonProperty
    @Excel(name = "管控措施")
    private String controlMeasures;
    /**
     * 字段描述：培训内容
     */
    @JsonProperty
    @Excel(name = "培训内容")
    private String content;
    /**
     * 字段描述：主要事项
     */
    @JsonProperty
    @Excel(name = "主要事项")
    private String mainMatter;
    /**
     * 字段描述：主责部门
     */
    @JsonProperty
    @Excel(name = "主责部门")
    private String mainDept;
    /**
     * 字段描述：协助部门
     */
    @JsonProperty
    @Excel(name = "协助部门")
    private String assistDept;
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
     * 字段描述：附件组id
     */
    @JsonProperty
    @Excel(name = "附件组id")
    private String fileGroupId;
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

    private List<QqchPersonControlPlan> children;
}
