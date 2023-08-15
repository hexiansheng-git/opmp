package com.hhwy.pm.qqch.preparation.quality.emp.service;

import com.hhwy.pm.qqch.common.domain.CompileEntity;
import com.hhwy.pm.qqch.preparation.quality.emp.domain.QqchEmpItem;

import java.util.List;

/**
 * @author mls
 * @date 2023-08-15 09:36:29
 * @remark
 */
public interface IQqchEmpItemService {

    QqchEmpItem getQqchEmpItem(QqchEmpItem qqchEmpItem);

    List<QqchEmpItem> getQqchEmpItemList(QqchEmpItem qqchEmpItem);

    int insertQqchEmpItem(QqchEmpItem qqchEmpItem);

    int insertQqchEmpItemList(List<QqchEmpItem> qqchEmpItemList);

    int updateQqchEmpItem(QqchEmpItem qqchEmpItem);

    int updateQqchEmpItemList(List<QqchEmpItem> qqchEmpItemList);

    int deleteQqchEmpItem(QqchEmpItem qqchEmpItem);

    int deleteQqchEmpItemByPks(List<Long> qqchEmpItemPkList);

    void save(CompileEntity<List<List<QqchEmpItem>>> dto);
    
}
