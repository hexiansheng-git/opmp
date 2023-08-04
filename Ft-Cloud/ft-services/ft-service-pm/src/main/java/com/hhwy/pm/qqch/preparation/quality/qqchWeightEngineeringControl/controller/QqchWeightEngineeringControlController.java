package com.hhwy.pm.qqch.preparation.quality.qqchWeightEngineeringControl.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.quality.qqchWeightEngineeringControl.domain.QqchWeightEngineeringControl;
import com.hhwy.pm.qqch.preparation.quality.qqchWeightEngineeringControl.domain.vo.QqchWeightEngineeringControlVo;
import com.hhwy.pm.qqch.preparation.quality.qqchWeightEngineeringControl.service.IQqchWeightEngineeringControlService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author ldd
 * @date 2023-08-04 15:10:09
 * @remark 9.4.2 重难点工程管控
 */
@Validated
@RestController
@RequestMapping("/qqchWeightEngineeringControl")
public class QqchWeightEngineeringControlController extends BaseController {

    @Autowired
    private IQqchWeightEngineeringControlService qqchWeightEngineeringControlService;

    @PreAuthorize(hasPermi = "qqchWeightEngineeringControl:list")
    @GetMapping("/list")
    public AjaxResult getQqchWeightEngineeringControlList(@Validated(ValidationGroups.Select.class) QqchWeightEngineeringControl qqchWeightEngineeringControlParam) {
        QqchWeightEngineeringControlVo vo = qqchWeightEngineeringControlService.getQqchWeightEngineeringControlList(qqchWeightEngineeringControlParam);
        return AjaxResult.success(vo);
    }


    @PreAuthorize(hasPermi = "qqchWeightEngineeringControl:save")
    @PostMapping("/save")
    public AjaxResult save(@Validated(ValidationGroups.Save.class) @RequestBody QqchWeightEngineeringControlVo vo) {
        qqchWeightEngineeringControlService.save(vo);
        return AjaxResult.success();
    }


    @PreAuthorize(hasPermi = "qqchWeightEngineeringControl:list")
    @GetMapping
    public AjaxResult getQqchWeightEngineeringControl(@Validated(ValidationGroups.Get.class) QqchWeightEngineeringControl qqchWeightEngineeringControlParam) {
        QqchWeightEngineeringControl qqchWeightEngineeringControl = qqchWeightEngineeringControlService.getQqchWeightEngineeringControl(qqchWeightEngineeringControlParam);
        return AjaxResult.success(qqchWeightEngineeringControl);
    }

    @PreAuthorize(hasPermi = "qqchWeightEngineeringControl:add")
    @PostMapping("/add")
    public AjaxResult insertQqchWeightEngineeringControl(@Validated(ValidationGroups.Save.class) @RequestBody QqchWeightEngineeringControl qqchWeightEngineeringControlParam) {
        qqchWeightEngineeringControlService.insertQqchWeightEngineeringControl(qqchWeightEngineeringControlParam);
        return AjaxResult.success(qqchWeightEngineeringControlParam);
    }


    @PreAuthorize(hasPermi = "qqchWeightEngineeringControl:update")
    @PostMapping("/update")
    public AjaxResult updateQqchWeightEngineeringControl(@Validated(ValidationGroups.Update.class) @RequestBody QqchWeightEngineeringControl qqchWeightEngineeringControlParam) {
        return toAjax(qqchWeightEngineeringControlService.updateQqchWeightEngineeringControl(qqchWeightEngineeringControlParam));
    }

    @PreAuthorize(hasPermi = "qqchWeightEngineeringControl:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchWeightEngineeringControlList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchWeightEngineeringControl> qqchWeightEngineeringControlListParam) {
        return toAjax(qqchWeightEngineeringControlService.updateQqchWeightEngineeringControlList(qqchWeightEngineeringControlListParam));
    }

    @PreAuthorize(hasPermi = "qqchWeightEngineeringControl:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchWeightEngineeringControl(@Validated(ValidationGroups.Delete.class) @RequestBody QqchWeightEngineeringControl qqchWeightEngineeringControlParam) {
        return toAjax(qqchWeightEngineeringControlService.deleteQqchWeightEngineeringControl(qqchWeightEngineeringControlParam));
    }

    @PreAuthorize(hasPermi = "qqchWeightEngineeringControl:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchWeightEngineeringControlByPks(@PathVariable Long[] ids) {
        List<Long> qqchWeightEngineeringControlPkList = Arrays.asList(ids);
        return toAjax(qqchWeightEngineeringControlService.deleteQqchWeightEngineeringControlByPks(qqchWeightEngineeringControlPkList));
    }


}
