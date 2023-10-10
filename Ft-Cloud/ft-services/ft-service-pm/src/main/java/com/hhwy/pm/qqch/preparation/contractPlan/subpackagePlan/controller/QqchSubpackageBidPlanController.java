package com.hhwy.pm.qqch.preparation.contractPlan.subpackagePlan.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
//import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.contractPlan.subpackagePlan.domain.QqchSubpackageBidPlan;
import com.hhwy.pm.qqch.preparation.contractPlan.subpackagePlan.domain.vo.QqchSubpackageBidPlanVo;
import com.hhwy.pm.qqch.preparation.contractPlan.subpackagePlan.service.IQqchSubpackageBidPlanService;
import com.hhwy.pm.qqch.sgch.qqchconst.domain.QqchConst;
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
 * @date 2023-08-03 13:35:44
 * @remark 分包策划
 */
@Validated
@RestController
@RequestMapping("/qqchSubpackageBidPlan")
public class QqchSubpackageBidPlanController extends BaseController {

    @Autowired
    private IQqchSubpackageBidPlanService qqchSubpackageBidPlanService;


//    @PreAuthorize(hasPermi = "qqchSubpackageBidPlan:list")
    @GetMapping
    public AjaxResult getQqchSubpackageBidPlan(@Validated(ValidationGroups.Get.class) QqchSubpackageBidPlan qqchSubpackageBidPlanParam) {
        QqchSubpackageBidPlan qqchSubpackageBidPlan = qqchSubpackageBidPlanService.getQqchSubpackageBidPlan(qqchSubpackageBidPlanParam);
        return AjaxResult.success(qqchSubpackageBidPlan);
    }

//    @PreAuthorize(hasPermi = "qqchSubpackageBidPlan:list")
    @GetMapping("/list")
    public AjaxResult getQqchSubpackageBidPlanList(@Validated(ValidationGroups.Select.class) QqchSubpackageBidPlan qqchSubpackageBidPlanParam) {
        startPage();
        List<QqchSubpackageBidPlan> qqchSubpackageBidPlanList = qqchSubpackageBidPlanService.getQqchSubpackageBidPlanList(qqchSubpackageBidPlanParam);
        return getDataTableAjaxResult(qqchSubpackageBidPlanList);
    }

//    @PreAuthorize(hasPermi = "qqchSubpackageBidPlan:add")
    @PostMapping("/add")
    public AjaxResult insertQqchSubpackageBidPlan(@Validated(ValidationGroups.Save.class) @RequestBody QqchSubpackageBidPlan qqchSubpackageBidPlanParam) {
        qqchSubpackageBidPlanService.insertQqchSubpackageBidPlan(qqchSubpackageBidPlanParam);
        return AjaxResult.success(qqchSubpackageBidPlanParam);
    }

//    @PreAuthorize(hasPermi = "qqchSubpackageBidPlan:update")
    @PostMapping("/update")
    public AjaxResult updateQqchSubpackageBidPlan(@Validated(ValidationGroups.Update.class) @RequestBody QqchSubpackageBidPlan qqchSubpackageBidPlanParam) {
        return toAjax(qqchSubpackageBidPlanService.updateQqchSubpackageBidPlan(qqchSubpackageBidPlanParam));
    }

//    @PreAuthorize(hasPermi = "qqchSubpackageBidPlan:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchSubpackageBidPlanList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchSubpackageBidPlan> qqchSubpackageBidPlanListParam) {
        return toAjax(qqchSubpackageBidPlanService.updateQqchSubpackageBidPlanList(qqchSubpackageBidPlanListParam));
    }

//    @PreAuthorize(hasPermi = "qqchSubpackageBidPlan:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchSubpackageBidPlan(@Validated(ValidationGroups.Delete.class) @RequestBody QqchSubpackageBidPlan qqchSubpackageBidPlanParam) {
        return toAjax(qqchSubpackageBidPlanService.deleteQqchSubpackageBidPlan(qqchSubpackageBidPlanParam));
    }

//    @PreAuthorize(hasPermi = "qqchSubpackageBidPlan:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchSubpackageBidPlanByPks(@PathVariable Long[] ids) {
        List<Long> qqchSubpackageBidPlanPkList = Arrays.asList(ids);
        return toAjax(qqchSubpackageBidPlanService.deleteQqchSubpackageBidPlanByPks(qqchSubpackageBidPlanPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchSubpackageBidPlan qqchSubpackageBidPlanParam) throws IOException {
        List<QqchSubpackageBidPlan> qqchSubpackageBidPlanList = qqchSubpackageBidPlanService.getQqchSubpackageBidPlanList(qqchSubpackageBidPlanParam);
        ExcelUtils<QqchSubpackageBidPlan> util = new ExcelUtils<>(QqchSubpackageBidPlan.class);
        util.exportExcel(response, qqchSubpackageBidPlanList, DateUtils.getDate());
    }

    /**
     * 获取分包招标策划Vo
     * @param qqchSubpackageBidPlan
     * @return
     */
    @GetMapping("getQqchSubpackageBidPlanVo")
    public AjaxResult getQqchSubpackageBidPlanVo(@Validated(ValidationGroups.Get.class) QqchSubpackageBidPlan qqchSubpackageBidPlan) {
        QqchSubpackageBidPlanVo qqchSubpackageBidPlanVo = qqchSubpackageBidPlanService.getQqchSubpackageBidPlanVo(qqchSubpackageBidPlan);
        return AjaxResult.success(qqchSubpackageBidPlanVo);
    }

    /**
     * 保存/确认/提交
     * @param qqchSubpackageBidPlanVo
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchSubpackageBidPlan:save")
    @PostMapping("/save")
    public AjaxResult save(@RequestBody QqchSubpackageBidPlanVo qqchSubpackageBidPlanVo) {
        qqchSubpackageBidPlanService.save(qqchSubpackageBidPlanVo);
        return AjaxResult.success();
    }

    /**
     * 处理选择的班组数据
     * @param qqchConstList
     * @return
     */
    @GetMapping("disposeSelectedData")
    public AjaxResult disposeSelectedData(List<QqchConst> qqchConstList){
        List<QqchSubpackageBidPlan> list = qqchSubpackageBidPlanService.disposeSelectedData(qqchConstList);
        return AjaxResult.success(list);
    }

}
