package com.hhwy.pm.qqch.tax.qqchTaxGlobal.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.common.aspect.CompileAspect;
import com.hhwy.pm.qqch.common.aspect.CompileOptEnum;
import com.hhwy.pm.qqch.tax.qqchTaxGlobal.domain.QqchTaxGlobalFormula;
import com.hhwy.pm.qqch.tax.qqchTaxGlobal.mapper.QqchTaxGlobalFormulaMapper;
import com.hhwy.pm.qqch.tax.qqchTaxGlobal.service.IQqchTaxGlobalFormulaService;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractInfo;
import com.hhwy.pm.xmsl.contractInfo.service.IXmslContractInfoService;
import com.hhwy.pm.xmsl.project.domain.XmslProjectBasicInfo;
import com.hhwy.pm.xmsl.project.service.IXmslProjectBasicInfoService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author mls
 * @date 2023-08-17 16:19:10
 * @remark
 */
@Service
public class QqchTaxGlobalFormulaServiceImpl implements IQqchTaxGlobalFormulaService {

    private static final String TN = "qqch_tax_global_formula";

    @Resource
    private IXmslContractInfoService contractInfoService;
    @Resource
    private QqchTaxGlobalFormulaMapper qqchTaxGlobalFormulaMapper;

    @Resource
    private IXmslProjectBasicInfoService projectBasicInfoService;


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

    @Override
    @CompileAspect(type = CompileOptEnum.LIST, tableName = TN)
    public QqchTaxGlobalFormula getFormula(QqchTaxGlobalFormula dto) {
        List<QqchTaxGlobalFormula> qqchTaxGlobalFormulaList = this.qqchTaxGlobalFormulaMapper.getQqchTaxGlobalFormulaList(dto);
        BigDecimal prePayRate = this.getPrePayRate();
        BigDecimal excContAmt = this.getExcContAmt();
        String currency = this.getCurrency();
        BigDecimal rate = this.getRateByCurrency(currency);
        QqchTaxGlobalFormula res = new QqchTaxGlobalFormula();
        if (!CollectionUtils.isEmpty(qqchTaxGlobalFormulaList)) {
            res = qqchTaxGlobalFormulaList.get(0);
        }
        
        res.setCurrency(currency);
        res.setExcContAmt(excContAmt);
        res.setRate(rate);
        res.setPrePayRate(prePayRate);

        return res;
    }

    @Override
    public int save(QqchTaxGlobalFormula dealSaveDto) {
        this.qqchTaxGlobalFormulaMapper.insertQqchTaxGlobalFormula(dealSaveDto);
        return 1;
    }


    private BigDecimal getPrePayRate() {
        XmslProjectBasicInfo where = new XmslProjectBasicInfo();
        where.setDelFlag("0");
        // TODO 需要获取到项目信息
        where.setProjectCode("0001");
        XmslProjectBasicInfo projectBasicInfo = projectBasicInfoService.getProjectBasicInfo(where);
        return projectBasicInfo.getPrepaymentRatio();
    }

    private BigDecimal getExcContAmt() {
        XmslContractInfo validMaxVersionContractInfo = contractInfoService.getValidMaxVersionContractInfo();
        return validMaxVersionContractInfo.getExcludingAmout();
    }

    private String getCurrency() {
        XmslContractInfo validMaxVersionContractInfo = contractInfoService.getValidMaxVersionContractInfo();
        return validMaxVersionContractInfo.getListCurrencyCode();
    }

    private BigDecimal getRateByCurrency(String currency) {

        return BigDecimal.ONE;
    }

}
