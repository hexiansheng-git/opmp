package com.hhwy.pm.qqch.preparation.safe.risk.service;

import com.hhwy.pm.qqch.preparation.safe.risk.domain.QqchSafeRiskList;
import com.hhwy.pm.qqch.preparation.safe.risk.domain.QqchSafeRiskListDetail;
import com.hhwy.pm.qqch.preparation.safe.risk.domain.vo.QqchSafeRiskListVo;
import com.hhwy.pm.qqch.preparation.safe.risk.domain.vo.SafeRiskAssembleDataVo;
import com.hhwy.pm.qqch.preparation.safe.risk.domain.vo.SafeRiskListQueryVo;

import java.util.List;


/**
 * @author zq
 * @date 2023-08-11 13:41:25
 * @remark
 */
public interface IQqchSafeRiskListService {

    QqchSafeRiskList getQqchSafeRiskList(QqchSafeRiskList qqchSafeRiskList);

    List<QqchSafeRiskList> getQqchSafeRiskListList(QqchSafeRiskList qqchSafeRiskList);

    int insertQqchSafeRiskList(QqchSafeRiskList qqchSafeRiskList);

    int insertQqchSafeRiskListList(QqchSafeRiskListVo qqchSafeRiskListVo);

    int updateQqchSafeRiskList(QqchSafeRiskList qqchSafeRiskList);

    int updateQqchSafeRiskListList(List<QqchSafeRiskList> qqchSafeRiskListList);

    int deleteQqchSafeRiskList(QqchSafeRiskList qqchSafeRiskList);

    int deleteQqchSafeRiskListByPks(List<Long> qqchSafeRiskListPkList);

    QqchSafeRiskListVo getList(SafeRiskListQueryVo queryVo);

    List<QqchSafeRiskListDetail> assembleData(SafeRiskAssembleDataVo assembleDataVo);

    void syncData();

    void initData();
}
