package com.hhwy.pm.qqch.sgch.important.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.feign.service.SystemServiceApi;
import com.hhwy.pm.qqch.common.domain.CompileEntity;
import com.hhwy.pm.qqch.sgch.important.domain.QqchImportant;
import com.hhwy.pm.qqch.sgch.important.service.IQqchImportantService;
import com.hhwy.pm.qqch.sgch.sche.domain.QqchScheCorr;
import com.hhwy.system.api.domain.SysUser;
import com.hhwy.utils.ParamUtils;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
import com.hhwy.utils.excel.FtExcelUtil;
import com.hhwy.utils.validation.ValidationGroups;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author mls
 * @date 2023-08-03 11:17:04
 * @remark
 */
@Validated
@RestController
@RequestMapping("/qqchImportant")
public class QqchImportantController extends BaseController {

    @Autowired
    private IQqchImportantService qqchImportantService;


    @Autowired
    private SystemServiceApi systemServiceApi;


    //  // @PreAuthorize(hasPermi = "qqchImportant:list")
    @GetMapping
    public AjaxResult getQqchImportant(@Validated(ValidationGroups.Get.class) CompileEntity<QqchImportant> qqchImportantParam) {
        QqchImportant qqchImportant = qqchImportantService.getQqchImportant(qqchImportantParam.dealListDto());
        return AjaxResult.success(qqchImportant);
    }

    //  // @PreAuthorize(hasPermi = "qqchImportant:list")
    @GetMapping("/list")
    @CustomLogger(title = "前期策划-前期策划编制-施工策划-开工前的重要工作策划", name = "1.2.3 开工前的重要工作策划" ,businessType = CustomBusinessType.SELECT)
    public AjaxResult list(@Validated(ValidationGroups.Select.class) QqchImportant qqchImportantParam) {
        CompileEntity qqchImportantList = qqchImportantService.list(qqchImportantParam);
        return AjaxResult.success(qqchImportantList);
    }

    // @PreAuthorize(hasPermi = "qqchImportant:add")
    @PostMapping("/save")
    @CustomLogger(title = "前期策划-前期策划编制-施工策划-开工前的重要工作策划", name = "1.2.3 开工前的重要工作策划" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult save(@Validated(ValidationGroups.Save.class) @RequestBody CompileEntity<List<QqchImportant>> dto) {
        List<QqchImportant> qqchImportants = dto.dealSaveDto();
        qqchImportantService.save(qqchImportants);
        return AjaxResult.success(qqchImportants);
    }


    // @PreAuthorize(hasPermi = "qqchImportant:importData")
    @PostMapping("/importData")
    @CustomLogger(title = "前期策划-前期策划编制-施工策划-开工前的重要工作策划", name = "1.2.3 开工前的重要工作策划" ,businessType = CustomBusinessType.IMPORT)
    public AjaxResult importData(@RequestParam("file") MultipartFile file) {
        FtExcelUtil<QqchImportant> excelUtil = new FtExcelUtil<>(QqchImportant.class);
        try {
            List<QqchImportant> qqchImportants = excelUtil.importExcel(file.getInputStream());
            this.checkData(qqchImportants);
            return AjaxResult.success(qqchImportants);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    private void checkData(List<QqchImportant> list) {
        if(CollectionUtils.isEmpty(list)) {
            return ;
        }
        List<String> users = list.stream().map(QqchImportant::getDutyUserName).distinct().filter(StringUtils::isNotEmpty).collect(Collectors.toList());
        SysUser where = new SysUser();
        where.setParams(ParamUtils.init().add("nickNameList", users).get());
        List<SysUser> dbUsers = systemServiceApi.selectSysUserInfoList(where);
        if (CollectionUtils.isEmpty(dbUsers)) throw new RuntimeException("请检查导入的责任人是否在系统中存在");
        StringBuilder err = new StringBuilder();

        for (QqchImportant qqchImportant : list) {
            String dutyUserName = qqchImportant.getDutyUserName();
            // 为空 就先跳过去
            if (StringUtils.isEmpty(dutyUserName)) continue;
            // 不为空查询 并设置Id
            dbUsers.stream().filter(i -> dutyUserName.equals(i.getNickName())).findFirst().ifPresent(i -> qqchImportant.setDutyUserId(i.getUserId()));
            if (qqchImportant.getDutyUserId() == null) {
                err.append("请检查责任人【").append(dutyUserName).append("】在系统中是否存在");
            }
        }

        if (!StringUtils.isEmpty(err + "")) {
            throw new RuntimeException("" + err);
        }
    }


    @PostMapping("/exportTemp")
    public void exportTemp(HttpServletRequest request, HttpServletResponse response) {
        FtExcelUtil<QqchScheCorr> excelUtil = new FtExcelUtil<>(QqchScheCorr.class);
        try {
            excelUtil.downloadTemplate(request, response, "importImportance.xlsx");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    // @PreAuthorize(hasPermi = "qqchImportant:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchImportantList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchImportant> qqchImportantListParam) {
        qqchImportantService.insertQqchImportantList(qqchImportantListParam);
        return AjaxResult.success(qqchImportantListParam);
    }

    // @PreAuthorize(hasPermi = "qqchImportant:update")
    @PostMapping("/update")
    public AjaxResult updateQqchImportant(@Validated(ValidationGroups.Update.class) @RequestBody QqchImportant qqchImportantParam) {
        return toAjax(qqchImportantService.updateQqchImportant(qqchImportantParam));
    }

    // @PreAuthorize(hasPermi = "qqchImportant:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchImportantList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchImportant> qqchImportantListParam) {
        return toAjax(qqchImportantService.updateQqchImportantList(qqchImportantListParam));
    }

    // @PreAuthorize(hasPermi = "qqchImportant:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchImportant(@Validated(ValidationGroups.Delete.class) @RequestBody QqchImportant qqchImportantParam) {
        return toAjax(qqchImportantService.deleteQqchImportant(qqchImportantParam));
    }

    // @PreAuthorize(hasPermi = "qqchImportant:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchImportantByPks(@PathVariable Long[] ids) {
        List<Long> qqchImportantPkList = Arrays.asList(ids);
        return toAjax(qqchImportantService.deleteQqchImportantByPks(qqchImportantPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchImportant qqchImportantParam) throws IOException {
        List<QqchImportant> qqchImportantList = qqchImportantService.getQqchImportantList(qqchImportantParam);
        ExcelUtils<QqchImportant> util = new ExcelUtils<>(QqchImportant.class);
        util.exportExcel(response, qqchImportantList, DateUtils.getDate());
    }


}
