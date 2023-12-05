package com.hhwy.pm.qqch.preparation.safe.qqchSafeEnvirRiskList.mapper;

import com.hhwy.pm.qqch.preparation.safe.qqchSafeEnvirRiskList.domain.QqchSafeEnvirRiskListDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author zq
 * @date 2023-08-14 14:00:39
 * @remark
 */
@Repository
public interface QqchSafeEnvirRiskListDetailMapper {

    QqchSafeEnvirRiskListDetail getQqchSafeEnvirRiskListDetail(QqchSafeEnvirRiskListDetail qqchSafeEnvirRiskListDetail);

    List<QqchSafeEnvirRiskListDetail> getQqchSafeEnvirRiskListDetailList(QqchSafeEnvirRiskListDetail qqchSafeEnvirRiskListDetail);

    int insertQqchSafeEnvirRiskListDetail(QqchSafeEnvirRiskListDetail qqchSafeEnvirRiskListDetail);

    int insertQqchSafeEnvirRiskListDetailList(@Param("qqchSafeEnvirRiskListDetailList") List<QqchSafeEnvirRiskListDetail> qqchSafeEnvirRiskListDetailList);

    int updateQqchSafeEnvirRiskListDetail(QqchSafeEnvirRiskListDetail qqchSafeEnvirRiskListDetail);

    int updateQqchSafeEnvirRiskListDetailList(@Param("qqchSafeEnvirRiskListDetailList") List<QqchSafeEnvirRiskListDetail> qqchSafeEnvirRiskListDetailList);

    int deleteQqchSafeEnvirRiskListDetail(QqchSafeEnvirRiskListDetail qqchSafeEnvirRiskListDetail);

    int deleteQqchSafeEnvirRiskListDetailByPks(@Param("qqchSafeEnvirRiskListDetailPkList") List<Long> qqchSafeEnvirRiskListDetailPkList);

    List<QqchSafeEnvirRiskListDetail> getQqchSafeEnvirRiskListDetailListByInfoId(@Param("infoIdList") List<Long> infoIdList);

    void deleteByInfoId(@Param("infoId") Long infoId, @Param("delUser") String delUser, @Param("delTime") Date delTime);

    List<QqchSafeEnvirRiskListDetail> getDetailListByInfoIds(@Param("infoIds") String infoIds);
}
