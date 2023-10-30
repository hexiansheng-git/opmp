package com.hhwy.pm.qqch.preparation.safe.qqchPublicSafeControlMeasure.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.safe.qqchPublicSafeControlMeasure.domain.QqchPublicSafeControlMeasure;
import com.hhwy.pm.qqch.preparation.safe.qqchPublicSafeControlMeasure.domain.vo.QqchPublicSafeControlMeasureVo;
import com.hhwy.pm.qqch.preparation.safe.qqchPublicSafeControlMeasure.service.IQqchPublicSafeControlMeasureService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author ldd
 * @date 2023-08-09 09:30:52
 * @remark
 */
@Validated
@RestController
@RequestMapping("/qqchPublicSafeControlMeasure")
public class QqchPublicSafeControlMeasureController extends BaseController {

    @Autowired
    private IQqchPublicSafeControlMeasureService qqchPublicSafeControlMeasureService;

    /**
     * 列表接口
     *
     * @param qqchPublicSafeControlMeasureParam
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchPublicSafeControlMeasure:list")
    @GetMapping("/list")
    public AjaxResult getQqchPublicSafeControlMeasureList(@Validated(ValidationGroups.Select.class) QqchPublicSafeControlMeasure qqchPublicSafeControlMeasureParam) {
        QqchPublicSafeControlMeasureVo vo = qqchPublicSafeControlMeasureService.getQqchPublicSafeControlMeasureList(qqchPublicSafeControlMeasureParam);
        return AjaxResult.success(vo);
    }

    /**
     * 保存/确认/提交
     *
     * @param vo
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchPublicSafeControlMeasure:save")
    @PostMapping("/save")
    public AjaxResult insertQqchPublicSafeControlMeasureList(@Validated(ValidationGroups.Save.class) @RequestBody QqchPublicSafeControlMeasureVo vo) {
        qqchPublicSafeControlMeasureService.save(vo);
        return AjaxResult.success();
    }


//    @PreAuthorize(hasPermi = "qqchPublicSafeControlMeasure:list")
    @GetMapping
    public AjaxResult getQqchPublicSafeControlMeasure(@Validated(ValidationGroups.Get.class) QqchPublicSafeControlMeasure qqchPublicSafeControlMeasureParam) {
        QqchPublicSafeControlMeasure qqchPublicSafeControlMeasure = qqchPublicSafeControlMeasureService.getQqchPublicSafeControlMeasure(qqchPublicSafeControlMeasureParam);
        return AjaxResult.success(qqchPublicSafeControlMeasure);
    }


//    @PreAuthorize(hasPermi = "qqchPublicSafeControlMeasure:add")
    @PostMapping("/add")
    public AjaxResult insertQqchPublicSafeControlMeasure(@Validated(ValidationGroups.Save.class) @RequestBody QqchPublicSafeControlMeasure qqchPublicSafeControlMeasureParam) {
        qqchPublicSafeControlMeasureService.insertQqchPublicSafeControlMeasure(qqchPublicSafeControlMeasureParam);
        return AjaxResult.success(qqchPublicSafeControlMeasureParam);
    }


//    @PreAuthorize(hasPermi = "qqchPublicSafeControlMeasure:update")
    @PostMapping("/update")
    public AjaxResult updateQqchPublicSafeControlMeasure(@Validated(ValidationGroups.Update.class) @RequestBody QqchPublicSafeControlMeasure qqchPublicSafeControlMeasureParam) {
        return toAjax(qqchPublicSafeControlMeasureService.updateQqchPublicSafeControlMeasure(qqchPublicSafeControlMeasureParam));
    }

//    @PreAuthorize(hasPermi = "qqchPublicSafeControlMeasure:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchPublicSafeControlMeasureList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchPublicSafeControlMeasure> qqchPublicSafeControlMeasureListParam) {
        return toAjax(qqchPublicSafeControlMeasureService.updateQqchPublicSafeControlMeasureList(qqchPublicSafeControlMeasureListParam));
    }

//    @PreAuthorize(hasPermi = "qqchPublicSafeControlMeasure:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchPublicSafeControlMeasure(@Validated(ValidationGroups.Delete.class) @RequestBody QqchPublicSafeControlMeasure qqchPublicSafeControlMeasureParam) {
        return toAjax(qqchPublicSafeControlMeasureService.deleteQqchPublicSafeControlMeasure(qqchPublicSafeControlMeasureParam));
    }

//    @PreAuthorize(hasPermi = "qqchPublicSafeControlMeasure:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchPublicSafeControlMeasureByPks(@PathVariable Long[] ids) {
        List<Long> qqchPublicSafeControlMeasurePkList = Arrays.asList(ids);
        return toAjax(qqchPublicSafeControlMeasureService.deleteQqchPublicSafeControlMeasureByPks(qqchPublicSafeControlMeasurePkList));
    }

}
