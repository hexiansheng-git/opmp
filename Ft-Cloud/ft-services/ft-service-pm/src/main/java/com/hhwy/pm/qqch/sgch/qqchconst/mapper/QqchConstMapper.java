package com.hhwy.pm.qqch.sgch.qqchconst.mapper;

import com.hhwy.pm.qqch.sgch.qqchconst.domain.QqchConst;
import com.hhwy.pm.qqch.sgch.qqchconst.domain.QqchConstStaffPlanResult;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author mls
 * @date 2023-08-03 16:08:05
 * @remark
 */
public interface QqchConstMapper {

    QqchConst getQqchConst(QqchConst qqchConst);

    List<QqchConst> getQqchConstList(QqchConst qqchConst);

    int insertQqchConst(QqchConst qqchConst);

    int insertQqchConstList(@Param("qqchConstList") List<QqchConst> qqchConstList);

    int updateQqchConst(QqchConst qqchConst);

    int updateQqchConstList(@Param("qqchConstList") List<QqchConst> qqchConstList);

    int deleteQqchConst(QqchConst qqchConst);

    int deleteQqchConstByPks(@Param("qqchConstPkList") List<Long> qqchConstPkList);

    List<QqchConstStaffPlanResult> selectQqchConst(BigDecimal version);
}
