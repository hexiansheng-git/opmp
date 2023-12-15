package com.hhwy.sd.equipEntryRecord.controller;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.hhwy.utils.tree.TreeUtil;
import java.util.Arrays;
import java.util.List;
import java.io.IOException;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import com.hhwy.sd.equipEntryRecord.service.IKcsjEquipEntryRecordInfoService;
import com.hhwy.sd.equipEntryRecord.domain.KcsjEquipEntryRecordInfo;

import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.common.security.annotation.PreAuthorize;

/**
 * @author zmh
 * @date 2023-12-14 11:08:15
 * @remark
 */
@Validated
@RestController
@RequestMapping("/kcsjEquipEntryRecordInfo")
public class KcsjEquipEntryRecordInfoController extends BaseController {

    @Autowired
    private IKcsjEquipEntryRecordInfoService kcsjEquipEntryRecordInfoService;


    @PreAuthorize(hasPermi = "kcsjEquipEntryRecordInfo:list")
    @GetMapping
    public AjaxResult getKcsjEquipEntryRecordInfo(@Validated(ValidationGroups.Get.class) KcsjEquipEntryRecordInfo kcsjEquipEntryRecordInfoParam) {
        KcsjEquipEntryRecordInfo kcsjEquipEntryRecordInfo = kcsjEquipEntryRecordInfoService.getKcsjEquipEntryRecordInfo(kcsjEquipEntryRecordInfoParam);
        return AjaxResult.success(kcsjEquipEntryRecordInfo);
    }

    /**
     * 台账页list
     * @param kcsjEquipEntryRecordInfoParam
     * @return
     */
    @PreAuthorize(hasPermi = "kcsjEquipEntryRecordInfo:list")
    @GetMapping("/list")
    public AjaxResult getKcsjEquipEntryRecordInfoList(@Validated(ValidationGroups.Select.class) KcsjEquipEntryRecordInfo kcsjEquipEntryRecordInfoParam) {
        List<KcsjEquipEntryRecordInfo> kcsjEquipEntryRecordInfoList = kcsjEquipEntryRecordInfoService.getKcsjEquipEntryRecordInfoList(kcsjEquipEntryRecordInfoParam);
        return getDataTableAjaxResult(kcsjEquipEntryRecordInfoList);
    }

    @PreAuthorize(hasPermi = "kcsjEquipEntryRecordInfo:add")
    @PostMapping("/add")
    public AjaxResult insertKcsjEquipEntryRecordInfo(@Validated(ValidationGroups.Save.class) @RequestBody KcsjEquipEntryRecordInfo kcsjEquipEntryRecordInfoParam) {kcsjEquipEntryRecordInfoService.insertKcsjEquipEntryRecordInfo(kcsjEquipEntryRecordInfoParam);
        return AjaxResult.success(kcsjEquipEntryRecordInfoParam);
    }

    @PreAuthorize(hasPermi = "kcsjEquipEntryRecordInfo:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertKcsjEquipEntryRecordInfoList(@Validated(ValidationGroups.Save.class) @RequestBody List<KcsjEquipEntryRecordInfo> kcsjEquipEntryRecordInfoListParam) {kcsjEquipEntryRecordInfoService.insertKcsjEquipEntryRecordInfoList(kcsjEquipEntryRecordInfoListParam);
        return AjaxResult.success(kcsjEquipEntryRecordInfoListParam);
    }

    @PreAuthorize(hasPermi = "kcsjEquipEntryRecordInfo:update")
    @PostMapping("/update")
    public AjaxResult updateKcsjEquipEntryRecordInfo(@Validated(ValidationGroups.Update.class) @RequestBody KcsjEquipEntryRecordInfo kcsjEquipEntryRecordInfoParam) {
        return toAjax(kcsjEquipEntryRecordInfoService.updateKcsjEquipEntryRecordInfo(kcsjEquipEntryRecordInfoParam));
    }

    @PreAuthorize(hasPermi = "kcsjEquipEntryRecordInfo:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateKcsjEquipEntryRecordInfoList(@Validated(ValidationGroups.Update.class) @RequestBody List<KcsjEquipEntryRecordInfo> kcsjEquipEntryRecordInfoListParam) {
        return toAjax(kcsjEquipEntryRecordInfoService.updateKcsjEquipEntryRecordInfoList(kcsjEquipEntryRecordInfoListParam));
    }

    @PreAuthorize(hasPermi = "kcsjEquipEntryRecordInfo:remove")
    @PostMapping("/delete")
    public AjaxResult deleteKcsjEquipEntryRecordInfo(@Validated(ValidationGroups.Delete.class) @RequestBody KcsjEquipEntryRecordInfo kcsjEquipEntryRecordInfoParam) {
        return toAjax(kcsjEquipEntryRecordInfoService.deleteKcsjEquipEntryRecordInfo(kcsjEquipEntryRecordInfoParam));
    }

    @PreAuthorize(hasPermi = "kcsjEquipEntryRecordInfo:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteKcsjEquipEntryRecordInfoByPks(@PathVariable Long[] ids) {
        List<Long> kcsjEquipEntryRecordInfoPkList = Arrays.asList(ids);
        return toAjax(kcsjEquipEntryRecordInfoService.deleteKcsjEquipEntryRecordInfoByPks(kcsjEquipEntryRecordInfoPkList));
    }

    @PostMapping("/export")
    public void export(HttpServletResponse response,@RequestBody KcsjEquipEntryRecordInfo kcsjEquipEntryRecordInfoParam) throws IOException {
        List<Long> ids = kcsjEquipEntryRecordInfoParam.getIds();
        List<KcsjEquipEntryRecordInfo> list = null;
        if(CollectionUtils.isEmpty(ids)){
            List<KcsjEquipEntryRecordInfo> kcsjEquipEntryRecordInfoList = kcsjEquipEntryRecordInfoService.getKcsjEquipEntryRecordInfoList(kcsjEquipEntryRecordInfoParam);
            if(CollectionUtils.isNotEmpty(kcsjEquipEntryRecordInfoList)){
                list = kcsjEquipEntryRecordInfoList;
            }
        }else{
            List<KcsjEquipEntryRecordInfo> byIdList = kcsjEquipEntryRecordInfoService.getIds(ids);
            if(CollectionUtils.isNotEmpty(byIdList)){
                list = byIdList;
            }
        }
        ExcelUtils<KcsjEquipEntryRecordInfo> utils = new ExcelUtils<>(KcsjEquipEntryRecordInfo.class);
        utils.exportExcel(response,list,DateUtils.getDate());
    }
}
