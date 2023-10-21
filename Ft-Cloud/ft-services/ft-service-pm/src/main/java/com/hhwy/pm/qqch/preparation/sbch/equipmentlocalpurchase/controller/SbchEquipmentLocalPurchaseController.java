package com.hhwy.pm.qqch.preparation.sbch.equipmentlocalpurchase.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.page.TableDataInfo;
import com.hhwy.common.security.annotation.PreAuthorize;

import com.hhwy.pm.qqch.preparation.sbch.equipmentlocalpurchase.domain.SbchEquipmentLocalPurchase;
import com.hhwy.pm.qqch.preparation.sbch.equipmentlocalpurchase.service.ISbchEquipmentLocalPurchaseDetailsService;
import com.hhwy.pm.qqch.preparation.sbch.equipmentlocalpurchase.service.ISbchEquipmentLocalPurchaseService;
import com.hhwy.utils.common.PmsConstant;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 设备申购管理Controller
 * 
 * @author hwj
 * @date 2022-11-22
 *
 * 7.2.5
 */
//@Validated
@RestController
@RequestMapping("/equipmentlocalpurchase/purchase")
public class SbchEquipmentLocalPurchaseController extends BaseController {
    @Autowired
    private ISbchEquipmentLocalPurchaseService sbchEquipmentPurchaseService;

    /**
     * 查询设备申购管理列表
     */
    @PreAuthorize(hasPermi ="equipmentlocalpurchase:purchase:list")
    @GetMapping("/list")
    //@CustomLogger(title = "设备属地化采购-列表查询",businessType = CustomBusinessType.SELECT)
    public AjaxResult list(BigDecimal version) {
        SbchEquipmentLocalPurchase list = sbchEquipmentPurchaseService.selectSbchEquipmentPurchaseList(version);
        return AjaxResult.success(list);
    }


    /**
     * 新增保存设备申购管理
     */
    @PreAuthorize(hasPermi ="equipmentlocalpurchase:purchase:add")
    //@CustomLogger(title = "设备属地化采购-保存",businessType = CustomBusinessType.SAVE)
    @PostMapping("/add")
    public AjaxResult addSave(@Validated(ValidationGroups.Save.class) @RequestBody SbchEquipmentLocalPurchase sbchEquipmentPurchase) {
        try {
            return new AjaxResult(200,"成功",sbchEquipmentPurchaseService.insertSbchEquipmentPurchaseAndDetails(sbchEquipmentPurchase));
        }catch (CustomBusinessException e){
            e.printStackTrace();
            return AjaxResult.error(e.getMsg());
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
    }
}
