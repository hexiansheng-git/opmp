package com.hhwy.pm.xmslClimateCondition.controller;

import java.util.Arrays;
import java.util.List;
import java.io.IOException;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import com.hhwy.pm.xmslClimateCondition.service.IXmslClimateConditionService;
import com.hhwy.pm.xmslClimateCondition.domain.XmslClimateCondition;

import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;

/**
 * @author zgx
 * @date 2023-07-03 18:09:54
 * @remark 
 */
@Validated
@RestController
@RequestMapping("/xmslClimateCondition")
public class XmslClimateConditionController extends BaseController{

    @Autowired
    private IXmslClimateConditionService xmslClimateConditionService;

                                                                                                                                                                                                                                                                                                                                
    @GetMapping
    public AjaxResult getXmslClimateCondition(@Validated(ValidationGroups.Get.class) @RequestBody XmslClimateCondition xmslClimateConditionParam){
        XmslClimateCondition xmslClimateCondition =  xmslClimateConditionService.getXmslClimateCondition(xmslClimateConditionParam);
        return AjaxResult.success(xmslClimateCondition);
    }

    @GetMapping("/list")
    public AjaxResult getXmslClimateConditionList(@Validated(ValidationGroups.Select.class) @RequestBody XmslClimateCondition xmslClimateConditionParam){
        startPage();
        List<XmslClimateCondition> xmslClimateConditionList = xmslClimateConditionService.getXmslClimateConditionList(xmslClimateConditionParam);
        return getDataTableAjaxResult(xmslClimateConditionList);
    }

    @PostMapping
    public AjaxResult insertXmslClimateCondition(@Validated(ValidationGroups.Save.class) @RequestBody XmslClimateCondition xmslClimateConditionParam){
        xmslClimateConditionService.insertXmslClimateCondition(xmslClimateConditionParam);
        return AjaxResult.success(xmslClimateConditionParam);
    }

    @PostMapping("/list")
    public AjaxResult insertXmslClimateConditionList(@Validated(ValidationGroups.Save.class) @RequestBody List<XmslClimateCondition> xmslClimateConditionListParam){
        xmslClimateConditionService.insertXmslClimateConditionList(xmslClimateConditionListParam);
        return AjaxResult.success(xmslClimateConditionListParam);
    }

    @PostMapping
    public AjaxResult updateXmslClimateCondition(@Validated(ValidationGroups.Update.class) @RequestBody XmslClimateCondition xmslClimateConditionParam){
        return toAjax(xmslClimateConditionService.updateXmslClimateCondition(xmslClimateConditionParam));
    }

    
    @PostMapping
    public AjaxResult deleteXmslClimateCondition(@Validated(ValidationGroups.Delete.class) @RequestBody XmslClimateCondition xmslClimateConditionParam){
        return toAjax(xmslClimateConditionService.deleteXmslClimateCondition(xmslClimateConditionParam));
    }

    
    @GetMapping("/export")
    public void export(HttpServletResponse response, XmslClimateCondition xmslClimateConditionParam) throws IOException {
        List<XmslClimateCondition> xmslClimateConditionList = xmslClimateConditionService.getXmslClimateConditionList(xmslClimateConditionParam);
        ExcelUtils<XmslClimateCondition> util = new ExcelUtils<>(XmslClimateCondition.class);
        util.exportExcel(response, xmslClimateConditionList, DateUtils.getDate());
    }
}
