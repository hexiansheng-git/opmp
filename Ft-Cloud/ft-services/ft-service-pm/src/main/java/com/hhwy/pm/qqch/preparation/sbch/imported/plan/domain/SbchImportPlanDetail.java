package com.hhwy.pm.qqch.preparation.sbch.imported.plan.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.utils.common.CommonBaseEntity;
import com.hhwy.utils.validation.ValidationGroups;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 进口方案详情对象 sbch_import_plan_detail
 * 
 * @author zq
 * @date 2022-12-06
 */
@Data
public class SbchImportPlanDetail extends CommonBaseEntity {
    private static final long serialVersionUID = 1L;

    /** null */

    @JsonSerialize(using= ToStringSerializer.class)
    @NotNull(message = "id不能为空", groups = {ValidationGroups.Update.class})
    private Long id;

    /** 主表id sbch_import_plan */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long planId;

    /** 项目编码 */
    @Excel(name = "项目编码")
    @NotBlank(message = "项目编码不能为空",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    private String projectCodes;

    /** 进口方式 */
    @Excel(name = "进口方式")
    @NotBlank(message = "进口方式不能为空",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    private String importWay;

    /** 收货人信息 */
    @Excel(name = "收货人信息")
    @NotBlank(message = "收货人信息不能为空",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    private String receiveStaff;

    /** 设备编码 */
    @Excel(name = "设备编码")
    @NotBlank(message = "设备编码不能为空",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    private String materialCodes;

    /** 发运港 */
    @Excel(name = "发运港")
    @NotBlank(message = "发运港不能为空",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    private String sendCustoms;

    /** 目的港 */
    @Excel(name = "目的港")
    @NotBlank(message = "目的港不能为空",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    private String goalCuntoms;

    /** null */
    @Excel(name = "报关价值")
    @NotBlank(message = "报关价值不能为空",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    private String cuntomsValue;

    /** 关税缴纳方式 */
    @Excel(name = "关税缴纳方式")
    @NotBlank(message = "关税缴纳方式不能为空",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    private String taxWay;

    /** 税率 */
    @Excel(name = "税率%")
    @NotNull(message = "'税率'只能输入数字",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    @Digits(integer = 3, fraction=2, message = "税率格式不正确",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    @DecimalMin(value = "0.00", message = "税率格式不正确",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    private BigDecimal taxPoint;

    /** 创建人id */
    private String createUser;

    /** 修改人id */
    private String updateUser;

    /** 修改人id */
    private String delUser;

    /** null */
    private Date delTime;

    /** 删除标识：0有效1无效 */
    private String delFlag;

    /** 预留字段1 */
    private String ptVar1;

    /** 预留字段2 */
    private String ptVar2;

    /** pt_var3 */
    private String ptVar3;

    /** 所属项目 */
    @JsonSerialize(using= ToStringSerializer.class)
    @NotNull(message = "所属项目不能为空",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    private Long projectId;

    /** 项目名称 */
    @Excel(name = "项目名称")
    @NotBlank(message = "项目名称方式不能为空",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    private String projectName;

    /** 区域id */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long regionId;

    /** 区域名称 */
    @Excel(name = "区域名称")
    private String regionName;

    /** 部门id */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long deptId;
}
