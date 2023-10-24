package com.hhwy.pm.qqch.preparation.quality.duty.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.quality.duty.domain.QqchQualityPostDuty;
import com.hhwy.pm.qqch.preparation.quality.duty.domain.vo.QqchQualityPostDutyVo;
import com.hhwy.pm.qqch.preparation.quality.duty.service.IQqchQualityPostDutyService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author zhenglili
 * @date 2023-08-03 14:28:57
 * @remark 9.1.1 质量岗位职责
 */
@Validated
@RestController
@RequestMapping("/qqchQualityPostDuty")
public class QqchQualityPostDutyController extends BaseController {

    @Autowired
    private IQqchQualityPostDutyService qqchQualityPostDutyService;

    /**
     * 列表
     *
     * @param version
     * @return
     */
    @GetMapping("/getList")
    public AjaxResult getList(BigDecimal version) {
        QqchQualityPostDutyVo qqchQualityPostDutyVo = qqchQualityPostDutyService
            .getQqchQualityPostDutyList(version);
        return AjaxResult.success(qqchQualityPostDutyVo);
    }

    /**
     * 9.1.2弹窗
     * @return
     */
    @GetMapping("/getPopWindows")
    public AjaxResult getPopWindows() throws ParserConfigurationException, IOException, SAXException {
        List<QqchQualityPostDuty> list = qqchQualityPostDutyService.getPopWindows();
        return AjaxResult.success(list);
    }

    /**
     * 保存/确认/提交
     *
     * @param qqchQualityPostDutyVo
     * @return
     */
    @PreAuthorize(hasPermi = "qqchQualityPostDuty:add")
    @PostMapping("/batchSave")
    public AjaxResult batchSave(@RequestBody QqchQualityPostDutyVo qqchQualityPostDutyVo) {
        qqchQualityPostDutyService.batchSave(qqchQualityPostDutyVo);
        return AjaxResult.success();
    }

    /**
     * 分页列表
     *
     * @return
     */
    @GetMapping("/getPageList")
    public AjaxResult getPageList() {
        // 获取最新版本
        BigDecimal version = VersionUtil.getVersion("qqch_quality_post_duty", null);

        startPage();
        List<QqchQualityPostDuty> list = qqchQualityPostDutyService.getNewVersionList(version);
        return getDataTableAjaxResult(list);
    }
}
