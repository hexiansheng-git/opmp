package com.hhwy.pm.qqch.preparation.safe.qqchDangerJobIdentification.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.safe.qqchDangerJobIdentification.domain.QqchDangerJobIdentification;
import com.hhwy.pm.qqch.preparation.safe.qqchDangerJobIdentification.domain.vo.QqchDangerJobIdentificationVo;
import com.hhwy.pm.qqch.preparation.safe.qqchDangerJobIdentification.service.IQqchDangerJobIdentificationService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author ldd
 * @date 2023-08-09 11:55:28
 * @remark 8.5 危险作业辨识
 */
@Validated
@RestController
@RequestMapping("/qqchDangerJobIdentification")
public class QqchDangerJobIdentificationController extends BaseController {

    @Autowired
    private IQqchDangerJobIdentificationService qqchDangerJobIdentificationService;

    /**
     * 列表接口
     *
     * @param qqchDangerJobIdentificationParam
     * @return
     */
    @PreAuthorize(hasPermi = "qqchDangerJobIdentification:list")
    @GetMapping("/list")
    public AjaxResult getQqchDangerJobIdentificationList(@Validated(ValidationGroups.Select.class) QqchDangerJobIdentification qqchDangerJobIdentificationParam) {
        QqchDangerJobIdentificationVo vo = qqchDangerJobIdentificationService.getQqchDangerJobIdentificationList(qqchDangerJobIdentificationParam);
        return AjaxResult.success(vo);
    }

    /**
     *  保存/确认/提交
     * @param vo
     * @return
     */
    @PreAuthorize(hasPermi = "qqchDangerJobIdentification:save")
    @PostMapping("/save")
    public AjaxResult insertQqchDangerJobIdentificationList(@Validated(ValidationGroups.Save.class) @RequestBody QqchDangerJobIdentificationVo vo) {
        qqchDangerJobIdentificationService.save(vo);
        return AjaxResult.success();
    }


    @PreAuthorize(hasPermi = "qqchDangerJobIdentification:list")
    @GetMapping
    public AjaxResult getQqchDangerJobIdentification(@Validated(ValidationGroups.Get.class) QqchDangerJobIdentification qqchDangerJobIdentificationParam) {
        QqchDangerJobIdentification qqchDangerJobIdentification = qqchDangerJobIdentificationService.getQqchDangerJobIdentification(qqchDangerJobIdentificationParam);
        return AjaxResult.success(qqchDangerJobIdentification);
    }


    @PreAuthorize(hasPermi = "qqchDangerJobIdentification:add")
    @PostMapping("/add")
    public AjaxResult insertQqchDangerJobIdentification(@Validated(ValidationGroups.Save.class) @RequestBody QqchDangerJobIdentification qqchDangerJobIdentificationParam) {
        qqchDangerJobIdentificationService.insertQqchDangerJobIdentification(qqchDangerJobIdentificationParam);
        return AjaxResult.success(qqchDangerJobIdentificationParam);
    }


    @PreAuthorize(hasPermi = "qqchDangerJobIdentification:update")
    @PostMapping("/update")
    public AjaxResult updateQqchDangerJobIdentification(@Validated(ValidationGroups.Update.class) @RequestBody QqchDangerJobIdentification qqchDangerJobIdentificationParam) {
        return toAjax(qqchDangerJobIdentificationService.updateQqchDangerJobIdentification(qqchDangerJobIdentificationParam));
    }

    @PreAuthorize(hasPermi = "qqchDangerJobIdentification:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchDangerJobIdentificationList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchDangerJobIdentification> qqchDangerJobIdentificationListParam) {
        return toAjax(qqchDangerJobIdentificationService.updateQqchDangerJobIdentificationList(qqchDangerJobIdentificationListParam));
    }

    @PreAuthorize(hasPermi = "qqchDangerJobIdentification:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchDangerJobIdentification(@Validated(ValidationGroups.Delete.class) @RequestBody QqchDangerJobIdentification qqchDangerJobIdentificationParam) {
        return toAjax(qqchDangerJobIdentificationService.deleteQqchDangerJobIdentification(qqchDangerJobIdentificationParam));
    }

    @PreAuthorize(hasPermi = "qqchDangerJobIdentification:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchDangerJobIdentificationByPks(@PathVariable Long[] ids) {
        List<Long> qqchDangerJobIdentificationPkList = Arrays.asList(ids);
        return toAjax(qqchDangerJobIdentificationService.deleteQqchDangerJobIdentificationByPks(qqchDangerJobIdentificationPkList));
    }

}
