package com.hhwy.pm.qqch.tax.qqchTaxIn.service;

import com.hhwy.pm.qqch.common.domain.CompileEntity;
import com.hhwy.pm.qqch.tax.qqchTaxIn.domain.QqchTaxIn;
import com.hhwy.pm.qqch.tax.qqchTaxIn.domain.QqchTaxInDetail;
import com.hhwy.pm.qqch.tax.qqchTaxIn.vo.TaxInVO;

import java.util.List;

/**
 * @author mls
 * @date 2023-08-09 18:17:32
 * @remark
 */
public interface IQqchTaxInService {

    QqchTaxIn getQqchTaxIn(QqchTaxIn qqchTaxIn);

    List<QqchTaxIn> getQqchTaxInList(QqchTaxIn qqchTaxIn);

    int insertQqchTaxIn(QqchTaxIn qqchTaxIn);

    int insertQqchTaxInList(List<QqchTaxIn> qqchTaxInList);

    int updateQqchTaxIn(QqchTaxIn qqchTaxIn);

    int updateQqchTaxInList(List<QqchTaxIn> qqchTaxInList);

    int deleteQqchTaxIn(QqchTaxIn qqchTaxIn);

    int deleteQqchTaxInByPks(List<Long> qqchTaxInPkList);

    /**
     * @param qqchTaxInParam
     * @return
     */
    CompileEntity<TaxInVO> list(QqchTaxIn qqchTaxInParam);

    void save(CompileEntity<TaxInVO> qqchTaxInParam);

    List<QqchTaxInDetail> saveInList(List<QqchTaxIn> list);


    public List<QqchTaxIn> getInList(QqchTaxIn taxIn);

    public List<TaxInVO.CurrencyVO> getCurrencyInfo(String currencyCode);
    public List<TaxInVO.CurrencyVO> getCurrencyInfo();

    public List<String> getYearList();

}
