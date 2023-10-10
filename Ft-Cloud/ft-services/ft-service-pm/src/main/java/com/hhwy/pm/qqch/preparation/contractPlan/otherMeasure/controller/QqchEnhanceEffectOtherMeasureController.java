package com.hhwy.pm.qqch.preparation.contractPlan.otherMeasure.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
//import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.contractPlan.otherMeasure.domain.QqchEnhanceEffectOtherMeasure;
import com.hhwy.pm.qqch.preparation.contractPlan.otherMeasure.domain.vo.QqchEnhanceEffectOtherMeasureVo;
import com.hhwy.pm.qqch.preparation.contractPlan.otherMeasure.service.IQqchEnhanceEffectOtherMeasureService;
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
 * @date 2023-08-08 17:08:56
 * @remark 提高经营效果的其他措施
 */
@Validated
@RestController
@RequestMapping("/qqchEnhanceEffectOtherMeasure")
public class QqchEnhanceEffectOtherMeasureController extends BaseController {

    @Autowired
    private IQqchEnhanceEffectOtherMeasureService qqchEnhanceEffectOtherMeasureService;


//    @PreAuthorize(hasPermi = "qqchEnhanceEffectOtherMeasure:list")
    @GetMapping
    public AjaxResult getQqchEnhanceEffectOtherMeasure(@Validated(ValidationGroups.Get.class) QqchEnhanceEffectOtherMeasure qqchEnhanceEffectOtherMeasureParam) {
        QqchEnhanceEffectOtherMeasure qqchEnhanceEffectOtherMeasure = qqchEnhanceEffectOtherMeasureService.getQqchEnhanceEffectOtherMeasure(qqchEnhanceEffectOtherMeasureParam);
        return AjaxResult.success(qqchEnhanceEffectOtherMeasure);
    }

//    @PreAuthorize(hasPermi = "qqchEnhanceEffectOtherMeasure:list")
    @GetMapping("/list")
    public AjaxResult getQqchEnhanceEffectOtherMeasureList(@Validated(ValidationGroups.Select.class) QqchEnhanceEffectOtherMeasure qqchEnhanceEffectOtherMeasureParam) {
        startPage();
        List<QqchEnhanceEffectOtherMeasure> qqchEnhanceEffectOtherMeasureList = qqchEnhanceEffectOtherMeasureService.getQqchEnhanceEffectOtherMeasureList(qqchEnhanceEffectOtherMeasureParam);
        return getDataTableAjaxResult(qqchEnhanceEffectOtherMeasureList);
    }

//    @PreAuthorize(hasPermi = "qqchEnhanceEffectOtherMeasure:add")
    @PostMapping("/add")
    public AjaxResult insertQqchEnhanceEffectOtherMeasure(@Validated(ValidationGroups.Save.class) @RequestBody QqchEnhanceEffectOtherMeasure qqchEnhanceEffectOtherMeasureParam) {
        qqchEnhanceEffectOtherMeasureService.insertQqchEnhanceEffectOtherMeasure(qqchEnhanceEffectOtherMeasureParam);
        return AjaxResult.success(qqchEnhanceEffectOtherMeasureParam);
    }

//    @PreAuthorize(hasPermi = "qqchEnhanceEffectOtherMeasure:update")
    @PostMapping("/update")
    public AjaxResult updateQqchEnhanceEffectOtherMeasure(@Validated(ValidationGroups.Update.class) @RequestBody QqchEnhanceEffectOtherMeasure qqchEnhanceEffectOtherMeasureParam) {
        return toAjax(qqchEnhanceEffectOtherMeasureService.updateQqchEnhanceEffectOtherMeasure(qqchEnhanceEffectOtherMeasureParam));
    }

//    @PreAuthorize(hasPermi = "qqchEnhanceEffectOtherMeasure:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchEnhanceEffectOtherMeasureList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchEnhanceEffectOtherMeasure> qqchEnhanceEffectOtherMeasureListParam) {
        return toAjax(qqchEnhanceEffectOtherMeasureService.updateQqchEnhanceEffectOtherMeasureList(qqchEnhanceEffectOtherMeasureListParam));
    }

//    @PreAuthorize(hasPermi = "qqchEnhanceEffectOtherMeasure:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchEnhanceEffectOtherMeasure(@Validated(ValidationGroups.Delete.class) @RequestBody QqchEnhanceEffectOtherMeasure qqchEnhanceEffectOtherMeasureParam) {
        return toAjax(qqchEnhanceEffectOtherMeasureService.deleteQqchEnhanceEffectOtherMeasure(qqchEnhanceEffectOtherMeasureParam));
    }

//    @PreAuthorize(hasPermi = "qqchEnhanceEffectOtherMeasure:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchEnhanceEffectOtherMeasureByPks(@PathVariable Long[] ids) {
        List<Long> qqchEnhanceEffectOtherMeasurePkList = Arrays.asList(ids);
        return toAjax(qqchEnhanceEffectOtherMeasureService.deleteQqchEnhanceEffectOtherMeasureByPks(qqchEnhanceEffectOtherMeasurePkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchEnhanceEffectOtherMeasure qqchEnhanceEffectOtherMeasureParam) throws IOException {
        List<QqchEnhanceEffectOtherMeasure> qqchEnhanceEffectOtherMeasureList = qqchEnhanceEffectOtherMeasureService.getQqchEnhanceEffectOtherMeasureList(qqchEnhanceEffectOtherMeasureParam);
        ExcelUtils<QqchEnhanceEffectOtherMeasure> util = new ExcelUtils<>(QqchEnhanceEffectOtherMeasure.class);
        util.exportExcel(response, qqchEnhanceEffectOtherMeasureList, DateUtils.getDate());
    }

    /**
     * 获取Vo
     * @param qqchEnhanceEffectOtherMeasure
     * @return
     */
    @GetMapping("getQqchEnhanceEffectOtherMeasureVo")
    public AjaxResult getQqchEnhanceEffectOtherMeasureVo(@Validated(ValidationGroups.Get.class) QqchEnhanceEffectOtherMeasure qqchEnhanceEffectOtherMeasure) {
        QqchEnhanceEffectOtherMeasureVo qqchEnhanceEffectOtherMeasureVo = qqchEnhanceEffectOtherMeasureService.getQqchEnhanceEffectOtherMeasureVo(qqchEnhanceEffectOtherMeasure);
        return AjaxResult.success(qqchEnhanceEffectOtherMeasureVo);
    }

    /**
     * 保存/确认/提交
     * @param qqchEnhanceEffectOtherMeasureVo
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchEnhanceEffectOtherMeasure:save")
    @PostMapping("/save")
    public AjaxResult save(@Validated(ValidationGroups.Save.class) @RequestBody QqchEnhanceEffectOtherMeasureVo qqchEnhanceEffectOtherMeasureVo) {
        qqchEnhanceEffectOtherMeasureService.save(qqchEnhanceEffectOtherMeasureVo);
        return AjaxResult.success();
    }

}
