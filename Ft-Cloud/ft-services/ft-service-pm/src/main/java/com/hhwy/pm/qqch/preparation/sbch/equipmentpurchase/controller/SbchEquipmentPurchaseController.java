package com.hhwy.pm.qqch.preparation.sbch.equipmentpurchase.controller;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.page.TableDataInfo;
import com.hhwy.common.security.annotation.PreAuthorize;

import com.hhwy.pm.qqch.preparation.sbch.equipmentpurchase.domain.SbchEquipmentPurchase;
import com.hhwy.pm.qqch.preparation.sbch.equipmentpurchase.domain.SbchEquipmentPurchaseDetails;
import com.hhwy.pm.qqch.preparation.sbch.equipmentpurchase.dto.SbchEquipmentPurchaseDTO;
import com.hhwy.pm.qqch.preparation.sbch.equipmentpurchase.service.ISbchEquipmentPurchaseDetailsService;
import com.hhwy.pm.qqch.preparation.sbch.equipmentpurchase.service.ISbchEquipmentPurchaseService;
import com.hhwy.pm.qqch.preparation.sbch.plan.domain.SbchTotalDemandPlanDetail;
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
 * @author hwj   https://39i26157b3.yicp.fun/pms/equipmentpurchase/purchase/cshInfo
 * @date 2022-11-22
 */
//@Validated
@RestController
@RequestMapping("/equipmentpurchase/purchase")
public class SbchEquipmentPurchaseController extends BaseController {
    @Autowired
    private ISbchEquipmentPurchaseService sbchEquipmentPurchaseService;

    /**
     * 查询设备申购管理列表
     */
    @PreAuthorize(hasPermi ="equipmentpurchase:purchase:list")
    @GetMapping("/getList")
    //@CustomLogger(title = "设备申购-列表查询",businessType = CustomBusinessType.SELECT)
    public AjaxResult list(BigDecimal version) {
        SbchEquipmentPurchase temp = sbchEquipmentPurchaseService.selectSbchEquipmentPurchaseList(version);
        return AjaxResult.success(temp);
    }

    /**
     * 新增保存设备申购管理
     */
    @PreAuthorize(hasPermi ="equipmentpurchase:purchase:add")
    //@CustomLogger(title = "设备申购-保存",businessType = CustomBusinessType.SAVE)
    @PostMapping("/add")
    public AjaxResult addSave(@Validated(ValidationGroups.Save.class) @RequestBody SbchEquipmentPurchase sbchEquipmentPurchase) {
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
