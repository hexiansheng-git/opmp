package com.hhwy.pm.qqch.preparation.safe.risk.mapper;

import com.hhwy.pm.qqch.preparation.safe.risk.domain.QqchSafeRiskList;
import com.hhwy.pm.qqch.preparation.safe.risk.domain.vo.QqchSafeRiskListVo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author zq
 * @date 2023-08-11 13:41:25
 * @remark
 */
public interface QqchSafeRiskListMapper {

    QqchSafeRiskList getQqchSafeRiskList(QqchSafeRiskList qqchSafeRiskList);

    List<QqchSafeRiskList> getQqchSafeRiskListList(QqchSafeRiskList qqchSafeRiskList);

    int insertQqchSafeRiskList(QqchSafeRiskList qqchSafeRiskList);

    int insertQqchSafeRiskListList(@Param("qqchSafeRiskListList") List<QqchSafeRiskList> qqchSafeRiskListList);

    int updateQqchSafeRiskList(QqchSafeRiskList qqchSafeRiskList);

    int updateQqchSafeRiskListList(@Param("qqchSafeRiskListList") List<QqchSafeRiskList> qqchSafeRiskListList);

    int deleteQqchSafeRiskList(QqchSafeRiskList qqchSafeRiskList);

    int deleteQqchSafeRiskListByPks(@Param("qqchSafeRiskListPkList") List<Long> qqchSafeRiskListPkList);

    BigDecimal selectMaxVersion(QqchSafeRiskListVo qqchSafeRiskListVo);

    BigDecimal selectLessOrEqualAssignVersion(QqchSafeRiskListVo qqchSafeRiskListVo);

    int getCount(@Param("version") BigDecimal version,@Param("type") String type);
}
