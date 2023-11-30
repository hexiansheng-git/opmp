package com.hhwy.pm.qyzs.quality.qyzsQualityCommonProblem.controller;


import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qyzs.quality.qyzsQualityCommonProblem.domain.QualityCommonProblemQueryVo;
import com.hhwy.pm.qyzs.quality.qyzsQualityCommonProblem.service.IQyzsQualityCommonProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cjh
 * @date 2023-11-22 11:52:14
 * @remark 质量知识库-质量通病库
 */
@Validated
@RestController
@RequestMapping("/qyzsQualityCommonProblem")
public class QyzsQualityCommonProblemController extends BaseController {

    @Autowired
    private IQyzsQualityCommonProblemService qyzsQualityCommonProblemService;


    @GetMapping("/list")
    public AjaxResult getQyzsQualityCommonProblemList(QualityCommonProblemQueryVo queryVo) {
        return qyzsQualityCommonProblemService.getQyzsQualityCommonProblemList(queryVo);
    }
}
