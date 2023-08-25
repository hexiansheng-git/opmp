package com.hhwy.pm.jdgl.diff.track.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.jdgl.diff.track.domain.JdglProgressCorrectionTrack;
import com.hhwy.pm.jdgl.diff.track.service.IJdglProgressCorrectionTrackService;
import com.hhwy.utils.validation.ValidationGroups;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhenglili
 * @date 2023-08-24 16:51:08
 * @remark 进度纠偏跟踪
 */
@Validated
@RestController
@RequestMapping("/jdglProgressCorrectionTrack")
public class JdglProgressCorrectionTrackController extends BaseController {

    @Autowired
    private IJdglProgressCorrectionTrackService jdglProgressCorrectionTrackService;

    /**
     * 查询单条数据-详情
     *
     * @param jdglProgressCorrectionTrackParam
     * @return
     */
    @PreAuthorize(hasPermi = "jdglProgressCorrectionTrack:list")
    @GetMapping
    public AjaxResult getOne(
        @Validated(ValidationGroups.Get.class) JdglProgressCorrectionTrack jdglProgressCorrectionTrackParam) {
        JdglProgressCorrectionTrack jdglProgressCorrectionTrack = jdglProgressCorrectionTrackService
            .getJdglProgressCorrectionTrack(jdglProgressCorrectionTrackParam);
        return AjaxResult.success(jdglProgressCorrectionTrack);
    }

    /**
     * 列表查询
     *
     * @param jdglProgressCorrectionTrackParam
     * @return
     */
    @PreAuthorize(hasPermi = "jdglProgressCorrectionTrack:list")
    @GetMapping("/getList")
    public AjaxResult getJdglProgressCorrectionTrackList(
        @Validated(ValidationGroups.Select.class) JdglProgressCorrectionTrack jdglProgressCorrectionTrackParam) {
        startPage();
        List<JdglProgressCorrectionTrack> jdglProgressCorrectionTrackList = jdglProgressCorrectionTrackService
            .getJdglProgressCorrectionTrackList(jdglProgressCorrectionTrackParam);
        return getDataTableAjaxResult(jdglProgressCorrectionTrackList);
    }

    @PreAuthorize(hasPermi = "jdglProgressCorrectionTrack:add")
    @PostMapping("/add")
    public AjaxResult insertJdglProgressCorrectionTrack(
        @Validated(ValidationGroups.Save.class) @RequestBody JdglProgressCorrectionTrack jdglProgressCorrectionTrackParam) {
        jdglProgressCorrectionTrackService.insertJdglProgressCorrectionTrack(jdglProgressCorrectionTrackParam);
        return AjaxResult.success(jdglProgressCorrectionTrackParam);
    }

    @PreAuthorize(hasPermi = "jdglProgressCorrectionTrack:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertJdglProgressCorrectionTrackList(
        @Validated(ValidationGroups.Save.class) @RequestBody List<JdglProgressCorrectionTrack> jdglProgressCorrectionTrackListParam) {
        jdglProgressCorrectionTrackService.insertJdglProgressCorrectionTrackList(jdglProgressCorrectionTrackListParam);
        return AjaxResult.success(jdglProgressCorrectionTrackListParam);
    }

    /**
     * 更新保存
     *
     * @param jdglProgressCorrectionTrackParam
     * @return
     */
    @PreAuthorize(hasPermi = "jdglProgressCorrectionTrack:update")
    @PostMapping("/update")
    public AjaxResult updateJdglProgressCorrectionTrack(
        @Validated(ValidationGroups.Update.class) @RequestBody JdglProgressCorrectionTrack jdglProgressCorrectionTrackParam) {
        return toAjax(
            jdglProgressCorrectionTrackService.updateJdglProgressCorrectionTrack(jdglProgressCorrectionTrackParam));
    }

    /**
     * @param jdglProgressCorrectionTrackListParam
     * @return
     */
    @PreAuthorize(hasPermi = "jdglProgressCorrectionTrack:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateJdglProgressCorrectionTrackList(
        @Validated(ValidationGroups.Update.class) @RequestBody List<JdglProgressCorrectionTrack> jdglProgressCorrectionTrackListParam) {
        return toAjax(jdglProgressCorrectionTrackService
            .updateJdglProgressCorrectionTrackList(jdglProgressCorrectionTrackListParam));
    }

    @PreAuthorize(hasPermi = "jdglProgressCorrectionTrack:remove")
    @PostMapping("/delete")
    public AjaxResult deleteJdglProgressCorrectionTrack(
        @Validated(ValidationGroups.Delete.class) @RequestBody JdglProgressCorrectionTrack jdglProgressCorrectionTrackParam) {
        return toAjax(
            jdglProgressCorrectionTrackService.deleteJdglProgressCorrectionTrack(jdglProgressCorrectionTrackParam));
    }

    @PreAuthorize(hasPermi = "jdglProgressCorrectionTrack:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteJdglProgressCorrectionTrackByPks(@PathVariable Long[] ids) {
        List<Long> jdglProgressCorrectionTrackPkList = Arrays.asList(ids);
        return toAjax(jdglProgressCorrectionTrackService
            .deleteJdglProgressCorrectionTrackByPks(jdglProgressCorrectionTrackPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, JdglProgressCorrectionTrack jdglProgressCorrectionTrackParam)
        throws IOException {
        List<JdglProgressCorrectionTrack> jdglProgressCorrectionTrackList = jdglProgressCorrectionTrackService
            .getJdglProgressCorrectionTrackList(jdglProgressCorrectionTrackParam);
        ExcelUtils<JdglProgressCorrectionTrack> util = new ExcelUtils<>(JdglProgressCorrectionTrack.class);
        util.exportExcel(response, jdglProgressCorrectionTrackList, DateUtils.getDate());
    }
}
