package com.hhwy.pm.qqch.preparation.survey.risk.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.constant.DictType;
import com.hhwy.feign.service.SystemServiceApi;
import com.hhwy.pm.qqch.common.defaultData.service.IQqchDefaultDataInitializeService;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.ModuleIdentity;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.impl.QqchModuleConfirmCaseServiceImpl;
import com.hhwy.pm.qqch.preparation.survey.risk.domain.QqchDailyControlPlan;
import com.hhwy.pm.qqch.preparation.survey.risk.domain.vo.QqchDailyControlPlanVo;
import com.hhwy.pm.qqch.preparation.survey.risk.mapper.QqchDailyControlPlanMapper;
import com.hhwy.pm.qqch.preparation.survey.risk.service.IQqchDailyControlPlanService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

;

/**
 * @author han
 * @date 2023-07-13 11:39:57
 * @remark 日常管控策划
 */
@Service
public class QqchDailyControlPlanServiceImpl implements IQqchDailyControlPlanService {

    @Autowired
    private QqchDailyControlPlanMapper qqchDailyControlPlanMapper;

    @Autowired
    private QqchModuleConfirmCaseServiceImpl qqchModuleConfirmCaseService;

    @Autowired
    private SystemServiceApi systemServiceApi;

    @Autowired
    private IQqchReviewService qqchReviewService;

    @Autowired
    private IQqchDefaultDataInitializeService qqchDefaultDataInitializeService;


    /**
     * 获取日常管控策划Vo
     * @return
     * @param version
     */
    public QqchDailyControlPlanVo getQqchDailyControlPlanVo(BigDecimal version) {
        QqchDailyControlPlanVo qqchDailyControlPlanVo = new QqchDailyControlPlanVo();

        version = VersionUtil.getVersion("qqch_daily_control_plan",version);
        QqchDailyControlPlan qqchDailyControlPlan = new QqchDailyControlPlan();
        qqchDailyControlPlan.setVersion(version);
        List<QqchDailyControlPlan> qqchDailyControlPlanList = qqchDailyControlPlanMapper.getQqchDailyControlPlanList(qqchDailyControlPlan);
        if(CollectionUtils.isEmpty(qqchDailyControlPlanList)){
            //判断是否已经初始化过
            boolean initialize = qqchDefaultDataInitializeService.interpretInitializeStatus(ModuleIdentity.OPTIMIZE_PROCEDURE_PLAN, version);
            if(!initialize){
                qqchDailyControlPlanList = this.getInitializeData();
            }
        }

        qqchDailyControlPlanVo.setVersion(version);
        qqchDailyControlPlanVo.setStageIdentity(qqchReviewService.getStage());
        qqchDailyControlPlanVo.setQqchDailyControlPlanList(qqchDailyControlPlanList);
        return qqchDailyControlPlanVo;
    }

    /**
     * 获取初始化数据
     * @return
     */
    public List<QqchDailyControlPlan> getInitializeData() {
        List<QqchDailyControlPlan> qqchDailyControlPlanList = new ArrayList<>();
        
        AjaxResult result = systemServiceApi.dictType(DictType.DAILY_CONTROL_PLAN_INITIALIZE);
        List<Map<String,Object>> dictDataList = (List<Map<String, Object>>) result.get("data");

        for (Map<String, Object> map : dictDataList) {
            String dictLabel = (String) map.get("dictLabel");
            QqchDailyControlPlan qqchDailyControlPlan = new QqchDailyControlPlan();
            qqchDailyControlPlan.setControlMeasure(dictLabel);
            qqchDailyControlPlanList.add(qqchDailyControlPlan);
        }
        
        return qqchDailyControlPlanList;
    }

    /**
     * 保存
     * @param qqchDailyControlPlanVo
     * @return
     */
    @Override
    @Transactional
    public void save(QqchDailyControlPlanVo qqchDailyControlPlanVo) {
        //删除旧数据
        QqchDailyControlPlan qqchDailyControlPlan = new QqchDailyControlPlan();
        qqchDailyControlPlan.setVersion(qqchDailyControlPlanVo.getVersion());
        qqchDailyControlPlanMapper.deleteQqchDailyControlPlan(qqchDailyControlPlan);

        //插入新数据
        this.insertQqchDailyControlPlanList(qqchDailyControlPlanVo.getQqchDailyControlPlanList(),qqchDailyControlPlanVo.getVersion());
    }

    /**
     * 确认
     * @param qqchDailyControlPlanVo
     * @return
     */
    @Override
    @Transactional
    public void confirm(QqchDailyControlPlanVo qqchDailyControlPlanVo) {
        this.save(qqchDailyControlPlanVo);

        String buttonMark = qqchDailyControlPlanVo.getButtonMark();
        if(ButtonMark.CONFIRM.equals(buttonMark)){
            //插入确认状态
            String menuId = qqchDailyControlPlanVo.getMenuId();
            String stageIdentity = qqchDailyControlPlanVo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
    }

    /**
     * 批量插入
     * @param qqchDailyControlPlanList
     * @param version
     */
    @Transactional
    public void insertQqchDailyControlPlanList(List<QqchDailyControlPlan> qqchDailyControlPlanList, BigDecimal version) {
        if(CollectionUtils.isEmpty(qqchDailyControlPlanList)){
            return;
        }
        for (QqchDailyControlPlan qqchDailyControlPlan : qqchDailyControlPlanList) {
            qqchDailyControlPlan.setId(IdWorker.createId());
            qqchDailyControlPlan.setVersion(version);
            if(version.compareTo(BigDecimal.ONE) == 0){
                qqchDailyControlPlan.setValid(Valid.YES);
            }
            qqchDailyControlPlan.setCreateUser(SecurityUtils.getUserName());
            qqchDailyControlPlan.setCreateTime(DateUtils.getNowDate());
        }
        qqchDailyControlPlanMapper.insertQqchDailyControlPlanList(qqchDailyControlPlanList);
    }
}
