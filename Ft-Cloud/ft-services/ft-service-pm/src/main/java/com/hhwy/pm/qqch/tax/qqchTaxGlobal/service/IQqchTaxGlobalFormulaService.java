package com.hhwy.pm.qqch.tax.qqchTaxGlobal.service;

import com.hhwy.pm.qqch.common.domain.CompileEntity;
import com.hhwy.pm.qqch.tax.qqchTaxGlobal.domain.QqchTaxGlobal;
import com.hhwy.pm.qqch.tax.qqchTaxGlobal.domain.QqchTaxGlobalFormula;

import java.util.List;

/**
 * @author mls
 * @date 2023-08-17 16:19:10
 * @remark
 */
public interface IQqchTaxGlobalFormulaService {

    QqchTaxGlobalFormula getQqchTaxGlobalFormula(QqchTaxGlobalFormula qqchTaxGlobalFormula);

    List<QqchTaxGlobalFormula> getQqchTaxGlobalFormulaList(QqchTaxGlobalFormula qqchTaxGlobalFormula);

    int insertQqchTaxGlobalFormula(QqchTaxGlobalFormula qqchTaxGlobalFormula);

    int insertQqchTaxGlobalFormulaList(List<QqchTaxGlobalFormula> qqchTaxGlobalFormulaList);

    int updateQqchTaxGlobalFormula(QqchTaxGlobalFormula qqchTaxGlobalFormula);

    int updateQqchTaxGlobalFormulaList(List<QqchTaxGlobalFormula> qqchTaxGlobalFormulaList);

    int deleteQqchTaxGlobalFormula(QqchTaxGlobalFormula qqchTaxGlobalFormula);

    int deleteQqchTaxGlobalFormulaByPks(List<Long> qqchTaxGlobalFormulaPkList);

    CompileEntity<QqchTaxGlobalFormula> getFormula(QqchTaxGlobalFormula dealListDto);

    int save(QqchTaxGlobalFormula dealSaveDto);

    /**
     * @param dealListDto 
     * @return
     */
    List<QqchTaxGlobal> getGlobalByFormula(QqchTaxGlobalFormula dealListDto);
}
