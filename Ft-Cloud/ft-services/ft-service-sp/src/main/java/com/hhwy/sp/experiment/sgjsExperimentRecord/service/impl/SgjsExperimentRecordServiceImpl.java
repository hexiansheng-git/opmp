package com.hhwy.sp.experiment.sgjsExperimentRecord.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.feign.service.PmServiceApi;
import com.hhwy.sp.experiment.sgjsExperimentRecord.domain.SgjsExperimentRecord;
import com.hhwy.sp.experiment.sgjsExperimentRecord.mapper.SgjsExperimentRecordMapper;
import com.hhwy.sp.experiment.sgjsExperimentRecord.service.ISgjsExperimentRecordService;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lcf--试验设备进场记录
 * @date 2023-12-11 15:03:30
 * @remark
 */
@Service
public class SgjsExperimentRecordServiceImpl implements ISgjsExperimentRecordService{

    @Autowired
    private SgjsExperimentRecordMapper sgjsExperimentRecordMapper;
    @Autowired
    private PmServiceApi pmServiceApi;


    public SgjsExperimentRecord getSgjsExperimentRecord(SgjsExperimentRecord sgjsExperimentRecord) {
        return sgjsExperimentRecordMapper.getSgjsExperimentRecord(sgjsExperimentRecord);
    }

    public List<SgjsExperimentRecord> getSgjsExperimentRecordList(SgjsExperimentRecord sgjsExperimentRecord) {
        return sgjsExperimentRecordMapper.getSgjsExperimentRecordList(sgjsExperimentRecord);
    }

    @Transactional
    public int insertSgjsExperimentRecord(SgjsExperimentRecord sgjsExperimentRecord) {
        sgjsExperimentRecord.setId(IdWorker.createId());
        sgjsExperimentRecord.setCreateUser(SecurityUtils.getUserName());
        sgjsExperimentRecord.setCreateTime(DateUtils.getNowDate());
        return sgjsExperimentRecordMapper.insertSgjsExperimentRecord(sgjsExperimentRecord);
    }

    @Transactional
    public int insertSgjsExperimentRecordList(List<SgjsExperimentRecord> sgjsExperimentRecordList) {
        for (SgjsExperimentRecord sgjsExperimentRecord : sgjsExperimentRecordList) {
            sgjsExperimentRecord.setId(IdWorker.createId());
            sgjsExperimentRecord.setCreateUser(SecurityUtils.getUserName());
            sgjsExperimentRecord.setCreateTime(DateUtils.getNowDate());
        }
        return sgjsExperimentRecordMapper.insertSgjsExperimentRecordList(sgjsExperimentRecordList);
    }

    @Transactional
    public int updateSgjsExperimentRecord(SgjsExperimentRecord sgjsExperimentRecord) {
        sgjsExperimentRecord.setUpdateUser(SecurityUtils.getUserName());
        sgjsExperimentRecord.setUpdateTime(DateUtils.getNowDate());
        return sgjsExperimentRecordMapper.updateSgjsExperimentRecord(sgjsExperimentRecord);
    }

    @Transactional
    public int updateSgjsExperimentRecordList(List<SgjsExperimentRecord> sgjsExperimentRecordList) {
        for (SgjsExperimentRecord sgjsExperimentRecord : sgjsExperimentRecordList) {
            sgjsExperimentRecord.setUpdateUser(SecurityUtils.getUserName());
            sgjsExperimentRecord.setUpdateTime(DateUtils.getNowDate());
        }
        return sgjsExperimentRecordMapper.updateSgjsExperimentRecordList(sgjsExperimentRecordList);
    }

    @Transactional
    public int deleteSgjsExperimentRecord(SgjsExperimentRecord sgjsExperimentRecord) {
        sgjsExperimentRecord.setUpdateUser(SecurityUtils.getUserName());
        sgjsExperimentRecord.setUpdateTime(DateUtils.getNowDate());
        return sgjsExperimentRecordMapper.deleteSgjsExperimentRecord(sgjsExperimentRecord);
    }

    @Transactional
    public int deleteSgjsExperimentRecordByPks(List<Long> sgjsExperimentRecordPkList) {
        return sgjsExperimentRecordMapper.deleteSgjsExperimentRecordByPks(sgjsExperimentRecordPkList);
    }

    @Override
    @Transactional
    public AjaxResult sync() {
        AjaxResult result = pmServiceApi.feignExperimentList();
        if(!result.get("code").toString().equals("200")){
            AjaxResult.error("同步异常");
        }
        JSONObject data = JSONObject.parseObject(JSONObject.toJSONString(result.get("data")));
        JSONArray array = JSONObject.parseArray(JSONObject.toJSONString(data.get("experimentList")));
        List<SgjsExperimentRecord> list=new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            JSONObject object = JSONObject.parseObject(JSONObject.toJSONString(array.get(i)));
            SgjsExperimentRecord info=new SgjsExperimentRecord();
            info.setMaterialCode(ObjectUtils.toString(object.get("equCode")));
            info.setMaterialName(ObjectUtils.toString(object.get("equName")));
            info.setCategoryName(ObjectUtils.toString(object.get("equTypeName")));
            info.setMaterialSpec(ObjectUtils.toString(object.get("spec")));
            info.setSource(ObjectUtils.toString(object.get("source")));
            info.setNum(Integer.parseInt(object.get("reqNum").toString()));
            info.setEntryDate(ObjectUtils.toDate(object.get("reqInDate")));
            info.setCreateTime(DateUtils.getNowDate());
            info.setCreateUser(SecurityUtils.getUserId()+"");
            info.setId(IdWorker.createId());
            info.setProjectId(ObjectUtils.toLong(object.get("")));
            list.add(info);
        }
        //同步完入库
        if(!CollectionUtils.isEmpty(list)){
            sgjsExperimentRecordMapper.insertSgjsExperimentRecordList(list);
        }
        return AjaxResult.success(list);
    }
}
