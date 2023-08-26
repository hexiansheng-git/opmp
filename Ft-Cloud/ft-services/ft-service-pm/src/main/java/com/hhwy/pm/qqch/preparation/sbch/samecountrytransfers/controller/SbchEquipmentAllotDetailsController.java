package com.hhwy.pm.qqch.preparation.sbch.samecountrytransfers.controller;//package com.hhwy.sbch.samecountrytransfers.controller;
//
//import java.util.List;
//import org.apache.shiro.authz.annotation.RequiresPermissions;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import com.hhwy.common.annotation.Log;
//import com.hhwy.common.enums.BusinessType;
//import com.hhwy.gen.domain.SbchEquipmentAllotDetails;
//import com.hhwy.gen.service.ISbchEquipmentAllotDetailsService;
//import com.hhwy.common.core.controller.BaseController;
//import com.hhwy.common.core.domain.AjaxResult;
//import com.hhwy.console.util.ExcelUtil;
//import com.hhwy.common.core.page.TableDataInfo;
//
///**
// * 同国别设备详情Controller
// *
// * @author hwj
// * @date 2022-11-25
// */
//@Controller
//@RequestMapping("/gen/details")
//public class SbchEquipmentAllotDetailsController extends BaseController {
//    private String prefix = "gen/details";
//
//    @Autowired
//    private ISbchEquipmentAllotDetailsService sbchEquipmentAllotDetailsService;
//
//    @RequiresPermissions("gen:details:view")
//    @GetMapping()
//    public String details() {
//        return prefix + "/details";
//    }
//
//    /**
//     * 查询同国别设备详情列表
//     */
//    @RequiresPermissions("gen:details:list")
//    @PostMapping("/list")
//    @ResponseBody
//    public TableDataInfo list(SbchEquipmentAllotDetails sbchEquipmentAllotDetails) {
//        startPage();
//        List<SbchEquipmentAllotDetails> list = sbchEquipmentAllotDetailsService.selectSbchEquipmentAllotDetailsList(sbchEquipmentAllotDetails);
//        return getDataTable(list);
//    }
//
//    /**
//     * 导出同国别设备详情列表
//     */
//    @RequiresPermissions("gen:details:export")
//    @Log(title = "同国别设备详情", businessType = BusinessType.EXPORT)
//    @PostMapping("/export")
//    @ResponseBody
//    public AjaxResult export(SbchEquipmentAllotDetails sbchEquipmentAllotDetails) {
//        List<SbchEquipmentAllotDetails> list = sbchEquipmentAllotDetailsService.selectSbchEquipmentAllotDetailsList(sbchEquipmentAllotDetails);
//        ExcelUtil<SbchEquipmentAllotDetails> util = new ExcelUtil<SbchEquipmentAllotDetails>(SbchEquipmentAllotDetails.class);
//        return util.exportExcel(list, "details");
//    }
//
//    /**
//     * 新增同国别设备详情
//     */
//    @GetMapping("/add")
//    public String add() {
//        return prefix + "/add";
//    }
//
//    /**
//     * 新增保存同国别设备详情
//     */
//    @RequiresPermissions("gen:details:add")
//    @Log(title = "同国别设备详情", businessType = BusinessType.INSERT)
//    @PostMapping("/add")
//    @ResponseBody
//    public AjaxResult addSave(SbchEquipmentAllotDetails sbchEquipmentAllotDetails) {
//        return toAjax(sbchEquipmentAllotDetailsService.insertSbchEquipmentAllotDetails(sbchEquipmentAllotDetails));
//    }
//
//    /**
//     * 修改同国别设备详情
//     */
//    @GetMapping("/edit/{id}")
//    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
//        SbchEquipmentAllotDetails sbchEquipmentAllotDetails = sbchEquipmentAllotDetailsService.selectSbchEquipmentAllotDetailsById(id);
//        mmap.put("sbchEquipmentAllotDetails", sbchEquipmentAllotDetails);
//        return prefix + "/edit";
//    }
//
//    /**
//     * 修改保存同国别设备详情
//     */
//    @RequiresPermissions("gen:details:edit")
//    @Log(title = "同国别设备详情", businessType = BusinessType.UPDATE)
//    @PostMapping("/edit")
//    @ResponseBody
//    public AjaxResult editSave(SbchEquipmentAllotDetails sbchEquipmentAllotDetails) {
//        return toAjax(sbchEquipmentAllotDetailsService.updateSbchEquipmentAllotDetails(sbchEquipmentAllotDetails));
//    }
//
//    /**
//     * 删除同国别设备详情
//     */
//    @RequiresPermissions("gen:details:remove")
//    @Log(title = "同国别设备详情", businessType = BusinessType.DELETE)
//    @PostMapping( "/remove")
//    @ResponseBody
//    public AjaxResult remove(String ids) {
//        return toAjax(sbchEquipmentAllotDetailsService.deleteSbchEquipmentAllotDetailsByIds(ids));
//    }
//}
