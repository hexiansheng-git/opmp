package com.hhwy.pm.qqch.wzch.transport.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.utils.common.MyPrepareBaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 当地运输方案策划对象 wzch_local_transport_plan
 * 
 * @author mls
 * @date 2022-12-06
 */
@JsonInclude(JsonInclude.Include.ALWAYS)
@Data
public class WzchLocalTransportPlan extends MyPrepareBaseEntity {
    private static final long serialVersionUID = 1L;

    /** id */

    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    /** 单据编号 */
    @Excel(name = "单据编号")
    private String planCode;

    /** 标题 */
    @Excel(name = "标题")
    private String title;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String projectName;

    /** 所属区域id */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long regionId;

    /** 所属区域 */
    @Excel(name = "所属区域")
    private String regionName;

    /** 附件组id */
    private String fileGroupId;

    /** 项目id */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long projectId;

    /** 版本号 */
    @Excel(name = "版本号")
    private String versionCode;

    /** 部门id */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long deptId;

    /** 是否有效 1-是 0-否 */
    @Excel(name = "是否有效")
    private String valid;

    /** 数据创建者id */
    private String createUser;

    /** 数据创建者名称 */
    @Excel(name = "编制人")
    private String createUserName;

    @Excel(name = "编制时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date createTime;

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

    private List<WzchLocalTransportPlanDetail> wzchLocalTransportPlanDetailList;

    public WzchLocalTransportPlan(String valid) {
        this.valid = valid;
    }
    public WzchLocalTransportPlan(BigDecimal version) {
        super.setVersion(version);
    }

    public WzchLocalTransportPlan(Long id, String valid) {
        this.id = id;
        this.valid = valid;
    }

    public WzchLocalTransportPlan() {
    }
}
