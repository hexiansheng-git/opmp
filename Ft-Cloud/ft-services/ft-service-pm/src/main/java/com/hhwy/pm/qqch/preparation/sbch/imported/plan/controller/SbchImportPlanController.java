package com.hhwy.pm.qqch.preparation.sbch.imported.plan.controller;

import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.sbch.imported.plan.domain.SbchImportPlan;
import com.hhwy.pm.qqch.preparation.sbch.imported.plan.domain.SbchImportPlanDetail;
import com.hhwy.pm.qqch.preparation.sbch.imported.plan.service.ISbchImportPlanDetailService;
import com.hhwy.pm.qqch.preparation.sbch.imported.plan.service.ISbchImportPlanService;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.objectUtil.ObjectNullUtil;
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
 * 进口方案Controller
 * 
 * @author zq
 * @date 2022-12-06
 */
@Controller
@RequestMapping("/inquiry/plan")
public class SbchImportPlanController extends BaseController {
    @Autowired
    private ISbchImportPlanService sbchImportPlanService;
    @Autowired
    private ISbchImportPlanDetailService sbchImportPlanDetailService;
    

    /**
     * 查询进口方案列表
     */
    @PostMapping("/list")
    @ResponseBody
    @CustomLogger(title = "前期策划-前期策划编制-设备策划-设备进口策划", name = "7.4.2进口方案", businessType = CustomBusinessType.SELECT)
    public AjaxResult list(@Validated(ValidationGroups.Select.class) @RequestBody SbchImportPlan sbchImportPlan) {
//        startPage(sbchImportPlan.getPageNum(),sbchImportPlan.getPageSize());
        List<SbchImportPlan> list = sbchImportPlanService.selectSbchImportPlanList(sbchImportPlan);
        return AjaxResult.success(getDataTable(list));
    }

    /**
     * 导出进口方案列表
     */
    @PostMapping("/export")
    @ResponseBody
    @CustomLogger(title = "前期策划-前期策划编制-设备策划-设备进口策划", name = "7.4.2进口方案", businessType = CustomBusinessType.EXPORT)
    public void export(@RequestBody SbchImportPlan sbchImportPlan, HttpServletResponse response) {
        try {
            List<SbchImportPlan> list = sbchImportPlanService.selectSbchImportPlanList(sbchImportPlan);
            ExcelUtils<SbchImportPlan> util = new ExcelUtils<SbchImportPlan>(SbchImportPlan.class);
            util.exportExcel(response,list, "info");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    /**
     * 新增保存进口方案
     */
    @PostMapping("/add")
    @ResponseBody
    @CustomLogger(title = "前期策划-前期策划编制-设备策划-设备进口策划", name = "7.4.2进口方案", businessType = CustomBusinessType.SAVE)
    public AjaxResult addSave(@Validated(ValidationGroups.Save.class) @RequestBody SbchImportPlan sbchImportPlan) {
        try{
            return toAjax(sbchImportPlanService.insertSbchImportPlan(sbchImportPlan));
        }catch (CustomBusinessException e){
            e.printStackTrace();
            return AjaxResult.error(e.getMsg());
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
    }
    

    /**
     * 修改保存进口方案
     */
    @PostMapping("/edit")
    @ResponseBody
    @CustomLogger(title = "前期策划-前期策划编制-设备策划-设备进口策划", name = "7.4.2进口方案", businessType = CustomBusinessType.SAVE)
    public AjaxResult editSave(@Validated(ValidationGroups.Update.class) @RequestBody SbchImportPlan sbchImportPlan) {
        try{
            return toAjax(sbchImportPlanService.updateSbchImportPlan(sbchImportPlan));
        }catch (CustomBusinessException e){
            e.printStackTrace();
            return AjaxResult.error(e.getMsg());
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 设备进口策划 进口方案详情
     * @author zq
     * @date 2022/12/7 18:19
     * @param id
     * @return com.hhwy.common.core.web.domain.AjaxResult
     */
    @GetMapping("/detail/{id}")
    @ResponseBody
    @CustomLogger(title = "前期策划-前期策划编制-设备策划-设备进口策划", name = "7.4.2进口方案", businessType = CustomBusinessType.SELECT)
    public AjaxResult detail(@PathVariable("id") Long id){
        SbchImportPlan sbchImportPlan = sbchImportPlanService.selectSbchImportPlanById(id);
        return AjaxResult.success(sbchImportPlan);
    }

    /**
     * 删除进口方案
     */
    @PostMapping( "/remove")
    @ResponseBody
    @CustomLogger(title = "前期策划-前期策划编制-设备策划-设备进口策划", name = "7.4.2进口方案", businessType = CustomBusinessType.DELETE)
    public AjaxResult remove(@RequestBody Map map) {
        if(ObjectNullUtil.isEmpty(map.get("ids"))){
            return AjaxResult.error("id不可为空");
        }
        return toAjax(sbchImportPlanService.deleteSbchImportPlanByIds(map.get("ids").toString()));
    }

    /**
     * 进口方案详情列表
     * @author zq
     * @date 2022/12/8 16:44
     * @param id
     * @return com.hhwy.common.core.web.domain.AjaxResult
     */
    @GetMapping("/detailList/{id}")
    @ResponseBody
    @CustomLogger(title = "前期策划-前期策划编制-设备策划-设备进口策划", name = "7.4.2进口方案", businessType = CustomBusinessType.SELECT)
    public AjaxResult detailList(@PathVariable("id") Long id){
        SbchImportPlanDetail sbchImportPlanDetail = new SbchImportPlanDetail();
        sbchImportPlanDetail.setPlanId(id);
        List<SbchImportPlanDetail> sbchImportPlanDetails = sbchImportPlanDetailService.getSbchImportPlanDetailList(sbchImportPlanDetail);
        return AjaxResult.success(sbchImportPlanDetails);
    }

    @GetMapping("/getList")
    @ResponseBody
    @CustomLogger(title = "前期策划-前期策划编制-设备策划-设备进口策划", name = "7.4.2进口方案", businessType = CustomBusinessType.SELECT)
    public AjaxResult getList(BigDecimal version){
        SbchImportPlan sbchImportPlan =  sbchImportPlanService.getList(version);
        return AjaxResult.success(sbchImportPlan);
    }

    @PostMapping("/batchAdd")
    @ResponseBody
    @CustomLogger(title = "前期策划-前期策划编制-设备策划-设备进口策划", name = "7.4.2进口方案", businessType = CustomBusinessType.SAVE)
    public AjaxResult batchAdd(@Validated(ValidationGroups.Save.class) @RequestBody SbchImportPlan sbchImportPlan){
        try{
            sbchImportPlanService.batchSave(sbchImportPlan);
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
