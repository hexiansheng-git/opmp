package com.hhwy.pm.qqch.preparation.technique.techManage.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.preparation.technique.techManage.domain.QqchProjectLinkupManage;
import com.hhwy.pm.qqch.preparation.technique.techManage.mapper.QqchProjectLinkupManageMapper;
import com.hhwy.pm.qqch.preparation.technique.techManage.service.IQqchProjectLinkupManageService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author han
 * @date 2023-07-25 11:24:02
 * @remark
 */
@Service
public class QqchProjectLinkupManageServiceImpl implements IQqchProjectLinkupManageService {

    @Autowired
    private QqchProjectLinkupManageMapper qqchProjectLinkupManageMapper;


    public QqchProjectLinkupManage getQqchProjectLinkupManage(QqchProjectLinkupManage qqchProjectLinkupManage) {
        return qqchProjectLinkupManageMapper.getQqchProjectLinkupManage(qqchProjectLinkupManage);
    }

    public List<QqchProjectLinkupManage> getQqchProjectLinkupManageList(QqchProjectLinkupManage qqchProjectLinkupManage) {
        return qqchProjectLinkupManageMapper.getQqchProjectLinkupManageList(qqchProjectLinkupManage);
    }

    @Transactional
    public int insertQqchProjectLinkupManage(QqchProjectLinkupManage qqchProjectLinkupManage) {
        qqchProjectLinkupManage.setId(IdWorker.createId());
        qqchProjectLinkupManage.setCreateUser(SecurityUtils.getUserName());
        qqchProjectLinkupManage.setCreateTime(DateUtils.getNowDate());
        return qqchProjectLinkupManageMapper.insertQqchProjectLinkupManage(qqchProjectLinkupManage);
    }

    @Transactional
    public int insertQqchProjectLinkupManageList(List<QqchProjectLinkupManage> qqchProjectLinkupManageList) {
        for (QqchProjectLinkupManage qqchProjectLinkupManage : qqchProjectLinkupManageList) {
            qqchProjectLinkupManage.setId(IdWorker.createId());
            qqchProjectLinkupManage.setCreateUser(SecurityUtils.getUserName());
            qqchProjectLinkupManage.setCreateTime(DateUtils.getNowDate());
        }
        return qqchProjectLinkupManageMapper.insertQqchProjectLinkupManageList(qqchProjectLinkupManageList);
    }

    @Transactional
    public int updateQqchProjectLinkupManage(QqchProjectLinkupManage qqchProjectLinkupManage) {
        qqchProjectLinkupManage.setUpdateUser(SecurityUtils.getUserName());
        qqchProjectLinkupManage.setUpdateTime(DateUtils.getNowDate());
        return qqchProjectLinkupManageMapper.updateQqchProjectLinkupManage(qqchProjectLinkupManage);
    }

    @Transactional
    public int updateQqchProjectLinkupManageList(List<QqchProjectLinkupManage> qqchProjectLinkupManageList) {
        for (QqchProjectLinkupManage qqchProjectLinkupManage : qqchProjectLinkupManageList) {
            qqchProjectLinkupManage.setUpdateUser(SecurityUtils.getUserName());
            qqchProjectLinkupManage.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchProjectLinkupManageMapper.updateQqchProjectLinkupManageList(qqchProjectLinkupManageList);
    }

    @Transactional
    public int deleteQqchProjectLinkupManage(QqchProjectLinkupManage qqchProjectLinkupManage) {
        qqchProjectLinkupManage.setUpdateUser(SecurityUtils.getUserName());
        qqchProjectLinkupManage.setUpdateTime(DateUtils.getNowDate());
        return qqchProjectLinkupManageMapper.deleteQqchProjectLinkupManage(qqchProjectLinkupManage);
    }

    @Transactional
    public int deleteQqchProjectLinkupManageByPks(List<Long> qqchProjectLinkupManagePkList) {
        return qqchProjectLinkupManageMapper.deleteQqchProjectLinkupManageByPks(qqchProjectLinkupManagePkList);
    }
}
