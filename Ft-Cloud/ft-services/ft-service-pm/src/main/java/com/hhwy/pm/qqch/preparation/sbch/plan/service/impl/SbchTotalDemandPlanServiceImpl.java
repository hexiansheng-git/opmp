package com.hhwy.pm.qqch.preparation.sbch.plan.service.impl;

import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.gencode.enums.CodeEnum;
import com.hhwy.pm.gencode.service.GenCodeService;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.sbch.plan.domain.SbchTotalDemandPlan;
import com.hhwy.pm.qqch.preparation.sbch.plan.domain.SbchTotalDemandPlanDetail;
import com.hhwy.pm.qqch.preparation.sbch.plan.mapper.SbchTotalDemandPlanDetailMapper;
import com.hhwy.pm.qqch.preparation.sbch.plan.mapper.SbchTotalDemandPlanMapper;
import com.hhwy.pm.qqch.preparation.sbch.plan.service.SbchTotalDemandPlanService;
import com.hhwy.pm.qqch.preparation.sbch.plan.vo.SbchTotalDemandPlanDetailVo;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.EntityUtils;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.myUtilPrepare.MyUtilPrepareUtil;
import com.hhwy.utils.objectUtil.ObjectNullUtil;
import com.hhwy.utils.validation.JyDetailsUtil;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zqq
 * @create 2023-08-23 16:39
 */
@Service
public class SbchTotalDemandPlanServiceImpl implements SbchTotalDemandPlanService {
    @Autowired
    private SbchTotalDemandPlanMapper sbchTotalDemandPlanMapper;
    @Autowired
    private SbchTotalDemandPlanDetailMapper sbchTotalDemandPlanDetailMapper;
    @Autowired
    private IQqchReviewService qqchReviewService;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;
    @Autowired
    private GenCodeService genCodeService;

    @Override
    public SbchTotalDemandPlan getList(BigDecimal version) {
        SbchTotalDemandPlan sbchTotalDemandPlanDetailVo = new SbchTotalDemandPlan();

        version = VersionUtil.getVersion("sbch_total_demand_plan", version);
        SbchTotalDemandPlan sbchTotalDemandPlan = new SbchTotalDemandPlan();
        sbchTotalDemandPlan.setVersion(version);
        List<SbchTotalDemandPlan> sbchTotalDemandPlans = sbchTotalDemandPlanMapper.selectSbchTotalDemandPlanList(sbchTotalDemandPlan);
        if(!ObjectNullUtil.isEmpty(sbchTotalDemandPlans)){
            SbchTotalDemandPlan sbchTotalDemandPlan1 = sbchTotalDemandPlans.get(0);
            sbchTotalDemandPlanDetailVo = sbchTotalDemandPlan1;
            SbchTotalDemandPlanDetail detailVo = new SbchTotalDemandPlanDetail();
            detailVo.setPlanId(sbchTotalDemandPlan1.getId());
            List<SbchTotalDemandPlanDetail> sbchTotalDemandPlanDetails = sbchTotalDemandPlanDetailMapper.selectSbchTotalDemandPlanDetailList(detailVo);
            ArrayList<SbchTotalDemandPlanDetail> returnList = new ArrayList<>();
            if(!ObjectNullUtil.isEmpty(sbchTotalDemandPlanDetails)){
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
            sbchTotalDemandPlanDetailVo.setPlanDetailList(returnList);
        }
        sbchTotalDemandPlanDetailVo.setVersion(version);
        sbchTotalDemandPlanDetailVo.setStageIdentity(qqchReviewService.getStage());
        return sbchTotalDemandPlanDetailVo;
    }

    @Override
    public void batchSave(SbchTotalDemandPlan vo) {
        List<SbchTotalDemandPlanDetail> list = vo.getPlanDetailList();
        SbchTotalDemandPlan temp = new SbchTotalDemandPlan();
        temp.setVersion(vo.getVersion());
        List<SbchTotalDemandPlan> sbchTotalDemandPlans = sbchTotalDemandPlanMapper.selectSbchTotalDemandPlanList(temp);
        if(!ObjectNullUtil.isEmpty(sbchTotalDemandPlans)){
            vo.setId(sbchTotalDemandPlans.get(0).getId());
            MyUtilPrepareUtil.setUpdateInfoBase(vo);
            sbchTotalDemandPlanMapper.updateSbchTotalDemandPlan(vo);
        }else{
            vo.setId(IdWorker.createId());
            String setCode = genCodeService.getSetCode(CodeEnum.EQU_TOTAL_PLAN);
            vo.setUnicode(setCode);
            vo.setTitleName("设备总需");
            MyUtilPrepareUtil.setCreateUpdateInfo(vo);
            sbchTotalDemandPlanMapper.insertSbchTotalDemandPlan(vo);
        }

        if(!ObjectNullUtil.isEmpty(list)){
            //校验数据必填
            if("1".equals(vo.getButtonMark())||"2".equals(vo.getButtonMark())){//确认
                JyDetailsUtil.jyDetails(list, ValidationGroups.Save.class);
            }
            for (SbchTotalDemandPlanDetail sbchTotalDemandPlanDetail : list) {
                sbchTotalDemandPlanDetail.setId(IdWorker.createId());
                sbchTotalDemandPlanDetail.setPlanId(vo.getId());
                EntityUtils.setCreateInfo(sbchTotalDemandPlanDetail);
            }
        }
        // 清空数据库表中数据
        SbchTotalDemandPlan sbchTotalDemandPlan = new SbchTotalDemandPlan();
        sbchTotalDemandPlan.setVersion(vo.getVersion());
        sbchTotalDemandPlanDetailMapper.deleteSbchTotalDemandPlanDetailByPlanId(vo.getId(), SecurityUtils.getSysUser().getUserId(),new Date());
        if(!ObjectNullUtil.isEmpty(list)){
            sbchTotalDemandPlanDetailMapper.batchInsert(list);
        }
        //判断是否是确认
        if(ButtonMark.CONFIRM.equals(vo.getButtonMark())){
            //插入确认记录
            String menuId = vo.getMenuId();
            String stageIdentity = vo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
    }
}
