package com.hhwy.pm.qqch.sgch.sbzx.qqchTrafficCar.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.sgch.sbzx.qqchTrafficCar.domain.QqchTrafficCar;
import com.hhwy.pm.qqch.sgch.sbzx.qqchTrafficCar.domain.vo.QqchTrafficCarVo;
import com.hhwy.pm.qqch.sgch.sbzx.qqchTrafficCar.service.IQqchTrafficCarService;
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
 * @date 2023-08-01 16:12:57
 * @remark
 */
@Validated
@RestController
@RequestMapping("/qqchTrafficCar")
public class QqchTrafficCarController extends BaseController {

    @Autowired
    private IQqchTrafficCarService qqchTrafficCarService;


    //    @PreAuthorize(hasPermi = "qqchTrafficCar:list")
    @GetMapping
    @CustomLogger(title = "前期策划-前期策划编制-施工策划-设备总需计划", name = "1.7.2交通车辆" ,businessType = CustomBusinessType.SELECT)
    public AjaxResult getQqchTrafficCar(@Validated(ValidationGroups.Get.class) QqchTrafficCar qqchTrafficCarParam) {
        QqchTrafficCar qqchTrafficCar = qqchTrafficCarService.getQqchTrafficCar(qqchTrafficCarParam);
        return AjaxResult.success(qqchTrafficCar);
    }

    /**
     * 列表接口
     *
     * @param qqchTrafficCarParam
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchTrafficCar:list")
    @GetMapping("/list")
    @CustomLogger(title = "前期策划-前期策划编制-施工策划-设备总需计划", name = "1.7.2交通车辆" ,businessType = CustomBusinessType.SELECT)
    public AjaxResult getQqchTrafficCarList(@Validated(ValidationGroups.Select.class) QqchTrafficCar qqchTrafficCarParam) {
        QqchTrafficCarVo vo = qqchTrafficCarService.getQqchTrafficCarList(qqchTrafficCarParam);
        return AjaxResult.success(vo);
    }

    /**
     * 保存/确认/提交
     *
     * @param qqchTrafficCarParam
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchTrafficCar:save")
    @PostMapping("/save")
    @CustomLogger(title = "前期策划-前期策划编制-施工策划-设备总需计划", name = "1.7.2交通车辆" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult insertQqchTrafficCar(@Validated(ValidationGroups.Save.class) @RequestBody QqchTrafficCarVo qqchTrafficCarParam) {
        qqchTrafficCarService.save(qqchTrafficCarParam);
        return AjaxResult.success();
    }


    //    @PreAuthorize(hasPermi = "qqchTrafficCar:update")
    @PostMapping("/update")
    public AjaxResult updateQqchTrafficCar(@Validated(ValidationGroups.Update.class) @RequestBody QqchTrafficCar qqchTrafficCarParam) {
        return toAjax(qqchTrafficCarService.updateQqchTrafficCar(qqchTrafficCarParam));
    }

    //            @PreAuthorize(hasPermi = "qqchTrafficCar:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchTrafficCarList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchTrafficCar> qqchTrafficCarListParam) {
        return toAjax(qqchTrafficCarService.updateQqchTrafficCarList(qqchTrafficCarListParam));
    }

//    @PreAuthorize(hasPermi = "qqchTrafficCar:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchTrafficCar(@Validated(ValidationGroups.Delete.class) @RequestBody QqchTrafficCar qqchTrafficCarParam) {
        return toAjax(qqchTrafficCarService.deleteQqchTrafficCar(qqchTrafficCarParam));
    }

//    @PreAuthorize(hasPermi = "qqchTrafficCar:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchTrafficCarByPks(@PathVariable Long[] ids) {
        List<Long> qqchTrafficCarPkList = Arrays.asList(ids);
        return toAjax(qqchTrafficCarService.deleteQqchTrafficCarByPks(qqchTrafficCarPkList));
    }


}
