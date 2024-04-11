package com.hhwy.sp.experiment.mixRatioManage.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.sp.experiment.mixRatioManage.domain.SgjsMixRatioManage;
import com.hhwy.sp.experiment.mixRatioManage.domain.vo.MixRatioManageQueryVo;
import com.hhwy.sp.experiment.mixRatioManage.service.ISgjsMixRatioManageService;
import com.hhwy.utils.excel.FtExcelUtil;
import com.hhwy.utils.validation.ValidationGroups;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author han
 * @date 2024-04-07 13:38:19
 * @remark 配合比管理
 */
@Validated
@RestController
@RequestMapping("/sgjsMixRatioManage")
public class SgjsMixRatioManageController extends BaseController {

    @Autowired
    private ISgjsMixRatioManageService sgjsMixRatioManageService;


    /**
     * 台账
     * @param queryVo
     * @return
     */
    @PreAuthorize(hasPermi = "sgjsMixRatioManage:list")
    @GetMapping("/list")
    public AjaxResult getListByQueryVo(@Validated(ValidationGroups.Select.class) MixRatioManageQueryVo queryVo) {
        startPage();
        List<SgjsMixRatioManage> sgjsMixRatioManageList = sgjsMixRatioManageService.getListByQueryVo(queryVo);
        return getDataTableAjaxResult(sgjsMixRatioManageList);
    }

    /**
     * 详情
     * @param id
     * @return
     */
    @PreAuthorize(hasPermi = "sgjsMixRatioManage:list")
    @GetMapping("/getById")
    public AjaxResult getById(Long id) {
        SgjsMixRatioManage mixRatioManage = sgjsMixRatioManageService.getById(id);
        return AjaxResult.success(mixRatioManage);
    }

    /**
     * 保存
     * @param mixRatioManage
     * @return
     */
    @PreAuthorize(hasPermi = "sgjsMixRatioManage:save")
    @PostMapping("/save")
    public AjaxResult save(@Validated(ValidationGroups.Save.class) @RequestBody SgjsMixRatioManage mixRatioManage) {
        sgjsMixRatioManageService.save(mixRatioManage);
        return AjaxResult.success();
    }

    @PreAuthorize(hasPermi = "sgjsMixRatioManage:remove")
    @PostMapping("/delete")
    public AjaxResult deleteSgjsMixRatioManage(@Validated(ValidationGroups.Delete.class) @RequestBody SgjsMixRatioManage sgjsMixRatioManageParam) {
        return toAjax(sgjsMixRatioManageService.deleteSgjsMixRatioManage(sgjsMixRatioManageParam));
    }

    @PreAuthorize(hasPermi = "sgjsMixRatioManage:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteSgjsMixRatioManageByPks(@PathVariable Long[] ids) {
        List<Long> sgjsMixRatioManagePkList = Arrays.asList(ids);
        return toAjax(sgjsMixRatioManageService.deleteSgjsMixRatioManageByPks(sgjsMixRatioManagePkList));
    }

    /**
     * 导出
     * @param response
     * @param queryVo
     * @throws IOException
     */
    @PostMapping("/export")
    public void export(HttpServletResponse response, MixRatioManageQueryVo queryVo) throws IOException {
        List<Long> ids = queryVo.getIds();
        List<SgjsMixRatioManage> mixRatioManageList;
        if(CollectionUtils.isEmpty(ids)){
            mixRatioManageList = sgjsMixRatioManageService.getListByQueryVo(queryVo);
        }else {
            mixRatioManageList = sgjsMixRatioManageService.getListByIds(ids);
        }
        FtExcelUtil<SgjsMixRatioManage> util = new FtExcelUtil<>(SgjsMixRatioManage.class);
        util.exportExcel(response, mixRatioManageList, DateUtils.getDate());
    }
}
