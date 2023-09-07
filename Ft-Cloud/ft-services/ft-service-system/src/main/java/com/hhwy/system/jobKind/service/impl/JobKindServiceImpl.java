package com.hhwy.system.jobKind.service.impl;


import com.hhwy.domain.base.system.country.CountryInfo;
import com.hhwy.domain.base.system.currency.CurrencyInfo;
import com.hhwy.domain.base.system.jobKind.JobKind;
import com.hhwy.system.jobKind.mapper.JobKindMapper;
import com.hhwy.system.jobKind.service.IJobKindService;
import com.hhwy.utils.selfEmpty.SelfEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * infoService业务层处理
 *
 * @author lcf
 * @date 2022-11-24
 */
@Service
public class JobKindServiceImpl implements IJobKindService {
    @Autowired
    private JobKindMapper jobKindMapper;

    /**
     * 查询列表
     *
     * @param jobKind info
     * @return info
     */
    @Override
    @SelfEmpty(clazz = CountryInfo.class )
    public List<JobKind> selectJobKindList(JobKind jobKind) {
        return jobKindMapper.selectJobKindList(jobKind);
    }

    @Override
    public void batchInsert(List<JobKind> rstList) {
        jobKindMapper.batchInsert(rstList);
    }


    /***
     * 功能描述: 数据同步，每次全量更新
     * 作者: fushudong
     * 时间: 2023/9/6
     */
    @Override
    @Transactional
    public void dataSync(List<JobKind> rstList) {
        jobKindMapper.deleteAll();
        jobKindMapper.batchInsert(rstList);
    }
}
