package com.hhwy.pm.qqch.preparation.technique.manage.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.technique.manage.domain.vo.QqchPostSettingVo;
import com.hhwy.pm.qqch.preparation.technique.manage.service.IQqchPostSettingService;
import com.hhwy.utils.validation.ValidationGroups;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhenglili
 * @date 2023-07-11 15:23:27
 * @remark 3.3.2岗位设置
 */
@Validated
@RestController
@RequestMapping("/qqchPostSetting")
public class QqchPostSettingController extends BaseController {

    @Autowired
    private IQqchPostSettingService qqchPostSettingService;

    @PreAuthorize(hasPermi = "qqchPostSetting:list")
    @GetMapping("/getTechDeptList")
    public AjaxResult getTechDeptList(BigDecimal version) {
        QqchPostSettingVo qqchPostSettingVo = qqchPostSettingService.getQqchPostSettingList("1", version);
        return AjaxResult.success(qqchPostSettingVo);
    }

    @PreAuthorize(hasPermi = "qqchPostSetting:list")
    @GetMapping("/getWorkAreaList")
    public AjaxResult getWorkAreaList(BigDecimal version) {
        QqchPostSettingVo qqchPostSettingVo = qqchPostSettingService.getQqchPostSettingList("2", version);
        return AjaxResult.success(qqchPostSettingVo);
    }

    @PreAuthorize(hasPermi = "qqchPostSetting:add")
    @PostMapping("/batchSaveTechDept")
    public AjaxResult batchSaveTechDept(
        @Validated(ValidationGroups.Save.class) @RequestBody QqchPostSettingVo qqchPostSettingVo) {
        String postType = "1";
        qqchPostSettingService.batchSave(qqchPostSettingVo, postType);
        return AjaxResult.success();
    }

    @PreAuthorize(hasPermi = "qqchPostSetting:add")
    @PostMapping("/batchSaveWorkArea")
    public AjaxResult batchSaveWorkArea(
        @Validated(ValidationGroups.Save.class) @RequestBody QqchPostSettingVo qqchPostSettingVo) {
        String postType = "2";
        qqchPostSettingService.batchSave(qqchPostSettingVo, postType);
        return AjaxResult.success();
    }
}
