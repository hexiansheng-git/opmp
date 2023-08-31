package com.hhwy.pm.qqch.preparation.sbch.sbchequipmentspecial.controller;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.alibaba.fastjson.JSON;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.page.TableDataInfo;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentspecial.domain.SbchEquipmentSpecial;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentspecial.service.ISbchEquipmentSpecialService;
import com.hhwy.utils.common.PmsConstant;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 特种设备管理Controller
 * 
 * @author hwj
 * @date 2022-12-05
 */
@Controller
@RequestMapping("/equipmentspecial/special")
public class SbchEquipmentSpecialController extends BaseController {

    @Autowired
    private ISbchEquipmentSpecialService sbchEquipmentSpecialService;
//    /**
//     * 新增 编辑 详情数据回显
//     */
//    @GetMapping("baseInfo")
//   // @CustomLogger(title = "特种设备管理-新增/编辑/详情查询数据",businessType = CustomBusinessType.SELECT)
//    @ResponseBody
//    public AjaxResult baseInfo(@RequestParam Map<String, String> map) {
//        return AjaxResult.success(sbchEquipmentSpecialService.baseInfo(map));
//    }

    /**
     * 查询特种设备管理列表
     */
    @PreAuthorize(hasPermi ="equipmentspecial:special:list")
    @PostMapping("/list")
   // @CustomLogger(title = "特种设备管理-列表查询",businessType = CustomBusinessType.SELECT)
    @ResponseBody
    public AjaxResult list(@Validated(ValidationGroups.Select.class) @RequestBody SbchEquipmentSpecial sbchEquipmentSpecial) {
        //分页
//        startPage(sbchEquipmentSpecial.getPageNum(),sbchEquipmentSpecial.getPageSize());
        List<SbchEquipmentSpecial> list = sbchEquipmentSpecialService.selectSbchEquipmentSpecialList(sbchEquipmentSpecial);
        TableDataInfo dataTable = getDataTable(list);
        if(null==dataTable){
            return new AjaxResult(PmsConstant.WARN_CODE,"未查询到数据");
        }
        return AjaxResult.success(dataTable);
    }

    /**
     * 导出特种设备管理列表
     */
    @PreAuthorize(hasPermi ="equipmentspecial:special:export")
   // @CustomLogger(title = "特种设备管理-导出",businessType = CustomBusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public void export(@RequestBody SbchEquipmentSpecial sbchEquipmentSpecial, HttpServletResponse response) {
        List<SbchEquipmentSpecial> list = sbchEquipmentSpecialService.selectSbchEquipmentSpecialList(sbchEquipmentSpecial);
        ExcelUtils<SbchEquipmentSpecial> util = new ExcelUtils<SbchEquipmentSpecial>(SbchEquipmentSpecial.class);
        try {
            util.exportExcel(response,list, "特种设备管理");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 新增保存特种设备管理
     */
    @PreAuthorize(hasPermi ="equipmentspecial:special:add")
   // @CustomLogger(title = "特种设备管理-保存",businessType = CustomBusinessType.SAVE)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated(ValidationGroups.Save.class) @RequestBody SbchEquipmentSpecial sbchEquipmentSpecial) {
        try {
            return toAjax(sbchEquipmentSpecialService.insertSbchEquipmentSpecial(sbchEquipmentSpecial));
        }catch (CustomBusinessException e){
            e.printStackTrace();
            return AjaxResult.error(e.getMsg());
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 修改保存特种设备管理
     */
    @PreAuthorize(hasPermi ="equipmentspecial:special:edit")
   // @CustomLogger(title = "特种设备管理-编辑",businessType = CustomBusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated(ValidationGroups.Update.class) @RequestBody SbchEquipmentSpecial sbchEquipmentSpecial) {
        try {
            return toAjax(sbchEquipmentSpecialService.updateSbchEquipmentSpecial(sbchEquipmentSpecial));
        }catch (CustomBusinessException e){
            e.printStackTrace();
            return AjaxResult.error(e.getMsg());
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 删除特种设备管理
     */
    @PreAuthorize(hasPermi ="equipmentspecial:special:remove")
   // @CustomLogger(title = "特种设备管理-删除",businessType = CustomBusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(@Validated(ValidationGroups.Update.class) @RequestBody String ids) {
            Map<String,String> maps = (Map) JSON.parse(ids);
            if (StringUtils.isBlank(maps.get("ids")))
                return AjaxResult.error("id不可为空");
            return toAjax(sbchEquipmentSpecialService.deleteSbchEquipmentSpecialByIds(maps.get("ids")));
    }

    @GetMapping("/getList")
    @ResponseBody
    public AjaxResult getList(BigDecimal version){
        SbchEquipmentSpecial sbchEquipmentSpecial = sbchEquipmentSpecialService.getList(version);
        return AjaxResult.success(sbchEquipmentSpecial);
    }

    @PostMapping("/batchAdd")
    @ResponseBody
    public AjaxResult batchAdd(@Validated(ValidationGroups.Save.class) @RequestBody SbchEquipmentSpecial sbchEquipmentSpecial){
        try{
            sbchEquipmentSpecialService.batchSave(sbchEquipmentSpecial);
            return AjaxResult.success();
        }catch (CustomBusinessException e){
            e.printStackTrace();
            return AjaxResult.error(e.getMsg());
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
    }
}
