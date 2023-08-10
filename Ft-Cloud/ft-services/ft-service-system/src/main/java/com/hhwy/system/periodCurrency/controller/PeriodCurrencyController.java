package com.hhwy.system.periodCurrency.controller;


import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.page.TableDataInfo;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.domain.base.system.period.PeriodInfo;
import com.hhwy.domain.base.system.periodCurrency.PeriodCurrency;
import com.hhwy.system.periodCurrency.service.IPeriodCurrencyService;
import com.hhwy.utils.Constant;
import com.hhwy.utils.common.PmsConstant;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 期次汇率Controller
 *
 * @author lcf
 * @date 2022-11-24
 */
@RestController
@RequestMapping("/periodCurrency/periodCurrency")
public class PeriodCurrencyController extends BaseController {

    @Autowired
    private IPeriodCurrencyService periodCurrencyService;


    /**
     * 查询期次汇率列表
     */
    @PreAuthorize(hasPermi = "periodCurrency:periodCurrency:list")
    @GetMapping("/list")
//    @CustomLogger(title = "汇率-列表查询",businessType = CustomBusinessType.SELECT)
    public AjaxResult list(@Validated(ValidationGroups.Select.class)PeriodCurrency periodCurrency) {
        startPage();
        List<PeriodCurrency> list = periodCurrencyService.selectPeriodCurrencyList(periodCurrency);
        TableDataInfo dataTable = getDataTable(list);
        if(null==dataTable){
            return new AjaxResult(PmsConstant.WARN_CODE,"未查询到数据");
        }
        return AjaxResult.success(dataTable);
    }

    /**
     * 导出期次汇率列表
     */
    @PreAuthorize(hasPermi = "periodCurrency:periodCurrency:export")
    @PostMapping("/export")
//    @CustomLogger(title = "汇率-导出",businessType = CustomBusinessType.EXPORT)
    public void export(PeriodCurrency periodCurrency, HttpServletResponse response) throws Exception {
        List<PeriodCurrency> list = periodCurrencyService.selectPeriodCurrencyList(periodCurrency);
        ExcelUtils<PeriodCurrency> util = new ExcelUtils<PeriodCurrency>(PeriodCurrency.class);
        util.exportExcel(response,list, "periodCurrency");
    }

    /**
     * 新增保存期次汇率
     */
    @PreAuthorize(hasPermi = "periodCurrency:periodCurrency:add")
    @PostMapping("/add")
//    @CustomLogger(title = "汇率-新增",businessType = CustomBusinessType.SAVE)
    public AjaxResult addSave(@Validated(ValidationGroups.Save.class) @RequestBody PeriodCurrency periodCurrency) {
        return toAjax(periodCurrencyService.insertPeriodCurrency(periodCurrency));
    }


    @PostMapping("/insertBathPeriodCurrency")
//    @CustomLogger(title = "汇率-(insertBathPeriodCurrency)新增",businessType = CustomBusinessType.SAVE)
    public AjaxResult insertBathPeriodCurrency(@RequestBody PeriodInfo periodInfo){
        int i = periodCurrencyService.insertBathPeriodCurrency(periodInfo);
        if(i==-1){
            return new AjaxResult(PmsConstant.WARN_CODE,"请填写汇率");
        }
        return toAjax(i);
    }

    /**
     * 修改保存期次汇率
     */
    @PreAuthorize(hasPermi = "periodCurrency:periodCurrency:edit")
    @PostMapping("/edit")
//    @CustomLogger(title = "汇率-修改",businessType = CustomBusinessType.UPDATE)
    public AjaxResult editSave(PeriodCurrency periodCurrency) {
        return toAjax(periodCurrencyService.updatePeriodCurrency(periodCurrency));
    }

    /**
     * 修改页面回显
     *
     * @param id
     * @return
     */
    @PreAuthorize(hasPermi = "periodCurrency:periodCurrency:editPageInfo")
    @PostMapping("/editPageInfo/{id}")
//    @CustomLogger(title = "汇率-修改",businessType = CustomBusinessType.UPDATE)
    public AjaxResult editPageInfo(@PathVariable(value = "id") Long id){
        return AjaxResult.success(periodCurrencyService.editPageInfo(id));
    }


    /**
     * 删除期次汇率
     */
    @PreAuthorize(hasPermi = "periodCurrency:periodCurrency:remove")
    @PostMapping( "/remove")
//    @CustomLogger(title = "汇率-删除",businessType = CustomBusinessType.DELETE)
    public AjaxResult remove(PeriodCurrency periodCurrency) {
        String ids = periodCurrency.getIds();
        if(StringUtils.isBlank(ids)){
            return new AjaxResult(Constant.WARN_CODE,"ids不能为空");
        }
        return toAjax(periodCurrencyService.deletePeriodCurrencyByIds(ids));
    }


    @PostMapping("/selectRatePeriodByCodeAndCurrent")
//    @CustomLogger(title = "根据期次和币种回显汇率", businessType = CustomBusinessType.OTHER)
    public AjaxResult selectRatePeriodByCodeAndCurrent(@Validated(ValidationGroups.Other.class) @RequestBody Map<String, String> map) {
        List<PeriodCurrency> periodCurrency = periodCurrencyService.selectRatePeriodByCodeAndCurrent(map);
        if (periodCurrency != null && periodCurrency.size() > 0) {
            // 这里先这样写 目前前端只会查询一个币种的指定期次的汇率 如果某天需要查询多个的话 就直接返回list
            return AjaxResult.success(periodCurrency.get(0));
        } else {
            return AjaxResult.error("未查询到数据");
        }
    }


    @PostMapping("/selectListRatePeriodByCodeAndCurrent")
//    @CustomLogger(title = "根据期次和币种回显汇率", businessType = CustomBusinessType.OTHER)
    public AjaxResult selectListRatePeriodByCodeAndCurrent(@Validated(ValidationGroups.Other.class) @RequestBody Map<String, String> map) {
        List<PeriodCurrency> periodCurrency = periodCurrencyService.selectRatePeriodByCodeAndCurrent(map);
        if (periodCurrency != null && periodCurrency.size() > 0) {
            return AjaxResult.success(periodCurrency);
        } else {
            return AjaxResult.error("未查询到数据");
        }
    }

    @PostMapping("/batchInsert")
//    @CustomLogger(title = "批量新增汇率", businessType = CustomBusinessType.OTHER)
    public AjaxResult batchInsert(@RequestBody List<PeriodCurrency> list){
        return AjaxResult.success(periodCurrencyService.batchInsert(list));
    }

}
