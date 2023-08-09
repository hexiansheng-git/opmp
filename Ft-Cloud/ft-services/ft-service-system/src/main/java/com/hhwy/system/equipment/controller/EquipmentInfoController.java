package com.hhwy.system.equipment.controller;

import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.domain.base.system.equipment.EquipmentInfo;
import com.hhwy.system.equipment.service.IEquipmentInfoService;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 基础模块设备分类子表Controller
 * /equipment/info 外部接口
 * /self/equipment/info 内部用
 * @author jzq
 * @date 2023-03-03
 */
@RestController
@RequestMapping({"/equipment/info","/self/equipment/info"})
public class EquipmentInfoController extends BaseController {

    @Autowired
    private IEquipmentInfoService equipmentInfoService;


    /**
     * 查询基础模块设备分类子表列表
     */
    @PreAuthorize(hasPermi ="equipment:info:list")
    @GetMapping("/list")
//    @CustomLogger(title = "基础模块设备分类子表-查询", businessType = CustomBusinessType.SELECT)
    public AjaxResult list(EquipmentInfo equipmentInfo) {
        startPage();
        List<EquipmentInfo> list = equipmentInfoService.selectEquipmentInfoList(equipmentInfo);
        return AjaxResult.success(getDataTable(list));
    }

    @PostMapping("/listAll")
//    @CustomLogger(title = "基础模块设备分类子表-查询", businessType = CustomBusinessType.SELECT)
    public AjaxResult listAll(@RequestBody EquipmentInfo equipmentInfo) {
        List<EquipmentInfo> list = equipmentInfoService.selectEquipmentInfoList(equipmentInfo);
        return AjaxResult.success(list);
    }

    /**
     * 导出基础模块设备分类子表列表
     */
    @PreAuthorize(hasPermi ="equipment:info:export")
//    @CustomLogger(title = "基础模块设备分类子表-导出", businessType = CustomBusinessType.EXPORT)
    @PostMapping("/export")
    public void export(@RequestBody EquipmentInfo equipmentInfo, HttpServletResponse response) throws Exception{
        List<EquipmentInfo> list = equipmentInfoService.selectEquipmentInfoList(equipmentInfo);
        ExcelUtils<EquipmentInfo> util = new ExcelUtils<EquipmentInfo>(EquipmentInfo.class);
        util.exportExcel(response,list, "info");
    }


    /**
     * 新增保存基础模块设备分类子表
     */
    @PreAuthorize(hasPermi ="equipment:info:add")
//    @CustomLogger(title = "基础模块设备分类子表-新增", businessType = CustomBusinessType.SAVE)
    @PostMapping("/add")
    public AjaxResult addSave(@Validated(ValidationGroups.Save.class) @RequestBody EquipmentInfo equipmentInfo) {
        int i = equipmentInfoService.insertEquipmentInfo(equipmentInfo);
        if(i==-1){
            return AjaxResult.error("设备编码已存在");
        }
        return toAjax(i);
    }

    /**
     * 修改保存基础模块设备分类子表
     */
    @PreAuthorize(hasPermi ="equipment:info:edit")
//    @CustomLogger(title = "基础模块设备分类子表-修改", businessType = CustomBusinessType.UPDATE)
    @PostMapping("/edit")
    public AjaxResult editSave(@Validated(ValidationGroups.Update.class)  @RequestBody EquipmentInfo equipmentInfo) {
        int i = equipmentInfoService.updateEquipmentInfo(equipmentInfo);
        if(i==-1){
            return AjaxResult.error("设备编码已存在");
        }
        return toAjax(i);
    }

    /**
     * 删除基础模块设备分类子表
     */
    @PreAuthorize(hasPermi ="equipment:info:remove")
//    @CustomLogger(title = "基础模块设备分类子表-删除", businessType = CustomBusinessType.DELETE)
    @PostMapping( "/remove")
    public AjaxResult remove(@RequestBody EquipmentInfo equipmentInfo) {
        List<String> ids = equipmentInfo.getIds();
        String idStr = StringUtils.join(ids, ",");
        return toAjax(equipmentInfoService.deleteEquipmentInfoByIds(idStr));
    }

    @PreAuthorize(hasPermi ="equipment:info:importData")
//    @CustomLogger(title = "基础模块设备分类子表-导入", businessType = CustomBusinessType.IMPORT)
    @PostMapping( "/importData")
    public AjaxResult importData(Long parentId, MultipartFile file){
        try {
            ExcelUtils<EquipmentInfo> util = new ExcelUtils<>(EquipmentInfo.class);
            List<EquipmentInfo> list = util.importExcel(file.getInputStream());
            return equipmentInfoService.importData(list,parentId);
        }catch (Exception e){
            throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error, "导入异常");
        }
    }
}
