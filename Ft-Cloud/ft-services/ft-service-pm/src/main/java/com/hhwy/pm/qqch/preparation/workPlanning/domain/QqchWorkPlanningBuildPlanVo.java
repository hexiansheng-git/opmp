package com.hhwy.pm.qqch.preparation.workPlanning.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.utils.validation.ValidationGroups;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.List;

@Data
public class QqchWorkPlanningBuildPlanVo {
    /**
     * 字段描述：版本
     */
    @JsonProperty
    @Excel(name = "版本")
    private BigDecimal version;

    @NotBlank(message = "保存/确认标识不能为空！",groups = ValidationGroups.Save.class)
    private String submitFlag;

    /**
     * 阶段标识（1：第一阶段，2：第二阶段，3：第三阶段）
     */
    @JsonProperty
    private String stageIdentity;

    @JsonProperty
    @Excel(name = "模块标识（页面唯一标识）1： 2： ...")
    private String moduleIdentity;

    private List<QqchWorkPlanningBuildPlan> dataList;
    //菜单id
    private String menuId;
}
