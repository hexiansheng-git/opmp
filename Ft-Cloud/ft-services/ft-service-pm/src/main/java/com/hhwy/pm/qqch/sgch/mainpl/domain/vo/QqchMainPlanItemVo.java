package com.hhwy.pm.qqch.sgch.mainpl.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.sgch.mainpl.domain.QqchMainPlanItem;
import com.hhwy.utils.validation.ValidationGroups;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Data
public class QqchMainPlanItemVo {

    private static final long serialVersionUID = 1L;



    /**
     * 字段描述：阶段标识
     */
    private String stageIdentity;
    /**
     * 字段描述：版本
     */
    private BigDecimal version;
    /**
     * 字段描述：菜单id
     */
    private String menuId;
    /**
     * 字段描述：按钮标识（0：保存，1：确认，2：提交）
     */
    private String buttonMark;

    @JsonProperty
    private String moduleIdentity;

    /**
     * 施工策划-总体进度计划集合
     */
    List<QqchMainPlanItem> qqchMainPlanItemList;
}
