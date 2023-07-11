package com.hhwy.pm.qqch.preparation.survey.optimize.controller;

import java.util.Arrays;
import java.util.List;
import java.io.IOException;

import com.hhwy.pm.qqch.preparation.survey.optimize.domain.QqchDesignTechnologyOptimize;
import com.hhwy.pm.qqch.preparation.survey.optimize.service.IQqchDesignTechnologyOptimizeService;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.common.security.annotation.PreAuthorize;

/**
 * @author han
 * @date 2023-07-07 18:35:48
 * @remark 设计技术优化要点
 */
@Validated
@RestController
@RequestMapping("/qqchDesignTechnologyOptimize")
public class QqchDesignTechnologyOptimizeController extends BaseController {

    @Autowired
    private IQqchDesignTechnologyOptimizeService qqchDesignTechnologyOptimizeService;


    @PreAuthorize(hasPermi = "qqchDesignTechnologyOptimize:list")
    @GetMapping
    public AjaxResult getQqchDesignTechnologyOptimize(@Validated(ValidationGroups.Get.class) @RequestBody QqchDesignTechnologyOptimize qqchDesignTechnologyOptimizeParam) {
        QqchDesignTechnologyOptimize qqchDesignTechnologyOptimize = qqchDesignTechnologyOptimizeService.getQqchDesignTechnologyOptimize(qqchDesignTechnologyOptimizeParam);
        return AjaxResult.success(qqchDesignTechnologyOptimize);
    }

    /**
     * 设计技术优化要点台账
     * @return
     */
    @PreAuthorize(hasPermi = "qqchDesignTechnologyOptimize:list")
    @GetMapping("/list")
    public AjaxResult getQqchDesignTechnologyOptimizeList() {
        List<QqchDesignTechnologyOptimize> qqchDesignTechnologyOptimizeList = qqchDesignTechnologyOptimizeService.getQqchDesignTechnologyOptimizeList();
        return AjaxResult.success(qqchDesignTechnologyOptimizeList);
    }

    /**
     * 批量编辑（新增和修改）
     * @param qqchDesignTechnologyOptimizeListParam
     * @return
     */
    @PreAuthorize(hasPermi = "qqchDesignTechnologyOptimize:update")
    @PostMapping("/batchEdit")
    public AjaxResult editQqchDesignTechnologyOptimizeList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchDesignTechnologyOptimize> qqchDesignTechnologyOptimizeListParam) {
        return toAjax(qqchDesignTechnologyOptimizeService.editQqchDesignTechnologyOptimizeList(qqchDesignTechnologyOptimizeListParam));
    }

    @PreAuthorize(hasPermi = "qqchDesignTechnologyOptimize:add")
    @PostMapping("/add")
    public AjaxResult insertQqchDesignTechnologyOptimize(@Validated(ValidationGroups.Save.class) @RequestBody QqchDesignTechnologyOptimize qqchDesignTechnologyOptimizeParam) {
        qqchDesignTechnologyOptimizeService.insertQqchDesignTechnologyOptimize(qqchDesignTechnologyOptimizeParam);
        return AjaxResult.success(qqchDesignTechnologyOptimizeParam);
    }

    @PreAuthorize(hasPermi = "qqchDesignTechnologyOptimize:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchDesignTechnologyOptimizeList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchDesignTechnologyOptimize> qqchDesignTechnologyOptimizeListParam) {
        qqchDesignTechnologyOptimizeService.insertQqchDesignTechnologyOptimizeList(qqchDesignTechnologyOptimizeListParam);
        return AjaxResult.success(qqchDesignTechnologyOptimizeListParam);
    }

    @PreAuthorize(hasPermi = "qqchDesignTechnologyOptimize:update")
    @PostMapping("/update")
    public AjaxResult updateQqchDesignTechnologyOptimize(@Validated(ValidationGroups.Update.class) @RequestBody QqchDesignTechnologyOptimize qqchDesignTechnologyOptimizeParam) {
        return toAjax(qqchDesignTechnologyOptimizeService.updateQqchDesignTechnologyOptimize(qqchDesignTechnologyOptimizeParam));
    }

    @PreAuthorize(hasPermi = "qqchDesignTechnologyOptimize:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchDesignTechnologyOptimizeList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchDesignTechnologyOptimize> qqchDesignTechnologyOptimizeListParam) {
        return toAjax(qqchDesignTechnologyOptimizeService.updateQqchDesignTechnologyOptimizeList(qqchDesignTechnologyOptimizeListParam));
    }

    @PreAuthorize(hasPermi = "qqchDesignTechnologyOptimize:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchDesignTechnologyOptimize(@Validated(ValidationGroups.Delete.class) @RequestBody QqchDesignTechnologyOptimize qqchDesignTechnologyOptimizeParam) {
        return toAjax(qqchDesignTechnologyOptimizeService.deleteQqchDesignTechnologyOptimize(qqchDesignTechnologyOptimizeParam));
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @PreAuthorize(hasPermi = "qqchDesignTechnologyOptimize:remove")
    @PostMapping("/remove/{ids}")
    public AjaxResult deleteQqchDesignTechnologyOptimizeByPks(@PathVariable Long[] ids) {
        List<Long> qqchDesignTechnologyOptimizePkList = Arrays.asList(ids);
        return toAjax(qqchDesignTechnologyOptimizeService.deleteQqchDesignTechnologyOptimizeByPks(qqchDesignTechnologyOptimizePkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchDesignTechnologyOptimize qqchDesignTechnologyOptimizeParam) throws IOException {
        List<QqchDesignTechnologyOptimize> qqchDesignTechnologyOptimizeList = qqchDesignTechnologyOptimizeService.getQqchDesignTechnologyOptimizeList();
        ExcelUtils<QqchDesignTechnologyOptimize> util = new ExcelUtils<>(QqchDesignTechnologyOptimize.class);
        util.exportExcel(response, qqchDesignTechnologyOptimizeList, DateUtils.getDate());
    }
}
