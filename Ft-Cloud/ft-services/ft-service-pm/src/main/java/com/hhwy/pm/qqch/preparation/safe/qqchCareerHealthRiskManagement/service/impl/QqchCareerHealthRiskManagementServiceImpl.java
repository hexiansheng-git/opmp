package com.hhwy.pm.qqch.preparation.safe.qqchCareerHealthRiskManagement.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.safe.qqchCareerHealthRiskManagement.domain.QqchCareerHealthRiskManagement;
import com.hhwy.pm.qqch.preparation.safe.qqchCareerHealthRiskManagement.domain.vo.QqchCareerHealthRiskManagementVo;
import com.hhwy.pm.qqch.preparation.safe.qqchCareerHealthRiskManagement.mapper.QqchCareerHealthRiskManagementMapper;
import com.hhwy.pm.qqch.preparation.safe.qqchCareerHealthRiskManagement.service.IQqchCareerHealthRiskManagementService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.ButtonMarkUtil;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.validation.JyDetailsUtil;
import com.hhwy.utils.validation.ValidationGroups;
import io.seata.common.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author ldd
 * @date 2023-08-09 15:01:41
 * @remark
 */
@Service
public class QqchCareerHealthRiskManagementServiceImpl implements IQqchCareerHealthRiskManagementService {

    @Autowired
    private QqchCareerHealthRiskManagementMapper qqchCareerHealthRiskManagementMapper;
    @Autowired
    private IQqchReviewService qqchReviewService;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;


    public QqchCareerHealthRiskManagement getQqchCareerHealthRiskManagement(QqchCareerHealthRiskManagement qqchCareerHealthRiskManagement) {
        return qqchCareerHealthRiskManagementMapper.getQqchCareerHealthRiskManagement(qqchCareerHealthRiskManagement);
    }


    @Transactional
    public int insertQqchCareerHealthRiskManagement(QqchCareerHealthRiskManagement qqchCareerHealthRiskManagement) {
        qqchCareerHealthRiskManagement.setId(IdWorker.createId());
        qqchCareerHealthRiskManagement.setCreateUser(SecurityUtils.getUserName());
        qqchCareerHealthRiskManagement.setCreateTime(DateUtils.getNowDate());
        return qqchCareerHealthRiskManagementMapper.insertQqchCareerHealthRiskManagement(qqchCareerHealthRiskManagement);
    }


    @Transactional
    public int updateQqchCareerHealthRiskManagement(QqchCareerHealthRiskManagement qqchCareerHealthRiskManagement) {
        qqchCareerHealthRiskManagement.setUpdateUser(SecurityUtils.getUserName());
        qqchCareerHealthRiskManagement.setUpdateTime(DateUtils.getNowDate());
        return qqchCareerHealthRiskManagementMapper.updateQqchCareerHealthRiskManagement(qqchCareerHealthRiskManagement);
    }

    @Transactional
    public int updateQqchCareerHealthRiskManagementList(List<QqchCareerHealthRiskManagement> qqchCareerHealthRiskManagementList) {
        for (QqchCareerHealthRiskManagement qqchCareerHealthRiskManagement : qqchCareerHealthRiskManagementList) {
            qqchCareerHealthRiskManagement.setUpdateUser(SecurityUtils.getUserName());
            qqchCareerHealthRiskManagement.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchCareerHealthRiskManagementMapper.updateQqchCareerHealthRiskManagementList(qqchCareerHealthRiskManagementList);
    }

    @Transactional
    public int deleteQqchCareerHealthRiskManagement(QqchCareerHealthRiskManagement qqchCareerHealthRiskManagement) {
        qqchCareerHealthRiskManagement.setUpdateUser(SecurityUtils.getUserName());
        qqchCareerHealthRiskManagement.setUpdateTime(DateUtils.getNowDate());
        return qqchCareerHealthRiskManagementMapper.deleteQqchCareerHealthRiskManagement(qqchCareerHealthRiskManagement);
    }

    @Transactional
    public int deleteQqchCareerHealthRiskManagementByPks(List<Long> qqchCareerHealthRiskManagementPkList) {
        return qqchCareerHealthRiskManagementMapper.deleteQqchCareerHealthRiskManagementByPks(qqchCareerHealthRiskManagementPkList);
    }

    /**
     *  列表接口
     * @param qqchCareerHealthRiskManagement
     * @return
     */
    public QqchCareerHealthRiskManagementVo getQqchCareerHealthRiskManagementList(QqchCareerHealthRiskManagement qqchCareerHealthRiskManagement) {
        QqchCareerHealthRiskManagementVo vo = new QqchCareerHealthRiskManagementVo();
        BigDecimal version = VersionUtil.getVersion("qqch_career_health_risk_management", qqchCareerHealthRiskManagement.getVersion());
        qqchCareerHealthRiskManagement.setVersion(version);
        List<QqchCareerHealthRiskManagement> qqchCareerHealthRiskManagementList = qqchCareerHealthRiskManagementMapper.getQqchCareerHealthRiskManagementList(qqchCareerHealthRiskManagement);
        vo.setVersion(version);
        vo.setStageIdentity(qqchReviewService.getStage());
        vo.setQqchCareerHealthRiskManagementList(qqchCareerHealthRiskManagementList);
        return vo;
    }

    /**
     * 保存/确认/提交
     * @param vo
     */
    @Override
    public void save(QqchCareerHealthRiskManagementVo vo) {
        String buttonMark = vo.getButtonMark();
        ButtonMarkUtil.checkButtonMark(buttonMark);

        BigDecimal version = vo.getVersion();
        List<QqchCareerHealthRiskManagement> qqchCareerHealthRiskManagementList = vo.getQqchCareerHealthRiskManagementList();
        if(CollectionUtils.isEmpty(qqchCareerHealthRiskManagementList)){
            return;
        }else {
            //校验数据必填
            if("1".equals(vo.getButtonMark())||"2".equals(vo.getButtonMark())){//确认
                JyDetailsUtil.jyDetails(qqchCareerHealthRiskManagementList, ValidationGroups.Save.class);
            }
        }
        this.insertQqchCareerHealthRiskManagementList(qqchCareerHealthRiskManagementList,version);

        //判断是否是确认
        if(ButtonMark.CONFIRM.equals(buttonMark)){
            //插入确认记录
            String menuId = vo.getMenuId();
            String stageIdentity = vo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
    }

    @Transactional
    public int insertQqchCareerHealthRiskManagementList(List<QqchCareerHealthRiskManagement> qqchCareerHealthRiskManagementList,BigDecimal version) {
        //删除旧数据
        QqchCareerHealthRiskManagement qqchCareerHealthRiskManagement1 = new QqchCareerHealthRiskManagement();
        qqchCareerHealthRiskManagement1.setVersion(version);
        qqchCareerHealthRiskManagementMapper.deleteQqchCareerHealthRiskManagement(qqchCareerHealthRiskManagement1);
        String valid = Valid.NO;
        if(version.compareTo(BigDecimal.ONE) == 0){
            valid = Valid.YES;
        }
        for (QqchCareerHealthRiskManagement qqchCareerHealthRiskManagement : qqchCareerHealthRiskManagementList) {
            qqchCareerHealthRiskManagement.setValid(valid);
            qqchCareerHealthRiskManagement.setVersion(version);
            qqchCareerHealthRiskManagement.setId(IdWorker.createId());
            qqchCareerHealthRiskManagement.setCreateUser(SecurityUtils.getUserName());
            qqchCareerHealthRiskManagement.setCreateTime(DateUtils.getNowDate());
        }
        return qqchCareerHealthRiskManagementMapper.insertQqchCareerHealthRiskManagementList(qqchCareerHealthRiskManagementList);
    }
}
