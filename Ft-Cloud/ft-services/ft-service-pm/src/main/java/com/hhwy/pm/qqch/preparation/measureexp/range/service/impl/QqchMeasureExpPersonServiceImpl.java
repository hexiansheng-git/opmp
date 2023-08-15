package com.hhwy.pm.qqch.preparation.measureexp.range.service.impl;

import java.util.List;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.common.aspect.CompileAspect;
import com.hhwy.pm.qqch.common.aspect.CompileOptEnum;
import com.hhwy.utils.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.pm.qqch.preparation.measureexp.range.mapper.QqchMeasureExpPersonMapper;
import com.hhwy.pm.qqch.preparation.measureexp.range.service.IQqchMeasureExpPersonService;
import com.hhwy.pm.qqch.preparation.measureexp.range.domain.QqchMeasureExpPerson;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;

/**
 * @author mls
 * @date 2023-07-25 18:01:30
 * @remark
 */
@Service
public class QqchMeasureExpPersonServiceImpl implements IQqchMeasureExpPersonService {

    private static final String TN = "qqch_measure_exp_person";

    @Resource
    private QqchMeasureExpPersonMapper qqchMeasureExpPersonMapper;


    public QqchMeasureExpPerson getQqchMeasureExpPerson(QqchMeasureExpPerson qqchMeasureExpPerson) {
        return qqchMeasureExpPersonMapper.getQqchMeasureExpPerson(qqchMeasureExpPerson);
    }

    public List<QqchMeasureExpPerson> getQqchMeasureExpPersonList(QqchMeasureExpPerson qqchMeasureExpPerson) {
        return qqchMeasureExpPersonMapper.getQqchMeasureExpPersonList(qqchMeasureExpPerson);
    }

    @Transactional(rollbackFor = Exception.class)
    public int insertQqchMeasureExpPerson(QqchMeasureExpPerson qqchMeasureExpPerson) {
        qqchMeasureExpPerson.setId(IdWorker.createId());
        qqchMeasureExpPerson.setCreateUser(SecurityUtils.getUserName());
        qqchMeasureExpPerson.setCreateTime(DateUtils.getNowDate());
        return qqchMeasureExpPersonMapper.insertQqchMeasureExpPerson(qqchMeasureExpPerson);
    }

    @Transactional(rollbackFor = Exception.class)
    public int insertQqchMeasureExpPersonList(List<QqchMeasureExpPerson> qqchMeasureExpPersonList) {
        if (CollectionUtils.isEmpty(qqchMeasureExpPersonList)) return 0;
        for (QqchMeasureExpPerson qqchMeasureExpPerson : qqchMeasureExpPersonList) {
            qqchMeasureExpPerson.setId(IdWorker.createId());
            qqchMeasureExpPerson.setCreateUser(SecurityUtils.getUserName());
            qqchMeasureExpPerson.setCreateTime(DateUtils.getNowDate());
        }
        return qqchMeasureExpPersonMapper.insertQqchMeasureExpPersonList(qqchMeasureExpPersonList);
    }

    @Transactional(rollbackFor = Exception.class)
    public int updateQqchMeasureExpPerson(QqchMeasureExpPerson qqchMeasureExpPerson) {
        qqchMeasureExpPerson.setUpdateUser(SecurityUtils.getUserName());
        qqchMeasureExpPerson.setUpdateTime(DateUtils.getNowDate());
        return qqchMeasureExpPersonMapper.updateQqchMeasureExpPerson(qqchMeasureExpPerson);
    }

    @Transactional(rollbackFor = Exception.class)
    public int updateQqchMeasureExpPersonList(List<QqchMeasureExpPerson> qqchMeasureExpPersonList) {
        for (QqchMeasureExpPerson qqchMeasureExpPerson : qqchMeasureExpPersonList) {
            qqchMeasureExpPerson.setUpdateUser(SecurityUtils.getUserName());
            qqchMeasureExpPerson.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchMeasureExpPersonMapper.updateQqchMeasureExpPersonList(qqchMeasureExpPersonList);
    }

    @Transactional(rollbackFor = Exception.class)
    public int deleteQqchMeasureExpPerson(QqchMeasureExpPerson qqchMeasureExpPerson) {
        qqchMeasureExpPerson.setUpdateUser(SecurityUtils.getUserName());
        qqchMeasureExpPerson.setUpdateTime(DateUtils.getNowDate());
        return qqchMeasureExpPersonMapper.deleteQqchMeasureExpPerson(qqchMeasureExpPerson);
    }

    @Transactional(rollbackFor = Exception.class)
    public int deleteQqchMeasureExpPersonByPks(List<Long> qqchMeasureExpPersonPkList) {
        return qqchMeasureExpPersonMapper.deleteQqchMeasureExpPersonByPks(qqchMeasureExpPersonPkList);
    }

    @Override
    @CompileAspect(type = CompileOptEnum.LIST, tableName = TN)
    public List<QqchMeasureExpPerson> getQqchMeasureExpPersonListByVersionCode(QqchMeasureExpPerson qqchMeasureExpPerson) {
        return this.getQqchMeasureExpPersonList(qqchMeasureExpPerson);
    }

    @Override
    @CompileAspect(type = CompileOptEnum.SAVE_LIST, tableName = TN)
    public void saveList(List<QqchMeasureExpPerson> personList) {
        if (CollectionUtils.isEmpty(personList)) return;
        for (QqchMeasureExpPerson qqchMeasureExpPerson : personList) {
            qqchMeasureExpPerson.setId(IdWorker.createId());
        }
        EntityUtils.setCreateUpdateInfo(personList);
        this.qqchMeasureExpPersonMapper.insertQqchMeasureExpPersonList(personList);
    }
}
