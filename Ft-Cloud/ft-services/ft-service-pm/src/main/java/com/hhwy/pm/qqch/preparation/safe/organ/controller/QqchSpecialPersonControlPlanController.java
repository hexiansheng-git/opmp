package com.hhwy.pm.qqch.preparation.safe.organ.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qqch.preparation.safe.organ.domain.vo.QqchSpecialPersonControlPlanVo;
import com.hhwy.pm.qqch.preparation.safe.organ.service.IQqchSpecialPersonControlPlanService;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * @author zhenglili
 * @date 2023-08-07 13:50:54
 * @remark 8.1.3 特种作业人员管控策划
 */
@Validated
@RestController
@RequestMapping("/qqchSpecialPersonControlPlan")
public class QqchSpecialPersonControlPlanController extends BaseController {

    @Autowired
    private IQqchSpecialPersonControlPlanService qqchSpecialPersonControlPlanService;

    /**
     * 列表
     *
     * @param version
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchSpecialPersonControlPlan:list")
    @GetMapping("/getList")
    public AjaxResult getList(BigDecimal version) {
        QqchSpecialPersonControlPlanVo qqchSpecialPersonControlPlanVo = qqchSpecialPersonControlPlanService
            .getQqchSpecialPersonControlPlanList(version);
        return AjaxResult.success(qqchSpecialPersonControlPlanVo);
    }

    /**
     * 保存/确认/提交
     *
     * @param qqchSpecialPersonControlPlanVo
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchSpecialPersonControlPlan:add")
    @PostMapping("/batchSave")
    @CustomLogger(title = "前期策划-前期策划编制-安全策划-8.1 安全组织机构及人员策划", name = "\n" +
            "8.1.3 特种作业人员管控策划" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult batchSave(@RequestBody QqchSpecialPersonControlPlanVo qqchSpecialPersonControlPlanVo) {
        qqchSpecialPersonControlPlanService.batchSave(qqchSpecialPersonControlPlanVo);
        return AjaxResult.success();
    }
}
