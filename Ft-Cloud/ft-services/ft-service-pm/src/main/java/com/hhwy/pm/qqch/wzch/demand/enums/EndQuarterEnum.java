package com.hhwy.pm.qqch.wzch.demand.enums;

import java.util.Arrays;
import java.util.List;

public enum EndQuarterEnum {

    FIRST("1",Arrays.asList("第一季度")),
    FIRST_TO_SECOND("2",Arrays.asList("第一季度","第二季度")),
    FIRST_TO_THIRD("3",Arrays.asList("第一季度","第二季度","第三季度")),
    FIRST_TO_FOURTH("4",Arrays.asList("第一季度","第二季度","第三季度","第四季度"));

    private String range;
    private List<String> quarterList;

     EndQuarterEnum(String range, List<String> quarterList) {
        this.range = range;
        this.quarterList = quarterList;
    }

    public static List<String> parseQuarterList(String range){
        for (EndQuarterEnum value : EndQuarterEnum.values()) {
            if(value.range.equals(range)){
                return value.quarterList;
            }
        }
        return null;
    }
}
