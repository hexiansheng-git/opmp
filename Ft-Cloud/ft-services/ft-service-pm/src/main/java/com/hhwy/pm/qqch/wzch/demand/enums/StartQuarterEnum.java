package com.hhwy.pm.qqch.wzch.demand.enums;

import java.util.Arrays;
import java.util.List;

public enum StartQuarterEnum {

    FIRST_TO_FOURTH("1", Arrays.asList("第一季度","第二季度","第三季度","第四季度")),
    SECOND_TO_FOURTH("2", Arrays.asList("第二季度","第三季度","第四季度")),
    THIRD_TO_FOURTH("3", Arrays.asList("第三季度","第四季度")),
    FOURTH("4", Arrays.asList("第四季度"));

    private String range;
    private List<String> quarterList;

    StartQuarterEnum(String range, List<String> quarterList) {
        this.range = range;
        this.quarterList = quarterList;
    }

    public static List<String> parseQuarterList(String range){
        for (StartQuarterEnum value : StartQuarterEnum.values()) {
            if(value.range.equals(range)){
                return value.quarterList;
            }
        }
        return null;
    }

    public String getRange() {
        return range;
    }

    public List<String> getQuarterList() {
        return quarterList;
    }
}
