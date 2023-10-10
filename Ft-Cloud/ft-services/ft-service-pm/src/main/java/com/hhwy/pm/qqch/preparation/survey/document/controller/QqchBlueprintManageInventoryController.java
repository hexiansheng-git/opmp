package com.hhwy.pm.qqch.preparation.survey.document.controller;

import com.hhwy.pm.qqch.preparation.survey.document.domain.vo.QqchBlueprintManageInventoryVo;
import com.hhwy.pm.qqch.preparation.survey.document.service.IQqchBlueprintManageInventoryService;
import org.springframework.web.bind.annotation.*;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
//import com.hhwy.common.security.annotation.PreAuthorize;

import java.math.BigDecimal;

/**
 * @author han
 * @date 2023-07-13 11:40:34
 * @remark 勘察设计图纸管理清单
 */
@Validated
@RestController
@RequestMapping("/qqchBlueprintManageInventory")
public class QqchBlueprintManageInventoryController extends BaseController {

    @Autowired
    private IQqchBlueprintManageInventoryService qqchBlueprintManageInventoryService;

    /**
     * 勘察设计图纸管理清单台账
     * @return
     */
    @GetMapping("/list")
    public AjaxResult getQqchBlueprintManageInventoryList(BigDecimal version) {
        QqchBlueprintManageInventoryVo qqchBlueprintManageInventoryVo = qqchBlueprintManageInventoryService.getQqchBlueprintManageInventoryVo(version);
        return AjaxResult.success(qqchBlueprintManageInventoryVo);
    }

    /**
     * 保存
     * @param qqchBlueprintManageInventoryVo
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchBlueprintManageInventory:save")
    @PostMapping("/save")
    public AjaxResult save(@Validated(ValidationGroups.Save.class) @RequestBody QqchBlueprintManageInventoryVo qqchBlueprintManageInventoryVo) {
        qqchBlueprintManageInventoryService.save(qqchBlueprintManageInventoryVo);
        return AjaxResult.success();
    }

    /**
     * 确认
     * @param qqchBlueprintManageInventoryVo
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchBlueprintManageInventory:save")
    @PostMapping("/confirm")
    public AjaxResult confirm(@Validated(ValidationGroups.Save.class) @RequestBody QqchBlueprintManageInventoryVo qqchBlueprintManageInventoryVo) {
        qqchBlueprintManageInventoryService.confirm(qqchBlueprintManageInventoryVo);
        return AjaxResult.success();
    }
}
