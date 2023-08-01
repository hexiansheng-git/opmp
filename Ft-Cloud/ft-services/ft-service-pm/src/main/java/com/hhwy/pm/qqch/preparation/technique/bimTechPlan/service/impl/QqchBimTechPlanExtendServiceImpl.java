package com.hhwy.pm.qqch.preparation.technique.bimTechPlan.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.preparation.technique.bimTechPlan.domain.QqchBimTechPlanExtend;
import com.hhwy.pm.qqch.preparation.technique.bimTechPlan.mapper.QqchBimTechPlanExtendMapper;
import com.hhwy.pm.qqch.preparation.technique.bimTechPlan.service.IQqchBimTechPlanExtendService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author han
 * @date 2023-07-27 15:04:01
 * @remark
 */
@Service
public class QqchBimTechPlanExtendServiceImpl implements IQqchBimTechPlanExtendService {

    @Autowired
    private QqchBimTechPlanExtendMapper qqchBimTechPlanExtendMapper;


    public QqchBimTechPlanExtend getQqchBimTechPlanExtend(QqchBimTechPlanExtend qqchBimTechPlanExtend) {
        return qqchBimTechPlanExtendMapper.getQqchBimTechPlanExtend(qqchBimTechPlanExtend);
    }

    public List<QqchBimTechPlanExtend> getQqchBimTechPlanExtendList(QqchBimTechPlanExtend qqchBimTechPlanExtend) {
        return qqchBimTechPlanExtendMapper.getQqchBimTechPlanExtendList(qqchBimTechPlanExtend);
    }

    /**
     * 处理bim标记
     * @param bimMark
     * @param version
     */
    @Transactional
    public void disposeBimMark(String bimMark, BigDecimal version){
        //根据版本删除旧标记
        QqchBimTechPlanExtend qqchBimTechPlanExtend = new QqchBimTechPlanExtend();
        qqchBimTechPlanExtend.setVersion(version);
        qqchBimTechPlanExtendMapper.deleteQqchBimTechPlanExtend(qqchBimTechPlanExtend);

        //插入新标记
        qqchBimTechPlanExtend.setBimMark(bimMark);
        this.insertQqchBimTechPlanExtend(qqchBimTechPlanExtend);
    }

    @Transactional
    public void insertQqchBimTechPlanExtend(QqchBimTechPlanExtend qqchBimTechPlanExtend) {
        qqchBimTechPlanExtend.setId(IdWorker.createId());
        qqchBimTechPlanExtend.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
        qqchBimTechPlanExtend.setCreateUserName(SecurityUtils.getUserName());
        qqchBimTechPlanExtend.setCreateTime(DateUtils.getNowDate());
        qqchBimTechPlanExtendMapper.insertQqchBimTechPlanExtend(qqchBimTechPlanExtend);
    }

    @Transactional
    public int insertQqchBimTechPlanExtendList(List<QqchBimTechPlanExtend> qqchBimTechPlanExtendList) {
        for (QqchBimTechPlanExtend qqchBimTechPlanExtend : qqchBimTechPlanExtendList) {
            qqchBimTechPlanExtend.setId(IdWorker.createId());
            qqchBimTechPlanExtend.setCreateUser(SecurityUtils.getUserName());
            qqchBimTechPlanExtend.setCreateTime(DateUtils.getNowDate());
        }
        return qqchBimTechPlanExtendMapper.insertQqchBimTechPlanExtendList(qqchBimTechPlanExtendList);
    }

    @Transactional
    public int updateQqchBimTechPlanExtend(QqchBimTechPlanExtend qqchBimTechPlanExtend) {
        qqchBimTechPlanExtend.setUpdateUser(SecurityUtils.getUserName());
        qqchBimTechPlanExtend.setUpdateTime(DateUtils.getNowDate());
        return qqchBimTechPlanExtendMapper.updateQqchBimTechPlanExtend(qqchBimTechPlanExtend);
    }

    @Transactional
    public int updateQqchBimTechPlanExtendList(List<QqchBimTechPlanExtend> qqchBimTechPlanExtendList) {
        for (QqchBimTechPlanExtend qqchBimTechPlanExtend : qqchBimTechPlanExtendList) {
            qqchBimTechPlanExtend.setUpdateUser(SecurityUtils.getUserName());
            qqchBimTechPlanExtend.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchBimTechPlanExtendMapper.updateQqchBimTechPlanExtendList(qqchBimTechPlanExtendList);
    }

    @Transactional
    public int deleteQqchBimTechPlanExtend(QqchBimTechPlanExtend qqchBimTechPlanExtend) {
        qqchBimTechPlanExtend.setUpdateUser(SecurityUtils.getUserName());
        qqchBimTechPlanExtend.setUpdateTime(DateUtils.getNowDate());
        return qqchBimTechPlanExtendMapper.deleteQqchBimTechPlanExtend(qqchBimTechPlanExtend);
    }

    @Transactional
    public int deleteQqchBimTechPlanExtendByPks(List<Long> qqchBimTechPlanExtendPkList) {
        return qqchBimTechPlanExtendMapper.deleteQqchBimTechPlanExtendByPks(qqchBimTechPlanExtendPkList);
    }
}
