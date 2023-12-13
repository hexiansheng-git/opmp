package com.hhwy.pm.qqch.preparation.survey.optimize.controller;

import com.hhwy.pm.qqch.preparation.survey.optimize.domain.vo.QqchDesignTechnologyOptimizeVo;
import com.hhwy.pm.qqch.preparation.survey.optimize.service.IQqchDesignTechnologyOptimizeService;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
import org.springframework.web.bind.annotation.*;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
//import com.hhwy.common.security.annotation.PreAuthorize;

import java.math.BigDecimal;

/**
 * @author han
 * @date 2023-07-07 18:35:48
 * @remark 设计技术优化要点
 */
@Validated
@RestController
@RequestMapping("/qqchDesignTechnologyOptimize")
public class QqchDesignTechnologyOptimizeController extends BaseController {

    @Autowired
    private IQqchDesignTechnologyOptimizeService qqchDesignTechnologyOptimizeService;


    /**
     * 设计技术优化要点台账
     * @return
     */
    @GetMapping("/list")
    public AjaxResult getQqchDesignTechnologyOptimizeVo(BigDecimal version) {
        QqchDesignTechnologyOptimizeVo qqchDesignTechnologyOptimizeVo = qqchDesignTechnologyOptimizeService.getQqchDesignTechnologyOptimizeVo(version);
        return AjaxResult.success(qqchDesignTechnologyOptimizeVo);
    }

    /**
     * 保存
     * @param qqchDesignTechnologyOptimizeVo
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchDesignTechnologyOptimize:save")
    @PostMapping("/save")
    @CustomLogger(title = "前期策划-前期策划编制-勘察设计策划-勘察设计优化变更策划", name = "\n" +
            "2.5.4 设计技术优化要点" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult save(@Validated(ValidationGroups.Save.class) @RequestBody QqchDesignTechnologyOptimizeVo qqchDesignTechnologyOptimizeVo) {
        qqchDesignTechnologyOptimizeService.save(qqchDesignTechnologyOptimizeVo);
        return AjaxResult.success();
    }

    /**
     * 确认  添加已确认状态，并保存数据
     * @param qqchDesignTechnologyOptimizeVo
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchDesignTechnologyOptimize:save")
    @PostMapping("/confirm")
    @CustomLogger(title = "前期策划-前期策划编制-勘察设计策划-2.5 勘察设计优化变更策划", name = "\n" +
            "2.5.4 设计技术优化要点" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult confirm(@Validated(ValidationGroups.Update.class) @RequestBody QqchDesignTechnologyOptimizeVo qqchDesignTechnologyOptimizeVo) {
        qqchDesignTechnologyOptimizeService.confirm(qqchDesignTechnologyOptimizeVo);
        return AjaxResult.success("确认成功！");
    }
}
