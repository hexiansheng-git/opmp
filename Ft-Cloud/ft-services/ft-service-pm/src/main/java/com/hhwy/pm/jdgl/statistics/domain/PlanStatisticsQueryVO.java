package com.hhwy.pm.jdgl.statistics.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hhwy.common.core.annotation.Excel;
import lombok.Data;

import java.util.Date;

@Data
public class PlanStatisticsQueryVO {

    @JsonProperty
    private String queryDateType;
    @JsonProperty
    private String year;
    @JsonProperty
    private String quarter;
    @JsonProperty
    private String month;
    @JsonProperty
    private String week;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    private Date startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    private Date endDate;

}
