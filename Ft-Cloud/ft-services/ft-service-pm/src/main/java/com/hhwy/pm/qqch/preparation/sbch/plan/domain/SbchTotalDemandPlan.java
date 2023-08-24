package com.hhwy.pm.qqch.preparation.sbch.plan.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.common.core.web.domain.BaseEntity;
import com.hhwy.utils.common.MyPrepareBaseEntity;
import com.hhwy.utils.validation.ValidationGroups;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 设备总部计划对象 sbch_total_demand_plan
 * 
 * @author zq
 * @date 2022-11-23
 */
@Data
public class SbchTotalDemandPlan extends MyPrepareBaseEntity {
    private static final long serialVersionUID = 1L;

    /** null */

    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    /** 单据编码 */
    @Excel(name = "单据编码")
    private String formNo;

    /** 标题 */
    @Excel(name = "标题")
    private String titleName;

    /** 版本号 */
    @Excel(name = "版本号")
    private BigDecimal versionNo;

    @Excel(name = "是否有效")
    private String isValid;

    /** 创建人id */
    @JsonSerialize(using= ToStringSerializer.class)
    private String createUser;
    @Excel(name = "创建人")
    private String createUserName;
    private String updateUserName;

    /** 修改人id */
    @JsonSerialize(using= ToStringSerializer.class)
    private String updateUser;

    /** null */
    private Date updateTime;

    /** 修改人id */

    @JsonSerialize(using= ToStringSerializer.class)
    private String delUser;

    /** null */
    private Date delTime;

    /** 删除标识：0有效1无效 */
    private String delFlag;

    /** 附件组id */
    private String fileGroupId;

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

    @Excel(name = "所属项目")
    @NotBlank(message = "所属项目名称不能为空",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    private String projectName;

    /** 区域id */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long regionId;

    /** 区域名称 */
    @Excel(name = "区域名称")
    private String regionName;

    /** 部门id */
    @JsonSerialize(using= ToStringSerializer.class)
    @NotNull(message = "所属部门不能为空",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    private Long deptId;

    private List<SbchTotalDemandPlanDetail> planDetailList;

    private String[] ids;
}
