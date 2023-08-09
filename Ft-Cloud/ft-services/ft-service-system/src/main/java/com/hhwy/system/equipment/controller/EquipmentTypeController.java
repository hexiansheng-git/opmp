package com.hhwy.system.equipment.controller;

import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;

import com.hhwy.domain.base.system.SysTreeUtil;
import com.hhwy.domain.base.system.equipment.EquipmentType;
import com.hhwy.system.equipment.service.IEquipmentTypeService;
import com.hhwy.utils.tree.TreeUtil;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * 基础模块---设备分类Controller
 * 
 * @author lcf
 * @date 2023-03-03
 */
@RestController
@RequestMapping({"/equipment/type","/self/equipment/type"})
public class EquipmentTypeController extends BaseController {

    @Autowired
    private IEquipmentTypeService equipmentTypeService;
    

    /**
     * 查询基础模块---设备分类列表
     */
    @PreAuthorize(hasPermi ="equipment:type:list")
    @PostMapping("/list")
    @ResponseBody
//    @CustomLogger(title = "基础模块设备分类表-查询", businessType = CustomBusinessType.SELECT)
    public AjaxResult list(EquipmentType equipmentType) {
        List<EquipmentType> list = equipmentTypeService.selectEquipmentTypeList(equipmentType);
        return AjaxResult.success(getDataTable(list));
    }

    @PreAuthorize(hasPermi ="equipment:type:treeList")
    @PostMapping("/treeList")
    @ResponseBody
//    @CustomLogger(title = "基础模块设备分类表-树形结构查询", businessType = CustomBusinessType.SELECT)
    public AjaxResult treeList(@RequestBody EquipmentType equipmentType){
        List<SysTreeUtil> list=equipmentTypeService.treeList(equipmentType);
        return AjaxResult.success(list);
    }


    /**
     * 导出基础模块---设备分类列表
     */
    @PreAuthorize(hasPermi ="equipment:type:export")
//    @CustomLogger(title = "基础模块设备分类表-导出", businessType = CustomBusinessType.EXPORT)
    @PostMapping("/export")
    public void export(@RequestBody EquipmentType equipmentType, HttpServletResponse response) throws Exception{
        List<EquipmentType> list = equipmentTypeService.selectEquipmentTypeList(equipmentType);
        ExcelUtils<EquipmentType> util = new ExcelUtils<EquipmentType>(EquipmentType.class);
        util.exportExcel(response,list, "info");
    }
    
    /**
     * 新增保存基础模块---设备分类
     */
    @PreAuthorize(hasPermi ="equipment:type:add")
//    @CustomLogger(title = "基础模块设备分类子表-新增", businessType = CustomBusinessType.SAVE)
    @PostMapping("/add")
    public AjaxResult addSave(@Validated(ValidationGroups.Save.class) @RequestBody EquipmentType equipmentType) {
        int i = equipmentTypeService.insertEquipmentType(equipmentType);
        if(i==-1){
            return AjaxResult.error("分类名称已存在");
        }
        return toAjax(i);
    }
    

    /**
     * 修改保存基础模块---设备分类
     */
    @PreAuthorize(hasPermi ="equipment:type:edit")
//    @CustomLogger(title = "基础模块设备分类表-修改", businessType = CustomBusinessType.UPDATE)
    @PostMapping("/edit")
    public AjaxResult editSave(@Validated(ValidationGroups.Update.class) @RequestBody EquipmentType equipmentType) {
        int i = equipmentTypeService.updateEquipmentType(equipmentType);
        if(i==-1){
            return AjaxResult.error("分类名称已存在");
        }
        return toAjax(i);
    }

    /**
     * 删除基础模块---设备分类
     */
    @PreAuthorize(hasPermi ="equipment:type:remove")
//    @CustomLogger(title = "基础模块设备分类表-删除", businessType = CustomBusinessType.DELETE)
    @PostMapping( "/remove")
    public AjaxResult remove(@RequestBody EquipmentType equipmentType) {
        String ids = equipmentType.getIds();
        int i = equipmentTypeService.deleteEquipmentTypeByIds(ids);
        if(i==-1){
            return AjaxResult.error("请先删除该分类下的数据");
        }
        return toAjax(i);
    }
}
