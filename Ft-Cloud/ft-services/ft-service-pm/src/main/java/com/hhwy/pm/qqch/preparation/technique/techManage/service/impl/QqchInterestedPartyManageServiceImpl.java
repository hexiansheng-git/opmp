package com.hhwy.pm.qqch.preparation.technique.techManage.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.impl.QqchModuleConfirmCaseServiceImpl;
import com.hhwy.pm.qqch.preparation.technique.techManage.domain.QqchInterestedPartyManage;
import com.hhwy.pm.qqch.preparation.technique.techManage.domain.vo.QqchInterestedPartyManageVo;
import com.hhwy.pm.qqch.preparation.technique.techManage.mapper.QqchInterestedPartyManageMapper;
import com.hhwy.pm.qqch.preparation.technique.techManage.service.IQqchInterestedPartyManageService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.ButtonMarkUtil;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author han
 * @date 2023-07-25 10:53:45
 * @remark 技术管理相关方管理
 */
@Service
public class QqchInterestedPartyManageServiceImpl implements IQqchInterestedPartyManageService {

    @Autowired
    private QqchInterestedPartyManageMapper qqchInterestedPartyManageMapper;

    @Autowired
    private QqchModuleConfirmCaseServiceImpl qqchModuleConfirmCaseService;

    @Autowired
    private IQqchReviewService qqchReviewService;


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
    public void insertQqchInterestedPartyManageList(List<QqchInterestedPartyManage> qqchInterestedPartyManageList, BigDecimal version) {
        //删除旧数据
        QqchInterestedPartyManage qqchInterestedPartyManage = new QqchInterestedPartyManage();
        qqchInterestedPartyManage.setVersion(version);
        qqchInterestedPartyManageMapper.deleteQqchInterestedPartyManage(qqchInterestedPartyManage);

        int sort = 1;
        String valid = Valid.NO;
        if(version.compareTo(BigDecimal.ONE) == 0){
            valid = Valid.YES;
        }
        for (QqchInterestedPartyManage interestedPartyManage : qqchInterestedPartyManageList) {
            interestedPartyManage.setId(IdWorker.createId());
            interestedPartyManage.setValid(valid);
            interestedPartyManage.setVersion(version);
            interestedPartyManage.setSort(sort++);
            interestedPartyManage.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            interestedPartyManage.setCreateUserName(SecurityUtils.getUserName());
            interestedPartyManage.setCreateTime(DateUtils.getNowDate());
        }
        qqchInterestedPartyManageMapper.insertQqchInterestedPartyManageList(qqchInterestedPartyManageList);
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

    /**
     * 获取技术管理相关方管理Vo
     * @param qqchInterestedPartyManage
     * @return
     */
    @Override
    public QqchInterestedPartyManageVo getQqchInterestedPartyManageVo(QqchInterestedPartyManage qqchInterestedPartyManage) {
        QqchInterestedPartyManageVo qqchInterestedPartyManageVo = new QqchInterestedPartyManageVo();

        BigDecimal version = qqchInterestedPartyManage.getVersion();
        version = VersionUtil.getVersion("qqch_interested_party_manage",version);

        qqchInterestedPartyManage.setVersion(version);
        List<QqchInterestedPartyManage> qqchInterestedPartyManageList = qqchInterestedPartyManageMapper.getQqchInterestedPartyManageList(qqchInterestedPartyManage);

        qqchInterestedPartyManageVo.setVersion(version);
        qqchInterestedPartyManageVo.setStageIdentity(qqchReviewService.getStage());
        qqchInterestedPartyManageVo.setQqchInterestedPartyManageList(qqchInterestedPartyManageList);
        return qqchInterestedPartyManageVo;
    }

    /**
     * 保存/确认/提交
     * @param qqchInterestedPartyManageVo
     * @return
     */
    @Override
    @Transactional
    public void save(QqchInterestedPartyManageVo qqchInterestedPartyManageVo) {
        String buttonMark = qqchInterestedPartyManageVo.getButtonMark();
        ButtonMarkUtil.checkButtonMark(buttonMark);

        BigDecimal version = qqchInterestedPartyManageVo.getVersion();
        List<QqchInterestedPartyManage> qqchInterestedPartyManageList = qqchInterestedPartyManageVo.getQqchInterestedPartyManageList();

        //处理数据
        this.insertQqchInterestedPartyManageList(qqchInterestedPartyManageList,version);

        //处理确认状态是确认
        if(ButtonMark.CONFIRM.equals(buttonMark)){
            //插入确认记录
            String menuId = qqchInterestedPartyManageVo.getMenuId();
            String stageIdentity = qqchInterestedPartyManageVo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
    }
}
