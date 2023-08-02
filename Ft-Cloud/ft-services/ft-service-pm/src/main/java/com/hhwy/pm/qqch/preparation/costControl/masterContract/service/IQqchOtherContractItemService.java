package com.hhwy.pm.qqch.preparation.costControl.masterContract.service;

import com.hhwy.pm.qqch.preparation.costControl.masterContract.domain.QqchOtherContractItem;
import com.hhwy.pm.qqch.preparation.costControl.masterContract.domain.vo.QqchOtherContractItemVo;

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

    int updateQqchOtherContractItem(QqchOtherContractItem qqchOtherContractItem);

    int updateQqchOtherContractItemList(List<QqchOtherContractItem> qqchOtherContractItemList);

    int deleteQqchOtherContractItem(QqchOtherContractItem qqchOtherContractItem);

    int deleteQqchOtherContractItemByPks(List<Long> qqchOtherContractItemPkList);

    /**
     * 获取其他合同事项分析Vo
     * @param qqchOtherContractItem
     * @return
     */
    QqchOtherContractItemVo getQqchOtherContractItemVo(QqchOtherContractItem qqchOtherContractItem);

    /**
     * 保存/确认/提交
     * @param qqchOtherContractItemVo
     * @return
     */
    void save(QqchOtherContractItemVo qqchOtherContractItemVo);
}
