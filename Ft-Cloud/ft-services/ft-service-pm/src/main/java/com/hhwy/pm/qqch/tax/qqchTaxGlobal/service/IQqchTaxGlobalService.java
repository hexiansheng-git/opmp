package com.hhwy.pm.qqch.tax.qqchTaxGlobal.service;

import com.hhwy.pm.qqch.common.domain.CompileEntity;
import com.hhwy.pm.qqch.tax.qqchTaxGlobal.domain.QqchTaxGlobal;

import java.io.IOException;
import java.util.List;

/**
 * @author mls
 * @date 2023-08-17 16:19:06
 * @remark
 */
public interface IQqchTaxGlobalService {

    QqchTaxGlobal getQqchTaxGlobal(QqchTaxGlobal qqchTaxGlobal);

    List<QqchTaxGlobal> getQqchTaxGlobalList(QqchTaxGlobal qqchTaxGlobal);

    int insertQqchTaxGlobal(QqchTaxGlobal qqchTaxGlobal);

    int insertQqchTaxGlobalList(List<QqchTaxGlobal> qqchTaxGlobalList);

    int updateQqchTaxGlobal(QqchTaxGlobal qqchTaxGlobal);

    int updateQqchTaxGlobalList(List<QqchTaxGlobal> qqchTaxGlobalList);

    int deleteQqchTaxGlobal(QqchTaxGlobal qqchTaxGlobal);

    int deleteQqchTaxGlobalByPks(List<Long> qqchTaxGlobalPkList);

    List<QqchTaxGlobal> list(QqchTaxGlobal dto) throws IOException;
    
}
