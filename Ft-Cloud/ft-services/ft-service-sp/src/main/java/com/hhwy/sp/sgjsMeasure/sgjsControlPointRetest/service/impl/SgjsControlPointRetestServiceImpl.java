package com.hhwy.sp.sgjsMeasure.sgjsControlPointRetest.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.sp.sgjsMeasure.sgjsControlPointRetest.domain.SgjsControlPointRetest;
import com.hhwy.sp.sgjsMeasure.sgjsControlPointRetest.mapper.SgjsControlPointRetestMapper;
import com.hhwy.sp.sgjsMeasure.sgjsControlPointRetest.service.ISgjsControlPointRetestService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 施工技术--测量管理--控制点复测
 *
 * @author lcf
 * @date 2024-03-12 14:54:13
 * @remark
 */
@Service
public class SgjsControlPointRetestServiceImpl implements ISgjsControlPointRetestService {

    @Autowired
    private SgjsControlPointRetestMapper sgjsControlPointRetestMapper;


    public SgjsControlPointRetest getSgjsControlPointRetest(SgjsControlPointRetest sgjsControlPointRetest) {
        return sgjsControlPointRetestMapper.getSgjsControlPointRetest(sgjsControlPointRetest);
    }

    public List<SgjsControlPointRetest> getSgjsControlPointRetestList(SgjsControlPointRetest sgjsControlPointRetest) {
        return sgjsControlPointRetestMapper.getSgjsControlPointRetestList(sgjsControlPointRetest);
    }

    @Transactional
    public int insertSgjsControlPointRetest(SgjsControlPointRetest sgjsControlPointRetest) {
        sgjsControlPointRetest.setId(IdWorker.createId());
        sgjsControlPointRetest.setCreateUser(SecurityUtils.getUserName());
        sgjsControlPointRetest.setCreateTime(DateUtils.getNowDate());
        return sgjsControlPointRetestMapper.insertSgjsControlPointRetest(sgjsControlPointRetest);
    }

    @Transactional
    public int insertSgjsControlPointRetestList(List<SgjsControlPointRetest> sgjsControlPointRetestList) {
        //因为是全量新增,先删掉库里原有的
        SgjsControlPointRetest info= new SgjsControlPointRetest();
        info.setUpdateTime(DateUtils.getNowDate());
        info.setUpdateUser(SecurityUtils.getUserId().toString());
        info.setDelFlag("1");
        sgjsControlPointRetestMapper.updateSgjsControlPointRetest(info);
        for (SgjsControlPointRetest sgjsControlPointRetest : sgjsControlPointRetestList) {
            sgjsControlPointRetest.setId(IdWorker.createId());
            sgjsControlPointRetest.setCreateUser(SecurityUtils.getUserName());
            sgjsControlPointRetest.setCreateTime(DateUtils.getNowDate());
        }
        return sgjsControlPointRetestMapper.insertSgjsControlPointRetestList(sgjsControlPointRetestList);
    }

    @Transactional
    public int updateSgjsControlPointRetest(SgjsControlPointRetest sgjsControlPointRetest) {
        sgjsControlPointRetest.setUpdateUser(SecurityUtils.getUserName());
        sgjsControlPointRetest.setUpdateTime(DateUtils.getNowDate());
        return sgjsControlPointRetestMapper.updateSgjsControlPointRetest(sgjsControlPointRetest);
    }

    @Transactional
    public int updateSgjsControlPointRetestList(List<SgjsControlPointRetest> sgjsControlPointRetestList) {
        for (SgjsControlPointRetest sgjsControlPointRetest : sgjsControlPointRetestList) {
            sgjsControlPointRetest.setUpdateUser(SecurityUtils.getUserName());
            sgjsControlPointRetest.setUpdateTime(DateUtils.getNowDate());
        }
        return sgjsControlPointRetestMapper.updateSgjsControlPointRetestList(sgjsControlPointRetestList);
    }

    @Transactional
    public int deleteSgjsControlPointRetest(SgjsControlPointRetest sgjsControlPointRetest) {
        sgjsControlPointRetest.setUpdateUser(SecurityUtils.getUserName());
        sgjsControlPointRetest.setUpdateTime(DateUtils.getNowDate());
        return sgjsControlPointRetestMapper.deleteSgjsControlPointRetest(sgjsControlPointRetest);
    }

    @Transactional
    public int deleteSgjsControlPointRetestByPks(List<Long> sgjsControlPointRetestPkList) {
        return sgjsControlPointRetestMapper.deleteSgjsControlPointRetestByPks(sgjsControlPointRetestPkList);
    }
}
