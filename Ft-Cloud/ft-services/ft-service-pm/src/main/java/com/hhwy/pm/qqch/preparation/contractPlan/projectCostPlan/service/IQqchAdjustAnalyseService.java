package com.hhwy.pm.qqch.preparation.contractPlan.projectCostPlan.service;

import com.hhwy.pm.qqch.preparation.contractPlan.projectCostPlan.domain.QqchAdjustAnalyse;
import com.hhwy.pm.qqch.preparation.contractPlan.projectCostPlan.domain.vo.QqchAdjustAnalyseVo;

import java.util.List;

/**
 * @author han
 * @date 2023-08-04 10:45:37
 * @remark
 */
public interface IQqchAdjustAnalyseService {

    QqchAdjustAnalyse getQqchAdjustAnalyse(QqchAdjustAnalyse qqchAdjustAnalyse);

    List<QqchAdjustAnalyse> getQqchAdjustAnalyseList(QqchAdjustAnalyse qqchAdjustAnalyse);

    int insertQqchAdjustAnalyse(QqchAdjustAnalyse qqchAdjustAnalyse);

    int updateQqchAdjustAnalyse(QqchAdjustAnalyse qqchAdjustAnalyse);

    int updateQqchAdjustAnalyseList(List<QqchAdjustAnalyse> qqchAdjustAnalyseList);

    int deleteQqchAdjustAnalyse(QqchAdjustAnalyse qqchAdjustAnalyse);

    int deleteQqchAdjustAnalyseByPks(List<Long> qqchAdjustAnalysePkList);

    /**
     * 获取调差分析Vo
     * @param qqchAdjustAnalyse
     * @return
     */
    QqchAdjustAnalyseVo getQqchAdjustAnalyseVo(QqchAdjustAnalyse qqchAdjustAnalyse);

    /**
     * 保存/确认/提交
     * @param qqchAdjustAnalyseVo
     * @return
     */
    void save(QqchAdjustAnalyseVo qqchAdjustAnalyseVo);
}
