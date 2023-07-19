package com.hhwy.pm.qqch.preparation.survey.organization.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.preparation.survey.organization.domain.QqchSurveyOrganization;
import com.hhwy.pm.qqch.preparation.survey.organization.mapper.QqchSurveyOrganizationMapper;
import com.hhwy.pm.qqch.preparation.survey.organization.service.IQqchSurveyOrganizationService;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.tree.TreeUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ldd
 * @date 2023-07-19 15:37:23
 * @remark
 */
@Service
public class QqchSurveyOrganizationServiceImpl implements IQqchSurveyOrganizationService {

    @Autowired
    private QqchSurveyOrganizationMapper qqchSurveyOrganizationMapper;


    public QqchSurveyOrganization getQqchSurveyOrganization(QqchSurveyOrganization qqchSurveyOrganization) {
        return qqchSurveyOrganizationMapper.getQqchSurveyOrganization(qqchSurveyOrganization);
    }

    /**
     *   列表查询
     *
     * @param qqchSurveyOrganization
     * @return
     */
    public List<QqchSurveyOrganization> getQqchSurveyOrganizationList(QqchSurveyOrganization qqchSurveyOrganization) {
        List<QqchSurveyOrganization> qqchSurveyOrganizationList = qqchSurveyOrganizationMapper.getQqchSurveyOrganizationList(qqchSurveyOrganization);
        if(CollectionUtils.isNotEmpty(qqchSurveyOrganizationList)){
            List<QqchSurveyOrganization> build = TreeUtil.build(qqchSurveyOrganizationList, 0l);
            return build;
        }
        return qqchSurveyOrganizationList;
    }

    @Transactional
    public int insertQqchSurveyOrganization(QqchSurveyOrganization qqchSurveyOrganization) {
        qqchSurveyOrganization.setId(IdWorker.createId());
        qqchSurveyOrganization.setCreateUser(SecurityUtils.getUserName());
        qqchSurveyOrganization.setCreateTime(DateUtils.getNowDate());
        return qqchSurveyOrganizationMapper.insertQqchSurveyOrganization(qqchSurveyOrganization);
    }

    @Transactional
    public int insertQqchSurveyOrganizationList(List<QqchSurveyOrganization> qqchSurveyOrganizationList) {
        for (QqchSurveyOrganization qqchSurveyOrganization : qqchSurveyOrganizationList) {
            qqchSurveyOrganization.setId(IdWorker.createId());
            qqchSurveyOrganization.setCreateUser(SecurityUtils.getUserName());
            qqchSurveyOrganization.setCreateTime(DateUtils.getNowDate());
        }
        return qqchSurveyOrganizationMapper.insertQqchSurveyOrganizationList(qqchSurveyOrganizationList);
    }

    @Transactional
    public int updateQqchSurveyOrganization(QqchSurveyOrganization qqchSurveyOrganization) {
        qqchSurveyOrganization.setUpdateUser(SecurityUtils.getUserName());
        qqchSurveyOrganization.setUpdateTime(DateUtils.getNowDate());
        return qqchSurveyOrganizationMapper.updateQqchSurveyOrganization(qqchSurveyOrganization);
    }

    @Transactional
    public int updateQqchSurveyOrganizationList(List<QqchSurveyOrganization> qqchSurveyOrganizationList) {
        for (QqchSurveyOrganization qqchSurveyOrganization : qqchSurveyOrganizationList) {
            qqchSurveyOrganization.setUpdateUser(SecurityUtils.getUserName());
            qqchSurveyOrganization.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchSurveyOrganizationMapper.updateQqchSurveyOrganizationList(qqchSurveyOrganizationList);
    }

    @Transactional
    public int deleteQqchSurveyOrganization(QqchSurveyOrganization qqchSurveyOrganization) {
        qqchSurveyOrganization.setUpdateUser(SecurityUtils.getUserName());
        qqchSurveyOrganization.setUpdateTime(DateUtils.getNowDate());
        return qqchSurveyOrganizationMapper.deleteQqchSurveyOrganization(qqchSurveyOrganization);
    }

    @Transactional
    public int deleteQqchSurveyOrganizationByPks(List<Long> qqchSurveyOrganizationPkList) {
        return qqchSurveyOrganizationMapper.deleteQqchSurveyOrganizationByPks(qqchSurveyOrganizationPkList);
    }
}
