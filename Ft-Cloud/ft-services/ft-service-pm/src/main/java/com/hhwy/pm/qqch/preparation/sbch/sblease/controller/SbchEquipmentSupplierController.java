package com.hhwy.pm.qqch.preparation.sbch.sblease.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.preparation.sbch.sblease.domain.LeaseVo;
import com.hhwy.pm.qqch.preparation.sbch.sblease.service.ISbchEquipmentService;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * @author zqq
 * @create 2023-08-26 15:21
 */
@RestController
@RequestMapping("/leasesupplier/supplier")
public class SbchEquipmentSupplierController extends BaseController {
    @Autowired
    private ISbchEquipmentService sbchEquipmentService;
    @GetMapping("/getList")
    public AjaxResult getList(BigDecimal version){
        LeaseVo leaseVo = sbchEquipmentService.getList(version);
        return AjaxResult.success(leaseVo);
    }

    @PostMapping("/batchAdd")
    public AjaxResult batchAdd(@Validated(ValidationGroups.Save.class) @RequestBody LeaseVo leaseVo){
        try {
            sbchEquipmentService.batchAdd(leaseVo);
            return AjaxResult.success();
        }catch (CustomBusinessException e){
            e.printStackTrace();
            return AjaxResult.error(e.getMsg());
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
    }

}
