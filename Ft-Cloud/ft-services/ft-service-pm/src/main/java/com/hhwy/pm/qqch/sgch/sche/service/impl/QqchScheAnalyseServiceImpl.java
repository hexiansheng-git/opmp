package com.hhwy.pm.qqch.sgch.sche.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.sgch.sche.domain.QqchScheAnalyse;
import com.hhwy.pm.qqch.sgch.sche.mapper.QqchScheAnalyseMapper;
import com.hhwy.pm.qqch.sgch.sche.service.IQqchScheAnalyseService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author mls
 * @date 2023-07-31 11:22:46
 * @remark
 */
@Service
public class QqchScheAnalyseServiceImpl implements IQqchScheAnalyseService {

    @Autowired
    private QqchScheAnalyseMapper qqchScheAnalyseMapper;


    public QqchScheAnalyse getQqchScheAnalyse(QqchScheAnalyse qqchScheAnalyse) {
        return qqchScheAnalyseMapper.getQqchScheAnalyse(qqchScheAnalyse);
    }

    public List<QqchScheAnalyse> getQqchScheAnalyseList(QqchScheAnalyse qqchScheAnalyse) {
        return qqchScheAnalyseMapper.getQqchScheAnalyseList(qqchScheAnalyse);
    }

    @Transactional
    public int insertQqchScheAnalyse(QqchScheAnalyse qqchScheAnalyse) {
        qqchScheAnalyse.setId(IdWorker.createId());
        qqchScheAnalyse.setCreateUser(SecurityUtils.getUserName());
        qqchScheAnalyse.setCreateTime(DateUtils.getNowDate());
        return qqchScheAnalyseMapper.insertQqchScheAnalyse(qqchScheAnalyse);
    }

    @Transactional
    public int insertQqchScheAnalyseList(List<QqchScheAnalyse> qqchScheAnalyseList) {
        for (QqchScheAnalyse qqchScheAnalyse : qqchScheAnalyseList) {
            qqchScheAnalyse.setId(IdWorker.createId());
            qqchScheAnalyse.setCreateUser(SecurityUtils.getUserName());
            qqchScheAnalyse.setCreateTime(DateUtils.getNowDate());
        }
        return qqchScheAnalyseMapper.insertQqchScheAnalyseList(qqchScheAnalyseList);
    }

    @Transactional
    public int updateQqchScheAnalyse(QqchScheAnalyse qqchScheAnalyse) {
        qqchScheAnalyse.setUpdateUser(SecurityUtils.getUserName());
        qqchScheAnalyse.setUpdateTime(DateUtils.getNowDate());
        return qqchScheAnalyseMapper.updateQqchScheAnalyse(qqchScheAnalyse);
    }

    @Transactional
    public int updateQqchScheAnalyseList(List<QqchScheAnalyse> qqchScheAnalyseList) {
        for (QqchScheAnalyse qqchScheAnalyse : qqchScheAnalyseList) {
            qqchScheAnalyse.setUpdateUser(SecurityUtils.getUserName());
            qqchScheAnalyse.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchScheAnalyseMapper.updateQqchScheAnalyseList(qqchScheAnalyseList);
    }

    @Transactional
    public int deleteQqchScheAnalyse(QqchScheAnalyse qqchScheAnalyse) {
        qqchScheAnalyse.setUpdateUser(SecurityUtils.getUserName());
        qqchScheAnalyse.setUpdateTime(DateUtils.getNowDate());
        return qqchScheAnalyseMapper.deleteQqchScheAnalyse(qqchScheAnalyse);
    }

    @Transactional
    public int deleteQqchScheAnalyseByPks(List<Long> qqchScheAnalysePkList) {
        return qqchScheAnalyseMapper.deleteQqchScheAnalyseByPks(qqchScheAnalysePkList);
    }
}
