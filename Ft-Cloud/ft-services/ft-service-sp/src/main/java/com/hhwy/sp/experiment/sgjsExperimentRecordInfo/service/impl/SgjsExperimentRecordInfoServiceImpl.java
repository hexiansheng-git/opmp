package com.hhwy.sp.experiment.sgjsExperimentRecordInfo.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.sp.experiment.sgjsExperimentRecord.domain.SgjsExperimentRecord;
import com.hhwy.sp.experiment.sgjsExperimentRecord.mapper.SgjsExperimentRecordMapper;
import com.hhwy.sp.experiment.sgjsExperimentRecordInfo.domain.SgjsExperimentRecordInfo;
import com.hhwy.sp.experiment.sgjsExperimentRecordInfo.mapper.SgjsExperimentRecordInfoMapper;
import com.hhwy.sp.experiment.sgjsExperimentRecordInfo.service.ISgjsExperimentRecordInfoService;
import com.hhwy.sp.experiment.sgjsExperimentRecordInfoDetail.domain.SgjsExperimentRecordInfoDetail;
import com.hhwy.sp.experiment.sgjsExperimentRecordInfoDetail.service.ISgjsExperimentRecordInfoDetailService;
import com.hhwy.sp.sgjsMeasure.sgjsEquipEntryRecord.sgjsEquipEntryRecord.domain.SgjsEquipEntryRecord;
import com.hhwy.sp.sgjsMeasure.sgjsEquipEntryRecord.sgjsEquipEntryRecordInfo.domain.SgjsEquipEntryRecordInfo;
import com.hhwy.sp.sgjsMeasure.sgjsEquipEntryRecord.sgjsEquipEntryRecordInfoDetail.domain.SgjsEquipEntryRecordInfoDetail;
import com.hhwy.utils.idworker.IdWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author lcf--设备实际进场记录
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
        //数据校验  试验编号不能重复
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
        //3、校验试验管理编号 是否重复
        List<String> codeList = iList.stream().map(e -> e.getExperimentCode()).collect(Collectors.toList());
        List<SgjsExperimentRecordInfo> rstList=sgjsExperimentRecordInfoMapper.selectByExperimentNos(codeList);
        if(!CollectionUtils.isEmpty(rstList)){
            logger.error("试验编码重复了【{}】",JSONObject.toJSONString(rstList));
            return -1;
        }
        //4、重新添加数据
        sgjsExperimentRecordInfoMapper.insertSgjsExperimentRecordInfoList(iList);
        //4、自检自校数据新增
        if(!CollectionUtils.isEmpty(list)){
            detailService.insertSgjsExperimentRecordInfoDetailList(list);
        }
        return 0;
    }
}
