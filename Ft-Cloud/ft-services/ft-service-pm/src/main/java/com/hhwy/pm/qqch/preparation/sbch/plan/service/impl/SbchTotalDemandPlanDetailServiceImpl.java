package com.hhwy.pm.qqch.preparation.sbch.plan.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.SpringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.gencode.enums.CodeEnum;
import com.hhwy.pm.gencode.service.GenCodeService;
import com.hhwy.pm.qqch.preparation.sbch.plan.domain.SbchTotalDemandPlan;
import com.hhwy.pm.qqch.preparation.sbch.plan.domain.SbchTotalDemandPlanDetail;
import com.hhwy.pm.qqch.preparation.sbch.plan.mapper.SbchTotalDemandPlanDetailMapper;
import com.hhwy.pm.qqch.preparation.sbch.plan.mapper.SbchTotalDemandPlanMapper;
import com.hhwy.pm.qqch.preparation.sbch.plan.service.ISbchTotalDemandPlanDetailService;
import com.hhwy.pm.qqch.preparation.sbch.plan.service.SbchTotalDemandPlanService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.myUtilPrepare.MyUtilPrepareUtil;
import com.hhwy.utils.myUtilPrepare.SetMaterialNameUtils;
import com.hhwy.utils.objectUtil.ObjectNullUtil;
import com.hhwy.utils.redisUtil.RedisUtils;
import com.hhwy.utils.selfEmpty.SelfEmpty;
import io.seata.common.util.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 设备总部计划总需用详情Service业务层处理
 * 
 * @author zq
 * @date 2022-11-23
 */
@Service
public class SbchTotalDemandPlanDetailServiceImpl implements ISbchTotalDemandPlanDetailService {
    @Autowired
    private SbchTotalDemandPlanDetailMapper sbchTotalDemandPlanDetailMapper;

    @Autowired
    private SetMaterialNameUtils setMaterialNameUtils;
    @Autowired
    private SbchTotalDemandPlanMapper sbchTotalDemandPlanMapper;
    @Autowired
    private SbchTotalDemandPlanService totalDemandPlanService;
    @Autowired
    private GenCodeService genCodeService;
    @Autowired
    private RedisUtils redisUtils;

    /***
     * 功能描述:  同步施工策划设备总需数据
     * @param version
     * @return com.hhwy.pm.qqch.preparation.sbch.plan.domain.SbchTotalDemandPlan
     * 作者: fushudong
     * 时间: 2023/9/22
     */
    @Transactional
    @Override
    public SbchTotalDemandPlan syncData(BigDecimal version) {
        SbchTotalDemandPlan result = new SbchTotalDemandPlan();
        version = VersionUtil.getVersion("sbch_total_demand_plan", version);
        SbchTotalDemandPlan sbchTotalDemandPlan = new SbchTotalDemandPlan();
        sbchTotalDemandPlan.setVersion(version);
        //查询主表
        List<SbchTotalDemandPlan> sbchTotalDemandPlans = sbchTotalDemandPlanMapper.selectSbchTotalDemandPlanList(sbchTotalDemandPlan);
        Long planId;
        if (ObjectNullUtil.isEmpty(sbchTotalDemandPlans)) {
            Long id = IdWorker.createId();
            planId = id;
            SbchTotalDemandPlan vo = new SbchTotalDemandPlan();
            vo.setId(id);
            String setCode = genCodeService.getSetCode(CodeEnum.EQU_TOTAL_PLAN);
            vo.setUnicode(setCode);
            vo.setTitleName("设备总需");
            MyUtilPrepareUtil.setCreateUpdateInfo(vo);
            sbchTotalDemandPlanMapper.insertSbchTotalDemandPlan(vo);
        }else {
            planId = sbchTotalDemandPlans.get(0).getId();
        }
        //获取1.7 设备总需所有设备
        List<SbchTotalDemandPlanDetail> list = sbchTotalDemandPlanDetailMapper.getAllDemandDevice(version);
        if (CollectionUtils.isEmpty(list)){
            return result;
        }
        list.forEach(p -> {
            p.setId(IdWorker.createId());
            p.setPlanId(planId);
            p.setPtVar1(StrUtil.isBlank(p.getMaterialType())?p.getPtVar3():p.getMaterialType());
            p.setIsSpecial("0");
            p.setMaterialType(null);
            p.setPtVar3(null);
            p.setMaterialType(p.getPtVar1());
            //设备类型编号为空，从redis中获取
            if (StrUtil.isBlank(p.getPtVar2())) {
                Object materialInfo = redisUtils.hGet("materialInfoRedis", p.getMaterialCode());
                if (materialInfo != null) {
                    Map<String, Object> materialMap = JSON.parseObject(materialInfo.toString(), Map.class);
                    //从categoryInfoRedis取出来分类名称
//                    Object categoryInfo = redisUtils.hGet("categoryInfoRedis", ObjectUtils.toString(materialMap.get("categoryCode")));
//                    Map<String, Object> categoryMap = JSON.parseObject(categoryInfo.toString(), Map.class);
                    p.setPtVar2(ObjectUtils.toString(materialMap.get("categoryCode")));
                }
            }
        });
        sbchTotalDemandPlanDetailMapper.deleteSbchTotalDemandPlanDetailByPlanId(planId, SecurityUtils.getUserId(), DateUtils.getNowDate());
        sbchTotalDemandPlanDetailMapper.batchInsert(list);
        return totalDemandPlanService.getList(version);
    }

    @Override
    @SelfEmpty(clazz = SbchTotalDemandPlanDetail.class)
    public List<SbchTotalDemandPlanDetail> selectSbchTotalDemandPlanDetailLeaderList(SbchTotalDemandPlanDetail sbchTotalDemandPlanDetail) {
        BigDecimal version = sbchTotalDemandPlanDetail.getVersion();
        version = VersionUtil.getVersion("sbch_total_demand_plan", version);
        SbchTotalDemandPlan sbchTotalDemandPlan = new SbchTotalDemandPlan();
        sbchTotalDemandPlan.setVersion(version);
        List<SbchTotalDemandPlan> sbchTotalDemandPlans = sbchTotalDemandPlanMapper.selectSbchTotalDemandPlanList(sbchTotalDemandPlan);
        if (CollUtil.isEmpty(sbchTotalDemandPlans)) new ArrayList<>();
        List<SbchTotalDemandPlanDetail> returnList = new ArrayList<>();
        if(ObjectNullUtil.isEmpty(sbchTotalDemandPlanDetail.getMaterialCodeList())){
            sbchTotalDemandPlanDetail.setMaterialCodeList(null);
        }
        sbchTotalDemandPlanDetail.setPlanId(sbchTotalDemandPlans.get(0).getId());
        List<SbchTotalDemandPlanDetail> sbchTotalDemandPlanDetails = sbchTotalDemandPlanDetailMapper.selectSbchTotalDemandPlanDetailLeaderList(sbchTotalDemandPlanDetail);
        if(!ObjectNullUtil.isEmpty(sbchTotalDemandPlanDetails)){
            Map<String, String> busAndMaterialMap = new HashMap<>();
            busAndMaterialMap.put("materialName", "materialName");
            busAndMaterialMap.put("materialSpec", "materialSpec");
            returnList = setMaterialNameUtils.setMaterialInfo(sbchTotalDemandPlanDetails, "materialCode", busAndMaterialMap);
            Map<String, String> busAndCategoryMap = new HashMap<>();
            busAndCategoryMap.put("ptVar1", "categoryName");
            returnList = setMaterialNameUtils.setCategoryInfo(sbchTotalDemandPlanDetails, "materialType", busAndCategoryMap);
        }
        return returnList;
    }
}
