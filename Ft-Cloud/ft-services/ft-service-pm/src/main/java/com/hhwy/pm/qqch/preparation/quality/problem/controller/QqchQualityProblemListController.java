package com.hhwy.pm.qqch.preparation.quality.problem.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.quality.problem.domain.vo.QqchQualityProblemListVo;
import com.hhwy.pm.qqch.preparation.quality.problem.service.IQqchQualityProblemListService;
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
    @PreAuthorize(hasPermi = "qqchQualityProblemList:list")
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
    @PreAuthorize(hasPermi = "qqchQualityProblemList:add")
    @PostMapping("/batchSave")
    public AjaxResult batchSave(
        @Validated(ValidationGroups.Save.class) @RequestBody QqchQualityProblemListVo qqchQualityProblemListVo) {
        qqchQualityProblemListService.batchSave(qqchQualityProblemListVo);
        return AjaxResult.success();
    }
}
