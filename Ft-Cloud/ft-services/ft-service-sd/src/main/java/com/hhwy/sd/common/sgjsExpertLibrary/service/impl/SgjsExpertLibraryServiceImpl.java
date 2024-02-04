package com.hhwy.sd.common.sgjsExpertLibrary.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.sd.common.sgjsExpertLibrary.domain.SgjsExpertLibrary;
import com.hhwy.sd.common.sgjsExpertLibrary.mapper.SgjsExpertLibraryMapper;
import com.hhwy.sd.common.sgjsExpertLibrary.service.ISgjsExpertLibraryService;
import com.hhwy.utils.common.CommonAssert;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Override
    public List<SgjsExpertLibrary> getListByForeignId(Long foreignId) {
        CommonAssert.notNull(foreignId,"外键不能为空！");
        SgjsExpertLibrary query = new SgjsExpertLibrary();
        query.setForeignId(foreignId);
        return sgjsExpertLibraryMapper.getSgjsExpertLibraryList(query);
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

    @Override
    public void deleteSgjsExpertLibraryByForeignId(Long foreignId) {
        CommonAssert.notNull(foreignId,"外键不能为空！");
        SgjsExpertLibrary delParam = new SgjsExpertLibrary();
        delParam.setForeignId(foreignId);
        sgjsExpertLibraryMapper.deleteSgjsExpertLibrary(delParam);
    }

    /**
     * 保存专家库数据集
     * @param foreignId 外键id
     * @param belongBusiness 所属功能
     * @param saveList 成果数据集
     */
    public void saveExpertLibraryList(Long foreignId, String belongBusiness, List<SgjsExpertLibrary> saveList) {
        CommonAssert.notNull(foreignId, "外键不能为空！");
        CommonAssert.notBlank(belongBusiness, "所属业务不能为空！");
        //根据外键删除数据
        SgjsExpertLibrary delParam = new SgjsExpertLibrary();
        delParam.setForeignId(foreignId);
        sgjsExpertLibraryMapper.deleteSgjsExpertLibrary(delParam);
        if (CollectionUtils.isEmpty(saveList)) {
            return;
        }
        //插入数据
        for (SgjsExpertLibrary library : saveList) {
            library.setId(IdWorker.createId());
            library.setForeignId(foreignId);
            library.setBelongBusiness(belongBusiness);
            library.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            library.setCreateUserName(SecurityUtils.getUserName());
            library.setCreateTime(DateUtils.getNowDate());
        }
        sgjsExpertLibraryMapper.insertSgjsExpertLibraryList(saveList);
    }
}
