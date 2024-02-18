package com.hhwy.sp.sciTech.sgjsTechMethod.controller;

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
import com.hhwy.sp.sciTech.sgjsTechMethod.service.ISgjsTechMethodService;
import com.hhwy.sp.sciTech.sgjsTechMethod.domain.SgjsTechMethod;

import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.common.security.annotation.PreAuthorize;

/**
 * @author cjh
 * @date 2024-01-25 10:11:14
 * @remark 科技管理-工艺工法管理
 */
@Validated
@RestController
@RequestMapping("/sgjsTechMethod")
public class SgjsTechMethodController extends BaseController {

    @Autowired
    private ISgjsTechMethodService sgjsTechMethodService;


    @PreAuthorize(hasPermi = "sgjsTechMethod:list")
    @GetMapping
    public AjaxResult getSgjsTechMethod(@Validated(ValidationGroups.Get.class) SgjsTechMethod sgjsTechMethodParam) {
        SgjsTechMethod sgjsTechMethod = sgjsTechMethodService.getSgjsTechMethod(sgjsTechMethodParam);
        return AjaxResult.success(sgjsTechMethod);
    }

    @PreAuthorize(hasPermi = "sgjsTechMethod:list")
    @GetMapping("/list")
    public AjaxResult getSgjsTechMethodList(@Validated(ValidationGroups.Select.class) SgjsTechMethod sgjsTechMethodParam) {
        startPage();
        List<SgjsTechMethod> sgjsTechMethodList = sgjsTechMethodService.getSgjsTechMethodList(sgjsTechMethodParam);
        return getDataTableAjaxResult(sgjsTechMethodList);
    }

    @PreAuthorize(hasPermi = "sgjsTechMethod:add")
    @PostMapping("/add")
    public AjaxResult insertSgjsTechMethod(@Validated(ValidationGroups.Save.class) @RequestBody SgjsTechMethod sgjsTechMethodParam) {
        sgjsTechMethodService.insertSgjsTechMethod(sgjsTechMethodParam);
        return AjaxResult.success(sgjsTechMethodParam);
    }

    @PreAuthorize(hasPermi = "sgjsTechMethod:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertSgjsTechMethodList(@Validated(ValidationGroups.Save.class) @RequestBody List<SgjsTechMethod> sgjsTechMethodListParam) {
        sgjsTechMethodService.insertSgjsTechMethodList(sgjsTechMethodListParam);
        return AjaxResult.success(sgjsTechMethodListParam);
    }

    @PreAuthorize(hasPermi = "sgjsTechMethod:update")
    @PostMapping("/update")
    public AjaxResult updateSgjsTechMethod(@Validated(ValidationGroups.Update.class) @RequestBody SgjsTechMethod sgjsTechMethodParam) {
        return AjaxResult.success(sgjsTechMethodService.updateSgjsTechMethod(sgjsTechMethodParam));
    }

    @PreAuthorize(hasPermi = "sgjsTechMethod:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateSgjsTechMethodList(@Validated(ValidationGroups.Update.class) @RequestBody List<SgjsTechMethod> sgjsTechMethodListParam) {
        return toAjax(sgjsTechMethodService.updateSgjsTechMethodList(sgjsTechMethodListParam));
    }

    @PreAuthorize(hasPermi = "sgjsTechMethod:remove")
    @PostMapping("/delete")
    public AjaxResult deleteSgjsTechMethod(@Validated(ValidationGroups.Delete.class) @RequestBody SgjsTechMethod sgjsTechMethodParam) {
        return toAjax(sgjsTechMethodService.deleteSgjsTechMethod(sgjsTechMethodParam));
    }

    @PreAuthorize(hasPermi = "sgjsTechMethod:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteSgjsTechMethodByPks(@PathVariable Long[] ids) {
        List<Long> sgjsTechMethodPkList = Arrays.asList(ids);
        return toAjax(sgjsTechMethodService.deleteSgjsTechMethodByPks(sgjsTechMethodPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, SgjsTechMethod sgjsTechMethodParam) throws IOException {
        String ids = sgjsTechMethodParam.getIds();
        List<Long> ids4L = new ArrayList<>();
        if(StringUtils.isNotEmpty(ids)) {
            String[] split = ids.split(",");
            for (String s: split) {
                ids4L.add(Long.parseLong(s));
            }
        }
        List<SgjsTechMethod> sgjsTechMethodList = null;
        if(CollectionUtils.isNotEmpty(ids4L)) {
            sgjsTechMethodList = sgjsTechMethodService.getSgjsTechMethodList4ids(ids4L);
        } else {
            sgjsTechMethodList = sgjsTechMethodService.getSgjsTechMethodList(sgjsTechMethodParam);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 评价结论字典
        LinkedHashMap<String, String> evaluateConclusionDict = DictUtil.getDictDataName("evaluate_conclusion");
        // 奖项类别
        LinkedHashMap<String, String> awardTypeDict = DictUtil.getDictDataName("award_type");
        if(CollectionUtils.isNotEmpty(sgjsTechMethodList)) {
            for (SgjsTechMethod sgjsTechMethod: sgjsTechMethodList) {
                // 成果奖项
                List<SgjsAchievementAward> sgjsAchievementAwardList = sgjsTechMethod.getSgjsAchievementAwardList();
                // 鉴定或评价
                List<ShjsAuthenticateEvaluate> shjsAuthenticateEvaluateList = sgjsTechMethod.getShjsAuthenticateEvaluateList();
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
                    sgjsTechMethod.setApplyAward(applyAwardStr.toString());
                    sgjsTechMethod.setAwardGrade(awardGradeStr.toString());
                    sgjsTechMethod.setAwardType(awardTypeStr.toString());
                    sgjsTechMethod.setGrantUnit(grantUnitStr.toString());
                    sgjsTechMethod.setAwardTime(awardTimeStr.toString());
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
                    sgjsTechMethod.setAuthenticateUnit(authenticateUnitStr.toString());
                    sgjsTechMethod.setAuthenticateDate(authenticateDateStr.toString());
                    sgjsTechMethod.setEvaluateConclusion(evaluateConclusionStr.toString());
                }
            }
        }
        FtExcelUtil<SgjsTechMethod> util = new FtExcelUtil<>(SgjsTechMethod.class);
        util.exportExcel(response, sgjsTechMethodList, DateUtils.getDate());
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
        sgjsTechMethodService.updateTaskStatus(id, isPass);
        return AjaxResult.success();
    }

    /***
     * 功能描述: 消息发布
     * 作者: fushudong
     * 时间: 2024/2/1
     */
    @RequestMapping("/messagePublic")
    public AjaxResult messagePublic(String message){
        return sgjsTechMethodService.messagePublic(message);
    }
}
