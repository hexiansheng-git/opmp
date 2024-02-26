package com.hhwy.sp.techManagement.sgsjTechnicalScienceTopic.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.converters.bigdecimal.BigDecimalStringConverter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hhwy.sp.utils.easyExcel.DateToStringConvert;
import com.hhwy.sp.utils.easyExcel.ExcelDict;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 功能描述: 科技管理 - 科研课题研发管理
 * @author fsd
 * @date 2024-01-29 14:11:17
 * @remark sgsj_technical_science_topic
 */
@Data
@ColumnWidth(value = 15)
public class SgsjTechnicalScienceTopicDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
     * 字段描述：课题编号
     */
    @JsonProperty
    @ExcelProperty(value = "课题编号")
    private String topicCode;
    /**
     * 字段描述：课题名称
     */
    @JsonProperty
    @ExcelProperty(value = "课题名称")
    private String topicName;

    /**
     * 字段描述：项目名称
     */
    @JsonProperty
    @ExcelProperty(value = "项目名称")
    private String projectName;

    /**
     * 字段描述：课题研发日期
     */
    @JsonProperty
    @ExcelProperty(value = "课题研发日期")
    private String startEndDate;

    /**
     * 字段描述：课题负责人
     */
    @JsonProperty
    @ExcelProperty(value = "课题负责人")
    private String dutyPersonName;
    /**
     * 字段描述：研发预算（万元）
     */
    @JsonProperty
    @ExcelProperty(value = "研发预算（万元）", converter = BigDecimalStringConverter.class)
    private BigDecimal rdCost;

    /**
     * 字段描述：协作单位
     */
    @JsonProperty
    @ExcelProperty(value = "协作单位")
    private String togetherUnit;
    /**
     * 字段描述：其他协作单位
     */
    @JsonProperty
    @ExcelProperty(value = "其他协作单位")
    private String togetherUnitOther;

    @JsonProperty
    @ExcelProperty(value = "申请状态")
    @ExcelDict(dictType = "data_current_state")
    private String applyState;

    /**
     * 字段描述：课题进度
     */
    @JsonProperty
    @ExcelProperty(value = "课题进度")
    @ExcelDict(dictType = "")
    private String topicCurentNode;
    /**
     * 字段描述：当前状态
     */
    @JsonProperty
    @ExcelProperty(value = "当前状态")
    private String taskStatus;
    /**
     * 字段描述：当前经办人
     */
    @JsonProperty
    @ExcelProperty(value = "当前经办人")
    private String handlePerson;

    /**
     * 字段描述：鉴定单位
     */
    @JsonProperty
    @ExcelProperty(value = "鉴定单位")
    private String authenticateUnit;
    /**
     * 字段描述：鉴定日期
     */
    @JsonProperty
    @DateTimeFormat(pattern = "yyyy年MM月dd日")
    @ExcelProperty(value = "鉴定日期")
    private Date authenticateDate;
    /**
     * 字段描述：评价结论
     */
    @JsonProperty
    @ExcelProperty(value = "评价结论")
    private String evaluateConclusion;


    /**
     * 字段描述：申报奖项
     */
    @JsonProperty
    @ExcelProperty(value = "申报奖项")
    private String applyAward;
    /**
     * 字段描述：奖项等级
     */
    @JsonProperty
    @ExcelProperty(value = "奖项等级")
    private String awardGrade;
    /**
     * 字段描述：奖项类别
     */
    @JsonProperty
    @ExcelProperty(value = "奖项类别")
    private String awardType;
    /**
     * 字段描述：授予单位
     */
    @JsonProperty
    @ExcelProperty(value = "授予单位")
    private String grantUnit;
    /**
     * 字段描述：奖项时间
     */
    @JsonProperty
    @DateTimeFormat(pattern = "yyyy年MM月dd日")
    @ExcelProperty(value = "奖项时间", converter = DateToStringConvert.class)
    private Date awardTime;

}
