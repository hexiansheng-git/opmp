package com.hhwy.sp.job.service;

import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.hhwy.common.core.exception.CustomException;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.tenant.utils.TenantDataSourceUtils;
import com.hhwy.feign.service.SystemServiceApi;
import com.hhwy.sp.experiment.sgjsExperimentRecord.domain.SgjsExperimentRecord;
import com.hhwy.sp.experiment.sgjsExperimentRecord.service.ISgjsExperimentRecordService;
import com.hhwy.sp.utils.syncThirdInterface.wushe.GetMaterialInfoInterface;
import com.hhwy.system.api.domain.SysTenant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 试验进场设备定时任务
 *
 * @author lcf
 * @data 2023-12-18
 */
@Service
public class ExperimentJobServiceImpl {

    private Logger logger= LoggerFactory.getLogger(ExperimentJobServiceImpl.class);

    @Autowired
    private SystemServiceApi systemServiceApi;
    @Autowired
    private ISgjsExperimentRecordService sgjsExperimentRecordService;
    @Autowired
    private GetMaterialInfoInterface materialInfoInterface;

    public void getWuSheMaterialRecord(){
        //切换到master
        String oldDataSource = DynamicDataSourceContextHolder.peek();
        DynamicDataSourceContextHolder.push("master");
        //获取所有租户
        List<SysTenant> tenantList = systemServiceApi.tenantList();
        try {
            for (SysTenant tenant : tenantList) {
                //切换租户
                String tenantKey = tenant.getTenantKey();//租户key就是项目编码
                String dataSource = TenantDataSourceUtils.getDataSourceNameByTenantKey(tenantKey);
                DynamicDataSourceContextHolder.push(dataSource);
                //获取试验设备进场记录所有数据
                //主表id
                List<SgjsExperimentRecord> list = sgjsExperimentRecordService.selectList(new SgjsExperimentRecord());
                //根据项目编码和设备编码查询物设系统设备进场记录
                List<String> materialCodeList = list.stream().map(e -> e.getMaterialCode()).collect(Collectors.toList());
                if(CollectionUtils.isEmpty(materialCodeList)){
                    logger.error("该项目【{}】设备编码为空",tenantKey);
                    continue;
                }
                Map<String,Object> map=new HashMap<>();
                map.put("projectCode",tenantKey);
                AjaxResult result = materialInfoInterface.syncMaterialInfo(map);

            }
        }catch (Exception e){
            throw new CustomException(e.getMessage());
        }finally {
            DynamicDataSourceContextHolder.poll();
            DynamicDataSourceContextHolder.push(oldDataSource);
        }
    }
}
