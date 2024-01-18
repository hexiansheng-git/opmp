package com.hhwy.pm.qqch.sgch.qqchLabourDemandPlan.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class QqchLabourDemandPlanDto {

    private Long id;

    private Date startTime;
    private Date endTime;

    @JsonFormat(pattern = "yyyy-MM")
    private Date time;

    private BigDecimal num;
    private BigDecimal chinaNum;
    private BigDecimal foreignNum;
}
