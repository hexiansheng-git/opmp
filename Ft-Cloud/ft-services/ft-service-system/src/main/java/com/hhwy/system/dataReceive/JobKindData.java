package com.hhwy.system.dataReceive;

import cn.hutool.core.date.DateUtil;
import com.hhwy.domain.base.system.jobKind.JobKind;
import com.hhwy.system.jobKind.service.IJobKindService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：工种
 * 作者: fushudong
 * 时间: 2023/09/06
 */
public class JobKindData {

    @Autowired
    private IJobKindService jobKindService;

    //数据同步
    public void syncData(){
        List<JobKind> list = new ArrayList<>();
        JobKind jobKind = new JobKind();
        jobKind.setJobCode("1");
        jobKind.setJobName("工长");
        JobKind jobKind1 = new JobKind();
        jobKind.setJobCode("1");
        jobKind.setJobName("普工");
        JobKind jobKind2 = new JobKind();
        jobKind.setJobCode("1");
        jobKind.setJobName("钢筋工");
        JobKind jobKind3 = new JobKind();
        jobKind.setJobCode("1");
        jobKind.setJobName("模板工");
        JobKind jobKind4 = new JobKind();
        jobKind.setJobCode("1");
        jobKind.setJobName("分包分责人");
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