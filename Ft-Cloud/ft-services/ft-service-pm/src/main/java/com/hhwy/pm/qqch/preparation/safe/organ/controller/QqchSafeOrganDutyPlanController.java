package com.hhwy.pm.qqch.preparation.safe.organ.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
//import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.safe.organ.domain.QqchSafeOrganDutyPlan;
import com.hhwy.pm.qqch.preparation.safe.organ.domain.vo.QqchSafeOrganDutyPlanVo;
import com.hhwy.pm.qqch.preparation.safe.organ.service.IQqchSafeOrganDutyPlanService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
