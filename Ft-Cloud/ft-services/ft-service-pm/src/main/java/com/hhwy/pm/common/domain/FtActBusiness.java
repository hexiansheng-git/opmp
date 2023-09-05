package com.hhwy.pm.common.domain;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 功能：工作流程发起记录实体
 * 作者: fushudong
 * 时间: 2023/09/04
 */
@Data
public class FtActBusiness {

    //业务主键
    private Long id;
    //业务主键
    private String businessId;
    //业务表名称
    private String businessTableName;
    //流程实例id
    private String processInstanceId;
    //租户标识
    private String tenantKey;
    //创建者
    private String createUser;
    //创建时间
    private Date createTime;
    //更新者
    private String updateUser;
    //更新时间
    private Date updateTime;
    //备注
    private String remark;

//    private List<ActRuTask> taskList;

    //当前审批节点名称
    private String name;
    //审批人
    private String assignee;
}

