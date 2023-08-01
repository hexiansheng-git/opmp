package com.hhwy.pm.qqch.sgch.sche.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.common.aspect.CompileAspect;
import com.hhwy.pm.qqch.common.aspect.CompileOptEnum;
import com.hhwy.pm.qqch.sgch.sche.domain.QqchScheFactors;
import com.hhwy.pm.qqch.sgch.sche.mapper.QqchScheFactorsMapper;
import com.hhwy.pm.qqch.sgch.sche.service.IQqchScheFactorsService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author mls
 * @date 2023-07-31 11:22:52
 * @remark
 */
@Service
public class QqchScheFactorsServiceImpl implements IQqchScheFactorsService {

    @Autowired
    private QqchScheFactorsMapper qqchScheFactorsMapper;

    private final static String TN = "qqch_sche_factors";


    public QqchScheFactors getQqchScheFactors(QqchScheFactors qqchScheFactors) {
        return qqchScheFactorsMapper.getQqchScheFactors(qqchScheFactors);
    }

    public List<QqchScheFactors> getQqchScheFactorsList(QqchScheFactors qqchScheFactors) {
        return qqchScheFactorsMapper.getQqchScheFactorsList(qqchScheFactors);
    }

    @Transactional(rollbackFor = Exception.class)
    public int insertQqchScheFactors(QqchScheFactors qqchScheFactors) {
        qqchScheFactors.setId(IdWorker.createId());
        qqchScheFactors.setCreateUser(SecurityUtils.getUserName());
        qqchScheFactors.setCreateTime(DateUtils.getNowDate());
        return qqchScheFactorsMapper.insertQqchScheFactors(qqchScheFactors);
    }

    @Transactional(rollbackFor = Exception.class)
    public int insertQqchScheFactorsList(List<QqchScheFactors> qqchScheFactorsList) {
        for (QqchScheFactors qqchScheFactors : qqchScheFactorsList) {
            qqchScheFactors.setId(IdWorker.createId());
            qqchScheFactors.setCreateUser(SecurityUtils.getUserName());
            qqchScheFactors.setCreateTime(DateUtils.getNowDate());
        }
        return qqchScheFactorsMapper.insertQqchScheFactorsList(qqchScheFactorsList);
    }

    @Transactional(rollbackFor = Exception.class)
    public int updateQqchScheFactors(QqchScheFactors qqchScheFactors) {
        qqchScheFactors.setUpdateUser(SecurityUtils.getUserName());
        qqchScheFactors.setUpdateTime(DateUtils.getNowDate());
        return qqchScheFactorsMapper.updateQqchScheFactors(qqchScheFactors);
    }

    @Transactional(rollbackFor = Exception.class)
    public int updateQqchScheFactorsList(List<QqchScheFactors> qqchScheFactorsList) {
        for (QqchScheFactors qqchScheFactors : qqchScheFactorsList) {
            qqchScheFactors.setUpdateUser(SecurityUtils.getUserName());
            qqchScheFactors.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchScheFactorsMapper.updateQqchScheFactorsList(qqchScheFactorsList);
    }

    @Transactional(rollbackFor = Exception.class)
    public int deleteQqchScheFactors(QqchScheFactors qqchScheFactors) {
        qqchScheFactors.setUpdateUser(SecurityUtils.getUserName());
        qqchScheFactors.setUpdateTime(DateUtils.getNowDate());
        return qqchScheFactorsMapper.deleteQqchScheFactors(qqchScheFactors);
    }

    @Transactional(rollbackFor = Exception.class)
    public int deleteQqchScheFactorsByPks(List<Long> qqchScheFactorsPkList) {
        return qqchScheFactorsMapper.deleteQqchScheFactorsByPks(qqchScheFactorsPkList);
    }

    @Override
    @CompileAspect(type = CompileOptEnum.SAVE_LIST, tableName = TN)
    public void saveList(List<QqchScheFactors> dealSaveDto) {
        this.checkData(dealSaveDto);
        for (QqchScheFactors qqchScheFactors : dealSaveDto) {
            qqchScheFactors.setId(IdWorker.createId());
        }
        this.qqchScheFactorsMapper.insertQqchScheFactorsList(dealSaveDto);
    }

    @Override
    @CompileAspect(type = CompileOptEnum.LIST, tableName = TN)
    public List<QqchScheFactors> getList(QqchScheFactors dealSaveDto) {
        List<QqchScheFactors> qqchScheFactorsList = this.getQqchScheFactorsList(dealSaveDto);
        return qqchScheFactorsList;
    }

    private void checkData(List<QqchScheFactors> dealSaveDto) {
        
    }
}
