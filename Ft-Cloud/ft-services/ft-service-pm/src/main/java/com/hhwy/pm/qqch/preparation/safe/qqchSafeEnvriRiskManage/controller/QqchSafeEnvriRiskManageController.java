package com.hhwy.pm.qqch.preparation.safe.qqchSafeEnvriRiskManage.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
//import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.safe.qqchSafeEnvriRiskManage.domain.QqchSafeEnvriRiskManage;
import com.hhwy.pm.qqch.preparation.safe.qqchSafeEnvriRiskManage.service.IQqchSafeEnvriRiskManageService;
import com.hhwy.pm.qqch.preparation.safe.qqchSafeEnvriRiskManage.vo.QqchSafeEnvriRiskManageVo;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * @author zq
 * @date 2023-08-14 14:04:07
 * @remark 8.8.3 环境风险过程管控措施
 */
@Validated
@RestController
@RequestMapping("/qqchSafeEnvriRiskManage")
public class QqchSafeEnvriRiskManageController extends BaseController {

    @Autowired
    private IQqchSafeEnvriRiskManageService qqchSafeEnvriRiskManageService;


//    @PreAuthorize(hasPermi = "qqchSafeEnvriRiskManage:list")
    @GetMapping
    public AjaxResult getQqchSafeEnvriRiskManage(@Validated(ValidationGroups.Get.class) QqchSafeEnvriRiskManage qqchSafeEnvriRiskManageParam) {
        QqchSafeEnvriRiskManage qqchSafeEnvriRiskManage = qqchSafeEnvriRiskManageService.getQqchSafeEnvriRiskManage(qqchSafeEnvriRiskManageParam);
        return AjaxResult.success(qqchSafeEnvriRiskManage);
    }

//    @PreAuthorize(hasPermi = "qqchSafeEnvriRiskManage:list")
    @GetMapping("/list")
    public AjaxResult getQqchSafeEnvriRiskManageList(@Validated(ValidationGroups.Select.class) QqchSafeEnvriRiskManage qqchSafeEnvriRiskManageParam) {
        startPage();
        List<QqchSafeEnvriRiskManage> qqchSafeEnvriRiskManageList = qqchSafeEnvriRiskManageService.getQqchSafeEnvriRiskManageList(qqchSafeEnvriRiskManageParam);
        return getDataTableAjaxResult(qqchSafeEnvriRiskManageList);
    }

//    @PreAuthorize(hasPermi = "qqchSafeEnvriRiskManage:add")
    @PostMapping("/add")
    public AjaxResult insertQqchSafeEnvriRiskManage(@Validated(ValidationGroups.Save.class) @RequestBody QqchSafeEnvriRiskManage qqchSafeEnvriRiskManageParam) {
        qqchSafeEnvriRiskManageService.insertQqchSafeEnvriRiskManage(qqchSafeEnvriRiskManageParam);
        return AjaxResult.success(qqchSafeEnvriRiskManageParam);
    }

//    @PreAuthorize(hasPermi = "qqchSafeEnvriRiskManage:add")
    @PostMapping("/batchAdd")
    @CustomLogger(title = "前期策划-前期策划编制-安全策划-8.8 环境风险管控策划", name = "\n" +
            "8.8.3 环境风险过程管控措施" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult insertQqchSafeEnvriRiskManageList(@Validated(ValidationGroups.Save.class) @RequestBody QqchSafeEnvriRiskManageVo qqchSafeEnvriRiskManageVo) {
        qqchSafeEnvriRiskManageService.insertQqchSafeEnvriRiskManageList(qqchSafeEnvriRiskManageVo);
        return AjaxResult.success();
    }

//    @PreAuthorize(hasPermi = "qqchSafeEnvriRiskManage:update")
    @PostMapping("/update")
    public AjaxResult updateQqchSafeEnvriRiskManage(@Validated(ValidationGroups.Update.class) @RequestBody QqchSafeEnvriRiskManage qqchSafeEnvriRiskManageParam) {
        return toAjax(qqchSafeEnvriRiskManageService.updateQqchSafeEnvriRiskManage(qqchSafeEnvriRiskManageParam));
    }

//    @PreAuthorize(hasPermi = "qqchSafeEnvriRiskManage:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchSafeEnvriRiskManageList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchSafeEnvriRiskManage> qqchSafeEnvriRiskManageListParam) {
        return toAjax(qqchSafeEnvriRiskManageService.updateQqchSafeEnvriRiskManageList(qqchSafeEnvriRiskManageListParam));
    }

//    @PreAuthorize(hasPermi = "qqchSafeEnvriRiskManage:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchSafeEnvriRiskManage(@Validated(ValidationGroups.Delete.class) @RequestBody QqchSafeEnvriRiskManage qqchSafeEnvriRiskManageParam) {
        return toAjax(qqchSafeEnvriRiskManageService.deleteQqchSafeEnvriRiskManage(qqchSafeEnvriRiskManageParam));
    }

//    @PreAuthorize(hasPermi = "qqchSafeEnvriRiskManage:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchSafeEnvriRiskManageByPks(@PathVariable Long[] ids) {
        List<Long> qqchSafeEnvriRiskManagePkList = Arrays.asList(ids);
        return toAjax(qqchSafeEnvriRiskManageService.deleteQqchSafeEnvriRiskManageByPks(qqchSafeEnvriRiskManagePkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchSafeEnvriRiskManage qqchSafeEnvriRiskManageParam) throws IOException {
        List<QqchSafeEnvriRiskManage> qqchSafeEnvriRiskManageList = qqchSafeEnvriRiskManageService.getQqchSafeEnvriRiskManageList(qqchSafeEnvriRiskManageParam);
        ExcelUtils<QqchSafeEnvriRiskManage> util = new ExcelUtils<>(QqchSafeEnvriRiskManage.class);
        util.exportExcel(response, qqchSafeEnvriRiskManageList, DateUtils.getDate());
    }

//    @PreAuthorize(hasAnyPermi = "qqchSafeEnvriRiskManage:list")
    @GetMapping("/getList")
    public AjaxResult getList(BigDecimal version) {
        QqchSafeEnvriRiskManageVo qqchSafeEnvriRiskManageVo = qqchSafeEnvriRiskManageService.getList(version);
        return AjaxResult.success(qqchSafeEnvriRiskManageVo);
    }

}
