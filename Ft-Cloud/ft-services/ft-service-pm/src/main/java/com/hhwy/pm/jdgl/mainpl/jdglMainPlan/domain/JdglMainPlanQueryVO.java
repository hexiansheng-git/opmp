package com.hhwy.pm.jdgl.mainpl.jdglMainPlan.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class JdglMainPlanQueryVO {

    /**
     * 作业编码
     */
    @JsonProperty
    private String itemCode;
    /**
     * 作业名称
     */
    @JsonProperty
    private String itemName;
    /**
     * 查询页签
     */
    @JsonProperty
    private String tabNo;
    /**
     * 责任人
     */
    @JsonProperty
    private String executer;

    /**
     * 开始时间
     */
    @JsonProperty
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    /**
     * 结束时间
     */
    @JsonProperty
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

}
