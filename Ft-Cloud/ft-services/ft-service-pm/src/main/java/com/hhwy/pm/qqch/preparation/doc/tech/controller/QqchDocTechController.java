package com.hhwy.pm.qqch.preparation.doc.tech.controller;

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
import com.hhwy.pm.qqch.preparation.doc.tech.service.IQqchDocTechService;
import com.hhwy.pm.qqch.preparation.doc.tech.domain.QqchDocTech;

import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.common.security.annotation.PreAuthorize;

/**
 * @author mls
 * @date 2023-07-25 18:25:45
 * @remark 
 */
@Validated
@RestController
@RequestMapping("/qqchDocTech")
public class QqchDocTechController extends BaseController{

    @Autowired
    private IQqchDocTechService qqchDocTechService;

                                                                                                                                                                                                                                                                            

    @PreAuthorize(hasPermi = "qqchDocTech:list")
    @GetMapping
    public AjaxResult getQqchDocTech(@Validated(ValidationGroups.Get.class)  QqchDocTech qqchDocTechParam){
        QqchDocTech qqchDocTech =  qqchDocTechService.getQqchDocTech(qqchDocTechParam);
        return AjaxResult.success(qqchDocTech);
    }

    @PreAuthorize(hasPermi = "qqchDocTech:list")
    @GetMapping("/list")
    public AjaxResult getQqchDocTechList(@Validated(ValidationGroups.Select.class) QqchDocTech qqchDocTechParam){
        startPage();
        List<QqchDocTech> qqchDocTechList = qqchDocTechService.getQqchDocTechList(qqchDocTechParam);
        return getDataTableAjaxResult(qqchDocTechList);
    }

    @PreAuthorize(hasPermi = "qqchDocTech:add")
    @PostMapping("/add")
    public AjaxResult insertQqchDocTech(@Validated(ValidationGroups.Save.class) @RequestBody QqchDocTech qqchDocTechParam){
        qqchDocTechService.insertQqchDocTech(qqchDocTechParam);
        return AjaxResult.success(qqchDocTechParam);
    }

    @PreAuthorize(hasPermi = "qqchDocTech:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchDocTechList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchDocTech> qqchDocTechListParam){
        qqchDocTechService.insertQqchDocTechList(qqchDocTechListParam);
        return AjaxResult.success(qqchDocTechListParam);
    }

    @PreAuthorize(hasPermi = "qqchDocTech:update")
    @PostMapping("/update")
    public AjaxResult updateQqchDocTech(@Validated(ValidationGroups.Update.class) @RequestBody QqchDocTech qqchDocTechParam){
        return toAjax(qqchDocTechService.updateQqchDocTech(qqchDocTechParam));
    }

            @PreAuthorize(hasPermi = "qqchDocTech:update")
        @PostMapping("/batchUpdate")
        public AjaxResult updateQqchDocTechList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchDocTech> qqchDocTechListParam){
            return toAjax(qqchDocTechService.updateQqchDocTechList(qqchDocTechListParam));
        }
    
    @PreAuthorize(hasPermi = "qqchDocTech:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchDocTech(@Validated(ValidationGroups.Delete.class) @RequestBody QqchDocTech qqchDocTechParam){
        return toAjax(qqchDocTechService.deleteQqchDocTech(qqchDocTechParam));
    }

            @PreAuthorize(hasPermi = "qqchDocTech:remove")
        @PostMapping("/{ids}")
        public AjaxResult deleteQqchDocTechByPks(@PathVariable Long[] ids){
            List<Long> qqchDocTechPkList = Arrays.asList(ids);
            return toAjax(qqchDocTechService.deleteQqchDocTechByPks(qqchDocTechPkList));
        }
    
    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchDocTech qqchDocTechParam) throws IOException {
        List<QqchDocTech> qqchDocTechList = qqchDocTechService.getQqchDocTechList(qqchDocTechParam);
        ExcelUtils<QqchDocTech> util = new ExcelUtils<>(QqchDocTech.class);
        util.exportExcel(response, qqchDocTechList, DateUtils.getDate());
    }
}
