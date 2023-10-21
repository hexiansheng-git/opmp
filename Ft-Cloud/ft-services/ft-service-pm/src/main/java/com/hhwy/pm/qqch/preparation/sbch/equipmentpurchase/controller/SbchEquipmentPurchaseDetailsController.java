//package com.hhwy.pm.qqch.preparation.sbch.equipmentpurchase.controller;
//
//import java.util.List;
//
//import cn.hutool.poi.excel.ExcelUtil;
//import com.hhwy.common.core.web.controller.BaseController;
//import com.hhwy.common.core.web.domain.AjaxResult;
//import com.hhwy.common.core.web.page.TableDataInfo;
//import com.hhwy.pm.qqch.preparation.sbch.equipmentpurchase.domain.SbchEquipmentPurchaseDetails;
//import com.hhwy.pm.qqch.preparation.sbch.equipmentpurchase.service.ISbchEquipmentPurchaseDetailsService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
///**
// * 设备申购管理详情Controller
// *
// * @author hwj
// * @date 2022-11-22
// */
//@Controller
//@RequestMapping("/gen/details")
//public class SbchEquipmentPurchaseDetailsController extends BaseController {
//    @Autowired
//    private ISbchEquipmentPurchaseDetailsService sbchEquipmentPurchaseDetailsService;
//
//    @RequiresPermissions("gen:details:view")
//    @GetMapping()
//    public String details() {
//        return prefix + "/details";
//    }
//
//    /**
//     * 查询设备申购管理详情列表
//     */
//    @RequiresPermissions("gen:details:list")
//    @PostMapping("/list")
//    @ResponseBody
//    public TableDataInfo list(SbchEquipmentPurchaseDetails sbchEquipmentPurchaseDetails) {
//        startPage();
//        List<SbchEquipmentPurchaseDetails> list = sbchEquipmentPurchaseDetailsService.selectSbchEquipmentPurchaseDetailsList(sbchEquipmentPurchaseDetails);
//        return getDataTable(list);
//    }
//
//    /**
//     * 导出设备申购管理详情列表
//     */
//    @RequiresPermissions("gen:details:export")
//    @Log(title = "设备申购管理详情", businessType = BusinessType.EXPORT)
//    @PostMapping("/export")
//    @ResponseBody
//    public AjaxResult export(SbchEquipmentPurchaseDetails sbchEquipmentPurchaseDetails) {
//        List<SbchEquipmentPurchaseDetails> list = sbchEquipmentPurchaseDetailsService.selectSbchEquipmentPurchaseDetailsList(sbchEquipmentPurchaseDetails);
//        ExcelUtil util = new ExcelUtil<SbchEquipmentPurchaseDetails>(SbchEquipmentPurchaseDetails.class);
//        return util.exportExcel(list, "details");
//    }
//
//    /**
//     * 新增设备申购管理详情
//     */
//    @GetMapping("/add")
//    public String add() {
//        return prefix + "/add";
//    }
//
//    /**
//     * 新增保存设备申购管理详情
//     */
//    @RequiresPermissions("gen:details:add")
//    @Log(title = "设备申购管理详情", businessType = BusinessType.INSERT)
//    @PostMapping("/add")
//    @ResponseBody
//    public AjaxResult addSave(SbchEquipmentPurchaseDetails sbchEquipmentPurchaseDetails) {
//        return toAjax(sbchEquipmentPurchaseDetailsService.insertSbchEquipmentPurchaseDetails(sbchEquipmentPurchaseDetails));
//    }
//
//    /**
//     * 修改设备申购管理详情
//     */
//    @GetMapping("/edit/{id}")
//    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
//        SbchEquipmentPurchaseDetails sbchEquipmentPurchaseDetails = sbchEquipmentPurchaseDetailsService.selectSbchEquipmentPurchaseDetailsById(id);
//        mmap.put("sbchEquipmentPurchaseDetails", sbchEquipmentPurchaseDetails);
//        return prefix + "/edit";
//    }
//
//    /**
//     * 修改保存设备申购管理详情
//     */
//    @RequiresPermissions("gen:details:edit")
//    @Log(title = "设备申购管理详情", businessType = BusinessType.UPDATE)
//    @PostMapping("/edit")
//    @ResponseBody
//    public AjaxResult editSave(SbchEquipmentPurchaseDetails sbchEquipmentPurchaseDetails) {
//        return toAjax(sbchEquipmentPurchaseDetailsService.updateSbchEquipmentPurchaseDetails(sbchEquipmentPurchaseDetails));
//    }
//
//    /**
//     * 删除设备申购管理详情
//     */
//    @RequiresPermissions("gen:details:remove")
//    @Log(title = "设备申购管理详情", businessType = BusinessType.DELETE)
//    @PostMapping("/remove")
//    @ResponseBody
//    public AjaxResult remove(String ids) {
//        return toAjax(sbchEquipmentPurchaseDetailsService.deleteSbchEquipmentPurchaseDetailsByIds(ids));
//    }
//}
