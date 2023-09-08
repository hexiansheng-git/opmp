package com.hhwy.enums;

/**
 * 流程enum
 *
 * @author mls
 */
public enum FlowEnum {


    /**********************前期策划开始************************/
    QQCH_REVIEW("qqch_receive", "qqch_receive_process"),

    /**********************前期策划结束************************/


    //项目设立，合同
    XMSL_CONTRACT("xmsl_contract_info", "process_test_contract"),

    /*********************进度管理****************************/
    // 进度管理年进度计划
    JDGL_YEARPLAN("jdgl_year_plan", "process_jdgl_year_plan"),
    // 进度管理季进度计划
    QUARTER_YEARPLAN("jdgl_quarter_plan", "process_jdgl_quarter_plan"),
    // 进度管理月进度计划
    MONTH_YEARPLAN("jdgl_month_plan", "process_jdgl_month_plan"),
    // 进度管理周进度计划
    WEEK_YEARPLAN("jdgl_week_plan", "process_jdgl_week_plan"),
    // 进度管理进度填报
    DAYSCHEDULE("jdgl_day_schedule","process_jdgl_day_schedule");

    public String getTableName() {
        return this.tableName;
    }

    public String getProcessKey() {
        return this.processKey;
    }

    private final String tableName;

    private final String processKey;


    FlowEnum(String tableName, String processKey) {
        this.tableName = tableName;
        this.processKey = processKey;
    }
}
