package com.hhwy.pm.qqch.preparation.sbch.sbchequipmentspecialplan.controller;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.alibaba.fastjson.JSON;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.page.TableDataInfo;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentspecialplan.domain.SbchEquipmentSpecialPlan;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentspecialplan.domain.SbchEquipmentSpecialPlanDetails;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentspecialplan.service.ISbchEquipmentSpecialPlanDetailsService;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentspecialplan.service.ISbchEquipmentSpecialPlanService;
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
 * 特种设备风险识别和措施策划Controller
 *
 * @author hwj
 * @date 2022-12-06
 */
@Controller
@RequestMapping("/equipmentspecial/plan")
public class SbchEquipmentSpecialPlanController extends BaseController {

    @Autowired
    private ISbchEquipmentSpecialPlanService sbchEquipmentSpecialPlanService;
    @Autowired
    private ISbchEquipmentSpecialPlanDetailsService sbchEquipmentSpecialPlanDetailsService;

    /**
     * 查询特种设备风险识别和措施策划列表
     */
//    @PreAuthorize(hasPermi ="equipmentspecial:plan:list")
    @PostMapping("/list")
    //@CustomLogger(title = "特种设备风险识别和措施策划-列表查询",businessType = CustomBusinessType.SELECT)
    @ResponseBody
    public AjaxResult list(@Validated(ValidationGroups.Select.class) @RequestBody SbchEquipmentSpecialPlan sbchEquipmentSpecialPlan) {
        //分页
//        startPage(sbchEquipmentSpecialPlan.getPageNum(),sbchEquipmentSpecialPlan.getPageSize());
        List<SbchEquipmentSpecialPlan> list = sbchEquipmentSpecialPlanService.selectSbchEquipmentSpecialPlanList(sbchEquipmentSpecialPlan);
        TableDataInfo dataTable = getDataTable(list);
        if(null==dataTable){
            return new AjaxResult(PmsConstant.WARN_CODE,"未查询到数据");
        }
        return AjaxResult.success(dataTable);
    }

    /**
     * 导出特种设备风险识别和措施策划列表
     */
//    @PreAuthorize(hasPermi ="equipmentspecial:plan:export")
    //@CustomLogger(title = "特种设备风险识别和措施策划-导出",businessType = CustomBusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public void export(@RequestBody SbchEquipmentSpecialPlan sbchEquipmentSpecialPlan,HttpServletResponse response) {
        List<SbchEquipmentSpecialPlan> list = sbchEquipmentSpecialPlanService.selectSbchEquipmentSpecialPlanList(sbchEquipmentSpecialPlan);
        ExcelUtils<SbchEquipmentSpecialPlan> util = new ExcelUtils<SbchEquipmentSpecialPlan>(SbchEquipmentSpecialPlan.class);
        try {
            util.exportExcel(response,list, "协作单位设备管理");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 新增保存特种设备风险识别和措施策划
     */
//    @PreAuthorize(hasPermi ="equipmentspecial:plan:add")
    //@CustomLogger(title = "特种设备风险识别和措施策划-保存",businessType = CustomBusinessType.SAVE)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated(ValidationGroups.Save.class) @RequestBody SbchEquipmentSpecialPlan sbchEquipmentSpecialPlan) {
        try {
            return toAjax(sbchEquipmentSpecialPlanService.insertSbchEquipmentSpecialPlan(sbchEquipmentSpecialPlan));
        }catch (CustomBusinessException e){
            e.printStackTrace();
            return AjaxResult.error(e.getMsg());
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 修改保存特种设备风险识别和措施策划
     */
//    @PreAuthorize(hasPermi ="equipmentspecial:plan:edit")
    //@CustomLogger(title = "特种设备风险识别和措施策划-编辑",businessType = CustomBusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated(ValidationGroups.Update.class) @RequestBody SbchEquipmentSpecialPlan sbchEquipmentSpecialPlan) {
        try {
            return toAjax(sbchEquipmentSpecialPlanService.updateSbchEquipmentSpecialPlan(sbchEquipmentSpecialPlan));
        }catch (CustomBusinessException e){
            e.printStackTrace();
            return AjaxResult.error(e.getMsg());
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 删除特种设备风险识别和措施策划
     */
//    @PreAuthorize(hasPermi ="equipmentspecial:special:remove")
    //@CustomLogger(title = "特种设备管理-删除",businessType = CustomBusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(@Validated(ValidationGroups.Update.class) @RequestBody String ids) {
        Map<String,String> maps = (Map) JSON.parse(ids);
        if (StringUtils.isBlank(maps.get("ids")))
            return AjaxResult.error("id不可为空");
        return toAjax(sbchEquipmentSpecialPlanService.deleteSbchEquipmentSpecialPlanByIds(maps.get("ids")));
    }

    /**
     * 选择特种设备风险识别和措施策划列表
     */
//    @PreAuthorize(hasPermi ="equipmentspecial:plan:xzSpecialPlan")
    @GetMapping("/xzSpecialPlan")
    //@CustomLogger(title = "特种设备风险识别和措施策划-列表查询",businessType = CustomBusinessType.SELECT)
    @ResponseBody
    public AjaxResult xzSpecialPlan(@Validated(ValidationGroups.Select.class) @RequestBody SbchEquipmentSpecialPlanDetails sbchEquipmentSpecialPlanDetails) {
        List<SbchEquipmentSpecialPlanDetails> list = sbchEquipmentSpecialPlanDetailsService.selectSbchEquipmentSpecialPlanDetailshistoryList(sbchEquipmentSpecialPlanDetails);
        TableDataInfo dataTable = getDataTable(list);
        if(null==dataTable){
            return new AjaxResult(PmsConstant.WARN_CODE,"未查询到数据");
        }
        return AjaxResult.success(dataTable);
    }

    @GetMapping("/getList")
    @ResponseBody
    public AjaxResult getList(BigDecimal version){
        SbchEquipmentSpecialPlan sbchEquipmentSpecialPlan = sbchEquipmentSpecialPlanService.getList(version);
        return AjaxResult.success(sbchEquipmentSpecialPlan);
    }

    @PostMapping("/batchAdd")
    @ResponseBody
    public AjaxResult batchAdd(@Validated(ValidationGroups.Save.class) @RequestBody SbchEquipmentSpecialPlan sbchEquipmentSpecialPlan){
        try{
            sbchEquipmentSpecialPlanService.batchSave(sbchEquipmentSpecialPlan);
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
