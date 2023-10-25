package com.hhwy.pm.qqch.preparation.sbch.single.controller;

import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;

import com.hhwy.pm.qqch.preparation.sbch.single.domain.SbchSingleCheck;
import com.hhwy.pm.qqch.preparation.sbch.single.domain.SbchSingleCheckDetail;
import com.hhwy.pm.qqch.preparation.sbch.single.service.ISbchSingleCheckDetailService;
import com.hhwy.pm.qqch.preparation.sbch.single.service.ISbchSingleCheckService;
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
 * 单机核算策划Controller
 * 
 * @author zq
 * @date 2022-12-22
 */
@Controller
@RequestMapping("/single/check")
public class SbchSingleCheckController extends BaseController {

    @Autowired
    private ISbchSingleCheckService sbchSingleCheckService;
    @Autowired
    private ISbchSingleCheckDetailService sbchSingleCheckDetailService;
   

    /**
     * 查询单机核算策划列表
     */
//    @PreAuthorize(hasPermi="single:check:list")
    @PostMapping("/list")
    @ResponseBody
    public AjaxResult list(@Validated(ValidationGroups.Select.class) @RequestBody SbchSingleCheck sbchSingleCheck) {
//        startPage(sbchSingleCheck.getPageNum(),sbchSingleCheck.getPageSize());
        List<SbchSingleCheck> list = sbchSingleCheckService.selectSbchSingleCheckList(sbchSingleCheck);
        return AjaxResult.success(getDataTable(list));
    }

    /**
     * 导出单机核算策划列表
     */
//    @PreAuthorize(hasPermi="single:check:export")
    //@CustomLogger(title = "单机核算策划", businessType = CustomBusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public void export(@RequestBody SbchSingleCheck sbchSingleCheck, HttpServletResponse response) {
        try {
            List<SbchSingleCheck> list = sbchSingleCheckService.selectSbchSingleCheckList(sbchSingleCheck);
            ExcelUtils<SbchSingleCheck> util = new ExcelUtils<SbchSingleCheck>(SbchSingleCheck.class);
            util.exportExcel(response,list, "check");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    /**
     * 新增保存单机核算策划
     */
//    @PreAuthorize(hasPermi="single:check:add")
    //@CustomLogger(title = "单机核算策划", businessType = CustomBusinessType.SAVE)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated(ValidationGroups.Save.class) @RequestBody SbchSingleCheck sbchSingleCheck) {

        try{
            return toAjax(sbchSingleCheckService.insertSbchSingleCheck(sbchSingleCheck));
        }catch (CustomBusinessException e){
            e.printStackTrace();
            return AjaxResult.error(e.getMsg());
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
    }



    /**
     * 修改保存单机核算策划
     */
//    @PreAuthorize(hasPermi="single:check:edit")
    //@CustomLogger(title = "单机核算策划", businessType = CustomBusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated(ValidationGroups.Update.class) @RequestBody SbchSingleCheck sbchSingleCheck) {

        try{
            return toAjax(sbchSingleCheckService.updateSbchSingleCheck(sbchSingleCheck));
        }catch (CustomBusinessException e){
            e.printStackTrace();
            return AjaxResult.error(e.getMsg());
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 删除单机核算策划
     */
//    @PreAuthorize(hasPermi="single:check:remove")
    //@CustomLogger(title = "单机核算策划", businessType = CustomBusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(@RequestBody Map map) {
        if(ObjectNullUtil.isEmpty(map.get("ids"))){
            return AjaxResult.error("id不可为空");
        }
        try{
            return toAjax(sbchSingleCheckService.deleteSbchSingleCheckByIds(map.get("ids").toString()));
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
     * @date 2022/12/22 14:26
     * @param id
     * @return com.hhwy.common.core.web.domain.AjaxResult
     */
    @GetMapping("/detail/{id}")
    @ResponseBody
    public AjaxResult detail(@PathVariable(name = "id")Long id){
        SbchSingleCheck sbchSingleCheck = sbchSingleCheckService.selectSbchSingleCheckById(id);
        SbchSingleCheckDetail sbchSingleCheckDetail = new SbchSingleCheckDetail();
        sbchSingleCheckDetail.setInfoId(id);
        List<SbchSingleCheckDetail> sbchSingleCheckDetails = sbchSingleCheckDetailService.selectSbchSingleCheckDetailList(sbchSingleCheckDetail);
        sbchSingleCheck.setDetailList(sbchSingleCheckDetails);
        return AjaxResult.success(sbchSingleCheck);
    }

    @GetMapping("/getTempleteList")
    @ResponseBody
    public AjaxResult getTempleteList(){
        List<JSONObject> list = sbchSingleCheckService.getTempleteList();
        return AjaxResult.success(list);
    }

    @GetMapping("/getList")
    @ResponseBody
    public AjaxResult getList(BigDecimal version){
        SbchSingleCheck sbchSingleCheck = sbchSingleCheckService.getList(version);
        return AjaxResult.success(sbchSingleCheck);
    }

    @PostMapping("batchAdd")
    @ResponseBody
    public AjaxResult batchAdd(@Validated(ValidationGroups.Save.class) @RequestBody SbchSingleCheck sbchSingleCheck){
        try{
            sbchSingleCheckService.batchSave(sbchSingleCheck);
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
