package com.hhwy.pm.qqch.sgch.qqchconst.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.hash.Hash;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.common.aspect.CompileAspect;
import com.hhwy.pm.qqch.common.aspect.CompileOptEnum;
import com.hhwy.pm.qqch.preparation.survey.qqchSurveyDesignTeams.domain.QqchSurveyDesignTeams;
import com.hhwy.pm.qqch.preparation.survey.qqchSurveyDesignTeams.domain.QqchSurveyEquPlan;
import com.hhwy.pm.qqch.preparation.survey.qqchSurveyDesignTeams.domain.QqchSurveyParam;
import com.hhwy.pm.qqch.preparation.survey.qqchSurveyDesignTeams.domain.QqchSurveyPersonPlan;
import com.hhwy.pm.qqch.preparation.survey.qqchSurveyDesignTeams.domain.vo.QqchSurveyDesignTeamsVo;
import com.hhwy.pm.qqch.preparation.survey.qqchSurveyDesignTeams.service.IQqchSurveyDesignTeamsService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.sgch.qqchLabourDemandPlan.domain.QqchLabourDemandPlan;
import com.hhwy.pm.qqch.sgch.qqchconst.domain.QqchConst;
import com.hhwy.pm.qqch.sgch.qqchconst.domain.QqchConstFacilityPlan;
import com.hhwy.pm.qqch.sgch.qqchconst.domain.QqchConstStaffPlan;
import com.hhwy.pm.qqch.sgch.qqchconst.mapper.QqchConstFacilityPlanMapper;
import com.hhwy.pm.qqch.sgch.qqchconst.mapper.QqchConstStaffPlanMapper;
import com.hhwy.pm.qqch.sgch.qqchconst.service.IQqchConstFacilityPlanService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author mls
 * @date 2023-08-03 16:08:12
 * @remark
 */
@Service
public class QqchConstFacilityPlanServiceImpl implements IQqchConstFacilityPlanService {


    private static final String TN = "qqch_const_facility_plan";
    @Autowired
    private QqchConstFacilityPlanMapper qqchConstFacilityPlanMapper;

    @Autowired
    private QqchConstStaffPlanMapper constStaffPlanMapper;

    @Autowired
    private IQqchSurveyDesignTeamsService surveyDesignTeamsService;
    @Autowired
    private IQqchReviewService qqchReviewService;


    public QqchConstFacilityPlan getQqchConstFacilityPlan(QqchConstFacilityPlan qqchConstFacilityPlan) {
        return qqchConstFacilityPlanMapper.getQqchConstFacilityPlan(qqchConstFacilityPlan);
    }

    public List<QqchConstFacilityPlan> getQqchConstFacilityPlanList(QqchConstFacilityPlan qqchConstFacilityPlan) {
        return qqchConstFacilityPlanMapper.getQqchConstFacilityPlanList(qqchConstFacilityPlan);
    }

    @Transactional
    public int insertQqchConstFacilityPlan(QqchConstFacilityPlan qqchConstFacilityPlan) {
        qqchConstFacilityPlan.setId(IdWorker.createId());
        qqchConstFacilityPlan.setCreateUser(SecurityUtils.getUserName());
        qqchConstFacilityPlan.setCreateTime(DateUtils.getNowDate());
        return qqchConstFacilityPlanMapper.insertQqchConstFacilityPlan(qqchConstFacilityPlan);
    }

    @Transactional
    public int insertQqchConstFacilityPlanList(List<QqchConstFacilityPlan> qqchConstFacilityPlanList) {
        for (QqchConstFacilityPlan qqchConstFacilityPlan : qqchConstFacilityPlanList) {
            qqchConstFacilityPlan.setId(IdWorker.createId());
            qqchConstFacilityPlan.setCreateUser(SecurityUtils.getUserName());
            qqchConstFacilityPlan.setCreateTime(DateUtils.getNowDate());
        }
        return qqchConstFacilityPlanMapper.insertQqchConstFacilityPlanList(qqchConstFacilityPlanList);
    }

    @Transactional
    public int updateQqchConstFacilityPlan(QqchConstFacilityPlan qqchConstFacilityPlan) {
        qqchConstFacilityPlan.setUpdateUser(SecurityUtils.getUserName());
        qqchConstFacilityPlan.setUpdateTime(DateUtils.getNowDate());
        return qqchConstFacilityPlanMapper.updateQqchConstFacilityPlan(qqchConstFacilityPlan);
    }

    @Transactional
    public int updateQqchConstFacilityPlanList(List<QqchConstFacilityPlan> qqchConstFacilityPlanList) {
        for (QqchConstFacilityPlan qqchConstFacilityPlan : qqchConstFacilityPlanList) {
            qqchConstFacilityPlan.setUpdateUser(SecurityUtils.getUserName());
            qqchConstFacilityPlan.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchConstFacilityPlanMapper.updateQqchConstFacilityPlanList(qqchConstFacilityPlanList);
    }

    @Transactional
    public int deleteQqchConstFacilityPlan(QqchConstFacilityPlan qqchConstFacilityPlan) {
        qqchConstFacilityPlan.setUpdateUser(SecurityUtils.getUserName());
        qqchConstFacilityPlan.setUpdateTime(DateUtils.getNowDate());
        return qqchConstFacilityPlanMapper.deleteQqchConstFacilityPlan(qqchConstFacilityPlan);
    }

    @Transactional
    public int deleteQqchConstFacilityPlanByPks(List<Long> qqchConstFacilityPlanPkList) {
        return qqchConstFacilityPlanMapper.deleteQqchConstFacilityPlanByPks(qqchConstFacilityPlanPkList);
    }

    @Override
    @CompileAspect(type = CompileOptEnum.SAVE_LIST, tableName = TN)
    public void saveList(List<QqchConstFacilityPlan> iFacList) {
        if (CollectionUtils.isEmpty(iFacList)) return;
        this.insertQqchConstFacilityPlanList(iFacList);
    }

    @Override
    @CompileAspect(type = CompileOptEnum.LIST, tableName = TN)
    public List<QqchConstFacilityPlan> list(QqchConstFacilityPlan dto) {
        return this.getQqchConstFacilityPlanList(dto);
    }

    @Override
    public QqchSurveyDesignTeamsVo dataSync(QqchSurveyDesignTeamsVo qqchSurveyDesignTeamsVo) {
        //界面已存在的数据
        List<QqchSurveyDesignTeams> pageData = qqchSurveyDesignTeamsVo.getQqchSurveyDesignTeamsList();
        //弹框勾选的数据
        List<QqchSurveyParam> selectData = qqchSurveyDesignTeamsVo.getParams();
        Map<String, List<QqchSurveyParam>> selectDataMap = selectData.stream()
                .filter(p -> p.getConstDesc() != null)
                .collect(Collectors.groupingBy(QqchSurveyParam::getConstDesc));

        HashMap<String, String> jobDutyMap = new HashMap<>();
        HashMap<String, QqchSurveyDesignTeams> constDscMap = new HashMap<>();
        List<QqchSurveyDesignTeams> resultList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(selectData)){
            for (QqchSurveyDesignTeams teams : pageData) {
                String teamName = teams.getTeamName();
                if (selectDataMap.containsKey(teamName)) { //当前数据被再次勾选，暂存已编辑数据
                    //保存界面中可以编辑的字段 “岗位职责”
                    List<QqchSurveyPersonPlan> personPlanList = teams.getQqchSurveyPersonPlanList();
                    if (CollectionUtil.isEmpty(personPlanList))
                        continue;
                    for (QqchSurveyPersonPlan plan : personPlanList) {
                        String jobDuty = plan.getJobDuty();
                        if (StringUtils.isEmpty(jobDuty)) {
                            continue;
                        }
                        //暂存已填写的岗位职责
                        jobDutyMap.put(plan.getJobNumber(), jobDuty);
                    }
                    //暂存已填写的主表信息
                    constDscMap.put(teams.getTeamName(), teams);
                }else { //当前数据未被勾选
                    //返回数据
                    resultList.add(teams);
                }
            }
        }

        Map<String, Map<String, List>> stringMapMap = queryDevicePlanListByConstDesc(selectData, null);
        Iterator<Map.Entry<String, Map<String, List>>> iterator = stringMapMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Map<String, List>> next = iterator.next();
            //班组
            String constDesc = next.getKey();
            //子表数据
            Map<String, List> value = next.getValue();
            //回填子表 - 岗位职责
            List<QqchSurveyPersonPlan> staffList = value.get("staffList");
            staffList.forEach(p -> {
                String jobDuty = jobDutyMap.get(p.getJobNumber());
                if (StringUtils.isNotBlank(jobDuty)){
                    p.setJobDuty(jobDuty);
                }
            });
            QqchSurveyDesignTeams qqchSurveyDesignTeams = new QqchSurveyDesignTeams();
            //回填主表 - 关联作业项和大纲提交时间
            QqchSurveyDesignTeams designTeams = constDscMap.get(constDesc);
            if (ObjectUtil.isNotEmpty(designTeams)) {
                qqchSurveyDesignTeams.setWbsName(designTeams.getWbsName());
                qqchSurveyDesignTeams.setWbsId(designTeams.getWbsId());
                qqchSurveyDesignTeams.setSubmitTime(designTeams.getSubmitTime());
            }
            List<QqchSurveyParam> qqchSurveyParams = selectDataMap.get(constDesc);
            if (ObjectUtil.isNotEmpty(qqchSurveyParams)) {
                qqchSurveyDesignTeams.setWorkContent(qqchSurveyParams.get(0).getWorkContent());
                qqchSurveyDesignTeams.setEnterTime(qqchSurveyParams.get(0).getEntryDate());
                qqchSurveyDesignTeams.setExitTime(qqchSurveyParams.get(0).getExitDate());
            }
            qqchSurveyDesignTeams.setTeamName(constDesc);
            qqchSurveyDesignTeams.setQqchSurveyEquPlanList(value.get("facilityPlanList"));
            qqchSurveyDesignTeams.setQqchSurveyPersonPlanList(staffList);
            resultList.add(qqchSurveyDesignTeams);
        }
//        surveyDesignTeamsService.save();
        QqchSurveyDesignTeamsVo vo = new QqchSurveyDesignTeamsVo();
        vo.setVersion(qqchSurveyDesignTeamsVo.getVersion());
        vo.setStageIdentity(qqchReviewService.getStage());
        vo.setQqchSurveyDesignTeamsList(resultList);
        return vo;
    }

    /***
     * 功能描述:  获取人员策划和设备策划，根据主表明细字段
     * @param qqchConst
     * @return com.hhwy.pm.qqch.sgch.qqchconst.domain.QqchConst
     * 作者: fushudong
     * 时间: 2023/10/11
     */
    @Override
    public Map<String, Map<String, List>> queryDevicePlanListByConstDesc(List<QqchSurveyParam> param, BigDecimal version) {
        if (CollectionUtil.isEmpty(param))
            return null;
        Map result = new HashMap<>();
        for (QqchSurveyParam qqchConst : param) {
            List<QqchSurveyPersonPlan> staffPlanByConstDesc = this.getStaffPlanByConstDesc(qqchConst, version);
            List<QqchSurveyEquPlan> facilityPlanByConstDesc = this.getFacilityPlanByConstDesc(qqchConst, version);
            HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
            objectObjectHashMap.put("staffList", staffPlanByConstDesc);
            objectObjectHashMap.put("facilityPlanList", facilityPlanByConstDesc);
            result.put(qqchConst.getConstDesc(), objectObjectHashMap);
        }
        return result;
    }
    private List<QqchSurveyPersonPlan> getStaffPlanByConstDesc(QqchSurveyParam qqchConst, BigDecimal version) {
        Map<String, List<QqchSurveyPersonPlan>> resultMap = new HashMap<>();
        List<QqchSurveyPersonPlan> resultList = new ArrayList<>();
        Long masterId13 = qqchConst.getMasterId13();
        Long masterId213 = qqchConst.getMasterId213();
        List<QqchConstStaffPlan> staffPlanByConstDesc = constStaffPlanMapper.getStaffPlanByConstDesc(masterId13, masterId213, version);
        staffPlanByConstDesc.forEach(bean -> {
            QqchSurveyPersonPlan qqchSurveyPersonPlan = new QqchSurveyPersonPlan();
            qqchSurveyPersonPlan.setJobName(bean.getOccupationName());
            qqchSurveyPersonPlan.setJobDuty(bean.getPtVar3());
            qqchSurveyPersonPlan.setJobNumber(bean.getOccupationCode());
            qqchSurveyPersonPlan.setPersonNum(bean.getTotalCount());
            qqchSurveyPersonPlan.setPlannedInDays(bean.getSiteDays()==null?0.0:bean.getSiteDays());
            resultList.add(qqchSurveyPersonPlan);
        });
//        resultMap.put("staffList", resultList);
        return resultList;
    }
    private List<QqchSurveyEquPlan> getFacilityPlanByConstDesc(QqchSurveyParam qqchConst, BigDecimal version) {
        Map<String, List<QqchSurveyEquPlan>> resultMap = new HashMap<>();
        List<QqchSurveyEquPlan> resultList = new ArrayList<>();
        Long masterId13 = qqchConst.getMasterId13();
        Long masterId213 = qqchConst.getMasterId213();
        List<QqchConstFacilityPlan> facilityPlanByConstDesc = qqchConstFacilityPlanMapper.getFacilityPlanByConstDesc(masterId13, masterId213, version);
        facilityPlanByConstDesc.forEach(bean -> {
            QqchSurveyEquPlan qqchSurveyEquPlan = new QqchSurveyEquPlan();
            qqchSurveyEquPlan.setEquCode(bean.getFacilityCode());
            qqchSurveyEquPlan.setEquName(bean.getFacilityName());
            qqchSurveyEquPlan.setEquSpec(bean.getSpecificationModel());
            qqchSurveyEquPlan.setUnit(bean.getUnits());
            BigDecimal bigDecimal = new BigDecimal(bean.getCount()==null? "0": String.valueOf(bean.getCount()));
            qqchSurveyEquPlan.setNum(bigDecimal);
            qqchSurveyEquPlan.setPlannedInDays(bean.getSiteDays()==null?0.0:bean.getSiteDays());
            resultList.add(qqchSurveyEquPlan);
        });
//        resultMap.put("facilityPlanList", resultList);
        return resultList;
    }
}
