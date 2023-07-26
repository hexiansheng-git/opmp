package com.hhwy.pm.qqch.preparation.technique.techArchivesManage.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.preparation.technique.techArchivesManage.domain.QqchPigeonholeDutyDivision;
import com.hhwy.pm.qqch.preparation.technique.techArchivesManage.mapper.QqchPigeonholeDutyDivisionMapper;
import com.hhwy.pm.qqch.preparation.technique.techArchivesManage.service.IQqchPigeonholeDutyDivisionService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author han
 * @date 2023-07-25 10:48:27
 * @remark 技术档案归档责任分工
 */
@Service
public class QqchPigeonholeDutyDivisionServiceImpl implements IQqchPigeonholeDutyDivisionService {

    @Autowired
    private QqchPigeonholeDutyDivisionMapper qqchPigeonholeDutyDivisionMapper;


    public QqchPigeonholeDutyDivision getQqchPigeonholeDutyDivision(QqchPigeonholeDutyDivision qqchPigeonholeDutyDivision) {
        return qqchPigeonholeDutyDivisionMapper.getQqchPigeonholeDutyDivision(qqchPigeonholeDutyDivision);
    }

    public List<QqchPigeonholeDutyDivision> getQqchPigeonholeDutyDivisionList(QqchPigeonholeDutyDivision qqchPigeonholeDutyDivision) {
        return qqchPigeonholeDutyDivisionMapper.getQqchPigeonholeDutyDivisionList(qqchPigeonholeDutyDivision);
    }

    @Transactional
    public int insertQqchPigeonholeDutyDivision(QqchPigeonholeDutyDivision qqchPigeonholeDutyDivision) {
        qqchPigeonholeDutyDivision.setId(IdWorker.createId());
        qqchPigeonholeDutyDivision.setCreateUser(SecurityUtils.getUserName());
        qqchPigeonholeDutyDivision.setCreateTime(DateUtils.getNowDate());
        return qqchPigeonholeDutyDivisionMapper.insertQqchPigeonholeDutyDivision(qqchPigeonholeDutyDivision);
    }

    @Transactional
    public int insertQqchPigeonholeDutyDivisionList(List<QqchPigeonholeDutyDivision> qqchPigeonholeDutyDivisionList) {
        for (QqchPigeonholeDutyDivision qqchPigeonholeDutyDivision : qqchPigeonholeDutyDivisionList) {
            qqchPigeonholeDutyDivision.setId(IdWorker.createId());
            qqchPigeonholeDutyDivision.setCreateUser(SecurityUtils.getUserName());
            qqchPigeonholeDutyDivision.setCreateTime(DateUtils.getNowDate());
        }
        return qqchPigeonholeDutyDivisionMapper.insertQqchPigeonholeDutyDivisionList(qqchPigeonholeDutyDivisionList);
    }

    @Transactional
    public int updateQqchPigeonholeDutyDivision(QqchPigeonholeDutyDivision qqchPigeonholeDutyDivision) {
        qqchPigeonholeDutyDivision.setUpdateUser(SecurityUtils.getUserName());
        qqchPigeonholeDutyDivision.setUpdateTime(DateUtils.getNowDate());
        return qqchPigeonholeDutyDivisionMapper.updateQqchPigeonholeDutyDivision(qqchPigeonholeDutyDivision);
    }

    @Transactional
    public int updateQqchPigeonholeDutyDivisionList(List<QqchPigeonholeDutyDivision> qqchPigeonholeDutyDivisionList) {
        for (QqchPigeonholeDutyDivision qqchPigeonholeDutyDivision : qqchPigeonholeDutyDivisionList) {
            qqchPigeonholeDutyDivision.setUpdateUser(SecurityUtils.getUserName());
            qqchPigeonholeDutyDivision.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchPigeonholeDutyDivisionMapper.updateQqchPigeonholeDutyDivisionList(qqchPigeonholeDutyDivisionList);
    }

    @Transactional
    public int deleteQqchPigeonholeDutyDivision(QqchPigeonholeDutyDivision qqchPigeonholeDutyDivision) {
        qqchPigeonholeDutyDivision.setUpdateUser(SecurityUtils.getUserName());
        qqchPigeonholeDutyDivision.setUpdateTime(DateUtils.getNowDate());
        return qqchPigeonholeDutyDivisionMapper.deleteQqchPigeonholeDutyDivision(qqchPigeonholeDutyDivision);
    }

    @Transactional
    public int deleteQqchPigeonholeDutyDivisionByPks(List<Long> qqchPigeonholeDutyDivisionPkList) {
        return qqchPigeonholeDutyDivisionMapper.deleteQqchPigeonholeDutyDivisionByPks(qqchPigeonholeDutyDivisionPkList);
    }
}
