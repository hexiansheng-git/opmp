package com.hhwy.pm.qqch.wzch.importplan.service.impl;

import com.hhwy.common.core.exception.BaseException;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.enums.FlowEnum;
import com.hhwy.pm.gencode.service.GenCodeService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.pm.qqch.wzch.enums.YesOrNoEnum;
import com.hhwy.pm.qqch.wzch.importplan.domain.WzchImportExportPlan;
import com.hhwy.pm.qqch.wzch.importplan.domain.WzchImportExportPlanDetail;
import com.hhwy.pm.qqch.wzch.importplan.mapper.WzchImportExportPlanMapper;
import com.hhwy.pm.qqch.wzch.importplan.service.IWzchImportExportPlanDetailService;
import com.hhwy.pm.qqch.wzch.importplan.service.IWzchImportExportPlanService;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.bigDecimalUtils.BigDecimalUtils;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 进出口策划Service业务层处理
 * 
 * @author mls
 * @date 2022-12-05
 */
@Service
public class WzchImportExportPlanServiceImpl implements IWzchImportExportPlanService {
    @Resource
    private WzchImportExportPlanMapper wzchImportExportPlanMapper;
    @Resource
    private IWzchImportExportPlanDetailService wzchImportExportPlanDetailService;
    @Resource
    private GenCodeService genCodeService;
    @Resource
    private IQqchReviewService qqchReviewService;
    /**
     * 查询进出口策划
     * 
     * @param id 进出口策划ID
     * @return 进出口策划
     */
    @Override
    public WzchImportExportPlan selectWzchImportExportPlanById(Long id) {
        return wzchImportExportPlanMapper.selectWzchImportExportPlanById(id);
    }

    /**
     * 查询进出口策划列表
     * 
     * @param wzchImportExportPlan 进出口策划
     * @return 进出口策划
     */
    @Override
//    @CustomDatascope(alias = "iep")
    public List<WzchImportExportPlan> selectWzchImportExportPlanList(WzchImportExportPlan wzchImportExportPlan) {
        return wzchImportExportPlanMapper.selectWzchImportExportPlanList(wzchImportExportPlan);
    }

    /**
     * 新增进出口策划
     * 
     * @param wzchImportExportPlan 进出口策划
     * @return 结果
     */
    @Override
    public int insertWzchImportExportPlan(WzchImportExportPlan wzchImportExportPlan) {

        wzchImportExportPlan.setCreateTime(DateUtils.getNowDate());

        return wzchImportExportPlanMapper.insertWzchImportExportPlan(wzchImportExportPlan);
    }

    /**
     * 修改进出口策划
     * 
     * @param wzchImportExportPlan 进出口策划
     * @return 结果
     */
    @Override
    public int updateWzchImportExportPlan(WzchImportExportPlan wzchImportExportPlan) {
        wzchImportExportPlan.setUpdateTime(DateUtils.getNowDate());
        return wzchImportExportPlanMapper.updateWzchImportExportPlan(wzchImportExportPlan);
    }

    /**
     * 删除进出口策划对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteWzchImportExportPlanByIds(String ids) {
        return wzchImportExportPlanMapper.deleteWzchImportExportPlanByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除进出口策划信息
     * 
     * @param id 进出口策划ID
     * @return 结果
     */
    @Override
    public int deleteWzchImportExportPlanById(Long id) {
        return wzchImportExportPlanMapper.deleteWzchImportExportPlanById(id);
    }

    @Override
    public WzchImportExportPlan detail(WzchImportExportPlan wzchImportExportPlan) {
        BigDecimal sourceVersion = wzchImportExportPlan.getVersion();
        BigDecimal version = VersionUtil.getVersion("wzch_import_export_plan", wzchImportExportPlan.getVersion());
        boolean isMatchVersion = BigDecimalUtils.equals(version,wzchImportExportPlan.getVersion());
        wzchImportExportPlan.setVersion(version);
        wzchImportExportPlan.setStageIdentity(qqchReviewService.getStage());
        List<WzchImportExportPlan> list = wzchImportExportPlanMapper.selectWzchImportExportPlanList(wzchImportExportPlan);
        wzchImportExportPlan.setVersion(ObjectUtils.nvlBigDecimal(sourceVersion,version));
        if(CollectionUtils.isEmpty(list)){
            wzchImportExportPlan.setWzchImportExportPlanDetailList(new ArrayList<>());
            return wzchImportExportPlan;
        }
        wzchImportExportPlan.setId(list.get(0).getId());
        wzchImportExportPlan.setId(isMatchVersion||sourceVersion==null?wzchImportExportPlan.getId():null); //若取得不是本版本，将id滞空，
        List<WzchImportExportPlanDetail> wzchImportExportPlanDetails = wzchImportExportPlanDetailService.selectWzchImportExportPlanDetailList(new WzchImportExportPlanDetail(wzchImportExportPlan.getId()));
        wzchImportExportPlan.setWzchImportExportPlanDetailList(wzchImportExportPlanDetails);
        return wzchImportExportPlan;
        
    }

    @Override
    public boolean remove(WzchImportExportPlan wzchImportExportPlan) {
        WzchImportExportPlan plan = wzchImportExportPlanMapper.selectWzchImportExportPlanById(wzchImportExportPlan.getId());
        if(plan==null){
            return false;
        }
        wzchImportExportPlanMapper.deleteWzchImportExportPlanById(wzchImportExportPlan.getId());
        List<WzchImportExportPlanDetail> wzchImportExportPlanDetails = wzchImportExportPlanDetailService.selectWzchImportExportPlanDetailList(new WzchImportExportPlanDetail(plan.getId()));
        if(CollectionUtils.isEmpty(wzchImportExportPlanDetails)){
            return true;
        }
        List<Long> detailIds = wzchImportExportPlanDetails.stream().map(WzchImportExportPlanDetail::getPlanId).collect(Collectors.toList());
        wzchImportExportPlanDetailService.deleteWzchImportExportPlanDetailByIds(detailIds);
        return true;
    }

    @Override
    public WzchImportExportPlan modify(WzchImportExportPlan wzchImportExportPlan){
        if(wzchImportExportPlan == null || wzchImportExportPlan.getId()==null){
            throw new BaseException("入参缺失");
        }
        WzchImportExportPlan plan = wzchImportExportPlanMapper.selectWzchImportExportPlanById(wzchImportExportPlan.getId());
        if (plan==null) {
            throw new BaseException("数据不存在");
        }
        if("0".equals(plan.getValid())){
            throw new BaseException("该版本数据未生效不能进行调整");
        }
        if (StringUtils.isBlank(plan.getVersionCode())) {
            throw new BaseException("未获取到版本信息");
        }
        BigDecimal versionCode = new BigDecimal(plan.getVersionCode()).add(new BigDecimal(1));
        List<WzchImportExportPlan> importExportPlans = wzchImportExportPlanMapper.selectWzchImportExportPlanList(new WzchImportExportPlan(versionCode.toString(), plan.getProjectId()));
        if(CollectionUtils.isNotEmpty(importExportPlans)){
            throw new BaseException("项目名称【"+plan.getProjectName()+"】已存在【V"+versionCode.toString()+"】版本！");
        }
        List<WzchImportExportPlanDetail> detailList = wzchImportExportPlanDetailService.selectWzchImportExportPlanDetailList(new WzchImportExportPlanDetail(plan.getId()));
        if(CollectionUtils.isEmpty(detailList)){
            throw new BaseException("调整失败");
        }
        Long planId = IdWorker.createId();
        plan.setId(planId);
        for (WzchImportExportPlanDetail detail : detailList) {
            detail.setId(IdWorker.createId());
            detail.setPlanId(planId);
            detail.setValid("0");
            detail.setCreateUser(null);
            detail.setCreateUserName(null);
            detail.setCreateTime(null);
            detail.setUpdateUser(null);
            detail.setUpdateUserName(null);
            detail.setUpdateTime(null);
        }

        plan.setPlanCode(genCodeService.getNewCode(plan.getPlanCode(),versionCode.intValue()));
        plan.setVersionCode(versionCode.toString());
        plan.setWzchImportExportPlanDetailList(detailList);
        plan.setValid("0");
        plan.setCreateUser(null);
        plan.setCreateUserName(null);
        plan.setCreateTime(null);
        plan.setUpdateUser(null);
        plan.setUpdateUserName(null);
        plan.setUpdateTime(null);
        return plan;
    }

    @Override
    public void processStatus(WzchImportExportPlan wzchImportExportPlan) {
        if(wzchImportExportPlan==null){
            throw new BaseException("入参缺失");
        }
        WzchImportExportPlan plan = wzchImportExportPlanMapper.selectWzchImportExportPlanById(wzchImportExportPlan.getId());
        if(plan==null){
            throw new BaseException("数据不存在");
        }
        //已经有效则返回
        if(YesOrNoEnum.YES.getValue().equals(plan.getValid())){
            return;
        }
        //先查询有效
        List<WzchImportExportPlan> importExportPlans = wzchImportExportPlanMapper.selectWzchImportExportPlanList(new WzchImportExportPlan(YesOrNoEnum.YES.getValue()));
        if(CollectionUtils.isNotEmpty(importExportPlans)) {
            WzchImportExportPlan exportPlan = importExportPlans.get(0);
            wzchImportExportPlanMapper.updateWzchImportExportPlan(new WzchImportExportPlan(exportPlan.getId(),YesOrNoEnum.NO.getValue()));
//            wzchImportExportPlanDetailService.updateValidByPlanId(new WzchLocalTransportPlanDetail(exportPlan.getId(),YesOrNoEnum.NO.getValue()));
        }
        wzchImportExportPlanMapper.updateWzchImportExportPlan(new WzchImportExportPlan(plan.getId(),YesOrNoEnum.YES.getValue()));
//        wzchImportExportPlanDetailService.updateValidByPlanId(new WzchLocalTransportPlanDetail(plan.getId(),YesOrNoEnum.YES.getValue()));

    }

}
