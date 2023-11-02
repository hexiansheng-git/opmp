package com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 功能：wbs信息
 * 作者: fushudong
 * 时间: 2023/09/11
 */
@Data
public class WbsInfo {

    //unique ID
    private String id;
    //WBS 分类码
    private String code;
    //WBS 名称
    private String name;
    //WBS 父级编码
    private String parentObjectId;
    //原定工期
    private Integer summaryPlannedDuration;
    //尚需工期
    private Integer summaryRemainingDuration;
    //计划完成百分比
    private BigDecimal summarySchedulePercentComplete;
    //开始
    private Date startDate;
    //完成
    private Date finishDate;
    //实际开始
    private Date summaryActualStartDate;
    //实际完成
    private Date summaryActualFinishDate;
    //基线项目开始
    private Date summaryBaselineStartDate;
    //基线项目完成
    private Date summaryBaselineFinishDate;
    //期望完成日期
    private Date summaryProgressFinishDate;
    //滞后天数（差值-基线项目完成日期）
    private Integer summaryFinishDateVariance;
    //总浮时
    private Integer summaryTotalFloat;
    //最后更新日期
    private Date lastUpdateDate;
    //尚需开始日期
    private Date summaryRemainingStartDate;
    //尚需完成日期
    private Date summaryRemainingFinishDate;
    //单位
    private String unit;
    //工程量
    private BigDecimal quantity;
}