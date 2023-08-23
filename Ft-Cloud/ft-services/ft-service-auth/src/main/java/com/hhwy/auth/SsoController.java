package com.hhwy.auth;


import com.hhwy.auth.core.service.SysLoginService;
import com.hhwy.auth.utils.EncryptUtils;
import com.hhwy.common.core.domain.R;
import com.hhwy.common.core.utils.DESUtil;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.service.TokenService;
import com.hhwy.system.api.model.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/pmsso")
@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
public class SsoController {
    @Value("${pm.sso.secrekey}")
    private String ssoSecrekey;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private SysLoginService sysLoginService;

    @PostMapping({"/validate"})
    public R<?> ssoValidate(@RequestBody Map<String, String> params) {
        String tenantKey = (String)params.get("tenantKey");
        String username = (String)params.get("userName");
        tenantKey = EncryptUtils.AESDecode(tenantKey, ssoSecrekey);
        username = EncryptUtils.AESDecode(username, ssoSecrekey);
        LoginUser userInfo = this.sysLoginService.usernameValidate(tenantKey, username);
        return R.ok(this.tokenService.createToken(userInfo, tenantKey));
    }
}
