package com.hhwy.pm.qqch.preparation.technique.scheme.mapper;

import com.hhwy.pm.qqch.preparation.technique.scheme.domain.QqchDangerConstructionList;
import java.math.BigDecimal;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author zhenglili
 * @date 2023-07-17 14:26:41
 * @remark 3.4.3危大工程方案清单
 */
public interface QqchDangerConstructionListMapper {

    QqchDangerConstructionList getQqchDangerConstructionList(QqchDangerConstructionList qqchDangerConstructionList);

    List<QqchDangerConstructionList> getQqchDangerConstructionListList(
        QqchDangerConstructionList qqchDangerConstructionList);

    int insertQqchDangerConstructionList(QqchDangerConstructionList qqchDangerConstructionList);

    int insertQqchDangerConstructionListList(
        @Param("qqchDangerConstructionListList") List<QqchDangerConstructionList> qqchDangerConstructionListList);

    int updateQqchDangerConstructionList(QqchDangerConstructionList qqchDangerConstructionList);

    int updateQqchDangerConstructionListList(
        @Param("list") List<QqchDangerConstructionList> qqchDangerConstructionListList);

    int deleteQqchDangerConstructionList(QqchDangerConstructionList qqchDangerConstructionList);

    int deleteQqchDangerConstructionListByPks(
        @Param("qqchDangerConstructionListPkList") List<Long> qqchDangerConstructionListPkList);

    List<QqchDangerConstructionList> getByWbsCodes(@Param("wbsCodes") String[] wbsCodes,
        @Param("maxVersion") BigDecimal maxVersion);
}
