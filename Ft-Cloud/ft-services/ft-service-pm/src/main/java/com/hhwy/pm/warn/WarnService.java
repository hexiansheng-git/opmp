package com.hhwy.pm.warn;

import com.hhwy.constant.WarnItem;
import com.hhwy.constant.WarnScopeType;

public interface WarnService {

    /**
     * 发送预警
     * @param warnItem 预警enum
     * @param warnScopeType 预警范围类型
     * @param warnUrl 预警链接
     * @param warnScope 部门或者用户id，多个之间用 ” ， “ 隔开
     * @param tenantKey 租户标识
     */
    void addWarn(WarnItem warnItem, WarnScopeType warnScopeType, String warnUrl, String warnScope, String tenantKey);

    /**
     *
     * @param warnItem
     * @param warnContent
     * @param warnScopeType
     * @param warnUrl
     * @param warnScope
     * @param tenantKey
     */
    void addWarn(WarnItem warnItem, String warnContent, WarnScopeType warnScopeType, String warnUrl, String warnScope, String tenantKey);
}
