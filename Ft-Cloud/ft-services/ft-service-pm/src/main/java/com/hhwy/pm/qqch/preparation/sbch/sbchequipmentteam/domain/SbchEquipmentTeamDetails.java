package com.hhwy.pm.qqch.preparation.sbch.sbchequipmentteam.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.utils.common.CommonBaseEntity;
import com.hhwy.utils.validation.ValidationGroups;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 协作单位详情对象 sbch_equipment_team_details
 * 
 * @author hwj
 * @date 2022-11-30
 */
@Data
public class SbchEquipmentTeamDetails extends CommonBaseEntity {
    private static final long serialVersionUID = 1L;

    /** id */

    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    /** 协作单位设备管理主表 sbch_equipment_team */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long mainId;

    /** 项目名称 */
    @Excel(name = "项目名称")
//    @NotBlank(message = "项目名称不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private String projectName;

    /** 协作单位名称 */
    @Excel(name = "协作单位名称")
    @NotBlank(message = "协作单位名称不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private String teamName;

    /** 工作内容 */
    @Excel(name = "工作内容")
    private String workContent;

    /** 负责人 */
    @Excel(name = "负责人")
    private String leader;

    /** 拟进场时间 */
    @Excel(name = "拟进场时间", width = 30, dateFormat = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date enterTime;

    @Excel(name = "备注")
    private String remark;

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

    /** 部门id */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long deptId;

    /** 项目id */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long projectId;

    /** 所属区域id */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long regionId;

    /** 所属区域 */
    private String region;

    /*协作单位设备详情list*/
    private List<SbchEquipmentTeamDetailsDetails> detailsDetailsList = new ArrayList<>();
}
