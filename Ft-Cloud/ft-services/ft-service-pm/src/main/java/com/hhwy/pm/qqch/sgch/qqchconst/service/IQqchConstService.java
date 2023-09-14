package com.hhwy.pm.qqch.sgch.qqchconst.service;

import com.hhwy.pm.qqch.common.domain.CompileEntity;
import com.hhwy.pm.qqch.sgch.qqchconst.domain.QqchConst;
import com.hhwy.pm.qqch.sgch.qqchconst.domain.QqchConstStaffPlanResult;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author mls
 * @date 2023-08-03 16:08:05
 * @remark
 */
public interface IQqchConstService {

    QqchConst getQqchConst(QqchConst qqchConst);

    List<QqchConst> getQqchConstList(QqchConst qqchConst);

    int insertQqchConst(QqchConst qqchConst);

    int insertQqchConstList(List<QqchConst> qqchConstList);

    int updateQqchConst(QqchConst qqchConst);

    int updateQqchConstList(List<QqchConst> qqchConstList);

    int deleteQqchConst(QqchConst qqchConst);

    int deleteQqchConstByPks(List<Long> qqchConstPkList);

    void save(List<QqchConst> qqchConsts, CompileEntity<List<QqchConst>> dtoList);

    CompileEntity list(QqchConst qqchConst);

    List<QqchConstStaffPlanResult> selectQqchConst(BigDecimal version);

    /**
     * 4.2弹窗
     * @return
     */
    List<QqchConst> popUpWindows(QqchConst qqchConst);

}
