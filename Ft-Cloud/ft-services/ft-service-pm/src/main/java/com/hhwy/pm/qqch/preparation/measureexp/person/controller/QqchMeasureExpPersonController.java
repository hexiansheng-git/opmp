package com.hhwy.pm.qqch.preparation.measureexp.person.controller;

import java.util.Arrays;
import java.util.List;
import java.io.IOException;

import com.hhwy.pm.qqch.preparation.measureexp.person.service.IQqchMeasureExpPersonService;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import com.hhwy.pm.qqch.preparation.measureexp.person.domain.QqchMeasureExpPerson;

import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.common.security.annotation.PreAuthorize;

/**
 * @author mls
 * @date 2023-07-25 18:01:30
 * @remark 
 */
@Validated
@RestController
@RequestMapping("/qqchMeasureExpPerson")
public class QqchMeasureExpPersonController extends BaseController{

    @Autowired
    private IQqchMeasureExpPersonService qqchMeasureExpPersonService;

                                                                                                                                                                                                                                                                                                                            

    @PreAuthorize(hasPermi = "qqchMeasureExpPerson:list")
    @GetMapping
    public AjaxResult getQqchMeasureExpPerson(@Validated(ValidationGroups.Get.class)  QqchMeasureExpPerson qqchMeasureExpPersonParam){
        QqchMeasureExpPerson qqchMeasureExpPerson =  qqchMeasureExpPersonService.getQqchMeasureExpPerson(qqchMeasureExpPersonParam);
        return AjaxResult.success(qqchMeasureExpPerson);
    }

    @PreAuthorize(hasPermi = "qqchMeasureExpPerson:list")
    @GetMapping("/list")
    public AjaxResult getQqchMeasureExpPersonList(@Validated(ValidationGroups.Select.class) QqchMeasureExpPerson qqchMeasureExpPersonParam){
        startPage();
        List<QqchMeasureExpPerson> qqchMeasureExpPersonList = qqchMeasureExpPersonService.getQqchMeasureExpPersonList(qqchMeasureExpPersonParam);
        return getDataTableAjaxResult(qqchMeasureExpPersonList);
    }

    @PreAuthorize(hasPermi = "qqchMeasureExpPerson:add")
    @PostMapping("/add")
    public AjaxResult insertQqchMeasureExpPerson(@Validated(ValidationGroups.Save.class) @RequestBody QqchMeasureExpPerson qqchMeasureExpPersonParam){
        qqchMeasureExpPersonService.insertQqchMeasureExpPerson(qqchMeasureExpPersonParam);
        return AjaxResult.success(qqchMeasureExpPersonParam);
    }

    @PreAuthorize(hasPermi = "qqchMeasureExpPerson:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchMeasureExpPersonList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchMeasureExpPerson> qqchMeasureExpPersonListParam){
        qqchMeasureExpPersonService.insertQqchMeasureExpPersonList(qqchMeasureExpPersonListParam);
        return AjaxResult.success(qqchMeasureExpPersonListParam);
    }

    @PreAuthorize(hasPermi = "qqchMeasureExpPerson:update")
    @PostMapping("/update")
    public AjaxResult updateQqchMeasureExpPerson(@Validated(ValidationGroups.Update.class) @RequestBody QqchMeasureExpPerson qqchMeasureExpPersonParam){
        return toAjax(qqchMeasureExpPersonService.updateQqchMeasureExpPerson(qqchMeasureExpPersonParam));
    }

            @PreAuthorize(hasPermi = "qqchMeasureExpPerson:update")
        @PostMapping("/batchUpdate")
        public AjaxResult updateQqchMeasureExpPersonList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchMeasureExpPerson> qqchMeasureExpPersonListParam){
            return toAjax(qqchMeasureExpPersonService.updateQqchMeasureExpPersonList(qqchMeasureExpPersonListParam));
        }
    
    @PreAuthorize(hasPermi = "qqchMeasureExpPerson:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchMeasureExpPerson(@Validated(ValidationGroups.Delete.class) @RequestBody QqchMeasureExpPerson qqchMeasureExpPersonParam){
        return toAjax(qqchMeasureExpPersonService.deleteQqchMeasureExpPerson(qqchMeasureExpPersonParam));
    }

            @PreAuthorize(hasPermi = "qqchMeasureExpPerson:remove")
        @PostMapping("/{ids}")
        public AjaxResult deleteQqchMeasureExpPersonByPks(@PathVariable Long[] ids){
            List<Long> qqchMeasureExpPersonPkList = Arrays.asList(ids);
            return toAjax(qqchMeasureExpPersonService.deleteQqchMeasureExpPersonByPks(qqchMeasureExpPersonPkList));
        }
    
    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchMeasureExpPerson qqchMeasureExpPersonParam) throws IOException {
        List<QqchMeasureExpPerson> qqchMeasureExpPersonList = qqchMeasureExpPersonService.getQqchMeasureExpPersonList(qqchMeasureExpPersonParam);
        ExcelUtils<QqchMeasureExpPerson> util = new ExcelUtils<>(QqchMeasureExpPerson.class);
        util.exportExcel(response, qqchMeasureExpPersonList, DateUtils.getDate());
    }
}
