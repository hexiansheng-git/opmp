package com.hhwy.pm.qqch.tax.qqchTaxGlobal.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.SpringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.common.aspect.CompileAspect;
import com.hhwy.pm.qqch.common.aspect.CompileOptEnum;
import com.hhwy.pm.qqch.common.domain.CompileEntity;
import com.hhwy.pm.qqch.tax.qqchTaxGlobal.domain.QqchTaxGlobal;
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
import java.util.ArrayList;
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
    public CompileEntity<QqchTaxGlobalFormula> getFormula(QqchTaxGlobalFormula dto) {
        CompileEntity<QqchTaxGlobalFormula> qqchTaxGlobalFormulaCompileEntity = new CompileEntity<>();

        List<QqchTaxGlobalFormula> qqchTaxGlobalFormulaList = this.qqchTaxGlobalFormulaMapper.getQqchTaxGlobalFormulaList(dto);
        XmslProjectBasicInfo prj = this.getPrj();
        BigDecimal prePayRate = prj.getPrepaymentRatio();
        XmslContractInfo cont = this.getCont();
        BigDecimal excContAmt = cont.getExcludingAmout();
        String currency = cont.getListCurrencyCode();
        BigDecimal rate = this.getRateByCurrency(currency);
        BigDecimal cnyRate = this.getRateByCurrency("CNY");
        BigDecimal localRate = this.getRateByCurrency(prj.getPaymentCurrency());
        QqchTaxGlobalFormula res = new QqchTaxGlobalFormula();
        if (!CollectionUtils.isEmpty(qqchTaxGlobalFormulaList)) {
            res = qqchTaxGlobalFormulaList.get(0);
        }

        res.setCurrency(currency);
        res.setExcContAmt(excContAmt);
        res.setRate(rate);
        res.setCnyRate(cnyRate);
        res.setLocalRate(localRate);
        res.setPrePayRate(prePayRate);
        qqchTaxGlobalFormulaCompileEntity.setDto(res);
        return qqchTaxGlobalFormulaCompileEntity;
    }

    @Override
    public int save(QqchTaxGlobalFormula dealSaveDto) {
        this.qqchTaxGlobalFormulaMapper.insertQqchTaxGlobalFormula(dealSaveDto);
        return 1;
    }

    @Override
    public List<QqchTaxGlobal> getGlobalByFormula(QqchTaxGlobalFormula dealListDto) {
        IQqchTaxGlobalFormulaService bean = SpringUtils.getBean(IQqchTaxGlobalFormulaService.class);
        CompileEntity<QqchTaxGlobalFormula> formula1 = bean.getFormula(dealListDto);
        QqchTaxGlobalFormula formula = formula1.getDto();
        ArrayList<QqchTaxGlobal> qqchTaxGlobals = new ArrayList<>();

        QqchTaxGlobal rec = new QqchTaxGlobal();
        rec.setItemName("本期预计实收工程款");
        rec.setRegionLocalAmt(formula.getLocalRecAmt());
        rec.setRegionLocalRate(formula.getLocalRate());
        rec.setOverseasCnyAmt(formula.getCnyRecAmt());
        rec.setOverseasUsdAmt(formula.getUsdRecAmt());
        rec.setOverseasCnyRate(formula.getCnyRate());


        QqchTaxGlobal back = new QqchTaxGlobal();
        back.setItemName("工程质保金返回");
        back.setRegionLocalAmt(formula.getLocalBackAmt());
        back.setOverseasCnyAmt(formula.getCnyBackAmt());
        back.setOverseasUsdAmt(formula.getUsdBackAmt());
        back.setOverseasCnyRate(formula.getCnyRate());
        back.setRegionLocalRate(formula.getLocalRate());


        QqchTaxGlobal pay = new QqchTaxGlobal();
        pay.setItemName("本期预计实收预付款");
        pay.setRegionLocalAmt(formula.getLocalPayAmt());
        pay.setOverseasCnyAmt(formula.getCnyPayAmt());
        pay.setOverseasUsdAmt(formula.getUsdPayAmt());
        pay.setOverseasCnyRate(formula.getCnyRate());
        pay.setRegionLocalRate(formula.getLocalRate());


        qqchTaxGlobals.add(rec);
        qqchTaxGlobals.add(back);
        qqchTaxGlobals.add(pay);
        return qqchTaxGlobals;
    }


    private XmslProjectBasicInfo getPrj() {
        XmslProjectBasicInfo projectBasicInfo = null;
        try {
            XmslProjectBasicInfo where = new XmslProjectBasicInfo();
            where.setDelFlag("0");
            // TODO 需要获取到项目信息
            where.setProjectCode("0001");
            projectBasicInfo = projectBasicInfoService.getProjectBasicInfo(where);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return projectBasicInfo == null ? new XmslProjectBasicInfo() : projectBasicInfo;
    }

    private XmslContractInfo getCont() {
        XmslContractInfo validMaxVersionContractInfo = null;
        try {
            validMaxVersionContractInfo = contractInfoService.getValidMaxVersionContractInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return validMaxVersionContractInfo == null ? new XmslContractInfo() : validMaxVersionContractInfo;
    }
    

    private BigDecimal getRateByCurrency(String currency) {

        return BigDecimal.ONE;
    }

}
