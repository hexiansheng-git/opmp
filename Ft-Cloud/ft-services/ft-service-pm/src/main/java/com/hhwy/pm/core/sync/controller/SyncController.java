package com.hhwy.pm.core.sync.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.core.sync.service.ISysSyncInfoService;
import com.hhwy.pm.qqch.common.domain.CompileEntity;
import com.hhwy.pm.qqch.group.domain.QqchWorkGroup;
import com.hhwy.pm.qqch.group.service.IQqchWorkGroupService;
import com.hhwy.pm.qqch.qqchWorkPlan.domain.QqchWorkPlan;
import com.hhwy.pm.qqch.qqchWorkPlan.service.IQqchWorkPlanService;
import com.hhwy.pm.qqch.sgch.qqchconst.domain.QqchConst;
import com.hhwy.pm.qqch.sgch.qqchconst.service.IQqchConstService;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.validation.ValidationGroups;
import io.netty.handler.codec.http.QueryStringDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Validated
@RestController
@RequestMapping("/syncInfo")
public class SyncController extends BaseController {

    @Autowired
    private IQqchWorkGroupService qqchWorkGroupService;
    @Autowired
    private IQqchWorkPlanService qqchWorkPlanService;
    @Autowired
    private ISysSyncInfoService syncInfoService;

    /**
     * 前期策划测试用
     * @param 
     * @return
     */
    @PostMapping("/workGroup")
    public AjaxResult sync(@RequestBody QqchWorkGroup qqchWorkGroup) {
        if(!SecurityUtils.getSysUser().isAdmin())
            return AjaxResult.error("ERROR");
        if(qqchWorkGroup.getId() != null){
            qqchWorkGroup = qqchWorkGroupService.getQqchWorkGroupById(qqchWorkGroup.getId());
            syncInfoService.pushQqchWorkGroup(qqchWorkGroup);
        }else{
            List<QqchWorkGroup> list = qqchWorkGroupService.getQqchWorkGroupList(new QqchWorkGroup());
            syncInfoService.pushQqchWorkGroup(list);
        }
        return AjaxResult.success();
    }

    /**
     * 前期策划测试用
     * @param
     * @return
     */
    @PostMapping("/workPlan")
    public AjaxResult workPlan(@RequestBody QqchWorkPlan qqchWorkPlan) {
        if(!SecurityUtils.getSysUser().isAdmin())
            return AjaxResult.error("ERROR");
        if(qqchWorkPlan.getId() != null){
            QqchWorkPlan query = new QqchWorkPlan();
            query.setId(IdWorker.createId());
            qqchWorkPlan = qqchWorkPlanService.getQqchWorkPlan(query);
            syncInfoService.pushQqchWorkPlan(qqchWorkPlan);
        }else{
            List<QqchWorkPlan> list = qqchWorkPlanService.getQqchWorkPlanList(new QqchWorkPlan());
            syncInfoService.pushQqchWorkPlan(list);
        }
        return AjaxResult.success();
    }
}
