package com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 功能：作业信息
 * 作者: fushudong
 * 时间: 2023/09/11
 */
@Data
public class ActivityInfo {

    //unique ID
    private String objectId;
    //项目代码
    private String projectId;
    //作业代码
    private String id;
    //作业名称
    private String name;
    //原定工期
    private Integer plannedDuration;
    //尚需工期
    private Integer remainingDuration;
    //计划完成百分比
    private BigDecimal schedulePercentComplete;
    //开始
    private Date startDate;
    //完成
    private Date finishDate;
    //实际开始
    private Date actualStartDate;
    //实际完成
    private Date actualFinishDate;
    //期望完成日期
    private Date expectedFinishDate;
    //滞后天数 （差值-基线项目完成日期）
    private Integer finishDateVariance;
    //总浮时
    private Integer totalFloat;
    //自由浮时
    private Integer freeFloat;
    //通过 WBSObjectId 可以对
    //应 WBS 分类码和名称
    private String wbsObjectId;
    //WBS 分类码
    private String wbsCode;
    //WBS 名称
    private String wbsName;
    //是否关键线路
    private Boolean isCritical;
    // 是否最长线路
    private Boolean isLongestPath;
    private Date lastUpdateDate;

    //作业逻辑关系
    List<PredecessorRelationships> relationships;

    //尚需开始日期
    private Date remainingEarlyStartDate;
    //尚需完成日期
    private Date remainingEarlyFinishDate;
    //基线开始日期
    private Date BaselineStartDate;
    //基线完成日期
    private Date BaselineFinishDate;
    //作业类型
    private String type;

}