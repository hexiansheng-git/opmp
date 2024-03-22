package com.hhwy.sp.buildSchemeManage.review.mapper;

import com.hhwy.sp.buildSchemeManage.review.domain.SgjsBuildSchemeStaffOpinionRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author han
 * @date 2024-03-20 09:40:06
 * @remark
 */
@Repository
public interface SgjsBuildSchemeStaffOpinionRecordMapper {

    SgjsBuildSchemeStaffOpinionRecord getSgjsBuildSchemeStaffOpinionRecord(SgjsBuildSchemeStaffOpinionRecord sgjsBuildSchemeStaffOpinionRecord);

    List<SgjsBuildSchemeStaffOpinionRecord> getSgjsBuildSchemeStaffOpinionRecordList(SgjsBuildSchemeStaffOpinionRecord sgjsBuildSchemeStaffOpinionRecord);

    int insertSgjsBuildSchemeStaffOpinionRecord(SgjsBuildSchemeStaffOpinionRecord sgjsBuildSchemeStaffOpinionRecord);

    int insertSgjsBuildSchemeStaffOpinionRecordList(@Param("sgjsBuildSchemeStaffOpinionRecordList") List<SgjsBuildSchemeStaffOpinionRecord> sgjsBuildSchemeStaffOpinionRecordList);

    int updateSgjsBuildSchemeStaffOpinionRecord(SgjsBuildSchemeStaffOpinionRecord sgjsBuildSchemeStaffOpinionRecord);

    int updateSgjsBuildSchemeStaffOpinionRecordList(@Param("list") List<SgjsBuildSchemeStaffOpinionRecord> sgjsBuildSchemeStaffOpinionRecordList);

    void updateUpdateResult(@Param("list") List<SgjsBuildSchemeStaffOpinionRecord> sgjsBuildSchemeStaffOpinionRecordList);

    int deleteSgjsBuildSchemeStaffOpinionRecord(SgjsBuildSchemeStaffOpinionRecord sgjsBuildSchemeStaffOpinionRecord);

    int deleteSgjsBuildSchemeStaffOpinionRecordByPks(@Param("sgjsBuildSchemeStaffOpinionRecordPkList") List<Long> sgjsBuildSchemeStaffOpinionRecordPkList);
}
