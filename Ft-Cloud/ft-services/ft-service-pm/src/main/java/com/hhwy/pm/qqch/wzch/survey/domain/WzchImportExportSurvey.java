package com.hhwy.pm.qqch.wzch.survey.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.utils.common.MyPrepareBaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 进出口调查对象 wzch_import_export_survey
 * 
 * @author mls
 * @date 2022-12-05
 */
@Data
public class WzchImportExportSurvey extends MyPrepareBaseEntity {
    private static final long serialVersionUID = 1L;

    public WzchImportExportSurvey(BigDecimal version) {
        super.setVersion(version);
    }
    public WzchImportExportSurvey() {
    }

    /** id */

    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    /** 单据编号 */
    @Excel(name = "单据编号")
    private String surveyCode;

    /** 标题 */
    @Excel(name = "标题")
    private String title;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String projectName;

    /** 所属区域id */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long regionId;

    /** 所属区域 */
    @Excel(name = "所属区域")
    private String regionName;

    /** 数据创建者名称 */
    @Excel(name = "编制人")
    private String createUserName;

    /** 附件组id */
    private String fileGroupId;

    /** 项目id */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long projectId;



    /** 部门id */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long deptId;

    /** 数据创建者id */
    private String createUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @Excel(name = "编制时间", dateFormat = "yyyy-MM-dd HH:mm")
    private Date createTime;

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

    private List<WzchImportExportSurveyCountry> wzchImportExportSurveyCountryList;

    private List<WzchImportExportSurveyCustoms> wzchImportExportSurveyCustomsList;

    /** 预留字段1 */
    private String ptVar1;

    /** 预留字段2 */
    private String ptVar2;

    /** 预留字段3 */
    private String ptVar3;

    /** 预留字段4 */
    private String ptVar4;

    /** 预留字段5 */
    private String ptVar5;


}
