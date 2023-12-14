package com.hhwy.pm.qqch.preparation.workPlanning.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.workPlanning.domain.QqchWorkPlaningArrange;
import com.hhwy.pm.qqch.preparation.workPlanning.domain.QqchWorkPlaningArrangeVo;
import com.hhwy.pm.qqch.preparation.workPlanning.service.IQqchWorkPlaningArrangeService;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author zq
 * @date 2023-07-19 11:49:17
 * @remark
 */
@Validated
@RestController
@RequestMapping("/qqchWorkPlaningArrange")
public class QqchWorkPlaningArrangeController extends BaseController {

    @Autowired
    private IQqchWorkPlaningArrangeService qqchWorkPlaningArrangeService;


    @PreAuthorize(hasPermi = "qqchWorkPlaningArrange:list")
    @GetMapping
    public AjaxResult getQqchWorkPlaningArrange(@Validated(ValidationGroups.Get.class) QqchWorkPlaningArrange qqchWorkPlaningArrangeParam) {
        QqchWorkPlaningArrange qqchWorkPlaningArrange = qqchWorkPlaningArrangeService.getQqchWorkPlaningArrange(qqchWorkPlaningArrangeParam);
        return AjaxResult.success(qqchWorkPlaningArrange);
    }

    @PreAuthorize(hasPermi = "qqchWorkPlaningArrange:list")
    @GetMapping("/list")
    public AjaxResult getQqchWorkPlaningArrangeList(@Validated(ValidationGroups.Select.class) QqchWorkPlaningArrange qqchWorkPlaningArrangeParam) {
        startPage();
        List<QqchWorkPlaningArrange> qqchWorkPlaningArrangeList = qqchWorkPlaningArrangeService.getQqchWorkPlaningArrangeList(qqchWorkPlaningArrangeParam);
        return getDataTableAjaxResult(qqchWorkPlaningArrangeList);
    }

    @PreAuthorize(hasPermi = "qqchWorkPlaningArrange:add")
    @PostMapping("/add")
    public AjaxResult insertQqchWorkPlaningArrange(@Validated(ValidationGroups.Save.class) @RequestBody QqchWorkPlaningArrange qqchWorkPlaningArrangeParam) {
        qqchWorkPlaningArrangeService.insertQqchWorkPlaningArrange(qqchWorkPlaningArrangeParam);
        return AjaxResult.success(qqchWorkPlaningArrangeParam);
    }

    @PreAuthorize(hasPermi = "qqchWorkPlaningArrange:add")
    @PostMapping("/batchAdd")
    @CustomLogger(title = "前期策划-前期策划编制-施工策划-1.4大临设施布设", name = "1.4.3便道部署", businessType = CustomBusinessType.SAVE)
    public AjaxResult insertQqchWorkPlaningArrangeList(@Validated(ValidationGroups.Save.class) @RequestBody QqchWorkPlaningArrangeVo qqchWorkPlaningArrangeListParam) {
        try {
            qqchWorkPlaningArrangeService.insertQqchWorkPlaningArrangeList(qqchWorkPlaningArrangeListParam);
            return AjaxResult.success(qqchWorkPlaningArrangeListParam);
        } catch (CustomBusinessException e) {
            e.printStackTrace();
            return AjaxResult.error(e.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
    }

    @PreAuthorize(hasPermi = "qqchWorkPlaningArrange:update")
    @PostMapping("/update")
    public AjaxResult updateQqchWorkPlaningArrange(@Validated(ValidationGroups.Update.class) @RequestBody QqchWorkPlaningArrange qqchWorkPlaningArrangeParam) {
        return toAjax(qqchWorkPlaningArrangeService.updateQqchWorkPlaningArrange(qqchWorkPlaningArrangeParam));
    }

    @PreAuthorize(hasPermi = "qqchWorkPlaningArrange:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchWorkPlaningArrangeList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchWorkPlaningArrange> qqchWorkPlaningArrangeListParam) {
        return toAjax(qqchWorkPlaningArrangeService.updateQqchWorkPlaningArrangeList(qqchWorkPlaningArrangeListParam));
    }

    @PreAuthorize(hasPermi = "qqchWorkPlaningArrange:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchWorkPlaningArrange(@Validated(ValidationGroups.Delete.class) @RequestBody QqchWorkPlaningArrange qqchWorkPlaningArrangeParam) {
        return toAjax(qqchWorkPlaningArrangeService.deleteQqchWorkPlaningArrange(qqchWorkPlaningArrangeParam));
    }

    @PreAuthorize(hasPermi = "qqchWorkPlaningArrange:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchWorkPlaningArrangeByPks(@PathVariable Long[] ids) {
        List<Long> qqchWorkPlaningArrangePkList = Arrays.asList(ids);
        return toAjax(qqchWorkPlaningArrangeService.deleteQqchWorkPlaningArrangeByPks(qqchWorkPlaningArrangePkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchWorkPlaningArrange qqchWorkPlaningArrangeParam) throws IOException {
        List<QqchWorkPlaningArrange> qqchWorkPlaningArrangeList = qqchWorkPlaningArrangeService.getQqchWorkPlaningArrangeList(qqchWorkPlaningArrangeParam);
        ExcelUtils<QqchWorkPlaningArrange> util = new ExcelUtils<>(QqchWorkPlaningArrange.class);
        util.exportExcel(response, qqchWorkPlaningArrangeList, DateUtils.getDate());
    }

    /**
     * 导入
     *
     * @param file
     * @return
     */
    @PreAuthorize(hasPermi = "qqchWorkPlaningArrange:importData")
    @PostMapping("/importData")
    @ResponseBody
    @CustomLogger(title = "前期策划-前期策划编制-施工策划-1.4大临设施布设", name = "1.4.3便道部署", businessType = CustomBusinessType.IMPORT)
    public AjaxResult importData(MultipartFile file) {
        try {
            ExcelUtils<QqchWorkPlaningArrange> util = new ExcelUtils<>(QqchWorkPlaningArrange.class);
            List<QqchWorkPlaningArrange> list = util.importExcel(file.getInputStream());
            return AjaxResult.success(list);
        } catch (Exception e) {
            throw new RuntimeException("导入失败！");
        }
    }

    @PostMapping("/detail")
    @ResponseBody
    public AjaxResult detail(QqchWorkPlaningArrange arrange) {
        try {
            QqchWorkPlaningArrangeVo qqchWorkPlaningArrangeVo = qqchWorkPlaningArrangeService.detail(arrange);
            return AjaxResult.success(qqchWorkPlaningArrangeVo);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
    }
}
