package com.hhwy.pm.qqch.preparation.technique.scheme.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.technique.scheme.domain.QqchConstructionList;
import com.hhwy.pm.qqch.preparation.technique.scheme.service.IQqchConstructionListService;
import com.hhwy.utils.validation.ValidationGroups;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author zhenglili
 * @date 2023-07-13 14:27:14
 * @remark 3.4.2施工方案清单
 */
@Validated
@RestController
@RequestMapping("/qqchConstructionList")
public class QqchConstructionListController extends BaseController {

    @Autowired
    private IQqchConstructionListService qqchConstructionListService;

    @PreAuthorize(hasPermi = "qqchConstructionList:list")
    @GetMapping("/getList")
    public AjaxResult getList(
        @Validated(ValidationGroups.Select.class) @RequestBody QqchConstructionList qqchConstructionListParam) {
        List<QqchConstructionList> qqchConstructionListList = qqchConstructionListService
            .getQqchConstructionListList(qqchConstructionListParam);
        return AjaxResult.success(qqchConstructionListList);
    }

    @PreAuthorize(hasPermi = "qqchConstructionList:add")
    @PostMapping("/batchSave")
    public AjaxResult batchSave(
        @Validated(ValidationGroups.Save.class) @RequestBody List<QqchConstructionList> qqchConstructionListListParam) {
        qqchConstructionListService.batchSave(qqchConstructionListListParam);
        return AjaxResult.success();
    }

    @PreAuthorize(hasPermi = "qqchConstructionList:remove")
    @PostMapping("/remove")
    public AjaxResult deleteQqchConstructionListByPks(Long[] ids) {
        List<Long> qqchConstructionListPkList = Arrays.asList(ids);
        return toAjax(qqchConstructionListService.deleteQqchConstructionListByPks(qqchConstructionListPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchConstructionList qqchConstructionListParam)
        throws IOException {
        List<QqchConstructionList> qqchConstructionListList = qqchConstructionListService
            .getQqchConstructionListList(qqchConstructionListParam);
        ExcelUtils<QqchConstructionList> util = new ExcelUtils<>(QqchConstructionList.class);
        util.exportExcel(response, qqchConstructionListList, DateUtils.getDate());
    }

    /**
     * 导入
     *
     * @param file
     * @return
     */
    @PostMapping("/importExcel")
    public AjaxResult importExcel(@RequestPart("file") MultipartFile file) {
        ExcelUtils<QqchConstructionList> util = new ExcelUtils<>(QqchConstructionList.class);
        try {
            InputStream inputStream = file.getInputStream();
            List<QqchConstructionList> list = util.importExcel(inputStream);
            return AjaxResult.success(list);
        } catch (Exception e) {
            throw new RuntimeException("导入失败！");
        }
    }
}
