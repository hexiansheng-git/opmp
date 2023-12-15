package com.hhwy.sp.experiment.sgjsExperimentRecordInfoDetail.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.hhwy.sp.experiment.sgjsExperimentRecordInfoDetail.domain.SgjsExperimentRecordInfoDetail;

/**
 * @author lcf--自检自校表记录
 * @date 2023-12-11 15:04:20
 * @remark
 */
public interface SgjsExperimentRecordInfoDetailMapper {

    SgjsExperimentRecordInfoDetail getSgjsExperimentRecordInfoDetail(SgjsExperimentRecordInfoDetail sgjsExperimentRecordInfoDetail);

    List<SgjsExperimentRecordInfoDetail> getSgjsExperimentRecordInfoDetailList(SgjsExperimentRecordInfoDetail sgjsExperimentRecordInfoDetail);

    int insertSgjsExperimentRecordInfoDetail(SgjsExperimentRecordInfoDetail sgjsExperimentRecordInfoDetail);

    int insertSgjsExperimentRecordInfoDetailList(@Param("sgjsExperimentRecordInfoDetailList") List<SgjsExperimentRecordInfoDetail> sgjsExperimentRecordInfoDetailList);

    int updateSgjsExperimentRecordInfoDetail(SgjsExperimentRecordInfoDetail sgjsExperimentRecordInfoDetail);

    int updateSgjsExperimentRecordInfoDetailList(@Param("sgjsExperimentRecordInfoDetailList") List<SgjsExperimentRecordInfoDetail> sgjsExperimentRecordInfoDetailList);

    int deleteSgjsExperimentRecordInfoDetail(SgjsExperimentRecordInfoDetail sgjsExperimentRecordInfoDetail);

    int deleteSgjsExperimentRecordInfoDetailByPks(@Param("sgjsExperimentRecordInfoDetailPkList") List<Long> sgjsExperimentRecordInfoDetailPkList);

    /**
     * 批量查询自检自校记录
     *
     * @param infoIdList
     * @return
     */
    List<SgjsExperimentRecordInfoDetail> selectByInfoIdList(@Param(value = "infoIdList") List<String> infoIdList);
}
