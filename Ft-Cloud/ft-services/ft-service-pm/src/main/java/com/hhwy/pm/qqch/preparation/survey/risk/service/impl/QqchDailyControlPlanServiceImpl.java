package com.hhwy.pm.qqch.preparation.survey.risk.service.impl;

import java.math.BigDecimal;
import java.util.List;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.common.mapper.CommonMapper;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.preparation.survey.risk.domain.QqchDailyControlPlan;
import com.hhwy.pm.qqch.preparation.survey.risk.domain.vo.QqchDailyControlPlanVo;
import com.hhwy.pm.qqch.preparation.survey.risk.mapper.QqchDailyControlPlanMapper;
import com.hhwy.pm.qqch.preparation.survey.risk.service.IQqchDailyControlPlanService;
import org.springframework.stereotype.Service;;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author han
 * @date 2023-07-13 11:39:57
 * @remark 日常管控策划
 */
@Service
public class QqchDailyControlPlanServiceImpl implements IQqchDailyControlPlanService {

    @Autowired
    private QqchDailyControlPlanMapper qqchDailyControlPlanMapper;

    @Autowired
    private CommonMapper commonMapper;


    /**
     * 获取日常管控策划Vo
     * @return
     */
    public QqchDailyControlPlanVo getQqchDailyControlPlanVo() {
        QqchDailyControlPlanVo qqchDailyControlPlanVo = new QqchDailyControlPlanVo();

        BigDecimal version = commonMapper.selectMaxVersion("qqch_daily_control_plan");
        qqchDailyControlPlanVo.setVersion(version);

        QqchDailyControlPlan qqchDailyControlPlan = new QqchDailyControlPlan();
        qqchDailyControlPlan.setVersion(version);
        List<QqchDailyControlPlan> qqchDailyControlPlanList = qqchDailyControlPlanMapper.getQqchDailyControlPlanList(qqchDailyControlPlan);
        qqchDailyControlPlanVo.setQqchDailyControlPlanList(qqchDailyControlPlanList);

        return qqchDailyControlPlanVo;
    }

    /**
     * 保存
     * @param qqchDailyControlPlanVo
     * @return
     */
    @Override
    public void save(QqchDailyControlPlanVo qqchDailyControlPlanVo) {
        //删除旧数据
        QqchDailyControlPlan qqchDailyControlPlan = new QqchDailyControlPlan();
        qqchDailyControlPlan.setVersion(qqchDailyControlPlanVo.getVersion());
        qqchDailyControlPlanMapper.deleteQqchDailyControlPlan(qqchDailyControlPlan);

        //插入新数据
        this.insertQqchDailyControlPlanList(qqchDailyControlPlanVo.getQqchDailyControlPlanList(),qqchDailyControlPlanVo.getVersion());
    }

    /**
     * 确认
     * @param qqchDailyControlPlanVo
     * @return
     */
    @Override
    public void confirm(QqchDailyControlPlanVo qqchDailyControlPlanVo) {
        this.save(qqchDailyControlPlanVo);
        //TODO 修改确认状态
    }

    /**
     * 批量插入
     * @param qqchDailyControlPlanList
     * @param version
     */
    @Transactional
    public void insertQqchDailyControlPlanList(List<QqchDailyControlPlan> qqchDailyControlPlanList, BigDecimal version) {
        for (QqchDailyControlPlan qqchDailyControlPlan : qqchDailyControlPlanList) {
            qqchDailyControlPlan.setId(IdWorker.createId());
            qqchDailyControlPlan.setVersion(version);
            qqchDailyControlPlan.setValid(Valid.YES);
            qqchDailyControlPlan.setCreateUser(SecurityUtils.getUserName());
            qqchDailyControlPlan.setCreateTime(DateUtils.getNowDate());
        }
        qqchDailyControlPlanMapper.insertQqchDailyControlPlanList(qqchDailyControlPlanList);
    }
}
