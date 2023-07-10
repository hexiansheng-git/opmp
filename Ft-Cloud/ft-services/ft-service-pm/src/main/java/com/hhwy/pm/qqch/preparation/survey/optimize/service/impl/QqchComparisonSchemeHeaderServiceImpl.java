package com.hhwy.pm.qqch.preparation.survey.optimize.service.impl;

import java.util.List;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.preparation.survey.optimize.domain.QqchComparisonSchemeHeader;
import com.hhwy.pm.qqch.preparation.survey.optimize.mapper.QqchComparisonSchemeHeaderMapper;
import com.hhwy.pm.qqch.preparation.survey.optimize.service.IQqchComparisonSchemeHeaderService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author han
 * @date 2023-07-07 18:35:43
 * @remark 比选方案表头
 */
@Service
public class QqchComparisonSchemeHeaderServiceImpl implements IQqchComparisonSchemeHeaderService {

    @Autowired
    private QqchComparisonSchemeHeaderMapper qqchComparisonSchemeHeaderMapper;


    public QqchComparisonSchemeHeader getQqchComparisonSchemeHeader(QqchComparisonSchemeHeader qqchComparisonSchemeHeader) {
        return qqchComparisonSchemeHeaderMapper.getQqchComparisonSchemeHeader(qqchComparisonSchemeHeader);
    }

    public List<QqchComparisonSchemeHeader> getQqchComparisonSchemeHeaderList(QqchComparisonSchemeHeader qqchComparisonSchemeHeader) {
        return qqchComparisonSchemeHeaderMapper.getQqchComparisonSchemeHeaderList(qqchComparisonSchemeHeader);
    }

    @Transactional
    public int insertQqchComparisonSchemeHeader(QqchComparisonSchemeHeader qqchComparisonSchemeHeader) {
        qqchComparisonSchemeHeader.setId(IdWorker.createId());
        qqchComparisonSchemeHeader.setCreateUser(StringUtils.valueOf(SecurityUtils.getUserId()));
        qqchComparisonSchemeHeader.setCreateUserName(SecurityUtils.getUserName());
        qqchComparisonSchemeHeader.setCreateTime(DateUtils.getNowDate());
        return qqchComparisonSchemeHeaderMapper.insertQqchComparisonSchemeHeader(qqchComparisonSchemeHeader);
    }

    @Transactional
    public int insertQqchComparisonSchemeHeaderList(List<QqchComparisonSchemeHeader> qqchComparisonSchemeHeaderList) {
        for (QqchComparisonSchemeHeader qqchComparisonSchemeHeader : qqchComparisonSchemeHeaderList) {
            qqchComparisonSchemeHeader.setId(IdWorker.createId());
            qqchComparisonSchemeHeader.setCreateUser(StringUtils.valueOf(SecurityUtils.getUserId()));
            qqchComparisonSchemeHeader.setCreateUserName(SecurityUtils.getUserName());
            qqchComparisonSchemeHeader.setCreateTime(DateUtils.getNowDate());
        }
        return qqchComparisonSchemeHeaderMapper.insertQqchComparisonSchemeHeaderList(qqchComparisonSchemeHeaderList);
    }

    @Transactional
    public int updateQqchComparisonSchemeHeader(QqchComparisonSchemeHeader qqchComparisonSchemeHeader) {
        qqchComparisonSchemeHeader.setUpdateUser(SecurityUtils.getUserName());
        qqchComparisonSchemeHeader.setUpdateTime(DateUtils.getNowDate());
        return qqchComparisonSchemeHeaderMapper.updateQqchComparisonSchemeHeader(qqchComparisonSchemeHeader);
    }

    @Transactional
    public int updateQqchComparisonSchemeHeaderList(List<QqchComparisonSchemeHeader> qqchComparisonSchemeHeaderList) {
        for (QqchComparisonSchemeHeader qqchComparisonSchemeHeader : qqchComparisonSchemeHeaderList) {
            qqchComparisonSchemeHeader.setUpdateUser(SecurityUtils.getUserName());
            qqchComparisonSchemeHeader.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchComparisonSchemeHeaderMapper.updateQqchComparisonSchemeHeaderList(qqchComparisonSchemeHeaderList);
    }

    @Transactional
    public int deleteQqchComparisonSchemeHeader(QqchComparisonSchemeHeader qqchComparisonSchemeHeader) {
        qqchComparisonSchemeHeader.setUpdateUser(SecurityUtils.getUserName());
        qqchComparisonSchemeHeader.setUpdateTime(DateUtils.getNowDate());
        return qqchComparisonSchemeHeaderMapper.deleteQqchComparisonSchemeHeader(qqchComparisonSchemeHeader);
    }

    @Transactional
    public int deleteQqchComparisonSchemeHeaderByPks(List<Long> qqchComparisonSchemeHeaderPkList) {
        return qqchComparisonSchemeHeaderMapper.deleteQqchComparisonSchemeHeaderByPks(qqchComparisonSchemeHeaderPkList);
    }
}
