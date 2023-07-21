package com.hhwy.pm.qqch.preparation.technique.difficulty.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.technique.difficulty.domain.vo.QqchTechKeyDifficultAnalysisVo;
import com.hhwy.pm.qqch.preparation.technique.difficulty.service.IQqchTechKeyDifficultAnalysisService;
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
 * @date 2023-07-10 14:20:39
 * @remark 3.2施工技术重难点分析
 */
@Validated
@RestController
@RequestMapping("/qqchTechKeyDifficultAnalysis")
public class QqchTechKeyDifficultAnalysisController extends BaseController {

    @Autowired
    private IQqchTechKeyDifficultAnalysisService qqchTechKeyDifficultAnalysisService;

    /**
     * 查询列表
     *
     * @param
     * @return
     */
    @PreAuthorize(hasPermi = "qqchTechKeyDifficultAnalysis:list")
    @GetMapping("/getList")
    public AjaxResult getList(BigDecimal version) {
        QqchTechKeyDifficultAnalysisVo qqchTechKeyDifficultAnalysisList = qqchTechKeyDifficultAnalysisService
            .getQqchTechKeyDifficultAnalysisList(version);
        return AjaxResult.success(qqchTechKeyDifficultAnalysisList);
    }

    /**
     * 批量保存
     *
     * @param qqchTechKeyDifficultAnalysisVoParam
     * @return
     */
    @PreAuthorize(hasPermi = "qqchTechKeyDifficultAnalysis:add")
    @PostMapping("/batchSave")
    public AjaxResult batchSave(
        @Validated(ValidationGroups.Save.class) @RequestBody QqchTechKeyDifficultAnalysisVo qqchTechKeyDifficultAnalysisVoParam) {
        qqchTechKeyDifficultAnalysisService.batchSave(qqchTechKeyDifficultAnalysisVoParam);
        return AjaxResult.success();
    }

}
