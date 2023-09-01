package com.hhwy.system.country.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.utils.http.HttpUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.page.TableDataInfo;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.domain.base.system.SysTreeUtil;
import com.hhwy.domain.base.system.country.CountryInfo;
import com.hhwy.domain.base.system.currency.CurrencyInfo;
import com.hhwy.system.country.service.ICountryInfoService;
import com.hhwy.system.currency.service.ICurrencyInfoService;
import com.hhwy.utils.common.PmsConstant;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.redisUtil.RedisUtils;

import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 国别Controller
 * 
 * @author jzq
 * @date 2022-11-01
 */
@RestController
@RequestMapping("/country/info")
public class CountryInfoController extends BaseController {
    
    @Autowired
    private ICountryInfoService countryInfoService;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private ICurrencyInfoService currencyInfoService;

    /**
     * 查询国别列表
     */
//    @PreAuthorize(hasPermi ="country:info:list")
    @GetMapping("/list")
//    @CustomLogger(title = "国别-列表查询",businessType = CustomBusinessType.SELECT)
    public AjaxResult list(@Validated(ValidationGroups.Select.class) CountryInfo countryInfo) {
        //分页
        startPage();
        List<CountryInfo> list = countryInfoService.selectCountryInfoList(countryInfo);
        TableDataInfo dataTable = getDataTable(list);
        if(null==dataTable){
            return new AjaxResult(PmsConstant.WARN_CODE,"未查询到数据");
        }
        return AjaxResult.success(dataTable);
    }

    //@PreAuthorize(hasPermi = "qqchModuleConfirmCase:list")
    //    @GetMapping("/list")
    //    public AjaxResult getQqchModuleConfirmCaseList(@Validated(ValidationGroups.Select.class) QqchModuleConfirmCase qqchModuleConfirmCaseParam) {
    //        startPage();
    //        List<QqchModuleConfirmCase> qqchModuleConfirmCaseList = qqchModuleConfirmCaseService.getQqchModuleConfirmCaseList(qqchModuleConfirmCaseParam);
    //        return getDataTableAjaxResult(qqchModuleConfirmCaseList);
    //    }



    /**
     * 导出国别列表
     */
    @PreAuthorize(hasPermi ="country:info:export")
    @PostMapping("/export")
//    @CustomLogger(title = "国别-导出",businessType = CustomBusinessType.EXPORT)
    public void export(@RequestBody CountryInfo countryInfo, HttpServletResponse response) {
        try {
            List<CountryInfo> list = countryInfoService.selectCountryInfoList(countryInfo);
            ExcelUtils<CountryInfo> util = new ExcelUtils<CountryInfo>(CountryInfo.class);
            util.exportExcel(response, list, "info");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    /**
     * 新增保存国别
     */
    @PreAuthorize(hasPermi ="country:info:add")
    @PostMapping("/add")
//    @CustomLogger(title = "国别-保存",businessType = CustomBusinessType.SAVE)
    public AjaxResult addSave(@Validated(ValidationGroups.Save.class) @RequestBody CountryInfo countryInfo) {
        int i = countryInfoService.insertCountryInfo(countryInfo);
        if(i==-1){
            return AjaxResult.error("国别不能重复");
        }
        return toAjax(i);
    }
    
    /**
     * 修改保存国别
     */
    @PreAuthorize(hasPermi ="country:info:edit")
    @PostMapping("/edit")
//    @CustomLogger(title = "国别-编辑",businessType = CustomBusinessType.UPDATE)
    public AjaxResult editSave(@Validated(ValidationGroups.Update.class) @RequestBody CountryInfo countryInfo) {
        return toAjax(countryInfoService.updateCountryInfo(countryInfo));
    }

    /**
     * 删除国别
     */
    @PreAuthorize(hasPermi ="country:info:remove")
    @PostMapping( "/remove")
//    @CustomLogger(title = "国别-删除",businessType = CustomBusinessType.DELETE)
    public AjaxResult remove(@NotBlank(message = "ids不能为空") @RequestBody List<String> ids) {
        String strIds = StringUtils.join(ids.toArray(), ",");
        return toAjax(countryInfoService.deleteCountryInfoByIds(strIds));
    }


    /**
     * 获取国家信息
     *
     * @return
     */
    @GetMapping("/test2")
    public AjaxResult getCountryInfo(){
        Integer t=0;
        for (int i = 0; i < 2; i++) {
            t=t+1;
            String  param="{ \"ESB\": { \"DATA\": { \"DATAINFOS\": { \"PUUID\": \"1\", \"DATAINFO\": [ { \"zgbnum\": \"\", \"LASTMODIFYRECORDTIME\": \"~\" } ] }, \"SPLITPAGE\": { \"CURRENTPAGE\": \""+t+"\", \"COUNTPERPAGE\": \"200\" } } } }";
            String url="https://esb.cfhec.net/env-101/por-902/mdm/route/esbmule/services/query/MDM_Q_SJFC_GJDQ";
            Map<String, String> headerMap=new HashMap<>();
            headerMap.put("apikey","rPFLbaT5mWodSwjulaYENn8kMigDvyzj");
            String s = HttpUtils.sendPost(url, param, headerMap);
            JSONObject jsonObject = JSONObject.parseObject(s);
            JSONObject object = JSONObject.parseObject(jsonObject.get("ESB").toString());
            JSONObject data = JSONObject.parseObject(object.get("DATA").toString());
            JSONObject das = JSONObject.parseObject(data.get("DATAINFOS").toString());
            JSONArray datainfo = JSONObject.parseArray(das.get("DATAINFO").toString());
            for (int j = 0; j <datainfo.size() ;j++) {
                JSONObject o = (JSONObject)datainfo.get(j);
                String code = o.get("CODE").toString();
                redisUtils.hPut("baishanyunCountryInfo",code, JSONObject.toJSONString(o));
            }
        }
        return AjaxResult.success();
    }

    /**
     * 获取币种信息
     *
     * @return
     */
    @PostMapping("/test3")
    public AjaxResult getCuntry(){
//        String url="https://esb.cfhec.net/env-101/por-902/mdm/route/esbmule/services/query/MDM_Q_SJFC_BZ";
//        Map<String, String> headerMap=new HashMap<>();
//        headerMap.put("apikey","dWimEXY79qmkhj3lHJpF2a50oAjWYeUk");
//        String s = HttpUtils.sendPost(url, null, headerMap);
//        System.out.println(s);
        int count = currencyInfoService.selectMaxSort();
        Integer t=0;
        List<CurrencyInfo> rstList=new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            t=t+1;
            String  param="{ \"ESB\": { \"DATA\": { \"DATAINFOS\": { \"PUUID\": \"1\", \"DATAINFO\": [ { \"zgbnum\": \"\", \"LASTMODIFYRECORDTIME\": \"~\" } ] }, \"SPLITPAGE\": { \"CURRENTPAGE\": \""+t+"\", \"COUNTPERPAGE\": \"200\" } } } }";
            String url="https://esb.cfhec.net/env-101/por-902/mdm/route/esbmule/services/query/MDM_Q_SJFC_BZ";
            Map<String, String> headerMap=new HashMap<>();
            headerMap.put("apikey","dWimEXY79qmkhj3lHJpF2a50oAjWYeUk");
            String s = HttpUtils.sendPost(url, param, headerMap);
            JSONObject jsonObject = JSONObject.parseObject(s);
            JSONObject object = JSONObject.parseObject(jsonObject.get("ESB").toString());
            JSONObject data = JSONObject.parseObject(object.get("DATA").toString());
            JSONObject das = JSONObject.parseObject(data.get("DATAINFOS").toString());
            JSONArray datainfo = JSONObject.parseArray(das.get("DATAINFO").toString());
            for (int j = 0; j <datainfo.size() ;j++) {
                CurrencyInfo info=new CurrencyInfo();
                JSONObject o = (JSONObject)datainfo.get(j);
                String code = o.get("zcurrencyalphabet").toString();
                String name = o.get("zcurrencyname").toString();
                info.setId(IdWorker.createId());
                info.setCreateTime(DateUtils.getNowDate());
                info.setCurrencyName(name);
                info.setCurrencyCode(code);
                info.setSort(count+1l+j);
                rstList.add(info);
                redisUtils.hPut("baishanyunCurrencyInfo",code, JSONObject.toJSONString(o));
            }
        }
        //批量入库
        currencyInfoService.batchInsert(rstList);
        return AjaxResult.success();
    }

//    /**
//     * 国家下的项目
//     *
//     * @return
//     */
//    @PostMapping("/countryProjectCascade")
////    @CustomLogger(title = "级联-国家下的项目",businessType = CustomBusinessType.SELECT)
//    public AjaxResult countryProjectCascade(CountryInfo countryInfo){
//        List<SysTreeUtil> list = countryInfoService.countryProjectCascade(countryInfo);
//        return AjaxResult.success(list);
//    }

    /**
     * 查询国别列表
     */
//    @PreAuthorize(hasPermi ="country:info:list")
    @GetMapping("/getAllList")
//    @CustomLogger(title = "国别-列表查询",businessType = CustomBusinessType.SELECT)
    public AjaxResult getAllList(@Validated(ValidationGroups.Select.class) CountryInfo countryInfo) {
        List<CountryInfo> list = countryInfoService.selectCountryInfoList(countryInfo);
        return AjaxResult.success(list);
    }

    @GetMapping("/selectCountryInfoByNames")
    public AjaxResult selectCountryInfoByNames(@RequestParam("name") String name){
        List<CountryInfo> countryInfo = countryInfoService.selectCountryInfoByNames(name);
        return AjaxResult.success(countryInfo);
    }
}
