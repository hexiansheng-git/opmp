package com.hhwy.pm.qqch.preparation.sbch.imported.customs.controller;

import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;

import com.hhwy.pm.qqch.preparation.sbch.imported.customs.domain.SbchImportCustomsClear;
import com.hhwy.pm.qqch.preparation.sbch.imported.customs.domain.SbchImportCustomsClearDetail;
import com.hhwy.pm.qqch.preparation.sbch.imported.customs.service.ISbchImportCustomsClearDetailService;
import com.hhwy.pm.qqch.preparation.sbch.imported.customs.service.ISbchImportCustomsClearService;
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
 * 清关档案策划Controller
 * 
 * @author zq
 * @date 2022-12-14
 */
@Controller
@RequestMapping("/customs/clear")
public class SbchImportCustomsClearController extends BaseController {

    @Autowired
    private ISbchImportCustomsClearService sbchImportCustomsClearService;

    @Autowired
    private ISbchImportCustomsClearDetailService sbchImportCustomsClearDetailService;
    /**
     * 查询清关档案策划列表
     */
    @PostMapping("/list")
    @ResponseBody
    @CustomLogger(title = "前期策划-前期策划编制-设备策划-设备进口策划", name = "7.4.4清关档案策划", businessType = CustomBusinessType.SELECT)
    public AjaxResult list(@Validated(ValidationGroups.Select.class) @RequestBody SbchImportCustomsClear sbchImportCustomsClear) {
//        startPage(sbchImportCustomsClear.getPageNum(),sbchImportCustomsClear.getPageSize());
        List<SbchImportCustomsClear> list = sbchImportCustomsClearService.selectSbchImportCustomsClearList(sbchImportCustomsClear);
        return AjaxResult.success(getDataTable(list));
    }

    /**
     * 导出清关档案策划列表
     */
    @PostMapping("/export")
    @ResponseBody
    @CustomLogger(title = "前期策划-前期策划编制-设备策划-设备进口策划", name = "7.4.4清关档案策划", businessType = CustomBusinessType.EXPORT)
    public void export(@RequestBody SbchImportCustomsClear sbchImportCustomsClear, HttpServletResponse response) {
         try {
             List<SbchImportCustomsClear> list = sbchImportCustomsClearService.selectSbchImportCustomsClearList(sbchImportCustomsClear);
             ExcelUtils<SbchImportCustomsClear> util = new ExcelUtils<SbchImportCustomsClear>(SbchImportCustomsClear.class);
             util.exportExcel(response,list, "clear");
         } catch (IOException e) {
             e.printStackTrace();
         }
    }


    /**
     * 新增保存清关档案策划
     */
    @PostMapping("/add")
    @ResponseBody
    @CustomLogger(title = "前期策划-前期策划编制-设备策划-设备进口策划", name = "7.4.4清关档案策划", businessType = CustomBusinessType.SAVE)
    public AjaxResult addSave(@Validated(ValidationGroups.Save.class) @RequestBody SbchImportCustomsClear sbchImportCustomsClear) {
         try{
             return toAjax(sbchImportCustomsClearService.insertSbchImportCustomsClear(sbchImportCustomsClear));
         }catch (CustomBusinessException e){
             e.printStackTrace();
             return AjaxResult.error(e.getMsg());
         }catch (Exception e){
             e.printStackTrace();
             return AjaxResult.error(e.getMessage());
         }
    }

    /**
     * 修改保存清关档案策划
     */
    @PostMapping("/edit")
    @ResponseBody
    @CustomLogger(title = "前期策划-前期策划编制-设备策划-设备进口策划", name = "7.4.4清关档案策划", businessType = CustomBusinessType.UPDATE)
    public AjaxResult editSave(@Validated(ValidationGroups.Update.class) @RequestBody SbchImportCustomsClear sbchImportCustomsClear) {
         try{
             return toAjax(sbchImportCustomsClearService.updateSbchImportCustomsClear(sbchImportCustomsClear));
         }catch (CustomBusinessException e){
             e.printStackTrace();
             return AjaxResult.error(e.getMsg());
         }catch (Exception e){
             e.printStackTrace();
             return AjaxResult.error(e.getMessage());
         }
    }

    /**
     * 详情
     * @author zq
     * @date 2022/12/19 13:36
     * @param id
     * @return com.hhwy.common.core.web.domain.AjaxResult
     */
    @GetMapping("/detail/{id}")
    @ResponseBody
    @CustomLogger(title = "前期策划-前期策划编制-设备策划-设备进口策划", name = "7.4.4清关档案策划", businessType = CustomBusinessType.SELECT)
    public AjaxResult detail(@PathVariable("id")Long id){
        SbchImportCustomsClear sbchImportCustomsClear = sbchImportCustomsClearService.selectSbchImportCustomsClearById(id);
        SbchImportCustomsClearDetail sbchImportCustomsClearDetail = new SbchImportCustomsClearDetail();
        sbchImportCustomsClearDetail.setInfoId(id);
        List<SbchImportCustomsClearDetail> list = sbchImportCustomsClearDetailService.selectSbchImportCustomsClearDetailList(sbchImportCustomsClearDetail);
        sbchImportCustomsClear.setDetailList(list);
        return AjaxResult.success(sbchImportCustomsClear);
    }

    /**
     * 删除清关档案策划
     */
    @PostMapping( "/remove")
    @ResponseBody
    @CustomLogger(title = "前期策划-前期策划编制-设备策划-设备进口策划", name = "7.4.4清关档案策划", businessType = CustomBusinessType.DELETE)
    public AjaxResult remove(@RequestBody Map map) {
         if(ObjectNullUtil.isEmpty(map.get("ids"))){
             return AjaxResult.error("id不可为空");
         }
         try{
             return toAjax(sbchImportCustomsClearService.deleteSbchImportCustomsClearByIds(map.get("ids").toString()));
         }catch (CustomBusinessException e){
             e.printStackTrace();
             return AjaxResult.error(e.getMsg());
         }catch (Exception e){
             return AjaxResult.error(e.getMessage());
         }
    }

    @GetMapping("/getList")
    @ResponseBody
    @CustomLogger(title = "前期策划-前期策划编制-设备策划-设备进口策划", name = "7.4.4清关档案策划", businessType = CustomBusinessType.SELECT)
    public AjaxResult getList(BigDecimal version){
        SbchImportCustomsClear sbchImportCustomsClear =  sbchImportCustomsClearService.getList(version);
        return AjaxResult.success(sbchImportCustomsClear);
    }

    @PostMapping("/batchAdd")
    @ResponseBody
    @CustomLogger(title = "前期策划-前期策划编制-设备策划-设备进口策划", name = "7.4.4清关档案策划", businessType = CustomBusinessType.SAVE)
    public AjaxResult batchAdd(@Validated(ValidationGroups.Save.class) @RequestBody SbchImportCustomsClear sbchImportCustomsClear){
        try{
            sbchImportCustomsClearService.batchSave(sbchImportCustomsClear);
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
