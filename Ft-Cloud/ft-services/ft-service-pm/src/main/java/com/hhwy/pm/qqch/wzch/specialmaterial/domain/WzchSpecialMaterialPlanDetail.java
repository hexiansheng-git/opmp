package com.hhwy.pm.qqch.wzch.specialmaterial.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.common.core.web.domain.BaseEntity;
import lombok.Data;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.util.Date;
import java.util.List;

/**
 * 专项物资发运策划-发运策划对象 wzch_special_material_plan_detail
 * 
 * @author mls
 * @date 2022-12-07
 */
@JsonInclude(JsonInclude.Include.ALWAYS)
@Data
public class WzchSpecialMaterialPlanDetail extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** id */

    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    /** 策划ID */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long planId;

    /** 运输方式 */
    @Excel(name = "运输方式")
    private String transportMode;

    /** 物资编码 */
    @Excel(name = "物资编码")
    private String materialCode;

    @Excel(name = "物资名称")
    private String materialName;

    /** 部门id */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long deptId;

    /** 是否有效 1-是 0-否 */
    private String valid;

    @Excel(name = "备注")
    private String remark;

    /** 数据创建者id */
    private String createUser;

    /** 编制人 */
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

    private List<String> idList;

    public WzchSpecialMaterialPlanDetail() {
    }

    public WzchSpecialMaterialPlanDetail(Long planId) {
        this.planId = planId;
    }

    public WzchSpecialMaterialPlanDetail(Long id, String valid) {
        this.id = id;
        this.valid = valid;
    }
}
