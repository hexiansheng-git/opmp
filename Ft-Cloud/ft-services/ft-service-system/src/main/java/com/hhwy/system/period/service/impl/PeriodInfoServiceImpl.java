package com.hhwy.system.period.service.impl;

import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.SecurityUtils;
import com.hhwy.common.core.utils.StringUtils;

import com.hhwy.domain.base.system.period.PeriodInfo;
import com.hhwy.domain.base.system.period.vo.PeriodCurrencyInfoVo;
import com.hhwy.domain.base.system.periodCurrency.PeriodCurrency;
import com.hhwy.system.period.mapper.PeriodInfoMapper;
import com.hhwy.system.period.service.IPeriodInfoService;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.selfEmpty.SelfEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * infoService业务层处理
 * 
 * @author lcf
 * @date 2022-11-24
 */
@Service
public class PeriodInfoServiceImpl implements IPeriodInfoService {
    private Logger logger= LoggerFactory.getLogger(PeriodInfoServiceImpl.class);
    @Autowired
    private PeriodInfoMapper periodInfoMapper;

    /**
     * 查询info
     * 
     * @param id infoID
     * @return info
     */
    @Override
    public PeriodInfo selectPeriodInfoById(Long id) {
        return periodInfoMapper.selectPeriodInfoById(id);
    }

    /**
     * 查询info列表
     * 
     * @param periodInfo info
     * @return info
     */
    @Override
    @SelfEmpty(clazz = PeriodInfo.class)
    public List<PeriodInfo> selectPeriodInfoList(PeriodInfo periodInfo) {
        return periodInfoMapper.selectPeriodInfoList(periodInfo);
    }

    /**
     * 新增info
     * 
     * @param periodInfo info
     * @return 结果
     */
    @Override
    @Transactional
    @SelfEmpty(clazz = PeriodInfo.class)
    public int insertPeriodInfo(PeriodInfo periodInfo) {
        //判断期次编码是否重复
        List<PeriodInfo> list=periodInfoMapper.validRepeat(periodInfo);
        if(null!=list && list.size()!=0){
            return -1;
        }
        periodInfo.setId(IdWorker.createId());
        periodInfo.setCreateTime(DateUtils.getNowDate());
        periodInfo.setCreateUser(SecurityUtils.getUserId().toString());
        periodInfo.setPtVar1(SecurityUtils.getUserName());
        return periodInfoMapper.insertPeriodInfo(periodInfo);
    }



    /**
     * 新增info
     *
     * @param periodInfo info
     * @return 结果
     */
    @Override
    @Transactional
    @SelfEmpty(clazz = PeriodInfo.class)
    public Long insertPeriodInfoReturnId(PeriodInfo periodInfo) {
        //判断期次编码是否重复
        List<PeriodInfo> list=periodInfoMapper.validRepeat(periodInfo);
        if(null!=list && list.size()!=0){
            return -1L;
        }
        Long id = IdWorker.createId();
        periodInfo.setId(id);
        periodInfo.setCreateTime(DateUtils.getNowDate());
        periodInfo.setCreateUser(SecurityUtils.getUserId().toString());
        periodInfoMapper.insertPeriodInfo(periodInfo);
        return id;
    }


    /**
     * 修改info
     * 
     * @param periodInfo info
     * @return 结果
     */
    @Override
    @SelfEmpty(clazz = PeriodInfo.class)
    public int updatePeriodInfo(PeriodInfo periodInfo) {
        //判断期次编码是否重复
        List<PeriodInfo> list=periodInfoMapper.validRepeat(periodInfo);
        if(null!=list && list.size()!=0){
            return -1;
        }
        periodInfo.setUpdateTime(DateUtils.getNowDate());
        periodInfo.setUpdateUser(SecurityUtils.getUserId().toString());
        return periodInfoMapper.updatePeriodInfo(periodInfo);
    }

    /**
     * 删除info对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deletePeriodInfoByIds(String ids) {
        return periodInfoMapper.deletePeriodInfoByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除info信息
     * 
     * @param id infoID
     * @return 结果
     */
    public int deletePeriodInfoById(Long id) {
        return periodInfoMapper.deletePeriodInfoById(id);
    }

    @Override
    @SelfEmpty(clazz = PeriodInfo.class)
    public List<PeriodCurrencyInfoVo> selectPeriodByDate(PeriodInfo periodInfo) {
        List<PeriodCurrencyInfoVo> list = periodInfoMapper.selectPeriodByDate(periodInfo);
        return list;
    }

    @Override
    public int newAddPeriodInfo(){
        //查询库中的上一个月
        PeriodInfo info = periodInfoMapper.selectLastMonth();
        if(null==info){
            //2023-04-25
            Date nowDate = DateUtils.getNowDate();
            info=new PeriodInfo();
            info.setEndDate(nowDate);
        }
        Date endDate = info.getEndDate();
        //开始时间
        String startTime = getStartTime(endDate);
        //结束日期
        String endTime = getEndTime(endDate);
        PeriodInfo result=new PeriodInfo();
        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            result.setBeginDate(sf.parse(startTime));
            result.setEndDate(sf.parse(endTime));
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
        String[] split = endTime.split("-");
        String year=split[0];
        String month=split[1];
        result.setPeriodCode(year+month);
        result.setCreateUser(SecurityUtils.getUserId().toString());
        result.setCreateTime(DateUtils.getNowDate());
        result.setPtVar1(SecurityUtils.getUserName());
        result.setId(IdWorker.createId());
        return periodInfoMapper.insertPeriodInfo(result);
    }

    /**
     * 提供给融智
     * 根据月份拿汇率
     *
     * @param periodInfo
     * @return
     */
    @Override
    public List<PeriodCurrencyInfoVo> selectPeriodByMonth(PeriodInfo periodInfo) {
        String periodDate = periodInfo.getPeriodDate();
        List<PeriodCurrencyInfoVo> list = periodInfoMapper.selectPeriodByMonth(periodInfo);
        return list;
    }

    @Override
    public PeriodInfo selectPeriodInfoByDate(PeriodInfo date) {
        return periodInfoMapper.selectPeriodInfoByDate(date);
    }

    @Override
    public List<PeriodCurrency> selectPeriodByYear(PeriodInfo periodInfo) {
        String queryDate = periodInfo.getQueryDate();
        if(StringUtils.isBlank(queryDate)){
            logger.error("请求参数queryDate不能为空");
            return new ArrayList<>();
        }
        List<PeriodCurrency> list=periodInfoMapper.selectPeriodByYear(periodInfo);
        return list;
    }

    @Override
    public List<PeriodInfo> selectBathByDate(List<String> list) {
        List<PeriodInfo> rstList=periodInfoMapper.selectBathByDate(list);
        return rstList;
    }

    @Override
    public int batchInsert(List<PeriodInfo> list) {
        return periodInfoMapper.batchInsert(list);
    }


    /**
     * 开始日期处理
     *
     * @param endDate
     * @return
     */
    private String getStartTime(Date endDate){
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
        calendar.setTime(endDate);
        //calendar.add(Calendar.MONTH,1);
        calendar.add(Calendar.DAY_OF_MONTH,1);
        Date time = calendar.getTime();
        String startTime = sf.format(time);
        return startTime;
    }

    /**
     * 结束日期处理
     *
     * @param endDate
     * @return
     */
    private String getEndTime(Date endDate){
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
        calendar.setTime(endDate);
        calendar.add(Calendar.MONTH,1);
        Date time = calendar.getTime();
        String startTime = sf.format(time);
        return startTime;
    }

    @Override
    public void dataSync(List<PeriodInfo> list) {
//        periodInfoMapper.deleteAll();
        periodInfoMapper.batchInsert(list);
    }
}
