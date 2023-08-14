package com.hhwy.pm.qqch.module.service;

import com.hhwy.pm.qqch.module.domain.QqchModuleConfirmCase;

import java.util.List;

/**
 * @author han
 * @date 2023-07-11 15:23:04
 * @remark
 */
public interface IQqchModuleConfirmCaseService {

    QqchModuleConfirmCase getQqchModuleConfirmCase(QqchModuleConfirmCase qqchModuleConfirmCase);

    List<QqchModuleConfirmCase> getQqchModuleConfirmCaseList(QqchModuleConfirmCase qqchModuleConfirmCase);

    List<QqchModuleConfirmCase>  getConfirmStatus(String menuId,String stage,String confirmUser);

    List<QqchModuleConfirmCase> getModuleConfirmInfo(QqchModuleConfirmCase qqchModuleConfirmCase);

    int insertQqchModuleConfirmCase(QqchModuleConfirmCase qqchModuleConfirmCase);

    int insertQqchModuleConfirmCaseList(List<QqchModuleConfirmCase> qqchModuleConfirmCaseList);

    int updateQqchModuleConfirmCase(QqchModuleConfirmCase qqchModuleConfirmCase);

    int updateQqchModuleConfirmCaseList(List<QqchModuleConfirmCase> qqchModuleConfirmCaseList);

    int deleteQqchModuleConfirmCase(QqchModuleConfirmCase qqchModuleConfirmCase);

    int deleteQqchModuleConfirmCaseByPks(List<Long> qqchModuleConfirmCasePkList);

    void addConfirmRecord(String menuId, String stageIdentity);
}
