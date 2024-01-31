package com.hhwy.sp.common.shjsAuthenticateEvaluate.service.impl;

import java.util.Arrays;
import java.util.List;

import cn.hutool.core.collection.CollUtil;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.sp.common.sgjsExpertLibrary.domain.SgjsExpertLibrary;
import com.hhwy.utils.common.CommonAssert;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.sp.common.shjsAuthenticateEvaluate.mapper.ShjsAuthenticateEvaluateMapper;
import com.hhwy.sp.common.shjsAuthenticateEvaluate.service.IShjsAuthenticateEvaluateService;
import com.hhwy.sp.common.shjsAuthenticateEvaluate.domain.ShjsAuthenticateEvaluate;
import com.hhwy.utils.idworker.IdWorker;

/**
 * 功能描述: 科技管理 - 鉴定或评价
 * 作者: fushudong
 * 时间: 2024/1/25
 */
@Service
public class ShjsAuthenticateEvaluateServiceImpl implements IShjsAuthenticateEvaluateService {

    @Autowired
    private ShjsAuthenticateEvaluateMapper shjsAuthenticateEvaluateMapper;


    public ShjsAuthenticateEvaluate getShjsAuthenticateEvaluate(ShjsAuthenticateEvaluate shjsAuthenticateEvaluate) {
        return shjsAuthenticateEvaluateMapper.getShjsAuthenticateEvaluate(shjsAuthenticateEvaluate);
    }

    public List<ShjsAuthenticateEvaluate> getShjsAuthenticateEvaluateList(ShjsAuthenticateEvaluate shjsAuthenticateEvaluate) {
        return shjsAuthenticateEvaluateMapper.getShjsAuthenticateEvaluateList(shjsAuthenticateEvaluate);
    }

    public void saveEvaluate(Long foreignId, String belongBusiness, List<ShjsAuthenticateEvaluate> saveList) {
        CommonAssert.notNull(foreignId,"外键不能为空！");
        CommonAssert.notBlank(belongBusiness,"所属业务不能为空！");
        //根据外键删除数据
        ShjsAuthenticateEvaluate delParam = new ShjsAuthenticateEvaluate();
        delParam.setForeignId(foreignId);
        shjsAuthenticateEvaluateMapper.deleteShjsAuthenticateEvaluate(delParam);

        if(CollectionUtils.isEmpty(saveList)){
            return;
        }
        //插入数据
        for (ShjsAuthenticateEvaluate evaluate : saveList) {
            evaluate.setId(IdWorker.createId());
            evaluate.setForeignId(foreignId);
            evaluate.setBelongBusiness(belongBusiness);
            evaluate.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            evaluate.setCreateUserName(SecurityUtils.getUserName());
            evaluate.setCreateTime(DateUtils.getNowDate());
        }
        shjsAuthenticateEvaluateMapper.insertShjsAuthenticateEvaluateList(saveList);
    }

    @Transactional
    public int insertShjsAuthenticateEvaluate(ShjsAuthenticateEvaluate shjsAuthenticateEvaluate) {
        shjsAuthenticateEvaluate.setId(IdWorker.createId());
        shjsAuthenticateEvaluate.setCreateUser(SecurityUtils.getUserName());
        shjsAuthenticateEvaluate.setCreateTime(DateUtils.getNowDate());
        return shjsAuthenticateEvaluateMapper.insertShjsAuthenticateEvaluate(shjsAuthenticateEvaluate);
    }

    @Transactional
    public int insertShjsAuthenticateEvaluateList(List<ShjsAuthenticateEvaluate> shjsAuthenticateEvaluateList) {
        if (CollUtil.isEmpty(shjsAuthenticateEvaluateList)) return 0;
        for (ShjsAuthenticateEvaluate shjsAuthenticateEvaluate : shjsAuthenticateEvaluateList) {
            shjsAuthenticateEvaluate.setId(IdWorker.createId());
            shjsAuthenticateEvaluate.setCreateUser(SecurityUtils.getUserName());
            shjsAuthenticateEvaluate.setCreateTime(DateUtils.getNowDate());
        }
        return shjsAuthenticateEvaluateMapper.insertShjsAuthenticateEvaluateList(shjsAuthenticateEvaluateList);
    }

    @Transactional
    public int updateShjsAuthenticateEvaluate(ShjsAuthenticateEvaluate shjsAuthenticateEvaluate) {
        shjsAuthenticateEvaluate.setUpdateUser(SecurityUtils.getUserName());
        shjsAuthenticateEvaluate.setUpdateTime(DateUtils.getNowDate());
        return shjsAuthenticateEvaluateMapper.updateShjsAuthenticateEvaluate(shjsAuthenticateEvaluate);
    }

    @Transactional
    public int updateShjsAuthenticateEvaluateList(List<ShjsAuthenticateEvaluate> shjsAuthenticateEvaluateList) {
        for (ShjsAuthenticateEvaluate shjsAuthenticateEvaluate : shjsAuthenticateEvaluateList) {
            shjsAuthenticateEvaluate.setUpdateUser(SecurityUtils.getUserName());
            shjsAuthenticateEvaluate.setUpdateTime(DateUtils.getNowDate());
        }
        return shjsAuthenticateEvaluateMapper.updateShjsAuthenticateEvaluateList(shjsAuthenticateEvaluateList);
    }

    @Transactional
    public int deleteShjsAuthenticateEvaluate(ShjsAuthenticateEvaluate shjsAuthenticateEvaluate) {
        return shjsAuthenticateEvaluateMapper.deleteShjsAuthenticateEvaluate(shjsAuthenticateEvaluate);
    }

    @Transactional
    public int deleteShjsAuthenticateEvaluateByPks(List<Long> shjsAuthenticateEvaluatePkList) {
        return shjsAuthenticateEvaluateMapper.deleteShjsAuthenticateEvaluateByPks(shjsAuthenticateEvaluatePkList);
    }

    @Override
    public int saveShjsAuthenticateEvaluateList(Long foreignId, String belongBusiness, List<ShjsAuthenticateEvaluate> shjsAuthenticateEvaluateList) {
        CommonAssert.notNull(foreignId,"外键不能为空！");
        CommonAssert.notBlank(belongBusiness,"所属业务不能为空！");
        ShjsAuthenticateEvaluate shjsAuthenticateEvaluate = new ShjsAuthenticateEvaluate();
        shjsAuthenticateEvaluate.setForeignId(foreignId);
        shjsAuthenticateEvaluateMapper.deleteShjsAuthenticateEvaluate(shjsAuthenticateEvaluate);
        if(CollectionUtils.isNotEmpty(shjsAuthenticateEvaluateList)) {
            for(ShjsAuthenticateEvaluate shjsAuthenticateEvaluate1 : shjsAuthenticateEvaluateList) {
                shjsAuthenticateEvaluate1.setId(IdWorker.createId());
                shjsAuthenticateEvaluate1.setForeignId(foreignId);
                shjsAuthenticateEvaluate1.setBelongBusiness(belongBusiness);
                shjsAuthenticateEvaluate1.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                shjsAuthenticateEvaluate1.setCreateUserName(SecurityUtils.getUserName());
                shjsAuthenticateEvaluate1.setCreateTime(DateUtils.getNowDate());
            }
            return shjsAuthenticateEvaluateMapper.insertShjsAuthenticateEvaluateList(shjsAuthenticateEvaluateList);
        }
        return 0;
    }

    @Override
    public List<ShjsAuthenticateEvaluate> getListByForeignIds(Long[] foreignIds) {
        CommonAssert.notNull(foreignIds,"外键不能为空！");
        return shjsAuthenticateEvaluateMapper.getListByForeignList(Arrays.asList(foreignIds));
    }
}
