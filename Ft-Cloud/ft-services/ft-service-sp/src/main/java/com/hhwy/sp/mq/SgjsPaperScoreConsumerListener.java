package com.hhwy.sp.mq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
        try {
            log.info("论文评分 数据同步：{}", s);
            Map map = JSONObject.parseObject(s, Map.class);
            List<SgjsPaperScore> paperScoreList = JSONObject.parseArray(JSON.toJSONString(map.get("paperScoreList")), SgjsPaperScore.class);
            List<SgjsPaperScoreRecord> scoreRecordList = JSONObject.parseArray(JSON.toJSONString(map.get("scoreRecordList")), SgjsPaperScoreRecord.class);
            sgjsPaperScoreService.updateSgjsPaperScoreList(paperScoreList);
            sgjsPaperScoreRecordService.updateSgjsPaperScoreRecordList(scoreRecordList);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
        defaultMQPushConsumer.setMaxReconsumeTimes(3);
        defaultMQPushConsumer.setInstanceName("sp_sgjs_paper_score");
    }
}
