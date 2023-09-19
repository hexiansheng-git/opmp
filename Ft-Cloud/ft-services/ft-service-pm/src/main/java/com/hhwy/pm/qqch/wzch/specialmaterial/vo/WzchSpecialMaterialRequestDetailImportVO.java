package com.hhwy.pm.qqch.wzch.specialmaterial.vo;

import com.alibaba.excel.annotation.ExcelProperty;

public class WzchSpecialMaterialRequestDetailImportVO {


    /** 发运方案事项 */
    @ExcelProperty(value = "发运方案事项")
    private String despatchPlan;

    /** 要求内容 */
    @ExcelProperty(value = "要求内容")
    private String requestContent;

    /** 实施措施 */
    @ExcelProperty(value = "实施措施")
    private String measures;

    /** 是否预警 1-是 0否 */
    @ExcelProperty(value = "是否预警")
    private String warn;

    @ExcelProperty(value = "备注")
    private  String remark;

    public String getDespatchPlan() {
        return despatchPlan;
    }

    public void setDespatchPlan(String despatchPlan) {
        this.despatchPlan = despatchPlan;
    }

    public String getRequestContent() {
        return requestContent;
    }

    public void setRequestContent(String requestContent) {
        this.requestContent = requestContent;
    }

    public String getMeasures() {
        return measures;
    }

    public void setMeasures(String measures) {
        this.measures = measures;
    }

    public String getWarn() {
        return warn;
    }

    public void setWarn(String warn) {
        this.warn = warn;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
