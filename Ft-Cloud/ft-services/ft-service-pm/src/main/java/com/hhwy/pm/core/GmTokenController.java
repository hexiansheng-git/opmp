package com.hhwy.pm.core;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.utils.GmTokenUtils;
import com.hhwy.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 总部版Token获取
 * @author wk
 * @date 2023-08-01 11:26:43
 * @remark 
 */
@Validated
@RestController
@RequestMapping("/gmToken")
@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
public class GmTokenController extends BaseController{
    @Value("${gm.sso.url}")
    private String ssoUrl;
    @Value("${gm.url}")
    private String gmUrl;
    @Value("${gm.sso.secrekey}")
    private String ssoSecrekey;

    @PostMapping("/get")
    public AjaxResult get(@RequestBody Map<String,Object> map){
        Object tenantKey = map.get("tenantKey");
        if(ObjectUtils.isBlank(tenantKey)){
            tenantKey = "master";
        }
        String token = GmTokenUtils.getToken(ssoUrl,ssoSecrekey,tenantKey.toString());
        return AjaxResult.success("",ObjectUtils.toMap("token",token,"gmUrl",gmUrl));
    }

}
