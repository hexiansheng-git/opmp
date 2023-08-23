package com.hhwy.pm.qqch.tax.qqchTaxGoal.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.common.aspect.CompileAspect;
import com.hhwy.pm.qqch.common.aspect.CompileOptEnum;
import com.hhwy.pm.qqch.common.domain.CompileEntity;
import com.hhwy.pm.qqch.tax.qqchTaxGoal.domain.QqchTaxGoal;
import com.hhwy.pm.qqch.tax.qqchTaxGoal.mapper.QqchTaxGoalMapper;
import com.hhwy.pm.qqch.tax.qqchTaxGoal.service.IQqchTaxGoalService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author mls
 * @date 2023-08-09 18:17:29
 * @remark
 */
@Service
public class QqchTaxGoalServiceImpl implements IQqchTaxGoalService {


    private static final String TN = "qqch_tax_goal";

    @Autowired
    private QqchTaxGoalMapper qqchTaxGoalMapper;


    public QqchTaxGoal getQqchTaxGoal(QqchTaxGoal qqchTaxGoal) {
        return qqchTaxGoalMapper.getQqchTaxGoal(qqchTaxGoal);
    }

    public List<QqchTaxGoal> getQqchTaxGoalList(QqchTaxGoal qqchTaxGoal) {
        return qqchTaxGoalMapper.getQqchTaxGoalList(qqchTaxGoal);
    }

    @Transactional
    public int insertQqchTaxGoal(QqchTaxGoal qqchTaxGoal) {
        qqchTaxGoal.setId(IdWorker.createId());
        qqchTaxGoal.setCreateUser(SecurityUtils.getUserName());
        qqchTaxGoal.setCreateTime(DateUtils.getNowDate());
        return qqchTaxGoalMapper.insertQqchTaxGoal(qqchTaxGoal);
    }

    @Transactional
    public int insertQqchTaxGoalList(List<QqchTaxGoal> qqchTaxGoalList) {
        for (QqchTaxGoal qqchTaxGoal : qqchTaxGoalList) {
            qqchTaxGoal.setId(IdWorker.createId());
            qqchTaxGoal.setCreateUser(SecurityUtils.getUserName());
            qqchTaxGoal.setCreateTime(DateUtils.getNowDate());
        }
        return qqchTaxGoalMapper.insertQqchTaxGoalList(qqchTaxGoalList);
    }

    @Transactional
    public int updateQqchTaxGoal(QqchTaxGoal qqchTaxGoal) {
        qqchTaxGoal.setUpdateUser(SecurityUtils.getUserName());
        qqchTaxGoal.setUpdateTime(DateUtils.getNowDate());
        return qqchTaxGoalMapper.updateQqchTaxGoal(qqchTaxGoal);
    }

    @Transactional
    public int updateQqchTaxGoalList(List<QqchTaxGoal> qqchTaxGoalList) {
        for (QqchTaxGoal qqchTaxGoal : qqchTaxGoalList) {
            qqchTaxGoal.setUpdateUser(SecurityUtils.getUserName());
            qqchTaxGoal.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchTaxGoalMapper.updateQqchTaxGoalList(qqchTaxGoalList);
    }

    @Transactional
    public int deleteQqchTaxGoal(QqchTaxGoal qqchTaxGoal) {
        qqchTaxGoal.setUpdateUser(SecurityUtils.getUserName());
        qqchTaxGoal.setUpdateTime(DateUtils.getNowDate());
        return qqchTaxGoalMapper.deleteQqchTaxGoal(qqchTaxGoal);
    }

    @Transactional
    public int deleteQqchTaxGoalByPks(List<Long> qqchTaxGoalPkList) {
        return qqchTaxGoalMapper.deleteQqchTaxGoalByPks(qqchTaxGoalPkList);
    }

    @Override
    @CompileAspect(type = CompileOptEnum.SAVE_LIST, tableName = TN)
    public void save(List<QqchTaxGoal> list) {
        this.qqchTaxGoalMapper.insertQqchTaxGoalList(list);
    }

    @Override
    @CompileAspect(type = CompileOptEnum.LIST,tableName = TN)
    public CompileEntity<List<QqchTaxGoal>> list(QqchTaxGoal qqchTaxGoalParam) {
        List<QqchTaxGoal> qqchTaxGoalList = this.qqchTaxGoalMapper.getQqchTaxGoalList(qqchTaxGoalParam);
        CompileEntity objectCompileEntity = new CompileEntity();
        objectCompileEntity.setDto(qqchTaxGoalList);
        objectCompileEntity.setVersion(qqchTaxGoalParam.getVersion());
        return objectCompileEntity;
    }
}
