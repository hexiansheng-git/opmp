package com.hhwy.system.currency.service.impl;


import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.SecurityUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.domain.base.system.country.CountryInfo;
import com.hhwy.domain.base.system.currency.CurrencyInfo;
import com.hhwy.domain.base.system.periodCurrency.PeriodCurrency;
import com.hhwy.system.currency.mapper.CurrencyInfoMapper;
import com.hhwy.system.currency.service.ICurrencyInfoService;
import com.hhwy.system.periodCurrency.service.IPeriodCurrencyService;
import com.hhwy.utils.Constant;
import com.hhwy.utils.common.PmsConstant;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.selfEmpty.SelfEmpty;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * infoService业务层处理
 * 
 * @author lcf
 * @date 2022-11-24
 */
@Service
public class CurrencyInfoServiceImpl implements ICurrencyInfoService {
    @Autowired
    private CurrencyInfoMapper currencyInfoMapper;
    @Autowired
    private IPeriodCurrencyService periodCurrencyService;

    private Logger logger= LoggerFactory.getLogger(CurrencyInfoServiceImpl.class);

    /**
     * 查询info
     * 
     * @param id infoID
     * @return info
     */
    @Override
    public CurrencyInfo selectCurrencyInfoById(Long id) {
        return currencyInfoMapper.selectCurrencyInfoById(id);
    }

    @Override
    public CurrencyInfo selectCurrencyInfoByCode(String code) {
        if(StringUtils.isBlank(code))
            return new CurrencyInfo();
        CurrencyInfo currencyInfo = new CurrencyInfo();
        currencyInfo.setCurrencyCode(code);
        List<CurrencyInfo> list = currencyInfoMapper.selectCurrencyInfoList(currencyInfo);
        if(CollectionUtils.isEmpty(list)){
            return new CurrencyInfo(); 
        }
        return list.get(0);
    }

    @Override
    public String selectCurrencyNameByCode(String code) {
        CurrencyInfo currencyInfo = this.selectCurrencyInfoByCode(code);
        return currencyInfo.getCurrencyName(); 
    }

    /**
     * 查询info列表
     * 
     * @param currencyInfo info
     * @return info
     */
    @Override
    @SelfEmpty(clazz = CountryInfo.class )
    public List<CurrencyInfo> selectCurrencyInfoList(CurrencyInfo currencyInfo) {
        return currencyInfoMapper.selectCurrencyInfoList(currencyInfo);
    }

    /**
     * 新增info
     * 
     * @param currencyInfo info
     * @return 结果
     */
    @Override
    @SelfEmpty(clazz =CountryInfo.class )
    public int insertCurrencyInfo(CurrencyInfo currencyInfo) {
        //判断币种编码是否重复
        CurrencyInfo info=new CurrencyInfo();
        info.setCurrencyCode(currencyInfo.getCurrencyCode());
        info.setCurrencyName(currencyInfo.getCurrencyName());
        List<CurrencyInfo> list = currencyInfoMapper.validRepeat(info);
        if(null!=list && list.size()!=0){
            return -1;
        }
        //新增
        currencyInfo.setId(IdWorker.createId());
        currencyInfo.setCreateTime(DateUtils.getNowDate());
        currencyInfo.setCreateUser(SecurityUtils.getUserId().toString());
        currencyInfo.setPtVar1(SecurityUtils.getUserName());
        return currencyInfoMapper.insertCurrencyInfo(currencyInfo);
    }

    /**
     * 修改info
     * 
     * @param currencyInfo info
     * @return 结果
     */
    @Override
    @SelfEmpty(clazz =CountryInfo.class )
    public int updateCurrencyInfo(CurrencyInfo currencyInfo) {
        //判断币种编码是否重复
        CurrencyInfo info=new CurrencyInfo();
        info.setCurrencyCode(currencyInfo.getCurrencyCode());
        info.setId(currencyInfo.getId());
        List<CurrencyInfo> list = currencyInfoMapper.validRepeat(info);
        if(null!=list && list.size()!=0){
            return -1;
        }
        currencyInfo.setUpdateTime(DateUtils.getNowDate());
        currencyInfo.setUpdateUser(SecurityUtils.getUserId().toString());
        currencyInfo.setPtVar2(SecurityUtils.getUserName());
        return currencyInfoMapper.updateCurrencyInfo(currencyInfo);
    }

    /**
     * 删除info对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteCurrencyInfoByIds(String ids) {
        return currencyInfoMapper.deleteCurrencyInfoByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除info信息
     * 
     * @param id infoID
     * @return 结果
     */
    public int deleteCurrencyInfoById(Long id) {
        return currencyInfoMapper.deleteCurrencyInfoById(id);
    }

    @Override
    public int selectMaxSort() {
        return currencyInfoMapper.selectMaxSort();
    }

    @Override
    public AjaxResult importData(List<CurrencyInfo> list) {
        if(CollectionUtils.isEmpty(list)){
            return new AjaxResult(PmsConstant.WARN_CODE,"导入数据不能为空");
        }
        String msg="";
        int t=1;
        for (int i = 0; i < list.size(); i++) {
            t=t+i;
            CurrencyInfo currencyInfo = list.get(i);
            String currencyCode = currencyInfo.getCurrencyCode();
            String currencyName = currencyInfo.getCurrencyName();
            if(StringUtils.isBlank(currencyCode)){
                msg=msg+"第"+t+"行币种代码不能为空;";
            }
            if(StringUtils.isBlank(currencyName)){
                msg=msg+"第"+t+"行币种名称不能为空;";
            }
        }
        logger.info("【币种管理】导入异常数据信息："+msg);
        if(StringUtils.isNotBlank(msg)){
            return new AjaxResult(Constant.WARN_CODE,msg);
        }
        List<CurrencyInfo> data = handleData(list);
        //数据批量插入
        int i = currencyInfoMapper.batchInsert(data);
        return AjaxResult.success(i);
    }

    @Override
    public List<PeriodCurrency> selectPeriodCurrency(PeriodCurrency periodCurrency) {
        CurrencyInfo currencyInfo=new CurrencyInfo();
        currencyInfo.setCurrencyName(periodCurrency.getPtVar3());
        List<CurrencyInfo> list = currencyInfoMapper.selectCurrencyInfoList(currencyInfo);
        if(CollectionUtils.isEmpty(list)){
            logger.error("未查询到币种列表数据");
            return null;
        }

        List<Long> currencyIdList = list.stream().map(e -> e.getId()).collect(Collectors.toList());

        //根据币种编码List和期次id查询币种的汇率设置信息
        PeriodCurrency info=new PeriodCurrency();
        info.setPeriodId(periodCurrency.getPeriodId());
        info.setCurrencyIdList(currencyIdList);
        List<PeriodCurrency> periodCurrencyList = periodCurrencyService.selectPeriodCurrencyList(info);
        //汇率为空，直接返回币种信息
        List<PeriodCurrency> result=new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            PeriodCurrency currency=new PeriodCurrency();
            CurrencyInfo info1 = list.get(i);
            currency.setCurrencyId(String.valueOf(info1.getId()));
            currency.setPtVar3(info1.getCurrencyName());
            result.add(currency);
        }
        if(CollectionUtils.isEmpty(periodCurrencyList)){
            logger.info("根据期次id和币种信息查汇率查不到，说明未设置，则直接返回币种信息即可");
            return result;
        }
        //不为空，处理币种和汇率
        List<PeriodCurrency> rstList = handlePeriod(result, periodCurrencyList);
        return rstList;
    }

    @Override
    public void batchInsert(List<CurrencyInfo> rstList) {
        currencyInfoMapper.batchInsert(rstList);
    }

    /**
     * 币种和汇率关联返回
     *
     * @param result 币种信息
     * @param periodCurrencyList 汇率信息
     * @return
     */
    private List<PeriodCurrency> handlePeriod(List<PeriodCurrency> result,List<PeriodCurrency> periodCurrencyList){
        Map<String, List<PeriodCurrency>> map = periodCurrencyList.stream().collect(Collectors.groupingBy(PeriodCurrency::getCurrencyId));
        for (int i = 0; i < result.size(); i++) {
            PeriodCurrency currency = result.get(i);
            List<PeriodCurrency> list = map.get(currency.getCurrencyId());
            if(CollectionUtils.isNotEmpty(list)){
                currency.setRate(list.get(0).getRate());
                currency.setPeriodId(list.get(0).getPeriodId());
                currency.setPeriodCode(list.get(0).getPeriodCode());
            }
        }
        return result;
    }

    /**
     * 数据处理
     *
     * @param list
     * @return
     */
    private List<CurrencyInfo> handleData(List<CurrencyInfo> list){
        //查询库中最大排序数
        Integer maxSort = currencyInfoMapper.selectMaxSort();
        maxSort=maxSort+1;
        for (int i = 0; i < list.size(); i++) {
            CurrencyInfo info = list.get(i);
            info.setCreateUser(SecurityUtils.getUserId().toString());
            info.setPtVar1(SecurityUtils.getUserName());
            info.setCreateTime(DateUtils.getNowDate());
            maxSort=maxSort+i;
            info.setSort(maxSort.longValue());
        }
        return list;
    }

    @Transactional
    @Override
    public void dataSync(List<CurrencyInfo> rstList) {
        currencyInfoMapper.deleteAll();
        currencyInfoMapper.batchInsert(rstList);
    }
}
