package com.hhwy.sd.groupManage.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.sd.groupManage.domain.KcsjGroupManageContract;
import com.hhwy.sd.groupManage.service.IKcsjGroupManageContractService;
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
 * @date 2023-12-13 15:27:09
 * @remark 勘察设计队伍管理-合同表
 */
@Validated
@RestController
@RequestMapping("/kcsjGroupManageContract")
public class KcsjGroupManageContractController extends BaseController {

    @Autowired
    private IKcsjGroupManageContractService kcsjGroupManageContractService;


    @PreAuthorize(hasPermi = "kcsjGroupManageContract:list")
    @GetMapping
    public AjaxResult getKcsjGroupManageContract(@Validated(ValidationGroups.Get.class) KcsjGroupManageContract kcsjGroupManageContractParam) {
        KcsjGroupManageContract kcsjGroupManageContract = kcsjGroupManageContractService.getKcsjGroupManageContract(kcsjGroupManageContractParam);
        return AjaxResult.success(kcsjGroupManageContract);
    }

    @PreAuthorize(hasPermi = "kcsjGroupManageContract:list")
    @GetMapping("/list")
    public AjaxResult getKcsjGroupManageContractList(@Validated(ValidationGroups.Select.class) KcsjGroupManageContract kcsjGroupManageContractParam) {
        startPage();
        List<KcsjGroupManageContract> kcsjGroupManageContractList = kcsjGroupManageContractService.getKcsjGroupManageContractList(kcsjGroupManageContractParam);
        return getDataTableAjaxResult(kcsjGroupManageContractList);
    }

    @PreAuthorize(hasPermi = "kcsjGroupManageContract:add")
    @PostMapping("/add")
    public AjaxResult insertKcsjGroupManageContract(@Validated(ValidationGroups.Save.class) @RequestBody KcsjGroupManageContract kcsjGroupManageContractParam) {
        kcsjGroupManageContractService.insertKcsjGroupManageContract(kcsjGroupManageContractParam);
        return AjaxResult.success(kcsjGroupManageContractParam);
    }

    @PreAuthorize(hasPermi = "kcsjGroupManageContract:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertKcsjGroupManageContractList(@Validated(ValidationGroups.Save.class) @RequestBody List<KcsjGroupManageContract> kcsjGroupManageContractListParam) {
        kcsjGroupManageContractService.insertKcsjGroupManageContractList(kcsjGroupManageContractListParam);
        return AjaxResult.success(kcsjGroupManageContractListParam);
    }

    @PreAuthorize(hasPermi = "kcsjGroupManageContract:update")
    @PostMapping("/update")
    public AjaxResult updateKcsjGroupManageContract(@Validated(ValidationGroups.Update.class) @RequestBody KcsjGroupManageContract kcsjGroupManageContractParam) {
        return toAjax(kcsjGroupManageContractService.updateKcsjGroupManageContract(kcsjGroupManageContractParam));
    }

    @PreAuthorize(hasPermi = "kcsjGroupManageContract:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateKcsjGroupManageContractList(@Validated(ValidationGroups.Update.class) @RequestBody List<KcsjGroupManageContract> kcsjGroupManageContractListParam) {
        return toAjax(kcsjGroupManageContractService.updateKcsjGroupManageContractList(kcsjGroupManageContractListParam));
    }

    @PreAuthorize(hasPermi = "kcsjGroupManageContract:remove")
    @PostMapping("/delete")
    public AjaxResult deleteKcsjGroupManageContract(@Validated(ValidationGroups.Delete.class) @RequestBody KcsjGroupManageContract kcsjGroupManageContractParam) {
        return toAjax(kcsjGroupManageContractService.deleteKcsjGroupManageContract(kcsjGroupManageContractParam));
    }

    @PreAuthorize(hasPermi = "kcsjGroupManageContract:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteKcsjGroupManageContractByPks(@PathVariable Long[] ids) {
        List<Long> kcsjGroupManageContractPkList = Arrays.asList(ids);
        return toAjax(kcsjGroupManageContractService.deleteKcsjGroupManageContractByPks(kcsjGroupManageContractPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, KcsjGroupManageContract kcsjGroupManageContractParam) throws IOException {
        List<KcsjGroupManageContract> kcsjGroupManageContractList = kcsjGroupManageContractService.getKcsjGroupManageContractList(kcsjGroupManageContractParam);
        ExcelUtils<KcsjGroupManageContract> util = new ExcelUtils<>(KcsjGroupManageContract.class);
        util.exportExcel(response, kcsjGroupManageContractList, DateUtils.getDate());
    }
}
