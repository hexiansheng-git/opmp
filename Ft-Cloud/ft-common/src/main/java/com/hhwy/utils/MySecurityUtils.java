package com.hhwy.utils;

import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;

/**
 * 解决新开线程拿不到租户标志问题
 * ThreadPoolUtil.execute(()->{
 *   MySecurityUtils.set(租户标志)
 * });
 * 优先尝试从线程名称中拿租户标志，拿不到就走平台的方式
 */
public class MySecurityUtils {

    private static final String PREFIX_FLAG = "tenantKey_";
    private static final String END_FLAG = "&";

    public static void set(String tenantKey){
        String defaultName = ObjectUtils.nvlString(Thread.currentThread().getName());
        Thread.currentThread().setName(PREFIX_FLAG+tenantKey+END_FLAG+defaultName);
    }
    public static String getTenantKey(){
        //优先尝试从线程名称中拿
        String name = Thread.currentThread().getName();
        if(!name.startsWith(PREFIX_FLAG))
            return SecurityUtils.getTenantKey();
        String tenantKey = StringUtils.substringBetween(name,PREFIX_FLAG,END_FLAG);
        if(StringUtils.isNotBlank(tenantKey))
            return tenantKey;
        return SecurityUtils.getTenantKey();
    }
}
