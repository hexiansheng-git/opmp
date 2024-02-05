package com.hhwy.sd.achievementReview.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.sd.achievementReview.domain.KcsjAchievement;
import com.hhwy.sd.achievementReview.domain.vo.AchievementQueryVo;
import com.hhwy.sd.achievementReview.domain.vo.AchievementVo;
import com.hhwy.sd.achievementReview.service.IKcsjAchievementService;
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
 * @date 2024-02-05 09:04:08
 * @remark 勘察设计成果评审-成果
 */
@Validated
@RestController
@RequestMapping("/kcsjAchievement")
public class KcsjAchievementController extends BaseController {

    @Autowired
    private IKcsjAchievementService kcsjAchievementService;


    @PreAuthorize(hasPermi = "kcsjAchievement:list")
    @GetMapping
    public AjaxResult getKcsjAchievement(@Validated(ValidationGroups.Get.class) KcsjAchievement kcsjAchievementParam) {
        KcsjAchievement kcsjAchievement = kcsjAchievementService.getKcsjAchievement(kcsjAchievementParam);
        return AjaxResult.success(kcsjAchievement);
    }

    /**
     * 台账
     * @param queryVo
     * @return
     */
    @PreAuthorize(hasPermi = "kcsjAchievement:list")
    @GetMapping("/list")
    public AjaxResult getKcsjAchievementList(AchievementQueryVo queryVo) {
        startPage();
        List<KcsjAchievement> kcsjAchievementList = kcsjAchievementService.getKcsjAchievementList(queryVo);
        return getDataTableAjaxResult(kcsjAchievementList);
    }

    /**
     * 保存
     * @param achievementVo
     * @return
     */
    @PreAuthorize(hasPermi = "kcsjAchievement:save")
    @PostMapping("/save")
    public AjaxResult save(@RequestBody AchievementVo achievementVo){
        kcsjAchievementService.save(achievementVo);
        return AjaxResult.success();
    }

    @PreAuthorize(hasPermi = "kcsjAchievement:add")
    @PostMapping("/add")
    public AjaxResult insertKcsjAchievement(@Validated(ValidationGroups.Save.class) @RequestBody KcsjAchievement kcsjAchievementParam) {
        kcsjAchievementService.insertKcsjAchievement(kcsjAchievementParam);
        return AjaxResult.success(kcsjAchievementParam);
    }

    @PreAuthorize(hasPermi = "kcsjAchievement:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertKcsjAchievementList(@Validated(ValidationGroups.Save.class) @RequestBody List<KcsjAchievement> kcsjAchievementListParam) {
        kcsjAchievementService.insertKcsjAchievementList(kcsjAchievementListParam);
        return AjaxResult.success(kcsjAchievementListParam);
    }

    @PreAuthorize(hasPermi = "kcsjAchievement:update")
    @PostMapping("/update")
    public AjaxResult updateKcsjAchievement(@Validated(ValidationGroups.Update.class) @RequestBody KcsjAchievement kcsjAchievementParam) {
        return toAjax(kcsjAchievementService.updateKcsjAchievement(kcsjAchievementParam));
    }

    @PreAuthorize(hasPermi = "kcsjAchievement:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateKcsjAchievementList(@Validated(ValidationGroups.Update.class) @RequestBody List<KcsjAchievement> kcsjAchievementListParam) {
        return toAjax(kcsjAchievementService.updateKcsjAchievementList(kcsjAchievementListParam));
    }

    @PreAuthorize(hasPermi = "kcsjAchievement:remove")
    @PostMapping("/delete")
    public AjaxResult deleteKcsjAchievement(@Validated(ValidationGroups.Delete.class) @RequestBody KcsjAchievement kcsjAchievementParam) {
        return toAjax(kcsjAchievementService.deleteKcsjAchievement(kcsjAchievementParam));
    }

    @PreAuthorize(hasPermi = "kcsjAchievement:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteKcsjAchievementByPks(@PathVariable Long[] ids) {
        List<Long> kcsjAchievementPkList = Arrays.asList(ids);
        return toAjax(kcsjAchievementService.deleteKcsjAchievementByPks(kcsjAchievementPkList));
    }

    /**
     * 导入
     * @param file
     * @return
     */
    @PreAuthorize(hasPermi = "kcsjDisclosureRecord:import")
    @PostMapping("/importData")
    public AjaxResult importData(@RequestPart("file") MultipartFile file){
        FtExcelUtil<KcsjAchievement> util = new FtExcelUtil<>(KcsjAchievement.class);
        try {
            InputStream inputStream = file.getInputStream();
            List<KcsjAchievement> achievementList = util.importExcel(inputStream);
            achievementList.stream().forEach(o -> {
                o.setId(IdWorker.createId());
                o.setIsAdd("1");
            });
            return AjaxResult.success(achievementList);
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
    @PostMapping("/export")
    public void export(HttpServletResponse response,@RequestBody AchievementQueryVo queryVo) throws IOException {
        List<Long> ids = queryVo.getIds();
        List<KcsjAchievement> kcsjAchievementList;
        if(CollectionUtils.isEmpty(ids)){
            kcsjAchievementList = kcsjAchievementService.getKcsjAchievementList(queryVo);
        }else {
            kcsjAchievementList = kcsjAchievementService.getListByIds(ids);
        }
        FtExcelUtil<KcsjAchievement> util = new FtExcelUtil<>(KcsjAchievement.class);
        util.exportExcel(response, kcsjAchievementList, DateUtils.getDate());
    }
}
