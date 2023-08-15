package com.hhwy.pm.qqch.preparation.safe.qqchSafeEnvirRiskList.service;

import com.hhwy.pm.qqch.preparation.safe.qqchSafeEnvirRiskList.domain.QqchSafeEnvirRiskListDetail;

import java.util.Date;
import java.util.List;

/**
 * @author zq
 * @date 2023-08-14 14:00:39
 * @remark
 */
public interface IQqchSafeEnvirRiskListDetailService {

    QqchSafeEnvirRiskListDetail getQqchSafeEnvirRiskListDetail(QqchSafeEnvirRiskListDetail qqchSafeEnvirRiskListDetail);

    List<QqchSafeEnvirRiskListDetail> getQqchSafeEnvirRiskListDetailList(QqchSafeEnvirRiskListDetail qqchSafeEnvirRiskListDetail);

    int insertQqchSafeEnvirRiskListDetail(QqchSafeEnvirRiskListDetail qqchSafeEnvirRiskListDetail);

    int insertQqchSafeEnvirRiskListDetailList(List<QqchSafeEnvirRiskListDetail> qqchSafeEnvirRiskListDetailList);

    int updateQqchSafeEnvirRiskListDetail(QqchSafeEnvirRiskListDetail qqchSafeEnvirRiskListDetail);

    int updateQqchSafeEnvirRiskListDetailList(List<QqchSafeEnvirRiskListDetail> qqchSafeEnvirRiskListDetailList);

    int deleteQqchSafeEnvirRiskListDetail(QqchSafeEnvirRiskListDetail qqchSafeEnvirRiskListDetail);

    int deleteQqchSafeEnvirRiskListDetailByPks(List<Long> qqchSafeEnvirRiskListDetailPkList);

    List<QqchSafeEnvirRiskListDetail> getQqchSafeEnvirRiskListDetailListByInfoId(List<Long> infoIdList);

    void deleteByInfoIds(List<Long> infoIdList, String valueOf, String userName, Date nowDate);
}
