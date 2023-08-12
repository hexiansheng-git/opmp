package com.hhwy.pm.qqch.preparation.safe.risk.mapper;

import java.util.List;

import com.hhwy.pm.qqch.preparation.safe.risk.domain.QqchSafeRiskListDetail;
import org.apache.ibatis.annotations.Param;

/**
 * @author zq
 * @date 2023-08-11 13:41:38
 * @remark
 */
public interface QqchSafeRiskListDetailMapper {

    QqchSafeRiskListDetail getQqchSafeRiskListDetail(QqchSafeRiskListDetail qqchSafeRiskListDetail);

    List<QqchSafeRiskListDetail> getQqchSafeRiskListDetailList(QqchSafeRiskListDetail qqchSafeRiskListDetail);

    int insertQqchSafeRiskListDetail(QqchSafeRiskListDetail qqchSafeRiskListDetail);

    int insertQqchSafeRiskListDetailList(@Param("qqchSafeRiskListDetailList") List<QqchSafeRiskListDetail> qqchSafeRiskListDetailList);

    int updateQqchSafeRiskListDetail(QqchSafeRiskListDetail qqchSafeRiskListDetail);

    int updateQqchSafeRiskListDetailList(@Param("qqchSafeRiskListDetailList") List<QqchSafeRiskListDetail> qqchSafeRiskListDetailList);

    int deleteQqchSafeRiskListDetail(QqchSafeRiskListDetail qqchSafeRiskListDetail);

    int deleteQqchSafeRiskListDetailByPks(@Param("qqchSafeRiskListDetailPkList") List<Long> qqchSafeRiskListDetailPkList);
}
