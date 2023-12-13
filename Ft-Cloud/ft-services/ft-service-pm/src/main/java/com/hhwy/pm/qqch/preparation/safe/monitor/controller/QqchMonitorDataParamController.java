package com.hhwy.pm.qqch.preparation.safe.monitor.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.safe.monitor.domain.vo.QqchMonitorDataParamVo;
import com.hhwy.pm.qqch.preparation.safe.monitor.service.IQqchMonitorDataParamService;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * @author zhenglili
 * @date 2023-08-10 16:50:46
 * @remark 8.12.2 监控数据参数
 */
@Validated
@RestController
@RequestMapping("/qqchMonitorDataParam")
public class QqchMonitorDataParamController extends BaseController {

    @Autowired
    private IQqchMonitorDataParamService qqchMonitorDataParamService;

    /**
     * 树列表
     *
     * @param version
     * @return
     */
    @PreAuthorize(hasPermi = "qqchMonitorDataParam:list")
    @GetMapping("/getTreeList")
    public AjaxResult getTreeList(BigDecimal version) {
        QqchMonitorDataParamVo qqchMonitorDataParamVo = qqchMonitorDataParamService
            .getQqchMonitorDataParamList(version);
        return AjaxResult.success(qqchMonitorDataParamVo);
    }

    /**
     * 保存/确认/提交
     *
     * @param qqchMonitorDataParamVo
     * @return
     */
    @PreAuthorize(hasPermi = "qqchMonitorDataParam:add")
    @PostMapping("/batchSave")
    @CustomLogger(title = "前期策划-前期策划编制-安全策划-8.12 远程监控策划", name = "\n" +
            "8.12.2 监控数据参数" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult batchSave(@RequestBody QqchMonitorDataParamVo qqchMonitorDataParamVo) {
        qqchMonitorDataParamService.batchSave(qqchMonitorDataParamVo);
        return AjaxResult.success();
    }
}
