package com.hhwy.utils.selfEmpty;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SelfEmpty {

    Class clazz();
}
