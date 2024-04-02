package com.hhwy.constant;

/**
 * 预警enum
 */
public enum WarnItem {


    WORK_GROUP_SET_UP("前期策划工作小组设立", "work_group_set_up", "项目中标之后的10天内，成立前期策划临时工作小组并完成审批，未完成则进行预警"),
    WORK_PLAN_COMMIT("前期策划工作计划提交","work_plan_commit","应在前期策划临时工作小组审批之后的3天内，按时提交前期策划工作计划报请审批，未提交则进行预警"),
    WORK_PLAN_APPROVAL("前期策划工作计划审批","work_plan_approval","前期策划工作计划提交后，三天内完成审批，未完成则进行预警"),
    PREPARATION_FIRST_STAGE("前期策划编制","preparation_first_stage","第一阶段：收到中标通知书后1个月内完成前期策划编制，未完成进行预警"),
    PREPARATION_SECOND_STAGE("前期策划编制","preparation_second_stage","第二阶段：合同签订后一个月内或开工令下发日（取先至时间）内完成前期策划编制，未完成进行预警"),
    PREPARATION_THIRD_STAGE("前期策划编制","preparation_third_stage","第三阶段：开工令下发3个月内完成前期策划编制，未完成进行预警"),
    QQCH_REVIEW("前期策划评审","qqch_review","收到项目前期策划后七天内完成审批，未完成进行预警"),
    QQCH_CHANGE_APPROVAL("前期策划变更审批","qqch_change_approval","变更申请提交后，三天内完成审批，未完成进行预警"),
    SUMMARY("前期策划总结评价-总结", "summary", "项目在完成初验后10天内报送前期策划总结，未报送则进行预警"),
    EVALUATION("前期策划总结评价-评价", "evaluation", "名国家总项目部 (片区公司) 在项目报送前期策划总结后7天内进行评价，未完成进行预警"),
    PERSON_CONTROL_PLAN_ONE("人员管控策划", "person_control_plan_one", "证件到期前1个月，进行预警"),
    PERSON_CONTROL_PLAN_TWO("人员管控策划", "person_control_plan_two", "证件到期前2个月，进行预警"),
    PERSON_CONTROL_PLAN_THREE("人员管控策划", "person_control_plan_three", "证件到期前3个月，进行预警"),
    WBS_P6_WARN("P6推送预警", "wbs_p6_warn", "P6数据已推送，请及时上传作业"),
    SGJS_BUILD_SCHEME_LIST("施工技术-施工方案编制", "sgjs_build_scheme_list", "根据施工方案清单计划，若该方案未按计划完成编制时间进行提交评审，则进行预警"),
    SGJS_BUILD_SCHEME_REVIEW("施工技术-施工方案评审", "sgjs_build_scheme_review", "根据施工方案清单计划，若该方案未按时完成评审，则进行预警"),
    KCSJ_PLAN_PROCESS("勘察设计-计划进度","plan_process_warn","提前七天提醒一次给项目总工；超期每两天提醒预警人");

    public String getWarnItem() {
        return warnItem;
    }

    public String getWarnItemId() {
        return warnItemId;
    }

    public String getWarnRule() {
        return warnRule;
    }

    /*预警项*/
    private final String warnItem;

    /*预警项id*/
    private final String warnItemId;

    /*预警规则*/
    private final String warnRule;

    WarnItem(String WarnItem, String WarnItemId, String warnRule) {
        this.warnItem = WarnItem;
        this.warnItemId = WarnItemId;
        this.warnRule = warnRule;
    }
}
