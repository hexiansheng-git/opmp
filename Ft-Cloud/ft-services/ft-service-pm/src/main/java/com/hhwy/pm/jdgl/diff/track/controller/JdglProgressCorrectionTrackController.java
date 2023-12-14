package com.hhwy.pm.jdgl.diff.track.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.jdgl.diff.track.domain.JdglProgressCorrectionTrack;
import com.hhwy.pm.jdgl.diff.track.domain.vo.ProgressCorrectionTrackQueryVo;
import com.hhwy.pm.jdgl.diff.track.service.IJdglProgressCorrectionTrackService;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
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
    @GetMapping("/getOne")
    @CustomLogger(title = "进度管理-进度纠偏跟踪", name = "进度纠偏跟踪", businessType = CustomBusinessType.SELECT)
    public AjaxResult getOne(
        @Validated(ValidationGroups.Get.class) JdglProgressCorrectionTrack jdglProgressCorrectionTrackParam) {
        JdglProgressCorrectionTrack jdglProgressCorrectionTrack = jdglProgressCorrectionTrackService.getJdglProgressCorrectionTrack(jdglProgressCorrectionTrackParam);
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
    @CustomLogger(title = "进度管理-进度纠偏跟踪", name = "进度纠偏跟踪", businessType = CustomBusinessType.SELECT)
    public AjaxResult getJdglProgressCorrectionTrackList(
        @Validated(ValidationGroups.Select.class) JdglProgressCorrectionTrack jdglProgressCorrectionTrackParam) {
        startPage();
        List<JdglProgressCorrectionTrack> jdglProgressCorrectionTrackList = jdglProgressCorrectionTrackService.getJdglProgressCorrectionTrackList(jdglProgressCorrectionTrackParam);
        return getDataTableAjaxResult(jdglProgressCorrectionTrackList);
    }

    @PostMapping("/gmList")
    @CustomLogger(title = "进度管理-进度纠偏跟踪", name = "进度纠偏跟踪", businessType = CustomBusinessType.SELECT)
    public AjaxResult gmList(@RequestBody ProgressCorrectionTrackQueryVo queryVo) {
        List<JdglProgressCorrectionTrack> jdglProgressCorrectionTrackList = jdglProgressCorrectionTrackService
            .gmList(queryVo);
        return AjaxResult.success(jdglProgressCorrectionTrackList);
    }

    /**
     * 新增保存
     *
     * @param jdglProgressCorrectionTrackParam
     * @return
     */
    @PreAuthorize(hasPermi = "jdglProgressCorrectionTrack:add")
    @PostMapping("/add")
    @CustomLogger(title = "进度管理-进度纠偏跟踪", name = "进度纠偏跟踪", businessType = CustomBusinessType.SAVE)
    public AjaxResult insertJdglProgressCorrectionTrack(
        @Validated(ValidationGroups.Save.class) @RequestBody JdglProgressCorrectionTrack jdglProgressCorrectionTrackParam) {
        jdglProgressCorrectionTrackService.insertJdglProgressCorrectionTrack(jdglProgressCorrectionTrackParam);
        JdglProgressCorrectionTrack jdglProgressCorrectionTrack = new JdglProgressCorrectionTrack();
        jdglProgressCorrectionTrack.setId(jdglProgressCorrectionTrackParam.getId());
        return AjaxResult
            .success(jdglProgressCorrectionTrackService.getJdglProgressCorrectionTrack(jdglProgressCorrectionTrack));
    }

    @PreAuthorize(hasPermi = "jdglProgressCorrectionTrack:add")
    @PostMapping("/batchAdd")
    @CustomLogger(title = "进度管理-进度纠偏跟踪", name = "进度纠偏跟踪", businessType = CustomBusinessType.SAVE)
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
        jdglProgressCorrectionTrackService.updateJdglProgressCorrectionTrack(jdglProgressCorrectionTrackParam);
        JdglProgressCorrectionTrack jdglProgressCorrectionTrack = new JdglProgressCorrectionTrack();
        jdglProgressCorrectionTrack.setId(jdglProgressCorrectionTrackParam.getId());
        return AjaxResult
            .success(jdglProgressCorrectionTrackService.getJdglProgressCorrectionTrack(jdglProgressCorrectionTrack));
    }

    @PreAuthorize(hasPermi = "jdglProgressCorrectionTrack:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateJdglProgressCorrectionTrackList(
        @Validated(ValidationGroups.Update.class) @RequestBody List<JdglProgressCorrectionTrack> jdglProgressCorrectionTrackListParam) {
        return toAjax(jdglProgressCorrectionTrackService
            .updateJdglProgressCorrectionTrackList(jdglProgressCorrectionTrackListParam));
    }

    @PreAuthorize(hasPermi = "jdglProgressCorrectionTrack:remove")
    @PostMapping("/delete")
    @CustomLogger(title = "进度管理-进度纠偏跟踪", name = "进度纠偏跟踪", businessType = CustomBusinessType.DELETE)
    public AjaxResult deleteJdglProgressCorrectionTrack(
        @Validated(ValidationGroups.Delete.class) @RequestBody JdglProgressCorrectionTrack jdglProgressCorrectionTrackParam) {
        return toAjax(
            jdglProgressCorrectionTrackService.deleteJdglProgressCorrectionTrack(jdglProgressCorrectionTrackParam));
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @PreAuthorize(hasPermi = "jdglProgressCorrectionTrack:remove")
    @PostMapping("/{ids}")
    @CustomLogger(title = "进度管理-进度纠偏跟踪", name = "进度纠偏跟踪", businessType = CustomBusinessType.DELETE)
    public AjaxResult deleteJdglProgressCorrectionTrackByPks(@PathVariable Long[] ids) {
        List<Long> jdglProgressCorrectionTrackPkList = Arrays.asList(ids);
        return toAjax(jdglProgressCorrectionTrackService
            .deleteJdglProgressCorrectionTrackByPks(jdglProgressCorrectionTrackPkList));
    }

    @GetMapping("/export")
    @CustomLogger(title = "进度管理-进度纠偏跟踪", name = "进度纠偏跟踪", businessType = CustomBusinessType.EXPORT)
    public void export(HttpServletResponse response, JdglProgressCorrectionTrack jdglProgressCorrectionTrackParam)
        throws IOException {
        List<JdglProgressCorrectionTrack> jdglProgressCorrectionTrackList = jdglProgressCorrectionTrackService
            .getJdglProgressCorrectionTrackList(jdglProgressCorrectionTrackParam);
        ExcelUtils<JdglProgressCorrectionTrack> util = new ExcelUtils<>(JdglProgressCorrectionTrack.class);
        util.exportExcel(response, jdglProgressCorrectionTrackList, DateUtils.getDate());
    }


    /**
     * 每周定时生成追踪数据。（偏差小于0的最新一条纠偏措施制定数据）
     *
     * @return
     */
    @PostMapping("/weekTimerTrack")
    @CustomLogger(title = "进度管理-进度纠偏跟踪", name = "进度纠偏跟踪", businessType = CustomBusinessType.SAVE)
    public AjaxResult weekTimerTrack() {
        jdglProgressCorrectionTrackService.weekTimerTrack();
        return AjaxResult.success();
    }
}
