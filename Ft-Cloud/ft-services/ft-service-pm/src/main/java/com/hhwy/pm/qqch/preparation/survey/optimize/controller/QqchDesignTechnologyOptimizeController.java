package com.hhwy.pm.qqch.preparation.survey.optimize.controller;

import java.util.Arrays;
import java.util.List;

import com.hhwy.pm.qqch.preparation.survey.optimize.domain.vo.QqchDesignTechnologyOptimizeVo;
import com.hhwy.pm.qqch.preparation.survey.optimize.service.IQqchDesignTechnologyOptimizeService;
import org.springframework.web.bind.annotation.*;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.common.security.annotation.PreAuthorize;

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
    @PreAuthorize(hasPermi = "qqchDesignTechnologyOptimize:list")
    @GetMapping("/list")
    public AjaxResult getQqchDesignTechnologyOptimizeVo() {
        QqchDesignTechnologyOptimizeVo qqchDesignTechnologyOptimizeVo = qqchDesignTechnologyOptimizeService.getQqchDesignTechnologyOptimizeVo();
        return AjaxResult.success(qqchDesignTechnologyOptimizeVo);
    }

    /**
     * 保存
     * @param qqchDesignTechnologyOptimizeVo
     * @return
     */
    @PreAuthorize(hasPermi = "qqchDesignTechnologyOptimize:update")
    @PostMapping("/save")
    public AjaxResult save(@Validated(ValidationGroups.Update.class) @RequestBody QqchDesignTechnologyOptimizeVo qqchDesignTechnologyOptimizeVo) {
        qqchDesignTechnologyOptimizeService.save(qqchDesignTechnologyOptimizeVo);
        return AjaxResult.success(qqchDesignTechnologyOptimizeVo);
    }

    /**
     * 确认  添加已确认状态，并保存数据
     * @param qqchDesignTechnologyOptimizeVo
     * @return
     */
    @PreAuthorize(hasPermi = "qqchDesignTechnologyOptimize:update")
    @PostMapping("/confirm")
    public AjaxResult confirm(@Validated(ValidationGroups.Update.class) @RequestBody QqchDesignTechnologyOptimizeVo qqchDesignTechnologyOptimizeVo) {
        qqchDesignTechnologyOptimizeService.confirm(qqchDesignTechnologyOptimizeVo);
        return AjaxResult.success(qqchDesignTechnologyOptimizeVo);
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @PreAuthorize(hasPermi = "qqchDesignTechnologyOptimize:remove")
    @PostMapping("/remove/{ids}")
    public AjaxResult deleteQqchDesignTechnologyOptimizeByPks(@PathVariable Long[] ids) {
        List<Long> qqchDesignTechnologyOptimizePkList = Arrays.asList(ids);
        return toAjax(qqchDesignTechnologyOptimizeService.deleteQqchDesignTechnologyOptimizeByPks(qqchDesignTechnologyOptimizePkList));
    }
}
