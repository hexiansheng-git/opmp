package com.hhwy.pm.qqch.sgch.qqchLabourDemandPlan.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.pm.qqch.sgch.qqchconst.domain.QqchConstStaffPlanResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author ldd
 * @date 2023-07-31 16:38:26
 * @remark qqch_labour_demand_plan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QqchLabourDemandPlanResult extends QqchConstStaffPlanResult {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：进场日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "进场日期", dateFormat = "yyyy-MM-dd")
    private Date entryDate;
    /**
     * 字段描述：退场日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "退场日期", dateFormat = "yyyy-MM-dd")
    private Date exitDate;


}
