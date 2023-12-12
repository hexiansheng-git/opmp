package com.hhwy.utils.customLog;


import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CustomLogger {
    //功能模块名称
    String title();
    //操作类型
    CustomBusinessType businessType();

    boolean isSaveRequestData() default true;

}
