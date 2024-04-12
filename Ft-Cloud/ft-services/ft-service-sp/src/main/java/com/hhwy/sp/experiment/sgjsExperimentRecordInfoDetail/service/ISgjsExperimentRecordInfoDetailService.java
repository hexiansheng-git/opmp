package com.hhwy.sp.experiment.sgjsExperimentRecordInfoDetail.service;

import java.util.List;
import com.hhwy.sp.experiment.sgjsExperimentRecordInfoDetail.domain.SgjsExperimentRecordInfoDetail;

/**
 * @author lcf--自检自校表记录
 * @date 2023-12-11 15:04:20
 * @remark
 */
public interface ISgjsExperimentRecordInfoDetailService {

    SgjsExperimentRecordInfoDetail getSgjsExperimentRecordInfoDetail(SgjsExperimentRecordInfoDetail sgjsExperimentRecordInfoDetail);

    List<SgjsExperimentRecordInfoDetail> getSgjsExperimentRecordInfoDetailList(SgjsExperimentRecordInfoDetail sgjsExperimentRecordInfoDetail);

    int insertSgjsExperimentRecordInfoDetail(SgjsExperimentRecordInfoDetail sgjsExperimentRecordInfoDetail);

    int insertSgjsExperimentRecordInfoDetailList(List<SgjsExperimentRecordInfoDetail> sgjsExperimentRecordInfoDetailList);

    int handleExperimentRecordInfoDetailData(List<SgjsExperimentRecordInfoDetail> sgjsExperimentRecordInfoDetailList,List<Long> recordIdList);

    int updateSgjsExperimentRecordInfoDetail(SgjsExperimentRecordInfoDetail sgjsExperimentRecordInfoDetail);

    int updateSgjsExperimentRecordInfoDetailList(List<SgjsExperimentRecordInfoDetail> sgjsExperimentRecordInfoDetailList);

    int deleteSgjsExperimentRecordInfoDetail(SgjsExperimentRecordInfoDetail sgjsExperimentRecordInfoDetail);

    int deleteSgjsExperimentRecordInfoDetailByPks(List<Long> sgjsExperimentRecordInfoDetailPkList);

    int deleteByRecordIds(List<Long> delIdList);
}
