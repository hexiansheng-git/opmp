package com.hhwy.pm.qqch.preparation.safe.qqchSafeMostEnvirRiskList.mapper;

import java.util.Date;
import java.util.List;

import com.hhwy.pm.qqch.preparation.safe.qqchSafeMostEnvirRiskList.domain.QqchSafeMostEnvirRiskListDetail;
import org.apache.ibatis.annotations.Param;

/**
 * @author zq
 * @date 2023-08-14 14:04:04
 * @remark
 */
public interface QqchSafeMostEnvirRiskListDetailMapper {

    QqchSafeMostEnvirRiskListDetail getQqchSafeMostEnvirRiskListDetail(QqchSafeMostEnvirRiskListDetail qqchSafeMostEnvirRiskListDetail);

    List<QqchSafeMostEnvirRiskListDetail> getQqchSafeMostEnvirRiskListDetailList(QqchSafeMostEnvirRiskListDetail qqchSafeMostEnvirRiskListDetail);

    int insertQqchSafeMostEnvirRiskListDetail(QqchSafeMostEnvirRiskListDetail qqchSafeMostEnvirRiskListDetail);

    int insertQqchSafeMostEnvirRiskListDetailList(@Param("qqchSafeMostEnvirRiskListDetailList") List<QqchSafeMostEnvirRiskListDetail> qqchSafeMostEnvirRiskListDetailList);

    int updateQqchSafeMostEnvirRiskListDetail(QqchSafeMostEnvirRiskListDetail qqchSafeMostEnvirRiskListDetail);

    int updateQqchSafeMostEnvirRiskListDetailList(@Param("qqchSafeMostEnvirRiskListDetailList") List<QqchSafeMostEnvirRiskListDetail> qqchSafeMostEnvirRiskListDetailList);

    int deleteQqchSafeMostEnvirRiskListDetail(QqchSafeMostEnvirRiskListDetail qqchSafeMostEnvirRiskListDetail);

    int deleteQqchSafeMostEnvirRiskListDetailByPks(@Param("qqchSafeMostEnvirRiskListDetailPkList") List<Long> qqchSafeMostEnvirRiskListDetailPkList);

    List<QqchSafeMostEnvirRiskListDetail> getListByInfoIds(@Param("infoIdList") List<Long> infoIdList);

    void deleteByInfoIds(@Param("infoIdList") List<Long> infoIdList, @Param("delUser") String delUser, @Param("delTime") Date nowDate);
}
