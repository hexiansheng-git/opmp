package com.hhwy.pm.qqch.preparation.safe.qqchSafeEnvirRiskList.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qqch.preparation.safe.qqchSafeEnvirRiskList.domain.QqchSafeEnvirRiskList;
import com.hhwy.pm.qqch.preparation.safe.qqchSafeEnvirRiskList.domain.QqchSafeEnvirRiskListDetail;
import com.hhwy.pm.qqch.preparation.safe.qqchSafeEnvirRiskList.domain.vo.AssembleDataVo;
import com.hhwy.pm.qqch.preparation.safe.qqchSafeEnvirRiskList.domain.vo.QqchSafeEnvirRiskListVo;
import com.hhwy.pm.qqch.preparation.safe.qqchSafeEnvirRiskList.domain.vo.SafeEnvirRiskListQueryVo;
import com.hhwy.pm.qqch.preparation.safe.qqchSafeEnvirRiskList.service.IQqchSafeEnvirRiskListService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author zq8.8.1环境风险管控
 * @date 2023-08-14 13:56:17
 * @remark
 */
@Validated
@RestController
@RequestMapping("/qqchSafeEnvirRiskList")
public class QqchSafeEnvirRiskListController extends BaseController {

    @Autowired
    private IQqchSafeEnvirRiskListService qqchSafeEnvirRiskListService;


//    @PreAuthorize(hasPermi = "qqchSafeEnvirRiskList:list")
    @GetMapping
    public AjaxResult getQqchSafeEnvirRiskList(@Validated(ValidationGroups.Get.class) QqchSafeEnvirRiskList qqchSafeEnvirRiskListParam) {
        QqchSafeEnvirRiskList qqchSafeEnvirRiskList = qqchSafeEnvirRiskListService.getQqchSafeEnvirRiskList(qqchSafeEnvirRiskListParam);
        return AjaxResult.success(qqchSafeEnvirRiskList);
    }

//    @PreAuthorize(hasPermi = "qqchSafeEnvirRiskList:list")
    @GetMapping("/list")
    public AjaxResult getQqchSafeEnvirRiskListList(@Validated(ValidationGroups.Select.class) QqchSafeEnvirRiskList qqchSafeEnvirRiskListParam) {
        startPage();
        List<QqchSafeEnvirRiskList> qqchSafeEnvirRiskListList = qqchSafeEnvirRiskListService.getQqchSafeEnvirRiskListList(qqchSafeEnvirRiskListParam);
        return getDataTableAjaxResult(qqchSafeEnvirRiskListList);
    }

//    @PreAuthorize(hasPermi = "qqchSafeEnvirRiskList:add")
    @PostMapping("/add")
    public AjaxResult insertQqchSafeEnvirRiskList(@Validated(ValidationGroups.Save.class) @RequestBody QqchSafeEnvirRiskList qqchSafeEnvirRiskListParam) {
        qqchSafeEnvirRiskListService.insertQqchSafeEnvirRiskList(qqchSafeEnvirRiskListParam);
        return AjaxResult.success(qqchSafeEnvirRiskListParam);
    }

//    @PreAuthorize(hasPermi = "qqchSafeEnvirRiskList:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchSafeEnvirRiskListList(@Validated(ValidationGroups.Save.class) @RequestBody QqchSafeEnvirRiskListVo qqchSafeEnvirRiskListVo) {
        qqchSafeEnvirRiskListService.insertQqchSafeEnvirRiskListList(qqchSafeEnvirRiskListVo);
        return AjaxResult.success();
    }

//    @PreAuthorize(hasPermi = "qqchSafeEnvirRiskList:update")
    @PostMapping("/update")
    public AjaxResult updateQqchSafeEnvirRiskList(@Validated(ValidationGroups.Update.class) @RequestBody QqchSafeEnvirRiskList qqchSafeEnvirRiskListParam) {
        return toAjax(qqchSafeEnvirRiskListService.updateQqchSafeEnvirRiskList(qqchSafeEnvirRiskListParam));
    }

//    @PreAuthorize(hasPermi = "qqchSafeEnvirRiskList:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchSafeEnvirRiskListList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchSafeEnvirRiskList> qqchSafeEnvirRiskListListParam) {
        return toAjax(qqchSafeEnvirRiskListService.updateQqchSafeEnvirRiskListList(qqchSafeEnvirRiskListListParam));
    }

//    @PreAuthorize(hasPermi = "qqchSafeEnvirRiskList:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchSafeEnvirRiskList(@Validated(ValidationGroups.Delete.class) @RequestBody QqchSafeEnvirRiskList qqchSafeEnvirRiskListParam) {
        return toAjax(qqchSafeEnvirRiskListService.deleteQqchSafeEnvirRiskList(qqchSafeEnvirRiskListParam));
    }

//    @PreAuthorize(hasPermi = "qqchSafeEnvirRiskList:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchSafeEnvirRiskListByPks(@PathVariable Long[] ids) {
        List<Long> qqchSafeEnvirRiskListPkList = Arrays.asList(ids);
        return toAjax(qqchSafeEnvirRiskListService.deleteQqchSafeEnvirRiskListByPks(qqchSafeEnvirRiskListPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchSafeEnvirRiskList qqchSafeEnvirRiskListParam) throws IOException {
        List<QqchSafeEnvirRiskList> qqchSafeEnvirRiskListList = qqchSafeEnvirRiskListService.getQqchSafeEnvirRiskListList(qqchSafeEnvirRiskListParam);
        ExcelUtils<QqchSafeEnvirRiskList> util = new ExcelUtils<>(QqchSafeEnvirRiskList.class);
        util.exportExcel(response, qqchSafeEnvirRiskListList, DateUtils.getDate());
    }

//    @PreAuthorize(hasAnyPermi = "qqchSafeEnvirRiskList:list")
    @GetMapping("/getList")
    public AjaxResult getList(SafeEnvirRiskListQueryVo queryVo){
        QqchSafeEnvirRiskListVo qqchSafeEnvirRiskListVo = qqchSafeEnvirRiskListService.getList(queryVo);
        return AjaxResult.success(qqchSafeEnvirRiskListVo);
    }

    /**
     * 组装数据
     * @param assembleDataVo
     * @return
     */
    @PostMapping("assembleDataVo")
    public AjaxResult assembleData(@RequestBody AssembleDataVo assembleDataVo) {
        List<QqchSafeEnvirRiskListDetail> detailList = qqchSafeEnvirRiskListService.assembleData(assembleDataVo);
        return AjaxResult.success(detailList);
    }
}
