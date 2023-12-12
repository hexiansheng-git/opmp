package com.hhwy.sp.experiment.sgjsExperimentRecordInfo.service;

import java.util.List;
import java.util.Map;

import com.hhwy.sp.experiment.sgjsExperimentRecordInfo.domain.SgjsExperimentRecordInfo;

/**
 * @author lcf--设备实际进场记录
 * @date 2023-12-11 15:03:58
 * @remark
 */
public interface ISgjsExperimentRecordInfoService {

    SgjsExperimentRecordInfo getSgjsExperimentRecordInfo(SgjsExperimentRecordInfo sgjsExperimentRecordInfo);

    List<SgjsExperimentRecordInfo> getSgjsExperimentRecordInfoList(SgjsExperimentRecordInfo sgjsExperimentRecordInfo);

    int insertSgjsExperimentRecordInfo(SgjsExperimentRecordInfo sgjsExperimentRecordInfo);

    int insertSgjsExperimentRecordInfoList(List<SgjsExperimentRecordInfo> sgjsExperimentRecordInfoList);

    int updateSgjsExperimentRecordInfo(SgjsExperimentRecordInfo sgjsExperimentRecordInfo);

    int updateSgjsExperimentRecordInfoList(List<SgjsExperimentRecordInfo> sgjsExperimentRecordInfoList);

    int deleteSgjsExperimentRecordInfo(SgjsExperimentRecordInfo sgjsExperimentRecordInfo);

    int deleteSgjsExperimentRecordInfoByPks(List<Long> sgjsExperimentRecordInfoPkList);

    /**
     * 数据新增
     *
     * @param map
     * @return
     */
    int batchAddMap(Map<String, Object> map);
}
