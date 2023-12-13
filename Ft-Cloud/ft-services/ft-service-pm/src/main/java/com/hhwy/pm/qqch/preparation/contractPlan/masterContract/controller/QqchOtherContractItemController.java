package com.hhwy.pm.qqch.preparation.contractPlan.masterContract.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
//import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.contractPlan.masterContract.domain.QqchOtherContractItem;
import com.hhwy.pm.qqch.preparation.contractPlan.masterContract.domain.vo.QqchOtherContractItemVo;
import com.hhwy.pm.qqch.preparation.contractPlan.masterContract.service.IQqchOtherContractItemService;
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
 * @date 2023-08-02 11:39:36
 * @remark 其他合同事项分析
 */
@Validated
@RestController
@RequestMapping("/qqchOtherContractItem")
public class QqchOtherContractItemController extends BaseController {

    @Autowired
    private IQqchOtherContractItemService qqchOtherContractItemService;


//    @PreAuthorize(hasPermi = "qqchOtherContractItem:list")
    @GetMapping
    public AjaxResult getQqchOtherContractItem(@Validated(ValidationGroups.Get.class) QqchOtherContractItem qqchOtherContractItemParam) {
        QqchOtherContractItem qqchOtherContractItem = qqchOtherContractItemService.getQqchOtherContractItem(qqchOtherContractItemParam);
        return AjaxResult.success(qqchOtherContractItem);
    }

//    @PreAuthorize(hasPermi = "qqchOtherContractItem:list")
    @GetMapping("/list")
    public AjaxResult getQqchOtherContractItemList(@Validated(ValidationGroups.Select.class) QqchOtherContractItem qqchOtherContractItemParam) {
        startPage();
        List<QqchOtherContractItem> qqchOtherContractItemList = qqchOtherContractItemService.getQqchOtherContractItemList(qqchOtherContractItemParam);
        return getDataTableAjaxResult(qqchOtherContractItemList);
    }

//    @PreAuthorize(hasPermi = "qqchOtherContractItem:add")
    @PostMapping("/add")
    public AjaxResult insertQqchOtherContractItem(@Validated(ValidationGroups.Save.class) @RequestBody QqchOtherContractItem qqchOtherContractItemParam) {
        qqchOtherContractItemService.insertQqchOtherContractItem(qqchOtherContractItemParam);
        return AjaxResult.success(qqchOtherContractItemParam);
    }

//    @PreAuthorize(hasPermi = "qqchOtherContractItem:update")
    @PostMapping("/update")
    public AjaxResult updateQqchOtherContractItem(@Validated(ValidationGroups.Update.class) @RequestBody QqchOtherContractItem qqchOtherContractItemParam) {
        return toAjax(qqchOtherContractItemService.updateQqchOtherContractItem(qqchOtherContractItemParam));
    }

//    @PreAuthorize(hasPermi = "qqchOtherContractItem:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchOtherContractItemList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchOtherContractItem> qqchOtherContractItemListParam) {
        return toAjax(qqchOtherContractItemService.updateQqchOtherContractItemList(qqchOtherContractItemListParam));
    }

//    @PreAuthorize(hasPermi = "qqchOtherContractItem:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchOtherContractItem(@Validated(ValidationGroups.Delete.class) @RequestBody QqchOtherContractItem qqchOtherContractItemParam) {
        return toAjax(qqchOtherContractItemService.deleteQqchOtherContractItem(qqchOtherContractItemParam));
    }

//    @PreAuthorize(hasPermi = "qqchOtherContractItem:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchOtherContractItemByPks(@PathVariable Long[] ids) {
        List<Long> qqchOtherContractItemPkList = Arrays.asList(ids);
        return toAjax(qqchOtherContractItemService.deleteQqchOtherContractItemByPks(qqchOtherContractItemPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchOtherContractItem qqchOtherContractItemParam) throws IOException {
        List<QqchOtherContractItem> qqchOtherContractItemList = qqchOtherContractItemService.getQqchOtherContractItemList(qqchOtherContractItemParam);
        ExcelUtils<QqchOtherContractItem> util = new ExcelUtils<>(QqchOtherContractItem.class);
        util.exportExcel(response, qqchOtherContractItemList, DateUtils.getDate());
    }

    /**
     * 获取其他合同事项分析Vo
     * @param qqchOtherContractItem
     * @return
     */
    @GetMapping("getQqchOtherContractItemVo")
    public AjaxResult getQqchOtherContractItemVo(@Validated(ValidationGroups.Get.class) QqchOtherContractItem qqchOtherContractItem) {
        QqchOtherContractItemVo qqchOtherContractItemVo = qqchOtherContractItemService.getQqchOtherContractItemVo(qqchOtherContractItem);
        return AjaxResult.success(qqchOtherContractItemVo);
    }

    /**
     * 保存/确认/提交
     * @param qqchOtherContractItemVo
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchOtherContractItem:save")
    @PostMapping("/save")
    @CustomLogger(title = "前期策划-前期策划编制-合同策划-4.1 主合同分析", name =
            "4.1.3 其他合同事项分析", businessType = CustomBusinessType.SAVE)
    public AjaxResult save(@Validated(ValidationGroups.Save.class) @RequestBody QqchOtherContractItemVo qqchOtherContractItemVo) {
        qqchOtherContractItemService.save(qqchOtherContractItemVo);
        return AjaxResult.success();
    }
}
