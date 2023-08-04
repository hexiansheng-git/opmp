package com.hhwy.pm.qqch.preparation.costControl.secondManagePlan.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author han
 * @date 2023-08-04 10:47:11
 * @remark 普通要点策划/变更策划/索赔策划 Vo类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SecondManageKeyPointPlanVo {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：主键
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long id;
    /**
     * 字段描述：父id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long pid;
    /**
     * 字段描述：优化方向
     */
    @JsonProperty
    private String optimizedDirection;
    /**
     * 字段描述：内容描述
     */
    @JsonProperty
    private String contentDescription;
    /**
     * 字段描述：合同权利（取二次经营要点识别关联合同条款中的条款内容）
     */
    @JsonProperty
    private String contractRight;
    /**
     * 字段描述：触发条件（取二次经营要点识别关联合同条款中的触发条件）
     */
    @JsonProperty
    private String triggerCondition;
    /**
     * 字段描述：合同依据（取二次经营要点中的关联合同条款）
     */
    @JsonProperty
    private String contractBasis;
    /**
     * 字段描述：应对措施（取二次经营要点中的拟采取措施）
     */
    @JsonProperty
    private String proposedMeasures;
    /**
     * 字段描述：备注/描述
     */
    @JsonProperty
    private String remark;

    private List<SecondManageKeyPointPlanVo> children;
}
