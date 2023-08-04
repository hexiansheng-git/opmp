package com.hhwy.pm.qqch.preparation.measureexp.equ.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.measureexp.equ.domain.QqchMeasureExpEqu;
import com.hhwy.pm.qqch.preparation.measureexp.equ.domain.vo.QqchMeasureExpEquVo;
import com.hhwy.pm.qqch.preparation.measureexp.equ.service.IQqchMeasureExpEquService;
import com.hhwy.utils.excel.FtExcelUtil;
import com.hhwy.utils.validation.ValidationGroups;
import java.io.IOException;
import java.math.BigDecimal;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhenglili
 * @date 2023-08-04 16:12:29
 * @remark 3.6.4测量仪器设备配置计划、3.7.4试验仪器设备配置计划
 */
@Validated
@RestController
@RequestMapping("/qqchMeasureExpEqu")
public class QqchMeasureExpEquController extends BaseController {

    @Autowired
    private IQqchMeasureExpEquService qqchMeasureExpEquService;

    /**
     * 测量仪器设备配置计划列表
     *
     * @param version
     * @return
     */
    @PreAuthorize(hasPermi = "qqchMeasureExpEqu:list")
    @GetMapping("/getMeasureList")
    public AjaxResult getMeasureList(BigDecimal version) {
        QqchMeasureExpEquVo qqchMeasureExpEquVo = qqchMeasureExpEquService.getQqchMeasureExpEquList(version, "1");
        return AjaxResult.success(qqchMeasureExpEquVo);
    }

    /**
     * 试验仪器设备配置计划列表
     *
     * @param version
     * @return
     */
    @PreAuthorize(hasPermi = "qqchMeasureExpEqu:list")
    @GetMapping("/getExperimentList")
    public AjaxResult getExperimentList(BigDecimal version) {
        QqchMeasureExpEquVo qqchMeasureExpEquVo = qqchMeasureExpEquService.getQqchMeasureExpEquList(version, "2");
        return AjaxResult.success(qqchMeasureExpEquVo);
    }

    /**
     * 测量仪器设备配置计划保存/确认/提交
     *
     * @param qqchMeasureExpEquVo
     * @return
     */
    @PreAuthorize(hasPermi = "qqchMeasureExpEqu:add")
    @PostMapping("/batchSaveMeasure")
    public AjaxResult batchSaveMeasure(
        @Validated(ValidationGroups.Save.class) @RequestBody QqchMeasureExpEquVo qqchMeasureExpEquVo) {
        qqchMeasureExpEquVo.setType("1");
        qqchMeasureExpEquService.insertQqchMeasureExpEquList(qqchMeasureExpEquVo);
        return AjaxResult.success();
    }

    /**
     * 试验仪器设备配置计划保存/确认/提交
     *
     * @param qqchMeasureExpEquVo
     * @return
     */
    @PreAuthorize(hasPermi = "qqchMeasureExpEqu:add")
    @PostMapping("/batchSaveExperiment")
    public AjaxResult batchSaveExperiment(
        @Validated(ValidationGroups.Save.class) @RequestBody QqchMeasureExpEquVo qqchMeasureExpEquVo) {
        qqchMeasureExpEquVo.setType("2");
        qqchMeasureExpEquService.insertQqchMeasureExpEquList(qqchMeasureExpEquVo);
        return AjaxResult.success();
    }

    /**
     * 导出测量仪器设备配置计划
     *
     * @param response
     * @param version
     * @throws IOException
     */
    @GetMapping("/exportMeasure")
    public void exportMeasure(HttpServletResponse response, BigDecimal version) {
        QqchMeasureExpEquVo qqchMeasureExpEquVo = qqchMeasureExpEquService.getQqchMeasureExpEquList(version, "1");
        FtExcelUtil<QqchMeasureExpEqu> util = new FtExcelUtil<>(QqchMeasureExpEqu.class);
        util.exportExcel(response, qqchMeasureExpEquVo.getMeasureList(), DateUtils.getDate());
    }

    /**
     * 导出试验仪器设备配置计划
     *
     * @param response
     * @param version
     * @throws IOException
     */
    @GetMapping("/exportExperiment")
    public void exportExperiment(HttpServletResponse response, BigDecimal version) {
        QqchMeasureExpEquVo qqchMeasureExpEquVo = qqchMeasureExpEquService.getQqchMeasureExpEquList(version, "2");
        FtExcelUtil<QqchMeasureExpEqu> util = new FtExcelUtil<>(QqchMeasureExpEqu.class);
        util.exportExcel(response, qqchMeasureExpEquVo.getExperimentList(), DateUtils.getDate());
    }
}
