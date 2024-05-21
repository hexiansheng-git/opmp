package com.hhwy.pm.qqch.qqchChange.controller;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.hhwy.common.core.exception.CustomException;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.common.tenant.utils.TenantDataSourceUtils;
import com.hhwy.enums.FlowEnum;
import com.hhwy.pm.common.FlowInfoSearchUtil;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlan.domain.JdglMainPlan;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlan.service.IJdglMainPlanService;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.service.IJdglData4P6Service;
import com.hhwy.pm.qqch.preparation.technique.scheme.service.IQqchSimilarProjectSchemeService;
import com.hhwy.pm.qqch.qqchChange.domain.QqchChange;
import com.hhwy.pm.qqch.qqchChange.domain.QqchChangeDetail;
import com.hhwy.pm.qqch.qqchChange.service.IQqchChangeDetailService;
import com.hhwy.pm.qqch.qqchChange.service.IQqchChangeService;
import com.hhwy.pm.qqch.qqchChange.vo.QqchChangeVo;
import com.hhwy.pm.qqch.sgch.dataShare.DataShareDevicePlanService;
import com.hhwy.pm.xmsl.contractInfo.service.IXmslContractInfoService;
import com.hhwy.pm.xmsl.project.service.IXmslProjectBasicInfoService;
import com.hhwy.system.api.domain.SysMenu;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
import com.hhwy.utils.validation.ValidationGroups;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

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
    private IQqchChangeDetailService qqchChangeDetailService;
    @Autowired
    private IXmslContractInfoService contractInfoService;
    @Autowired
    private IXmslProjectBasicInfoService projectBasicInfoService;
    @Autowired
    private DataShareDevicePlanService dataShareDevicePlanService;
    @Autowired
    private IQqchSimilarProjectSchemeService qqchSimilarProjectSchemeService;
    @Autowired
    private IJdglData4P6Service jdglData4P6Service;
    @Autowired
    private IJdglMainPlanService jdglMainPlanService;

    @PreAuthorize(hasPermi = "qqchChange:list")
    @PostMapping("/list")
    @CustomLogger(title = "前期策划-前期策划变更", name = "前期策划变更" ,businessType = CustomBusinessType.SELECT)
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
    @CustomLogger(title = "前期策划-前期策划变更", name = "前期策划变更" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult adjust(){
        QqchChangeVo vo = qqchChangeService.adjustDetail();
        FlowInfoSearchUtil.getFlowInfo(vo,FlowEnum.QQCH_CHANGE);
        return AjaxResult.success(vo);
    }

    @GetMapping("/detail")
    @CustomLogger(title = "前期策划-前期策划变更", name = "前期策划变更" ,businessType = CustomBusinessType.SELECT)
    public AjaxResult detail(Long id){
        QqchChangeVo vo = qqchChangeService.detail(id);
        FlowInfoSearchUtil.getFlowInfo(vo,FlowEnum.QQCH_CHANGE);
        return AjaxResult.success(vo);
    }

    @PreAuthorize(hasPermi = "qqchChange:add")
    @PostMapping("/save")
    @CustomLogger(title = "前期策划-前期策划变更", name = "前期策划变更" ,businessType = CustomBusinessType.SAVE)
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
    @CustomLogger(title = "前期策划-前期策划变更", name = "前期策划变更" ,businessType = CustomBusinessType.DELETE)
    public AjaxResult deleteQqchChange(@Validated(ValidationGroups.Delete.class) @RequestBody QqchChange qqchChangeParam) {
        return toAjax(qqchChangeService.deleteQqchChange(qqchChangeParam));
    }

    @GetMapping("/export")
    @CustomLogger(title = "前期策划-前期策划变更", name = "前期策划变更" ,businessType = CustomBusinessType.EXPORT)
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

        /*变更完成后触发一些操作*/
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        String tenantKey = SecurityUtils.getTenantKey();
        executorService.submit(() -> {
            //切换
            String oldDataSource = DynamicDataSourceContextHolder.peek();
            DynamicDataSourceContextHolder.push(TenantDataSourceUtils.getDataSourceNameByTenantKey(tenantKey));
            try {
                //推送设备策划数据到物设中间库
                dataShareDevicePlanService.eachChangePush(tenantKey);
                //进度管理 - 总体计划数据初始化
                //判断此次变更有没有涉及到1.2.1的内容
                QqchChange qqchChange = new QqchChange();
                qqchChange.setId(businessId);
                QqchChange qqchChange1 = qqchChangeService.getQqchChange(qqchChange);
                List<QqchChangeDetail> qqchChangeDetailList = qqchChangeDetailService.getQqchChangeDetailList(qqchChange1.getId());
                List<QqchChangeDetail> collect = qqchChangeDetailList.stream().filter(p -> p.getItemId().equals("/preliminaryPlanning/constructionPlannin/child2/list2_1")
                        || p.getItemName().equals("1.2.1 总体进度计划")).collect(Collectors.toList());
                if (CollUtil.isNotEmpty(collect)) {
                    //如果变更中包含1.2.1的内容，则重新拉去1.2.1的数据到 进度管理 - 总体计划
                    jdglData4P6Service.syncData();
                    //进度管理 - 总体计划  设置基线版本
                    jdglMainPlanService.updateJdglBaseMainPlan();
                }
            }catch (Exception e){
                e.printStackTrace();
                throw new CustomException(e.getMessage());
            }finally {
                DynamicDataSourceContextHolder.poll();
                DynamicDataSourceContextHolder.push(oldDataSource);
            }
        });

        //推送同类项目方案
        qqchSimilarProjectSchemeService.pushData();
        return AjaxResult.success();
    }

    /**
     * 变更审批预警
     * @return
     */
    @GetMapping("changeApprovalWarn")
    public AjaxResult changeApprovalWarn(){
        qqchChangeService.changeApprovalWarn();
        return AjaxResult.success();
    }
}
