package com.hhwy.pm.common.service;

import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.constant.CommonYesNo;
import com.hhwy.pm.common.constant.ButtonStatus;
import com.hhwy.pm.common.domain.PermissionMark;
import com.hhwy.pm.common.mapper.CommonMapper;
import com.hhwy.pm.constant.PmConstant;
import com.hhwy.pm.qqch.module.domain.QqchModuleConfirmCase;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.qqchWorkPlan.domain.QqchWorkPlan;
import com.hhwy.pm.qqch.qqchWorkPlan.domain.QqchWorkPlanDetail;
import com.hhwy.pm.qqch.qqchWorkPlan.service.IQqchWorkPlanDetailService;
import com.hhwy.pm.qqch.qqchWorkPlan.service.IQqchWorkPlanService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.utils.common.CommonAssert;
import com.hhwy.utils.exception.CustomBusinessException;
import io.jsonwebtoken.lang.Assert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * 物资策划通用业务类
 *
 * @author mls
 */
@Slf4j
@Service
public class CommonService {
    @Resource
    private CommonMapper commonMapper;

    @Autowired
    private IQqchReviewService qqchReviewService;

    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;
    
    @Autowired
    private IQqchWorkPlanService qqchWorkPlanService;


    @Autowired
    private IQqchWorkPlanDetailService qqchWorkPlanDetailService;

//    @Value("${editableFlag}")
//    private String editableFlag;

    /**
     * 校验单据能否被调整 (单条数据只能调整一次)
     *
     * @param tableName  表名称
     * @param businessId 业务id (编辑数据的时候会进行id查询, 调整的时候也会根据id查询)
     * @return
     */
    public void canAdjustOnly(Long businessId, String tableName) {
        Assert.notNull(businessId, "主键不能为空");
        Assert.notNull(tableName, "表名称不能为空");
        String valid = this.commonMapper.selectCanAdjust(businessId, tableName);
        if (!"1".equals(valid)) {
            throw new CustomBusinessException("只能调整生效的单据");
        }
        //项目中 版本号最大的id
        Long idMax = this.commonMapper.selectCanAdjustOnly(tableName);
        if (businessId.longValue() != idMax.longValue()) {
            throw new CustomBusinessException("此条数据只能调整一次");
        }
    }


    /**根据主id，逻辑删除子表数据
     * @param mainId
     * @param tableName
     */
    public void deleteDetailsByMainId(Long mainId, String tableName) {
        Assert.notNull(mainId, "主表id不能为空");
        Assert.notNull(tableName, "表名称不能为空");
        this.commonMapper.deleteDetailsByMainId(mainId, tableName);
    }

    private static final String POP_WINDOWS = "popWindows";

    /**
     * 检验菜单是否有编辑权限
     *
     * @param menuId 菜单id
     * @return
     */
    public PermissionMark checkIsEditable(String menuId) {
        CommonAssert.notBlank(menuId,"菜单id不能为空！");
        PermissionMark permissionMark = new PermissionMark();

        if(POP_WINDOWS.equals(menuId)){
            permissionMark.setButtonStatus(ButtonStatus.DISAPPEAR);
            return permissionMark;
        }

        if(SecurityUtils.getSysUser().isAdmin()){
            return permissionMark;
        }

        //获取当前阶段
        String currentStage = qqchReviewService.getStage();
        if (PmConstant.END_STAGE.equals(currentStage)) {
            permissionMark.setReviewEnd(CommonYesNo.YES);
            permissionMark.setButtonStatus(ButtonStatus.DISAPPEAR);
            permissionMark.setMsg("前期策划评审已结束");
            return permissionMark;
        }

        //获取当前登录人信息
        Long userId = SecurityUtils.getUserId();
        // 根据当前阶段和登录人查询有没有编辑权限
        QqchWorkPlan qqchWorkPlan = new QqchWorkPlan();
        qqchWorkPlan.setValid("1");
        qqchWorkPlan.setTaskStatus("5");
        List<QqchWorkPlan> qqchWorkPlanList = qqchWorkPlanService.getQqchWorkPlanList(qqchWorkPlan);
        // 查询到的数量不是0个的话 工作计划
        if (qqchWorkPlanList.size() != 1){
            permissionMark.setHavePlan(CommonYesNo.NO);
            permissionMark.setButtonStatus(ButtonStatus.DISAPPEAR);
            permissionMark.setMsg("当前不存在工作计划，不可编辑！");
            return permissionMark;
        }

        // 有效的工作计划
        QqchWorkPlan workPlan = qqchWorkPlanList.get(0);
        Long id = workPlan.getId();
        QqchWorkPlanDetail planDetail = new QqchWorkPlanDetail();
        planDetail.setItemId(menuId);
        planDetail.setMainId(id);


        switch (currentStage) {
            case PmConstant.ONE:
                planDetail.setIsFirst(PmConstant.ONE);
                planDetail.setEditorFirst(userId + "");
                break;
            case PmConstant.TWO:
                planDetail.setIsSecond(PmConstant.ONE);
                planDetail.setEditorSecond(userId + "");
                break;
            case PmConstant.THREE:
                planDetail.setIsThird(PmConstant.ONE);
                planDetail.setEditorThird(userId + "");
                break;
            default:
        }
        // 根据阶段 编制人 页面唯一标识查询有没有编辑权限
        List<QqchWorkPlanDetail> qqchWorkPlanDetailList = qqchWorkPlanDetailService.getQqchWorkPlanDetailList(planDetail);
        // 如果没有查询到数据
//        if (CollectionUtils.isEmpty(qqchWorkPlanDetailList)){
//            permissionMark.setMsg("当前用户在当前阶段没有当前页面的编辑权限!");
//            permissionMark.setButtonStatus(ButtonStatus.DISAPPEAR);
//            permissionMark.setEditable(CommonYesNo.NO);
//            return permissionMark;
//        }

        // 获取当前菜单 当前阶段 当前登录人有没有确认过
        List<QqchModuleConfirmCase> confirmStatus = qqchModuleConfirmCaseService.getConfirmStatus(menuId, currentStage, null);
        // 确认记录不为空的话 则证明当前阶段已经被确认过 无需再进行确认
        if (!CollectionUtils.isEmpty(confirmStatus)){
            permissionMark.setMsg("当前页面在当前阶段已确认完成!");
            permissionMark.setButtonStatus(ButtonStatus.GREY);
            permissionMark.setConfirmed(CommonYesNo.YES);
        }

        return permissionMark;
    }
}
