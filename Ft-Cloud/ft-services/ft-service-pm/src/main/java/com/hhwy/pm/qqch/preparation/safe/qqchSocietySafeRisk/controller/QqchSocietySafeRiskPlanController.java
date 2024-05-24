package com.hhwy.pm.qqch.preparation.safe.qqchSocietySafeRisk.controller;

import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.io.IOException;

import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.pm.qqch.preparation.safe.qqchSafeEnvriRiskManage.vo.QqchSafeEnvriRiskManageVo;
import com.hhwy.pm.qqch.preparation.safe.qqchSocietySafeRisk.domain.QqchSocietySafeRiskPlan;
import com.hhwy.pm.qqch.preparation.safe.qqchSocietySafeRisk.domain.vo.QqchSocietySafeRiskPlanVo;
import com.hhwy.pm.qqch.preparation.safe.qqchSocietySafeRisk.domain.vo.QqchSocietySafeRiskVo;
import com.hhwy.pm.qqch.preparation.safe.qqchSocietySafeRisk.service.IQqchSocietySafeRiskPlanService;
import com.hhwy.pm.qqch.preparation.survey.managemodel.domain.QqchSurveyManageModel;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.commons.io.IOUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.common.security.annotation.PreAuthorize;

/**
 * @author hwj
 * @date 2024-05-23 17:36:41
 * @remark 8.6.1社会安全风险策划（变更后）
 */
@Validated
@RestController
@RequestMapping("/qqchSocietySafeRiskPlan")
public class QqchSocietySafeRiskPlanController extends BaseController {

    @Autowired
    private IQqchSocietySafeRiskPlanService qqchSocietySafeRiskPlanService;

    /**
     * 查询单条
     * @param qqchSocietySafeRiskPlanParam
     * @return
     */
    @PreAuthorize(hasPermi = "qqchSocietySafeRiskPlan:list")
    @GetMapping
    public AjaxResult getQqchSocietySafeRiskPlan(@Validated(ValidationGroups.Get.class) QqchSocietySafeRiskPlan qqchSocietySafeRiskPlanParam) {
        QqchSocietySafeRiskPlan qqchSocietySafeRiskPlan = qqchSocietySafeRiskPlanService.getQqchSocietySafeRiskPlan(qqchSocietySafeRiskPlanParam);
        return AjaxResult.success(qqchSocietySafeRiskPlan);
    }

    @GetMapping("/getList")
    public AjaxResult getList(BigDecimal version) {
        QqchSocietySafeRiskPlan qqchSocietySafeRiskPlanParam = new QqchSocietySafeRiskPlan();
        List<QqchSocietySafeRiskPlan> qqchSocietySafeRiskPlanList = qqchSocietySafeRiskPlanService.getQqchSocietySafeRiskPlanList(qqchSocietySafeRiskPlanParam);
        if (qqchSocietySafeRiskPlanList == null || qqchSocietySafeRiskPlanList.size() == 0) {
            //数据初始化
            this.init();
        }
        QqchSocietySafeRiskPlanVo list = qqchSocietySafeRiskPlanService.getList(version);
        return AjaxResult.success(list);
    }
    /**
     * 查询list
     * @param qqchSocietySafeRiskPlanParam
     * @return
     */
    @PreAuthorize(hasPermi = "qqchSocietySafeRiskPlan:list")
    @GetMapping("/list")
    public AjaxResult getQqchSocietySafeRiskPlanList(@Validated(ValidationGroups.Select.class) QqchSocietySafeRiskPlan qqchSocietySafeRiskPlanParam) {
        List<QqchSocietySafeRiskPlan> qqchSocietySafeRiskPlanList = qqchSocietySafeRiskPlanService.getQqchSocietySafeRiskPlanList(qqchSocietySafeRiskPlanParam);
        if (qqchSocietySafeRiskPlanList == null || qqchSocietySafeRiskPlanList.size() == 0) {
            //数据初始化
            this.init();
        }
        List<QqchSocietySafeRiskPlan> qqchSocietySafeRiskPlanListNew = qqchSocietySafeRiskPlanService.getQqchSocietySafeRiskPlanList(qqchSocietySafeRiskPlanParam);

        return getDataTableAjaxResult(qqchSocietySafeRiskPlanListNew);
    }

    @PreAuthorize(hasPermi = "qqchSocietySafeRiskPlan:add")
    @PostMapping("/add")
    public AjaxResult insertQqchSocietySafeRiskPlan(@Validated(ValidationGroups.Save.class) @RequestBody QqchSocietySafeRiskPlan qqchSocietySafeRiskPlanParam) {
        qqchSocietySafeRiskPlanService.insertQqchSocietySafeRiskPlan(qqchSocietySafeRiskPlanParam);
        return AjaxResult.success(qqchSocietySafeRiskPlanParam);
    }

    @PreAuthorize(hasPermi = "qqchSocietySafeRiskPlan:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchSocietySafeRiskPlanList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchSocietySafeRiskPlan> qqchSocietySafeRiskPlanListParam) {
        qqchSocietySafeRiskPlanService.insertQqchSocietySafeRiskPlanList(qqchSocietySafeRiskPlanListParam);
        return AjaxResult.success(qqchSocietySafeRiskPlanListParam);
    }

    @PreAuthorize(hasPermi = "qqchSocietySafeRiskPlan:update")
    @PostMapping("/update")
    public AjaxResult updateQqchSocietySafeRiskPlan(@Validated(ValidationGroups.Update.class) @RequestBody QqchSocietySafeRiskPlan qqchSocietySafeRiskPlanParam) {
        return toAjax(qqchSocietySafeRiskPlanService.updateQqchSocietySafeRiskPlan(qqchSocietySafeRiskPlanParam));
    }

    @PreAuthorize(hasPermi = "qqchSocietySafeRiskPlan:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchSocietySafeRiskPlanList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchSocietySafeRiskPlan> qqchSocietySafeRiskPlanListParam) {
        return toAjax(qqchSocietySafeRiskPlanService.updateQqchSocietySafeRiskPlanList(qqchSocietySafeRiskPlanListParam));
    }

    @PreAuthorize(hasPermi = "qqchSocietySafeRiskPlan:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchSocietySafeRiskPlan(@Validated(ValidationGroups.Delete.class) @RequestBody QqchSocietySafeRiskPlan qqchSocietySafeRiskPlanParam) {
        return toAjax(qqchSocietySafeRiskPlanService.deleteQqchSocietySafeRiskPlan(qqchSocietySafeRiskPlanParam));
    }

    @PreAuthorize(hasPermi = "qqchSocietySafeRiskPlan:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchSocietySafeRiskPlanByPks(@PathVariable Long[] ids) {
        List<Long> qqchSocietySafeRiskPlanPkList = Arrays.asList(ids);
        return toAjax(qqchSocietySafeRiskPlanService.deleteQqchSocietySafeRiskPlanByPks(qqchSocietySafeRiskPlanPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchSocietySafeRiskPlan qqchSocietySafeRiskPlanParam) throws IOException {
        List<QqchSocietySafeRiskPlan> qqchSocietySafeRiskPlanList = qqchSocietySafeRiskPlanService.getQqchSocietySafeRiskPlanList(qqchSocietySafeRiskPlanParam);
        ExcelUtils<QqchSocietySafeRiskPlan> util = new ExcelUtils<>(QqchSocietySafeRiskPlan.class);
        util.exportExcel(response, qqchSocietySafeRiskPlanList, DateUtils.getDate());
    }

    @PostMapping("/save")
    @CustomLogger(title = "前期策划-前期策划编制-安全策划-公共安全风险管控策划", name = "8.6.1社会安全风险策划" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult insertQqchSocietySafeRiskList(@Validated(ValidationGroups.Save.class) @RequestBody QqchSocietySafeRiskPlanVo vo) {
        qqchSocietySafeRiskPlanService.save(vo);
        return AjaxResult.success();
    }

    /**
     * 初始化数据
     */
    @Transactional
    public void init(){
        try {
            InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("template/8_6_1.json");
            String json = IOUtils.toString(resourceAsStream, StandardCharsets.UTF_8);
            List<QqchSocietySafeRiskPlan> list = JSONObject.parseArray(json, QqchSocietySafeRiskPlan.class);
            qqchSocietySafeRiskPlanService.insertQqchSocietySafeRiskPlanList(list);
        }catch (IOException e){
            throw new RuntimeException("初始化数据失败！");
        }
    }
}
