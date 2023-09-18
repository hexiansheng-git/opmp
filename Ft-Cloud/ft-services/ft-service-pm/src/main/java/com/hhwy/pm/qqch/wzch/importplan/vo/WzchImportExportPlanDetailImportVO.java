package com.hhwy.pm.qqch.wzch.importplan.vo;

import com.alibaba.excel.annotation.ExcelProperty;

public class WzchImportExportPlanDetailImportVO {

    /** 进口物资分类 */
    @ExcelProperty(value = "进口物资分类")
    private String importClass;

    /** 进口主体 */
    @ExcelProperty(value = "进口主体")
    private String importMainPart;

    /** 收货人信息 */
    @ExcelProperty(value = "收货人信息")
    private String consignee;

    /** 发运港 */
    @ExcelProperty(value = "发运港")
    private String despatchHarbor;

    /** 到货港 */
    @ExcelProperty(value = "到货港")
    private String arrivalHarbor;

    /** 运输距离 */
    @ExcelProperty(value = "运输距离")
    private String transDistance;

    /** 报关价值 */
    @ExcelProperty(value = "报关价值")
    private String customsCost;

    /** 报关方式 */
    @ExcelProperty(value = "报关方式")
    private String customsMode;

    /** 关税缴纳方式 */
    @ExcelProperty(value = "关税缴纳方式")
    private String tariffPaymentMethod;

    /** 税率(%) */
    @ExcelProperty(value = "税率(%)")
    private String taxRate;

    /** 采购组织时限 */
    @ExcelProperty(value = "采购组织时限")
    private String useTimeLimit;

    /** 备货时限 */
    @ExcelProperty(value = "备货时限")
    private String choiceTimeLimit;

    /** 发运时限 */
    @ExcelProperty(value = "发运时限")
    private String despatchTimeLimit;

    /** 清关运输时限 */
    @ExcelProperty(value = "清关运输时限")
    private String customsTimeLimit;

    /** 特别注意事项 */
    @ExcelProperty(value = "特别注意事项")
    private String specialRemarks;

    public String getImportClass() {
        return importClass;
    }

    public void setImportClass(String importClass) {
        this.importClass = importClass;
    }

    public String getImportMainPart() {
        return importMainPart;
    }

    public void setImportMainPart(String importMainPart) {
        this.importMainPart = importMainPart;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getDespatchHarbor() {
        return despatchHarbor;
    }

    public void setDespatchHarbor(String despatchHarbor) {
        this.despatchHarbor = despatchHarbor;
    }

    public String getArrivalHarbor() {
        return arrivalHarbor;
    }

    public void setArrivalHarbor(String arrivalHarbor) {
        this.arrivalHarbor = arrivalHarbor;
    }

    public String getTransDistance() {
        return transDistance;
    }

    public void setTransDistance(String transDistance) {
        this.transDistance = transDistance;
    }

    public String getCustomsCost() {
        return customsCost;
    }

    public void setCustomsCost(String customsCost) {
        this.customsCost = customsCost;
    }

    public String getCustomsMode() {
        return customsMode;
    }

    public void setCustomsMode(String customsMode) {
        this.customsMode = customsMode;
    }

    public String getTariffPaymentMethod() {
        return tariffPaymentMethod;
    }

    public void setTariffPaymentMethod(String tariffPaymentMethod) {
        this.tariffPaymentMethod = tariffPaymentMethod;
    }

    public String getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(String taxRate) {
        this.taxRate = taxRate;
    }

    public String getUseTimeLimit() {
        return useTimeLimit;
    }

    public void setUseTimeLimit(String useTimeLimit) {
        this.useTimeLimit = useTimeLimit;
    }

    public String getChoiceTimeLimit() {
        return choiceTimeLimit;
    }

    public void setChoiceTimeLimit(String choiceTimeLimit) {
        this.choiceTimeLimit = choiceTimeLimit;
    }

    public String getDespatchTimeLimit() {
        return despatchTimeLimit;
    }

    public void setDespatchTimeLimit(String despatchTimeLimit) {
        this.despatchTimeLimit = despatchTimeLimit;
    }

    public String getCustomsTimeLimit() {
        return customsTimeLimit;
    }

    public void setCustomsTimeLimit(String customsTimeLimit) {
        this.customsTimeLimit = customsTimeLimit;
    }

    public String getSpecialRemarks() {
        return specialRemarks;
    }

    public void setSpecialRemarks(String specialRemarks) {
        this.specialRemarks = specialRemarks;
    }
}
