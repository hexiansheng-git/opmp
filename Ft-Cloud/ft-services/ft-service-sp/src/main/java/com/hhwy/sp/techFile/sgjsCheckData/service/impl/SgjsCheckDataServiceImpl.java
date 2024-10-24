package com.hhwy.sp.techFile.sgjsCheckData.service.impl;

import java.util.ArrayList;
import java.util.List;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.sp.techData.sgjsTechnicalData.domain.SgjsTechnicalData;
import com.hhwy.sp.techFile.sgjsTechnicalFileBlueprint.domain.SgjsTechnicalFileBlueprint;
import com.hhwy.utils.tree.TreeUtil;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.sp.techFile.sgjsCheckData.mapper.SgjsCheckDataMapper;
import com.hhwy.sp.techFile.sgjsCheckData.service.ISgjsCheckDataService;
import com.hhwy.sp.techFile.sgjsCheckData.domain.SgjsCheckData;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author xuzl
 * @date 2024-10-18 15:52:00
 * @remark
 */
@Service
public class SgjsCheckDataServiceImpl implements ISgjsCheckDataService {

    @Autowired
    private SgjsCheckDataMapper sgjsCheckDataMapper;


    public SgjsCheckData getSgjsCheckData(SgjsCheckData sgjsCheckData) {
        return sgjsCheckDataMapper.getSgjsCheckData(sgjsCheckData);
    }

    public List<SgjsCheckData> getSgjsCheckDataList(SgjsCheckData sgjsCheckData) {
        return sgjsCheckDataMapper.getSgjsCheckDataList(sgjsCheckData);
    }

    @Transactional
    public int insertSgjsCheckData(SgjsCheckData sgjsCheckData) {
        sgjsCheckData.setId(IdWorker.createId());
        sgjsCheckData.setCreateUser(SecurityUtils.getUserName());
        sgjsCheckData.setCreateTime(DateUtils.getNowDate());
        return sgjsCheckDataMapper.insertSgjsCheckData(sgjsCheckData);
    }

    @Transactional
    public int insertSgjsCheckDataList(List<SgjsCheckData> sgjsCheckDataList) {
        if(CollUtil.isEmpty(sgjsCheckDataList)){
            return 1;
        }
        List<SgjsCheckData> addList = new ArrayList<>();
        List<SgjsCheckData> updateList = new ArrayList<>();

        for (SgjsCheckData sgjsCheckData : sgjsCheckDataList) {
            String isAdd = sgjsCheckData.getIsAdd();
            if(StrUtil.isBlank(isAdd)) {
                sgjsCheckData.setId(IdWorker.createId());
                sgjsCheckData.setCreateUser(SecurityUtils.getUserName());
                sgjsCheckData.setCreateTime(DateUtils.getNowDate());
                updateList.add(sgjsCheckData);
                continue;
            }
            sgjsCheckData.setCreateUser(SecurityUtils.getUserName());
            sgjsCheckData.setCreateTime(DateUtils.getNowDate());
            addList.add(sgjsCheckData);
        }
        int i = 0;
        if(CollectionUtils.isNotEmpty(addList)) {
            i += sgjsCheckDataMapper.insertSgjsCheckDataList(addList);
        }
        if(CollectionUtils.isNotEmpty(updateList)) {
            i += sgjsCheckDataMapper.updateSgjsCheckDataList(updateList);
        }
        return i;
    }

    @Transactional
    public int updateSgjsCheckData(SgjsCheckData sgjsCheckData) {
        sgjsCheckData.setUpdateUser(SecurityUtils.getUserName());
        sgjsCheckData.setUpdateTime(DateUtils.getNowDate());
        return sgjsCheckDataMapper.updateSgjsCheckData(sgjsCheckData);
    }

    @Transactional
    public int updateSgjsCheckDataList(List<SgjsCheckData> sgjsCheckDataList) {
        for (SgjsCheckData sgjsCheckData : sgjsCheckDataList) {
            sgjsCheckData.setUpdateUser(SecurityUtils.getUserName());
            sgjsCheckData.setUpdateTime(DateUtils.getNowDate());
        }
        return sgjsCheckDataMapper.updateSgjsCheckDataList(sgjsCheckDataList);
    }

    @Transactional
    public int deleteSgjsCheckData(SgjsCheckData sgjsCheckData) {
        sgjsCheckData.setUpdateUser(SecurityUtils.getUserName());
        sgjsCheckData.setUpdateTime(DateUtils.getNowDate());
        return sgjsCheckDataMapper.deleteSgjsCheckData(sgjsCheckData);
    }

    @Transactional
    public int deleteSgjsCheckDataByPks(List<Long> sgjsCheckDataPkList) {
        return sgjsCheckDataMapper.deleteSgjsCheckDataByPks(sgjsCheckDataPkList);
    }
}
