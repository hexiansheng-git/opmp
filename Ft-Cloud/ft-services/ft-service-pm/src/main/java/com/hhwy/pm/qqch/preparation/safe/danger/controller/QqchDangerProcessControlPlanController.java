package com.hhwy.pm.qqch.preparation.safe.danger.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.safe.danger.domain.vo.QqchDangerProcessControlPlanVo;
import com.hhwy.pm.qqch.preparation.safe.danger.service.IQqchDangerProcessControlPlanService;
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
 * @date 2023-08-07 14:24:24
 * @remark 8.3.3 危大工程过程管控策划
 */
@Validated
@RestController
@RequestMapping("/qqchDangerProcessControlPlan")
public class QqchDangerProcessControlPlanController extends BaseController {

    @Autowired
    private IQqchDangerProcessControlPlanService qqchDangerProcessControlPlanService;

    /**
     * 列表
     *
     * @param version
     * @return
     */
    @PreAuthorize(hasPermi = "qqchDangerProcessControlPlan:list")
    @GetMapping("/getList")
    public AjaxResult getQqchDangerProcessControlPlanList(BigDecimal version) {
        QqchDangerProcessControlPlanVo qqchDangerProcessControlPlanVo = qqchDangerProcessControlPlanService
            .getQqchDangerProcessControlPlanList(version);
        return AjaxResult.success(qqchDangerProcessControlPlanVo);
    }

    /**
     * 保存/确认/提交
     *
     * @param qqchDangerProcessControlPlanVo
     * @return
     */
    @PreAuthorize(hasPermi = "qqchDangerProcessControlPlan:update")
    @PostMapping("/batchSave")
    public AjaxResult batchSave(
        @Validated(ValidationGroups.Update.class) @RequestBody QqchDangerProcessControlPlanVo qqchDangerProcessControlPlanVo) {
        qqchDangerProcessControlPlanService.batchSave(qqchDangerProcessControlPlanVo);
        return AjaxResult.success();
    }
}
