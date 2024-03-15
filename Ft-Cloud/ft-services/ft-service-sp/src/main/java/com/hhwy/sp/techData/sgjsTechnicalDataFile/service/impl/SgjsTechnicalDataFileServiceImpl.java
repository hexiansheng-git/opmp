package com.hhwy.sp.techData.sgjsTechnicalDataFile.service.impl;

import java.util.List;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.domain.base.project.ProjectDto;
import com.hhwy.feign.service.PmServiceApi;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.sp.techData.sgjsTechnicalDataFile.mapper.SgjsTechnicalDataFileMapper;
import com.hhwy.sp.techData.sgjsTechnicalDataFile.service.ISgjsTechnicalDataFileService;
import com.hhwy.sp.techData.sgjsTechnicalDataFile.domain.SgjsTechnicalDataFile;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author cjh
 * @date 2024-02-27 15:10:21
 * @remark
 */
@Service
public class SgjsTechnicalDataFileServiceImpl implements ISgjsTechnicalDataFileService {

    @Autowired
    private SgjsTechnicalDataFileMapper sgjsTechnicalDataFileMapper;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    @Autowired
    private PmServiceApi pmServiceApi;


    public SgjsTechnicalDataFile getSgjsTechnicalDataFile(SgjsTechnicalDataFile sgjsTechnicalDataFile) {
        return sgjsTechnicalDataFileMapper.getSgjsTechnicalDataFile(sgjsTechnicalDataFile);
    }

    public List<SgjsTechnicalDataFile> getSgjsTechnicalDataFileList(SgjsTechnicalDataFile sgjsTechnicalDataFile) {
        return sgjsTechnicalDataFileMapper.getSgjsTechnicalDataFileList(sgjsTechnicalDataFile);
    }

    @Transactional
    public int insertSgjsTechnicalDataFile(SgjsTechnicalDataFile sgjsTechnicalDataFile) {
        ProjectDto projectDto = pmServiceApi.getProjectDto();
        sgjsTechnicalDataFile.setId(IdWorker.createId());
        sgjsTechnicalDataFile.setCreateUser(SecurityUtils.getUserName());
        sgjsTechnicalDataFile.setCreateTime(DateUtils.getNowDate());
        sgjsTechnicalDataFile.setRegionId(projectDto.getRegionId());
        sgjsTechnicalDataFile.setRegionName(projectDto.getRegionName());
        sgjsTechnicalDataFile.setProjectId(projectDto.getProjectId());
        sgjsTechnicalDataFile.setPtVar5(projectDto.getProjectCode());
        return sgjsTechnicalDataFileMapper.insertSgjsTechnicalDataFile(sgjsTechnicalDataFile);
    }

    @Transactional
    public int insertSgjsTechnicalDataFileList(List<SgjsTechnicalDataFile> sgjsTechnicalDataFileList) {
        for (SgjsTechnicalDataFile sgjsTechnicalDataFile : sgjsTechnicalDataFileList) {
            sgjsTechnicalDataFile.setId(IdWorker.createId());
            sgjsTechnicalDataFile.setCreateUser(SecurityUtils.getUserName());
            sgjsTechnicalDataFile.setCreateTime(DateUtils.getNowDate());
        }
        return sgjsTechnicalDataFileMapper.insertSgjsTechnicalDataFileList(sgjsTechnicalDataFileList);
    }

    @Transactional
    public int updateSgjsTechnicalDataFile(SgjsTechnicalDataFile sgjsTechnicalDataFile) {
        if(null == sgjsTechnicalDataFile.getId()) {
            return insertSgjsTechnicalDataFile(sgjsTechnicalDataFile);
        }
        return sgjsTechnicalDataFileMapper.updateSgjsTechnicalDataFile(sgjsTechnicalDataFile);
    }

    @Transactional
    public int updateSgjsTechnicalDataFileList(List<SgjsTechnicalDataFile> sgjsTechnicalDataFileList) {
        for (SgjsTechnicalDataFile sgjsTechnicalDataFile : sgjsTechnicalDataFileList) {
            sgjsTechnicalDataFile.setUpdateUser(SecurityUtils.getUserName());
            sgjsTechnicalDataFile.setUpdateTime(DateUtils.getNowDate());
        }
        return sgjsTechnicalDataFileMapper.updateSgjsTechnicalDataFileList(sgjsTechnicalDataFileList);
    }

    @Transactional
    public int deleteSgjsTechnicalDataFile(SgjsTechnicalDataFile sgjsTechnicalDataFile) {
        sgjsTechnicalDataFile.setUpdateUser(SecurityUtils.getUserName());
        sgjsTechnicalDataFile.setUpdateTime(DateUtils.getNowDate());
        return sgjsTechnicalDataFileMapper.deleteSgjsTechnicalDataFile(sgjsTechnicalDataFile);
    }

    @Transactional
    public int deleteSgjsTechnicalDataFileByPks(List<Long> sgjsTechnicalDataFilePkList) {
        return sgjsTechnicalDataFileMapper.deleteSgjsTechnicalDataFileByPks(sgjsTechnicalDataFilePkList);
    }
}
