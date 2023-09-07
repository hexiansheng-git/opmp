package com.hhwy.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author m
 */
public class ParamUtils {

    /**
     * 实例变量会跟着实例的销毁而销毁
      */
    private final Map<String, Object> param;
    {
        param = new HashMap<>();
    }

    public static ParamUtils init(){
        // new对象 当此对象没有任何引用时,就会被销毁掉
        return new ParamUtils();
    }

    public ParamUtils add(String key, Object val) {
        this.param.put(key, val);
        return this;
    }

    public Map<String, Object> get() {
        return param;
    }




}
