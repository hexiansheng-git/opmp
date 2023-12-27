package com.hhwy.pm.qqch.preparation.technique.expert.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.impl.QqchModuleConfirmCaseServiceImpl;
import com.hhwy.pm.qqch.preparation.technique.expert.domain.QqchTargetAdvisoryOrgan;
import com.hhwy.pm.qqch.preparation.technique.expert.domain.vo.QqchTargetAdvisoryOrganVo;
import com.hhwy.pm.qqch.preparation.technique.expert.mapper.QqchTargetAdvisoryOrganMapper;
import com.hhwy.pm.qqch.preparation.technique.expert.service.IQqchTargetAdvisoryOrganService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.ButtonMarkUtil;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.pm.qyzs.speciallistOrg.qyzsEnquiryOrgLibrary.domain.QyzsEnquiryOrgLibrary;
import com.hhwy.pm.xmsl.project.domain.vo.ProjectBasicInfo;
import com.hhwy.pm.xmsl.project.service.IXmslProjectBasicInfoService;
import com.hhwy.utils.idworker.IdWorker;
import io.seata.common.util.CollectionUtils;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author han
 * @date 2023-07-25 10:56:43
 * @remark 外部目标咨询机构选择
 */
@Service
public class QqchTargetAdvisoryOrganServiceImpl implements IQqchTargetAdvisoryOrganService {

    @Autowired
    private QqchTargetAdvisoryOrganMapper qqchTargetAdvisoryOrganMapper;

    @Autowired
    private QqchModuleConfirmCaseServiceImpl qqchModuleConfirmCaseService;

    @Autowired
    private IQqchReviewService qqchReviewService;

    @Autowired
    private IXmslProjectBasicInfoService xmslProjectBasicInfoService;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;


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
    public void insertQqchTargetAdvisoryOrganList(List<QqchTargetAdvisoryOrgan> qqchTargetAdvisoryOrganList, BigDecimal version) {
        //删除旧数据
        QqchTargetAdvisoryOrgan qqchTargetAdvisoryOrgan = new QqchTargetAdvisoryOrgan();
        qqchTargetAdvisoryOrgan.setVersion(version);
        qqchTargetAdvisoryOrganMapper.deleteQqchTargetAdvisoryOrgan(qqchTargetAdvisoryOrgan);

        if(CollectionUtils.isEmpty(qqchTargetAdvisoryOrganList)){
            return;
        }
        String valid = Valid.NO;
        if(version.compareTo(BigDecimal.ONE) == 0){
            valid = Valid.YES;
        }
        for (QqchTargetAdvisoryOrgan targetAdvisoryOrgan : qqchTargetAdvisoryOrganList) {
            targetAdvisoryOrgan.setId(IdWorker.createId());
            targetAdvisoryOrgan.setValid(valid);
            targetAdvisoryOrgan.setVersion(version);
            targetAdvisoryOrgan.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            targetAdvisoryOrgan.setCreateUserName(SecurityUtils.getUserName());
            targetAdvisoryOrgan.setCreateTime(DateUtils.getNowDate());
        }
        qqchTargetAdvisoryOrganMapper.insertQqchTargetAdvisoryOrganList(qqchTargetAdvisoryOrganList);
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

    /**
     * 获取外部目标咨询机构选择Vo
     * @param qqchTargetAdvisoryOrgan
     * @return
     */
    @Override
    public QqchTargetAdvisoryOrganVo getQqchTargetAdvisoryOrganVo(QqchTargetAdvisoryOrgan qqchTargetAdvisoryOrgan) {
        QqchTargetAdvisoryOrganVo qqchTargetAdvisoryOrganVo = new QqchTargetAdvisoryOrganVo();

        BigDecimal version = qqchTargetAdvisoryOrgan.getVersion();
        version = VersionUtil.getVersion("qqch_target_advisory_organ",version);

        qqchTargetAdvisoryOrgan.setVersion(version);
        List<QqchTargetAdvisoryOrgan> qqchTargetAdvisoryOrganList = qqchTargetAdvisoryOrganMapper.getQqchTargetAdvisoryOrganList(qqchTargetAdvisoryOrgan);

        qqchTargetAdvisoryOrganVo.setVersion(version);
        qqchTargetAdvisoryOrganVo.setStageIdentity(qqchReviewService.getStage());
        qqchTargetAdvisoryOrganVo.setList(qqchTargetAdvisoryOrganList);
        return qqchTargetAdvisoryOrganVo;
    }

    /**
     * 保存/确认/提交
     * @param qqchTargetAdvisoryOrganVo
     * @return
     */
    @Override
    @Transactional
    public void save(QqchTargetAdvisoryOrganVo qqchTargetAdvisoryOrganVo) {
        String buttonMark = qqchTargetAdvisoryOrganVo.getButtonMark();
        ButtonMarkUtil.checkButtonMark(buttonMark);

        BigDecimal version = qqchTargetAdvisoryOrganVo.getVersion();
        List<QqchTargetAdvisoryOrgan> qqchTargetAdvisoryOrganList = qqchTargetAdvisoryOrganVo.getList();

        //处理数据
        this.insertQqchTargetAdvisoryOrganList(qqchTargetAdvisoryOrganList,version);

        //处理确认状态是确认
        if(ButtonMark.CONFIRM.equals(buttonMark)){
            //插入确认记录
            String menuId = qqchTargetAdvisoryOrganVo.getMenuId();
            String stageIdentity = qqchTargetAdvisoryOrganVo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
    }

    @Override
    public void addToQyzsEnquiryOrgLibrary(List<QqchTargetAdvisoryOrgan> list) {
        List<QqchTargetAdvisoryOrgan> needPushList = list.stream().filter(o -> !"0".equals(o.getPtVar1())).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(needPushList)){
            return;
        }
        ProjectBasicInfo projectInfo = xmslProjectBasicInfoService.projectInfo();
        List<QyzsEnquiryOrgLibrary> orgLibraryList = new ArrayList<>();
        for (QqchTargetAdvisoryOrgan organ : needPushList) {
            QyzsEnquiryOrgLibrary orgLibrary = new QyzsEnquiryOrgLibrary();
            orgLibrary.setId(IdWorker.createId());
            orgLibrary.setOrgName(organ.getOrganName());
            orgLibrary.setOrgType(organ.getOrganType());
            orgLibrary.setEnterpriseCertification(organ.getEnterpriseQualification());
            orgLibrary.setMainBusiness(organ.getPrimaryBusiness());
            orgLibrary.setSpecialtyDirection(organ.getMajorField());
            orgLibrary.setDataSource(projectInfo.getProjectName());
            orgLibrary.setPersonId(Math.toIntExact(SecurityUtils.getUserId()));
            orgLibrary.setPersonName(SecurityUtils.getUserName());
            orgLibrary.setEditTime(DateUtils.getNowDate());
            orgLibrary.setRemark(organ.getRemark());
            orgLibrary.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            orgLibrary.setCreateUserName(SecurityUtils.getUserName());
            orgLibrary.setCreateTime(DateUtils.getNowDate());
            orgLibraryList.add(orgLibrary);
        }

        rocketMQTemplate.convertAndSend("qyzs_enquiry_org_library:tenantSuccess",orgLibraryList);
    }
}
