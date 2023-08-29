package com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.vo;

import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.alibaba.excel.annotation.ExcelProperty;
import com.hhwy.utils.excelUtil.MyDateConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * @author han
 * @date 2023-07-27 15:51:15
 * @remark 高新维护计划
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QqchAdvancedVindicatePlanExportVo {

    @ExcelProperty(value = "序号",index = 0)
    private String SerialNumber;

    /**
     * 字段描述：单位名称
     */
    @JsonProperty
    @ExcelProperty(value = "单位名称",index = 1)
    private String unitName;
    /**
     * 字段描述：研发课题名称
     */
    @JsonProperty
    @ExcelProperty(value = "研发课题名称",index = 2)
    private String researchTopicName;
    /**
     * 字段描述：课题编号
     */
    @JsonProperty
    @ExcelProperty(value = "课题编号",index = 3)
    private String topicCode;
    /**
     * 字段描述：课题类别
     */
    @JsonProperty
    @ExcelProperty(value = "课题类别",index = 4)
    private String topicType;
    /**
     * 字段描述：起始日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @DateTimeFormat("yyyy-MM-dd")
    @ExcelProperty(value = {"研发日期","起始日期"},index = 5,converter = MyDateConverter.class)
    private Date startDate;
    /**
     * 字段描述：完成日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @DateTimeFormat("yyyy-MM-dd")
    @ExcelProperty(value = {"研发日期","完成日期"},index = 6,converter = MyDateConverter.class)
    private Date endDate;
    /**
     * 字段描述：合计
     */
    @JsonProperty
    @ExcelProperty(value = {"研发费用预算（万元）","合计"},index = 7)
    private BigDecimal total;
    /**
     * 字段描述：拟定研发人员
     */
    @JsonProperty
    @ExcelProperty(value = "拟定研发人员",index = 8)
    private String simulateResearchStaff;
    /**
     * 字段描述：计划取得研发成果（外部证据）（专利、工法、查新、论文等各几项）
     */
    @JsonProperty
    @ExcelProperty(value = "计划取得研发成果（外部证据）（专利、工法、查新、论文等各几项）",index = 9)
    private String extrinsicEvidence;

    /* 年份预算键值对集合 */
    private Map<String,BigDecimal> vintageBudgetMap;
}
