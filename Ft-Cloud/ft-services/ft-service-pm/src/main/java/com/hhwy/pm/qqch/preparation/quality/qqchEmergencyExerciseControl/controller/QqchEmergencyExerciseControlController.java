package com.hhwy.pm.qqch.preparation.quality.qqchEmergencyExerciseControl.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.quality.qqchEmergencyExerciseControl.domain.QqchEmergencyExerciseControl;
import com.hhwy.pm.qqch.preparation.quality.qqchEmergencyExerciseControl.domain.vo.QqchEmergencyExerciseControlVo;
import com.hhwy.pm.qqch.preparation.quality.qqchEmergencyExerciseControl.service.IQqchEmergencyExerciseControlService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author ldd
 * @date 2023-08-10 18:39:07
 * @remark 8.10.2 应急演练管控
 */
@Validated
@RestController
@RequestMapping("/qqchEmergencyExerciseControl")
public class QqchEmergencyExerciseControlController extends BaseController {

    @Autowired
    private IQqchEmergencyExerciseControlService qqchEmergencyExerciseControlService;


    //    @PreAuthorize(hasPermi = "qqchEmergencyExerciseControl:list")
    @GetMapping("/list")
    public AjaxResult getQqchEmergencyExerciseControlList(@Validated(ValidationGroups.Select.class) QqchEmergencyExerciseControl qqchEmergencyExerciseControlParam) {
        QqchEmergencyExerciseControlVo vo = qqchEmergencyExerciseControlService.getQqchEmergencyExerciseControlList(qqchEmergencyExerciseControlParam);
        return AjaxResult.success(vo);
    }

    //    @PreAuthorize(hasPermi = "qqchEmergencyExerciseControl:add")
    @PostMapping("/save")
    public AjaxResult insertQqchEmergencyExerciseControlList(@Validated(ValidationGroups.Save.class) @RequestBody QqchEmergencyExerciseControlVo vo) {
        qqchEmergencyExerciseControlService.save(vo);
        return AjaxResult.success();
    }

    //    @PreAuthorize(hasPermi = "qqchEmergencyExerciseControl:list")
    @GetMapping
    public AjaxResult getQqchEmergencyExerciseControl(@Validated(ValidationGroups.Get.class) QqchEmergencyExerciseControl qqchEmergencyExerciseControlParam) {
        QqchEmergencyExerciseControl qqchEmergencyExerciseControl = qqchEmergencyExerciseControlService.getQqchEmergencyExerciseControl(qqchEmergencyExerciseControlParam);
        return AjaxResult.success(qqchEmergencyExerciseControl);
    }


    //    @PreAuthorize(hasPermi = "qqchEmergencyExerciseControl:add")
    @PostMapping("/add")
    public AjaxResult insertQqchEmergencyExerciseControl(@Validated(ValidationGroups.Save.class) @RequestBody QqchEmergencyExerciseControl qqchEmergencyExerciseControlParam) {
        qqchEmergencyExerciseControlService.insertQqchEmergencyExerciseControl(qqchEmergencyExerciseControlParam);
        return AjaxResult.success(qqchEmergencyExerciseControlParam);
    }


    //    @PreAuthorize(hasPermi = "qqchEmergencyExerciseControl:update")
    @PostMapping("/update")
    public AjaxResult updateQqchEmergencyExerciseControl(@Validated(ValidationGroups.Update.class) @RequestBody QqchEmergencyExerciseControl qqchEmergencyExerciseControlParam) {
        return toAjax(qqchEmergencyExerciseControlService.updateQqchEmergencyExerciseControl(qqchEmergencyExerciseControlParam));
    }

    //            @PreAuthorize(hasPermi = "qqchEmergencyExerciseControl:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchEmergencyExerciseControlList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchEmergencyExerciseControl> qqchEmergencyExerciseControlListParam) {
        return toAjax(qqchEmergencyExerciseControlService.updateQqchEmergencyExerciseControlList(qqchEmergencyExerciseControlListParam));
    }

    //    @PreAuthorize(hasPermi = "qqchEmergencyExerciseControl:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchEmergencyExerciseControl(@Validated(ValidationGroups.Delete.class) @RequestBody QqchEmergencyExerciseControl qqchEmergencyExerciseControlParam) {
        return toAjax(qqchEmergencyExerciseControlService.deleteQqchEmergencyExerciseControl(qqchEmergencyExerciseControlParam));
    }

    //            @PreAuthorize(hasPermi = "qqchEmergencyExerciseControl:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchEmergencyExerciseControlByPks(@PathVariable Long[] ids) {
        List<Long> qqchEmergencyExerciseControlPkList = Arrays.asList(ids);
        return toAjax(qqchEmergencyExerciseControlService.deleteQqchEmergencyExerciseControlByPks(qqchEmergencyExerciseControlPkList));
    }

}
