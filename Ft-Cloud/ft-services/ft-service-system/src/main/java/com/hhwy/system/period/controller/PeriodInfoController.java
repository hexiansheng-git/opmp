package com.hhwy.system.period.controller;

import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.page.TableDataInfo;
import com.hhwy.common.security.annotation.PreAuthorize;

import com.hhwy.domain.base.system.period.PeriodInfo;
import com.hhwy.domain.base.system.period.vo.PeriodCurrencyInfoVo;
import com.hhwy.domain.base.system.periodCurrency.PeriodCurrency;
import com.hhwy.system.period.service.IPeriodInfoService;
import com.hhwy.utils.Constant;
import com.hhwy.utils.common.PmsConstant;
import com.hhwy.utils.core.DateUtil;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;


/**
 * infoController
 * 
 * @author lcf
 * @date 2022-11-24
 */
@RestController
@RequestMapping("/period/info")
public class PeriodInfoController extends BaseController {

    @Autowired
    private IPeriodInfoService periodInfoService;


    /**
     * 查询info列表
     */
    @PreAuthorize(hasPermi = "period:info:list")
    @GetMapping("/list")
//    @CustomLogger(title = "期次-列表查询",businessType = CustomBusinessType.SELECT)
    public AjaxResult list(@Validated(ValidationGroups.Select.class)PeriodInfo periodInfo) {
        startPage();
        List<PeriodInfo> list = periodInfoService.selectPeriodInfoList(periodInfo);
        TableDataInfo dataTable = getDataTable(list);
        if(null==dataTable){
            return new AjaxResult(PmsConstant.WARN_CODE,"未查询到数据");
        }
        return AjaxResult.success(dataTable);
    }

    /**
     * 导出info列表
     */
    @PreAuthorize(hasPermi = "period:info:export")
    @PostMapping("/export")
//    @CustomLogger(title = "期次-导出",businessType = CustomBusinessType.EXPORT)
    public void export(@RequestBody PeriodInfo periodInfo, HttpServletResponse response) throws Exception {
        List<PeriodInfo> list = periodInfoService.selectPeriodInfoList(periodInfo);
        ExcelUtils<PeriodInfo> util = new ExcelUtils<PeriodInfo>(PeriodInfo.class);
        util.exportExcel(response,list, "期次");
    }
    

    /**
     * 新增保存info
     */
    @PreAuthorize(hasPermi = "period:info:add")
    @PostMapping("/add")
//    @CustomLogger(title = "期次-新增",businessType = CustomBusinessType.SAVE)
    public AjaxResult addSave(@Validated(ValidationGroups.Save.class) @RequestBody PeriodInfo periodInfo) {
        int i = periodInfoService.insertPeriodInfo(periodInfo);
        if(i==-1){
            return new AjaxResult(Constant.WARN_CODE,"期次编码重复");
        }
        return toAjax(i);
    }

    /**
     * 新增保存info
     */
    @PreAuthorize(hasPermi = "period:info:addPage")
    @PostMapping("/addPage")
//    @CustomLogger(title = "期次-新增页面",businessType = CustomBusinessType.SAVE)
    public AjaxResult addPage(){
        //开始日期
        String firstToMonth = DateUtil.getFirstToPattern("yyyy-MM-dd");
        //结束日期
        String lastDayOfMonth = DateUtil.getLastDayOfMonth();
        //期号
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        String num=year+""+String.format("%02d",month+1);
        PeriodInfo periodInfo=new PeriodInfo();
        periodInfo.setPeriodCode(num);
        periodInfo.setPtVar1(firstToMonth);
        periodInfo.setPtVar2(lastDayOfMonth);
        return AjaxResult.success(periodInfo);
    }


    /**
     * 修改保存info
     */
    @PreAuthorize(hasPermi = "period:info:edit")
    @PostMapping("/edit")
//    @CustomLogger(title = "期次-修改",businessType = CustomBusinessType.UPDATE)
    public AjaxResult editSave(@Validated(ValidationGroups.Update.class) @RequestBody PeriodInfo periodInfo) {
        return toAjax(periodInfoService.updatePeriodInfo(periodInfo));
    }


    /**
     * 修改页面回显
     */
    @PreAuthorize(hasPermi = "period:info:editPage")
    @PostMapping("/editPage/{id}")
//    @CustomLogger(title = "期次-修改",businessType = CustomBusinessType.UPDATE)
    public AjaxResult editPage(@PathVariable(value = "id") Long id) {
        return AjaxResult.success(periodInfoService.selectPeriodInfoById(id));
    }

    /**
     * 新增新增新增
     *
     * @return
     */
    @PreAuthorize(hasPermi = "period:info:newAdd")
    @GetMapping("/adminAdd")
//    @CustomLogger(title = "期次-修改需求后的新增",businessType = CustomBusinessType.UPDATE)
    public AjaxResult adminAdd() {
        int i=0;
        try{
            i=periodInfoService.newAddPeriodInfo();
        }catch (Exception e){
            e.printStackTrace();
        }
        return toAjax(i);
    }


    /**
     * 删除info
     */
    @PreAuthorize(hasPermi = "period:info:remove")
    @PostMapping( "/remove")
//    @CustomLogger(title = "期次-删除",businessType = CustomBusinessType.DELETE)
    public AjaxResult remove(@Validated(ValidationGroups.Delete.class) @RequestBody PeriodInfo periodInfo) {
        List<String> idsList = periodInfo.getIds();
        String ids = String.join(",", idsList);
        return toAjax(periodInfoService.deletePeriodInfoByIds(ids));
    }

    /**
     *根据日期获取币种、汇率、期次
     *
     * @return
     */
    //@PreAuthorize(hasPermi = "period:info:selectPeriodByDate")
    @PostMapping( "/selectPeriodByDate")
//    @CustomLogger(title = "期次-删除",businessType = CustomBusinessType.OTHER)
    public AjaxResult selectPeriodByDate(@Validated(ValidationGroups.Other.class) @RequestBody PeriodInfo periodInfo){
        List<PeriodCurrencyInfoVo> list = periodInfoService.selectPeriodByDate(periodInfo);
        return AjaxResult.success(list);
    }

    @PostMapping( "/selectPeriodInfoByDate")
//    @CustomLogger(title = "根据时间查询期次信息",businessType = CustomBusinessType.OTHER)
    public AjaxResult selectPeriodInfoByDate(@RequestBody PeriodInfo periodInfo){
        PeriodInfo periodInfoByDate = periodInfoService.selectPeriodInfoByDate(periodInfo);
        return AjaxResult.success(periodInfoByDate);
    }

    @GetMapping( "/selectAllPeriodByYear")
    public AjaxResult selectAllPeriodByYear(String year){
        Map<String, BigDecimal> resMap = periodInfoService.selectAllPeriodByYear(year);
        return AjaxResult.success(resMap);
    }

    /**
     * to国欣
     *
     * @param periodInfo
     * @return
     */
    @PostMapping( "/selectPeriodByYear")
//    @CustomLogger(title = "根据年查询期次信息",businessType = CustomBusinessType.OTHER)
    public AjaxResult selectPeriodByYear(@RequestBody PeriodInfo periodInfo){
        List<PeriodCurrency> list = periodInfoService.selectPeriodByYear(periodInfo);
        List<Map> rstList=new ArrayList<>();
        for (int i = 0; i <list.size() ; i++) {
            Map map=new HashMap();
            map.put("rate",list.get(i).getRate());
            map.put("periodCode",list.get(i).getPeriodCode());
            map.put("periodId",list.get(i).getPeriodId());
            map.put("currencyCode",list.get(i).getCurrencyCode());
            rstList.add(map);
        }
        return AjaxResult.success(rstList);
    }

    /**
     * to张倩
     * 根据日期批量查询期次信息
     *
     * @return
     */
    @PostMapping("/selectBathByDate")
//    @CustomLogger(title = "根据日期批量查询期次信息",businessType = CustomBusinessType.OTHER)
    public AjaxResult selectBathByDate(@RequestBody List<String> list){
        List<PeriodInfo> rstList = periodInfoService.selectBathByDate(list);
        return AjaxResult.success(rstList);
    }

    /**
     * 批次批量新增
     *
     * @param list
     * @return
     */
    @PostMapping("/batchInsert")
//    @CustomLogger(title = "批量新增期次信息",businessType = CustomBusinessType.OTHER)
    public AjaxResult batchInsert(@RequestBody List<PeriodInfo> list){
        int i=periodInfoService.batchInsert(list);
        return AjaxResult.success(i);
    }
}
