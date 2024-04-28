package com.hhwy.pm.qqch.wzch.transport.service.impl;

import com.hhwy.common.core.exception.BaseException;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.pm.qqch.wzch.enums.YesOrNoEnum;
import com.hhwy.pm.qqch.wzch.transport.domain.WzchLocalTransportPlan;
import com.hhwy.pm.qqch.wzch.transport.domain.WzchLocalTransportPlanDetail;
import com.hhwy.pm.qqch.wzch.transport.mapper.WzchLocalTransportPlanMapper;
import com.hhwy.pm.qqch.wzch.transport.service.IWzchLocalTransportPlanDetailService;
import com.hhwy.pm.qqch.wzch.transport.service.IWzchLocalTransportPlanService;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.bigDecimalUtils.BigDecimalUtils;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 当地运输方案策划Service业务层处理
 * 
 * @author mls
 * @date 2022-12-06
 */
@Service
public class WzchLocalTransportPlanServiceImpl implements IWzchLocalTransportPlanService {
    @Autowired
    private WzchLocalTransportPlanMapper wzchLocalTransportPlanMapper;
    @Resource
    private IWzchLocalTransportPlanDetailService wzchLocalTransportPlanDetailService;
    @Resource
    private IQqchReviewService qqchReviewService;

    /**
     * 查询当地运输方案策划
     * 
     * @param id 当地运输方案策划ID
     * @return 当地运输方案策划
     */
    @Override
    public WzchLocalTransportPlan selectWzchLocalTransportPlanById(Long id) {
        return wzchLocalTransportPlanMapper.selectWzchLocalTransportPlanById(id);
    }

    /**
     * 查询当地运输方案策划列表
     * 
     * @param wzchLocalTransportPlan 当地运输方案策划
     * @return 当地运输方案策划
     */
    @Override
//    @CustomDatascope(alias = "wzch_local_transport_plan")
    public List<WzchLocalTransportPlan> selectWzchLocalTransportPlanList(WzchLocalTransportPlan wzchLocalTransportPlan) {
        return wzchLocalTransportPlanMapper.selectWzchLocalTransportPlanList(wzchLocalTransportPlan);
    }

    /**
     * 新增当地运输方案策划
     * 
     * @param wzchLocalTransportPlan 当地运输方案策划
     * @return 结果
     */
    @Override
    public int insertWzchLocalTransportPlan(WzchLocalTransportPlan wzchLocalTransportPlan) {

        wzchLocalTransportPlan.setCreateTime(DateUtils.getNowDate());

        return wzchLocalTransportPlanMapper.insertWzchLocalTransportPlan(wzchLocalTransportPlan);
    }

    /**
     * 修改当地运输方案策划
     * 
     * @param wzchLocalTransportPlan 当地运输方案策划
     * @return 结果
     */
    @Override
    public int updateWzchLocalTransportPlan(WzchLocalTransportPlan wzchLocalTransportPlan) {
        wzchLocalTransportPlan.setUpdateTime(DateUtils.getNowDate());
        return wzchLocalTransportPlanMapper.updateWzchLocalTransportPlan(wzchLocalTransportPlan);
    }

    /**
     * 删除当地运输方案策划对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteWzchLocalTransportPlanByIds(String ids) {
        return wzchLocalTransportPlanMapper.deleteWzchLocalTransportPlanByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除当地运输方案策划信息
     * 
     * @param id 当地运输方案策划ID
     * @return 结果
     */
    @Override
    public int deleteWzchLocalTransportPlanById(Long id) {
        return wzchLocalTransportPlanMapper.deleteWzchLocalTransportPlanById(id);
    }

    @Override
    public WzchLocalTransportPlan edit(WzchLocalTransportPlan wzchLocalTransportPlan) {
        BigDecimal sourceVersion = wzchLocalTransportPlan.getVersion();
        BigDecimal version = VersionUtil.getVersion("wzch_local_transport_plan", wzchLocalTransportPlan.getVersion());
        boolean isMatchVersion = BigDecimalUtils.equals(version,wzchLocalTransportPlan.getVersion());
        
        wzchLocalTransportPlan.setStageIdentity(qqchReviewService.getStage());
        List<WzchLocalTransportPlan> list = wzchLocalTransportPlanMapper.selectWzchLocalTransportPlanList(new WzchLocalTransportPlan(version));
        if(CollectionUtils.isEmpty(list)){
            wzchLocalTransportPlan.setWzchLocalTransportPlanDetailList(new ArrayList<>());
            return wzchLocalTransportPlan;
        }
        wzchLocalTransportPlan = list.get(0);
        wzchLocalTransportPlan.setVersion(ObjectUtils.nvlBigDecimal(sourceVersion,version));
        wzchLocalTransportPlan.setVersionCode(wzchLocalTransportPlan.getVersion()+"");
        wzchLocalTransportPlan.setStageIdentity(qqchReviewService.getStage());
        List<WzchLocalTransportPlanDetail> wzchLocalTransportPlanDetails = wzchLocalTransportPlanDetailService.selectWzchLocalTransportPlanDetailList(new WzchLocalTransportPlanDetail(wzchLocalTransportPlan.getId()));
        wzchLocalTransportPlan.setId(isMatchVersion?wzchLocalTransportPlan.getId():null); //若取得不是本版本，将id滞空，
        wzchLocalTransportPlanDetails = wzchLocalTransportPlanDetails.stream().sorted(Comparator.comparing(WzchLocalTransportPlanDetail::getId)).collect(Collectors.toList());
        wzchLocalTransportPlan.setWzchLocalTransportPlanDetailList(wzchLocalTransportPlanDetails);
        return wzchLocalTransportPlan;
    }

    @Override
    public boolean remove(WzchLocalTransportPlan wzchLocalTransportPlan) {
        if(wzchLocalTransportPlan==null || wzchLocalTransportPlan.getId()==null){
            throw new BaseException("入参缺失");
        }
        WzchLocalTransportPlan plan = wzchLocalTransportPlanMapper.selectWzchLocalTransportPlanById(wzchLocalTransportPlan.getId());
        if(plan==null){
            throw new BaseException("数据不存在");
        }
        wzchLocalTransportPlanMapper.deleteWzchLocalTransportPlanById(plan.getId());
        List<WzchLocalTransportPlanDetail> wzchLocalTransportPlanDetails = wzchLocalTransportPlanDetailService.selectWzchLocalTransportPlanDetailList(new WzchLocalTransportPlanDetail(plan.getId()));
        if (CollectionUtils.isEmpty(wzchLocalTransportPlanDetails)) {
            return true;
        }
        List<Long> detailIds = wzchLocalTransportPlanDetails.stream().map(WzchLocalTransportPlanDetail::getId).collect(Collectors.toList());
        wzchLocalTransportPlanDetailService.deleteWzchLocalTransportPlanDetailByIds(detailIds);
        return true;
    }

    @Override
    public WzchLocalTransportPlan modify(WzchLocalTransportPlan wzchLocalTransportPlan) {
        if(wzchLocalTransportPlan==null || wzchLocalTransportPlan.getId()==null){
            throw new BaseException("入参缺失");
        }
        WzchLocalTransportPlan plan = wzchLocalTransportPlanMapper.selectWzchLocalTransportPlanById(wzchLocalTransportPlan.getId());
        if("0".equals(plan.getValid()) || StringUtils.isBlank(plan.getVersionCode())){
            throw new BaseException("调整失败");
        }
        List<WzchLocalTransportPlanDetail> wzchLocalTransportPlanDetails = wzchLocalTransportPlanDetailService.selectWzchLocalTransportPlanDetailList(new WzchLocalTransportPlanDetail(plan.getId()));
        if (CollectionUtils.isEmpty(wzchLocalTransportPlanDetails)) {
            throw new BaseException("调整失败");
        }
        for (WzchLocalTransportPlanDetail wzchLocalTransportPlanDetail : wzchLocalTransportPlanDetails) {
            wzchLocalTransportPlanDetail.setId(null);
            wzchLocalTransportPlanDetail.setPlanId(null);
            wzchLocalTransportPlanDetail.setValid("0");
            wzchLocalTransportPlanDetail.setCreateTime(null);
            wzchLocalTransportPlanDetail.setCreateUser(null);
            wzchLocalTransportPlanDetail.setCreateUserName(null);
            wzchLocalTransportPlanDetail.setUpdateUser(null);
            wzchLocalTransportPlanDetail.setUpdateTime(null);
            wzchLocalTransportPlanDetail.setUpdateUserName(null);
        }
        BigDecimal versionCode = new BigDecimal(plan.getVersionCode()).add(new BigDecimal(1));
        plan.setVersionCode(versionCode.toString());
        plan.setId(IdWorker.createId());
        plan.setValid("0");
        plan.setPlanCode(null);
        plan.setCreateTime(null);
        plan.setCreateUser(null);
        plan.setCreateUserName(null);
        plan.setUpdateUser(null);
        plan.setUpdateTime(null);
        plan.setUpdateUserName(null);
        plan.setWzchLocalTransportPlanDetailList(wzchLocalTransportPlanDetails);
        return plan;
    }

    @Override
    public void processStatus(WzchLocalTransportPlan wzchLocalTransportPlan) {
        if(wzchLocalTransportPlan==null){
            throw new BaseException("入参缺失");
        }
        WzchLocalTransportPlan plan = wzchLocalTransportPlanMapper.selectWzchLocalTransportPlanById(wzchLocalTransportPlan.getId());
        if(plan==null){
            throw new BaseException("数据不存在");
        }
        //已经有效则返回
        if(YesOrNoEnum.YES.getValue().equals(plan.getValid())){
            return;
        }
        //先查询有效
        List<WzchLocalTransportPlan> wzchLocalTransportPlans = wzchLocalTransportPlanMapper.selectWzchLocalTransportPlanList(new WzchLocalTransportPlan(YesOrNoEnum.YES.getValue()));
        if(CollectionUtils.isNotEmpty(wzchLocalTransportPlans)) {
            WzchLocalTransportPlan transportPlan = wzchLocalTransportPlans.get(0);
            wzchLocalTransportPlanMapper.updateWzchLocalTransportPlan(new WzchLocalTransportPlan(transportPlan.getId(),YesOrNoEnum.NO.getValue()));
            wzchLocalTransportPlanDetailService.updateValidByPlanId(new WzchLocalTransportPlanDetail(transportPlan.getId(),YesOrNoEnum.NO.getValue()));
        }
        wzchLocalTransportPlanMapper.updateWzchLocalTransportPlan(new WzchLocalTransportPlan(plan.getId(),YesOrNoEnum.YES.getValue()));
        wzchLocalTransportPlanDetailService.updateValidByPlanId(new WzchLocalTransportPlanDetail(plan.getId(),YesOrNoEnum.YES.getValue()));
    }


}
