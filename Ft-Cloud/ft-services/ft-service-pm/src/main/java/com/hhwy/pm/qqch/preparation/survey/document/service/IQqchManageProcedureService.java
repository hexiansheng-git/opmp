package com.hhwy.pm.qqch.preparation.survey.document.service;

import com.hhwy.pm.qqch.preparation.survey.document.domain.vo.QqchManageProcedureVo;

/**
 * @author han
 * @date 2023-07-13 11:40:23
 * @remark 管理程序
 */
public interface IQqchManageProcedureService {

    /**
     * 获取管理程序Vo
     * @return
     */
    QqchManageProcedureVo getQqchManageProcedureVo();

    /**
     * 保存
     * @param qqchManageProcedureVo
     * @return
     */
    void save(QqchManageProcedureVo qqchManageProcedureVo);

    /**
     * 确认
     * @param qqchManageProcedureVo
     * @return
     */
    void confirm(QqchManageProcedureVo qqchManageProcedureVo);
}
