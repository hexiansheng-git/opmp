package com.hhwy.domain.base.system.jobKind;

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
 * info对象 t_job_kind
 * 
 * @author lcf
 * @date 2022-11-24
 */
@Data
public class JobKind extends CommonBaseEntity {
    private static final long serialVersionUID = 1L;

    /** id */
    @JsonSerialize(using= ToStringSerializer.class)
    @NotNull(message = "id不能为空",groups = {ValidationGroups.Update.class})
    private Long id;

    /** 工种名称 */
    @Excel(name = "工种名称")
    @NotBlank(message = "工种名称不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private String jobName;

    /** 币种编码 */
    @Excel(name = "工种编码")
    @NotBlank(message = "工种编码不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private String jobCode;

    /** 排序 */
    @Excel(name = "排序")
    @JsonSerialize(using= ToStringSerializer.class)
    @NotNull(message = "排序不能为空",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    private Long sort;

    /** 数据创建者 */
    @Excel(name = "数据创建者")
    private String createUser;

    /** 数据修改者 */
    @Excel(name = "数据修改者")
    private String updateUser;

    /** 数据删除者 */
    @Excel(name = "数据删除者")
    private String delUser;

    /** 数据删除系统时间 */
    @Excel(name = "数据删除系统时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date delTime;

    /** 删除标识：0有效1无效 */
    private String delFlag;

    /** 预留字段1 */
    @Excel(name = "预留字段1")
    private String ptVar1;

    /** 预留字段2 */
    @Excel(name = "预留字段2")
    private String ptVar2;

    /** 预留字段3 */
    @Excel(name = "预留字段3")
    private String ptVar3;

    @Excel(name = "备注")
    private String remark;

}
