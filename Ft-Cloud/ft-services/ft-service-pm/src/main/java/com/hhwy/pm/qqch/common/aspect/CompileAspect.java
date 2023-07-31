package com.hhwy.pm.qqch.common.aspect;

import java.lang.annotation.*;

/**
 * @author m
 */
@Target(ElementType.METHOD)             // 注解的作用域在方法上面
@Retention(RetentionPolicy.RUNTIME)     // 在运行时保留
@Documented
public @interface CompileAspect {

    /**
     * 操作类型
     *
     * @return
     */
    CompileOptEnum type();

    /**
     * 表名称
     *
     * @return
     */
    String tableName();
}
