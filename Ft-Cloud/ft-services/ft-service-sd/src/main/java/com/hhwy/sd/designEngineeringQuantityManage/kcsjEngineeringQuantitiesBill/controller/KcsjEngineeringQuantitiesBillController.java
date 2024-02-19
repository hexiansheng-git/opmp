package com.hhwy.sd.designEngineeringQuantityManage.kcsjEngineeringQuantitiesBill.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.sd.designEngineeringQuantityManage.kcsjEngineeringQuantitiesBill.domain.KcsjEngineeringQuantitiesBill;
import com.hhwy.sd.designEngineeringQuantityManage.kcsjEngineeringQuantitiesBill.service.IKcsjEngineeringQuantitiesBillService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author wll
 * @date 2024-02-04 14:04:56
 * @remark 勘察设计-设计工程量管理-工程量清单
 */
@Validated
@RestController
@RequestMapping("/kcsjEngineeringQuantitiesBill")
public class KcsjEngineeringQuantitiesBillController extends BaseController {

    @Autowired
    private IKcsjEngineeringQuantitiesBillService kcsjEngineeringQuantitiesBillService;


    /**
     * 详情
     *
     * @param kcsjEngineeringQuantitiesBillParam
     * @return
     */
    @PreAuthorize(hasPermi = "kcsjEngineeringQuantitiesBill:select")
    @GetMapping
    public AjaxResult getKcsjEngineeringQuantitiesBill(@Validated(ValidationGroups.Get.class) KcsjEngineeringQuantitiesBill kcsjEngineeringQuantitiesBillParam) {
        KcsjEngineeringQuantitiesBill kcsjEngineeringQuantitiesBill = kcsjEngineeringQuantitiesBillService.getKcsjEngineeringQuantitiesBill(kcsjEngineeringQuantitiesBillParam);
        return AjaxResult.success(kcsjEngineeringQuantitiesBill);
    }

    /**
     * 列表查询
     *
     * @param kcsjEngineeringQuantitiesBillParam
     * @return
     */
    @PreAuthorize(hasPermi = "kcsjEngineeringQuantitiesBill:list")
    @GetMapping("/list")
    public AjaxResult getKcsjEngineeringQuantitiesBillList(@Validated(ValidationGroups.Select.class) KcsjEngineeringQuantitiesBill kcsjEngineeringQuantitiesBillParam) {
        startPage();
        List<KcsjEngineeringQuantitiesBill> kcsjEngineeringQuantitiesBillList = kcsjEngineeringQuantitiesBillService.getKcsjEngineeringQuantitiesBillList(kcsjEngineeringQuantitiesBillParam);
        return getDataTableAjaxResult(kcsjEngineeringQuantitiesBillList);
    }

    /**
     * 新增数据
     *
     * @param kcsjEngineeringQuantitiesBillParam
     * @return
     */
    @PreAuthorize(hasPermi = "kcsjEngineeringQuantitiesBill:add")
    @PostMapping("/add")
    public AjaxResult insertKcsjEngineeringQuantitiesBill(@Validated(ValidationGroups.Save.class) @RequestBody KcsjEngineeringQuantitiesBill kcsjEngineeringQuantitiesBillParam) {
        return kcsjEngineeringQuantitiesBillService.insertKcsjEngineeringQuantitiesBill(kcsjEngineeringQuantitiesBillParam);

    }

    @PreAuthorize(hasPermi = "kcsjEngineeringQuantitiesBill:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertKcsjEngineeringQuantitiesBillList(@Validated(ValidationGroups.Save.class) @RequestBody List<KcsjEngineeringQuantitiesBill> kcsjEngineeringQuantitiesBillListParam) {
        kcsjEngineeringQuantitiesBillService.insertKcsjEngineeringQuantitiesBillList(kcsjEngineeringQuantitiesBillListParam);
        return AjaxResult.success(kcsjEngineeringQuantitiesBillListParam);
    }

    /**
     * 修改数据
     * @param kcsjEngineeringQuantitiesBillParam
     * @return
     */
    @PreAuthorize(hasPermi = "kcsjEngineeringQuantitiesBill:update")
    @PostMapping("/update")
    public AjaxResult updateKcsjEngineeringQuantitiesBill(@Validated(ValidationGroups.Update.class) @RequestBody KcsjEngineeringQuantitiesBill kcsjEngineeringQuantitiesBillParam) {
        return kcsjEngineeringQuantitiesBillService.updateKcsjEngineeringQuantitiesBill(kcsjEngineeringQuantitiesBillParam);
    }


    @PreAuthorize(hasPermi = "kcsjEngineeringQuantitiesBill:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateKcsjEngineeringQuantitiesBillList(@Validated(ValidationGroups.Update.class) @RequestBody List<KcsjEngineeringQuantitiesBill> kcsjEngineeringQuantitiesBillListParam) {
        return toAjax(kcsjEngineeringQuantitiesBillService.updateKcsjEngineeringQuantitiesBillList(kcsjEngineeringQuantitiesBillListParam));
    }


    /**
     * 批量删除
     * @param kcsjEngineeringQuantitiesBill
     * @return
     */
    @PreAuthorize(hasPermi = "kcsjEngineeringQuantitiesBill:remove")
    @PostMapping("/delByIds")
    public AjaxResult deleteKcsjEngineeringQuantitiesBillByPks(@RequestBody KcsjEngineeringQuantitiesBill kcsjEngineeringQuantitiesBill) {
        return AjaxResult.success(kcsjEngineeringQuantitiesBillService.deleteKcsjEngineeringQuantitiesBillByPks(kcsjEngineeringQuantitiesBill.getDelIdList()));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, KcsjEngineeringQuantitiesBill kcsjEngineeringQuantitiesBillParam) throws IOException {
        List<KcsjEngineeringQuantitiesBill> kcsjEngineeringQuantitiesBillList = kcsjEngineeringQuantitiesBillService.getKcsjEngineeringQuantitiesBillList(kcsjEngineeringQuantitiesBillParam);
        ExcelUtils<KcsjEngineeringQuantitiesBill> util = new ExcelUtils<>(KcsjEngineeringQuantitiesBill.class);
        util.exportExcel(response, kcsjEngineeringQuantitiesBillList, DateUtils.getDate());
    }


}
