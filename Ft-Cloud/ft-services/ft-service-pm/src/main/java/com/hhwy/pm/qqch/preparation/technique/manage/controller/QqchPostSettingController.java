package com.hhwy.pm.qqch.preparation.technique.manage.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.technique.manage.domain.QqchPostSetting;
import com.hhwy.pm.qqch.preparation.technique.manage.service.IQqchPostSettingService;
import com.hhwy.utils.tree.TreeVO;
import com.hhwy.utils.validation.ValidationGroups;
import java.util.Arrays;
import java.util.List;
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
    public AjaxResult getTechDeptList(
        @Validated(ValidationGroups.Select.class) @RequestBody QqchPostSetting qqchPostSettingParam) {
        qqchPostSettingParam.setPostType("1");
        List<? extends TreeVO> treeList = qqchPostSettingService.getQqchPostSettingList(qqchPostSettingParam);
        return AjaxResult.success(treeList);
    }

    @PreAuthorize(hasPermi = "qqchPostSetting:list")
    @GetMapping("/getWorkAreaList")
    public AjaxResult getWorkAreaList(
        @Validated(ValidationGroups.Select.class) @RequestBody QqchPostSetting qqchPostSettingParam) {
        qqchPostSettingParam.setPostType("2");
        List<? extends TreeVO> treeList = qqchPostSettingService.getQqchPostSettingList(qqchPostSettingParam);
        return AjaxResult.success(treeList);
    }

    @PreAuthorize(hasPermi = "qqchPostSetting:add")
    @PostMapping("/batchSaveTechDept")
    public AjaxResult batchSaveTechDept(
        @Validated(ValidationGroups.Save.class) @RequestBody List<QqchPostSetting> qqchPostSettingListParam) {
        String postType = "1";
        qqchPostSettingService.batchSave(qqchPostSettingListParam, postType);
        return AjaxResult.success(qqchPostSettingListParam);
    }

    @PreAuthorize(hasPermi = "qqchPostSetting:add")
    @PostMapping("/batchSaveWorkArea")
    public AjaxResult batchSaveWorkArea(
        @Validated(ValidationGroups.Save.class) @RequestBody List<QqchPostSetting> qqchPostSettingListParam) {
        String postType = "2";
        qqchPostSettingService.batchSave(qqchPostSettingListParam, postType);
        return AjaxResult.success(qqchPostSettingListParam);
    }

    @PreAuthorize(hasPermi = "qqchPostSetting:remove")
    @PostMapping("/remove")
    public AjaxResult deleteQqchPostSettingByPks(Long[] ids) {
        List<Long> qqchPostSettingPkList = Arrays.asList(ids);
        return toAjax(qqchPostSettingService.deleteQqchPostSettingByPks(qqchPostSettingPkList));
    }
}
