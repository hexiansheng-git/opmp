package com.hhwy.sp.sgjsExperiment.sgjsExperProgressManage.mapper;

import com.hhwy.sp.sgjsExperiment.sgjsExperProgressManage.domain.SgjsExperProgressManage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wll
 * @date 2023-12-09 11:06:27
 * @remark 
 */
public interface SgjsExperProgressManageMapper {
                                                                                                                                                                                                                                                                                                                                                                                        
    SgjsExperProgressManage getSgjsExperProgressManage(SgjsExperProgressManage sgjsExperProgressManage);

    List<SgjsExperProgressManage> getSgjsExperProgressManageList(SgjsExperProgressManage sgjsExperProgressManage);

    int insertSgjsExperProgressManage(SgjsExperProgressManage sgjsExperProgressManage);

    int insertSgjsExperProgressManageList(@Param("sgjsExperProgressManageList") List<SgjsExperProgressManage> sgjsExperProgressManageList);

    int updateSgjsExperProgressManage(SgjsExperProgressManage sgjsExperProgressManage);

            int updateSgjsExperProgressManageList(@Param("sgjsExperProgressManageList") List<SgjsExperProgressManage> sgjsExperProgressManageList);
    
    int deleteSgjsExperProgressManage(SgjsExperProgressManage sgjsExperProgressManage);

            int deleteSgjsExperProgressManageByPks(@Param("sgjsExperProgressManagePkList") List<Long> sgjsExperProgressManagePkList);
    }
