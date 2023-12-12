package com.hhwy.log.controller;

import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.page.TableDataInfo;
import com.hhwy.domain.base.InterfaceLog.InterfaceLog;
import com.hhwy.log.service.IInterfaceLogService;
import com.hhwy.log.utils.log.InterfaceLogUtil;
import com.hhwy.utils.core.DateUtil;
import com.hhwy.utils.objectUtil.ObjectNullUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/customLog")
public class InterfaceLogController extends BaseController {
    @Autowired
    private IInterfaceLogService customLogService;

    @ResponseBody
    @PostMapping("/list")
    public TableDataInfo list(@RequestBody Map<String,Object> params){
        startPage(Integer.parseInt(String.valueOf(params.get("pageNum"))), Integer.parseInt(String.valueOf(params.get("pageSize"))));
        String createTime = (String) params.get("createTime");
        if(ObjectNullUtil.isEmpty(createTime)){
            params.put("createTime", DateUtil.getCurrentDate());
        }
        InterfaceLog where = new InterfaceLog();
        where.setParams(params);

        List<InterfaceLog> list = customLogService.selectInterfaceLogList(where);
        return getDataTable(list);
    }

    @ResponseBody
    @GetMapping("/detail")
    public AjaxResult detail(Long id, String var1){
        InterfaceLog interfaceLog = customLogService.selectInterfaceLogById(id);
        String ip = interfaceLog.getVar1();
        SimpleDateFormat DATE_SDF = new SimpleDateFormat("yyyy-MM-dd");
        String date = DATE_SDF.format(interfaceLog.getCreateTime());
        Long seek = Long.parseLong(interfaceLog.getOffset());
        String str = InterfaceLogUtil.readLineLog(date, seek, id);
        JSONObject jsonObject = new JSONObject();
        if(StringUtils.isNotBlank(str)){
            str = str.replace(id + ": ", "");
            jsonObject = (JSONObject) JSONObject.parse(str);
        }
        jsonObject.put("info", interfaceLog);
        return AjaxResult.success(jsonObject);
    }

    /**
     * 成功日志插入
     *
     * @param interfaceLog
     */
    @PostMapping("/insertSuccessLog")
    public void insertSuccessLog(@RequestBody InterfaceLog interfaceLog){
        customLogService.insertSuccessLog(interfaceLog);
    }

    /**
     * 失败日志插入
     *
     * @param interfaceLog
     */
    @PostMapping("/insertFailLog")
    public void insertFailLog(@RequestBody InterfaceLog interfaceLog){
        customLogService.insertFailLog(interfaceLog);
    }

    /**
     * 成功日志插入
     *
     * @param map
     */
    @PostMapping("/insertSuccessLogMap")
    public void insertSuccessLogMap(@RequestBody Map<String,String> map){
        String interFaceName = map.get("interFaceName");
        String req = map.get("req");
        String res = map.get("res");
        customLogService.insertSuccessLog(interFaceName,req,res);
    }

    /**
     * 失败日志插入
     *
     * @param map
     */
    @PostMapping("/insertFailLogMap")
    public void insertFailLogMap(@RequestBody Map<String,String> map){
        String interFaceName = map.get("interFaceName");
        String req = map.get("req");
        String res = map.get("res");
        customLogService.insertFailLog(interFaceName,req,res);
    }
}
