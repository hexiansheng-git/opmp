package com.hhwy.pm.qqch.tax.qqchTaxGlobalFormula.service.impl;

import java.util.List;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.security.util.SecurityUtils;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.pm.qqch.tax.qqchTaxGlobalFormula.mapper.QqchTaxGlobalFormulaMapper;
import com.hhwy.pm.qqch.tax.qqchTaxGlobalFormula.service.IQqchTaxGlobalFormulaService;
import com.hhwy.pm.qqch.tax.qqchTaxGlobalFormula.domain.QqchTaxGlobalFormula;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author mls
 * @date 2023-08-17 16:19:10
 * @remark 
 */
@Service
public class QqchTaxGlobalFormulaServiceImpl implements IQqchTaxGlobalFormulaService{

    @Autowired
    private QqchTaxGlobalFormulaMapper qqchTaxGlobalFormulaMapper;

                                                                                                                                                                                                                                                                                                                                                                                                                                                    
    public QqchTaxGlobalFormula getQqchTaxGlobalFormula(QqchTaxGlobalFormula qqchTaxGlobalFormula) {
        return qqchTaxGlobalFormulaMapper.getQqchTaxGlobalFormula(qqchTaxGlobalFormula);
    }

    public List<QqchTaxGlobalFormula> getQqchTaxGlobalFormulaList(QqchTaxGlobalFormula qqchTaxGlobalFormula) {
        return qqchTaxGlobalFormulaMapper.getQqchTaxGlobalFormulaList(qqchTaxGlobalFormula);
    }

    @Transactional
    public int insertQqchTaxGlobalFormula(QqchTaxGlobalFormula qqchTaxGlobalFormula) {
        qqchTaxGlobalFormula.setId(IdWorker.createId());
        qqchTaxGlobalFormula.setCreateUser(SecurityUtils.getUserName());
        qqchTaxGlobalFormula.setCreateTime(DateUtils.getNowDate());
        return qqchTaxGlobalFormulaMapper.insertQqchTaxGlobalFormula(qqchTaxGlobalFormula);
    }

    @Transactional
    public int insertQqchTaxGlobalFormulaList(List<QqchTaxGlobalFormula> qqchTaxGlobalFormulaList) {
        for (QqchTaxGlobalFormula qqchTaxGlobalFormula : qqchTaxGlobalFormulaList) {
            qqchTaxGlobalFormula.setId(IdWorker.createId());
            qqchTaxGlobalFormula.setCreateUser(SecurityUtils.getUserName());
            qqchTaxGlobalFormula.setCreateTime(DateUtils.getNowDate());
        }
        return qqchTaxGlobalFormulaMapper.insertQqchTaxGlobalFormulaList(qqchTaxGlobalFormulaList);
    }

    @Transactional
    public int updateQqchTaxGlobalFormula(QqchTaxGlobalFormula qqchTaxGlobalFormula) {
        qqchTaxGlobalFormula.setUpdateUser(SecurityUtils.getUserName());
        qqchTaxGlobalFormula.setUpdateTime(DateUtils.getNowDate());
        return qqchTaxGlobalFormulaMapper.updateQqchTaxGlobalFormula(qqchTaxGlobalFormula);
    }

            @Transactional
        public int updateQqchTaxGlobalFormulaList(List<QqchTaxGlobalFormula> qqchTaxGlobalFormulaList) {
            for (QqchTaxGlobalFormula qqchTaxGlobalFormula : qqchTaxGlobalFormulaList) {
                qqchTaxGlobalFormula.setUpdateUser(SecurityUtils.getUserName());
                qqchTaxGlobalFormula.setUpdateTime(DateUtils.getNowDate());
            }
            return qqchTaxGlobalFormulaMapper.updateQqchTaxGlobalFormulaList(qqchTaxGlobalFormulaList);
        }
    
    @Transactional
    public int deleteQqchTaxGlobalFormula(QqchTaxGlobalFormula qqchTaxGlobalFormula) {
        qqchTaxGlobalFormula.setUpdateUser(SecurityUtils.getUserName());
        qqchTaxGlobalFormula.setUpdateTime(DateUtils.getNowDate());
        return qqchTaxGlobalFormulaMapper.deleteQqchTaxGlobalFormula(qqchTaxGlobalFormula);
    }

            @Transactional
        public int deleteQqchTaxGlobalFormulaByPks(List<Long> qqchTaxGlobalFormulaPkList) {
            return qqchTaxGlobalFormulaMapper.deleteQqchTaxGlobalFormulaByPks(qqchTaxGlobalFormulaPkList);
        }
    }
