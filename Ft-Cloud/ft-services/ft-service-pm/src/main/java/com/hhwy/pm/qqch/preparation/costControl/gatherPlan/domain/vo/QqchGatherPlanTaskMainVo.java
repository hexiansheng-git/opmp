package com.hhwy.pm.qqch.preparation.costControl.gatherPlan.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author han
 * @date 2023-08-23 11:10:38
 * @remark qqch_gather_plan_task_main
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QqchGatherPlanTaskMainVo extends PreparationEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：wbs主表id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long wbsMainId;
    /**
     * 字段描述：wbs版本
     */
    @JsonProperty
    private Integer wbsVersion;

    private List<QqchGatherPlanTaskVo> qqchGatherPlanTaskVoList;
}
