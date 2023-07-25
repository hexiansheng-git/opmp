package com.hhwy.pm.qqch.preparation.doc.dwg.controller;

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
import com.hhwy.pm.qqch.preparation.doc.dwg.service.IQqchDocDwgService;
import com.hhwy.pm.qqch.preparation.doc.dwg.domain.QqchDocDwg;

import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.common.security.annotation.PreAuthorize;

/**
 * @author mls
 * @date 2023-07-25 18:25:42
 * @remark 
 */
@Validated
@RestController
@RequestMapping("/qqchDocDwg")
public class QqchDocDwgController extends BaseController{

    @Autowired
    private IQqchDocDwgService qqchDocDwgService;

                                                                                                                                                                                                                                                                                                                                        

    @PreAuthorize(hasPermi = "qqchDocDwg:list")
    @GetMapping
    public AjaxResult getQqchDocDwg(@Validated(ValidationGroups.Get.class)  QqchDocDwg qqchDocDwgParam){
        QqchDocDwg qqchDocDwg =  qqchDocDwgService.getQqchDocDwg(qqchDocDwgParam);
        return AjaxResult.success(qqchDocDwg);
    }

    @PreAuthorize(hasPermi = "qqchDocDwg:list")
    @GetMapping("/list")
    public AjaxResult getQqchDocDwgList(@Validated(ValidationGroups.Select.class) QqchDocDwg qqchDocDwgParam){
        startPage();
        List<QqchDocDwg> qqchDocDwgList = qqchDocDwgService.getQqchDocDwgList(qqchDocDwgParam);
        return getDataTableAjaxResult(qqchDocDwgList);
    }

    @PreAuthorize(hasPermi = "qqchDocDwg:add")
    @PostMapping("/add")
    public AjaxResult insertQqchDocDwg(@Validated(ValidationGroups.Save.class) @RequestBody QqchDocDwg qqchDocDwgParam){
        qqchDocDwgService.insertQqchDocDwg(qqchDocDwgParam);
        return AjaxResult.success(qqchDocDwgParam);
    }

    @PreAuthorize(hasPermi = "qqchDocDwg:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchDocDwgList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchDocDwg> qqchDocDwgListParam){
        qqchDocDwgService.insertQqchDocDwgList(qqchDocDwgListParam);
        return AjaxResult.success(qqchDocDwgListParam);
    }

    @PreAuthorize(hasPermi = "qqchDocDwg:update")
    @PostMapping("/update")
    public AjaxResult updateQqchDocDwg(@Validated(ValidationGroups.Update.class) @RequestBody QqchDocDwg qqchDocDwgParam){
        return toAjax(qqchDocDwgService.updateQqchDocDwg(qqchDocDwgParam));
    }

            @PreAuthorize(hasPermi = "qqchDocDwg:update")
        @PostMapping("/batchUpdate")
        public AjaxResult updateQqchDocDwgList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchDocDwg> qqchDocDwgListParam){
            return toAjax(qqchDocDwgService.updateQqchDocDwgList(qqchDocDwgListParam));
        }
    
    @PreAuthorize(hasPermi = "qqchDocDwg:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchDocDwg(@Validated(ValidationGroups.Delete.class) @RequestBody QqchDocDwg qqchDocDwgParam){
        return toAjax(qqchDocDwgService.deleteQqchDocDwg(qqchDocDwgParam));
    }

            @PreAuthorize(hasPermi = "qqchDocDwg:remove")
        @PostMapping("/{ids}")
        public AjaxResult deleteQqchDocDwgByPks(@PathVariable Long[] ids){
            List<Long> qqchDocDwgPkList = Arrays.asList(ids);
            return toAjax(qqchDocDwgService.deleteQqchDocDwgByPks(qqchDocDwgPkList));
        }
    
    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchDocDwg qqchDocDwgParam) throws IOException {
        List<QqchDocDwg> qqchDocDwgList = qqchDocDwgService.getQqchDocDwgList(qqchDocDwgParam);
        ExcelUtils<QqchDocDwg> util = new ExcelUtils<>(QqchDocDwg.class);
        util.exportExcel(response, qqchDocDwgList, DateUtils.getDate());
    }
}
