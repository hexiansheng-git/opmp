package com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleWbs.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hhwy.common.core.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class JdglDayScheduleWbs4Value extends JdglDayScheduleWbs{

    /**
     * 字段描述：本日产值
     */
    @JsonProperty
    @Excel(name = "本日产值")
    private BigDecimal thisValue;

}
