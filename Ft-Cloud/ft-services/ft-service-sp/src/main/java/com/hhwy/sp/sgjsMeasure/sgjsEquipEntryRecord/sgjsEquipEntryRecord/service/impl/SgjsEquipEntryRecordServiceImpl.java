package com.hhwy.sp.sgjsMeasure.sgjsEquipEntryRecord.sgjsEquipEntryRecord.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.feign.service.PmServiceApi;
import com.hhwy.sp.sgjsMeasure.sgjsEquipEntryRecord.sgjsEquipEntryRecord.domain.SgjsEquipEntryRecord;
import com.hhwy.sp.sgjsMeasure.sgjsEquipEntryRecord.sgjsEquipEntryRecord.mapper.SgjsEquipEntryRecordMapper;
import com.hhwy.sp.sgjsMeasure.sgjsEquipEntryRecord.sgjsEquipEntryRecord.service.ISgjsEquipEntryRecordService;
import com.hhwy.utils.date.FtDateUtils;
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

/**
 * @author lcf   测量管理--测试设备进场记录
 * @date 2023-12-08 10:47:00
 * @remark
 */
@Service
public class SgjsEquipEntryRecordServiceImpl implements ISgjsEquipEntryRecordService{

    @Autowired
    private SgjsEquipEntryRecordMapper sgjsEquipEntryRecordMapper;
    @Autowired
    private PmServiceApi pmServiceApi;

    private Logger logger= LoggerFactory.getLogger(SgjsEquipEntryRecordServiceImpl.class);

    public SgjsEquipEntryRecord getSgjsEquipEntryRecord(SgjsEquipEntryRecord sgjsEquipEntryRecord) {
        return sgjsEquipEntryRecordMapper.getSgjsEquipEntryRecord(sgjsEquipEntryRecord);
    }

    public List<SgjsEquipEntryRecord> getSgjsEquipEntryRecordList(SgjsEquipEntryRecord sgjsEquipEntryRecord) {
        //筛选条件
        if(StringUtils.isNotEmpty(sgjsEquipEntryRecord.getEntryDateStr())){
            String actualDateStr = sgjsEquipEntryRecord.getEntryDateStr();
            String[] split = actualDateStr.split("~");
            String begin=split[0].replaceAll("(?:年|月|日)", "-");
            String end=split[1].replaceAll("(?:年|月|日)", "-");
            sgjsEquipEntryRecord.setEntryDateBegin(FtDateUtils.parseDate(begin));
            sgjsEquipEntryRecord.setEntryDateEnd(FtDateUtils.parseDate(end));
        }
        return sgjsEquipEntryRecordMapper.getSgjsEquipEntryRecordList(sgjsEquipEntryRecord);
    }

    @Transactional
    public int insertSgjsEquipEntryRecord(SgjsEquipEntryRecord sgjsEquipEntryRecord) {
        sgjsEquipEntryRecord.setId(IdWorker.createId());
        sgjsEquipEntryRecord.setCreateUser(SecurityUtils.getUserName());
        sgjsEquipEntryRecord.setCreateTime(DateUtils.getNowDate());
        return sgjsEquipEntryRecordMapper.insertSgjsEquipEntryRecord(sgjsEquipEntryRecord);
    }

    @Transactional
    public int insertSgjsEquipEntryRecordList(List<SgjsEquipEntryRecord> sgjsEquipEntryRecordList) {
        for (SgjsEquipEntryRecord sgjsEquipEntryRecord : sgjsEquipEntryRecordList) {
            sgjsEquipEntryRecord.setId(IdWorker.createId());
            sgjsEquipEntryRecord.setCreateUser(SecurityUtils.getUserName());
            sgjsEquipEntryRecord.setCreateTime(DateUtils.getNowDate());
        }
        return sgjsEquipEntryRecordMapper.insertSgjsEquipEntryRecordList(sgjsEquipEntryRecordList);
    }

    @Transactional
    public int updateSgjsEquipEntryRecord(SgjsEquipEntryRecord sgjsEquipEntryRecord) {
        sgjsEquipEntryRecord.setUpdateUser(SecurityUtils.getUserName());
        sgjsEquipEntryRecord.setUpdateTime(DateUtils.getNowDate());
        return sgjsEquipEntryRecordMapper.updateSgjsEquipEntryRecord(sgjsEquipEntryRecord);
    }

    @Transactional
    public int updateSgjsEquipEntryRecordList(List<SgjsEquipEntryRecord> sgjsEquipEntryRecordList) {
        for (SgjsEquipEntryRecord sgjsEquipEntryRecord : sgjsEquipEntryRecordList) {
            sgjsEquipEntryRecord.setUpdateUser(SecurityUtils.getUserName());
            sgjsEquipEntryRecord.setUpdateTime(DateUtils.getNowDate());
        }
        return sgjsEquipEntryRecordMapper.updateSgjsEquipEntryRecordList(sgjsEquipEntryRecordList);
    }

    @Transactional
    public int deleteSgjsEquipEntryRecord(SgjsEquipEntryRecord sgjsEquipEntryRecord) {
        sgjsEquipEntryRecord.setUpdateUser(SecurityUtils.getUserName());
        sgjsEquipEntryRecord.setUpdateTime(DateUtils.getNowDate());
        return sgjsEquipEntryRecordMapper.deleteSgjsEquipEntryRecord(sgjsEquipEntryRecord);
    }

    @Transactional
    public int deleteSgjsEquipEntryRecordByPks(List<Long> sgjsEquipEntryRecordPkList) {
        return sgjsEquipEntryRecordMapper.deleteSgjsEquipEntryRecordByPks(sgjsEquipEntryRecordPkList);
    }

    @Override
    public AjaxResult sync() {
        AjaxResult result = pmServiceApi.qqchMeasureExpEquList();
        List<SgjsEquipEntryRecord> list=new ArrayList<>();
        if(result.get("code").toString().equals("200")){
            JSONObject data = JSONObject.parseObject(JSONObject.toJSONString(result.get("data")));
            List<LinkedHashMap> map=(List<LinkedHashMap>)data.get("measureList");
            if(CollectionUtils.isEmpty(map)){
                return AjaxResult.error("同步转换异常");
            }

            return AjaxResult.success(list);
        }
        logger.error("同步3.6.4异常");
        return null;
    }
}
