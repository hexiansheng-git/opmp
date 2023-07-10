package com.hhwy.pm.qqch.preparation.survey.optimize.service.impl;

import java.util.List;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.preparation.survey.optimize.domain.QqchDesignTechnologyOptimize;
import com.hhwy.pm.qqch.preparation.survey.optimize.mapper.QqchDesignTechnologyOptimizeMapper;
import com.hhwy.pm.qqch.preparation.survey.optimize.service.IQqchDesignTechnologyOptimizeService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author han
 * @date 2023-07-07 18:35:48
 * @remark 设计技术优化要点
 */
@Service
public class QqchDesignTechnologyOptimizeServiceImpl implements IQqchDesignTechnologyOptimizeService {

    @Autowired
    private QqchDesignTechnologyOptimizeMapper qqchDesignTechnologyOptimizeMapper;


    public QqchDesignTechnologyOptimize getQqchDesignTechnologyOptimize(QqchDesignTechnologyOptimize qqchDesignTechnologyOptimize) {
        return qqchDesignTechnologyOptimizeMapper.getQqchDesignTechnologyOptimize(qqchDesignTechnologyOptimize);
    }

    public List<QqchDesignTechnologyOptimize> getQqchDesignTechnologyOptimizeList(QqchDesignTechnologyOptimize qqchDesignTechnologyOptimize) {
        return qqchDesignTechnologyOptimizeMapper.getQqchDesignTechnologyOptimizeList(qqchDesignTechnologyOptimize);
    }

    @Transactional
    public int insertQqchDesignTechnologyOptimize(QqchDesignTechnologyOptimize qqchDesignTechnologyOptimize) {
        qqchDesignTechnologyOptimize.setId(IdWorker.createId());
        qqchDesignTechnologyOptimize.setCreateUser(StringUtils.valueOf(SecurityUtils.getUserId()));
        qqchDesignTechnologyOptimize.setCreateUserName(SecurityUtils.getUserName());
        qqchDesignTechnologyOptimize.setCreateTime(DateUtils.getNowDate());
        return qqchDesignTechnologyOptimizeMapper.insertQqchDesignTechnologyOptimize(qqchDesignTechnologyOptimize);
    }

    @Transactional
    public int insertQqchDesignTechnologyOptimizeList(List<QqchDesignTechnologyOptimize> qqchDesignTechnologyOptimizeList) {
        for (QqchDesignTechnologyOptimize qqchDesignTechnologyOptimize : qqchDesignTechnologyOptimizeList) {
            qqchDesignTechnologyOptimize.setId(IdWorker.createId());
            qqchDesignTechnologyOptimize.setCreateUser(StringUtils.valueOf(SecurityUtils.getUserId()));
            qqchDesignTechnologyOptimize.setCreateUserName(SecurityUtils.getUserName());
            qqchDesignTechnologyOptimize.setCreateTime(DateUtils.getNowDate());
        }
        return qqchDesignTechnologyOptimizeMapper.insertQqchDesignTechnologyOptimizeList(qqchDesignTechnologyOptimizeList);
    }

    @Transactional
    public int updateQqchDesignTechnologyOptimize(QqchDesignTechnologyOptimize qqchDesignTechnologyOptimize) {
        qqchDesignTechnologyOptimize.setUpdateUser(SecurityUtils.getUserName());
        qqchDesignTechnologyOptimize.setUpdateTime(DateUtils.getNowDate());
        return qqchDesignTechnologyOptimizeMapper.updateQqchDesignTechnologyOptimize(qqchDesignTechnologyOptimize);
    }

    @Transactional
    public int updateQqchDesignTechnologyOptimizeList(List<QqchDesignTechnologyOptimize> qqchDesignTechnologyOptimizeList) {
        for (QqchDesignTechnologyOptimize qqchDesignTechnologyOptimize : qqchDesignTechnologyOptimizeList) {
            qqchDesignTechnologyOptimize.setUpdateUser(SecurityUtils.getUserName());
            qqchDesignTechnologyOptimize.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchDesignTechnologyOptimizeMapper.updateQqchDesignTechnologyOptimizeList(qqchDesignTechnologyOptimizeList);
    }

    @Transactional
    public int deleteQqchDesignTechnologyOptimize(QqchDesignTechnologyOptimize qqchDesignTechnologyOptimize) {
        qqchDesignTechnologyOptimize.setUpdateUser(SecurityUtils.getUserName());
        qqchDesignTechnologyOptimize.setUpdateTime(DateUtils.getNowDate());
        return qqchDesignTechnologyOptimizeMapper.deleteQqchDesignTechnologyOptimize(qqchDesignTechnologyOptimize);
    }

    @Transactional
    public int deleteQqchDesignTechnologyOptimizeByPks(List<Long> qqchDesignTechnologyOptimizePkList) {
        return qqchDesignTechnologyOptimizeMapper.deleteQqchDesignTechnologyOptimizeByPks(qqchDesignTechnologyOptimizePkList);
    }
}
