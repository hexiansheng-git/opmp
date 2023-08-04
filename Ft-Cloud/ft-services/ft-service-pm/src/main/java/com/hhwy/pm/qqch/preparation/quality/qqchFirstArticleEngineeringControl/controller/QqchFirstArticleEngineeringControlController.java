package com.hhwy.pm.qqch.preparation.quality.qqchFirstArticleEngineeringControl.controller;

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
import com.hhwy.pm.qqch.preparation.quality.qqchFirstArticleEngineeringControl.service.IQqchFirstArticleEngineeringControlService;
import com.hhwy.pm.qqch.preparation.quality.qqchFirstArticleEngineeringControl.domain.QqchFirstArticleEngineeringControl;

import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.common.security.annotation.PreAuthorize;

/**
 * @author ldd
 * @date 2023-08-04 17:06:12
 * @remark 
 */
@Validated
@RestController
@RequestMapping("/qqchFirstArticleEngineeringControl")
public class QqchFirstArticleEngineeringControlController extends BaseController{

    @Autowired
    private IQqchFirstArticleEngineeringControlService qqchFirstArticleEngineeringControlService;

                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    

    @PreAuthorize(hasPermi = "qqchFirstArticleEngineeringControl:list")
    @GetMapping
    public AjaxResult getQqchFirstArticleEngineeringControl(@Validated(ValidationGroups.Get.class)  QqchFirstArticleEngineeringControl qqchFirstArticleEngineeringControlParam){
        QqchFirstArticleEngineeringControl qqchFirstArticleEngineeringControl =  qqchFirstArticleEngineeringControlService.getQqchFirstArticleEngineeringControl(qqchFirstArticleEngineeringControlParam);
        return AjaxResult.success(qqchFirstArticleEngineeringControl);
    }

    @PreAuthorize(hasPermi = "qqchFirstArticleEngineeringControl:list")
    @GetMapping("/list")
    public AjaxResult getQqchFirstArticleEngineeringControlList(@Validated(ValidationGroups.Select.class) QqchFirstArticleEngineeringControl qqchFirstArticleEngineeringControlParam){
        startPage();
        List<QqchFirstArticleEngineeringControl> qqchFirstArticleEngineeringControlList = qqchFirstArticleEngineeringControlService.getQqchFirstArticleEngineeringControlList(qqchFirstArticleEngineeringControlParam);
        return getDataTableAjaxResult(qqchFirstArticleEngineeringControlList);
    }

    @PreAuthorize(hasPermi = "qqchFirstArticleEngineeringControl:add")
    @PostMapping("/add")
    public AjaxResult insertQqchFirstArticleEngineeringControl(@Validated(ValidationGroups.Save.class) @RequestBody QqchFirstArticleEngineeringControl qqchFirstArticleEngineeringControlParam){
        qqchFirstArticleEngineeringControlService.insertQqchFirstArticleEngineeringControl(qqchFirstArticleEngineeringControlParam);
        return AjaxResult.success(qqchFirstArticleEngineeringControlParam);
    }

    @PreAuthorize(hasPermi = "qqchFirstArticleEngineeringControl:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchFirstArticleEngineeringControlList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchFirstArticleEngineeringControl> qqchFirstArticleEngineeringControlListParam){
        qqchFirstArticleEngineeringControlService.insertQqchFirstArticleEngineeringControlList(qqchFirstArticleEngineeringControlListParam);
        return AjaxResult.success(qqchFirstArticleEngineeringControlListParam);
    }

    @PreAuthorize(hasPermi = "qqchFirstArticleEngineeringControl:update")
    @PostMapping("/update")
    public AjaxResult updateQqchFirstArticleEngineeringControl(@Validated(ValidationGroups.Update.class) @RequestBody QqchFirstArticleEngineeringControl qqchFirstArticleEngineeringControlParam){
        return toAjax(qqchFirstArticleEngineeringControlService.updateQqchFirstArticleEngineeringControl(qqchFirstArticleEngineeringControlParam));
    }

            @PreAuthorize(hasPermi = "qqchFirstArticleEngineeringControl:update")
        @PostMapping("/batchUpdate")
        public AjaxResult updateQqchFirstArticleEngineeringControlList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchFirstArticleEngineeringControl> qqchFirstArticleEngineeringControlListParam){
            return toAjax(qqchFirstArticleEngineeringControlService.updateQqchFirstArticleEngineeringControlList(qqchFirstArticleEngineeringControlListParam));
        }
    
    @PreAuthorize(hasPermi = "qqchFirstArticleEngineeringControl:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchFirstArticleEngineeringControl(@Validated(ValidationGroups.Delete.class) @RequestBody QqchFirstArticleEngineeringControl qqchFirstArticleEngineeringControlParam){
        return toAjax(qqchFirstArticleEngineeringControlService.deleteQqchFirstArticleEngineeringControl(qqchFirstArticleEngineeringControlParam));
    }

            @PreAuthorize(hasPermi = "qqchFirstArticleEngineeringControl:remove")
        @PostMapping("/{ids}")
        public AjaxResult deleteQqchFirstArticleEngineeringControlByPks(@PathVariable Long[] ids){
            List<Long> qqchFirstArticleEngineeringControlPkList = Arrays.asList(ids);
            return toAjax(qqchFirstArticleEngineeringControlService.deleteQqchFirstArticleEngineeringControlByPks(qqchFirstArticleEngineeringControlPkList));
        }
    
    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchFirstArticleEngineeringControl qqchFirstArticleEngineeringControlParam) throws IOException {
        List<QqchFirstArticleEngineeringControl> qqchFirstArticleEngineeringControlList = qqchFirstArticleEngineeringControlService.getQqchFirstArticleEngineeringControlList(qqchFirstArticleEngineeringControlParam);
        ExcelUtils<QqchFirstArticleEngineeringControl> util = new ExcelUtils<>(QqchFirstArticleEngineeringControl.class);
        util.exportExcel(response, qqchFirstArticleEngineeringControlList, DateUtils.getDate());
    }
}
