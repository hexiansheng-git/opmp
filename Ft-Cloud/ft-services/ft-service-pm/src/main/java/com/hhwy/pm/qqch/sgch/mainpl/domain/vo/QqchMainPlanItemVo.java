package com.hhwy.pm.qqch.sgch.mainpl.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.sgch.mainpl.domain.QqchMainPlanItem;
import lombok.Data;

import java.util.List;

@Data
public class QqchMainPlanItemVo extends PreparationEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 施工策划-总体进度计划集合
     */
    List<QqchMainPlanItem> qqchMainPlanItemList;
}
