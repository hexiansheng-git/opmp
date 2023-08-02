package com.hhwy.pm.qqch.preparation.costControl.masterContract.mapper;

import com.hhwy.pm.qqch.preparation.costControl.masterContract.domain.QqchOtherContractItem;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author han
 * @date 2023-08-02 11:39:36
 * @remark 其他合同事项分析
 */
@Repository
public interface QqchOtherContractItemMapper {

    QqchOtherContractItem getQqchOtherContractItem(QqchOtherContractItem qqchOtherContractItem);

    List<QqchOtherContractItem> getQqchOtherContractItemList(QqchOtherContractItem qqchOtherContractItem);

    int insertQqchOtherContractItem(QqchOtherContractItem qqchOtherContractItem);

    int insertQqchOtherContractItemList(@Param("qqchOtherContractItemList") List<QqchOtherContractItem> qqchOtherContractItemList);

    int updateQqchOtherContractItem(QqchOtherContractItem qqchOtherContractItem);

    int updateQqchOtherContractItemList(@Param("list") List<QqchOtherContractItem> qqchOtherContractItemList);

    int deleteQqchOtherContractItem(QqchOtherContractItem qqchOtherContractItem);

    int deleteQqchOtherContractItemByPks(@Param("qqchOtherContractItemPkList") List<Long> qqchOtherContractItemPkList);
}
