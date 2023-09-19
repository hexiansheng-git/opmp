package com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.domain;


import java.math.BigDecimal;
import java.util.Date;

/**
 * 功能：作业自定义字段实体
 * 作者: fushudong
 * 时间: 2023/09/18
 */
public class ActivityConstField extends  ActivityInfo{


    //作业代码
    private String activityCode;
    //作业名称
    private String activityName;


    /**
     * 字段描述：滞后原因
     */
    private String lagReason;

    /**
     * 字段描述：纠偏目标
     */
    private String correctionTarget;

    /**
     * 字段描述：具体措施
     */
    private String concreteMeasure;
    /**
     * 字段描述：纠偏完成日期
     */
    private Date correctionCompDate;

    /**
     * 字段描述：负责人
     */
    private String executer;

    /**
     * 字段描述：单位
     */
    private String unit;

    /**
     * 字段描述：工程量
     */
    private BigDecimal quantity;


    public String getActivityCode() {
        return activityCode;
    }

    public void setActivityCode(String activityCode) {
        this.activityCode = activityCode;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getLagReason() {
        return lagReason;
    }

    public void setLagReason(String lagReason) {
        this.lagReason = lagReason;
    }

    public String getCorrectionTarget() {
        return correctionTarget;
    }

    public void setCorrectionTarget(String correctionTarget) {
        this.correctionTarget = correctionTarget;
    }

    public String getConcreteMeasure() {
        return concreteMeasure;
    }

    public void setConcreteMeasure(String concreteMeasure) {
        this.concreteMeasure = concreteMeasure;
    }

    public Date getCorrectionCompDate() {
        return correctionCompDate;
    }

    public void setCorrectionCompDate(Date correctionCompDate) {
        this.correctionCompDate = correctionCompDate;
    }

    public String getExecuter() {
        return executer;
    }

    public void setExecuter(String executer) {
        this.executer = executer;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }
}