package com.hhwy.pm.qqch.preparation.sbch.staffing.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.utils.common.CommonBaseEntity;
import com.hhwy.utils.validation.ValidationGroups;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 设备人员配置策划--设备人员详情对象 sbch_staffing_detail
 * 
 * @author zq
 * @date 2022-11-28
 */
@Data
public class SbchStaffingDetail extends CommonBaseEntity {
    private static final long serialVersionUID = 1L;

    /** null */

    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    /** 主表id(sbch_staffing_info) */
    @Excel(name = "主表id(sbch_staffing_info)")
    @JsonSerialize(using= ToStringSerializer.class)
    @NotNull(message = "主表id不能为空", groups = {ValidationGroups.Save.class})
    private Long staffingId;

    /** 总需计划id（sbch_total_demand_plan） */
    @Excel(name = "总需计划id", readConverterExp = "s=bch_total_demand_plan")
    @NotNull(message = "总需计划主表id不能为空", groups = {ValidationGroups.Save.class})
    @JsonSerialize(using= ToStringSerializer.class)
    private Long planId;

    /** sbch_total_demand_plan_detail id */
    @Excel(name = "sbch_total_demand_plan_detail id")
    @NotNull(message = "总需计划子表id不能为空", groups = {ValidationGroups.Save.class})
    @JsonSerialize(using= ToStringSerializer.class)
    private Long planDetailId;

    /** 设备名称 */
    @Excel(name = "设备名称")
    @NotBlank(message = "设备名称不能为空",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    private String materialName;

    /** 设备编码 */
    @Excel(name = "设备编码")
    @NotBlank(message = "设备编码不能为空",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    private String materialCode;

    /** 岗位名称 */
    @Excel(name = "岗位名称")
    @NotBlank(message = "岗位名称不能为空",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    private String postName;

    /** 工种 */
    @Excel(name = "工种")
    @NotBlank(message = "工种不能为空",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    private String workType;

    /** 人员姓名 */
    @Excel(name = "人员姓名")
    private String staffName;

    /** 任职条件 */
    @Excel(name = "任职条件")
    @NotBlank(message = "任职条件不能为空",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    private String qualification;

    /** 资格证书 */
    @Excel(name = "资格证书")
    private String qualiGroupId;

    /** 工作经历 */
    @Excel(name = "工作经历")
    private String workGroupId;

    /** 字典项 is_satisfy 是否满足岗位要求 */
    @Excel(name = "字典项 is_satisfy 是否满足岗位要求")
    @NotBlank(message = "是否满足岗位要求不能为空",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    private String isSatisfy;

    /** 计划到岗日期 */
    @Excel(name = "计划到岗日期", width = 30, dateFormat = "yyyy-MM-dd")
    @NotNull(message = "计划到岗日期不能为空",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date planComingDate;

    /** 创建人id */
    @Excel(name = "创建人id")
    private String createUser;

    /** 修改人id */
    @Excel(name = "修改人id")
    private String updateUser;

    /** 修改人id */
    @Excel(name = "修改人id")
    private String delUser;

    /** null */
    @Excel(name = "null", width = 30, dateFormat = "yyyy-MM-dd")
    private Date delTime;

    /** 删除标识：0有效1无效 */
    private String delFlag;

    /** 预留字段1 */
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

    /** 项目名称 */
    @Excel(name = "项目名称")
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
}
