package com.hhwy.pm.jdgl.mainpl.jdglMainPlan.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlan.domain.JdglMainPlan;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlan.domain.JdglMainPlanQueryVO;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlan.service.IJdglMainPlanService;
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
 * @author 陈锦豪
 * @date 2023-08-29 15:12:20
 * @remark
 */
@Validated
@RestController
@RequestMapping("/jdglMainPlan")
public class JdglMainPlanController extends BaseController {

    @Autowired
    private IJdglMainPlanService jdglMainPlanService;


    // // @PreAuthorize(hasPermi = "jdglMainPlan:list")
    @GetMapping
    public AjaxResult getJdglMainPlan(@Validated(ValidationGroups.Get.class) JdglMainPlan jdglMainPlanParam) {
        JdglMainPlan jdglMainPlan = jdglMainPlanService.getJdglMainPlan(jdglMainPlanParam);
        return AjaxResult.success(jdglMainPlan);
    }

    // // @PreAuthorize(hasPermi = "jdglMainPlan:list")
    @GetMapping("/getUsingMainPlan")
    @CustomLogger(title = "进度管理-计划管理-总体计划", name = "总体计划" ,businessType = CustomBusinessType.SELECT)
    public AjaxResult getUsingMainPlan(JdglMainPlanQueryVO queryVO) {
        JdglMainPlan jdglMainPlan = jdglMainPlanService.getUsingJdglMainPlan(queryVO);
        return AjaxResult.success(jdglMainPlan);
    }

    // // @PreAuthorize(hasPermi = "jdglMainPlan:list")
    @GetMapping("/getBaseMainPlan")
    public AjaxResult getBaseMainPlan() {
        JdglMainPlan jdglMainPlan = jdglMainPlanService.getBaseMainPlan();
        return AjaxResult.success(jdglMainPlan);
    }

    //基线计划列表查询
    @GetMapping("/getBaseMainPlanList")
    public AjaxResult getBaseMainPlanList(@RequestParam(value = "tenantKey", required = false) String tenantKey) {
            List<JdglMainPlan> jdglMainPlanList = jdglMainPlanService.getBaseMainPlanList(tenantKey);
        return AjaxResult.success(jdglMainPlanList);
    }

    //基线计划详情查询
    @GetMapping("/getBaseMainPlanDetail")
    public AjaxResult getBaseMainPlanDetail(@RequestParam(value = "tenantKey", required = false) String tenantKey,
                                            @RequestParam("id") Long id) {
        JdglMainPlan jdglMainPlan = jdglMainPlanService.getBaseMainPlanDetail(tenantKey, id);
        return AjaxResult.success(jdglMainPlan);
    }

    // // @PreAuthorize(hasPermi = "jdglMainPlan:list")
    @GetMapping("/list")
    public AjaxResult getJdglMainPlanList(@Validated(ValidationGroups.Select.class) JdglMainPlan jdglMainPlanParam) {
        startPage();
        List<JdglMainPlan> jdglMainPlanList = jdglMainPlanService.getJdglMainPlanList(jdglMainPlanParam);
        return getDataTableAjaxResult(jdglMainPlanList);
    }

    // @PreAuthorize(hasPermi = "jdglMainPlan:add")
    @PostMapping("/add")
    public AjaxResult insertJdglMainPlan(@Validated(ValidationGroups.Save.class) @RequestBody JdglMainPlan jdglMainPlanParam) {
        jdglMainPlanService.insertJdglMainPlan(jdglMainPlanParam);
        return AjaxResult.success(jdglMainPlanParam);
    }

    // @PreAuthorize(hasPermi = "jdglMainPlan:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertJdglMainPlanList(@Validated(ValidationGroups.Save.class) @RequestBody List<JdglMainPlan> jdglMainPlanListParam) {
        jdglMainPlanService.insertJdglMainPlanList(jdglMainPlanListParam);
        return AjaxResult.success(jdglMainPlanListParam);
    }

    // @PreAuthorize(hasPermi = "jdglMainPlan:update")
    @PostMapping("/update")
    public AjaxResult updateJdglMainPlan(@Validated(ValidationGroups.Update.class) @RequestBody JdglMainPlan jdglMainPlanParam) {
        return toAjax(jdglMainPlanService.updateJdglMainPlan(jdglMainPlanParam));
    }

    // @PreAuthorize(hasPermi = "jdglMainPlan:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateJdglMainPlanList(@Validated(ValidationGroups.Update.class) @RequestBody List<JdglMainPlan> jdglMainPlanListParam) {
        return toAjax(jdglMainPlanService.updateJdglMainPlanList(jdglMainPlanListParam));
    }

    // @PreAuthorize(hasPermi = "jdglMainPlan:remove")
    @PostMapping("/delete")
    public AjaxResult deleteJdglMainPlan(@Validated(ValidationGroups.Delete.class) @RequestBody JdglMainPlan jdglMainPlanParam) {
        return toAjax(jdglMainPlanService.deleteJdglMainPlan(jdglMainPlanParam));
    }

    // @PreAuthorize(hasPermi = "jdglMainPlan:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteJdglMainPlanByPks(@PathVariable Long[] ids) {
        List<Long> jdglMainPlanPkList = Arrays.asList(ids);
        return toAjax(jdglMainPlanService.deleteJdglMainPlanByPks(jdglMainPlanPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, JdglMainPlan jdglMainPlanParam) throws IOException {
        List<JdglMainPlan> jdglMainPlanList = jdglMainPlanService.getJdglMainPlanList(jdglMainPlanParam);
        ExcelUtils<JdglMainPlan> util = new ExcelUtils<>(JdglMainPlan.class);
        util.exportExcel(response, jdglMainPlanList, DateUtils.getDate());
    }

    @GetMapping("test")
    public AjaxResult test(Long id){
        jdglMainPlanService.test(id);
        return AjaxResult.success();
    }
}
