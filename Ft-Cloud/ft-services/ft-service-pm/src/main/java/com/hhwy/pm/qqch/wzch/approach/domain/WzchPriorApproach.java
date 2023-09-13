package com.hhwy.pm.qqch.wzch.approach.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.utils.common.CommonBaseEntity;
import com.hhwy.utils.common.MyPrepareBaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * 优先进场物资对象 wzch_prior_approach
 * 
 * @author mls
 * @date 2022-11-28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WzchPriorApproach extends MyPrepareBaseEntity {
    private static final long serialVersionUID = 1L;

    /** id */

    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    /** 单据编号 */
    @Excel(name = "单据编号")
    private String approachCode;

    /** 标题 */
    @Excel(name = "标题")
    private String title;

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

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String projectName;

    /** 部门id */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long deptId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @Excel(name = "编制时间", dateFormat = "yyyy-MM-dd HH:mm")
    private Date createTime;

    /** 数据创建者id */
    private String createUser;

    /** 数据创建者名称 */
    @Excel(name = "编制人")
    private String createUserName;

    /** 数据修改者id */
    private String updateUser;

    /** 数据修改者名称 */
    private String updateUserName;

    /** 数据删除者 */
    private String delUser;

    /** 数据删除系统时间 */
    private Date delTime;

    /** 删除标记: 0-未删除 1-已删除 */
    private String delFlag;

    private List<WzchPriorApproachDetail> wzchPriorApproachDetailList;

    /** 预留字段1 */
    private String ptVar1;

    /** 预留字段2 */
    private String ptVar2;

    /** 预留字段3 */
    private String ptVar3;

    /** 预留字段4 */
    private String ptVar4;

    private List<String> idList;


}
