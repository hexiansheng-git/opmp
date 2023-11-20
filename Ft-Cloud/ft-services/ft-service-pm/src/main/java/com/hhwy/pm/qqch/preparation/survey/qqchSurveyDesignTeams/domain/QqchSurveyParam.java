package com.hhwy.pm.qqch.preparation.survey.qqchSurveyDesignTeams.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hhwy.common.core.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * 功能：勘察设计数据同步
 * 作者: fushudong
 * 时间: 2023/10/13
 */
@Data
public class QqchSurveyParam {
    //1.3主表id
    private Long masterId13;

    //2.1.3主表id
    private Long masterId213;

    //班组名称
    private String constDesc;

    //工作内容
    private String workContent;

    //进场时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    private Date entryDate;

    //退场时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    private Date exitDate;
}