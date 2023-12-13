package com.hhwy.pm.qqch.preparation.quality.qqchEmergencyImplementationPlan.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.quality.qqchEmergencyImplementationPlan.domain.QqchEmergencyImplementationPlan;
import com.hhwy.pm.qqch.preparation.quality.qqchEmergencyImplementationPlan.domain.vo.QqchEmergencyImplementationPlanVo;
import com.hhwy.pm.qqch.preparation.quality.qqchEmergencyImplementationPlan.service.IQqchEmergencyImplementationPlanService;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author ldd
 * @date 2023-08-10 18:39:15
 * @remark 8.10.3 应急演练实施策划
 */
@Validated
@RestController
@RequestMapping("/qqchEmergencyImplementationPlan")
public class QqchEmergencyImplementationPlanController extends BaseController {

    @Autowired
    private IQqchEmergencyImplementationPlanService qqchEmergencyImplementationPlanService;

//    @PreAuthorize(hasPermi = "qqchEmergencyImplementationPlan:list")
    @GetMapping("/list")
    @CustomLogger(title = "前期策划-前期策划编制-安全策划-应急管控策划", name = "8.10.3应急演练实施策划" ,businessType = CustomBusinessType.SELECT)
    public AjaxResult getQqchEmergencyImplementationPlanList(@Validated(ValidationGroups.Select.class) QqchEmergencyImplementationPlan qqchEmergencyImplementationPlanParam) {
        QqchEmergencyImplementationPlanVo vo = qqchEmergencyImplementationPlanService.getQqchEmergencyImplementationPlanList(qqchEmergencyImplementationPlanParam);
        return AjaxResult.success(vo);
    }

//    @PreAuthorize(hasPermi = "qqchEmergencyImplementationPlan:save")
    @PostMapping("/save")
    @CustomLogger(title = "前期策划-前期策划编制-安全策划-应急管控策划", name = "8.10.3应急演练实施策划" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult insertQqchEmergencyImplementationPlanList(@Validated(ValidationGroups.Save.class) @RequestBody QqchEmergencyImplementationPlanVo vo) {
        qqchEmergencyImplementationPlanService.save(vo);
        return AjaxResult.success();
    }

//    @PreAuthorize(hasPermi = "qqchEmergencyImplementationPlan:list")
    @GetMapping
    @CustomLogger(title = "前期策划-前期策划编制-安全策划-应急管控策划", name = "8.10.3应急演练实施策划" ,businessType = CustomBusinessType.SELECT)
    public AjaxResult getQqchEmergencyImplementationPlan(@Validated(ValidationGroups.Get.class) QqchEmergencyImplementationPlan qqchEmergencyImplementationPlanParam) {
        QqchEmergencyImplementationPlan qqchEmergencyImplementationPlan = qqchEmergencyImplementationPlanService.getQqchEmergencyImplementationPlan(qqchEmergencyImplementationPlanParam);
        return AjaxResult.success(qqchEmergencyImplementationPlan);
    }


//    @PreAuthorize(hasPermi = "qqchEmergencyImplementationPlan:add")
    @PostMapping("/add")
    @CustomLogger(title = "前期策划-前期策划编制-安全策划-应急管控策划", name = "8.10.3应急演练实施策划" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult insertQqchEmergencyImplementationPlan(@Validated(ValidationGroups.Save.class) @RequestBody QqchEmergencyImplementationPlan qqchEmergencyImplementationPlanParam) {
        qqchEmergencyImplementationPlanService.insertQqchEmergencyImplementationPlan(qqchEmergencyImplementationPlanParam);
        return AjaxResult.success(qqchEmergencyImplementationPlanParam);
    }


//    @PreAuthorize(hasPermi = "qqchEmergencyImplementationPlan:update")
    @PostMapping("/update")
    public AjaxResult updateQqchEmergencyImplementationPlan(@Validated(ValidationGroups.Update.class) @RequestBody QqchEmergencyImplementationPlan qqchEmergencyImplementationPlanParam) {
        return toAjax(qqchEmergencyImplementationPlanService.updateQqchEmergencyImplementationPlan(qqchEmergencyImplementationPlanParam));
    }

//    @PreAuthorize(hasPermi = "qqchEmergencyImplementationPlan:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchEmergencyImplementationPlanList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchEmergencyImplementationPlan> qqchEmergencyImplementationPlanListParam) {
        return toAjax(qqchEmergencyImplementationPlanService.updateQqchEmergencyImplementationPlanList(qqchEmergencyImplementationPlanListParam));
    }

//    @PreAuthorize(hasPermi = "qqchEmergencyImplementationPlan:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchEmergencyImplementationPlan(@Validated(ValidationGroups.Delete.class) @RequestBody QqchEmergencyImplementationPlan qqchEmergencyImplementationPlanParam) {
        return toAjax(qqchEmergencyImplementationPlanService.deleteQqchEmergencyImplementationPlan(qqchEmergencyImplementationPlanParam));
    }

//    @PreAuthorize(hasPermi = "qqchEmergencyImplementationPlan:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchEmergencyImplementationPlanByPks(@PathVariable Long[] ids) {
        List<Long> qqchEmergencyImplementationPlanPkList = Arrays.asList(ids);
        return toAjax(qqchEmergencyImplementationPlanService.deleteQqchEmergencyImplementationPlanByPks(qqchEmergencyImplementationPlanPkList));
    }


}
