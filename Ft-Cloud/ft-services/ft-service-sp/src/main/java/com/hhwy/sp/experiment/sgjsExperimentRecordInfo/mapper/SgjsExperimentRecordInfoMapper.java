package com.hhwy.sp.experiment.sgjsExperimentRecordInfo.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.hhwy.sp.experiment.sgjsExperimentRecordInfo.domain.SgjsExperimentRecordInfo;

/**
 * @author lcf--设备实际进场记录
 * @date 2023-12-11 15:03:58
 * @remark 
 */
public interface SgjsExperimentRecordInfoMapper {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        
    SgjsExperimentRecordInfo getSgjsExperimentRecordInfo(SgjsExperimentRecordInfo sgjsExperimentRecordInfo);

    List<SgjsExperimentRecordInfo> getSgjsExperimentRecordInfoList(SgjsExperimentRecordInfo sgjsExperimentRecordInfo);

    int insertSgjsExperimentRecordInfo(SgjsExperimentRecordInfo sgjsExperimentRecordInfo);

    int insertSgjsExperimentRecordInfoList(@Param("sgjsExperimentRecordInfoList") List<SgjsExperimentRecordInfo> sgjsExperimentRecordInfoList);

    int updateSgjsExperimentRecordInfo(SgjsExperimentRecordInfo sgjsExperimentRecordInfo);

            int updateSgjsExperimentRecordInfoList(@Param("sgjsExperimentRecordInfoList") List<SgjsExperimentRecordInfo> sgjsExperimentRecordInfoList);
    
    int deleteSgjsExperimentRecordInfo(SgjsExperimentRecordInfo sgjsExperimentRecordInfo);

            int deleteSgjsExperimentRecordInfoByPks(@Param("sgjsExperimentRecordInfoPkList") List<Long> sgjsExperimentRecordInfoPkList);
    }
