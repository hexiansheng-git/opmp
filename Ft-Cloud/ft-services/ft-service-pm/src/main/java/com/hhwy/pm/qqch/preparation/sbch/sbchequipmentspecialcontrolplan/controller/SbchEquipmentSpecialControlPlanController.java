package com.hhwy.pm.qqch.preparation.sbch.sbchequipmentspecialcontrolplan.controller;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.alibaba.fastjson.JSON;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.page.TableDataInfo;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentspecialcontrolplan.domain.SbchEquipmentSpecialControlPlan;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentspecialcontrolplan.service.ISbchEquipmentSpecialControlPlanService;
import com.hhwy.utils.common.PmsConstant;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
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
 * 特种设备过程管控策划Controller
 * 
 * @author hwj
 * @date 2022-12-07
 *
 * 7.6.3
 */
@Controller
@RequestMapping("/equipmentspecial/control")
public class SbchEquipmentSpecialControlPlanController extends BaseController {
    @Autowired
    private ISbchEquipmentSpecialControlPlanService sbchEquipmentSpecialControlPlanService;

//    /**
//     * 新增 编辑 详情数据回显
//     */
//    @GetMapping("baseInfo")
//    //@CustomLogger(title = "特种设备过程管控策划-新增/编辑/详情查询数据",businessType = CustomBusinessType.SELECT)
//    @ResponseBody
//    public AjaxResult baseInfo(@RequestParam Map<String, String> map) {
//        return AjaxResult.success(sbchEquipmentSpecialControlPlanService.baseInfo(map));
//    }


    /**
     * 查询特种设备过程管控策划列表
     */
    @PostMapping("/list")
    @ResponseBody
    @CustomLogger(title = "前期策划-前期策划编制-设备策划-特种设备策划", name = "7.6.3特种设备过程管控策划", businessType = CustomBusinessType.SELECT)
    public AjaxResult list(@Validated(ValidationGroups.Select.class) @RequestBody SbchEquipmentSpecialControlPlan sbchEquipmentSpecialControlPlan) {
//        startPage(sbchEquipmentSpecialControlPlan.getPageNum(),sbchEquipmentSpecialControlPlan.getPageSize());
        List<SbchEquipmentSpecialControlPlan> list = sbchEquipmentSpecialControlPlanService.selectSbchEquipmentSpecialControlPlanList(sbchEquipmentSpecialControlPlan);
        TableDataInfo dataTable = getDataTable(list);
        if(null==dataTable){
            return new AjaxResult(PmsConstant.WARN_CODE,"未查询到数据");
        }
        return AjaxResult.success(dataTable);    }

    /**
     * 导出特种设备过程管控策划列表
     */
    @PostMapping("/export")
    @ResponseBody
    @CustomLogger(title = "前期策划-前期策划编制-设备策划-特种设备策划", name = "7.6.3特种设备过程管控策划", businessType = CustomBusinessType.EXPORT)
    public void export(@RequestBody SbchEquipmentSpecialControlPlan sbchEquipmentSpecialControlPlan, HttpServletResponse response) {
        List<SbchEquipmentSpecialControlPlan> list = sbchEquipmentSpecialControlPlanService.selectSbchEquipmentSpecialControlPlanList(sbchEquipmentSpecialControlPlan);
        ExcelUtils<SbchEquipmentSpecialControlPlan> util = new ExcelUtils<SbchEquipmentSpecialControlPlan>(SbchEquipmentSpecialControlPlan.class);
        try {
            util.exportExcel(response,list, "特种设备过程管控策划");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }    }

    /**
     * 新增保存特种设备过程管控策划
     */
    @PostMapping("/add")
    @ResponseBody
    @CustomLogger(title = "前期策划-前期策划编制-设备策划-特种设备策划", name = "7.6.3特种设备过程管控策划", businessType = CustomBusinessType.SAVE)
    public AjaxResult addSave(@Validated(ValidationGroups.Save.class) @RequestBody SbchEquipmentSpecialControlPlan sbchEquipmentSpecialControlPlan) {
        try {
            return toAjax(sbchEquipmentSpecialControlPlanService.insertSbchEquipmentSpecialControlPlan(sbchEquipmentSpecialControlPlan));
        }catch (CustomBusinessException e){
            e.printStackTrace();
            return AjaxResult.error(e.getMsg());
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 修改保存特种设备过程管控策划
     */
    @PostMapping("/edit")
    @ResponseBody
    @CustomLogger(title = "前期策划-前期策划编制-设备策划-特种设备策划", name = "7.6.3特种设备过程管控策划", businessType = CustomBusinessType.UPDATE)
    public AjaxResult editSave(@Validated(ValidationGroups.Update.class) @RequestBody SbchEquipmentSpecialControlPlan sbchEquipmentSpecialControlPlan) {
        try {
            return toAjax(sbchEquipmentSpecialControlPlanService.updateSbchEquipmentSpecialControlPlan(sbchEquipmentSpecialControlPlan));
        }catch (CustomBusinessException e){
            e.printStackTrace();
            return AjaxResult.error(e.getMsg());
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 删除特种设备过程管控策划
     */
    @PostMapping( "/remove")
    @ResponseBody
    @CustomLogger(title = "前期策划-前期策划编制-设备策划-特种设备策划", name = "7.6.3特种设备过程管控策划", businessType = CustomBusinessType.DELETE)
    public AjaxResult remove(@Validated(ValidationGroups.Update.class) @RequestBody String ids) {
        Map<String,String> maps = (Map) JSON.parse(ids);
        if (StringUtils.isBlank(maps.get("ids")))
            return AjaxResult.error("id不可为空");
        return toAjax(sbchEquipmentSpecialControlPlanService.deleteSbchEquipmentSpecialControlPlanByIds(maps.get("ids")));
    }

    @GetMapping("/getList")
    @ResponseBody
    @CustomLogger(title = "前期策划-前期策划编制-设备策划-特种设备策划", name = "7.6.3特种设备过程管控策划", businessType = CustomBusinessType.SELECT)
    public AjaxResult getList(BigDecimal version){
        SbchEquipmentSpecialControlPlan sbchEquipmentSpecialControlPlan = sbchEquipmentSpecialControlPlanService.getList(version);
        return AjaxResult.success(sbchEquipmentSpecialControlPlan);
    }

    @PostMapping("/batchAdd")
    @ResponseBody
    @CustomLogger(title = "前期策划-前期策划编制-设备策划-特种设备策划", name = "7.6.3特种设备过程管控策划", businessType = CustomBusinessType.SAVE)
    public AjaxResult batchAdd(@Validated(ValidationGroups.Save.class)@RequestBody SbchEquipmentSpecialControlPlan sbchEquipmentSpecialControlPlan ){
        try{
            sbchEquipmentSpecialControlPlanService.batchSave(sbchEquipmentSpecialControlPlan);
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
