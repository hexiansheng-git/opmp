package com.hhwy.system.jobKind.service;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.domain.base.system.currency.CurrencyInfo;
import com.hhwy.domain.base.system.jobKind.JobKind;

import java.util.List;


/**
 * infoService接口
 * 
 * @author lcf
 * @date 2022-11-24
 */
public interface IJobKindService {
    /**
     * 查询列表
     */
    List<JobKind> selectJobKindList(JobKind jobKind);

    /**
     * 批量新增
     */
    void batchInsert(List<JobKind> rstList);

    /**
     * 数据同步
     */
    void dataSync(List<JobKind> rstList);
}
