package com.hhwy.pm.qqch.wzch.specialmaterial.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.hhwy.common.core.exception.BaseException;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.pm.qqch.wzch.common.service.WzchCommonService;
import com.hhwy.pm.qqch.wzch.enums.YesOrNoEnum;
import com.hhwy.pm.qqch.wzch.specialmaterial.domain.WzchSpecialMaterialPlan;
import com.hhwy.pm.qqch.wzch.specialmaterial.domain.WzchSpecialMaterialPlanDetail;
import com.hhwy.pm.qqch.wzch.specialmaterial.domain.WzchSpecialMaterialRequestDetail;
import com.hhwy.pm.qqch.wzch.specialmaterial.mapper.WzchSpecialMaterialPlanMapper;
import com.hhwy.pm.qqch.wzch.specialmaterial.service.IWzchSpecialMaterialPlanDetailService;
import com.hhwy.pm.qqch.wzch.specialmaterial.service.IWzchSpecialMaterialPlanService;
import com.hhwy.pm.qqch.wzch.specialmaterial.service.IWzchSpecialMaterialRequestDetailService;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.bigDecimalUtils.BigDecimalUtils;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 专项物资发运策划Service业务层处理
 * 
 * @author mls
 * @date 2022-12-07
 */
@Service
public class WzchSpecialMaterialPlanServiceImpl implements IWzchSpecialMaterialPlanService {
    @Resource
    private WzchSpecialMaterialPlanMapper wzchSpecialMaterialPlanMapper;
    @Resource
    private IWzchSpecialMaterialPlanDetailService wzchSpecialMaterialPlanDetailService;
    @Resource
    private IWzchSpecialMaterialRequestDetailService wzchSpecialMaterialRequestDetailService;
    @Resource
    private WzchCommonService wzchCommonService;
    @Resource
    IQqchReviewService qqchReviewService;

    /**
     * 查询专项物资发运策划
     * 
     * @param id 专项物资发运策划ID
     * @return 专项物资发运策划
     */
    @Override
    public WzchSpecialMaterialPlan selectWzchSpecialMaterialPlanById(Long id) {
        return wzchSpecialMaterialPlanMapper.selectWzchSpecialMaterialPlanById(id);
    }

    /**
     * 查询专项物资发运策划列表
     * 
     * @param wzchSpecialMaterialPlan 专项物资发运策划
     * @return 专项物资发运策划
     */
    @Override
//    @CustomDatascope(alias = "smp")
    public List<WzchSpecialMaterialPlan> selectWzchSpecialMaterialPlanList(WzchSpecialMaterialPlan wzchSpecialMaterialPlan) {
        return wzchSpecialMaterialPlanMapper.selectWzchSpecialMaterialPlanList(wzchSpecialMaterialPlan);
    }

    /**
     * 新增专项物资发运策划
     * 
     * @param wzchSpecialMaterialPlan 专项物资发运策划
     * @return 结果
     */
    @Override
    public int insertWzchSpecialMaterialPlan(WzchSpecialMaterialPlan wzchSpecialMaterialPlan) {

        wzchSpecialMaterialPlan.setCreateTime(DateUtils.getNowDate());

        return wzchSpecialMaterialPlanMapper.insertWzchSpecialMaterialPlan(wzchSpecialMaterialPlan);
    }

    /**
     * 修改专项物资发运策划
     * 
     * @param wzchSpecialMaterialPlan 专项物资发运策划
     * @return 结果
     */
    @Override
    public int updateWzchSpecialMaterialPlan(WzchSpecialMaterialPlan wzchSpecialMaterialPlan) {
        wzchSpecialMaterialPlan.setUpdateTime(DateUtils.getNowDate());
        return wzchSpecialMaterialPlanMapper.updateWzchSpecialMaterialPlan(wzchSpecialMaterialPlan);
    }

    /**
     * 删除专项物资发运策划对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteWzchSpecialMaterialPlanByIds(List<Long> ids) {
        return wzchSpecialMaterialPlanMapper.deleteWzchSpecialMaterialPlanByIds(ids);
    }

    /**
     * 删除专项物资发运策划信息
     * 
     * @param id 专项物资发运策划ID
     * @return 结果
     */
    @Override
    public int deleteWzchSpecialMaterialPlanById(Long id) {
        return wzchSpecialMaterialPlanMapper.deleteWzchSpecialMaterialPlanById(id);
    }

    @Override
    public WzchSpecialMaterialPlan detail(WzchSpecialMaterialPlan wzchSpecialMaterialPlan) {
        BigDecimal sourceVersion = wzchSpecialMaterialPlan.getVersion();
        BigDecimal version = VersionUtil.getVersion("wzch_special_material_plan", wzchSpecialMaterialPlan.getVersion());
        boolean isMatchVersion = BigDecimalUtils.equals(version,wzchSpecialMaterialPlan.getVersion());
        
        wzchSpecialMaterialPlan.setStageIdentity(qqchReviewService.getStage());
        
        List<WzchSpecialMaterialPlan> list = wzchSpecialMaterialPlanMapper.selectWzchSpecialMaterialPlanList(new WzchSpecialMaterialPlan(version));
        if(CollectionUtils.isEmpty(list)){
            wzchSpecialMaterialPlan.setPlanDetailList(new ArrayList<>());
            wzchSpecialMaterialPlan.setRequestDetailList(new ArrayList<>());
            return wzchSpecialMaterialPlan;
        }
        wzchSpecialMaterialPlan = list.get(0);
        wzchSpecialMaterialPlan.setVersion(ObjectUtils.nvlBigDecimal(sourceVersion,version));
        wzchSpecialMaterialPlan.setVersionCode(wzchSpecialMaterialPlan.getVersion().toString());
        wzchSpecialMaterialPlan.setVersionCodeStr("V"+wzchSpecialMaterialPlan.getVersion());
        wzchSpecialMaterialPlan.setId(isMatchVersion||sourceVersion==null?wzchSpecialMaterialPlan.getId():null); //若取得不是本版本，将id滞空，
        wzchSpecialMaterialPlan.setStageIdentity(qqchReviewService.getStage());
        List<WzchSpecialMaterialPlanDetail> wzchSpecialMaterialPlanDetails = wzchSpecialMaterialPlanDetailService.selectWzchSpecialMaterialPlanDetailList(new WzchSpecialMaterialPlanDetail(wzchSpecialMaterialPlan.getId()));
        HashMap<String, String> map = new HashMap<>(1);
        map.put("materialName", "materialName");
        wzchCommonService.setMaterialInfo(wzchSpecialMaterialPlanDetails,"materialCode",map);
        if(CollectionUtils.isNotEmpty(wzchSpecialMaterialPlanDetails)){
            wzchSpecialMaterialPlanDetails = wzchSpecialMaterialPlanDetails.stream().sorted(Comparator.comparing(WzchSpecialMaterialPlanDetail::getMaterialCode)).collect(Collectors.toList());
        }
        wzchSpecialMaterialPlan.setPlanDetailList(wzchSpecialMaterialPlanDetails);
        List<WzchSpecialMaterialRequestDetail> wzchSpecialMaterialRequestDetails = wzchSpecialMaterialRequestDetailService.selectWzchSpecialMaterialRequestDetailList(new WzchSpecialMaterialRequestDetail(wzchSpecialMaterialPlan.getId()));
        wzchSpecialMaterialPlan.setRequestDetailList(wzchSpecialMaterialRequestDetails);
        return wzchSpecialMaterialPlan;
    }

    @Override
    public boolean remove(WzchSpecialMaterialPlan wzchSpecialMaterialPlan) {
        if(wzchSpecialMaterialPlan==null || wzchSpecialMaterialPlan.getId()==null){
            throw new BaseException("入参缺失");
        }
        WzchSpecialMaterialPlan plan = wzchSpecialMaterialPlanMapper.selectWzchSpecialMaterialPlanById(wzchSpecialMaterialPlan.getId());
        if(plan == null){
            throw new BaseException("数据不存在");
        }
        wzchSpecialMaterialPlanMapper.deleteWzchSpecialMaterialPlanById(plan.getId());
        List<WzchSpecialMaterialPlanDetail> wzchSpecialMaterialPlanDetails = wzchSpecialMaterialPlanDetailService.selectWzchSpecialMaterialPlanDetailList(new WzchSpecialMaterialPlanDetail(plan.getId()));
        if(CollectionUtils.isNotEmpty(wzchSpecialMaterialPlanDetails)){
            List<Long> planDetailIds = wzchSpecialMaterialPlanDetails.stream().map(WzchSpecialMaterialPlanDetail::getId).collect(Collectors.toList());
            wzchSpecialMaterialPlanDetailService.deleteWzchSpecialMaterialPlanDetailByIds(planDetailIds);
        }
        List<WzchSpecialMaterialRequestDetail> wzchSpecialMaterialRequestDetails = wzchSpecialMaterialRequestDetailService.selectWzchSpecialMaterialRequestDetailList(new WzchSpecialMaterialRequestDetail(plan.getId()));
        if(CollectionUtils.isNotEmpty(wzchSpecialMaterialRequestDetails)){
            List<Long> requestDetailIds = wzchSpecialMaterialRequestDetails.stream().map(WzchSpecialMaterialRequestDetail::getId).collect(Collectors.toList());
            wzchSpecialMaterialRequestDetailService.deleteWzchSpecialMaterialRequestDetailByIds(requestDetailIds);
        }
        return true;
    }

    @Override
    public WzchSpecialMaterialPlan modify(WzchSpecialMaterialPlan wzchSpecialMaterialPlan) {

        if(wzchSpecialMaterialPlan==null || wzchSpecialMaterialPlan.getId()==null){
            throw new BaseException("入参缺失");
        }
        WzchSpecialMaterialPlan plan = wzchSpecialMaterialPlanMapper.selectWzchSpecialMaterialPlanById(wzchSpecialMaterialPlan.getId());
        if (plan==null) {
            throw new BaseException("数据不存在");
        }
        if("0".equals(plan.getValid())){
            throw new BaseException("该版本未生效不能进行调整");
        }
        if (StringUtils.isBlank(plan.getVersionCode())) {
            throw new BaseException("未获取到版本信息");
        }
        BigDecimal versionCode = new BigDecimal(plan.getVersionCode()).add(new BigDecimal(1));
        List<WzchSpecialMaterialPlan> planList = wzchSpecialMaterialPlanMapper.selectWzchSpecialMaterialPlanList(new WzchSpecialMaterialPlan(versionCode.toString(), plan.getProjectId()));
        if(CollectionUtils.isNotEmpty(planList)){
            throw new BaseException("项目名称【"+plan.getProjectName()+"】已存在【V"+versionCode.toString()+"】版本！");
        }
        List<WzchSpecialMaterialPlanDetail> wzchSpecialMaterialPlanDetails = wzchSpecialMaterialPlanDetailService.selectWzchSpecialMaterialPlanDetailList(new WzchSpecialMaterialPlanDetail(plan.getId()));
        if (CollectionUtils.isEmpty(wzchSpecialMaterialPlanDetails)) {
            throw new BaseException("调整失败");
        }
        Long planId = IdWorker.createId();
        plan.setId(planId);
        for (WzchSpecialMaterialPlanDetail wzchSpecialMaterialPlanDetail : wzchSpecialMaterialPlanDetails) {
            wzchSpecialMaterialPlanDetail.setId(IdWorker.createId());
            wzchSpecialMaterialPlanDetail.setPlanId(planId);
            wzchSpecialMaterialPlanDetail.setValid("0");
            wzchSpecialMaterialPlanDetail.setCreateTime(null);
            wzchSpecialMaterialPlanDetail.setCreateUser(null);
            wzchSpecialMaterialPlanDetail.setCreateUserName(null);
            wzchSpecialMaterialPlanDetail.setUpdateUser(null);
            wzchSpecialMaterialPlanDetail.setUpdateTime(null);
            wzchSpecialMaterialPlanDetail.setUpdateUserName(null);
        }
        List<WzchSpecialMaterialRequestDetail> wzchSpecialMaterialRequestDetails = wzchSpecialMaterialRequestDetailService.selectWzchSpecialMaterialRequestDetailList(new WzchSpecialMaterialRequestDetail(plan.getId()));
        for (WzchSpecialMaterialRequestDetail wzchSpecialMaterialRequestDetail : wzchSpecialMaterialRequestDetails) {
            wzchSpecialMaterialRequestDetail.setId(IdWorker.createId());
            wzchSpecialMaterialRequestDetail.setPlanId(planId);
            wzchSpecialMaterialRequestDetail.setValid("0");
            wzchSpecialMaterialRequestDetail.setCreateTime(null);
            wzchSpecialMaterialRequestDetail.setCreateUser(null);
            wzchSpecialMaterialRequestDetail.setCreateUserName(null);
            wzchSpecialMaterialRequestDetail.setUpdateUser(null);
            wzchSpecialMaterialRequestDetail.setUpdateTime(null);
            wzchSpecialMaterialRequestDetail.setUpdateUserName(null);
        }
        plan.setVersionCode(versionCode.toString());
        plan.setValid("0");
        plan.setPlanCode(null);
        plan.setCreateTime(null);
        plan.setCreateUser(null);
        plan.setCreateUserName(null);
        plan.setUpdateUser(null);
        plan.setUpdateTime(null);
        plan.setUpdateUserName(null);
        wzchCommonService.setWzchtMaterialInfo(wzchSpecialMaterialPlanDetails);
        plan.setPlanDetailList(wzchSpecialMaterialPlanDetails);
        plan.setRequestDetailList(wzchSpecialMaterialRequestDetails);
        return plan;

    }

    @Override
    @Transactional
    public void processStatus(WzchSpecialMaterialPlan wzchSpecialMaterialPlan) {
        if(wzchSpecialMaterialPlan== null || wzchSpecialMaterialPlan.getId()==null){
            throw new BaseException("入参缺失");
        }
        WzchSpecialMaterialPlan plan = wzchSpecialMaterialPlanMapper.selectWzchSpecialMaterialPlanById(wzchSpecialMaterialPlan.getId());
        if(plan==null){
            throw new BaseException("数据不存在");
        }
        //已经有效则返回
        if(YesOrNoEnum.YES.getValue().equals(plan.getValid())){
            return;
        }
        //先查询有效
        List<WzchSpecialMaterialPlan> planList = wzchSpecialMaterialPlanMapper.selectWzchSpecialMaterialPlanList(new WzchSpecialMaterialPlan(YesOrNoEnum.YES.getValue()));
        if(CollectionUtils.isNotEmpty(planList)) {
            WzchSpecialMaterialPlan materialPlan = planList.get(0);
            wzchSpecialMaterialPlanMapper.updateWzchSpecialMaterialPlan(new WzchSpecialMaterialPlan(materialPlan.getId(),YesOrNoEnum.NO.getValue()));
            wzchSpecialMaterialPlanDetailService.updateValidByPlanId(new WzchSpecialMaterialPlanDetail(materialPlan.getId(),YesOrNoEnum.NO.getValue()));
            wzchSpecialMaterialRequestDetailService.updateValidByPlanId(new WzchSpecialMaterialRequestDetail(materialPlan.getId(),YesOrNoEnum.NO.getValue()));
        }
        wzchSpecialMaterialPlanMapper.updateWzchSpecialMaterialPlan(new WzchSpecialMaterialPlan(plan.getId(),YesOrNoEnum.YES.getValue()));
        wzchSpecialMaterialPlanDetailService.updateValidByPlanId(new WzchSpecialMaterialPlanDetail(plan.getId(),YesOrNoEnum.YES.getValue()));
        wzchSpecialMaterialRequestDetailService.updateValidByPlanId(new WzchSpecialMaterialRequestDetail(plan.getId(),YesOrNoEnum.YES.getValue()));





    }
}
