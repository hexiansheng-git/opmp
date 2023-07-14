package com.hhwy.pm.xmsl.wbs.controller;

import java.util.Arrays;
import java.util.List;
import java.io.IOException;

import com.hhwy.pm.xmsl.wbs.domain.XmslWbsMain;
import com.hhwy.pm.xmsl.wbs.service.IXmslWbsMainService;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import com.hhwy.pm.xmsl.wbs.domain.XmslWbsMain;

import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.common.security.annotation.PreAuthorize;

/**
 * @author wk
 * @date 2023-07-13 18:02:49
 * @remark 
 */
@Validated
@RestController
@RequestMapping("/xmslWbsMain")
public class XmslWbsMainController extends BaseController{

    @Autowired
    private IXmslWbsMainService xmslWbsMainService;

                                                                                                                                                    

    @PreAuthorize(hasPermi = "xmslWbsMain:list")
    @GetMapping
    public AjaxResult getXmslWbsMain(@Validated(ValidationGroups.Get.class) @RequestBody XmslWbsMain xmslWbsMainParam){
        XmslWbsMain xmslWbsMain =  xmslWbsMainService.getXmslWbsMain(xmslWbsMainParam);
        return AjaxResult.success(xmslWbsMain);
    }

    @PreAuthorize(hasPermi = "xmslWbsMain:list")
    @GetMapping("/list")
    public AjaxResult getXmslWbsMainList(@Validated(ValidationGroups.Select.class) @RequestBody XmslWbsMain xmslWbsMainParam){
        startPage();
        List<XmslWbsMain> xmslWbsMainList = xmslWbsMainService.getXmslWbsMainList(xmslWbsMainParam);
        return getDataTableAjaxResult(xmslWbsMainList);
    }

    @PreAuthorize(hasPermi = "xmslWbsMain:add")
    @PostMapping("/add")
    public AjaxResult insertXmslWbsMain(@Validated(ValidationGroups.Save.class) @RequestBody XmslWbsMain xmslWbsMainParam){
        xmslWbsMainService.insertXmslWbsMain(xmslWbsMainParam);
        return AjaxResult.success(xmslWbsMainParam);
    }

    @PreAuthorize(hasPermi = "xmslWbsMain:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertXmslWbsMainList(@Validated(ValidationGroups.Save.class) @RequestBody List<XmslWbsMain> xmslWbsMainListParam){
        xmslWbsMainService.insertXmslWbsMainList(xmslWbsMainListParam);
        return AjaxResult.success(xmslWbsMainListParam);
    }

    @PreAuthorize(hasPermi = "xmslWbsMain:update")
    @PostMapping("/update")
    public AjaxResult updateXmslWbsMain(@Validated(ValidationGroups.Update.class) @RequestBody XmslWbsMain xmslWbsMainParam){
        return toAjax(xmslWbsMainService.updateXmslWbsMain(xmslWbsMainParam));
    }

            @PreAuthorize(hasPermi = "xmslWbsMain:update")
        @PostMapping("/batchUpdate")
        public AjaxResult updateXmslWbsMainList(@Validated(ValidationGroups.Update.class) @RequestBody List<XmslWbsMain> xmslWbsMainListParam){
            return toAjax(xmslWbsMainService.updateXmslWbsMainList(xmslWbsMainListParam));
        }
    
    @PreAuthorize(hasPermi = "xmslWbsMain:remove")
    @PostMapping("/delete")
    public AjaxResult deleteXmslWbsMain(@Validated(ValidationGroups.Delete.class) @RequestBody XmslWbsMain xmslWbsMainParam){
        return toAjax(xmslWbsMainService.deleteXmslWbsMain(xmslWbsMainParam));
    }

            @PreAuthorize(hasPermi = "xmslWbsMain:remove")
        @PostMapping("/{ids}")
        public AjaxResult deleteXmslWbsMainByPks(@PathVariable Long[] ids){
            List<Long> xmslWbsMainPkList = Arrays.asList(ids);
            return toAjax(xmslWbsMainService.deleteXmslWbsMainByPks(xmslWbsMainPkList));
        }
    
    @GetMapping("/export")
    public void export(HttpServletResponse response, XmslWbsMain xmslWbsMainParam) throws IOException {
        List<XmslWbsMain> xmslWbsMainList = xmslWbsMainService.getXmslWbsMainList(xmslWbsMainParam);
        ExcelUtils<XmslWbsMain> util = new ExcelUtils<>(XmslWbsMain.class);
        util.exportExcel(response, xmslWbsMainList, DateUtils.getDate());
    }
}
