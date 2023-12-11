package com.hhwy.sp.sgjsMeasure.sgjsEquipEntryRecord.sgjsEquipEntryRecordInfo.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.sp.sgjsMeasure.sgjsEquipEntryRecord.sgjsEquipEntryRecord.domain.SgjsEquipEntryRecord;
import com.hhwy.sp.sgjsMeasure.sgjsEquipEntryRecord.sgjsEquipEntryRecord.mapper.SgjsEquipEntryRecordMapper;
import com.hhwy.sp.sgjsMeasure.sgjsEquipEntryRecord.sgjsEquipEntryRecordInfo.domain.SgjsEquipEntryRecordInfo;
import com.hhwy.sp.sgjsMeasure.sgjsEquipEntryRecord.sgjsEquipEntryRecordInfo.mapper.SgjsEquipEntryRecordInfoMapper;
import com.hhwy.sp.sgjsMeasure.sgjsEquipEntryRecord.sgjsEquipEntryRecordInfo.service.ISgjsEquipEntryRecordInfoService;
import com.hhwy.sp.sgjsMeasure.sgjsEquipEntryRecord.sgjsEquipEntryRecordInfoDetail.domain.SgjsEquipEntryRecordInfoDetail;
import com.hhwy.sp.sgjsMeasure.sgjsEquipEntryRecord.sgjsEquipEntryRecordInfoDetail.service.ISgjsEquipEntryRecordInfoDetailService;
import com.hhwy.utils.date.FtDateUtils;
import com.hhwy.utils.idworker.IdWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author lcf   测量管理--测试设备进场记录
 * @date 2023-12-08 10:49:36
 * @remark
 */
@Service
public class SgjsEquipEntryRecordInfoServiceImpl implements ISgjsEquipEntryRecordInfoService{

    private Logger logger= LoggerFactory.getLogger(SgjsEquipEntryRecordInfoServiceImpl.class);

    @Autowired
    private SgjsEquipEntryRecordInfoMapper sgjsEquipEntryRecordInfoMapper;
    @Autowired
    private SgjsEquipEntryRecordMapper sgjsEquipEntryRecordMapper;
    @Autowired
    private ISgjsEquipEntryRecordInfoDetailService sgjsEquipEntryRecordInfoDetailService;


    public SgjsEquipEntryRecordInfo getSgjsEquipEntryRecordInfo(SgjsEquipEntryRecordInfo sgjsEquipEntryRecordInfo) {
        return sgjsEquipEntryRecordInfoMapper.getSgjsEquipEntryRecordInfo(sgjsEquipEntryRecordInfo);
    }

    public List<SgjsEquipEntryRecordInfo> getSgjsEquipEntryRecordInfoList(SgjsEquipEntryRecordInfo sgjsEquipEntryRecordInfo) {
        //搜索条件
        if(StringUtils.isNotEmpty(sgjsEquipEntryRecordInfo.getEntryDateStr())){
            String actualDateStr = sgjsEquipEntryRecordInfo.getEntryDateStr();
            String[] split = actualDateStr.split("~");
            String begin=split[0].replaceAll("(?:年|月|日)", "-");
            String end=split[1].replaceAll("(?:年|月|日)", "-");
            sgjsEquipEntryRecordInfo.setEntryDateBegin(FtDateUtils.parseDate(begin));
            sgjsEquipEntryRecordInfo.setEntryDateEnd(FtDateUtils.parseDate(end));
        }
        return sgjsEquipEntryRecordInfoMapper.getSgjsEquipEntryRecordInfoList(sgjsEquipEntryRecordInfo);
    }

    @Transactional
    public int insertSgjsEquipEntryRecordInfo(SgjsEquipEntryRecordInfo sgjsEquipEntryRecordInfo) {
        sgjsEquipEntryRecordInfo.setId(IdWorker.createId());
        sgjsEquipEntryRecordInfo.setCreateUser(SecurityUtils.getUserName());
        sgjsEquipEntryRecordInfo.setCreateTime(DateUtils.getNowDate());
        return sgjsEquipEntryRecordInfoMapper.insertSgjsEquipEntryRecordInfo(sgjsEquipEntryRecordInfo);
    }

    @Transactional
    public int insertSgjsEquipEntryRecordInfoList(List<SgjsEquipEntryRecordInfo> sgjsEquipEntryRecordInfoList) {

        for (SgjsEquipEntryRecordInfo sgjsEquipEntryRecordInfo : sgjsEquipEntryRecordInfoList) {
            sgjsEquipEntryRecordInfo.setId(IdWorker.createId());
            sgjsEquipEntryRecordInfo.setCreateUser(SecurityUtils.getUserName());
            sgjsEquipEntryRecordInfo.setCreateTime(DateUtils.getNowDate());
        }
        return sgjsEquipEntryRecordInfoMapper.insertSgjsEquipEntryRecordInfoList(sgjsEquipEntryRecordInfoList);
    }

    @Transactional
    public int updateSgjsEquipEntryRecordInfo(SgjsEquipEntryRecordInfo sgjsEquipEntryRecordInfo) {
        sgjsEquipEntryRecordInfo.setUpdateUser(SecurityUtils.getUserName());
        sgjsEquipEntryRecordInfo.setUpdateTime(DateUtils.getNowDate());
        return sgjsEquipEntryRecordInfoMapper.updateSgjsEquipEntryRecordInfo(sgjsEquipEntryRecordInfo);
    }

    @Transactional
    public int updateSgjsEquipEntryRecordInfoList(List<SgjsEquipEntryRecordInfo> sgjsEquipEntryRecordInfoList) {
        for (SgjsEquipEntryRecordInfo sgjsEquipEntryRecordInfo : sgjsEquipEntryRecordInfoList) {
            sgjsEquipEntryRecordInfo.setUpdateUser(SecurityUtils.getUserName());
            sgjsEquipEntryRecordInfo.setUpdateTime(DateUtils.getNowDate());
        }
        return sgjsEquipEntryRecordInfoMapper.updateSgjsEquipEntryRecordInfoList(sgjsEquipEntryRecordInfoList);
    }

    @Transactional
    public int deleteSgjsEquipEntryRecordInfo(SgjsEquipEntryRecordInfo sgjsEquipEntryRecordInfo) {
        sgjsEquipEntryRecordInfo.setUpdateUser(SecurityUtils.getUserName());
        sgjsEquipEntryRecordInfo.setUpdateTime(DateUtils.getNowDate());
        return sgjsEquipEntryRecordInfoMapper.deleteSgjsEquipEntryRecordInfo(sgjsEquipEntryRecordInfo);
    }

    @Transactional
    public int deleteSgjsEquipEntryRecordInfoByPks(List<Long> sgjsEquipEntryRecordInfoPkList) {
        return sgjsEquipEntryRecordInfoMapper.deleteSgjsEquipEntryRecordInfoByPks(sgjsEquipEntryRecordInfoPkList);
    }

    @Override
    @Transactional
    public int batchAddMap(Map<String, Object> map) {
        List<SgjsEquipEntryRecord> equipList= (List<SgjsEquipEntryRecord>)map.get("equipList");
        if(CollectionUtils.isEmpty(equipList)){
            logger.error("传参equipList空了");
            return -1;
        }
        List<SgjsEquipEntryRecordInfo> infoList= (List<SgjsEquipEntryRecordInfo>)map.get("infoList");
        if(CollectionUtils.isEmpty(infoList)){
            logger.error("传参infoList空了");
            return -2;
        }
        //1、批量修改主表实际进场数量
        for (SgjsEquipEntryRecord info:equipList) {
            info.setUpdateTime(DateUtils.getNowDate());
            info.setUpdateUser(SecurityUtils.getUserName());
        }
        sgjsEquipEntryRecordMapper.bathUpdateByList(equipList);
        //2、删除子表所有数据
        SgjsEquipEntryRecordInfo record=new SgjsEquipEntryRecordInfo();
        record.setUpdateTime(DateUtils.getNowDate());
        record.setUpdateUser(SecurityUtils.getUserId()+"");
        sgjsEquipEntryRecordInfoMapper.deleteAll(record);
        List<SgjsEquipEntryRecordInfoDetail> list=new ArrayList<>();
        for (SgjsEquipEntryRecordInfo info:infoList) {
            info.setCreateTime(DateUtils.getNowDate());
            info.setCreateUser(SecurityUtils.getUserId()+"");
            info.setCreateUserName(SecurityUtils.getSysUser().getNickName());
            List<SgjsEquipEntryRecordInfoDetail> detailList = info.getDetailList();
            list.addAll(detailList);
        }
        //3、重新添加数据
        sgjsEquipEntryRecordInfoMapper.insertSgjsEquipEntryRecordInfoList(infoList);
        //4、自检自校数据新增
        sgjsEquipEntryRecordInfoDetailService.insertSgjsEquipEntryRecordInfoDetailList(list);
        return 1;
    }
}
