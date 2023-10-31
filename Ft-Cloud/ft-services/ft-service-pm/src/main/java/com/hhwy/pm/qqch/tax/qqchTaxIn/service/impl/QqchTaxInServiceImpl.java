package com.hhwy.pm.qqch.tax.qqchTaxIn.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.SpringUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.common.service.CommonServiceUtil;
import com.hhwy.pm.constant.PmConstant;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.domain.JdglMainPlanItem;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.service.IJdglData4P6Service;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.service.IJdglMainPlanItemService;
import com.hhwy.pm.qqch.common.aspect.CompileAspect;
import com.hhwy.pm.qqch.common.aspect.CompileOptEnum;
import com.hhwy.pm.qqch.common.domain.CompileEntity;
import com.hhwy.pm.qqch.tax.qqchTaxIn.domain.QqchTaxIn;
import com.hhwy.pm.qqch.tax.qqchTaxIn.domain.QqchTaxInDetail;
import com.hhwy.pm.qqch.tax.qqchTaxIn.mapper.QqchTaxInMapper;
import com.hhwy.pm.qqch.tax.qqchTaxIn.service.IQqchTaxInDetailService;
import com.hhwy.pm.qqch.tax.qqchTaxIn.service.IQqchTaxInService;
import com.hhwy.pm.qqch.tax.qqchTaxIn.vo.TaxInVO;
import com.hhwy.pm.qqch.tax.qqchTaxInstallment.service.IQqchTaxStageService;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractPayinfo;
import com.hhwy.pm.xmsl.contractInfo.service.IXmslContractPayinfoService;
import com.hhwy.pm.xmsl.project.service.IXmslProjectBasicInfoService;
import com.hhwy.utils.EntityUtils;
import com.hhwy.utils.common.CommonAssert;
import com.hhwy.utils.date.FtDateUtils;
import com.hhwy.utils.idworker.IdWorker;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
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

    @Resource
    private IXmslProjectBasicInfoService projectBasicInfoService;
    @Resource
    private IJdglData4P6Service jdglData4P6Service;

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
    private IJdglMainPlanItemService jdglMainPlanItemService;

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
        IQqchTaxInService bean = SpringUtils.getBean(IQqchTaxInService.class);

        CompileEntity entity = new CompileEntity();

        TaxInVO taxInVO = new TaxInVO();
        taxInVO.setYearList(this.getYearList());
        List<TaxInVO.CurrencyVO> currencyInfo = this.getCurrencyInfo();
        taxInVO.setCurrencyVOList(currencyInfo);
        // 先查询主收入
        qqchTaxInParam.setDataType("1");
        List<QqchTaxIn> inList = bean.getInList(qqchTaxInParam);
        if (CollectionUtils.isEmpty(inList)) {
            List<QqchTaxInDetail> detailList = this.getDetialList();


            inList = new ArrayList<>();
            for (TaxInVO.CurrencyVO currencyVO : currencyInfo) {
                QqchTaxIn qqchTaxIn = new QqchTaxIn();
                qqchTaxIn.setId(IdWorker.createId());
                qqchTaxIn.setOtherBusName(currencyVO.getCurrencyName());
                qqchTaxIn.setCurrency(currencyVO.getCurrency());
                qqchTaxIn.setCurrencyName(currencyVO.getCurrencyName());
                qqchTaxIn.setRate(currencyVO.getRate());
                qqchTaxIn.setDetailList(detailList);
                inList.add(qqchTaxIn);
            }
        }

        taxInVO.setInList(inList);
        // 在查询其他收入
        qqchTaxInParam.setDataType("2");
        List<QqchTaxIn> other = bean.getInList(qqchTaxInParam);
        taxInVO.setOtherList(other);

        entity.setModuleIdentity(qqchTaxInParam.getModuleIdentity());
        entity.setVersion(qqchTaxInParam.getVersion());
        entity.setDto(taxInVO);
        return entity;
    }

    private List<QqchTaxInDetail> getDetialList() {
        List<String> yearList = this.getYearList();

        return yearList.stream().map(item -> {
            QqchTaxInDetail qqchTaxInDetail = new QqchTaxInDetail();
            qqchTaxInDetail.setId(IdWorker.createId());
            qqchTaxInDetail.setYear(item);
            return qqchTaxInDetail;
        }).collect(Collectors.toList());

    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(CompileEntity<TaxInVO> qqchTaxInParam) {

        IQqchTaxInService bean = SpringUtils.getBean(IQqchTaxInService.class);

        List<QqchTaxIn> allTaxInList = new ArrayList<>();

        // 分期会用到
        Long recordId = IdWorker.createId();
        this.qqchTaxStageService.saveStage(recordId, "1");

        // 保存主要
        List<QqchTaxIn> inList = qqchTaxInParam.getDto().getInList();
        List<TaxInVO.CurrencyVO> currencyInfo = this.getCurrencyInfo();
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
        List<QqchTaxInDetail> allDetails = bean.saveInList(allTaxInList);
        // 新增年份数据
        this.detailService.save(CompileEntity.dealSaveDto(qqchTaxInParam, allDetails));

    }


    @CompileAspect(type = CompileOptEnum.SAVE_LIST, tableName = TN)
    @Override
    public List<QqchTaxInDetail> saveInList(List<QqchTaxIn> list) {
        List<QqchTaxInDetail> allDetails = new ArrayList<>();

//        List<TaxInVO.CurrencyVO> currencyInfo = this.getCurrencyInfo();

//        Map<String, BigDecimal> currencyRateMap = currencyInfo.stream()
//                .filter(ite -> StringUtils.isNotEmpty(ite.getCurrency()))
//                .collect(Collectors.toMap(TaxInVO.CurrencyVO::getCurrency, TaxInVO.CurrencyVO::getRate, (v1, v2) -> v1));
        for (QqchTaxIn item : list) {
            List<QqchTaxInDetail> detailList = item.getDetailList();
            // 不为空才循环
            if (!CollectionUtils.isEmpty(detailList)) {
                String currency = item.getCurrency();
                currency = StringUtils.isEmpty(currency) ? PmConstant.USD : currency;
//                item.setRate(currencyRateMap.get(currency));
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
            JdglMainPlanItem item = jdglMainPlanItemService.getProjStartAndFinish();
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
    @CompileAspect(type = CompileOptEnum.TREE, tableName = TN)
    public List<QqchTaxIn> getInList(QqchTaxIn qqchTaxIn) {
        List<QqchTaxIn> qqchTaxInList = this.qqchTaxInMapper.getQqchTaxInList(qqchTaxIn);
        if (CollectionUtils.isEmpty(qqchTaxInList)) return new ArrayList<>();
        List<Long> collect = qqchTaxInList.stream().map(QqchTaxIn::getId).collect(Collectors.toList());

        // 查询详情
        QqchTaxInDetail where = new QqchTaxInDetail();
        where.setMasterIdList(collect);
        List<QqchTaxInDetail> qqchTaxInDetailList = this.detailService.getQqchTaxInDetailList(where);

        // 分组
        Map<Long, List<QqchTaxInDetail>> idMap = qqchTaxInDetailList.stream().collect(Collectors.groupingBy(QqchTaxInDetail::getMasterId));

        // 挂到主数据上面
        for (QqchTaxIn taxIn : qqchTaxInList) {
            taxIn.setDetailList(idMap.get(taxIn.getId()));
        }
        return qqchTaxInList;
    }

//
//    @Override
//    @CompileAspect(type = CompileOptEnum.TREE, tableName = TN)
//    public List<QqchTaxIn> getInList(QqchTaxIn qqchTaxIn) {
//        List<QqchTaxIn> qqchTaxInList = this.qqchTaxInMapper.getQqchTaxInList(qqchTaxIn);
//        List<QqchTaxIn> currencyChildren = this.getCurrencyChildren();
//        if (CollectionUtils.isEmpty(qqchTaxInList)) {
//            if (PmConstant.ONE.equals(qqchTaxIn.getDataType())) {
//                qqchTaxInList = currencyChildren;
//            } else {
//                InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("template/10_3_4_1.json");
//                String json = "";
//                try {
//                    json = IOUtils.toString(resourceAsStream, StandardCharsets.UTF_8);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                qqchTaxInList = JSONObject.parseArray(json, QqchTaxIn.class);
//                qqchTaxInList.stream().filter(ite -> PmConstant.ONE.equals(ite.getLeaf())).forEach(i -> {
//                    i.setChildren(currencyChildren);
//                });
//            }
//        }
//        List<Long> collect = qqchTaxInList.stream().map(QqchTaxIn::getId).collect(Collectors.toList());
//
//        // 查询详情
//        QqchTaxInDetail where = new QqchTaxInDetail();
//        where.setMasterIdList(collect);
//        List<QqchTaxInDetail> qqchTaxInDetailList = this.detailService.getQqchTaxInDetailList(where);
//
//        // 分组
//        Map<Long, List<QqchTaxInDetail>> idMap = qqchTaxInDetailList.stream().collect(Collectors.groupingBy(QqchTaxInDetail::getMasterId));
//
//        // 挂到主数据上面
//        for (QqchTaxIn taxIn : qqchTaxInList) {
//            taxIn.setDetailList(idMap.get(taxIn.getId()));
//        }
//        return qqchTaxInList;
//    }


}




