package com.hhwy.pm.xmsl.implement.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.xmsl.implement.domain.LocalResourceSupply;
import com.hhwy.pm.xmsl.implement.service.ILocalResourceSupplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhenglili
 * @date 2023-07-04 13:15:00
 * @remark 当地资源供应
 */
@RestController
@RequestMapping("/localResourceSupply")
public class LocalResourceSupplyController extends BaseController {

    @Autowired
    private ILocalResourceSupplyService localResourceSupplyService;

    @GetMapping("/getList")
    public AjaxResult getList(LocalResourceSupply localResourceSupplyParam) {
        LocalResourceSupply localResourceSupply = localResourceSupplyService.getList(localResourceSupplyParam);
        return AjaxResult.success(localResourceSupply);
    }

    @PostMapping("/save")
    public AjaxResult save(@RequestBody LocalResourceSupply geologicalCondition) {
        localResourceSupplyService.save(geologicalCondition);
        return AjaxResult.success("保存成功！");
    }
}
