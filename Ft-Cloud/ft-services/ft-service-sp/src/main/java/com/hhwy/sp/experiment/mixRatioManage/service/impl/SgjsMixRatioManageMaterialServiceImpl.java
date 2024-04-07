package com.hhwy.sp.experiment.mixRatioManage.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.sp.experiment.mixRatioManage.domain.SgjsMixRatioManageMaterial;
import com.hhwy.sp.experiment.mixRatioManage.mapper.SgjsMixRatioManageMaterialMapper;
import com.hhwy.sp.experiment.mixRatioManage.service.ISgjsMixRatioManageMaterialService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author han
 * @date 2024-04-07 13:27:44
 * @remark
 */
@Service
public class SgjsMixRatioManageMaterialServiceImpl implements ISgjsMixRatioManageMaterialService {

    @Autowired
    private SgjsMixRatioManageMaterialMapper sgjsMixRatioManageMaterialMapper;


    public SgjsMixRatioManageMaterial getSgjsMixRatioManageMaterial(SgjsMixRatioManageMaterial sgjsMixRatioManageMaterial) {
        return sgjsMixRatioManageMaterialMapper.getSgjsMixRatioManageMaterial(sgjsMixRatioManageMaterial);
    }

    public List<SgjsMixRatioManageMaterial> getSgjsMixRatioManageMaterialList(SgjsMixRatioManageMaterial sgjsMixRatioManageMaterial) {
        return sgjsMixRatioManageMaterialMapper.getSgjsMixRatioManageMaterialList(sgjsMixRatioManageMaterial);
    }

    @Transactional
    public int insertSgjsMixRatioManageMaterial(SgjsMixRatioManageMaterial sgjsMixRatioManageMaterial) {
        sgjsMixRatioManageMaterial.setId(IdWorker.createId());
        sgjsMixRatioManageMaterial.setCreateUser(SecurityUtils.getUserName());
        sgjsMixRatioManageMaterial.setCreateTime(DateUtils.getNowDate());
        return sgjsMixRatioManageMaterialMapper.insertSgjsMixRatioManageMaterial(sgjsMixRatioManageMaterial);
    }

    @Transactional
    public int insertSgjsMixRatioManageMaterialList(List<SgjsMixRatioManageMaterial> sgjsMixRatioManageMaterialList) {
        for (SgjsMixRatioManageMaterial sgjsMixRatioManageMaterial : sgjsMixRatioManageMaterialList) {
            sgjsMixRatioManageMaterial.setId(IdWorker.createId());
            sgjsMixRatioManageMaterial.setCreateUser(SecurityUtils.getUserName());
            sgjsMixRatioManageMaterial.setCreateTime(DateUtils.getNowDate());
        }
        return sgjsMixRatioManageMaterialMapper.insertSgjsMixRatioManageMaterialList(sgjsMixRatioManageMaterialList);
    }

    @Transactional
    public int updateSgjsMixRatioManageMaterial(SgjsMixRatioManageMaterial sgjsMixRatioManageMaterial) {
        sgjsMixRatioManageMaterial.setUpdateUser(SecurityUtils.getUserName());
        sgjsMixRatioManageMaterial.setUpdateTime(DateUtils.getNowDate());
        return sgjsMixRatioManageMaterialMapper.updateSgjsMixRatioManageMaterial(sgjsMixRatioManageMaterial);
    }

    @Transactional
    public int updateSgjsMixRatioManageMaterialList(List<SgjsMixRatioManageMaterial> sgjsMixRatioManageMaterialList) {
        for (SgjsMixRatioManageMaterial sgjsMixRatioManageMaterial : sgjsMixRatioManageMaterialList) {
            sgjsMixRatioManageMaterial.setUpdateUser(SecurityUtils.getUserName());
            sgjsMixRatioManageMaterial.setUpdateTime(DateUtils.getNowDate());
        }
        return sgjsMixRatioManageMaterialMapper.updateSgjsMixRatioManageMaterialList(sgjsMixRatioManageMaterialList);
    }

    @Transactional
    public int deleteSgjsMixRatioManageMaterial(SgjsMixRatioManageMaterial sgjsMixRatioManageMaterial) {
        sgjsMixRatioManageMaterial.setUpdateUser(SecurityUtils.getUserName());
        sgjsMixRatioManageMaterial.setUpdateTime(DateUtils.getNowDate());
        return sgjsMixRatioManageMaterialMapper.deleteSgjsMixRatioManageMaterial(sgjsMixRatioManageMaterial);
    }

    @Transactional
    public int deleteSgjsMixRatioManageMaterialByPks(List<Long> sgjsMixRatioManageMaterialPkList) {
        return sgjsMixRatioManageMaterialMapper.deleteSgjsMixRatioManageMaterialByPks(sgjsMixRatioManageMaterialPkList);
    }

    @Override
    public List<SgjsMixRatioManageMaterial> getListByForeignId(Long foreignId) {
        return sgjsMixRatioManageMaterialMapper.getListByForeignId(foreignId);
    }

    @Override
    public List<SgjsMixRatioManageMaterial> getListByIds(List<Long> ids) {
        return sgjsMixRatioManageMaterialMapper.getListByIds(ids);
    }
}
