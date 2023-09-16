package com.hhwy.pm.qqch.wzch.specialmaterial.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 专项物资发运策划-发运要求对象 wzch_special_material_request_detail
 * 
 * @author mls
 * @date 2022-12-07
 */
@JsonInclude(JsonInclude.Include.ALWAYS)
@Data
public class WzchSpecialMaterialRequestDetail extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** id */

    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    /** 策划ID */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long planId;

    /** 发运方案事项 */
    @Excel(name = "发运方案事项")
    private String despatchPlan;

    /** 要求内容 */
    @Excel(name = "要求内容")
    private String requestContent;

    /** 实施措施 */
    @Excel(name = "实施措施")
    private String measures;

    /** 是否预警 1-是 0否 */
    @Excel(name = "是否预警")
    private String warn;

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

    public WzchSpecialMaterialRequestDetail() {
    }

    public WzchSpecialMaterialRequestDetail(Long planId) {
        this.planId = planId;
    }

    public WzchSpecialMaterialRequestDetail(Long id, String valid) {
        this.id = id;
        this.valid = valid;
    }
}
