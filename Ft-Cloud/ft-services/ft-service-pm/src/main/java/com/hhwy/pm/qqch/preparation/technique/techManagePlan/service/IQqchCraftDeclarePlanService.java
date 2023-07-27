package com.hhwy.pm.qqch.preparation.technique.techManagePlan.service;

import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.QqchCraftDeclarePlan;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.vo.QqchCraftDeclarePlanExportVo;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.vo.QqchCraftDeclarePlanVo;

import java.util.List;

/**
 * @author han
 * @date 2023-07-25 10:40:02
 * @remark 工艺工法申报计划
 */
public interface IQqchCraftDeclarePlanService {

    QqchCraftDeclarePlan getQqchCraftDeclarePlan(QqchCraftDeclarePlan qqchCraftDeclarePlan);

    List<QqchCraftDeclarePlan> getQqchCraftDeclarePlanList(QqchCraftDeclarePlan qqchCraftDeclarePlan);

    int insertQqchCraftDeclarePlan(QqchCraftDeclarePlan qqchCraftDeclarePlan);

    int updateQqchCraftDeclarePlan(QqchCraftDeclarePlan qqchCraftDeclarePlan);

    int updateQqchCraftDeclarePlanList(List<QqchCraftDeclarePlan> qqchCraftDeclarePlanList);

    int deleteQqchCraftDeclarePlan(QqchCraftDeclarePlan qqchCraftDeclarePlan);

    int deleteQqchCraftDeclarePlanByPks(List<Long> qqchCraftDeclarePlanPkList);

    /**
     * 获取工艺工法申报计划Vo
     * @param qqchCraftDeclarePlan
     * @return
     */
    QqchCraftDeclarePlanVo getQqchCraftDeclarePlanVo(QqchCraftDeclarePlan qqchCraftDeclarePlan);

    /**
     * 保存/确认/提交
     * @param qqchCraftDeclarePlanVo
     * @return
     */
    void save(QqchCraftDeclarePlanVo qqchCraftDeclarePlanVo);

    /**
     * 获取导出数据
     * @param qqchCraftDeclarePlan
     * @return
     */
    List<QqchCraftDeclarePlanExportVo> getQqchCraftDeclarePlanExportVoList(QqchCraftDeclarePlan qqchCraftDeclarePlan);
}
