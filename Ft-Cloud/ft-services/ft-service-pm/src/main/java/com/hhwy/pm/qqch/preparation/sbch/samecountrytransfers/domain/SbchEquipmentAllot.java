package com.hhwy.pm.qqch.preparation.sbch.samecountrytransfers.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.utils.common.MyPrepareBaseEntity;
import com.hhwy.utils.validation.ValidationGroups;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 同国别设备对象 sbch_equipment_allot
 * 
 * @author hwj
 * @date 2022-11-25
 */
@Data
public class SbchEquipmentAllot extends MyPrepareBaseEntity {
    private static final long serialVersionUID = 1L;

    /** id */

    @JsonSerialize(using= ToStringSerializer.class)
    @NotNull(message = "主键id不能为空",groups = {ValidationGroups.Update.class})
    private Long id;

    /** 单据编号 */
    //@PmsExcel(name = "表单编码")
    @NotBlank(message = "单据编号不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private String adjustCode;

    /** 标题 */
    //@PmsExcel(name = "标题")
    @NotBlank(message = "标题不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private String title;

    /** 项目id */
    @JsonSerialize(using= ToStringSerializer.class)
    @NotNull(message = "项目id不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private Long projectId;

    /** 项目名称 */
    //@PmsExcel(name = "所属项目")
    @NotBlank(message = "项目名称不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private String projectName;

    /** 所属区域id */
    @JsonSerialize(using= ToStringSerializer.class)
    @NotNull(message = "所属区域id能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private Long regionId;

    /** 所属区域 */
    //@PmsExcel(name = "所属区域")
    @NotBlank(message = "所属区域为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private String region;

    /** 版本号 */
    //@PmsExcel(name = "版本号")
    @NotNull(message = "版本号",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private BigDecimal versionCode;

    /** 是否有效 1-有效 0-失效 */
    //@PmsExcel(name = "是否有效",readConverterExp = "0=否,1=是")
    private String valid;


    /** 备注/描述 */
    private String remark;

    /** 附件组id */
    private String fileGroupId;

    /** 部门id */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long deptId;

    /** 数据创建者id */
    private String createUser;

    /** 数据创建者名称 */
    //@PmsExcel(name = "编制人")
    private String createUserName;

    //@PmsExcel(name = "编制时间", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private Date createTime;

    //@PmsExcel(name = "流程状态",dictType = "task_status")
    private String taskStatus;

    //@PmsExcel(name = "当前处理人")
    private String processTaskMan;

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

    private String[] ids;
}
