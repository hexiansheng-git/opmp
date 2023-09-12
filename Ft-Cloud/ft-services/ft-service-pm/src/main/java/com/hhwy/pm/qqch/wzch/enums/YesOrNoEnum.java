package com.hhwy.pm.qqch.wzch.enums;

import com.hhwy.common.core.utils.StringUtils;

public enum YesOrNoEnum {
    YES("1","是"),
    NO("0","否");

    YesOrNoEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    private String value;
    private String desc;

    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public static String parseDesc(String value){
        if(StringUtils.isBlank(value)){
            return null;
        }
        for (com.hhwy.pm.qqch.wzch.enums.YesOrNoEnum e : com.hhwy.pm.qqch.wzch.enums.YesOrNoEnum.values()) {
            if (e.getValue().equals(value)) {
                return e.getDesc();
            }
        }
        return null;
    }

    public static String parseValue(String desc){
        if(StringUtils.isBlank(desc)){
            return null;
        }
        for (com.hhwy.pm.qqch.wzch.enums.YesOrNoEnum e : com.hhwy.pm.qqch.wzch.enums.YesOrNoEnum.values()) {
            if (e.getDesc().equals(desc)) {
                return e.getValue();
            }
        }
        return null;
    }

}
