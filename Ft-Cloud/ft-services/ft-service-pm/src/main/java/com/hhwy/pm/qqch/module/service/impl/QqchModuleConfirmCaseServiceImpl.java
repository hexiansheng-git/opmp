package com.hhwy.pm.qqch.module.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ConfirmStatus;
import com.hhwy.pm.qqch.module.domain.QqchModuleConfirmCase;
import com.hhwy.pm.qqch.module.mapper.QqchModuleConfirmCaseMapper;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.utils.common.CommonAssert;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
     * 根据菜单id，阶段，确认人id获取确认状态
     * @param menuId 菜单id
     * @param stage 阶段
     * @param confirmUser 确认人id
     * @return
     */
    public List<QqchModuleConfirmCase>  getConfirmStatus(String menuId,String stage,String confirmUser){
        QqchModuleConfirmCase qqchModuleConfirmCase = new QqchModuleConfirmCase();
        qqchModuleConfirmCase.setModuleIdentity(menuId);
        qqchModuleConfirmCase.setStageIdentity(stage);
        qqchModuleConfirmCase.setConfirmUser(confirmUser);
        List<QqchModuleConfirmCase> moduleConfirmInfo = qqchModuleConfirmCaseMapper.getQqchModuleConfirmCaseList(qqchModuleConfirmCase);
        return moduleConfirmInfo;
    }

    /**
     * 插入一条确认记录
     * @param menuId 页面菜单id
     * @param stageIdentity 阶段标识
     */
    @Transactional
    public void addConfirmRecord(String menuId,String stageIdentity){
        CommonAssert.notBlank(menuId,"菜单id不能为空！");
        CommonAssert.notBlank(stageIdentity,"阶段不能为空！");
        //查询是否存在确认记录
        QqchModuleConfirmCase qqchModuleConfirmCase = new QqchModuleConfirmCase();
        qqchModuleConfirmCase.setModuleIdentity(menuId);
        qqchModuleConfirmCase.setStageIdentity(stageIdentity);
        qqchModuleConfirmCase.setConfirmUser(String.valueOf(SecurityUtils.getUserId()));
        qqchModuleConfirmCase = qqchModuleConfirmCaseMapper.getQqchModuleConfirmCase(qqchModuleConfirmCase);

        if(qqchModuleConfirmCase != null){
            throw new RuntimeException("该页面已确认完成，请勿重复确认！");
        }

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
