package com.hhwy.pm.qqch.preparation.technique.techTrainPlan.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
//import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.technique.techTrainPlan.domain.QqchTechTrainPlan;
import com.hhwy.pm.qqch.preparation.technique.techTrainPlan.domain.vo.QqchTechTrainPlanVo;
import com.hhwy.pm.qqch.preparation.technique.techTrainPlan.service.IQqchTechTrainPlanService;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
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
 * @date 2023-07-25 10:57:39
 * @remark 技术培训策划
 */
@Validated
@RestController
@RequestMapping("/qqchTechTrainPlan")
public class QqchTechTrainPlanController extends BaseController {

    @Autowired
    private IQqchTechTrainPlanService qqchTechTrainPlanService;


//    @PreAuthorize(hasPermi = "qqchTechTrainPlan:list")
    @GetMapping
    public AjaxResult getQqchTechTrainPlan(@Validated(ValidationGroups.Get.class) QqchTechTrainPlan qqchTechTrainPlanParam) {
        QqchTechTrainPlan qqchTechTrainPlan = qqchTechTrainPlanService.getQqchTechTrainPlan(qqchTechTrainPlanParam);
        return AjaxResult.success(qqchTechTrainPlan);
    }

//    @PreAuthorize(hasPermi = "qqchTechTrainPlan:list")
    @GetMapping("/list")
    public AjaxResult getQqchTechTrainPlanList(@Validated(ValidationGroups.Select.class) QqchTechTrainPlan qqchTechTrainPlanParam) {
        startPage();
        List<QqchTechTrainPlan> qqchTechTrainPlanList = qqchTechTrainPlanService.getQqchTechTrainPlanList(qqchTechTrainPlanParam);
        return getDataTableAjaxResult(qqchTechTrainPlanList);
    }

//    @PreAuthorize(hasPermi = "qqchTechTrainPlan:add")
    @PostMapping("/add")
    public AjaxResult insertQqchTechTrainPlan(@Validated(ValidationGroups.Save.class) @RequestBody QqchTechTrainPlan qqchTechTrainPlanParam) {
        qqchTechTrainPlanService.insertQqchTechTrainPlan(qqchTechTrainPlanParam);
        return AjaxResult.success(qqchTechTrainPlanParam);
    }

//    @PreAuthorize(hasPermi = "qqchTechTrainPlan:update")
    @PostMapping("/update")
    public AjaxResult updateQqchTechTrainPlan(@Validated(ValidationGroups.Update.class) @RequestBody QqchTechTrainPlan qqchTechTrainPlanParam) {
        return toAjax(qqchTechTrainPlanService.updateQqchTechTrainPlan(qqchTechTrainPlanParam));
    }

//    @PreAuthorize(hasPermi = "qqchTechTrainPlan:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchTechTrainPlanList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchTechTrainPlan> qqchTechTrainPlanListParam) {
        return toAjax(qqchTechTrainPlanService.updateQqchTechTrainPlanList(qqchTechTrainPlanListParam));
    }

//    @PreAuthorize(hasPermi = "qqchTechTrainPlan:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchTechTrainPlan(@Validated(ValidationGroups.Delete.class) @RequestBody QqchTechTrainPlan qqchTechTrainPlanParam) {
        return toAjax(qqchTechTrainPlanService.deleteQqchTechTrainPlan(qqchTechTrainPlanParam));
    }

//    @PreAuthorize(hasPermi = "qqchTechTrainPlan:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchTechTrainPlanByPks(@PathVariable Long[] ids) {
        List<Long> qqchTechTrainPlanPkList = Arrays.asList(ids);
        return toAjax(qqchTechTrainPlanService.deleteQqchTechTrainPlanByPks(qqchTechTrainPlanPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchTechTrainPlan qqchTechTrainPlanParam) throws IOException {
        List<QqchTechTrainPlan> qqchTechTrainPlanList = qqchTechTrainPlanService.getQqchTechTrainPlanList(qqchTechTrainPlanParam);
        ExcelUtils<QqchTechTrainPlan> util = new ExcelUtils<>(QqchTechTrainPlan.class);
        util.exportExcel(response, qqchTechTrainPlanList, DateUtils.getDate());
    }

    /**
     * 获取技术培训策划Vo
     * @param qqchTechTrainPlan
     * @return
     */
    @GetMapping("getQqchTechTrainPlanVo")
    public AjaxResult getQqchTechTrainPlanVo(@Validated(ValidationGroups.Select.class) QqchTechTrainPlan qqchTechTrainPlan) {
        QqchTechTrainPlanVo qqchTechTrainPlanVo = qqchTechTrainPlanService.getQqchTechTrainPlanVo(qqchTechTrainPlan);
        return AjaxResult.success(qqchTechTrainPlanVo);
    }

    /**
     * 保存/确认/提交
     * @param qqchTechTrainPlanVo
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchTechTrainPlan:save")
    @PostMapping("/save")
    @CustomLogger(title = "前期策划-前期策划编制-施工技术策划-3.15 技术培训策划", name =
            "3.15 技术培训策划", businessType = CustomBusinessType.SAVE)
    public AjaxResult save(@Validated(ValidationGroups.Save.class) @RequestBody QqchTechTrainPlanVo qqchTechTrainPlanVo) {
        qqchTechTrainPlanService.save(qqchTechTrainPlanVo);
        return AjaxResult.success();
    }
}
