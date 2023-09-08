package com.hhwy.enums;

/**
 * 流程enum
 *
 * @author mls
 */
public enum FlowStatusEnum {


    //流程状态 0-未发起; 1审核中; 4-流程已结束,业务未结束; 5-流程和业务都已结束'
    FLOW_STATUS_INIT("待发起", "0"),
    FLOW_STATUS_AUDITING("审批中", "1"),
    FLOW_STATUS_END("已结束", "4");


    public String getName() {
        return this.name;
    }

    public String getKey() {
        return this.Key;
    }

    private final String name;

    private final String Key;


    FlowStatusEnum(String name, String Key) {
        this.name = name;
        this.Key = Key;
    }

    @Override
    public String toString() {
        return this.getKey();
    }
}
