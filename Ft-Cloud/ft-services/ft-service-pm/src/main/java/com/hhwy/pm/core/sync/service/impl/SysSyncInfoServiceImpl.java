package com.hhwy.pm.core.sync.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.pm.core.sync.domain.SysSyncInfo;
import com.hhwy.pm.core.sync.enums.SyncBusinessEnum;
import com.hhwy.pm.core.sync.mapper.SysSyncInfoMapper;
import com.hhwy.pm.core.sync.service.ISysSyncInfoLogService;
import com.hhwy.pm.core.sync.service.ISysSyncInfoService;
import com.hhwy.pm.qqch.group.domain.QqchWorkGroup;
import com.hhwy.pm.qqch.group.service.IQqchWorkGroupService;
import com.hhwy.pm.qqch.qqchPerformInspection.domain.QqchPerformInspection;
import com.hhwy.pm.qqch.qqchWorkPlan.domain.QqchWorkPlan;
import com.hhwy.pm.qqch.review.domain.Review;
import com.hhwy.pm.xmsl.project.domain.vo.ProjectBasicInfo;
import com.hhwy.pm.xmsl.project.service.IXmslProjectBasicInfoService;
import com.hhwy.utils.AddBaseInfoUtil;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.PageFuncUtils;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.collections4.SetUtils;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hhwy.common.core.text.Convert;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;

/**
 * 数据同步节点记录Service业务层处理
 * 
 * @author wk
 * @date 2023-09-04
 */
@Service
public class SysSyncInfoServiceImpl implements ISysSyncInfoService {
    private final int LIMIT_PUSH_SIZE = 500;
    @Autowired
    private SysSyncInfoMapper sysSyncInfoMapper;
    @Resource
    private ISysSyncInfoLogService sysSyncInfoLogService;
    @Autowired
    private IQqchWorkGroupService qqchWorkGroupService;
    @Autowired
    private IXmslProjectBasicInfoService projectBasicInfoService;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    

    @Override
    public Long getLastId(SyncBusinessEnum syncBusinessEnum) {
        SysSyncInfo syncInfo = selectSysSyncInfoByEnum(syncBusinessEnum);
        if(syncInfo == null){
            syncInfo = new SysSyncInfo();
            syncInfo.setBusinessName(syncBusinessEnum.busType());
            syncInfo.setLastPushId(0L);
            new AddBaseInfoUtil<>().addBaseEntity(syncInfo);
            return syncInfo.getLastPushId();
        }
        return syncInfo.getLastPushId();
    }

    /**
     * 查询数据同步节点记录
     * 
     * @param syncBusinessEnum 数据同步节点记录ID
     * @return 数据同步节点记录
     */
    @Override
    public SysSyncInfo selectSysSyncInfoByEnum(SyncBusinessEnum syncBusinessEnum) {
        SysSyncInfo query = new SysSyncInfo();
        query.setBusinessName(syncBusinessEnum.busType());
        List<SysSyncInfo> list = sysSyncInfoMapper.selectSysSyncInfoList(query);
        if(CollectionUtils.isEmpty(list))
            return null;
        return list.get(0);
    }

    @Override
    @Transactional
    public void pushQqchWorkGroup(List<QqchWorkGroup> list) {
        long beginMills = System.currentTimeMillis();
        Integer status = 1;
        String errMsg = "";
        try{
            ProjectBasicInfo projectBasicInfo = projectBasicInfoService.projectInfo();
            for (int i = 0; i < list.size(); i++) {
                QqchWorkGroup temp =  list.get(i);
                temp.setProjectId(projectBasicInfo.getProjectId());
                temp.setProjectName(projectBasicInfo.getProjectName());
            }
            rocketMQTemplate.convertAndSend("qqch_work_group:tenantSuccess", JSONObject.toJSONString(list));
        }catch(Exception e){
            e.printStackTrace();
            status = 0;
            errMsg = e.getMessage();
            throw e;
        }finally {
            String ids = list.stream().map(r->r.getId()+"").collect(Collectors.joining(","));
            //3、更新syncInfo
            sysSyncInfoLogService.insert(SyncBusinessEnum.QQCHWORKGROUP_ENUM,ids,1L,System.currentTimeMillis()-beginMills,status,errMsg);
        }
    }

    @Override
    @Transactional
    public void pushQqchWorkGroup(QqchWorkGroup qqchWorkGroup) {
        pushQqchWorkGroup(Arrays.asList(qqchWorkGroup));
    }

    @Override
    @Transactional
    public void pushQqchWorkPlan(List<QqchWorkPlan> list) {
        long beginMills = System.currentTimeMillis();
        Integer status = 1;
        String errMsg = "";
        try{
            ProjectBasicInfo projectBasicInfo = projectBasicInfoService.projectInfo();
            for (int i = 0; i < list.size(); i++) {
                QqchWorkPlan temp =  list.get(i);
                temp.setProjectId(projectBasicInfo.getProjectId());
                temp.setProjectName(projectBasicInfo.getProjectName());
            }
            rocketMQTemplate.convertAndSend("qqch_work_plan:tenantSuccess", JSONObject.toJSONString(list));
        }catch(Exception e){
            e.printStackTrace();
            status = 0;
            errMsg = e.getMessage();
            throw e;
        }finally {
            String ids = list.stream().map(r->r.getId()+"").collect(Collectors.joining(","));
            //3、更新syncInfo
            sysSyncInfoLogService.insert(SyncBusinessEnum.QQCHWORKPLAN_ENUM,ids,list.size()+0L,System.currentTimeMillis()-beginMills,status,errMsg);
        }
    }

    @Override
    @Transactional
    public void pushQqchWorkPlan(QqchWorkPlan qqchWorkPlan) {
        pushQqchWorkPlan(Arrays.asList(qqchWorkPlan));
    }

    @Override
    public void pushQqchReview(List<Review> list) {
        long beginMills = System.currentTimeMillis();
        Integer status = 1;
        String errMsg = "";
        try{
            ProjectBasicInfo projectBasicInfo = projectBasicInfoService.projectInfo();
            for (int i = 0; i < list.size(); i++) {
                Review temp =  list.get(i);
                temp.setProjectId(projectBasicInfo.getProjectId());
                temp.setProjectName(projectBasicInfo.getProjectName());
            }
            rocketMQTemplate.convertAndSend("qqch_review:tenantSuccess", JSONObject.toJSONString(list));
        }catch(Exception e){
            e.printStackTrace();
            status = 0;
            errMsg = e.getMessage();
            throw e;
        }finally {
            String ids = list.stream().map(r->r.getId()+"").collect(Collectors.joining(","));
            //3、更新syncInfo
            sysSyncInfoLogService.insert(SyncBusinessEnum.QQCHWORKPLAN_ENUM,ids,list.size()+0L,System.currentTimeMillis()-beginMills,status,errMsg);
        }        
    }

    @Override
    public void pushQqchReview(Review review) {
        pushQqchReview(Arrays.asList(review));
    }

    @Override
    public void pushQqchPerformInspection(List<QqchPerformInspection> list) {
        long beginMills = System.currentTimeMillis();
        Integer status = 1;
        String errMsg = "";
        try{
            ProjectBasicInfo projectBasicInfo = projectBasicInfoService.projectInfo();
            List<JSONObject> jsonObjectList = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                QqchPerformInspection temp = list.get(i);
                JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(temp));
                jsonObject.put("projectId",projectBasicInfo.getProjectId());
                jsonObject.put("projectName",projectBasicInfo.getProjectName());
                jsonObjectList.add(jsonObject);
            }
            rocketMQTemplate.convertAndSend("qqch_performInspection:tenantSuccess", JSONObject.toJSONString(jsonObjectList));
        }catch(Exception e){
            e.printStackTrace();
            status = 0;
            errMsg = e.getMessage();
            throw e;
        }finally {
            String ids = list.stream().map(r->r.getId()+"").collect(Collectors.joining(","));
            //3、更新syncInfo
            sysSyncInfoLogService.insert(SyncBusinessEnum.QQCHWORKPLAN_ENUM,ids,list.size()+0L,System.currentTimeMillis()-beginMills,status,errMsg);
        }
    }

    @Override
    public void pushQqchPerformInspection(QqchPerformInspection inspection) {
        pushQqchPerformInspection(Arrays.asList(inspection)); 
    }
}
