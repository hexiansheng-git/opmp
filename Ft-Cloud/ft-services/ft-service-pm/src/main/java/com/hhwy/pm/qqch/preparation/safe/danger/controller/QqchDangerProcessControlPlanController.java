package com.hhwy.pm.qqch.preparation.safe.danger.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.safe.danger.domain.vo.QqchDangerProcessControlPlanVo;
import com.hhwy.pm.qqch.preparation.safe.danger.service.IQqchDangerProcessControlPlanService;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

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
    @PreAuthorize(hasPermi = "qqchDangerProcessControlPlan:add")
    @PostMapping("/batchSave")
    @CustomLogger(title = "前期策划-前期策划编制-安全策划-8.3 危大工程管控策划", name = "\n" +
            "8.3.3 危大工程过程管控策划" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult batchSave(
        @Validated(ValidationGroups.Save.class) @RequestBody QqchDangerProcessControlPlanVo qqchDangerProcessControlPlanVo) {
        qqchDangerProcessControlPlanService.batchSave(qqchDangerProcessControlPlanVo);
        return AjaxResult.success();
    }
}
