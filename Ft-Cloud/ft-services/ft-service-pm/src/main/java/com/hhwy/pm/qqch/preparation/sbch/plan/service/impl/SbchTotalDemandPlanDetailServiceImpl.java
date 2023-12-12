package com.hhwy.pm.qqch.preparation.sbch.plan.service.impl;

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
            p.setPtVar1(p.getMaterialType());
            p.setIsSpecial("0");
        });
        sbchTotalDemandPlanDetailMapper.deleteSbchTotalDemandPlanDetailByPlanId(planId, SecurityUtils.getUserId(), DateUtils.getNowDate());
        sbchTotalDemandPlanDetailMapper.batchInsert(list);
        return totalDemandPlanService.getList(version);
    }

    @Override
    @SelfEmpty(clazz = SbchTotalDemandPlanDetail.class)
    public List<SbchTotalDemandPlanDetail> selectSbchTotalDemandPlanDetailLeaderList(SbchTotalDemandPlanDetail sbchTotalDemandPlanDetail) {
        ArrayList<SbchTotalDemandPlanDetail> returnList = new ArrayList<>();
        if(ObjectNullUtil.isEmpty(sbchTotalDemandPlanDetail.getMaterialCodeList())){
            sbchTotalDemandPlanDetail.setMaterialCodeList(null);
        }
        String projectType = sbchTotalDemandPlanDetail.getProjectType();
        List<SbchTotalDemandPlanDetail> sbchTotalDemandPlanDetails = sbchTotalDemandPlanDetailMapper.selectSbchTotalDemandPlanDetailLeaderList(sbchTotalDemandPlanDetail);
        if(!ObjectNullUtil.isEmpty(sbchTotalDemandPlanDetails)){
            Map<String, String> busAndMaterialMap = new HashMap<>();
            busAndMaterialMap.put("materialName", "materialName");
            busAndMaterialMap.put("materialSpec", "materialSpec");
            sbchTotalDemandPlanDetails = setMaterialNameUtils.setMaterialInfo(sbchTotalDemandPlanDetails, "materialCode", busAndMaterialMap);
            Map<String, String> busAndCategoryMap = new HashMap<>();
            busAndCategoryMap.put("ptVar1", "categoryName");
            sbchTotalDemandPlanDetails = setMaterialNameUtils.setCategoryInfo(sbchTotalDemandPlanDetails, "materialType", busAndCategoryMap);


            //根据设备编码分组  数量汇总展示
            Map<String, List<SbchTotalDemandPlanDetail>> plamDetailMap = sbchTotalDemandPlanDetails.stream().collect(Collectors.groupingBy(t -> t.getMaterialCode()));
            for (String materialCode : plamDetailMap.keySet()) {
                List<SbchTotalDemandPlanDetail> detailList = plamDetailMap.get(materialCode);
                //总数量
                int totalNum = detailList.stream().mapToInt(item -> ObjectUtils.isEmpty(item.getTotalNum()) ? 0 : item.getTotalNum().intValue()).sum();
                int allocateNum = detailList.stream().mapToInt(item -> ObjectUtils.isEmpty(item.getAllocateNum()) ? 0 : item.getAllocateNum().intValue()).sum();
                int localBuyNum = detailList.stream().mapToInt(item -> ObjectUtils.isEmpty(item.getLocalBuyNum()) ? 0 : item.getLocalBuyNum().intValue()).sum();
                int countryBuyNum = detailList.stream().mapToInt(item -> ObjectUtils.isEmpty(item.getCountryBuyNum()) ? 0 : item.getCountryBuyNum().intValue()).sum();
                int localLeaseNum = detailList.stream().mapToInt(item -> ObjectUtils.isEmpty(item.getLocalLeaseNum()) ? 0 : item.getLocalLeaseNum().intValue()).sum();
                int companySelfNum = detailList.stream().mapToInt(item -> ObjectUtils.isEmpty(item.getCompanySelfNum()) ? 0 : item.getCompanySelfNum().intValue()).sum();

                SbchTotalDemandPlanDetail detail = new SbchTotalDemandPlanDetail();
                BeanUtils.copyProperties(detailList.get(0),detail);
                detail.setTotalNum(Long.parseLong(totalNum+""));
                detail.setAllocateNum(Long.parseLong(allocateNum+""));
                detail.setLocalBuyNum(Long.parseLong(localBuyNum+""));
                detail.setCountryBuyNum(Long.parseLong(countryBuyNum+""));
                detail.setLocalLeaseNum(Long.parseLong(localLeaseNum+""));
                detail.setCompanySelfNum(Long.parseLong(companySelfNum+""));
                returnList.add(detail);
            }
        }
        return returnList;
    }
}
