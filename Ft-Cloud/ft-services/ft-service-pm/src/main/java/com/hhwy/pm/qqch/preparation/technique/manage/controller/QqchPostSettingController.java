package com.hhwy.pm.qqch.preparation.technique.manage.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qqch.preparation.technique.manage.domain.QqchPostSetting;
import com.hhwy.pm.qqch.preparation.technique.manage.domain.vo.QqchPostSettingVo;
import com.hhwy.pm.qqch.preparation.technique.manage.service.IQqchPostSettingService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

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

    @GetMapping("/getTreeList")
    public AjaxResult getTreeList(BigDecimal version) {
        QqchPostSettingVo qqchPostSettingVo = qqchPostSettingService.getTreeList(version);
        return AjaxResult.success(qqchPostSettingVo);
    }

    @PostMapping("/batchSave")
    public AjaxResult batchSave(
        @Validated(ValidationGroups.Save.class) @RequestBody QqchPostSettingVo qqchPostSettingVo) {
        qqchPostSettingService.batchSave(qqchPostSettingVo);
        return AjaxResult.success();
    }

    /**
     * 获取项目技术管理部门及岗位设置表
     * @return
     */
    @GetMapping("getTechDeptList")
    public List<QqchPostSetting> getTechDeptList() {
        return qqchPostSettingService.getTechDeptList();
    }
}
