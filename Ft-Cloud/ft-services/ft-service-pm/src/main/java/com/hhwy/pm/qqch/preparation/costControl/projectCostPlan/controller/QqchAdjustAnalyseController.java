package com.hhwy.pm.qqch.preparation.costControl.projectCostPlan.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.costControl.projectCostPlan.domain.QqchAdjustAnalyse;
import com.hhwy.pm.qqch.preparation.costControl.projectCostPlan.domain.vo.QqchAdjustAnalyseVo;
import com.hhwy.pm.qqch.preparation.costControl.projectCostPlan.service.IQqchAdjustAnalyseService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author han
 * @date 2023-08-04 10:45:37
 * @remark 调差分析
 */
@Validated
@RestController
@RequestMapping("/qqchAdjustAnalyse")
public class QqchAdjustAnalyseController extends BaseController {

    @Autowired
    private IQqchAdjustAnalyseService qqchAdjustAnalyseService;


    @PreAuthorize(hasPermi = "qqchAdjustAnalyse:list")
    @GetMapping
    public AjaxResult getQqchAdjustAnalyse(@Validated(ValidationGroups.Get.class) QqchAdjustAnalyse qqchAdjustAnalyseParam) {
        QqchAdjustAnalyse qqchAdjustAnalyse = qqchAdjustAnalyseService.getQqchAdjustAnalyse(qqchAdjustAnalyseParam);
        return AjaxResult.success(qqchAdjustAnalyse);
    }

    @PreAuthorize(hasPermi = "qqchAdjustAnalyse:list")
    @GetMapping("/list")
    public AjaxResult getQqchAdjustAnalyseList(@Validated(ValidationGroups.Select.class) QqchAdjustAnalyse qqchAdjustAnalyseParam) {
        startPage();
        List<QqchAdjustAnalyse> qqchAdjustAnalyseList = qqchAdjustAnalyseService.getQqchAdjustAnalyseList(qqchAdjustAnalyseParam);
        return getDataTableAjaxResult(qqchAdjustAnalyseList);
    }

    @PreAuthorize(hasPermi = "qqchAdjustAnalyse:add")
    @PostMapping("/add")
    public AjaxResult insertQqchAdjustAnalyse(@Validated(ValidationGroups.Save.class) @RequestBody QqchAdjustAnalyse qqchAdjustAnalyseParam) {
        qqchAdjustAnalyseService.insertQqchAdjustAnalyse(qqchAdjustAnalyseParam);
        return AjaxResult.success(qqchAdjustAnalyseParam);
    }

    @PreAuthorize(hasPermi = "qqchAdjustAnalyse:update")
    @PostMapping("/update")
    public AjaxResult updateQqchAdjustAnalyse(@Validated(ValidationGroups.Update.class) @RequestBody QqchAdjustAnalyse qqchAdjustAnalyseParam) {
        return toAjax(qqchAdjustAnalyseService.updateQqchAdjustAnalyse(qqchAdjustAnalyseParam));
    }

    @PreAuthorize(hasPermi = "qqchAdjustAnalyse:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchAdjustAnalyseList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchAdjustAnalyse> qqchAdjustAnalyseListParam) {
        return toAjax(qqchAdjustAnalyseService.updateQqchAdjustAnalyseList(qqchAdjustAnalyseListParam));
    }

    @PreAuthorize(hasPermi = "qqchAdjustAnalyse:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchAdjustAnalyse(@Validated(ValidationGroups.Delete.class) @RequestBody QqchAdjustAnalyse qqchAdjustAnalyseParam) {
        return toAjax(qqchAdjustAnalyseService.deleteQqchAdjustAnalyse(qqchAdjustAnalyseParam));
    }

    @PreAuthorize(hasPermi = "qqchAdjustAnalyse:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchAdjustAnalyseByPks(@PathVariable Long[] ids) {
        List<Long> qqchAdjustAnalysePkList = Arrays.asList(ids);
        return toAjax(qqchAdjustAnalyseService.deleteQqchAdjustAnalyseByPks(qqchAdjustAnalysePkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchAdjustAnalyse qqchAdjustAnalyseParam) throws IOException {
        List<QqchAdjustAnalyse> qqchAdjustAnalyseList = qqchAdjustAnalyseService.getQqchAdjustAnalyseList(qqchAdjustAnalyseParam);
        ExcelUtils<QqchAdjustAnalyse> util = new ExcelUtils<>(QqchAdjustAnalyse.class);
        util.exportExcel(response, qqchAdjustAnalyseList, DateUtils.getDate());
    }

    /**
     * 获取调差分析Vo
     * @param qqchAdjustAnalyse
     * @return
     */
    @PreAuthorize(hasPermi = "qqchAdjustAnalyse:list")
    @GetMapping("getQqchAdjustAnalyseVo")
    public AjaxResult getQqchAdjustAnalyseVo(@Validated(ValidationGroups.Get.class) QqchAdjustAnalyse qqchAdjustAnalyse) {
        QqchAdjustAnalyseVo qqchAdjustAnalyseVo = qqchAdjustAnalyseService.getQqchAdjustAnalyseVo(qqchAdjustAnalyse);
        return AjaxResult.success(qqchAdjustAnalyseVo);
    }

    /**
     * 保存/确认/提交
     * @param qqchAdjustAnalyseVo
     * @return
     */
    @PreAuthorize(hasPermi = "qqchAdjustAnalyse:add")
    @PostMapping("/save")
    public AjaxResult save(@RequestBody QqchAdjustAnalyseVo qqchAdjustAnalyseVo) {
        qqchAdjustAnalyseService.save(qqchAdjustAnalyseVo);
        return AjaxResult.success();
    }
}
