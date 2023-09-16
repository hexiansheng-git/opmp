package com.hhwy.pm.qqch.wzch.specialmaterial.vo;

import com.alibaba.excel.annotation.ExcelProperty;


public class WzchSpecialMaterialPlanDetailImportVO {

    /** 运输方式 */
    @ExcelProperty(value = "运输方式")
    private String transportMode;

    /** 物资编码 */
    @ExcelProperty(value = "物资编码")
    private String materialCode;

    @ExcelProperty(value = "物资名称")
    private String materialName;

    @ExcelProperty(value = "备注")
    private String remark;

    public String getTransportMode() {
        return transportMode;
    }

    public void setTransportMode(String transportMode) {
        this.transportMode = transportMode;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
