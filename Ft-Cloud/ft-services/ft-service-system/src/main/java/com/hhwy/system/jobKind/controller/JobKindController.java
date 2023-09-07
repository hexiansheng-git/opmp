package com.hhwy.system.jobKind.controller;

import cn.hutool.core.date.DateUtil;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.page.TableDataInfo;
import com.hhwy.domain.base.system.jobKind.JobKind;
import com.hhwy.system.jobKind.service.IJobKindService;
import com.hhwy.utils.common.PmsConstant;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


/**
 * infoController
 * 
 * @author lcf
 * @date 2022-11-24
 */
@RestController
@RequestMapping("/jobKind/info")
public class JobKindController extends BaseController {

    @Autowired
    private IJobKindService jobKindService;

    /**
     * 查询info列表
     */
    @GetMapping("/list")
    public AjaxResult list(@Validated(ValidationGroups.Select.class) JobKind currencyInfo) {
        startPage();
        List<JobKind> list = jobKindService.selectJobKindList(currencyInfo);
        TableDataInfo dataTable = getDataTable(list);
        if(null==dataTable){
            return new AjaxResult(PmsConstant.WARN_CODE,"未查询到数据");
        }
        return AjaxResult.success(dataTable);
    }

    @PostMapping("/listNoPage")
    public AjaxResult listNoPage(@RequestBody JobKind currencyInfo){
        List<JobKind> list = jobKindService.selectJobKindList(currencyInfo);
        TableDataInfo dataTable = getDataTable(list);
        if(null==dataTable){
            return new AjaxResult(PmsConstant.WARN_CODE,"未查询到数据");
        }
        return AjaxResult.success(dataTable);
    }

    @PostMapping("/getList")
    public AjaxResult getCurrencyList(@RequestBody JobKind currencyInfo) {
        return AjaxResult.success(jobKindService.selectJobKindList(currencyInfo));
    }

    //数据同步
    @GetMapping("/syncData")
    public void syncData(){
        List<JobKind> list = new ArrayList<>();
        JobKind jobKind = new JobKind();
        jobKind.setJobCode("1");
        jobKind.setJobName("工长");
        JobKind jobKind1 = new JobKind();
        jobKind1.setJobCode("1");
        jobKind1.setJobName("普工");
        JobKind jobKind2 = new JobKind();
        jobKind2.setJobCode("1");
        jobKind2.setJobName("钢筋工");
        JobKind jobKind3 = new JobKind();
        jobKind3.setJobCode("1");
        jobKind3.setJobName("模板工");
        JobKind jobKind4 = new JobKind();
        jobKind4.setJobCode("1");
        jobKind4.setJobName("分包分责人");
        list.add(jobKind);
        list.add(jobKind1);
        list.add(jobKind2);
        list.add(jobKind3);
        list.add(jobKind4);
        for (int i = 0; i < list.size(); i++) {
            JobKind jobKind5 = list.get(i);
            jobKind5.setCreateUser("1");
            jobKind5.setCreateTime(DateUtil.date());
            jobKind5.setId(IdWorker.createId());
            jobKind5.setDelFlag("0");
            jobKind5.setSort(Long.valueOf(i+1));
        }
        jobKindService.dataSync(list);
    }

}
