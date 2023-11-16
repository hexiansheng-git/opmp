package com.hhwy.pm.qqch.wzch.puchasesupply.controller;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.exception.BaseException;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.wzch.common.service.WzchCommonService;
import com.hhwy.pm.qqch.wzch.puchasesupply.domain.WzchPurchaseSupply;
import com.hhwy.pm.qqch.wzch.puchasesupply.dto.WzchPurchaseSupplyDTO;
import com.hhwy.pm.qqch.wzch.puchasesupply.dto.WzchPurchaseSupplyDetailDTO;
import com.hhwy.pm.qqch.wzch.puchasesupply.dto.WzchPurchaseViewDetailDTO;
import com.hhwy.pm.qqch.wzch.puchasesupply.service.IWzchPurchaseSupplyDetailService;
import com.hhwy.pm.qqch.wzch.puchasesupply.service.IWzchPurchaseSupplyService;
import com.hhwy.utils.excel.FtExcelUtil;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.validation.ValidationGroups;
import lombok.Data;
import lombok.ToString;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * 采购供应策划Controller
 *
 * @author mls
 * @date 2022-11-17
 */
@RestController
@RequestMapping("wzch/purchaseSupply")
public class WzchPurchaseSupplyController extends BaseController {
    @Resource
    private IWzchPurchaseSupplyService wzchPurchaseSupplyService;
    @Resource
    private IWzchPurchaseSupplyDetailService detailService;
    @Resource
    private WzchCommonService wzchCommonService;

    private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 新增 编辑 详情数据回显
     */
    @GetMapping("baseInfo")
//    @CustomLogger(title = "新增 编辑 详情数据回显", businessType = CustomBusinessType.SELECT)
    public AjaxResult baseInfo(@RequestParam(required = false) WzchPurchaseSupplyDTO purchaseSupply) {
        
        return AjaxResult.success(wzchPurchaseSupplyService.baseInfo(purchaseSupply==null?new WzchPurchaseSupplyDTO():purchaseSupply));
    }



    /**
     * 新增保存采购供应策划
     */
//    @PreAuthorize(hasPermi = "wzch:puchasesupply:add")
    @PostMapping("/add")
//    @CustomLogger(title = "新增保存采购供应策划", businessType = CustomBusinessType.SAVE)
    public AjaxResult addSave(@Validated(ValidationGroups.Save.class) @RequestBody WzchPurchaseSupplyDTO dto) {
        return AjaxResult.success("操作成功", String.valueOf(wzchPurchaseSupplyService.insert(dto)));

    }


    /**
     * 修改保存采购供应策划
     */
//    @PreAuthorize(hasPermi = "wzch:puchasesupply:edit")
    @PostMapping("/edit")
//    @CustomLogger(title = "修改保存优先进场物资设备采购策划", businessType = CustomBusinessType.UPDATE)
    public AjaxResult editSave(@Validated(ValidationGroups.Update.class) @RequestBody WzchPurchaseSupplyDTO dto) {
        return AjaxResult.success("操作成功", String.valueOf(wzchPurchaseSupplyService.edit(dto)));

    }

    /**
     * 修改保存采购供应策划
     */
//    @PreAuthorize(hasPermi = "wzch:puchasesupply:save")
    @PostMapping("/save")
//    @CustomLogger(title = "修改保存优先进场物资设备采购策划", businessType = CustomBusinessType.UPDATE)
    public AjaxResult save(@Validated(ValidationGroups.Update.class) @RequestBody WzchPurchaseSupplyDTO dto) {
        return AjaxResult.success("操作成功", String.valueOf(wzchPurchaseSupplyService.save(dto)));

    }


    /**
     * 修改保存采购供应策划
     */
//    @PreAuthorize(hasPermi = "wzch:puchasesupply:adjust")
    @PostMapping("/adjust")
//    @CustomLogger(title = "调整保存优先进场物资设备采购策划", businessType = CustomBusinessType.UPDATE)
    public AjaxResult adjust(@Validated(ValidationGroups.Update.class) @RequestBody WzchPurchaseSupplyDTO dto) {
        return AjaxResult.success("操作成功", String.valueOf(wzchPurchaseSupplyService.adjust(dto)));

    }

    /**
     * 删除采购供应策划
     */
//    @PreAuthorize(hasPermi = "wzch:puchasesupply:remove")
    @PostMapping("/remove")
    public AjaxResult remove(@RequestBody Map<String, String> params) {
        String ids = params.get("ids");
        return toAjax(wzchPurchaseSupplyService.deleteWzchPurchaseSupplyByIds(ids));
    }


    /**
     * 根据项目id获取物资详情 获取的是来源策划的数据  如果物资在有采购相关的数据 就拿取物资 如果没有 就不用拿取
     */
    @PostMapping("detail/getMtlDetailList")
//    @CustomLogger(title = "根据项目id获取物资详情", businessType = CustomBusinessType.SELECT)
    public AjaxResult getListByPrjId(@RequestBody WzchPurchaseSupplyDetailDTO dto) {
        try {
            List<WzchPurchaseSupplyDetailDTO> list = detailService.getListByPrjId(dto);

            Map<String, String> map = new HashMap<>(2);
            map.put("materialStandard_materialStandardName", "material_standard");
            map.put("currency_currencyName", "currency");
            map.put("categoryName_categoryNameName", "total_demand_category_name");
            list = wzchCommonService.setDicValue(list, map);
            return AjaxResult.success(list);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error, "导出异常");

        }
    }


    /**
     * 导出采购供应策划详情列表
     */
    @PostMapping("detail/export")
//    @CustomLogger(title = "导出采购供应策划详情列表", businessType = CustomBusinessType.EXPORT)
    public void exportDetail(@Validated(ValidationGroups.Other.class) @RequestBody Map<String, List<WzchPurchaseSupplyDetailDTO>> params, HttpServletResponse response) {
        try {
            List<WzchPurchaseSupplyDetailDTO> detailList = params.get("detailList");
            List<WzchPurchaseSupplyDetailDTO> children = new ArrayList<>();
            for (WzchPurchaseSupplyDetailDTO wzchPurchaseSupplyDetailDTO : detailList) {
                List<WzchPurchaseSupplyDetailDTO> children1 = wzchPurchaseSupplyDetailDTO.getChildren();
                if(CollectionUtils.isNotEmpty(children1)) children.addAll(children1);
            }
            Map<String, String> map = new HashMap<>();
            map.put("materialStandard", "material_standard");
            map.put("categoryName", "total_demand_category_name");
            map.put("currency", "remittance_currency_type");
            map.put("source", "wzch_purchase_source");
            wzchCommonService.exportDealDict(detailList, map);
            wzchCommonService.exportDealDict(children, map);
            FtExcelUtil<WzchPurchaseSupplyDetailDTO> util = new FtExcelUtil<>(WzchPurchaseSupplyDetailDTO.class);
            List<WzchPurchaseSupplyDetailDTO> importDatas = detailService.dealList(detailList);
            util.exportExcel(response, importDatas, "采购供应策划物资详情");
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error, "导出异常");
        }
    }


    /**
     * 导入采购供应策划物资详情
     */
    @PostMapping("detail/importData")
//    @CustomLogger(title = "导入采购供应策划物资详情", businessType = CustomBusinessType.IMPORT)
    public AjaxResult importData(MultipartFile file, @RequestParam Map map) {
        try {
            FtExcelUtil<WzchPurchaseSupplyDetailDTO> util = new FtExcelUtil<>(WzchPurchaseSupplyDetailDTO.class);
            List<WzchPurchaseSupplyDetailDTO> dtoList = util.importExcel(file.getInputStream());

            wzchCommonService.setCurrency(dtoList,"currency","currencyName");
            Map<String, String> dm = new HashMap<>(3);
            dm.put("materialStandard_materialStandardName", "material_standard");
            dm.put("categoryName_categoryNameName", "total_demand_category_name");
            dm.put("source_sourceName", "wzch_purchase_source");
            dtoList = wzchCommonService.setDicValue(dtoList, dm);
            for (int i = dtoList.size()-1; i >= 0; i--) {
                dtoList.get(i).setPurchaseSupplyDetailId((long) i);
                dtoList.get(i).setId(IdWorker.createId());
            }
            // 将数据进行分级
            dtoList = detailService.getLevelList(dtoList);
            return AjaxResult.success(dtoList);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error, e.getMessage());

        }
    }

    @PostMapping("detail/purchaseView")
//    @CustomLogger(title = "查询采购视角", businessType = CustomBusinessType.SELECT)
    public AjaxResult purchaseView(@RequestBody Map<String, String> map) {
        List<WzchPurchaseViewDetailDTO> list = detailService.purchaseView(map);
        return AjaxResult.success("成功", list);
    }


    @PostMapping("detail/savePurchaseView")
//    @CustomLogger(title = "保存采购视角", businessType = CustomBusinessType.SAVE)
    public AjaxResult savePurchaseView(@RequestBody SavePurchaseViewDTO dto) {
        List<WzchPurchaseViewDetailDTO> viewList = dto.getViewList();
        String projectId = dto.projectId;
//        if (CollectionUtils.isEmpty(viewList))
//            throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error, "数据不能为空");
        detailService.savePurchaseView(viewList, projectId);
        return AjaxResult.success("操作成功");
    }


    @Data
    @ToString
    public static class SavePurchaseViewDTO implements Serializable {
        private String projectId;
        private List<WzchPurchaseViewDetailDTO> viewList;

    }

    @PostMapping("/sync")
    public AjaxResult sync(@RequestBody WzchPurchaseSupply purchaseSupply) {
        try{
            Assert.notNull(purchaseSupply.getVersion(), "version不能为空");
            wzchPurchaseSupplyService.sync(purchaseSupply);
            return AjaxResult.success();
        }catch (BaseException b){
            b.printStackTrace();
            throw new BaseException(b.getDefaultMessage());
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException("同步数据异常:"+e.getMessage());
        }
    }

}
