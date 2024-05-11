package com.hhwy.pm.core.sync.enums;


public enum SyncBusinessEnum {

    /** 前期策划工作小组 */
    QQCHWORKGROUP_ENUM("qqch_work_group"),
    /** 前期策划工作计划 */
    QQCHWORKPLAN_ENUM("qqch_work_plan"),
    /** wbs推送新增 */
    WBSPUSHP6_ADD_ENUM("wbsPushP6_add"),
    /** wbs推送p6修改 */
    WBSPUSHP6_UPDATE_ENUM("wbsPushP6_update"),
    /** wbs推送p6删除 */
    WBSPUSHP6_DELETE_ENUM("wbsPushP6_delete"),

    /** 作业推送p6删除 */
    WORKPUSHP6_ADD_ENUM("workPushP6_add"),
    /** 作业推送p6删除 */
    WORKPUSHP6_UPDATE_ENUM("workPushP6_update"),
    /** 作业推送p6删除 */
    WORKPUSHP6_DELETE_ENUM("wbsPushP6_delete"),
    ;


    
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
