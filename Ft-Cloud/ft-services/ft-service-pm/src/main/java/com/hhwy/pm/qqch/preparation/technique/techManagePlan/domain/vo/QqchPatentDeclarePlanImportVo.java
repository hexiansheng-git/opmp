package com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hhwy.common.core.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author han
 * @date 2023-07-25 10:40:39
 * @remark 专利申报计划
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QqchPatentDeclarePlanImportVo {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：增加时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "增加时间", dateFormat = "yyyy-MM-dd")
    private Date addTime;
    /**
     * 字段描述：原序号
     */
    @JsonProperty
    @Excel(name = "原序号")
    private String originalSerialNumber;
    /**
     * 字段描述：一般课题名称
     */
    @JsonProperty
    @Excel(name = "一般课题名称")
    private String generalTopicName;
    /**
     * 字段描述：科技研发立项在研课题
     */
    @JsonProperty
    @Excel(name = "科技研发立项在研课题")
    private String inResearchTopic;
    /**
     * 字段描述：科技攻关项目
     */
    @JsonProperty
    @Excel(name = "科技攻关项目")
    private String technologyKeyProject;
    /**
     * 字段描述：专利发明人
     */
    @JsonProperty
    @Excel(name = "专利发明人")
    private String patentInventor;
    /**
     * 字段描述：第一发明人身份证号
     */
    @JsonProperty
    @Excel(name = "第一发明人身份证号")
    private String firstInventorIdNumber;
    /**
     * 字段描述：备注
     */
    @JsonProperty
    @Excel(name = "备注")
    private String remark;
}
