package com.hhwy.pm.qqch.preparation.technique.techManagePlan.service;

import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.QqchPatentDeclarePlan;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.vo.QqchPatentDeclarePlanExportVo;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.vo.QqchPatentDeclarePlanVo;

import java.util.List;

/**
 * @author han
 * @date 2023-07-25 10:40:39
 * @remark 专利申报计划
 */
public interface IQqchPatentDeclarePlanService {

    QqchPatentDeclarePlan getQqchPatentDeclarePlan(QqchPatentDeclarePlan qqchPatentDeclarePlan);

    List<QqchPatentDeclarePlan> getQqchPatentDeclarePlanList(QqchPatentDeclarePlan qqchPatentDeclarePlan);

    int insertQqchPatentDeclarePlan(QqchPatentDeclarePlan qqchPatentDeclarePlan);

    int updateQqchPatentDeclarePlan(QqchPatentDeclarePlan qqchPatentDeclarePlan);

    int updateQqchPatentDeclarePlanList(List<QqchPatentDeclarePlan> qqchPatentDeclarePlanList);

    int deleteQqchPatentDeclarePlan(QqchPatentDeclarePlan qqchPatentDeclarePlan);

    int deleteQqchPatentDeclarePlanByPks(List<Long> qqchPatentDeclarePlanPkList);

    /**
     * 获取专利申报计划Vo
     * @param qqchPatentDeclarePlan
     * @return
     */
    QqchPatentDeclarePlanVo getQqchPatentDeclarePlanVo(QqchPatentDeclarePlan qqchPatentDeclarePlan);

    /**
     * 保存/确认/提交
     * @param qqchPatentDeclarePlanVo
     * @return
     */
    void save(QqchPatentDeclarePlanVo qqchPatentDeclarePlanVo);

    /**
     * 获取导出数据
     * @param qqchPatentDeclarePlan
     * @return
     */
    List<QqchPatentDeclarePlanExportVo> getQqchPatentDeclarePlanExportVoList(QqchPatentDeclarePlan qqchPatentDeclarePlan);
}
