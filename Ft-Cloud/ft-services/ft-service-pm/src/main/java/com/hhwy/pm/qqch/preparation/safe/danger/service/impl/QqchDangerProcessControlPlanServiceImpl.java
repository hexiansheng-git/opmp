package com.hhwy.pm.qqch.preparation.safe.danger.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlan.service.IJdglMainPlanService;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.domain.JdglMainPlanItem;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.quality.qqchSafetyTrain.domain.QqchSafetyTrain;
import com.hhwy.pm.qqch.preparation.quality.qqchSafetyTrain.domain.vo.QqchSafetyTrainVo;
import com.hhwy.pm.qqch.preparation.quality.qqchSafetyTrain.mapper.QqchSafetyTrainMapper;
import com.hhwy.pm.qqch.preparation.quality.qqchSafetyTrain.service.IQqchSafetyTrainService;
import com.hhwy.pm.qqch.preparation.safe.danger.domain.QqchDangerList;
import com.hhwy.pm.qqch.preparation.safe.danger.domain.QqchDangerProcessControlPlan;
import com.hhwy.pm.qqch.preparation.safe.danger.domain.vo.QqchDangerProcessControlPlanVo;
import com.hhwy.pm.qqch.preparation.safe.danger.mapper.QqchDangerProcessControlPlanMapper;
import com.hhwy.pm.qqch.preparation.safe.danger.service.IQqchDangerListService;
import com.hhwy.pm.qqch.preparation.safe.danger.service.IQqchDangerProcessControlPlanService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.date.FtDateUtils;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.objectUtil.ObjectNullUtil;
import io.reactivex.Single;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhenglili
 * @date 2023-08-07 14:24:24
 * @remark 8.3.3 危大工程过程管控策划
 */
@Service
public class QqchDangerProcessControlPlanServiceImpl implements IQqchDangerProcessControlPlanService {

    @Autowired
    private QqchDangerProcessControlPlanMapper qqchDangerProcessControlPlanMapper;
    @Autowired
    private IQqchReviewService qqchReviewService;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;
    @Autowired
    private IQqchDangerListService qqchDangerListService;
    @Autowired
    private QqchSafetyTrainMapper qqchSafetyTrainMapper;
    @Autowired
    private IQqchSafetyTrainService qqchSafetyTrainService;
    @Autowired
    private IJdglMainPlanService jdglMainPlanService;

    /**
     * 列表
     *
     * @param version
     * @return
     */
    public QqchDangerProcessControlPlanVo getQqchDangerProcessControlPlanList(BigDecimal version) {
        QqchDangerProcessControlPlanVo vo = new QqchDangerProcessControlPlanVo();
        version = VersionUtil.getVersion("qqch_danger_process_control_plan", version);

        QqchDangerProcessControlPlan qryParam = new QqchDangerProcessControlPlan();
        qryParam.setVersion(version);
        List<QqchDangerProcessControlPlan> planList = qqchDangerProcessControlPlanMapper
            .getQqchDangerProcessControlPlanList(qryParam);

        // 组装列表
        List<QqchDangerProcessControlPlan> newList = new ArrayList<>();
        //获取所有wbs编码
        List<String> itemCodeList = new ArrayList<>();

        // 危大工程清单
        List<QqchDangerList> dangerList = qqchDangerListService.getQqchDangerListList(version).getList();
        for (QqchDangerList qqchDangerList : dangerList) {
            QqchDangerProcessControlPlan qqchDangerProcessControlPlan = new QqchDangerProcessControlPlan();
            for (QqchDangerProcessControlPlan plan : planList) {
                if (plan.getSchemeCode().equals(qqchDangerList.getSchemeCode())) {
                    BeanUtils.copyProperties(plan, qqchDangerProcessControlPlan);
                    break;
                }
            }
            qqchDangerProcessControlPlan.setSchemeCode(qqchDangerList.getSchemeCode());
            qqchDangerProcessControlPlan.setSchemeName(qqchDangerList.getSchemeName());
            qqchDangerProcessControlPlan.setWhetherFirst(qqchDangerList.getWhetherFirst());
            qqchDangerProcessControlPlan.setWbsCode(qqchDangerList.getWbsCode());
            newList.add(qqchDangerProcessControlPlan);
            if (StringUtils.isNotBlank(qqchDangerList.getWbsCode())) {
                if (qqchDangerList.getWbsCode().contains(",")) {
                    String[] wbs = qqchDangerList.getWbsCode().split(",");
                    itemCodeList.addAll(Arrays.asList(wbs));
                } else {
                    itemCodeList.add(qqchDangerList.getWbsCode());
                }
            }
        }
        if (!ObjectNullUtil.isEmpty(itemCodeList)) {
            //过滤空字符串
            List<String> itemCodeListNew = itemCodeList.stream().filter(itemList -> StringUtils.isNotBlank(itemList)).collect(Collectors.toList());

            //获取进度总体计划对应的数据
            JdglMainPlanItem jdglMainPlanItem = new JdglMainPlanItem();
            jdglMainPlanItem.setItemType("wbs");
            jdglMainPlanItem.setItemCodeList(itemCodeListNew);
            List<JdglMainPlanItem> itemList = jdglMainPlanService.getJdglMainPlanByWBS(jdglMainPlanItem);
            Map<String, JdglMainPlanItem> itemInfoMap = itemList.stream().collect(Collectors.groupingBy(t -> t.getItemCode(), Collectors.collectingAndThen(Collectors.toList(), v -> v.get(0))));

            newList.forEach(item->{
                if (StringUtils.isNotBlank(item.getWbsCode())) {
                    if (item.getWbsCode().contains(",")) {
                        String[] wbs = item.getWbsCode().split(",");
                        //过滤空字符串
                        List<String> wbsList = Arrays.asList(wbs).stream().filter(item1 -> StringUtils.isNotBlank(item1)).collect(Collectors.toList());
                        List<Date> dataList = new ArrayList<>();
                        wbsList.forEach(item2->{
                            JdglMainPlanItem planItem = itemInfoMap.get(item2);
                            if (planItem != null) {
                                dataList.add(planItem.getStartDate());
                            }
                        });
                        if (!ObjectNullUtil.isEmpty(dataList)) {
                            //获取最早开始时间
                            Date minDate = Collections.min(dataList);
                            item.setPlanStartDate(minDate);
                        }
                    } else {
                        JdglMainPlanItem planItem = itemInfoMap.get(item.getWbsCode());
                        if (planItem != null) {
                            item.setPlanStartDate(planItem.getStartDate());
                        }
                    }
                    if (item.getPlanStartDate() != null) {
                        item.setSchemeFinalizeDate(FtDateUtils.getDateLastMonth(item.getPlanStartDate()));
                        item.setThirdDisclosureDate(FtDateUtils.getDateLastTwoWeek(item.getPlanStartDate()));
                        item.setSafeTrainDate(FtDateUtils.getDateLastTwoWeek(item.getPlanStartDate()));
                    }
                }
            });
        }
        this.insertControlPlan(newList, version);
        planList = qqchDangerProcessControlPlanMapper
                .getQqchDangerProcessControlPlanList(qryParam);
        vo.setVersion(version);
        vo.setStageIdentity(qqchReviewService.getStage());
        vo.setList(planList);
        return vo;
    }


    /**
     * 保存/确认/提交
     *
     * @param voParam
     * @return
     */
    @Transactional
    public void batchSave(QqchDangerProcessControlPlanVo voParam) {
        this.insertControlPlan(voParam.getList(), voParam.getVersion());
        String buttonMark = voParam.getButtonMark();
        if (ButtonMark.CONFIRM.equals(buttonMark)) {
            // 插入确认状态
            String menuId = voParam.getMenuId();
            String stageIdentity = voParam.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId, stageIdentity);
        }
    }

    public void insertControlPlan(List<QqchDangerProcessControlPlan> list,BigDecimal version){
        version = VersionUtil.getVersion("qqch_danger_process_control_plan", version);
        List<QqchSafetyTrain> qqchSafetyTrainNew = new ArrayList<>();
        //获取8.9的针对8.3.3同步过去的数据
        QqchSafetyTrain qqchSafetyTrain = new QqchSafetyTrain();
//        qqchSafetyTrain.setPtVar2("2");
        QqchSafetyTrainVo vo = qqchSafetyTrainService.getQqchSafetyTrainList(qqchSafetyTrain);

        // 清空数据库表中数据
        QqchDangerProcessControlPlan deleteParam = new QqchDangerProcessControlPlan();
        deleteParam.setVersion(version);
        qqchDangerProcessControlPlanMapper.deleteQqchDangerProcessControlPlan(deleteParam);

        if (!CollectionUtils.isEmpty(list)) {
            for (QqchDangerProcessControlPlan qqchDangerProcessControlPlan : list) {
                qqchDangerProcessControlPlan.setId(IdWorker.createId());
                qqchDangerProcessControlPlan.setVersion(version);
                if (version.compareTo(BigDecimal.ONE) == 0) {
                    qqchDangerProcessControlPlan.setValid(Valid.YES);
                }
                qqchDangerProcessControlPlan.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                qqchDangerProcessControlPlan.setCreateUserName(SecurityUtils.getUserName());
                qqchDangerProcessControlPlan.setCreateTime(DateUtils.getNowDate());
                if (qqchDangerProcessControlPlan.getPlanStartDate() != null && StringUtils.isBlank(qqchDangerProcessControlPlan.getPtVar2())) {
                    qqchDangerProcessControlPlan.setPtVar2(IdWorker.createId() + "");
                } else if (qqchDangerProcessControlPlan.getPlanStartDate() == null && StringUtils.isNotBlank(qqchDangerProcessControlPlan.getPtVar2())) {
                    qqchDangerProcessControlPlan.setPtVar2("");
                }

                if (StringUtils.isNotBlank(qqchDangerProcessControlPlan.getPtVar2())) {
                    //8.9若为空，直接组装
                    if (vo != null && vo.getQqchSafetyTrainList() != null && vo.getQqchSafetyTrainList().size() == 0) {
                        QqchSafetyTrain train = new QqchSafetyTrain();
                        train.setContent("危大工程专项培训");
                        train.setTrainType("危大工程专项培训");
                        train.setTime(FtDateUtils.getDateLastOneWeek(qqchDangerProcessControlPlan.getPlanStartDate()));
                        train.setPtVar2("2");
                        train.setPtVar1(qqchDangerProcessControlPlan.getPtVar2());
                        train.setUpdateTime(new Date());
                        qqchSafetyTrainNew.add(train);
                    } else {
                        //根据唯一标识获取获取8.9对应的数据，重新组装培训时间，队伍
                        List<QqchSafetyTrain> safetyTrains = vo.getQqchSafetyTrainList().stream().filter(item -> (StringUtils.isNotBlank(item.getPtVar1()) && item.getPtVar1().equals(qqchDangerProcessControlPlan.getPtVar2()))).collect(Collectors.toList());
                        if (ObjectNullUtil.isEmpty(safetyTrains)) {
                            QqchSafetyTrain train = new QqchSafetyTrain();
                            train.setContent("危大工程专项培训");
                            train.setTrainType("危大工程专项培训");
                            train.setTime(FtDateUtils.getDateLastOneWeek(qqchDangerProcessControlPlan.getPlanStartDate()));
                            train.setPtVar2("2");
                            train.setPtVar1(qqchDangerProcessControlPlan.getPtVar2());
                            train.setUpdateTime(new Date());
                            qqchSafetyTrainNew.add(train);
                        } else {
                            safetyTrains.forEach(item -> {
                                item.setContent("危大工程专项培训");
                                item.setTrainType("危大工程专项培训");
                                item.setTime(FtDateUtils.getDateLastOneWeek(qqchDangerProcessControlPlan.getPlanStartDate()));
                                if (item.getUpdateTime() == null) {
                                    item.setUpdateTime(new Date());
                                }
                            });
                            qqchSafetyTrainNew.addAll(safetyTrains);
                        }
                    }
                }
            }
            qqchDangerProcessControlPlanMapper.insertQqchDangerProcessControlPlanList(list);
            if (vo!=null&&vo.getQqchSafetyTrainList()!=null&&vo.getQqchSafetyTrainList().size()>0) {
                List<QqchSafetyTrain> safetyTrainsHave = vo.getQqchSafetyTrainList().stream().filter(item -> !"2".equals(item.getPtVar2())).collect(Collectors.toList());
                qqchSafetyTrainNew.addAll(safetyTrainsHave);
            }
            if (!ObjectNullUtil.isEmpty(qqchSafetyTrainNew)) {
                //        时间排序
                qqchSafetyTrainNew.sort((t1, t2) -> t2.getUpdateTime().compareTo(t1.getUpdateTime()));
                BigDecimal versionTrain = VersionUtil.getVersion("qqch_safety_train", vo.getVersion());
                qqchSafetyTrainService.insertQqchSafetyTrainList(qqchSafetyTrainNew,versionTrain);
            }
        }
    }

    /**
     * 同步数据(废弃)
     *
     * @param voParam
     */
    @Transactional
    public void syncData(QqchDangerProcessControlPlanVo voParam) {
        // 从数据库查出数据
        List<QqchDangerProcessControlPlan> dbList = this.getQqchDangerProcessControlPlanList(voParam.getVersion())
            .getList();

        // 清空数据库表中数据
        QqchDangerProcessControlPlan deleteParam = new QqchDangerProcessControlPlan();
        deleteParam.setVersion(voParam.getVersion());
        qqchDangerProcessControlPlanMapper.deleteQqchDangerProcessControlPlan(deleteParam);

        if (!CollectionUtils.isEmpty(voParam.getList())) {
            for (QqchDangerProcessControlPlan qqchDangerProcessControlPlan : voParam.getList()) {
                qqchDangerProcessControlPlan.setId(IdWorker.createId());
                qqchDangerProcessControlPlan.setVersion(voParam.getVersion());
                if (voParam.getVersion().compareTo(BigDecimal.ONE) == 0) {
                    qqchDangerProcessControlPlan.setValid(Valid.YES);
                }
                qqchDangerProcessControlPlan.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                qqchDangerProcessControlPlan.setCreateUserName(SecurityUtils.getUserName());
                qqchDangerProcessControlPlan.setCreateTime(DateUtils.getNowDate());

                if (!CollectionUtils.isEmpty(dbList)) {
                    for (QqchDangerProcessControlPlan db : dbList) {
                        if (db.getSchemeCode().equals(qqchDangerProcessControlPlan.getSchemeCode())) {
                            qqchDangerProcessControlPlan.setAssistUnit(db.getAssistUnit());
                            qqchDangerProcessControlPlan.setPlanStartDate(db.getPlanStartDate());
                            qqchDangerProcessControlPlan.setSchemeFinalizeDate(db.getSchemeFinalizeDate());
                            qqchDangerProcessControlPlan.setThirdDisclosureDate(db.getThirdDisclosureDate());
                            qqchDangerProcessControlPlan.setSafeTrainDate(db.getSafeTrainDate());
                            qqchDangerProcessControlPlan.setPreClassSpeechDate(db.getPreClassSpeechDate());
                            qqchDangerProcessControlPlan.setLeaderExamineFrequency(db.getLeaderExamineFrequency());
                            qqchDangerProcessControlPlan.setLeaderFileGroupId(db.getLeaderFileGroupId());
                            qqchDangerProcessControlPlan.setSpecialExamineFrequency(db.getSpecialExamineFrequency());
                            qqchDangerProcessControlPlan.setFileGroupId(db.getFileGroupId());
                            qqchDangerProcessControlPlan.setWhetherFirst(db.getWhetherFirst());
                        }
                    }
                }
            }
            qqchDangerProcessControlPlanMapper.insertQqchDangerProcessControlPlanList(voParam.getList());
        }
    }
}
