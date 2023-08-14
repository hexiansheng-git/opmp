package com.hhwy.pm.common.service;

import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.common.mapper.CommonMapper;
import com.hhwy.pm.qqch.constant.ConfirmStatus;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.qqchWorkPlan.domain.QqchWorkPlan;
import com.hhwy.pm.qqch.qqchWorkPlan.domain.QqchWorkPlanDetail;
import com.hhwy.pm.qqch.qqchWorkPlan.service.IQqchWorkPlanDetailService;
import com.hhwy.pm.qqch.qqchWorkPlan.service.IQqchWorkPlanService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.utils.exception.CustomBusinessException;
import io.jsonwebtoken.lang.Assert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    /**
     * 检验菜单是否有编辑权限
     * @param menuId 菜单id
     * @return
     */
    public boolean checkIsEditable(String menuId) {

        //获取当前阶段
        String currentStage = qqchReviewService.getStage();
        if ("end".equals(currentStage)) {
            throw new RuntimeException("前期策划评审已结束");
        }
        //获取当前登录人信息
        Long userId = SecurityUtils.getUserId();
        // 根据当前阶段和登录人查询有没有编辑权限
        QqchWorkPlan qqchWorkPlan = new QqchWorkPlan();
        qqchWorkPlan.setValid("1");
        qqchWorkPlan.setTaskStatus("5");
        qqchWorkPlan.setDelFlag("0");
        List<QqchWorkPlan> qqchWorkPlanList = qqchWorkPlanService.getQqchWorkPlanList(qqchWorkPlan);
        if (qqchWorkPlanList.size() != 1) {
            throw new RuntimeException("前期策划工作计划数据异常");
        }
        // 有效的工作计划
        QqchWorkPlan workPlan = qqchWorkPlanList.get(0);
        Long id = workPlan.getId();
        QqchWorkPlanDetail planDetail = new QqchWorkPlanDetail();
        planDetail.setDelFlag("0");
        planDetail.setItemId(menuId);
        planDetail.setMainId(id);
        List<QqchWorkPlanDetail> qqchWorkPlanDetailList = qqchWorkPlanDetailService.getQqchWorkPlanDetailList(planDetail);


        // 获取当前菜单 当前阶段 确认状态
        String confirmStatus = qqchModuleConfirmCaseService.getConfirmStatus(menuId, currentStage, "" + userId);


        return false;
    }
}
