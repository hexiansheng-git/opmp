package com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleBill.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleWbs.domain.JdglDayScheduleWbs;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractList;
import com.hhwy.pm.xmsl.contractInfo.service.IXmslContractListService;
import com.hhwy.pm.xmsl.drawReview.domain.XmslDrawReview;
import com.hhwy.pm.xmsl.drawReview.domain.XmslDrawReviewList;
import com.hhwy.pm.xmsl.drawReview.service.IXmslDrawReviewService;
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


    public JdglDayScheduleBill getJdglDayScheduleBill(JdglDayScheduleBill jdglDayScheduleBill) {
        return jdglDayScheduleBillMapper.getJdglDayScheduleBill(jdglDayScheduleBill);
    }

    public List<JdglDayScheduleBill> getJdglDayScheduleBillList(JdglDayScheduleBill jdglDayScheduleBill) {
        return jdglDayScheduleBillMapper.getJdglDayScheduleBillList(jdglDayScheduleBill);
    }

    @Override
    public List<JdglDayScheduleBill> getBillValueListByRangeDate(Date startDate, Date endDate) {
        return jdglDayScheduleBillMapper.getBillValueListByRangeDate(startDate, endDate);
    }

    @Override
    public List<JdglDayScheduleBill> getBillValueListByEndDate(Date endDate) {
        return jdglDayScheduleBillMapper.getBillValueListByEndDate(endDate);
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
        for (JdglDayScheduleBill jdglDayScheduleBill : jdglDayScheduleBillList) {
            jdglDayScheduleBill.setId(IdWorker.createId());
            jdglDayScheduleBill.setCreateUser(SecurityUtils.getUserName());
            jdglDayScheduleBill.setCreateTime(DateUtils.getNowDate());
        }
        return jdglDayScheduleBillMapper.insertJdglDayScheduleBillList(jdglDayScheduleBillList);
    }

    @Transactional
    public int updateJdglDayScheduleBill(JdglDayScheduleBill jdglDayScheduleBill) {
        jdglDayScheduleBill.setUpdateUser(SecurityUtils.getUserName());
        jdglDayScheduleBill.setUpdateTime(DateUtils.getNowDate());
        return jdglDayScheduleBillMapper.updateJdglDayScheduleBill(jdglDayScheduleBill);
    }

    @Transactional
    public int updateJdglDayScheduleBillList(List<JdglDayScheduleBill> jdglDayScheduleBillList, Long dayScheduleId, String wbsCode) {
        if(CollectionUtils.isEmpty(jdglDayScheduleBillList)) {
            return jdglDayScheduleBillMapper.deleteJdglDayScheduleBillByDayScheduleIdAndWbsCode(dayScheduleId, wbsCode);
        }
        jdglDayScheduleBillMapper.deleteJdglDayScheduleBillByDayScheduleIdAndWbsCode(dayScheduleId, wbsCode);
        for (JdglDayScheduleBill jdglDayScheduleBill : jdglDayScheduleBillList) {
            jdglDayScheduleBill.setDayScheduleId(dayScheduleId);
            jdglDayScheduleBill.setWbsCode(wbsCode);
            jdglDayScheduleBill.setUpdateUser(SecurityUtils.getUserName());
            jdglDayScheduleBill.setUpdateTime(DateUtils.getNowDate());
            jdglDayScheduleBill.setBillValue(jdglDayScheduleBill.getThisQuantity().multiply(jdglDayScheduleBill.getBillPrice()));
        }
        return jdglDayScheduleBillMapper.insertJdglDayScheduleBillList(jdglDayScheduleBillList);
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

                    Long listId = xmslDrawReviewList.getListId();
                    String listCode = xmslDrawReviewList.getListCode();

                    JdglDayScheduleBill jdglDayScheduleBill = new JdglDayScheduleBill();

                    jdglDayScheduleBill.setBillId(listId);
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

    /**
     * 获取wbs及图纸复核数据并过滤当前日报的wbs
     * @return
     */
    @Override
    public List<JdglDayScheduleWbs> getAllWbs4NoThis(Long datScheduleId) {

        List<JdglDayScheduleWbs> returnList = new ArrayList<>();

        // 获取wbs数据


        // 获取图纸复核清单数据

        // 获取当前日填报wbs数据

        //

        return returnList;
    }
}
