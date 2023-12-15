package com.hhwy.sp.experiment.sgjsCriticalExpReport.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.web.domain.BaseEntity;
import com.hhwy.utils.excel.FtExcel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author han
 * @date 2023-12-11 16:41:12
 * @remark sgjs_critical_exp_report
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SgjsCriticalExpReport extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：主键
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long id;
    /**
     * 字段描述：试验报告编号
     */
    @JsonProperty
    @FtExcel(name = "试验报告编号")
    private String expReportCode;
    /**
     * 字段描述：试验类型
     */
    @JsonProperty
    @FtExcel(name = "试验类型",dictType = "exp_type")
    private String expType;
    /**
     * 字段描述：试验报告名称
     */
    @JsonProperty
    @FtExcel(name = "试验报告名称")
    private String expReportName;
    /**
     * 字段描述：WBS编码
     */
    @JsonProperty
    private String wbsCode;
    /**
     * 字段描述：WBS名称
     */
    @JsonProperty
    @FtExcel(name = "所属WBS名称")
    private String wbsName;
    /**
     * 字段描述：计划实施日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @FtExcel(name = "计划实施日期", dateFormat = "yyyy-MM-dd")
    private Date planEffectDate;
    /**
     * 字段描述：是否外委（1：是，0：否）
     */
    @JsonProperty
    @FtExcel(name = "是否外委",dictType = "common_yes")
    private String outsource;
    /**
     * 字段描述：是否监理审批（1：是，0：否）
     */
    @JsonProperty
    @FtExcel(name = "是否监理审批",dictType = "common_yes")
    private String superviseApproval;
    /**
     * 字段描述：计划提交日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @FtExcel(name = "计划提交日期", dateFormat = "yyyy-MM-dd")
    private Date planCommitDate;
    /**
     * 字段描述：实际提交日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @FtExcel(name = "实际提交日期", dateFormat = "yyyy-MM-dd")
    private Date actualCommitDate;
    /**
     * 字段描述：提交人id
     */
    @JsonProperty
    private String submitterId;
    /**
     * 字段描述：提交人名称
     */
    @JsonProperty
    @FtExcel(name = "提交人名称")
    private String submitterName;
    /**
     * 字段描述：附件组id
     */
    @JsonProperty
    private String fileGroupId;
    /**
     * 字段描述：备注
     */
    @JsonProperty
    private String remark;
    /**
     * 字段描述：流程状态（5已完成）
     */
    @JsonProperty
    private String taskStatus;
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

    /**
     * 是否是新增数据
     */
    private String isAdd;
}
