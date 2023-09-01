package com.hhwy.pm.qqch.preparation.sbch.single.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.utils.common.CommonBaseEntity;
import com.hhwy.utils.validation.ValidationGroups;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 单机核算策划对象 sbch_single_check_detail
 * 
 * @author zq
 * @date 2022-12-22
 */
@Data
public class SbchSingleCheckDetail extends CommonBaseEntity {
    private static final long serialVersionUID = 1L;

    /** null */

    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    /** 主表id（sbch_single_check） */
    @Excel(name = "主表id", readConverterExp = "s=bch_single_check")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long infoId;

    /** 工作内容 */
    @Excel(name = "工作内容")
    @NotBlank(message = "工作内容不能为空",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    private String workContent;

    /** 牵头部门 */
    @Excel(name = "牵头部门")
    @NotBlank(message = "牵头部门不能为空",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    private String middleDept;

    /** 主办部门 */
    @Excel(name = "主办部门")
    @NotBlank(message = "主办部门不能为空",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    private String mainDept;

    /** 数据采集周期 */
    @Excel(name = "数据采集周期")
    @NotBlank(message = "数据采集周期不能为空",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    private String dataGetTime;

    /** 执行措施 */
    @Excel(name = "执行措施")
    @NotBlank(message = "执行措施不能为空",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    private String useWay;

    /** 创建人id */
    @Excel(name = "创建人id")
    private String createUser;

    /** 创建人姓名 */
    @Excel(name = "创建人姓名")
    private String createUserName;

    /** 修改人id */
    @Excel(name = "修改人id")
    private String updateUser;

    /** 修改人姓名 */
    @Excel(name = "修改人姓名")
    private String updateUserName;

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

    @Excel(name = "备注")
    private String contentRemark;
}
