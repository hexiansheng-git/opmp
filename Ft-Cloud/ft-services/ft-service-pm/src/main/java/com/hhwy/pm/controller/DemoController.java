package com.hhwy.pm.controller;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.service.TokenService;
import com.hhwy.pm.service.IDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hhwy.common.core.web.controller.BaseController;

/**
 * 参数配置 信息操作处理
 *
 * @author hhwy
 */
@RestController
@RequestMapping("/test")
public class DemoController extends BaseController {

    @Autowired
    private IDemoService demoService;

    @Autowired
    private TokenService tokenService;

    /**
     * 获取参数配置列表
     */
    @GetMapping("/one")
    public AjaxResult list(String code) {
        return AjaxResult.success("操作成功",tokenService.getLoginUser().getUsername());
    }

}
