package com.hhwy.pm.qqch.wzch.priorpurchase.controller;

import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.page.TableDataInfo;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.domain.base.system.material.MaterialInfo;
import com.hhwy.pm.qqch.wzch.common.service.WzchCommonService;
import com.hhwy.pm.qqch.wzch.priorpurchase.domain.WzchPriorPurchaseDetail;
import com.hhwy.pm.qqch.wzch.priorpurchase.dto.WzchPriorPurchaseDetailDTO;
import com.hhwy.pm.qqch.wzch.priorpurchase.service.IWzchPriorPurchaseDetailService;
import com.hhwy.utils.MaterialUtils;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.validation.ValidationGroups;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 优先进场物资设备采购策划物资详情Controller
 *
 * @author mls
 * @date 2022-11-17
 */
@RestController
@RequestMapping("wzch/priorPurchaseDetail")
public class WzchPriorPurchaseDetailController extends BaseController {

    @Autowired
    private IWzchPriorPurchaseDetailService wzchPriorPurchaseDetailService;
    @Autowired
    private WzchCommonService commonService;


    /**
     * 查询优先进场物资设备采购策划物资详情列表
     */
//    @PreAuthorize(hasPermi = "wzch:priorpurchase:list")
    @PostMapping("/list")
//    @CustomLogger(title = "查询优先进场物资设备采购策划列表", businessType = CustomBusinessType.SELECT)
    public TableDataInfo list(WzchPriorPurchaseDetail wzchPriorPurchaseDetail) {
        startPage();
        List<WzchPriorPurchaseDetailDTO> list = wzchPriorPurchaseDetailService.selectWzchPriorPurchaseDetailList(wzchPriorPurchaseDetail);
        return getDataTable(list);
    }

    /**
     * 导出优先进场物资设备采购策划物资详情列表
     */
    @PostMapping("/export")
//    @CustomLogger(title = "导出优先进场物资设备采购策划列表", businessType = CustomBusinessType.OTHER)
    public void export(@RequestBody Map<String, List<WzchPriorPurchaseDetailDTO>> params, HttpServletResponse response) {
        try {
            List<WzchPriorPurchaseDetailDTO> detailList = params.get("detailList");
            HashMap<String, String> map = new HashMap<>();
            map.put("materialStandard", "material_standard");
            map.put("categoryName", "total_demand_category_name");
            map.put("source", "purchase_source");
            map.put("currency", "remittance_currency_type");
            this.commonService.exportDealDict(detailList, map);
            ExcelUtils<WzchPriorPurchaseDetailDTO> util = new ExcelUtils<>(WzchPriorPurchaseDetailDTO.class);
            util.exportExcel(response, detailList, "priorpurchase");
        } catch (IOException e) {
            e.printStackTrace();
            throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error, "导出异常");
        }
    }


    @PostMapping("/downTemplate")
//    @CustomLogger(title = "获取导入模板", businessType = CustomBusinessType.OTHER)
    public void downTemplate(@RequestBody WzchPriorPurchaseDetailDTO dto, HttpServletResponse response) {
        try {
            List<WzchPriorPurchaseDetail> list = new ArrayList<>(1);
            ExcelUtils<WzchPriorPurchaseDetail> util = new ExcelUtils<>(WzchPriorPurchaseDetail.class);
            util.exportExcel(response, list, "priorpurchase");
        } catch (IOException e) {
            e.printStackTrace();
            throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error, "导出异常");
        }
    }


    @PostMapping("/importData")
//    @CustomLogger(title = "导入优先进场物资设备采购策划列表", businessType = CustomBusinessType.SELECT)
    public AjaxResult importData(@Validated(ValidationGroups.Select.class) MultipartFile file) {
        ExcelUtils<WzchPriorPurchaseDetailDTO> util = new ExcelUtils<>(WzchPriorPurchaseDetailDTO.class);
        try {
            List<WzchPriorPurchaseDetailDTO> importList = util.importExcel(file.getInputStream());
            //判断物资编码是否正确
            for (int i = 0; i < importList.size(); i++) {
                WzchPriorPurchaseDetailDTO temp = importList.get(i);
                MaterialInfo materialInfo = MaterialUtils.getMaterialInfoByCode(temp.getMaterialCode());
                Assert.isTrue(materialInfo!=null && StringUtils.isNotBlank(materialInfo.getMaterialCode()),"物资编码"+temp.getMaterialCode()+"不存在于物资编码库!");
            }
            HashMap<String, String> map = new HashMap<>();
            map.put("materialStandard", "material_standard");
            map.put("categoryName", "total_demand_category_name");
            map.put("source", "purchase_source");
            map.put("currency", "remittance_currency_type");
            commonService.importDealDict(importList, map);
            commonService.setWzchtMaterialInfo(importList);


            Map<String, String> dictMap = new HashMap<>(2);
            dictMap.put("materialStandard_materialStandardName","material_standard");
            dictMap.put("categoryName_categoryNameName","total_demand_category_name");
            commonService.setDicValue(importList,dictMap);
            // 为空 返回
            if (CollectionUtils.isEmpty(importList)) return AjaxResult.error("数据不能为空");
            return AjaxResult.success(importList);
        }catch(IllegalArgumentException e){
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("导入异常");
        }

    }


    /**
     * 新增保存优先进场物资设备采购策划物资详情
     */
//    @PreAuthorize(hasPermi = "wzch:priorpurchase:add")
    @PostMapping("/add")
    public AjaxResult addSave(WzchPriorPurchaseDetail wzchPriorPurchaseDetail) {
        return toAjax(wzchPriorPurchaseDetailService.insertWzchPriorPurchaseDetail(wzchPriorPurchaseDetail));
    }


    /**
     * 修改保存优先进场物资设备采购策划物资详情
     */
//    @PreAuthorize(hasPermi = "wzch:priorpurchase:edit")
    @PostMapping("/edit")
    public AjaxResult editSave(WzchPriorPurchaseDetail wzchPriorPurchaseDetail) {
        return toAjax(wzchPriorPurchaseDetailService.updateWzchPriorPurchaseDetail(wzchPriorPurchaseDetail));
    }

    /**
     * 删除优先进场物资设备采购策划物资详情
     */
//    @PreAuthorize(hasPermi = "wzch:priorpurchase:remove")
    @PostMapping("/remove")
    public AjaxResult remove(String ids) {
        return toAjax(wzchPriorPurchaseDetailService.deleteWzchPriorPurchaseDetailByIds(ids));
    }


    /**
     * 根据项目获取优先进场物资台账物资详情信息
     *
     * @param detail
     * @return
     */
    @PostMapping("/getMtlDetailList")
//    @CustomLogger(title = "根据项目获取优先进场物资台账物资详情", businessType = CustomBusinessType.SELECT)
    public AjaxResult getMtlDetailList(@Validated(ValidationGroups.Update.class) @RequestBody WzchPriorPurchaseDetail detail) {
        List<WzchPriorPurchaseDetailDTO> list = wzchPriorPurchaseDetailService.getMtlDetailList(detail);
        Map<String, String> map = new HashMap<>(2);
        map.put("materialStandard_materialStandardName", "material_standard");
        map.put("categoryName_categoryNameName", "total_demand_category_name");
        list = commonService.setDicValue(list, map);
        return AjaxResult.success("操作成功", list);
    }

}
