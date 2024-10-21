package com.hhwy.sp.techFile.sgjsCheckDataCatalog.service.impl;

import java.util.List;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.security.util.SecurityUtils;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.sp.techFile.sgjsCheckDataCatalog.mapper.SgjsCheckDataCatalogMapper;
import com.hhwy.sp.techFile.sgjsCheckDataCatalog.service.ISgjsCheckDataCatalogService;
import com.hhwy.sp.techFile.sgjsCheckDataCatalog.domain.SgjsCheckDataCatalog;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author xuzl
 * @date 2024-10-18 16:45:49
 * @remark
 */
@Service
public class SgjsCheckDataCatalogServiceImpl implements ISgjsCheckDataCatalogService {

    @Autowired
    private SgjsCheckDataCatalogMapper sgjsCheckDataCatalogMapper;


    public SgjsCheckDataCatalog getSgjsCheckDataCatalog(SgjsCheckDataCatalog sgjsCheckDataCatalog) {
        return sgjsCheckDataCatalogMapper.getSgjsCheckDataCatalog(sgjsCheckDataCatalog);
    }

    public List<SgjsCheckDataCatalog> getSgjsCheckDataCatalogList(SgjsCheckDataCatalog sgjsCheckDataCatalog) {
        return sgjsCheckDataCatalogMapper.getSgjsCheckDataCatalogList(sgjsCheckDataCatalog);
    }

    @Transactional
    public int insertSgjsCheckDataCatalog(SgjsCheckDataCatalog sgjsCheckDataCatalog) {
        sgjsCheckDataCatalog.setId(IdWorker.createId());
        sgjsCheckDataCatalog.setCreateUser(SecurityUtils.getUserName());
        sgjsCheckDataCatalog.setCreateTime(DateUtils.getNowDate());
        return sgjsCheckDataCatalogMapper.insertSgjsCheckDataCatalog(sgjsCheckDataCatalog);
    }

    @Transactional
    public int insertSgjsCheckDataCatalogList(List<SgjsCheckDataCatalog> sgjsCheckDataCatalogList) {
        for (SgjsCheckDataCatalog sgjsCheckDataCatalog : sgjsCheckDataCatalogList) {
            sgjsCheckDataCatalog.setId(IdWorker.createId());
            sgjsCheckDataCatalog.setCreateUser(SecurityUtils.getUserName());
            sgjsCheckDataCatalog.setCreateTime(DateUtils.getNowDate());
        }
        return sgjsCheckDataCatalogMapper.insertSgjsCheckDataCatalogList(sgjsCheckDataCatalogList);
    }

    @Transactional
    public int updateSgjsCheckDataCatalog(SgjsCheckDataCatalog sgjsCheckDataCatalog) {
        sgjsCheckDataCatalog.setUpdateUser(SecurityUtils.getUserName());
        sgjsCheckDataCatalog.setUpdateTime(DateUtils.getNowDate());
        return sgjsCheckDataCatalogMapper.updateSgjsCheckDataCatalog(sgjsCheckDataCatalog);
    }

    @Transactional
    public int updateSgjsCheckDataCatalogList(List<SgjsCheckDataCatalog> sgjsCheckDataCatalogList) {
        for (SgjsCheckDataCatalog sgjsCheckDataCatalog : sgjsCheckDataCatalogList) {
            sgjsCheckDataCatalog.setUpdateUser(SecurityUtils.getUserName());
            sgjsCheckDataCatalog.setUpdateTime(DateUtils.getNowDate());
        }
        return sgjsCheckDataCatalogMapper.updateSgjsCheckDataCatalogList(sgjsCheckDataCatalogList);
    }

    @Transactional
    public int deleteSgjsCheckDataCatalog(SgjsCheckDataCatalog sgjsCheckDataCatalog) {
        sgjsCheckDataCatalog.setUpdateUser(SecurityUtils.getUserName());
        sgjsCheckDataCatalog.setUpdateTime(DateUtils.getNowDate());
        return sgjsCheckDataCatalogMapper.deleteSgjsCheckDataCatalog(sgjsCheckDataCatalog);
    }

    @Transactional
    public int deleteSgjsCheckDataCatalogByPks(List<Long> sgjsCheckDataCatalogPkList) {
        return sgjsCheckDataCatalogMapper.deleteSgjsCheckDataCatalogByPks(sgjsCheckDataCatalogPkList);
    }

    @Override
    public int insertBath(List<SgjsCheckDataCatalog> sgjsCheckDataCatalogList) {
        for (SgjsCheckDataCatalog sgjsCheckDataCatalog : sgjsCheckDataCatalogList) {
            sgjsCheckDataCatalog.setCreateTime(DateUtils.getNowDate());
        }
        return sgjsCheckDataCatalogMapper.insertSgjsCheckDataCatalogList(sgjsCheckDataCatalogList);
    }

    @Override
    public int updateBath(List<SgjsCheckDataCatalog> sgjsCheckDataCatalogList) {
        for (SgjsCheckDataCatalog sgjsCheckDataCatalog : sgjsCheckDataCatalogList) {
            sgjsCheckDataCatalog.setUpdateTime(DateUtils.getNowDate());
        }
        return sgjsCheckDataCatalogMapper.updateSgjsCheckDataCatalogList(sgjsCheckDataCatalogList);
    }
}
