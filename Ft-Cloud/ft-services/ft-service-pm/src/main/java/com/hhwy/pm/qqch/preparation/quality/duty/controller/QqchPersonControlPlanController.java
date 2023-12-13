package com.hhwy.pm.qqch.preparation.quality.duty.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.quality.duty.domain.vo.QqchPersonControlPlanVo;
import com.hhwy.pm.qqch.preparation.quality.duty.service.IQqchPersonControlPlanService;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * @author zhenglili
 * @date 2023-08-03 14:29:43
 * @remark 9.1.2 人员管控策划
 */
@Validated
@RestController
@RequestMapping("/qqchPersonControlPlan")
public class QqchPersonControlPlanController extends BaseController {

    @Autowired
    private IQqchPersonControlPlanService qqchPersonControlPlanService;

    /**
     * 列表
     *
     * @param version
     * @return
     */
    @PreAuthorize(hasPermi = "qqchPersonControlPlan:list")
    @GetMapping("/getList")
    public AjaxResult getList(BigDecimal version) {
        QqchPersonControlPlanVo qqchPersonControlPlanVo = qqchPersonControlPlanService
            .getQqchPersonControlPlanList(version);
        return AjaxResult.success(qqchPersonControlPlanVo);
    }

    /**
     * 保存/确认/提交
     *
     * @param qqchPersonControlPlanVo
     * @return
     */
    @PreAuthorize(hasPermi = "qqchPersonControlPlan:add")
    @PostMapping("/batchSave")
    @CustomLogger(title = "前期策划-前期策划编制-质量策划-9.1质量组织设置及职责", name = "\n" +
            "9.1.2 人员管控策划" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult batchSave(
        @Validated(ValidationGroups.Save.class) @RequestBody QqchPersonControlPlanVo qqchPersonControlPlanVo) {
        qqchPersonControlPlanService.insertQqchPersonControlPlanList(qqchPersonControlPlanVo);
        return AjaxResult.success();
    }

    /**
     * 人员管控策划发送预警消息
     *
     * @return
     */
    @GetMapping("personControlPlanWarn")
    public AjaxResult personControlPlanWarn() {
        qqchPersonControlPlanService.personControlPlanWarn();
        return AjaxResult.success();
    }
}
