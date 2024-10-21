package com.hhwy.sp.mq;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.hhwy.common.core.exception.CustomException;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.tenant.utils.TenantDataSourceUtils;
import com.hhwy.feign.service.SystemServiceApi;
import com.hhwy.sp.techFile.sgjsCheckDataCatalog.domain.SgjsCheckDataCatalog;
import com.hhwy.sp.techFile.sgjsCheckDataCatalog.domain.SgjsFileDirectorySet;
import com.hhwy.sp.techFile.sgjsCheckDataCatalog.service.ISgjsCheckDataCatalogService;
import com.hhwy.system.api.domain.SysTenant;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 技术检查资料
 *
 * @author lcf
 * @date 2024-10-18
 */
@Service
@RocketMQMessageListener(
        consumerGroup = "sgjs_file_directory_set",
        topic = "sgjs_file_directory_set",
        selectorExpression = "isUsed",
        // 消费模式: 顺序消费
        consumeMode = ConsumeMode.ORDERLY)
@Slf4j
public class SgjsFileDirectorySetConsumerListener implements RocketMQListener<String>, RocketMQPushConsumerLifecycleListener {
    @Autowired
    private SystemServiceApi systemServiceApi;
    @Autowired
    private ISgjsCheckDataCatalogService sgjsCheckDataCatalogService;

    private Logger logger= LoggerFactory.getLogger(SgjsFileDirectorySetConsumerListener.class);

    @Override
    public void onMessage(String s) {
        if(StringUtils.isEmpty(s)) return;
        logger.info("接收到的参数s--->【{}】", s);
        List<SysTenant> tenantList = systemServiceApi.tenantList();
        String oldDataSource = null;
        try {
            for (SysTenant tenant : tenantList) {
                oldDataSource = TenantDataSourceUtils.getDataSourceNameByTenantKey("master");
                String dataSource = TenantDataSourceUtils.getDataSourceNameByTenantKey(tenant.getTenantKey());
                if(StringUtils.isNotBlank(dataSource) && !dataSource.equals(oldDataSource)) {
                    DynamicDataSourceContextHolder.push(dataSource);
                    //数据下发入库
                    List<SgjsFileDirectorySet> list = JSONArray.parseArray(s, SgjsFileDirectorySet.class);
                    List<SgjsCheckDataCatalog> catalogList =new ArrayList<>();
                    for (int i = 0; i < list.size(); i++) {
                        SgjsFileDirectorySet directorySet = list.get(i);
                        SgjsCheckDataCatalog info=new SgjsCheckDataCatalog();
                        BeanUtils.copyProperties(directorySet,info);
                        catalogList.add(info);
                    }
                    List<SgjsCheckDataCatalog> insertList=new ArrayList<>();
                    List<SgjsCheckDataCatalog> updateList=new ArrayList<>();
                    //表中数据  找出修改的  和新增的
                    List<SgjsCheckDataCatalog> allList = sgjsCheckDataCatalogService.getSgjsCheckDataCatalogList(new SgjsCheckDataCatalog());
                    //空了  全入库
                    if(CollectionUtils.isEmpty(allList)){
                        sgjsCheckDataCatalogService.insertBath(catalogList);
                    }
                    for (SgjsCheckDataCatalog info:allList) {
                        List<SgjsCheckDataCatalog> rstList = catalogList.stream().filter(e -> e.getId() == info.getId()).collect(Collectors.toList());
                        if(CollectionUtils.isEmpty(rstList)){
                            insertList.add(info);
                        }else{
                            updateList.add(info);
                        }
                    }
                    if(!CollectionUtils.isEmpty(insertList)){
                        sgjsCheckDataCatalogService.insertBath(catalogList);
                    }
                    if(!CollectionUtils.isEmpty(updateList)){
                        sgjsCheckDataCatalogService.updateBath(catalogList);
                    }
                    logger.info("结束啦啦啦啦啦啦啦啦啦啦啦啦！");
                }
            }

        }catch (Exception e){
            logger.error("SgjsFileDirectorySetConsumerListener下发报错了------->【{}】",e.getMessage());
            throw new CustomException(e.getMessage());
        }finally {
            DynamicDataSourceContextHolder.poll();
            DynamicDataSourceContextHolder.push(oldDataSource);
        }

    }

    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
        defaultMQPushConsumer.setMaxReconsumeTimes(1);
        defaultMQPushConsumer.setInstanceName("sgjs_file_directory_set");
    }
}
