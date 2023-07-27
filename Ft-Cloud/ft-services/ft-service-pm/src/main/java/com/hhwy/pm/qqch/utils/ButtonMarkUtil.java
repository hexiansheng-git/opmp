package com.hhwy.pm.qqch.utils;

import io.seata.common.util.StringUtils;

public class ButtonMarkUtil {

    public static void checkButtonMark(String buttonMark){
        if(StringUtils.isBlank(buttonMark)){
            throw new RuntimeException("按钮标识不能为空！");
        }
        if(!"0".equals(buttonMark) && !"1".equals(buttonMark) && !"2".equals(buttonMark)){
            throw new RuntimeException("按钮标识不符合规范！");
        }
    }
}
