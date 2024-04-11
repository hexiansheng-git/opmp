package com.hhwy.sp.experiment.sgjsExperimentRecordInfo.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.domain.SysSyncInfoLog;
import com.hhwy.feign.service.PmServiceApi;
import com.hhwy.sp.experiment.sgjsExperimentRecord.domain.SgjsExperimentRecord;
import com.hhwy.sp.experiment.sgjsExperimentRecord.mapper.SgjsExperimentRecordMapper;
import com.hhwy.sp.experiment.sgjsExperimentRecordInfo.domain.SgjsExperimentRecordInfo;
import com.hhwy.sp.experiment.sgjsExperimentRecordInfo.mapper.SgjsExperimentRecordInfoMapper;
import com.hhwy.sp.experiment.sgjsExperimentRecordInfo.service.ISgjsExperimentRecordInfoService;
import com.hhwy.sp.experiment.sgjsExperimentRecordInfoDetail.domain.SgjsExperimentRecordInfoDetail;
import com.hhwy.sp.experiment.sgjsExperimentRecordInfoDetail.service.ISgjsExperimentRecordInfoDetailService;
import com.hhwy.sp.sgjsMeasure.sgjsEquipEntryRecord.sgjsEquipEntryRecordInfo.domain.SgjsEquipEntryRecordInfo;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 设备实际进场记录
 *
 * @author lcf
 * @date 2023-12-11 15:03:58
 * @remark
 */
@Service
public class SgjsExperimentRecordInfoServiceImpl implements ISgjsExperimentRecordInfoService{

    @Autowired
    private SgjsExperimentRecordInfoMapper sgjsExperimentRecordInfoMapper;
    @Autowired
    private SgjsExperimentRecordMapper sgjsExperimentRecordMapper;
    @Autowired
    private ISgjsExperimentRecordInfoDetailService detailService;
    @Autowired
    private PmServiceApi pmServiceApi;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    private Logger logger= LoggerFactory.getLogger(SgjsExperimentRecordInfoServiceImpl.class);


    public SgjsExperimentRecordInfo getSgjsExperimentRecordInfo(SgjsExperimentRecordInfo sgjsExperimentRecordInfo) {
        return sgjsExperimentRecordInfoMapper.getSgjsExperimentRecordInfo(sgjsExperimentRecordInfo);
    }

    public List<SgjsExperimentRecordInfo> getSgjsExperimentRecordInfoList(SgjsExperimentRecordInfo sgjsExperimentRecordInfo) {
        return sgjsExperimentRecordInfoMapper.getSgjsExperimentRecordInfoList(sgjsExperimentRecordInfo);
    }

    @Transactional
    public int insertSgjsExperimentRecordInfo(SgjsExperimentRecordInfo sgjsExperimentRecordInfo) {
        sgjsExperimentRecordInfo.setId(IdWorker.createId());
        sgjsExperimentRecordInfo.setCreateUser(SecurityUtils.getUserName());
        sgjsExperimentRecordInfo.setCreateTime(DateUtils.getNowDate());
        return sgjsExperimentRecordInfoMapper.insertSgjsExperimentRecordInfo(sgjsExperimentRecordInfo);
    }

    @Transactional
    public int insertSgjsExperimentRecordInfoList(List<SgjsExperimentRecordInfo> sgjsExperimentRecordInfoList) {
        for (SgjsExperimentRecordInfo sgjsExperimentRecordInfo : sgjsExperimentRecordInfoList) {
            sgjsExperimentRecordInfo.setId(IdWorker.createId());
            sgjsExperimentRecordInfo.setCreateUser(SecurityUtils.getUserName());
            sgjsExperimentRecordInfo.setCreateTime(DateUtils.getNowDate());
        }
        return sgjsExperimentRecordInfoMapper.insertSgjsExperimentRecordInfoList(sgjsExperimentRecordInfoList);
    }

    @Transactional
    public int updateSgjsExperimentRecordInfo(SgjsExperimentRecordInfo sgjsExperimentRecordInfo) {
        sgjsExperimentRecordInfo.setUpdateUser(SecurityUtils.getUserName());
        sgjsExperimentRecordInfo.setUpdateTime(DateUtils.getNowDate());
        return sgjsExperimentRecordInfoMapper.updateSgjsExperimentRecordInfo(sgjsExperimentRecordInfo);
    }

    @Transactional
    public int updateSgjsExperimentRecordInfoList(List<SgjsExperimentRecordInfo> sgjsExperimentRecordInfoList) {
        for (SgjsExperimentRecordInfo sgjsExperimentRecordInfo : sgjsExperimentRecordInfoList) {
            sgjsExperimentRecordInfo.setUpdateUser(SecurityUtils.getUserName());
            sgjsExperimentRecordInfo.setUpdateTime(DateUtils.getNowDate());
        }
        return sgjsExperimentRecordInfoMapper.updateSgjsExperimentRecordInfoList(sgjsExperimentRecordInfoList);
    }

    @Transactional
    public int deleteSgjsExperimentRecordInfo(SgjsExperimentRecordInfo sgjsExperimentRecordInfo) {
        sgjsExperimentRecordInfo.setUpdateUser(SecurityUtils.getUserName());
        sgjsExperimentRecordInfo.setUpdateTime(DateUtils.getNowDate());
        return sgjsExperimentRecordInfoMapper.deleteSgjsExperimentRecordInfo(sgjsExperimentRecordInfo);
    }

    @Transactional
    public int deleteSgjsExperimentRecordInfoByPks(List<Long> sgjsExperimentRecordInfoPkList) {
        return sgjsExperimentRecordInfoMapper.deleteSgjsExperimentRecordInfoByPks(sgjsExperimentRecordInfoPkList);
    }

    @Override
    @Transactional
    public int batchAddMap(Map<String, Object> map) {
        List<LinkedHashMap<String,Object>> equipList= (List<LinkedHashMap<String,Object>>)map.get("equipList");
        if(CollectionUtils.isEmpty(equipList)){
            logger.error("传参equipList空了");
            return -1;
        }
        List<LinkedHashMap<String,Object>> infoList= (List<LinkedHashMap<String,Object>>)map.get("infoList");
        if(CollectionUtils.isEmpty(infoList)){
            logger.error("传参infoList空了");
            return -2;
        }
        List<SgjsExperimentRecordInfoDetail> list=new ArrayList<>();
        List<SgjsExperimentRecordInfo> iList=new ArrayList<>();
        for (LinkedHashMap<String,Object> mInfo:infoList) {
            String s = JSONObject.toJSONString(mInfo);
            SgjsExperimentRecordInfo info = JSONObject.parseObject(s, SgjsExperimentRecordInfo.class);
            info.setCreateTime(DateUtils.getNowDate());
            info.setCreateUser(SecurityUtils.getUserId()+"");
            info.setCreateUserName(SecurityUtils.getSysUser().getNickName());
            List<SgjsExperimentRecordInfoDetail> detailList = info.getDetailList();
            if(!CollectionUtils.isEmpty(detailList)){
                list.addAll(detailList);
            }
            iList.add(info);
        }
        //校验
        int i = validData(iList);
        if(i!=1){
            //不等于1  不成功 直接返回
            return i;
        }
        //1、批量修改主表实际进场数量
        List<SgjsExperimentRecord> eList=new ArrayList<>();
        for (LinkedHashMap<String,Object> mInfo:equipList) {
            SgjsExperimentRecord info=new SgjsExperimentRecord();
            info.setUpdateTime(DateUtils.getNowDate());
            info.setUpdateUser(SecurityUtils.getUserName());
            info.setId(Long.parseLong(mInfo.get("recordId")+""));
            info.setActualNum(Integer.parseInt(mInfo.get("actualNum")+""));
            eList.add(info);
        }
        sgjsExperimentRecordMapper.bathUpdateByList(eList);
        //2、删除子表所有数据
        SgjsEquipEntryRecordInfo record=new SgjsEquipEntryRecordInfo();
        record.setUpdateTime(DateUtils.getNowDate());
        record.setUpdateUser(SecurityUtils.getUserId()+"");
        sgjsExperimentRecordInfoMapper.deleteAll(record);
        //3、重新添加数据
        sgjsExperimentRecordInfoMapper.insertSgjsExperimentRecordInfoList(iList);
        //4、自检自校数据新增
        if(!CollectionUtils.isEmpty(list)){
            detailService.insertSgjsExperimentRecordInfoDetailList(list);
        }
        //同步总部版数据
        syncDataToGm(map);
        return 0;
    }

    /**
     * 同步总部版
     *
     * @param map
     */
    private void syncDataToGm(Map<String, Object> map) {
        long beginMills = System.currentTimeMillis();
        Integer status = 1;
        String errMsg = "";
        map.put("type","2");
        try{
            rocketMQTemplate.convertAndSend("sgjs_experiment_record:tenantSuccess1", JSONObject.toJSONString(map));
        }catch (Exception e){
            e.printStackTrace();
            status = 0;
            errMsg = e.getMessage();
            logger.error("报错了【{}】",e.getMessage());
            throw e;
        }finally {
            //3、更新syncInfo
            SysSyncInfoLog log=new SysSyncInfoLog();
            log.setBusinessName("sgjs_experiment_record");
            log.setStatus(status);
            log.setFailMsg(errMsg);
            log.setPtVar1(JSONObject.toJSONString(map));
            logger.error("sgjs_experiment_record同步失败【{}】,时间：【{}】",JSONObject.toJSONString(map),System.currentTimeMillis()-beginMills);
            pmServiceApi.insertSyncLog(log);
        }
    }

    /**
     * 校验数据是否重复
     *
     * @param iList
     * @return
     */
    private int validData(List<SgjsExperimentRecordInfo> iList){
        if(CollectionUtils.isEmpty(iList)){
            logger.error("空了。。。。。。。。【{}】",iList);
            return 0;
        }
        //1、校验传过来的试验管理编号 是否重复 experimentCode
        Map<String,List<SgjsExperimentRecordInfo>> filterMap = iList.stream().collect(Collectors.groupingBy(e->e.getExperimentCode()));
        Set<String> keySet = filterMap.keySet();
        for (String key : keySet) {
            List<SgjsExperimentRecordInfo> filterList = filterMap.get(key);
            if(filterList.size()>1){
                logger.info("重复啦啦啦啦啦啦"+filterList.get(0).getExperimentCode());
                return -1;
            }
        }
        //2、校验库中是否重复 查询的sql语句无敌了
        //List<String> codeList = iList.stream().map(e -> e.getExperimentCode()).collect(Collectors.toList());
        List<SgjsExperimentRecordInfo> rstList=sgjsExperimentRecordInfoMapper.selectByExperimentNos(iList);
        Map<String,List<SgjsExperimentRecordInfo>> paramMap = rstList.stream().collect(Collectors.groupingBy(e->e.getId()+e.getExperimentCode()));
        for (int i = 0; i < iList.size(); i++) {
            String id = iList.get(i).getId()+"";
            String experimentCode = iList.get(i).getExperimentCode();
            List<SgjsExperimentRecordInfo> list = paramMap.get(id + experimentCode);
            if(!CollectionUtils.isEmpty(list)&&list.size()>1){
                logger.error("试验编码重复了【{}】",JSONObject.toJSONString(rstList));
                return -1;
            }
        }
//        if(!CollectionUtils.isEmpty(rstList)){
//            logger.error("试验编码重复了【{}】",JSONObject.toJSONString(rstList));
//            return -1;
//        }
        return 1;
    }

}
