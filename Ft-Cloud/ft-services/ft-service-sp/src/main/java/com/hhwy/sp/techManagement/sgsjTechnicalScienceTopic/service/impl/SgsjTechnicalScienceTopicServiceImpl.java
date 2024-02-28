package com.hhwy.sp.techManagement.sgsjTechnicalScienceTopic.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.hhwy.common.core.domain.R;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.enums.FlowEnum;
import com.hhwy.feign.service.SystemServiceApi;
import com.hhwy.sp.common.FlowInfoSearchUtil;
import com.hhwy.sp.common.constant.BelongBusiness;
import com.hhwy.sp.common.sgjsAchievementAward.domain.SgjsAchievementAward;
import com.hhwy.sp.common.sgjsAchievementAward.service.ISgjsAchievementAwardService;
import com.hhwy.sp.common.sgjsExpertLibrary.domain.SgjsExpertLibrary;
import com.hhwy.sp.common.sgjsExpertLibrary.service.ISgjsExpertLibraryService;
import com.hhwy.sp.common.sgjsAuthenticateEvaluate.domain.SgjsAuthenticateEvaluate;
import com.hhwy.sp.common.sgjsAuthenticateEvaluate.service.ISgjsAuthenticateEvaluateService;
import com.hhwy.sp.techManagement.sgsjTechnicalScienceTopic.domain.SgsjTechnicalScienceTopic;
import com.hhwy.sp.techManagement.sgsjTechnicalScienceTopic.domain.SgsjTechnicalScienceTopicDTO;
import com.hhwy.sp.techManagement.sgsjTechnicalScienceTopic.mapper.SgsjTechnicalScienceTopicMapper;
import com.hhwy.sp.techManagement.sgsjTechnicalScienceTopic.service.ISgsjTechnicalScienceTopicService;
import com.hhwy.sp.techManagement.sgsjTechnicalScienceTopic.sgsjTechnicalScienceTopicModify.domain.SgsjTechnicalScienceTopicModify;
import com.hhwy.sp.techManagement.sgsjTechnicalScienceTopic.sgsjTechnicalScienceTopicModify.service.ISgsjTechnicalScienceTopicModifyService;
import com.hhwy.system.api.domain.SysUser;
import com.hhwy.utils.common.CommonBaseEntity;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 功能描述: 科技管理 - 科研课题研发管理
 *
 * @author fsd
 * @date 2024-01-29 14:11:17
 * @remark
 */
@Service
public class SgsjTechnicalScienceTopicServiceImpl implements ISgsjTechnicalScienceTopicService {

    @Autowired
    private SgsjTechnicalScienceTopicMapper sgsjTechnicalScienceTopicMapper;

    @Autowired
    private ISgjsAchievementAwardService sgjsAchievementAwardService;
    @Autowired
    private ISgjsAuthenticateEvaluateService shjsAuthenticateEvaluateService;
    @Autowired
    private ISgjsExpertLibraryService sgjsExpertLibraryService;
    @Autowired
    private ISgsjTechnicalScienceTopicModifyService technicalScienceTopicModifyService;

    @Autowired
    private SystemServiceApi systemServiceApi;

    public SgsjTechnicalScienceTopic getSgsjTechnicalScienceTopic(SgsjTechnicalScienceTopic sgsjTechnicalScienceTopic) {
        return sgsjTechnicalScienceTopicMapper.getSgsjTechnicalScienceTopic(sgsjTechnicalScienceTopic);
    }

    /***
     * 功能描述: 查询
     */
    public List<SgsjTechnicalScienceTopic> getSgsjTechnicalScienceTopicList(SgsjTechnicalScienceTopic sgsjTechnicalScienceTopic) {
        List<SgsjTechnicalScienceTopic> resultList = sgsjTechnicalScienceTopicMapper.getSgsjTechnicalScienceTopicList(sgsjTechnicalScienceTopic);
        if (CollUtil.isEmpty(resultList)) return Collections.emptyList();
        Long[] ids = resultList.stream().map(SgsjTechnicalScienceTopic::getId).toArray(Long[]::new);
        List<SgjsAchievementAward> awardList = sgjsAchievementAwardService.getListByForeignIds(ids);
        Map<Long, List<SgjsAchievementAward>> awardMap = new HashMap<>();
        if (CollUtil.isNotEmpty(awardList)) {
            awardMap = awardList.stream().collect(Collectors.groupingBy(SgjsAchievementAward::getForeignId));
        }
        List<SgjsAuthenticateEvaluate> evaluateList = shjsAuthenticateEvaluateService.getListByForeignIds(ids);
        Map<Long, List<SgjsAuthenticateEvaluate>> evaluateMap = new HashMap<>();
        if (CollUtil.isNotEmpty(evaluateList)) {
            evaluateMap = evaluateList.stream().collect(Collectors.groupingBy(SgjsAuthenticateEvaluate::getForeignId));
        }
        for (SgsjTechnicalScienceTopic bean : resultList) {
            if (awardMap.containsKey(bean.getId())) {
                bean.setAwardList(awardMap.get(bean.getId()));
            }
            if (evaluateMap.containsKey(bean.getId())) {
                bean.setEvaluateList(evaluateMap.get(bean.getId()));
            }
        }
        //获取流程信息  立项流程
        resultList.forEach(p -> {
            p.setPtVar3(String.valueOf(p.getId()));
            p.setId(Long.valueOf(p.getPtVar2()));
        });
        FlowInfoSearchUtil.getFlowInfo(resultList, FlowEnum.SGJS_TECH_SCIENCE_TOPIC_LX);
        resultList.forEach(p -> p.setId(Long.valueOf(p.getPtVar3())));
        return resultList;
    }

    /***
     * 功能描述: 课题立项里的保存
     */
    @Transactional
    public SgsjTechnicalScienceTopic insertSgsjTechnicalScienceTopic(SgsjTechnicalScienceTopic sgsjTechnicalScienceTopic) {
        Assert.isTrue(sgsjTechnicalScienceTopic != null, "请求参数缺失");
        Assert.isTrue(sgsjTechnicalScienceTopic.getId() != null, "id不能为空");
        //保存子表
        Long id = sgsjTechnicalScienceTopic.getId();
        //专家库
        List<SgjsExpertLibrary> listAcceptance = sgsjTechnicalScienceTopic.getListAcceptance();
        List<SgjsExpertLibrary> listOutline = sgsjTechnicalScienceTopic.getListOutline();
        List<SgjsExpertLibrary> listTopic = sgsjTechnicalScienceTopic.getListTopic();
        sgjsExpertLibraryService.saveExpertLibrary(id, BelongBusiness.BELONG_BUSINESS_4, listAcceptance);
        sgjsExpertLibraryService.saveExpertLibrary(id, BelongBusiness.BELONG_BUSINESS_3, listOutline);
        sgjsExpertLibraryService.saveExpertLibrary(id, BelongBusiness.BELONG_BUSINESS_2, listTopic);
        //成果
        List<SgjsAchievementAward> awardList = sgsjTechnicalScienceTopic.getAwardList();
        sgjsAchievementAwardService.saveAchievementAward(id, BelongBusiness.BELONG_BUSINESS_9, awardList);
        //鉴定或评价
        List<SgjsAuthenticateEvaluate> evaluateList = sgsjTechnicalScienceTopic.getEvaluateList();
        shjsAuthenticateEvaluateService.saveEvaluate(id, BelongBusiness.BELONG_BUSINESS_9, evaluateList);
        //保存主表
        sgsjTechnicalScienceTopicMapper.updateSgsjTechnicalScienceTopic(sgsjTechnicalScienceTopic);
        //保存修改记录
        SgsjTechnicalScienceTopic param = new SgsjTechnicalScienceTopic();
        param.setId(sgsjTechnicalScienceTopic.getId());
        SgsjTechnicalScienceTopic oldData = sgsjTechnicalScienceTopicMapper.getSgsjTechnicalScienceTopic(param);
        List<SgsjTechnicalScienceTopicModify> modifyList = compareToObj(oldData, sgsjTechnicalScienceTopic);
        //返回结果获取
        SgsjTechnicalScienceTopic sgsjTechnicalScienceTopic1 = new SgsjTechnicalScienceTopic();
        sgsjTechnicalScienceTopic1.setId(id);
        SgsjTechnicalScienceTopic result = sgsjTechnicalScienceTopicMapper.getSgsjTechnicalScienceTopic(sgsjTechnicalScienceTopic1);
        if (CollUtil.isEmpty(modifyList)) {
            return result;
        }
        SysUser sysUser = SecurityUtils.getSysUser();
        modifyList.forEach(p ->{
            p.setTaskNode(sgsjTechnicalScienceTopic.getTaskStatus());
            p.setTopicNode(sgsjTechnicalScienceTopic.getTopicCurentNode());
            p.setModifyDatetime(DateUtils.getNowDate());
            p.setModifyPerson(String.valueOf(sysUser.getUserId()));
            p.setModifyPersionName(sysUser.getNickName());
            p.setId(IdWorker.createId());
            p.setCreateUser(sysUser.getUserName());
            p.setCreateTime(DateUtils.getNowDate());
        });
        technicalScienceTopicModifyService.insertSgsjTechnicalScienceTopicModifyList(modifyList);
        return result;
    }

    /***
     * 功能描述: 课题申请里的保存
     */
    @Transactional
    public void applyAdd(SgsjTechnicalScienceTopic sgsjTechnicalScienceTopic) {
        if (sgsjTechnicalScienceTopic == null) {
            return;
        }
        //保存
        if (sgsjTechnicalScienceTopic.getId() == null){
            //保存主表
            sgsjTechnicalScienceTopic.setId(IdWorker.createId());
            sgsjTechnicalScienceTopic.setPtVar1(String.valueOf(IdWorker.createId()));
            sgsjTechnicalScienceTopic.setPtVar2(String.valueOf(IdWorker.createId()));
            sgsjTechnicalScienceTopic.setCreateUser(SecurityUtils.getUserName());
            sgsjTechnicalScienceTopic.setCreateTime(DateUtils.getNowDate());
            sgsjTechnicalScienceTopicMapper.insertSgsjTechnicalScienceTopic(sgsjTechnicalScienceTopic);
        }else {
            //修改
            sgsjTechnicalScienceTopicMapper.updateSgsjTechnicalScienceTopic(sgsjTechnicalScienceTopic);
        }
        //保存子表
        Long id = sgsjTechnicalScienceTopic.getId();
        List<SgjsExpertLibrary> libraryList = sgsjTechnicalScienceTopic.getListApply();
        sgjsExpertLibraryService.saveExpertLibrary(id, BelongBusiness.BELONG_BUSINESS_1, libraryList);
    }

    @Transactional
    public int insertSgsjTechnicalScienceTopicList(List<SgsjTechnicalScienceTopic> sgsjTechnicalScienceTopicList) {
        for (SgsjTechnicalScienceTopic sgsjTechnicalScienceTopic : sgsjTechnicalScienceTopicList) {
            sgsjTechnicalScienceTopic.setId(IdWorker.createId());
            sgsjTechnicalScienceTopic.setCreateUser(SecurityUtils.getUserName());
            sgsjTechnicalScienceTopic.setCreateTime(DateUtils.getNowDate());
        }
        return sgsjTechnicalScienceTopicMapper.insertSgsjTechnicalScienceTopicList(sgsjTechnicalScienceTopicList);
    }

    @Transactional
    public int updateSgsjTechnicalScienceTopic(SgsjTechnicalScienceTopic sgsjTechnicalScienceTopic) {
        sgsjTechnicalScienceTopic.setUpdateUser(SecurityUtils.getUserName());
        sgsjTechnicalScienceTopic.setUpdateTime(DateUtils.getNowDate());
        return sgsjTechnicalScienceTopicMapper.updateSgsjTechnicalScienceTopic(sgsjTechnicalScienceTopic);
    }

    @Transactional
    public int updateSgsjTechnicalScienceTopicList(List<SgsjTechnicalScienceTopic> sgsjTechnicalScienceTopicList) {
        for (SgsjTechnicalScienceTopic sgsjTechnicalScienceTopic : sgsjTechnicalScienceTopicList) {
            sgsjTechnicalScienceTopic.setUpdateUser(SecurityUtils.getUserName());
            sgsjTechnicalScienceTopic.setUpdateTime(DateUtils.getNowDate());
        }
        return sgsjTechnicalScienceTopicMapper.updateSgsjTechnicalScienceTopicList(sgsjTechnicalScienceTopicList);
    }

    @Transactional
    public int deleteSgsjTechnicalScienceTopic(SgsjTechnicalScienceTopic sgsjTechnicalScienceTopic) {
        sgsjTechnicalScienceTopic.setUpdateUser(SecurityUtils.getUserName());
        sgsjTechnicalScienceTopic.setUpdateTime(DateUtils.getNowDate());
        return sgsjTechnicalScienceTopicMapper.deleteSgsjTechnicalScienceTopic(sgsjTechnicalScienceTopic);
    }

    @Transactional
    public int deleteSgsjTechnicalScienceTopicByPks(List<Long> sgsjTechnicalScienceTopicPkList) {
        return sgsjTechnicalScienceTopicMapper.deleteSgsjTechnicalScienceTopicByPks(sgsjTechnicalScienceTopicPkList);
    }

    @Transactional
    @Override
    public void deleteById(SgsjTechnicalScienceTopic sgsjTechnicalScienceTopicParam) {
        sgsjTechnicalScienceTopicMapper.deleteById(sgsjTechnicalScienceTopicParam);
        Long id = sgsjTechnicalScienceTopicParam.getId();
        sgjsExpertLibraryService.deleteSgjsExpertLibraryByForeignId(id);
        sgjsAchievementAwardService.deleteSgjsAchievementAwardByForeignId(id);
        SgjsAuthenticateEvaluate shjsAuthenticateEvaluate = new SgjsAuthenticateEvaluate();
        shjsAuthenticateEvaluate.setForeignId(id);
        shjsAuthenticateEvaluateService.deleteShjsAuthenticateEvaluate(shjsAuthenticateEvaluate);
    }

    /**
     * 功能描述: 申请明细
     */
    @Override
    public SgsjTechnicalScienceTopic applyDetail(SgsjTechnicalScienceTopic param) {
        SgsjTechnicalScienceTopic resultBean = sgsjTechnicalScienceTopicMapper.getSgsjTechnicalScienceTopic(param);
        Assert.isTrue(resultBean != null, "课题不存在,请检查参数是否正确");
        Long id = resultBean.getId();
        //知识库
        List<SgjsExpertLibrary> listByForeignId = sgjsExpertLibraryService.getListByForeignId(id);
        if (CollUtil.isNotEmpty(listByForeignId)) {
            Map<String, List<SgjsExpertLibrary>> collect = listByForeignId.stream().collect(Collectors.groupingBy(SgjsExpertLibrary::getBelongBusiness));
            resultBean.setListApply(collect.get(BelongBusiness.BELONG_BUSINESS_1));
        }
        //获取流程信息
        if (StrUtil.isNotBlank(resultBean.getPtVar1())) {
            SgsjTechnicalScienceTopic sgsjTechnicalScienceTopic = new SgsjTechnicalScienceTopic();
            sgsjTechnicalScienceTopic.setId(Long.valueOf(resultBean.getPtVar1()));
            FlowInfoSearchUtil.getFlowInfo(sgsjTechnicalScienceTopic, FlowEnum.SGJS_TECH_SCIENCE_TOPIC);
            sgsjTechnicalScienceTopic.setId(null);
            BeanUtil.copyProperties(sgsjTechnicalScienceTopic, resultBean, CopyOptions.create(CommonBaseEntity.class, true));
        }
        return resultBean;
    }

    /**
     * 功能描述: 立项明细
     */
    @Override
    public SgsjTechnicalScienceTopic getDetail(SgsjTechnicalScienceTopic param) {
        SgsjTechnicalScienceTopic resultBean = sgsjTechnicalScienceTopicMapper.getSgsjTechnicalScienceTopic(param);
        Assert.isTrue(resultBean != null, "课题不存在,请检查参数是否正确");
        Long id = resultBean.getId();
        //鉴定/评价
        resultBean.setEvaluateList(shjsAuthenticateEvaluateService.getListByForeignIds(new Long[]{id}));
        //成果
        resultBean.setAwardList(sgjsAchievementAwardService.getListByForeignId(id));
        //知识库
        List<SgjsExpertLibrary> listByForeignId = sgjsExpertLibraryService.getListByForeignId(id);
        if (CollUtil.isNotEmpty(listByForeignId)) {
            Map<String, List<SgjsExpertLibrary>> collect = listByForeignId.stream().collect(Collectors.groupingBy(SgjsExpertLibrary::getBelongBusiness));
            resultBean.setListTopic(collect.get(BelongBusiness.BELONG_BUSINESS_2));
            resultBean.setListOutline(collect.get(BelongBusiness.BELONG_BUSINESS_3));
            resultBean.setListAcceptance(collect.get(BelongBusiness.BELONG_BUSINESS_4));
        }
        //获取流程信息
        if (StrUtil.isNotBlank(resultBean.getPtVar2())) {
            SgsjTechnicalScienceTopic sgsjTechnicalScienceTopic = new SgsjTechnicalScienceTopic();
            sgsjTechnicalScienceTopic.setId(Long.valueOf(resultBean.getPtVar2()));
            FlowInfoSearchUtil.getFlowInfo(sgsjTechnicalScienceTopic, FlowEnum.SGJS_TECH_SCIENCE_TOPIC_LX);
            sgsjTechnicalScienceTopic.setId(null);
            BeanUtil.copyProperties(sgsjTechnicalScienceTopic, resultBean, CopyOptions.create(CommonBaseEntity.class, true));
        }
        return resultBean;
    }


    //修改记录判断
    private List<SgsjTechnicalScienceTopicModify> compareToObj(SgsjTechnicalScienceTopic oldData, SgsjTechnicalScienceTopic newData) {
        List<SgsjTechnicalScienceTopicModify> objects = new ArrayList<>();
        if (!compareStr(oldData.getApplyState(), newData.getApplyState())) {
            SgsjTechnicalScienceTopicModify differData = new SgsjTechnicalScienceTopicModify();
            differData.setModifyContent("申请状态");
            differData.setAfterModify(StrUtil.isBlank(oldData.getApplyState())?"":oldData.getApplyState());
            differData.setBeforeModify(StrUtil.isBlank(newData.getApplyState())?"":newData.getApplyState());
            objects.add(differData);
        }
        if (!compareStr(oldData.getTaskStatus(), newData.getTaskStatus())){
            SgsjTechnicalScienceTopicModify differData = new SgsjTechnicalScienceTopicModify();
            differData.setModifyContent("当前状态");
            differData.setAfterModify(StrUtil.isBlank(oldData.getTaskStatus())?"":oldData.getTaskStatus());
            differData.setBeforeModify(StrUtil.isBlank(newData.getTaskStatus())?"":newData.getTaskStatus());
            objects.add(differData);
        }
        if (!compareStr(oldData.getHandlePerson(), newData.getHandlePerson())) {
            SgsjTechnicalScienceTopicModify differData = new SgsjTechnicalScienceTopicModify();
            differData.setModifyContent("当前经办人");
            differData.setAfterModify(StrUtil.isBlank(oldData.getHandlePerson())?"":oldData.getHandlePerson());
            differData.setBeforeModify(StrUtil.isBlank(newData.getHandlePerson())?"":newData.getHandlePerson());
            objects.add(differData);
        }
        if (!compareStr(oldData.getTopicCode(), newData.getTopicCode())) {
            SgsjTechnicalScienceTopicModify differData = new SgsjTechnicalScienceTopicModify();
            differData.setModifyContent("课题编号");
            differData.setAfterModify(StrUtil.isBlank(oldData.getTopicCode())?"":oldData.getTopicCode());
            differData.setBeforeModify(StrUtil.isBlank(newData.getTopicCode())?"":newData.getTopicCode());
            objects.add(differData);
        }
        if (!compareStr(oldData.getTopicName(), newData.getTopicName())) {
            SgsjTechnicalScienceTopicModify differData = new SgsjTechnicalScienceTopicModify();
            differData.setModifyContent("课题名称");
            differData.setAfterModify(StrUtil.isBlank(oldData.getTopicName())?"":oldData.getTopicName());
            differData.setBeforeModify(StrUtil.isBlank(newData.getTopicName())?"":newData.getTopicName());
            objects.add(differData);
        }
        if (!compareStr(oldData.getTopicCurentNode(), newData.getTopicCurentNode())) {
            SgsjTechnicalScienceTopicModify differData = new SgsjTechnicalScienceTopicModify();
            differData.setModifyContent("课题进度");
            differData.setAfterModify(StrUtil.isBlank(oldData.getTopicCurentNode())?"":oldData.getTopicCurentNode());
            differData.setBeforeModify(StrUtil.isBlank(newData.getTopicCurentNode())?"":newData.getTopicCurentNode());
            objects.add(differData);
        }
        if (!compareStr(oldData.getStartEndDate(), newData.getStartEndDate())) {
            SgsjTechnicalScienceTopicModify differData = new SgsjTechnicalScienceTopicModify();
            differData.setModifyContent("课题研发日期");
            differData.setAfterModify(StrUtil.isBlank(oldData.getStartEndDate())?"":oldData.getStartEndDate());
            differData.setBeforeModify(StrUtil.isBlank(newData.getStartEndDate())?"":newData.getStartEndDate());
            objects.add(differData);
        }
        if (!compareStr(oldData.getDutyPerson(), newData.getDutyPerson())) {
            SgsjTechnicalScienceTopicModify differData = new SgsjTechnicalScienceTopicModify();
            differData.setModifyContent("课题负责人id");
            differData.setAfterModify(StrUtil.isBlank(oldData.getDutyPerson())?"":oldData.getDutyPerson());
            differData.setBeforeModify(StrUtil.isBlank(newData.getDutyPerson())?"":newData.getDutyPerson());
            objects.add(differData);
        }
        if (!compareStr(oldData.getDutyPersonName(), newData.getDutyPersonName())) {
            SgsjTechnicalScienceTopicModify differData = new SgsjTechnicalScienceTopicModify();
            differData.setModifyContent("课题负责人");
            differData.setAfterModify(StrUtil.isBlank(oldData.getDutyPersonName())?"":oldData.getDutyPersonName());
            differData.setBeforeModify(StrUtil.isBlank(newData.getDutyPersonName())?"":newData.getDutyPersonName());
            objects.add(differData);
        }
        if (!compareStr(oldData.getTogetherUnit(), newData.getTogetherUnit())) {
            SgsjTechnicalScienceTopicModify differData = new SgsjTechnicalScienceTopicModify();
            differData.setModifyContent("协作单位");
            differData.setAfterModify(StrUtil.isBlank(oldData.getTogetherUnit())?"":oldData.getTogetherUnit());
            differData.setBeforeModify(StrUtil.isBlank(newData.getTogetherUnit())?"":newData.getTogetherUnit());
            objects.add(differData);
        }
        if (!compareStr(oldData.getTogetherUnitOther(), newData.getTogetherUnitOther())) {
            SgsjTechnicalScienceTopicModify differData = new SgsjTechnicalScienceTopicModify();
            differData.setModifyContent("其他协作单位");
            differData.setAfterModify(StrUtil.isBlank(oldData.getTogetherUnitOther())?"":oldData.getTogetherUnitOther());
            differData.setBeforeModify(StrUtil.isBlank(newData.getTogetherUnitOther())?"":newData.getTogetherUnitOther());
            objects.add(differData);
        }
        if (!compareBigDecimal(oldData.getRdCost(), newData.getRdCost())) {
            SgsjTechnicalScienceTopicModify differData = new SgsjTechnicalScienceTopicModify();
            differData.setModifyContent("研发预算（万元）");
            differData.setAfterModify(oldData.getRdCost() == null ? null : String.valueOf(oldData.getRdCost()));
            differData.setBeforeModify(newData.getRdCost() == null ? null : String.valueOf(newData.getRdCost()));
            objects.add(differData);
        }
        if (!compareBigDecimal(oldData.getAlreadyPayCost(), newData.getAlreadyPayCost())) {
            SgsjTechnicalScienceTopicModify differData = new SgsjTechnicalScienceTopicModify();
            differData.setModifyContent("已拨付经费（万元）");
            differData.setAfterModify(oldData.getAlreadyPayCost() == null ? null : String.valueOf(oldData.getAlreadyPayCost()));
            differData.setBeforeModify(newData.getAlreadyPayCost() == null ? null : String.valueOf(newData.getAlreadyPayCost()));
            objects.add(differData);
        }
        if (!compareBigDecimal(oldData.getLeftCost(), newData.getLeftCost())) {
            SgsjTechnicalScienceTopicModify differData = new SgsjTechnicalScienceTopicModify();
            differData.setModifyContent("剩余经费（万元）");
            differData.setAfterModify(oldData.getLeftCost() == null ? null : String.valueOf(oldData.getLeftCost()));
            differData.setBeforeModify(newData.getLeftCost() == null ? null : String.valueOf(newData.getLeftCost()));
            objects.add(differData);
        }
        if (!compareStr(oldData.getWriteInPerson(), newData.getWriteInPerson())) {
            SgsjTechnicalScienceTopicModify differData = new SgsjTechnicalScienceTopicModify();
            differData.setModifyContent("登记人id");
            differData.setAfterModify(StrUtil.isBlank(oldData.getWriteInPerson())?"":oldData.getWriteInPerson());
            differData.setBeforeModify(StrUtil.isBlank(newData.getWriteInPerson())?"":newData.getWriteInPerson());
            objects.add(differData);
        }
        if (!compareStr(oldData.getWriteInPersonName(), newData.getWriteInPersonName())) {
            SgsjTechnicalScienceTopicModify differData = new SgsjTechnicalScienceTopicModify();
            differData.setModifyContent("登记人");
            differData.setAfterModify(StrUtil.isBlank(oldData.getWriteInPersonName())?"":oldData.getWriteInPersonName());
            differData.setBeforeModify(StrUtil.isBlank(newData.getWriteInPersonName())?"":newData.getWriteInPersonName());
            objects.add(differData);
        }
        if (!compareStr(oldData.getWriteInPersonPhoneNum(), newData.getWriteInPersonPhoneNum())) {
            SgsjTechnicalScienceTopicModify differData = new SgsjTechnicalScienceTopicModify();
            differData.setModifyContent("登记人联系方式");
            differData.setAfterModify(StrUtil.isBlank(oldData.getWriteInPersonPhoneNum())?"":oldData.getWriteInPersonPhoneNum());
            differData.setBeforeModify(StrUtil.isBlank(newData.getWriteInPersonPhoneNum())?"":newData.getWriteInPersonPhoneNum());
            objects.add(differData);
        }
        if (!compareStr(oldData.getTopicSummary(), newData.getTopicSummary())) {
            SgsjTechnicalScienceTopicModify differData = new SgsjTechnicalScienceTopicModify();
            differData.setModifyContent("课题简介");
            differData.setAfterModify(StrUtil.isBlank(oldData.getTopicSummary())?"":oldData.getTopicSummary());
            differData.setBeforeModify(StrUtil.isBlank(newData.getTopicSummary())?"":newData.getTopicSummary());
            objects.add(differData);
        }
        if (!compareStr(oldData.getTopicFileGroupId(), newData.getTopicFileGroupId())) {
            SgsjTechnicalScienceTopicModify differData = new SgsjTechnicalScienceTopicModify();
            differData.setModifyContent("课题附件");
            differData.setAfterModify(StrUtil.isBlank(oldData.getTopicFileGroupId())?"":oldData.getTopicFileGroupId());
            differData.setBeforeModify(StrUtil.isBlank(newData.getTopicFileGroupId())?"":newData.getTopicFileGroupId());
            objects.add(differData);
        }
        if (!compareStr(oldData.getContractFileGroupId(), newData.getContractFileGroupId())) {
            SgsjTechnicalScienceTopicModify differData = new SgsjTechnicalScienceTopicModify();
            differData.setModifyContent("合同附件");
            differData.setAfterModify(StrUtil.isBlank(oldData.getContractFileGroupId())?"":oldData.getContractFileGroupId());
            differData.setBeforeModify(StrUtil.isBlank(newData.getContractFileGroupId())?"":newData.getContractFileGroupId());
            objects.add(differData);
        }
        if (!compareStr(oldData.getInspectFileGroupId(), newData.getInspectFileGroupId())) {
            SgsjTechnicalScienceTopicModify differData = new SgsjTechnicalScienceTopicModify();
            differData.setModifyContent("检查附件");
            differData.setAfterModify(StrUtil.isBlank(oldData.getInspectFileGroupId())?"":oldData.getInspectFileGroupId());
            differData.setBeforeModify(StrUtil.isBlank(newData.getInspectFileGroupId())?"":newData.getInspectFileGroupId());
            objects.add(differData);
        }
        if (!compareStr(oldData.getAcceptanceFileGroupId(), newData.getAcceptanceFileGroupId())) {
            SgsjTechnicalScienceTopicModify differData = new SgsjTechnicalScienceTopicModify();
            differData.setModifyContent("验收附件");
            differData.setAfterModify(StrUtil.isBlank(oldData.getAcceptanceFileGroupId())?"":oldData.getTopicFileGroupId());
            differData.setBeforeModify(StrUtil.isBlank(newData.getAcceptanceFileGroupId())?"":newData.getAcceptanceFileGroupId());
            objects.add(differData);
        }
        if (!compareStr(oldData.getTopicFileGroupId(), newData.getTopicFileGroupId())) {
            SgsjTechnicalScienceTopicModify differData = new SgsjTechnicalScienceTopicModify();
            differData.setModifyContent("课题附件");
            differData.setAfterModify(StrUtil.isBlank(oldData.getTopicFileGroupId())?"":oldData.getTopicFileGroupId());
            differData.setBeforeModify(StrUtil.isBlank(newData.getTopicFileGroupId())?"":newData.getTopicFileGroupId());
            objects.add(differData);
        }
        return objects;
    }

    private boolean compareStr(String s1, String s2){
        if (StrUtil.isBlank(s1) && StrUtil.isBlank(s2)){
            return true;
        }
        if (StrUtil.isNotBlank(s1) && StrUtil.isBlank(s2)){
            return false;
        }
        if (StrUtil.isBlank(s1) && StrUtil.isNotBlank(s2)){
            return false;
        }
        return s1.equals(s2);
    }

    private boolean compareBigDecimal(BigDecimal b1, BigDecimal b2){
        if ( b1 == null && b2 == null){
            return true;
        }
        if ( b1 != null && b2 == null){
            return false;
        }
        if ( b1 == null && b2 != null){
            return false;
        }
        return b1.equals(b2);
    }

    //知会消息发布
    @Override
    public AjaxResult messagePublic() {
        // todo 指定角色暂不确定
        String[] roles = {"area_handler", "regionDutyPerson", "common"};
        AjaxResult ajaxResult = systemServiceApi.selectByRoleAndTenant(roles, SecurityUtils.getTenantKey());
        Integer code = (Integer) ajaxResult.get("code");
        Assert.isTrue(code.equals(200), "获取用户列表失败");
        String s = JSON.toJSONString(ajaxResult.get("data"));
        List<SysUser> sysUsers = JSON.parseArray(s, SysUser.class);
        String clientIds = sysUsers.stream().map(SysUser::getUserName).collect(Collectors.joining(","));
        String topic = "system";
        // todo 消息体内容暂不确定
        String message = "";
        R r = systemServiceApi.batchPublish(clientIds, topic, message);
        if (r.getCode() == 200) {
            return AjaxResult.success("消息发布成功");
        }else {
            return AjaxResult.error("消息发布失败");
        }
    }

    //导出
    @Override
    public List<SgsjTechnicalScienceTopicDTO> export(SgsjTechnicalScienceTopic param) {
        List<SgsjTechnicalScienceTopicDTO> exportData = new ArrayList<>();
        //主表
        List<SgsjTechnicalScienceTopic> resultList = sgsjTechnicalScienceTopicMapper.getSgsjTechnicalScienceTopicList(param);
        if (CollUtil.isEmpty(resultList)) return new ArrayList<>();
        Long[] ids = resultList.stream().map(SgsjTechnicalScienceTopic::getId).toArray(Long[]::new);
        //成果
        List<SgjsAchievementAward> awardList = sgjsAchievementAwardService.getListByForeignIds(ids);
        Map<Long, List<SgjsAchievementAward>> awardMap = awardList.stream().collect(Collectors.groupingBy(SgjsAchievementAward::getForeignId));
        //鉴定、评价
        List<SgjsAuthenticateEvaluate> evaluateList = shjsAuthenticateEvaluateService.getListByForeignIds(ids);
        Map<Long, List<SgjsAuthenticateEvaluate>> evaluateMap = evaluateList.stream().collect(Collectors.groupingBy(SgjsAuthenticateEvaluate::getForeignId));
        if (CollUtil.isEmpty(awardList) && CollUtil.isEmpty(evaluateList)) {
            exportData = BeanUtil.copyToList(resultList, SgsjTechnicalScienceTopicDTO.class);
            resultList.clear();
        }
        for (SgsjTechnicalScienceTopic main : resultList) {
            List<SgjsAchievementAward> sgjsAchievementAwards = awardMap.get(main.getId());
            List<SgjsAuthenticateEvaluate> shjsAuthenticateEvaluates = evaluateMap.get(main.getId());
            int loopCount = 1;
            if (CollUtil.isNotEmpty(sgjsAchievementAwards) && CollUtil.isNotEmpty(shjsAuthenticateEvaluates)){
                loopCount = Math.max(sgjsAchievementAwards.size(), shjsAuthenticateEvaluates.size());
            }else if (CollUtil.isNotEmpty(shjsAuthenticateEvaluates)){
                loopCount = shjsAuthenticateEvaluates.size();
            }else if (CollUtil.isNotEmpty(sgjsAchievementAwards)) {
                loopCount = sgjsAchievementAwards.size();
            }
            for (int i = 0; i < loopCount; i++) {
                SgsjTechnicalScienceTopicDTO dto = new SgsjTechnicalScienceTopicDTO();
                BeanUtil.copyProperties(main, dto);
                if (CollUtil.isNotEmpty(sgjsAchievementAwards) && sgjsAchievementAwards.size() > i) {
                    SgjsAchievementAward aAchievementAward = sgjsAchievementAwards.get(i);
                    dto.setApplyAward(aAchievementAward.getApplyAward());
                    dto.setAwardGrade(aAchievementAward.getAwardGrade());
                    dto.setAwardType(aAchievementAward.getAwardType());
                    dto.setGrantUnit(aAchievementAward.getGrantUnit());
                    dto.setAwardTime(aAchievementAward.getAwardTime());
                }
                if (CollUtil.isNotEmpty(shjsAuthenticateEvaluates) && shjsAuthenticateEvaluates.size() > i) {
                    SgjsAuthenticateEvaluate shjsAuthenticateEvaluate = shjsAuthenticateEvaluates.get(i);
                    dto.setAuthenticateUnit(shjsAuthenticateEvaluate.getAuthenticateUnit());
                    dto.setAuthenticateDate(shjsAuthenticateEvaluate.getAuthenticateDate());
                    dto.setEvaluateConclusion(shjsAuthenticateEvaluate.getEvaluateConclusion());
                }
                exportData.add(dto);
            }
        }
        return exportData;
    }
}
