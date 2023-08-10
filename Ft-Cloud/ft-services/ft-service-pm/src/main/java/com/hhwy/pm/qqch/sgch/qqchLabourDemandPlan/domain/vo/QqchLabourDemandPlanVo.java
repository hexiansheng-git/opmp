package com.hhwy.pm.qqch.sgch.qqchLabourDemandPlan.domain.vo;

import com.hhwy.pm.qqch.sgch.qqchLabourDemandPlan.domain.QqchLabourDemandPlan;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QqchLabourDemandPlanVo {
    /**
     * 字段描述：阶段标识（1：第一阶段，2：第二阶段，3：第三阶段）
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
    /**
     * 字段描述：劳动力需求计划
     */
    private List<QqchLabourDemandPlan> qqchLabourDemandPlanList;

    private String startTime;

    private String endTime;
}
