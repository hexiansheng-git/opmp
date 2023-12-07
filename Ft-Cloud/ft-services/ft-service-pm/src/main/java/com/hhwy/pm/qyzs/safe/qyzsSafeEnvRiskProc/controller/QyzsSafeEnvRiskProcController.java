package com.hhwy.pm.qyzs.safe.qyzsSafeEnvRiskProc.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qyzs.safe.qyzsSafeEnvRiskProc.domain.SafeEnvRiskProcQueryVo;
import com.hhwy.pm.qyzs.safe.qyzsSafeEnvRiskProc.service.IQyzsSafeEnvRiskProcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cjh
 * @date 2023-11-17 16:25:55
 * @remark 安全知识库-环境风险源库
 */
@Validated
@RestController
@RequestMapping("/qyzsSafeEnvRiskProc")
public class QyzsSafeEnvRiskProcController extends BaseController {

    @Autowired
    private IQyzsSafeEnvRiskProcService qyzsSafeEnvRiskProcService;


    @PostMapping("/getList")
    public AjaxResult getQyzsSafeEnvRiskProcList(@RequestBody SafeEnvRiskProcQueryVo queryVo) {
        return qyzsSafeEnvRiskProcService.getQyzsSafeEnvRiskProcList(queryVo);
    }
}
