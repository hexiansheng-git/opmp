package com.hhwy.pm.qqch.preparation.survey.optimize.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.preparation.survey.optimize.domain.QqchChangeProcedurePlan;
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

    /**
     * 获取设计技术优化要点集合
     * @return
     */
    public List<QqchDesignTechnologyOptimize> getQqchDesignTechnologyOptimizeList() {
        return qqchDesignTechnologyOptimizeMapper.getQqchDesignTechnologyOptimizeList();
    }

    /**
     * 批量编辑（新增和修改）
     * @param qqchDesignTechnologyOptimizeListParam
     * @return
     */
    @Override
    @Transactional
    public int editQqchDesignTechnologyOptimizeList(List<QqchDesignTechnologyOptimize> qqchDesignTechnologyOptimizeListParam) {
        List<QqchDesignTechnologyOptimize> insertList = new ArrayList<>();
        List<QqchDesignTechnologyOptimize> updateList = new ArrayList<>();
        for (QqchDesignTechnologyOptimize qqchDesignTechnologyOptimize : qqchDesignTechnologyOptimizeListParam) {
            Long id = qqchDesignTechnologyOptimize.getId();
            if(id == null){
                qqchDesignTechnologyOptimize.setId(IdWorker.createId());
                qqchDesignTechnologyOptimize.setCreateUser(StringUtils.valueOf(SecurityUtils.getUserId()));
                qqchDesignTechnologyOptimize.setCreateUserName(SecurityUtils.getUserName());
                qqchDesignTechnologyOptimize.setCreateTime(DateUtils.getNowDate());
                insertList.add(qqchDesignTechnologyOptimize);
            }else {
                qqchDesignTechnologyOptimize.setUpdateUser(SecurityUtils.getUserName());
                qqchDesignTechnologyOptimize.setUpdateTime(DateUtils.getNowDate());
                updateList.add(qqchDesignTechnologyOptimize);
            }
        }
        if(insertList.size() > 0){
            qqchDesignTechnologyOptimizeMapper.insertQqchDesignTechnologyOptimizeList(insertList);
        }
        if(updateList.size() > 0){
            qqchDesignTechnologyOptimizeMapper.updateQqchDesignTechnologyOptimizeList(updateList);
        }
        return 1;
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
