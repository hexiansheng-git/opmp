package com.hhwy.pm.qqch.preparation.safe.qqchSafeMostEnvirRiskList.mapper;

import com.hhwy.pm.qqch.preparation.safe.qqchSafeMostEnvirRiskList.domain.QqchSafeMostEnvirRiskList;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zq
 * @date 2023-08-14 14:04:02
 * @remark
 */
public interface QqchSafeMostEnvirRiskListMapper {

    QqchSafeMostEnvirRiskList getQqchSafeMostEnvirRiskList(QqchSafeMostEnvirRiskList qqchSafeMostEnvirRiskList);

    List<QqchSafeMostEnvirRiskList> getQqchSafeMostEnvirRiskListList(QqchSafeMostEnvirRiskList qqchSafeMostEnvirRiskList);

    int insertQqchSafeMostEnvirRiskList(QqchSafeMostEnvirRiskList qqchSafeMostEnvirRiskList);

    int insertQqchSafeMostEnvirRiskListList(@Param("qqchSafeMostEnvirRiskListList") List<QqchSafeMostEnvirRiskList> qqchSafeMostEnvirRiskListList);

    int updateQqchSafeMostEnvirRiskList(QqchSafeMostEnvirRiskList qqchSafeMostEnvirRiskList);

    int updateQqchSafeMostEnvirRiskListList(@Param("qqchSafeMostEnvirRiskListList") List<QqchSafeMostEnvirRiskList> qqchSafeMostEnvirRiskListList);

    int deleteQqchSafeMostEnvirRiskList(QqchSafeMostEnvirRiskList qqchSafeMostEnvirRiskList);

    int deleteQqchSafeMostEnvirRiskListByPks(@Param("qqchSafeMostEnvirRiskListPkList") List<Long> qqchSafeMostEnvirRiskListPkList);
}
