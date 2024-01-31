package com.hhwy.sp.techManagement.sgjsPaperPublish.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.utils.excel.FtExcel;
import lombok.Data;

import java.util.Date;

/**
 * @author han
 * @date 2024-01-25 11:01:37
 * @remark sgjs_paper_publish
 */
@Data
public class PaperPublishExportVo {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：主键
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long id;
    /**
     * 字段描述：论文编号
     */
    @JsonProperty
    @FtExcel(name = "论文编号")
    private String paperCode;
    /**
     * 字段描述：论文名称
     */
    @JsonProperty
    @FtExcel(name = "论文名称")
    private String paperName;
    /**
     * 字段描述：项目名称
     */
    @JsonProperty
    @FtExcel(name = "项目名称")
    private String projectName;
    /**
     * 字段描述：专业类别
     */
    @JsonProperty
    @FtExcel(name = "专业类别",dictType = "profession_type")
    private String professionType;
    /**
     * 字段描述：专业板块
     */
    @JsonProperty
    @FtExcel(name = "专业板块",dictType = "profession_plate")
    private String professionPlate;
    /**
     * 字段描述：主要完成人
     */
    @JsonProperty
    @FtExcel(name = "主要完成人")
    private String principalConsumator;
    /**
     * 字段描述：参与单位
     */
    @JsonProperty
    @FtExcel(name = "参与单位")
    private String participationUnit;
    /**
     * 字段描述：刊物等级
     */
    @JsonProperty
    @FtExcel(name = "刊物等级",dictType = "periodical_grade")
    private String periodicalGrade;
    /**
     * 字段描述：刊物名称
     */
    @JsonProperty
    @FtExcel(name = "刊物名称")
    private String periodicalName;
    /**
     * 字段描述：刊登日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @FtExcel(name = "刊登日期", dateFormat = "yyyy-MM-dd")
    private Date publishDate;

    @FtExcel(name = "成果奖项")
    private String allAward;
}
