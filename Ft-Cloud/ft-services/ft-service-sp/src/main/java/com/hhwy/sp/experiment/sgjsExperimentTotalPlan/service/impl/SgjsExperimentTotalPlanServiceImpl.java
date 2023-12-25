package com.hhwy.sp.experiment.sgjsExperimentTotalPlan.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.feign.service.PmServiceApi;
import com.hhwy.sp.experiment.sgjsExperimentTotalPlan.domain.SgjsExperimentTotalPlan;
import com.hhwy.sp.experiment.sgjsExperimentTotalPlan.mapper.SgjsExperimentTotalPlanMapper;
import com.hhwy.sp.experiment.sgjsExperimentTotalPlan.service.ISgjsExperimentTotalPlanService;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.idworker.IdWorker;
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
        return sgjsExperimentTotalPlanMapper.insertSgjsExperimentTotalPlan(sgjsExperimentTotalPlan);
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
