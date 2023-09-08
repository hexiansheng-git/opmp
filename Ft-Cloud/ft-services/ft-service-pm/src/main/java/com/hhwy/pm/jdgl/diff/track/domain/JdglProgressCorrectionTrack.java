package com.hhwy.pm.jdgl.diff.track.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.common.core.web.domain.BaseEntity;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import lombok.Data;

/**
 * @author zhenglili
 * @date 2023-08-24 16:51:08
 * @remark jdgl_progress_correction_track
 */
@Data
public class JdglProgressCorrectionTrack extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：主键
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "主键")
    private Long id;
    /**
     * 字段描述：期次
     */
    @JsonProperty
    @Excel(name = "期次")
    private String period;
    /**
     * 字段描述：风险等级（字典类别track_risk_level）
     */
    @JsonProperty
    @Excel(name = "风险等级（字典类别track_risk_level）")
    private String riskLevel;
    /**
     * 字段描述：本期综合得分
     */
    @JsonProperty
    @Excel(name = "本期综合得分")
    private BigDecimal periodTotalScore;
    /**
     * 字段描述：纠偏措施编制日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "纠偏措施编制日期", dateFormat = "yyyy-MM-dd")
    private Date correctionDate;
    /**
     * 字段描述：预计纠偏完成日期
     */
    @JsonProperty
    @Excel(name = "预计纠偏完成日期")
    private Date planCorrectionCompleteDate;
    /**
     * 字段描述：周报期次
     */
    @JsonProperty
    @Excel(name = "周报期次")
    private String weekReportPeriod;
    /**
     * 字段描述：周报生成日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "周报生成日期", dateFormat = "yyyy-MM-dd")
    private Date weekReportBuildDate;
    /**
     * 字段描述：当月综合得分
     */
    @JsonProperty
    @Excel(name = "当月综合得分")
    private BigDecimal monthTotalScore;
    /**
     * 字段描述：周报时间
     */
    @JsonProperty
    @Excel(name = "周报时间")
    private String weekReportTime;
    /**
     * 字段描述：本周产值计划
     */
    @JsonProperty
    @Excel(name = "本周产值计划")
    private BigDecimal weekValuePlan;
    /**
     * 字段描述：本周产值完成
     */
    @JsonProperty
    @Excel(name = "本周产值完成")
    private BigDecimal weekValueComplete;
    /**
     * 字段描述：周完成比例
     */
    @JsonProperty
    @Excel(name = "周完成比例")
    private BigDecimal weekCompleteRatio;
    /**
     * 字段描述：当月产值计划（$）
     */
    @JsonProperty
    @Excel(name = "当月产值计划（$）")
    private BigDecimal monthValuePlan;
    /**
     * 字段描述：当月产值完成（$）
     */
    @JsonProperty
    @Excel(name = "当月产值完成（$）")
    private BigDecimal monthValueComplete;
    /**
     * 字段描述：开累产值完成（$）
     */
    @JsonProperty
    @Excel(name = "开累产值完成（$）")
    private BigDecimal sumValueComplete;
    /**
     * 字段描述：开累计量（$）
     */
    @JsonProperty
    @Excel(name = "开累计量（$）")
    private BigDecimal sumMeterage;
    /**
     * 字段描述：工期完成百分比（%）
     */
    @JsonProperty
    @Excel(name = "工期完成百分比（%）")
    private BigDecimal durationCompletePercentage;
    /**
     * 字段描述：项目计划当月总时差
     */
    @JsonProperty
    @Excel(name = "项目计划当月总时差")
    private BigDecimal planMathTotalTimeDiff;
    /**
     * 字段描述：季度产值计划完成百分比
     */
    @JsonProperty
    @Excel(name = "季度产值计划完成百分比")
    private BigDecimal quarterValuePlanCompletePercentage;
    /**
     * 字段描述：关键线路形象完成百分比
     */
    @JsonProperty
    @Excel(name = "关键线路形象完成百分比")
    private BigDecimal keyLineImageCompletePercentage;
    /**
     * 字段描述：本月差异值（%）
     */
    @JsonProperty
    @Excel(name = "本月差异值（%）")
    private BigDecimal monthDiffValue;
    /**
     * 字段描述：近三个月差异化值（%）
     */
    @JsonProperty
    @Excel(name = "近三个月差异化值（%）")
    private BigDecimal lastThreeMonthDiffValue;
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
     * 详情集合
     */
    private List<JdglProgressCorrectionTrackDetail> detailList;
}
