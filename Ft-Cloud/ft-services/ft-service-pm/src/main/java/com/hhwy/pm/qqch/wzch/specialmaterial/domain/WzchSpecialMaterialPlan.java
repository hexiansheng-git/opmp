package com.hhwy.pm.qqch.wzch.specialmaterial.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.utils.common.MyPrepareBaseEntity;
import com.hhwy.utils.validation.ValidationGroups;
import lombok.Data;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 专项物资发运策划对象 wzch_special_material_plan
 * 
 * @author mls
 * @date 2022-12-07
 */
@JsonInclude(JsonInclude.Include.ALWAYS)
@Data
public class WzchSpecialMaterialPlan extends MyPrepareBaseEntity {
    private static final long serialVersionUID = 1L;

    /** id */

//    @NotNull(message = "ID不能为空",groups = {ValidationGroups.Save.class})
    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    /** 单据编号 */

    @Excel(name = "单据编号")
    private String planCode;

    /** 标题 */
//    @NotBlank(message = "单据编号不能为空",groups = {ValidationGroups.Save.class})
    @Excel(name = "标题")
    private String title;

    /** 项目名称 */
//    @NotBlank(message = "项目名称不能为空",groups = {ValidationGroups.Save.class})
    @Excel(name = "项目名称")
    private String projectName;

    /** 所属区域id */
//    @NotNull(message = "所属区域ID不能为空",groups = {ValidationGroups.Save.class})
    @JsonSerialize(using= ToStringSerializer.class)
    private Long regionId;

    /** 所属区域 */
//    @NotBlank(message = "所属区域不能为空",groups = {ValidationGroups.Save.class})
    @Excel(name = "所属区域")
    private String regionName;

    /** 附件组id */
    private String fileGroupId;

    /** 项目id */
//    @NotNull(message = "项目ID不能为空",groups = {ValidationGroups.Save.class})
    @JsonSerialize(using= ToStringSerializer.class)
    private Long projectId;


    /** 版本号 */
//    @NotBlank(message = "版本号不能为空",groups = {ValidationGroups.Save.class})
    @Excel(name = "版本号")
    private String versionCode;
    private String versionCodeStr;

    /** 限价方案说明 */
    private String limitedPriceExplain;

    /** 部门id */

    @JsonSerialize(using= ToStringSerializer.class)
    private Long deptId;

    /** 是否有效 1-是 0-否 */
    @Excel(name = "是否有效")
    private String valid;

    /** 编制人 */
    @Excel(name = "编制人")
    private String createUserName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @Excel(name = "编制时间", dateFormat = "yyyy-MM-dd HH:mm")
    private Date createTime;

    /** 数据创建者id */
    private String createUser;


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

    /** 预留字段5 */
    private String ptVar5;

    private List<String> idList;

    private List<WzchSpecialMaterialPlanDetail> planDetailList;

    private List<WzchSpecialMaterialRequestDetail> requestDetailList;

    public WzchSpecialMaterialPlan(String valid) {
        this.valid = valid;
    }
    public WzchSpecialMaterialPlan(BigDecimal version) {
        super.setVersion(version);
    }

    public WzchSpecialMaterialPlan(Long id, String valid) {
        this.id = id;
        this.valid = valid;
    }

    public WzchSpecialMaterialPlan(String versionCode, Long projectId) {
        this.versionCode = versionCode;
        this.projectId = projectId;
    }

    public WzchSpecialMaterialPlan() {
    }


}
