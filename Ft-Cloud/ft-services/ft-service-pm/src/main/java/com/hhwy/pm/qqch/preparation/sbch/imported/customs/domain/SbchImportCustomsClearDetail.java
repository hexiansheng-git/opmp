package com.hhwy.pm.qqch.preparation.sbch.imported.customs.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.utils.common.CommonBaseEntity;
import com.hhwy.utils.validation.ValidationGroups;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 清关档案策划详情对象 sbch_import_customs_clear_detail
 * 
 * @author zq
 * @date 2022-12-14
 */
@Data
public class SbchImportCustomsClearDetail extends CommonBaseEntity {
    private static final long serialVersionUID = 1L;

    /** null */

    @JsonSerialize(using= ToStringSerializer.class)
    @NotNull(message = "主表id不能为空", groups = {ValidationGroups.Update.class})
    private Long id;

    /** 主表id（sbch_import_customs_clear） */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long infoId;

    /** 档案名称 */
    @Excel(name = "档案名称")
    private String fileName;

    /** 责任部门 */
    @Excel(name = "责任部门")
    private String deptName;

    /** 存档方式 */
    @Excel(name = "存档方式")
    private String saveWay;

    /** 存档年限 */
    @Excel(name = "存档年限")
    private String saveYear;

    private String remark;

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
    @Excel(name = "所属项目")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long projectId;

    /** 区域id */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long regionId;

    /** 部门id */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long deptId;

}
