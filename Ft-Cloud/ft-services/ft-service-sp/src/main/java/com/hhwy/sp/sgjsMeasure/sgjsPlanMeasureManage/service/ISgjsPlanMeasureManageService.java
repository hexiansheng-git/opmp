package com.hhwy.sp.sgjsMeasure.sgjsPlanMeasureManage.service;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.sp.sgjsMeasure.sgjsPlanMeasureManage.domain.SgjsPlanMeasureManageVo;
import java.util.List;
import com.hhwy.sp.sgjsMeasure.sgjsPlanMeasureManage.domain.SgjsPlanMeasureManage;

/**
 * @author zmh
 * @date 2023-12-07 18:13:51
 * @remark 
 */
public interface ISgjsPlanMeasureManageService {
                                                                                                                                                                                                                                                                                                                
    SgjsPlanMeasureManage getSgjsPlanMeasureManage(SgjsPlanMeasureManage sgjsPlanMeasureManage);

    List<SgjsPlanMeasureManage> getSgjsPlanMeasureManageList(SgjsPlanMeasureManage sgjsPlanMeasureManage);

    int insertSgjsPlanMeasureManage(SgjsPlanMeasureManage sgjsPlanMeasureManage);

    AjaxResult batchAdd(SgjsPlanMeasureManageVo sgjsPlanMeasureManageVo);

    int updateSgjsPlanMeasureManage(SgjsPlanMeasureManage sgjsPlanMeasureManage);

            int updateSgjsPlanMeasureManageList(List<SgjsPlanMeasureManage> sgjsPlanMeasureManageList);
    
    int deleteSgjsPlanMeasureManage(SgjsPlanMeasureManage sgjsPlanMeasureManage);

            int deleteSgjsPlanMeasureManageByPks(List<Long> sgjsPlanMeasureManagePkList);

    SgjsPlanMeasureManageVo list(SgjsPlanMeasureManage sgjsPlanMeasureManageParam);

    SgjsPlanMeasureManageVo qqchMeasureExpPlanSelect();

    List<SgjsPlanMeasureManage> getIds(List<String> ids);
}
