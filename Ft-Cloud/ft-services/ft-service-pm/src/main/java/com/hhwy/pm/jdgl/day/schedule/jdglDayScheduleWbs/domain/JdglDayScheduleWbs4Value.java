package com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleWbs.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hhwy.common.core.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class JdglDayScheduleWbs4Value extends JdglDayScheduleWbs{

    /**
     * 字段描述：本阶段实际产值
     */
    @JsonProperty
    @Excel(name = "本阶段实际产值")
    private BigDecimal thisValue;

    /**
     * 字段描述：本阶段计划产值
     */
    @JsonProperty
    @Excel(name = "本阶段计划产值")
    private BigDecimal thisPlanValue;

}
