package com.hhwy.pm.qqch.preparation.sbch.material.controller;

import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;

import com.hhwy.pm.qqch.preparation.sbch.material.domain.SbchMaterialManager;
import com.hhwy.pm.qqch.preparation.sbch.material.domain.SbchMaterialManagerDetail;
import com.hhwy.pm.qqch.preparation.sbch.material.service.ISbchMaterialManagerDetailService;
import com.hhwy.pm.qqch.preparation.sbch.material.service.ISbchMaterialManagerService;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.objectUtil.ObjectNullUtil;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


/**
 * 设备现场管理Controller
 * 
 * @author zq
 * @date 2022-12-20
 */
@Controller
@RequestMapping("/material/manager")
public class SbchMaterialManagerController extends BaseController {

    @Autowired
    private ISbchMaterialManagerService sbchMaterialManagerService;
    @Autowired
    private ISbchMaterialManagerDetailService sbchMaterialManagerDetailService;
    

    /**
     * 查询设备现场管理列表
     */
    @PreAuthorize(hasPermi="material:manager:list")
    @PostMapping("/list")
    @ResponseBody
    public AjaxResult list(@Validated(ValidationGroups.Select.class) @RequestBody SbchMaterialManager sbchMaterialManager) {
//        startPage(sbchMaterialManager.getPageNum(),sbchMaterialManager.getPageSize());
        List<SbchMaterialManager> list = sbchMaterialManagerService.selectSbchMaterialManagerList(sbchMaterialManager);
        return AjaxResult.success(getDataTable(list));
    }

    /**
     * 导出设备现场管理列表
     */
    @PreAuthorize(hasPermi="material:manager:export")
    //@CustomLogger(title = "设备现场管理", businessType = CustomBusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public void export(@RequestBody SbchMaterialManager sbchMaterialManager, HttpServletResponse response) {
        try{
            List<SbchMaterialManager> list = sbchMaterialManagerService.selectSbchMaterialManagerList(sbchMaterialManager);
            ExcelUtils<SbchMaterialManager> util = new ExcelUtils<SbchMaterialManager>(SbchMaterialManager.class);
            util.exportExcel(response,list, "manager");
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    

    /**
     * 新增保存设备现场管理
     */
    @PreAuthorize(hasPermi="material:manager:add")
    //@CustomLogger(title = "设备现场管理", businessType = CustomBusinessType.SAVE)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated(ValidationGroups.Save.class) @RequestBody SbchMaterialManager sbchMaterialManager) {
        try{
            return toAjax(sbchMaterialManagerService.insertSbchMaterialManager(sbchMaterialManager));
        }catch (CustomBusinessException e){
            e.printStackTrace();
            return AjaxResult.error(e.getMsg());
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
    }
    

    /**
     * 修改保存设备现场管理
     */
    @PreAuthorize(hasPermi="material:manager:edit")
    //@CustomLogger(title = "设备现场管理", businessType = CustomBusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated(ValidationGroups.Update.class) @RequestBody SbchMaterialManager sbchMaterialManager) {
        try{
            return toAjax(sbchMaterialManagerService.updateSbchMaterialManager(sbchMaterialManager));
        }catch (CustomBusinessException e){
            e.printStackTrace();
            return AjaxResult.error(e.getMsg());
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 删除设备现场管理
     */
    @PreAuthorize(hasPermi="material:manager:remove")
    //@CustomLogger(title = "设备现场管理", businessType = CustomBusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(@RequestBody Map map) {
        if(ObjectNullUtil.isEmpty(map.get("ids"))){
            return AjaxResult.error("id不可为空");
        }
        try{
            return toAjax(sbchMaterialManagerService.deleteSbchMaterialManagerByIds(map.get("ids").toString()));
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
     * @date 2022/12/20 14:28
     * @param id
     * @return com.hhwy.common.core.web.domain.AjaxResult
     */
    @GetMapping("/detail/{id}")
    @ResponseBody
    public AjaxResult detail(@PathVariable("id")Long id){
        SbchMaterialManager sbchMaterialManager = sbchMaterialManagerService.selectSbchMaterialManagerById(id);
        SbchMaterialManagerDetail sbchMaterialManagerDetail = new SbchMaterialManagerDetail();
        sbchMaterialManagerDetail.setInfoId(id);
        List<SbchMaterialManagerDetail> sbchMaterialManagerDetails = sbchMaterialManagerDetailService.selectSbchMaterialManagerDetailList(sbchMaterialManagerDetail);
        sbchMaterialManager.setDetailList(sbchMaterialManagerDetails);
        return AjaxResult.success(sbchMaterialManager);
    }

    @GetMapping("/getTempleteList")
    @ResponseBody
    public AjaxResult getTempleteList(){
        List<JSONObject> list = sbchMaterialManagerService.getTempleteList();
        return AjaxResult.success(list);
    }

    @GetMapping("/getList")
    @ResponseBody
    public AjaxResult getList(BigDecimal version){
        SbchMaterialManager sbchMaterialManager = sbchMaterialManagerService.getList(version);
        return AjaxResult.success(sbchMaterialManager);
    }

    @PostMapping("/batchAdd")
    @ResponseBody
    public AjaxResult batchAdd(@Validated(ValidationGroups.Save.class) @RequestBody SbchMaterialManager sbchMaterialManager){
        try{
            sbchMaterialManagerService.batchSave(sbchMaterialManager);
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
