package com.hhwy.pm.jdgl.diff.make.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.jdgl.diff.make.domain.JdglCorrectionMeasuresMake;
import com.hhwy.pm.jdgl.diff.make.service.IJdglCorrectionMeasuresMakeService;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
import com.hhwy.utils.validation.ValidationGroups;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhenglili
 * @date 2023-08-25 14:33:50
 * @remark 纠偏措施制定
 */
@Validated
@RestController
@RequestMapping("/jdglCorrectionMeasuresMake")
public class JdglCorrectionMeasuresMakeController extends BaseController {

    @Autowired
    private IJdglCorrectionMeasuresMakeService jdglCorrectionMeasuresMakeService;

    /**
     * 查询单条详情
     *
     * @param jdglCorrectionMeasuresMakeParam
     * @return
     */
    @PreAuthorize(hasPermi = "jdglCorrectionMeasuresMake:list")
    @GetMapping("/getOne")
    @CustomLogger(title = "进度管理-差异化管控", name = "纠偏措施制定", businessType = CustomBusinessType.SELECT)
    public AjaxResult getJdglCorrectionMeasuresMake(@Validated(ValidationGroups.Get.class) JdglCorrectionMeasuresMake jdglCorrectionMeasuresMakeParam) {
        JdglCorrectionMeasuresMake jdglCorrectionMeasuresMake = jdglCorrectionMeasuresMakeService.getJdglCorrectionMeasuresMake(jdglCorrectionMeasuresMakeParam);
        return AjaxResult.success(jdglCorrectionMeasuresMake);
    }

    /**
     * 列表
     *
     * @param jdglCorrectionMeasuresMakeParam
     * @return
     */
    @PreAuthorize(hasPermi = "jdglCorrectionMeasuresMake:list")
    @GetMapping("/getList")
    @CustomLogger(title = "进度管理-差异化管控", name = "纠偏措施制定", businessType = CustomBusinessType.SELECT)
    public AjaxResult getJdglCorrectionMeasuresMakeList(
        @Validated(ValidationGroups.Select.class) JdglCorrectionMeasuresMake jdglCorrectionMeasuresMakeParam) {
        startPage();
        List<JdglCorrectionMeasuresMake> jdglCorrectionMeasuresMakeList = jdglCorrectionMeasuresMakeService.getJdglCorrectionMeasuresMakeList(jdglCorrectionMeasuresMakeParam);
        return getDataTableAjaxResult(jdglCorrectionMeasuresMakeList);
    }

    /**
     * 新增保存
     *
     * @param jdglCorrectionMeasuresMakeParam
     * @return
     */
    @PreAuthorize(hasPermi = "jdglCorrectionMeasuresMake:add")
    @PostMapping("/add")
    @CustomLogger(title = "进度管理-差异化管控", name = "纠偏措施制定", businessType = CustomBusinessType.SAVE)
    public AjaxResult insertJdglCorrectionMeasuresMake(
        @Validated(ValidationGroups.Save.class) @RequestBody JdglCorrectionMeasuresMake jdglCorrectionMeasuresMakeParam) {
        jdglCorrectionMeasuresMakeService.insertJdglCorrectionMeasuresMake(jdglCorrectionMeasuresMakeParam);
        JdglCorrectionMeasuresMake jdglCorrectionMeasuresMake = new JdglCorrectionMeasuresMake();
        jdglCorrectionMeasuresMake.setId(jdglCorrectionMeasuresMakeParam.getId());
        return AjaxResult
            .success(jdglCorrectionMeasuresMakeService.getJdglCorrectionMeasuresMake(jdglCorrectionMeasuresMake));
    }

    @PreAuthorize(hasPermi = "jdglCorrectionMeasuresMake:add")
    @PostMapping("/batchAdd")
    @CustomLogger(title = "进度管理-差异化管控", name = "纠偏措施制定", businessType = CustomBusinessType.SAVE)
    public AjaxResult insertJdglCorrectionMeasuresMakeList(
        @Validated(ValidationGroups.Save.class) @RequestBody List<JdglCorrectionMeasuresMake> jdglCorrectionMeasuresMakeListParam) {
        jdglCorrectionMeasuresMakeService.insertJdglCorrectionMeasuresMakeList(jdglCorrectionMeasuresMakeListParam);
        return AjaxResult.success(jdglCorrectionMeasuresMakeListParam);
    }

    /**
     * 更新保存
     *
     * @param jdglCorrectionMeasuresMakeParam
     * @return
     */
    @PreAuthorize(hasPermi = "jdglCorrectionMeasuresMake:update")
    @PostMapping("/update")
    public AjaxResult updateJdglCorrectionMeasuresMake(
        @Validated(ValidationGroups.Update.class) @RequestBody JdglCorrectionMeasuresMake jdglCorrectionMeasuresMakeParam) {
        jdglCorrectionMeasuresMakeService.updateJdglCorrectionMeasuresMake(jdglCorrectionMeasuresMakeParam);
        JdglCorrectionMeasuresMake jdglCorrectionMeasuresMake = new JdglCorrectionMeasuresMake();
        jdglCorrectionMeasuresMake.setId(jdglCorrectionMeasuresMakeParam.getId());
        return AjaxResult.success(jdglCorrectionMeasuresMakeService.getJdglCorrectionMeasuresMake(jdglCorrectionMeasuresMake));
    }

    @PreAuthorize(hasPermi = "jdglCorrectionMeasuresMake:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateJdglCorrectionMeasuresMakeList(
        @Validated(ValidationGroups.Update.class) @RequestBody List<JdglCorrectionMeasuresMake> jdglCorrectionMeasuresMakeListParam) {
        return toAjax(jdglCorrectionMeasuresMakeService
            .updateJdglCorrectionMeasuresMakeList(jdglCorrectionMeasuresMakeListParam));
    }

    @PreAuthorize(hasPermi = "jdglCorrectionMeasuresMake:remove")
    @PostMapping("/delete")
    @CustomLogger(title = "进度管理-差异化管控", name = "纠偏措施制定", businessType = CustomBusinessType.DELETE)
    public AjaxResult deleteJdglCorrectionMeasuresMake(
        @Validated(ValidationGroups.Delete.class) @RequestBody JdglCorrectionMeasuresMake jdglCorrectionMeasuresMakeParam) {
        return toAjax(
            jdglCorrectionMeasuresMakeService.deleteJdglCorrectionMeasuresMake(jdglCorrectionMeasuresMakeParam));
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @PreAuthorize(hasPermi = "jdglCorrectionMeasuresMake:remove")
    @PostMapping("/{ids}")
    @CustomLogger(title = "进度管理-差异化管控", name = "纠偏措施制定", businessType = CustomBusinessType.DELETE)
    public AjaxResult deleteJdglCorrectionMeasuresMakeByPks(@PathVariable Long[] ids) {
        List<Long> jdglCorrectionMeasuresMakePkList = Arrays.asList(ids);
        return toAjax(
            jdglCorrectionMeasuresMakeService.deleteJdglCorrectionMeasuresMakeByPks(jdglCorrectionMeasuresMakePkList));
    }

    @GetMapping("/export")
    @CustomLogger(title = "进度管理-差异化管控", name = "纠偏措施制定", businessType = CustomBusinessType.EXPORT)
    public void export(HttpServletResponse response, JdglCorrectionMeasuresMake jdglCorrectionMeasuresMakeParam)
        throws IOException {
        List<JdglCorrectionMeasuresMake> jdglCorrectionMeasuresMakeList = jdglCorrectionMeasuresMakeService
            .getJdglCorrectionMeasuresMakeList(jdglCorrectionMeasuresMakeParam);
        ExcelUtils<JdglCorrectionMeasuresMake> util = new ExcelUtils<>(JdglCorrectionMeasuresMake.class);
        util.exportExcel(response, jdglCorrectionMeasuresMakeList, DateUtils.getDate());
    }

    /**
     *
     * 更新流程数据
     * @param id 主键
     * @return  监听器
     */
    @RequestMapping(value ="/listener",method = RequestMethod.POST)
    public AjaxResult updateJdglCorrectionMeasuresMakeProcess(@RequestParam ("id") Long id) {
        jdglCorrectionMeasuresMakeService.updateJdglCorrectionMeasuresMakeProcess(id);
        return AjaxResult.success("成功");
    }

    /**
     * 同步差异化分析数据
     *
     * @param period
     * @return
     */
    @PostMapping("/syncData")
    @CustomLogger(title = "进度管理-差异化管控", name = "纠偏措施制定", businessType = CustomBusinessType.SAVE)
    public AjaxResult syncData(@RequestParam Date period) {
        jdglCorrectionMeasuresMakeService.syncData(period);
        return AjaxResult.success();
    }
}
