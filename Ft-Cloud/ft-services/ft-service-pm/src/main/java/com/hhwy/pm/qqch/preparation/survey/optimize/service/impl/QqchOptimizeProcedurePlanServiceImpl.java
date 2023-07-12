package com.hhwy.pm.qqch.preparation.survey.optimize.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.module.domain.QqchModuleConfirmCase;
import com.hhwy.pm.qqch.preparation.survey.optimize.domain.QqchOptimizeProcedurePlan;
import com.hhwy.pm.qqch.preparation.survey.optimize.domain.vo.QqchOptimizeProcedurePlanVo;
import com.hhwy.pm.qqch.preparation.survey.optimize.mapper.QqchOptimizeProcedurePlanMapper;
import com.hhwy.pm.qqch.preparation.survey.optimize.service.IQqchOptimizeProcedurePlanService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author han
 * @date 2023-07-07 18:35:53
 * @remark 优化程序策划
 */
@Service
public class QqchOptimizeProcedurePlanServiceImpl implements IQqchOptimizeProcedurePlanService {

    @Autowired
    private QqchOptimizeProcedurePlanMapper qqchOptimizeProcedurePlanMapper;


    /**
     * 获取优化程序策划集合
     * @return
     */
    public QqchOptimizeProcedurePlanVo getQqchOptimizeProcedurePlanVo() {
        QqchOptimizeProcedurePlanVo qqchOptimizeProcedurePlanVo = new QqchOptimizeProcedurePlanVo();
        List<QqchOptimizeProcedurePlan> qqchOptimizeProcedurePlanList = qqchOptimizeProcedurePlanMapper.getQqchOptimizeProcedurePlanList();
        qqchOptimizeProcedurePlanVo.setQqchOptimizeProcedurePlanList(qqchOptimizeProcedurePlanList);

        //TODO 获取确认状态
        qqchOptimizeProcedurePlanVo.setQqchModuleConfirmCase(new QqchModuleConfirmCase());

        return qqchOptimizeProcedurePlanVo;
    }

    /**
     * 保存
     * @param qqchOptimizeProcedurePlanVo
     * @return
     */
    @Override
    public void save(QqchOptimizeProcedurePlanVo qqchOptimizeProcedurePlanVo) {
        this.editQqchOptimizeProcedurePlanList(qqchOptimizeProcedurePlanVo.getQqchOptimizeProcedurePlanList());
    }

    /**
     * 确认
     * @param qqchOptimizeProcedurePlanVo
     * @return
     */
    @Override
    @Transactional
    public void confirm(QqchOptimizeProcedurePlanVo qqchOptimizeProcedurePlanVo) {
        this.editQqchOptimizeProcedurePlanList(qqchOptimizeProcedurePlanVo.getQqchOptimizeProcedurePlanList());

        //TODO 修改确认状态
    }

    /**
     * 批量编辑（新增和修改）
     * @param qqchOptimizeProcedurePlanListParam
     * @return
     */
    @Transactional
    public int editQqchOptimizeProcedurePlanList(List<QqchOptimizeProcedurePlan> qqchOptimizeProcedurePlanListParam) {
        List<QqchOptimizeProcedurePlan> insertList = new ArrayList<>();
        List<QqchOptimizeProcedurePlan> updateList = new ArrayList<>();
        for (QqchOptimizeProcedurePlan qqchOptimizeProcedurePlan : qqchOptimizeProcedurePlanListParam) {
            Long id = qqchOptimizeProcedurePlan.getId();
            if(id == null){
                qqchOptimizeProcedurePlan.setId(IdWorker.createId());
                qqchOptimizeProcedurePlan.setCreateUser(StringUtils.valueOf(SecurityUtils.getUserId()));
                qqchOptimizeProcedurePlan.setCreateUserName(SecurityUtils.getUserName());
                qqchOptimizeProcedurePlan.setCreateTime(DateUtils.getNowDate());
                insertList.add(qqchOptimizeProcedurePlan);
            }else {
                qqchOptimizeProcedurePlan.setUpdateUser(SecurityUtils.getUserName());
                qqchOptimizeProcedurePlan.setUpdateTime(DateUtils.getNowDate());
                updateList.add(qqchOptimizeProcedurePlan);
            }
        }
        if(insertList.size() > 0){
            qqchOptimizeProcedurePlanMapper.insertQqchOptimizeProcedurePlanList(insertList);
        }
        if(updateList.size() > 0){
            qqchOptimizeProcedurePlanMapper.updateQqchOptimizeProcedurePlanList(updateList);
        }
        return 1;
    }

    /**
     * 批量删除
     * @param qqchOptimizeProcedurePlanPkList
     * @return
     */
    @Override
    @Transactional
    public int deleteQqchOptimizeProcedurePlanByPks(List<Long> qqchOptimizeProcedurePlanPkList) {
        return qqchOptimizeProcedurePlanMapper.deleteQqchOptimizeProcedurePlanByPks(qqchOptimizeProcedurePlanPkList);
    }
}
