package com.hhwy.pm.qqch.preparation.safe.organ.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.safe.organ.domain.vo.QqchSafeOrganDutyPlanVo;
import com.hhwy.pm.qqch.preparation.safe.organ.service.IQqchSafeOrganDutyPlanService;
import com.hhwy.utils.validation.ValidationGroups;
import java.math.BigDecimal;
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
    @PreAuthorize(hasPermi = "qqchSafeOrganDutyPlan:list")
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
    @PreAuthorize(hasPermi = "qqchSafeOrganDutyPlan:add")
    @PostMapping("/batchSave")
    public AjaxResult batchSave(
        @Validated(ValidationGroups.Save.class) @RequestBody QqchSafeOrganDutyPlanVo qqchSafeOrganDutyPlanVo) {
        qqchSafeOrganDutyPlanService.batchSave(qqchSafeOrganDutyPlanVo);
        return AjaxResult.success();
    }
}
