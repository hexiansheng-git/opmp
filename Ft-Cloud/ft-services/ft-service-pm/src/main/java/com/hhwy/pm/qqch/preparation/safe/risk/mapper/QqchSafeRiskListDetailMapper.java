package com.hhwy.pm.qqch.preparation.safe.risk.mapper;

import com.hhwy.pm.qqch.preparation.safe.risk.domain.QqchSafeRiskListDetail;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author zq
 * @date 2023-08-11 13:41:38
 * @remark
 */
public interface QqchSafeRiskListDetailMapper {

    QqchSafeRiskListDetail getQqchSafeRiskListDetail(QqchSafeRiskListDetail qqchSafeRiskListDetail);

    List<QqchSafeRiskListDetail> getQqchSafeRiskListDetailList(QqchSafeRiskListDetail qqchSafeRiskListDetail);

    List<QqchSafeRiskListDetail> getListByInfoIds(@Param("infoIds") String infoIds);

    int insertQqchSafeRiskListDetail(QqchSafeRiskListDetail qqchSafeRiskListDetail);

    int insertQqchSafeRiskListDetailList(@Param("qqchSafeRiskListDetailList") List<QqchSafeRiskListDetail> qqchSafeRiskListDetailList);

    int updateQqchSafeRiskListDetail(QqchSafeRiskListDetail qqchSafeRiskListDetail);

    int updateQqchSafeRiskListDetailList(@Param("qqchSafeRiskListDetailList") List<QqchSafeRiskListDetail> qqchSafeRiskListDetailList);

    int deleteQqchSafeRiskListDetail(QqchSafeRiskListDetail qqchSafeRiskListDetail);

    int deleteQqchSafeRiskListDetailByPks(@Param("qqchSafeRiskListDetailPkList") List<Long> qqchSafeRiskListDetailPkList);

    int deleteQqchSafeRiskListDetailByInfoIds(@Param("qqchSafeRiskListDetailPkList") List<Long> qqchSafeRiskListDetailPkList);

    void deleteByInfoId(@Param("infoId") Long infoId, @Param("delUser") String userName, @Param("delTime") Date nowDate);

    int getCount();

    void deleteAll();
}
