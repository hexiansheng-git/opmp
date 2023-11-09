package com.hhwy.pm.qqch.preparation.sbch.imported.plan.controller;

import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.sbch.imported.plan.domain.SbchImportPlan;
import com.hhwy.pm.qqch.preparation.sbch.imported.plan.domain.SbchImportPlanDetail;
import com.hhwy.pm.qqch.preparation.sbch.imported.plan.service.ISbchImportPlanDetailService;
import com.hhwy.pm.qqch.preparation.sbch.imported.plan.service.ISbchImportPlanService;
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
//    @PreAuthorize(hasPermi="inquiry:plan:list")
    @PostMapping("/list")
    //@CustomLogger(title = "设备进口方案 进口调查查询", businessType = CustomBusinessType.SELECT)
    @ResponseBody
    public AjaxResult list(@Validated(ValidationGroups.Select.class) @RequestBody SbchImportPlan sbchImportPlan) {
//        startPage(sbchImportPlan.getPageNum(),sbchImportPlan.getPageSize());
        List<SbchImportPlan> list = sbchImportPlanService.selectSbchImportPlanList(sbchImportPlan);
        return AjaxResult.success(getDataTable(list));
    }

    /**
     * 导出进口方案列表
     */
//    @PreAuthorize(hasPermi="inquiry:plan:export")
    //@CustomLogger(title = "设备进口方案 进口调查导出", businessType = CustomBusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
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
//    @PreAuthorize(hasPermi="inquiry:plan:add")
    //@CustomLogger(title = "设备进口方案 进口调查添加", businessType = CustomBusinessType.SAVE)
    @PostMapping("/add")
    @ResponseBody
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
//    @PreAuthorize(hasPermi="inquiry:plan:edit")
    //@CustomLogger(title = "设备进口策划 进口方案修改保存", businessType = CustomBusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
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
    public AjaxResult detail(@PathVariable("id") Long id){
        SbchImportPlan sbchImportPlan = sbchImportPlanService.selectSbchImportPlanById(id);
        return AjaxResult.success(sbchImportPlan);
    }

    /**
     * 删除进口方案
     */
//    @PreAuthorize(hasPermi="inquiry:plan:remove")
    //@CustomLogger(title = "设备进口策划 进口方案修改删除", businessType = CustomBusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
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
    public AjaxResult detailList(@PathVariable("id") Long id){
        SbchImportPlanDetail sbchImportPlanDetail = new SbchImportPlanDetail();
        sbchImportPlanDetail.setPlanId(id);
        List<SbchImportPlanDetail> sbchImportPlanDetails = sbchImportPlanDetailService.getSbchImportPlanDetailList(sbchImportPlanDetail);
        return AjaxResult.success(sbchImportPlanDetails);
    }

    @GetMapping("/getList")
    @ResponseBody
    public AjaxResult getList(BigDecimal version){
        SbchImportPlan sbchImportPlan =  sbchImportPlanService.getList(version);
        return AjaxResult.success(sbchImportPlan);
    }

    @PostMapping("/batchAdd")
    @ResponseBody
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
