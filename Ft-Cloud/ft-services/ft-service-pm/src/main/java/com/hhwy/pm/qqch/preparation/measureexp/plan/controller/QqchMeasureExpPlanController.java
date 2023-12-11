package com.hhwy.pm.qqch.preparation.measureexp.plan.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qqch.common.domain.CompileEntity;
import com.hhwy.pm.qqch.preparation.measureexp.plan.domain.QqchMeasureExpPlan;
import com.hhwy.pm.qqch.preparation.measureexp.plan.service.IQqchMeasureExpPlanService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * 工作计划
 * @author mls
 * @date 2023-07-25 18:01:32
 * @remark
 */
@Validated
@RestController
@RequestMapping("/qqchMeasureExpPlan")
public class QqchMeasureExpPlanController extends BaseController {

    @Autowired
    private IQqchMeasureExpPlanService qqchMeasureExpPlanService;

    @Autowired
    private IQqchReviewService reviewService;



//    @PreAuthorize(hasPermi = "qqchMeasureExpPlan:list")
    @GetMapping
    public AjaxResult getQqchMeasureExpPlan(@Validated(ValidationGroups.Get.class) QqchMeasureExpPlan qqchMeasureExpPlanParam) {
        QqchMeasureExpPlan qqchMeasureExpPlan = qqchMeasureExpPlanService.getQqchMeasureExpPlan(qqchMeasureExpPlanParam);
        return AjaxResult.success(qqchMeasureExpPlan);
    }

//    @PreAuthorize(hasPermi = "qqchMeasureExpPlan:list")
    @GetMapping("/list")
    public AjaxResult getQqchMeasureExpPlanList(@Validated(ValidationGroups.Select.class) QqchMeasureExpPlan qqchMeasureExpPlanParam) {
        CompileEntity res = new CompileEntity<>();
        List<QqchMeasureExpPlan> qqchMeasureExpPlanList = qqchMeasureExpPlanService.getQqchMeasureExpPlanListByVersion(qqchMeasureExpPlanParam);
        res.setDto(qqchMeasureExpPlanList);
        res.setVersion(qqchMeasureExpPlanParam.getVersion());
        res.setStageIdentity(reviewService.getStage());
        return AjaxResult.success(res);
    }

    /**
     * 3.7.1 测量管理计划
     * @return
     */
    @GetMapping("/feignList")
    public AjaxResult feignList() {
        QqchMeasureExpPlan qqchMeasureExpPlanParam = new QqchMeasureExpPlan();
        qqchMeasureExpPlanParam.setDataType("1"); //1-测量管理计划 2-实验管理计划
        return this.getQqchMeasureExpPlanList(qqchMeasureExpPlanParam);
    }

    /**
     * 3.7.2 实验工作计划 最新生效数据
     * @return
     */
    @GetMapping("/feignPlanList")
    public AjaxResult feignPlanList() {
        QqchMeasureExpPlan plan = new QqchMeasureExpPlan();
        plan.setDataType("2");
        return this.getQqchMeasureExpPlanList(plan);
    }


//    @PreAuthorize(hasPermi = "qqchMeasureExpPlan:add")
    @PostMapping("/save")
    public AjaxResult save(@Validated(ValidationGroups.Save.class) @RequestBody CompileEntity<List<QqchMeasureExpPlan>> map) {
        qqchMeasureExpPlanService.saveTree(map);
        return AjaxResult.success("操作成功");
    }


//    @PreAuthorize(hasPermi = "qqchMeasureExpPlan:add")
    @PostMapping("/add")
    public AjaxResult insertQqchMeasureExpPlan(@Validated(ValidationGroups.Save.class) @RequestBody QqchMeasureExpPlan qqchMeasureExpPlanParam) {
        qqchMeasureExpPlanService.insertQqchMeasureExpPlan(qqchMeasureExpPlanParam);
        return AjaxResult.success(qqchMeasureExpPlanParam);
    }

//    @PreAuthorize(hasPermi = "qqchMeasureExpPlan:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchMeasureExpPlanList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchMeasureExpPlan> qqchMeasureExpPlanListParam) {
        qqchMeasureExpPlanService.insertQqchMeasureExpPlanList(qqchMeasureExpPlanListParam);
        return AjaxResult.success(qqchMeasureExpPlanListParam);
    }

//    @PreAuthorize(hasPermi = "qqchMeasureExpPlan:update")
    @PostMapping("/update")
    public AjaxResult updateQqchMeasureExpPlan(@Validated(ValidationGroups.Update.class) @RequestBody QqchMeasureExpPlan qqchMeasureExpPlanParam) {
        return toAjax(qqchMeasureExpPlanService.updateQqchMeasureExpPlan(qqchMeasureExpPlanParam));
    }

//    @PreAuthorize(hasPermi = "qqchMeasureExpPlan:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchMeasureExpPlanList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchMeasureExpPlan> qqchMeasureExpPlanListParam) {
        return toAjax(qqchMeasureExpPlanService.updateQqchMeasureExpPlanList(qqchMeasureExpPlanListParam));
    }

//    @PreAuthorize(hasPermi = "qqchMeasureExpPlan:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchMeasureExpPlan(@Validated(ValidationGroups.Delete.class) @RequestBody QqchMeasureExpPlan qqchMeasureExpPlanParam) {
        return toAjax(qqchMeasureExpPlanService.deleteQqchMeasureExpPlan(qqchMeasureExpPlanParam));
    }

//    @PreAuthorize(hasPermi = "qqchMeasureExpPlan:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchMeasureExpPlanByPks(@PathVariable Long[] ids) {
        List<Long> qqchMeasureExpPlanPkList = Arrays.asList(ids);
        return toAjax(qqchMeasureExpPlanService.deleteQqchMeasureExpPlanByPks(qqchMeasureExpPlanPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchMeasureExpPlan qqchMeasureExpPlanParam) throws IOException {
        List<QqchMeasureExpPlan> qqchMeasureExpPlanList = qqchMeasureExpPlanService.getQqchMeasureExpPlanList(qqchMeasureExpPlanParam);
        ExcelUtils<QqchMeasureExpPlan> util = new ExcelUtils<>(QqchMeasureExpPlan.class);
        util.exportExcel(response, qqchMeasureExpPlanList, DateUtils.getDate());
    }

}
