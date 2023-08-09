package com.hhwy.pm.qqch.sgch.important.service;

import com.hhwy.pm.qqch.common.domain.CompileDTO;
import com.hhwy.pm.qqch.sgch.important.domain.QqchImportant;

import java.util.List;

/**
 * @author mls
 * @date 2023-08-03 11:17:04
 * @remark
 */
public interface IQqchImportantService {

    QqchImportant getQqchImportant(QqchImportant qqchImportant);

    List<QqchImportant> getQqchImportantList(QqchImportant qqchImportant);

    int insertQqchImportant(QqchImportant qqchImportant);

    int insertQqchImportantList(List<QqchImportant> qqchImportantList);

    int updateQqchImportant(QqchImportant qqchImportant);

    int updateQqchImportantList(List<QqchImportant> qqchImportantList);

    int deleteQqchImportant(QqchImportant qqchImportant);

    int deleteQqchImportantByPks(List<Long> qqchImportantPkList);

    /**
     * @param dtos
     */
    void save(List<QqchImportant> dtos);

    /**
     * @param qqchImportantParam
     * @return
     */
    CompileDTO list(QqchImportant qqchImportantParam);

}
