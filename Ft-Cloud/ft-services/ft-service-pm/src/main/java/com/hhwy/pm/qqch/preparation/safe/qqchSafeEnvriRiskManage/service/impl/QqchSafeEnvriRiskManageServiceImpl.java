package com.hhwy.pm.qqch.preparation.safe.qqchSafeEnvriRiskManage.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.safe.qqchSafeEnvriRiskManage.domain.QqchSafeEnvriRiskManage;
import com.hhwy.pm.qqch.preparation.safe.qqchSafeEnvriRiskManage.mapper.QqchSafeEnvriRiskManageMapper;
import com.hhwy.pm.qqch.preparation.safe.qqchSafeEnvriRiskManage.service.IQqchSafeEnvriRiskManageService;
import com.hhwy.pm.qqch.preparation.safe.qqchSafeEnvriRiskManage.vo.QqchSafeEnvriRiskManageVo;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.validation.JyDetailsUtil;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author zq
 * @date 2023-08-14 14:04:07
 * @remark
 */
@Service
public class QqchSafeEnvriRiskManageServiceImpl implements IQqchSafeEnvriRiskManageService {

    @Autowired
    private QqchSafeEnvriRiskManageMapper qqchSafeEnvriRiskManageMapper;
    @Autowired
    private IQqchReviewService qqchReviewService;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;

    public QqchSafeEnvriRiskManage getQqchSafeEnvriRiskManage(QqchSafeEnvriRiskManage qqchSafeEnvriRiskManage) {
        return qqchSafeEnvriRiskManageMapper.getQqchSafeEnvriRiskManage(qqchSafeEnvriRiskManage);
    }

    public List<QqchSafeEnvriRiskManage> getQqchSafeEnvriRiskManageList(QqchSafeEnvriRiskManage qqchSafeEnvriRiskManage) {
        return qqchSafeEnvriRiskManageMapper.getQqchSafeEnvriRiskManageList(qqchSafeEnvriRiskManage);
    }

    @Transactional
    public int insertQqchSafeEnvriRiskManage(QqchSafeEnvriRiskManage qqchSafeEnvriRiskManage) {
        qqchSafeEnvriRiskManage.setId(IdWorker.createId());
        qqchSafeEnvriRiskManage.setCreateUser(SecurityUtils.getUserName());
        qqchSafeEnvriRiskManage.setCreateTime(DateUtils.getNowDate());
        return qqchSafeEnvriRiskManageMapper.insertQqchSafeEnvriRiskManage(qqchSafeEnvriRiskManage);
    }

    @Transactional
    public int insertQqchSafeEnvriRiskManageList(QqchSafeEnvriRiskManageVo voParam) {

        //删除数据
        QqchSafeEnvriRiskManage qqchSafeEnvriRiskManage = new QqchSafeEnvriRiskManage();
        qqchSafeEnvriRiskManage.setVersion(voParam.getVersion());
        qqchSafeEnvriRiskManageMapper.deleteQqchSafeEnvriRiskManage(qqchSafeEnvriRiskManage);
        if (!CollectionUtils.isEmpty(voParam.getList())) {
            List<QqchSafeEnvriRiskManage> list = voParam.getList();
            //校验数据必填
            if ("1".equals(voParam.getButtonMark()) || "2".equals(voParam.getButtonMark())) {//确认
                JyDetailsUtil.jyDetails(list, ValidationGroups.Save.class);
            }

            for (QqchSafeEnvriRiskManage safeEnvriRiskManage : list) {
                safeEnvriRiskManage.setId(IdWorker.createId());
                safeEnvriRiskManage.setVersion(voParam.getVersion());
                if (voParam.getVersion().compareTo(BigDecimal.ONE) == 0) {
                    safeEnvriRiskManage.setValid(Valid.YES);
                }
                safeEnvriRiskManage.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                safeEnvriRiskManage.setCreateUserName(SecurityUtils.getUserName());
                safeEnvriRiskManage.setCreateTime(DateUtils.getNowDate());
            }
            qqchSafeEnvriRiskManageMapper.insertQqchSafeEnvriRiskManageList(list);
        }
        String buttonMark = voParam.getButtonMark();

        if (ButtonMark.CONFIRM.equals(buttonMark)) {
            // 插入确认状态
            qqchReviewService.updateFinishNum(voParam.getStageIdentity(), voParam.getModuleIdentity());
            qqchModuleConfirmCaseService.addConfirmRecord(voParam.getMenuId(), voParam.getStageIdentity());

        }
        return 1;
    }

    @Transactional
    public int updateQqchSafeEnvriRiskManage(QqchSafeEnvriRiskManage qqchSafeEnvriRiskManage) {
        qqchSafeEnvriRiskManage.setUpdateUser(SecurityUtils.getUserName());
        qqchSafeEnvriRiskManage.setUpdateTime(DateUtils.getNowDate());
        return qqchSafeEnvriRiskManageMapper.updateQqchSafeEnvriRiskManage(qqchSafeEnvriRiskManage);
    }

    @Transactional
    public int updateQqchSafeEnvriRiskManageList(List<QqchSafeEnvriRiskManage> qqchSafeEnvriRiskManageList) {
        for (QqchSafeEnvriRiskManage qqchSafeEnvriRiskManage : qqchSafeEnvriRiskManageList) {
            qqchSafeEnvriRiskManage.setUpdateUser(SecurityUtils.getUserName());
            qqchSafeEnvriRiskManage.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchSafeEnvriRiskManageMapper.updateQqchSafeEnvriRiskManageList(qqchSafeEnvriRiskManageList);
    }

    @Transactional
    public int deleteQqchSafeEnvriRiskManage(QqchSafeEnvriRiskManage qqchSafeEnvriRiskManage) {
        qqchSafeEnvriRiskManage.setUpdateUser(SecurityUtils.getUserName());
        qqchSafeEnvriRiskManage.setUpdateTime(DateUtils.getNowDate());
        return qqchSafeEnvriRiskManageMapper.deleteQqchSafeEnvriRiskManage(qqchSafeEnvriRiskManage);
    }

    @Transactional
    public int deleteQqchSafeEnvriRiskManageByPks(List<Long> qqchSafeEnvriRiskManageList) {
        return qqchSafeEnvriRiskManageMapper.deleteQqchSafeEnvriRiskManageByPks(qqchSafeEnvriRiskManageList);
    }

    @Override
    public QqchSafeEnvriRiskManageVo getList(BigDecimal version) {
        QqchSafeEnvriRiskManageVo qqchSafeEnvriRiskManageVo = new QqchSafeEnvriRiskManageVo();
        version = VersionUtil.getVersion("qqch_safe_envri_risk_manage", version);

        QqchSafeEnvriRiskManage qqchSafeEnvriRiskManage = new QqchSafeEnvriRiskManage();
        qqchSafeEnvriRiskManage.setVersion(version);
        List<QqchSafeEnvriRiskManage> list = qqchSafeEnvriRiskManageMapper.getQqchSafeEnvriRiskManageList(qqchSafeEnvriRiskManage);

        qqchSafeEnvriRiskManageVo.setVersion(version);
        qqchSafeEnvriRiskManageVo.setStageIdentity(qqchReviewService.getStage());
        qqchSafeEnvriRiskManageVo.setList(list);
        return qqchSafeEnvriRiskManageVo;
    }
}
