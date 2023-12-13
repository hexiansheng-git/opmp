package com.hhwy.pm.qqch.preparation.sbch.imported.material.controller;

import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;

import com.hhwy.pm.qqch.preparation.sbch.imported.material.domain.SbchMaterialTranPlan;
import com.hhwy.pm.qqch.preparation.sbch.imported.material.domain.SbchMaterialTranPlanDetail;
import com.hhwy.pm.qqch.preparation.sbch.imported.material.service.ISbchMaterialTranPlanDetailService;
import com.hhwy.pm.qqch.preparation.sbch.imported.material.service.ISbchMaterialTranPlanService;
import com.hhwy.pm.qqch.preparation.sbch.plan.domain.SbchTotalDemandPlanDetail;
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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 大型成套设备运输方案Controller
 * 
 * @author zq
 * @date 2022-12-12
 */
@Controller
@RequestMapping("/train/info")
public class SbchMaterialTranPlanController extends BaseController {
    @Autowired
    private ISbchMaterialTranPlanService sbchMaterialTranPlanService;
    @Autowired
    private ISbchMaterialTranPlanDetailService sbchMaterialTranPlanDetailService;
//    @Autowired
//    private ISbchTotalDemandPlanDetailService sbchTotalDemandPlanDetailService;

    /**
     * 查询大型成套设备运输方案列表
     */
    @PostMapping("/list")
    @ResponseBody
    @CustomLogger(title = "前期策划-前期策划编制-设备策划-设备进口策划", name = "7.4.3大型成套设备运输方案", businessType = CustomBusinessType.SELECT)
    public AjaxResult list(@RequestBody SbchMaterialTranPlan sbchMaterialTranPlan) {
//        startPage(sbchMaterialTranPlan.getPageNum(),sbchMaterialTranPlan.getPageSize());
        List<SbchMaterialTranPlan> list = sbchMaterialTranPlanService.selectSbchMaterialTranPlanList(sbchMaterialTranPlan);
        return AjaxResult.success(getDataTable(list));
    }

    /**
     * 导出大型成套设备运输方案列表
     */
    @PostMapping("/export")
    @ResponseBody
    @CustomLogger(title = "前期策划-前期策划编制-设备策划-设备进口策划", name = "7.4.3大型成套设备运输方案", businessType = CustomBusinessType.EXPORT)
    public void export(@RequestBody SbchMaterialTranPlan sbchMaterialTranPlan, HttpServletResponse response) {
        try{
            List<SbchMaterialTranPlan> list = sbchMaterialTranPlanService.selectSbchMaterialTranPlanList(sbchMaterialTranPlan);
            ExcelUtils<SbchMaterialTranPlan> util = new ExcelUtils<SbchMaterialTranPlan>(SbchMaterialTranPlan.class);
            util.exportExcel(response,list, "info");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 新增保存大型成套设备运输方案
     */
    @PostMapping("/add")
    @ResponseBody
    @CustomLogger(title = "前期策划-前期策划编制-设备策划-设备进口策划", name = "7.4.3大型成套设备运输方案", businessType = CustomBusinessType.SAVE)
    public AjaxResult addSave(@Validated(ValidationGroups.Save.class) @RequestBody SbchMaterialTranPlan sbchMaterialTranPlan) {
        try{
            return toAjax(sbchMaterialTranPlanService.insertSbchMaterialTranPlan(sbchMaterialTranPlan));
        }catch (CustomBusinessException e){
            e.printStackTrace();
            return AjaxResult.error(e.getMsg());
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
    }


    /**
     * 修改保存大型成套设备运输方案
     */
    @PostMapping("/edit")
    @ResponseBody
    @CustomLogger(title = "前期策划-前期策划编制-设备策划-设备进口策划", name = "7.4.3大型成套设备运输方案", businessType = CustomBusinessType.UPDATE)
    public AjaxResult editSave(@Validated(ValidationGroups.Update.class) @RequestBody SbchMaterialTranPlan sbchMaterialTranPlan) {
        try{
            return toAjax(sbchMaterialTranPlanService.updateSbchMaterialTranPlan(sbchMaterialTranPlan));
        }catch (CustomBusinessException e){
            e.printStackTrace();
            return AjaxResult.error(e.getMsg());
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 删除大型成套设备运输方案
     */
    @PostMapping( "/remove")
    @ResponseBody
    @CustomLogger(title = "前期策划-前期策划编制-设备策划-设备进口策划", name = "7.4.3大型成套设备运输方案", businessType = CustomBusinessType.DELETE)
    public AjaxResult remove(@RequestBody Map map) {
        if(ObjectNullUtil.isEmpty(map.get("ids"))){
            return AjaxResult.error("id不可为空");
        }
        try{
            return toAjax(sbchMaterialTranPlanService.deleteSbchMaterialTranPlanByIds(map.get("ids").toString()));
        }catch (CustomBusinessException e){
            e.printStackTrace();
            return AjaxResult.error(e.getMsg());
        }catch (Exception e){
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 详情
     * @author zq
     * @date 2022/12/12 14:54
     * @param id
     * @return com.hhwy.common.core.web.domain.AjaxResult
     */
    @GetMapping("/detail/{id}")
    @ResponseBody
    @CustomLogger(title = "前期策划-前期策划编制-设备策划-设备进口策划", name = "7.4.3大型成套设备运输方案", businessType = CustomBusinessType.SELECT)
    public AjaxResult detail (@PathVariable("id") Long id){
        SbchMaterialTranPlan sbchMaterialTranPlan = sbchMaterialTranPlanService.selectSbchMaterialTranPlanById(id);
        return AjaxResult.success(sbchMaterialTranPlan);
    }

    /**
     * 子表详情列表
     * @author zq
     * @date 2022/12/5 20:05
     * @param id
     * @return com.hhwy.common.core.web.domain.AjaxResult
     */
    @GetMapping("/detailList/{id}")
    @ResponseBody
    @CustomLogger(title = "前期策划-前期策划编制-设备策划-设备进口策划", name = "7.4.3大型成套设备运输方案", businessType = CustomBusinessType.SELECT)
    public AjaxResult detailList(@PathVariable("id") Long id){
        SbchMaterialTranPlanDetail sbchMaterialTranPlanDetail = new SbchMaterialTranPlanDetail();
        sbchMaterialTranPlanDetail.setPlanId(id);
        List<SbchMaterialTranPlanDetail> list = sbchMaterialTranPlanDetailService.selectSbchMaterialTranPlanDetailList(sbchMaterialTranPlanDetail);
        HashSet<String> materialCodes = new HashSet<>();
        for (SbchMaterialTranPlanDetail detail : list) {
            materialCodes.add(detail.getMaterialCode());
        }
        SbchTotalDemandPlanDetail sbchTotalDemandPlanDetail = new SbchTotalDemandPlanDetail();
        sbchTotalDemandPlanDetail.setMaterialCodeList(new ArrayList<>(materialCodes));
//        List<SbchTotalDemandPlanDetail> detailList = sbchTotalDemandPlanDetailService.selectSbchTotalDemandPlanDetailLeaderList(sbchTotalDemandPlanDetail);
//        Map<String, String> materialNameMap = detailList.stream().collect(Collectors.groupingBy(t -> t.getMaterialCode(), Collectors.collectingAndThen(Collectors.toList(), v -> v.get(0).getMaterialName() + "&" + v.get(0).getMaterialSpec())));
//        for (SbchMaterialTranPlanDetail detail : list) {
//            String s = materialNameMap.get(detail.getMaterialCode());
//            if (!ObjectNullUtil.isEmpty(s)) {
//                String[] split = s.split("&");
//                detail.setMaterialName(split[0]);
//                detail.setMaterialSpec(split.length>1 ? split[1] : "");
//            }
//        }

        return AjaxResult.success(list);
    }

    @GetMapping("/getList")
    @ResponseBody
    @CustomLogger(title = "前期策划-前期策划编制-设备策划-设备进口策划", name = "7.4.3大型成套设备运输方案", businessType = CustomBusinessType.SELECT)
    public AjaxResult getList(BigDecimal version){
        SbchMaterialTranPlan sbchMaterialTranPlan = sbchMaterialTranPlanService.getList(version);
        return AjaxResult.success(sbchMaterialTranPlan);
    }

    @PostMapping("/batchAdd")
    @ResponseBody
    @CustomLogger(title = "前期策划-前期策划编制-设备策划-设备进口策划", name = "7.4.3大型成套设备运输方案", businessType = CustomBusinessType.SAVE)
    public AjaxResult batchAdd(@Validated(ValidationGroups.Save.class) @RequestBody  SbchMaterialTranPlan sbchMaterialTranPlan){
        try{
            sbchMaterialTranPlanService.batchSave(sbchMaterialTranPlan);
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
