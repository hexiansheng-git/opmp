package com.hhwy.system.periodCurrency.service.impl;


import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.SecurityUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.domain.base.system.period.PeriodInfo;
import com.hhwy.domain.base.system.periodCurrency.PeriodCurrency;
import com.hhwy.system.period.service.IPeriodInfoService;
import com.hhwy.system.periodCurrency.mapper.PeriodCurrencyMapper;
import com.hhwy.system.periodCurrency.service.IPeriodCurrencyService;

import com.hhwy.utils.common.CommonAssert;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.selfEmpty.SelfEmpty;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 期次汇率Service业务层处理
 * 
 * @author lcf
 * @date 2022-11-24
 */
@Service
public class PeriodCurrencyServiceImpl implements IPeriodCurrencyService {
    @Autowired
    private PeriodCurrencyMapper periodCurrencyMapper;
    @Autowired
    private IPeriodInfoService periodInfoService;
//    @Autowired
//    private AppNoticeUtils appNoticeUtils;

    private Logger logger= LoggerFactory.getLogger(PeriodCurrencyServiceImpl.class);

    /**
     * 查询期次汇率
     * 
     * @param id 期次汇率ID
     * @return 期次汇率
     */
    @Override
    public PeriodCurrency selectPeriodCurrencyById(Long id) {
        return periodCurrencyMapper.selectPeriodCurrencyById(id);
    }

    /**
     * 查询期次汇率列表
     * 
     * @param periodCurrency 期次汇率
     * @return 期次汇率
     */
    @Override
    @SelfEmpty(clazz=PeriodCurrency.class)
    public List<PeriodCurrency> selectPeriodCurrencyList(PeriodCurrency periodCurrency) {
        return periodCurrencyMapper.selectPeriodCurrencyList(periodCurrency);
    }

    /**
     * 新增期次汇率 给所有币种加汇率
     * 
     * @param periodCurrency 期次汇率
     * @return 结果
     */
    @Override
    @Transactional
    @SelfEmpty(clazz=PeriodCurrency.class)
    public int insertPeriodCurrency(PeriodCurrency periodCurrency) {
        //根据期次编号查询期次信息
        PeriodInfo periodInfo=new PeriodInfo();
        periodInfo.setPeriodCode(periodCurrency.getPeriodCode());
        List<PeriodInfo> list = periodInfoService.selectPeriodInfoList(periodInfo);
        if(CollectionUtils.isEmpty(list)){
            logger.info("根据期次编码未查询到期次信息");
            return -1;
        }
        //汇率批量新增
        periodCurrency.setId(IdWorker.createId());
        periodCurrency.setCreateTime(DateUtils.getNowDate());
        periodCurrency.setCreateUser(SecurityUtils.getUserId().toString());
        periodCurrency.setPeriodId(list.get(0).getId()+"");//期次表id
        periodCurrency.setPeriodCode(list.get(0).getPeriodCode());
        int i=periodCurrencyMapper.insertPeriodCurrency(periodCurrency);
//        appNoticeUtils.setAppNoticeRedisInfo(AppConstant.WBS_URL_INFO);
        return i;
    }


    @Override
    @Transactional
    @SelfEmpty(clazz=PeriodInfo.class)
    public int insertBathPeriodCurrency(PeriodInfo periodInfo) {
        Long periodId = periodInfo.getId();
        List<PeriodCurrency> list = periodInfo.getList();
        if(CollectionUtils.isEmpty(list)){
            logger.error("汇率信息不能为空");
            return -1;
        }
        PeriodCurrency info=new PeriodCurrency();
        info.setPeriodId(String.valueOf(periodId));
        info.setDelUser(SecurityUtils.getUserId().toString());
        info.setDelTime(DateUtils.getNowDate());
        info.setPtVar3(SecurityUtils.getUserName());
        //汇率每次设置 删除该期次下的所有汇率 重新添加
        int i = periodCurrencyMapper.deletePeriodCurrencyByPeriodId(info);
        for (int j = 0; j < list.size(); j++) {
            PeriodCurrency periodCurrency = list.get(j);
            periodCurrency.setCreateUser(SecurityUtils.getUserId().toString());
            periodCurrency.setCreateTime(DateUtils.getNowDate());
            periodCurrency.setPtVar1(SecurityUtils.getUserName());
            periodCurrency.setId(IdWorker.createId());
        }
        i = periodCurrencyMapper.batchInsert(list);
//        appNoticeUtils.setAppNoticeRedisInfo(AppConstant.WBS_URL_INFO);
        return i;
    }

    /**
     * 修改期次汇率
     * 
     * @param periodCurrency 期次汇率
     * @return 结果
     */
    @Override
    @SelfEmpty(clazz=PeriodCurrency.class)
    public int updatePeriodCurrency(PeriodCurrency periodCurrency) {
        periodCurrency.setUpdateTime(DateUtils.getNowDate());
//        appNoticeUtils.setAppNoticeRedisInfo(AppConstant.WBS_URL_INFO);
        return periodCurrencyMapper.updatePeriodCurrency(periodCurrency);
    }

    /**
     * 删除期次汇率对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deletePeriodCurrencyByIds(String ids) {
//        appNoticeUtils.setAppNoticeRedisInfo(AppConstant.WBS_URL_INFO);
        return periodCurrencyMapper.deletePeriodCurrencyByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除期次汇率信息
     * 
     * @param id 期次汇率ID
     * @return 结果
     */
    public int deletePeriodCurrencyById(Long id) {
        return periodCurrencyMapper.deletePeriodCurrencyById(id);
    }

    @Override
    public PeriodCurrency editPageInfo(Long id) {
        return null;
    }

    @Override
    public List<PeriodCurrency> selectRatePeriodByCodeAndCurrent(Map<String, String> map) {
        String periodCode = map.get("periodCode");
        String currency = map.get("currency");
        String periodDate = map.get("periodDate");
        if (StringUtils.isNotEmpty(periodCode) && StringUtils.isNotEmpty(periodDate)) throw new RuntimeException("期次编码和期次时间只能填写一个");
        try {
            if (StringUtils.isEmpty(periodCode)) {
                PeriodInfo p = new PeriodInfo();
                p.setPeriodDate(periodDate);
                PeriodInfo periodInfo = periodInfoService.selectPeriodInfoByDate(p);
                periodCode = periodInfo.getPeriodCode();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        CommonAssert.notBlank(periodCode,"期次号不能为空");
        CommonAssert.notBlank(currency, "币种不能为空");
        return periodCurrencyMapper.selectRatePeriodByCodeAndCurrent(currency, periodCode);
    }

    @Override
    public int batchInsert(List<PeriodCurrency> list) {
        return periodCurrencyMapper.batchInsert(list);
    }

    @Override
    public void dataSync(List<PeriodCurrency> list) {
//        periodCurrencyMapper.deleteAll();
        periodCurrencyMapper.batchInsert(list);
    }
}
