package com.hhwy.pm.qqch.preparation.safe.qqchSafeEnvirRiskList.mapper;

import java.util.Date;
import java.util.List;

import com.hhwy.pm.qqch.preparation.safe.qqchSafeEnvirRiskList.domain.QqchSafeEnvirRiskListDetail;
import org.apache.ibatis.annotations.Param;

/**
 * @author zq
 * @date 2023-08-14 14:00:39
 * @remark
 */
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

    void deleteByInfoIds(@Param("infoIdList") List<Long> infoIdList, @Param("delUser") String delUser, @Param("delUserName") String delUserName, @Param("delTime") Date delTime);

}
