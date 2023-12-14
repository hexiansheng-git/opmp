package com.hhwy.pm.qqch.preparation.quality.problem.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qqch.preparation.quality.problem.domain.vo.QqchQualityProblemTrainVo;
import com.hhwy.pm.qqch.preparation.quality.problem.service.IQqchQualityProblemTrainService;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * @author zhenglili
 * @date 2023-08-03 14:30:51
 * @remark 9.2.3 质量通病培训策划
 */
@Validated
@RestController
@RequestMapping("/qqchQualityProblemTrain")
public class QqchQualityProblemTrainController extends BaseController {

    @Autowired
    private IQqchQualityProblemTrainService qqchQualityProblemTrainService;

    /**
     * 列表
     *
     * @param version
     * @return
     */
    @GetMapping("/getList")
    public AjaxResult getList(BigDecimal version) {
        QqchQualityProblemTrainVo qqchQualityProblemTrainVo = qqchQualityProblemTrainService
            .getQqchQualityProblemTrainList(version);
        return AjaxResult.success(qqchQualityProblemTrainVo);
    }

    /**
     * 保存/确认/提交
     *
     * @param qqchQualityProblemTrainVo
     * @return
     */
    @PostMapping("/batchSave")
    @CustomLogger(title = "前期策划-前期策划编制-质量策划-9.2 质量通病", name = "\n" +
            "9.2.3 质量通病培训策划" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult batchSave(
        @Validated(ValidationGroups.Save.class) @RequestBody QqchQualityProblemTrainVo qqchQualityProblemTrainVo) {
        qqchQualityProblemTrainService.batchSave(qqchQualityProblemTrainVo);
        return AjaxResult.success();
    }
}
