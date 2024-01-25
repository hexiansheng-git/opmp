package com.hhwy.sp.common.sgjsExpertLibrary.service.impl;

import java.util.List;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.sp.common.sgjsExpertLibrary.domain.SgjsExpertLibrary;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.sp.common.sgjsExpertLibrary.mapper.SgjsExpertLibraryMapper;
import com.hhwy.sp.common.sgjsExpertLibrary.service.ISgjsExpertLibraryService;
import com.hhwy.utils.idworker.IdWorker;

/***
 * 功能描述: 科技管理 - 专家库
 * 作者: fushudong
 * 时间: 2024/1/25
 */
@Service
public class SgjsExpertLibraryServiceImpl implements ISgjsExpertLibraryService {

    @Autowired
    private SgjsExpertLibraryMapper sgjsExpertLibraryMapper;


    public SgjsExpertLibrary getSgjsExpertLibrary(SgjsExpertLibrary sgjsExpertLibrary) {
        return sgjsExpertLibraryMapper.getSgjsExpertLibrary(sgjsExpertLibrary);
    }

    public List<SgjsExpertLibrary> getSgjsExpertLibraryList(SgjsExpertLibrary sgjsExpertLibrary) {
        return sgjsExpertLibraryMapper.getSgjsExpertLibraryList(sgjsExpertLibrary);
    }

    @Transactional
    public int insertSgjsExpertLibrary(SgjsExpertLibrary sgjsExpertLibrary) {
        sgjsExpertLibrary.setId(IdWorker.createId());
        sgjsExpertLibrary.setCreateUser(SecurityUtils.getUserName());
        sgjsExpertLibrary.setCreateTime(DateUtils.getNowDate());
        return sgjsExpertLibraryMapper.insertSgjsExpertLibrary(sgjsExpertLibrary);
    }

    @Transactional
    public int insertSgjsExpertLibraryList(List<SgjsExpertLibrary> sgjsExpertLibraryList) {
        for (SgjsExpertLibrary sgjsExpertLibrary : sgjsExpertLibraryList) {
            sgjsExpertLibrary.setId(IdWorker.createId());
            sgjsExpertLibrary.setCreateUser(SecurityUtils.getUserName());
            sgjsExpertLibrary.setCreateTime(DateUtils.getNowDate());
        }
        return sgjsExpertLibraryMapper.insertSgjsExpertLibraryList(sgjsExpertLibraryList);
    }

    @Transactional
    public int updateSgjsExpertLibrary(SgjsExpertLibrary sgjsExpertLibrary) {
        sgjsExpertLibrary.setUpdateUser(SecurityUtils.getUserName());
        sgjsExpertLibrary.setUpdateTime(DateUtils.getNowDate());
        return sgjsExpertLibraryMapper.updateSgjsExpertLibrary(sgjsExpertLibrary);
    }

    @Transactional
    public int updateSgjsExpertLibraryList(List<SgjsExpertLibrary> sgjsExpertLibraryList) {
        for (SgjsExpertLibrary sgjsExpertLibrary : sgjsExpertLibraryList) {
            sgjsExpertLibrary.setUpdateUser(SecurityUtils.getUserName());
            sgjsExpertLibrary.setUpdateTime(DateUtils.getNowDate());
        }
        return sgjsExpertLibraryMapper.updateSgjsExpertLibraryList(sgjsExpertLibraryList);
    }

    @Transactional
    public int deleteSgjsExpertLibrary(SgjsExpertLibrary sgjsExpertLibrary) {
        sgjsExpertLibrary.setUpdateUser(SecurityUtils.getUserName());
        sgjsExpertLibrary.setUpdateTime(DateUtils.getNowDate());
        return sgjsExpertLibraryMapper.deleteSgjsExpertLibrary(sgjsExpertLibrary);
    }

    @Transactional
    public int deleteSgjsExpertLibraryByPks(List<Long> sgjsExpertLibraryPkList) {
        return sgjsExpertLibraryMapper.deleteSgjsExpertLibraryByPks(sgjsExpertLibraryPkList);
    }
}
