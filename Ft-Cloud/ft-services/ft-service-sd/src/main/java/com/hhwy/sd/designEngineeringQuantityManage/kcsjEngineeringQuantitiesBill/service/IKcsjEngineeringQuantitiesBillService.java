package com.hhwy.sd.designEngineeringQuantityManage.kcsjEngineeringQuantitiesBill.service;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.sd.designEngineeringQuantityManage.kcsjEngineeringQuantitiesBill.domain.KcsjEngineeringQuantitiesBill;

import java.util.List;

/**
 * @author wll
 * @date 2024-02-04 14:04:56
 * @remark 勘察设计-设计工程量管理-工程量清单
 */
public interface IKcsjEngineeringQuantitiesBillService {
    //详情
    KcsjEngineeringQuantitiesBill getKcsjEngineeringQuantitiesBill(KcsjEngineeringQuantitiesBill kcsjEngineeringQuantitiesBill);

    //列表
    List<KcsjEngineeringQuantitiesBill> getKcsjEngineeringQuantitiesBillList(KcsjEngineeringQuantitiesBill kcsjEngineeringQuantitiesBill);

    //新增
    AjaxResult insertKcsjEngineeringQuantitiesBill(KcsjEngineeringQuantitiesBill kcsjEngineeringQuantitiesBill);

    int insertKcsjEngineeringQuantitiesBillList(List<KcsjEngineeringQuantitiesBill> kcsjEngineeringQuantitiesBillList);

    AjaxResult updateKcsjEngineeringQuantitiesBill(KcsjEngineeringQuantitiesBill kcsjEngineeringQuantitiesBill);

    int updateKcsjEngineeringQuantitiesBillList(List<KcsjEngineeringQuantitiesBill> kcsjEngineeringQuantitiesBillList);


    int deleteKcsjEngineeringQuantitiesBillByPks(List<Long> kcsjEngineeringQuantitiesBillPkList);
}
