package com.hhwy.pm.qqch.preparation.survey.document.service;

import com.hhwy.pm.qqch.preparation.survey.document.domain.QqchManageProcedure;

import java.util.List;

/**
 * @author han
 * @date 2023-07-13 11:40:23
 * @remark 管理程序
 */
public interface IQqchManageProcedureService {

    QqchManageProcedure getQqchManageProcedure(QqchManageProcedure qqchManageProcedure);

    List<QqchManageProcedure> getQqchManageProcedureList(QqchManageProcedure qqchManageProcedure);

    int insertQqchManageProcedure(QqchManageProcedure qqchManageProcedure);

    int insertQqchManageProcedureList(List<QqchManageProcedure> qqchManageProcedureList);

    int updateQqchManageProcedure(QqchManageProcedure qqchManageProcedure);

    int updateQqchManageProcedureList(List<QqchManageProcedure> qqchManageProcedureList);

    int deleteQqchManageProcedure(QqchManageProcedure qqchManageProcedure);

    int deleteQqchManageProcedureByPks(List<Long> qqchManageProcedurePkList);
}
