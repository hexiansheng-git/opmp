package com.hhwy.system.market.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.SecurityUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.page.TableDataInfo;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.domain.base.system.marketSurvey.MarketSurvey;
import com.hhwy.system.market.service.IMarketSurveyService;
import com.hhwy.utils.common.PmsConstant;
import com.hhwy.utils.exception.CustomBusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 市场调查Controller
 * 
 * @author lcf
 * @date 2022-11-30
 */
@RestController
@RequestMapping("/market/survey")
public class MarketSurveyController extends BaseController {

    @Autowired
    private IMarketSurveyService marketSurveyService;

    /**
     * 查询市场调查列表
     */
    @PreAuthorize(hasPermi ="market:survey:list")
    @PostMapping("/list")
    public AjaxResult list(@RequestBody MarketSurvey marketSurvey) {
        startPage(marketSurvey.getPageNum(),marketSurvey.getPageSize());
        List<MarketSurvey> list = marketSurveyService.selectMarketSurveyList(marketSurvey);
        TableDataInfo dataTable = getDataTable(list);
        if(null==dataTable){
            return new AjaxResult(PmsConstant.WARN_CODE,"未查询到数据");
        }
        return AjaxResult.success(dataTable);
    }

    /**
     * 导出市场调查列表
     */
    @PreAuthorize(hasPermi ="market:survey:export")
    @PostMapping("/export")
    public void export(@RequestBody MarketSurvey marketSurvey, HttpServletResponse response)throws Exception {
        List<MarketSurvey> list = marketSurveyService.selectMarketSurveyList(marketSurvey);
        ExcelUtils<MarketSurvey> util = new ExcelUtils<MarketSurvey>(MarketSurvey.class);
        util.exportExcel(response,list, "survey");
    }


    /**
     * 新增保存市场调查
     */
    @PreAuthorize(hasPermi ="market:survey:add")
    @PostMapping("/add")
    public AjaxResult addSave(@RequestBody MarketSurvey marketSurvey) {
        return toAjax(marketSurveyService.insertMarketSurvey(marketSurvey));
    }


    @PostMapping("/addPage")
    @PreAuthorize(hasPermi ="market:survey:addPage")
    public AjaxResult addPage(){
        MarketSurvey marketSurvey=new MarketSurvey();
        marketSurvey.setCreateTime(DateUtils.getNowDate());
        marketSurvey.setCreateUser(SecurityUtils.getUserId().toString());
        marketSurvey.setPtVar1(SecurityUtils.getUserName());
        return AjaxResult.success(marketSurvey);
    }

    /**
     * 修改保存市场调查
     */
    @PreAuthorize(hasPermi ="market:survey:edit")
    @PostMapping("/edit")
    public AjaxResult editSave(@RequestBody MarketSurvey marketSurvey) {
        return toAjax(marketSurveyService.updateMarketSurvey(marketSurvey));
    }

    /**
     * 删除市场调查
     */
    @PreAuthorize(hasPermi ="market:survey:remove")
    @PostMapping( "/remove")
    public AjaxResult remove(@RequestBody List<String> ids) {
        String strIds = StringUtils.join(ids.toArray(), ",");
        return toAjax(marketSurveyService.deleteMarketSurveyByIds(strIds));
    }

    /**
     * 查询市场调查列表
     */
//    @PreAuthorize(hasPermi ="market:survey:selectMarketInfo")
    @PostMapping("/selectMarketInfo")
    public AjaxResult selectMarketInfo(@RequestBody MarketSurvey marketSurvey) {
        startPage();
        List<MarketSurvey> list = marketSurveyService.selectMarketInfo(marketSurvey);
        TableDataInfo dataTable = getDataTable(list);
        if(null==dataTable){
            return new AjaxResult(PmsConstant.WARN_CODE,"未查询到数据");
        }
        return AjaxResult.success(dataTable);
    }

    /**
     * 根据id查详情
     *
     * @param id
     * @return
     */
    @GetMapping("/selectInfoById/{id}")
    public AjaxResult selectInfoById(@PathVariable(value = "id") Long id){
        MarketSurvey marketSurvey = marketSurveyService.selectMarketSurveyById(id);
        return AjaxResult.success(marketSurvey);
    }

    /**
     * 数据导入
     *
     * @param file
     * @return
     */
    @PostMapping("/importData")
    @PreAuthorize(hasPermi ="market:survey:importData")
    public AjaxResult importData(MultipartFile file){
        try {
            ExcelUtils<MarketSurvey> util = new ExcelUtils<>(MarketSurvey.class);
            List<MarketSurvey> list = util.importExcel(file.getInputStream());
            //数据处理
            return marketSurveyService.importData(list);
        }catch (Exception e){
            throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error, "导入异常");
        }
    }
}
