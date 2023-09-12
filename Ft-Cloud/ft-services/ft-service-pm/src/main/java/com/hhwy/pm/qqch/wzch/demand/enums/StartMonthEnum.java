package com.hhwy.pm.qqch.wzch.demand.enums;

import java.util.Arrays;
import java.util.List;

public enum StartMonthEnum {

    ONE_TO_TWELVE("01", Arrays.asList("1月", "2月", "3月", "4月","5月","6月", "7月", "8月", "9月","10月","11月","12月")),
    TWO_TO_TWELVE("02", Arrays.asList( "2月", "3月", "4月","5月","6月", "7月", "8月", "9月","10月","11月","12月")),
    THREE_TO_TWELVE("03", Arrays.asList( "3月", "4月","5月","6月", "7月", "8月", "9月","10月","11月","12月")),
    FOUR_TO_TWELVE("04", Arrays.asList( "4月","5月","6月", "7月", "8月", "9月","10月","11月","12月")),
    FIVE_TO_TWELVE("05", Arrays.asList("5月","6月", "7月", "8月", "9月","10月","11月","12月")),
    SIX_TO_TWELVE("06", Arrays.asList("6月", "7月", "8月", "9月","10月","11月","12月")),
    SEVEN_TO_TWELVE("07", Arrays.asList( "7月", "8月", "9月","10月","11月","12月")),
    EIGHT_TO_TWELVE("08", Arrays.asList( "8月", "9月","10月","11月","12月")),
    NINE_TO_TWELVE("09", Arrays.asList( "9月","10月","11月","12月")),
    TEN_TO_TWELVE("10", Arrays.asList( "10月","11月","12月")),
    ELEVEN_TO_TWELVE("11", Arrays.asList( "11月","12月")),
    TWELVE("12", Arrays.asList( "12月"));
    private String range;
    private List<String> monthList;

    StartMonthEnum(String range, List<String> monthList) {
        this.range = range;
        this.monthList = monthList;
    }

    public static List<String> parseMonthList(String range){
        for (StartMonthEnum value : StartMonthEnum.values()) {
            if(value.range.equals(range)){
                return value.monthList;
            }
        }
        return null;
    }

    public String getRange() {
        return range;
    }

    public List<String> getMonthList() {
        return monthList;
    }
}
