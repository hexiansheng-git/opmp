package com.hhwy.pm.qqch.sgch.sbzx.qqchConstructionEquipment.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.sgch.sbzx.qqchConstructionEquipment.domain.QqchConstructionEquipment;
import com.hhwy.pm.qqch.sgch.sbzx.qqchConstructionEquipment.domain.vo.QqchConstructionEquipmentVo;
import com.hhwy.pm.qqch.sgch.sbzx.qqchConstructionEquipment.service.IQqchConstructionEquipmentService;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author ldd
 * @date 2023-08-01 16:22:12
 * @remark 1.7.1 施工设备
 */
@Validated
@RestController
@RequestMapping("/qqchConstructionEquipment")
public class QqchConstructionEquipmentController extends BaseController {

    @Autowired
    private IQqchConstructionEquipmentService qqchConstructionEquipmentService;


    //    @PreAuthorize(hasPermi = "qqchConstructionEquipment:list")
    @GetMapping
    @CustomLogger(title = "前期策划-前期策划编制-施工策划-设备总需计划", name = "1.7.1施工设备", businessType = CustomBusinessType.SELECT)
    public AjaxResult getQqchConstructionEquipment(@Validated(ValidationGroups.Get.class) QqchConstructionEquipment qqchConstructionEquipmentParam) {
        QqchConstructionEquipment qqchConstructionEquipment = qqchConstructionEquipmentService.getQqchConstructionEquipment(qqchConstructionEquipmentParam);
        return AjaxResult.success(qqchConstructionEquipment);
    }

    /**
     * 列表接口
     *
     * @param qqchConstructionEquipmentParam
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchConstructionEquipment:list")
    @GetMapping("/list")
    @CustomLogger(title = "前期策划-前期策划编制-施工策划-设备总需计划", name = "1.7.1施工设备", businessType = CustomBusinessType.SELECT)
    public AjaxResult getQqchConstructionEquipmentList(@Validated(ValidationGroups.Select.class) QqchConstructionEquipment qqchConstructionEquipmentParam) {
        QqchConstructionEquipmentVo vo = qqchConstructionEquipmentService.getQqchConstructionEquipmentList(qqchConstructionEquipmentParam);
        return AjaxResult.success(vo);
    }


    /**
     * 保存/确认/提交
     *
     * @param qqchConstructionEquipmentVo
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchConstructionEquipment:save")
    @PostMapping("/save")
    @CustomLogger(title = "前期策划-前期策划编制-施工策划-设备总需计划", name = "1.7.1施工设备", businessType = CustomBusinessType.SAVE)
    public AjaxResult insertQqchConstructionEquipment(@Validated(ValidationGroups.Save.class) @RequestBody QqchConstructionEquipmentVo qqchConstructionEquipmentVo) {
        qqchConstructionEquipmentService.save(qqchConstructionEquipmentVo);
        return AjaxResult.success();
    }


    //    @PreAuthorize(hasPermi = "qqchConstructionEquipment:update")
    @PostMapping("/update")
    public AjaxResult updateQqchConstructionEquipment(@Validated(ValidationGroups.Update.class) @RequestBody QqchConstructionEquipment qqchConstructionEquipmentParam) {
        return toAjax(qqchConstructionEquipmentService.updateQqchConstructionEquipment(qqchConstructionEquipmentParam));
    }

    //        @PreAuthorize(hasPermi = "qqchConstructionEquipment:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchConstructionEquipmentList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchConstructionEquipment> qqchConstructionEquipmentListParam) {
        return toAjax(qqchConstructionEquipmentService.updateQqchConstructionEquipmentList(qqchConstructionEquipmentListParam));
    }

    //    @PreAuthorize(hasPermi = "qqchConstructionEquipment:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchConstructionEquipment(@Validated(ValidationGroups.Delete.class) @RequestBody QqchConstructionEquipment qqchConstructionEquipmentParam) {
        return toAjax(qqchConstructionEquipmentService.deleteQqchConstructionEquipment(qqchConstructionEquipmentParam));
    }

    //            @PreAuthorize(hasPermi = "qqchConstructionEquipment:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchConstructionEquipmentByPks(@PathVariable Long[] ids) {
        List<Long> qqchConstructionEquipmentPkList = Arrays.asList(ids);
        return toAjax(qqchConstructionEquipmentService.deleteQqchConstructionEquipmentByPks(qqchConstructionEquipmentPkList));
    }

}
