package com.hhwy.pm.qqch.preparation.quality.qqchQualityRiskControlMeasures.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.quality.qqchQualityRiskControlMeasures.domain.QqchQualityRiskControlMeasures;
import com.hhwy.pm.qqch.preparation.quality.qqchQualityRiskControlMeasures.domain.vo.QqchQualityRiskControlMeasuresVo;
import com.hhwy.pm.qqch.preparation.quality.qqchQualityRiskControlMeasures.service.IQqchQualityRiskControlMeasuresService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author ldd
 * @date 2023-08-04 11:20:54
 * @remark 9.3.2 质量风险管控措施
 */
@Validated
@RestController
@RequestMapping("/qqchQualityRiskControlMeasures")
public class QqchQualityRiskControlMeasuresController extends BaseController {

    @Autowired
    private IQqchQualityRiskControlMeasuresService qqchQualityRiskControlMeasuresService;


    /**
     * 列表页面
     *
     * @param qqchQualityRiskControlMeasuresParam
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchQualityRiskControlMeasures:list")
    @GetMapping("/list")
    public AjaxResult getQqchQualityRiskControlMeasuresList(@Validated(ValidationGroups.Select.class) QqchQualityRiskControlMeasures qqchQualityRiskControlMeasuresParam) {
        QqchQualityRiskControlMeasuresVo vo = qqchQualityRiskControlMeasuresService.getQqchQualityRiskControlMeasuresList(qqchQualityRiskControlMeasuresParam);
        return AjaxResult.success(vo);
    }


    /**
     * 保存/确认/提交
     *
     * @param vo
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchQualityRiskControlMeasures:save")
    @PostMapping("/save")
    public AjaxResult save(@Validated(ValidationGroups.Save.class) @RequestBody QqchQualityRiskControlMeasuresVo vo) {
        qqchQualityRiskControlMeasuresService.save(vo);
        return AjaxResult.success();
    }

    //    @PreAuthorize(hasPermi = "qqchQualityRiskControlMeasures:list")
    @GetMapping
    public AjaxResult getQqchQualityRiskControlMeasures(@Validated(ValidationGroups.Get.class) QqchQualityRiskControlMeasures qqchQualityRiskControlMeasuresParam) {
        QqchQualityRiskControlMeasures qqchQualityRiskControlMeasures = qqchQualityRiskControlMeasuresService.getQqchQualityRiskControlMeasures(qqchQualityRiskControlMeasuresParam);
        return AjaxResult.success(qqchQualityRiskControlMeasures);
    }

    //    @PreAuthorize(hasPermi = "qqchQualityRiskControlMeasures:add")
    @PostMapping("/add")
    public AjaxResult insertQqchQualityRiskControlMeasures(@Validated(ValidationGroups.Save.class) @RequestBody QqchQualityRiskControlMeasures qqchQualityRiskControlMeasuresParam) {
        qqchQualityRiskControlMeasuresService.insertQqchQualityRiskControlMeasures(qqchQualityRiskControlMeasuresParam);
        return AjaxResult.success(qqchQualityRiskControlMeasuresParam);
    }

    //    @PreAuthorize(hasPermi = "qqchQualityRiskControlMeasures:update")
    @PostMapping("/update")
    public AjaxResult updateQqchQualityRiskControlMeasures(@Validated(ValidationGroups.Update.class) @RequestBody QqchQualityRiskControlMeasures qqchQualityRiskControlMeasuresParam) {
        return toAjax(qqchQualityRiskControlMeasuresService.updateQqchQualityRiskControlMeasures(qqchQualityRiskControlMeasuresParam));
    }

//    @PreAuthorize(hasPermi = "qqchQualityRiskControlMeasures:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchQualityRiskControlMeasuresList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchQualityRiskControlMeasures> qqchQualityRiskControlMeasuresListParam) {
        return toAjax(qqchQualityRiskControlMeasuresService.updateQqchQualityRiskControlMeasuresList(qqchQualityRiskControlMeasuresListParam));
    }

//    @PreAuthorize(hasPermi = "qqchQualityRiskControlMeasures:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchQualityRiskControlMeasures(@Validated(ValidationGroups.Delete.class) @RequestBody QqchQualityRiskControlMeasures qqchQualityRiskControlMeasuresParam) {
        return toAjax(qqchQualityRiskControlMeasuresService.deleteQqchQualityRiskControlMeasures(qqchQualityRiskControlMeasuresParam));
    }

//    @PreAuthorize(hasPermi = "qqchQualityRiskControlMeasures:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchQualityRiskControlMeasuresByPks(@PathVariable Long[] ids) {
        List<Long> qqchQualityRiskControlMeasuresPkList = Arrays.asList(ids);
        return toAjax(qqchQualityRiskControlMeasuresService.deleteQqchQualityRiskControlMeasuresByPks(qqchQualityRiskControlMeasuresPkList));
    }
}
