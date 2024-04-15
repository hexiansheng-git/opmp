package com.hhwy.sp.sgjsMeasure.sgjsControlPointRetest.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.domain.SysSyncInfoLog;
import com.hhwy.feign.service.PmServiceApi;
import com.hhwy.sp.sgjsMeasure.sgjsControlPointRetest.domain.SgjsControlPointRetest;
import com.hhwy.sp.sgjsMeasure.sgjsControlPointRetest.mapper.SgjsControlPointRetestMapper;
import com.hhwy.sp.sgjsMeasure.sgjsControlPointRetest.service.ISgjsControlPointRetestService;
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
 * 施工技术--测量管理--控制点复测
 *
 * @author lcf
 * @date 2024-03-12 14:54:13
 * @remark
 */
@Service
public class SgjsControlPointRetestServiceImpl implements ISgjsControlPointRetestService {

    @Autowired
    private SgjsControlPointRetestMapper sgjsControlPointRetestMapper;
    @Autowired
    private PmServiceApi pmServiceApi;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    private Logger logger= LoggerFactory.getLogger(SgjsControlPointRetestServiceImpl.class);


    public SgjsControlPointRetest getSgjsControlPointRetest(SgjsControlPointRetest sgjsControlPointRetest) {
        return sgjsControlPointRetestMapper.getSgjsControlPointRetest(sgjsControlPointRetest);
    }

    public List<SgjsControlPointRetest> getSgjsControlPointRetestList(SgjsControlPointRetest sgjsControlPointRetest) {
        return sgjsControlPointRetestMapper.getSgjsControlPointRetestList(sgjsControlPointRetest);
    }

    @Transactional
    public int insertSgjsControlPointRetest(SgjsControlPointRetest sgjsControlPointRetest) {
        sgjsControlPointRetest.setId(IdWorker.createId());
        sgjsControlPointRetest.setCreateUser(SecurityUtils.getUserName());
        sgjsControlPointRetest.setCreateTime(DateUtils.getNowDate());
        return sgjsControlPointRetestMapper.insertSgjsControlPointRetest(sgjsControlPointRetest);
    }

    @Transactional
    public int insertSgjsControlPointRetestList(List<SgjsControlPointRetest> sgjsControlPointRetestList) {
        //因为是全量新增,先删掉库里原有的
        SgjsControlPointRetest info= new SgjsControlPointRetest();
        info.setUpdateTime(DateUtils.getNowDate());
        info.setUpdateUser(SecurityUtils.getUserId().toString());
        info.setDelFlag("1");
        sgjsControlPointRetestMapper.updateSgjsControlPointRetest(info);
        //入库
        Map<String,Object> map=new HashMap<>();
        if(CollectionUtils.isEmpty(sgjsControlPointRetestList)){
            logger.info("空了！！！！！！！！");
            map.put("isDel","0");
            //空了 ，但需要删除总部版数据，所以只穿id
            Map<String, Object> prjInfo = pmServiceApi.getPrjInfo();
            if(prjInfo.get("projectId") != null)map.put("prjId",prjInfo.get("projectId")+"");
            syncDataToGm(map);
            return 1;
        }
        for (SgjsControlPointRetest sgjsControlPointRetest : sgjsControlPointRetestList) {
            sgjsControlPointRetest.setId(IdWorker.createId());
            sgjsControlPointRetest.setCreateUser(SecurityUtils.getUserName());
            sgjsControlPointRetest.setCreateTime(DateUtils.getNowDate());
        }
        int i = sgjsControlPointRetestMapper.insertSgjsControlPointRetestList(sgjsControlPointRetestList);
        //同步
        map.put("isDel","1");
        map.put("infoList",sgjsControlPointRetestList);
        syncDataToGm(map);
        return i;
    }

    private void syncDataToGm(Map<String, Object> map) {
        long beginMills = System.currentTimeMillis();
        Integer status = 1;
        String errMsg = "";
        try{
            rocketMQTemplate.convertAndSend("sgjs_control_point_retest:tenantSuccess1", JSONObject.toJSONString(map));
        }catch (Exception e){
            e.printStackTrace();
            status = 0;
            errMsg = e.getMessage();
            logger.error("报错了【{}】",e.getMessage());
            throw e;
        }finally {
            //3、更新syncInfo
            SysSyncInfoLog log=new SysSyncInfoLog();
            log.setBusinessName("sgjs_control_point_retest");
            log.setStatus(status);
            log.setFailMsg(errMsg);
            log.setPtVar1(JSONObject.toJSONString(map));
            logger.error("sgjs_control_point_retest同步失败【{}】,时间：【{}】",JSONObject.toJSONString(map),System.currentTimeMillis()-beginMills);
            pmServiceApi.insertSyncLog(log);
        }
    }

    @Transactional
    public int updateSgjsControlPointRetest(SgjsControlPointRetest sgjsControlPointRetest) {
        sgjsControlPointRetest.setUpdateUser(SecurityUtils.getUserName());
        sgjsControlPointRetest.setUpdateTime(DateUtils.getNowDate());
        return sgjsControlPointRetestMapper.updateSgjsControlPointRetest(sgjsControlPointRetest);
    }

    @Transactional
    public int updateSgjsControlPointRetestList(List<SgjsControlPointRetest> sgjsControlPointRetestList) {
        for (SgjsControlPointRetest sgjsControlPointRetest : sgjsControlPointRetestList) {
            sgjsControlPointRetest.setUpdateUser(SecurityUtils.getUserName());
            sgjsControlPointRetest.setUpdateTime(DateUtils.getNowDate());
        }
        return sgjsControlPointRetestMapper.updateSgjsControlPointRetestList(sgjsControlPointRetestList);
    }

    @Transactional
    public int deleteSgjsControlPointRetest(SgjsControlPointRetest sgjsControlPointRetest) {
        sgjsControlPointRetest.setUpdateUser(SecurityUtils.getUserName());
        sgjsControlPointRetest.setUpdateTime(DateUtils.getNowDate());
        return sgjsControlPointRetestMapper.deleteSgjsControlPointRetest(sgjsControlPointRetest);
    }

    @Transactional
    public int deleteSgjsControlPointRetestByPks(List<Long> sgjsControlPointRetestPkList) {
        return sgjsControlPointRetestMapper.deleteSgjsControlPointRetestByPks(sgjsControlPointRetestPkList);
    }
}
