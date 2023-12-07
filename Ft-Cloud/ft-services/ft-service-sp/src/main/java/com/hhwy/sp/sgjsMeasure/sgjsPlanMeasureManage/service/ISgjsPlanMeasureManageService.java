package com.hhwy.sp.sgjsPlanMeasure.sgjsPlanMeasureManage.service;

import java.util.List;
import com.hhwy.sp.sgjsPlanMeasure.sgjsPlanMeasureManage.domain.SgjsPlanMeasureManage;

/**
 * @author zmh
 * @date 2023-12-07 18:13:51
 * @remark 
 */
public interface ISgjsPlanMeasureManageService {
                                                                                                                                                                                                                                                                                                                
    SgjsPlanMeasureManage getSgjsPlanMeasureManage(SgjsPlanMeasureManage sgjsPlanMeasureManage);

    List<SgjsPlanMeasureManage> getSgjsPlanMeasureManageList(SgjsPlanMeasureManage sgjsPlanMeasureManage);

    int insertSgjsPlanMeasureManage(SgjsPlanMeasureManage sgjsPlanMeasureManage);

    int insertSgjsPlanMeasureManageList(List<SgjsPlanMeasureManage> sgjsPlanMeasureManageList);

    int updateSgjsPlanMeasureManage(SgjsPlanMeasureManage sgjsPlanMeasureManage);

            int updateSgjsPlanMeasureManageList(List<SgjsPlanMeasureManage> sgjsPlanMeasureManageList);
    
    int deleteSgjsPlanMeasureManage(SgjsPlanMeasureManage sgjsPlanMeasureManage);

            int deleteSgjsPlanMeasureManageByPks(List<Long> sgjsPlanMeasureManagePkList);
    }
