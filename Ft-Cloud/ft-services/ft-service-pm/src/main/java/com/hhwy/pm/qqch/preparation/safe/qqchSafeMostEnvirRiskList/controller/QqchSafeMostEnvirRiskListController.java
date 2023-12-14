package com.hhwy.pm.qqch.preparation.safe.qqchSafeMostEnvirRiskList.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qqch.preparation.safe.qqchSafeMostEnvirRiskList.domain.QqchSafeMostEnvirRiskList;
import com.hhwy.pm.qqch.preparation.safe.qqchSafeMostEnvirRiskList.domain.vo.QqchSafeMostEnvirRiskListVo;
import com.hhwy.pm.qqch.preparation.safe.qqchSafeMostEnvirRiskList.domain.vo.SafeMostEnvirRiskListQueryVo;
import com.hhwy.pm.qqch.preparation.safe.qqchSafeMostEnvirRiskList.service.IQqchSafeMostEnvirRiskListService;
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

/**
 * @author zq
 * @date 2023-08-14 14:04:02
 * @remark 8.8.2 重大环境风险管控
 */
@Validated
@RestController
@RequestMapping("/qqchSafeMostEnvirRiskList")
public class QqchSafeMostEnvirRiskListController extends BaseController {

    @Autowired
    private IQqchSafeMostEnvirRiskListService qqchSafeMostEnvirRiskListService;


    @GetMapping
    public AjaxResult getQqchSafeMostEnvirRiskList(@Validated(ValidationGroups.Get.class) QqchSafeMostEnvirRiskList qqchSafeMostEnvirRiskListParam) {
        QqchSafeMostEnvirRiskList qqchSafeMostEnvirRiskList = qqchSafeMostEnvirRiskListService.getQqchSafeMostEnvirRiskList(qqchSafeMostEnvirRiskListParam);
        return AjaxResult.success(qqchSafeMostEnvirRiskList);
    }

    @GetMapping("/list")
    public AjaxResult getQqchSafeMostEnvirRiskListList(@Validated(ValidationGroups.Select.class) QqchSafeMostEnvirRiskList qqchSafeMostEnvirRiskListParam) {
        startPage();
        List<QqchSafeMostEnvirRiskList> qqchSafeMostEnvirRiskListList = qqchSafeMostEnvirRiskListService.getQqchSafeMostEnvirRiskListList(qqchSafeMostEnvirRiskListParam);
        return getDataTableAjaxResult(qqchSafeMostEnvirRiskListList);
    }

    @PostMapping("/add")
    public AjaxResult insertQqchSafeMostEnvirRiskList(@Validated(ValidationGroups.Save.class) @RequestBody QqchSafeMostEnvirRiskList qqchSafeMostEnvirRiskListParam) {
        qqchSafeMostEnvirRiskListService.insertQqchSafeMostEnvirRiskList(qqchSafeMostEnvirRiskListParam);
        return AjaxResult.success(qqchSafeMostEnvirRiskListParam);
    }

    @PostMapping("/batchAdd")
    @CustomLogger(title = "前期策划-前期策划编制-安全策划-8.8 环境风险管控策划", name = "\n" +
            "8.8.2 重大环境风险管控" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult insertQqchSafeMostEnvirRiskListList(@Validated(ValidationGroups.Save.class) @RequestBody QqchSafeMostEnvirRiskListVo qqchSafeMostEnvirRiskListVo) {
        qqchSafeMostEnvirRiskListService.insertQqchSafeMostEnvirRiskListList(qqchSafeMostEnvirRiskListVo);
        return AjaxResult.success();
    }

    @PostMapping("/update")
    public AjaxResult updateQqchSafeMostEnvirRiskList(@Validated(ValidationGroups.Update.class) @RequestBody QqchSafeMostEnvirRiskList qqchSafeMostEnvirRiskListParam) {
        return toAjax(qqchSafeMostEnvirRiskListService.updateQqchSafeMostEnvirRiskList(qqchSafeMostEnvirRiskListParam));
    }

    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchSafeMostEnvirRiskListList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchSafeMostEnvirRiskList> qqchSafeMostEnvirRiskListListParam) {
        return toAjax(qqchSafeMostEnvirRiskListService.updateQqchSafeMostEnvirRiskListList(qqchSafeMostEnvirRiskListListParam));
    }

    @PostMapping("/delete")
    public AjaxResult deleteQqchSafeMostEnvirRiskList(@Validated(ValidationGroups.Delete.class) @RequestBody QqchSafeMostEnvirRiskList qqchSafeMostEnvirRiskListParam) {
        return toAjax(qqchSafeMostEnvirRiskListService.deleteQqchSafeMostEnvirRiskList(qqchSafeMostEnvirRiskListParam));
    }

    @PostMapping("/{ids}")
    public AjaxResult deleteQqchSafeMostEnvirRiskListByPks(@PathVariable Long[] ids) {
        List<Long> qqchSafeMostEnvirRiskListPkList = Arrays.asList(ids);
        return toAjax(qqchSafeMostEnvirRiskListService.deleteQqchSafeMostEnvirRiskListByPks(qqchSafeMostEnvirRiskListPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchSafeMostEnvirRiskList qqchSafeMostEnvirRiskListParam) throws IOException {
        List<QqchSafeMostEnvirRiskList> qqchSafeMostEnvirRiskListList = qqchSafeMostEnvirRiskListService.getQqchSafeMostEnvirRiskListList(qqchSafeMostEnvirRiskListParam);
        ExcelUtils<QqchSafeMostEnvirRiskList> util = new ExcelUtils<>(QqchSafeMostEnvirRiskList.class);
        util.exportExcel(response, qqchSafeMostEnvirRiskListList, DateUtils.getDate());
    }

    @GetMapping("/getList")
    public AjaxResult getList(SafeMostEnvirRiskListQueryVo queryVo){
        QqchSafeMostEnvirRiskListVo qqchSafeEnvirRiskListVo = qqchSafeMostEnvirRiskListService.getList(queryVo);
        return AjaxResult.success(qqchSafeEnvirRiskListVo);
    }
}
