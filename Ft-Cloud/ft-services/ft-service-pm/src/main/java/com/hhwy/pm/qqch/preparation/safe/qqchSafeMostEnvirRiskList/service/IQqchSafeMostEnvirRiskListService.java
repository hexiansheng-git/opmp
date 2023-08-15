package com.hhwy.pm.qqch.preparation.safe.qqchSafeMostEnvirRiskList.service;

import java.math.BigDecimal;
import java.util.List;

import com.hhwy.pm.qqch.preparation.safe.qqchSafeMostEnvirRiskList.domain.QqchSafeMostEnvirRiskList;
import com.hhwy.pm.qqch.preparation.safe.qqchSafeMostEnvirRiskList.vo.QqchSafeMostEnvirRiskListVo;

/**
 * @author zq
 * @date 2023-08-14 14:04:02
 * @remark
 */
public interface IQqchSafeMostEnvirRiskListService {

    QqchSafeMostEnvirRiskList getQqchSafeMostEnvirRiskList(QqchSafeMostEnvirRiskList qqchSafeMostEnvirRiskList);

    List<QqchSafeMostEnvirRiskList> getQqchSafeMostEnvirRiskListList(QqchSafeMostEnvirRiskList qqchSafeMostEnvirRiskList);

    int insertQqchSafeMostEnvirRiskList(QqchSafeMostEnvirRiskList qqchSafeMostEnvirRiskList);

    int insertQqchSafeMostEnvirRiskListList(QqchSafeMostEnvirRiskListVo qqchSafeMostEnvirRiskListVo);

    int updateQqchSafeMostEnvirRiskList(QqchSafeMostEnvirRiskList qqchSafeMostEnvirRiskList);

    int updateQqchSafeMostEnvirRiskListList(List<QqchSafeMostEnvirRiskList> qqchSafeMostEnvirRiskListList);

    int deleteQqchSafeMostEnvirRiskList(QqchSafeMostEnvirRiskList qqchSafeMostEnvirRiskList);

    int deleteQqchSafeMostEnvirRiskListByPks(List<Long> qqchSafeMostEnvirRiskListPkList);

    QqchSafeMostEnvirRiskListVo getList(BigDecimal version);
}
