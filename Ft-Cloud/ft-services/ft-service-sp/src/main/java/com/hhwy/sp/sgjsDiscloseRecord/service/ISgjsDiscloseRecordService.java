package com.hhwy.sp.sgjsDiscloseRecord.service;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.sp.sgjsDiscloseRecord.domain.SgjsDiscloseRecord;

import java.util.List;

/**
 * @author cjh
 * @date 2023-11-23 14:47:22
 * @remark
 */
public interface ISgjsDiscloseRecordService {

    SgjsDiscloseRecord getSgjsDiscloseRecord(SgjsDiscloseRecord sgjsDiscloseRecord);

    List<SgjsDiscloseRecord> getSgjsDiscloseRecordList(SgjsDiscloseRecord sgjsDiscloseRecord);

    int insertSgjsDiscloseRecord(SgjsDiscloseRecord sgjsDiscloseRecord);

    int insertSgjsDiscloseRecordList(List<SgjsDiscloseRecord> sgjsDiscloseRecordList);

    int updateSgjsDiscloseRecord(SgjsDiscloseRecord sgjsDiscloseRecord);

    int updateSgjsDiscloseRecordList(List<SgjsDiscloseRecord> sgjsDiscloseRecordList);

    int deleteSgjsDiscloseRecord(SgjsDiscloseRecord sgjsDiscloseRecord);

    int deleteSgjsDiscloseRecordByPks(List<Long> sgjsDiscloseRecordPkList);

    int importData(List<SgjsDiscloseRecord> sgjsDiscloseRecordList, String dataType);

    /**
     * 方案安全交底预警
     *
     * @data 2024-08-20
     * @auth lcf
     */
    void disCloseWarn();

    /**
     * 方案安全交底流程监听
     *
     * @param id
     * @return
     */
    AjaxResult disCloseRecordListener(Long id,String status);
}
