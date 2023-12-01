package com.hhwy.pm.qyzs.safe.qyzsSafeRiskBigProj.controller;


import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qyzs.safe.qyzsSafeRiskBigProj.domain.SafeRiskBigProjQueryVo;
import com.hhwy.pm.qyzs.safe.qyzsSafeRiskBigProj.service.IQyzsSafeRiskBigProjService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cjh
 * @date 2023-11-20 15:21:23
 * @remark 安全知识库-危大工程清单
 */
@Validated
@RestController
@RequestMapping("/qyzsSafeRiskBigProj")
public class QyzsSafeRiskBigProjController extends BaseController {

    @Autowired
    private IQyzsSafeRiskBigProjService qyzsSafeRiskBigProjService;


    @GetMapping("/getList")
    public AjaxResult getList(SafeRiskBigProjQueryVo queryVo) {
        return qyzsSafeRiskBigProjService.getQyzsSafeRiskBigProjList(queryVo);
    }

    /**
     * 获取危大工程清单危大工程类型列表
     * @return
     */
    @GetMapping("getRiskProjTypeList")
    public AjaxResult getRiskProjTypeList() {
        return AjaxResult.success(qyzsSafeRiskBigProjService.getRiskProjTypeList());
    }
}
