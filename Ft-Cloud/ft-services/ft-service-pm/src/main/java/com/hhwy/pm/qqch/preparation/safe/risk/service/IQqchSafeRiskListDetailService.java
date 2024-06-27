package com.hhwy.pm.qqch.preparation.safe.risk.service;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qqch.preparation.safe.risk.domain.QqchSafeRiskListDetail;

import java.util.Date;
import java.util.List;

/**
 * @author zq
 * @date 2023-08-11 13:41:38
 * @remark
 */
public interface IQqchSafeRiskListDetailService {

    QqchSafeRiskListDetail getQqchSafeRiskListDetail(QqchSafeRiskListDetail qqchSafeRiskListDetail);

    List<QqchSafeRiskListDetail> getQqchSafeRiskListDetailList(QqchSafeRiskListDetail qqchSafeRiskListDetail);

    int insertQqchSafeRiskListDetail(QqchSafeRiskListDetail qqchSafeRiskListDetail);

    int insertQqchSafeRiskListDetailList(List<QqchSafeRiskListDetail> qqchSafeRiskListDetailList);

    int updateQqchSafeRiskListDetail(QqchSafeRiskListDetail qqchSafeRiskListDetail);

    int updateQqchSafeRiskListDetailList(List<QqchSafeRiskListDetail> qqchSafeRiskListDetailList);

    int deleteQqchSafeRiskListDetail(QqchSafeRiskListDetail qqchSafeRiskListDetail);

    int deleteQqchSafeRiskListDetailByPks(List<Long> qqchSafeRiskListDetailPkList);

    void deleteByInfoId(Long infoId, String valueOf, String userName, Date nowDate);

    AjaxResult syncData();

    void deleteByInfoIds(List<Long> infoIds);
}
