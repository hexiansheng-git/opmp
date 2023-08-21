package com.hhwy.pm.qqch.tax.qqchTaxGlobal.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.hhwy.pm.qqch.tax.qqchTaxGlobal.domain.QqchTaxGlobalFormula;

/**
 * @author mls
 * @date 2023-08-17 16:19:10
 * @remark 
 */
public interface QqchTaxGlobalFormulaMapper {
                                                                                                                                                                                                                                                                                                                                                                                                                                                    
    QqchTaxGlobalFormula getQqchTaxGlobalFormula(QqchTaxGlobalFormula qqchTaxGlobalFormula);

    List<QqchTaxGlobalFormula> getQqchTaxGlobalFormulaList(QqchTaxGlobalFormula qqchTaxGlobalFormula);

    int insertQqchTaxGlobalFormula(QqchTaxGlobalFormula qqchTaxGlobalFormula);

    int insertQqchTaxGlobalFormulaList(@Param("qqchTaxGlobalFormulaList") List<QqchTaxGlobalFormula> qqchTaxGlobalFormulaList);

    int updateQqchTaxGlobalFormula(QqchTaxGlobalFormula qqchTaxGlobalFormula);

            int updateQqchTaxGlobalFormulaList(@Param("qqchTaxGlobalFormulaList") List<QqchTaxGlobalFormula> qqchTaxGlobalFormulaList);
    
    int deleteQqchTaxGlobalFormula(QqchTaxGlobalFormula qqchTaxGlobalFormula);

            int deleteQqchTaxGlobalFormulaByPks(@Param("qqchTaxGlobalFormulaPkList") List<Long> qqchTaxGlobalFormulaPkList);
    }
