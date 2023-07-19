package com.hhwy.pm.xmsl.wbs.controller;

import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.xmsl.wbs.domain.XmslWbs;
import com.hhwy.pm.xmsl.wbs.dto.XmslWbsDto;
import com.hhwy.pm.xmsl.wbs.service.IXmslWbsService;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.utils.validation.ValidationUtil;
import org.flowable.task.service.delegate.DelegateTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * wbs
 * @author wk
 * @date 2023-07-13 18:13:13
 * @remark
 */
@Validated
@RestController
@RequestMapping("/xmslWbs")
public class XmslWbsController extends BaseController {
    @Autowired
    private IXmslWbsService xmslWbsService;

    @PreAuthorize(hasPermi = "xmslWbs:list")
    @PostMapping
    public AjaxResult getXmslWbs(@Validated(ValidationGroups.Get.class) @RequestBody XmslWbs xmslWbsParam) {
        XmslWbs xmslWbs = xmslWbsService.getXmslWbs(xmslWbsParam);
        return AjaxResult.success(xmslWbs);
    }

    @PreAuthorize(hasPermi = "xmslWbs:list")
    @PostMapping("/list")
    public AjaxResult getXmslWbsList(@Validated(ValidationGroups.Select.class) @RequestBody XmslWbs wbs) {
        Map map = xmslWbsService.listData(wbs);
        return AjaxResult.success(map);
    }

    @PreAuthorize(hasPermi = "xmslWbs:add")
    @PostMapping("/save")
    public AjaxResult save(@RequestBody XmslWbsDto dto) {
        if(dto.getSubmitFlag() != null && dto.getSubmitFlag() == 1)
            ValidationUtil.getValidator().validate(dto,ValidationGroups.Save.class);
        xmslWbsService.save(dto);
        return AjaxResult.success();
    }

    @PreAuthorize(hasPermi = "xmslWbs:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteXmslWbsByPks(@PathVariable Long[] ids) {
        List<Long> xmslWbsPkList = Arrays.asList(ids);
        return toAjax(xmslWbsService.deleteXmslWbsByPks(xmslWbsPkList));
    }

//    @GetMapping("/export")
//    public void export(HttpServletResponse response, XmslWbs xmslWbsParam) throws IOException {
//        List<XmslWbs> xmslWbsList = xmslWbsService.getXmslWbsListByTname(xmslWbsParam);
//        ExcelUtils<XmslWbs> util = new ExcelUtils<>(XmslWbs.class);
//        util.exportExcel(response, xmslWbsList, DateUtils.getDate());
//    }

}
