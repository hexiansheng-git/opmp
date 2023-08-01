package com.hhwy.pm.qqch.preparation.measureexp.range.service.impl;

import java.util.List;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.common.aspect.CompileAspect;
import com.hhwy.pm.qqch.common.aspect.CompileOptEnum;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.pm.qqch.preparation.measureexp.range.mapper.QqchMeasureOrgMapper;
import com.hhwy.pm.qqch.preparation.measureexp.range.service.IQqchMeasureOrgService;
import com.hhwy.pm.qqch.preparation.measureexp.range.domain.QqchMeasureOrg;
import com.hhwy.utils.idworker.IdWorker;

import javax.annotation.Resource;

/**
 * @author mls
 * @date 2023-07-25 18:01:39
 * @remark
 */
@Service
public class QqchMeasureOrgServiceImpl implements IQqchMeasureOrgService {

    @Resource
    private QqchMeasureOrgMapper qqchMeasureOrgMapper;
    private final static String TN = "qqch_measure_org";

    public QqchMeasureOrg getQqchMeasureOrg(QqchMeasureOrg qqchMeasureOrg) {
        return qqchMeasureOrgMapper.getQqchMeasureOrg(qqchMeasureOrg);
    }

    public List<QqchMeasureOrg> getQqchMeasureOrgList(QqchMeasureOrg qqchMeasureOrg) {
        return qqchMeasureOrgMapper.getQqchMeasureOrgList(qqchMeasureOrg);
    }

    @Transactional(rollbackFor = Exception.class)
    public int insertQqchMeasureOrg(QqchMeasureOrg qqchMeasureOrg) {
        qqchMeasureOrg.setId(IdWorker.createId());
        qqchMeasureOrg.setCreateUser(SecurityUtils.getUserName());
        qqchMeasureOrg.setCreateTime(DateUtils.getNowDate());
        return qqchMeasureOrgMapper.insertQqchMeasureOrg(qqchMeasureOrg);
    }

    @Transactional(rollbackFor = Exception.class)
    public int insertQqchMeasureOrgList(List<QqchMeasureOrg> qqchMeasureOrgList) {
        for (QqchMeasureOrg qqchMeasureOrg : qqchMeasureOrgList) {
            qqchMeasureOrg.setId(IdWorker.createId());
            qqchMeasureOrg.setCreateUser(SecurityUtils.getUserName());
            qqchMeasureOrg.setCreateTime(DateUtils.getNowDate());
        }
        return qqchMeasureOrgMapper.insertQqchMeasureOrgList(qqchMeasureOrgList);
    }

    @Transactional(rollbackFor = Exception.class)
    public int updateQqchMeasureOrg(QqchMeasureOrg qqchMeasureOrg) {
        qqchMeasureOrg.setUpdateUser(SecurityUtils.getUserName());
        qqchMeasureOrg.setUpdateTime(DateUtils.getNowDate());
        return qqchMeasureOrgMapper.updateQqchMeasureOrg(qqchMeasureOrg);
    }

    @Transactional(rollbackFor = Exception.class)
    public int updateQqchMeasureOrgList(List<QqchMeasureOrg> qqchMeasureOrgList) {
        for (QqchMeasureOrg qqchMeasureOrg : qqchMeasureOrgList) {
            qqchMeasureOrg.setUpdateUser(SecurityUtils.getUserName());
            qqchMeasureOrg.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchMeasureOrgMapper.updateQqchMeasureOrgList(qqchMeasureOrgList);
    }

    @Transactional(rollbackFor = Exception.class)
    public int deleteQqchMeasureOrg(QqchMeasureOrg qqchMeasureOrg) {
        qqchMeasureOrg.setUpdateUser(SecurityUtils.getUserName());
        qqchMeasureOrg.setUpdateTime(DateUtils.getNowDate());
        return qqchMeasureOrgMapper.deleteQqchMeasureOrg(qqchMeasureOrg);
    }

    @Transactional(rollbackFor = Exception.class)
    public int deleteQqchMeasureOrgByPks(List<Long> qqchMeasureOrgPkList) {
        return qqchMeasureOrgMapper.deleteQqchMeasureOrgByPks(qqchMeasureOrgPkList);
    }

    @Override
    @CompileAspect(type = CompileOptEnum.LIST, tableName = TN)
    public List<QqchMeasureOrg> getQqchMeasureOrgListByVersion(QqchMeasureOrg dto) {
        return this.getQqchMeasureOrgList(dto);
    }

    @Override
    @CompileAspect(type = CompileOptEnum.SAVE, tableName = TN)
    public void save(QqchMeasureOrg org) {
        org.setId(IdWorker.createId());
        this.qqchMeasureOrgMapper.insertQqchMeasureOrg(org);
    }
}
