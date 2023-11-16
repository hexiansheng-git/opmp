package com.hhwy.pm.qqch.wzch.priorpurchase.controller;

import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.exception.BaseException;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.wzch.common.service.WzchCommonService;
import com.hhwy.pm.qqch.wzch.priorpurchase.domain.WzchPriorPurchase;
import com.hhwy.pm.qqch.wzch.priorpurchase.dto.WzchPriorPurchaseDTO;
import com.hhwy.pm.qqch.wzch.priorpurchase.service.IWzchPriorPurchaseService;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.validation.ValidationGroups;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 优先进场物资设备采购策划Controller
 *
 * @author mls
 * @date 2022-11-17
 */
@RestController
@RequestMapping("wzch/priorpurchase")
public class WzchPriorPurchaseController extends BaseController {
    @Resource
    private WzchCommonService wzchCommonService;
    @Resource
    private IWzchPriorPurchaseService wzchPriorPurchaseService;

    /**
     * 新增 编辑 详情数据回显
     */
    @GetMapping("baseInfo")
//    @CustomLogger(title = "新增 编辑 详情数据回显", businessType = CustomBusinessType.SELECT)
    public AjaxResult baseInfo(@RequestParam(required = false) WzchPriorPurchaseDTO vo) {
        return AjaxResult.success(wzchPriorPurchaseService.baseInfo(vo==null?new WzchPriorPurchaseDTO():vo));
    }


    /**
     * 查询优先进场物资设备采购策划列表
     */
//    @PreAuthorize(hasPermi = "wzch:priorpurchase:list")
    @PostMapping("/list")
//    @CustomLogger(title = "查询优先进场物资设备采购策划列表", businessType = CustomBusinessType.SELECT)
    public AjaxResult list(@Validated(ValidationGroups.Select.class) @RequestBody WzchPriorPurchase wzchPriorPurchase) {
        startPage();
        List<WzchPriorPurchase> list = wzchPriorPurchaseService.selectWzchPriorPurchaseList(wzchPriorPurchase);
        return AjaxResult.success(getDataTable(list));
    }

    /**
     * 导出优先进场物资设备采购策划列表
     */
    @PostMapping("/export")
//    @CustomLogger(title = "导出优先进场物资设备采购策划列表", businessType = CustomBusinessType.EXPORT)
    public void export(@RequestBody WzchPriorPurchase wzchPriorPurchase, HttpServletResponse response) {
        try {
            List<WzchPriorPurchase> list = wzchPriorPurchaseService.selectWzchPriorPurchaseList(wzchPriorPurchase);
            HashMap<String, String> map = new HashMap<>();
            map.put("valid", "warn_flag");
            this.wzchCommonService.exportDealDict(list, map);
            ExcelUtils<WzchPriorPurchase> util = new ExcelUtils<>(WzchPriorPurchase.class);
            util.exportExcel(response, list, "优先进场物资设备采购策划");
        } catch (IOException e) {
            e.printStackTrace();
            throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error, "导出异常");
        }
    }

    /**
     * 新增保存优先进场物资设备采购策划
     */
//    @PreAuthorize(hasPermi = "wzch:priorpurchase:add")
    @PostMapping("/add")
//    @CustomLogger(title = "新增保存优先进场物资设备采购策划", businessType = CustomBusinessType.SAVE)
    public AjaxResult add(@Validated(ValidationGroups.Save.class) @RequestBody WzchPriorPurchaseDTO wzchPriorPurchaseDTO) {
        return AjaxResult.success("操作成功", String.valueOf(wzchPriorPurchaseService.insert(wzchPriorPurchaseDTO)));
    }


    /**
     * 修改保存优先进场物资设备采购策划
     */
//    @PreAuthorize(hasPermi = "wzch:priorpurchase:edit")
    @PostMapping("/edit")
//    @CustomLogger(title = "修改保存优先进场物资设备采购策划", businessType = CustomBusinessType.UPDATE)
    public AjaxResult editSave(@Validated(ValidationGroups.Update.class) @RequestBody WzchPriorPurchaseDTO wzchPriorPurchaseDTO) {
        return AjaxResult.success("操作成功", String.valueOf(wzchPriorPurchaseService.edit(wzchPriorPurchaseDTO)));
    }


//    @PreAuthorize(hasPermi = "wzch:priorpurchase:adjust")
    @PostMapping("/adjust")
//    @CustomLogger(title = "调整优先进场物资设备采购策划", businessType = CustomBusinessType.UPDATE)
    public AjaxResult adjust(@Validated(ValidationGroups.Update.class) @RequestBody WzchPriorPurchaseDTO wzchPriorPurchaseDTO) {
        return AjaxResult.success("操作成功", String.valueOf(wzchPriorPurchaseService.adjust(wzchPriorPurchaseDTO)));
    }

//    @PreAuthorize(hasPermi = "wzch:priorpurchase:save")
    @PostMapping("/save")
//    @CustomLogger(title = "调整优先进场物资设备采购策划", businessType = CustomBusinessType.UPDATE)
    public AjaxResult save(@Validated(ValidationGroups.Update.class) @RequestBody WzchPriorPurchaseDTO wzchPriorPurchaseDTO) {
        return AjaxResult.success("操作成功", String.valueOf(wzchPriorPurchaseService.save(wzchPriorPurchaseDTO)));
    }

    /**
     * 删除优先进场物资设备采购策划
     */
//    @PreAuthorize(hasPermi = "wzch:priorpurchase:remove")
    @PostMapping("/remove")
//    @CustomLogger(title = "删除优先进场物资设备采购策划", businessType = CustomBusinessType.DELETE)
    public AjaxResult remove(@Validated(ValidationGroups.Delete.class) @RequestBody Map map) {
        String ids = String.valueOf(map.get("ids"));
        return toAjax(wzchPriorPurchaseService.deleteWzchPriorPurchaseByIds(ids));
    }

    /**
     * 从优先进场物资同步数据
     * @param dto
     * @return
     */
    @PostMapping("/sync")
    public AjaxResult sync(@RequestBody WzchPriorPurchaseDTO dto) {
        try{
            Assert.notNull(dto.getVersion(), "version不能为空");
            wzchPriorPurchaseService.sync(dto);
            return AjaxResult.success();
        }catch (BaseException b){
            b.printStackTrace();
            throw new BaseException(b.getDefaultMessage());
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException("同步异常:"+e.getMessage());
        }
    }

}
