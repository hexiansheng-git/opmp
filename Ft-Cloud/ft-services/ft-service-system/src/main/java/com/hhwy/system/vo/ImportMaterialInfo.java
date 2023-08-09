package com.hhwy.system.vo;

import com.hhwy.common.core.annotation.Excel;

public class ImportMaterialInfo {
    @Excel(name = "分类编码")
    private String categoryCode;

    @Excel(name = "排序")
    private Long sort;

    @Excel(name = "配件编码")
    private String materialCode;

    @Excel(name = "配件名称")
    private String materialName;
    //配件号
    @Excel(name = "配件号")
    private String partNo;

    //单位
    @Excel(name = "单位")
    private String unit;
    //所属机型
    @Excel(name = "所属机型")
    private String ownType;
    //所属部位
    @Excel(name = "所属部位")
    private String ownSite;

    @Excel(name = "类型0材料1设备2配件")
    private String type;
    @Excel(name = "备注")
    private String remark;
    @Excel(name = "配件简称")
    private String p1;
    @Excel(name = "规格型号")
    private String p2;
    @Excel(name = "备注1")
    private String p3;

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
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

    public String getPartNo() {
        return partNo;
    }

    public void setPartNo(String partNo) {
        this.partNo = partNo;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getOwnType() {
        return ownType;
    }

    public void setOwnType(String ownType) {
        this.ownType = ownType;
    }

    public String getOwnSite() {
        return ownSite;
    }

    public void setOwnSite(String ownSite) {
        this.ownSite = ownSite;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getP1() {
        return p1;
    }

    public void setP1(String p1) {
        this.p1 = p1;
    }

    public String getP2() {
        return p2;
    }

    public void setP2(String p2) {
        this.p2 = p2;
    }

    public String getP3() {
        return p3;
    }

    public void setP3(String p3) {
        this.p3 = p3;
    }
}
