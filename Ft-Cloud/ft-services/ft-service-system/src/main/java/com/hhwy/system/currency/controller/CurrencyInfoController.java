package com.hhwy.system.currency.controller;

import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.page.TableDataInfo;
import com.hhwy.common.security.annotation.PreAuthorize;

import com.hhwy.domain.base.system.currency.CurrencyInfo;
import com.hhwy.domain.base.system.periodCurrency.PeriodCurrency;
import com.hhwy.system.currency.service.ICurrencyInfoService;
import com.hhwy.utils.Constant;
import com.hhwy.utils.common.PmsConstant;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * infoController
 * 
 * @author lcf
 * @date 2022-11-24
 */
@RestController
@RequestMapping("/currency/info")
public class CurrencyInfoController extends BaseController {

    @Autowired
    private ICurrencyInfoService currencyInfoService;

    /**
     * 查询info列表
     */
    @PreAuthorize(hasPermi = "currency:info:list")
    @GetMapping("/list")
//    @CustomLogger(title = "币种-列表查询",businessType = CustomBusinessType.SELECT)
    public AjaxResult list(@Validated(ValidationGroups.Select.class) CurrencyInfo currencyInfo) {
        startPage();
        List<CurrencyInfo> list = currencyInfoService.selectCurrencyInfoList(currencyInfo);
        TableDataInfo dataTable = getDataTable(list);
        if(null==dataTable){
            return new AjaxResult(PmsConstant.WARN_CODE,"未查询到数据");
        }
        return AjaxResult.success(dataTable);
    }

    @PostMapping("/listNoPage")
//    @CustomLogger(title = "币种-列表查询(NoPage)",businessType = CustomBusinessType.SELECT)
    public AjaxResult listNoPage(@RequestBody CurrencyInfo currencyInfo){
        List<CurrencyInfo> list = currencyInfoService.selectCurrencyInfoList(currencyInfo);
        TableDataInfo dataTable = getDataTable(list);
        if(null==dataTable){
            return new AjaxResult(PmsConstant.WARN_CODE,"未查询到数据");
        }
        return AjaxResult.success(dataTable);
    }

    /**
     * 导出info列表
     */
    @PreAuthorize(hasPermi = "currency:info:export")
    @PostMapping("/export")
//    @CustomLogger(title = "币种-导出",businessType = CustomBusinessType.EXPORT)
    public void export(CurrencyInfo currencyInfo, HttpServletResponse response) throws Exception{
        List<CurrencyInfo> list = currencyInfoService.selectCurrencyInfoList(currencyInfo);
        ExcelUtils<CurrencyInfo> util = new ExcelUtils<CurrencyInfo>(CurrencyInfo.class);
        util.exportExcel(response,list, "币种");
    }

    /**
     * 新增保存info
     */
    @PreAuthorize(hasPermi = "currency:info:add")
    @PostMapping("/add")
//    @CustomLogger(title = "币种-新增",businessType = CustomBusinessType.SAVE)
    public AjaxResult addSave(@Validated(ValidationGroups.Save.class) @RequestBody CurrencyInfo currencyInfo) {
        int i = currencyInfoService.insertCurrencyInfo(currencyInfo);
        if(i==-1){
            return new AjaxResult(Constant.WARN_CODE,"币种编码已存在");
        }
        return toAjax(i);
    }

    @PostMapping("/addPage")
//    @CustomLogger(title = "币种-新增页面",businessType = CustomBusinessType.SAVE)
    public AjaxResult addPage(){
        //查询库中已有顺序号码+1
        int i=currencyInfoService.selectMaxSort();
        return AjaxResult.success(i+1);
    }

    /**
     * 修改保存info
     */
    @PreAuthorize(hasPermi = "currency:info:edit")
    @PostMapping("/edit")
//    @CustomLogger(title = "币种-保存",businessType = CustomBusinessType.UPDATE)
    public AjaxResult editSave(@Validated(ValidationGroups.Update.class) @RequestBody CurrencyInfo currencyInfo) {
        int i = currencyInfoService.updateCurrencyInfo(currencyInfo);
        if(i==-1){
            return new AjaxResult(Constant.WARN_CODE,"币种编码已存在");
        }
        return toAjax(i);
    }

    /**
     * 修改回显
     *
     * @param id
     * @return
     */
    @GetMapping("/editPageInfo/{id}")
    @PreAuthorize(hasPermi = "currency:info:editPageInfo")
//    @CustomLogger(title = "币种-保存页面",businessType = CustomBusinessType.OTHER)
    public AjaxResult editPageInfo(@PathVariable(value = "id")Long id){
        CurrencyInfo info = currencyInfoService.selectCurrencyInfoById(id);
        return AjaxResult.success(info);
    }

    /**
     * 删除info
     */
    @PreAuthorize(hasPermi = "currency:info:remove")
    @PostMapping( "/remove")
//    @CustomLogger(title = "币种-删除",businessType = CustomBusinessType.DELETE)
    public AjaxResult remove(@Validated(ValidationGroups.Delete.class) @RequestBody CurrencyInfo currencyInfo) {
        String ids = currencyInfo.getIds();
        return toAjax(currencyInfoService.deleteCurrencyInfoByIds(ids));
    }

    @PostMapping("/getList")
//    @CustomLogger(title = "币种-列表查询",businessType = CustomBusinessType.SELECT)
    public AjaxResult getCurrencyList(@RequestBody CurrencyInfo currencyInfo) {
        return AjaxResult.success(currencyInfoService.selectCurrencyInfoList(currencyInfo));
    }



    @PostMapping("/selectList")
//    @CustomLogger(title = "币种-列表查询",businessType = CustomBusinessType.SELECT)
    public List<CurrencyInfo> selectList(@RequestBody CurrencyInfo currencyInfo) {
        return currencyInfoService.selectCurrencyInfoList(currencyInfo);
    }

    /**
     * 导入
     *
     * @param file
     * @return
     */
    @PostMapping("/importData")
    @PreAuthorize(hasPermi = "currency:info:importData")
//    @CustomLogger(title = "币种-导入",businessType = CustomBusinessType.SELECT)
    public AjaxResult importData(MultipartFile file) {
        try {
            ExcelUtils<CurrencyInfo> util = new ExcelUtils<>(CurrencyInfo.class);
            List<CurrencyInfo> list = util.importExcel(file.getInputStream());
            //数据处理
            return currencyInfoService.importData(list);
        }catch (Exception e){
            throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error, "导入异常");
        }
    }

    /**
     * 查币种对应的汇率
     *
     * @param periodCurrency
     * @return
     */
    @PostMapping("/selectPeriodCurrency")
//    @CustomLogger(title = "币种-查币种对应的汇率",businessType = CustomBusinessType.SELECT)
    public AjaxResult selectPeriodCurrency(@RequestBody PeriodCurrency periodCurrency){
        List<PeriodCurrency> list = currencyInfoService.selectPeriodCurrency(periodCurrency);
        return AjaxResult.success(list);
    }
}
