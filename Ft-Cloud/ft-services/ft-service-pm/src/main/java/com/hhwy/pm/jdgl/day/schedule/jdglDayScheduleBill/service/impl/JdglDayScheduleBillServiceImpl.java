package com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleBill.service.impl;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleWbs.domain.JdglDayScheduleWbs;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.domain.JdglMainPlanItem;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.service.IJdglMainPlanItemService;
import com.hhwy.pm.jdgl.statistics.util.StatisticsUtils;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractInfo;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractList;
import com.hhwy.pm.xmsl.contractInfo.service.IXmslContractInfoService;
import com.hhwy.pm.xmsl.contractInfo.service.IXmslContractListService;
import com.hhwy.pm.xmsl.drawReview.domain.XmslDrawReview;
import com.hhwy.pm.xmsl.drawReview.domain.XmslDrawReviewList;
import com.hhwy.pm.xmsl.drawReview.service.IXmslDrawReviewService;
import com.hhwy.pm.xmsl.wbs.WbsRedisUtils;
import com.hhwy.pm.xmsl.wbs.domain.XmslWbs;
import com.hhwy.pm.xmsl.wbs.service.IXmslWbsService;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleBill.mapper.JdglDayScheduleBillMapper;
import com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleBill.service.IJdglDayScheduleBillService;
import com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleBill.domain.JdglDayScheduleBill;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author cjh
 * @date 2023-08-24 14:05:49
 * @remark
 */
@Service
public class JdglDayScheduleBillServiceImpl implements IJdglDayScheduleBillService {

    @Autowired
    private JdglDayScheduleBillMapper jdglDayScheduleBillMapper;

    @Autowired
    private IXmslDrawReviewService xmslDrawReviewService;

    @Autowired
    private IXmslContractListService xmslContractListService;

    @Autowired
    private IXmslWbsService wbsService;

    @Autowired
    private IJdglMainPlanItemService jdglMainPlanItemService;


    public JdglDayScheduleBill getJdglDayScheduleBill(JdglDayScheduleBill jdglDayScheduleBill) {
        return jdglDayScheduleBillMapper.getJdglDayScheduleBill(jdglDayScheduleBill);
    }

    public List<JdglDayScheduleBill> getJdglDayScheduleBillList(JdglDayScheduleBill jdglDayScheduleBill) {
        Long wbsId = jdglDayScheduleBill.getWbsId();
        String wbsCode = jdglDayScheduleBill.getWbsCode();
        String wbsName = jdglDayScheduleBill.getWbsName();
        String itemCode = jdglDayScheduleBill.getItemCode();
        Long dayScheduleId = jdglDayScheduleBill.getDayScheduleId();
        Date date = jdglDayScheduleBill.getDate();
        if(dayScheduleId == null) {
            jdglDayScheduleBill.setDayScheduleId(-1l);
        }
        String isLeaf = jdglDayScheduleBill.getIsLeaf();
        jdglDayScheduleBill.setWbsId(null);

        List<JdglDayScheduleBill> jdglDayScheduleBillList = null;

        if ("0".equals(isLeaf)) {
            jdglDayScheduleBillList = jdglDayScheduleBillMapper.getJdglDayScheduleBillList4Group(jdglDayScheduleBill);
        } else {
            jdglDayScheduleBillList = jdglDayScheduleBillMapper.getJdglDayScheduleBillList(jdglDayScheduleBill);
        }

        if(CollectionUtils.isEmpty(jdglDayScheduleBillList)) {
            jdglDayScheduleBillList = new ArrayList<>();
            XmslDrawReview last = xmslDrawReviewService.getLast();
            if(last == null) {
                return jdglDayScheduleBillList;
            }
            Integer version = last.getVersion();
            Long id = last.getId();
            if(wbsId == null) {
                XmslWbs wbsByCode = wbsService.getByCode(wbsCode);
                if(wbsByCode != null) wbsId = Long.parseLong(wbsByCode.getId());
            }
            List<XmslDrawReviewList> xmslDrawReviewLists = xmslDrawReviewService.relationWbsList(version, id, wbsCode + "-" + itemCode, wbsId);
            if(CollectionUtils.isEmpty(xmslDrawReviewLists)) {
                return jdglDayScheduleBillList;
            }

            List<String> itemCodes = new ArrayList<>();
            itemCodes.add(wbsCode);
            itemCodes.add(itemCode);
            List<JdglMainPlanItem> planItemByItemCodes = jdglMainPlanItemService.getUsingJdglMainPlanItemByItemCodes(itemCodes);
            BigDecimal radio = BigDecimal.ZERO;
            if(!CollectionUtils.isEmpty(planItemByItemCodes)) {
                JdglMainPlanItem wbsPlanItem = planItemByItemCodes.stream().filter(vo -> wbsCode.equals(vo.getItemCode())).findFirst().orElse(null);
                JdglMainPlanItem itemPlanItem = planItemByItemCodes.stream().filter(vo -> itemCode.equals(vo.getItemCode())).findFirst().orElse(null);
                if(itemPlanItem != null && itemPlanItem.getQuantity() != null && wbsPlanItem != null && wbsPlanItem.getQuantity() != null) {
                    if(BigDecimal.ZERO.compareTo(wbsPlanItem.getQuantity()) != 0) {
                        radio = itemPlanItem.getQuantity().divide(wbsPlanItem.getQuantity(), 4, BigDecimal.ROUND_HALF_UP);
                    }
                }
            }

            // 获取主合同清单数据
            List<XmslContractList> validMaxVersionContractInventoryList = xmslContractListService.getValidMaxVersionContractInventoryList();

            List<JdglDayScheduleBill> billValueListByEndDate4WbsBill = getBillValueListByEndDate4WbsBill(StatisticsUtils.addDays(date, -1));

            for (XmslDrawReviewList xmslDrawReviewList : xmslDrawReviewLists) {
                String listCode = xmslDrawReviewList.getListCode();
                JdglDayScheduleBill jdglDayScheduleBill1 = new JdglDayScheduleBill();
                jdglDayScheduleBill1.setDayScheduleId(dayScheduleId);
                jdglDayScheduleBill1.setWbsId(xmslDrawReviewList.getWbsId());
                jdglDayScheduleBill1.setWbsCode(wbsCode);
                jdglDayScheduleBill1.setWbsName(wbsName);
                jdglDayScheduleBill1.setBillCode(xmslDrawReviewList.getListCode());
                jdglDayScheduleBill1.setBillName(xmslDrawReviewList.getChineseName());
                jdglDayScheduleBill1.setItemCode(itemCode);
                if(!CollectionUtils.isEmpty(validMaxVersionContractInventoryList)) {
                    for (XmslContractList xmslContractList : validMaxVersionContractInventoryList) {
                        if(listCode != null && listCode.equals(xmslContractList.getCode())) {
                            jdglDayScheduleBill1.setBillPrice(
                                    xmslContractList.getChangeAmount() == null || Long.valueOf("0").equals(xmslContractList.getChangeAmount())
                                    ? xmslContractList.getWinUnitPrice() : xmslContractList.getChangeAmount()
                            );
                        }
                    }
                }
                jdglDayScheduleBill1.setUnit(xmslDrawReviewList.getUnit());
                BigDecimal checkNum = xmslDrawReviewList.getCheckNum();
                if(checkNum != null) jdglDayScheduleBill1.setDesignQuantity(checkNum.multiply(radio));
                BigDecimal totalQty = BigDecimal.ZERO;
                if(!CollectionUtils.isEmpty(billValueListByEndDate4WbsBill)) {
                    JdglDayScheduleBill jdglDayScheduleBill2 = billValueListByEndDate4WbsBill.stream().filter(vo ->
                            itemCode.equals(vo.getItemCode()) && jdglDayScheduleBill1.getBillCode().equals(vo.getBillCode())).findFirst().orElse(null);
                    if(jdglDayScheduleBill2 != null) totalQty = jdglDayScheduleBill2.getThisQuantity();
                }
                if(jdglDayScheduleBill1.getDesignQuantity() != null && totalQty != null) {
                    jdglDayScheduleBill1.setRemainQuantity(jdglDayScheduleBill1.getDesignQuantity().subtract(totalQty));
                } else {
                    jdglDayScheduleBill1.setRemainQuantity(jdglDayScheduleBill1.getDesignQuantity());
                }
                jdglDayScheduleBill1.setIsMain(xmslDrawReviewList.getImageProgress());
                jdglDayScheduleBillList.add(jdglDayScheduleBill1);
            }
        }
        return jdglDayScheduleBillList;
    }

    @Override
    public List<JdglDayScheduleBill> getBillValueListByRangeDate(Date startDate, Date endDate) {
        return jdglDayScheduleBillMapper.getBillValueListByRangeDate(startDate, endDate);
    }

    @Override
    public List<JdglDayScheduleBill> getBillValueListByEndDate(Date endDate) {
        return jdglDayScheduleBillMapper.getBillValueListByEndDate(endDate);
    }

    @Override
    public List<JdglDayScheduleBill> getBillValueListByRangeDate4WbsBill(Date startDate, Date endDate) {
        return jdglDayScheduleBillMapper.getBillValueListByRangeDate4WbsBill(startDate, endDate);
    }

    @Override
    public List<JdglDayScheduleBill> getBillValueListByEndDate4WbsBill(Date endDate) {
        return jdglDayScheduleBillMapper.getBillValueListByEndDate4WbsBill(endDate);
    }

    @Transactional
    public int insertJdglDayScheduleBill(JdglDayScheduleBill jdglDayScheduleBill) {
        jdglDayScheduleBill.setId(IdWorker.createId());
        jdglDayScheduleBill.setCreateUser(SecurityUtils.getUserName());
        jdglDayScheduleBill.setCreateTime(DateUtils.getNowDate());
        return jdglDayScheduleBillMapper.insertJdglDayScheduleBill(jdglDayScheduleBill);
    }

    @Transactional
    public int insertJdglDayScheduleBillList(List<JdglDayScheduleBill> jdglDayScheduleBillList) {
        if(CollectionUtils.isEmpty(jdglDayScheduleBillList)) {
            return 0;
        }
        for (JdglDayScheduleBill jdglDayScheduleBill : jdglDayScheduleBillList) {
            jdglDayScheduleBill.setId(IdWorker.createId());
            jdglDayScheduleBill.setCreateUser(SecurityUtils.getUserName());
            jdglDayScheduleBill.setCreateTime(DateUtils.getNowDate());
        }
        return jdglDayScheduleBillMapper.insertJdglDayScheduleBillList(jdglDayScheduleBillList);
    }

    /**
     * 根据wbs初始化插入清单数据
     * @param jdglDayScheduleWbs
     * @return
     */
    @Override
    public int insertJdglDayScheduleBillList(JdglDayScheduleWbs jdglDayScheduleWbs) {



        return 0;
    }

    @Transactional
    public int updateJdglDayScheduleBill(JdglDayScheduleBill jdglDayScheduleBill) {
        jdglDayScheduleBill.setUpdateUser(SecurityUtils.getUserName());
        jdglDayScheduleBill.setUpdateTime(DateUtils.getNowDate());
        return jdglDayScheduleBillMapper.updateJdglDayScheduleBill(jdglDayScheduleBill);
    }

    @Override
    public int updateJdglDayScheduleBillList(List<JdglDayScheduleBill> jdglDayScheduleBillList) {
        if(CollectionUtils.isEmpty(jdglDayScheduleBillList)) {
            return 0;
        }
        for (JdglDayScheduleBill jdglDayScheduleBill : jdglDayScheduleBillList) {
            jdglDayScheduleBill.setUpdateUser(SecurityUtils.getUserName());
            jdglDayScheduleBill.setUpdateTime(DateUtils.getNowDate());
        }
        return jdglDayScheduleBillMapper.updateJdglDayScheduleBillList(jdglDayScheduleBillList);
    }

    @Transactional
    public int updateJdglDayScheduleBillList(List<JdglDayScheduleBill> jdglDayScheduleBillList, Long dayScheduleId, String wbsCode) {
        if(CollectionUtils.isEmpty(jdglDayScheduleBillList)) {
            return 0;
        }
        for (JdglDayScheduleBill jdglDayScheduleBill : jdglDayScheduleBillList) {
            jdglDayScheduleBill.setDayScheduleId(dayScheduleId);
            jdglDayScheduleBill.setWbsCode(wbsCode);
            jdglDayScheduleBill.setUpdateUser(SecurityUtils.getUserName());
            jdglDayScheduleBill.setUpdateTime(DateUtils.getNowDate());
        }
        return jdglDayScheduleBillMapper.updateJdglDayScheduleBillList(jdglDayScheduleBillList);
//        if(CollectionUtils.isEmpty(jdglDayScheduleBillList)) {
//            return jdglDayScheduleBillMapper.deleteJdglDayScheduleBillByDayScheduleIdAndWbsCode(dayScheduleId, wbsCode);
//        }
//        jdglDayScheduleBillMapper.deleteJdglDayScheduleBillByDayScheduleIdAndWbsCode(dayScheduleId, wbsCode);
//        for (JdglDayScheduleBill jdglDayScheduleBill : jdglDayScheduleBillList) {
//            jdglDayScheduleBill.setDayScheduleId(dayScheduleId);
//            jdglDayScheduleBill.setWbsCode(wbsCode);
//            jdglDayScheduleBill.setUpdateUser(SecurityUtils.getUserName());
//            jdglDayScheduleBill.setUpdateTime(DateUtils.getNowDate());
//            if(jdglDayScheduleBill.getThisQuantity() != null && jdglDayScheduleBill.getBillPrice() != null) {
//                jdglDayScheduleBill.setBillValue(jdglDayScheduleBill.getThisQuantity().multiply(jdglDayScheduleBill.getBillPrice()));
//            }
//        }
//        return jdglDayScheduleBillMapper.insertJdglDayScheduleBillList(jdglDayScheduleBillList);
    }

    @Transactional
    public int deleteJdglDayScheduleBill(JdglDayScheduleBill jdglDayScheduleBill) {
        jdglDayScheduleBill.setUpdateUser(SecurityUtils.getUserName());
        jdglDayScheduleBill.setUpdateTime(DateUtils.getNowDate());
        return jdglDayScheduleBillMapper.deleteJdglDayScheduleBill(jdglDayScheduleBill);
    }

    @Transactional
    public int deleteJdglDayScheduleBillByPks(List<Long> jdglDayScheduleBillPkList) {
        return jdglDayScheduleBillMapper.deleteJdglDayScheduleBillByPks(jdglDayScheduleBillPkList);
    }

    /**
     * 根据wbs获取关联的图纸复核清单数据及合同清单中清单单价
     * @param jdglDayScheduleWbsList
     */
    @Override
    public List<JdglDayScheduleWbs> getInitBill(List<JdglDayScheduleWbs> jdglDayScheduleWbsList, Date date) {

        if(CollectionUtils.isEmpty(jdglDayScheduleWbsList)) {
            return null;
        }
        // 获取最新的图纸复核主表
        XmslDrawReview last = xmslDrawReviewService.getLast();
        if(last == null) {
            return null;
        }
        // 获取生效的合同清单数据
        List<XmslContractList> effectList = xmslContractListService.getEffectList(new XmslContractList());
        if(CollectionUtils.isEmpty(effectList)) {
            return null;
        }

        // 获取历史进度填报wbs_清单的填报量
        List<JdglDayScheduleBill> historyBill = jdglDayScheduleBillMapper.getHistoryBill(date);

        for (JdglDayScheduleWbs jdglDayScheduleWbs: jdglDayScheduleWbsList) {
            if("1".equals(jdglDayScheduleWbs.getIsLeaf())) {

                Long wbsId = jdglDayScheduleWbs.getWbsId();
                String wbsCode = jdglDayScheduleWbs.getWbsCode();

                //根据wbs获取图纸复核关联的清单
                List<XmslDrawReviewList> xmslDrawReviewLists = xmslDrawReviewService.relationWbsList(last.getVersion(), last.getId(), jdglDayScheduleWbs.getWbsCode(), jdglDayScheduleWbs.getWbsId());
                if(xmslDrawReviewLists == null) {
                    return null;
                }
                List<JdglDayScheduleBill> jdglDayScheduleBillList = new ArrayList<JdglDayScheduleBill>();
                for (XmslDrawReviewList xmslDrawReviewList: xmslDrawReviewLists) {

                    String listCode = xmslDrawReviewList.getListCode();

                    JdglDayScheduleBill jdglDayScheduleBill = new JdglDayScheduleBill();

                    jdglDayScheduleBill.setBillCode(listCode);
                    jdglDayScheduleBill.setWbsId(wbsId);
                    jdglDayScheduleBill.setWbsCode(jdglDayScheduleWbs.getWbsCode());
                    jdglDayScheduleBill.setWbsName(jdglDayScheduleWbs.getWbsName());
                    jdglDayScheduleBill.setDesignQuantity(xmslDrawReviewList.getCheckNum());
                    jdglDayScheduleBill.setUnit(xmslDrawReviewList.getUnit());
                    jdglDayScheduleBill.setIsMain(xmslDrawReviewList.getImageProgress());

                    // 剩余工程量
                    if(!CollectionUtils.isEmpty(historyBill)) {
                        List<JdglDayScheduleBill> collect = historyBill.stream().filter(vo -> wbsCode.equals(vo.getWbsCode()) && listCode.equals(vo.getBillCode())).collect(Collectors.toList());
                        if(!CollectionUtils.isEmpty(collect)) {
                            jdglDayScheduleBill.setRemainQuantity(xmslDrawReviewList.getCheckNum().subtract(collect.get(0).getThisQuantity()));
                        }
                    }

                    // 清单单价，清单名称
                    List<XmslContractList> collect = effectList.stream().filter(vo -> listCode.equals(vo.getCode())).collect(Collectors.toList());
                    if(!CollectionUtils.isEmpty(collect)) {
                        jdglDayScheduleBill.setBillPrice(collect.get(0).getAfterUnitPrice());
                        jdglDayScheduleBill.setBillName(collect.get(0).getChineseName());
                    }

                    jdglDayScheduleBillList.add(jdglDayScheduleBill);
                }
                if(!CollectionUtils.isEmpty(jdglDayScheduleBillList)) {
                    jdglDayScheduleWbs.setJdglDayScheduleBillList(jdglDayScheduleBillList);
                }
            }
        }

        return jdglDayScheduleWbsList;

    }

    @Override
    public void deleteJdglDayScheduleBillByDayScheduleId(Long dayScheduleId) {
        jdglDayScheduleBillMapper.deleteJdglDayScheduleBillByDayScheduleId(dayScheduleId);
    }

    @Override
    public int deleteJdglDayScheduleBillByDayWbsIds(List<Long> jdglDayScheduleWbsPkList, Long dayScheduleId) {
        return jdglDayScheduleBillMapper.deleteJdglDayScheduleBillByDayWbsIds(jdglDayScheduleWbsPkList, dayScheduleId);
    }

}
