package com.hhwy.flowable.service;

import org.springframework.stereotype.Component;

public interface INodeTaskService {

    /**
     * 是否当前审批节点为第一节点
     * @param insId 实例ID
     * @return
     */
    public String isNowfirstNode(String insId);
}
