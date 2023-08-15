package com.hhwy.pm.qqch.preparation.quality.emp.mapper;

import com.hhwy.pm.qqch.preparation.quality.emp.domain.QqchEmpItem;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * @author mls
 * @date 2023-08-15 09:36:29
 * @remark
 */
public interface QqchEmpItemMapper {

    QqchEmpItem getQqchEmpItem(QqchEmpItem qqchEmpItem);

    List<QqchEmpItem> getQqchEmpItemList(QqchEmpItem qqchEmpItem);

    int insertQqchEmpItem(QqchEmpItem qqchEmpItem);

    int insertQqchEmpItemList(@Param("qqchEmpItemList") List<QqchEmpItem> qqchEmpItemList);

    int updateQqchEmpItem(QqchEmpItem qqchEmpItem);

    int updateQqchEmpItemList(@Param("qqchEmpItemList") List<QqchEmpItem> qqchEmpItemList);

    int deleteQqchEmpItem(QqchEmpItem qqchEmpItem);

    int deleteQqchEmpItemByPks(@Param("qqchEmpItemPkList") List<Long> qqchEmpItemPkList);

    int deleteByWbsCodeAndVersion(@Param("wbsCodeList")Collection<String> wbsCodeList, @Param("version")BigDecimal version);
}
