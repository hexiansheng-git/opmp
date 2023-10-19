package com.hhwy.pm.qqch.review.service.impl;

import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.hhwy.common.core.exception.CustomException;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.common.tenant.utils.TenantDataSourceUtils;
import com.hhwy.constant.WarnItem;
import com.hhwy.constant.WarnScopeType;
import com.hhwy.enums.FlowEnum;
import com.hhwy.feign.service.SystemServiceApi;
import com.hhwy.pm.common.FlowInfoSearchUtil;
import com.hhwy.pm.constant.PmConstant;
import com.hhwy.pm.core.sync.service.ISysSyncInfoService;
import com.hhwy.pm.qqch.group.domain.QqchWorkGroup;
import com.hhwy.pm.qqch.group.domain.QqchWorkGroupMember;
import com.hhwy.pm.qqch.group.mapper.QqchWorkGroupMapper;
import com.hhwy.pm.qqch.group.service.IQqchWorkGroupMemberService;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.qqchWorkPlan.domain.QqchWorkPlan;
import com.hhwy.pm.qqch.qqchWorkPlan.domain.QqchWorkPlanDetail;
import com.hhwy.pm.qqch.qqchWorkPlan.service.IQqchWorkPlanDetailService;
import com.hhwy.pm.qqch.qqchWorkPlan.service.IQqchWorkPlanService;
import com.hhwy.pm.qqch.review.domain.Review;
import com.hhwy.pm.qqch.review.mapper.ReviewMapper;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.warn.WarnService;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractInfo;
import com.hhwy.pm.xmsl.contractInfo.service.IXmslContractInfoService;
import com.hhwy.pm.xmsl.project.domain.vo.ProjectBasicInfo;
import com.hhwy.pm.xmsl.project.service.IXmslProjectBasicInfoService;
import com.hhwy.system.api.domain.SysTenant;
import com.hhwy.utils.BusinessTaskResultUtil;
import com.hhwy.utils.EntityUtils;
import com.hhwy.utils.common.CommonAssert;
import com.hhwy.utils.date.FtDateUtils;
import com.hhwy.utils.dict.DictUtil;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.redissonLock.RedissonLockUtil;
import com.hhwy.utils.tree.TreeUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

import static java.util.stream.Collectors.toList;

/**
 * @author mls
 * @date 2023-07-18 09:58:24
 * @remark
 */
@Service
public class QqchReviewServiceImpl implements IQqchReviewService {
    private final static String ONE = "1";//菜单进入
    private final static String TWO = "2";//详情和编辑
    private final static String THREE = "3";//调整

    private final static FlowEnum flowEnum = FlowEnum.QQCH_REVIEW;
    @Resource
    private ReviewMapper reviewMapper;

    @Autowired
    private QqchWorkGroupMapper qqchWorkGroupMapper;

    @Resource
    private IQqchWorkPlanService workPlanService;

    @Resource
    private IQqchWorkPlanDetailService workPlanDetailService;
    
    @Resource
    private IQqchModuleConfirmCaseService moduleConfirmCaseService;
    @Resource
    private ISysSyncInfoService sysSyncInfoService;

    @Autowired
    private IXmslProjectBasicInfoService xmslProjectBasicInfoService;

    @Autowired
    private WarnService warnService;

    @Autowired
    private SystemServiceApi systemServiceApi;

    @Autowired
    private IXmslContractInfoService xmslContractInfoService;

    @Autowired
    private IQqchWorkPlanDetailService qqchWorkPlanDetailService;

    @Autowired
    private IQqchWorkGroupMemberService qqchWorkGroupMemberService;

    public Review getQqchReview(Review review) {
        return reviewMapper.getQqchReview(review);
    }

    public List<Review> getQqchReviewList(Review review) {
        return reviewMapper.getQqchReviewList(review);
    }

    @Transactional(rollbackFor = Exception.class)
    public int insertQqchReview(Review review) {
        review.setId(IdWorker.createId());
        review.setCreateUser(SecurityUtils.getUserName());
        review.setCreateTime(DateUtils.getNowDate());
        return reviewMapper.insertQqchReview(review);
    }

    @Override
    @Transactional
    public void savePlan(Long workPlanId) {
        HashMap<String, String> param = new HashMap<>();
        param.put("id", workPlanId + "");
        param.put("type", "2");
        QqchWorkPlan qqchWorkPlan = workPlanService.baseInfo(param);
        List<QqchWorkPlanDetail> detailList = qqchWorkPlan.getDetailList();
        List<QqchWorkPlanDetail> qqchWorkPlanDetails = TreeUtil.treeToList(detailList);
        int firstNum = 0;
        int secondNum = 0;
        int thirdNum = 0;

        StringBuilder firstStage = new StringBuilder();
        StringBuilder seconStage = new StringBuilder();
        StringBuilder thirdStage = new StringBuilder();

        List<String> planStages = DictUtil.getDictValueList("plan_stage");
        for (QqchWorkPlanDetail detail : qqchWorkPlanDetails) {
            if ("1".equals(detail.getIsFirst()) && "1".equals(detail.getPtVar1())) {
                firstNum++;
                firstStage.append(detail.getItemName()).append(",");
            }
            if ("1".equals(detail.getIsSecond()) && "1".equals(detail.getPtVar1())) {
                secondNum++;
                seconStage.append(detail.getItemName()).append(",");
            }
            if ("1".equals(detail.getIsThird()) && "1".equals(detail.getPtVar1())) {
                thirdNum++;
                thirdStage.append(detail.getItemName()).append(",");
            }
        }


        QqchWorkGroup planEstablishDirector = this.getPlanEstablishDirector();


        List<Review> iData = new ArrayList<>();
        for (String planStage : planStages) {

            Review review = new Review();
            review.setId(IdWorker.createId());
            review.setPlanStage(planStage);
            review.setPlanEstablishDirectorId(planEstablishDirector.getPlanEstablishDirectorId());
            review.setPlanEstablishDirector(planEstablishDirector.getPlanEstablishDirector());

            String cont = "";
            Date date = null;
            Integer num = null;
            String stage = "";
            switch (planStage) {
                case "1":
                    num = firstNum;
                    cont = qqchWorkPlan.getContentFirst();
                    date = qqchWorkPlan.getSubmitFirst();
                    stage = firstStage.toString();
                    break;
                case "2":
                    num = secondNum;
                    cont = qqchWorkPlan.getContentSecond();
                    date = qqchWorkPlan.getSubmitSecond();
                    stage = seconStage.toString();
                    break;
                case "3":
                    num = thirdNum;
                    cont = qqchWorkPlan.getContentThird();
                    date = qqchWorkPlan.getSubmitThird();
                    stage = thirdStage.toString();
                    break;
                default:
            }
            review.setStage(stage);
            review.setPlanNum(num);
            review.setPlanContent(cont);
            review.setReqSubmitDate(date);
            review.setTaskStatus("0");
            review.setFinishNum(0);
            review.setExigencyStatus(qqchWorkPlan.getExigencyStatus());
            EntityUtils.setCreateUpdateInfo(review);
            iData.add(review);
        }


        List<Review> qqchReviewList = this.getQqchReviewList(new Review());

        // 如果之前有数据的话
        if (!CollectionUtils.isEmpty(qqchReviewList)) {
            this.checkData(qqchReviewList, iData);
            this.updateQqchReviewList(qqchReviewList);
            this.updateFinishNum();
        }else {
            this.reviewMapper.insertQqchReviewList(iData);
        }
    }

    private void checkData(List<Review> qqchReviewList, List<Review> iData) {
        StringBuffer erroMsg = new StringBuffer();
        for (Review review : qqchReviewList) {

            iData.stream().filter(item -> item.getPlanStage().equals(review.getPlanStage())).findFirst().ifPresent(i -> {

                if ("1".equals(review.getTaskStatus())) {
                    erroMsg.append("第").append(review.getPlanStage()).append("阶段的数据正在审中,不能更改策划项名称");
                }
                // 只有某个阶段中的数据没有提交才能进行更改 审批完成的不管
                if ("0".equals(review.getTaskStatus())) {
                    review.setPlanEstablishDirectorId(i.getPlanEstablishDirectorId());
                    review.setPlanEstablishDirector(i.getPlanEstablishDirector());
                    review.setStage(i.getStage());
                    review.setPlanNum(i.getPlanNum());
                    review.setPlanContent(i.getPlanContent());
                    review.setReqSubmitDate(i.getReqSubmitDate());
                }

            });

        }
        if (StringUtils.isNotEmpty(erroMsg.toString())) {
            throw new CustomBusinessException(erroMsg.toString());
        }

    }


    private QqchWorkGroup getPlanEstablishDirector() {
        QqchWorkGroup where = new QqchWorkGroup();
        where.setEffective("1");
        return qqchWorkGroupMapper.getQqchWorkGroup(where);
    }


    @Transactional(rollbackFor = Exception.class)
    public int insertQqchReviewList(List<Review> reviewList) {
        for (Review review : reviewList) {
            review.setId(IdWorker.createId());
            review.setCreateUser(SecurityUtils.getUserName());
            review.setCreateTime(DateUtils.getNowDate());
        }
        return reviewMapper.insertQqchReviewList(reviewList);
    }

    @Transactional(rollbackFor = Exception.class)
    public int updateQqchReview(Review review) {
        review.setUpdateUser(SecurityUtils.getUserName());
        review.setUpdateTime(DateUtils.getNowDate());
        int result = reviewMapper.updateQqchReview(review);
        //推送到总部版
        sysSyncInfoService.pushQqchReview(review);
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    public int updateQqchReviewList(List<Review> reviewList) {
        for (Review review : reviewList) {
            review.setUpdateUser(SecurityUtils.getUserName());
            review.setUpdateTime(DateUtils.getNowDate());
        }
        return reviewMapper.updateQqchReviewList(reviewList);
    }

    @Transactional(rollbackFor = Exception.class)
    public int deleteQqchReview(Review review) {
        review.setUpdateUser(SecurityUtils.getUserName());
        review.setUpdateTime(DateUtils.getNowDate());
        return reviewMapper.deleteQqchReview(review);
    }

    @Transactional(rollbackFor = Exception.class)
    public int deleteQqchReviewByPks(List<Long> qqchReviewPkList) {
        return reviewMapper.deleteQqchReviewByPks(qqchReviewPkList);
    }

    /**
     * 新增编辑 详情请求接口
     *
     * @param map
     * @return
     */
    @Override
    public Review reviewInfo(Map<String, String> map) {
        String id = map.get("id");
        CommonAssert.notBlank(id, "id不能为空");
        Long idl = Long.valueOf(id);
        // 根据id查询数据
        Review review = this.getQqchReview(new Review(idl));
        review.setInitDate(new Date());
        review.setInitUserId(SecurityUtils.getUserId());
        review.setInitUserName(SecurityUtils.getUserName());
        BusinessTaskResultUtil.handleProcessData(review, FlowEnum.QQCH_REVIEW);
        return review;
    }

    @Override
    @Deprecated
    public void updateFinishNum(String stageIdentity, String moduleIdentity){
        // 获取代理对象
        IQqchReviewService o = (IQqchReviewService)AopContext.currentProxy();
        o.updateFinishNum();
    }

    /**
     * 确认更新阶段确认功能数量 全量更新一下
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateFinishNum() {

        try {
            if (RedissonLockUtil.lock("stageIdentity")) {
                QqchWorkPlanDetail where = new QqchWorkPlanDetail();
                where.setDelFlag("0");
                // 查询工作计划的数据
                List<QqchWorkPlanDetail> qqchWorkPlanDetailList = workPlanDetailService.getQqchWorkPlanDetailList(where);

                // 第一阶段的菜单id
                List<String> firstList = qqchWorkPlanDetailList.stream()
                        .filter(i->"1".equals(i.getIsFirst()))
                        .map(QqchWorkPlanDetail::getItemId)
                        .filter(Objects::nonNull)
                        .distinct().collect(toList());

                // 第2阶段的菜单id
                List<String> secondList = qqchWorkPlanDetailList.stream()
                        .filter(i->"1".equals(i.getIsSecond()))
                        .map(QqchWorkPlanDetail::getItemId)
                        .filter(Objects::nonNull).distinct().collect(toList());

                // 第3阶段的菜单id
                List<String> thirdList = qqchWorkPlanDetailList.stream()
                        .filter(i->"1".equals(i.getIsThird()))
                        .map(QqchWorkPlanDetail::getItemId)
                        .filter(Objects::nonNull)
                        .distinct().collect(toList());

                // 根据阶段, 模块Id集合 去记录表中查询记录数量
                int firstNum = moduleConfirmCaseService.getConfirmNumByStage("1",firstList);
                int secondNum = moduleConfirmCaseService.getConfirmNumByStage("2",secondList);
                int thirdNum = moduleConfirmCaseService.getConfirmNumByStage("3",thirdList);

                List<Review> qqchReviewList = this.reviewMapper.getQqchReviewList(new Review());

                for (Review review : qqchReviewList) {
                    String planStage = review.getPlanStage();
                    switch (planStage) {
                        case "1":
                            review.setPlanNum(firstList.size());
                            review.setFinishNum(firstNum);
                            break;
                        case "2":
                            review.setPlanNum(secondList.size());
                            review.setFinishNum(secondNum);
                            break;
                        case "3":
                            review.setPlanNum(thirdList.size());
                            review.setFinishNum(thirdNum);
                            break;
                        default:
                    }
                }

                if(!CollectionUtils.isEmpty(qqchReviewList)){
                    this.reviewMapper.updateQqchReviewList(qqchReviewList);
                }
            }
        } finally {
            RedissonLockUtil.unlock("stageIdentity");
        }

    }

    @Override
    public void canAdjust() {
        Review where = new Review();
        where.setTaskStatus("1");
        List<Review> qqchReviewList = this.getQqchReviewList(where);
        if (!CollectionUtils.isEmpty(qqchReviewList)) {
            throw new CustomBusinessException("前期策划评审有正在审批中的阶段数据,请退回审批或等审批结束后才能调整");
        }
    }

    @Override
    @Transactional
    public void listener(Long id) {
        Review qqchReview = this.getQqchReview(new Review(id));
        qqchReview.setTaskStatus("5");
        qqchReview.setReviewStatus("4");
        qqchReview.setReviewCompleteDate(DateUtils.getNowDate());
        this.updateQqchReview(qqchReview);
    }

    @Override
    public String getStage() {
        Review where = new Review();
        where.setDelFlag("0");
        List<Review> qqchReviewList = this.getQqchReviewList(where).stream().sorted(Comparator.comparing(Review::getPlanStage)).collect(toList());
        for (Review review : qqchReviewList) {
            String taskStatus = review.getTaskStatus();
            if (!"5".equals(taskStatus)){
                return review.getPlanStage();
            }
        }
        // 已经结束 不用查询阶段
        return PmConstant.END_STAGE;
    }

    /**
     * 通过阶段获取前期策划评审数据
     * @param planStage
     * @return
     */
    @Override
    public Review getReviewByPlanStage(String planStage){
        return reviewMapper.getReviewByPlanStage(planStage);
    }

    /**
     * 根据阶段获取该阶段是否已编制完成
     * @param planStage
     * @return
     */
    private boolean getWhetherCompleteByStage(String planStage){
        int count = reviewMapper.getFinishedReviewCountByStage(planStage);
        return count > 0;
    }

    /**
     * 获取阶段预警人
     * @param planStage
     * @return
     */
    private String getStageWarnScope(String planStage){
        StringBuilder warnScope = new StringBuilder();
        //获取填报人员
        List<Long> editorList = qqchWorkPlanDetailService.getEditorListByPlanStage(planStage);
        //获取工作小组组长
        List<QqchWorkGroupMember> groupLeader = qqchWorkGroupMemberService.getGroupLeader();

        for (Long editor : editorList) {
            warnScope.append(editor).append(",");
        }
        for (QqchWorkGroupMember member : groupLeader) {
            warnScope.append(member.getDirectorUserName()).append(",");
        }
        return warnScope.toString();
    }

    /**
     * 前期策划编制第一阶段预警
     */
    @Override
    public void preparationFirstStageWarn() {
        //切换到master
        String oldDataSource = DynamicDataSourceContextHolder.peek();
        DynamicDataSourceContextHolder.push("master");
        //获取所有租户
        List<SysTenant> tenantList = systemServiceApi.tenantList();

        try {
            for (SysTenant tenant : tenantList) {
                //切换租户
                String tenantKey = tenant.getTenantKey();
                String dataSource = TenantDataSourceUtils.getDataSourceNameByTenantKey(tenantKey);
                DynamicDataSourceContextHolder.push(dataSource);

                /*判断第一阶段编制是否已完成*/
                boolean whetherCompleteByStage = this.getWhetherCompleteByStage("1");
                if(whetherCompleteByStage){
                    continue;
                }

                //获取项目数据
                ProjectBasicInfo projectInfo = xmslProjectBasicInfoService.projectInfo();
                if(projectInfo == null){
                    continue;
                }
                /*中标日期*/
                Date winTheBiddingDate = projectInfo.getWinTheBiddingDate();
                if(winTheBiddingDate == null){
                    continue;
                }
                Date nowDate = DateUtils.getNowDate();
                Long diffDays = FtDateUtils.getDays(winTheBiddingDate, nowDate);
                if(diffDays > 30){
                    /*发送预警*/
                    String warnScope = getStageWarnScope("1");
                    if(StringUtils.isNotBlank(warnScope)){
                        warnService.addWarn(WarnItem.PREPARATION_FIRST_STAGE, WarnScopeType.USER,null,warnScope,tenantKey);
                    }
                }
            }
        }catch (Exception e){
            throw new CustomException(e.getMessage());
        }finally {
            DynamicDataSourceContextHolder.poll();
            DynamicDataSourceContextHolder.push(oldDataSource);
        }
    }

    /**
     * 前期策划编制第二阶段预警
     */
    @Override
    public void preparationSecondStageWarn() {
        //切换到master
        String oldDataSource = DynamicDataSourceContextHolder.peek();
        DynamicDataSourceContextHolder.push("master");
        //获取所有租户
        List<SysTenant> tenantList = systemServiceApi.tenantList();

        try {
            for (SysTenant tenant : tenantList) {
                //切换租户
                String tenantKey = tenant.getTenantKey();
                String dataSource = TenantDataSourceUtils.getDataSourceNameByTenantKey(tenantKey);
                DynamicDataSourceContextHolder.push(dataSource);

                /*判断第二阶段编制是否已完成*/
                boolean whetherCompleteByStage = this.getWhetherCompleteByStage("2");
                if(whetherCompleteByStage){
                    continue;
                }

                //获取项目数据
                ProjectBasicInfo projectInfo = xmslProjectBasicInfoService.projectInfo();
                //获取合同数据
                XmslContractInfo contractInfo = xmslContractInfoService.getValidMaxVersionContractInfo();
                /*开工日期*/
                Date startDate = null;
                /*合同签订日期*/
                Date signDate = null;
                if(projectInfo != null){
                    startDate = projectInfo.getStartDate();
                }
                if(contractInfo != null){
                    signDate = contractInfo.getSignDate();
                }
                if(startDate == null && signDate == null){
                    continue;
                }

                Date signDateCutOffTime = null;
                if(signDate != null){
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(signDate);
                    calendar.add(Calendar.MONTH,1);
                    signDateCutOffTime = calendar.getTime();
                }

                Date smallDate = startDate;
                if(smallDate == null){
                    smallDate = signDate;
                }else if(signDateCutOffTime != null && signDateCutOffTime.compareTo(startDate) < 0){
                    smallDate = signDateCutOffTime;
                }

                Date nowDate = DateUtils.getNowDate();
                if(nowDate.compareTo(smallDate) > 0){
                    String warnScope = getStageWarnScope("1");
                    if(StringUtils.isNotBlank(warnScope)){
                        warnService.addWarn(WarnItem.PREPARATION_SECOND_STAGE,WarnScopeType.USER,null,"admin",tenantKey);
                    }
                }
            }
        }catch (Exception e){
            throw new CustomException(e.getMessage());
        }finally {
            DynamicDataSourceContextHolder.poll();
            DynamicDataSourceContextHolder.push(oldDataSource);
        }
    }

    /**
     * 前期策划编制第三阶段预警
     */
    @Override
    public void preparationThirdStageWarn() {
        //切换到master
        String oldDataSource = DynamicDataSourceContextHolder.peek();
        DynamicDataSourceContextHolder.push("master");
        //获取所有租户
        List<SysTenant> tenantList = systemServiceApi.tenantList();

        try {
            for (SysTenant tenant : tenantList) {
                //切换租户
                String tenantKey = tenant.getTenantKey();
                String dataSource = TenantDataSourceUtils.getDataSourceNameByTenantKey(tenantKey);
                DynamicDataSourceContextHolder.push(dataSource);

                /*判断第三阶段编制是否已完成*/
                boolean whetherCompleteByStage = this.getWhetherCompleteByStage("3");
                if(whetherCompleteByStage){
                    continue;
                }

                //获取项目数据
                ProjectBasicInfo projectInfo = xmslProjectBasicInfoService.projectInfo();
                if(projectInfo == null){
                    continue;
                }
                /*开工日期*/
                Date startDate = projectInfo.getStartDate();
                if(startDate == null){
                    continue;
                }

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(startDate);
                calendar.add(Calendar.MONTH,3);
                /*截止时间*/
                Date cutOffTime = calendar.getTime();
                Date nowDate = DateUtils.getNowDate();

                if(nowDate.compareTo(cutOffTime) > 0){
                    /*发送预警*/
                    String warnScope = getStageWarnScope("1");
                    if(StringUtils.isNotBlank(warnScope)) {
                        warnService.addWarn(WarnItem.PREPARATION_THIRD_STAGE, WarnScopeType.USER, null, "admin", tenantKey);
                    }
                }
            }
        }catch (Exception e){
            throw new CustomException(e.getMessage());
        }finally {
            DynamicDataSourceContextHolder.poll();
            DynamicDataSourceContextHolder.push(oldDataSource);
        }
    }

    /**
     * 前期策划评审预警
     */
    @Override
    public void reviewWarn() {
        //切换到master
        String oldDataSource = DynamicDataSourceContextHolder.peek();
        DynamicDataSourceContextHolder.push("master");
        //获取所有租户
        List<SysTenant> tenantList = systemServiceApi.tenantList();

        try {
            for (SysTenant tenant : tenantList) {
                //切换租户
                String tenantKey = tenant.getTenantKey();
                String dataSource = TenantDataSourceUtils.getDataSourceNameByTenantKey(tenantKey);
                DynamicDataSourceContextHolder.push(dataSource);

                Review approvedDate = this.getApprovedDate(tenantKey);
                if(approvedDate == null){
                    continue;
                }
                Date initDate = approvedDate.getInitDate();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(initDate);
                calendar.add(Calendar.DATE,7);
                Date cutOffTime = calendar.getTime();
                Date nowDate = DateUtils.getNowDate();

                if(nowDate.compareTo(cutOffTime) > 0){
                    /*发送预警*/
                    String instanceId = approvedDate.getInstanceId();
                    StringBuilder warnScope = new StringBuilder();
                    String approve = FlowInfoSearchUtil.getApprove(instanceId);
                    warnScope.append(approve).append("yinqingbo");
                    warnService.addWarn(WarnItem.QQCH_REVIEW,WarnScopeType.USER,null,warnScope.toString(),tenantKey);
                }
            }
        }catch (Exception e){
            throw new CustomException(e.getMessage());
        }finally {
            DynamicDataSourceContextHolder.poll();
            DynamicDataSourceContextHolder.push(oldDataSource);
        }
    }

    /**
     * 获取当前正在审批中的评审数据
     * @return
     */
    private Review getApprovedDate(String tenantKey){
        Review review = reviewMapper.getApprovedData();
        if(review != null){
            String planStage = review.getPlanStage();
            if("3".equals(planStage)){
                FlowInfoSearchUtil.setInstanceId(review,FlowEnum.QQCH_REVIEW2,tenantKey);
            }else {
                FlowInfoSearchUtil.setInstanceId(review,FlowEnum.QQCH_REVIEW,tenantKey);
            }
        }
        return review;
    }
}
