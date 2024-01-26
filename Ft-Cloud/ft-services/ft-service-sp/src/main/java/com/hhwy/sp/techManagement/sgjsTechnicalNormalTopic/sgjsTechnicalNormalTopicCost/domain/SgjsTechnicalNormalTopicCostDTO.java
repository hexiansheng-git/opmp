package com.hhwy.sp.techManagement.sgjsTechnicalNormalTopic.sgjsTechnicalNormalTopicCost.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.sp.techManagement.sgjsTechnicalNormalTopic.domain.SgjsTechnicalNormalTopic;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 功能描述: 科技管理 - 一般课题研发管理 - 年费用
 *
 * @author fsd
 * @date 2024-01-25 13:25:50
 * @remark sgjs_technical_normal_topic_cost
 */
@Data
public class SgjsTechnicalNormalTopicCostDTO {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：年
     */
    @JsonProperty
    @Excel(name = "${year}年", sort = 12)
    private Integer year;
    /**
     * 字段描述：研发费用预算（万元）
     */
    @JsonProperty
    @Excel(name = "研发费用预算（万元）", sort = 13)
    private BigDecimal rdCost;

}
