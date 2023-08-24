package com.hhwy.pm.qqch.review.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.common.tenant.utils.TenantDataSourceUtils;
import com.hhwy.enums.FlowEnum;
import com.hhwy.pm.constant.PmConstant;
import com.hhwy.pm.qqch.group.domain.QqchWorkGroup;
import com.hhwy.pm.qqch.group.mapper.QqchWorkGroupMapper;
import com.hhwy.pm.qqch.module.domain.QqchModuleConfirmCase;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.qqchWorkPlan.domain.QqchWorkPlan;
import com.hhwy.pm.qqch.qqchWorkPlan.domain.QqchWorkPlanDetail;
import com.hhwy.pm.qqch.qqchWorkPlan.service.IQqchWorkPlanDetailService;
import com.hhwy.pm.qqch.qqchWorkPlan.service.IQqchWorkPlanService;
import com.hhwy.pm.qqch.review.domain.Review;
import com.hhwy.pm.qqch.review.mapper.ReviewMapper;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.utils.BusinessTaskResultUtil;
import com.hhwy.utils.EntityUtils;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.common.CommonAssert;
import com.hhwy.utils.dict.DictUtil;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.redissonLock.RedissonLockUtil;
import com.hhwy.utils.tree.TreeUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;

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
        QqchWorkPlan qqchWorkPlan = (QqchWorkPlan) workPlanService.baseInfo(param);
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
            EntityUtils.setCreateUpdateInfo(review);
            iData.add(review);
        }


        List<Review> qqchReviewList = this.getQqchReviewList(new Review());

        // 如果之前有数据的话
        if (!CollectionUtils.isEmpty(qqchReviewList)) {
            this.checkData(qqchReviewList, iData);
            this.updateQqchReviewList(qqchReviewList);
            this.updateFinishNum(null,null);
        }
        this.reviewMapper.insertQqchReviewList(iData);
        

        return ;
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
        return reviewMapper.updateQqchReview(review);
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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateFinishNum(String stageIdentity, String moduleIdentity) {

        try {
            if (RedissonLockUtil.lock(stageIdentity)) {
                QqchWorkPlanDetail where = new QqchWorkPlanDetail();
                where.setItemId(moduleIdentity);
                switch (stageIdentity) {
                    case "1":
                        where.setIsFirst("1");
                        break;
                    case "2":
                        where.setIsSecond("1");
                        break;
                    case "3":
                        where.setIsThird("1");
                        break;
                    default:
                }
                // 查询工作计划的数据
                List<QqchWorkPlanDetail> qqchWorkPlanDetailList = workPlanDetailService.getQqchWorkPlanDetailList(where);
                // 模块Id
                List<String> moduleIdentityList = qqchWorkPlanDetailList.stream().map(QqchWorkPlanDetail::getItemId).map(String::valueOf).filter(Objects::nonNull).distinct().collect(toList());
                // 编制人
                List<String> editFirstList = qqchWorkPlanDetailList.stream().map(QqchWorkPlanDetail::getEditorFirst).filter(Objects::nonNull).distinct().collect(toList());
                List<String> editSecondList = qqchWorkPlanDetailList.stream().map(QqchWorkPlanDetail::getEditorSecond).filter(Objects::nonNull).distinct().collect(toList());
                List<String> editThirdList = qqchWorkPlanDetailList.stream().map(QqchWorkPlanDetail::getEditorThird).filter(Objects::nonNull).distinct().collect(toList());
                
                ArrayList<String> allConfirmList = new ArrayList<>(editFirstList);
                allConfirmList.addAll(editSecondList);
                allConfirmList.addAll(editThirdList);

                QqchModuleConfirmCase moduleWhere = new QqchModuleConfirmCase();
                moduleWhere.setConfirmStatus("1");
                moduleWhere.setModuleIdentityList(moduleIdentityList);
                moduleWhere.setConfirmUserList(allConfirmList);
                // 根据阶段 模块Id 模块负责人去记录表中查询记录数量
                List<QqchModuleConfirmCase> qqchModuleConfirmCaseList = moduleConfirmCaseService.getModuleConfirmInfo(moduleWhere);
                // 查询到之后根据阶段分组 其中数组数量就是确认数量
                Map<String, List<QqchModuleConfirmCase>> stageMap = qqchModuleConfirmCaseList.stream().filter(item->StringUtils.isNotEmpty(item.getStageIdentity())).collect(Collectors.groupingBy(QqchModuleConfirmCase::getStageIdentity));
                
                List<Review> qqchReviewList = this.reviewMapper.getQqchReviewList(new Review());
                Map<String, List<Review>> reviewStageMap = qqchReviewList.stream().collect(Collectors.groupingBy(Review::getPlanStage));
                for (String stage : stageMap.keySet()) {
                    List<QqchModuleConfirmCase> qqchModuleConfirmCases = stageMap.get(stage);
                    List<Review> reviews = reviewStageMap.get(stage);
                    if (!CollectionUtils.isEmpty(reviews)){
                        Review review = reviews.get(0);
                        review.setFinishNum(qqchModuleConfirmCases.size());
                        EntityUtils.setUpdateInfo(review);
                    }
                }
                this.reviewMapper.updateQqchReviewList(qqchReviewList);
            }
        } finally {
            RedissonLockUtil.unlock(stageIdentity);
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
    public void listener(Long id) {
        Review qqchReview = this.getQqchReview(new Review(id));
        qqchReview.setTaskStatus("5");
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

    
}
