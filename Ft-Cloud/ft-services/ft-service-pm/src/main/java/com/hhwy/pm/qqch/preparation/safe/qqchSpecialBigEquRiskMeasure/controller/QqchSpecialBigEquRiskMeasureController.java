package com.hhwy.pm.qqch.preparation.safe.qqchSpecialBigEquRiskMeasure.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.safe.qqchSpecialBigEquRiskMeasure.domain.QqchSpecialBigEquRiskMeasure;
import com.hhwy.pm.qqch.preparation.safe.qqchSpecialBigEquRiskMeasure.domain.vo.QqchSpecialBigEquRiskMeasureVo;
import com.hhwy.pm.qqch.preparation.safe.qqchSpecialBigEquRiskMeasure.service.IQqchSpecialBigEquRiskMeasureService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author ldd
 * @date 2023-08-08 15:22:12
 * @remark 8.4.2 特种设备及大型设备风险识别与措施
 */
@Validated
@RestController
@RequestMapping("/qqchSpecialBigEquRiskMeasure")
public class QqchSpecialBigEquRiskMeasureController extends BaseController {

    @Autowired
    private IQqchSpecialBigEquRiskMeasureService qqchSpecialBigEquRiskMeasureService;

//    @PreAuthorize(hasPermi = "qqchSpecialBigEquRiskMeasure:list")
    @GetMapping("/list")
    public AjaxResult getQqchSpecialBigEquRiskMeasureList(@Validated(ValidationGroups.Select.class) QqchSpecialBigEquRiskMeasure qqchSpecialBigEquRiskMeasureParam) {
        QqchSpecialBigEquRiskMeasureVo vo = qqchSpecialBigEquRiskMeasureService.getQqchSpecialBigEquRiskMeasureList(qqchSpecialBigEquRiskMeasureParam);
        return AjaxResult.success(vo);
    }

//    @PreAuthorize(hasPermi = "qqchSpecialBigEquRiskMeasure:save")
    @PostMapping("/save")
    public AjaxResult insertQqchSpecialBigEquRiskMeasureList(@Validated(ValidationGroups.Save.class) @RequestBody QqchSpecialBigEquRiskMeasureVo vo) {
        qqchSpecialBigEquRiskMeasureService.save(vo);
        return AjaxResult.success();
    }


//    @PreAuthorize(hasPermi = "qqchSpecialBigEquRiskMeasure:list")
    @GetMapping
    public AjaxResult getQqchSpecialBigEquRiskMeasure(@Validated(ValidationGroups.Get.class) QqchSpecialBigEquRiskMeasure qqchSpecialBigEquRiskMeasureParam) {
        QqchSpecialBigEquRiskMeasure qqchSpecialBigEquRiskMeasure = qqchSpecialBigEquRiskMeasureService.getQqchSpecialBigEquRiskMeasure(qqchSpecialBigEquRiskMeasureParam);
        return AjaxResult.success(qqchSpecialBigEquRiskMeasure);
    }


//    @PreAuthorize(hasPermi = "qqchSpecialBigEquRiskMeasure:add")
    @PostMapping("/add")
    public AjaxResult insertQqchSpecialBigEquRiskMeasure(@Validated(ValidationGroups.Save.class) @RequestBody QqchSpecialBigEquRiskMeasure qqchSpecialBigEquRiskMeasureParam) {
        qqchSpecialBigEquRiskMeasureService.insertQqchSpecialBigEquRiskMeasure(qqchSpecialBigEquRiskMeasureParam);
        return AjaxResult.success(qqchSpecialBigEquRiskMeasureParam);
    }


//    @PreAuthorize(hasPermi = "qqchSpecialBigEquRiskMeasure:update")
    @PostMapping("/update")
    public AjaxResult updateQqchSpecialBigEquRiskMeasure(@Validated(ValidationGroups.Update.class) @RequestBody QqchSpecialBigEquRiskMeasure qqchSpecialBigEquRiskMeasureParam) {
        return toAjax(qqchSpecialBigEquRiskMeasureService.updateQqchSpecialBigEquRiskMeasure(qqchSpecialBigEquRiskMeasureParam));
    }

//    @PreAuthorize(hasPermi = "qqchSpecialBigEquRiskMeasure:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchSpecialBigEquRiskMeasureList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchSpecialBigEquRiskMeasure> qqchSpecialBigEquRiskMeasureListParam) {
        return toAjax(qqchSpecialBigEquRiskMeasureService.updateQqchSpecialBigEquRiskMeasureList(qqchSpecialBigEquRiskMeasureListParam));
    }

//    @PreAuthorize(hasPermi = "qqchSpecialBigEquRiskMeasure:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchSpecialBigEquRiskMeasure(@Validated(ValidationGroups.Delete.class) @RequestBody QqchSpecialBigEquRiskMeasure qqchSpecialBigEquRiskMeasureParam) {
        return toAjax(qqchSpecialBigEquRiskMeasureService.deleteQqchSpecialBigEquRiskMeasure(qqchSpecialBigEquRiskMeasureParam));
    }

//    @PreAuthorize(hasPermi = "qqchSpecialBigEquRiskMeasure:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchSpecialBigEquRiskMeasureByPks(@PathVariable Long[] ids) {
        List<Long> qqchSpecialBigEquRiskMeasurePkList = Arrays.asList(ids);
        return toAjax(qqchSpecialBigEquRiskMeasureService.deleteQqchSpecialBigEquRiskMeasureByPks(qqchSpecialBigEquRiskMeasurePkList));
    }

}
