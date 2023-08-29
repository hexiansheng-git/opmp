package com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hhwy.utils.excel.FtExcel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author han
 * @date 2023-07-25 10:39:25
 * @remark 课题研究计划导出Vo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QqchTopicResearchPlanExportVo {
    private static final long serialVersionUID = 1L;

    @FtExcel(name = "序号")
    private String SerialNumber;
    /**
     * 字段描述：所属区域名称
     */
    @JsonProperty
    @FtExcel(name = "所属区域名称")
    private String regionName;
    /**
     * 字段描述：项目名称
     */
    @JsonProperty
    @FtExcel(name = "项目名称")
    private String projectName;
    /**
     * 字段描述：课题名称
     */
    @JsonProperty
    @FtExcel(name = "课题名称")
    private String topicName;
    /**
     * 字段描述：依托工程相关主要参数  主要研究内容、主要创新点（如果有）
     */
    @JsonProperty
    @FtExcel(name = "依托工程相关主要参数  主要研究内容、主要创新点（如果有）")
    private String keyParameter;
    /**
     * 字段描述：五项考核指标  基本要求：局级优秀论文1篇、省部级工法1篇、专利1个
     */
    @JsonProperty
    @FtExcel(name = "五项考核指标  基本要求：局级优秀论文1篇、省部级工法1篇、专利1个")
    private String assessTarget;
    /**
     * 字段描述：课题立项时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @FtExcel(name = "课题立项时间", dateFormat = "yyyy-MM-dd")
    private Date topicApprovalTime;
    /**
     * 字段描述：计划研发时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @FtExcel(name = "计划研发时间", dateFormat = "yyyy-MM-dd")
    private Date planDevelopmentTime;
    /**
     * 字段描述：项目负责人
     */
    @JsonProperty
    @FtExcel(name = "项目负责人")
    private String projectDirector;
    /**
     * 字段描述：申请费用
     */
    @JsonProperty
    @FtExcel(name = "申请费用")
    private BigDecimal applyCost;
    /**
     * 字段描述：项目自筹
     */
    @JsonProperty
    @FtExcel(name = "项目自筹")
    private String projectFunds;
    /**
     * 字段描述：阶段性工作计划
     */
    @JsonProperty
    @FtExcel(name = "阶段性工作计划")
    private String stageWorkPlan;
    /**
     * 字段描述：配套科研成果
     */
    @JsonProperty
    @FtExcel(name = "配套科研成果")
    private String matchedResearchAchievement;
    /**
     * 字段描述：备注
     */
    @JsonProperty
    @FtExcel(name = "备注")
    private String remark;
}
