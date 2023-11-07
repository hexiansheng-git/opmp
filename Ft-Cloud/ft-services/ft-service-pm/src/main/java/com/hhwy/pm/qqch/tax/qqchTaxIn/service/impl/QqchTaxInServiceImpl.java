package com.hhwy.pm.qqch.tax.qqchTaxIn.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.domain.base.system.period.PeriodInfo;
import com.hhwy.feign.service.SystemServiceApi;
import com.hhwy.pm.common.service.CommonServiceUtil;
import com.hhwy.pm.constant.PmConstant;
import com.hhwy.pm.qqch.common.aspect.CompileAspect;
import com.hhwy.pm.qqch.common.aspect.CompileOptEnum;
import com.hhwy.pm.qqch.common.domain.CompileEntity;
import com.hhwy.pm.qqch.sgch.mainpl.domain.QqchMainPlanItem;
import com.hhwy.pm.qqch.sgch.mainpl.service.IQqchMainPlanItemService;
import com.hhwy.pm.qqch.sgch.prodplan.domain.QqchProdPlan;
import com.hhwy.pm.qqch.sgch.prodplan.service.IQqchProdPlanService;
import com.hhwy.pm.qqch.tax.qqchTaxIn.domain.QqchTaxIn;
import com.hhwy.pm.qqch.tax.qqchTaxIn.domain.QqchTaxInDetail;
import com.hhwy.pm.qqch.tax.qqchTaxIn.domain.vo.TaxInVO;
import com.hhwy.pm.qqch.tax.qqchTaxIn.mapper.QqchTaxInMapper;
import com.hhwy.pm.qqch.tax.qqchTaxIn.service.IQqchTaxInDetailService;
import com.hhwy.pm.qqch.tax.qqchTaxIn.service.IQqchTaxInService;
import com.hhwy.pm.qqch.tax.qqchTaxInstallment.service.IQqchTaxStageService;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractInfo;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractPayinfo;
import com.hhwy.pm.xmsl.contractInfo.service.IXmslContractInfoService;
import com.hhwy.pm.xmsl.contractInfo.service.IXmslContractPayinfoService;
import com.hhwy.utils.EntityUtils;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.common.CommonAssert;
import com.hhwy.utils.common.PmsUtils;
import com.hhwy.utils.date.FtDateUtils;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.tree.ListTreeUtil;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author mls
 * @date 2023-08-09 18:17:32
 * @remark
 */
@Log
@Service
public class QqchTaxInServiceImpl implements IQqchTaxInService {

    private final static String TN = "qqch_tax_in";

    @Autowired
    private IXmslContractPayinfoService contractPayinfoService;

    @Autowired
    private QqchTaxInMapper qqchTaxInMapper;

    @Autowired
    private IQqchTaxInDetailService detailService;

    @Resource
    private IQqchTaxStageService qqchTaxStageService;
    @Resource
    private IQqchMainPlanItemService qqchMainPlanItemService;
    @Resource
    private IQqchProdPlanService qqchProdPlanService;
    @Resource
    private IXmslContractInfoService contractInfoService;
    @Resource
    private SystemServiceApi systemServiceApi;

    public QqchTaxIn getQqchTaxIn(QqchTaxIn qqchTaxIn) {
        return qqchTaxInMapper.getQqchTaxIn(qqchTaxIn);
    }

    public List<QqchTaxIn> getQqchTaxInList(QqchTaxIn qqchTaxIn) {
        return qqchTaxInMapper.getQqchTaxInList(qqchTaxIn);
    }

    @Transactional(rollbackFor = Exception.class)
    public int insertQqchTaxIn(QqchTaxIn qqchTaxIn) {
        qqchTaxIn.setId(IdWorker.createId());
        qqchTaxIn.setCreateUser(SecurityUtils.getUserName());
        qqchTaxIn.setCreateTime(DateUtils.getNowDate());
        return qqchTaxInMapper.insertQqchTaxIn(qqchTaxIn);
    }

    @Transactional(rollbackFor = Exception.class)
    public int insertQqchTaxInList(List<QqchTaxIn> qqchTaxInList) {
        for (QqchTaxIn qqchTaxIn : qqchTaxInList) {
            qqchTaxIn.setId(IdWorker.createId());
            qqchTaxIn.setCreateUser(SecurityUtils.getUserName());
            qqchTaxIn.setCreateTime(DateUtils.getNowDate());
        }
        return qqchTaxInMapper.insertQqchTaxInList(qqchTaxInList);
    }

    @Transactional(rollbackFor = Exception.class)
    public int updateQqchTaxIn(QqchTaxIn qqchTaxIn) {
        qqchTaxIn.setUpdateUser(SecurityUtils.getUserName());
        qqchTaxIn.setUpdateTime(DateUtils.getNowDate());
        return qqchTaxInMapper.updateQqchTaxIn(qqchTaxIn);
    }

    @Transactional(rollbackFor = Exception.class)
    public int updateQqchTaxInList(List<QqchTaxIn> qqchTaxInList) {
        for (QqchTaxIn qqchTaxIn : qqchTaxInList) {
            qqchTaxIn.setUpdateUser(SecurityUtils.getUserName());
            qqchTaxIn.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchTaxInMapper.updateQqchTaxInList(qqchTaxInList);
    }

    @Transactional(rollbackFor = Exception.class)
    public int deleteQqchTaxIn(QqchTaxIn qqchTaxIn) {
        qqchTaxIn.setUpdateUser(SecurityUtils.getUserName());
        qqchTaxIn.setUpdateTime(DateUtils.getNowDate());
        return qqchTaxInMapper.deleteQqchTaxIn(qqchTaxIn);
    }

    @Transactional(rollbackFor = Exception.class)
    public int deleteQqchTaxInByPks(List<Long> qqchTaxInPkList) {
        return qqchTaxInMapper.deleteQqchTaxInByPks(qqchTaxInPkList);
    }

    @Override
    @CompileAspect(type = CompileOptEnum.LIST, tableName = TN)
    public CompileEntity<TaxInVO> list(QqchTaxIn qqchTaxInParam) {
        CompileEntity<TaxInVO> entity = new CompileEntity<>();

        TaxInVO taxInVO = new TaxInVO();
        taxInVO.setYearList(this.getYearList());
        List<TaxInVO.CurrencyVO> currencyInfo = this.getCurrencyInfo();
        taxInVO.setCurrencyVOList(currencyInfo);

        // 先查询主收入
        qqchTaxInParam.setDataType("1");
        List<QqchTaxIn> inList = this.getInList(qqchTaxInParam);
//        if (CollectionUtils.isEmpty(inList)) {
//            inList = this.getDefaultAmtInfo(qqchTaxInParam.getVersion(),currencyInfo);
//        }
        taxInVO.setInList(inList);

        // 再查询其他收入
        qqchTaxInParam.setDataType("2");
        List<QqchTaxIn> other = this.getInList(qqchTaxInParam);
        taxInVO.setOtherList(other);

        entity.setModuleIdentity(qqchTaxInParam.getModuleIdentity());
        entity.setVersion(qqchTaxInParam.getVersion());
        entity.setDto(taxInVO);
        return entity;
    }

    /**
     * 同步主营业务收入数据
     * @param version
     * @return
     */
    @Override
    public List<QqchTaxIn> syncMajorIn(BigDecimal version) {
        return this.getDefaultAmtInfo(version);
    }

    public List<QqchTaxIn> getDefaultAmtInfo(BigDecimal version){
        //获取1.2.5的产值（最新有效版本），币种为合同的清单标价货币。格式化为10.3.3的明细
        List<QqchProdPlan> list = qqchProdPlanService.getValidList();
        //汇总每年的产值
        Map<String,BigDecimal> yearAmtMap = new HashMap<>();
        for (QqchProdPlan temp : list) {
            Calendar tempCalendar = Calendar.getInstance();
            tempCalendar.setTime(temp.getPlanDate());
            Integer year = tempCalendar.get(Calendar.YEAR);
            ObjectUtils.add2Map(yearAmtMap, String.valueOf(year), temp.getMonthProdValue());
        }
        //获取清单标价货币的汇率
        BigDecimal listRate = getListCurrencyRate();
        //构建数据
        List<QqchTaxIn> inList = new ArrayList<>();
        List<TaxInVO.CurrencyVO> currencyInfo = this.getCurrencyInfo();
        for (TaxInVO.CurrencyVO currencyVO : currencyInfo) {
            QqchTaxIn qqchTaxIn = new QqchTaxIn();
            qqchTaxIn.setId(IdWorker.createId());
            qqchTaxIn.setOtherBusName(currencyVO.getCurrencyName());
            qqchTaxIn.setCurrency(currencyVO.getCurrency());
            qqchTaxIn.setCurrencyName(currencyVO.getCurrencyName());
            qqchTaxIn.setRate(currencyVO.getRate());
            List<QqchTaxInDetail> detailList = this.getDetialList(qqchTaxIn,yearAmtMap,listRate);
            qqchTaxIn.setDetailList(detailList);
            inList.add(qqchTaxIn);
        }
        return inList;
    }

    private List<QqchTaxInDetail> getDetialList(QqchTaxIn taxIn,Map<String,BigDecimal> amtMap,BigDecimal listRate) {
        List<String> yearList = this.getYearList();

        return yearList.stream().map(item -> {
            BigDecimal sourceAmt = ObjectUtils.nvlBigDecimal(amtMap.get(item));
            BigDecimal amt = PmsUtils.amountTransfer(sourceAmt,listRate,taxIn.getRate());
            BigDecimal usdAmt = PmsUtils.amountTransferUSD(amt,listRate,taxIn.getCurrency());
            QqchTaxInDetail qqchTaxInDetail = new QqchTaxInDetail();
            qqchTaxInDetail.setId(IdWorker.createId());
            qqchTaxInDetail.setYear(item);
            qqchTaxInDetail.setDataType("1");
            qqchTaxInDetail.setCurrency(taxIn.getCurrency());
            qqchTaxInDetail.setRate(taxIn.getRate());
            qqchTaxInDetail.setAmt(amt);
            qqchTaxInDetail.setUsdAmt(usdAmt);
            return qqchTaxInDetail;
        }).collect(Collectors.toList());
    }

    /**
     * 从清单里获取清单标价货币的汇率
     * 优先从支付信息里拿，没有再取实时汇率
     */
    public BigDecimal getListCurrencyRate(){
        XmslContractInfo contractInfo = contractInfoService.getValidMaxVersionContractInfo();
        String listCurrencyCode = contractInfo.getListCurrencyCode();
        if(StringUtils.isBlank(listCurrencyCode))
            return BigDecimal.ONE;
        /*汇率*/
        BigDecimal exchangeRate = null;
        //项目支付信息数据
        XmslContractPayinfo xmslContractPayinfo = new XmslContractPayinfo();
        xmslContractPayinfo.setMasterId(contractInfo.getId());
        List<XmslContractPayinfo> payinfoList = contractPayinfoService.getXmslContractPayinfoList(xmslContractPayinfo);
        if(!CollectionUtils.isEmpty(payinfoList)) {
            XmslContractPayinfo payInfo = payinfoList.stream().filter(o -> listCurrencyCode.equals(o.getCurrencyCode())).findFirst().orElse(null);
            if(payInfo != null){
                String rateType = payInfo.getRateType();
                if("1".equals(rateType)){
                    exchangeRate = new BigDecimal(payInfo.getObversionRate());
                }
            }
        }
        if(exchangeRate == null){ //取实时汇率
            Date nowDate = DateUtils.getNowDate();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
            String nowStr = sdf.format(nowDate);

            PeriodInfo periodInfo = new PeriodInfo();
            periodInfo.setCurrencyCode(listCurrencyCode);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(nowDate);
            int year = calendar.get(Calendar.YEAR);
            periodInfo.setQueryDate(String.valueOf(year));
            AjaxResult ajaxResult = systemServiceApi.selectPeriodByYear(periodInfo);
            if(ajaxResult.get("data") != null) {
                List<Map> data = (List<Map>) ajaxResult.get("data");
                Map periodMap = data.stream().filter(map -> nowStr.equals(map.get("periodCode"))).findFirst().orElse(null);
                if(periodMap != null && periodMap.get("rate") != null) {
                    exchangeRate = ObjectUtils.nvlBigDecimal(periodMap.get("rate"));
                }
            }
        }
        return ObjectUtils.nvlBigDecimal(exchangeRate,BigDecimal.ONE);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(CompileEntity<TaxInVO> qqchTaxInParam) {
        BigDecimal version = qqchTaxInParam.getVersion();

        List<QqchTaxIn> allTaxInList = new ArrayList<>();

        // 分期会用到
        Long recordId = IdWorker.createId();
        this.qqchTaxStageService.saveStage(recordId, "1");

        // 保存主要
        List<QqchTaxIn> inList = qqchTaxInParam.getDto().getInList();
        List<TaxInVO.CurrencyVO> currencyInfo = this.getCurrencyInfo();
        if(!CollectionUtils.isEmpty(inList)){
            List<QqchTaxIn> qqchTaxIns = CompileEntity.dealSaveDto(qqchTaxInParam, inList);
            for (QqchTaxIn qqchTaxIn : qqchTaxIns) {
                qqchTaxIn.setDataType("1");
                qqchTaxIn.setRecordId(recordId);
                String currency = qqchTaxIn.getCurrency();
                CommonAssert.notBlank(currency, "币种编码不能为空");
                currencyInfo.stream().filter(i -> currency.equals(i.getCurrency())).findFirst().ifPresent(item -> {
                    BigDecimal rate = item.getRate();
                    qqchTaxIn.setRate(rate);
                });

                List<QqchTaxInDetail> detailList = qqchTaxIn.getDetailList();
                if (!CollectionUtils.isEmpty(detailList)) {
                    for (QqchTaxInDetail qqchTaxInDetail : detailList) {
                        qqchTaxInDetail.setCurrency(currency);
                    }
                }
                allTaxInList.add(qqchTaxIn);
            }
        }

        // 保存其他
        List<QqchTaxIn> otherList = qqchTaxInParam.getDto().getOtherList();
        if (!CollectionUtils.isEmpty(otherList)) {
            List<QqchTaxIn> otherInList = CompileEntity.dealSaveDto(qqchTaxInParam, otherList);
            for (QqchTaxIn qqchTaxIn : otherInList) {
                qqchTaxIn.setDataType("2");
                qqchTaxIn.setRecordId(recordId);
                allTaxInList.add(qqchTaxIn);
            }
        }

        // 所有的详情
        List<QqchTaxInDetail> allDetails = this.saveInList(allTaxInList,version);
        // 新增年份数据
        if(!CollectionUtils.isEmpty(allDetails)){
            this.detailService.save(CompileEntity.dealSaveDto(qqchTaxInParam, allDetails));
        }
    }


    @CompileAspect(type = CompileOptEnum.SAVE_LIST, tableName = TN)
    public List<QqchTaxInDetail> saveInList(List<QqchTaxIn> list,BigDecimal version) {
        List<QqchTaxInDetail> allDetails = new ArrayList<>();
        if(CollectionUtils.isEmpty(list)){
            return allDetails;
        }
        for (QqchTaxIn item : list) {
            List<QqchTaxInDetail> detailList = item.getDetailList();
            // 不为空才循环
            if (!CollectionUtils.isEmpty(detailList)) {
                String currency = item.getCurrency();
                currency = StringUtils.isEmpty(currency) ? PmConstant.USD : currency;
                for (QqchTaxInDetail detail : detailList) {
                    detail.setId(IdWorker.createId());
                    // 设置币种
                    detail.setCurrency(currency);
                    // 设置汇率
                    detail.setRate(item.getRate());
                    detail.setMasterId(item.getId());
                    detail.setDataType(item.getDataType());
                    detail.setVersion(item.getVersion());
                    detail.setValid(item.getValid());
                    detail.setVersion(item.getVersion());
                    // 价格转换
                    detail.setUsdAmt(CommonServiceUtil.getUsdAmt(detail.getAmt(), detail.getRate()));

                }
                allDetails.addAll(detailList);
            }

        }

        EntityUtils.setCreateUpdateInfo(list);
        EntityUtils.setCreateUpdateInfo(allDetails);
        // 新增数据
        QqchTaxIn delQuery = new QqchTaxIn();
        delQuery.setVersion(version);
        qqchTaxInMapper.deleteQqchTaxIn(delQuery);

        this.qqchTaxInMapper.insertQqchTaxInList(list);

        return allDetails;
    }


    /**
     * TODO 获取币种信息
     *
     * @return
     */
    public List<TaxInVO.CurrencyVO> getCurrencyInfo(String currencyCodes) {
        XmslContractPayinfo xmslContractPayinfo = new XmslContractPayinfo();
        xmslContractPayinfo.setCurrencyCodes(StringUtils.isEmpty(currencyCodes) ? null : currencyCodes.split(","));
        List<XmslContractPayinfo> payInfo = contractPayinfoService.getPayInfo(xmslContractPayinfo);
        payInfo = payInfo.stream().filter(Objects::nonNull).collect(Collectors.toList());
        List<TaxInVO.CurrencyVO> res = payInfo.stream().map(item -> {
            TaxInVO.CurrencyVO currencyVO = new TaxInVO.CurrencyVO();
            currencyVO.setCurrency(item.getCurrencyCode());
            currencyVO.setCurrencyName(item.getCurrencyName());
            currencyVO.setRate(getRate(item.getObversionRate()));
            return currencyVO;
        }).collect(Collectors.toList());
        // 假数据
        if (CollectionUtils.isEmpty(res)) {
            res = new ArrayList<>();
            res.add(new TaxInVO.CurrencyVO("BIRR", "比尔", new BigDecimal("6.9")));
            res.add(new TaxInVO.CurrencyVO("CFA", "中非法郎", new BigDecimal("1.1")));
        }
        return res;
    }


    public BigDecimal getRate(String s) {
        try {
            return new BigDecimal(s);
        } catch (Exception e) {
            e.getMessage();
            return BigDecimal.ZERO;
        }
    }


    public List<TaxInVO.CurrencyVO> getCurrencyInfo() {
        return this.getCurrencyInfo(null);
    }

    /**
     * 获取年份信息
     *
     * @return
     */
    public List<String> getYearList() {
        ArrayList<String> res = new ArrayList<>();

        try {
            QqchMainPlanItem item = qqchMainPlanItemService.getProjStartAndFinish();
            if(item == null || item.getStartDate()==null || item.getFinishDate() ==null)
                return new ArrayList<>();
            List<Date> dateList = FtDateUtils.getYearList(item.getStartDate(), item.getFinishDate());

            // 获取p6的计划开始时间和结束时间
            res = new ArrayList<>();
            for (Date date : dateList) {
                res.add(FtDateUtils.getYear(date) + "");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("10.3,ERROR:获取项目开始、结束日期异常");
            res.add("2023");
            res.add("2024");
            res.add("2025");
        }
        return res;
    }

    /**
     * 获取收入信息
     *
     * @param qqchTaxIn
     * @return
     */
    @Override
//    @CompileAspect(type = CompileOptEnum.TREE, tableName = TN)
    public List<QqchTaxIn> getInList(QqchTaxIn qqchTaxIn) {
        List<QqchTaxIn> qqchTaxInList = this.qqchTaxInMapper.getQqchTaxInList(qqchTaxIn);
        if (CollectionUtils.isEmpty(qqchTaxInList)){
            return new ArrayList<>();
        }
        List<Long> collect = qqchTaxInList.stream().map(QqchTaxIn::getId).collect(Collectors.toList());

        // 查询详情
        QqchTaxInDetail where = new QqchTaxInDetail();
        where.setMasterIdList(collect);
        List<QqchTaxInDetail> qqchTaxInDetailList = this.detailService.getQqchTaxInDetailList(where);

        // 分组
        Map<Long, List<QqchTaxInDetail>> idMap = qqchTaxInDetailList.stream().collect(Collectors.groupingBy(QqchTaxInDetail::getMasterId));

        // 挂到主数据上面
        for (QqchTaxIn taxIn : qqchTaxInList) {
            List<QqchTaxInDetail> qqchTaxInDetails = idMap.get(taxIn.getId());
            if(qqchTaxInDetails == null){
                qqchTaxInDetails = new ArrayList<>();
            }
            taxIn.setDetailList(qqchTaxInDetails);
        }

        String dataType = qqchTaxIn.getDataType();
        if("2".equals(dataType)){
            //转树列表
            qqchTaxInList = ListTreeUtil.formatTree(
                    qqchTaxInList,
                    o -> o.getPid() == null,
                    (r, n) -> r.getId().equals(n.getPid()),
                    QqchTaxIn::getChildren,
                    QqchTaxIn::setChildren);
        }

        return qqchTaxInList;
    }
}




