package com.hhwy.pm.qqch.preparation.quality.problem.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qqch.preparation.quality.problem.domain.vo.QqchQualityProblemControlVo;
import com.hhwy.pm.qqch.preparation.quality.problem.service.IQqchQualityProblemControlService;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * @author zhenglili
 * @date 2023-08-03 14:30:43
 * @remark 9.2.2 质量通病控制措施
 */
@Validated
@RestController
@RequestMapping("/qqchQualityProblemControl")
public class QqchQualityProblemControlController extends BaseController {

    @Autowired
    private IQqchQualityProblemControlService qqchQualityProblemControlService;

    /**
     * 列表
     *
     * @param version
     * @return
     */
    @GetMapping("/getList")
    public AjaxResult getList(BigDecimal version) {
        QqchQualityProblemControlVo qqchQualityProblemControlVo = qqchQualityProblemControlService
            .getQqchQualityProblemControlList(version);
        return AjaxResult.success(qqchQualityProblemControlVo);
    }

    /**
     * 保存/确认/提交
     *
     * @param qqchQualityProblemControlVo
     * @return
     */
    @PostMapping("/batchSave")
    @CustomLogger(title = "前期策划-前期策划编制-质量策划-9.2 质量通病", name = "\n" +
            "9.2.2 质量通病控制措施" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult batchSave(
        @Validated(ValidationGroups.Save.class) @RequestBody QqchQualityProblemControlVo qqchQualityProblemControlVo) {
        qqchQualityProblemControlService.batchSave(qqchQualityProblemControlVo);
        return AjaxResult.success();
    }
}
