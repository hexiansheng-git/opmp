package com.hhwy.pm.xmsl.implement.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.xmsl.implement.domain.GeologicalCondition;
import com.hhwy.pm.xmsl.implement.service.IGeologicalConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhenglili
 * @date 2023-07-03 12:53:07
 * @remark 地质条件
 */
@RestController
@RequestMapping("/geologicalCondition")
public class GeologicalConditionController extends BaseController {

    @Autowired
    private IGeologicalConditionService geologicalConditionService;

    @GetMapping("/getList")
    public AjaxResult getList(GeologicalCondition geologicalConditionParam) {
        GeologicalCondition geologicalCondition = geologicalConditionService.getList(geologicalConditionParam);
        return AjaxResult.success(geologicalCondition);
    }

    @PostMapping("/save")
    public AjaxResult save(@RequestBody GeologicalCondition geologicalCondition) {
        geologicalConditionService.save(geologicalCondition);
        return AjaxResult.success("保存成功！");
    }
}
