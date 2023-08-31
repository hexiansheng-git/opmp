package com.hhwy.pm.qqch.preparation.sbch.material.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.utils.common.CommonBaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 设备现场管理对象 sbch_material_manager_detail
 * 
 * @author zq
 * @date 2022-12-20
 */
@Data
public class SbchMaterialManagerDetail extends CommonBaseEntity {
    private static final long serialVersionUID = 1L;

    /** null */

    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    /** 主表id */
    @Excel(name = "主表id")

    @JsonSerialize(using= ToStringSerializer.class)
    private Long infoId;

    /** 管控内容 */
    @Excel(name = "管控内容")
    private String controlContent;

    /** 管控目标 */
    @Excel(name = "管控目标")
    private String controlTarget;

    /** 执行措施 */
    @Excel(name = "执行措施")
    private String useMeasures;

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

    /** 区域id */
    @Excel(name = "区域id")

    @JsonSerialize(using= ToStringSerializer.class)
    private Long regionId;

    /** 部门id */
    @Excel(name = "部门id")

    @JsonSerialize(using= ToStringSerializer.class)
    private Long deptId;

}
