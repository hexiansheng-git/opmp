package com.hhwy.pm.qqch.wzch.transport.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 当地运输方案策划详情对象 wzch_local_transport_plan_detail
 *
 * @author mls
 * @date 2022-12-06
 */
@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
public class WzchLocalTransportPlanDetail extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** id */

    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    /** 策划ID */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long planId;

    /** 注意事项 */
    @Excel(name = "注意事项")
    private String heedNote;

    /** 事项说明 */
    @Excel(name = "事项说明")
    private String description;

    /** 执行部门 */
    @Excel(name = "执行部门")
    private String executDept;

    @Excel(name = "备注")
    private String remark;

    /** 是否有效 1-是 0-否 */
    private String valid;

    /** 数据创建者id */
    private String createUser;

    /** 数据创建者名称 */
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

    /** 预留字段4 */
    private String ptVar4;

    /** 预留字段5 */
    private String ptVar5;

    private List<String> idList;

    public WzchLocalTransportPlanDetail() {

    }

    public WzchLocalTransportPlanDetail(Long id, String valid) {
        this.id = id;
        this.valid = valid;
    }

    public WzchLocalTransportPlanDetail(Long planId) {
        this.planId = planId;
    }
}
