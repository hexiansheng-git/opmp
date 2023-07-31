package com.hhwy.pm.qqch.sgch.sche.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.sgch.sche.domain.QqchScheAnalyse;
import com.hhwy.pm.qqch.sgch.sche.service.IQqchScheAnalyseService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author mls
 * @date 2023-07-31 11:22:46
 * @remark
 */
@Validated
@RestController
@RequestMapping("/qqchScheAnalyse")
public class QqchScheAnalyseController extends BaseController {

    @Autowired
    private IQqchScheAnalyseService qqchScheAnalyseService;


    @PreAuthorize(hasPermi = "qqchScheAnalyse:list")
    @GetMapping
    public AjaxResult getQqchScheAnalyse(@Validated(ValidationGroups.Get.class) QqchScheAnalyse qqchScheAnalyseParam) {
        QqchScheAnalyse qqchScheAnalyse = qqchScheAnalyseService.getQqchScheAnalyse(qqchScheAnalyseParam);
        return AjaxResult.success(qqchScheAnalyse);
    }

    @PreAuthorize(hasPermi = "qqchScheAnalyse:list")
    @GetMapping("/list")
    public AjaxResult getQqchScheAnalyseList(@Validated(ValidationGroups.Select.class) QqchScheAnalyse qqchScheAnalyseParam) {
        startPage();
        List<QqchScheAnalyse> qqchScheAnalyseList = qqchScheAnalyseService.getQqchScheAnalyseList(qqchScheAnalyseParam);
        return getDataTableAjaxResult(qqchScheAnalyseList);
    }

    @PreAuthorize(hasPermi = "qqchScheAnalyse:add")
    @PostMapping("/add")
    public AjaxResult insertQqchScheAnalyse(@Validated(ValidationGroups.Save.class) @RequestBody QqchScheAnalyse qqchScheAnalyseParam) {
        qqchScheAnalyseService.insertQqchScheAnalyse(qqchScheAnalyseParam);
        return AjaxResult.success(qqchScheAnalyseParam);
    }

    @PreAuthorize(hasPermi = "qqchScheAnalyse:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchScheAnalyseList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchScheAnalyse> qqchScheAnalyseListParam) {
        qqchScheAnalyseService.insertQqchScheAnalyseList(qqchScheAnalyseListParam);
        return AjaxResult.success(qqchScheAnalyseListParam);
    }

    @PreAuthorize(hasPermi = "qqchScheAnalyse:update")
    @PostMapping("/update")
    public AjaxResult updateQqchScheAnalyse(@Validated(ValidationGroups.Update.class) @RequestBody QqchScheAnalyse qqchScheAnalyseParam) {
        return toAjax(qqchScheAnalyseService.updateQqchScheAnalyse(qqchScheAnalyseParam));
    }

    @PreAuthorize(hasPermi = "qqchScheAnalyse:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchScheAnalyseList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchScheAnalyse> qqchScheAnalyseListParam) {
        return toAjax(qqchScheAnalyseService.updateQqchScheAnalyseList(qqchScheAnalyseListParam));
    }

    @PreAuthorize(hasPermi = "qqchScheAnalyse:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchScheAnalyse(@Validated(ValidationGroups.Delete.class) @RequestBody QqchScheAnalyse qqchScheAnalyseParam) {
        return toAjax(qqchScheAnalyseService.deleteQqchScheAnalyse(qqchScheAnalyseParam));
    }

    @PreAuthorize(hasPermi = "qqchScheAnalyse:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchScheAnalyseByPks(@PathVariable Long[] ids) {
        List<Long> qqchScheAnalysePkList = Arrays.asList(ids);
        return toAjax(qqchScheAnalyseService.deleteQqchScheAnalyseByPks(qqchScheAnalysePkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchScheAnalyse qqchScheAnalyseParam) throws IOException {
        List<QqchScheAnalyse> qqchScheAnalyseList = qqchScheAnalyseService.getQqchScheAnalyseList(qqchScheAnalyseParam);
        ExcelUtils<QqchScheAnalyse> util = new ExcelUtils<>(QqchScheAnalyse.class);
        util.exportExcel(response, qqchScheAnalyseList, DateUtils.getDate());
    }
}
