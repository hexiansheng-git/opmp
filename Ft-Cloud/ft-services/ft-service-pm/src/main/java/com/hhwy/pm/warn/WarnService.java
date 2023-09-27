package com.hhwy.pm.warn;

import com.hhwy.constant.WarnItem;
import com.hhwy.constant.WarnScopeType;

public interface WarnService {

    void addWarn(WarnItem warnItem, WarnScopeType warnScopeType, String warnUrl, String warnScope, String tenantKey);
}
