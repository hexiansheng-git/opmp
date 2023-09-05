package com.hhwy.enums;

/**
 * 流程enum
 *
 * @author mls
 */
public enum FlowStatusEnum {


    //项目设立，合同
    FLOW_STATUS_1("待发起", "1"),
    FLOW_STATUS_2("审批中", "2"),
    FLOW_STATUS_3("已结束", "3");


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
}
