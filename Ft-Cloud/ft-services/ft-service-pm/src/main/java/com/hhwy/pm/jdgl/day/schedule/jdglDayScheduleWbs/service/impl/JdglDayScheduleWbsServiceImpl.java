package com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleWbs.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.jdgl.day.schedule.jdglDaySchedule.service.IJdglDayScheduleService;
import com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleBill.domain.JdglDayScheduleBill;
import com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleBill.service.IJdglDayScheduleBillService;
import com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleWbs.domain.JdglDayScheduleWbs4Value;
import com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleWbs.service.IJdglDayScheduleWbsService;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractList;
import com.hhwy.pm.xmsl.drawReview.domain.XmslDrawReviewWbs;
import com.hhwy.pm.xmsl.drawReview.service.IXmslDrawReviewService;
import com.hhwy.pm.xmsl.drawReview.service.IXmslDrawReviewWbsService;
import com.hhwy.pm.xmsl.wbs.WbsRedisUtils;
import com.hhwy.pm.xmsl.wbs.domain.XmslWbs;
import com.hhwy.utils.tree.TreeUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleWbs.mapper.JdglDayScheduleWbsMapper;
import com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleWbs.domain.JdglDayScheduleWbs;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author cjh
 * @date 2023-08-24 14:05:56
 * @remark
 */
@Service
public class JdglDayScheduleWbsServiceImpl implements IJdglDayScheduleWbsService {

    @Autowired
    private JdglDayScheduleWbsMapper jdglDayScheduleWbsMapper;

    @Autowired
    private IJdglDayScheduleBillService iJdglDayScheduleBillService;

    @Autowired
    private IXmslDrawReviewService xmslDrawReviewService;

    @Autowired
    private IXmslDrawReviewWbsService xmslDrawReviewWbsService;

    public JdglDayScheduleWbs getJdglDayScheduleWbs(JdglDayScheduleWbs jdglDayScheduleWbs) {
        JdglDayScheduleWbs jdglDayScheduleWbs1 = jdglDayScheduleWbsMapper.getJdglDayScheduleWbs(jdglDayScheduleWbs);
        if(jdglDayScheduleWbs1 != null) {
            JdglDayScheduleBill jdglDayScheduleBill = new JdglDayScheduleBill();
            jdglDayScheduleBill.setDayScheduleId(jdglDayScheduleWbs1.getDayScheduleId());
            jdglDayScheduleBill.setWbsCode(jdglDayScheduleWbs1.getWbsCode());
            List<JdglDayScheduleBill> jdglDayScheduleBillList = iJdglDayScheduleBillService.getJdglDayScheduleBillList(jdglDayScheduleBill);
            jdglDayScheduleWbs1.setJdglDayScheduleBillList(jdglDayScheduleBillList);
        }
        return jdglDayScheduleWbs1;
    }

    public List<JdglDayScheduleWbs> getJdglDayScheduleWbsList(JdglDayScheduleWbs jdglDayScheduleWbs) {
        List<JdglDayScheduleWbs> jdglDayScheduleWbsList = jdglDayScheduleWbsMapper.getJdglDayScheduleWbsList(jdglDayScheduleWbs);
        if(!CollectionUtils.isEmpty(jdglDayScheduleWbsList)) {
            for (JdglDayScheduleWbs jdglDayScheduleWbs1 : jdglDayScheduleWbsList) {
                JdglDayScheduleBill jdglDayScheduleBill = new JdglDayScheduleBill();
                jdglDayScheduleBill.setDayScheduleId(jdglDayScheduleWbs1.getDayScheduleId());
                jdglDayScheduleBill.setWbsCode(jdglDayScheduleWbs1.getWbsCode());
                List<JdglDayScheduleBill> jdglDayScheduleBillList = iJdglDayScheduleBillService.getJdglDayScheduleBillList(jdglDayScheduleBill);
                jdglDayScheduleWbs1.setJdglDayScheduleBillList(jdglDayScheduleBillList);
            }
        }
        List<JdglDayScheduleWbs> build = TreeUtil.build(jdglDayScheduleWbsList, jdglDayScheduleWbs.getId());
        return build;
    }


    @Override
    public List<JdglDayScheduleWbs> getJdglDayScheduleWbsListByPerson(JdglDayScheduleWbs jdglDayScheduleWbs) {

        List<JdglDayScheduleWbs> returnList = new ArrayList<>();

        // 所有数据
        List<JdglDayScheduleWbs> jdglDayScheduleWbsList = jdglDayScheduleWbsMapper.getJdglDayScheduleWbsList(jdglDayScheduleWbs);

        if(CollectionUtils.isEmpty(jdglDayScheduleWbsList)) {
            return returnList;
        }

        // 过滤出当前登录人过滤出的wbs叶子节点集合
        String userId = SecurityUtils.getUserId() + "";
        List<JdglDayScheduleWbs> collect = jdglDayScheduleWbsList.stream().filter(vo -> userId.equals(vo.getEditerId())).collect(Collectors.toList());

        if(CollectionUtils.isEmpty(collect)) {
            return returnList;
        }

        // 根据当前登录人过滤出来的wbs叶子节点的祖籍id获取所有相关wbs层级数据
        for (JdglDayScheduleWbs jdglDayScheduleWbs1 : collect) {
            List<JdglDayScheduleWbs> collect1 = collect.stream().filter(vo ->
                    jdglDayScheduleWbs1.getAncestrals().contains(vo.getWbsId() + "")
                            && !jdglDayScheduleWbs1.getId().equals(vo.getId())).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(collect1))  returnList.addAll(collect1);

        }

        if(!CollectionUtils.isEmpty(returnList)) {
            for (JdglDayScheduleWbs jdglDayScheduleWbs1 : returnList) {
                JdglDayScheduleBill jdglDayScheduleBill = new JdglDayScheduleBill();
                jdglDayScheduleBill.setDayScheduleId(jdglDayScheduleWbs1.getDayScheduleId());
                jdglDayScheduleBill.setWbsCode(jdglDayScheduleWbs1.getWbsCode());
                List<JdglDayScheduleBill> jdglDayScheduleBillList = iJdglDayScheduleBillService.getJdglDayScheduleBillList(jdglDayScheduleBill);
                jdglDayScheduleWbs1.setJdglDayScheduleBillList(jdglDayScheduleBillList);
            }
        }
        List<JdglDayScheduleWbs> build = TreeUtil.build(returnList, jdglDayScheduleWbs.getId());
        return build;
    }


    /**
     * 获取wbs及图纸复核数据并过滤当前日报的wbs
     * @return
     */
    @Override
    public List<JdglDayScheduleWbs> getLazyWbs4NoThis(JdglDayScheduleWbs jdglDayScheduleWbsParam) {

        Long pid = jdglDayScheduleWbsParam.getPid();
        Long dayScheduleId = jdglDayScheduleWbsParam.getDayScheduleId();
        String wbsName = jdglDayScheduleWbsParam.getWbsName();

        List<JdglDayScheduleWbs> returnList = new ArrayList<>();

//        WbsRedisUtils.getDireChildWbs(pid == null ? "-1" : pid + "");
//
//        // 获取图纸复核清单数据
//        if(CollectionUtils.isEmpty(list)) {
//            return null;
//        }
//
//        // 获取当前日填报wbs数据
//        JdglDayScheduleWbs jdglDayScheduleWbs = new JdglDayScheduleWbs();
//        jdglDayScheduleWbs.setDayScheduleId(dayScheduleId);
//        List<JdglDayScheduleWbs> jdglDayScheduleWbsList = jdglDayScheduleWbsMapper.getJdglDayScheduleWbsList(jdglDayScheduleWbs);
//
//        //
//        if(!CollectionUtils.isEmpty(jdglDayScheduleWbsList)) {
//            list.stream().forEach(vo -> {
//
//                JdglDayScheduleWbs jdglDayScheduleWbs1 = new JdglDayScheduleWbs();
//                jdglDayScheduleWbs1.setId(Long.valueOf(vo.getId()));
//                jdglDayScheduleWbs1.setPid(Long.valueOf(vo.getPid()));
//                jdglDayScheduleWbs1.setAncestrals(vo.getAncestors());
//                jdglDayScheduleWbs1.setSort(vo.getSort());
////                jdglDayScheduleWbs1.setIsLeaf(vo.g);
//                jdglDayScheduleWbs1.setHaveChildren(vo.getHaveChildren());
//                jdglDayScheduleWbs1.setWbsCode(vo.getCode());
//                jdglDayScheduleWbs1.setWbsId(Long.valueOf(vo.getId()));
//                jdglDayScheduleWbs1.setWbsName(vo.getName());
//                jdglDayScheduleWbs1.setWbsPid(Long.valueOf(vo.getPid()));
//                for (JdglDayScheduleWbs jdglDayScheduleWbs2 : jdglDayScheduleWbsList) {
//                    if(StringUtils.isNotEmpty(vo.getCode()) && vo.getCode().equals(jdglDayScheduleWbs1.getWbsCode())) {
//                        jdglDayScheduleWbs1.setIsExists("1");
//                    }
//                }
//            });
//        }

        return returnList;
    }
    @Override
    public List<JdglDayScheduleWbs> getJdglDayScheduleWbsLazyList(JdglDayScheduleWbs jdglDayScheduleWbs) {
        Long id = jdglDayScheduleWbs.getId();
        jdglDayScheduleWbs.setId(null);
        List<JdglDayScheduleWbs> jdglDayScheduleWbsList = jdglDayScheduleWbsMapper.getJdglDayScheduleWbsList(jdglDayScheduleWbs);
        if(!CollectionUtils.isEmpty(jdglDayScheduleWbsList)) {
            for (JdglDayScheduleWbs jdglDayScheduleWbs1 : jdglDayScheduleWbsList) {
                JdglDayScheduleBill jdglDayScheduleBill = new JdglDayScheduleBill();
                jdglDayScheduleBill.setDayScheduleId(jdglDayScheduleWbs1.getDayScheduleId());
                jdglDayScheduleBill.setWbsCode(jdglDayScheduleWbs1.getWbsCode());
                List<JdglDayScheduleBill> jdglDayScheduleBillList = iJdglDayScheduleBillService.getJdglDayScheduleBillList(jdglDayScheduleBill);
                jdglDayScheduleWbs1.setJdglDayScheduleBillList(jdglDayScheduleBillList);
            }
        }
        List<JdglDayScheduleWbs> build = TreeUtil.build(jdglDayScheduleWbsList, id);
        if(!CollectionUtils.isEmpty(build)) {
            for (JdglDayScheduleWbs jdglDayScheduleWbs1: build) {
                List<JdglDayScheduleWbs> children = jdglDayScheduleWbs1.getChildren();
                if(!CollectionUtils.isEmpty(children)) {
                    jdglDayScheduleWbs1.setHaveChildren(children.size());
                    jdglDayScheduleWbs1.setChildren(null);
                }
            }
        }
        return build;
    }

    /**
     * 根据日期从总进度计划获取形象清单关联的wbs及清单数据&未完&
     * @param date
     * @return
     */
    @Override
    public List<JdglDayScheduleWbs> getInitWbs(Date date) {

        List<JdglDayScheduleWbs> jdglDayScheduleWbsList = new ArrayList<JdglDayScheduleWbs>();
        // 根据日期获取总进度计划中作业区间内的wbs数据

        // 根据wbs获取图纸复核中清单数据以及从合同工程量清单中获取清单单价
        iJdglDayScheduleBillService.getInitBill(jdglDayScheduleWbsList, date);

        return null;
    }

    /**
     * 根据日期区间查询wbs数据
     * @param startDate
     * @param endDate
     * @return
     */
    @Override
    public List<JdglDayScheduleWbs4Value> getWbsListByDateRange(Date startDate, Date endDate) {
        return jdglDayScheduleWbsMapper.getWbsListByDateRange4Value(startDate, endDate);
    }

    /**
     * 根据日期获取开累信息
     * @param endDate
     * @return
     */
    @Override
    public List<JdglDayScheduleWbs4Value> getTotalWbsListByDateRange(Date endDate) {
        return jdglDayScheduleWbsMapper.getTotalWbsListByDateRange4Value(endDate);
    }


    @Transactional
    public int insertJdglDayScheduleWbs(JdglDayScheduleWbs jdglDayScheduleWbs) {
        jdglDayScheduleWbs.setId(IdWorker.createId());
        jdglDayScheduleWbs.setCreateUser(SecurityUtils.getUserName());
        jdglDayScheduleWbs.setCreateTime(DateUtils.getNowDate());
        return jdglDayScheduleWbsMapper.insertJdglDayScheduleWbs(jdglDayScheduleWbs);
    }

    @Transactional
    public int insertJdglDayScheduleWbsList(List<JdglDayScheduleWbs> jdglDayScheduleWbsList) {
        if(CollectionUtils.isEmpty(jdglDayScheduleWbsList)) {
            return 0;
        }

        // 获取当前进度填报中wbs数据
        Long dayScheduleId = jdglDayScheduleWbsList.get(0).getDayScheduleId();
        JdglDayScheduleWbs query = new JdglDayScheduleWbs();
        query.setDayScheduleId(dayScheduleId);
        List<JdglDayScheduleWbs> jdglDayScheduleWbsList1 = getJdglDayScheduleWbsList(query);

        ArrayList<JdglDayScheduleWbs> jdglDayScheduleWbsAncestrals = new ArrayList<>();

        for (JdglDayScheduleWbs jdglDayScheduleWbs : jdglDayScheduleWbsList) {

//            jdglDayScheduleWbs.setId(IdWorker.createId());
            jdglDayScheduleWbs.setCreateUser(SecurityUtils.getUserName());
            jdglDayScheduleWbs.setCreateTime(DateUtils.getNowDate());

            // 获取祖籍图纸复核wbs数据
            String ancestrals = jdglDayScheduleWbs.getAncestrals();
            if(StringUtils.isEmpty(ancestrals)) {
                continue;
            }
            String[] split = ancestrals.split(",");

            Set<Long> ids = new HashSet<>();

            for (String s : split) {
                ids.add(Long.valueOf(s));
            }


            List<XmslDrawReviewWbs> byIds = xmslDrawReviewWbsService.getByIds(ids);

            if(!CollectionUtils.isEmpty(byIds)) {
                for (XmslDrawReviewWbs xmslDrawReviewWbs : byIds) {
                    if(jdglDayScheduleWbs.getWbsId().equals(xmslDrawReviewWbs.getWbsId())) {
                        continue;
                    }
                    JdglDayScheduleWbs jdglDayScheduleWbs1 = new JdglDayScheduleWbs();
                    jdglDayScheduleWbs1.setId(IdWorker.createId());
                    jdglDayScheduleWbs1.setDayScheduleId(dayScheduleId);
                    jdglDayScheduleWbs1.setWbsId(xmslDrawReviewWbs.getWbsId());
                    jdglDayScheduleWbs1.setWbsCode(xmslDrawReviewWbs.getCode());
                    jdglDayScheduleWbs1.setWbsName(xmslDrawReviewWbs.getName());
                    jdglDayScheduleWbs1.setAncestrals(xmslDrawReviewWbs.getAncestors());
//                    jdglDayScheduleWbs1.setSort(xmslDrawReviewWbs.get);
//                    jdglDayScheduleWbs1.setIsLeaf(xmslDrawReviewWbs.get);
//                    jdglDayScheduleWbs1.setDesignQuantity(xmslDrawReviewWbs.getDesignQuanlity());
                    if(!CollectionUtils.isEmpty(jdglDayScheduleWbsList1)) {
                        for (JdglDayScheduleWbs vo2 : jdglDayScheduleWbsList1) {
                            if(vo2.getWbsId().equals(xmslDrawReviewWbs.getWbsId())) {
                                jdglDayScheduleWbs1.setId(vo2.getId());
                            }
                        }
                    }
                    jdglDayScheduleWbsAncestrals.add(jdglDayScheduleWbs1);
                }
            }

            if(!CollectionUtils.isEmpty(jdglDayScheduleWbsAncestrals)) {
                for (JdglDayScheduleWbs vo : jdglDayScheduleWbsAncestrals) {
                    int count = 0;
                    Long pid = 0L;
                    for (JdglDayScheduleWbs vo2 : jdglDayScheduleWbsList1) {
                        if(vo2.getWbsId().equals(vo.getWbsId())) {
                            count++;
                        }
                        if(vo2.getWbsId().equals(vo.getPid())) {
                            pid = vo.getId();
                        }
                    }
                }
            }





//            xmslDrawReviewService.

            List<JdglDayScheduleBill> jdglDayScheduleBillList = jdglDayScheduleWbs.getJdglDayScheduleBillList();
            if(!CollectionUtils.isEmpty(jdglDayScheduleBillList)) {
                for (JdglDayScheduleBill jdglDayScheduleBill : jdglDayScheduleBillList) {
                    jdglDayScheduleBill.setDayScheduleId(jdglDayScheduleWbs.getDayScheduleId());
                    jdglDayScheduleBill.setWbsCode(jdglDayScheduleWbs.getWbsCode());
                }
                iJdglDayScheduleBillService.insertJdglDayScheduleBillList(jdglDayScheduleBillList);
            }
        }
        return jdglDayScheduleWbsMapper.insertJdglDayScheduleWbsList(jdglDayScheduleWbsList);
    }

    @Transactional
    public int updateJdglDayScheduleWbs(JdglDayScheduleWbs jdglDayScheduleWbs) {
        jdglDayScheduleWbs.setUpdateUser(SecurityUtils.getUserName());
        jdglDayScheduleWbs.setUpdateTime(DateUtils.getNowDate());
        return jdglDayScheduleWbsMapper.updateJdglDayScheduleWbs(jdglDayScheduleWbs);
    }

    @Transactional
    public int updateJdglDayScheduleWbsList(List<JdglDayScheduleWbs> jdglDayScheduleWbsList, Long dayScheduleId) {
        if(CollectionUtils.isEmpty(jdglDayScheduleWbsList)) {
            iJdglDayScheduleBillService.deleteJdglDayScheduleBillByDayScheduleId(dayScheduleId);
            return jdglDayScheduleWbsMapper.deleteJdglDayScheduleWbsByDayScheduleId(dayScheduleId);
        }
        jdglDayScheduleWbsMapper.deleteJdglDayScheduleWbsByDayScheduleId(dayScheduleId);
        List<JdglDayScheduleWbs> jdglDayScheduleWbs1 = TreeUtil.treeToList(jdglDayScheduleWbsList);
        for (JdglDayScheduleWbs jdglDayScheduleWbs : jdglDayScheduleWbs1) {
            List<JdglDayScheduleBill> jdglDayScheduleBillList = jdglDayScheduleWbs.getJdglDayScheduleBillList();
            if(!CollectionUtils.isEmpty(jdglDayScheduleBillList)) {
                for (JdglDayScheduleBill jdglDayScheduleBill : jdglDayScheduleBillList) {
                    jdglDayScheduleBill.setWbsCode(jdglDayScheduleWbs.getWbsCode());
                    jdglDayScheduleBill.setDayScheduleId(dayScheduleId);
                }

            }
            iJdglDayScheduleBillService.updateJdglDayScheduleBillList(jdglDayScheduleBillList, dayScheduleId, jdglDayScheduleWbs.getWbsCode());
            // 更新完清单更新主表产值数据;
        }

        return jdglDayScheduleWbsMapper.insertJdglDayScheduleWbsList(jdglDayScheduleWbs1);
    }

    @Transactional
    public int deleteJdglDayScheduleWbs(JdglDayScheduleWbs jdglDayScheduleWbs) {
        jdglDayScheduleWbs.setUpdateUser(SecurityUtils.getUserName());
        jdglDayScheduleWbs.setUpdateTime(DateUtils.getNowDate());
        return jdglDayScheduleWbsMapper.deleteJdglDayScheduleWbs(jdglDayScheduleWbs);
    }

    @Transactional
    public int deleteJdglDayScheduleWbsByPks(List<Long> jdglDayScheduleWbsPkList) {
        return jdglDayScheduleWbsMapper.deleteJdglDayScheduleWbsByPks(jdglDayScheduleWbsPkList);
    }


}
