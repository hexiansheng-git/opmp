package com.hhwy.pm.core.sync.enums;


public enum SyncBusinessEnum {

    /** 前期策划工作小组 */
    QQCHWORKGROUP_ENUM("qqch_work_group"),
    /** 前期策划工作计划 */
    QQCHWORKPLAN_ENUM("qqch_work_plan");
    
    /**
     * 前缀
     */
    private final String busType;

    SyncBusinessEnum(String busType) {
        this.busType = busType;
    }

    /**
     * 获取前缀
     *
     * @return
     */
    public String busType() {
        return this.busType;
    }
}
