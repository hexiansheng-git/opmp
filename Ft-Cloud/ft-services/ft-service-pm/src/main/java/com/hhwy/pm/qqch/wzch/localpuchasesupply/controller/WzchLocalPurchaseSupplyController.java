package com.hhwy.pm.qqch.wzch.localpuchasesupply.controller;

import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.exception.BaseException;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.wzch.common.service.WzchCommonService;
import com.hhwy.pm.qqch.wzch.localpuchasesupply.domain.WzchLocalPurchaseSupply;
import com.hhwy.pm.qqch.wzch.localpuchasesupply.dto.WzchLocalPurchaseSupplyDTO;
import com.hhwy.pm.qqch.wzch.localpuchasesupply.dto.WzchLocalPurchaseSupplyDetailDTO;
import com.hhwy.pm.qqch.wzch.localpuchasesupply.dto.WzchLocalPurchaseViewDetailDTO;
import com.hhwy.pm.qqch.wzch.localpuchasesupply.service.IWzchLocalPurchaseSupplyDetailService;
import com.hhwy.pm.qqch.wzch.localpuchasesupply.service.IWzchLocalPurchaseSupplyService;
import com.hhwy.pm.qqch.wzch.puchasesupply.domain.WzchPurchaseSupply;
import com.hhwy.utils.excel.FtExcelUtil;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.validation.ValidationGroups;
import lombok.Data;
import lombok.ToString;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 属地化采购供应策划Controller
 *
 * @author mls
 * @date 2022-11-17
 */
@RestController
@RequestMapping("wzch/localPurchaseSupply")
public class WzchLocalPurchaseSupplyController extends BaseController {

    @Resource
    private IWzchLocalPurchaseSupplyService wzchPurchaseSupplyService;
    @Resource
    private WzchCommonService wzchCommonService;

    @Resource
    private IWzchLocalPurchaseSupplyDetailService detailService;



    private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


//    @PostMapping("listener")
////    @CustomLogger(title = "流程审批完成", businessType = CustomBusinessType.SELECT)
//    public AjaxResult listener(@RequestBody Map<String, Object> map) {
//        DelegateTask delegateTask = JSONObject.parseObject(JSONObject.toJSONString(map.get("execution")), DelegateTask.class);
//        Map varMap = delegateTask.getVariables();
//        String businessId = (String) varMap.get("businessId");
//        wzchPurchaseSupplyService.updateValidStatus(businessId);
//        return AjaxResult.success("成功");
//    }


    /**
     * 新增 编辑 详情数据回显
     */
    @GetMapping("baseInfo")
//    @CustomLogger(title = "新增 编辑 详情数据回显", businessType = CustomBusinessType.SELECT)
    public AjaxResult baseInfo(@RequestParam(required = false) WzchLocalPurchaseSupplyDTO dto) {
        return AjaxResult.success(wzchPurchaseSupplyService.baseInfo(dto==null?new WzchLocalPurchaseSupplyDTO():dto));
    }

    /**
     * 查询采购供应策划列表
     */
//    @PreAuthorize(hasPermi = "wzch:localpuchasesupply:list")
    @PostMapping("/list")
//    @CustomLogger(title = "查询采购供应策划列表", businessType = CustomBusinessType.SELECT)
    public AjaxResult list(@Validated(ValidationGroups.Select.class) @RequestBody WzchLocalPurchaseSupply wzchPurchaseSupply) {
        startPage();
        List<WzchLocalPurchaseSupply> list = wzchPurchaseSupplyService.selectWzchPurchaseSupplyList(wzchPurchaseSupply);
        return AjaxResult.success(getDataTable(list));
    }

    /**
     * 导出采购供应策划列表
     */
    @PostMapping("/export")
//    @CustomLogger(title = "导出采购供应策划列表", businessType = CustomBusinessType.EXPORT)
    public void export(@RequestBody Map<String, List<WzchLocalPurchaseSupply>> params, HttpServletResponse response) {
        try {
            List<WzchLocalPurchaseSupply> detailList = params.get("detailList");
            ExcelUtils<WzchLocalPurchaseSupply> util = new ExcelUtils<>(WzchLocalPurchaseSupply.class);
            util.exportExcel(response, detailList, "属地采购供应策划");
        } catch (IOException e) {
            e.printStackTrace();
            throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error, "导出异常");
        }
    }


    /**
     * 新增保存采购供应策划
     */
//    @PreAuthorize(hasPermi = "wzch:localpuchasesupply:add")
    @PostMapping("/add")
//    @CustomLogger(title = "新增保存采购供应策划", businessType = CustomBusinessType.SAVE)
    public AjaxResult addSave(@Validated(ValidationGroups.Save.class) @RequestBody WzchLocalPurchaseSupplyDTO dto) {
        return AjaxResult.success("操作成功", String.valueOf(wzchPurchaseSupplyService.insert(dto)));

    }


    /**
     * 修改保存采购供应策划
     */
//    @PreAuthorize(hasPermi = "wzch:localpuchasesupply:edit")
    @PostMapping("/edit")
//    @CustomLogger(title = "修改保存优先进场物资设备采购策划", businessType = CustomBusinessType.UPDATE)
    public AjaxResult editSave(@Validated(ValidationGroups.Update.class) @RequestBody WzchLocalPurchaseSupplyDTO dto) {
        return AjaxResult.success("操作成功", String.valueOf(wzchPurchaseSupplyService.edit(dto)));

    }

    /**
     * 保存采购供应策划
     */
//    @PreAuthorize(hasPermi = "wzch:localpuchasesupply:save")
    @PostMapping("/save")
//    @CustomLogger(title = "修改保存优先进场物资设备采购策划", businessType = CustomBusinessType.UPDATE)
    public AjaxResult save(@Validated(ValidationGroups.Update.class) @RequestBody WzchLocalPurchaseSupplyDTO dto) {
        return AjaxResult.success("操作成功", String.valueOf(wzchPurchaseSupplyService.save(dto)));

    }


    /**
     * 修改保存采购供应策划
     */
//    @PreAuthorize(hasPermi = "wzch:localpuchasesupply:adjust")
    @PostMapping("/adjust")
//    @CustomLogger(title = "调整保存优先进场物资设备采购策划", businessType = CustomBusinessType.UPDATE)
    public AjaxResult adjust(@Validated(ValidationGroups.Update.class) @RequestBody WzchLocalPurchaseSupplyDTO dto) {
        return AjaxResult.success("操作成功", String.valueOf(wzchPurchaseSupplyService.adjust(dto)));

    }

    /**
     * 删除采购供应策划
     */
//    @PreAuthorize(hasPermi = "wzch:localpuchasesupply:remove")
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
    public AjaxResult getListByPrjId(@RequestBody WzchLocalPurchaseSupplyDetailDTO dto) {
        try {
            List<WzchLocalPurchaseSupplyDetailDTO> list = detailService.getListByPrjId(dto);

            Map<String, String> map = new HashMap<>(2);
            map.put("materialStandard_materialStandardName", "material_standard");
            map.put("currency_currencyName", "currency");
            map.put("categoryName_categoryNameName", "total_demand_category_name");
            list = wzchCommonService.setDicValue(list, map);

            return AjaxResult.success(list);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("获取详情失败");
        }
    }


    /**
     * 导出采购供应策划详情列表
     */
    @PostMapping("detail/export")
//    @CustomLogger(title = "导出采购供应策划详情列表", businessType = CustomBusinessType.EXPORT)
    public void exportDetail(@Validated(ValidationGroups.Other.class) @RequestBody Map<String, List<WzchLocalPurchaseSupplyDetailDTO>> params, HttpServletResponse response) {
        try {
            List<WzchLocalPurchaseSupplyDetailDTO> detailList = params.get("detailList");
            List<WzchLocalPurchaseSupplyDetailDTO> children = new ArrayList<>();
            for (WzchLocalPurchaseSupplyDetailDTO wzchPurchaseSupplyDetailDTO : detailList) {
                List<WzchLocalPurchaseSupplyDetailDTO> children1 = wzchPurchaseSupplyDetailDTO.getChildren();
                if(CollectionUtils.isNotEmpty(children1)) children.addAll(children1);
            }
            Map<String, String> map = new HashMap<>();
            map.put("materialStandard", "material_standard");
            map.put("categoryName", "total_demand_category_name");
            map.put("currency", "remittance_currency_type");
            map.put("source", "wzch_purchase_source");
            wzchCommonService.exportDealDict(detailList, map);
            wzchCommonService.exportDealDict(children, map);
            FtExcelUtil<WzchLocalPurchaseSupplyDetailDTO> util = new FtExcelUtil<>(WzchLocalPurchaseSupplyDetailDTO.class);
            util.exportExcel(response, detailService.dealList(detailList), "采购供应策划物资详情");
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
            FtExcelUtil<WzchLocalPurchaseSupplyDetailDTO> util = new FtExcelUtil<>(WzchLocalPurchaseSupplyDetailDTO.class);
            List<WzchLocalPurchaseSupplyDetailDTO> dtoList = util.importExcel(file.getInputStream());

            wzchCommonService.setCurrency(dtoList,"currency","currencyName");
            Map<String, String> dm = new HashMap<>(3);
            dm.put("materialStandard_materialStandardName", "material_standard");
            dm.put("categoryName_categoryNameName", "total_demand_category_name");
            dm.put("source_sourceName", "wzch_purchase_source");
            dtoList = wzchCommonService.setDicValue(dtoList, dm);
            for (int i = 0; i < dtoList.size(); i++) {
                dtoList.get(i).setPurchaseSupplyDetailId((long) i);
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
        List<WzchLocalPurchaseViewDetailDTO> list = detailService.purchaseView(map);
        return AjaxResult.success("成功", list);
    }


    @PostMapping("detail/savePurchaseView")
//    @CustomLogger(title = "保存采购视角", businessType = CustomBusinessType.SAVE)
    public AjaxResult savePurchaseView(@RequestBody SavePurchaseViewDTO dto) {
        List<WzchLocalPurchaseViewDetailDTO> viewList = dto.getViewList();
        String projectId = dto.getProjectId();
        detailService.savePurchaseView(viewList, projectId);
        return AjaxResult.success("操作成功");
    }

    @Data
    @ToString
    public static class SavePurchaseViewDTO implements Serializable {
        private String projectId;
        private List<WzchLocalPurchaseViewDetailDTO> viewList;

    }

    @PostMapping("/sync")
    public AjaxResult sync(@RequestBody  WzchLocalPurchaseSupply purchaseSupply) {
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
