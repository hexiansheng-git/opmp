package com.hhwy.pm.qqch.preparation.sbch.single.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.utils.common.CommonBaseEntity;
import com.hhwy.utils.common.MyPrepareBaseEntity;
import com.hhwy.utils.validation.ValidationGroups;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * 单机核算策划对象 sbch_single_check
 * 
 * @author zq
 * @date 2022-12-22
 */
@Data
public class SbchSingleCheck extends MyPrepareBaseEntity {
    private static final long serialVersionUID = 1L;

    /** null */

    @JsonSerialize(using= ToStringSerializer.class)
    @NotNull(message = "id不能为空", groups = {ValidationGroups.Update.class})
    private Long id;

    /** null */
    @Excel(name = "单据编码")
    private String formNo;

    /** 标题 */
    @Excel(name = "标题")
    private String titleName;

    /** 是否有效（0，失效，1：有效） */
    @Excel(name = "是否有效")
    private String isValid;

    /** 创建人id */
    private String createUser;

    /** 创建人员名称 */
    @Excel(name = "创建人员名称")
    private String createUserName;

    /** 修改人id */
    private String updateUser;

    /** null */
    private String updateUserName;

    /** 修改人id */
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
    @NotNull(message = "所属项目不能为空",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    @JsonSerialize(using= ToStringSerializer.class)
    private Long projectId;

//    /** 项目名称 */
    @Excel(name = "项目名称")
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
    private Long deptId;

    private String[] ids;

    private List<SbchSingleCheckDetail> detailList;
}
