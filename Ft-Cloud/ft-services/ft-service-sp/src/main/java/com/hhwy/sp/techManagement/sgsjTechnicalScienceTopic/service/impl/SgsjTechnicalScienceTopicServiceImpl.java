package com.hhwy.sp.techManagement.sgsjTechnicalScienceTopic.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
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
import com.hhwy.sp.common.shjsAuthenticateEvaluate.domain.ShjsAuthenticateEvaluate;
import com.hhwy.sp.common.shjsAuthenticateEvaluate.service.IShjsAuthenticateEvaluateService;
import com.hhwy.sp.techManagement.sgsjTechnicalScienceTopic.domain.SgsjTechnicalScienceTopic;
import com.hhwy.sp.techManagement.sgsjTechnicalScienceTopic.domain.SgsjTechnicalScienceTopicDTO;
import com.hhwy.sp.techManagement.sgsjTechnicalScienceTopic.mapper.SgsjTechnicalScienceTopicMapper;
import com.hhwy.sp.techManagement.sgsjTechnicalScienceTopic.service.ISgsjTechnicalScienceTopicService;
import com.hhwy.sp.techManagement.sgsjTechnicalScienceTopic.sgsjTechnicalScienceTopicModify.domain.SgsjTechnicalScienceTopicModify;
import com.hhwy.sp.techManagement.sgsjTechnicalScienceTopic.sgsjTechnicalScienceTopicModify.service.ISgsjTechnicalScienceTopicModifyService;
import com.hhwy.sp.utils.easyExcel.CustomMergeStrategy;
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
import java.util.stream.Stream;

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
    private IShjsAuthenticateEvaluateService shjsAuthenticateEvaluateService;
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
        List<ShjsAuthenticateEvaluate> evaluateList = shjsAuthenticateEvaluateService.getListByForeignIds(ids);
        Map<Long, List<ShjsAuthenticateEvaluate>> evaluateMap = new HashMap<>();
        if (CollUtil.isNotEmpty(evaluateList)) {
            evaluateMap = evaluateList.stream().collect(Collectors.groupingBy(ShjsAuthenticateEvaluate::getForeignId));
        }
        for (SgsjTechnicalScienceTopic bean : resultList) {
            if (awardMap.containsKey(bean.getId())) {
                bean.setAwardList(awardMap.get(bean.getId()));
            }
            if (evaluateMap.containsKey(bean.getId())) {
                bean.setEvaluateList(evaluateMap.get(bean.getId()));
            }
        }
        return resultList;
    }

    /***
     * 功能描述: 课题立项里的保存
     */
    @Transactional
    public int insertSgsjTechnicalScienceTopic(SgsjTechnicalScienceTopic sgsjTechnicalScienceTopic) {
        Assert.isTrue(sgsjTechnicalScienceTopic != null, "参数异常");
        Assert.isTrue(sgsjTechnicalScienceTopic.getId() != null, "参数异常");
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
        List<ShjsAuthenticateEvaluate> evaluateList = sgsjTechnicalScienceTopic.getEvaluateList();
        shjsAuthenticateEvaluateService.saveEvaluate(id, BelongBusiness.BELONG_BUSINESS_9, evaluateList);
        //保存主表
        sgsjTechnicalScienceTopic.setId(IdWorker.createId());
//        sgsjTechnicalScienceTopic.setPtVar2(String.valueOf(IdWorker.createId()));
        sgsjTechnicalScienceTopic.setCreateUser(SecurityUtils.getUserName());
        sgsjTechnicalScienceTopic.setCreateTime(DateUtils.getNowDate());
        sgsjTechnicalScienceTopicMapper.insertSgsjTechnicalScienceTopic(sgsjTechnicalScienceTopic);
        //保存修改记录
        SgsjTechnicalScienceTopic param = new SgsjTechnicalScienceTopic();
        param.setId(sgsjTechnicalScienceTopic.getId());
        SgsjTechnicalScienceTopic oldData = sgsjTechnicalScienceTopicMapper.getSgsjTechnicalScienceTopic(param);
        List<SgsjTechnicalScienceTopicModify> modifyList = compareToObj(oldData, sgsjTechnicalScienceTopic);
        if (CollUtil.isEmpty(modifyList)) {
            return 1;
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
        return 1;
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
        ShjsAuthenticateEvaluate shjsAuthenticateEvaluate = new ShjsAuthenticateEvaluate();
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
            differData.setAfterModify(oldData.getApplyState());
            differData.setBeforeModify(newData.getApplyState());
            objects.add(differData);
        }
        if (!compareStr(oldData.getTaskStatus(), newData.getTaskStatus())){
            SgsjTechnicalScienceTopicModify differData = new SgsjTechnicalScienceTopicModify();
            differData.setModifyContent("当前状态");
            differData.setAfterModify(oldData.getTaskStatus());
            differData.setBeforeModify(newData.getTaskStatus());
            objects.add(differData);
        }
        if (!compareStr(oldData.getHandlePerson(), newData.getHandlePerson())) {
            SgsjTechnicalScienceTopicModify differData = new SgsjTechnicalScienceTopicModify();
            differData.setModifyContent("当前经办人");
            differData.setAfterModify(oldData.getHandlePerson());
            differData.setBeforeModify(newData.getHandlePerson());
            objects.add(differData);
        }
        if (!compareStr(oldData.getTopicCode(), newData.getTopicCode())) {
            SgsjTechnicalScienceTopicModify differData = new SgsjTechnicalScienceTopicModify();
            differData.setModifyContent("课题编号");
            differData.setAfterModify(oldData.getTopicCode());
            differData.setBeforeModify(newData.getTopicCode());
            objects.add(differData);
        }
        if (!compareStr(oldData.getTopicName(), newData.getTopicName())) {
            SgsjTechnicalScienceTopicModify differData = new SgsjTechnicalScienceTopicModify();
            differData.setModifyContent("课题名称");
            differData.setAfterModify(oldData.getTopicName());
            differData.setBeforeModify(newData.getTopicName());
            objects.add(differData);
        }
        if (!compareStr(oldData.getTopicCurentNode(), newData.getTopicCurentNode())) {
            SgsjTechnicalScienceTopicModify differData = new SgsjTechnicalScienceTopicModify();
            differData.setModifyContent("课题进度");
            differData.setAfterModify(oldData.getTopicCurentNode());
            differData.setBeforeModify(newData.getTopicCurentNode());
            objects.add(differData);
        }
        if (!compareStr(oldData.getStartEndDate(), newData.getStartEndDate())) {
            SgsjTechnicalScienceTopicModify differData = new SgsjTechnicalScienceTopicModify();
            differData.setModifyContent("课题研发日期");
            differData.setAfterModify(oldData.getStartEndDate());
            differData.setBeforeModify(newData.getStartEndDate());
            objects.add(differData);
        }
        if (!compareStr(oldData.getDutyPerson(), newData.getDutyPerson())) {
            SgsjTechnicalScienceTopicModify differData = new SgsjTechnicalScienceTopicModify();
            differData.setModifyContent("课题负责人id");
            differData.setAfterModify(oldData.getDutyPerson());
            differData.setBeforeModify(newData.getDutyPerson());
            objects.add(differData);
        }
        if (!compareStr(oldData.getDutyPersonName(), newData.getDutyPersonName())) {
            SgsjTechnicalScienceTopicModify differData = new SgsjTechnicalScienceTopicModify();
            differData.setModifyContent("课题负责人");
            differData.setAfterModify(oldData.getDutyPersonName());
            differData.setBeforeModify(newData.getDutyPersonName());
            objects.add(differData);
        }
        if (!compareStr(oldData.getTogetherUnit(), newData.getTogetherUnit())) {
            SgsjTechnicalScienceTopicModify differData = new SgsjTechnicalScienceTopicModify();
            differData.setModifyContent("协作单位");
            differData.setAfterModify(oldData.getTogetherUnit());
            differData.setBeforeModify(newData.getTogetherUnit());
            objects.add(differData);
        }
        if (!compareStr(oldData.getTogetherUnitOther(), newData.getTogetherUnitOther())) {
            SgsjTechnicalScienceTopicModify differData = new SgsjTechnicalScienceTopicModify();
            differData.setModifyContent("其他协作单位");
            differData.setAfterModify(oldData.getTogetherUnitOther());
            differData.setBeforeModify(newData.getTogetherUnitOther());
            objects.add(differData);
        }
        if (!compareBigDecimal(oldData.getRdCost(), newData.getRdCost())) {
            SgsjTechnicalScienceTopicModify differData = new SgsjTechnicalScienceTopicModify();
            differData.setModifyContent("研发预算（万元）");
            differData.setAfterModify(String.valueOf(oldData.getRdCost()));
            differData.setBeforeModify(String.valueOf(newData.getRdCost()));
            objects.add(differData);
        }
        if (!compareBigDecimal(oldData.getAlreadyPayCost(), newData.getAlreadyPayCost())) {
            SgsjTechnicalScienceTopicModify differData = new SgsjTechnicalScienceTopicModify();
            differData.setModifyContent("已拨付经费（万元）");
            differData.setAfterModify(String.valueOf(oldData.getAlreadyPayCost()));
            differData.setBeforeModify(String.valueOf(newData.getAlreadyPayCost()));
            objects.add(differData);
        }
        if (!compareBigDecimal(oldData.getLeftCost(), newData.getLeftCost())) {
            SgsjTechnicalScienceTopicModify differData = new SgsjTechnicalScienceTopicModify();
            differData.setModifyContent("已拨付经费（万元）");
            differData.setAfterModify(String.valueOf(oldData.getLeftCost()));
            differData.setBeforeModify(String.valueOf(newData.getLeftCost()));
            objects.add(differData);
        }
        if (!compareStr(oldData.getWriteInPerson(), newData.getWriteInPerson())) {
            SgsjTechnicalScienceTopicModify differData = new SgsjTechnicalScienceTopicModify();
            differData.setModifyContent("登记人id");
            differData.setAfterModify(String.valueOf(oldData.getWriteInPerson()));
            differData.setBeforeModify(String.valueOf(newData.getWriteInPerson()));
            objects.add(differData);
        }
        if (!compareStr(oldData.getWriteInPersonName(), newData.getWriteInPersonName())) {
            SgsjTechnicalScienceTopicModify differData = new SgsjTechnicalScienceTopicModify();
            differData.setModifyContent("登记人");
            differData.setAfterModify(String.valueOf(oldData.getWriteInPersonName()));
            differData.setBeforeModify(String.valueOf(newData.getWriteInPersonName()));
            objects.add(differData);
        }
        if (!compareStr(oldData.getWriteInPersonPhoneNum(), newData.getWriteInPersonPhoneNum())) {
            SgsjTechnicalScienceTopicModify differData = new SgsjTechnicalScienceTopicModify();
            differData.setModifyContent("登记人联系方式");
            differData.setAfterModify(String.valueOf(oldData.getWriteInPersonPhoneNum()));
            differData.setBeforeModify(String.valueOf(newData.getWriteInPersonPhoneNum()));
            objects.add(differData);
        }
        if (!compareStr(oldData.getTopicSummary(), newData.getTopicSummary())) {
            SgsjTechnicalScienceTopicModify differData = new SgsjTechnicalScienceTopicModify();
            differData.setModifyContent("课题简介");
            differData.setAfterModify(String.valueOf(oldData.getTopicSummary()));
            differData.setBeforeModify(String.valueOf(newData.getTopicSummary()));
            objects.add(differData);
        }
        if (!compareStr(oldData.getTopicFileGroupId(), newData.getTopicFileGroupId())) {
            SgsjTechnicalScienceTopicModify differData = new SgsjTechnicalScienceTopicModify();
            differData.setModifyContent("课题附件");
            differData.setAfterModify(String.valueOf(oldData.getTopicFileGroupId()));
            differData.setBeforeModify(String.valueOf(newData.getTopicFileGroupId()));
            objects.add(differData);
        }
        if (!compareStr(oldData.getContractFileGroupId(), newData.getContractFileGroupId())) {
            SgsjTechnicalScienceTopicModify differData = new SgsjTechnicalScienceTopicModify();
            differData.setModifyContent("合同附件");
            differData.setAfterModify(String.valueOf(oldData.getContractFileGroupId()));
            differData.setBeforeModify(String.valueOf(newData.getContractFileGroupId()));
            objects.add(differData);
        }
        if (!compareStr(oldData.getInspectFileGroupId(), newData.getInspectFileGroupId())) {
            SgsjTechnicalScienceTopicModify differData = new SgsjTechnicalScienceTopicModify();
            differData.setModifyContent("检查附件");
            differData.setAfterModify(String.valueOf(oldData.getInspectFileGroupId()));
            differData.setBeforeModify(String.valueOf(newData.getInspectFileGroupId()));
            objects.add(differData);
        }
        if (!compareStr(oldData.getAcceptanceFileGroupId(), newData.getAcceptanceFileGroupId())) {
            SgsjTechnicalScienceTopicModify differData = new SgsjTechnicalScienceTopicModify();
            differData.setModifyContent("验收附件");
            differData.setAfterModify(String.valueOf(oldData.getAcceptanceFileGroupId()));
            differData.setBeforeModify(String.valueOf(newData.getAcceptanceFileGroupId()));
            objects.add(differData);
        }
        if (!compareStr(oldData.getTopicFileGroupId(), newData.getTopicFileGroupId())) {
            SgsjTechnicalScienceTopicModify differData = new SgsjTechnicalScienceTopicModify();
            differData.setModifyContent("课题附件");
            differData.setAfterModify(String.valueOf(oldData.getTopicFileGroupId()));
            differData.setBeforeModify(String.valueOf(newData.getTopicFileGroupId()));
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
        AjaxResult ajaxResult = systemServiceApi.selectByRoleKeyList(roles);
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
    public void export(SgsjTechnicalScienceTopic param) {
        List<SgsjTechnicalScienceTopicDTO> data = new ArrayList<>();
        List<SgsjTechnicalScienceTopic> resultList = sgsjTechnicalScienceTopicMapper.getSgsjTechnicalScienceTopicList(param);
        if (CollUtil.isEmpty(resultList)) return;
        Map<Long, SgsjTechnicalScienceTopic> resultMap = resultList.stream().collect(Collectors.toMap(SgsjTechnicalScienceTopic::getId, value -> value));
        Long[] ids = resultList.stream().map(SgsjTechnicalScienceTopic::getId).toArray(Long[]::new);
        List<SgjsAchievementAward> awardList = sgjsAchievementAwardService.getListByForeignIds(ids);
        List<ShjsAuthenticateEvaluate> evaluateList = shjsAuthenticateEvaluateService.getListByForeignIds(ids);
        for (SgjsAchievementAward sgjsAchievementAward : awardList) {
            Long foreignId = sgjsAchievementAward.getForeignId();
            SgsjTechnicalScienceTopic sgsjTechnicalScienceTopic = resultMap.get(foreignId);
            SgsjTechnicalScienceTopicDTO dto = new SgsjTechnicalScienceTopicDTO();
            BeanUtil.copyProperties(sgsjTechnicalScienceTopic, dto);
            dto.setApplyAward(sgjsAchievementAward.getApplyAward());
            dto.setAwardGrade(sgjsAchievementAward.getAwardGrade());
            dto.setAwardType(sgjsAchievementAward.getAwardType());
            dto.setGrantUnit(sgjsAchievementAward.getGrantUnit());
            dto.setChildId(sgjsAchievementAward.getId());
            dto.setMainId(sgsjTechnicalScienceTopic.getId());
            data.add(dto);
        }
        Map<Long, List<SgsjTechnicalScienceTopicDTO>> dataMap = data.stream().collect(Collectors.groupingBy(SgsjTechnicalScienceTopicDTO::getMainId));
        for (ShjsAuthenticateEvaluate authenticateEvaluate : evaluateList) {
            Long foreignId = authenticateEvaluate.getForeignId();
            List<SgsjTechnicalScienceTopicDTO> sgsjTechnicalScienceTopicDTOS = dataMap.get(foreignId);
            sgsjTechnicalScienceTopicDTOS.get(0).setAuthenticateUnit(authenticateEvaluate.getAuthenticateUnit());
            sgsjTechnicalScienceTopicDTOS.get(0).setAuthenticateDate(authenticateEvaluate.getAuthenticateDate());
            sgsjTechnicalScienceTopicDTOS.get(0).setEvaluateConclusion(authenticateEvaluate.getEvaluateConclusion());
        }

        EasyExcel.write("D:\\exprot.xlsx").sheet("导出测试")
                .head(SgsjTechnicalScienceTopicDTO.class)
                .registerWriteHandler(new CustomMergeStrategy())
                .doWrite(new ArrayList<>(dataMap.values()));
    }
}
