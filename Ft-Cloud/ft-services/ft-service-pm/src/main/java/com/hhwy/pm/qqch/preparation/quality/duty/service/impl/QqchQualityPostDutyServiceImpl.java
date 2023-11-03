package com.hhwy.pm.qqch.preparation.quality.duty.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.ehr.domain.Attachment;
import com.hhwy.pm.ehr.domain.PersonCertifyCompetency;
import com.hhwy.pm.ehr.service.IEhrService;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.quality.duty.domain.QqchQualityPostDuty;
import com.hhwy.pm.qqch.preparation.quality.duty.domain.vo.QqchQualityPostDutyVo;
import com.hhwy.pm.qqch.preparation.quality.duty.mapper.QqchQualityPostDutyMapper;
import com.hhwy.pm.qqch.preparation.quality.duty.service.IQqchQualityPostDutyService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.validation.JyDetailsUtil;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zhenglili
 * @date 2023-08-03 14:28:57
 * @remark 9.1.1 质量岗位职责
 */
@Service
public class QqchQualityPostDutyServiceImpl implements IQqchQualityPostDutyService {

    @Autowired
    private QqchQualityPostDutyMapper qqchQualityPostDutyMapper;
    @Autowired
    private IQqchReviewService qqchReviewService;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;
    @Autowired
    private IEhrService ehrService;

    /**
     * 列表
     *
     * @param version
     * @return
     */
    public QqchQualityPostDutyVo getQqchQualityPostDutyList(BigDecimal version) {
        QqchQualityPostDutyVo vo = new QqchQualityPostDutyVo();
        version = VersionUtil.getVersion("qqch_quality_post_duty", version);

        List<QqchQualityPostDuty> list = this.getNewVersionList(version);
        vo.setVersion(version);
        vo.setStageIdentity(qqchReviewService.getStage());
        vo.setList(list);
        return vo;
    }

    /**
     * 保存/确认/提交
     *
     * @param voParam
     * @return
     */
    @Transactional
    public void batchSave(QqchQualityPostDutyVo voParam) {
        // 清空数据库表中数据
        QqchQualityPostDuty deleteParam = new QqchQualityPostDuty();
        deleteParam.setVersion(voParam.getVersion());
        qqchQualityPostDutyMapper.deleteQqchQualityPostDuty(deleteParam);

        String buttonMark = voParam.getButtonMark();
        if (!CollectionUtils.isEmpty(voParam.getList())) {
            // 校验非空
            if (!ButtonMark.SAVE.equals(buttonMark)) {
                JyDetailsUtil.jyDetails(voParam.getList(), ValidationGroups.Save.class);
            }

            for (QqchQualityPostDuty qqchQualityPostDuty : voParam.getList()) {
                qqchQualityPostDuty.setId(IdWorker.createId());
                qqchQualityPostDuty.setVersion(voParam.getVersion());
                if (voParam.getVersion().compareTo(BigDecimal.ONE) == 0) {
                    qqchQualityPostDuty.setValid(Valid.YES);
                }
                qqchQualityPostDuty.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                qqchQualityPostDuty.setCreateUserName(SecurityUtils.getUserName());
                qqchQualityPostDuty.setCreateTime(DateUtils.getNowDate());
            }
            qqchQualityPostDutyMapper.insertQqchQualityPostDutyList(voParam.getList());
        }

        if (ButtonMark.CONFIRM.equals(buttonMark)) {
            // 插入确认状态
            String menuId = voParam.getMenuId();
            String stageIdentity = voParam.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId, stageIdentity);
        }
    }

    /**
     * 获取最新版本列表
     *
     * @param version
     * @return
     */
    public List<QqchQualityPostDuty> getNewVersionList(BigDecimal version) {
        QqchQualityPostDuty qryParam = new QqchQualityPostDuty();
        qryParam.setVersion(version);
        List<QqchQualityPostDuty> list = qqchQualityPostDutyMapper.getQqchQualityPostDutyList(qryParam);
        return list;
    }

    /**
     * 9.1.2弹窗
     * @return
     */
    @Override
    public List<QqchQualityPostDuty> getPopWindows() throws ParserConfigurationException, IOException, SAXException {
        BigDecimal version = VersionUtil.getVersion("qqch_quality_post_duty", null);
        List<QqchQualityPostDuty> list = qqchQualityPostDutyMapper.getDistinctQualityPostDutyList(version);

        String userName4As = list.stream().map(QqchQualityPostDuty::getPersonId).collect(Collectors.joining());
        Map<String, List<PersonCertifyCompetency>> certList = ehrService.getCertListByUserName4As(userName4As);

        list.stream().forEach(duty -> {
            String personId = duty.getPersonId();
            List<PersonCertifyCompetency> personCertifyCompetencyList = certList.get(personId);
            if(CollectionUtils.isEmpty(personCertifyCompetencyList)){
                personCertifyCompetencyList = new ArrayList<>();
                for (int i = 1; i < 4; i++) {
                    PersonCertifyCompetency competency1 = new PersonCertifyCompetency();
                    competency1.setCategoryName("职(执)业资格类别名称" + i);
                    competency1.setCategoryNumber("职(执)业资格类别编码" + i);
                    competency1.setCertificateName("职(执)业资格名称" + i);
                    competency1.setCertificateNo("职(执)业资格编码" + i);
                    competency1.setLevelName("职(执)业资格级别名称" + i);
                    competency1.setLevelNumber("职(执)业资格级别编码" + i);
                    competency1.setZymc("专业名称" + i);
                    competency1.setPrzcny("聘任注册时间 格式为:yyyy-MM-dd" + i);
                    competency1.setAppointUnit("聘任或注册单位" + i);
                    competency1.setCertificateNumber("注册编号" + i);
                    competency1.setIsHighest(true);
                    competency1.setIssueDate("2022-02-01");

                    List<Attachment> attachmentList = new ArrayList<>();
                    Attachment attachment = new Attachment();
                    attachment.setId("jkljlkjlksdf0980234" + i);
                    attachment.setName("测试证件" + i);
                    attachmentList.add(attachment);
                    competency1.setAttachmentList(attachmentList);

                    personCertifyCompetencyList.add(competency1);
                }
            }
            duty.setPersonCertifyCompetencyList(personCertifyCompetencyList);
        });
        return list;
    }
}
