package com.hhwy.sp.experiment.sgjsExperimentRecordInfoDetail.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.sp.experiment.sgjsExperimentRecordInfo.domain.SgjsExperimentRecordInfo;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.sp.experiment.sgjsExperimentRecordInfoDetail.mapper.SgjsExperimentRecordInfoDetailMapper;
import com.hhwy.sp.experiment.sgjsExperimentRecordInfoDetail.service.ISgjsExperimentRecordInfoDetailService;
import com.hhwy.sp.experiment.sgjsExperimentRecordInfoDetail.domain.SgjsExperimentRecordInfoDetail;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author lcf--自检自校表记录
 * @date 2023-12-11 15:04:20
 * @remark 
 */
@Service
public class SgjsExperimentRecordInfoDetailServiceImpl implements ISgjsExperimentRecordInfoDetailService{

    @Autowired
    private SgjsExperimentRecordInfoDetailMapper sgjsExperimentRecordInfoDetailMapper;

                                                                                                                                                                                                                                                                                                    
    public SgjsExperimentRecordInfoDetail getSgjsExperimentRecordInfoDetail(SgjsExperimentRecordInfoDetail sgjsExperimentRecordInfoDetail) {
        return sgjsExperimentRecordInfoDetailMapper.getSgjsExperimentRecordInfoDetail(sgjsExperimentRecordInfoDetail);
    }

    public List<SgjsExperimentRecordInfoDetail> getSgjsExperimentRecordInfoDetailList(SgjsExperimentRecordInfoDetail sgjsExperimentRecordInfoDetail) {
        return sgjsExperimentRecordInfoDetailMapper.getSgjsExperimentRecordInfoDetailList(sgjsExperimentRecordInfoDetail);
    }

    @Transactional
    public int insertSgjsExperimentRecordInfoDetail(SgjsExperimentRecordInfoDetail sgjsExperimentRecordInfoDetail) {
        sgjsExperimentRecordInfoDetail.setId(IdWorker.createId());
        sgjsExperimentRecordInfoDetail.setCreateUser(SecurityUtils.getUserName());
        sgjsExperimentRecordInfoDetail.setCreateTime(DateUtils.getNowDate());
        return sgjsExperimentRecordInfoDetailMapper.insertSgjsExperimentRecordInfoDetail(sgjsExperimentRecordInfoDetail);
    }

    @Override
    public int handleExperimentRecordInfoDetailData(List<SgjsExperimentRecordInfoDetail> sgjsExperimentRecordInfoDetailList, List<Long> recordIdList) {
        if(CollectionUtils.isNotEmpty(recordIdList)){
            sgjsExperimentRecordInfoDetailMapper.deleteByRecordIds(recordIdList);
        }
        List<Long> idList=new ArrayList<>();
        List<SgjsExperimentRecordInfoDetail> list = sgjsExperimentRecordInfoDetailMapper.getSgjsExperimentRecordInfoDetailList(new SgjsExperimentRecordInfoDetail());
        Map<Long, List<SgjsExperimentRecordInfoDetail>> map = list.stream().collect(Collectors.groupingBy(SgjsExperimentRecordInfoDetail::getId));
        List<SgjsExperimentRecordInfoDetail> insertList = new ArrayList<>();
        List<SgjsExperimentRecordInfoDetail> updateList = new ArrayList<>();
        for (SgjsExperimentRecordInfoDetail infoDetail : sgjsExperimentRecordInfoDetailList) {
            List<Long> infoDelIdList = infoDetail.getInfoDelIdList();
            if(CollectionUtils.isNotEmpty(infoDelIdList)){
                idList.addAll(infoDelIdList);
            }
            Long id = infoDetail.getId();
            List<SgjsExperimentRecordInfoDetail> manageList = map.get(id);
            if(org.apache.commons.collections4.CollectionUtils.isNotEmpty(manageList)){
                infoDetail.setUpdateTime(DateUtils.getNowDate());
                updateList.add(infoDetail);
            }else {
                infoDetail.setDelFlag("0");
                infoDetail.setCreateUser(SecurityUtils.getUserName());
                infoDetail.setCreateTime(DateUtils.getNowDate());
                insertList.add(infoDetail);
            }
        }
        if(CollectionUtils.isNotEmpty(idList)){
            sgjsExperimentRecordInfoDetailMapper.deleteSgjsExperimentRecordInfoDetailByPks(idList);
        }
        if(org.apache.commons.collections4.CollectionUtils.isNotEmpty(updateList)){
            sgjsExperimentRecordInfoDetailMapper.updateSgjsExperimentRecordInfoDetailList(updateList);
        }
        if(org.apache.commons.collections4.CollectionUtils.isNotEmpty(insertList)){
            sgjsExperimentRecordInfoDetailMapper.insertSgjsExperimentRecordInfoDetailList(insertList);
        }
        return 1;
    }

    @Transactional
    public int insertSgjsExperimentRecordInfoDetailList(List<SgjsExperimentRecordInfoDetail> sgjsExperimentRecordInfoDetailList) {
        List<Long> idList=new ArrayList<>();
        List<SgjsExperimentRecordInfoDetail> list = sgjsExperimentRecordInfoDetailMapper.getSgjsExperimentRecordInfoDetailList(new SgjsExperimentRecordInfoDetail());
        Map<Long, List<SgjsExperimentRecordInfoDetail>> map = list.stream().collect(Collectors.groupingBy(SgjsExperimentRecordInfoDetail::getId));
        List<SgjsExperimentRecordInfoDetail> insertList = new ArrayList<>();
        List<SgjsExperimentRecordInfoDetail> updateList = new ArrayList<>();
        for (SgjsExperimentRecordInfoDetail infoDetail : sgjsExperimentRecordInfoDetailList) {
            List<Long> infoDelIdList = infoDetail.getInfoDelIdList();
            if(CollectionUtils.isNotEmpty(infoDelIdList)){
                idList.addAll(infoDelIdList);
            }
            Long id = infoDetail.getId();
            List<SgjsExperimentRecordInfoDetail> manageList = map.get(id);
            if(org.apache.commons.collections4.CollectionUtils.isNotEmpty(manageList)){
                infoDetail.setUpdateTime(DateUtils.getNowDate());
                updateList.add(infoDetail);
            }else {
                infoDetail.setDelFlag("0");
                infoDetail.setCreateUser(SecurityUtils.getUserName());
                infoDetail.setCreateTime(DateUtils.getNowDate());
                insertList.add(infoDetail);
            }
        }
        if(CollectionUtils.isNotEmpty(idList)){
            sgjsExperimentRecordInfoDetailMapper.deleteSgjsExperimentRecordInfoDetailByPks(idList);
        }
        if(org.apache.commons.collections4.CollectionUtils.isNotEmpty(updateList)){
            sgjsExperimentRecordInfoDetailMapper.updateSgjsExperimentRecordInfoDetailList(updateList);
        }
        if(org.apache.commons.collections4.CollectionUtils.isNotEmpty(insertList)){
            sgjsExperimentRecordInfoDetailMapper.insertSgjsExperimentRecordInfoDetailList(insertList);
        }
        return 1;
    }

    @Transactional
    public int updateSgjsExperimentRecordInfoDetail(SgjsExperimentRecordInfoDetail sgjsExperimentRecordInfoDetail) {
        sgjsExperimentRecordInfoDetail.setUpdateUser(SecurityUtils.getUserName());
        sgjsExperimentRecordInfoDetail.setUpdateTime(DateUtils.getNowDate());
        return sgjsExperimentRecordInfoDetailMapper.updateSgjsExperimentRecordInfoDetail(sgjsExperimentRecordInfoDetail);
    }

            @Transactional
        public int updateSgjsExperimentRecordInfoDetailList(List<SgjsExperimentRecordInfoDetail> sgjsExperimentRecordInfoDetailList) {
            for (SgjsExperimentRecordInfoDetail sgjsExperimentRecordInfoDetail : sgjsExperimentRecordInfoDetailList) {
                sgjsExperimentRecordInfoDetail.setUpdateUser(SecurityUtils.getUserName());
                sgjsExperimentRecordInfoDetail.setUpdateTime(DateUtils.getNowDate());
            }
            return sgjsExperimentRecordInfoDetailMapper.updateSgjsExperimentRecordInfoDetailList(sgjsExperimentRecordInfoDetailList);
        }
    
    @Transactional
    public int deleteSgjsExperimentRecordInfoDetail(SgjsExperimentRecordInfoDetail sgjsExperimentRecordInfoDetail) {
        sgjsExperimentRecordInfoDetail.setUpdateUser(SecurityUtils.getUserName());
        sgjsExperimentRecordInfoDetail.setUpdateTime(DateUtils.getNowDate());
        return sgjsExperimentRecordInfoDetailMapper.deleteSgjsExperimentRecordInfoDetail(sgjsExperimentRecordInfoDetail);
    }

            @Transactional
        public int deleteSgjsExperimentRecordInfoDetailByPks(List<Long> sgjsExperimentRecordInfoDetailPkList) {
            return sgjsExperimentRecordInfoDetailMapper.deleteSgjsExperimentRecordInfoDetailByPks(sgjsExperimentRecordInfoDetailPkList);
        }

    @Override
    public int deleteByRecordIds(List<Long> delIdList) {
        return sgjsExperimentRecordInfoDetailMapper.deleteByRecordIds(delIdList);
    }
}
