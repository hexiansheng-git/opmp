package com.hhwy.sd.equipEntryRecord.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.sd.equipEntryRecord.domain.KcsjEquipEntryRecord;
import com.hhwy.sd.equipEntryRecord.domain.KcsjEquipEntryRecordVo;
import com.hhwy.sd.equipEntryRecord.domain.SyncWusheEquipVo;
import com.hhwy.sd.equipEntryRecord.service.IKcsjEquipEntryRecordService;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
import com.hhwy.utils.tree.TreeUtil;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author zmh
 * @date 2023-12-14 11:08:08
 * @remark
 */
@Validated
@RestController
@RequestMapping("/kcsjEquipEntryRecord")
public class KcsjEquipEntryRecordController extends BaseController {

    @Autowired
    private IKcsjEquipEntryRecordService kcsjEquipEntryRecordService;


    /**
     * 台账页查询
     * @param kcsjEquipEntryRecordParam
     * @return
     */
    @PreAuthorize(hasPermi = "kcsjEquipEntryRecord:list")
    @GetMapping("/list")
    public AjaxResult getKcsjEquipEntryRecordList(@Validated(ValidationGroups.Select.class) KcsjEquipEntryRecord kcsjEquipEntryRecordParam) {
        KcsjEquipEntryRecordVo kcsjEquipEntryRecordVo = kcsjEquipEntryRecordService.getKcsjEquipEntryRecordList(kcsjEquipEntryRecordParam);
        return AjaxResult.success(kcsjEquipEntryRecordVo);
    }

    @PreAuthorize(hasPermi = "kcsjEquipEntryRecord:add")
    @PostMapping("/add")
    @CustomLogger(title = "勘察设计-勘察设备进场记录",name = "勘察设备进场记录",businessType = CustomBusinessType.SAVE)
    public AjaxResult insertKcsjEquipEntryRecord(@Validated(ValidationGroups.Save.class) @RequestBody KcsjEquipEntryRecord kcsjEquipEntryRecordParam) {
        kcsjEquipEntryRecordService.insertKcsjEquipEntryRecord(kcsjEquipEntryRecordParam);
        return AjaxResult.success(kcsjEquipEntryRecordParam);
    }

    /**
     * 批量保存
     * @param kcsjEquipEntryRecordVo
     * @return
     */
    @PreAuthorize(hasPermi = "kcsjEquipEntryRecord:batchAdd")
    @PostMapping("/batchAdd")
    @CustomLogger(title = "勘察设计-勘察设备进场记录",name = "勘察设备进场记录",businessType = CustomBusinessType.SAVE)
    public AjaxResult insertKcsjEquipEntryRecordList(@Validated(ValidationGroups.Save.class) @RequestBody KcsjEquipEntryRecordVo kcsjEquipEntryRecordVo) {
        AjaxResult ajaxResul =  kcsjEquipEntryRecordService.insertKcsjEquipEntryRecordList(kcsjEquipEntryRecordVo);
        return AjaxResult.success(ajaxResul);
    }

    @PreAuthorize(hasPermi = "kcsjEquipEntryRecord:update")
    @PostMapping("/update")
    @CustomLogger(title = "勘察设计-勘察设备进场记录",name = "勘察设备进场记录",businessType = CustomBusinessType.UPDATE)
    public AjaxResult updateKcsjEquipEntryRecord(@Validated(ValidationGroups.Update.class) @RequestBody KcsjEquipEntryRecord kcsjEquipEntryRecordParam) {
        return toAjax(kcsjEquipEntryRecordService.updateKcsjEquipEntryRecord(kcsjEquipEntryRecordParam));
    }

    @PreAuthorize(hasPermi = "kcsjEquipEntryRecord:update")
    @PostMapping("/batchUpdate")
    @CustomLogger(title = "勘察设计-勘察设备进场记录",name = "勘察设备进场记录",businessType = CustomBusinessType.UPDATE)
    public AjaxResult updateKcsjEquipEntryRecordList(@Validated(ValidationGroups.Update.class) @RequestBody List<KcsjEquipEntryRecord> kcsjEquipEntryRecordListParam) {
        return toAjax(kcsjEquipEntryRecordService.updateKcsjEquipEntryRecordList(kcsjEquipEntryRecordListParam));
    }

    @PreAuthorize(hasPermi = "kcsjEquipEntryRecord:remove")
    @PostMapping("/delete")
    public AjaxResult deleteKcsjEquipEntryRecord(@Validated(ValidationGroups.Delete.class) @RequestBody KcsjEquipEntryRecord kcsjEquipEntryRecordParam) {
        return toAjax(kcsjEquipEntryRecordService.deleteKcsjEquipEntryRecord(kcsjEquipEntryRecordParam));
    }

    @PreAuthorize(hasPermi = "kcsjEquipEntryRecord:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteKcsjEquipEntryRecordByPks(@PathVariable Long[] ids) {
        List<Long> kcsjEquipEntryRecordPkList = Arrays.asList(ids);
        return toAjax(kcsjEquipEntryRecordService.deleteKcsjEquipEntryRecordByPks(kcsjEquipEntryRecordPkList));
    }

    @GetMapping("/export")
    @CustomLogger(title = "勘察设计-勘察设备进场记录",name = "勘察设备进场记录",businessType = CustomBusinessType.EXPORT)
    public void export(HttpServletResponse response, KcsjEquipEntryRecord kcsjEquipEntryRecordParam) throws IOException {
        KcsjEquipEntryRecordVo kcsjEquipEntryRecordVo = kcsjEquipEntryRecordService.getKcsjEquipEntryRecordList(kcsjEquipEntryRecordParam);
        ExcelUtils<KcsjEquipEntryRecord> util = new ExcelUtils<>(KcsjEquipEntryRecord.class);
        util.exportExcel(response, TreeUtil.treeToList(kcsjEquipEntryRecordVo.getTreeList()), DateUtils.getDate());
    }

    /**
     * 同步
     *
     * @param
     * @return
     */
    @PreAuthorize(hasPermi = "sgjsPlanMeasureManage:sync")
    @GetMapping("/sync")
    public AjaxResult sync() {
        KcsjEquipEntryRecordVo kcsjEquipEntryRecordVo = kcsjEquipEntryRecordService.sync();
        return AjaxResult.success(kcsjEquipEntryRecordVo);
    }

    /**
     * 物设同步
     *
     * @return
     */
    @PostMapping("/syncWushe")
    public AjaxResult syncWushe(@RequestBody List<SyncWusheEquipVo> list){
        AjaxResult result = kcsjEquipEntryRecordService.syncWushe(list);
        return result;
    }

    /**
     * 定时任务
     * 同步物设设备进场记录
     *
     * @return
     */
    @GetMapping("/syncWusheJob")
    public AjaxResult syncWusheJob(){
        kcsjEquipEntryRecordService.syncWusheJob();
        return AjaxResult.success();
    }
}
