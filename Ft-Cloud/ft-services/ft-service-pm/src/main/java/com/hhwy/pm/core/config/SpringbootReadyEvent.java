package com.hhwy.pm.core.config;

import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.hhwy.common.core.exception.CustomException;
import com.hhwy.common.tenant.utils.TenantDataSourceUtils;
import com.hhwy.feign.service.SystemServiceApi;
import com.hhwy.pm.xmsl.wbs.WbsRedisUtils;
import com.hhwy.pm.xmsl.wbs.service.IXmslWbsService;
import com.hhwy.system.api.domain.SysTenant;
import com.hhwy.utils.redisUtil.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * 启动时触发
 * 异步同步每个租户的项目wbs缓存（如果不存在的话）
 */
@Configuration
public class SpringbootReadyEvent {
    private static final Logger log= LoggerFactory.getLogger(SpringbootReadyEvent.class);
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private IXmslWbsService wbsService;
    @Autowired
    private SystemServiceApi systemServiceApi;

    @EventListener({ApplicationReadyEvent.class})
    void initMethod(){
        ExecutorService executorService = Executors.newSingleThreadExecutor();//单一线程池
        executorService.execute(() -> {
            //切换到master
            String oldDataSource = DynamicDataSourceContextHolder.peek();
            DynamicDataSourceContextHolder.push("master");
            //获取所有租户
            List<SysTenant> tenantList = systemServiceApi.tenantList();
            try {
                for (SysTenant tenant : tenantList) {
                    String tenantKey = tenant.getTenantKey();
                    String key = WbsRedisUtils.getKey(tenantKey);
                    if(redisUtils.hasKey(key)){
                        continue;
                    }
                    //切换租户
//                    String dataSource = TenantDataSourceUtils.getDataSourceNameByTenantKey(tenantKey);
//                    DynamicDataSourceContextHolder.push(dataSource);
                    wbsService.initWbs2Redis(tenantKey);
                }
                //MASTER
                wbsService.initWbs2Redis("master");
            }catch (Exception e){
                e.printStackTrace();
                throw new CustomException(e.getMessage());
            }finally {
                DynamicDataSourceContextHolder.poll();
                DynamicDataSourceContextHolder.push(oldDataSource);
            }
        });
    }

}
