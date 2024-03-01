package com.hhwy.sp.common.sgjsAuthenticateEvaluate.service.impl;

import java.util.Arrays;
import java.util.List;

import cn.hutool.core.collection.CollUtil;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.utils.common.CommonAssert;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.sp.common.sgjsAuthenticateEvaluate.mapper.SgjsAuthenticateEvaluateMapper;
import com.hhwy.sp.common.sgjsAuthenticateEvaluate.service.ISgjsAuthenticateEvaluateService;
import com.hhwy.sp.common.sgjsAuthenticateEvaluate.domain.SgjsAuthenticateEvaluate;
import com.hhwy.utils.idworker.IdWorker;

/**
 * 功能描述: 科技管理 - 鉴定或评价
 * 作者: fushudong
 * 时间: 2024/1/25
 */
@Service
public class SgjsAuthenticateEvaluateServiceImpl implements ISgjsAuthenticateEvaluateService {

    @Autowired
    private SgjsAuthenticateEvaluateMapper shjsAuthenticateEvaluateMapper;


    public SgjsAuthenticateEvaluate getShjsAuthenticateEvaluate(SgjsAuthenticateEvaluate shjsAuthenticateEvaluate) {
        return shjsAuthenticateEvaluateMapper.getShjsAuthenticateEvaluate(shjsAuthenticateEvaluate);
    }

    public List<SgjsAuthenticateEvaluate> getShjsAuthenticateEvaluateList(SgjsAuthenticateEvaluate shjsAuthenticateEvaluate) {
        return shjsAuthenticateEvaluateMapper.getShjsAuthenticateEvaluateList(shjsAuthenticateEvaluate);
    }

    public void saveEvaluate(Long foreignId, String belongBusiness, List<SgjsAuthenticateEvaluate> saveList) {
        CommonAssert.notNull(foreignId,"外键不能为空！");
        CommonAssert.notBlank(belongBusiness,"所属业务不能为空！");
        //根据外键删除数据
        SgjsAuthenticateEvaluate delParam = new SgjsAuthenticateEvaluate();
        delParam.setForeignId(foreignId);
        delParam.setBelongBusiness(belongBusiness);
        shjsAuthenticateEvaluateMapper.deleteShjsAuthenticateEvaluate(delParam);

        if(CollectionUtils.isEmpty(saveList)){
            return;
        }
        //插入数据
        for (SgjsAuthenticateEvaluate evaluate : saveList) {
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
    public int insertShjsAuthenticateEvaluate(SgjsAuthenticateEvaluate shjsAuthenticateEvaluate) {
        shjsAuthenticateEvaluate.setId(IdWorker.createId());
        shjsAuthenticateEvaluate.setCreateUser(SecurityUtils.getUserName());
        shjsAuthenticateEvaluate.setCreateTime(DateUtils.getNowDate());
        return shjsAuthenticateEvaluateMapper.insertShjsAuthenticateEvaluate(shjsAuthenticateEvaluate);
    }

    @Transactional
    public int insertShjsAuthenticateEvaluateList(List<SgjsAuthenticateEvaluate> shjsAuthenticateEvaluateList) {
        if (CollUtil.isEmpty(shjsAuthenticateEvaluateList)) return 0;
        for (SgjsAuthenticateEvaluate shjsAuthenticateEvaluate : shjsAuthenticateEvaluateList) {
            shjsAuthenticateEvaluate.setId(IdWorker.createId());
            shjsAuthenticateEvaluate.setCreateUser(SecurityUtils.getUserName());
            shjsAuthenticateEvaluate.setCreateTime(DateUtils.getNowDate());
        }
        return shjsAuthenticateEvaluateMapper.insertShjsAuthenticateEvaluateList(shjsAuthenticateEvaluateList);
    }

    @Transactional
    public int updateShjsAuthenticateEvaluate(SgjsAuthenticateEvaluate shjsAuthenticateEvaluate) {
        shjsAuthenticateEvaluate.setUpdateUser(SecurityUtils.getUserName());
        shjsAuthenticateEvaluate.setUpdateTime(DateUtils.getNowDate());
        return shjsAuthenticateEvaluateMapper.updateShjsAuthenticateEvaluate(shjsAuthenticateEvaluate);
    }

    @Transactional
    public int updateShjsAuthenticateEvaluateList(List<SgjsAuthenticateEvaluate> shjsAuthenticateEvaluateList) {
        for (SgjsAuthenticateEvaluate shjsAuthenticateEvaluate : shjsAuthenticateEvaluateList) {
            shjsAuthenticateEvaluate.setUpdateUser(SecurityUtils.getUserName());
            shjsAuthenticateEvaluate.setUpdateTime(DateUtils.getNowDate());
        }
        return shjsAuthenticateEvaluateMapper.updateShjsAuthenticateEvaluateList(shjsAuthenticateEvaluateList);
    }

    @Transactional
    public int deleteShjsAuthenticateEvaluate(SgjsAuthenticateEvaluate shjsAuthenticateEvaluate) {
        return shjsAuthenticateEvaluateMapper.deleteShjsAuthenticateEvaluate(shjsAuthenticateEvaluate);
    }

    @Transactional
    public int deleteShjsAuthenticateEvaluateByPks(List<Long> shjsAuthenticateEvaluatePkList) {
        return shjsAuthenticateEvaluateMapper.deleteShjsAuthenticateEvaluateByPks(shjsAuthenticateEvaluatePkList);
    }

    @Override
    public int saveShjsAuthenticateEvaluateList(Long foreignId, String belongBusiness, List<SgjsAuthenticateEvaluate> shjsAuthenticateEvaluateList) {
        CommonAssert.notNull(foreignId,"外键不能为空！");
        CommonAssert.notBlank(belongBusiness,"所属业务不能为空！");
        SgjsAuthenticateEvaluate shjsAuthenticateEvaluate = new SgjsAuthenticateEvaluate();
        shjsAuthenticateEvaluate.setForeignId(foreignId);
        shjsAuthenticateEvaluateMapper.deleteShjsAuthenticateEvaluate(shjsAuthenticateEvaluate);
        if(CollectionUtils.isNotEmpty(shjsAuthenticateEvaluateList)) {
            for(SgjsAuthenticateEvaluate shjsAuthenticateEvaluate1 : shjsAuthenticateEvaluateList) {
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
    public List<SgjsAuthenticateEvaluate> getListByForeignIds(Long[] foreignIds) {
        CommonAssert.notNull(foreignIds,"外键不能为空！");
        return shjsAuthenticateEvaluateMapper.getListByForeignList(Arrays.asList(foreignIds));
    }
}
