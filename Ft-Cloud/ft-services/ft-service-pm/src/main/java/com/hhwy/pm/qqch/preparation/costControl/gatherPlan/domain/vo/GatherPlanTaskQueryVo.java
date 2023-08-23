package com.hhwy.pm.qqch.preparation.costControl.gatherPlan.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author han
 * @date 2023-08-23 11:25:13
 * @remark qqch_gather_plan_task
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GatherPlanTaskQueryVo {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：wbs  id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String parentId;

    /**
     * 字段描述：wbs主表id
     */
    @JsonProperty
    private Long wbsMainId;

    /**
     * 字段描述：版本
     */
    @JsonProperty
    private BigDecimal version;
}
