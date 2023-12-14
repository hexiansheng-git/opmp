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
    @PostMapping("/list")
    @ResponseBody
    @CustomLogger(title = "前期策划-前期策划编制-设备策划-特种设备策划", name = "7.6.2特种设备风险识别与措施策划", businessType = CustomBusinessType.SELECT)
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
    @PostMapping("/export")
    @ResponseBody
    @CustomLogger(title = "前期策划-前期策划编制-设备策划-特种设备策划", name = "7.6.2特种设备风险识别与措施策划", businessType = CustomBusinessType.EXPORT)
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
    @PostMapping("/add")
    @ResponseBody
    @CustomLogger(title = "前期策划-前期策划编制-设备策划-特种设备策划", name = "7.6.2特种设备风险识别与措施策划", businessType = CustomBusinessType.SAVE)
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
    @PostMapping("/edit")
    @ResponseBody
    @CustomLogger(title = "前期策划-前期策划编制-设备策划-特种设备策划", name = "7.6.2特种设备风险识别与措施策划", businessType = CustomBusinessType.UPDATE)
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
    @PostMapping( "/remove")
    @ResponseBody
    @CustomLogger(title = "前期策划-前期策划编制-设备策划-特种设备策划", name = "7.6.2特种设备风险识别与措施策划", businessType = CustomBusinessType.DELETE)
    public AjaxResult remove(@Validated(ValidationGroups.Update.class) @RequestBody String ids) {
        Map<String,String> maps = (Map) JSON.parse(ids);
        if (StringUtils.isBlank(maps.get("ids")))
            return AjaxResult.error("id不可为空");
        return toAjax(sbchEquipmentSpecialPlanService.deleteSbchEquipmentSpecialPlanByIds(maps.get("ids")));
    }

    /**
     * 选择特种设备风险识别和措施策划列表
     */
    @GetMapping("/xzSpecialPlan")
    @ResponseBody
    @CustomLogger(title = "前期策划-前期策划编制-设备策划-特种设备策划", name = "7.6.2特种设备风险识别与措施策划", businessType = CustomBusinessType.SELECT)
    public AjaxResult xzSpecialPlan(SbchEquipmentSpecialPlanDetails sbchEquipmentSpecialPlanDetails) {
        List<SbchEquipmentSpecialPlanDetails> list = sbchEquipmentSpecialPlanDetailsService.selectSbchEquipmentSpecialPlanDetailshistoryList(sbchEquipmentSpecialPlanDetails);
        TableDataInfo dataTable = getDataTable(list);
        if(null==dataTable){
            return new AjaxResult(PmsConstant.WARN_CODE,"未查询到数据");
        }
        return AjaxResult.success(dataTable);
    }

    @GetMapping("/getList")
    @ResponseBody
    @CustomLogger(title = "前期策划-前期策划编制-设备策划-特种设备策划", name = "7.6.2特种设备风险识别与措施策划", businessType = CustomBusinessType.SELECT)
    public AjaxResult getList(BigDecimal version){
        SbchEquipmentSpecialPlan sbchEquipmentSpecialPlan = sbchEquipmentSpecialPlanService.getList(version);
        return AjaxResult.success(sbchEquipmentSpecialPlan);
    }

    @PostMapping("/batchAdd")
    @ResponseBody
    @CustomLogger(title = "前期策划-前期策划编制-设备策划-特种设备策划", name = "7.6.2特种设备风险识别与措施策划", businessType = CustomBusinessType.SAVE)
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
