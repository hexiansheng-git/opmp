package com.hhwy.sd.disclosureRecord.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.sd.disclosureRecord.domain.KcsjDisclosureRecord;
import com.hhwy.sd.disclosureRecord.domain.vo.DisclosureRecordQueryVo;
import com.hhwy.sd.disclosureRecord.domain.vo.DisclosureRecordVo;
import com.hhwy.sd.disclosureRecord.service.IKcsjDisclosureRecordService;
import com.hhwy.utils.excel.FtExcelUtil;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.validation.ValidationGroups;
import io.seata.common.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 * @author han
 * @date 2023-12-18 11:15:48
 * @remark 勘察设计交底记录
 */
@Validated
@RestController
@RequestMapping("/kcsjDisclosureRecord")
public class KcsjDisclosureRecordController extends BaseController {

    @Autowired
    private IKcsjDisclosureRecordService kcsjDisclosureRecordService;


    @PreAuthorize(hasPermi = "kcsjDisclosureRecord:list")
    @GetMapping
    public AjaxResult getKcsjDisclosureRecord(@Validated(ValidationGroups.Get.class) KcsjDisclosureRecord kcsjDisclosureRecordParam) {
        KcsjDisclosureRecord kcsjDisclosureRecord = kcsjDisclosureRecordService.getKcsjDisclosureRecord(kcsjDisclosureRecordParam);
        return AjaxResult.success(kcsjDisclosureRecord);
    }

    /**
     * 台账
     * @param queryVo
     * @return
     */
    @PreAuthorize(hasPermi = "kcsjDisclosureRecord:list")
    @GetMapping("/list")
    public AjaxResult getKcsjDisclosureRecordList(@Validated(ValidationGroups.Select.class) DisclosureRecordQueryVo queryVo) {
        List<KcsjDisclosureRecord> kcsjDisclosureRecordList = kcsjDisclosureRecordService.getKcsjDisclosureRecordList(queryVo);
        return AjaxResult.success(kcsjDisclosureRecordList);
    }

    @PreAuthorize(hasPermi = "kcsjDisclosureRecord:add")
    @PostMapping("/add")
    public AjaxResult insertKcsjDisclosureRecord(@Validated(ValidationGroups.Save.class) @RequestBody KcsjDisclosureRecord kcsjDisclosureRecordParam) {
        kcsjDisclosureRecordService.insertKcsjDisclosureRecord(kcsjDisclosureRecordParam);
        return AjaxResult.success(kcsjDisclosureRecordParam);
    }

    /**
     * 保存
     * @param recordVo
     * @return
     */
    @PreAuthorize(hasPermi = "kcsjDisclosureRecord:save")
    @PostMapping("/save")
    public AjaxResult save(@Validated(ValidationGroups.Save.class) @RequestBody DisclosureRecordVo recordVo) {
        kcsjDisclosureRecordService.save(recordVo);
        return AjaxResult.success();
    }

    @PreAuthorize(hasPermi = "kcsjDisclosureRecord:update")
    @PostMapping("/update")
    public AjaxResult updateKcsjDisclosureRecord(@Validated(ValidationGroups.Update.class) @RequestBody KcsjDisclosureRecord kcsjDisclosureRecordParam) {
        return toAjax(kcsjDisclosureRecordService.updateKcsjDisclosureRecord(kcsjDisclosureRecordParam));
    }

    @PreAuthorize(hasPermi = "kcsjDisclosureRecord:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateKcsjDisclosureRecordList(@Validated(ValidationGroups.Update.class) @RequestBody List<KcsjDisclosureRecord> kcsjDisclosureRecordListParam) {
        return toAjax(kcsjDisclosureRecordService.updateKcsjDisclosureRecordList(kcsjDisclosureRecordListParam));
    }

    @PreAuthorize(hasPermi = "kcsjDisclosureRecord:remove")
    @PostMapping("/delete")
    public AjaxResult deleteKcsjDisclosureRecord(@Validated(ValidationGroups.Delete.class) @RequestBody KcsjDisclosureRecord kcsjDisclosureRecordParam) {
        return toAjax(kcsjDisclosureRecordService.deleteKcsjDisclosureRecord(kcsjDisclosureRecordParam));
    }

    @PreAuthorize(hasPermi = "kcsjDisclosureRecord:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteKcsjDisclosureRecordByPks(@PathVariable Long[] ids) {
        List<Long> kcsjDisclosureRecordPkList = Arrays.asList(ids);
        return toAjax(kcsjDisclosureRecordService.deleteKcsjDisclosureRecordByPks(kcsjDisclosureRecordPkList));
    }

    /**
     * 导入
     * @param file
     * @return
     */
    @PreAuthorize(hasPermi = "kcsjDisclosureRecord:import")
    @PostMapping("/importData")
    public AjaxResult importData(@RequestPart("file") MultipartFile file){
        FtExcelUtil<KcsjDisclosureRecord> util = new FtExcelUtil<>(KcsjDisclosureRecord.class);
        try {
            InputStream inputStream = file.getInputStream();
            List<KcsjDisclosureRecord> recordList = util.importExcel(inputStream);
            recordList.stream().forEach(o -> {
                o.setId(IdWorker.createId());
                o.setIsAdd("1");
            });
            return AjaxResult.success(recordList);
        } catch (Exception e) {
            throw new RuntimeException("导入失败！");
        }
    }

    /**
     * 导出
     * @param response
     * @param queryVo
     * @throws IOException
     */
    @PreAuthorize(hasPermi = "kcsjDisclosureRecord:export")
    @PostMapping("/export")
    public void export(HttpServletResponse response,@RequestBody DisclosureRecordQueryVo queryVo) throws IOException {
        List<Long> ids = queryVo.getIds();
        List<KcsjDisclosureRecord> recordList;
        if(CollectionUtils.isEmpty(ids)){
            recordList = kcsjDisclosureRecordService.getKcsjDisclosureRecordList(queryVo);
        }else {
            recordList = kcsjDisclosureRecordService.getListByIds(ids);
        }
        FtExcelUtil<KcsjDisclosureRecord> util = new FtExcelUtil<>(KcsjDisclosureRecord.class);
        util.exportExcel(response, recordList, DateUtils.getDate());
    }

    /**
     * 同步前期策划交底记录
     * @return
     */
    @PreAuthorize(hasPermi = "kcsjDisclosureRecord:sync")
    @PostMapping("sync")
    public AjaxResult sync() {
        kcsjDisclosureRecordService.sync();
        return AjaxResult.success();
    }
}
