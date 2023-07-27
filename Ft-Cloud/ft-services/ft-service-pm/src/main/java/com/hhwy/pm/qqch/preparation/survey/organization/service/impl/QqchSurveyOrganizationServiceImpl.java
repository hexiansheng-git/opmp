package com.hhwy.pm.qqch.preparation.survey.organization.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.survey.organization.domain.QqchSurveyOrganization;
import com.hhwy.pm.qqch.preparation.survey.organization.domain.QqchSurveyOrganizationVo;
import com.hhwy.pm.qqch.preparation.survey.organization.mapper.QqchSurveyOrganizationMapper;
import com.hhwy.pm.qqch.preparation.survey.organization.service.IQqchSurveyOrganizationService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.tree.TreeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author ldd
 * @date 2023-07-19 15:37:23
 * @remark 2.1.2 项目部勘察设计组织机构
 */
@Service
public class QqchSurveyOrganizationServiceImpl implements IQqchSurveyOrganizationService {

    @Autowired
    private QqchSurveyOrganizationMapper qqchSurveyOrganizationMapper;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;
    @Autowired
    private IQqchReviewService qqchReviewService;




    /**
     *   列表查询
     *
     * @param qqchSurveyOrganization
     * @return
     */
    public QqchSurveyOrganizationVo getQqchSurveyOrganizationList(QqchSurveyOrganization qqchSurveyOrganization) {
        BigDecimal version = VersionUtil.getVersion("qqch_survey_organization",qqchSurveyOrganization.getVersion());
        qqchSurveyOrganization.setVersion(version);
        List<QqchSurveyOrganization> qqchSurveyOrganizationList = qqchSurveyOrganizationMapper.getQqchSurveyOrganizationList(qqchSurveyOrganization);
        List<QqchSurveyOrganization> build = TreeUtil.build(qqchSurveyOrganizationList, 0l);
        QqchSurveyOrganizationVo vo = new QqchSurveyOrganizationVo();
        vo.setVersion(version);
        vo.setStageIdentity(qqchReviewService.getStage());
        vo.setQqchSurveyOrganizationList(build);
        return vo;
    }


    /**
     *  批增
     *
     * @param qqchSurveyOrganizationVo
     * @return
     */
    @Transactional
    public int save(QqchSurveyOrganizationVo qqchSurveyOrganizationVo) {
        //删除旧数据
        QqchSurveyOrganization qqchSurveyManageModel = new QqchSurveyOrganization();
        qqchSurveyManageModel.setVersion(qqchSurveyOrganizationVo.getVersion());
        qqchSurveyOrganizationMapper.deleteQqchSurveyOrganization(qqchSurveyManageModel);
        //插入新数据
        return  this.insertQqchSurveyOrganizationList(qqchSurveyOrganizationVo.getQqchSurveyOrganizationList(), qqchSurveyOrganizationVo.getVersion());
    }

    /**
     *  确认
     * @param qqchSurveyOrganizationVo
     * @return
     */
    @Override
    public void confirm(QqchSurveyOrganizationVo qqchSurveyOrganizationVo) {
        this.save(qqchSurveyOrganizationVo);
        String buttonMark = qqchSurveyOrganizationVo.getButtonMark();
        if(ButtonMark.CONFIRM.equals(buttonMark)){
            //插入确认状态
            String menuId = qqchSurveyOrganizationVo.getMenuId();
            String stageIdentity = qqchSurveyOrganizationVo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
    }


    private int insertQqchSurveyOrganizationList(List<QqchSurveyOrganization> qqchSurveyOrganizationList, BigDecimal version) {
        List<QqchSurveyOrganization> insertList = TreeUtil.treeToList(qqchSurveyOrganizationList);
        for (QqchSurveyOrganization qqchSurveyOrganization : insertList) {
            qqchSurveyOrganization.setVersion(version);
            if(version.compareTo(BigDecimal.ONE) == 0){
                qqchSurveyOrganization.setValid(Valid.YES);
            }
            qqchSurveyOrganization.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            qqchSurveyOrganization.setCreateUserName(SecurityUtils.getUserName());
            qqchSurveyOrganization.setCreateTime(DateUtils.getNowDate());
            if(qqchSurveyOrganization.getPid()==null){
                qqchSurveyOrganization.setPid(0l);
            }
        }
        return qqchSurveyOrganizationMapper.insertQqchSurveyOrganizationList(insertList);
    }


}
