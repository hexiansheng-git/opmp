package com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleWbs.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

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

}
