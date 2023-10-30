package com.hhwy.pm.qqch.preparation.safe.qqchSocietySafeRisk.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.safe.qqchSocietySafeRisk.domain.QqchSocietySafeRisk;
import com.hhwy.pm.qqch.preparation.safe.qqchSocietySafeRisk.domain.vo.QqchSocietySafeRiskVo;
import com.hhwy.pm.qqch.preparation.safe.qqchSocietySafeRisk.service.IQqchSocietySafeRiskService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author ldd
 * @date 2023-08-09 09:30:14
 * @remark 8.6.1 社会安全风险策划
 */
@Validated
@RestController
@RequestMapping("/qqchSocietySafeRisk")
public class QqchSocietySafeRiskController extends BaseController {

    @Autowired
    private IQqchSocietySafeRiskService qqchSocietySafeRiskService;

//    @PreAuthorize(hasPermi = "qqchSocietySafeRisk:list")
    @GetMapping("/list")
    public AjaxResult getQqchSocietySafeRiskList(@Validated(ValidationGroups.Select.class) QqchSocietySafeRisk qqchSocietySafeRiskParam) {
        QqchSocietySafeRiskVo vo = qqchSocietySafeRiskService.getQqchSocietySafeRiskList(qqchSocietySafeRiskParam);
        return AjaxResult.success(vo);
    }

//    @PreAuthorize(hasPermi = "qqchSocietySafeRisk:save")
    @PostMapping("/save")
    public AjaxResult insertQqchSocietySafeRiskList(@Validated(ValidationGroups.Save.class) @RequestBody QqchSocietySafeRiskVo vo) {
        qqchSocietySafeRiskService.save(vo);
        return AjaxResult.success();
    }


//    @PreAuthorize(hasPermi = "qqchSocietySafeRisk:list")
    @GetMapping
    public AjaxResult getQqchSocietySafeRisk(@Validated(ValidationGroups.Get.class) QqchSocietySafeRisk qqchSocietySafeRiskParam) {
        QqchSocietySafeRisk qqchSocietySafeRisk = qqchSocietySafeRiskService.getQqchSocietySafeRisk(qqchSocietySafeRiskParam);
        return AjaxResult.success(qqchSocietySafeRisk);
    }

//    @PreAuthorize(hasPermi = "qqchSocietySafeRisk:add")
    @PostMapping("/add")
    public AjaxResult insertQqchSocietySafeRisk(@Validated(ValidationGroups.Save.class) @RequestBody QqchSocietySafeRisk qqchSocietySafeRiskParam) {
        qqchSocietySafeRiskService.insertQqchSocietySafeRisk(qqchSocietySafeRiskParam);
        return AjaxResult.success(qqchSocietySafeRiskParam);
    }


//    @PreAuthorize(hasPermi = "qqchSocietySafeRisk:update")
    @PostMapping("/update")
    public AjaxResult updateQqchSocietySafeRisk(@Validated(ValidationGroups.Update.class) @RequestBody QqchSocietySafeRisk qqchSocietySafeRiskParam) {
        return toAjax(qqchSocietySafeRiskService.updateQqchSocietySafeRisk(qqchSocietySafeRiskParam));
    }

//    @PreAuthorize(hasPermi = "qqchSocietySafeRisk:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchSocietySafeRiskList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchSocietySafeRisk> qqchSocietySafeRiskListParam) {
        return toAjax(qqchSocietySafeRiskService.updateQqchSocietySafeRiskList(qqchSocietySafeRiskListParam));
    }

//    @PreAuthorize(hasPermi = "qqchSocietySafeRisk:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchSocietySafeRisk(@Validated(ValidationGroups.Delete.class) @RequestBody QqchSocietySafeRisk qqchSocietySafeRiskParam) {
        return toAjax(qqchSocietySafeRiskService.deleteQqchSocietySafeRisk(qqchSocietySafeRiskParam));
    }

//    @PreAuthorize(hasPermi = "qqchSocietySafeRisk:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchSocietySafeRiskByPks(@PathVariable Long[] ids) {
        List<Long> qqchSocietySafeRiskPkList = Arrays.asList(ids);
        return toAjax(qqchSocietySafeRiskService.deleteQqchSocietySafeRiskByPks(qqchSocietySafeRiskPkList));
    }

}
