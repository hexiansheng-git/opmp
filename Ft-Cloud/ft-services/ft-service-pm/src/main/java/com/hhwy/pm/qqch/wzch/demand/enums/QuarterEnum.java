package com.hhwy.pm.qqch.wzch.demand.enums;

public enum QuarterEnum {

    FIRET(1,"第一季度"),
    SECOND(2,"第二季度"),
    THIRD(3,"第三季度"),
    FOURTH(4,"第四季度");
    private Integer value ;
    private String desc ;

    QuarterEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public Integer getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public static Integer parseValue(String desc){
        for (QuarterEnum m : QuarterEnum.values()) {
            if (m.getDesc().equals(desc)) {
                return m.getValue();
            }
        }
        return null;
    }


}
