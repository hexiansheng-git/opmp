package com.hhwy.pm.qqch.preparation.safe.qqchSafeEnvirRiskList.service;

import com.hhwy.pm.qqch.preparation.safe.qqchSafeEnvirRiskList.domain.QqchSafeEnvirRiskList;
import com.hhwy.pm.qqch.preparation.safe.qqchSafeEnvirRiskList.vo.QqchSafeEnvirRiskListVo;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author zq
 * @date 2023-08-14 13:56:17
 * @remark
 */
public interface IQqchSafeEnvirRiskListService {

    QqchSafeEnvirRiskList getQqchSafeEnvirRiskList(QqchSafeEnvirRiskList qqchSafeEnvirRiskList);

    List<QqchSafeEnvirRiskList> getQqchSafeEnvirRiskListList(QqchSafeEnvirRiskList qqchSafeEnvirRiskList);

    int insertQqchSafeEnvirRiskList(QqchSafeEnvirRiskList qqchSafeEnvirRiskList);

    int insertQqchSafeEnvirRiskListList(QqchSafeEnvirRiskListVo qqchSafeEnvirRiskListVo);

    int updateQqchSafeEnvirRiskList(QqchSafeEnvirRiskList qqchSafeEnvirRiskList);

    int updateQqchSafeEnvirRiskListList(List<QqchSafeEnvirRiskList> qqchSafeEnvirRiskListList);

    int deleteQqchSafeEnvirRiskList(QqchSafeEnvirRiskList qqchSafeEnvirRiskList);

    int deleteQqchSafeEnvirRiskListByPks(List<Long> qqchSafeEnvirRiskListPkList);

    QqchSafeEnvirRiskListVo getList(BigDecimal version);
}
