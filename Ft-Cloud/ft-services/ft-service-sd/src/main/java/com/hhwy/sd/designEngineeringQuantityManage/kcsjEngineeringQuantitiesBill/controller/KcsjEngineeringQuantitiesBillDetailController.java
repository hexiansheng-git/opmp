package com.hhwy.sd.designEngineeringQuantityManage.kcsjEngineeringQuantitiesBill.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.sd.designEngineeringQuantityManage.kcsjEngineeringQuantitiesBill.domain.KcsjEngineeringQuantitiesBillDetail;
import com.hhwy.sd.designEngineeringQuantityManage.kcsjEngineeringQuantitiesBill.service.IKcsjEngineeringQuantitiesBillDetailService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wll
 * @date 2024-02-04 14:05:09
 * @remark 勘察设计-设计工程量管理-工程量清单
 */
@Validated
@RestController
@RequestMapping("/kcsjEngineeringQuantitiesBillDetail")
public class KcsjEngineeringQuantitiesBillDetailController extends BaseController {

    @Autowired
    private IKcsjEngineeringQuantitiesBillDetailService kcsjEngineeringQuantitiesBillDetailService;


    @PreAuthorize(hasPermi = "kcsjEngineeringQuantitiesBillDetail:list")
    @GetMapping
    public AjaxResult getKcsjEngineeringQuantitiesBillDetail(@Validated(ValidationGroups.Get.class) KcsjEngineeringQuantitiesBillDetail kcsjEngineeringQuantitiesBillDetailParam) {
        KcsjEngineeringQuantitiesBillDetail kcsjEngineeringQuantitiesBillDetail = kcsjEngineeringQuantitiesBillDetailService.getKcsjEngineeringQuantitiesBillDetail(kcsjEngineeringQuantitiesBillDetailParam);
        return AjaxResult.success(kcsjEngineeringQuantitiesBillDetail);
    }

    @PreAuthorize(hasPermi = "kcsjEngineeringQuantitiesBillDetail:list")
    @GetMapping("/list")
    public AjaxResult getKcsjEngineeringQuantitiesBillDetailList(@Validated(ValidationGroups.Select.class) KcsjEngineeringQuantitiesBillDetail kcsjEngineeringQuantitiesBillDetailParam) {
        startPage();
        List<KcsjEngineeringQuantitiesBillDetail> kcsjEngineeringQuantitiesBillDetailList = kcsjEngineeringQuantitiesBillDetailService.getKcsjEngineeringQuantitiesBillDetailList(kcsjEngineeringQuantitiesBillDetailParam);
        return getDataTableAjaxResult(kcsjEngineeringQuantitiesBillDetailList);
    }

    @PreAuthorize(hasPermi = "kcsjEngineeringQuantitiesBillDetail:add")
    @PostMapping("/add")
    public AjaxResult insertKcsjEngineeringQuantitiesBillDetail(@Validated(ValidationGroups.Save.class) @RequestBody KcsjEngineeringQuantitiesBillDetail kcsjEngineeringQuantitiesBillDetailParam) {
        kcsjEngineeringQuantitiesBillDetailService.insertKcsjEngineeringQuantitiesBillDetail(kcsjEngineeringQuantitiesBillDetailParam);
        return AjaxResult.success(kcsjEngineeringQuantitiesBillDetailParam);
    }

    @PreAuthorize(hasPermi = "kcsjEngineeringQuantitiesBillDetail:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertKcsjEngineeringQuantitiesBillDetailList(@Validated(ValidationGroups.Save.class) @RequestBody List<KcsjEngineeringQuantitiesBillDetail> kcsjEngineeringQuantitiesBillDetailListParam) {
        kcsjEngineeringQuantitiesBillDetailService.insertKcsjEngineeringQuantitiesBillDetailList(kcsjEngineeringQuantitiesBillDetailListParam);
        return AjaxResult.success(kcsjEngineeringQuantitiesBillDetailListParam);
    }

    @PreAuthorize(hasPermi = "kcsjEngineeringQuantitiesBillDetail:update")
    @PostMapping("/update")
    public AjaxResult updateKcsjEngineeringQuantitiesBillDetail(@Validated(ValidationGroups.Update.class) @RequestBody KcsjEngineeringQuantitiesBillDetail kcsjEngineeringQuantitiesBillDetailParam) {
        return toAjax(kcsjEngineeringQuantitiesBillDetailService.updateKcsjEngineeringQuantitiesBillDetail(kcsjEngineeringQuantitiesBillDetailParam));
    }

    @PreAuthorize(hasPermi = "kcsjEngineeringQuantitiesBillDetail:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateKcsjEngineeringQuantitiesBillDetailList(@Validated(ValidationGroups.Update.class) @RequestBody List<KcsjEngineeringQuantitiesBillDetail> kcsjEngineeringQuantitiesBillDetailListParam) {
        return toAjax(kcsjEngineeringQuantitiesBillDetailService.updateKcsjEngineeringQuantitiesBillDetailList(kcsjEngineeringQuantitiesBillDetailListParam));
    }

    @PreAuthorize(hasPermi = "kcsjEngineeringQuantitiesBillDetail:remove")
    @PostMapping("/delete")
    public AjaxResult deleteKcsjEngineeringQuantitiesBillDetail(@Validated(ValidationGroups.Delete.class) @RequestBody KcsjEngineeringQuantitiesBillDetail kcsjEngineeringQuantitiesBillDetailParam) {
        return toAjax(kcsjEngineeringQuantitiesBillDetailService.deleteKcsjEngineeringQuantitiesBillDetail(kcsjEngineeringQuantitiesBillDetailParam));
    }

    @PreAuthorize(hasPermi = "kcsjEngineeringQuantitiesBillDetail:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteKcsjEngineeringQuantitiesBillDetailByPks(@PathVariable Long[] ids) {
        List<Long> kcsjEngineeringQuantitiesBillDetailPkList = Arrays.asList(ids);
        return toAjax(kcsjEngineeringQuantitiesBillDetailService.deleteKcsjEngineeringQuantitiesBillDetailByPks(kcsjEngineeringQuantitiesBillDetailPkList));
    }

    /**
     * 导出
     *
     * @param response
     * @param list
     * @throws IOException
     */
    @PreAuthorize(hasPermi = "kcsjEngineeringQuantitiesBillDetail:export")
    @PostMapping("/export")
    public void export(HttpServletResponse response,
                       @RequestBody KcsjEngineeringQuantitiesBillDetail kcsjEngineeringQuantitiesBillDetailParam) throws IOException {
        List<KcsjEngineeringQuantitiesBillDetail> data = new ArrayList<>();
        if (kcsjEngineeringQuantitiesBillDetailParam.getDelIdList().size() > 0) {
            data = kcsjEngineeringQuantitiesBillDetailService.getDetailList(kcsjEngineeringQuantitiesBillDetailParam);
        } else {
            data = kcsjEngineeringQuantitiesBillDetailService.getKcsjEngineeringQuantitiesBillDetailListByMainId(kcsjEngineeringQuantitiesBillDetailParam);
        }
        ExcelUtils<KcsjEngineeringQuantitiesBillDetail> util = new ExcelUtils<>(KcsjEngineeringQuantitiesBillDetail.class);
        List<KcsjEngineeringQuantitiesBillDetail> collect = data.stream().distinct().collect(Collectors.toList());
        util.exportExcel(response, collect, DateUtils.getDate());
        /*List<KcsjEngineeringQuantitiesBillDetail> treeList=new ArrayList<>();
        treeList = ListTreeUtil.formatList(
                list,
                KcsjEngineeringQuantitiesBillDetail::getIsAdd,
                KcsjEngineeringQuantitiesBillDetail::getId,
                KcsjEngineeringQuantitiesBillDetail::setId,
                KcsjEngineeringQuantitiesBillDetail::setPid,
                KcsjEngineeringQuantitiesBillDetail::getChildren,
                KcsjEngineeringQuantitiesBillDetail::setChildren);
        ExcelUtils<KcsjEngineeringQuantitiesBillDetail> util = new ExcelUtils<>(KcsjEngineeringQuantitiesBillDetail.class);
        util.exportExcel(response, treeList, DateUtils.getDate());*/

    }

    /**
     * 导入
     *
     * @param file
     * @return
     */
    @PreAuthorize(hasPermi = "kcsjEngineeringQuantitiesBillDetail:import")
    @PostMapping("/importData")
    public AjaxResult importData(@RequestPart("file") MultipartFile file) {
       return kcsjEngineeringQuantitiesBillDetailService.importData(file);
    }

}
