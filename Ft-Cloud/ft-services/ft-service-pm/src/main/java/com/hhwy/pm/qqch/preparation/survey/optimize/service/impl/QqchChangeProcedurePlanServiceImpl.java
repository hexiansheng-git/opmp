package com.hhwy.pm.qqch.preparation.survey.optimize.service.impl;

import java.math.BigDecimal;
import java.util.List;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.common.mapper.CommonMapper;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.preparation.survey.optimize.domain.QqchChangeProcedurePlan;
import com.hhwy.pm.qqch.preparation.survey.optimize.domain.vo.QqchChangeProcedurePlanVo;
import com.hhwy.pm.qqch.preparation.survey.optimize.mapper.QqchChangeProcedurePlanMapper;
import com.hhwy.pm.qqch.preparation.survey.optimize.service.IQqchChangeProcedurePlanService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author han
 * @date 2023-07-07 18:35:34
 * @remark 变更程序策划
 */
@Service
public class QqchChangeProcedurePlanServiceImpl implements IQqchChangeProcedurePlanService {

    @Autowired
    private QqchChangeProcedurePlanMapper qqchChangeProcedurePlanMapper;

    @Autowired
    private CommonMapper commonMapper;


    /**
     * 获取变更程序策划
     * @return
     */
    public QqchChangeProcedurePlanVo getQqchChangeProcedurePlanVo() {
        QqchChangeProcedurePlanVo qqchChangeProcedurePlanVo = new QqchChangeProcedurePlanVo();

        BigDecimal version = commonMapper.selectMaxVersion("qqch_change_procedure_plan");
        qqchChangeProcedurePlanVo.setVersion(version);

        List<QqchChangeProcedurePlan> qqchChangeProcedurePlanList = qqchChangeProcedurePlanMapper.getQqchChangeProcedurePlanList(version);
        qqchChangeProcedurePlanVo.setQqchChangeProcedurePlanList(qqchChangeProcedurePlanList);

        //TODO 获取确认状态

        return qqchChangeProcedurePlanVo;
    }

    /**
     * 保存
     * @param qqchChangeProcedurePlanVo
     */
    @Override
    @Transactional
    public void save(QqchChangeProcedurePlanVo qqchChangeProcedurePlanVo) {
        //删除旧数据
        QqchChangeProcedurePlan qqchChangeProcedurePlan = new QqchChangeProcedurePlan();
        qqchChangeProcedurePlan.setVersion(qqchChangeProcedurePlanVo.getVersion());
        qqchChangeProcedurePlanMapper.deleteQqchChangeProcedurePlan(qqchChangeProcedurePlan);

        //插入新数据
        List<QqchChangeProcedurePlan> qqchChangeProcedurePlanList = qqchChangeProcedurePlanVo.getQqchChangeProcedurePlanList();
        this.insertQqchChangeProcedurePlanList(qqchChangeProcedurePlanList, qqchChangeProcedurePlanVo.getVersion());
    }

    /**
     * 确认
     * @param qqchChangeProcedurePlanVo
     * @return
     */
    @Override
    @Transactional
    public void confirm(QqchChangeProcedurePlanVo qqchChangeProcedurePlanVo) {
        this.save(qqchChangeProcedurePlanVo);

        //TODO 修改确认状态
    }

    /**
     * 批量插入
     * @param qqchChangeProcedurePlanList
     * @param version
     */
    @Transactional
    public void insertQqchChangeProcedurePlanList(List<QqchChangeProcedurePlan> qqchChangeProcedurePlanList, BigDecimal version){
        for (QqchChangeProcedurePlan qqchChangeProcedurePlan : qqchChangeProcedurePlanList) {
            qqchChangeProcedurePlan.setId(IdWorker.createId());
            qqchChangeProcedurePlan.setVersion(version);
            qqchChangeProcedurePlan.setValid(Valid.YES);
            qqchChangeProcedurePlan.setCreateUser(StringUtils.valueOf(SecurityUtils.getUserId()));
            qqchChangeProcedurePlan.setCreateUserName(SecurityUtils.getUserName());
            qqchChangeProcedurePlan.setCreateTime(DateUtils.getNowDate());
        }
        qqchChangeProcedurePlanMapper.insertQqchChangeProcedurePlanList(qqchChangeProcedurePlanList);
    }
}
