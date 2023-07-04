package com.hhwy.pm.xmsl.implement.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.xmsl.implement.domain.XmslClimateCondition;
import com.hhwy.pm.xmsl.implement.service.IXmslClimateConditionService;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhenglili
 * @date 2023-07-03 12:52:07
 * @remark 气候条件
 */
@RestController
@RequestMapping("/xmslClimateCondition")
public class XmslClimateConditionController extends BaseController {

    @Autowired
    private IXmslClimateConditionService xmslClimateConditionService;

    @GetMapping("/getList")
    public AjaxResult getList(XmslClimateCondition xmslClimateConditionParam) {
        List<XmslClimateCondition> xmslClimateConditionList = xmslClimateConditionService
            .getXmslClimateConditionList(xmslClimateConditionParam);
        return AjaxResult.success(xmslClimateConditionList);
    }

    @PostMapping("/save")
    public AjaxResult save(@RequestBody List<XmslClimateCondition> xmslClimateConditionListParam) {
        xmslClimateConditionService.save(xmslClimateConditionListParam);
        return AjaxResult.success("保存成功！");
    }

    @DeleteMapping("/remove")
    public AjaxResult deleteXmslClimateConditionByPks(Long[] pks) {
        List<Long> xmslClimateConditionPkList = Arrays.asList(pks);
        return toAjax(xmslClimateConditionService.deleteXmslClimateConditionByPks(xmslClimateConditionPkList));
    }
}
