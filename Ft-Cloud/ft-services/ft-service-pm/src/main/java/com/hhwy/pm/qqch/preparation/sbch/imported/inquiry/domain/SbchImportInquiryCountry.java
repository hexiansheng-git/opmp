package com.hhwy.pm.qqch.preparation.sbch.imported.inquiry.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.utils.common.CommonBaseEntity;
import com.hhwy.utils.validation.ValidationGroups;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * 设备进口策划 进口调查-国家详情对象 sbch_import_inquiry_country
 * 
 * @author zq
 * @date 2022-12-05
 */
@Data
public class SbchImportInquiryCountry extends CommonBaseEntity {
    private static final long serialVersionUID = 1L;

    /** null */

    @JsonSerialize(using= ToStringSerializer.class)
    @NotNull(message = "主表id不能为空", groups = {ValidationGroups.Save.class})
    private Long id;

    /** 主表id （sbch_import_inquiry） */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long inquiryId;

    /** 国家名称 */
    @Excel(name = "国家编码")
    @NotBlank(message = "国家编码不能为空",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    private String countryCode;

    /** 国家语言 */
    @Excel(name = "国家语言")
    @NotBlank(message = "国家语言不能为空",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    private String countryLanguage;

    /** 币种 */
    @Excel(name = "币种")
    @NotBlank(message = "币种不能为空",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    private String currencyCode;

    /** 自然环境与当地风俗 */
    @Excel(name = "自然环境与当地风俗")
    @NotBlank(message = "自然环境与当地风俗不能为空",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    private String natureCuntoms;

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

    //港口详情
    private List<SbchImportInquiryCustoms> customsList;
}
