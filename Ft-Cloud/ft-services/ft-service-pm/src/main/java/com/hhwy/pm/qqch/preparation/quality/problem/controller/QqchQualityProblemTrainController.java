package com.hhwy.pm.qqch.preparation.quality.problem.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.quality.problem.domain.vo.QqchQualityProblemTrainVo;
import com.hhwy.pm.qqch.preparation.quality.problem.service.IQqchQualityProblemTrainService;
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
    public AjaxResult batchSave(
        @Validated(ValidationGroups.Save.class) @RequestBody QqchQualityProblemTrainVo qqchQualityProblemTrainVo) {
        qqchQualityProblemTrainService.batchSave(qqchQualityProblemTrainVo);
        return AjaxResult.success();
    }
}
