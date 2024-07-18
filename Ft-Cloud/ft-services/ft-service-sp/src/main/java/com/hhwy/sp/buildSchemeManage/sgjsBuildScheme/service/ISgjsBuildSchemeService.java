package com.hhwy.sp.buildSchemeManage.sgjsBuildScheme.service;

import com.hhwy.sp.buildSchemeManage.sgjsBuildScheme.domain.SgjsBuildScheme;

import java.util.List;

/**
 * @author fushudong
 * @date 2024-03-19 15:57:26
 * @remark
 */
public interface ISgjsBuildSchemeService {

    SgjsBuildScheme getSgjsBuildScheme(SgjsBuildScheme sgjsBuildScheme);

    List<SgjsBuildScheme> getSgjsBuildSchemeList(SgjsBuildScheme sgjsBuildScheme);

    List<SgjsBuildScheme> getSgjsBuildSchemeList1(SgjsBuildScheme sgjsBuildScheme);

    Long insertSgjsBuildScheme(SgjsBuildScheme sgjsBuildScheme);

    int insertSgjsBuildSchemeList(List<SgjsBuildScheme> sgjsBuildSchemeList);

    int updateSgjsBuildScheme(SgjsBuildScheme sgjsBuildScheme);

    int updateSgjsBuildSchemeList(List<SgjsBuildScheme> sgjsBuildSchemeList);

    int deleteSgjsBuildScheme(SgjsBuildScheme sgjsBuildScheme);

    int deleteSgjsBuildSchemeByPks(List<Long> sgjsBuildSchemePkList);

    SgjsBuildScheme detail(SgjsBuildScheme sgjsBuildSchemeParam);

    SgjsBuildScheme adjust(SgjsBuildScheme sgjsBuildSchemeParam);

    void updateTaskStatus(Long id, String isPass);

    void warnMessage();

    void doSendGm(String tenantKey, String admin);

    void sendProcessCompleteNotice(Long id, String isPass);
}
