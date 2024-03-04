package com.hhwy.sp.techManagement.sgsjTechnicalScienceTopic.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.config.filter.IFilterConfig;
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
import org.springframework.beans.factory.annotation.Value;
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
            if (StrUtil.isBlank(p.getTopicCurentNode())) {
                //申请
                p.setId(Long.valueOf(p.getPtVar1()));
                FlowInfoSearchUtil.getFlowInfo(p, FlowEnum.SGJS_TECH_SCIENCE_TOPIC);
            } else {
                //立项
                p.setId(Long.valueOf(p.getPtVar2()));
                FlowInfoSearchUtil.getFlowInfo(p, FlowEnum.SGJS_TECH_SCIENCE_TOPIC_LX);
            }
        });
        resultList.forEach(p -> {
            p.setId(Long.valueOf(p.getPtVar3()));
            p.setPtVar3(null);
        });
        return resultList;
    }

    /***
     * 功能描述: 课题立项里的保存
     */
    @Transactional
    public SgsjTechnicalScienceTopic insertSgsjTechnicalScienceTopic(SgsjTechnicalScienceTopic sgsjTechnicalScienceTopic) {
        Assert.isTrue(sgsjTechnicalScienceTopic != null, "请求参数缺失");
        Assert.isTrue(sgsjTechnicalScienceTopic.getId() != null, "id不能为空");
        //判断课题编号是否重复
        String topicCode = sgsjTechnicalScienceTopic.getTopicCode();
        if (StrUtil.isNotBlank(topicCode)) {
            SgsjTechnicalScienceTopic param1 = new SgsjTechnicalScienceTopic();
            param1.setTopicCode(topicCode);
            SgsjTechnicalScienceTopic sgsjTechnicalScienceTopic2 = sgsjTechnicalScienceTopicMapper.getSgsjTechnicalScienceTopic(param1);
            if (sgsjTechnicalScienceTopic2 != null) {
                Assert.isTrue(sgsjTechnicalScienceTopic.getId().equals(sgsjTechnicalScienceTopic2.getId()), "编号不能重复");
            }
        }
        //保存子表
        Long id = sgsjTechnicalScienceTopic.getId();
        //专家库
        List<SgjsExpertLibrary> listAcceptance = sgsjTechnicalScienceTopic.getListAcceptance();
        List<SgjsExpertLibrary> listOutline = sgsjTechnicalScienceTopic.getListOutline();
        List<SgjsExpertLibrary> listTopic = sgsjTechnicalScienceTopic.getListTopic();
        if (CollUtil.isNotEmpty(listAcceptance))
            sgjsExpertLibraryService.saveExpertLibrary(id, BelongBusiness.BELONG_BUSINESS_4, listAcceptance);
        if (CollUtil.isNotEmpty(listOutline))
            sgjsExpertLibraryService.saveExpertLibrary(id, BelongBusiness.BELONG_BUSINESS_3, listOutline);
        if (CollUtil.isNotEmpty(listTopic))
            sgjsExpertLibraryService.saveExpertLibrary(id, BelongBusiness.BELONG_BUSINESS_2, listTopic);
        //成果
        List<SgjsAchievementAward> awardList = sgsjTechnicalScienceTopic.getAwardList();
        sgjsAchievementAwardService.saveAchievementAward(id, BelongBusiness.BELONG_BUSINESS_9, awardList);
        //鉴定或评价
        List<SgjsAuthenticateEvaluate> evaluateList = sgsjTechnicalScienceTopic.getEvaluateList();
        shjsAuthenticateEvaluateService.saveEvaluate(id, BelongBusiness.BELONG_BUSINESS_9, evaluateList);
        //返回结果获取
        SgsjTechnicalScienceTopic sgsjTechnicalScienceTopic1 = new SgsjTechnicalScienceTopic();
        sgsjTechnicalScienceTopic1.setId(id);
        SgsjTechnicalScienceTopic result = sgsjTechnicalScienceTopicMapper.getSgsjTechnicalScienceTopic(sgsjTechnicalScienceTopic1);

        //判断当前记录是否在流程中，如果已发起审批，则需要保存修改记录你
        SgsjTechnicalScienceTopic parm = new SgsjTechnicalScienceTopic();
        parm.setId(Long.valueOf(result.getPtVar2()));
        FlowInfoSearchUtil.getFlowInfo(parm, FlowEnum.SGJS_TECH_SCIENCE_TOPIC);
        String taskStatus = parm.getTaskStatus();
        //如果流程已发起，需要处理修改记录信息
        if (!taskStatus.equals("0") && !taskStatus.equals("4")){
            //获取老的记录
            SgsjTechnicalScienceTopic param = new SgsjTechnicalScienceTopic();
            param.setId(sgsjTechnicalScienceTopic.getId());
            SgsjTechnicalScienceTopic oldData = sgsjTechnicalScienceTopicMapper.getSgsjTechnicalScienceTopic(param);
            //对比记录
            String topicCurentNode = sgsjTechnicalScienceTopic.getTopicCurentNode();
            List<SgsjTechnicalScienceTopicModify> modifyList = compareToObj(oldData, sgsjTechnicalScienceTopic, topicCurentNode);
            if (CollUtil.isNotEmpty(modifyList)) {
                SysUser sysUser = SecurityUtils.getSysUser();
                modifyList.forEach(p ->{
                    p.setTaskNode(parm.getProcessTaskName());
                    p.setTopicNode(sgsjTechnicalScienceTopic.getTopicCurentNode());
                    p.setModifyDatetime(DateUtils.getNowDate());
                    p.setModifyPerson(String.valueOf(sysUser.getUserId()));
                    p.setModifyPersionName(sysUser.getNickName());
                    p.setId(IdWorker.createId());
                    p.setCreateUser(sysUser.getUserName());
                    p.setCreateTime(DateUtils.getNowDate());
                    p.setForeignId(sgsjTechnicalScienceTopic.getId());
                });
                technicalScienceTopicModifyService.insertSgsjTechnicalScienceTopicModifyList(modifyList);
            }
        }
        //保存主表
        sgsjTechnicalScienceTopicMapper.updateSgsjTechnicalScienceTopic(sgsjTechnicalScienceTopic);
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
        return sgsjTechnicalScienceTopicMapper.updateTaskStatus(sgsjTechnicalScienceTopic);
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
            FlowInfoSearchUtil.getFlowInfo(sgsjTechnicalScienceTopic, FlowEnum.SGJS_TECH_SCIENCE_TOPIC);
            sgsjTechnicalScienceTopic.setId(null);
            BeanUtil.copyProperties(sgsjTechnicalScienceTopic, resultBean, CopyOptions.create(CommonBaseEntity.class, true));
        }
        return resultBean;
    }


    //修改记录判断
    private List<SgsjTechnicalScienceTopicModify> compareToObj(SgsjTechnicalScienceTopic oldData, SgsjTechnicalScienceTopic newData, String topicCurentNode) {
        List<SgsjTechnicalScienceTopicModify> objects = new ArrayList<>();
        if (!compareStr(oldData.getTopicCode(), newData.getTopicCode())) {
            SgsjTechnicalScienceTopicModify differData = new SgsjTechnicalScienceTopicModify();
            differData.setModifyContent("课题编号");
            differData.setBeforeModify(StrUtil.isBlank(oldData.getTopicCode())?"":oldData.getTopicCode());
            differData.setAfterModify(StrUtil.isBlank(newData.getTopicCode())?"":newData.getTopicCode());
            objects.add(differData);
        }
        if (!compareStr(oldData.getTopicName(), newData.getTopicName())) {
            SgsjTechnicalScienceTopicModify differData = new SgsjTechnicalScienceTopicModify();
            differData.setModifyContent("课题名称");
            differData.setBeforeModify(StrUtil.isBlank(oldData.getTopicName())?"":oldData.getTopicName());
            differData.setAfterModify(StrUtil.isBlank(newData.getTopicName())?"":newData.getTopicName());
            objects.add(differData);
        }
        if (!compareStr(oldData.getStartEndDate(), newData.getStartEndDate())) {
            SgsjTechnicalScienceTopicModify differData = new SgsjTechnicalScienceTopicModify();
            differData.setModifyContent("课题研发日期");
            differData.setBeforeModify(StrUtil.isBlank(oldData.getStartEndDate())?"":oldData.getStartEndDate());
            differData.setAfterModify(StrUtil.isBlank(newData.getStartEndDate())?"":newData.getStartEndDate());
            objects.add(differData);
        }
        if (!compareStr(oldData.getDutyPersonName(), newData.getDutyPersonName())) {
            SgsjTechnicalScienceTopicModify differData = new SgsjTechnicalScienceTopicModify();
            differData.setModifyContent("课题负责人");
            differData.setBeforeModify(StrUtil.isBlank(oldData.getDutyPersonName())?"":oldData.getDutyPersonName());
            differData.setAfterModify(StrUtil.isBlank(newData.getDutyPersonName())?"":newData.getDutyPersonName());
            objects.add(differData);
        }
        if (!compareStr(oldData.getTogetherUnit(), newData.getTogetherUnit())) {
            SgsjTechnicalScienceTopicModify differData = new SgsjTechnicalScienceTopicModify();
            differData.setModifyContent("协作单位");
            differData.setBeforeModify(StrUtil.isBlank(oldData.getTogetherUnit())?"":oldData.getTogetherUnit());
            differData.setAfterModify(StrUtil.isBlank(newData.getTogetherUnit())?"":newData.getTogetherUnit());
            objects.add(differData);
        }
        if (!compareStr(oldData.getTogetherUnitOther(), newData.getTogetherUnitOther())) {
            SgsjTechnicalScienceTopicModify differData = new SgsjTechnicalScienceTopicModify();
            differData.setModifyContent("其他协作单位");
            differData.setBeforeModify(StrUtil.isBlank(oldData.getTogetherUnitOther())?"":oldData.getTogetherUnitOther());
            differData.setAfterModify(StrUtil.isBlank(newData.getTogetherUnitOther())?"":newData.getTogetherUnitOther());
            objects.add(differData);
        }
        if (!compareBigDecimal(oldData.getRdCost(), newData.getRdCost())) {
            SgsjTechnicalScienceTopicModify differData = new SgsjTechnicalScienceTopicModify();
            differData.setModifyContent("研发预算（万元）");
            differData.setBeforeModify(oldData.getRdCost() == null ? null : String.valueOf(oldData.getRdCost()));
            differData.setAfterModify(newData.getRdCost() == null ? null : String.valueOf(newData.getRdCost()));
            objects.add(differData);
        }
        if (!compareBigDecimal(oldData.getAlreadyPayCost(), newData.getAlreadyPayCost())) {
            SgsjTechnicalScienceTopicModify differData = new SgsjTechnicalScienceTopicModify();
            differData.setModifyContent("已拨付经费（万元）");
            differData.setBeforeModify(oldData.getAlreadyPayCost() == null ? null : String.valueOf(oldData.getAlreadyPayCost()));
            differData.setAfterModify(newData.getAlreadyPayCost() == null ? null : String.valueOf(newData.getAlreadyPayCost()));
            objects.add(differData);
        }
        if (!compareBigDecimal(oldData.getLeftCost(), newData.getLeftCost())) {
            SgsjTechnicalScienceTopicModify differData = new SgsjTechnicalScienceTopicModify();
            differData.setModifyContent("剩余经费（万元）");
            differData.setBeforeModify(oldData.getLeftCost() == null ? null : String.valueOf(oldData.getLeftCost()));
            differData.setAfterModify(newData.getLeftCost() == null ? null : String.valueOf(newData.getLeftCost()));
            objects.add(differData);
        }
        if (!compareStr(oldData.getWriteInPersonName(), newData.getWriteInPersonName())) {
            SgsjTechnicalScienceTopicModify differData = new SgsjTechnicalScienceTopicModify();
            differData.setModifyContent("登记人");
            differData.setBeforeModify(StrUtil.isBlank(oldData.getWriteInPersonName())?"":oldData.getWriteInPersonName());
            differData.setAfterModify(StrUtil.isBlank(newData.getWriteInPersonName())?"":newData.getWriteInPersonName());
            objects.add(differData);
        }
        if (!compareStr(oldData.getWriteInPersonPhoneNum(), newData.getWriteInPersonPhoneNum())) {
            SgsjTechnicalScienceTopicModify differData = new SgsjTechnicalScienceTopicModify();
            differData.setModifyContent("登记人联系方式");
            differData.setBeforeModify(StrUtil.isBlank(oldData.getWriteInPersonPhoneNum())?"":oldData.getWriteInPersonPhoneNum());
            differData.setAfterModify(StrUtil.isBlank(newData.getWriteInPersonPhoneNum())?"":newData.getWriteInPersonPhoneNum());
            objects.add(differData);
        }
        if (!compareStr(oldData.getTopicSummary(), newData.getTopicSummary())) {
            SgsjTechnicalScienceTopicModify differData = new SgsjTechnicalScienceTopicModify();
            differData.setModifyContent("课题简介");
            differData.setBeforeModify(StrUtil.isBlank(oldData.getTopicSummary())?"":oldData.getTopicSummary());
            differData.setAfterModify(StrUtil.isBlank(newData.getTopicSummary())?"":newData.getTopicSummary());
            objects.add(differData);
        }
        if (StrUtil.isNotBlank(topicCurentNode) && topicCurentNode.equals("2")) {
            if (!compareStr(oldData.getOutlineFileGroupId(), newData.getOutlineFileGroupId())) {
                SgsjTechnicalScienceTopicModify differData = new SgsjTechnicalScienceTopicModify();
                differData.setModifyContent("大纲附件");
                differData.setBeforeModify(StrUtil.isBlank(oldData.getOutlineFileGroupId()) ? "" : oldData.getOutlineFileGroupId());
                differData.setAfterModify(StrUtil.isBlank(newData.getOutlineFileGroupId()) ? "" : newData.getOutlineFileGroupId());
                objects.add(differData);
            }
        }
        if (StrUtil.isNotBlank(topicCurentNode) && topicCurentNode.equals("3")) {
            if (!compareStr(oldData.getContractFileGroupId(), newData.getContractFileGroupId())) {
                SgsjTechnicalScienceTopicModify differData = new SgsjTechnicalScienceTopicModify();
                differData.setModifyContent("合同附件");
                differData.setBeforeModify(StrUtil.isBlank(oldData.getContractFileGroupId())?"":oldData.getContractFileGroupId());
                differData.setAfterModify(StrUtil.isBlank(newData.getContractFileGroupId())?"":newData.getContractFileGroupId());
                objects.add(differData);
            }
        }
        if (StrUtil.isNotBlank(topicCurentNode) && topicCurentNode.equals("4")) {
            if (!compareStr(oldData.getInspectFileGroupId(), newData.getInspectFileGroupId())) {
                SgsjTechnicalScienceTopicModify differData = new SgsjTechnicalScienceTopicModify();
                differData.setModifyContent("检查附件");
                differData.setBeforeModify(StrUtil.isBlank(oldData.getInspectFileGroupId()) ? "" : oldData.getInspectFileGroupId());
                differData.setAfterModify(StrUtil.isBlank(newData.getInspectFileGroupId()) ? "" : newData.getInspectFileGroupId());
                objects.add(differData);
            }
        }
        if (StrUtil.isNotBlank(topicCurentNode) && topicCurentNode.equals("5")) {
            if (!compareStr(oldData.getAcceptanceFileGroupId(), newData.getAcceptanceFileGroupId())) {
                SgsjTechnicalScienceTopicModify differData = new SgsjTechnicalScienceTopicModify();
                differData.setModifyContent("验收附件");
                differData.setBeforeModify(StrUtil.isBlank(oldData.getAcceptanceFileGroupId()) ? "" : oldData.getTopicFileGroupId());
                differData.setAfterModify(StrUtil.isBlank(newData.getAcceptanceFileGroupId()) ? "" : newData.getAcceptanceFileGroupId());
                objects.add(differData);
            }
        }
        if (!compareStr(oldData.getTopicFileGroupId(), newData.getTopicFileGroupId())) {
            SgsjTechnicalScienceTopicModify differData = new SgsjTechnicalScienceTopicModify();
            differData.setModifyContent("课题附件");
            differData.setBeforeModify(StrUtil.isBlank(oldData.getTopicFileGroupId())?"":oldData.getTopicFileGroupId());
            differData.setAfterModify(StrUtil.isBlank(newData.getTopicFileGroupId())?"":newData.getTopicFileGroupId());
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
        return NumberUtil.equals(b1, b2);
    }

    @Value("${kygl.mesPublish.roleKey}")
    private String roleKeyArr;
    @Value("${kygl.mesPublish.roleName}")
    private String roleNameArr;

    //知会消息发布
    @Override
    public AjaxResult messagePublic(String message) {
        Assert.isTrue(StrUtil.isNotBlank(message), "message参数不能为空");
        Assert.isTrue(StrUtil.isNotBlank(roleKeyArr), "未配置消息发布角色");
        String[] roles = StrUtil.splitToArray(roleKeyArr, ",");
        AjaxResult ajaxResult = systemServiceApi.selectByRoleAndTenant(roles, SecurityUtils.getTenantKey());
        Integer code = (Integer) ajaxResult.get("code");
        Assert.isTrue(code.equals(200), "获取用户列表失败");
        String userInfoStr = JSON.toJSONString(ajaxResult.get("data"));
        Assert.isTrue(StrUtil.isNotBlank(userInfoStr), "角色未绑定用户");
        List<SysUser> sysUsers = JSON.parseArray(userInfoStr, SysUser.class);
        String clientIds = sysUsers.stream().map(SysUser::getUserName).collect(Collectors.joining(","));
        String topic = "system";
        R r = systemServiceApi.batchPublish(clientIds, topic, message);
        if (r.getCode() == 200) {
            return AjaxResult.success("消息发布成功");
        }else {
            return AjaxResult.error("消息发布失败");
        }
    }

    //获取消息发布角色名称
    @Override
    public String getRoleName() {
        return roleNameArr;
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

    //导出专家意见
    @Override
    public Map<String, Object> getExpertSuggest(SgsjTechnicalScienceTopic param) {
        SgsjTechnicalScienceTopic sgsjTechnicalScienceTopic = new SgsjTechnicalScienceTopic();
        sgsjTechnicalScienceTopic.setId(param.getId());
        SgsjTechnicalScienceTopic topicInfo = sgsjTechnicalScienceTopicMapper.getSgsjTechnicalScienceTopic(sgsjTechnicalScienceTopic);
        Assert.isTrue(ObjectUtil.isNotEmpty(topicInfo), "无此课题");
        SgjsExpertLibrary expertParam = new SgjsExpertLibrary();
        expertParam.setForeignId(param.getId());
        //根据课题节点判断导出业务类型
        if (StrUtil.isBlank(param.getTopicCurentNode())) {
            expertParam.setBelongBusiness("1");
        }else if (param.getTopicCurentNode().equals("1")) {
            expertParam.setBelongBusiness("2");
        }else if (param.getTopicCurentNode().equals("2")) {
            expertParam.setBelongBusiness("3");
        }else if (param.getTopicCurentNode().equals("5")) {
            expertParam.setBelongBusiness("4");
        }
        List<SgjsExpertLibrary> expertList = sgjsExpertLibraryService.getSgjsExpertLibraryList(expertParam);
        Assert.isTrue(CollUtil.isNotEmpty(expertList), "该课题当前阶段无专家意见");
        return getStringObjectMap(topicInfo, expertList);
    }

    //导出word拼数据
    private static Map<String, Object> getStringObjectMap(SgsjTechnicalScienceTopic topicInfo, List<SgjsExpertLibrary> expertList) {
        Map<String, Object> resultMap = new HashMap<>();
        //主表数据
        resultMap.put("topicCode", StrUtil.isBlank(topicInfo.getTopicCode())?"-":topicInfo.getTopicCode());
        resultMap.put("topicName", StrUtil.isBlank(topicInfo.getTopicName())?"-":topicInfo.getTopicName());
        resultMap.put("startEndDate", StrUtil.isBlank(topicInfo.getStartEndDate())?"-":topicInfo.getStartEndDate());
        resultMap.put("dutyPersonName", StrUtil.isBlank(topicInfo.getDutyPersonName())?"-":topicInfo.getDutyPersonName());
        resultMap.put("togetherUnit", StrUtil.isBlank(topicInfo.getTogetherUnit())?"-":topicInfo.getTogetherUnit());
        resultMap.put("togetherUnitOther", StrUtil.isBlank(topicInfo.getTogetherUnitOther())?"-":topicInfo.getTogetherUnitOther());
        resultMap.put("rdCost", topicInfo.getRdCost()==null?BigDecimal.ZERO:topicInfo.getRdCost());
        resultMap.put("alreadyPayCost", topicInfo.getAlreadyPayCost()==null?BigDecimal.ZERO:topicInfo.getAlreadyPayCost());
        resultMap.put("leftCost", topicInfo.getLeftCost()==null?BigDecimal.ZERO:topicInfo.getLeftCost());
        resultMap.put("writeInPersonName", StrUtil.isBlank(topicInfo.getWriteInPersonName())?"-":topicInfo.getWriteInPersonName());
        resultMap.put("writeInPersonPhoneNum", StrUtil.isBlank(topicInfo.getWriteInPersonPhoneNum())?"-":topicInfo.getWriteInPersonPhoneNum());
        resultMap.put("topicSummary", StrUtil.isBlank(topicInfo.getTopicSummary())?"-":topicInfo.getTopicSummary());
        resultMap.put("projectName", StrUtil.isBlank(topicInfo.getProjectName())?"-":topicInfo.getProjectName());
        //专家意见
        List<Map<String, Object>> list = new ArrayList<>();
        resultMap.put("expertGroupSuggest", expertList.get(0).getPtVar1());
        for (int i = 0; i < expertList.size(); i++) {
            Map<String, Object> map = new HashMap<>();
            SgjsExpertLibrary sgjsExpertLibrary = expertList.get(i);
            map.put("serialNumber", i+1);
            map.put("expertName", StrUtil.isBlank(sgjsExpertLibrary.getExpertName())?"-":sgjsExpertLibrary.getExpertName());
            map.put("belongUnit", StrUtil.isBlank(sgjsExpertLibrary.getBelongUnit())?"-":sgjsExpertLibrary.getBelongUnit());
            map.put("businessAreas", StrUtil.isBlank(sgjsExpertLibrary.getBusinessAreas())?"-":sgjsExpertLibrary.getBusinessAreas());
            map.put("suggest", StrUtil.isBlank(sgjsExpertLibrary.getSuggest())?"-":sgjsExpertLibrary.getSuggest());
            map.put("remark", StrUtil.isBlank(sgjsExpertLibrary.getRemark())?"-":sgjsExpertLibrary.getRemark());
            list.add(map);
        }
        resultMap.put("list", list);
        return resultMap;
    }
}
