package com.hhwy.auth;


import com.hhwy.auth.core.service.SysLoginService;
import com.hhwy.common.core.domain.R;
import com.hhwy.common.core.utils.DESUtil;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.service.TokenService;
import com.hhwy.system.api.model.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/pmsso")
public class SsoController {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private SysLoginService sysLoginService;

    @PostMapping({"/validate"})
    public R<?> ssoValidate(@RequestBody Map<String, String> params) {
        String tenantKey = (String)params.get("tenantKey");
        String username = (String)params.get("userName");
        LoginUser userInfo = this.sysLoginService.usernameValidate(tenantKey, username);
        return R.ok(this.tokenService.createToken(userInfo, tenantKey));
    }
}
