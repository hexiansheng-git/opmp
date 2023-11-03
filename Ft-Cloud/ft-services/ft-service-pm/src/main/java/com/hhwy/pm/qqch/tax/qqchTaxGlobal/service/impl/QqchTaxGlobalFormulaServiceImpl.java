package com.hhwy.pm.qqch.tax.qqchTaxGlobal.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.SpringUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.common.service.CommonServiceUtil;
import com.hhwy.pm.constant.PmConstant;
import com.hhwy.pm.jdgl.statistics.util.StatisticsUtils;
import com.hhwy.pm.qqch.common.aspect.CompileAspect;
import com.hhwy.pm.qqch.common.aspect.CompileOptEnum;
import com.hhwy.pm.qqch.common.domain.CompileEntity;
import com.hhwy.pm.qqch.sgch.prodplan.service.IQqchProdPlanService;
import com.hhwy.pm.qqch.tax.qqchTaxGlobal.domain.QqchTaxGlobal;
import com.hhwy.pm.qqch.tax.qqchTaxGlobal.domain.QqchTaxGlobalFormula;
import com.hhwy.pm.qqch.tax.qqchTaxGlobal.mapper.QqchTaxGlobalFormulaMapper;
import com.hhwy.pm.qqch.tax.qqchTaxGlobal.service.IQqchTaxGlobalFormulaService;
import com.hhwy.pm.qqch.tax.qqchTaxIn.service.IQqchTaxInService;
import com.hhwy.pm.qqch.tax.qqchTaxIn.domain.vo.TaxInVO;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractInfo;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractPayinfo;
import com.hhwy.pm.xmsl.contractInfo.service.IXmslContractInfoService;
import com.hhwy.pm.xmsl.project.domain.vo.ProjectBasicInfo;
import com.hhwy.pm.xmsl.project.service.IXmslProjectBasicInfoService;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.common.CommonAssert;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

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
    @Resource
    private IQqchTaxInService taxInService;
    @Resource
    private IQqchProdPlanService qqchProdPlanService;


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

        Integer year = dto.getYear();

        if(year == null) {
            throw new RuntimeException("年份参数异常!");
        }

        CompileEntity<QqchTaxGlobalFormula> qqchTaxGlobalFormulaCompileEntity = new CompileEntity<>();

        // 获取当前的明细填报数据
        List<QqchTaxGlobalFormula> qqchTaxGlobalFormulaList = this.qqchTaxGlobalFormulaMapper.getQqchTaxGlobalFormulaList(dto);

        QqchTaxGlobalFormula res = new QqchTaxGlobalFormula();
        if (!CollectionUtils.isEmpty(qqchTaxGlobalFormulaList)) {
            res = qqchTaxGlobalFormulaList.get(0);
        }

        // 获取项目信息
        ProjectBasicInfo prj = this.getPrj();
        // 预付款比例
        BigDecimal prePayRate = prj.getPrepaymentRatio();
        // 项目-当地货币
        String paymentCurrency = prj.getPaymentCurrency();

        // 获取合同信息
        XmslContractInfo cont = this.getCont();
        // 合同-支付信息集合
        List<XmslContractPayinfo> xmslContractPayinfoList = cont.getXmslContractPayinfoList();
        // 合同不含税金额
        BigDecimal excContAmt = cont.getExcludingAmout();
        // 合同币种：清单标价货币(编码)
        String currency = cont.getListCurrencyCode();

        // 合同币种对美汇率
        BigDecimal rate = res.getRate();
        // 合同币种比例
        BigDecimal contProportion = BigDecimal.ZERO;
        // 合同币种
        if(rate == null || rate.compareTo(BigDecimal.ZERO) == 0) {
            rate = getExchageRate(currency, xmslContractPayinfoList);
            contProportion = getProportion(currency, xmslContractPayinfoList);
        }

        // 获取人民币币种
        BigDecimal cnyRate = res.getCnyRate();
        // 人民币比例
        BigDecimal cnyProportion = BigDecimal.ZERO;
        // 人民币对美汇率
        if(cnyRate == null || cnyRate.compareTo(BigDecimal.ZERO) == 0) {
            cnyRate = getExchageRate("CNY", xmslContractPayinfoList);
            cnyProportion = getProportion("CNY", xmslContractPayinfoList);
        }

        // 当地币种对美汇率
        BigDecimal localRate = res.getLocalRate();
        // 当地币种比例
        BigDecimal localProportion = BigDecimal.ZERO;
        // 当地币种对美汇率
        if(localRate == null || localRate.compareTo(BigDecimal.ZERO) == 0) {
            localRate = getExchageRate(paymentCurrency, xmslContractPayinfoList);
            localProportion = getProportion(paymentCurrency, xmslContractPayinfoList);
        }

        // 美元比例
        BigDecimal usdProportion = getProportion("USD", xmslContractPayinfoList);

        // 计量账单审核时长（天)
        BigDecimal meteringCircle = BigDecimal.ZERO;
        // 合同-计量批复时限（天）
        String meteringTime = cont.getMeteringTime();
        meteringCircle = meteringCircle.add(StringUtils.isEmpty(meteringTime) || !StatisticsUtils.isNumeric2(meteringTime)
                ? BigDecimal.ZERO : new BigDecimal(meteringTime));
        // 合同-计量账单审核时长（天)
        String billProcessDuration = cont.getBillProcessDuration();
        meteringCircle = meteringCircle.add(StringUtils.isEmpty(billProcessDuration) || !StatisticsUtils.isNumeric2(billProcessDuration)
                ? BigDecimal.ZERO : new BigDecimal(billProcessDuration));
        meteringCircle = meteringCircle.divide(new BigDecimal(30), 0, BigDecimal.ROUND_UP);

        res.setMeteringCircle(meteringCircle); // 计量账单审核时长（天)

        // 从1.2.5获取工程量计量金额
        Map<String, Date> dateRange = getDateRange(year,meteringCircle);
        BigDecimal qqchProdPlanAmt = qqchProdPlanService.getQqchProdPlanAmt4DateRange(null, dateRange.get("start"), dateRange.get("end"));

        if(rate != null && qqchProdPlanAmt != null && rate.compareTo(BigDecimal.ZERO) != 0) {
            res.setQuantities(qqchProdPlanAmt.divide(rate, 2, BigDecimal.ROUND_HALF_UP)); // 工程量计量金额
        } else {
            res.setQuantities(BigDecimal.ZERO); // 工程量计量金额
        }

        // 节点回收比例
        BigDecimal nodeRecoveryRate = BigDecimal.ZERO;
        // 合同-竣工日期
        Date completedTime = cont.getCompletedTime();
        // 合同缺陷责任(月)
        String defectLiability = cont.getDefectLiability();

        if(completedTime != null) {
            Calendar cl = Calendar.getInstance();
            cl.setTime(completedTime);
            int year1 = cl.get(Calendar.YEAR);
            nodeRecoveryRate = nodeRecoveryRate.add(year1 == year ? new BigDecimal(50) : BigDecimal.ZERO);
            if(StringUtils.isNotEmpty(defectLiability) && StatisticsUtils.isNumeric2(defectLiability)) {
                BigDecimal divide = new BigDecimal(defectLiability).divide(BigDecimal.ONE, 0, BigDecimal.ROUND_UP);
                cl.add(Calendar.MONTH, divide.intValue());
                int year2 = cl.get(Calendar.YEAR);
                nodeRecoveryRate = nodeRecoveryRate.add(year2 == year ? new BigDecimal(50) : BigDecimal.ZERO);

            }
        }

        res.setNodeRecoveryRate(res.getNodeRecoveryRate() == null ? nodeRecoveryRate : res.getNodeRecoveryRate()); // 节点回收比例
        res.setCurrency(currency);      //币种
        if(rate != null && excContAmt != null && rate.compareTo(BigDecimal.ZERO) != 0) {
            res.setExcContAmt(excContAmt.divide(rate, 2, BigDecimal.ROUND_HALF_UP));  //不含税合同金额
        } else {
            res.setExcContAmt(BigDecimal.ZERO);  //不含税合同金额
        }
        res.setRate(rate == null ? BigDecimal.ZERO : rate);              //汇率
        res.setCnyRate(cnyRate == null ? BigDecimal.ZERO : cnyRate);        //美元对人民币汇率
        res.setLocalRate(localRate == null ? BigDecimal.ZERO : localRate);    //项目当地币汇率
        res.setPrePayRate(prePayRate == null ? BigDecimal.ZERO : prePayRate);  //预付款比例
        res.setLocalProportion(localProportion == null ? BigDecimal.ZERO : localProportion); // 当地币种支付比例
        res.setCnyProportion(cnyProportion == null ? BigDecimal.ZERO : cnyProportion); // 人民币支付比例
        res.setContProportion(contProportion == null ? BigDecimal.ZERO : contProportion); // 合同币种支付比例
        res.setUsdProportion(usdProportion == null ? BigDecimal.ZERO : usdProportion); // 美元支付比例
        //质保金（保留金）扣除比例（%） 从项目中拿  qualityGuaranteeDepositRatio
        res.setGuaDeductRate(ObjectUtils.nvlBigDecimal(prj.getQualityGuaranteeDepositRatio()));
        qqchTaxGlobalFormulaCompileEntity.setDto(res);
        return qqchTaxGlobalFormulaCompileEntity;
    }

    private BigDecimal getProportion(String currency, List<XmslContractPayinfo> xmslContractPayinfoList) {
        BigDecimal proportion = BigDecimal.ZERO;
        if(StringUtils.isNotEmpty(currency)) {
            XmslContractPayinfo xmslContractPayinfo = xmslContractPayinfoList.stream().filter(vo -> currency.equals(vo.getCurrencyCode())).findFirst().orElse(null);
            if(xmslContractPayinfo != null && xmslContractPayinfo.getProportion() != null)
                proportion = new BigDecimal(xmslContractPayinfo.getProportion());
        }
        return proportion;
    }

    /**
     * 获取汇率
     * @param currency
     * @param xmslContractPayinfoList
     * @return
     */
    private BigDecimal getExchageRate(String currency, List<XmslContractPayinfo> xmslContractPayinfoList) {
        BigDecimal rate = BigDecimal.ZERO;
        if(StringUtils.isNotEmpty(currency)) {
            XmslContractPayinfo xmslContractPayinfo = xmslContractPayinfoList.stream().filter(vo -> currency.equals(vo.getCurrencyCode())).findFirst().orElse(null);
            if(xmslContractPayinfo != null && "1".equals(xmslContractPayinfo.getRateType())) {
                String obversionRate = xmslContractPayinfo.getObversionRate();
                rate = StringUtils.isEmpty(obversionRate) ? BigDecimal.ZERO : new BigDecimal(obversionRate);
            } else {
                rate = this.getRateByCurrency(currency);
            }
        }
        return rate;
    }

    /**
     * 根据年份、计量账单审核时长获取计量年月区间
     * @param year
     * @param meteringCircle
     * @return
     */
    private Map<String, Date> getDateRange(Integer year, BigDecimal meteringCircle) {

        Map<String, Date> returnMap = new HashMap<>();

        if(year == null || meteringCircle == null) {
            return returnMap;
        }

        int meteringI = 0 - meteringCircle.intValue();

        Calendar cl = Calendar.getInstance();
        cl.set(year, Calendar.JANUARY, 1);
        cl.add(Calendar.MONTH, meteringI);
        Date startDate = cl.getTime();

        cl.set(year, Calendar.DECEMBER, 1);
        cl.add(Calendar.MONTH, meteringI);
        Date endDate = cl.getTime();

        returnMap.put("start", startDate);
        returnMap.put("end", endDate);

        return returnMap;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int save(QqchTaxGlobalFormula dealSaveDto) {
        dealSaveDto.setId(IdWorker.createId());
        Integer year = dealSaveDto.getYear();
        CommonAssert.notNull(year, "年份不能为空");
        QqchTaxGlobalFormula where = new QqchTaxGlobalFormula();
        where.setYear(year);
        List<QqchTaxGlobalFormula> qqchTaxGlobalFormulaList = qqchTaxGlobalFormulaMapper.getQqchTaxGlobalFormulaList(where);
        if (!CollectionUtils.isEmpty(qqchTaxGlobalFormulaList)){
            // 根据年份把数据删掉 这里跟玉涛确认多了 不用做版本控制
            List<Long> ids = qqchTaxGlobalFormulaList.stream().map(QqchTaxGlobalFormula::getId).collect(Collectors.toList());
            this.qqchTaxGlobalFormulaMapper.deleteQqchTaxGlobalFormulaByPks(ids);
        }
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

    @Override
    public Map<String, Object> getPrjInfo(QqchTaxGlobalFormula param) {
        HashMap<String, Object> res = new HashMap<>();
        res.put("yearList", taxInService.getYearList());


        ProjectBasicInfo projectBasicInfo = projectBasicInfoService.projectInfo();

        TaxInVO.CurrencyVO currencyVO = new TaxInVO.CurrencyVO();
        currencyVO.setCurrency(projectBasicInfo.getLocalCurrencyCode());
        Map<String, String> currencyNamesByCodes = CommonServiceUtil.getCurrencyNamesByCodes(Collections.singletonList(projectBasicInfo.getLocalCurrencyCode()));
        currencyVO.setCurrencyName(currencyNamesByCodes.get(projectBasicInfo.getLocalCurrencyCode()));

        ArrayList<String> currencyList = new ArrayList<>();
        String localCurrencyCode = projectBasicInfo.getLocalCurrencyCode();
        currencyList.add(localCurrencyCode);


        currencyList.add(PmConstant.CNY);
        Map<String, BigDecimal> usdRate = CommonServiceUtil.getUsdRate(currencyList);
        BigDecimal currencyRate = usdRate.get(localCurrencyCode);
        BigDecimal cnyRate = usdRate.get(PmConstant.CNY);

        currencyVO.setRate(currencyRate);
        currencyVO.setCnyRate(cnyRate);

        res.put("currencyInfo", currencyVO);


        return res;
    }


    private ProjectBasicInfo getPrj() {
        ProjectBasicInfo projectBasicInfo = null;
        try {
            projectBasicInfo = projectBasicInfoService.projectInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return projectBasicInfo == null ? new ProjectBasicInfo() : projectBasicInfo;
    }

    private XmslContractInfo getCont() {
        XmslContractInfo xmslContractInfo = new XmslContractInfo();
        try {
//            validMaxVersionContractInfo = contractInfoService.getValidMaxVersionContractInfo();
            xmslContractInfo = contractInfoService.getXmslContractInfo(xmslContractInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return xmslContractInfo == null ? new XmslContractInfo() : xmslContractInfo;
    }


    private BigDecimal getRateByCurrency(String currency) {
        BigDecimal returnBig = BigDecimal.ZERO;
        List<String> currencyList = Arrays.asList(currency);
        Map<String, BigDecimal> usdRate = CommonServiceUtil.getUsdRate(currencyList);
        if(usdRate != null) {
            returnBig = usdRate.get(currency);
        }
        return returnBig;
    }


    private BigDecimal getRateByCurrency(String currency, Date date) {
        // TODO

        return BigDecimal.ZERO;
    }

}
