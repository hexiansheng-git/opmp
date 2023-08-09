package com.hhwy.pm.qqch.tax.qqchTaxGoal.service.impl;

import java.util.List;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.security.util.SecurityUtils;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.pm.qqch.tax.qqchTaxGoal.mapper.QqchTaxGoalMapper;
import com.hhwy.pm.qqch.tax.qqchTaxGoal.service.IQqchTaxGoalService;
import com.hhwy.pm.qqch.tax.qqchTaxGoal.domain.QqchTaxGoal;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author mls
 * @date 2023-08-09 18:17:29
 * @remark 
 */
@Service
public class QqchTaxGoalServiceImpl implements IQqchTaxGoalService{

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
    }
