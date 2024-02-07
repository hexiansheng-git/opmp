package com.hhwy.sd.designEngineeringQuantityManage.kcsjEngineeringQuantitiesBill.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.sd.designEngineeringQuantityManage.kcsjEngineeringQuantitiesBill.domain.KcsjEngineeringQuantitiesBillDetail;
import com.hhwy.sd.designEngineeringQuantityManage.kcsjEngineeringQuantitiesBill.service.IKcsjEngineeringQuantitiesBillDetailService;
import com.hhwy.utils.excel.FtExcelUtil;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author wll
 * @date 2024-02-04 14:05:09
 * @remark
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

    @PostMapping("/export")
    public void export(HttpServletResponse response, @RequestBody KcsjEngineeringQuantitiesBillDetail kcsjEngineeringQuantitiesBillDetailParam) throws IOException {
        List<KcsjEngineeringQuantitiesBillDetail> data=new ArrayList<>();
        if (kcsjEngineeringQuantitiesBillDetailParam.getDelIdList().size()>0){
             data = kcsjEngineeringQuantitiesBillDetailService.getDetailList(kcsjEngineeringQuantitiesBillDetailParam);
        }else {
            data = kcsjEngineeringQuantitiesBillDetailService.getKcsjEngineeringQuantitiesBillDetailList(kcsjEngineeringQuantitiesBillDetailParam);
        }
        ExcelUtils<KcsjEngineeringQuantitiesBillDetail> util = new ExcelUtils<>(KcsjEngineeringQuantitiesBillDetail.class);
        util.exportExcel(response, data, DateUtils.getDate());
    }

    /**
     * 导入
     * @param file
     * @return
     */
    @PreAuthorize(hasPermi = "kcsjEngineeringQuantitiesBillDetail:import")
    @PostMapping("/importData")
    public AjaxResult importData(@RequestPart("file") MultipartFile file){
        FtExcelUtil<KcsjEngineeringQuantitiesBillDetail> util = new FtExcelUtil<>(KcsjEngineeringQuantitiesBillDetail.class);
        try {
            InputStream inputStream = file.getInputStream();
            List<KcsjEngineeringQuantitiesBillDetail> recordList = util.importTreeExcel(inputStream);
            List<KcsjEngineeringQuantitiesBillDetail> total=new ArrayList<>();
            if (recordList.size()>0){
                finaTotal(recordList,total);
            }
            total.stream().forEach(o -> {
                o.setIsAdd("1");
            });
            return AjaxResult.success(recordList);
        } catch (Exception e) {
            throw new RuntimeException("导入失败！");
        }
    }


    private void finaTotal(List<KcsjEngineeringQuantitiesBillDetail> recordList,List<KcsjEngineeringQuantitiesBillDetail> total){
        for (KcsjEngineeringQuantitiesBillDetail detail : recordList) {
            recordList.add(detail);
            if (detail.getChildren().size()>0){
                finaTotal(detail.getChildren(),total);
            }
        }

    }


}
