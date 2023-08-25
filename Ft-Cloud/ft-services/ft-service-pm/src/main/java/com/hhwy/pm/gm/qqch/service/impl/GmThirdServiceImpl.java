package com.hhwy.pm.gm.qqch.service.impl;

import com.alibaba.nacos.client.utils.TenantUtil;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.hhwy.common.core.utils.token.TokenUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.common.tenant.utils.TenantDataSourceUtils;
import com.hhwy.pm.gm.qqch.service.IGmThirdService;
import com.hhwy.pm.qqch.evaluation.domain.QqchSummaryEvaluation;
import com.hhwy.pm.qqch.evaluation.service.IQqchSummaryEvaluationService;
import com.hhwy.pm.qqch.review.domain.Review;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.exception.CustomBusinessException;
import org.bouncycastle.jcajce.provider.util.SecretKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GmThirdServiceImpl implements IGmThirdService {
    @Autowired
    private IQqchReviewService reviewService;
    @Autowired
    private IQqchSummaryEvaluationService summaryEvaluationService;
    
    @Override
    public Map<String, List<Review>> reviewList(Map map) {
        if(ObjectUtils.isBlank(map.get("tenantKeys")))
            return new HashMap<>(2);
        String[] tenantKeys = map.get("tenantKeys").toString().split(",");
        Map<String,List<Review>> resuMap = new HashMap<>();
        Review queryReview = new Review();
        queryReview.setPlanStage(ObjectUtils.nvlString(map.get("planStage")));
        for (int i = 0; i < tenantKeys.length; i++) {
            //切换租户 
            String oldDataSource = DynamicDataSourceContextHolder.peek();
            DynamicDataSourceContextHolder.push(TenantDataSourceUtils.getDataSourceNameByTenantKey(tenantKeys[i]));
            try {
                List<Review> list = this.reviewService.getQqchReviewList(queryReview);
                resuMap.put(tenantKeys[i], list);
            }catch (Exception e){
                e.printStackTrace();
                throw new CustomBusinessException(e.getMessage());
            }finally {
                DynamicDataSourceContextHolder.poll();
                DynamicDataSourceContextHolder.push(oldDataSource);
            }
        }
        return resuMap;
    }

    @Override
    public List<QqchSummaryEvaluation> evauluationList(Map map) {
        if(ObjectUtils.isBlank(map.get("tenantKeys")))
            return new ArrayList<>(2);
        String[] tenantKeys = map.get("tenantKeys").toString().split(",");
        List<QqchSummaryEvaluation> list = new ArrayList<>();
        for (int i = 0; i < tenantKeys.length; i++) {
            //切换租户 
            String oldDataSource = DynamicDataSourceContextHolder.peek();
            DynamicDataSourceContextHolder.push(TenantDataSourceUtils.getDataSourceNameByTenantKey(tenantKeys[i]));
            try {
                QqchSummaryEvaluation temp = summaryEvaluationService.getQqchSummaryEvaluation(new QqchSummaryEvaluation());
                temp.setProjectName(tenantKeys[i]);
                list.add(temp);
            }catch (Exception e){
                e.printStackTrace();
                throw new CustomBusinessException(e.getMessage());
            }finally {
                DynamicDataSourceContextHolder.poll();
                DynamicDataSourceContextHolder.push(oldDataSource);
            }    
        }
        return list;
    }
}
