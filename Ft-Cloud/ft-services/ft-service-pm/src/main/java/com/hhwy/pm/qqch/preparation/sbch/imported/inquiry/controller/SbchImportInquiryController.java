package com.hhwy.pm.qqch.preparation.sbch.imported.inquiry.controller;

import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;

import com.hhwy.pm.qqch.preparation.sbch.imported.inquiry.domain.SbchImportInquiry;
import com.hhwy.pm.qqch.preparation.sbch.imported.inquiry.service.ISbchImportInquiryService;
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
 * 设备进口策划 进口调查Controller
 * 
 * @author zq
 * @date 2022-12-05
 */
@Controller
@RequestMapping("/inquiry/info")
public class SbchImportInquiryController extends BaseController {

    @Autowired
    private ISbchImportInquiryService sbchImportInquiryService;
    

    /**
     * 查询设备进口策划 进口调查列表
     */
    @PreAuthorize(hasPermi="inquiry:info:list")
    @PostMapping("/list")
    //@CustomLogger(title = "设备进口策划 进口调查查询", businessType = CustomBusinessType.SELECT)
    @ResponseBody
    public AjaxResult list(@Validated(ValidationGroups.Select.class) @RequestBody SbchImportInquiry sbchImportInquiry) {
        List<SbchImportInquiry> list = sbchImportInquiryService.selectSbchImportInquiryList(sbchImportInquiry);
        return AjaxResult.success(getDataTable(list));
    }

    /**
     * 导出设备进口策划 进口调查列表
     */
    @PreAuthorize(hasPermi="inquiry:info:export")
    //@CustomLogger(title = "设备进口策划 进口调查导出", businessType = CustomBusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public void export(@RequestBody SbchImportInquiry sbchImportInquiry, HttpServletResponse response) {
        try {
            List<SbchImportInquiry> list = sbchImportInquiryService.selectSbchImportInquiryList(sbchImportInquiry);
            ExcelUtils<SbchImportInquiry> util = new ExcelUtils<SbchImportInquiry>(SbchImportInquiry.class);
            util.exportExcel(response,list, "info");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 新增保存设备进口策划 进口调查
     */
    @PreAuthorize(hasPermi="inquiry:info:add")
    //@CustomLogger(title = "设备进口策划 进口调查添加", businessType = CustomBusinessType.SAVE)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated(ValidationGroups.Save.class) @RequestBody SbchImportInquiry sbchImportInquiry) {
        try{
            return toAjax(sbchImportInquiryService.insertSbchImportInquiry(sbchImportInquiry));
        }catch (CustomBusinessException e){
            e.printStackTrace();
            return AjaxResult.error(e.getMsg());
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
    }


    /**
     * 修改保存设备进口策划 进口调查
     */
    @PreAuthorize(hasPermi="inquiry:info:edit")
    //@CustomLogger(title = "设备进口策划 进口调查修改保存", businessType = CustomBusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated(ValidationGroups.Update.class) @RequestBody SbchImportInquiry sbchImportInquiry) {
        try{
            return toAjax(sbchImportInquiryService.updateSbchImportInquiry(sbchImportInquiry));
        }catch (CustomBusinessException e){
            e.printStackTrace();
            return AjaxResult.error(e.getMsg());
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 删除设备进口策划 进口调查
     */
    @PreAuthorize(hasPermi="inquiry:info:remove")
    //@CustomLogger(title = "设备进口策划 进口调查修改删除", businessType = CustomBusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(@RequestBody Map map) {
        if(ObjectNullUtil.isEmpty(map.get("ids"))){
            return AjaxResult.error("id不可为空");
        }
        try{
            return toAjax(sbchImportInquiryService.deleteSbchImportInquiryByIds(map.get("ids").toString()));
        }catch (CustomBusinessException e){
            e.printStackTrace();
            return AjaxResult.error(e.getMsg());
        }catch (Exception e){
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 设备进口策划进口调查详情
     * @author zq
     * @date 2022/12/5 20:05
     * @param id
     * @return com.hhwy.common.core.web.domain.AjaxResult
     */
    @GetMapping("/detail/{id}")
    @ResponseBody
    public AjaxResult detail(@PathVariable("id") Long id){
        SbchImportInquiry sbchImportInquiry = sbchImportInquiryService.selectSbchImportInquiryById(id);
        return AjaxResult.success(sbchImportInquiry);
    }

    /**
     * 设备进口策划进口调查详情 --国家详情，港口详情
     * @author zq
     * @date 2022/12/5 20:05
     * @param id
     * @return com.hhwy.common.core.web.domain.AjaxResult
     */
    @GetMapping("/detailList/{id}")
    @ResponseBody
    public AjaxResult detailList(@PathVariable("id") Long id){
        Map map  = sbchImportInquiryService.selectInquiryDetailList(id);
        return AjaxResult.success(map);
    }

    @GetMapping("/getList")
    @ResponseBody
    public AjaxResult getList(BigDecimal version){
        SbchImportInquiry sbchImportInquiry = sbchImportInquiryService.getList(version);
        return AjaxResult.success(sbchImportInquiry);
    }

    @PostMapping("/batchAdd")
    @ResponseBody
    public AjaxResult batchAdd(@Validated(ValidationGroups.Save.class) @RequestBody SbchImportInquiry sbchImportInquiry){
        try{
            sbchImportInquiryService.batchSave(sbchImportInquiry);
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
