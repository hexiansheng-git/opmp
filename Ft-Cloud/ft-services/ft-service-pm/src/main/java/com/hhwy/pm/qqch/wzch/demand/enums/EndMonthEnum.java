package com.hhwy.pm.qqch.wzch.demand.enums;

import java.util.Arrays;
import java.util.List;

public enum EndMonthEnum {

    ONE("01", Arrays.asList("1月")),
    ONE_TO_TWO("02", Arrays.asList("1月", "2月")),
    ONE_TO_THREE("03", Arrays.asList("1月", "2月", "3月")),
    ONE_TO_FOUR("04", Arrays.asList("1月", "2月", "3月", "4月")),
    ONE_TO_FIVE("05", Arrays.asList("1月", "2月", "3月", "4月","5月")),
    ONE_TO_SIXE("06", Arrays.asList("1月", "2月", "3月", "4月","5月","6月")),
    ONE_TO_SEVEN("07", Arrays.asList("1月", "2月", "3月", "4月","5月","6月", "7月")),
    ONE_TO_EIGHT("08", Arrays.asList("1月", "2月", "3月", "4月","5月","6月", "7月", "8月")),
    ONE_TO_NINE("09", Arrays.asList("1月", "2月", "3月", "4月","5月","6月", "7月", "8月", "9月")),
    ONE_TO_TNE("10", Arrays.asList("1月", "2月", "3月", "4月","5月","6月", "7月", "8月", "9月","10月")),
    ONE_TO_ELEVEN("11", Arrays.asList("1月", "2月", "3月", "4月","5月","6月", "7月", "8月", "9月","10月","11月")),
    ONE_TO_TWELVE("12", Arrays.asList("1月", "2月", "3月", "4月","5月","6月", "7月", "8月", "9月","10月","11月","12月"));


    private String range;
    private List<String> monthList;

    EndMonthEnum(String range, List<String> monthList) {
        this.range = range;
        this.monthList = monthList;
    }

    public static List<String> parseMonthList(String range){
        for (EndMonthEnum value : EndMonthEnum.values()) {
            if(value.range.equals(range)){
                return value.monthList;
            }
        }
        return null;
    }
}
