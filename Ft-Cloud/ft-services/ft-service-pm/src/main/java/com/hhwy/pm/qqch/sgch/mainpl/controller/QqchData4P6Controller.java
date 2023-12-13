package com.hhwy.pm.qqch.sgch.mainpl.controller;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.sgch.mainpl.domain.QqchMainPlanItem;
import com.hhwy.pm.qqch.sgch.mainpl.service.IQqchData4P6Service;
import com.hhwy.pm.qqch.utils.VersionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/qqchData4P6")
public class QqchData4P6Controller {

    @Autowired
    private IQqchData4P6Service qqchData4P6Service;

    @PostMapping("/initQqchData4P6")
    public AjaxResult initQqchData4P6(BigDecimal version) {
        String tenantKey = SecurityUtils.getTenantKey();
        return AjaxResult.success(qqchData4P6Service.initQqchData4P6(tenantKey, version));
    }



    @PostMapping("/initQqchData4P6Thread")
    public AjaxResult initQqchData4P6Thread(BigDecimal version) {
        String tenantKey = SecurityUtils.getTenantKey();
        version = VersionUtil.getVersion(QqchMainPlanItem.TABLE_NAME, version);
        qqchData4P6Service.initQqchData4P64Thread(tenantKey, version);
        return AjaxResult.success();
    }

}
