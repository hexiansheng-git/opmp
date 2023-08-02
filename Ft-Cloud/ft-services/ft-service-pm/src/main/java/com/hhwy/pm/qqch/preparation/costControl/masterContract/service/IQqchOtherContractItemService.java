package com.hhwy.pm.qqch.preparation.costControl.masterContract.service;

import com.hhwy.pm.qqch.preparation.costControl.masterContract.domain.QqchOtherContractItem;

import java.util.List;

/**
 * @author han
 * @date 2023-08-02 11:39:36
 * @remark 其他合同事项分析
 */
public interface IQqchOtherContractItemService {

    QqchOtherContractItem getQqchOtherContractItem(QqchOtherContractItem qqchOtherContractItem);

    List<QqchOtherContractItem> getQqchOtherContractItemList(QqchOtherContractItem qqchOtherContractItem);

    int insertQqchOtherContractItem(QqchOtherContractItem qqchOtherContractItem);

    int insertQqchOtherContractItemList(List<QqchOtherContractItem> qqchOtherContractItemList);

    int updateQqchOtherContractItem(QqchOtherContractItem qqchOtherContractItem);

    int updateQqchOtherContractItemList(List<QqchOtherContractItem> qqchOtherContractItemList);

    int deleteQqchOtherContractItem(QqchOtherContractItem qqchOtherContractItem);

    int deleteQqchOtherContractItemByPks(List<Long> qqchOtherContractItemPkList);
}
