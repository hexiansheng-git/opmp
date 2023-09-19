package com.hhwy.pm.qqch.wzch.survey.domain;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 进出口调查国家详情对象 wzch_import_export_survey_country
 * 
 * @author mls
 * @date 2022-12-05
 */
@Data
public class WzchImportExportSurveyCountry extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** id */
    @ExcelIgnore
    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    /** 进出口调查id */
    @ExcelIgnore
    @JsonSerialize(using= ToStringSerializer.class)
    private Long surveyId;

    /** 国家名称 */
    @Excel(name = "国家名称")
    private String countryName;

    /** 语言名称 */
    @Excel(name = "国家语言")
    private String language;

    /** 币种 */
    @Excel(name = "币种")
    private String currency;

    /** 自然环境与当地风俗 */
    @Excel(name = "自然环境与当地风俗")
    private String wildCustom;

    /** 部门id */
    @JsonSerialize(using= ToStringSerializer.class)
    @ExcelIgnore
    private Long deptId;

    /** 数据创建者id */
    @ExcelIgnore
    private String createUser;

    /** 数据创建者名称 */
    private String createUserName;

    /** 数据修改者id */
    @ExcelIgnore
    private String updateUser;

    /** 数据修改者名称 */
    private String updateUserName;

    /** 数据删除者 */
    private String delUser;

    /** 数据删除系统时间 */
    private Date delTime;

    /** 删除标识：0有效1无效 */
    private String delFlag;

    /** 预留字段1 */
    private String ptVar1;

    /** 预留字段2 */
    private String ptVar2;

    /** 预留字段3 */
    private String ptVar3;

    public WzchImportExportSurveyCountry(Long surveyId) {
        this.surveyId = surveyId;
    }

    public WzchImportExportSurveyCountry() {
    }
}
