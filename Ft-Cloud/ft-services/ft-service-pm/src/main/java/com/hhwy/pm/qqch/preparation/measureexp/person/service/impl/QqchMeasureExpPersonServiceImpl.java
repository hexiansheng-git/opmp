package com.hhwy.pm.qqch.preparation.measureexp.person.service.impl;

import java.util.List;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.pm.qqch.preparation.measureexp.person.mapper.QqchMeasureExpPersonMapper;
import com.hhwy.pm.qqch.preparation.measureexp.person.service.IQqchMeasureExpPersonService;
import com.hhwy.pm.qqch.preparation.measureexp.person.domain.QqchMeasureExpPerson;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author mls
 * @date 2023-07-25 18:01:30
 * @remark 
 */
@Service
public class QqchMeasureExpPersonServiceImpl implements IQqchMeasureExpPersonService{

    @Autowired
    private QqchMeasureExpPersonMapper qqchMeasureExpPersonMapper;

                                                                                                                                                                                                                                                                                                                            
    public QqchMeasureExpPerson getQqchMeasureExpPerson(QqchMeasureExpPerson qqchMeasureExpPerson) {
        return qqchMeasureExpPersonMapper.getQqchMeasureExpPerson(qqchMeasureExpPerson);
    }

    public List<QqchMeasureExpPerson> getQqchMeasureExpPersonList(QqchMeasureExpPerson qqchMeasureExpPerson) {
        return qqchMeasureExpPersonMapper.getQqchMeasureExpPersonList(qqchMeasureExpPerson);
    }

    @Transactional
    public int insertQqchMeasureExpPerson(QqchMeasureExpPerson qqchMeasureExpPerson) {
        qqchMeasureExpPerson.setId(IdWorker.createId());
        qqchMeasureExpPerson.setCreateUser(SecurityUtils.getUserName());
        qqchMeasureExpPerson.setCreateTime(DateUtils.getNowDate());
        return qqchMeasureExpPersonMapper.insertQqchMeasureExpPerson(qqchMeasureExpPerson);
    }

    @Transactional
    public int insertQqchMeasureExpPersonList(List<QqchMeasureExpPerson> qqchMeasureExpPersonList) {
        for (QqchMeasureExpPerson qqchMeasureExpPerson : qqchMeasureExpPersonList) {
            qqchMeasureExpPerson.setId(IdWorker.createId());
            qqchMeasureExpPerson.setCreateUser(SecurityUtils.getUserName());
            qqchMeasureExpPerson.setCreateTime(DateUtils.getNowDate());
        }
        return qqchMeasureExpPersonMapper.insertQqchMeasureExpPersonList(qqchMeasureExpPersonList);
    }

    @Transactional
    public int updateQqchMeasureExpPerson(QqchMeasureExpPerson qqchMeasureExpPerson) {
        qqchMeasureExpPerson.setUpdateUser(SecurityUtils.getUserName());
        qqchMeasureExpPerson.setUpdateTime(DateUtils.getNowDate());
        return qqchMeasureExpPersonMapper.updateQqchMeasureExpPerson(qqchMeasureExpPerson);
    }

            @Transactional
        public int updateQqchMeasureExpPersonList(List<QqchMeasureExpPerson> qqchMeasureExpPersonList) {
            for (QqchMeasureExpPerson qqchMeasureExpPerson : qqchMeasureExpPersonList) {
                qqchMeasureExpPerson.setUpdateUser(SecurityUtils.getUserName());
                qqchMeasureExpPerson.setUpdateTime(DateUtils.getNowDate());
            }
            return qqchMeasureExpPersonMapper.updateQqchMeasureExpPersonList(qqchMeasureExpPersonList);
        }
    
    @Transactional
    public int deleteQqchMeasureExpPerson(QqchMeasureExpPerson qqchMeasureExpPerson) {
        qqchMeasureExpPerson.setUpdateUser(SecurityUtils.getUserName());
        qqchMeasureExpPerson.setUpdateTime(DateUtils.getNowDate());
        return qqchMeasureExpPersonMapper.deleteQqchMeasureExpPerson(qqchMeasureExpPerson);
    }

            @Transactional
        public int deleteQqchMeasureExpPersonByPks(List<Long> qqchMeasureExpPersonPkList) {
            return qqchMeasureExpPersonMapper.deleteQqchMeasureExpPersonByPks(qqchMeasureExpPersonPkList);
        }
    }
