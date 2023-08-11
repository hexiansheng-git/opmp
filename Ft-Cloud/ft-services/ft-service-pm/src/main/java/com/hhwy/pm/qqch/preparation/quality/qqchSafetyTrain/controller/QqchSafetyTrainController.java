package com.hhwy.pm.qqch.preparation.quality.qqchSafetyTrain.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.quality.qqchSafetyTrain.domain.QqchSafetyTrain;
import com.hhwy.pm.qqch.preparation.quality.qqchSafetyTrain.domain.vo.QqchSafetyTrainVo;
import com.hhwy.pm.qqch.preparation.quality.qqchSafetyTrain.service.IQqchSafetyTrainService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author ldd
 * @date 2023-08-10 18:38:55
 * @remark 8.9 安全培训策划
 */
@Validated
@RestController
@RequestMapping("/qqchSafetyTrain")
public class QqchSafetyTrainController extends BaseController {

    @Autowired
    private IQqchSafetyTrainService qqchSafetyTrainService;


    /**
     * 列表接口
     *
     * @param qqchSafetyTrainParam
     * @return
     */
    @PreAuthorize(hasPermi = "qqchSafetyTrain:list")
    @GetMapping("/list")
    public AjaxResult getQqchSafetyTrainList(@Validated(ValidationGroups.Select.class) QqchSafetyTrain qqchSafetyTrainParam) {
        QqchSafetyTrainVo vo = qqchSafetyTrainService.getQqchSafetyTrainList(qqchSafetyTrainParam);
        return AjaxResult.success(vo);
    }

    /**
     * 确认/修改/提交
     * @param vo
     * @return
     */
    @PreAuthorize(hasPermi = "qqchSafetyTrain:save")
    @PostMapping("/save")
    public AjaxResult insertQqchSafetyTrainList(@Validated(ValidationGroups.Save.class) @RequestBody QqchSafetyTrainVo vo) {
        qqchSafetyTrainService.save(vo);
        return AjaxResult.success();
    }


    @PreAuthorize(hasPermi = "qqchSafetyTrain:list")
    @GetMapping
    public AjaxResult getQqchSafetyTrain(@Validated(ValidationGroups.Get.class) QqchSafetyTrain qqchSafetyTrainParam) {
        QqchSafetyTrain qqchSafetyTrain = qqchSafetyTrainService.getQqchSafetyTrain(qqchSafetyTrainParam);
        return AjaxResult.success(qqchSafetyTrain);
    }


    @PreAuthorize(hasPermi = "qqchSafetyTrain:add")
    @PostMapping("/add")
    public AjaxResult insertQqchSafetyTrain(@Validated(ValidationGroups.Save.class) @RequestBody QqchSafetyTrain qqchSafetyTrainParam) {
        qqchSafetyTrainService.insertQqchSafetyTrain(qqchSafetyTrainParam);
        return AjaxResult.success(qqchSafetyTrainParam);
    }


    @PreAuthorize(hasPermi = "qqchSafetyTrain:update")
    @PostMapping("/update")
    public AjaxResult updateQqchSafetyTrain(@Validated(ValidationGroups.Update.class) @RequestBody QqchSafetyTrain qqchSafetyTrainParam) {
        return toAjax(qqchSafetyTrainService.updateQqchSafetyTrain(qqchSafetyTrainParam));
    }

    @PreAuthorize(hasPermi = "qqchSafetyTrain:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchSafetyTrainList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchSafetyTrain> qqchSafetyTrainListParam) {
        return toAjax(qqchSafetyTrainService.updateQqchSafetyTrainList(qqchSafetyTrainListParam));
    }

    @PreAuthorize(hasPermi = "qqchSafetyTrain:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchSafetyTrain(@Validated(ValidationGroups.Delete.class) @RequestBody QqchSafetyTrain qqchSafetyTrainParam) {
        return toAjax(qqchSafetyTrainService.deleteQqchSafetyTrain(qqchSafetyTrainParam));
    }

    @PreAuthorize(hasPermi = "qqchSafetyTrain:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchSafetyTrainByPks(@PathVariable Long[] ids) {
        List<Long> qqchSafetyTrainPkList = Arrays.asList(ids);
        return toAjax(qqchSafetyTrainService.deleteQqchSafetyTrainByPks(qqchSafetyTrainPkList));
    }

}
