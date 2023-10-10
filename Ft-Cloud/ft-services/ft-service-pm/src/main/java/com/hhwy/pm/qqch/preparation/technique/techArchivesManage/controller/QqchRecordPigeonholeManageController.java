package com.hhwy.pm.qqch.preparation.technique.techArchivesManage.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
//import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.technique.techArchivesManage.domain.QqchRecordPigeonholeManage;
import com.hhwy.pm.qqch.preparation.technique.techArchivesManage.domain.vo.QqchRecordPigeonholeManageVo;
import com.hhwy.pm.qqch.preparation.technique.techArchivesManage.service.IQqchRecordPigeonholeManageService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author han
 * @date 2023-07-25 10:49:08
 * @remark 技术档案归档管理
 */
@Validated
@RestController
@RequestMapping("/qqchRecordPigeonholeManage")
public class QqchRecordPigeonholeManageController extends BaseController {

    @Autowired
    private IQqchRecordPigeonholeManageService qqchRecordPigeonholeManageService;


//    @PreAuthorize(hasPermi = "qqchRecordPigeonholeManage:list")
    @GetMapping
    public AjaxResult getQqchRecordPigeonholeManage(@Validated(ValidationGroups.Get.class) QqchRecordPigeonholeManage qqchRecordPigeonholeManageParam) {
        QqchRecordPigeonholeManage qqchRecordPigeonholeManage = qqchRecordPigeonholeManageService.getQqchRecordPigeonholeManage(qqchRecordPigeonholeManageParam);
        return AjaxResult.success(qqchRecordPigeonholeManage);
    }

//    @PreAuthorize(hasPermi = "qqchRecordPigeonholeManage:list")
    @GetMapping("/list")
    public AjaxResult getQqchRecordPigeonholeManageList(@Validated(ValidationGroups.Select.class) QqchRecordPigeonholeManage qqchRecordPigeonholeManageParam) {
        startPage();
        List<QqchRecordPigeonholeManage> qqchRecordPigeonholeManageList = qqchRecordPigeonholeManageService.getQqchRecordPigeonholeManageList(qqchRecordPigeonholeManageParam);
        return getDataTableAjaxResult(qqchRecordPigeonholeManageList);
    }

//    @PreAuthorize(hasPermi = "qqchRecordPigeonholeManage:add")
    @PostMapping("/add")
    public AjaxResult insertQqchRecordPigeonholeManage(@Validated(ValidationGroups.Save.class) @RequestBody QqchRecordPigeonholeManage qqchRecordPigeonholeManageParam) {
        qqchRecordPigeonholeManageService.insertQqchRecordPigeonholeManage(qqchRecordPigeonholeManageParam);
        return AjaxResult.success(qqchRecordPigeonholeManageParam);
    }

//    @PreAuthorize(hasPermi = "qqchRecordPigeonholeManage:update")
    @PostMapping("/update")
    public AjaxResult updateQqchRecordPigeonholeManage(@Validated(ValidationGroups.Update.class) @RequestBody QqchRecordPigeonholeManage qqchRecordPigeonholeManageParam) {
        return toAjax(qqchRecordPigeonholeManageService.updateQqchRecordPigeonholeManage(qqchRecordPigeonholeManageParam));
    }

//    @PreAuthorize(hasPermi = "qqchRecordPigeonholeManage:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchRecordPigeonholeManageList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchRecordPigeonholeManage> qqchRecordPigeonholeManageListParam) {
        return toAjax(qqchRecordPigeonholeManageService.updateQqchRecordPigeonholeManageList(qqchRecordPigeonholeManageListParam));
    }

//    @PreAuthorize(hasPermi = "qqchRecordPigeonholeManage:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchRecordPigeonholeManage(@Validated(ValidationGroups.Delete.class) @RequestBody QqchRecordPigeonholeManage qqchRecordPigeonholeManageParam) {
        return toAjax(qqchRecordPigeonholeManageService.deleteQqchRecordPigeonholeManage(qqchRecordPigeonholeManageParam));
    }

//    @PreAuthorize(hasPermi = "qqchRecordPigeonholeManage:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchRecordPigeonholeManageByPks(@PathVariable Long[] ids) {
        List<Long> qqchRecordPigeonholeManagePkList = Arrays.asList(ids);
        return toAjax(qqchRecordPigeonholeManageService.deleteQqchRecordPigeonholeManageByPks(qqchRecordPigeonholeManagePkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchRecordPigeonholeManage qqchRecordPigeonholeManageParam) throws IOException {
        List<QqchRecordPigeonholeManage> qqchRecordPigeonholeManageList = qqchRecordPigeonholeManageService.getQqchRecordPigeonholeManageList(qqchRecordPigeonholeManageParam);
        ExcelUtils<QqchRecordPigeonholeManage> util = new ExcelUtils<>(QqchRecordPigeonholeManage.class);
        util.exportExcel(response, qqchRecordPigeonholeManageList, DateUtils.getDate());
    }

    /**
     * 获取技术档案归档管理Vo
     * @param qqchRecordPigeonholeManage
     * @return
     */
    @GetMapping("getQqchRecordPigeonholeManageVo")
    public AjaxResult getQqchRecordPigeonholeManageVo(@Validated(ValidationGroups.Select.class) QqchRecordPigeonholeManage qqchRecordPigeonholeManage) {
        QqchRecordPigeonholeManageVo qqchRecordPigeonholeManageVo = qqchRecordPigeonholeManageService.getQqchRecordPigeonholeManageVo(qqchRecordPigeonholeManage);
        return AjaxResult.success(qqchRecordPigeonholeManageVo);
    }

    /**
     * 保存/确认/提交
     * @param qqchRecordPigeonholeManageVo
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchRecordPigeonholeManage:save")
    @PostMapping("/save")
    public AjaxResult save(@Validated(ValidationGroups.Save.class) @RequestBody QqchRecordPigeonholeManageVo qqchRecordPigeonholeManageVo) {
        qqchRecordPigeonholeManageService.save(qqchRecordPigeonholeManageVo);
        return AjaxResult.success();
    }
}
