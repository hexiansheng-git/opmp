package com.hhwy.pm.qqch.preparation.survey.document.service.impl;

import java.util.List;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.preparation.survey.document.domain.QqchManageProcedure;
import com.hhwy.pm.qqch.preparation.survey.document.mapper.QqchManageProcedureMapper;
import com.hhwy.pm.qqch.preparation.survey.document.service.IQqchManageProcedureService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author han
 * @date 2023-07-13 11:40:23
 * @remark
 */
@Service
public class QqchManageProcedureServiceImpl implements IQqchManageProcedureService {

    @Autowired
    private QqchManageProcedureMapper qqchManageProcedureMapper;


    public QqchManageProcedure getQqchManageProcedure(QqchManageProcedure qqchManageProcedure) {
        return qqchManageProcedureMapper.getQqchManageProcedure(qqchManageProcedure);
    }

    public List<QqchManageProcedure> getQqchManageProcedureList(QqchManageProcedure qqchManageProcedure) {
        return qqchManageProcedureMapper.getQqchManageProcedureList(qqchManageProcedure);
    }

    @Transactional
    public int insertQqchManageProcedure(QqchManageProcedure qqchManageProcedure) {
        qqchManageProcedure.setId(IdWorker.createId());
        qqchManageProcedure.setCreateUser(SecurityUtils.getUserName());
        qqchManageProcedure.setCreateTime(DateUtils.getNowDate());
        return qqchManageProcedureMapper.insertQqchManageProcedure(qqchManageProcedure);
    }

    @Transactional
    public int insertQqchManageProcedureList(List<QqchManageProcedure> qqchManageProcedureList) {
        for (QqchManageProcedure qqchManageProcedure : qqchManageProcedureList) {
            qqchManageProcedure.setId(IdWorker.createId());
            qqchManageProcedure.setCreateUser(SecurityUtils.getUserName());
            qqchManageProcedure.setCreateTime(DateUtils.getNowDate());
        }
        return qqchManageProcedureMapper.insertQqchManageProcedureList(qqchManageProcedureList);
    }

    @Transactional
    public int updateQqchManageProcedure(QqchManageProcedure qqchManageProcedure) {
        qqchManageProcedure.setUpdateUser(SecurityUtils.getUserName());
        qqchManageProcedure.setUpdateTime(DateUtils.getNowDate());
        return qqchManageProcedureMapper.updateQqchManageProcedure(qqchManageProcedure);
    }

    @Transactional
    public int updateQqchManageProcedureList(List<QqchManageProcedure> qqchManageProcedureList) {
        for (QqchManageProcedure qqchManageProcedure : qqchManageProcedureList) {
            qqchManageProcedure.setUpdateUser(SecurityUtils.getUserName());
            qqchManageProcedure.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchManageProcedureMapper.updateQqchManageProcedureList(qqchManageProcedureList);
    }

    @Transactional
    public int deleteQqchManageProcedure(QqchManageProcedure qqchManageProcedure) {
        qqchManageProcedure.setUpdateUser(SecurityUtils.getUserName());
        qqchManageProcedure.setUpdateTime(DateUtils.getNowDate());
        return qqchManageProcedureMapper.deleteQqchManageProcedure(qqchManageProcedure);
    }

    @Transactional
    public int deleteQqchManageProcedureByPks(List<Long> qqchManageProcedurePkList) {
        return qqchManageProcedureMapper.deleteQqchManageProcedureByPks(qqchManageProcedurePkList);
    }
}
