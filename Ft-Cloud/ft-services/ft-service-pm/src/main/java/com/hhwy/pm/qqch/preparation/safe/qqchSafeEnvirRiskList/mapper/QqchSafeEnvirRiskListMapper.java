package com.hhwy.pm.qqch.preparation.safe.qqchSafeEnvirRiskList.mapper;

import com.hhwy.pm.qqch.preparation.safe.qqchSafeEnvirRiskList.domain.QqchSafeEnvirRiskList;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zq
 * @date 2023-08-14 13:56:17
 * @remark
 */
@Repository
public interface QqchSafeEnvirRiskListMapper {

    QqchSafeEnvirRiskList getQqchSafeEnvirRiskList(QqchSafeEnvirRiskList qqchSafeEnvirRiskList);

    List<QqchSafeEnvirRiskList> getQqchSafeEnvirRiskListList(QqchSafeEnvirRiskList qqchSafeEnvirRiskList);

    int insertQqchSafeEnvirRiskList(QqchSafeEnvirRiskList qqchSafeEnvirRiskList);

    int insertQqchSafeEnvirRiskListList(@Param("qqchSafeEnvirRiskListList") List<QqchSafeEnvirRiskList> qqchSafeEnvirRiskListList);

    int updateQqchSafeEnvirRiskList(QqchSafeEnvirRiskList qqchSafeEnvirRiskList);

    int updateQqchSafeEnvirRiskListList(@Param("qqchSafeEnvirRiskListList") List<QqchSafeEnvirRiskList> qqchSafeEnvirRiskListList);

    int deleteQqchSafeEnvirRiskList(QqchSafeEnvirRiskList qqchSafeEnvirRiskList);

    int deleteQqchSafeEnvirRiskListByPks(@Param("qqchSafeEnvirRiskListPkList") List<Long> qqchSafeEnvirRiskListPkList);
}
