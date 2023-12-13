package com.hhwy.feign.service;

import com.hhwy.domain.base.InterfaceLog.InterfaceLog;
import com.hhwy.domain.log.SysSyncLog;
import com.hhwy.feign.factory.LogServiceFallBackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * 日志管理
 *
 * @author lcf
 * @date 2023-09-21
 */
@FeignClient(name = "ft-service-log", fallbackFactory = LogServiceFallBackFactory.class)
public interface ILogServiceApi {
    /**
     * 成功日志插入
     *
     * @auth lcf
     * @param interfaceLog
     */
    @PostMapping("/customLog/insertSuccessLog")
    void insertSuccessLog(@RequestBody InterfaceLog interfaceLog);

    /**
     * 成功日志插入
     *
     * @auth lcf
     * @param map
     */
    @PostMapping("/customLog/insertSuccessLogMap")
    void insertSuccessLogMap(@RequestBody Map<String,String> map);

    /**
     * 失败日志插入
     *
     * @auth lcf
     * @param interfaceLog
     */
    @PostMapping("/customLog/insertFailLog")
    void insertFailLog(@RequestBody InterfaceLog interfaceLog);

    /**
     * 失败日志插入
     *
     * @param map
     */
    @PostMapping("/customLog/insertFailLogMap")
    void insertFailLogMap(@RequestBody Map<String,String> map);

    /**
     * sysSyncLog日志插入
     * 成功失败根据状态而定
     * 2023-09-22
     *
     * @param log
     */
    @PostMapping("/sysSyncLog/insertSysSyncLog")
    void insertSysSyncLog(SysSyncLog log);
}
