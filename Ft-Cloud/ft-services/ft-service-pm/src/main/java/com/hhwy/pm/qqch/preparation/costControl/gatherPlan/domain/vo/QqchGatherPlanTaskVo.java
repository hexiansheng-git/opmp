package com.hhwy.pm.qqch.preparation.costControl.gatherPlan.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author han
 * @date 2023-08-23 11:10:35
 * @remark qqch_gather_plan_task
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QqchGatherPlanTaskVo {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：wbs主键id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long id;
    /**
     * 字段描述：本地表主键id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long gatherId;
    /**
     * 字段描述：主表id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long masterId;
    /**
     * 字段描述：wbs  id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long wbsId;
    /**
     * 字段描述：wbs编号
     */
    @JsonProperty
    private String wbsCode;
    /**
     * 字段描述：是否包含子级，0:否,1:是
     */
    @JsonProperty
    private Integer haveChildren;
    /**
     * 字段描述：项目部位（部位编号）
     */
    @JsonProperty
    private String partCode;
    /**
     * 字段描述：名称（部位名称）
     */
    @JsonProperty
    private String partName;
    /**
     * 字段描述：节点类型,字典:xmsl_wbs_type  类型
     */
    @JsonProperty
    private String type;
    /**
     * 字段描述：采集人
     */
    @JsonProperty
    private String gatherer;
    /**
     * 字段描述：采集人id
     */
    @JsonProperty
    private String gathererId;
    /**
     * 字段描述：审核人
     */
    @JsonProperty
    private String verifier;
    /**
     * 字段描述：审核人id
     */
    @JsonProperty
    private String verifierId;
    /**
     * 字段描述：开工时间
     */
    @JsonProperty
    private String startWorkTime;
    /**
     * 字段描述：完工时间
     */
    @JsonProperty
    private String endWorkTime;
    /**
     * 字段描述：备注/描述
     */
    @JsonProperty
    private String remark;

    private List<QqchGatherPlanTaskVo> children = new ArrayList<>();
}
