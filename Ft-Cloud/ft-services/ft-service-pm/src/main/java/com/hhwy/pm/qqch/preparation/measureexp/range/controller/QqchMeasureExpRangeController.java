package com.hhwy.pm.qqch.preparation.measureexp.range.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.common.domain.CompileEntity;
import com.hhwy.pm.qqch.preparation.measureexp.range.domain.QqchMeasureExpPerson;
import com.hhwy.pm.qqch.preparation.measureexp.range.domain.QqchMeasureExpRange;
import com.hhwy.pm.qqch.preparation.measureexp.range.domain.QqchMeasureOrg;
import com.hhwy.pm.qqch.preparation.measureexp.range.dto.QqchMeasureExpDTO;
import com.hhwy.pm.qqch.preparation.measureexp.range.service.IQqchMeasureExpPersonService;
import com.hhwy.pm.qqch.preparation.measureexp.range.service.IQqchMeasureExpRangeService;
import com.hhwy.pm.qqch.preparation.measureexp.range.service.IQqchMeasureOrgService;
import com.hhwy.pm.qqch.preparation.measureexp.range.service.IQqchMeasureService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mls
 * @date 2023-07-25 18:01:34
 * @remark
 */
@Validated
@RestController
@RequestMapping("/qqchMeasureExpRange")
public class QqchMeasureExpRangeController extends BaseController {

    @Resource
    private IQqchMeasureExpRangeService qqchMeasureExpRangeService;
    @Resource
    private IQqchMeasureService measureService;

    @Resource
    private IQqchReviewService reviewService;

    @Resource
    private IQqchMeasureOrgService orgService;

    @Resource
    private IQqchMeasureExpPersonService personService;


//    @PreAuthorize(hasPermi = "qqchMeasureExpRange:list")
    @GetMapping
    public AjaxResult getQqchMeasureExpRange(@Validated(ValidationGroups.Get.class) QqchMeasureExpRange qqchMeasureExpRangeParam) {
        QqchMeasureExpRange qqchMeasureExpRange = qqchMeasureExpRangeService.getQqchMeasureExpRange(qqchMeasureExpRangeParam);
        return AjaxResult.success(qqchMeasureExpRange);
    }

    @GetMapping("/list")
    public AjaxResult getQqchMeasureExpRangeList(@Validated(ValidationGroups.Select.class) QqchMeasureExpRange dto) {
        Map<String, Object> res = new HashMap<>();
        CompileEntity compileEntity = new CompileEntity();
        compileEntity.setVersion(dto.getVersion());
        compileEntity.setStageIdentity(reviewService.getStage());

        // 组织模式
        List<QqchMeasureOrg> measureOrgListByVersion = orgService.getQqchMeasureOrgListByVersion(new QqchMeasureOrg());
        // 工作范围
        List<QqchMeasureExpRange> measureExpRangeList = qqchMeasureExpRangeService.getQqchMeasureExpRangeListByVersion(dto);
        // 人员配置
        QqchMeasureExpPerson wherePer = new QqchMeasureExpPerson();
        wherePer.setDataType(dto.getDataType());
        List<QqchMeasureExpPerson> measureExpPersonList = personService.getQqchMeasureExpPersonListByVersionCode(CompileEntity.dealListDto(dto.getVersion(), wherePer));
        
        res.put("org", CollectionUtils.isEmpty(measureOrgListByVersion) ? new QqchMeasureExpRange() : measureOrgListByVersion.get(0));
        res.put("expRangeList", measureExpRangeList);
        res.put("personList", measureExpPersonList);
        compileEntity.setDto(res);
        return AjaxResult.success(compileEntity);
    }

//    @PreAuthorize(hasPermi = "qqchMeasureExpRange:add")
    @PostMapping("/save")
    public AjaxResult save(@Validated(ValidationGroups.Save.class) @RequestBody QqchMeasureExpDTO expVO) {
        measureService.saveAll(expVO);
        return AjaxResult.success("操作成功");
    }


//    @PreAuthorize(hasPermi = "qqchMeasureExpRange:add")
    @PostMapping("/add")
    public AjaxResult insertQqchMeasureExpRange(@Validated(ValidationGroups.Save.class) @RequestBody QqchMeasureExpRange qqchMeasureExpRangeParam) {
        qqchMeasureExpRangeService.insertQqchMeasureExpRange(qqchMeasureExpRangeParam);
        return AjaxResult.success(qqchMeasureExpRangeParam);
    }

//    @PreAuthorize(hasPermi = "qqchMeasureExpRange:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchMeasureExpRangeList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchMeasureExpRange> qqchMeasureExpRangeListParam) {
        qqchMeasureExpRangeService.insertQqchMeasureExpRangeList(qqchMeasureExpRangeListParam);
        return AjaxResult.success(qqchMeasureExpRangeListParam);
    }

//    @PreAuthorize(hasPermi = "qqchMeasureExpRange:update")
    @PostMapping("/update")
    public AjaxResult updateQqchMeasureExpRange(@Validated(ValidationGroups.Update.class) @RequestBody QqchMeasureExpRange qqchMeasureExpRangeParam) {
        return toAjax(qqchMeasureExpRangeService.updateQqchMeasureExpRange(qqchMeasureExpRangeParam));
    }

//    @PreAuthorize(hasPermi = "qqchMeasureExpRange:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchMeasureExpRangeList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchMeasureExpRange> qqchMeasureExpRangeListParam) {
        return toAjax(qqchMeasureExpRangeService.updateQqchMeasureExpRangeList(qqchMeasureExpRangeListParam));
    }

//    @PreAuthorize(hasPermi = "qqchMeasureExpRange:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchMeasureExpRange(@Validated(ValidationGroups.Delete.class) @RequestBody QqchMeasureExpRange qqchMeasureExpRangeParam) {
        return toAjax(qqchMeasureExpRangeService.deleteQqchMeasureExpRange(qqchMeasureExpRangeParam));
    }

//    @PreAuthorize(hasPermi = "qqchMeasureExpRange:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchMeasureExpRangeByPks(@PathVariable Long[] ids) {
        List<Long> qqchMeasureExpRangePkList = Arrays.asList(ids);
        return toAjax(qqchMeasureExpRangeService.deleteQqchMeasureExpRangeByPks(qqchMeasureExpRangePkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchMeasureExpRange qqchMeasureExpRangeParam) throws IOException {
        List<QqchMeasureExpRange> qqchMeasureExpRangeList = qqchMeasureExpRangeService.getQqchMeasureExpRangeList(qqchMeasureExpRangeParam);
        ExcelUtils<QqchMeasureExpRange> util = new ExcelUtils<>(QqchMeasureExpRange.class);
        util.exportExcel(response, qqchMeasureExpRangeList, DateUtils.getDate());
    }
}
