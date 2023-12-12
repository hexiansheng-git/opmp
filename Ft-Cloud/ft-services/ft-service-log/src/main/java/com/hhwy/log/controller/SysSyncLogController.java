package com.hhwy.log.controller;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.domain.log.SysSyncLog;
import com.hhwy.log.service.ISysSyncLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sysSyncLog")
public class SysSyncLogController {

    @Autowired
    private ISysSyncLogService sysSyncLogService;

    /**
     * 日志查询
     *
     * @param sysSyncLog
     * @return
     */
    @PostMapping("/selectSysSyncLogList")
    public AjaxResult selectSysSyncLogList(@RequestBody SysSyncLog sysSyncLog){
        return AjaxResult.success(sysSyncLogService.selectSysSyncLogList(sysSyncLog));
    }

    /**
     * 日志记录
     *
     * @param sysSyncLog
     * @return
     */
    @PostMapping("/insertSysSyncLog")
    public AjaxResult insertSysSyncLog(@RequestBody SysSyncLog sysSyncLog){
        return AjaxResult.success(sysSyncLogService.insertSysSyncLog(sysSyncLog));
    }

    /**
     * 日志记录
     *
     * @param sysSyncLog
     * @return
     */
    @PostMapping("/updateSysSyncLog")
    public AjaxResult updateSysSyncLog(@RequestBody SysSyncLog sysSyncLog){
        return AjaxResult.success(sysSyncLogService.updateSysSyncLog(sysSyncLog));
    }
}
