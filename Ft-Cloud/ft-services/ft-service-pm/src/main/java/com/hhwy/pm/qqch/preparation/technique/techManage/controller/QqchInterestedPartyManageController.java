package com.hhwy.pm.qqch.preparation.technique.techManage.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
//import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.technique.techManage.domain.QqchInterestedPartyManage;
import com.hhwy.pm.qqch.preparation.technique.techManage.domain.vo.QqchInterestedPartyManageVo;
import com.hhwy.pm.qqch.preparation.technique.techManage.service.IQqchInterestedPartyManageService;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
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
 * @date 2023-07-25 10:53:45
 * @remark 技术管理相关方管理
 */
@Validated
@RestController
@RequestMapping("/qqchInterestedPartyManage")
public class QqchInterestedPartyManageController extends BaseController {

    @Autowired
    private IQqchInterestedPartyManageService qqchInterestedPartyManageService;


//    @PreAuthorize(hasPermi = "qqchInterestedPartyManage:list")
    @GetMapping
    public AjaxResult getQqchInterestedPartyManage(@Validated(ValidationGroups.Get.class) QqchInterestedPartyManage qqchInterestedPartyManageParam) {
        QqchInterestedPartyManage qqchInterestedPartyManage = qqchInterestedPartyManageService.getQqchInterestedPartyManage(qqchInterestedPartyManageParam);
        return AjaxResult.success(qqchInterestedPartyManage);
    }

//    @PreAuthorize(hasPermi = "qqchInterestedPartyManage:list")
    @GetMapping("/list")
    public AjaxResult getQqchInterestedPartyManageList(@Validated(ValidationGroups.Select.class) QqchInterestedPartyManage qqchInterestedPartyManageParam) {
        startPage();
        List<QqchInterestedPartyManage> qqchInterestedPartyManageList = qqchInterestedPartyManageService.getQqchInterestedPartyManageList(qqchInterestedPartyManageParam);
        return getDataTableAjaxResult(qqchInterestedPartyManageList);
    }

//    @PreAuthorize(hasPermi = "qqchInterestedPartyManage:add")
    @PostMapping("/add")
    public AjaxResult insertQqchInterestedPartyManage(@Validated(ValidationGroups.Save.class) @RequestBody QqchInterestedPartyManage qqchInterestedPartyManageParam) {
        qqchInterestedPartyManageService.insertQqchInterestedPartyManage(qqchInterestedPartyManageParam);
        return AjaxResult.success(qqchInterestedPartyManageParam);
    }

//    @PreAuthorize(hasPermi = "qqchInterestedPartyManage:update")
    @PostMapping("/update")
    public AjaxResult updateQqchInterestedPartyManage(@Validated(ValidationGroups.Update.class) @RequestBody QqchInterestedPartyManage qqchInterestedPartyManageParam) {
        return toAjax(qqchInterestedPartyManageService.updateQqchInterestedPartyManage(qqchInterestedPartyManageParam));
    }

//    @PreAuthorize(hasPermi = "qqchInterestedPartyManage:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchInterestedPartyManageList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchInterestedPartyManage> qqchInterestedPartyManageListParam) {
        return toAjax(qqchInterestedPartyManageService.updateQqchInterestedPartyManageList(qqchInterestedPartyManageListParam));
    }

//    @PreAuthorize(hasPermi = "qqchInterestedPartyManage:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchInterestedPartyManage(@Validated(ValidationGroups.Delete.class) @RequestBody QqchInterestedPartyManage qqchInterestedPartyManageParam) {
        return toAjax(qqchInterestedPartyManageService.deleteQqchInterestedPartyManage(qqchInterestedPartyManageParam));
    }

//    @PreAuthorize(hasPermi = "qqchInterestedPartyManage:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchInterestedPartyManageByPks(@PathVariable Long[] ids) {
        List<Long> qqchInterestedPartyManagePkList = Arrays.asList(ids);
        return toAjax(qqchInterestedPartyManageService.deleteQqchInterestedPartyManageByPks(qqchInterestedPartyManagePkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchInterestedPartyManage qqchInterestedPartyManageParam) throws IOException {
        List<QqchInterestedPartyManage> qqchInterestedPartyManageList = qqchInterestedPartyManageService.getQqchInterestedPartyManageList(qqchInterestedPartyManageParam);
        ExcelUtils<QqchInterestedPartyManage> util = new ExcelUtils<>(QqchInterestedPartyManage.class);
        util.exportExcel(response, qqchInterestedPartyManageList, DateUtils.getDate());
    }

    /**
     * 获取技术管理相关方管理Vo
     * @param qqchInterestedPartyManage
     * @return
     */
    @GetMapping("getQqchInterestedPartyManageVo")
    public AjaxResult getQqchInterestedPartyManageVo(@Validated(ValidationGroups.Select.class) QqchInterestedPartyManage qqchInterestedPartyManage) {
        QqchInterestedPartyManageVo qqchInterestedPartyManageVo = qqchInterestedPartyManageService.getQqchInterestedPartyManageVo(qqchInterestedPartyManage);
        return AjaxResult.success(qqchInterestedPartyManageVo);
    }

    /**
     * 保存/确认/提交
     * @param qqchInterestedPartyManageVo
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchInterestedPartyManage:save")
    @PostMapping("/save")
    @CustomLogger(title = "前期策划-前期策划编制-施工技术策划-3.12 技术管理相关方管理及沟通管理", name = "\n" +
            "3.12.1 技术管理相关方管理" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult save(@Validated(ValidationGroups.Save.class) @RequestBody QqchInterestedPartyManageVo qqchInterestedPartyManageVo) {
        qqchInterestedPartyManageService.save(qqchInterestedPartyManageVo);
        return AjaxResult.success();
    }
}
