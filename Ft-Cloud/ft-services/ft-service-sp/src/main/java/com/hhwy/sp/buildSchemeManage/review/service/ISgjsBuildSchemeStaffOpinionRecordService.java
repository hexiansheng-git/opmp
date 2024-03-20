package com.hhwy.sp.buildSchemeManage.review.service;

import com.hhwy.sp.buildSchemeManage.review.domain.SgjsBuildSchemeStaffOpinionRecord;

import java.util.List;

/**
 * @author han
 * @date 2024-03-20 09:40:06
 * @remark
 */
public interface ISgjsBuildSchemeStaffOpinionRecordService {

    SgjsBuildSchemeStaffOpinionRecord getSgjsBuildSchemeStaffOpinionRecord(SgjsBuildSchemeStaffOpinionRecord sgjsBuildSchemeStaffOpinionRecord);

    List<SgjsBuildSchemeStaffOpinionRecord> getSgjsBuildSchemeStaffOpinionRecordList(SgjsBuildSchemeStaffOpinionRecord sgjsBuildSchemeStaffOpinionRecord);

    int insertSgjsBuildSchemeStaffOpinionRecord(SgjsBuildSchemeStaffOpinionRecord sgjsBuildSchemeStaffOpinionRecord);

    int insertSgjsBuildSchemeStaffOpinionRecordList(List<SgjsBuildSchemeStaffOpinionRecord> sgjsBuildSchemeStaffOpinionRecordList);

    int updateSgjsBuildSchemeStaffOpinionRecord(SgjsBuildSchemeStaffOpinionRecord sgjsBuildSchemeStaffOpinionRecord);

    int updateSgjsBuildSchemeStaffOpinionRecordList(List<SgjsBuildSchemeStaffOpinionRecord> sgjsBuildSchemeStaffOpinionRecordList);

    int deleteSgjsBuildSchemeStaffOpinionRecord(SgjsBuildSchemeStaffOpinionRecord sgjsBuildSchemeStaffOpinionRecord);

    int deleteSgjsBuildSchemeStaffOpinionRecordByPks(List<Long> sgjsBuildSchemeStaffOpinionRecordPkList);
}
