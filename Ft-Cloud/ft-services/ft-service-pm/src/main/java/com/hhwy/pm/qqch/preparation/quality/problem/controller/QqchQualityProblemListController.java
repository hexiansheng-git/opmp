package com.hhwy.pm.qqch.preparation.quality.problem.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qqch.preparation.quality.problem.domain.vo.QqchQualityProblemListVo;
import com.hhwy.pm.qqch.preparation.quality.problem.service.IQqchQualityProblemListService;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * @author zhenglili
 * @date 2023-08-03 14:30:34
 * @remark 9.2.1 质量通病清单
 */
@Validated
@RestController
@RequestMapping("/qqchQualityProblemList")
public class QqchQualityProblemListController extends BaseController {

    @Autowired
    private IQqchQualityProblemListService qqchQualityProblemListService;

    /**
     * 列表
     *
     * @param version
     * @return
     */
    @GetMapping("/getList")
    public AjaxResult getList(BigDecimal version) {
        QqchQualityProblemListVo qqchQualityProblemListVo = qqchQualityProblemListService
            .getQqchQualityProblemListList(version);
        return AjaxResult.success(qqchQualityProblemListVo);
    }

    /**
     * 保存/确认/提交
     *
     * @param qqchQualityProblemListVo
     * @return
     */
    @PostMapping("/batchSave")
    @CustomLogger(title = "前期策划-前期策划编制-质量策划-9.2 质量通病", name = "\n" +
            "9.2.1 质量通病清单" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult batchSave(
        @Validated(ValidationGroups.Save.class) @RequestBody QqchQualityProblemListVo qqchQualityProblemListVo) {
        qqchQualityProblemListService.batchSave(qqchQualityProblemListVo);
        return AjaxResult.success();
    }
}
