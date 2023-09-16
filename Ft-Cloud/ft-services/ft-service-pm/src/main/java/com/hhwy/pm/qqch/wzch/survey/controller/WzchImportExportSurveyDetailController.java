package com.hhwy.pm.qqch.wzch.survey.controller;

import com.hhwy.common.core.exception.BaseException;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qqch.wzch.survey.domain.WzchImportExportSurvey;
import com.hhwy.pm.qqch.wzch.survey.domain.WzchImportExportSurveyCountry;
import com.hhwy.pm.qqch.wzch.survey.domain.WzchImportExportSurveyCustoms;
import com.hhwy.pm.qqch.wzch.survey.service.IWzchImportExportSurveyCountryService;
import com.hhwy.pm.qqch.wzch.survey.service.IWzchImportExportSurveyCustomsService;
import com.hhwy.pm.qqch.wzch.survey.service.IWzchImportExportSurveyService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 进出口调查Controller
 * 
 * @author mls
 * @date 2022-12-05
 */
@RestController
@RequestMapping("/wzch/surveyDetail")
public class WzchImportExportSurveyDetailController extends BaseController {

    @Resource
    private IWzchImportExportSurveyService wzchImportExportSurveyService;
    @Resource
    private IWzchImportExportSurveyCountryService wzchImportExportSurveyCountryService;
    @Resource
    private IWzchImportExportSurveyCustomsService wzchImportExportSurveyCustomsService;


    /**
     * 导出进出口调查-保存
     */
//    @PreAuthorize(hasPermi ="wzch:surveyDetail:save")
//    @CustomLogger(title = "国别-列表查询",businessType = CustomBusinessType.EXPORT)
    @PostMapping("/save")
    public AjaxResult save(@RequestBody WzchImportExportSurvey wzchImportExportSurvey) {
        try{
            wzchImportExportSurveyService.save(wzchImportExportSurvey);
            return new AjaxResult(200,"保存成功",wzchImportExportSurvey.getId());
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException(e.getMessage());
        }

    }


    /**
     * 导出进出口调查国家详情-导出
     */
   //  @PreAuthorize(hasPermi ="wzch:surveyDetail:export")
//    @CustomLogger(title = "国家详情-导出",businessType = CustomBusinessType.EXPORT)
    @PostMapping("/country/export")
    public void countryExport(@RequestBody List<WzchImportExportSurveyCountry> list, HttpServletResponse response) {
        try{
            ExcelUtils<WzchImportExportSurveyCountry> util = new ExcelUtils<WzchImportExportSurveyCountry>(WzchImportExportSurveyCountry.class);
            util.exportExcel(response,list, "国家详情");
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException("导出异常");
        }

    }

    /**
     * 导出进出口调查国家详情-导入
     */
   //  @PreAuthorize(hasPermi ="wzch:surveyDetail:import")
//    @CustomLogger(title = "国家详情-导入",businessType = CustomBusinessType.IMPORT)
    @PostMapping("/country/import")
    public AjaxResult countryImport( MultipartFile file) {
        try{
            List<WzchImportExportSurveyCountry> list = wzchImportExportSurveyCountryService.importCountry(file);
            return new AjaxResult(200,"导入成功",list);
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException("导入异常");
        }

    }

    /**
     * 导出进出口调查海关详情-导出
     * @param list
     * @param response
     */
   //  @PreAuthorize(hasPermi ="wzch:surveyDetail:export")
//    @CustomLogger(title = "海关详情-导出",businessType = CustomBusinessType.EXPORT)
    @PostMapping("/customs/export")
    public void customsExport(@RequestBody List<WzchImportExportSurveyCustoms> list, HttpServletResponse response) {
        try{
            ExcelUtils<WzchImportExportSurveyCustoms> util = new ExcelUtils<WzchImportExportSurveyCustoms>(WzchImportExportSurveyCustoms.class);
            util.exportExcel(response,list, "海关详情");
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException("导出异常");
        }
    }

    /**
     * 导出进出口调查海关详情-导入
     * @param file
     */
  //   @PreAuthorize(hasPermi ="wzch:surveyDetail:import")
//    @CustomLogger(title = "海关详情-导入",businessType = CustomBusinessType.IMPORT)
    @PostMapping("/customs/import")
    public AjaxResult customsImport(MultipartFile file) {
        try{
            List<WzchImportExportSurveyCustoms> list = wzchImportExportSurveyCustomsService.importCustoms(file);
            return new AjaxResult(200,"导入成功",list);
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException("导入异常");
        }
    }

    /**
     * 修改保存进出口调查
     */
  //   @PreAuthorize(hasPermi ="wzch:surveyDetail:edit")
//    @CustomLogger(title = "国别-列表查询",businessType = CustomBusinessType.SELECT)
    @PostMapping("/edit")
    public AjaxResult editSave(WzchImportExportSurvey wzchImportExportSurvey) {
        try{
            WzchImportExportSurvey survey = wzchImportExportSurveyService.edit(wzchImportExportSurvey);
            return new AjaxResult(200,"成功",survey);
        }catch (BaseException b){
            b.printStackTrace();
            throw new BaseException(b.getDefaultMessage());
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException("编辑异常");
        }
        
    }

    /**
     * 修改保存进出口调查
     */
 //    @PreAuthorize(hasPermi ="wzch:surveyDetail:detail")
//    @CustomLogger(title = "国别-列表查询",businessType = CustomBusinessType.SELECT)
    @PostMapping("/detail")
    public AjaxResult detail(WzchImportExportSurvey wzchImportExportSurvey) {
        try{
            WzchImportExportSurvey survey = wzchImportExportSurveyService.edit(wzchImportExportSurvey);
            return new AjaxResult(200,"成功",survey);
        }catch (BaseException b){
            b.printStackTrace();
            throw new BaseException(b.getDefaultMessage());
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException("编辑异常");
        }

    }

    /**
     * 删除进出口调查
     */
 //    @PreAuthorize(hasPermi ="wzch:surveyDetail:remove")
//    @CustomLogger(title = "国别-列表查询",businessType = CustomBusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(Long id) {
        return toAjax(wzchImportExportSurveyService.deleteWzchImportExportSurveyById(id));
    }
}
