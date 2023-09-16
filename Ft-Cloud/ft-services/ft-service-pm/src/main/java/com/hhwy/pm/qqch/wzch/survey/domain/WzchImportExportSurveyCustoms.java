package com.hhwy.pm.qqch.wzch.survey.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 进出口调查海关详情对象 wzch_import_export_survey_customs
 * 
 * @author mls
 * @date 2022-12-05
 */
@Data
public class WzchImportExportSurveyCustoms extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** id */

    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    /** 进出口调查id */

    @JsonSerialize(using= ToStringSerializer.class)
    private Long surveyId;

    /** 海关名称 */
    @Excel(name = "海关名称")
    private String customsName;

    /** 海关名称ID */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long customsId;

    /** 海关概况 */
    @Excel(name = "海关概况")
    private String customsSurvey;

    /** 港口名称 */
    @Excel(name = "港口名称")
    private String portName;

    /** 港口ID */
    private String portId;

    /** 清关公司概况 */
    @Excel(name = "清关公司概况")
    private String customsCompanySurvey;

    /** 清关政策 */
    @Excel(name = "清关政策")
    private String customsPolicy;

    /** 相关后续 */
    @Excel(name = "相关后续")
    private String customsFollowProcess;

    /** 关税情况 */
    @Excel(name = "关税情况")
    private String customsDutyState;

    /** 清关特别注意事项 */
    @Excel(name = "清关特别注意事项")
    private String customsNote;

    /** 附件组id */
    private String fileGroupId;

    /** 部门id */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long deptId;

    /** 数据创建者id */
    private String createUser;

    /** 数据创建者名称 */
    private String createUserName;

    /** 数据修改者id */
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


    public WzchImportExportSurveyCustoms(Long surveyId) {
        this.surveyId = surveyId;
    }

    public WzchImportExportSurveyCustoms() {
    }

}
