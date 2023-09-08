package com.hhwy.pm.jdgl.monthpl.jdglMonthPlan.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.common.core.web.domain.BaseEntity;
import com.hhwy.pm.jdgl.monthpl.jdglMonthImagePlan.domain.JdglMonthImagePlan;
import com.hhwy.pm.jdgl.monthpl.jdglMonthValuePlan.domain.JdglMonthValuePlan;
import com.hhwy.utils.validation.ValidationGroups;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author chenjinhao
 * @date 2023-08-21 15:48:04
 * @remark jdgl_month_plan
 */
@Data
public class JdglMonthPlan extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：主键id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "主键id")
    private Long id;
    /**
     * 字段描述：附件组id
     */
    @JsonProperty
    @Excel(name = "附件组id")
    private String fileGroupId;
    /**
     * 字段描述：备注/描述
     */
    @JsonProperty
    @Excel(name = "备注/描述")
    private String remark;
    /**
     * 字段描述：项目名称
     */
    @JsonProperty
    @Excel(name = "项目名称")
    private String projectName;
    /**
     * 字段描述：年份
     */
    @JsonProperty
    @Excel(name = "年份")
    @NotBlank(message = "期次不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private String year;
    /**
     * 字段描述：月度
     */
    @JsonProperty
    @Excel(name = "月度")
    @NotBlank(message = "期次不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private String month;
    /**
     * 字段描述：起始日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "起始日期", dateFormat = "yyyy-MM-dd")
    private Date startDate;
    /**
     * 字段描述：结束日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "结束日期", dateFormat = "yyyy-MM-dd")
    private Date endDate;
    /**
     * 字段描述：版本
     */
    @JsonProperty
    @Excel(name = "版本")
    private String version;
    /**
     * 字段描述：是否生效版本
     */
    @JsonProperty
    @Excel(name = "是否生效版本")
    private String isUse;
    /**
     * 字段描述：有效合同金额（合同币种）
     */
    @JsonProperty
    @Excel(name = "有效合同金额（合同币种）")
    private BigDecimal contactAmtCu;
    /**
     * 字段描述：本季计划产值（万美元）
     */
    @JsonProperty
    @Excel(name = "本季计划产值（万美元）")
    private BigDecimal quarterPlanValueDl;
    /**
     * 字段描述：季度开累完成产值（万美元）
     */
    @JsonProperty
    @Excel(name = "季度开累完成产值（万美元）")
    private BigDecimal quarterCompValueDl;
    /**
     * 字段描述：季度剩余未完成产值（万美元）
     */
    @JsonProperty
    @Excel(name = "季度剩余未完成产值（万美元）")
    private BigDecimal remainQuarterAmtDl;
    /**
     * 字段描述：本月度计划产值（合同币种）
     */
    @JsonProperty
    @Excel(name = "本月度计划产值（合同币种）")
    private BigDecimal thisPlanValueCu;
    /**
     * 字段描述：合同币种
     */
    @JsonProperty
    @Excel(name = "合同币种")
    private String custUnit;
    /**
     * 字段描述：合同编码
     */
    @JsonProperty
    @Excel(name = "合同编码")
    private String custUnitCode;
    /**
     * 字段描述：汇率
     */
    @JsonProperty
    @Excel(name = "汇率")
    private BigDecimal exchangeRate;
    /**
     * 字段描述：本月度计划产值（万美元）
     */
    @JsonProperty
    @Excel(name = "本月度计划产值（万美元）")
    private BigDecimal thisPlanValueDl;
    /**
     * 字段描述：是否需调整计划
     */
    @JsonProperty
    @Excel(name = "是否需调整计划")
    private String isNeedAdjust;
    /**
     * 字段描述：批复季计划产值（美元）
     */
    @JsonProperty
    @Excel(name = "批复月计划产值（美元）")
    private BigDecimal approveThisPlanValueDl;
    /**
     * 字段描述：计划说明
     */
    @JsonProperty
    @Excel(name = "计划说明")
    private String planDescription;
    /**
     * 字段描述：月度进度产值计划
     */
    @JsonProperty
    private List<JdglMonthValuePlan> jdglMonthValuePlanList;
    /**
     * 字段描述：月度进度产值计划
     */
    @JsonProperty
    private List<JdglMonthImagePlan> jdglMonthImagePlanList;
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
    @JsonFormat(pattern = "yyyy-MM-dd")
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
     * 字段描述：当前审批人
     */
    @JsonProperty
    @Excel(name = "当前审批人")
    private String assignee;
    /**
     * 字段描述：当前总计划版本
     */
    @JsonProperty
    @Excel(name = "当前总计划版本")
    private String thisTotalVersion;

}
