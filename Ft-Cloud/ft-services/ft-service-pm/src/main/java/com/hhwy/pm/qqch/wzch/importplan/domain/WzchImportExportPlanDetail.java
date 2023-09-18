package com.hhwy.pm.qqch.wzch.importplan.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 进出口策划详情对象 wzch_import_export_plan_detail
 *         importSurveyCountryDetail
 * @author mls
 * @date 2022-12-05
 */
@Data
public class WzchImportExportPlanDetail extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 进出口策划详情ID */

    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    /** 策划ID */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long planId;

    /** 进口物资分类ID */

    @JsonSerialize(using= ToStringSerializer.class)
    private Long importClassId;

    /** 进口物资分类 */
    @Excel(name = "进口物资分类")
    private String importClass;

    /** 进口主体 */
    @Excel(name = "进口主体")
    private String importMainPart;

    /** 收货人信息 */
    @Excel(name = "收货人信息")
    private String consignee;

    /** 发运港 */
    @Excel(name = "发运港")
    private String despatchHarbor;

    /** 到货港 */
    @Excel(name = "到货港")
    private String arrivalHarbor;

    /** 运输距离 */
    @Excel(name = "运输距离")
    private String transDistance;

    /** 报关价值 */
    @Excel(name = "报关价值")
    private String customsCost;

    /** 报关方式 */
    @Excel(name = "报关方式")
    private String customsMode;

    /** 关税缴纳方式 */
    @Excel(name = "关税缴纳方式")
    private String tariffPaymentMethod;

    /** 税率(%) */
    @Excel(name = "税率(%)")
    private String taxRate;

    /** 采购组织时限 */
    @Excel(name = "采购组织时限")
    private String useTimeLimit;

    /** 备货时限 */
    @Excel(name = "备货时限")
    private String choiceTimeLimit;

    /** 发运时限 */
    @Excel(name = "发运时限")
    private String despatchTimeLimit;

    /** 清关运输时限 */
    @Excel(name = "清关运输时限")
    private String customsTimeLimit;

    /** 特别注意事项 */
    @Excel(name = "特别注意事项")
    private String specialRemarks;

    /** 项目id */

    @JsonSerialize(using= ToStringSerializer.class)
    private Long projectId;

    /** 项目名称 */
    private String projectName;

    /** 部门id */

    @JsonSerialize(using= ToStringSerializer.class)
    private Long deptId;

    /** 是否有效 1-是 0-否 */
    private String valid;

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

    /** 预留字段4 */
    private String ptVar4;

    /** 预留字段5 */
    private String ptVar5;

    private List<String> idList;

    public WzchImportExportPlanDetail(Long planId) {
        this.planId = planId;
    }

    public WzchImportExportPlanDetail() {
    }
}
