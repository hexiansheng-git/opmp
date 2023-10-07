package com.hhwy.constant;

/**
 * 预警enum
 */
public enum WarnItem {


    WORK_GROUP_SET_UP("前期策划工作小组设立", "work_group_set_up", "项目中标之后的10天内，成立前期策划临时工作小组并完成审批，未完成则进行预警"),
    SUMMARY_SET_UP("前期策划总结评价-总结", "summary_set_up", "项目在完成初验后10天内报送前期策划总结，未报送则进行预警"),
    EVALUATION_SET_UP("前期策划总结评价-评价", "evaluation_set_up", "名国家总项目部 (片区公司) 在项目报送前期策划总结后7天内进行评价，未完成进行预警");

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
