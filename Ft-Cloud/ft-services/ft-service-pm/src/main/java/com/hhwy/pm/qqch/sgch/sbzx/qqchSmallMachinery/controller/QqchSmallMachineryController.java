package com.hhwy.pm.qqch.sgch.sbzx.qqchSmallMachinery.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.sgch.sbzx.qqchSmallMachinery.domain.QqchSmallMachinery;
import com.hhwy.pm.qqch.sgch.sbzx.qqchSmallMachinery.domain.vo.QqchSmallMachineryVo;
import com.hhwy.pm.qqch.sgch.sbzx.qqchSmallMachinery.service.IQqchSmallMachineryService;
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
 * @date 2023-08-02 10:50:59
 * @remark 1.7.4  小型机具
 */
@Validated
@RestController
@RequestMapping("/qqchSmallMachinery")
public class QqchSmallMachineryController extends BaseController {

    @Autowired
    private IQqchSmallMachineryService qqchSmallMachineryService;


    //    @PreAuthorize(hasPermi = "qqchSmallMachinery:list")
    @GetMapping
    @CustomLogger(title = "前期策划-前期策划编制-施工策划-设备总需计划", name = "1.7.4小型机具" ,businessType = CustomBusinessType.SELECT)
    public AjaxResult getQqchSmallMachinery(@Validated(ValidationGroups.Get.class) QqchSmallMachinery qqchSmallMachineryParam) {
        QqchSmallMachinery qqchSmallMachinery = qqchSmallMachineryService.getQqchSmallMachinery(qqchSmallMachineryParam);
        return AjaxResult.success(qqchSmallMachinery);
    }

    /**
     * 拉取 施工部署数据(设备策划)
     *
     * @param qqchSmallMachineryVo
     * @return
     */
    @PostMapping("/syncData")
    @CustomLogger(title = "前期策划-前期策划编制-施工策划-设备总需计划", name = "1.7.4小型机具" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult syncData(@Validated(ValidationGroups.Save.class) @RequestBody QqchSmallMachineryVo qqchSmallMachineryVo) {
        QqchSmallMachineryVo vo = qqchSmallMachineryService.syncData(qqchSmallMachineryVo);
        return AjaxResult.success(vo);
    }

    /**
     * 列表接口
     *
     * @param qqchSmallMachineryParam
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchSmallMachinery:list")
    @GetMapping("/list")
    @CustomLogger(title = "前期策划-前期策划编制-施工策划-设备总需计划", name = "1.7.4小型机具" ,businessType = CustomBusinessType.SELECT)
    public AjaxResult getQqchSmallMachineryList(@Validated(ValidationGroups.Select.class) QqchSmallMachinery qqchSmallMachineryParam) {
        QqchSmallMachineryVo vo = qqchSmallMachineryService.getQqchSmallMachineryList(qqchSmallMachineryParam);
        return AjaxResult.success(vo);
    }

    /**
     * 新增接口
     *
     * @param qqchSmallMachineryVo
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchSmallMachinery:save")
    @PostMapping("/save")
    @CustomLogger(title = "前期策划-前期策划编制-施工策划-设备总需计划", name = "1.7.4小型机具" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult save(@Validated(ValidationGroups.Save.class) @RequestBody QqchSmallMachineryVo qqchSmallMachineryVo) {
        qqchSmallMachineryService.save(qqchSmallMachineryVo);
        return AjaxResult.success();
    }

    //    @PreAuthorize(hasPermi = "qqchSmallMachinery:add")
    @PostMapping("/add")
    @CustomLogger(title = "前期策划-前期策划编制-施工策划-设备总需计划", name = "1.7.4小型机具" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult insertQqchSmallMachinery(@Validated(ValidationGroups.Save.class) @RequestBody QqchSmallMachinery qqchSmallMachineryParam) {
        qqchSmallMachineryService.insertQqchSmallMachinery(qqchSmallMachineryParam);
        return AjaxResult.success(qqchSmallMachineryParam);
    }


    //    @PreAuthorize(hasPermi = "qqchSmallMachinery:update")
    @PostMapping("/update")
    public AjaxResult updateQqchSmallMachinery(@Validated(ValidationGroups.Update.class) @RequestBody QqchSmallMachinery qqchSmallMachineryParam) {
        return toAjax(qqchSmallMachineryService.updateQqchSmallMachinery(qqchSmallMachineryParam));
    }

    //            @PreAuthorize(hasPermi = "qqchSmallMachinery:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchSmallMachineryList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchSmallMachinery> qqchSmallMachineryListParam) {
        return toAjax(qqchSmallMachineryService.updateQqchSmallMachineryList(qqchSmallMachineryListParam));
    }

    //    @PreAuthorize(hasPermi = "qqchSmallMachinery:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchSmallMachinery(@Validated(ValidationGroups.Delete.class) @RequestBody QqchSmallMachinery qqchSmallMachineryParam) {
        return toAjax(qqchSmallMachineryService.deleteQqchSmallMachinery(qqchSmallMachineryParam));
    }

    //            @PreAuthorize(hasPermi = "qqchSmallMachinery:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchSmallMachineryByPks(@PathVariable Long[] ids) {
        List<Long> qqchSmallMachineryPkList = Arrays.asList(ids);
        return toAjax(qqchSmallMachineryService.deleteQqchSmallMachineryByPks(qqchSmallMachineryPkList));
    }


}
