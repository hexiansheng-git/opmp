package com.hhwy.sp.buildSchemeManage.review.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.utils.excel.FtExcel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author han
 * @date 2024-03-20 09:39:35
 * @remark 施工方案进展
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SgjsBuildSchemeEvolve {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：施工方案清单主键id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long id;
    /**
     * 字段描述：施工方案评审主键id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long reviewId;
    /**
     * 字段描述：方案名称
     */
    @JsonProperty
    @FtExcel(name = "方案名称")
    private String schemeName;
    /**
     * 字段描述：方案编号
     */
    @JsonProperty
    @FtExcel(name = "方案编号")
    private String schemeNum;
    /**
     * 字段描述：方案分级 1Ⅰ、2Ⅱ、3Ⅲ、4Ⅳ
     */
    @JsonProperty
    @FtExcel(name = "方案分级")
    private String schemeLevel;
    /**
     * 字段描述：方案类型
     */
    @JsonProperty
    @FtExcel(name = "方案类型")
    private String schemeType;
    /**
     * 字段描述：变更类型 1推迟、2提前、3新增、4废止
     */
    @JsonProperty
    private String changeType;
    /**
     * 字段描述：分部分项工程（取关联wbs）
     */
    @JsonProperty
    @FtExcel(name = "分部分项工程")
    private String subdivisionProject;
    /**
     * 字段描述：计划实施时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @FtExcel(name = "计划实施时间", dateFormat = "yyyy年MM月dd日")
    private Date planImplementTime;
    /**
     * 字段描述：清单审核通过时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @FtExcel(name = "清单通过时间", dateFormat = "yyyy年MM月dd日")
    private Date inventoryApprovalTime;
    /**
     * 字段描述：编制完成时间   取计划编制完成时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @FtExcel(name = "编制完成时间", dateFormat = "yyyy年MM月dd日")
    private Date completionTime;
    /**
     * 字段描述：提交审核时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @FtExcel(name = "提交审核时间", dateFormat = "yyyy年MM月dd日")
    private Date submitDate;
    /**
     * 字段描述：提交审核时间颜色  "red"  "green"   “gray"
     */
    @JsonProperty
    private String submitDateColor = "white";
    /**
     * 字段描述：方案审核通过时间
     */
    @JsonProperty
    @FtExcel(name = "方案审核通过时间", dateFormat = "yyyy年MM月dd日")
    private Date schemeApprovalTime;
    /**
     * 字段描述：方案审核通过时间颜色  "red"  "green"  “gray"：废弃   white
     */
    @JsonProperty
    private String schemeApprovalTimeColor = "white";
    /**
     * 字段描述：方案总得分
     */
    @JsonProperty
    @FtExcel(name = "方案总得分")
    private Double score;
    /**
     * 字段描述：方案评审状态
     */
    @JsonProperty
    @FtExcel(name = "方案评审状态")
    private String taskStatus;
    /**
     * 字段描述：方案进展  滞后/正常
     */
    @JsonProperty
    @FtExcel(name = "方案进展")
    private String schemeEvolve;
}
