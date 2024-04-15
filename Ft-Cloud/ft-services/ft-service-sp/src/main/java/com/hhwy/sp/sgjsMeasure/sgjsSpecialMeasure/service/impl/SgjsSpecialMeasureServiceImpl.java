package com.hhwy.sp.sgjsMeasure.sgjsSpecialMeasure.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.domain.SysSyncInfoLog;
import com.hhwy.feign.service.PmServiceApi;
import com.hhwy.sp.sgjsMeasure.sgjsControlPointRetest.domain.SgjsControlPointRetest;
import com.hhwy.sp.sgjsMeasure.sgjsSpecialMeasure.domain.SgjsSpecialMeasure;
import com.hhwy.sp.sgjsMeasure.sgjsSpecialMeasure.mapper.SgjsSpecialMeasureMapper;
import com.hhwy.sp.sgjsMeasure.sgjsSpecialMeasure.service.ISgjsSpecialMeasureService;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 施工技术--测量管理--特殊工程监控量测
 *
 * @author lcf
 * @date 2024-03-12 14:54:37
 * @remark
 */
@Service
public class SgjsSpecialMeasureServiceImpl implements ISgjsSpecialMeasureService {

    @Autowired
    private SgjsSpecialMeasureMapper sgjsSpecialMeasureMapper;

    @Autowired
    private PmServiceApi pmServiceApi;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    private Logger logger= LoggerFactory.getLogger(SgjsSpecialMeasureServiceImpl.class);


    public SgjsSpecialMeasure getSgjsSpecialMeasure(SgjsSpecialMeasure sgjsSpecialMeasure) {
        return sgjsSpecialMeasureMapper.getSgjsSpecialMeasure(sgjsSpecialMeasure);
    }

    public List<SgjsSpecialMeasure> getSgjsSpecialMeasureList(SgjsSpecialMeasure sgjsSpecialMeasure) {
        return sgjsSpecialMeasureMapper.getSgjsSpecialMeasureList(sgjsSpecialMeasure);
    }

    @Transactional
    public int insertSgjsSpecialMeasure(SgjsSpecialMeasure sgjsSpecialMeasure) {
        sgjsSpecialMeasure.setId(IdWorker.createId());
        sgjsSpecialMeasure.setCreateUser(SecurityUtils.getUserName());
        sgjsSpecialMeasure.setCreateTime(DateUtils.getNowDate());
        return sgjsSpecialMeasureMapper.insertSgjsSpecialMeasure(sgjsSpecialMeasure);
    }

    @Transactional
    public int insertSgjsSpecialMeasureList(List<SgjsSpecialMeasure> sgjsSpecialMeasureList) {
        SgjsSpecialMeasure info=new SgjsSpecialMeasure();
        info.setUpdateTime(DateUtils.getNowDate());
        info.setUpdateUser(SecurityUtils.getUserId().toString());
        info.setDelFlag("1");
        sgjsSpecialMeasureMapper.updateSgjsSpecialMeasure(info);
        //入库
        Map<String,Object> map=new HashMap<>();
        if(CollectionUtils.isEmpty(sgjsSpecialMeasureList)){
            logger.info("空了！！！！！！！！");
            map.put("isDel","0");
            //空了 ，但需要删除总部版数据，所以只穿id
            Map<String, Object> prjInfo = pmServiceApi.getPrjInfo();
            if(prjInfo.get("projectId") != null)map.put("prjId",prjInfo.get("projectId")+"");
            syncDataToGm(map);
            return 1;
        }
        for (SgjsSpecialMeasure sgjsSpecialMeasure : sgjsSpecialMeasureList) {
            sgjsSpecialMeasure.setId(IdWorker.createId());
            sgjsSpecialMeasure.setCreateUser(SecurityUtils.getUserName());
            sgjsSpecialMeasure.setCreateTime(DateUtils.getNowDate());
        }
        int i = sgjsSpecialMeasureMapper.insertSgjsSpecialMeasureList(sgjsSpecialMeasureList);
        //同步
        map.put("isDel","1");
        map.put("infoList",sgjsSpecialMeasureList);
        syncDataToGm(map);
        return i;
    }

    private void syncDataToGm(Map<String, Object> map) {
        long beginMills = System.currentTimeMillis();
        Integer status = 1;
        String errMsg = "";
        try{
            rocketMQTemplate.convertAndSend("sgjs_special_measure:tenantSuccess1", JSONObject.toJSONString(map));
        }catch (Exception e){
            e.printStackTrace();
            status = 0;
            errMsg = e.getMessage();
            logger.error("报错了【{}】",e.getMessage());
            throw e;
        }finally {
            //3、更新syncInfo
            SysSyncInfoLog log=new SysSyncInfoLog();
            log.setBusinessName("sgjs_special_measure");
            log.setStatus(status);
            log.setFailMsg(errMsg);
            log.setPtVar1(JSONObject.toJSONString(map));
            logger.error("sgjs_special_measure同步失败【{}】,时间：【{}】",JSONObject.toJSONString(map),System.currentTimeMillis()-beginMills);
            pmServiceApi.insertSyncLog(log);
        }
    }

    @Transactional
    public int updateSgjsSpecialMeasure(SgjsSpecialMeasure sgjsSpecialMeasure) {
        sgjsSpecialMeasure.setUpdateUser(SecurityUtils.getUserName());
        sgjsSpecialMeasure.setUpdateTime(DateUtils.getNowDate());
        return sgjsSpecialMeasureMapper.updateSgjsSpecialMeasure(sgjsSpecialMeasure);
    }

    @Transactional
    public int updateSgjsSpecialMeasureList(List<SgjsSpecialMeasure> sgjsSpecialMeasureList) {
        for (SgjsSpecialMeasure sgjsSpecialMeasure : sgjsSpecialMeasureList) {
            sgjsSpecialMeasure.setUpdateUser(SecurityUtils.getUserName());
            sgjsSpecialMeasure.setUpdateTime(DateUtils.getNowDate());
        }
        return sgjsSpecialMeasureMapper.updateSgjsSpecialMeasureList(sgjsSpecialMeasureList);
    }

    @Transactional
    public int deleteSgjsSpecialMeasure(SgjsSpecialMeasure sgjsSpecialMeasure) {
        sgjsSpecialMeasure.setUpdateUser(SecurityUtils.getUserName());
        sgjsSpecialMeasure.setUpdateTime(DateUtils.getNowDate());
        return sgjsSpecialMeasureMapper.deleteSgjsSpecialMeasure(sgjsSpecialMeasure);
    }

    @Transactional
    public int deleteSgjsSpecialMeasureByPks(List<Long> sgjsSpecialMeasurePkList) {
        return sgjsSpecialMeasureMapper.deleteSgjsSpecialMeasureByPks(sgjsSpecialMeasurePkList);
    }
}
