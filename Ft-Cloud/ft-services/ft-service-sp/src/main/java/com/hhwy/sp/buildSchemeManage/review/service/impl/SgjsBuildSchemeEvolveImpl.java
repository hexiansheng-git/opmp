package com.hhwy.sp.buildSchemeManage.review.service.impl;

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
        return evolveList;
    }

    @Override
    public List<SgjsBuildSchemeEvolve> getListByIds(List<Long> ids) {
        List<SgjsBuildSchemeEvolve> evolveList = sgjsBuildSchemeListService.getEvolveListByIds(ids);
        this.setSumScore(evolveList);
        this.changeDict(evolveList);
        return evolveList;
    }

    private void setSumScore(List<SgjsBuildSchemeEvolve> evolveList){
        List<Long> reviewIdList = evolveList.stream().map(SgjsBuildSchemeEvolve::getReviewId).collect(Collectors.toList());
        List<SgjsBuildSchemeReviewStaff> sumScoreList = sgjsBuildSchemeReviewStaffMapper.getSumScoreByReviewIdList(reviewIdList);
        Map<Long, Double> sumSocreMap = sumScoreList.stream().collect(Collectors.toMap(SgjsBuildSchemeReviewStaff::getReviewId, SgjsBuildSchemeReviewStaff::getScore));
        for (SgjsBuildSchemeEvolve evolve : evolveList) {
            Long reviewId = evolve.getReviewId();
            if(sumSocreMap.containsKey(reviewId)){
                evolve.setScore(sumSocreMap.get(reviewId));
            }
        }
    }

    private void changeDict(List<SgjsBuildSchemeEvolve> evolveList){
        DictUtil.dictValueToLabel(evolveList, "scheme_level", SgjsBuildSchemeEvolve::getSchemeLevel, SgjsBuildSchemeEvolve::setSchemeLevel);
        DictUtil.dictValueToLabel(evolveList, "scheme_type", SgjsBuildSchemeEvolve::getSchemeType, SgjsBuildSchemeEvolve::setSchemeType);

        for (SgjsBuildSchemeEvolve evolve : evolveList) {
            String taskStatus = evolve.getTaskStatus();
            if(StringUtils.isNotBlank(taskStatus)){
                switch (taskStatus){
                    case "0" : evolve.setTaskStatus(TaskStatus.NOT_INITIATED);
                    case "1" : evolve.setTaskStatus(TaskStatus.IN_PROGRESS);
                    case "4" : evolve.setTaskStatus(TaskStatus.COMPLETED);
                }
            }
        }
    }
}
