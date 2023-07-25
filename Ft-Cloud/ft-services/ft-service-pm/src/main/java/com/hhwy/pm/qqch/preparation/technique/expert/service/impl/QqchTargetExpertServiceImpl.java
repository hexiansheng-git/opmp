package com.hhwy.pm.qqch.preparation.technique.expert.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.preparation.technique.expert.domain.QqchTargetExpert;
import com.hhwy.pm.qqch.preparation.technique.expert.mapper.QqchTargetExpertMapper;
import com.hhwy.pm.qqch.preparation.technique.expert.service.IQqchTargetExpertService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author han
 * @date 2023-07-25 10:56:35
 * @remark
 */
@Service
public class QqchTargetExpertServiceImpl implements IQqchTargetExpertService {

    @Autowired
    private QqchTargetExpertMapper qqchTargetExpertMapper;


    public QqchTargetExpert getQqchTargetExpert(QqchTargetExpert qqchTargetExpert) {
        return qqchTargetExpertMapper.getQqchTargetExpert(qqchTargetExpert);
    }

    public List<QqchTargetExpert> getQqchTargetExpertList(QqchTargetExpert qqchTargetExpert) {
        return qqchTargetExpertMapper.getQqchTargetExpertList(qqchTargetExpert);
    }

    @Transactional
    public int insertQqchTargetExpert(QqchTargetExpert qqchTargetExpert) {
        qqchTargetExpert.setId(IdWorker.createId());
        qqchTargetExpert.setCreateUser(SecurityUtils.getUserName());
        qqchTargetExpert.setCreateTime(DateUtils.getNowDate());
        return qqchTargetExpertMapper.insertQqchTargetExpert(qqchTargetExpert);
    }

    @Transactional
    public int insertQqchTargetExpertList(List<QqchTargetExpert> qqchTargetExpertList) {
        for (QqchTargetExpert qqchTargetExpert : qqchTargetExpertList) {
            qqchTargetExpert.setId(IdWorker.createId());
            qqchTargetExpert.setCreateUser(SecurityUtils.getUserName());
            qqchTargetExpert.setCreateTime(DateUtils.getNowDate());
        }
        return qqchTargetExpertMapper.insertQqchTargetExpertList(qqchTargetExpertList);
    }

    @Transactional
    public int updateQqchTargetExpert(QqchTargetExpert qqchTargetExpert) {
        qqchTargetExpert.setUpdateUser(SecurityUtils.getUserName());
        qqchTargetExpert.setUpdateTime(DateUtils.getNowDate());
        return qqchTargetExpertMapper.updateQqchTargetExpert(qqchTargetExpert);
    }

    @Transactional
    public int updateQqchTargetExpertList(List<QqchTargetExpert> qqchTargetExpertList) {
        for (QqchTargetExpert qqchTargetExpert : qqchTargetExpertList) {
            qqchTargetExpert.setUpdateUser(SecurityUtils.getUserName());
            qqchTargetExpert.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchTargetExpertMapper.updateQqchTargetExpertList(qqchTargetExpertList);
    }

    @Transactional
    public int deleteQqchTargetExpert(QqchTargetExpert qqchTargetExpert) {
        qqchTargetExpert.setUpdateUser(SecurityUtils.getUserName());
        qqchTargetExpert.setUpdateTime(DateUtils.getNowDate());
        return qqchTargetExpertMapper.deleteQqchTargetExpert(qqchTargetExpert);
    }

    @Transactional
    public int deleteQqchTargetExpertByPks(List<Long> qqchTargetExpertPkList) {
        return qqchTargetExpertMapper.deleteQqchTargetExpertByPks(qqchTargetExpertPkList);
    }
}
