package com.hhwy.job.task.system;

import com.hhwy.job.feign.service.SystemServiceApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 功能：主数据信息拉取
 * 作者: fushudong
 * 时间: 2023/10/20
 */
@Slf4j
@Component("DataPullMain")
public class MainDataPull {

    @Autowired
    private SystemServiceApi systemServiceApi;

    /**
     * 国别
     */
    public void pullCountry(){
        log.info("国别数据拉取开始");
        systemServiceApi.pullContry();
        log.info("国别数据拉取完成");
    }

    /**
     * 币种
     */
    public void pullCurrency(){
        log.info("币种数据拉取开始");
        systemServiceApi.pullCurrency();
        log.info("币种数据拉取开始");
    }

    /**
     * 期次和期次汇率
     */
    public void pullPeriodCurrency(){
        log.info("期次和期次汇率数据拉取开始");
        systemServiceApi.pullPeriodCurrency();
        log.info("期次和期次汇率数据拉取完成");
    }
}
