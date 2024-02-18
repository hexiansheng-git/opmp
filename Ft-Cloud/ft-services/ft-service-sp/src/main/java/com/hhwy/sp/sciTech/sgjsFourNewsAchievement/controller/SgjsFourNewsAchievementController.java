package com.hhwy.sp.sciTech.sgjsFourNewsAchievement.controller;

import java.text.SimpleDateFormat;
import java.util.*;
import java.io.IOException;

import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.sp.common.sgjsAchievementAward.domain.SgjsAchievementAward;
import com.hhwy.sp.common.shjsAuthenticateEvaluate.domain.ShjsAuthenticateEvaluate;
import com.hhwy.utils.dict.DictUtil;
import com.hhwy.utils.excel.FtExcelUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import com.hhwy.sp.sciTech.sgjsFourNewsAchievement.service.ISgjsFourNewsAchievementService;
import com.hhwy.sp.sciTech.sgjsFourNewsAchievement.domain.SgjsFourNewsAchievement;

import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.common.security.annotation.PreAuthorize;

/**
 * @author cjh
 * @date 2024-01-25 10:10:50
 * @remark 科技管理-四新成果管理
 */
@Validated
@RestController
@RequestMapping("/sgjsFourNewsAchievement")
public class SgjsFourNewsAchievementController extends BaseController {

    @Autowired
    private ISgjsFourNewsAchievementService sgjsFourNewsAchievementService;


    @PreAuthorize(hasPermi = "sgjsFourNewsAchievement:list")
    @GetMapping
    public AjaxResult getSgjsFourNewsAchievement(@Validated(ValidationGroups.Get.class) SgjsFourNewsAchievement sgjsFourNewsAchievementParam) {
        SgjsFourNewsAchievement sgjsFourNewsAchievement = sgjsFourNewsAchievementService.getSgjsFourNewsAchievement(sgjsFourNewsAchievementParam);
        return AjaxResult.success(sgjsFourNewsAchievement);
    }

    @PreAuthorize(hasPermi = "sgjsFourNewsAchievement:list")
    @GetMapping("/list")
    public AjaxResult getSgjsFourNewsAchievementList(@Validated(ValidationGroups.Select.class) SgjsFourNewsAchievement sgjsFourNewsAchievementParam) {
        startPage();
        List<SgjsFourNewsAchievement> sgjsFourNewsAchievementList = sgjsFourNewsAchievementService.getSgjsFourNewsAchievementList(sgjsFourNewsAchievementParam);
        return getDataTableAjaxResult(sgjsFourNewsAchievementList);
    }

    @PreAuthorize(hasPermi = "sgjsFourNewsAchievement:add")
    @PostMapping("/add")
    public AjaxResult insertSgjsFourNewsAchievement(@Validated(ValidationGroups.Save.class) @RequestBody SgjsFourNewsAchievement sgjsFourNewsAchievementParam) {
        sgjsFourNewsAchievementService.insertSgjsFourNewsAchievement(sgjsFourNewsAchievementParam);
        return AjaxResult.success(sgjsFourNewsAchievementParam);
    }

    @PreAuthorize(hasPermi = "sgjsFourNewsAchievement:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertSgjsFourNewsAchievementList(@Validated(ValidationGroups.Save.class) @RequestBody List<SgjsFourNewsAchievement> sgjsFourNewsAchievementListParam) {
        sgjsFourNewsAchievementService.insertSgjsFourNewsAchievementList(sgjsFourNewsAchievementListParam);
        return AjaxResult.success(sgjsFourNewsAchievementListParam);
    }

    @PreAuthorize(hasPermi = "sgjsFourNewsAchievement:update")
    @PostMapping("/update")
    public AjaxResult updateSgjsFourNewsAchievement(@Validated(ValidationGroups.Update.class) @RequestBody SgjsFourNewsAchievement sgjsFourNewsAchievementParam) {
        return AjaxResult.success(sgjsFourNewsAchievementService.updateSgjsFourNewsAchievement(sgjsFourNewsAchievementParam));
    }

    @PreAuthorize(hasPermi = "sgjsFourNewsAchievement:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateSgjsFourNewsAchievementList(@Validated(ValidationGroups.Update.class) @RequestBody List<SgjsFourNewsAchievement> sgjsFourNewsAchievementListParam) {
        return toAjax(sgjsFourNewsAchievementService.updateSgjsFourNewsAchievementList(sgjsFourNewsAchievementListParam));
    }

    @PreAuthorize(hasPermi = "sgjsFourNewsAchievement:remove")
    @PostMapping("/delete")
    public AjaxResult deleteSgjsFourNewsAchievement(@Validated(ValidationGroups.Delete.class) @RequestBody SgjsFourNewsAchievement sgjsFourNewsAchievementParam) {
        return toAjax(sgjsFourNewsAchievementService.deleteSgjsFourNewsAchievement(sgjsFourNewsAchievementParam));
    }

    @PreAuthorize(hasPermi = "sgjsFourNewsAchievement:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteSgjsFourNewsAchievementByPks(@PathVariable Long[] ids) {
        List<Long> sgjsFourNewsAchievementPkList = Arrays.asList(ids);
        return toAjax(sgjsFourNewsAchievementService.deleteSgjsFourNewsAchievementByPks(sgjsFourNewsAchievementPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, SgjsFourNewsAchievement sgjsFourNewsAchievementParam) throws IOException {
        String ids = sgjsFourNewsAchievementParam.getIds();
        List<Long> ids4L = new ArrayList<>();
        if(StringUtils.isNotEmpty(ids)) {
            String[] split = ids.split(",");
            for (String s: split) {
                ids4L.add(Long.parseLong(s));
            }
        }
        List<SgjsFourNewsAchievement> sgjsFourNewsAchievementList = null;
        if(CollectionUtils.isNotEmpty(ids4L)) {
            sgjsFourNewsAchievementList = sgjsFourNewsAchievementService.getSgjsFourNewsAchievementList4Ids(ids4L);
        } else {
            sgjsFourNewsAchievementList = sgjsFourNewsAchievementService.getSgjsFourNewsAchievementList(sgjsFourNewsAchievementParam);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 评价结论字典
        LinkedHashMap<String, String> evaluateConclusionDict = DictUtil.getDictDataName("evaluate_conclusion");
        // 奖项类别
        LinkedHashMap<String, String> awardTypeDict = DictUtil.getDictDataName("award_type");

        if(CollectionUtils.isNotEmpty(sgjsFourNewsAchievementList)) {
            for (SgjsFourNewsAchievement sgjsFourNewsAchievement: sgjsFourNewsAchievementList) {
                // 成果奖项
                List<SgjsAchievementAward> sgjsAchievementAwardList = sgjsFourNewsAchievement.getSgjsAchievementAwardList();
                // 鉴定或评价
                List<ShjsAuthenticateEvaluate> shjsAuthenticateEvaluateList = sgjsFourNewsAchievement.getShjsAuthenticateEvaluateList();
                if(CollectionUtils.isNotEmpty(sgjsAchievementAwardList)) {
                    StringBuilder applyAwardStr = new StringBuilder();
                    StringBuilder awardGradeStr = new StringBuilder();
                    StringBuilder awardTypeStr = new StringBuilder();
                    StringBuilder grantUnitStr = new StringBuilder();
                    StringBuilder awardTimeStr = new StringBuilder();
                    for (int i = 0; i < sgjsAchievementAwardList.size(); i++) {
                        String applyAward = sgjsAchievementAwardList.get(i).getApplyAward();
                        String awardGrade = sgjsAchievementAwardList.get(i).getAwardGrade();
                        String awardType = sgjsAchievementAwardList.get(i).getAwardType();
                        String grantUnit = sgjsAchievementAwardList.get(i).getGrantUnit();
                        Date awardTime = sgjsAchievementAwardList.get(i).getAwardTime();
                        String flag = "";
                        if(0 < i) {
                            flag = ",";
                        }
                        applyAwardStr.append(flag).append(applyAward);
                        awardGradeStr.append(flag).append(awardGrade);
                        awardTypeStr.append(flag).append(awardTypeDict.get(awardType));
                        grantUnitStr.append(flag).append(grantUnit);
                        awardTimeStr.append(flag).append(sdf.format(awardTime));
                    }
                    sgjsFourNewsAchievement.setApplyAward(applyAwardStr.toString());
                    sgjsFourNewsAchievement.setAwardGrade(awardGradeStr.toString());
                    sgjsFourNewsAchievement.setAwardType(awardTypeStr.toString());
                    sgjsFourNewsAchievement.setGrantUnit(grantUnitStr.toString());
                    sgjsFourNewsAchievement.setAwardTime(awardTimeStr.toString());
                }
                StringBuilder authenticateUnitStr = new StringBuilder();
                StringBuilder authenticateDateStr = new StringBuilder();
                StringBuilder evaluateConclusionStr = new StringBuilder();
                if(CollectionUtils.isNotEmpty(shjsAuthenticateEvaluateList)) {
                    for (int i = 0; i < shjsAuthenticateEvaluateList.size(); i++) {
                        String authenticateUnit = shjsAuthenticateEvaluateList.get(0).getAuthenticateUnit();
                        Date authenticateDate = shjsAuthenticateEvaluateList.get(0).getAuthenticateDate();
                        String evaluateConclusion = shjsAuthenticateEvaluateList.get(0).getEvaluateConclusion();
                        String flag = "";
                        if(0 < i) {
                            flag = ",";
                        }
                        authenticateUnitStr.append(flag).append(authenticateUnit);
                        authenticateDateStr.append(flag).append(sdf.format(authenticateDate));
                        evaluateConclusionStr.append(flag).append(evaluateConclusionDict.get(evaluateConclusion));
                    }
                    sgjsFourNewsAchievement.setAuthenticateUnit(authenticateUnitStr.toString());
                    sgjsFourNewsAchievement.setAuthenticateDate(authenticateDateStr.toString());
                    sgjsFourNewsAchievement.setEvaluateConclusion(evaluateConclusionStr.toString());
                }
            }
        }
        FtExcelUtil<SgjsFourNewsAchievement> util = new FtExcelUtil<>(SgjsFourNewsAchievement.class);
        util.exportExcel(response, sgjsFourNewsAchievementList, DateUtils.getDate());
    }

    /**
     *
     * 更新流程数据
     * @param id 主键
     * @return  监听器
     */
    @RequestMapping(value ="/listener",method = RequestMethod.POST)
    @Transactional
    public AjaxResult updateTaskStatus(@RequestParam ("id") Long id, @RequestParam ("isPass") String isPass) {
        sgjsFourNewsAchievementService.updateTaskStatus(id, isPass);
        return AjaxResult.success();
    }

    /***
     * 功能描述: 消息发布
     * 作者: fushudong
     * 时间: 2024/2/1
     */
    @RequestMapping("/messagePublic")
    public AjaxResult messagePublic(String message){
        return sgjsFourNewsAchievementService.messagePublic(message);
    }
}
