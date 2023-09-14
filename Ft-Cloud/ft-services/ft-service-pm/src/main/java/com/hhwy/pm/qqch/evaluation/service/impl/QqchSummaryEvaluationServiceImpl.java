package com.hhwy.pm.qqch.evaluation.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.evaluation.domain.QqchSummaryEvaluation;
import com.hhwy.pm.qqch.evaluation.mapper.QqchSummaryEvaluationMapper;
import com.hhwy.pm.qqch.evaluation.service.IQqchSummaryEvaluationService;
import com.hhwy.utils.common.CommonAssert;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhenglili
 * @date 2023-07-24 16:48:10
 * @remark 前期策划总结评价
 */
@Service
public class QqchSummaryEvaluationServiceImpl implements IQqchSummaryEvaluationService {

    @Autowired
    private QqchSummaryEvaluationMapper qqchSummaryEvaluationMapper;

    public QqchSummaryEvaluation getQqchSummaryEvaluation(QqchSummaryEvaluation qqchSummaryEvaluation) {
        return qqchSummaryEvaluationMapper.getQqchSummaryEvaluation(qqchSummaryEvaluation);
    }

    @Transactional
    public void save(QqchSummaryEvaluation qqchSummaryEvaluation) {
        if (qqchSummaryEvaluation == null) {
            return;
        }
        if (qqchSummaryEvaluation.getId() == null) {
            qqchSummaryEvaluation.setId(IdWorker.createId());
            qqchSummaryEvaluation.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            qqchSummaryEvaluation.setCreateUserName(SecurityUtils.getUserName());
            qqchSummaryEvaluation.setCreateTime(DateUtils.getNowDate());
            qqchSummaryEvaluationMapper.insertQqchSummaryEvaluation(qqchSummaryEvaluation);
        } else {
            qqchSummaryEvaluation.setUpdateUser(String.valueOf(SecurityUtils.getUserId()));
            qqchSummaryEvaluation.setUpdateTime(DateUtils.getNowDate());
            qqchSummaryEvaluationMapper.updateQqchSummaryEvaluation(qqchSummaryEvaluation);
        }
    }

    @Transactional
    public void submit(QqchSummaryEvaluation qqchSummaryEvaluation) {
        CommonAssert.isEmpty(qqchSummaryEvaluation.getInitialUnitId(), "初评单位不能为空");
        CommonAssert.isEmpty(qqchSummaryEvaluation.getInitialPersonId(), "评价人不能为空");
        CommonAssert.isEmpty(qqchSummaryEvaluation.getInitialDate(), "评价时间不能为空");
        CommonAssert.isEmpty(qqchSummaryEvaluation.getInitialFileGroupId(), "策划评价报告不能为空");

        CommonAssert.isEmpty(qqchSummaryEvaluation.getFinalUnitId(), "初评单位不能为空");
        CommonAssert.isEmpty(qqchSummaryEvaluation.getFinalPersonId(), "评价人不能为空");
        CommonAssert.isEmpty(qqchSummaryEvaluation.getFinalDate(), "评价时间不能为空");
        CommonAssert.isEmpty(qqchSummaryEvaluation.getFinalFileGroupId(), "策划评价报告不能为空");

        qqchSummaryEvaluation.setTaskStatus("5");
        // 保存数据
        this.save(qqchSummaryEvaluation);
    }

    /**
     * 修改流程数据
     *
     * @param id
     */
    @Transactional
    public void updateQqchSummaryEvaluationProcess(Long id) {
        QqchSummaryEvaluation qqchSummaryEvaluation = new QqchSummaryEvaluation();
        qqchSummaryEvaluation.setId(id);
        qqchSummaryEvaluation.setTaskStatus("5");
        qqchSummaryEvaluationMapper.updateQqchSummaryEvaluation(qqchSummaryEvaluation);
    }
}
