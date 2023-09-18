package com.hhwy.pm.qqch.wzch.survey.vo;

import com.alibaba.excel.annotation.ExcelProperty;


public class WzchImportExportSurveyCountryImportVo {

    /** 国家名称 */
    @ExcelProperty(value = "国家名称")
    private String countryName;

    /** 语言名称 */
    @ExcelProperty(value = "国家语言")
    private String language;

    /** 币种 */
    @ExcelProperty(value = "币种")
    private String currency;

    /** 自然环境与当地风俗 */
    @ExcelProperty(value = "自然环境与当地风俗")
    private String wildCustom;

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getWildCustom() {
        return wildCustom;
    }

    public void setWildCustom(String wildCustom) {
        this.wildCustom = wildCustom;
    }
}
