package com.hhwy.sp.buildSchemeManage.review.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.sp.buildSchemeManage.review.constant.TaskStatus;
import com.hhwy.sp.buildSchemeManage.review.domain.SgjsBuildSchemeEvolve;
import com.hhwy.sp.buildSchemeManage.review.domain.SgjsBuildSchemeReviewStaff;
import com.hhwy.sp.buildSchemeManage.review.domain.vo.BuildSchemeEvolveQueryVo;
import com.hhwy.sp.buildSchemeManage.review.mapper.SgjsBuildSchemeReviewStaffMapper;
import com.hhwy.sp.buildSchemeManage.review.service.ISgjsBuildSchemeEvolveService;
import com.hhwy.sp.buildSchemeManage.sgjsBuildSchemeList.service.ISgjsBuildSchemeListService;
import com.hhwy.utils.dict.DictUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author han
 * @date 2024-03-20 09:39:35
 * @remark
 */
@Service
public class SgjsBuildSchemeEvolveImpl implements ISgjsBuildSchemeEvolveService {

    @Autowired
    private ISgjsBuildSchemeListService sgjsBuildSchemeListService;

    @Autowired
    private SgjsBuildSchemeReviewStaffMapper sgjsBuildSchemeReviewStaffMapper;


    @Override
    public List<SgjsBuildSchemeEvolve> getListByQueryVo(BuildSchemeEvolveQueryVo queryVo) {
        List<SgjsBuildSchemeEvolve> evolveList = sgjsBuildSchemeListService.getListByEvolveQueryVo(queryVo);
        if(CollectionUtils.isEmpty(evolveList)){
            return evolveList;
        }
        this.setSumScore(evolveList);
        this.changeDict(evolveList);
        this.setSchemeEvolve(evolveList);
        return evolveList;
    }

    @Override
    public List<SgjsBuildSchemeEvolve> getListByIds(List<Long> ids) {
        List<SgjsBuildSchemeEvolve> evolveList = sgjsBuildSchemeListService.getEvolveListByIds(ids);
        this.setSumScore(evolveList);
        this.changeDict(evolveList);
        this.setSchemeEvolve(evolveList);
        return evolveList;
    }

    private void setSumScore(List<SgjsBuildSchemeEvolve> evolveList){
        List<Long> reviewIdList = evolveList.stream().map(SgjsBuildSchemeEvolve::getReviewId).collect(Collectors.toList());
        List<SgjsBuildSchemeReviewStaff> sumScoreList = sgjsBuildSchemeReviewStaffMapper.getSumScoreByReviewIdList(reviewIdList);
        Map<Long, Double> sumSocreMap = sumScoreList.stream().collect(Collectors.toMap(SgjsBuildSchemeReviewStaff::getReviewId, SgjsBuildSchemeReviewStaff::getScore));
        for (SgjsBuildSchemeEvolve evolve : evolveList) {
            Long reviewId = evolve.getReviewId();
            if(sumSocreMap.containsKey(reviewId)){
                Double score = sumSocreMap.get(reviewId);
                if(score != null){
                    score = (double) Math.round(score);
                }
                evolve.setScore(score);
            }
        }
    }

    private void changeDict(List<SgjsBuildSchemeEvolve> evolveList){
        DictUtil.dictValueToLabel(evolveList, "scheme_level", SgjsBuildSchemeEvolve::getSchemeLevel, SgjsBuildSchemeEvolve::setSchemeLevel);
        DictUtil.dictValueToLabel(evolveList, "scheme_type_all", SgjsBuildSchemeEvolve::getSchemeType, SgjsBuildSchemeEvolve::setSchemeType);

        for (SgjsBuildSchemeEvolve evolve : evolveList) {
            String taskStatus = evolve.getTaskStatus();
            if(StringUtils.isNotBlank(taskStatus)){
                switch (taskStatus){
                    case "0" :
                        evolve.setTaskStatus(TaskStatus.NOT_INITIATED.getValue());
                        break;
                    case "1" :
                        evolve.setTaskStatus(TaskStatus.IN_PROGRESS.getValue());
                        break;
                    case "4" :
                        evolve.setTaskStatus(TaskStatus.COMPLETED.getValue());
                        break;
                }
            }
        }
    }

    /**
     * 设置方案进展
     * @param evolveList
     */
    private void setSchemeEvolve(List<SgjsBuildSchemeEvolve> evolveList){
        for (SgjsBuildSchemeEvolve evolve : evolveList) {
            String changeType = evolve.getChangeType();
            if("4".equals(changeType)){
                //改数据是废弃数据
                evolve.setSchemeApprovalTimeColor("gray");
                evolve.setSubmitDateColor("gray");
                evolve.setSchemeEvolve("废弃");
                continue;
            }
            Date planImplementTime = evolve.getPlanImplementTime();//计划实施时间
            if(planImplementTime != null){
                Date schemeApprovalTime = evolve.getSchemeApprovalTime();//方案审核通过时间
                if(schemeApprovalTime == null){
                    schemeApprovalTime = DateUtils.getNowDate();
                }
                int compare = schemeApprovalTime.compareTo(planImplementTime);
                if(compare > 0){
                    evolve.setSchemeApprovalTimeColor("red");
                }else {
                    evolve.setSchemeApprovalTimeColor("green");
                }
            }

            Date completionTime = evolve.getCompletionTime();//编制完成时间
            if(completionTime != null){
                Date submitDate = evolve.getSubmitDate();//提交审核时间
                if(submitDate == null){
                    submitDate = DateUtils.getNowDate();
                }
                int compare = submitDate.compareTo(completionTime);
                if(compare > 0){
                    evolve.setSubmitDateColor("red");
                }else {
                    evolve.setSubmitDateColor("green");
                }
            }

            //设置进展状态
            if("red".equals(evolve.getSchemeApprovalTimeColor())|| "red".equals(evolve.getSubmitDateColor())){
                evolve.setSchemeEvolve("滞后");
            }else{
                evolve.setSchemeEvolve("正常");
            }
        }
    }
}
