package com.hhwy.pm.qqch.wzch.survey.vo;


import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 进出口调查海关详情对象 wzch_import_export_survey_customs
 * 
 * @author mls
 * @date 2022-12-05
 */
public class WzchImportExportSurveyCustomsImportVo {
    private static final long serialVersionUID = 1L;
    

    /** 海关名称 */
    @ExcelProperty(value = "海关名称")
    private String customsName;

    /** 海关概况 */
    @ExcelProperty(value = "海关概况")
    private String customsSurvey;

    /** 港口名称 */
    @ExcelProperty(value = "港口名称")
    private String portName;

    /** 清关公司概况 */
    @ExcelProperty(value = "清关公司概况")
    private String customsCompanySurvey;

    /** 清关政策 */
    @ExcelProperty(value = "清关政策")
    private String customsPolicy;

    /** 相关后续（清关流程及要点） */
    @ExcelProperty(value = "相关后续")
    private String customsFollowProcess;

    /** 关税情况 */
    @ExcelProperty(value = "关税情况")
    private String customsDutyState;

    /** 清关特别注意事项 */
    @ExcelProperty(value = "清关特别注意事项")
    private String customsNote;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getCustomsName() {
        return customsName;
    }

    public void setCustomsName(String customsName) {
        this.customsName = customsName;
    }

    public String getCustomsSurvey() {
        return customsSurvey;
    }

    public void setCustomsSurvey(String customsSurvey) {
        this.customsSurvey = customsSurvey;
    }

    public String getPortName() {
        return portName;
    }

    public void setPortName(String portName) {
        this.portName = portName;
    }

    public String getCustomsCompanySurvey() {
        return customsCompanySurvey;
    }

    public void setCustomsCompanySurvey(String customsCompanySurvey) {
        this.customsCompanySurvey = customsCompanySurvey;
    }

    public String getCustomsPolicy() {
        return customsPolicy;
    }

    public void setCustomsPolicy(String customsPolicy) {
        this.customsPolicy = customsPolicy;
    }

    public String getCustomsFollowProcess() {
        return customsFollowProcess;
    }

    public void setCustomsFollowProcess(String customsFollowProcess) {
        this.customsFollowProcess = customsFollowProcess;
    }

    public String getCustomsDutyState() {
        return customsDutyState;
    }

    public void setCustomsDutyState(String customsDutyState) {
        this.customsDutyState = customsDutyState;
    }

    public String getCustomsNote() {
        return customsNote;
    }

    public void setCustomsNote(String customsNote) {
        this.customsNote = customsNote;
    }
}
