package com.hhwy.pm.qqch.preparation.safe.qqchSafeMostEnvirRiskList.mapper;

import com.hhwy.pm.qqch.preparation.safe.qqchSafeMostEnvirRiskList.domain.QqchSafeMostEnvirRiskListDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author zq
 * @date 2023-08-14 14:04:04
 * @remark
 */
@Repository
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

    void deleteByInfoId(@Param("infoId") Long infoId, @Param("delUser") String delUser, @Param("delTime") Date nowDate);
}
