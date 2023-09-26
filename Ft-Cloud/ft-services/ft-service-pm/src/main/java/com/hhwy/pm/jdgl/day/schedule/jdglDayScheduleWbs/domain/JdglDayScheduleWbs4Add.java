package com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleWbs.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class JdglDayScheduleWbs4Add {

    @JsonProperty
    private List<JdglDayScheduleWbs> wbsTreeList;

    @JsonProperty
    private List<JdglDayScheduleWbs> addWbsList;

    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long dayScheduleId;

    /**
     * 字段描述：日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "日期", dateFormat = "yyyy-MM-dd")
    private Date date;

}
