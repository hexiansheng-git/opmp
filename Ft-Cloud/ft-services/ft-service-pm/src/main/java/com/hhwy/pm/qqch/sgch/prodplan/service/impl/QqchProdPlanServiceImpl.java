package com.hhwy.pm.qqch.sgch.prodplan.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.jdgl.statistics.util.StatisticsUtils;
import com.hhwy.pm.qqch.common.aspect.CompileAspect;
import com.hhwy.pm.qqch.common.aspect.CompileOptEnum;
import com.hhwy.pm.qqch.common.domain.CompileEntity;
import com.hhwy.pm.qqch.sgch.mainpl.domain.QqchMainPlanItem;
import com.hhwy.pm.qqch.sgch.mainpl.service.IQqchMainPlanItemService;
import com.hhwy.pm.qqch.sgch.prodplan.domain.QqchProdPlan;
import com.hhwy.pm.qqch.sgch.prodplan.mapper.QqchProdPlanMapper;
import com.hhwy.pm.qqch.sgch.prodplan.service.IQqchProdPlanService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractInfo;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractList;
import com.hhwy.pm.xmsl.contractInfo.service.IXmslContractInfoService;
import com.hhwy.pm.xmsl.contractInfo.service.IXmslContractListService;
import com.hhwy.pm.xmsl.drawReview.domain.XmslDrawReviewList;
import com.hhwy.pm.xmsl.drawReview.service.IXmslDrawReviewListService;
import com.hhwy.utils.date.FtDateUtils;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author mls
 * @date 2023-08-17 16:20:08
 * @remark
 */
@Service
public class QqchProdPlanServiceImpl implements IQqchProdPlanService {

    @Autowired
    private QqchProdPlanMapper qqchProdPlanMapper;

    @Autowired
    private IQqchMainPlanItemService qqchMainPlanItemService;

    @Autowired
    private IXmslDrawReviewListService drawReviewListService;

    @Autowired
    private IXmslContractListService xmslContractListService;

    @Autowired
    private IXmslContractInfoService xmslContractInfoService;


    private final static String TN = "qqch_prod_plan";


    public QqchProdPlan getQqchProdPlan(QqchProdPlan qqchProdPlan) {
        return qqchProdPlanMapper.getQqchProdPlan(qqchProdPlan);
    }

    public List<QqchProdPlan> getQqchProdPlanList(QqchProdPlan qqchProdPlan) {
        return qqchProdPlanMapper.getQqchProdPlanList(qqchProdPlan);
    }

    @Override
    public List<QqchProdPlan> getValidList() {
        BigDecimal version = VersionUtil.getVersion(TN, null);
        QqchProdPlan qqchProdPlan = new QqchProdPlan();
        qqchProdPlan.setVersion(version);
        return qqchProdPlanMapper.getQqchProdPlanList(qqchProdPlan);
    }

    @Transactional
    public int insertQqchProdPlan(QqchProdPlan qqchProdPlan) {
        qqchProdPlan.setId(IdWorker.createId());
        qqchProdPlan.setCreateUser(SecurityUtils.getUserName());
        qqchProdPlan.setCreateTime(DateUtils.getNowDate());
        return qqchProdPlanMapper.insertQqchProdPlan(qqchProdPlan);
    }

    @Transactional
    public int insertQqchProdPlanList(List<QqchProdPlan> qqchProdPlanList) {
        for (QqchProdPlan qqchProdPlan : qqchProdPlanList) {
            qqchProdPlan.setId(IdWorker.createId());
//            qqchProdPlan.setCreateUser(SecurityUtils.getUserName());
            qqchProdPlan.setCreateTime(DateUtils.getNowDate());
        }
        return qqchProdPlanMapper.insertQqchProdPlanList(qqchProdPlanList);
    }

    @Transactional
    public int updateQqchProdPlan(QqchProdPlan qqchProdPlan) {
        qqchProdPlan.setUpdateUser(SecurityUtils.getUserName());
        qqchProdPlan.setUpdateTime(DateUtils.getNowDate());
        return qqchProdPlanMapper.updateQqchProdPlan(qqchProdPlan);
    }

    @Transactional
    public int updateQqchProdPlanList(List<QqchProdPlan> qqchProdPlanList) {
        for (QqchProdPlan qqchProdPlan : qqchProdPlanList) {
            qqchProdPlan.setUpdateUser(SecurityUtils.getUserName());
            qqchProdPlan.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchProdPlanMapper.updateQqchProdPlanList(qqchProdPlanList);
    }

    @Transactional
    public int deleteQqchProdPlan(QqchProdPlan qqchProdPlan) {
        qqchProdPlan.setUpdateUser(SecurityUtils.getUserName());
        qqchProdPlan.setUpdateTime(DateUtils.getNowDate());
        return qqchProdPlanMapper.deleteQqchProdPlan(qqchProdPlan);
    }

    @Transactional
    public int deleteQqchProdPlanByPks(List<Long> qqchProdPlanPkList) {
        return qqchProdPlanMapper.deleteQqchProdPlanByPks(qqchProdPlanPkList);
    }

    @Override
    public CompileEntity<List<QqchProdPlan>> getList(QqchProdPlan dto) {
//        Date[] time = this.getTime();
//        HashMap<Date, List<String>> wbsList = getWbsList(time[0], time[1]);

        return null;
    }

    @Override
    @CompileAspect(type = CompileOptEnum.SAVE_LIST, tableName = TN)
    public void save(List<QqchProdPlan> dto) {
        this.qqchProdPlanMapper.insertQqchProdPlanList(dto);
    }

    @Override
    @CompileAspect(type = CompileOptEnum.LIST,tableName = TN)
    public  CompileEntity<HashMap<String, Object>>  selectList(QqchProdPlan qqchProdPlanParam) {
        BigDecimal version = VersionUtil.getVersion(this.TN, qqchProdPlanParam.getVersion());
        qqchProdPlanParam.setVersion(version);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月");
        CompileEntity<HashMap<String, Object>> objectCompileEntity = new CompileEntity<>();
        HashMap<String, Object> res = new HashMap<>();
        List<QqchProdPlan> qqchProdPlanList = this.qqchProdPlanMapper.getQqchProdPlanList(qqchProdPlanParam);
        if(!CollectionUtils.isEmpty(qqchProdPlanList)) {
            qqchProdPlanList.stream().forEach(vo -> {
                if(vo.getFinishRatio() != null) {
                    vo.setFinishRatio(vo.getFinishRatio().multiply(new BigDecimal(100)));
                }
            });
        }
        res.put("list",qqchProdPlanList);
        res.put("xData",qqchProdPlanList.stream().map(QqchProdPlan::getPlanDate).map(sdf::format).collect(Collectors.toList()));
        res.put("yData",qqchProdPlanList.stream().map(QqchProdPlan::getFinishRatio).collect(Collectors.toList()));
        objectCompileEntity.setDto(res);
        return objectCompileEntity;
    }

    /**
     * 从p6总体计划推数据
     * @return
     */
    @Override
    public int putProdPlanData(BigDecimal version) {

        List<QqchProdPlan> qqchProdPlanList = new ArrayList<>();

        // 获取总体计划数据
        List<QqchMainPlanItem> qqchMainPlanItemList = qqchMainPlanItemService.getQqchMainPlanItemListNoTree(new QqchMainPlanItem());
        List<QqchMainPlanItem> workList = new ArrayList<>();
        if(CollectionUtils.isEmpty(qqchMainPlanItemList)) {
            return 0;
        } else {
            workList = qqchMainPlanItemList.stream().filter(vo -> QqchMainPlanItem.ITEMTYPE_ITEM.equals(vo.getItemType())).collect(Collectors.toList());
        }

        deleteQqchProdPlanByVersion(version);

        // 获取项目开始结束日期
        QqchMainPlanItem projStartAndFinish = qqchMainPlanItemService.getProjStartAndFinish(null);

        List<Date> dateList = new ArrayList<>();
        Date startDate = projStartAndFinish.getStartDate();
        Date finishDate = projStartAndFinish.getFinishDate();

        // 获取日期区间
        getTime(dateList, startDate, finishDate);

        if(CollectionUtils.isEmpty(dateList)) {
            return 0;
        }

        // 持续增加的总产值
        BigDecimal sumProdValue = BigDecimal.ZERO;

        // 获取合同信息
        XmslContractInfo contractInfo = xmslContractInfoService.getValidMaxVersionContractInfo();

        // 获取图纸复核数据
        List<XmslDrawReviewList> fullEffectList = drawReviewListService.getFullEffectList();

        // 获取合同清单数据
        List<XmslContractList> contractInventoryList = xmslContractListService.getValidMaxVersionContractInventoryList();

        for (Date date : dateList) {

            QqchProdPlan qqchProdPlan = new QqchProdPlan();

            Map<String, Date> dateRange4YearMonth = StatisticsUtils.getDateRange4YearMonth(date);

            Date start = dateRange4YearMonth.get("start");
            Date end = dateRange4YearMonth.get("end");

            qqchProdPlan.setPlanDate(date);
            qqchProdPlan.setVersion(version);

            // 计算月计划产值
            BigDecimal monthProdValue = BigDecimal.ZERO;

            List<QqchMainPlanItem> thisMonthWorkList = new ArrayList<>();

            // 获取当月作业数据
            for (QqchMainPlanItem qqchMainPlanItem : workList) {
                Date startDate1 = qqchMainPlanItem.getStartDate();
                Date finishDate1 = qqchMainPlanItem.getFinishDate();
                if(startDate1 == null || finishDate1 == null) {
                    continue;
                }
                if(startDate1.compareTo(start) <= 0 && finishDate1.compareTo(start) >= 0)  thisMonthWorkList.add(qqchMainPlanItem);
                if(startDate1.compareTo(start) >= 0 && finishDate1.compareTo(end) <= 0)  thisMonthWorkList.add(qqchMainPlanItem);
                if(startDate1.compareTo(end) <= 0 && finishDate1.compareTo(end) >= 0)  thisMonthWorkList.add(qqchMainPlanItem);

            }
            // 数据去重
            thisMonthWorkList = thisMonthWorkList.stream().distinct().collect(Collectors.toList());

            if(CollectionUtils.isEmpty(thisMonthWorkList)) {
                continue;
            }

            for (QqchMainPlanItem qqchMainPlanItem : thisMonthWorkList) {
                // 当前作业设计量
                BigDecimal quantity = qqchMainPlanItem.getQuantity();
                if(quantity == null) continue;;

                // 当前作业开始结束日期
                Date startDate1 = qqchMainPlanItem.getStartDate();
                Date finishDate1 = qqchMainPlanItem.getFinishDate();
                Integer totalDays = StatisticsUtils.getDaysByRangeDate(startDate1, finishDate1);
                if(startDate1 != null && startDate1.compareTo(start) <= 0 ) startDate1 = start;
                if(finishDate1 != null && finishDate1.compareTo(end) >= 0) finishDate1 = end;
                Integer thisMonthDays = StatisticsUtils.getDaysByRangeDate(startDate1, finishDate1);

                // 计算本月日期在当前作业开始结束日期当中占比
                BigDecimal rate = BigDecimal.ZERO;
                if (totalDays != 0) rate = new BigDecimal(thisMonthDays).divide(new BigDecimal(totalDays), 4, BigDecimal.ROUND_HALF_UP);
                // 计算本月作业计划完成量
                BigDecimal thisMonthCompQty = quantity.multiply(rate);
                // 获取作业对应wbs
                QqchMainPlanItem wbs = qqchMainPlanItemList.stream().filter(vo -> vo.getId().equals(qqchMainPlanItem.getPid())).findFirst().orElse(null);
                if(wbs != null && wbs.getQuantity() != null && wbs.getQuantity().compareTo(BigDecimal.ZERO) != 0) {
                    // 计算当前作业完成量与wbs设计量占比
                    rate = thisMonthCompQty.divide(wbs.getQuantity(), 4, BigDecimal.ROUND_HALF_UP);
                    String wbsCode = wbs.getItemCode();
                    if(!CollectionUtils.isEmpty(fullEffectList)) {

                        // 获取图纸复核中wbs对应的数据
                        List<XmslDrawReviewList> drawReviewLists = fullEffectList.stream().filter(vo -> wbsCode.equals(vo.getWbsCode())).collect(Collectors.toList());
                        if(!CollectionUtils.isEmpty(drawReviewLists)) {
                            for (XmslDrawReviewList xmslDrawReviewList : drawReviewLists) {
                                String listCode = xmslDrawReviewList.getListCode();

                                // 图纸复核中wbs清单复核量
                                BigDecimal checkNum = xmslDrawReviewList.getCheckNum();
                                BigDecimal price = BigDecimal.ZERO;

                                // 合同中对应清单的单价
                                if(!CollectionUtils.isEmpty(contractInventoryList)) {
                                    XmslContractList xmslContractList = contractInventoryList.stream().filter(vo -> listCode != null && listCode.equals(vo.getCode())).findFirst().orElse(null);
                                    if(xmslContractList != null) price = xmslContractList.getChangeUnitPrice() != null ? xmslContractList.getChangeUnitPrice() : xmslContractList.getWinUnitPrice();
                                }

                                // 根据完成占比*清单复核量*清单单价计算当前作业本月完成产值
                                if(rate != null && checkNum != null && price != null) {
                                    monthProdValue = monthProdValue.add(
                                            rate.multiply(checkNum).multiply(price)
                                    );
                                }
                            }
                        }
                    }
                }
            }

            qqchProdPlan.setMonthProdValue(monthProdValue);

            sumProdValue = sumProdValue.add(monthProdValue);

            qqchProdPlan.setSumProdValue(new BigDecimal(sumProdValue.floatValue()));

            if(contractInfo != null && contractInfo.getEffectiveAmout() != null && contractInfo.getEffectiveAmout().compareTo(BigDecimal.ZERO) != 0) {
                qqchProdPlan.setFinishRatio(sumProdValue.divide(contractInfo.getEffectiveAmout(), 2, BigDecimal.ROUND_HALF_UP));
            }

            qqchProdPlanList.add(qqchProdPlan);
        }

        if(CollectionUtils.isEmpty(qqchProdPlanList)) {
            return 0;
        }

        return insertQqchProdPlanList(qqchProdPlanList);

    }

    /**
     * 删除版本数据
     * @param version
     */
    private void deleteQqchProdPlanByVersion(BigDecimal version) {
        this.qqchProdPlanMapper.deleteQqchProdPlanByVersion(version);
    }


    // TODO 获取p6计划的开始时间和结束时间
    private void getTime(List<Date> dateList, Date startDate,Date finishDate) {

        Date s = getRealMonth(startDate);
        Date f = getRealMonth(finishDate);

        if(s != null) {
            dateList.add(s);
        }

        if(s.compareTo(f) == 0) {
            return;
        }

        Calendar cl = Calendar.getInstance();

        cl.setTime(startDate);
        cl.add(Calendar.MONTH, 1);

        startDate = cl.getTime();

        getTime(dateList, startDate, finishDate);
    }

    private Date getRealMonth(Date date) {
        Date returnDate = null;
        if(date == null) {
            return returnDate;
        }
        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        int day = cl.get(Calendar.DAY_OF_MONTH);
        if(day >= 21) {
            cl.add(Calendar.MONTH, 1);
            returnDate = cl.getTime();
        } else {
            returnDate = date;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String format = sdf.format(returnDate);
        try {
            returnDate = sdf.parse(format);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return returnDate;
    }


    // 获取p6的
    private HashMap<Date, List<String>> getWbsList(Date startDate, Date endDate) {
        HashMap<Date, List<String>> res = new HashMap<>();

        // 根据开始时间结束时间获取其中的月份
        List<Date> dateList = FtDateUtils.getDateList(startDate, endDate);
        // 根据每个月的作业项
        for (Date date : dateList) {
            // 伪代码
            ArrayList<String> ids = new ArrayList<>();
            ids.add("1");
            ids.add("2");
            ids.add("3");
            ids.add("4");
            ids.add("5");
            ids.add("6");
            res.put(date, ids);
        }

        return res;
    }


}
