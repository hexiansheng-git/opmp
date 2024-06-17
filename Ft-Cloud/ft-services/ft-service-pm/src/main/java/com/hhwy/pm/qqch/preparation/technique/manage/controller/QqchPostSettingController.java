package com.hhwy.pm.qqch.preparation.technique.manage.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qqch.preparation.technique.manage.domain.QqchPostSetting;
import com.hhwy.pm.qqch.preparation.technique.manage.domain.vo.QqchPostSettingVo;
import com.hhwy.pm.qqch.preparation.technique.manage.service.IQqchPostSettingService;
import com.hhwy.pm.xmsl.project.domain.XmslProjectBasicInfo;
import com.hhwy.pm.xmsl.project.domain.vo.ProjectBasicInfo;
import com.hhwy.pm.xmsl.project.service.IXmslProjectBasicInfoService;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
import com.hhwy.utils.validation.ValidationGroups;
import org.ehcache.shadow.org.terracotta.offheapstore.storage.listener.ListenableStorageEngine;
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
    @Autowired
    private IXmslProjectBasicInfoService projectBasicInfoService;

    @GetMapping("/getTreeList")
    public AjaxResult getTreeList(BigDecimal version) {
        QqchPostSettingVo qqchPostSettingVo = qqchPostSettingService.getTreeList(version);
        return AjaxResult.success(qqchPostSettingVo);
    }

    @PostMapping("/batchSave")
    @CustomLogger(title = "前期策划-前期策划编制-施工技术策划-3.3 技术管理模式", name = "\n" +
            "3.3.2 岗位设置" ,businessType = CustomBusinessType.SAVE)
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
        ProjectBasicInfo projectInfo = projectBasicInfoService.projectInfo();
        List<QqchPostSetting> list = qqchPostSettingService.getTechDeptList();
        for (int i = 0; i < list.size(); i++) {
            QqchPostSetting temp = list.get(i);
            temp.setProjectId(projectInfo.getProjectId());
            temp.setProjectName(projectInfo.getProjectName());
        }
        return list;
    }
}
