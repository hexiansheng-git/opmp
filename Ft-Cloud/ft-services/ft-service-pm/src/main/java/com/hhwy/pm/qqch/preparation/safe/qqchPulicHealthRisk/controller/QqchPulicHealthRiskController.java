package com.hhwy.pm.qqch.preparation.safe.qqchPulicHealthRisk.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.safe.qqchPulicHealthRisk.domain.QqchPulicHealthRisk;
import com.hhwy.pm.qqch.preparation.safe.qqchPulicHealthRisk.domain.vo.QqchPulicHealthRiskVo;
import com.hhwy.pm.qqch.preparation.safe.qqchPulicHealthRisk.service.IQqchPulicHealthRiskService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author ldd
 * @date 2023-08-09 15:01:10
 * @remark 8.7.2 公共卫生风险策划
 */
@Validated
@RestController
@RequestMapping("/qqchPulicHealthRisk")
public class QqchPulicHealthRiskController extends BaseController {

    @Autowired
    private IQqchPulicHealthRiskService qqchPulicHealthRiskService;

//    @PreAuthorize(hasPermi = "qqchPulicHealthRisk:list")
    @GetMapping("/list")
    public AjaxResult getQqchPulicHealthRiskList(@Validated(ValidationGroups.Select.class) QqchPulicHealthRisk qqchPulicHealthRiskParam) {
        QqchPulicHealthRiskVo vo = qqchPulicHealthRiskService.getQqchPulicHealthRiskList(qqchPulicHealthRiskParam);
        return AjaxResult.success(vo);
    }

//    @PreAuthorize(hasPermi = "qqchPulicHealthRisk:save")
    @PostMapping("/save")
    public AjaxResult insertQqchPulicHealthRiskList(@Validated(ValidationGroups.Save.class) @RequestBody QqchPulicHealthRiskVo vo) {
        qqchPulicHealthRiskService.save(vo);
        return AjaxResult.success();
    }


//    @PreAuthorize(hasPermi = "qqchPulicHealthRisk:list")
    @GetMapping
    public AjaxResult getQqchPulicHealthRisk(@Validated(ValidationGroups.Get.class) QqchPulicHealthRisk qqchPulicHealthRiskParam) {
        QqchPulicHealthRisk qqchPulicHealthRisk = qqchPulicHealthRiskService.getQqchPulicHealthRisk(qqchPulicHealthRiskParam);
        return AjaxResult.success(qqchPulicHealthRisk);
    }

//    @PreAuthorize(hasPermi = "qqchPulicHealthRisk:add")
    @PostMapping("/add")
    public AjaxResult insertQqchPulicHealthRisk(@Validated(ValidationGroups.Save.class) @RequestBody QqchPulicHealthRisk qqchPulicHealthRiskParam) {
        qqchPulicHealthRiskService.insertQqchPulicHealthRisk(qqchPulicHealthRiskParam);
        return AjaxResult.success(qqchPulicHealthRiskParam);
    }

//    @PreAuthorize(hasPermi = "qqchPulicHealthRisk:update")
    @PostMapping("/update")
    public AjaxResult updateQqchPulicHealthRisk(@Validated(ValidationGroups.Update.class) @RequestBody QqchPulicHealthRisk qqchPulicHealthRiskParam) {
        return toAjax(qqchPulicHealthRiskService.updateQqchPulicHealthRisk(qqchPulicHealthRiskParam));
    }

//    @PreAuthorize(hasPermi = "qqchPulicHealthRisk:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchPulicHealthRiskList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchPulicHealthRisk> qqchPulicHealthRiskListParam) {
        return toAjax(qqchPulicHealthRiskService.updateQqchPulicHealthRiskList(qqchPulicHealthRiskListParam));
    }

//    @PreAuthorize(hasPermi = "qqchPulicHealthRisk:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchPulicHealthRisk(@Validated(ValidationGroups.Delete.class) @RequestBody QqchPulicHealthRisk qqchPulicHealthRiskParam) {
        return toAjax(qqchPulicHealthRiskService.deleteQqchPulicHealthRisk(qqchPulicHealthRiskParam));
    }

//    @PreAuthorize(hasPermi = "qqchPulicHealthRisk:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchPulicHealthRiskByPks(@PathVariable Long[] ids) {
        List<Long> qqchPulicHealthRiskPkList = Arrays.asList(ids);
        return toAjax(qqchPulicHealthRiskService.deleteQqchPulicHealthRiskByPks(qqchPulicHealthRiskPkList));
    }

}
