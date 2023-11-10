package com.hhwy.pm.qqch.qqchChange.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.enums.FlowEnum;
import com.hhwy.pm.common.FlowInfoSearchUtil;
import com.hhwy.pm.qqch.qqchChange.domain.QqchChange;
import com.hhwy.pm.qqch.qqchChange.service.IQqchChangeService;
import com.hhwy.pm.qqch.qqchChange.vo.QqchChangeVo;
import com.hhwy.system.api.domain.SysMenu;
import com.hhwy.system.api.domain.SysUser;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * 前期策划变更
 * @author wk
 * @date 2023-11-06 17:41:43
 * @remark
 */
@Validated
@RestController
@RequestMapping("/qqchChange")
public class QqchChangeController extends BaseController {

    @Autowired
    private IQqchChangeService qqchChangeService;

    @PreAuthorize(hasPermi = "qqchChange:list")
    @PostMapping("/list")
    public AjaxResult getQqchChangeList(@RequestBody @Validated(ValidationGroups.Select.class) QqchChange qqchChangeParam) {
        startPage();
        List<QqchChange> qqchChangeList = qqchChangeService.list(qqchChangeParam);
        FlowInfoSearchUtil.getFlowInfo(qqchChangeList,FlowEnum.QQCH_CHANGE);
        return getDataTableAjaxResult(qqchChangeList);
    }

    /**
     * 返回调整明细
     * @return
     */
    @PreAuthorize(hasPermi = "qqchChange:adjust")
    @GetMapping("/adjust")
    public AjaxResult adjust(){
        QqchChangeVo vo = qqchChangeService.adjustDetail();
        FlowInfoSearchUtil.getFlowInfo(vo,FlowEnum.QQCH_CHANGE);
        return AjaxResult.success(vo);
    }

    @GetMapping("/detail")
    public AjaxResult detail(Long mainId){
        QqchChangeVo vo = qqchChangeService.detail(mainId);
        FlowInfoSearchUtil.getFlowInfo(vo,FlowEnum.QQCH_CHANGE);
        return AjaxResult.success(vo);
    }

    @PreAuthorize(hasPermi = "qqchChange:add")
    @PostMapping("/save")
    public AjaxResult save(@RequestBody QqchChangeVo vo) {
        qqchChangeService.save(vo);
        return AjaxResult.success("",vo.getId());
    }

    @PreAuthorize(hasPermi = "qqchChange:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchChange(@Validated(ValidationGroups.Delete.class) @RequestBody QqchChange qqchChangeParam) {
        return toAjax(qqchChangeService.deleteQqchChange(qqchChangeParam));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchChange qqchChangeParam) throws IOException {
        List<QqchChange> qqchChangeList = qqchChangeService.getQqchChangeList(qqchChangeParam);
        ExcelUtils<QqchChange> util = new ExcelUtils<>(QqchChange.class);
        util.exportExcel(response, qqchChangeList, DateUtils.getDate());
    }

    /**
     * 权限菜单
     * @param mainId
     * @param authFlag
     * @return
     */
    @GetMapping("/menu/qqch")
    public AjaxResult authMenuList(@RequestParam Long mainId,@RequestParam String authFlag) {
        List<SysMenu> list = qqchChangeService.authMenuList(mainId,authFlag);
        return AjaxResult.success(list);
    }
}
