package com.hhwy.pm.qqch.preparation.survey.optimize.service.impl;

import java.util.List;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.preparation.survey.optimize.domain.QqchComparisonSchemeContent;
import com.hhwy.pm.qqch.preparation.survey.optimize.mapper.QqchComparisonSchemeContentMapper;
import com.hhwy.pm.qqch.preparation.survey.optimize.service.IQqchComparisonSchemeContentService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author han
 * @date 2023-07-07 19:02:54
 * @remark 比选方案比选内容
 */
@Service
public class QqchComparisonSchemeContentServiceImpl implements IQqchComparisonSchemeContentService {

    @Autowired
    private QqchComparisonSchemeContentMapper qqchComparisonSchemeContentMapper;


    public QqchComparisonSchemeContent getQqchComparisonSchemeContent(QqchComparisonSchemeContent qqchComparisonSchemeContent) {
        return qqchComparisonSchemeContentMapper.getQqchComparisonSchemeContent(qqchComparisonSchemeContent);
    }

    public List<QqchComparisonSchemeContent> getQqchComparisonSchemeContentList(QqchComparisonSchemeContent qqchComparisonSchemeContent) {
        return qqchComparisonSchemeContentMapper.getQqchComparisonSchemeContentList(qqchComparisonSchemeContent);
    }

    @Transactional
    public int insertQqchComparisonSchemeContent(QqchComparisonSchemeContent qqchComparisonSchemeContent) {
        qqchComparisonSchemeContent.setId(IdWorker.createId());
        qqchComparisonSchemeContent.setCreateUser(StringUtils.valueOf(SecurityUtils.getUserId()));
        qqchComparisonSchemeContent.setCreateUserName(SecurityUtils.getUserName());
        qqchComparisonSchemeContent.setCreateTime(DateUtils.getNowDate());
        return qqchComparisonSchemeContentMapper.insertQqchComparisonSchemeContent(qqchComparisonSchemeContent);
    }

    @Transactional
    public int insertQqchComparisonSchemeContentList(List<QqchComparisonSchemeContent> qqchComparisonSchemeContentList) {
        for (QqchComparisonSchemeContent qqchComparisonSchemeContent : qqchComparisonSchemeContentList) {
            qqchComparisonSchemeContent.setId(IdWorker.createId());
            qqchComparisonSchemeContent.setCreateUser(SecurityUtils.getUserName());
            qqchComparisonSchemeContent.setCreateTime(DateUtils.getNowDate());
        }
        return qqchComparisonSchemeContentMapper.insertQqchComparisonSchemeContentList(qqchComparisonSchemeContentList);
    }

    @Transactional
    public int updateQqchComparisonSchemeContent(QqchComparisonSchemeContent qqchComparisonSchemeContent) {
        qqchComparisonSchemeContent.setUpdateUser(SecurityUtils.getUserName());
        qqchComparisonSchemeContent.setUpdateTime(DateUtils.getNowDate());
        return qqchComparisonSchemeContentMapper.updateQqchComparisonSchemeContent(qqchComparisonSchemeContent);
    }

    @Transactional
    public int updateQqchComparisonSchemeContentList(List<QqchComparisonSchemeContent> qqchComparisonSchemeContentList) {
        for (QqchComparisonSchemeContent qqchComparisonSchemeContent : qqchComparisonSchemeContentList) {
            qqchComparisonSchemeContent.setUpdateUser(SecurityUtils.getUserName());
            qqchComparisonSchemeContent.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchComparisonSchemeContentMapper.updateQqchComparisonSchemeContentList(qqchComparisonSchemeContentList);
    }

    @Transactional
    public int deleteQqchComparisonSchemeContent(QqchComparisonSchemeContent qqchComparisonSchemeContent) {
        qqchComparisonSchemeContent.setUpdateUser(SecurityUtils.getUserName());
        qqchComparisonSchemeContent.setUpdateTime(DateUtils.getNowDate());
        return qqchComparisonSchemeContentMapper.deleteQqchComparisonSchemeContent(qqchComparisonSchemeContent);
    }

    @Transactional
    public int deleteQqchComparisonSchemeContentByPks(List<Long> qqchComparisonSchemeContentPkList) {
        return qqchComparisonSchemeContentMapper.deleteQqchComparisonSchemeContentByPks(qqchComparisonSchemeContentPkList);
    }
}
