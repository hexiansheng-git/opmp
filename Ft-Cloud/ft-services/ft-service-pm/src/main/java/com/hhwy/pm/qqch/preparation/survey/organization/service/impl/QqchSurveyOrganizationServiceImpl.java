package com.hhwy.pm.qqch.preparation.survey.organization.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.preparation.survey.organization.domain.QqchSurveyOrganization;
import com.hhwy.pm.qqch.preparation.survey.organization.mapper.QqchSurveyOrganizationMapper;
import com.hhwy.pm.qqch.preparation.survey.organization.service.IQqchSurveyOrganizationService;
import com.hhwy.utils.EntityUtils;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.tree.TreeUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    /**
     *  批增
     *
     * @param qqchSurveyOrganizationList
     * @return
     */
    @Transactional
    public int insertQqchSurveyOrganizationList(List<QqchSurveyOrganization> qqchSurveyOrganizationList) {
        if(CollectionUtils.isEmpty(qqchSurveyOrganizationList)){
            return 0;
        }

        List<QqchSurveyOrganization> insertList = new ArrayList<>();
        List<QqchSurveyOrganization> updateList = new ArrayList<>();
        for (QqchSurveyOrganization qqchSurveyOrganization : qqchSurveyOrganizationList) {
            this.recursionSubset(qqchSurveyOrganization, insertList, updateList);
        }
        if (insertList.size() > 0) {
            insertList.forEach(q->{
                if (q.getPid() != null) {
                    q.setPid(q.getPid());
                } else {
                    q.setPid(0l);
                }
            });
            qqchSurveyOrganizationMapper.insertQqchSurveyOrganizationList(insertList);
        }
        if (updateList.size() > 0) {
            qqchSurveyOrganizationMapper.updateQqchSurveyOrganizationList(updateList);
        }
        return 1;
    }

    /**
     *  递归处理
     *
     * @param qqchSurveyOrganization
     * @param insertList
     * @param updateList
     */
    private void recursionSubset(QqchSurveyOrganization qqchSurveyOrganization, List<QqchSurveyOrganization> insertList, List<QqchSurveyOrganization> updateList) {
        Long id = qqchSurveyOrganization.getId();
        if (id == null) {
            id = IdWorker.createId();
            qqchSurveyOrganization.setId(id);
            EntityUtils.setCreateUpdateInfo(qqchSurveyOrganization);
            insertList.add(qqchSurveyOrganization);
        } else {
            EntityUtils.setUpdateInfo(qqchSurveyOrganization);
            updateList.add(qqchSurveyOrganization);
        }
        List<QqchSurveyOrganization> children = qqchSurveyOrganization.getChildren();
        if (!CollectionUtils.isEmpty(children)) {
            for (QqchSurveyOrganization child : children) {
                child.setPid(id);
                this.recursionSubset(child, insertList, updateList);
            }
        }
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
