package com.hhwy.pm.qqch.wzch.survey.controller;

import com.hhwy.common.core.exception.BaseException;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.page.TableDataInfo;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.wzch.survey.domain.WzchImportExportSurvey;
import com.hhwy.pm.qqch.wzch.survey.service.IWzchImportExportSurveyService;
import com.hhwy.pm.qqch.wzch.survey.vo.WzchImportExportSurveyAddResponse;
import com.hhwy.utils.common.PmsConstant;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 进出口调查Controller
 * 
 * @author mls
 * @date 2022-12-05
 */
@RestController
@RequestMapping("/wzch/survey")
public class WzchImportExportSurveyController extends BaseController {

    @Autowired
    private IWzchImportExportSurveyService wzchImportExportSurveyService;


    /**
     * 查询进出口调查列表
     */
//    @PreAuthorize(hasPermi ="wzch:survey:list")
    @PostMapping("/list")
//    @CustomLogger(title = "口调查列表-列表查询",businessType = CustomBusinessType.SELECT)
    public AjaxResult list(@RequestBody WzchImportExportSurvey wzchImportExportSurvey) {
        startPage();
        List<WzchImportExportSurvey> list = wzchImportExportSurveyService.selectWzchImportExportSurveyList(wzchImportExportSurvey);
        TableDataInfo dataTable = getDataTable(list);
        if(null==dataTable){
            return new AjaxResult(PmsConstant.WARN_CODE,"未查询到数据");
        }
        return  new AjaxResult(200,"成功",dataTable);
    }


//    @PreAuthorize(hasPermi ="wzch:survey:add")
    @GetMapping("/add")
//    @CustomLogger(title = "口调查列表-新增",businessType = CustomBusinessType.SELECT)
    public AjaxResult add() {
        WzchImportExportSurveyAddResponse wzchImportExportSurveyAddResponse = new WzchImportExportSurveyAddResponse();
        wzchImportExportSurveyAddResponse.setId(IdWorker.createId());
        wzchImportExportSurveyAddResponse.setCreateUserName(SecurityUtils.getSysUser().getNickName());
        wzchImportExportSurveyAddResponse.setCreateTime(DateUtils.getNowDate());
        return  new AjaxResult(200,"成功",wzchImportExportSurveyAddResponse);
    }

    /**
     * 导出进出口调查列表
     */
//    @CustomLogger(title = "进出口调查-导出",businessType = CustomBusinessType.EXPORT)
    @PostMapping("/export")
    public void export(@RequestBody WzchImportExportSurvey wzchImportExportSurvey, HttpServletResponse response) {
        try{
            List<WzchImportExportSurvey> list = wzchImportExportSurveyService.selectWzchImportExportSurveyList(wzchImportExportSurvey);
            ExcelUtils<WzchImportExportSurvey> util = new ExcelUtils<WzchImportExportSurvey>(WzchImportExportSurvey.class);
            util.exportExcel(response,list, "进出口调查");
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException("导出异常");
        }

    }

    /**
     * 编辑保存进出口调查
     */
//    @PreAuthorize(hasPermi ="wzch:survey:edit")
//    @CustomLogger(title = "进出口调查-编辑",businessType = CustomBusinessType.SELECT)
    @PostMapping("/edit")
    public AjaxResult edit(@RequestBody WzchImportExportSurvey wzchImportExportSurvey) {
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
     * 进出口调查详情
     */
//    @PreAuthorize(hasPermi ="wzch:survey:detail")
//    @CustomLogger(title = "进出口调查-详情",businessType = CustomBusinessType.SELECT)
    @PostMapping("/detail")
    public AjaxResult detail(@RequestBody WzchImportExportSurvey wzchImportExportSurvey) {
        try{
            WzchImportExportSurvey survey = wzchImportExportSurveyService.edit(wzchImportExportSurvey);
            return new AjaxResult(200,"成功",survey);
        }catch (BaseException b){
            b.printStackTrace();
            throw new BaseException("查询失败");
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException("查询异常");
        }

    }

    /**
     * 删除进出口调查
     */
//    @PreAuthorize(hasPermi ="wzch:survey:remove")
//    @CustomLogger(title = "进出口调查-删除",businessType = CustomBusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(@RequestBody WzchImportExportSurvey wzchImportExportSurvey) {
          if(wzchImportExportSurvey==null || wzchImportExportSurvey.getId()==null){
              throw new BaseException("入参缺失");
          }
        return toAjax(wzchImportExportSurveyService.deleteWzchImportExportSurveyById(wzchImportExportSurvey.getId()));
    }
}
