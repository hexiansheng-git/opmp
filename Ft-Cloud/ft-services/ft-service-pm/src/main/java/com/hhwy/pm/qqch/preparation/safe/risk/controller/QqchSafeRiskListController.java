package com.hhwy.pm.qqch.preparation.safe.risk.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qqch.preparation.safe.risk.domain.QqchSafeRiskList;
import com.hhwy.pm.qqch.preparation.safe.risk.domain.QqchSafeRiskListDetail;
import com.hhwy.pm.qqch.preparation.safe.risk.domain.vo.QqchSafeRiskListVo;
import com.hhwy.pm.qqch.preparation.safe.risk.domain.vo.SafeRiskAssembleDataVo;
import com.hhwy.pm.qqch.preparation.safe.risk.domain.vo.SafeRiskListQueryVo;
import com.hhwy.pm.qqch.preparation.safe.risk.service.IQqchSafeRiskListService;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
//import com.hhwy.common.security.annotation.PreAuthorize;

/**
 * @author zq
 * @date 2023-08-11 13:41:25
 * @remark 8.2.1安全风险清单
 */
@Validated
@RestController
@RequestMapping("/qqchSafeRiskList")
public class QqchSafeRiskListController extends BaseController {

    @Autowired
    private IQqchSafeRiskListService qqchSafeRiskListService;


//    @PreAuthorize(hasPermi = "qqchSafeRiskList:list")
    @GetMapping
    public AjaxResult getQqchSafeRiskList(@Validated(ValidationGroups.Get.class) QqchSafeRiskList qqchSafeRiskListParam) {
        QqchSafeRiskList qqchSafeRiskList = qqchSafeRiskListService.getQqchSafeRiskList(qqchSafeRiskListParam);
        return AjaxResult.success(qqchSafeRiskList);
    }

//    @PreAuthorize(hasPermi = "qqchSafeRiskList:list")
    @GetMapping("/list")
    public AjaxResult getQqchSafeRiskListList(@Validated(ValidationGroups.Select.class) QqchSafeRiskList qqchSafeRiskListParam) {
        startPage();
        List<QqchSafeRiskList> qqchSafeRiskListList = qqchSafeRiskListService.getQqchSafeRiskListList(qqchSafeRiskListParam);
        return getDataTableAjaxResult(qqchSafeRiskListList);
    }

//    @PreAuthorize(hasPermi = "qqchSafeRiskList:add")
    @PostMapping("/add")
    public AjaxResult insertQqchSafeRiskList(@Validated(ValidationGroups.Save.class) @RequestBody QqchSafeRiskList qqchSafeRiskListParam) {
        qqchSafeRiskListService.insertQqchSafeRiskList(qqchSafeRiskListParam);
        return AjaxResult.success(qqchSafeRiskListParam);
    }

//    @PreAuthorize(hasPermi = "qqchSafeRiskList:add")
    @PostMapping("/batchAdd")
    @CustomLogger(title = "前期策划-前期策划编制-安全策划-8.2 安全风险管控策划", name = "\n" +
            "8.2.1 安全风险清单/8.2.2 重大安全风险清单" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult insertQqchSafeRiskListList(@Validated(ValidationGroups.Save.class) @RequestBody QqchSafeRiskListVo qqchSafeRiskListVo) {
        qqchSafeRiskListService.insertQqchSafeRiskListList(qqchSafeRiskListVo);
        return AjaxResult.success();
    }

//    @PreAuthorize(hasPermi = "qqchSafeRiskList:update")
    @PostMapping("/update")
    public AjaxResult updateQqchSafeRiskList(@Validated(ValidationGroups.Update.class) @RequestBody QqchSafeRiskList qqchSafeRiskListParam) {
        return toAjax(qqchSafeRiskListService.updateQqchSafeRiskList(qqchSafeRiskListParam));
    }

//    @PreAuthorize(hasPermi = "qqchSafeRiskList:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchSafeRiskListList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchSafeRiskList> qqchSafeRiskListListParam) {
        return toAjax(qqchSafeRiskListService.updateQqchSafeRiskListList(qqchSafeRiskListListParam));
    }

//    @PreAuthorize(hasPermi = "qqchSafeRiskList:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchSafeRiskList(@Validated(ValidationGroups.Delete.class) @RequestBody QqchSafeRiskList qqchSafeRiskListParam) {
        return toAjax(qqchSafeRiskListService.deleteQqchSafeRiskList(qqchSafeRiskListParam));
    }

//    @PreAuthorize(hasPermi = "qqchSafeRiskList:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchSafeRiskListByPks(@PathVariable Long[] ids) {
        List<Long> qqchSafeRiskListPkList = Arrays.asList(ids);
        return toAjax(qqchSafeRiskListService.deleteQqchSafeRiskListByPks(qqchSafeRiskListPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchSafeRiskList qqchSafeRiskListParam) throws IOException {
        List<QqchSafeRiskList> qqchSafeRiskListList = qqchSafeRiskListService.getQqchSafeRiskListList(qqchSafeRiskListParam);
        ExcelUtils<QqchSafeRiskList> util = new ExcelUtils<>(QqchSafeRiskList.class);
        util.exportExcel(response, qqchSafeRiskListList, DateUtils.getDate());
    }

    /**
     * 列表
     *
     * @param queryVo
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchSafeRiskList:list")
    @GetMapping("/getList")
    public AjaxResult getList(@Validated(ValidationGroups.Select.class) SafeRiskListQueryVo queryVo) {
         return AjaxResult.success(qqchSafeRiskListService.getList(queryVo));
    }

    /**
     * 组装数据
     * @param assembleDataVo
     * @return
     */
    @PostMapping("assembleDataVo")
    public AjaxResult assembleData(@RequestBody SafeRiskAssembleDataVo assembleDataVo) {
        List<QqchSafeRiskListDetail> detailList = qqchSafeRiskListService.assembleData(assembleDataVo);
        return AjaxResult.success(detailList);
    }
}
