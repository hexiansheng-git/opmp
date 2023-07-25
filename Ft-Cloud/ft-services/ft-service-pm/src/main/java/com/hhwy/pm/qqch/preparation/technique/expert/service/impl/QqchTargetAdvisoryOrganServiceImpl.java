package com.hhwy.pm.qqch.preparation.technique.expert.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.preparation.technique.expert.domain.QqchTargetAdvisoryOrgan;
import com.hhwy.pm.qqch.preparation.technique.expert.mapper.QqchTargetAdvisoryOrganMapper;
import com.hhwy.pm.qqch.preparation.technique.expert.service.IQqchTargetAdvisoryOrganService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author han
 * @date 2023-07-25 10:56:43
 * @remark
 */
@Service
public class QqchTargetAdvisoryOrganServiceImpl implements IQqchTargetAdvisoryOrganService {

    @Autowired
    private QqchTargetAdvisoryOrganMapper qqchTargetAdvisoryOrganMapper;


    public QqchTargetAdvisoryOrgan getQqchTargetAdvisoryOrgan(QqchTargetAdvisoryOrgan qqchTargetAdvisoryOrgan) {
        return qqchTargetAdvisoryOrganMapper.getQqchTargetAdvisoryOrgan(qqchTargetAdvisoryOrgan);
    }

    public List<QqchTargetAdvisoryOrgan> getQqchTargetAdvisoryOrganList(QqchTargetAdvisoryOrgan qqchTargetAdvisoryOrgan) {
        return qqchTargetAdvisoryOrganMapper.getQqchTargetAdvisoryOrganList(qqchTargetAdvisoryOrgan);
    }

    @Transactional
    public int insertQqchTargetAdvisoryOrgan(QqchTargetAdvisoryOrgan qqchTargetAdvisoryOrgan) {
        qqchTargetAdvisoryOrgan.setId(IdWorker.createId());
        qqchTargetAdvisoryOrgan.setCreateUser(SecurityUtils.getUserName());
        qqchTargetAdvisoryOrgan.setCreateTime(DateUtils.getNowDate());
        return qqchTargetAdvisoryOrganMapper.insertQqchTargetAdvisoryOrgan(qqchTargetAdvisoryOrgan);
    }

    @Transactional
    public int insertQqchTargetAdvisoryOrganList(List<QqchTargetAdvisoryOrgan> qqchTargetAdvisoryOrganList) {
        for (QqchTargetAdvisoryOrgan qqchTargetAdvisoryOrgan : qqchTargetAdvisoryOrganList) {
            qqchTargetAdvisoryOrgan.setId(IdWorker.createId());
            qqchTargetAdvisoryOrgan.setCreateUser(SecurityUtils.getUserName());
            qqchTargetAdvisoryOrgan.setCreateTime(DateUtils.getNowDate());
        }
        return qqchTargetAdvisoryOrganMapper.insertQqchTargetAdvisoryOrganList(qqchTargetAdvisoryOrganList);
    }

    @Transactional
    public int updateQqchTargetAdvisoryOrgan(QqchTargetAdvisoryOrgan qqchTargetAdvisoryOrgan) {
        qqchTargetAdvisoryOrgan.setUpdateUser(SecurityUtils.getUserName());
        qqchTargetAdvisoryOrgan.setUpdateTime(DateUtils.getNowDate());
        return qqchTargetAdvisoryOrganMapper.updateQqchTargetAdvisoryOrgan(qqchTargetAdvisoryOrgan);
    }

    @Transactional
    public int updateQqchTargetAdvisoryOrganList(List<QqchTargetAdvisoryOrgan> qqchTargetAdvisoryOrganList) {
        for (QqchTargetAdvisoryOrgan qqchTargetAdvisoryOrgan : qqchTargetAdvisoryOrganList) {
            qqchTargetAdvisoryOrgan.setUpdateUser(SecurityUtils.getUserName());
            qqchTargetAdvisoryOrgan.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchTargetAdvisoryOrganMapper.updateQqchTargetAdvisoryOrganList(qqchTargetAdvisoryOrganList);
    }

    @Transactional
    public int deleteQqchTargetAdvisoryOrgan(QqchTargetAdvisoryOrgan qqchTargetAdvisoryOrgan) {
        qqchTargetAdvisoryOrgan.setUpdateUser(SecurityUtils.getUserName());
        qqchTargetAdvisoryOrgan.setUpdateTime(DateUtils.getNowDate());
        return qqchTargetAdvisoryOrganMapper.deleteQqchTargetAdvisoryOrgan(qqchTargetAdvisoryOrgan);
    }

    @Transactional
    public int deleteQqchTargetAdvisoryOrganByPks(List<Long> qqchTargetAdvisoryOrganPkList) {
        return qqchTargetAdvisoryOrganMapper.deleteQqchTargetAdvisoryOrganByPks(qqchTargetAdvisoryOrganPkList);
    }
}
