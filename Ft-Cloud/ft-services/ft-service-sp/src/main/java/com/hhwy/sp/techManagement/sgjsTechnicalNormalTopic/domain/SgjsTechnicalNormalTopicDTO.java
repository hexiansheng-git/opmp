package com.hhwy.sp.techManagement.sgjsTechnicalNormalTopic.domain;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.common.core.web.domain.BaseEntity;
import com.hhwy.sp.techManagement.sgjsTechnicalNormalTopic.sgjsTechnicalNormalTopicCost.domain.SgjsTechnicalNormalTopicCost;
import com.hhwy.utils.dict.DictUtil;
import lombok.Data;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 功能描述: 科技管理 - 一般课题研发管理
 * @author fsd
 * @date 2024-01-25 10:22:49
 * @remark sgjs_technical_normal_topic
 */
@Data
public class SgjsTechnicalNormalTopicDTO {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：所属区域名称
     */
    @JsonProperty
    @ExcelProperty(value = "单位名称", order = 1)
    private String regionName;

    /**
     * 字段描述：项目名称
     */
    @JsonProperty
    @ExcelProperty(value = "依托项目名称", order = 2)
    private String projectName;

    /**
     * 字段描述：课题编号
     */
    @JsonProperty
    @ExcelProperty(value = "课题编号", order = 3)
    private String topicCode;
    /**
     * 字段描述：课题名称
     */
    @JsonProperty
    @ExcelProperty(value = "课题名称", order = 4)
    private String topicName;
    /**
     * 字段描述：高新资质
     */
    @JsonProperty
    @ExcelProperty(value = "高新资质", order = 5)
    private String highCertificate;
    /**
     * 字段描述：课题类别编号
     */
    @JsonProperty
    private String topicKind;
    /**
     * 字段描述：课题类别
     */
    @JsonProperty
    @ExcelProperty(value = "课题类别", order = 6)
    private String topicKindName;

    /**
     * 字段描述：项目技术经济目标编号
     */
    @JsonProperty
    private String ecoTarget;
    /**
     * 字段描述：项目技术经济目标
     */
    @JsonProperty
    @ExcelProperty(value = "项目技术经济目标", order = 7)
    private String ecoTargetName;

    /**
     * 字段描述：课题状态
     */
    @JsonProperty
    @ExcelProperty(value = "课题状态", order = 8)
    private String topicState;
    /**
     * 字段描述：项目成果形式
     */
    @JsonProperty
    @ExcelProperty(value = "项目成果形式", order = 8)
    private String achievementKind;

    /**
     * 字段描述：研发起始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @ExcelProperty(value = "研发起始日期", order = 10)
    private Date startDate;
    /**
     * 字段描述：研发完成时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @ExcelProperty(value = "研发完成日期", order = 11)
    private Date endDate;
    /**
     * 字段描述：研发人员名单
     */
    @JsonProperty
    private String personList;
    /**
     * 字段描述：研发人员名单
     */
    @JsonProperty
    @ExcelProperty(value = "研发人员名单")
    private String personNameList;

    /**
     * 字段描述：备注
     */
    @JsonProperty
    @ExcelProperty(value = "备注")
    private String remark;

}
