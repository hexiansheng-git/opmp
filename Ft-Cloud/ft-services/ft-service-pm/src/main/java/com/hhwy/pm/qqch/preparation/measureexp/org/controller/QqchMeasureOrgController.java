package com.hhwy.pm.qqch.preparation.measureexp.org.controller;

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
import com.hhwy.pm.qqch.preparation.measureexp.org.service.IQqchMeasureOrgService;
import com.hhwy.pm.qqch.preparation.measureexp.org.domain.QqchMeasureOrg;

import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.common.security.annotation.PreAuthorize;

/**
 * @author mls
 * @date 2023-07-25 18:01:39
 * @remark 
 */
@Validated
@RestController
@RequestMapping("/qqchMeasureOrg")
public class QqchMeasureOrgController extends BaseController{

    @Autowired
    private IQqchMeasureOrgService qqchMeasureOrgService;

                                                                                                                                                                                                                                        

    @PreAuthorize(hasPermi = "qqchMeasureOrg:list")
    @GetMapping
    public AjaxResult getQqchMeasureOrg(@Validated(ValidationGroups.Get.class)  QqchMeasureOrg qqchMeasureOrgParam){
        QqchMeasureOrg qqchMeasureOrg =  qqchMeasureOrgService.getQqchMeasureOrg(qqchMeasureOrgParam);
        return AjaxResult.success(qqchMeasureOrg);
    }

    @PreAuthorize(hasPermi = "qqchMeasureOrg:list")
    @GetMapping("/list")
    public AjaxResult getQqchMeasureOrgList(@Validated(ValidationGroups.Select.class) QqchMeasureOrg qqchMeasureOrgParam){
        startPage();
        List<QqchMeasureOrg> qqchMeasureOrgList = qqchMeasureOrgService.getQqchMeasureOrgList(qqchMeasureOrgParam);
        return getDataTableAjaxResult(qqchMeasureOrgList);
    }

    @PreAuthorize(hasPermi = "qqchMeasureOrg:add")
    @PostMapping("/add")
    public AjaxResult insertQqchMeasureOrg(@Validated(ValidationGroups.Save.class) @RequestBody QqchMeasureOrg qqchMeasureOrgParam){
        qqchMeasureOrgService.insertQqchMeasureOrg(qqchMeasureOrgParam);
        return AjaxResult.success(qqchMeasureOrgParam);
    }

    @PreAuthorize(hasPermi = "qqchMeasureOrg:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchMeasureOrgList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchMeasureOrg> qqchMeasureOrgListParam){
        qqchMeasureOrgService.insertQqchMeasureOrgList(qqchMeasureOrgListParam);
        return AjaxResult.success(qqchMeasureOrgListParam);
    }

    @PreAuthorize(hasPermi = "qqchMeasureOrg:update")
    @PostMapping("/update")
    public AjaxResult updateQqchMeasureOrg(@Validated(ValidationGroups.Update.class) @RequestBody QqchMeasureOrg qqchMeasureOrgParam){
        return toAjax(qqchMeasureOrgService.updateQqchMeasureOrg(qqchMeasureOrgParam));
    }

            @PreAuthorize(hasPermi = "qqchMeasureOrg:update")
        @PostMapping("/batchUpdate")
        public AjaxResult updateQqchMeasureOrgList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchMeasureOrg> qqchMeasureOrgListParam){
            return toAjax(qqchMeasureOrgService.updateQqchMeasureOrgList(qqchMeasureOrgListParam));
        }
    
    @PreAuthorize(hasPermi = "qqchMeasureOrg:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchMeasureOrg(@Validated(ValidationGroups.Delete.class) @RequestBody QqchMeasureOrg qqchMeasureOrgParam){
        return toAjax(qqchMeasureOrgService.deleteQqchMeasureOrg(qqchMeasureOrgParam));
    }

            @PreAuthorize(hasPermi = "qqchMeasureOrg:remove")
        @PostMapping("/{ids}")
        public AjaxResult deleteQqchMeasureOrgByPks(@PathVariable Long[] ids){
            List<Long> qqchMeasureOrgPkList = Arrays.asList(ids);
            return toAjax(qqchMeasureOrgService.deleteQqchMeasureOrgByPks(qqchMeasureOrgPkList));
        }
    
    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchMeasureOrg qqchMeasureOrgParam) throws IOException {
        List<QqchMeasureOrg> qqchMeasureOrgList = qqchMeasureOrgService.getQqchMeasureOrgList(qqchMeasureOrgParam);
        ExcelUtils<QqchMeasureOrg> util = new ExcelUtils<>(QqchMeasureOrg.class);
        util.exportExcel(response, qqchMeasureOrgList, DateUtils.getDate());
    }
}
