package com.hhwy.pm.qqch.tax.qqchTaxGlobalFormula.service;

import java.util.List;
import com.hhwy.pm.qqch.tax.qqchTaxGlobalFormula.domain.QqchTaxGlobalFormula;

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
    }
