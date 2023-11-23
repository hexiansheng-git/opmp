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
import com.hhwy.pm.qqch.sgch.dataShare.DataShareDevicePlanService;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractInfo;
import com.hhwy.pm.xmsl.contractInfo.service.IXmslContractInfoService;
import com.hhwy.pm.xmsl.project.domain.vo.ProjectBasicInfo;
import com.hhwy.pm.xmsl.project.service.IXmslProjectBasicInfoService;
import com.hhwy.system.api.domain.SysMenu;
import com.hhwy.system.api.domain.SysUser;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.validation.ValidationGroups;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    @Autowired
    private IXmslContractInfoService contractInfoService;
    @Autowired
    private IXmslProjectBasicInfoService projectBasicInfoService;
    @Autowired
    private DataShareDevicePlanService dataShareDevicePlanService;

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
    public AjaxResult detail(Long id){
        QqchChangeVo vo = qqchChangeService.detail(id);
        FlowInfoSearchUtil.getFlowInfo(vo,FlowEnum.QQCH_CHANGE);
        return AjaxResult.success(vo);
    }

    @PreAuthorize(hasPermi = "qqchChange:add")
    @PostMapping("/save")
    public AjaxResult save(@RequestBody QqchChangeVo vo) {
        qqchChangeService.save(vo);
        Map resuMap = new HashMap<>();
        resuMap.put("id",vo.getId());
        if(!StringUtils.equals(vo.getSubmitFlag(),"1"))
            return AjaxResult.success("",resuMap);
        return AjaxResult.success("",resuMap);


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

    /**
     * 编制人编辑节点提交触发
     * 修改编制完成数量
     * @param businessId
     * @return
     */
    @PostMapping("/editFinishListener")
    public AjaxResult editFinishListener(@RequestParam("id") Long businessId){
        qqchChangeService.editingFinishFlow(businessId);
        return AjaxResult.success();
    }

    /**
     * 单个评审人节点提交触发
     * 修改编制完成数量
     * @param businessId
     * @return
     */
    @PostMapping("/reviewFinishListener")
    public AjaxResult reviewFinishListener(@RequestParam("id") Long businessId){
        qqchChangeService.reviewFinishFlow(businessId);
        return AjaxResult.success();
    }

    /**
     * 单个评审人节点提交触发
     * 修改编制完成数量
     * @param businessId
     * @return
     */
    @PostMapping("/reviewAllFinishListener")
    public AjaxResult reviewAllFinishListener(@RequestParam("id") Long businessId){
        qqchChangeService.reviewAllFinishFlow(businessId);
        return AjaxResult.success();
    }

    /**
     * 流程结束后触发
     * @param businessId
     * @return
     */
    @PostMapping("/listener")
    public AjaxResult listener(@RequestParam("id") Long businessId){
        qqchChangeService.finishFlow(businessId);
        //推送设备策划数据到物设中间库
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            dataShareDevicePlanService.eachChangePush();
        });
        return AjaxResult.success();
    }


}
