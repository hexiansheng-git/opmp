package com.hhwy.pm.qqch.preparation.survey.optimize.service.impl;

import java.math.BigDecimal;
import java.util.List;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.common.mapper.CommonMapper;
import com.hhwy.pm.qqch.module.contant.Valid;
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

    @Autowired
    private CommonMapper commonMapper;


    /**
     * 获取优化程序策划集合
     * @return
     */
    @Override
    public QqchOptimizeProcedurePlanVo getQqchOptimizeProcedurePlanVo() {
        QqchOptimizeProcedurePlanVo qqchOptimizeProcedurePlanVo = new QqchOptimizeProcedurePlanVo();

        BigDecimal version = commonMapper.selectMaxVersion("qqch_optimize_procedure_plan");
        qqchOptimizeProcedurePlanVo.setVersion(version);

        List<QqchOptimizeProcedurePlan> qqchOptimizeProcedurePlanList = qqchOptimizeProcedurePlanMapper.getQqchOptimizeProcedurePlanList(version);
        qqchOptimizeProcedurePlanVo.setQqchOptimizeProcedurePlanList(qqchOptimizeProcedurePlanList);

        //TODO 获取确认状态

        return qqchOptimizeProcedurePlanVo;
    }

    /**
     * 保存
     * @param qqchOptimizeProcedurePlanVo
     * @return
     */
    @Override
    @Transactional
    public void save(QqchOptimizeProcedurePlanVo qqchOptimizeProcedurePlanVo) {
        //删除旧数据
        QqchOptimizeProcedurePlan qqchOptimizeProcedurePlan = new QqchOptimizeProcedurePlan();
        qqchOptimizeProcedurePlan.setVersion(qqchOptimizeProcedurePlanVo.getVersion());
        qqchOptimizeProcedurePlanMapper.deleteQqchOptimizeProcedurePlan(qqchOptimizeProcedurePlan);

        //插入新数据
        this.insertQqchOptimizeProcedurePlanList(qqchOptimizeProcedurePlanVo.getQqchOptimizeProcedurePlanList(),qqchOptimizeProcedurePlanVo.getVersion());
    }

    /**
     * 确认
     * @param qqchOptimizeProcedurePlanVo
     * @return
     */
    @Override
    @Transactional
    public void confirm(QqchOptimizeProcedurePlanVo qqchOptimizeProcedurePlanVo) {
        this.save(qqchOptimizeProcedurePlanVo);

        //TODO 修改确认状态
    }

    /**
     * 批量编辑（新增和修改）
     * @param qqchOptimizeProcedurePlanList
     * @return
     */
    @Transactional
    public void insertQqchOptimizeProcedurePlanList(List<QqchOptimizeProcedurePlan> qqchOptimizeProcedurePlanList, BigDecimal version) {
        for (QqchOptimizeProcedurePlan qqchOptimizeProcedurePlan : qqchOptimizeProcedurePlanList) {
            qqchOptimizeProcedurePlan.setId(IdWorker.createId());
            qqchOptimizeProcedurePlan.setVersion(version);
            qqchOptimizeProcedurePlan.setValid(Valid.YES);
            qqchOptimizeProcedurePlan.setCreateUser(StringUtils.valueOf(SecurityUtils.getUserId()));
            qqchOptimizeProcedurePlan.setCreateUserName(SecurityUtils.getUserName());
            qqchOptimizeProcedurePlan.setCreateTime(DateUtils.getNowDate());
        }
        qqchOptimizeProcedurePlanMapper.insertQqchOptimizeProcedurePlanList(qqchOptimizeProcedurePlanList);
    }
}
