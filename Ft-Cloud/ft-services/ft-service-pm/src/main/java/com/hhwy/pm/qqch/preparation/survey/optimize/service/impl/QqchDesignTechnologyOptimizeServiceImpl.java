package com.hhwy.pm.qqch.preparation.survey.optimize.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.module.domain.QqchModuleConfirmCase;
import com.hhwy.pm.qqch.preparation.survey.optimize.domain.QqchDesignTechnologyOptimize;
import com.hhwy.pm.qqch.preparation.survey.optimize.domain.vo.QqchDesignTechnologyOptimizeVo;
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


    /**
     * 获取设计技术优化要点集合
     * @return
     */
    @Override
    @Transactional
    public QqchDesignTechnologyOptimizeVo getQqchDesignTechnologyOptimizeVo() {
        QqchDesignTechnologyOptimizeVo qqchDesignTechnologyOptimizeVo = new QqchDesignTechnologyOptimizeVo();
        List<QqchDesignTechnologyOptimize> qqchDesignTechnologyOptimizeList = qqchDesignTechnologyOptimizeMapper.getQqchDesignTechnologyOptimizeList();
        qqchDesignTechnologyOptimizeVo.setQqchDesignTechnologyOptimizeList(qqchDesignTechnologyOptimizeList);

        //TODO 获取确认状态
        qqchDesignTechnologyOptimizeVo.setQqchModuleConfirmCase(new QqchModuleConfirmCase());

        return qqchDesignTechnologyOptimizeVo;
    }

    /**
     * 保存
     * @param qqchDesignTechnologyOptimizeVo
     */
    @Override
    public void save(QqchDesignTechnologyOptimizeVo qqchDesignTechnologyOptimizeVo){
        this.editQqchDesignTechnologyOptimizeList(qqchDesignTechnologyOptimizeVo.getQqchDesignTechnologyOptimizeList());
    }

    /**
     * 确认
     * @param qqchDesignTechnologyOptimizeVo
     */
    @Override
    public void confirm(QqchDesignTechnologyOptimizeVo qqchDesignTechnologyOptimizeVo){
        this.editQqchDesignTechnologyOptimizeList(qqchDesignTechnologyOptimizeVo.getQqchDesignTechnologyOptimizeList());

        //TODO 修改确认状态

    }

    /**
     * 批量编辑（新增和修改）
     * @param qqchDesignTechnologyOptimizeListParam
     * @return
     */
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

    /**
     * 批量删除
     * @param qqchDesignTechnologyOptimizePkList
     * @return
     */
    @Override
    @Transactional
    public int deleteQqchDesignTechnologyOptimizeByPks(List<Long> qqchDesignTechnologyOptimizePkList) {
        return qqchDesignTechnologyOptimizeMapper.deleteQqchDesignTechnologyOptimizeByPks(qqchDesignTechnologyOptimizePkList);
    }
}
