package com.hhwy.pm.qqch.preparation.safe.qqchSafeMostEnvirRiskList.service;

import com.hhwy.pm.qqch.preparation.safe.qqchSafeMostEnvirRiskList.domain.QqchSafeMostEnvirRiskListDetail;

import java.util.Date;
import java.util.List;


/**
 * @author zq
 * @date 2023-08-14 14:04:04
 * @remark
 */
public interface IQqchSafeMostEnvirRiskListDetailService {

    QqchSafeMostEnvirRiskListDetail getQqchSafeMostEnvirRiskListDetail(QqchSafeMostEnvirRiskListDetail qqchSafeMostEnvirRiskListDetail);

    List<QqchSafeMostEnvirRiskListDetail> getQqchSafeMostEnvirRiskListDetailList(QqchSafeMostEnvirRiskListDetail qqchSafeMostEnvirRiskListDetail);

    int insertQqchSafeMostEnvirRiskListDetail(QqchSafeMostEnvirRiskListDetail qqchSafeMostEnvirRiskListDetail);

    int insertQqchSafeMostEnvirRiskListDetailList(List<QqchSafeMostEnvirRiskListDetail> qqchSafeMostEnvirRiskListDetailList);

    int updateQqchSafeMostEnvirRiskListDetail(QqchSafeMostEnvirRiskListDetail qqchSafeMostEnvirRiskListDetail);

    int updateQqchSafeMostEnvirRiskListDetailList(List<QqchSafeMostEnvirRiskListDetail> qqchSafeMostEnvirRiskListDetailList);

    int deleteQqchSafeMostEnvirRiskListDetail(QqchSafeMostEnvirRiskListDetail qqchSafeMostEnvirRiskListDetail);

    int deleteQqchSafeMostEnvirRiskListDetailByPks(List<Long> qqchSafeMostEnvirRiskListDetailPkList);

    List<QqchSafeMostEnvirRiskListDetail> getListByInfoIds(List<Long> infoIdList);

    void deleteByInfoIds(List<Long> infoIdList, String delUser, Date nowDate);
}
