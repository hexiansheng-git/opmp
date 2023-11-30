package com.hhwy.pm.qyzs.safe.qyzsSafeCultureMeasureFee.controller;


import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qyzs.safe.qyzsSafeCultureMeasureFee.domain.SafeCultureMeasureFeeQueryVo;
import com.hhwy.pm.qyzs.safe.qyzsSafeCultureMeasureFee.service.IQyzsSafeCultureMeasureFeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cjh
 * @date 2023-11-21 10:46:55
 * @remark 安全知识库-安全文明措施费标准
 */
@Validated
@RestController
@RequestMapping("/qyzsSafeCultureMeasureFee")
public class QyzsSafeCultureMeasureFeeController extends BaseController {

    @Autowired
    private IQyzsSafeCultureMeasureFeeService qyzsSafeCultureMeasureFeeService;


    @GetMapping("/list")
    public AjaxResult getQyzsSafeCultureMeasureFeeList(SafeCultureMeasureFeeQueryVo queryVo) {
        return qyzsSafeCultureMeasureFeeService.getQyzsSafeCultureMeasureFeeList(queryVo);
    }
}
