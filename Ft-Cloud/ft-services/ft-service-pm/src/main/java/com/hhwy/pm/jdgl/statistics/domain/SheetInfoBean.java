package com.hhwy.pm.jdgl.statistics.domain;

import java.util.List;

public class SheetInfoBean {

    /**
     * sheet页名称
     */
    private String sheetName;

    /**
     * sheet标题bean
     */
    private Class<?> headClass;

    /**
     * sheet页数据
     */
    private List<?> dataList;

    public SheetInfoBean() {
    }

    public SheetInfoBean(String sheetName, Class<?> headClass, List<?> dataList) {
        this.sheetName = sheetName;
        this.headClass = headClass;
        this.dataList = dataList;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public Class<?> getHeadClass() {
        return headClass;
    }

    public void setHeadClass(Class<?> headClass) {
        this.headClass = headClass;
    }

    public List<?> getDataList() {
        return dataList;
    }

    public void setDataList(List<?> dataList) {
        this.dataList = dataList;
    }

    @Override
    public String toString() {
        return "SheetInfoBean{" +
                "sheetName='" + sheetName + '\'' +
                ", headClass=" + headClass +
                ", dataList=" + dataList +
                '}';
    }
}
