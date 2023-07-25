package com.hhwy.pm.qqch.preparation.technique.techManage.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.preparation.technique.techManage.domain.QqchInterestedPartyManage;
import com.hhwy.pm.qqch.preparation.technique.techManage.mapper.QqchInterestedPartyManageMapper;
import com.hhwy.pm.qqch.preparation.technique.techManage.service.IQqchInterestedPartyManageService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author han
 * @date 2023-07-25 10:53:45
 * @remark
 */
@Service
public class QqchInterestedPartyManageServiceImpl implements IQqchInterestedPartyManageService {

    @Autowired
    private QqchInterestedPartyManageMapper qqchInterestedPartyManageMapper;


    public QqchInterestedPartyManage getQqchInterestedPartyManage(QqchInterestedPartyManage qqchInterestedPartyManage) {
        return qqchInterestedPartyManageMapper.getQqchInterestedPartyManage(qqchInterestedPartyManage);
    }

    public List<QqchInterestedPartyManage> getQqchInterestedPartyManageList(QqchInterestedPartyManage qqchInterestedPartyManage) {
        return qqchInterestedPartyManageMapper.getQqchInterestedPartyManageList(qqchInterestedPartyManage);
    }

    @Transactional
    public int insertQqchInterestedPartyManage(QqchInterestedPartyManage qqchInterestedPartyManage) {
        qqchInterestedPartyManage.setId(IdWorker.createId());
        qqchInterestedPartyManage.setCreateUser(SecurityUtils.getUserName());
        qqchInterestedPartyManage.setCreateTime(DateUtils.getNowDate());
        return qqchInterestedPartyManageMapper.insertQqchInterestedPartyManage(qqchInterestedPartyManage);
    }

    @Transactional
    public int insertQqchInterestedPartyManageList(List<QqchInterestedPartyManage> qqchInterestedPartyManageList) {
        for (QqchInterestedPartyManage qqchInterestedPartyManage : qqchInterestedPartyManageList) {
            qqchInterestedPartyManage.setId(IdWorker.createId());
            qqchInterestedPartyManage.setCreateUser(SecurityUtils.getUserName());
            qqchInterestedPartyManage.setCreateTime(DateUtils.getNowDate());
        }
        return qqchInterestedPartyManageMapper.insertQqchInterestedPartyManageList(qqchInterestedPartyManageList);
    }

    @Transactional
    public int updateQqchInterestedPartyManage(QqchInterestedPartyManage qqchInterestedPartyManage) {
        qqchInterestedPartyManage.setUpdateUser(SecurityUtils.getUserName());
        qqchInterestedPartyManage.setUpdateTime(DateUtils.getNowDate());
        return qqchInterestedPartyManageMapper.updateQqchInterestedPartyManage(qqchInterestedPartyManage);
    }

    @Transactional
    public int updateQqchInterestedPartyManageList(List<QqchInterestedPartyManage> qqchInterestedPartyManageList) {
        for (QqchInterestedPartyManage qqchInterestedPartyManage : qqchInterestedPartyManageList) {
            qqchInterestedPartyManage.setUpdateUser(SecurityUtils.getUserName());
            qqchInterestedPartyManage.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchInterestedPartyManageMapper.updateQqchInterestedPartyManageList(qqchInterestedPartyManageList);
    }

    @Transactional
    public int deleteQqchInterestedPartyManage(QqchInterestedPartyManage qqchInterestedPartyManage) {
        qqchInterestedPartyManage.setUpdateUser(SecurityUtils.getUserName());
        qqchInterestedPartyManage.setUpdateTime(DateUtils.getNowDate());
        return qqchInterestedPartyManageMapper.deleteQqchInterestedPartyManage(qqchInterestedPartyManage);
    }

    @Transactional
    public int deleteQqchInterestedPartyManageByPks(List<Long> qqchInterestedPartyManagePkList) {
        return qqchInterestedPartyManageMapper.deleteQqchInterestedPartyManageByPks(qqchInterestedPartyManagePkList);
    }
}
