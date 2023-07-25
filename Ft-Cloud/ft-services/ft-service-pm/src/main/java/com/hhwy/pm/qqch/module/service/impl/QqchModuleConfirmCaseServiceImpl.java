package com.hhwy.pm.qqch.module.service.impl;

import java.util.List;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ConfirmStatus;
import com.hhwy.pm.qqch.module.domain.QqchModuleConfirmCase;
import com.hhwy.pm.qqch.module.mapper.QqchModuleConfirmCaseMapper;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author han
 * @date 2023-07-11 15:23:04
 * @remark
 */
@Service
public class QqchModuleConfirmCaseServiceImpl implements IQqchModuleConfirmCaseService {

    @Autowired
    private QqchModuleConfirmCaseMapper qqchModuleConfirmCaseMapper;


    public QqchModuleConfirmCase getQqchModuleConfirmCase(QqchModuleConfirmCase qqchModuleConfirmCase) {
        return qqchModuleConfirmCaseMapper.getQqchModuleConfirmCase(qqchModuleConfirmCase);
    }

    public List<QqchModuleConfirmCase> getQqchModuleConfirmCaseList(QqchModuleConfirmCase qqchModuleConfirmCase) {
        return qqchModuleConfirmCaseMapper.getQqchModuleConfirmCaseList(qqchModuleConfirmCase);
    }

    /**
     * 插入一条确认记录
     * @param menuId 页面菜单id
     * @param stageIdentity 阶段标识
     */
    public void addConfirmRecord(String menuId,String stageIdentity){
        //查询是否存在确认记录
        QqchModuleConfirmCase qqchModuleConfirmCase = new QqchModuleConfirmCase();
        qqchModuleConfirmCase.setModuleIdentity(menuId);
        qqchModuleConfirmCase.setStageIdentity(stageIdentity);
        qqchModuleConfirmCase = qqchModuleConfirmCaseMapper.getQqchModuleConfirmCase(qqchModuleConfirmCase);
        if(qqchModuleConfirmCase == null){
            //插入确认记录
            qqchModuleConfirmCase = new QqchModuleConfirmCase();
            qqchModuleConfirmCase.setId(IdWorker.createId());
            qqchModuleConfirmCase.setModuleIdentity(menuId);
            qqchModuleConfirmCase.setStageIdentity(stageIdentity);
            qqchModuleConfirmCase.setConfirmStatus(ConfirmStatus.CONFIRMED);
            qqchModuleConfirmCase.setConfirmUser(String.valueOf(SecurityUtils.getUserId()));
            qqchModuleConfirmCase.setConfirmUserName(SecurityUtils.getUserName());
            qqchModuleConfirmCase.setConfirmTime(DateUtils.getNowDate());
            qqchModuleConfirmCase.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            qqchModuleConfirmCase.setCreateUserName(SecurityUtils.getUserName());
            qqchModuleConfirmCase.setCreateTime(DateUtils.getNowDate());
            qqchModuleConfirmCaseMapper.insertQqchModuleConfirmCase(qqchModuleConfirmCase);
        }
    }

    @Override
    public List<QqchModuleConfirmCase> getModuleConfirmInfo(QqchModuleConfirmCase qqchModuleConfirmCase) {
        return qqchModuleConfirmCaseMapper.getModuleConfirmInfo(qqchModuleConfirmCase);
    }

    @Transactional
    public int insertQqchModuleConfirmCase(QqchModuleConfirmCase qqchModuleConfirmCase) {
        qqchModuleConfirmCase.setId(IdWorker.createId());
        qqchModuleConfirmCase.setCreateUser(SecurityUtils.getUserName());
        qqchModuleConfirmCase.setCreateTime(DateUtils.getNowDate());
        return qqchModuleConfirmCaseMapper.insertQqchModuleConfirmCase(qqchModuleConfirmCase);
    }

    @Transactional
    public int insertQqchModuleConfirmCaseList(List<QqchModuleConfirmCase> qqchModuleConfirmCaseList) {
        for (QqchModuleConfirmCase qqchModuleConfirmCase : qqchModuleConfirmCaseList) {
            qqchModuleConfirmCase.setId(IdWorker.createId());
            qqchModuleConfirmCase.setCreateUser(SecurityUtils.getUserName());
            qqchModuleConfirmCase.setCreateTime(DateUtils.getNowDate());
        }
        return qqchModuleConfirmCaseMapper.insertQqchModuleConfirmCaseList(qqchModuleConfirmCaseList);
    }

    @Transactional
    public int updateQqchModuleConfirmCase(QqchModuleConfirmCase qqchModuleConfirmCase) {
        qqchModuleConfirmCase.setUpdateUser(SecurityUtils.getUserName());
        qqchModuleConfirmCase.setUpdateTime(DateUtils.getNowDate());
        return qqchModuleConfirmCaseMapper.updateQqchModuleConfirmCase(qqchModuleConfirmCase);
    }

    @Transactional
    public int updateQqchModuleConfirmCaseList(List<QqchModuleConfirmCase> qqchModuleConfirmCaseList) {
        for (QqchModuleConfirmCase qqchModuleConfirmCase : qqchModuleConfirmCaseList) {
            qqchModuleConfirmCase.setUpdateUser(SecurityUtils.getUserName());
            qqchModuleConfirmCase.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchModuleConfirmCaseMapper.updateQqchModuleConfirmCaseList(qqchModuleConfirmCaseList);
    }

    @Transactional
    public int deleteQqchModuleConfirmCase(QqchModuleConfirmCase qqchModuleConfirmCase) {
        qqchModuleConfirmCase.setUpdateUser(SecurityUtils.getUserName());
        qqchModuleConfirmCase.setUpdateTime(DateUtils.getNowDate());
        return qqchModuleConfirmCaseMapper.deleteQqchModuleConfirmCase(qqchModuleConfirmCase);
    }

    @Transactional
    public int deleteQqchModuleConfirmCaseByPks(List<Long> qqchModuleConfirmCasePkList) {
        return qqchModuleConfirmCaseMapper.deleteQqchModuleConfirmCaseByPks(qqchModuleConfirmCasePkList);
    }
}
