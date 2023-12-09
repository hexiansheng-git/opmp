package com.hhwy.sp.sgjsExperiment.sgjsExperProgressManage.service;

import java.util.List;
import com.hhwy.sp.sgjsExperiment.sgjsExperProgressManage.domain.SgjsExperProgressManage;

/**
 * @author wll
 * @date 2023-12-09 11:06:27
 * @remark 
 */
public interface ISgjsExperProgressManageService {
                                                                                                                                                                                                                                                                                                                                                                                        
    SgjsExperProgressManage getSgjsExperProgressManage(SgjsExperProgressManage sgjsExperProgressManage);

    List<SgjsExperProgressManage> getSgjsExperProgressManageList(SgjsExperProgressManage sgjsExperProgressManage);

    int insertSgjsExperProgressManage(SgjsExperProgressManage sgjsExperProgressManage);

    int insertSgjsExperProgressManageList(List<SgjsExperProgressManage> sgjsExperProgressManageList);

    int updateSgjsExperProgressManage(SgjsExperProgressManage sgjsExperProgressManage);

            int updateSgjsExperProgressManageList(List<SgjsExperProgressManage> sgjsExperProgressManageList);
    
    int deleteSgjsExperProgressManage(SgjsExperProgressManage sgjsExperProgressManage);

            int deleteSgjsExperProgressManageByPks(List<Long> sgjsExperProgressManagePkList);
    }
