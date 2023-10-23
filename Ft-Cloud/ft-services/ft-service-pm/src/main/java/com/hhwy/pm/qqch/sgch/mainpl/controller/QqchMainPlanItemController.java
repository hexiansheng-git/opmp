package com.hhwy.pm.qqch.sgch.mainpl.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.sgch.mainpl.domain.QqchMainPlanItem;
import com.hhwy.pm.qqch.sgch.mainpl.domain.vo.QqchMainPlanItemVo;
import com.hhwy.pm.qqch.sgch.mainpl.service.IQqchMainPlanItemService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author 陈锦豪
 * @date 2023-08-29 15:12:27
 * @remark
 */
@Validated
@RestController
@RequestMapping("/qqchMainPlanItem")
public class QqchMainPlanItemController extends BaseController {

    @Autowired
    private IQqchMainPlanItemService qqchMainPlanItemService;


    //  // @PreAuthorize(hasPermi = "qqchMainPlanItem:list")
    @GetMapping
    public AjaxResult getQqchMainPlanItem(@Validated(ValidationGroups.Get.class) QqchMainPlanItem qqchMainPlanItemParam) {
        QqchMainPlanItem qqchMainPlanItem = qqchMainPlanItemService.getQqchMainPlanItem(qqchMainPlanItemParam);
        return AjaxResult.success(qqchMainPlanItem);
    }

//    //  // @PreAuthorize(hasPermi = "qqchMainPlanItem:list")
//    @GetMapping("/getUsing4One")
//    public AjaxResult getUsing4One(@Validated(ValidationGroups.Get.class) QqchMainPlanItem qqchMainPlanItemParam) {
//        QqchMainPlanItem qqchMainPlanItem = qqchMainPlanItemService.getUsing4One(qqchMainPlanItemParam);
//        return AjaxResult.success(qqchMainPlanItem);
//    }

    /**
     * 列表数据查询
     * @param qqchMainPlanItemParam
     * @return
     */
    //  // @PreAuthorize(hasPermi = "qqchMainPlanItem:list")
    @GetMapping("/list")
    public AjaxResult getQqchMainPlanItemList(@Validated(ValidationGroups.Select.class) QqchMainPlanItem qqchMainPlanItemParam) {
        List<QqchMainPlanItem> qqchMainPlanItemList = qqchMainPlanItemService.getQqchMainPlanItemListNoTree(qqchMainPlanItemParam);
        return getDataTableAjaxResult(qqchMainPlanItemList);
    }

    /**
     * 列表数据查询
     * @param qqchMainPlanItemParam
     * @return
     */
    //  // @PreAuthorize(hasPermi = "qqchMainPlanItem:list")
    @GetMapping("/list4page")
    public AjaxResult getQqchMainPlanItemList4page(@Validated(ValidationGroups.Select.class) QqchMainPlanItem qqchMainPlanItemParam) {
        BigDecimal version = VersionUtil.getVersion(QqchMainPlanItem.TABLE_NAME, qqchMainPlanItemParam.getVersion());
        qqchMainPlanItemParam.setVersion(version);
        startPage();
        List<QqchMainPlanItem> qqchMainPlanItemList = qqchMainPlanItemService.getQqchMainPlanItemList(qqchMainPlanItemParam);
        return getDataTableAjaxResult(qqchMainPlanItemList);
    }

    /**
     * 懒加载
     * @param qqchMainPlanItemParam
     * @return
     */
    //  // @PreAuthorize(hasPermi = "qqchMainPlanItem:list")
    @GetMapping("/treelist")
    public AjaxResult getQqchMainPlanItemTreelist(@Validated(ValidationGroups.Select.class) QqchMainPlanItem qqchMainPlanItemParam) {
        List<QqchMainPlanItem> qqchMainPlanItemList = qqchMainPlanItemService.getQqchMainPlanItemList4Lazy(qqchMainPlanItemParam);
        return getDataTableAjaxResult(qqchMainPlanItemList);
    }



    //  // @PreAuthorize(hasPermi = "qqchMainPlanItem:list")
    @GetMapping("/getKeyRoad")
    public AjaxResult getKeyRoad(@Validated(ValidationGroups.Select.class) QqchMainPlanItem qqchMainPlanItemParam) {
        List<QqchMainPlanItem> qqchMainPlanItemList = qqchMainPlanItemService.getKeyRoad(qqchMainPlanItemParam);
        return getDataTableAjaxResult(qqchMainPlanItemList);
    }



    // @PreAuthorize(hasPermi = "qqchMainPlanItem:add")
    @PostMapping("/add")
    public AjaxResult insertQqchMainPlanItem(@Validated(ValidationGroups.Save.class) @RequestBody QqchMainPlanItem qqchMainPlanItemParam) {
        qqchMainPlanItemService.insertQqchMainPlanItem(qqchMainPlanItemParam);
        return AjaxResult.success(qqchMainPlanItemParam);
    }

    // @PreAuthorize(hasPermi = "qqchMainPlanItem:confirm")
    @PostMapping("/confirm")
    public AjaxResult confirm(@Validated(ValidationGroups.Save.class) @RequestBody QqchMainPlanItemVo qqchMainPlanItemVoParam) {
        qqchMainPlanItemService.confirm(qqchMainPlanItemVoParam);
        return AjaxResult.success(qqchMainPlanItemVoParam);
    }

    // @PreAuthorize(hasPermi = "qqchMainPlanItem:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchMainPlanItemList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchMainPlanItem> qqchMainPlanItemListParam) {
        qqchMainPlanItemService.insertQqchMainPlanItemList(qqchMainPlanItemListParam);
        return AjaxResult.success(qqchMainPlanItemListParam);
    }

    // @PreAuthorize(hasPermi = "qqchMainPlanItem:update")
    @PostMapping("/update")
    public AjaxResult updateQqchMainPlanItem(@Validated(ValidationGroups.Update.class) @RequestBody QqchMainPlanItem qqchMainPlanItemParam) {
        return toAjax(qqchMainPlanItemService.updateQqchMainPlanItem(qqchMainPlanItemParam));
    }

    // @PreAuthorize(hasPermi = "qqchMainPlanItem:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchMainPlanItemList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchMainPlanItem> qqchMainPlanItemListParam) {
        return toAjax(qqchMainPlanItemService.updateQqchMainPlanItemList(qqchMainPlanItemListParam));
    }

    // @PreAuthorize(hasPermi = "qqchMainPlanItem:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchMainPlanItem(@Validated(ValidationGroups.Delete.class) @RequestBody QqchMainPlanItem qqchMainPlanItemParam) {
        return toAjax(qqchMainPlanItemService.deleteQqchMainPlanItem(qqchMainPlanItemParam));
    }

    // @PreAuthorize(hasPermi = "qqchMainPlanItem:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchMainPlanItemByPks(@PathVariable Long[] ids) {
        List<Long> qqchMainPlanItemPkList = Arrays.asList(ids);
        return toAjax(qqchMainPlanItemService.deleteQqchMainPlanItemByPks(qqchMainPlanItemPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchMainPlanItem qqchMainPlanItemParam) throws IOException {
        BigDecimal version = VersionUtil.getVersion(QqchMainPlanItem.TABLE_NAME, qqchMainPlanItemParam.getVersion());
        qqchMainPlanItemParam.setVersion(version);
        List<QqchMainPlanItem> qqchMainPlanItemList = qqchMainPlanItemService.getQqchMainPlanItemList(qqchMainPlanItemParam);
        ExcelUtils<QqchMainPlanItem> util = new ExcelUtils<>(QqchMainPlanItem.class);
        util.exportExcel(response, qqchMainPlanItemList, DateUtils.getDate());
    }

    /**
     * 获取项目开始与结束
     * @return
     */
    @GetMapping("/getProjStartAndFinish")
    public AjaxResult getProjStartAndFinish(BigDecimal version) {
        return AjaxResult.success(qqchMainPlanItemService.getProjStartAndFinish(version));
    }

}
