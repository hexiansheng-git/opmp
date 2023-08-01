package com.hhwy.pm.qqch.sgch.sche.service;

import com.hhwy.pm.qqch.sgch.sche.domain.QqchScheDiffDesc;

import java.util.List;

/**
 * @author mls
 * @date 2023-07-31 11:22:50
 * @remark
 */
public interface IQqchScheDiffDescService {

    QqchScheDiffDesc getQqchScheDiffDesc(QqchScheDiffDesc qqchScheDiffDesc);

    List<QqchScheDiffDesc> getQqchScheDiffDescList(QqchScheDiffDesc qqchScheDiffDesc);

    int insertQqchScheDiffDesc(QqchScheDiffDesc qqchScheDiffDesc);

    int insertQqchScheDiffDescList(List<QqchScheDiffDesc> qqchScheDiffDescList);

    int updateQqchScheDiffDesc(QqchScheDiffDesc qqchScheDiffDesc);

    int updateQqchScheDiffDescList(List<QqchScheDiffDesc> qqchScheDiffDescList);

    int deleteQqchScheDiffDesc(QqchScheDiffDesc qqchScheDiffDesc);

    int deleteQqchScheDiffDescByPks(List<Long> qqchScheDiffDescPkList);

    /**保存
     * 
     * @param qqchScheDiffDesc 
     */
    void save(QqchScheDiffDesc qqchScheDiffDesc);

    QqchScheDiffDesc getDesc(QqchScheDiffDesc dealSaveDto);
}
