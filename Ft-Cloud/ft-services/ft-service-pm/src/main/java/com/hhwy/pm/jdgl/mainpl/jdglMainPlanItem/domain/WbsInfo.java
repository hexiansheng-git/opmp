package com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.domain;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 功能：wbs信息
 * 作者: fushudong
 * 时间: 2023/09/11
 */
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

    public Date getSummaryRemainingStartDate() {
        return summaryRemainingStartDate;
    }

    public void setSummaryRemainingStartDate(Date summaryRemainingStartDate) {
        this.summaryRemainingStartDate = summaryRemainingStartDate;
    }

    public Date getSummaryRemainingFinishDate() {
        return summaryRemainingFinishDate;
    }

    public void setSummaryRemainingFinishDate(Date summaryRemainingFinishDate) {
        this.summaryRemainingFinishDate = summaryRemainingFinishDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentObjectId() {
        return parentObjectId;
    }

    public void setParentObjectId(String parentObjectId) {
        this.parentObjectId = parentObjectId;
    }

    public Integer getSummaryPlannedDuration() {
        return summaryPlannedDuration;
    }

    public void setSummaryPlannedDuration(Integer summaryPlannedDuration) {
        this.summaryPlannedDuration = summaryPlannedDuration;
    }

    public Integer getSummaryRemainingDuration() {
        return summaryRemainingDuration;
    }

    public void setSummaryRemainingDuration(Integer summaryRemainingDuration) {
        this.summaryRemainingDuration = summaryRemainingDuration;
    }

    public BigDecimal getSummarySchedulePercentComplete() {
        return summarySchedulePercentComplete;
    }

    public void setSummarySchedulePercentComplete(BigDecimal summarySchedulePercentComplete) {
        this.summarySchedulePercentComplete = summarySchedulePercentComplete;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public Date getSummaryActualStartDate() {
        return summaryActualStartDate;
    }

    public void setSummaryActualStartDate(Date summaryActualStartDate) {
        this.summaryActualStartDate = summaryActualStartDate;
    }

    public Date getSummaryActualFinishDate() {
        return summaryActualFinishDate;
    }

    public void setSummaryActualFinishDate(Date summaryActualFinishDate) {
        this.summaryActualFinishDate = summaryActualFinishDate;
    }

    public Date getSummaryBaselineStartDate() {
        return summaryBaselineStartDate;
    }

    public void setSummaryBaselineStartDate(Date summaryBaselineStartDate) {
        this.summaryBaselineStartDate = summaryBaselineStartDate;
    }

    public Date getSummaryBaselineFinishDate() {
        return summaryBaselineFinishDate;
    }

    public void setSummaryBaselineFinishDate(Date summaryBaselineFinishDate) {
        this.summaryBaselineFinishDate = summaryBaselineFinishDate;
    }

    public Date getSummaryProgressFinishDate() {
        return summaryProgressFinishDate;
    }

    public void setSummaryProgressFinishDate(Date summaryProgressFinishDate) {
        this.summaryProgressFinishDate = summaryProgressFinishDate;
    }

    public Integer getSummaryFinishDateVariance() {
        return summaryFinishDateVariance;
    }

    public void setSummaryFinishDateVariance(Integer summaryFinishDateVariance) {
        this.summaryFinishDateVariance = summaryFinishDateVariance;
    }

    public Integer getSummaryTotalFloat() {
        return summaryTotalFloat;
    }

    public void setSummaryTotalFloat(Integer summaryTotalFloat) {
        this.summaryTotalFloat = summaryTotalFloat;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }
}