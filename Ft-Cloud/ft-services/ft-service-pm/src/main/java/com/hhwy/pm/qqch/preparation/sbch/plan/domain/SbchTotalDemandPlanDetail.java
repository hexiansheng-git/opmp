package com.hhwy.pm.qqch.preparation.sbch.plan.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.common.core.web.domain.BaseEntity;
import com.hhwy.utils.common.CommonBaseEntity;
import com.hhwy.utils.validation.ValidationGroups;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 设备总部计划总需用详情对象 sbch_total_demand_plan_detail
 * 
 * @author zq
 * @date 2022-11-23
 */
@Data
public class SbchTotalDemandPlanDetail extends CommonBaseEntity {
    private static final long serialVersionUID = 1L;

    /** null */

    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    /** 总部计划主表id（sbch_total_demand_plan表 id） */
    @Excel(name = "总部计划主表id", readConverterExp = "s=bch_total_demand_plan表,i=d")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long planId;

    /** 设备分类 */
    @Excel(name = "设备分类")
//    @NotBlank(message = "设备分类不能为空",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    private String materialType;

    /** 设备编码 */
    @Excel(name = "设备编码")
    @NotBlank(message = "设备编码不能为空",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    private String materialCode;

    /** 设备名称 */
    @Excel(name = "设备名称")
    @NotBlank(message = "设备名称不能为空",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    private String materialName;

    /** 规格型号 */
    @Excel(name = "规格型号")
    private String materialSpec;

    /** 功率（KW） */
    @Excel(name = "功率KW")
    @NotNull(message = "'功率'只能输入数字",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    @Digits(integer = 5, fraction=2, message = "功率格式不正确",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    @DecimalMin(value = "0.00", message = "功率格式不正确",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    private BigDecimal materialPower;

    /** 品牌ids */
    @Excel(name = "品牌ids")
    private String brandIds;

    /** 建议品牌 */
    @Excel(name = "建议品牌")
    private String brandNames;

    /** 单位 */
    @Excel(name = "单位")
//    @NotBlank(message = "单位不能为空",groups = {ValidationGroups.Save.class,ValidationGroups.Update.class})
    private String materialUnit;

    /** 总需求量 */
    @Excel(name = "总需求量")

//    @JsonSerialize(using= ToStringSerializer.class)
    private BigDecimal totalNum;

    /** 内部调拨数量 */
    @Excel(name = "内部调拨数量")

//    @JsonSerialize(using= ToStringSerializer.class)
    private BigDecimal allocateNum;

    /** 国内采购 */
    @Excel(name = "国内采购")

//    @JsonSerialize(using= ToStringSerializer.class)
    private BigDecimal countryBuyNum;

    /** 当地采购数量 */
    @Excel(name = "当地采购数量")

//    @JsonSerialize(using= ToStringSerializer.class)
    private BigDecimal localBuyNum;

    /** 当地租赁数量 */
    @Excel(name = "当地租赁数量")

//    @JsonSerialize(using= ToStringSerializer.class)
    private BigDecimal localLeaseNum;

    /** 协作单位自带 */
    @Excel(name = "协作单位自带")

//    @JsonSerialize(using= ToStringSerializer.class)
    private BigDecimal companySelfNum;

    /** 计划进场时间 */
    @Excel(name = "计划进场时间", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "计划进场时间不能为空",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    private Date planEntryTime;

    /** 计划退场时间 */
    @Excel(name = "计划退场时间", width = 30, dateFormat = "yyyy-MM-dd")
    @NotNull(message = "计划退场时间不能为空",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date planExitTime;

    /** 是否特种设备（0：否，1：是） */
    @Excel(name = "是否特种设备", readConverterExp = "0=：否，1：是")
//    @NotBlank(message = "是否特种设备不能为空",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    private String isSpecial;

    /** 创建人id */
    @Excel(name = "创建人id")

    @JsonSerialize(using= ToStringSerializer.class)
    private String createUser;

    private String createUserName;

    /** 修改人id */
    @Excel(name = "修改人id")

    @JsonSerialize(using= ToStringSerializer.class)
    private String updateUser;

    private String updateUserName;

    /** 修改人id */
    @Excel(name = "修改人id")

    @JsonSerialize(using= ToStringSerializer.class)
    private String delUser;

    /** null */
    @Excel(name = "null", width = 30, dateFormat = "yyyy-MM-dd")
    private Date delTime;

    /** 删除标识：0有效1无效 */
    private String delFlag;

    /** 预留字段1  设备分类名称*/
    @Excel(name = "预留字段1")
    private String ptVar1;

    /** 预留字段2 */
    @Excel(name = "预留字段2")
    private String ptVar2;

    /** pt_var3 */
    @Excel(name = "pt_var3")
    private String ptVar3;

    /** 所属项目 */
    @Excel(name = "所属项目")

    @JsonSerialize(using= ToStringSerializer.class)
    private Long projectId;

    private String prjCode;

    private String projectName;

    /** 区域id */
    @Excel(name = "区域id")

    @JsonSerialize(using= ToStringSerializer.class)
    private Long regionId;

    /** 区域名称 */
    @Excel(name = "区域名称")
    private String regionName;

    /** 部门id */
    @Excel(name = "部门id")

    @JsonSerialize(using= ToStringSerializer.class)
    private Long deptId;

    private List<Long> planIds;

    private List<String> materialCodeList;

    private List<String> projectIds;

    private String projectType;

    //现场设备用 累计购置数量
    private Integer equBuyNum;


    private Integer pageNum;

    private Integer pageSize;

    private BigDecimal version;

    private Long[] ids;
    
    private Integer thirdCountryBuyNum;
}
