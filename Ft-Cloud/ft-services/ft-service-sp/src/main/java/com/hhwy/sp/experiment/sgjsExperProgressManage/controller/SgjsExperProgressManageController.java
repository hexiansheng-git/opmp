package com.hhwy.sp.experiment.sgjsExperProgressManage.controller;


import com.alibaba.nacos.common.utils.CollectionUtils;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.sp.experiment.sgjsExperProgressManage.domain.SgjsExperProgressManage;
import com.hhwy.sp.experiment.sgjsExperProgressManage.domain.SgjsExperProgressManageVo;
import com.hhwy.sp.experiment.sgjsExperProgressManage.service.ISgjsExperProgressManageService;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
import com.hhwy.utils.tree.TreeUtil;
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

/**
 * @author wll
 * @date 2023-12-09 11:06:27
 * @remark 施工技术-试验进度计划管理
 */
@Validated
@RestController
@RequestMapping("/sgjsExperProgressManage")
public class SgjsExperProgressManageController extends BaseController {


    @Autowired
    private ISgjsExperProgressManageService sgjsExperProgressManageService;


    /**
     * 列表数据
     *
     * @param sgjsExperProgressManageParam
     * @return
     */
    @PreAuthorize(hasPermi = "sgjsExperProgressManage:list")
    @GetMapping("/list")
    public AjaxResult getSgjsExperProgressManageList(@Validated(ValidationGroups.Select.class) SgjsExperProgressManage sgjsExperProgressManageParam) {
        SgjsExperProgressManageVo vo = sgjsExperProgressManageService.list(sgjsExperProgressManageParam);
        return AjaxResult.success(vo);
    }


    /**
     * 批量新增
     *
     * @param sgjsExperProgressManageVo
     * @return
     */
    @PreAuthorize(hasPermi = "sgjsExperProgressManage:add")
    @PostMapping("/batchAdd")
    @CustomLogger(title = "施工技术-试验管理-试验计划进度管理",name = "测量报告提交",businessType = CustomBusinessType.SAVE)
    public AjaxResult insertSgjsExperProgressManageList(
            @Validated(ValidationGroups.Save.class) @RequestBody SgjsExperProgressManageVo sgjsExperProgressManageVo) {
        AjaxResult ajaxResult = sgjsExperProgressManageService.batchAdd(sgjsExperProgressManageVo);
        return ajaxResult;
    }


    /**
     * 导出数据
     *
     * @param response
     * @param sgjsExperProgressManageParam
     * @throws IOException
     */
    @PreAuthorize(hasPermi = "sgjsExperProgressManage:report")
    @PostMapping("/export")
    public void export(HttpServletResponse response,
                       @RequestBody SgjsExperProgressManage sgjsExperProgressManageParam) throws IOException {
        List<Long> ids = sgjsExperProgressManageParam.getIds();
        List<SgjsExperProgressManage> treeList = null;
        if (CollectionUtils.isEmpty(ids)) {
            SgjsExperProgressManageVo sgjsExperProgressManageVo = sgjsExperProgressManageService.list(sgjsExperProgressManageParam);
            treeList = sgjsExperProgressManageVo.getTreeList();
            if (CollectionUtils.isNotEmpty(treeList)) {
                treeList = TreeUtil.treeToListWithLevel(treeList);
            }
        } else {
            List<String> idsStr = new ArrayList<>();
            for (Long id : ids) {
                idsStr.add(String.valueOf(id));
            }
            List<SgjsExperProgressManage> list = sgjsExperProgressManageService.getIds(idsStr);
            if (CollectionUtils.isNotEmpty(list)) {
                treeList = list;
            }
        }
        ExcelUtils<SgjsExperProgressManage> utils = new ExcelUtils<>(SgjsExperProgressManage.class);
        utils.exportExcel(response, treeList, DateUtils.getDate());
    }

    /**
     * 同步前期策划的数据
     *
     * @return
     */
    @PreAuthorize(hasPermi = "sgjsExperProgressManage:sync")
    @GetMapping("/sync")
    public SgjsExperProgressManageVo sync() {
        return sgjsExperProgressManageService.sync();
    }


    /**
     * 详情
     * @param sgjsExperProgressManageParam
     * @return
     */
    @PreAuthorize(hasPermi = "sgjsExperProgressManage:list")
    @GetMapping
    public AjaxResult getSgjsExperProgressManage(@Validated(ValidationGroups.Get.class) SgjsExperProgressManage sgjsExperProgressManageParam) {
        SgjsExperProgressManage sgjsExperProgressManage = sgjsExperProgressManageService.getSgjsExperProgressManage(sgjsExperProgressManageParam);
        return AjaxResult.success(sgjsExperProgressManage);
    }

    @PreAuthorize(hasPermi = "sgjsExperProgressManage:update")
    @PostMapping("/update")
    @CustomLogger(title = "施工技术-试验管理-试验计划进度管理",name = "试验计划进度管理",businessType = CustomBusinessType.UPDATE)
    public AjaxResult updateSgjsExperProgressManage(@Validated(ValidationGroups.Update.class) @RequestBody SgjsExperProgressManage sgjsExperProgressManageParam) {
        return toAjax(sgjsExperProgressManageService.updateSgjsExperProgressManage(sgjsExperProgressManageParam));
    }

    @PreAuthorize(hasPermi = "sgjsExperProgressManage:update")
    @PostMapping("/batchUpdate")
    @CustomLogger(title = "施工技术-试验管理-试验计划进度管理",name = "试验计划进度管理",businessType = CustomBusinessType.UPDATE)
    public AjaxResult updateSgjsExperProgressManageList(@Validated(ValidationGroups.Update.class) @RequestBody List<SgjsExperProgressManage> sgjsExperProgressManageListParam) {
        return toAjax(sgjsExperProgressManageService.updateSgjsExperProgressManageList(sgjsExperProgressManageListParam));
    }

    @PreAuthorize(hasPermi = "sgjsExperProgressManage:remove")
    @PostMapping("/delete")
    public AjaxResult deleteSgjsExperProgressManage(@Validated(ValidationGroups.Delete.class) @RequestBody List<Long> ids) {
        return toAjax(sgjsExperProgressManageService.deleteSgjsExperProgressManageByPks(ids));
    }

    @PreAuthorize(hasPermi = "sgjsExperProgressManage:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteSgjsExperProgressManageByPks(@PathVariable Long[] ids) {
        List<Long> sgjsExperProgressManagePkList = Arrays.asList(ids);
        return toAjax(sgjsExperProgressManageService.deleteSgjsExperProgressManageByPks(sgjsExperProgressManagePkList));
    }

    @PreAuthorize(hasPermi = "sgjsExperProgressManage:add")
    @PostMapping("/add")
    @CustomLogger(title = "施工技术-试验管理-试验计划进度管理",name = "试验计划进度管理",businessType = CustomBusinessType.SAVE)
    public AjaxResult insertSgjsExperProgressManage(@Validated(ValidationGroups.Save.class) @RequestBody SgjsExperProgressManage sgjsExperProgressManageParam) {
        sgjsExperProgressManageService.insertSgjsExperProgressManage(sgjsExperProgressManageParam);
        return AjaxResult.success(sgjsExperProgressManageParam);
    }

    /**
     * 导入
     *
     * @param file
     * @return
     */
    @PreAuthorize(hasPermi = "sgjsExperProgressManage:import")
    @PostMapping("/importData")
    @CustomLogger(title = "施工技术-试验管理-试验计划进度管理",name = "试验计划进度管理",businessType = CustomBusinessType.IMPORT)
    public AjaxResult importData(@RequestPart("file") MultipartFile file) {
        return sgjsExperProgressManageService.importData(file);
    }

}
