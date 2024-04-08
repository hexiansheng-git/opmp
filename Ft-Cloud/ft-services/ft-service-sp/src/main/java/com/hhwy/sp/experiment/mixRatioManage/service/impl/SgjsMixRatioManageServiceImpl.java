package com.hhwy.sp.experiment.mixRatioManage.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.domain.base.project.ProjectDto;
import com.hhwy.feign.service.PmServiceApi;
import com.hhwy.sp.experiment.mixRatioManage.domain.SgjsMixRatioManage;
import com.hhwy.sp.experiment.mixRatioManage.domain.SgjsMixRatioManageMaterial;
import com.hhwy.sp.experiment.mixRatioManage.domain.vo.MixRatioManageQueryVo;
import com.hhwy.sp.experiment.mixRatioManage.mapper.SgjsMixRatioManageMapper;
import com.hhwy.sp.experiment.mixRatioManage.mapper.SgjsMixRatioManageMaterialMapper;
import com.hhwy.sp.experiment.mixRatioManage.service.ISgjsMixRatioManageService;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author han
 * @date 2024-04-07 13:38:19
 * @remark
 */
@Service
public class SgjsMixRatioManageServiceImpl implements ISgjsMixRatioManageService {

    @Autowired
    private SgjsMixRatioManageMapper sgjsMixRatioManageMapper;

    @Autowired
    private SgjsMixRatioManageMaterialMapper sgjsMixRatioManageMaterialMapper;

    @Autowired
    private PmServiceApi pmServiceApi;


    public SgjsMixRatioManage getSgjsMixRatioManage(SgjsMixRatioManage sgjsMixRatioManage) {
        return sgjsMixRatioManageMapper.getSgjsMixRatioManage(sgjsMixRatioManage);
    }

    public List<SgjsMixRatioManage> getSgjsMixRatioManageList(SgjsMixRatioManage sgjsMixRatioManage) {
        return sgjsMixRatioManageMapper.getSgjsMixRatioManageList(sgjsMixRatioManage);
    }

    @Transactional
    public int insertSgjsMixRatioManage(SgjsMixRatioManage sgjsMixRatioManage) {
        sgjsMixRatioManage.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
        sgjsMixRatioManage.setCreateUserName(SecurityUtils.getUserName());
        sgjsMixRatioManage.setCreateTime(DateUtils.getNowDate());
        return sgjsMixRatioManageMapper.insertSgjsMixRatioManage(sgjsMixRatioManage);
    }

    @Transactional
    public int insertSgjsMixRatioManageList(List<SgjsMixRatioManage> sgjsMixRatioManageList) {
        for (SgjsMixRatioManage sgjsMixRatioManage : sgjsMixRatioManageList) {
            sgjsMixRatioManage.setId(IdWorker.createId());
            sgjsMixRatioManage.setCreateUser(SecurityUtils.getUserName());
            sgjsMixRatioManage.setCreateTime(DateUtils.getNowDate());
        }
        return sgjsMixRatioManageMapper.insertSgjsMixRatioManageList(sgjsMixRatioManageList);
    }

    @Transactional
    public int updateSgjsMixRatioManage(SgjsMixRatioManage sgjsMixRatioManage) {
        sgjsMixRatioManage.setUpdateUser(SecurityUtils.getUserName());
        sgjsMixRatioManage.setUpdateTime(DateUtils.getNowDate());
        return sgjsMixRatioManageMapper.updateSgjsMixRatioManage(sgjsMixRatioManage);
    }

    @Transactional
    public int updateSgjsMixRatioManageList(List<SgjsMixRatioManage> sgjsMixRatioManageList) {
        for (SgjsMixRatioManage sgjsMixRatioManage : sgjsMixRatioManageList) {
            sgjsMixRatioManage.setUpdateUser(SecurityUtils.getUserName());
            sgjsMixRatioManage.setUpdateTime(DateUtils.getNowDate());
        }
        return sgjsMixRatioManageMapper.updateSgjsMixRatioManageList(sgjsMixRatioManageList);
    }

    @Transactional
    public int deleteSgjsMixRatioManage(SgjsMixRatioManage sgjsMixRatioManage) {
        sgjsMixRatioManage.setUpdateUser(SecurityUtils.getUserName());
        sgjsMixRatioManage.setUpdateTime(DateUtils.getNowDate());
        return sgjsMixRatioManageMapper.deleteSgjsMixRatioManage(sgjsMixRatioManage);
    }

    @Transactional
    public int deleteSgjsMixRatioManageByPks(List<Long> sgjsMixRatioManagePkList) {
        return sgjsMixRatioManageMapper.deleteSgjsMixRatioManageByPks(sgjsMixRatioManagePkList);
    }

    @Override
    public List<SgjsMixRatioManage> getListByQueryVo(MixRatioManageQueryVo queryVo) {
        return sgjsMixRatioManageMapper.getListByQueryVo(queryVo);
    }

    @Override
    public SgjsMixRatioManage getById(Long id) {
        SgjsMixRatioManage mixRatioManage = sgjsMixRatioManageMapper.getById(id);
        List<SgjsMixRatioManageMaterial> materialList = sgjsMixRatioManageMaterialMapper.getListByForeignId(id);
        mixRatioManage.setMaterialList(materialList);
        return mixRatioManage;
    }

    @Override
    @Transactional
    public void save(SgjsMixRatioManage mixRatioManage) {
        String saveType = mixRatioManage.getSaveType();
        Long id = mixRatioManage.getId();
        if("add".equals(saveType) || id == null){
            id = IdWorker.createId();
            mixRatioManage.setId(id);
            ProjectDto projectDto = pmServiceApi.getProjectDto();
            mixRatioManage.setRegionId(projectDto.getRegionId());
            mixRatioManage.setRegionName(projectDto.getRegionName());
            mixRatioManage.setProjectId(projectDto.getProjectId());
            mixRatioManage.setProjectName(projectDto.getProjectName());
            mixRatioManage.setProjectCode(projectDto.getProjectCode());
            this.insertSgjsMixRatioManage(mixRatioManage);
        }else {
            this.updateSgjsMixRatioManage(mixRatioManage);
        }

        List<SgjsMixRatioManageMaterial> materialList = mixRatioManage.getMaterialList();
        this.saveMaterialList(id, materialList);
    }

    private void saveMaterialList(Long foreignId, List<SgjsMixRatioManageMaterial> materialList){
        sgjsMixRatioManageMaterialMapper.deleteByForeignId(foreignId);

        if(CollectionUtils.isEmpty(materialList)){
            return;
        }

        for (SgjsMixRatioManageMaterial material : materialList) {
            material.setId(IdWorker.createId());
            material.setForeignId(foreignId);
            material.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            material.setCreateUserName(SecurityUtils.getUserName());
            material.setCreateTime(DateUtils.getNowDate());
        }

        sgjsMixRatioManageMaterialMapper.insertSgjsMixRatioManageMaterialList(materialList);
    }

    @Override
    public List<SgjsMixRatioManage> getListByIds(List<Long> ids) {
        return sgjsMixRatioManageMapper.getListByIds(ids);
    }
}
