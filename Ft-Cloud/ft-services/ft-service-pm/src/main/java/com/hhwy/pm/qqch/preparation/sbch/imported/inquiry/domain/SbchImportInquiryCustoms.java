package com.hhwy.pm.qqch.preparation.sbch.imported.inquiry.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.utils.common.CommonBaseEntity;
import com.hhwy.utils.validation.ValidationGroups;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 设备进口策划 进口调查-港口详情对象 sbch_import_inquiry_customs
 * 
 * @author zq
 * @date 2022-12-05
 */
@Data
public class SbchImportInquiryCustoms extends CommonBaseEntity {
    private static final long serialVersionUID = 1L;

    /** null */

    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    /** 主表id （sbch_import_inquiry） */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long inquiryId;

    /** 父表id（sbch_import_inquiry_country） */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long countryId;

    @Excel(name = "国家编码")
    @NotBlank(message = "国家编码不能为空",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    private String countryCode;

    /** 港口名称 */
    @Excel(name = "港口名称")
    @NotBlank(message = "港口名称不能为空",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    private String portName;

    /** 海关名称 */
    @Excel(name = "海关名称")
    @NotBlank(message = "海关名称不能为空",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    private String customsName;

    @Excel(name = "海关概况")
    @NotBlank(message = "海关概况不能为空",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    private String customsStatus;

    /** 关税情况 */
    @Excel(name = "关税情况")
    @NotBlank(message = "关税情况不能为空",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    private String taxStatus;

    /** 清关政策 */
    @Excel(name = "清关政策")
    @NotBlank(message = "清关政策不能为空",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    private String customsPolicy;

    /** 清关流程及要点 */
    @Excel(name = "清关流程及要点")
    @NotBlank(message = "清关流程及要点不能为空",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    private String customsPoint;

    /** 清关公司概况 */
    @Excel(name = "清关公司概况")
    @NotBlank(message = "清关公司概况不能为空",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    private String customsCompany;

    /** 清关特别注意事项 */
    @Excel(name = "清关特别注意事项")
    @NotBlank(message = "清关特别注意事项不能为空",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    private String customsAttention;

    /** null */
    private String fileGroupId;

    @Excel(name = "备注")
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
    @JsonSerialize(using= ToStringSerializer.class)
    private Long projectId;

    /** 项目名称 */
    @Excel(name = "项目名称")
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
}
