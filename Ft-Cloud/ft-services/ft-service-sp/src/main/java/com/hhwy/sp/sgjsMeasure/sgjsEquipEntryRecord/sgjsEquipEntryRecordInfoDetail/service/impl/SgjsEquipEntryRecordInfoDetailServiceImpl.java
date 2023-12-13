package com.hhwy.sp.sgjsMeasure.sgjsEquipEntryRecord.sgjsEquipEntryRecordInfoDetail.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.sp.sgjsMeasure.sgjsEquipEntryRecord.sgjsEquipEntryRecordInfo.domain.SgjsEquipEntryRecordInfo;
import com.hhwy.sp.sgjsMeasure.sgjsEquipEntryRecord.sgjsEquipEntryRecordInfo.mapper.SgjsEquipEntryRecordInfoMapper;
import com.hhwy.sp.sgjsMeasure.sgjsEquipEntryRecord.sgjsEquipEntryRecordInfoDetail.domain.SgjsEquipEntryRecordInfoDetail;
import com.hhwy.sp.sgjsMeasure.sgjsEquipEntryRecord.sgjsEquipEntryRecordInfoDetail.mapper.SgjsEquipEntryRecordInfoDetailMapper;
import com.hhwy.sp.sgjsMeasure.sgjsEquipEntryRecord.sgjsEquipEntryRecordInfoDetail.service.ISgjsEquipEntryRecordInfoDetailService;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author lcf   测量管理--测试设备进场记录
 * @date 2023-12-08 10:49:49
 * @remark
 */
@Service
public class SgjsEquipEntryRecordInfoDetailServiceImpl implements ISgjsEquipEntryRecordInfoDetailService{

    @Autowired
    private SgjsEquipEntryRecordInfoDetailMapper sgjsEquipEntryRecordInfoDetailMapper;
    @Autowired
    private SgjsEquipEntryRecordInfoMapper infoMapper;


    public SgjsEquipEntryRecordInfoDetail getSgjsEquipEntryRecordInfoDetail(SgjsEquipEntryRecordInfoDetail sgjsEquipEntryRecordInfoDetail) {
        return sgjsEquipEntryRecordInfoDetailMapper.getSgjsEquipEntryRecordInfoDetail(sgjsEquipEntryRecordInfoDetail);
    }

    public List<SgjsEquipEntryRecordInfoDetail> getSgjsEquipEntryRecordInfoDetailList(SgjsEquipEntryRecordInfoDetail sgjsEquipEntryRecordInfoDetail) {
        return sgjsEquipEntryRecordInfoDetailMapper.getSgjsEquipEntryRecordInfoDetailList(sgjsEquipEntryRecordInfoDetail);
    }

    @Transactional
    public int insertSgjsEquipEntryRecordInfoDetail(SgjsEquipEntryRecordInfoDetail sgjsEquipEntryRecordInfoDetail) {
        sgjsEquipEntryRecordInfoDetail.setId(IdWorker.createId());
        sgjsEquipEntryRecordInfoDetail.setCreateUser(SecurityUtils.getUserName());
        sgjsEquipEntryRecordInfoDetail.setCreateTime(DateUtils.getNowDate());
        return sgjsEquipEntryRecordInfoDetailMapper.insertSgjsEquipEntryRecordInfoDetail(sgjsEquipEntryRecordInfoDetail);
    }

    @Transactional
    public int insertSgjsEquipEntryRecordInfoDetailList(List<SgjsEquipEntryRecordInfoDetail> sgjsEquipEntryRecordInfoDetailList) {
        //添加之前先删掉库中原有数据
        SgjsEquipEntryRecordInfoDetail detail=new SgjsEquipEntryRecordInfoDetail();
        detail.setUpdateTime(DateUtils.getNowDate());
        detail.setUpdateUser(SecurityUtils.getUserId()+"");
        sgjsEquipEntryRecordInfoDetailMapper.deleteAll(detail);
        for (SgjsEquipEntryRecordInfoDetail sgjsEquipEntryRecordInfoDetail : sgjsEquipEntryRecordInfoDetailList) {
            sgjsEquipEntryRecordInfoDetail.setId(IdWorker.createId());
            sgjsEquipEntryRecordInfoDetail.setCreateUser(SecurityUtils.getUserName());
            sgjsEquipEntryRecordInfoDetail.setCreateTime(DateUtils.getNowDate());
        }
        return sgjsEquipEntryRecordInfoDetailMapper.insertSgjsEquipEntryRecordInfoDetailList(sgjsEquipEntryRecordInfoDetailList);
    }

    @Transactional
    public int updateSgjsEquipEntryRecordInfoDetail(SgjsEquipEntryRecordInfoDetail sgjsEquipEntryRecordInfoDetail) {
        sgjsEquipEntryRecordInfoDetail.setUpdateUser(SecurityUtils.getUserName());
        sgjsEquipEntryRecordInfoDetail.setUpdateTime(DateUtils.getNowDate());
        return sgjsEquipEntryRecordInfoDetailMapper.updateSgjsEquipEntryRecordInfoDetail(sgjsEquipEntryRecordInfoDetail);
    }

    @Transactional
    public int updateSgjsEquipEntryRecordInfoDetailList(List<SgjsEquipEntryRecordInfoDetail> sgjsEquipEntryRecordInfoDetailList) {
        for (SgjsEquipEntryRecordInfoDetail sgjsEquipEntryRecordInfoDetail : sgjsEquipEntryRecordInfoDetailList) {
            sgjsEquipEntryRecordInfoDetail.setUpdateUser(SecurityUtils.getUserName());
            sgjsEquipEntryRecordInfoDetail.setUpdateTime(DateUtils.getNowDate());
        }
        return sgjsEquipEntryRecordInfoDetailMapper.updateSgjsEquipEntryRecordInfoDetailList(sgjsEquipEntryRecordInfoDetailList);
    }

    @Transactional
    public int deleteSgjsEquipEntryRecordInfoDetail(SgjsEquipEntryRecordInfoDetail sgjsEquipEntryRecordInfoDetail) {
        sgjsEquipEntryRecordInfoDetail.setUpdateUser(SecurityUtils.getUserName());
        sgjsEquipEntryRecordInfoDetail.setUpdateTime(DateUtils.getNowDate());
        return sgjsEquipEntryRecordInfoDetailMapper.deleteSgjsEquipEntryRecordInfoDetail(sgjsEquipEntryRecordInfoDetail);
    }

    @Transactional
    public int deleteSgjsEquipEntryRecordInfoDetailByPks(List<Long> sgjsEquipEntryRecordInfoDetailPkList) {
        return sgjsEquipEntryRecordInfoDetailMapper.deleteSgjsEquipEntryRecordInfoDetailByPks(sgjsEquipEntryRecordInfoDetailPkList);
    }

    @Override
    public void batchAddMap(Map<String, Object> map) {
        JSONArray jsonArray = JSONArray.parseArray(JSONObject.toJSONString(map.get("infoList")));
        JSONObject o = (JSONObject)jsonArray.get(0);
        Long infoId = ObjectUtils.toLong(o.get("infoId"));
        Date checkData = ObjectUtils.toDate(o.get("checkData"));
        SgjsEquipEntryRecordInfo info=new SgjsEquipEntryRecordInfo();
        info.setId(infoId);
        info.setCheckDate(checkData);
        info.setUpdateUser(SecurityUtils.getUserId()+"");
        info.setUpdateTime(DateUtils.getNowDate());
        infoMapper.updateSgjsEquipEntryRecordInfo(info);
        JSONArray array = JSONArray.parseArray(JSONObject.toJSONString(map.get("detailList")));
        List<SgjsEquipEntryRecordInfoDetail> infoList=new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            SgjsEquipEntryRecordInfoDetail detail = JSONObject.parseObject(JSONObject.toJSONString(array.get(i)), SgjsEquipEntryRecordInfoDetail.class);
            detail.setCreateTime(DateUtils.getNowDate());
            detail.setCreateUser(SecurityUtils.getUserId()+"");
            infoList.add(detail);
        }

        //删除之前数据
        sgjsEquipEntryRecordInfoDetailMapper.deleteAllData();
        //全量新增
        sgjsEquipEntryRecordInfoDetailMapper.insertSgjsEquipEntryRecordInfoDetailList(infoList);
    }
}
