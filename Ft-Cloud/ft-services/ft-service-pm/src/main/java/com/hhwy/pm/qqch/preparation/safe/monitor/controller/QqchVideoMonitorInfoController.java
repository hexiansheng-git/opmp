package com.hhwy.pm.qqch.preparation.safe.monitor.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.safe.monitor.domain.vo.QqchVideoMonitorInfoVo;
import com.hhwy.pm.qqch.preparation.safe.monitor.service.IQqchVideoMonitorInfoService;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * @author zhenglili
 * @date 2023-08-10 16:50:39
 * @remark 8.12.1 视频监控信息
 */
@Validated
@RestController
@RequestMapping("/qqchVideoMonitorInfo")
public class QqchVideoMonitorInfoController extends BaseController {

    @Autowired
    private IQqchVideoMonitorInfoService qqchVideoMonitorInfoService;

    /**
     * 列表
     *
     * @param version
     * @return
     */
    @PreAuthorize(hasPermi = "qqchVideoMonitorInfo:list")
    @GetMapping("/getList")
    public AjaxResult getList(BigDecimal version) {
        QqchVideoMonitorInfoVo qqchVideoMonitorInfoVo = qqchVideoMonitorInfoService
            .getQqchVideoMonitorInfoList(version);
        return AjaxResult.success(qqchVideoMonitorInfoVo);
    }

    /**
     * 保存/确认/提交
     *
     * @param qqchVideoMonitorInfoVo
     * @return
     */
    @PreAuthorize(hasPermi = "qqchVideoMonitorInfo:add")
    @PostMapping("/batchSave")
    @CustomLogger(title = "前期策划-前期策划编制-安全策划-8.12 远程监控策划", name = "\n" +
            "8.12.1 视频监控信息" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult batchSave(@RequestBody QqchVideoMonitorInfoVo qqchVideoMonitorInfoVo) {
        qqchVideoMonitorInfoService.batchSave(qqchVideoMonitorInfoVo);
        return AjaxResult.success();
    }
}
