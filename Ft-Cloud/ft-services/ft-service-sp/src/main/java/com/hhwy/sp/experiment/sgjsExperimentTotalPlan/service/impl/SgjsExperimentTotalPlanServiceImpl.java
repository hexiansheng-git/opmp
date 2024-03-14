package com.hhwy.sp.experiment.sgjsExperimentTotalPlan.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.domain.SysSyncInfoLog;
import com.hhwy.feign.service.PmServiceApi;
import com.hhwy.sp.experiment.sgjsExperimentTotalPlan.domain.SgjsExperimentTotalPlan;
import com.hhwy.sp.experiment.sgjsExperimentTotalPlan.mapper.SgjsExperimentTotalPlanMapper;
import com.hhwy.sp.experiment.sgjsExperimentTotalPlan.service.ISgjsExperimentTotalPlanService;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * @author lcf--试验总体计划
 * @date 2023-12-11 10:00:11
 * @remark
 */
@Service
public class SgjsExperimentTotalPlanServiceImpl implements ISgjsExperimentTotalPlanService{

    @Autowired
    private SgjsExperimentTotalPlanMapper sgjsExperimentTotalPlanMapper;
    @Autowired
    private PmServiceApi pmServiceApi;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    private Logger logger= LoggerFactory.getLogger(SgjsExperimentTotalPlanServiceImpl.class);


    public SgjsExperimentTotalPlan getSgjsExperimentTotalPlan(SgjsExperimentTotalPlan sgjsExperimentTotalPlan) {
        return sgjsExperimentTotalPlanMapper.getSgjsExperimentTotalPlan(sgjsExperimentTotalPlan);
    }

    public List<SgjsExperimentTotalPlan> getSgjsExperimentTotalPlanList(SgjsExperimentTotalPlan sgjsExperimentTotalPlan) {
        return sgjsExperimentTotalPlanMapper.getSgjsExperimentTotalPlanList(sgjsExperimentTotalPlan);
    }

    @Transactional
    public int insertSgjsExperimentTotalPlan(SgjsExperimentTotalPlan sgjsExperimentTotalPlan) {
        sgjsExperimentTotalPlan.setId(IdWorker.createId());
        sgjsExperimentTotalPlan.setCreateUser(SecurityUtils.getUserName());
        sgjsExperimentTotalPlan.setCreateTime(DateUtils.getNowDate());
        int i = sgjsExperimentTotalPlanMapper.insertSgjsExperimentTotalPlan(sgjsExperimentTotalPlan);
        //总部版同步数据
        //syncDataToGm(sgjsExperimentTotalPlan);
        return i;
    }

    /**
     * 总部版同步
     *
     * @param sgjsExperimentTotalPlan
     */
    private void syncDataToGm(SgjsExperimentTotalPlan sgjsExperimentTotalPlan) {
        long beginMills = System.currentTimeMillis();
        Integer status = 1;
        String errMsg = "";
        try{
            rocketMQTemplate.convertAndSend("sgjs_experiment_total_plan:tenantSuccess1", JSONObject.toJSONString(sgjsExperimentTotalPlan));
        }catch (Exception e){
            e.printStackTrace();
            status = 0;
            errMsg = e.getMessage();
            logger.error("报错了【{}】",e.getMessage());
            throw e;
        }finally {
            //3、更新syncInfo
            SysSyncInfoLog log=new SysSyncInfoLog();
            log.setBusinessName("sgjs_experiment_total_plan");
            log.setStatus(status);
            log.setFailMsg(errMsg);
            log.setPtVar1(JSONObject.toJSONString(sgjsExperimentTotalPlan));
            logger.error("sgjs_experiment_total_plan同步失败【{}】,时间：【{}】",JSONObject.toJSONString(sgjsExperimentTotalPlan),System.currentTimeMillis()-beginMills);
            pmServiceApi.insertSyncLog(log);
        }
    }

    @Transactional
    public int insertSgjsExperimentTotalPlanList(List<SgjsExperimentTotalPlan> sgjsExperimentTotalPlanList) {
        for (SgjsExperimentTotalPlan sgjsExperimentTotalPlan : sgjsExperimentTotalPlanList) {
            sgjsExperimentTotalPlan.setId(IdWorker.createId());
            sgjsExperimentTotalPlan.setCreateUser(SecurityUtils.getUserName());
            sgjsExperimentTotalPlan.setCreateTime(DateUtils.getNowDate());
        }
        return sgjsExperimentTotalPlanMapper.insertSgjsExperimentTotalPlanList(sgjsExperimentTotalPlanList);
    }

    @Transactional
    public int updateSgjsExperimentTotalPlan(SgjsExperimentTotalPlan sgjsExperimentTotalPlan) {
        sgjsExperimentTotalPlan.setPtVar2(SecurityUtils.getUserId()+"");
        return sgjsExperimentTotalPlanMapper.updateSgjsExperimentTotalPlan(sgjsExperimentTotalPlan);
    }

    @Transactional
    public int updateSgjsExperimentTotalPlanList(List<SgjsExperimentTotalPlan> sgjsExperimentTotalPlanList) {
        for (SgjsExperimentTotalPlan sgjsExperimentTotalPlan : sgjsExperimentTotalPlanList) {
            sgjsExperimentTotalPlan.setUpdateUser(SecurityUtils.getUserName());
            sgjsExperimentTotalPlan.setUpdateTime(DateUtils.getNowDate());
        }
        return sgjsExperimentTotalPlanMapper.updateSgjsExperimentTotalPlanList(sgjsExperimentTotalPlanList);
    }

    @Transactional
    public int deleteSgjsExperimentTotalPlan(SgjsExperimentTotalPlan sgjsExperimentTotalPlan) {
        sgjsExperimentTotalPlan.setUpdateUser(SecurityUtils.getUserName());
        sgjsExperimentTotalPlan.setUpdateTime(DateUtils.getNowDate());
        return sgjsExperimentTotalPlanMapper.deleteSgjsExperimentTotalPlan(sgjsExperimentTotalPlan);
    }

    @Transactional
    public int deleteSgjsExperimentTotalPlanByPks(List<Long> sgjsExperimentTotalPlanPkList) {
        return sgjsExperimentTotalPlanMapper.deleteSgjsExperimentTotalPlanByPks(sgjsExperimentTotalPlanPkList);
    }

    @Override
    public SgjsExperimentTotalPlan selectDetailInfo() {
        Map<String, Object> prjInfo = pmServiceApi.getPrjInfo();
        if(ObjectUtils.isEmpty(prjInfo)){
            return null;
        }
        SgjsExperimentTotalPlan info=new SgjsExperimentTotalPlan();
        info.setProjectLocation(prjInfo.get("projectLocation")+"");
        info.setBusinessAreasAndProducts(prjInfo.get("businessAreasAndProductsLabel")+"");
        info.setProjectCode(prjInfo.get("projectCode")+"");
        if(null!=prjInfo.get("regionId")){
            info.setRegionId(Long.parseLong(prjInfo.get("regionId")+""));
        }
        info.setRegionName(ObjectUtils.toString(prjInfo.get("regionName")));
        info.setProjectName(prjInfo.get("projectName")+"");
        info.setWinTheBiddingUnit(ObjectUtils.toString(prjInfo.get("winTheBiddingUnit")));
        if(null!=prjInfo.get("projectId")){
            info.setProjectId(Long.parseLong(prjInfo.get("projectId")+""));
        }
        List<SgjsExperimentTotalPlan> list = sgjsExperimentTotalPlanMapper.getSgjsExperimentTotalPlanList(new SgjsExperimentTotalPlan());
        info.setCreateUser(SecurityUtils.getUserId()+"");
        info.setCreateUserName(SecurityUtils.getSysUser().getNickName());
        info.setPtVar1(SecurityUtils.getSysUser().getPhoneNumber());
        if(!CollectionUtils.isEmpty(list)){
            info.setFileGroupId(list.get(0).getFileGroupId());
            if(null!=list.get(0).getId()){
                info.setId(list.get(0).getId());
                info.setUpdateTime(list.get(0).getUpdateTime());
                info.setUpdateUser(list.get(0).getUpdateUser());
            }
        }
        return info;
    }


}
