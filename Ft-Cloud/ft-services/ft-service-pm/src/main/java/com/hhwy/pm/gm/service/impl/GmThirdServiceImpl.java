package com.hhwy.pm.gm.service.impl;

import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.web.page.TableDataInfo;
import com.hhwy.common.tenant.utils.TenantDataSourceUtils;
import com.hhwy.feign.service.SystemServiceApi;
import com.hhwy.pm.gm.mapper.GmThirdMapper;
import com.hhwy.pm.gm.service.IGmThirdService;
import com.hhwy.pm.qqch.evaluation.domain.QqchSummaryEvaluation;
import com.hhwy.pm.qqch.evaluation.service.IQqchSummaryEvaluationService;
import com.hhwy.pm.qqch.qqchPerformInspection.domain.QqchPerformInspection;
import com.hhwy.pm.qqch.qqchPerformInspection.service.IQqchPerformInspectionService;
import com.hhwy.pm.qqch.qqchWorkPlan.domain.QqchWorkPlan;
import com.hhwy.pm.qqch.qqchWorkPlan.service.IQqchWorkPlanService;
import com.hhwy.pm.qqch.review.domain.Review;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.system.api.domain.SysTenant;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.PageFuncUtils;
import com.hhwy.utils.exception.CustomBusinessException;
import io.netty.util.internal.ObjectUtil;
import io.seata.core.protocol.MergedWarpMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

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
    @Autowired
    private SystemServiceApi systemServiceApi;
    @Autowired
    private GmThirdMapper gmThirdMapper;
    @Autowired
    private IQqchPerformInspectionService performInspectionService;
    @Autowired
    private IQqchWorkPlanService workPlanService;

    @Override
    public TableDataInfo workPlanList(Map map) {
        if(ObjectUtils.isBlank(map.get("tenantKeys"))){
            return new TableDataInfo(new ArrayList<>(2),0);
        }
        List<QqchWorkPlan> list = null;
        String tenantKeyStr = ObjectUtils.nvlString(map.get("tenantKeys"));
        String[] tenantKeys = Convert.toStrArray(tenantKeyStr);
        QqchWorkPlan query = new QqchWorkPlan();
        query.setPlanApprovalUnit(ObjectUtils.nvlString(map.get("planApprovalUnit")));
        query.setValid(ObjectUtils.nvlString(map.get("valid")));
        for (int i = 0; i < tenantKeys.length; i++) {
            //切换到master
            String oldDataSource = DynamicDataSourceContextHolder.peek();
            DynamicDataSourceContextHolder.push(TenantDataSourceUtils.getDataSourceNameByTenantKey(tenantKeys[i]));
            try {
                list = workPlanService.getQqchWorkPlanList(query);
            }catch (Exception e){
                e.printStackTrace();
                throw new CustomBusinessException(e.getMessage());
            }finally {
                DynamicDataSourceContextHolder.poll();
                DynamicDataSourceContextHolder.push(oldDataSource);
            }    
        }
        return PageFuncUtils.getTableDataInfo(ObjectUtils.nvl(map.get("pageNum"),1), ObjectUtils.nvl(map.get("pageSize"),10), list);
    }

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
    public List<Map> inspectionSummaryList() {
        List<SysTenant> list = systemServiceApi.tenantList();
        List<Map> resuList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            SysTenant tempTenant = list.get(i);
            //切换租户 真
            String oldDataSource = DynamicDataSourceContextHolder.peek();
            DynamicDataSourceContextHolder.push(TenantDataSourceUtils.getDataSourceNameByTenantKey(tempTenant.getTenantKey()));
            try {
                List<Map> summaryList = gmThirdMapper.inspectionSummaryList();
                for (int j = 0; j < summaryList.size(); j++) {
                    if(summaryList.get(j) == null)
                        continue;
                    summaryList.get(j).put("tenantKey", tempTenant.getTenantKey());
                }
                resuList.addAll(summaryList);
            }catch (Exception e){
                e.printStackTrace();
                throw new CustomBusinessException(e.getMessage());
            }finally {
                DynamicDataSourceContextHolder.poll();
                DynamicDataSourceContextHolder.push(oldDataSource);
            }
        }
        return resuList;
    }

    @Override
    public List<QqchPerformInspection> inspectionList(Map map){
        if(ObjectUtils.isBlank(map.get("tenantKeys")))
            return new ArrayList<>(2);
        String tenantKeyStr = map.get("tenantKeys").toString();
        String[] tenantKeys = tenantKeyStr.split(",");
        //构建查询实体类
        QqchPerformInspection queryInspction = new QqchPerformInspection();
        queryInspction.setCheckUnit(ObjectUtils.nvlString(map.get("checkUnit")));
        queryInspction.setCheckPersonName(ObjectUtils.nvlString(map.get("checkPersonName")));
        queryInspction.setParams(ObjectUtils.toMap("checkUnitFlag",ObjectUtils.nvlString(map.get("checkUnitFlag"))));
        List<QqchPerformInspection> resuList = new ArrayList<>();
        for (int i = 0; i < tenantKeys.length; i++) {
            //切换租户 
            String oldDataSource = DynamicDataSourceContextHolder.peek();
            DynamicDataSourceContextHolder.push(TenantDataSourceUtils.getDataSourceNameByTenantKey(tenantKeys[i]));
            try {
                List<QqchPerformInspection> list = performInspectionService.getQqchPerformInspectionList(queryInspction);
                for (int j = 0; j < list.size(); j++) {
                    list.get(j).setPtVar1(tenantKeys[i]);
                }
                resuList.addAll(list);
            }catch (Exception e){
                e.printStackTrace();
                throw new CustomBusinessException(e.getMessage());
            }finally {
                DynamicDataSourceContextHolder.poll();
                DynamicDataSourceContextHolder.push(oldDataSource);
            }    
        }
        return resuList;
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
