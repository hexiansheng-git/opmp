package com.hhwy.pm.qqch.preparation.quality.qqchEmergencyPlanControl.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.quality.qqchEmergencyPlanControl.domain.QqchEmergencyPlanControl;
import com.hhwy.pm.qqch.preparation.quality.qqchEmergencyPlanControl.domain.vo.QqchEmergencyPlanControlVo;
import com.hhwy.pm.qqch.preparation.quality.qqchEmergencyPlanControl.service.IQqchEmergencyPlanControlService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author ldd
 * @date 2023-08-11
 * @remark 8.10.1 应急预案管控
 */
@Validated
@RestController
@RequestMapping("/qqchEmergencyPlanControl")
public class QqchEmergencyPlanControlController extends BaseController {

    @Autowired
    private IQqchEmergencyPlanControlService qqchEmergencyPlanControlService;

    /**
     * 列表接口
     *
     * @param qqchEmergencyPlanControlParam
     * @return
     */
    @PreAuthorize(hasPermi = "qqchEmergencyPlanControl:list")
    @GetMapping("/list")
    public AjaxResult getQqchEmergencyPlanControlList(@Validated(ValidationGroups.Select.class) QqchEmergencyPlanControl qqchEmergencyPlanControlParam) {
        QqchEmergencyPlanControlVo vo = qqchEmergencyPlanControlService.getQqchEmergencyPlanControlList(qqchEmergencyPlanControlParam);
        return AjaxResult.success(vo);
    }


    /**
     * 保存/确认/提交
     *
     * @param vo
     * @return
     */
    @PreAuthorize(hasPermi = "qqchEmergencyPlanControl:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchEmergencyPlanControlList(@Validated(ValidationGroups.Save.class) @RequestBody QqchEmergencyPlanControlVo vo) {
        qqchEmergencyPlanControlService.save(vo);
        return AjaxResult.success();
    }

    @PreAuthorize(hasPermi = "qqchEmergencyPlanControl:list")
    @GetMapping
    public AjaxResult getQqchEmergencyPlanControl(@Validated(ValidationGroups.Get.class) QqchEmergencyPlanControl qqchEmergencyPlanControlParam) {
        QqchEmergencyPlanControl qqchEmergencyPlanControl = qqchEmergencyPlanControlService.getQqchEmergencyPlanControl(qqchEmergencyPlanControlParam);
        return AjaxResult.success(qqchEmergencyPlanControl);
    }


    @PreAuthorize(hasPermi = "qqchEmergencyPlanControl:add")
    @PostMapping("/add")
    public AjaxResult insertQqchEmergencyPlanControl(@Validated(ValidationGroups.Save.class) @RequestBody QqchEmergencyPlanControl qqchEmergencyPlanControlParam) {
        qqchEmergencyPlanControlService.insertQqchEmergencyPlanControl(qqchEmergencyPlanControlParam);
        return AjaxResult.success(qqchEmergencyPlanControlParam);
    }


    @PreAuthorize(hasPermi = "qqchEmergencyPlanControl:update")
    @PostMapping("/update")
    public AjaxResult updateQqchEmergencyPlanControl(@Validated(ValidationGroups.Update.class) @RequestBody QqchEmergencyPlanControl qqchEmergencyPlanControlParam) {
        return toAjax(qqchEmergencyPlanControlService.updateQqchEmergencyPlanControl(qqchEmergencyPlanControlParam));
    }

    @PreAuthorize(hasPermi = "qqchEmergencyPlanControl:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchEmergencyPlanControlList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchEmergencyPlanControl> qqchEmergencyPlanControlListParam) {
        return toAjax(qqchEmergencyPlanControlService.updateQqchEmergencyPlanControlList(qqchEmergencyPlanControlListParam));
    }

    @PreAuthorize(hasPermi = "qqchEmergencyPlanControl:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchEmergencyPlanControl(@Validated(ValidationGroups.Delete.class) @RequestBody QqchEmergencyPlanControl qqchEmergencyPlanControlParam) {
        return toAjax(qqchEmergencyPlanControlService.deleteQqchEmergencyPlanControl(qqchEmergencyPlanControlParam));
    }

    @PreAuthorize(hasPermi = "qqchEmergencyPlanControl:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchEmergencyPlanControlByPks(@PathVariable Long[] ids) {
        List<Long> qqchEmergencyPlanControlPkList = Arrays.asList(ids);
        return toAjax(qqchEmergencyPlanControlService.deleteQqchEmergencyPlanControlByPks(qqchEmergencyPlanControlPkList));
    }

}
