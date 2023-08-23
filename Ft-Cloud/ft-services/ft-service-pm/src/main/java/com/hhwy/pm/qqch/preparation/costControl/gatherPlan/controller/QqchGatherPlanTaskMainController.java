package com.hhwy.pm.qqch.preparation.costControl.gatherPlan.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.costControl.gatherPlan.domain.QqchGatherPlanTaskMain;
import com.hhwy.pm.qqch.preparation.costControl.gatherPlan.domain.vo.GatherPlanTaskQueryVo;
import com.hhwy.pm.qqch.preparation.costControl.gatherPlan.domain.vo.QqchGatherPlanTaskMainVo;
import com.hhwy.pm.qqch.preparation.costControl.gatherPlan.service.IQqchGatherPlanTaskMainService;
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
 * @date 2023-08-23 11:10:38
 * @remark 成本数据采集计划任务表
 */
@Validated
@RestController
@RequestMapping("/qqchGatherPlanTaskMain")
public class QqchGatherPlanTaskMainController extends BaseController {

    @Autowired
    private IQqchGatherPlanTaskMainService qqchGatherPlanTaskMainService;


    @PreAuthorize(hasPermi = "qqchGatherPlanTaskMain:list")
    @GetMapping
    public AjaxResult getQqchGatherPlanTaskMain(@Validated(ValidationGroups.Get.class) QqchGatherPlanTaskMain qqchGatherPlanTaskMainParam) {
        QqchGatherPlanTaskMain qqchGatherPlanTaskMain = qqchGatherPlanTaskMainService.getQqchGatherPlanTaskMain(qqchGatherPlanTaskMainParam);
        return AjaxResult.success(qqchGatherPlanTaskMain);
    }

    @PreAuthorize(hasPermi = "qqchGatherPlanTaskMain:list")
    @GetMapping("/list")
    public AjaxResult getQqchGatherPlanTaskMainList(@Validated(ValidationGroups.Select.class) QqchGatherPlanTaskMain qqchGatherPlanTaskMainParam) {
        startPage();
        List<QqchGatherPlanTaskMain> qqchGatherPlanTaskMainList = qqchGatherPlanTaskMainService.getQqchGatherPlanTaskMainList(qqchGatherPlanTaskMainParam);
        return getDataTableAjaxResult(qqchGatherPlanTaskMainList);
    }

    @PreAuthorize(hasPermi = "qqchGatherPlanTaskMain:add")
    @PostMapping("/add")
    public AjaxResult insertQqchGatherPlanTaskMain(@Validated(ValidationGroups.Save.class) @RequestBody QqchGatherPlanTaskMain qqchGatherPlanTaskMainParam) {
        qqchGatherPlanTaskMainService.insertQqchGatherPlanTaskMain(qqchGatherPlanTaskMainParam);
        return AjaxResult.success(qqchGatherPlanTaskMainParam);
    }

    @PreAuthorize(hasPermi = "qqchGatherPlanTaskMain:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchGatherPlanTaskMainList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchGatherPlanTaskMain> qqchGatherPlanTaskMainListParam) {
        qqchGatherPlanTaskMainService.insertQqchGatherPlanTaskMainList(qqchGatherPlanTaskMainListParam);
        return AjaxResult.success(qqchGatherPlanTaskMainListParam);
    }

    @PreAuthorize(hasPermi = "qqchGatherPlanTaskMain:update")
    @PostMapping("/update")
    public AjaxResult updateQqchGatherPlanTaskMain(@Validated(ValidationGroups.Update.class) @RequestBody QqchGatherPlanTaskMain qqchGatherPlanTaskMainParam) {
        return toAjax(qqchGatherPlanTaskMainService.updateQqchGatherPlanTaskMain(qqchGatherPlanTaskMainParam));
    }

    @PreAuthorize(hasPermi = "qqchGatherPlanTaskMain:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchGatherPlanTaskMainList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchGatherPlanTaskMain> qqchGatherPlanTaskMainListParam) {
        return toAjax(qqchGatherPlanTaskMainService.updateQqchGatherPlanTaskMainList(qqchGatherPlanTaskMainListParam));
    }

    @PreAuthorize(hasPermi = "qqchGatherPlanTaskMain:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchGatherPlanTaskMain(@Validated(ValidationGroups.Delete.class) @RequestBody QqchGatherPlanTaskMain qqchGatherPlanTaskMainParam) {
        return toAjax(qqchGatherPlanTaskMainService.deleteQqchGatherPlanTaskMain(qqchGatherPlanTaskMainParam));
    }

    @PreAuthorize(hasPermi = "qqchGatherPlanTaskMain:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchGatherPlanTaskMainByPks(@PathVariable Long[] ids) {
        List<Long> qqchGatherPlanTaskMainPkList = Arrays.asList(ids);
        return toAjax(qqchGatherPlanTaskMainService.deleteQqchGatherPlanTaskMainByPks(qqchGatherPlanTaskMainPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchGatherPlanTaskMain qqchGatherPlanTaskMainParam) throws IOException {
        List<QqchGatherPlanTaskMain> qqchGatherPlanTaskMainList = qqchGatherPlanTaskMainService.getQqchGatherPlanTaskMainList(qqchGatherPlanTaskMainParam);
        ExcelUtils<QqchGatherPlanTaskMain> util = new ExcelUtils<>(QqchGatherPlanTaskMain.class);
        util.exportExcel(response, qqchGatherPlanTaskMainList, DateUtils.getDate());
    }

    /**
     * 台账Vo
     *
     * @param queryVo 版本
     * @return
     */
    @GetMapping("getQqchGatherPlanTaskMainVo")
    public AjaxResult getQqchGatherPlanTaskMainVo(GatherPlanTaskQueryVo queryVo) {
        QqchGatherPlanTaskMainVo qqchGatherPlanTaskMainVo = qqchGatherPlanTaskMainService.getQqchGatherPlanTaskMainVo(queryVo);
        return AjaxResult.success(qqchGatherPlanTaskMainVo);
    }

    /**
     * 保存/确认/提交
     * @param qqchGatherPlanTaskMainVo
     * @return
     */
    @PreAuthorize(hasPermi = "qqchGatherPlanTaskMain:save")
    @PostMapping("/save")
    public AjaxResult save(@RequestBody QqchGatherPlanTaskMainVo qqchGatherPlanTaskMainVo) {
        qqchGatherPlanTaskMainService.save(qqchGatherPlanTaskMainVo);
        return AjaxResult.success();
    }
}
