package com.hhwy.sp.mq;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.hhwy.common.core.exception.CustomException;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.tenant.utils.TenantDataSourceUtils;
import com.hhwy.sp.techManagement.sgjsPaperScore.domain.SgjsPaperScore;
import com.hhwy.sp.techManagement.sgjsPaperScore.service.ISgjsPaperScoreService;
import com.hhwy.sp.techManagement.sgjsPaperScore.sgjsPaperScoreRecord.domain.SgjsPaperScoreRecord;
import com.hhwy.sp.techManagement.sgjsPaperScore.sgjsPaperScoreRecord.service.ISgjsPaperScoreRecordService;
import com.hhwy.utils.exception.CustomBusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RocketMQMessageListener(
        consumerGroup = "sp_sgjs_paper_score",
        topic = "sp_sgjs_paper_score",
        selectorExpression = "tenantSuccess",
        // 消费模式: 顺序消费
        consumeMode = ConsumeMode.ORDERLY)
@Slf4j
public class SgjsPaperScoreConsumerListener implements RocketMQListener<String> , RocketMQPushConsumerLifecycleListener {
    @Autowired
    private ISgjsPaperScoreService sgjsPaperScoreService;
    @Autowired
    private ISgjsPaperScoreRecordService sgjsPaperScoreRecordService;

    @Override
    public void onMessage(String s) {
        String oldDataSource = null;
        try {
            log.info("论文评分 数据同步 总部->项目：{}", s);
            Map map = JSONObject.parseObject(s, Map.class);
            List<SgjsPaperScore> paperScoreList = JSONObject.parseArray(JSON.toJSONString(map.get("paperScoreList")), SgjsPaperScore.class);
            List<SgjsPaperScoreRecord> scoreRecordList = JSONObject.parseArray(JSON.toJSONString(map.get("scoreRecordList")), SgjsPaperScoreRecord.class);
            String tenantKey = paperScoreList.get(0).getPtVar4();
            if (StrUtil.isBlank(tenantKey)) {
                log.error("论文评分 数据同步!!! 租户标识不能为空");
                return;
            }
            oldDataSource = DynamicDataSourceContextHolder.peek();
            String dataSource = TenantDataSourceUtils.getDataSourceNameByTenantKey(tenantKey);
            DynamicDataSourceContextHolder.push("master");
            if (StringUtils.isNotBlank(dataSource) && !dataSource.equals(oldDataSource)) {
                DynamicDataSourceContextHolder.push(dataSource);
                sgjsPaperScoreService.updateSgjsPaperScoreList(paperScoreList);
                sgjsPaperScoreRecordService.insertSgjsPaperScoreRecordList(scoreRecordList);
            }
        } catch(Exception e){
            throw new CustomException(e.getMessage());
        } finally {
            DynamicDataSourceContextHolder.poll();
            DynamicDataSourceContextHolder.push(oldDataSource);
        }
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
        defaultMQPushConsumer.setMaxReconsumeTimes(3);
        defaultMQPushConsumer.setInstanceName("sp_sgjs_paper_score");
    }
}
