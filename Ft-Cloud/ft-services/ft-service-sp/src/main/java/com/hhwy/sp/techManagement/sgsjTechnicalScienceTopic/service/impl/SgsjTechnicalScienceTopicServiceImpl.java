package com.hhwy.sp.techManagement.sgsjTechnicalScienceTopic.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.domain.R;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.domain.base.project.ProjectDto;
import com.hhwy.enums.FlowEnum;
import com.hhwy.feign.service.PmServiceApi;
import com.hhwy.feign.service.SystemServiceApi;
import com.hhwy.sp.common.FileUploadUtil;
import com.hhwy.sp.common.FlowInfoSearchUtil;
import com.hhwy.sp.common.constant.BelongBusiness;
import com.hhwy.sp.common.sgjsAchievementAward.domain.SgjsAchievementAward;
import com.hhwy.sp.common.sgjsAchievementAward.service.ISgjsAchievementAwardService;
import com.hhwy.sp.common.sgjsAuthenticateEvaluate.domain.SgjsAuthenticateEvaluate;
import com.hhwy.sp.common.sgjsAuthenticateEvaluate.service.ISgjsAuthenticateEvaluateService;
import com.hhwy.sp.common.sgjsExpertLibrary.domain.SgjsExpertLibrary;
import com.hhwy.sp.common.sgjsExpertLibrary.service.ISgjsExpertLibraryService;
import com.hhwy.sp.sciTech.sgjsFourNewsAchievement.domain.SgjsFourNewsAchievement;
import com.hhwy.sp.techManagement.sgjsTechnicalNormalTopic.domain.SgjsTechnicalNormalTopic;
import com.hhwy.sp.techManagement.sgjsTechnicalNormalTopic.sgjsTechnicalNormalTopicCost.domain.SgjsTechnicalNormalTopicCost;
import com.hhwy.sp.techManagement.sgsjTechnicalScienceTopic.domain.FileDto;
import com.hhwy.sp.techManagement.sgsjTechnicalScienceTopic.domain.SgsjTechnicalScienceTopic;
import com.hhwy.sp.techManagement.sgsjTechnicalScienceTopic.domain.SgsjTechnicalScienceTopicDTO;
import com.hhwy.sp.techManagement.sgsjTechnicalScienceTopic.mapper.SgsjTechnicalScienceTopicMapper;
import com.hhwy.sp.techManagement.sgsjTechnicalScienceTopic.service.ISgsjTechnicalScienceTopicService;
import com.hhwy.sp.techManagement.sgsjTechnicalScienceTopic.sgsjTechnicalScienceTopicModify.domain.SgsjTechnicalScienceTopicModify;
import com.hhwy.sp.techManagement.sgsjTechnicalScienceTopic.sgsjTechnicalScienceTopicModify.service.ISgsjTechnicalScienceTopicModifyService;
import com.hhwy.system.api.RemoteNoticeService;
import com.hhwy.system.api.domain.SysNotice;
import com.hhwy.system.api.domain.SysUser;
import com.hhwy.utils.common.CommonBaseEntity;
import com.hhwy.utils.idworker.IdWorker;
import io.seata.common.util.CollectionUtils;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
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
    @Autowired
    private FileUploadUtil fileUploadUtil;
    @Autowired
    private RemoteNoticeService remoteNoticeService;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    @Autowired
    private RestTemplate restTemplate;
    @Value("${file.url}")
    private String fileUrl;
    @Autowired
    private PmServiceApi pmServiceApi;

    private static ProjectDto projectInfo;

    //向总部推送数据用
    private static ThreadPoolExecutor executorService = new ThreadPoolExecutor(0, 2, 10, TimeUnit.MINUTES, new ArrayBlockingQueue<>(5));

    private ProjectDto getProjectDto(){
        if (projectInfo != null) return projectInfo;
        return pmServiceApi.getProjectDto();
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
    public int insertSgsjTechnicalScienceTopic(SgsjTechnicalScienceTopic sgsjTechnicalScienceTopic) {
        sgsjTechnicalScienceTopic.setCreateUser(SecurityUtils.getUserName());
        sgsjTechnicalScienceTopic.setCreateTime(DateUtils.getNowDate());
        return sgsjTechnicalScienceTopicMapper.insertSgsjTechnicalScienceTopic(sgsjTechnicalScienceTopic);
    }

    @Transactional
    public int updateSgsjTechnicalScienceTopic(SgsjTechnicalScienceTopic sgsjTechnicalScienceTopic) {
        sgsjTechnicalScienceTopic.setUpdateUser(SecurityUtils.getUserName());
        sgsjTechnicalScienceTopic.setUpdateTime(DateUtils.getNowDate());
        int i = sgsjTechnicalScienceTopicMapper.updateTaskStatus(sgsjTechnicalScienceTopic);
        executorService.execute(this::doSendGm);
        return i;
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

    public SgsjTechnicalScienceTopic getSgsjTechnicalScienceTopic(SgsjTechnicalScienceTopic sgsjTechnicalScienceTopic) {
        return sgsjTechnicalScienceTopicMapper.getSgsjTechnicalScienceTopic(sgsjTechnicalScienceTopic);
    }

    /***
     * 功能描述: 查询
     */
    public List<SgsjTechnicalScienceTopic> getSgsjTechnicalScienceTopicList(SgsjTechnicalScienceTopic sgsjTechnicalScienceTopic) {
        List<SgsjTechnicalScienceTopic> resultList = sgsjTechnicalScienceTopicMapper.getSgsjTechnicalScienceTopicListNew(sgsjTechnicalScienceTopic);
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
        //获取流程信息
        return tableListFlowableInfo(resultList);
    }

    //获取流程信息
    private List<SgsjTechnicalScienceTopic> tableListFlowableInfo(List<SgsjTechnicalScienceTopic> resultList) {
        List<SgsjTechnicalScienceTopic> applyList = new ArrayList<>();
        List<SgsjTechnicalScienceTopic> lxList = new ArrayList<>();
        List<SgsjTechnicalScienceTopic> allList = new ArrayList<>();
        //获取流程信息
        resultList.forEach(p -> {
            p.setPtVar3(String.valueOf(p.getId()));
            if (StrUtil.isBlank(p.getTopicCurentNode())) {
                //申请
                p.setId(Long.valueOf(p.getPtVar1()));
                applyList.add(p);
            } else {
                //立项
                p.setId(Long.valueOf(p.getPtVar2()));
                lxList.add(p);
            }
        });
        if (CollUtil.isNotEmpty(applyList)) {
            FlowInfoSearchUtil.getFlowInfo(applyList, FlowEnum.SGJS_TECH_SCIENCE_TOPIC);
            allList.addAll(applyList);
        }
        if (CollUtil.isNotEmpty(lxList)) {
            FlowInfoSearchUtil.getFlowInfo(lxList, FlowEnum.SGJS_TECH_SCIENCE_TOPIC_LX);
            allList.addAll(lxList);
        }
        allList.forEach(p -> {
            p.setId(Long.valueOf(p.getPtVar3()));
            p.setPtVar3(null);
        });
        return allList;
    }

    /***
     * 功能描述: 课题立项里的保存
     */
    @Transactional
    public SgsjTechnicalScienceTopic lxAdd(SgsjTechnicalScienceTopic sgsjTechnicalScienceTopic) {
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
        Long id = sgsjTechnicalScienceTopic.getId();
        //保存子表
        handleChilderData(sgsjTechnicalScienceTopic, id);
        //返回结果获取
        SgsjTechnicalScienceTopic sgsjTechnicalScienceTopic1 = new SgsjTechnicalScienceTopic();
        sgsjTechnicalScienceTopic1.setId(id);
        SgsjTechnicalScienceTopic result = sgsjTechnicalScienceTopicMapper.getSgsjTechnicalScienceTopic(sgsjTechnicalScienceTopic1);
        //判断当前记录是否在流程中，如果已发起审批，则需要保存修改记录你
        SgsjTechnicalScienceTopic parm = new SgsjTechnicalScienceTopic();
        parm.setId(Long.valueOf(result.getPtVar2()));
        FlowInfoSearchUtil.getFlowInfo(parm, FlowEnum.SGJS_TECH_SCIENCE_TOPIC);
        String taskStatus = parm.getTaskStatus();
        if (!taskStatus.equals("0") && !taskStatus.equals("4")){
            //如果流程已发起，需要处理修改记录信息
            handleModifyRecord(sgsjTechnicalScienceTopic, parm);
//            ExecutorService executorService = Executors.newSingleThreadExecutor();
//            executorService.execute(() -> handleModifyRecord(sgsjTechnicalScienceTopic, parm));
        }
        //保存主表
        sgsjTechnicalScienceTopicMapper.updateSgsjTechnicalScienceTopic(sgsjTechnicalScienceTopic);
        executorService.execute(this::doSendGm);
        return result;
    }

    //立项保存子表数据
    private void handleChilderData(SgsjTechnicalScienceTopic sgsjTechnicalScienceTopic, Long id) {
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
    }

    //处理修改记录
    private void handleModifyRecord(SgsjTechnicalScienceTopic sgsjTechnicalScienceTopic, SgsjTechnicalScienceTopic parm) {
        //获取老的记录
        SgsjTechnicalScienceTopic param = new SgsjTechnicalScienceTopic();
        param.setId(sgsjTechnicalScienceTopic.getId());
        SgsjTechnicalScienceTopic oldData = sgsjTechnicalScienceTopicMapper.getSgsjTechnicalScienceTopic(param);
        //对比记录
        List<SgsjTechnicalScienceTopicModify> modifyList = compareToObj(oldData, sgsjTechnicalScienceTopic, parm);
        if (CollUtil.isNotEmpty(modifyList)) {
            SysUser sysUser = SecurityUtils.getSysUser();
            modifyList.forEach(p ->{
                p.setTaskNode(parm.getProcessTaskName());
                String nextNode = sgsjTechnicalScienceTopic.getPtVar3();
                String topicCurentNode = sgsjTechnicalScienceTopic.getTopicCurentNode();
                p.setTopicNode(StrUtil.isBlank(nextNode)?topicCurentNode:Integer.parseInt(topicCurentNode) - 1 + "");
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

    /***
     * 功能描述: 课题申请里的保存
     */
    @Transactional
    public void applyAdd(SgsjTechnicalScienceTopic sgsjTechnicalScienceTopic) {
        if (sgsjTechnicalScienceTopic == null) {
            return;
        }
        if (StrUtil.isNotBlank(sgsjTechnicalScienceTopic.getTopicFileGroupId())) {
            String fileName = getFileName(sgsjTechnicalScienceTopic.getTopicFileGroupId());
            sgsjTechnicalScienceTopic.setPtVar5(fileName);
        }
        ProjectDto projectDto = getProjectDto();
        //保存
        if (sgsjTechnicalScienceTopic.getId() == null){
            //保存主表
            sgsjTechnicalScienceTopic.setId(IdWorker.createId());
            sgsjTechnicalScienceTopic.setPtVar1(String.valueOf(IdWorker.createId()));
            sgsjTechnicalScienceTopic.setCreateUser(SecurityUtils.getUserName());
            sgsjTechnicalScienceTopic.setCreateTime(DateUtils.getNowDate());
            sgsjTechnicalScienceTopic.setRegionId(projectDto.getRegionId());
            sgsjTechnicalScienceTopic.setRegionName(projectDto.getRegionName());
            sgsjTechnicalScienceTopic.setProjectId(projectDto.getProjectId());
            sgsjTechnicalScienceTopicMapper.insertSgsjTechnicalScienceTopic(sgsjTechnicalScienceTopic);
        }else {
            //修改
            sgsjTechnicalScienceTopicMapper.updateSgsjTechnicalScienceTopic(sgsjTechnicalScienceTopic);
            String applyState = sgsjTechnicalScienceTopic.getApplyState();
            if (StrUtil.isNotBlank(applyState) && (StrUtil.equalsAny(applyState, "3", "4"))) {
                //3，4代表流程结束，需要创建一条新数据给立项用
                SgsjTechnicalScienceTopic param = new SgsjTechnicalScienceTopic();
                param.setId(sgsjTechnicalScienceTopic.getId());
                SgsjTechnicalScienceTopic lxData = sgsjTechnicalScienceTopicMapper.getSgsjTechnicalScienceTopic(param);
                this.addLxData(lxData);
            }
        }
        //保存子表
        Long id = sgsjTechnicalScienceTopic.getId();
        List<SgjsExpertLibrary> libraryList = sgsjTechnicalScienceTopic.getListApply();
        sgjsExpertLibraryService.saveExpertLibrary(id, BelongBusiness.BELONG_BUSINESS_1, libraryList);
        executorService.execute(this::doSendGm);
    }

    /**
    * 功能描述: 申请完成后新增一条立项数据
    * @param: sgsjTechnicalScienceTopic 保存参数
    * 作者: fsd
    * 时间: 2024/3/6
    */
    public void addLxData(SgsjTechnicalScienceTopic sgsjTechnicalScienceTopic) {
        String fileGroupId = fileUploadUtil.copyFile(sgsjTechnicalScienceTopic.getTopicFileGroupId());
        sgsjTechnicalScienceTopic.setTopicFileGroupId(fileGroupId);
        sgsjTechnicalScienceTopic.setTopicCurentNode("0");
        sgsjTechnicalScienceTopic.setPtVar4(String.valueOf(sgsjTechnicalScienceTopic.getId()));
        sgsjTechnicalScienceTopic.setId(IdWorker.createId());
        sgsjTechnicalScienceTopic.setPtVar2(String.valueOf(IdWorker.createId()));
        sgsjTechnicalScienceTopicMapper.insertSgsjTechnicalScienceTopic(sgsjTechnicalScienceTopic);
        executorService.execute(this::doSendGm);
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
    private List<SgsjTechnicalScienceTopicModify> compareToObj(SgsjTechnicalScienceTopic oldData, SgsjTechnicalScienceTopic newData, SgsjTechnicalScienceTopic parm) {
        String processTaskName = parm.getProcessTaskName();
        String topicCurentNode = newData.getTopicCurentNode();
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
        if (!compareStr(oldData.getRdCost(), newData.getRdCost())) {
            SgsjTechnicalScienceTopicModify differData = new SgsjTechnicalScienceTopicModify();
            differData.setModifyContent("研发预算（万元）");
            differData.setBeforeModify(StrUtil.isBlank(oldData.getRdCost()) ? null : oldData.getRdCost());
            differData.setAfterModify(StrUtil.isBlank(newData.getRdCost()) ? null : newData.getRdCost());
            objects.add(differData);
        }
        if (!compareStr(oldData.getAlreadyPayCost(), newData.getAlreadyPayCost())) {
            SgsjTechnicalScienceTopicModify differData = new SgsjTechnicalScienceTopicModify();
            differData.setModifyContent("已拨付经费（万元）");
            differData.setBeforeModify(StrUtil.isBlank(oldData.getAlreadyPayCost()) ? null : oldData.getAlreadyPayCost());
            differData.setAfterModify(StrUtil.isBlank(newData.getAlreadyPayCost()) ? null : newData.getAlreadyPayCost());
            objects.add(differData);
        }
        if (!compareStr(oldData.getLeftCost(), newData.getLeftCost())) {
            SgsjTechnicalScienceTopicModify differData = new SgsjTechnicalScienceTopicModify();
            differData.setModifyContent("剩余经费（万元）");
            differData.setBeforeModify(StrUtil.isBlank(oldData.getLeftCost()) ? null : oldData.getLeftCost());
            differData.setAfterModify(StrUtil.isBlank(newData.getLeftCost()) ? null : newData.getLeftCost());
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
        String currentNodeFlag = newData.getPtVar3();
        SgsjTechnicalScienceTopicModify sgsjTechnicalScienceTopicModify = new SgsjTechnicalScienceTopicModify();
        sgsjTechnicalScienceTopicModify.setForeignId(oldData.getId());
        if (StrUtil.isNotBlank(topicCurentNode) && topicCurentNode.equals("2")
                || (StrUtil.isNotBlank(currentNodeFlag) && currentNodeFlag.equals("3"))) {
            sgsjTechnicalScienceTopicModify.setModifyContent("大纲附件");
            SgsjTechnicalScienceTopicModify result = technicalScienceTopicModifyService.getMaxCreateTimeDataByModifyContent(sgsjTechnicalScienceTopicModify);
            String oldName = result == null?"": result.getAfterModify();
            String newName = getFileName(newData.getOutlineFileGroupId());
            if (!compareStr(oldName, newName)) {
                SgsjTechnicalScienceTopicModify differData = new SgsjTechnicalScienceTopicModify();
                differData.setModifyContent("大纲附件");
                differData.setBeforeModify(StrUtil.isBlank(oldName) ? "" : oldName);
                differData.setAfterModify(StrUtil.isBlank(newName) ? "" : newName);
                objects.add(differData);
            }
        }
        if (StrUtil.isNotBlank(topicCurentNode) && topicCurentNode.equals("3")
                || (StrUtil.isNotBlank(currentNodeFlag) && currentNodeFlag.equals("4"))) {
            sgsjTechnicalScienceTopicModify.setModifyContent("合同附件");
            SgsjTechnicalScienceTopicModify result = technicalScienceTopicModifyService.getMaxCreateTimeDataByModifyContent(sgsjTechnicalScienceTopicModify);
            String oldName = result == null?"": result.getAfterModify();
            String newName = getFileName(newData.getContractFileGroupId());
            if (!compareStr(oldName, newName)) {
                SgsjTechnicalScienceTopicModify differData = new SgsjTechnicalScienceTopicModify();
                differData.setModifyContent("合同附件");
                differData.setBeforeModify(StrUtil.isBlank(oldName) ? "" : oldName);
                differData.setAfterModify(StrUtil.isBlank(newName) ? "" : newName);
                objects.add(differData);
            }
        }
        if (StrUtil.isNotBlank(topicCurentNode) && topicCurentNode.equals("4")
                || (StrUtil.isNotBlank(currentNodeFlag) && currentNodeFlag.equals("5"))) {
            sgsjTechnicalScienceTopicModify.setModifyContent("检查附件");
            SgsjTechnicalScienceTopicModify result = technicalScienceTopicModifyService.getMaxCreateTimeDataByModifyContent(sgsjTechnicalScienceTopicModify);
            String oldName = result == null?"": result.getAfterModify();
            String newName = getFileName(newData.getInspectFileGroupId());
            if (!compareStr(oldName, newName)) {
                SgsjTechnicalScienceTopicModify differData = new SgsjTechnicalScienceTopicModify();
                differData.setModifyContent("检查附件");
                differData.setBeforeModify(StrUtil.isBlank(oldName) ? "" : oldName);
                differData.setAfterModify(StrUtil.isBlank(newName) ? "" : newName);
                objects.add(differData);
            }
        }
        if (StrUtil.isNotBlank(topicCurentNode) && topicCurentNode.equals("5")) {
            sgsjTechnicalScienceTopicModify.setModifyContent("验收附件");
            SgsjTechnicalScienceTopicModify result = technicalScienceTopicModifyService.getMaxCreateTimeDataByModifyContent(sgsjTechnicalScienceTopicModify);
            String oldName = result == null?"": result.getAfterModify();
            String newName = getFileName(newData.getAcceptanceFileGroupId());
            if (!compareStr(oldName, newName)) {
                SgsjTechnicalScienceTopicModify differData = new SgsjTechnicalScienceTopicModify();
                differData.setModifyContent("验收附件");
                differData.setBeforeModify(StrUtil.isBlank(oldName) ? "" : oldName);
                differData.setAfterModify(StrUtil.isBlank(newName) ? "" : newName);
                differData.setPtVar5(newName);
                objects.add(differData);
            }
        }
        sgsjTechnicalScienceTopicModify.setModifyContent("课题附件");
        //查询是否有修改记录
        SgsjTechnicalScienceTopicModify result = technicalScienceTopicModifyService.getMaxCreateTimeDataByModifyContent(sgsjTechnicalScienceTopicModify);
        String oldName = result == null?"" : result.getAfterModify();
        //没有修改记录，则需要查询申请时的附件
        oldName = StrUtil.isBlank(oldName) ? oldData.getPtVar5() : oldName;
        String newName = getFileName(newData.getTopicFileGroupId());
        if (!compareStr(oldName, newName)) {
            SgsjTechnicalScienceTopicModify differData = new SgsjTechnicalScienceTopicModify();
            differData.setModifyContent("课题附件");
            differData.setBeforeModify(StrUtil.isBlank(oldName) ? "" : oldName);
            differData.setAfterModify(StrUtil.isBlank(newName) ? "" : newName);
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

    @Value("${kygl.mesPublish.roleKey}")
    private String roleKeyArr;
    @Value("${kygl.mesPublish.roleName}")
    private String roleNameArr;

    /**
     *
     * @param title 功能名称
     * @param message 消息内容
     * @return
     */
    //知会消息发布
    @Override
    public AjaxResult messagePublic(String title, String message) {
        Assert.isTrue(StrUtil.isNotBlank(message), "message参数不能为空");
        Assert.isTrue(StrUtil.isNotBlank(title), "title参数不能为空");
        Assert.isTrue(StrUtil.isNotBlank(roleKeyArr), "未配置消息发布角色");
        String[] roles = StrUtil.splitToArray(roleKeyArr, ",");
        AjaxResult ajaxResult = systemServiceApi.selectByRoleAndTenant(roles, SecurityUtils.getTenantKey());
        Integer code = (Integer) ajaxResult.get("code");
        Assert.isTrue(code.equals(200), "获取用户列表失败");
        String userInfoStr = JSON.toJSONString(ajaxResult.get("data"));
        Assert.isTrue(StrUtil.isNotBlank(userInfoStr), "角色未绑定用户");
        List<SysUser> sysUsers = JSON.parseArray(userInfoStr, SysUser.class);
        String clientIds = sysUsers.stream().map(SysUser::getUserName).collect(Collectors.joining(","));
        SysNotice sysNotice = new SysNotice();
        sysNotice.setNoticeTitle(title);
        sysNotice.setNoticeType("1");
        sysNotice.setNoticeScopeType("3");
        sysNotice.setNoticeScope(clientIds);
        sysNotice.setStatus("0");
        sysNotice.setNoticeContent(message);
        R r = remoteNoticeService.addForFeign(sysNotice);
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
        List<SgsjTechnicalScienceTopic> resultList = sgsjTechnicalScienceTopicMapper.getSgsjTechnicalScienceTopicListNew(param);
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
        resultMap.put("rdCost", StrUtil.isBlank(topicInfo.getRdCost())?"-":topicInfo.getRdCost());
        resultMap.put("alreadyPayCost", StrUtil.isBlank(topicInfo.getAlreadyPayCost())?"-":topicInfo.getAlreadyPayCost());
        resultMap.put("leftCost", StrUtil.isBlank(topicInfo.getLeftCost())?"-":topicInfo.getLeftCost());
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

    //获取文件名
    private String getFileName(String fileGroupId) {
        if (StrUtil.isBlank(fileGroupId)) return "";
//        String fileUrl = "http://10.0.1.118/fileservice/fileext/";
//        String fileGroupId = "45045c5b6e29ac96a811fc969fb47784";
        String url = fileUrl + "/fileext/list/" + fileGroupId;
        String jsonString = restTemplate.getForObject(url, String.class);
        List<FileDto> fileDtoList = JSONObject.parseArray(jsonString, FileDto.class);
        if (CollectionUtils.isEmpty(fileDtoList)) {
            return "";
        }
        Set<String> objects = new HashSet<>();
        for (FileDto fileDto : fileDtoList) {
            String fileName = fileDto.getFileName();
            String extension = fileDto.getExtension();
            objects.add(fileName + extension);
        }
        return String.join(" | ", objects);
    }

    //数据推送总部版
    public void doSendGm(){
        //全量推送，（已发起审批的）
        //主表
        SgsjTechnicalScienceTopic sgsjTechnicalScienceTopic = new SgsjTechnicalScienceTopic();
        List<SgsjTechnicalScienceTopic> sgsjTechnicalScienceTopicList = sgsjTechnicalScienceTopicMapper.getSgsjTechnicalScienceTopicList(sgsjTechnicalScienceTopic);
        if (CollUtil.isEmpty(sgsjTechnicalScienceTopicList)){
            //推送空数据
            sendEmpty();
            return;
        }
        //获取流程信息
        List<SgsjTechnicalScienceTopic> sgsjTechnicalScienceTopics = this.tableListFlowableInfo(sgsjTechnicalScienceTopicList);
        //（已发起审批的）
        List<SgsjTechnicalScienceTopic> collect = sgsjTechnicalScienceTopics.stream()
                .filter(p -> !p.getTaskStatus().equals("0")).collect(Collectors.toList());
        if (CollUtil.isEmpty(sgsjTechnicalScienceTopicList)){
            //推送空数据
            sendEmpty();
            return;
        }
        //专家
        SgjsExpertLibrary sgjsExpertLibrary = new SgjsExpertLibrary();
        List<SgjsExpertLibrary> sgjsExpertLibraryList = sgjsExpertLibraryService.getSgjsExpertLibraryList(sgjsExpertLibrary);
        Map<Long, List<SgjsExpertLibrary>> collect1 = sgjsExpertLibraryList.stream().collect(Collectors.groupingBy(SgjsExpertLibrary::getForeignId));
        //鉴定或评价
        SgjsAuthenticateEvaluate sgjsAuthenticateEvaluate = new SgjsAuthenticateEvaluate();
        List<SgjsAuthenticateEvaluate> shjsAuthenticateEvaluateList = shjsAuthenticateEvaluateService.getShjsAuthenticateEvaluateList(sgjsAuthenticateEvaluate);
        Map<Long, List<SgjsAuthenticateEvaluate>> collect2 = shjsAuthenticateEvaluateList.stream().collect(Collectors.groupingBy(SgjsAuthenticateEvaluate::getForeignId));
        //成果奖项
        SgjsAchievementAward sgjsAchievementAward = new SgjsAchievementAward();
        List<SgjsAchievementAward> sgjsAchievementAwardList = sgjsAchievementAwardService.getSgjsAchievementAwardList(sgjsAchievementAward);
        Map<Long, List<SgjsAchievementAward>> collect3 = sgjsAchievementAwardList.stream().collect(Collectors.groupingBy(SgjsAchievementAward::getForeignId));
        //修改记录
        SgsjTechnicalScienceTopicModify sgsjTechnicalScienceTopicModify = new SgsjTechnicalScienceTopicModify();
        List<SgsjTechnicalScienceTopicModify> sgsjTechnicalScienceTopicModifyList = technicalScienceTopicModifyService.getSgsjTechnicalScienceTopicModifyList(sgsjTechnicalScienceTopicModify);
        Map<Long, List<SgsjTechnicalScienceTopicModify>> collect4 = sgsjTechnicalScienceTopicModifyList.stream().collect(Collectors.groupingBy(SgsjTechnicalScienceTopicModify::getForeignId));
        ProjectDto projectDto = getProjectDto();
        collect.forEach(p -> {
            if (CollUtil.isNotEmpty(collect1.get(p.getId()))) p.setListApply(collect1.get(p.getId()));
            if (CollUtil.isNotEmpty(collect2.get(p.getId()))) p.setEvaluateList(collect2.get(p.getId()));
            if (CollUtil.isNotEmpty(collect3.get(p.getId()))) p.setAwardList(collect3.get(p.getId()));
            if (CollUtil.isNotEmpty(collect4.get(p.getId()))) p.setListModify(collect4.get(p.getId()));
            p.setPtVar5(projectDto.getProjectCode());
        });
        rocketMQTemplate.convertAndSend("sgsj_technical_science_topic:tenantSuccess", collect);
    }

    //推送空数据
    private void sendEmpty() {
        List<SgsjTechnicalScienceTopic> objects = new ArrayList<>();
        ProjectDto projectDto = getProjectDto();
        SgsjTechnicalScienceTopic param = new SgsjTechnicalScienceTopic();
        param.setProjectId(projectDto.getProjectId());
        objects.add(param);
        rocketMQTemplate.convertAndSend("sgsj_technical_science_topic:tenantSuccess", objects);
    }
}
