package com.hhwy.pm.qqch.preparation.safe.organ.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qqch.preparation.safe.organ.domain.QqchSafeOrganDutyPlan;
import com.hhwy.pm.qqch.preparation.safe.organ.domain.vo.QqchSafeOrganDutyPlanVo;
import com.hhwy.pm.qqch.preparation.safe.organ.service.IQqchSafeOrganDutyPlanService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author zhenglili
 * @date 2023-08-07 13:49:43
 * @remark 8.1.1 安全组织职责策划
 */
@Validated
@RestController
@RequestMapping("/qqchSafeOrganDutyPlan")
public class QqchSafeOrganDutyPlanController extends BaseController {

    @Autowired
    private IQqchSafeOrganDutyPlanService qqchSafeOrganDutyPlanService;

    /**
     * 列表
     *
     * @param version
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchSafeOrganDutyPlan:list")
    @GetMapping("/getList")
    public AjaxResult getList(BigDecimal version) {
        QqchSafeOrganDutyPlanVo qqchSafeOrganDutyPlanVo = qqchSafeOrganDutyPlanService
            .getQqchSafeOrganDutyPlanList(version);
        return AjaxResult.success(qqchSafeOrganDutyPlanVo);
    }

    /**
     * 保存/确认/提交
     *
     * @param qqchSafeOrganDutyPlanVo
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchSafeOrganDutyPlan:add")
    @PostMapping("/batchSave")
    @CustomLogger(title = "前期策划-前期策划编制-安全策划-8.1 安全组织机构及人员策划", name = "\n" +
            "8.1.1 安全组织职责策划" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult batchSave(@RequestBody QqchSafeOrganDutyPlanVo qqchSafeOrganDutyPlanVo) {
        qqchSafeOrganDutyPlanService.batchSave(qqchSafeOrganDutyPlanVo);
        return AjaxResult.success();
    }

    /**
     * 分页列表
     *
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchSafeOrganDutyPlan:list")
    @GetMapping("/getPageList")
    public AjaxResult getPageList() {
        // 获取最新版本
        BigDecimal version = VersionUtil.getVersion("qqch_safe_organ_duty_plan", null);
        startPage();
        List<QqchSafeOrganDutyPlan> list = qqchSafeOrganDutyPlanService.getNewVersionList(version);
        return getDataTableAjaxResult(list);
    }
}
