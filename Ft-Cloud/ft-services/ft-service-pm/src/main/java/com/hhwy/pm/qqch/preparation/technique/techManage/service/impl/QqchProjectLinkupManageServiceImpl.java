package com.hhwy.pm.qqch.preparation.technique.techManage.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.impl.QqchModuleConfirmCaseServiceImpl;
import com.hhwy.pm.qqch.preparation.technique.techManage.domain.QqchProjectLinkupManage;
import com.hhwy.pm.qqch.preparation.technique.techManage.domain.vo.QqchProjectLinkupManageVo;
import com.hhwy.pm.qqch.preparation.technique.techManage.mapper.QqchProjectLinkupManageMapper;
import com.hhwy.pm.qqch.preparation.technique.techManage.service.IQqchProjectLinkupManageService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.ButtonMarkUtil;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.idworker.IdWorker;
import io.seata.common.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author han
 * @date 2023-07-25 11:24:02
 * @remark 技术管理项目沟通管理
 */
@Service
public class QqchProjectLinkupManageServiceImpl implements IQqchProjectLinkupManageService {

    @Autowired
    private QqchProjectLinkupManageMapper qqchProjectLinkupManageMapper;

    @Autowired
    private QqchModuleConfirmCaseServiceImpl qqchModuleConfirmCaseService;

    @Autowired
    private IQqchReviewService qqchReviewService;


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
    public void insertQqchProjectLinkupManageList(List<QqchProjectLinkupManage> qqchProjectLinkupManageList, BigDecimal version) {
        //删除旧数据
        QqchProjectLinkupManage qqchProjectLinkupManage = new QqchProjectLinkupManage();
        qqchProjectLinkupManage.setVersion(version);
        qqchProjectLinkupManageMapper.deleteQqchProjectLinkupManage(qqchProjectLinkupManage);

        if(CollectionUtils.isEmpty(qqchProjectLinkupManageList)){
            return;
        }
        int sort = 1;
        String valid = Valid.NO;
        if(version.compareTo(BigDecimal.ONE) == 0){
            valid = Valid.YES;
        }
        for (QqchProjectLinkupManage projectLinkupManage : qqchProjectLinkupManageList) {
            projectLinkupManage.setId(IdWorker.createId());
            projectLinkupManage.setValid(valid);
            projectLinkupManage.setVersion(version);
            projectLinkupManage.setSort(sort++);
            projectLinkupManage.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            projectLinkupManage.setCreateUserName(SecurityUtils.getUserName());
            projectLinkupManage.setCreateTime(DateUtils.getNowDate());
        }
        qqchProjectLinkupManageMapper.insertQqchProjectLinkupManageList(qqchProjectLinkupManageList);
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

    /**
     * 获取技术管理项目沟通管理Vo
     * @param qqchProjectLinkupManage
     * @return
     */
    @Override
    public QqchProjectLinkupManageVo getQqchProjectLinkupManageVo(QqchProjectLinkupManage qqchProjectLinkupManage) {
        QqchProjectLinkupManageVo qqchProjectLinkupManageVo = new QqchProjectLinkupManageVo();

        BigDecimal version = qqchProjectLinkupManage.getVersion();
        version = VersionUtil.getVersion("qqch_project_linkup_manage",version);

        qqchProjectLinkupManage.setVersion(version);
        List<QqchProjectLinkupManage> qqchProjectLinkupManageList = qqchProjectLinkupManageMapper.getQqchProjectLinkupManageList(qqchProjectLinkupManage);

        qqchProjectLinkupManageVo.setVersion(version);
        qqchProjectLinkupManageVo.setStageIdentity(qqchReviewService.getStage());
        qqchProjectLinkupManageVo.setList(qqchProjectLinkupManageList);
        return qqchProjectLinkupManageVo;
    }

    /**
     * 保存/确认/提交
     * @param qqchProjectLinkupManageVo
     * @return
     */
    @Override
    @Transactional
    public void save(QqchProjectLinkupManageVo qqchProjectLinkupManageVo) {
        String buttonMark = qqchProjectLinkupManageVo.getButtonMark();
        ButtonMarkUtil.checkButtonMark(buttonMark);

        BigDecimal version = qqchProjectLinkupManageVo.getVersion();
        List<QqchProjectLinkupManage> qqchProjectLinkupManageList = qqchProjectLinkupManageVo.getList();

        //处理数据
        this.insertQqchProjectLinkupManageList(qqchProjectLinkupManageList,version);

        //处理确认状态是确认
        if(ButtonMark.CONFIRM.equals(buttonMark)){
            //插入确认记录
            String menuId = qqchProjectLinkupManageVo.getMenuId();
            String stageIdentity = qqchProjectLinkupManageVo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
    }
}
