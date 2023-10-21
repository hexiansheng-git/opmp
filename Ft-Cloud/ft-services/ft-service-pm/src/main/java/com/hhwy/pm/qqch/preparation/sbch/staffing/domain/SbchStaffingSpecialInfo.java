package com.hhwy.pm.qqch.preparation.sbch.staffing.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.utils.common.MyPrepareBaseEntity;
import com.hhwy.utils.validation.ValidationGroups;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 设备人员配置-特种设备爱人员对象 sbch_staffing_special_info
 * 
 * @author zq
 * @date 2022-11-30
 */
@Data
public class SbchStaffingSpecialInfo extends MyPrepareBaseEntity {
    private static final long serialVersionUID = 1L;

    /** null */

    @JsonSerialize(using= ToStringSerializer.class)
    @NotNull(message = "id不能为空", groups = {ValidationGroups.Update.class})
    private Long id;

    /** 单据编码 */
    @Excel(name = "单据编码")
    private String formNo;

    /** 标题 */
    @Excel(name = "标题")
//    @NotBlank(message = "标题不能为空",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    private String titleName;

    /** 版本号 */
    @Excel(name = "版本号")
    private BigDecimal versionNo;

    /** 是否有效（0，失效，1：有效） */
    @Excel(name = "是否有效")
    private String isValid;

    /** 创建人id */
    private String createUser;

    /** null */
    @Excel(name = "创建人姓名")
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
    @JsonSerialize(using= ToStringSerializer.class)
    @NotNull(message = "所属项目不能为空",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    private Long projectId;
//
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

    private List<SbchStaffingSpecialDetail> detailList;

    private String[] ids;
}
