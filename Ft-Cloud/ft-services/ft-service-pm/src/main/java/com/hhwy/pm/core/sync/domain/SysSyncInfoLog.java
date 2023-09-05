package com.hhwy.pm.core.sync.domain;

import com.hhwy.common.core.annotation.Excel;
import com.hhwy.common.core.web.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import java.util.Date;

/**
 * 数据同步日志对象 sys_sync_info_log
 * 
 * @author XXX
 * @date 2023-09-04
 */
@Data
public class SysSyncInfoLog extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** Id */
    private Long id;

    /** 业务功能名称 */
    @Excel(name = "业务功能名称")
    private String businessName;

    /** 上一次推送最大ID */
    @Excel(name = "上一次推送最大ID")
    private Long lastPushId;

    /** 推送条目数 */
    @Excel(name = "推送条目数")
    private Integer pushCount;

    /** 推送耗时，毫秒数 */
    @Excel(name = "推送耗时，毫秒数")
    private Long useTime;

    /** 状态,0:失败,1:成功 */
    @Excel(name = "状态,0:失败,1:成功")
    private Integer status;

    /** 失败次数 */
    @Excel(name = "失败次数")
    private Integer failCount;

    /** 失败消息 */
    @Excel(name = "失败消息")
    private String failMsg;

    /** 数据创建者id */
    @Excel(name = "数据创建者id")
    private String createUser;

    /** 数据创建者名称 */
    @Excel(name = "数据创建者名称")
    private String createUserName;

    /** 数据修改者id */
    @Excel(name = "数据修改者id")
    private String updateUser;

    /** 数据删除者 */
    @Excel(name = "数据删除者")
    private String delUser;

    /** 数据删除系统时间 */
    @Excel(name = "数据删除系统时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date delTime;

    /** 删除标识：0未删除；1已删除 */
    private String delFlag;

    /** 预留字段1 */
    @Excel(name = "预留字段1")
    private String ptVar1;

    /** 预留字段2 */
    @Excel(name = "预留字段2")
    private String ptVar2;

    /** 预留字段3 */
    @Excel(name = "预留字段3")
    private String ptVar3;

    /** 预留字段4 */
    @Excel(name = "预留字段4")
    private String ptVar4;

    /** 预留字段5 */
    @Excel(name = "预留字段5")
    private String ptVar5;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("businessName", getBusinessName())
            .append("lastPushId", getLastPushId())
            .append("pushCount", getPushCount())
            .append("useTime", getUseTime())
            .append("status", getStatus())
            .append("failCount", getFailCount())
            .append("failMsg", getFailMsg())
            .append("createUser", getCreateUser())
            .append("createUserName", getCreateUserName())
            .append("createTime", getCreateTime())
            .append("updateUser", getUpdateUser())
            .append("updateTime", getUpdateTime())
            .append("delUser", getDelUser())
            .append("delTime", getDelTime())
            .append("delFlag", getDelFlag())
            .append("ptVar1", getPtVar1())
            .append("ptVar2", getPtVar2())
            .append("ptVar3", getPtVar3())
            .append("ptVar4", getPtVar4())
            .append("ptVar5", getPtVar5())
            .toString();
    }
}
