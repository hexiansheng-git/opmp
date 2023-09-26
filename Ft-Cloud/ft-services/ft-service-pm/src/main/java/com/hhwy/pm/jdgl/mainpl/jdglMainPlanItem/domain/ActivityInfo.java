package com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 功能：作业信息
 * 作者: fushudong
 * 时间: 2023/09/11
 */
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

    public Date getRemainingEarlyStartDate() {
        return remainingEarlyStartDate;
    }

    public void setRemainingEarlyStartDate(Date remainingEarlyStartDate) {
        this.remainingEarlyStartDate = remainingEarlyStartDate;
    }

    public Date getRemainingEarlyFinishDate() {
        return remainingEarlyFinishDate;
    }

    public void setRemainingEarlyFinishDate(Date remainingEarlyFinishDate) {
        this.remainingEarlyFinishDate = remainingEarlyFinishDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<PredecessorRelationships> getRelationships() {
        return relationships;
    }

    public void setRelationships(List<PredecessorRelationships> relationships) {
        this.relationships = relationships;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPlannedDuration() {
        return plannedDuration;
    }

    public void setPlannedDuration(Integer plannedDuration) {
        this.plannedDuration = plannedDuration;
    }

    public Integer getRemainingDuration() {
        return remainingDuration;
    }

    public void setRemainingDuration(Integer remainingDuration) {
        this.remainingDuration = remainingDuration;
    }

    public BigDecimal getSchedulePercentComplete() {
        return schedulePercentComplete;
    }

    public void setSchedulePercentComplete(BigDecimal schedulePercentComplete) {
        this.schedulePercentComplete = schedulePercentComplete;
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

    public Date getActualStartDate() {
        return actualStartDate;
    }

    public void setActualStartDate(Date actualStartDate) {
        this.actualStartDate = actualStartDate;
    }

    public Date getActualFinishDate() {
        return actualFinishDate;
    }

    public void setActualFinishDate(Date actualFinishDate) {
        this.actualFinishDate = actualFinishDate;
    }

    public Date getExpectedFinishDate() {
        return expectedFinishDate;
    }

    public void setExpectedFinishDate(Date expectedFinishDate) {
        this.expectedFinishDate = expectedFinishDate;
    }

    public Integer getFinishDateVariance() {
        return finishDateVariance;
    }

    public void setFinishDateVariance(Integer finishDateVariance) {
        this.finishDateVariance = finishDateVariance;
    }

    public Integer getTotalFloat() {
        return totalFloat;
    }

    public void setTotalFloat(Integer totalFloat) {
        this.totalFloat = totalFloat;
    }

    public Integer getFreeFloat() {
        return freeFloat;
    }

    public void setFreeFloat(Integer freeFloat) {
        this.freeFloat = freeFloat;
    }

    public String getWbsObjectId() {
        return wbsObjectId;
    }

    public void setWbsObjectId(String wbsObjectId) {
        this.wbsObjectId = wbsObjectId;
    }

    public String getWbsCode() {
        return wbsCode;
    }

    public void setWbsCode(String wbsCode) {
        this.wbsCode = wbsCode;
    }

    public String getWbsName() {
        return wbsName;
    }

    public void setWbsName(String wbsName) {
        this.wbsName = wbsName;
    }

    public Boolean getCritical() {
        return isCritical;
    }

    public void setCritical(Boolean critical) {
        isCritical = critical;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Date getBaselineStartDate() {
        return BaselineStartDate;
    }

    public void setBaselineStartDate(Date baselineStartDate) {
        BaselineStartDate = baselineStartDate;
    }

    public Date getBaselineFinishDate() {
        return BaselineFinishDate;
    }

    public void setBaselineFinishDate(Date baselineFinishDate) {
        BaselineFinishDate = baselineFinishDate;
    }
}