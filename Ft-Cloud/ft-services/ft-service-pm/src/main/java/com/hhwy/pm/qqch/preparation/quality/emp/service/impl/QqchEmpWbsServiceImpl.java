package com.hhwy.pm.qqch.preparation.quality.emp.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.preparation.quality.emp.domain.QqchEmpWbs;
import com.hhwy.pm.qqch.preparation.quality.emp.mapper.QqchEmpWbsMapper;
import com.hhwy.pm.qqch.preparation.quality.emp.service.IQqchEmpWbsService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author mls
 * @date 2023-08-15 09:36:48
 * @remark
 */
@Service
public class QqchEmpWbsServiceImpl implements IQqchEmpWbsService {

    @Autowired
    private QqchEmpWbsMapper qqchEmpWbsMapper;


    public QqchEmpWbs getQqchEmpWbs(QqchEmpWbs qqchEmpWbs) {
        return qqchEmpWbsMapper.getQqchEmpWbs(qqchEmpWbs);
    }

    public List<QqchEmpWbs> getQqchEmpWbsList(QqchEmpWbs qqchEmpWbs) {
        return qqchEmpWbsMapper.getQqchEmpWbsList(qqchEmpWbs);
    }

    @Transactional
    public int insertQqchEmpWbs(QqchEmpWbs qqchEmpWbs) {
        qqchEmpWbs.setId(IdWorker.createId());
        qqchEmpWbs.setCreateUser(SecurityUtils.getUserName());
        qqchEmpWbs.setCreateTime(DateUtils.getNowDate());
        return qqchEmpWbsMapper.insertQqchEmpWbs(qqchEmpWbs);
    }

    @Transactional
    public int insertQqchEmpWbsList(List<QqchEmpWbs> qqchEmpWbsList) {
        for (QqchEmpWbs qqchEmpWbs : qqchEmpWbsList) {
            qqchEmpWbs.setId(IdWorker.createId());
            qqchEmpWbs.setCreateUser(SecurityUtils.getUserName());
            qqchEmpWbs.setCreateTime(DateUtils.getNowDate());
        }
        return qqchEmpWbsMapper.insertQqchEmpWbsList(qqchEmpWbsList);
    }

    @Transactional
    public int updateQqchEmpWbs(QqchEmpWbs qqchEmpWbs) {
        qqchEmpWbs.setUpdateUser(SecurityUtils.getUserName());
        qqchEmpWbs.setUpdateTime(DateUtils.getNowDate());
        return qqchEmpWbsMapper.updateQqchEmpWbs(qqchEmpWbs);
    }

    @Transactional
    public int updateQqchEmpWbsList(List<QqchEmpWbs> qqchEmpWbsList) {
        for (QqchEmpWbs qqchEmpWbs : qqchEmpWbsList) {
            qqchEmpWbs.setUpdateUser(SecurityUtils.getUserName());
            qqchEmpWbs.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchEmpWbsMapper.updateQqchEmpWbsList(qqchEmpWbsList);
    }

    @Transactional
    public int deleteQqchEmpWbs(QqchEmpWbs qqchEmpWbs) {
        qqchEmpWbs.setUpdateUser(SecurityUtils.getUserName());
        qqchEmpWbs.setUpdateTime(DateUtils.getNowDate());
        return qqchEmpWbsMapper.deleteQqchEmpWbs(qqchEmpWbs);
    }

    @Transactional
    public int deleteQqchEmpWbsByPks(List<Long> qqchEmpWbsPkList) {
        return qqchEmpWbsMapper.deleteQqchEmpWbsByPks(qqchEmpWbsPkList);
    }
}
