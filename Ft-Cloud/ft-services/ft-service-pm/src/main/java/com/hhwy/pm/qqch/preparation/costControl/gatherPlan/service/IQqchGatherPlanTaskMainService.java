package com.hhwy.pm.qqch.preparation.costControl.gatherPlan.service;

import com.hhwy.pm.qqch.preparation.costControl.gatherPlan.domain.QqchGatherPlanTaskMain;
import com.hhwy.pm.qqch.preparation.costControl.gatherPlan.domain.vo.GatherPlanTaskQueryVo;
import com.hhwy.pm.qqch.preparation.costControl.gatherPlan.domain.vo.QqchGatherPlanTaskMainVo;

import java.util.List;

/**
 * @author han
 * @date 2023-08-23 11:10:38
 * @remark
 */
public interface IQqchGatherPlanTaskMainService {

    QqchGatherPlanTaskMain getQqchGatherPlanTaskMain(QqchGatherPlanTaskMain qqchGatherPlanTaskMain);

    List<QqchGatherPlanTaskMain> getQqchGatherPlanTaskMainList(QqchGatherPlanTaskMain qqchGatherPlanTaskMain);

    int insertQqchGatherPlanTaskMain(QqchGatherPlanTaskMain qqchGatherPlanTaskMain);

    int insertQqchGatherPlanTaskMainList(List<QqchGatherPlanTaskMain> qqchGatherPlanTaskMainList);

    int updateQqchGatherPlanTaskMain(QqchGatherPlanTaskMain qqchGatherPlanTaskMain);

    int updateQqchGatherPlanTaskMainList(List<QqchGatherPlanTaskMain> qqchGatherPlanTaskMainList);

    int deleteQqchGatherPlanTaskMain(QqchGatherPlanTaskMain qqchGatherPlanTaskMain);

    int deleteQqchGatherPlanTaskMainByPks(List<Long> qqchGatherPlanTaskMainPkList);

    /**
     * 获取成本数据采集计划任务表Vo
     *
     * @param queryVo @return
     */
    QqchGatherPlanTaskMainVo getQqchGatherPlanTaskMainVo(GatherPlanTaskQueryVo queryVo);

    /**
     * 保存/确认/提交
     * @param qqchGatherPlanTaskMainVo
     * @return
     */
    void save(QqchGatherPlanTaskMainVo qqchGatherPlanTaskMainVo);
}
