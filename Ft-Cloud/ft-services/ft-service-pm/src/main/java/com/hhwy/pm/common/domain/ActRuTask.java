package com.hhwy.pm.common.domain;

import lombok.Data;

/**
 * 功能：当前待处理节点信息
 * 作者: fushudong
 * 时间: 2023/09/04
 */
@Data
public class ActRuTask {

    //当前审批节点名称
    private String name;
    //审批人
    private String assignee;
}