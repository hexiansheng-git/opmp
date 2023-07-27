package com.hhwy.pm.qqch.preparation.technique.techManagePlan.service;

import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.QqchAppInnovatePlan;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.vo.QqchAppInnovatePlanExportVo;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.vo.QqchAppInnovatePlanVo;

import java.util.List;

/**
 * @author han
 * @date 2023-07-25 10:39:47
 * @remark 四新应用及创新计划
 */
public interface IQqchAppInnovatePlanService {

    QqchAppInnovatePlan getQqchAppInnovatePlan(QqchAppInnovatePlan qqchAppInnovatePlan);

    List<QqchAppInnovatePlan> getQqchAppInnovatePlanList(QqchAppInnovatePlan qqchAppInnovatePlan);

    int insertQqchAppInnovatePlan(QqchAppInnovatePlan qqchAppInnovatePlan);

    int updateQqchAppInnovatePlan(QqchAppInnovatePlan qqchAppInnovatePlan);

    int updateQqchAppInnovatePlanList(List<QqchAppInnovatePlan> qqchAppInnovatePlanList);

    int deleteQqchAppInnovatePlan(QqchAppInnovatePlan qqchAppInnovatePlan);

    int deleteQqchAppInnovatePlanByPks(List<Long> qqchAppInnovatePlanPkList);

    /**
     * 获取四新应用及创新计划Vo
     * @param qqchAppInnovatePlan
     * @return
     */
    QqchAppInnovatePlanVo getQqchAppInnovatePlanVo(QqchAppInnovatePlan qqchAppInnovatePlan);

    /**
     * 保存/确认/提交
     * @param qqchAppInnovatePlanVo
     * @return
     */
    void save(QqchAppInnovatePlanVo qqchAppInnovatePlanVo);

    /**
     * 获取导出数据
     * @param qqchAppInnovatePlan
     */
    List<QqchAppInnovatePlanExportVo> getQqchAppInnovatePlanExportVoList(QqchAppInnovatePlan qqchAppInnovatePlan);
}
