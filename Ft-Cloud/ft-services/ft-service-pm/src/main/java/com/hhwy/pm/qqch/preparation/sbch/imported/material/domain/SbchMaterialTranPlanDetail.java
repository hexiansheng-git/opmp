package com.hhwy.pm.qqch.preparation.sbch.imported.material.domain;

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
import java.util.Date;

/**
 * 大型成套设备运输方案详情对象 sbch_material_tran_plan_detail
 * 
 * @author zq
 * @date 2022-12-12
 */
@Data
public class SbchMaterialTranPlanDetail extends CommonBaseEntity {
    private static final long serialVersionUID = 1L;

    /** null */

    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    /** 主表id(sbch_material_tran_plan) */
    @NotNull(message = "主表id不能为空", groups = {ValidationGroups.Save.class})
    @JsonSerialize(using= ToStringSerializer.class)
    private Long planId;

    /** 设备编码 */
    @Excel(name = "设备编码")
    @NotBlank(message = "设备编码不能为空",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    private String materialCode;

    /** 生产厂家 */
    @Excel(name = "生产厂家")
    @NotBlank(message = "设生产厂家不能为空",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    private String manufacturer;

    /** 设备尺寸 */
    @Excel(name = "设备尺寸")
    @NotBlank(message = "设备尺寸不能为空",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    private String materialSize;

    /** 设备重量 */
    @Excel(name = "设备重量")
    @NotBlank(message = "设备重量不能为空",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    private String materialWeight;

    /** 集装箱类型（字典项 box_type） */
    @Excel(name = "散货/集装箱")
    @NotBlank(message = "散货/集装箱不能为空",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    private String boxType;

    /** 集装箱size（20尺/40尺） */
    private String boxSize;

    /** 集装箱数量 */
    @Excel(name = "20尺集装箱数量")
    private Integer boxNum1;

    @Excel(name = "40尺集装箱数量")
    private Integer boxNum2;

    /** 散装方量 */
    @Excel(name = "散装方量")
    private String bulkVolume;

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
    private Long projectId;

    /** 项目名称 */
    @Excel(name = "项目名称")
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

    @Excel(name = "运输时长（天）")
    @NotNull(message = "'运输时长'只能输入数字",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    @Digits(integer = 10, fraction=0, message = "运输时长格式不正确",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    @DecimalMin(value = "0", message = "运输时长格式不正确",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    private Integer trainTime;

    @Excel(name = "起运地")
    @NotBlank(message = "起运地不能为空",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    private String startCustom;

    @Excel(name = "目的地")
    @NotBlank(message = "目的地不能为空",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    private String endCustom;

    @Excel(name = "运输方式")
    @NotBlank(message = "运输方式不能为空",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    private String trainWay;

    //运输方案
    private String fileGroupId;

    //设备名称
    private String materialName;
    //设备规格型号
    private String materialSpec;
}
