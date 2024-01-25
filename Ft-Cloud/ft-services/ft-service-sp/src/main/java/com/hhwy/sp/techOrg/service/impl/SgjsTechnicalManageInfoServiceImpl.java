package com.hhwy.sp.techOrg.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.domain.SysSyncInfoLog;
import com.hhwy.feign.service.PmServiceApi;
import com.hhwy.sp.techOrg.domain.SgjsTechnicalManageInfo;
import com.hhwy.sp.techOrg.domain.SgjsTechnicalManageInfoVo;
import com.hhwy.sp.techOrg.mapper.SgjsTechnicalManageInfoMapper;
import com.hhwy.sp.techOrg.service.ISgjsTechnicalManageInfoService;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author lcf
 * @date 2023-11-23 10:21:37
 * @remark
 */
@Service
public class SgjsTechnicalManageInfoServiceImpl implements ISgjsTechnicalManageInfoService {

    @Autowired
    private SgjsTechnicalManageInfoMapper sgjsTechnicalManageInfoMapper;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    @Autowired
    private PmServiceApi pmServiceApi;

    private Logger logger= LoggerFactory.getLogger(SgjsTechnicalManageInfoServiceImpl.class);


    public SgjsTechnicalManageInfo getSgjsTechnicalManageInfo(SgjsTechnicalManageInfo sgjsTechnicalManageInfo) {
        return sgjsTechnicalManageInfoMapper.getSgjsTechnicalManageInfo(sgjsTechnicalManageInfo);
    }

    public List<SgjsTechnicalManageInfo> getSgjsTechnicalManageInfoList(SgjsTechnicalManageInfo sgjsTechnicalManageInfo) {
        List<SgjsTechnicalManageInfo> list = sgjsTechnicalManageInfoMapper.getSgjsTechnicalManageInfoList(sgjsTechnicalManageInfo);
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setType("0");
        }
        return list;
    }

    @Transactional
    public int insertSgjsTechnicalManageInfo(SgjsTechnicalManageInfo sgjsTechnicalManageInfo) {
        sgjsTechnicalManageInfo.setId(IdWorker.createId());
        sgjsTechnicalManageInfo.setCreateUser(SecurityUtils.getUserName());
        sgjsTechnicalManageInfo.setCreateTime(DateUtils.getNowDate());
        return sgjsTechnicalManageInfoMapper.insertSgjsTechnicalManageInfo(sgjsTechnicalManageInfo);
    }

    @Transactional
    public int insertSgjsTechnicalManageInfoList(SgjsTechnicalManageInfoVo vo) {
        List<String> delIdList = vo.getDelIdList();
        if(!CollectionUtils.isEmpty(delIdList)){
            sgjsTechnicalManageInfoMapper.deleteInfoByTechIds(delIdList);
        }
        List<SgjsTechnicalManageInfo> sgjsTechnicalManageInfoList = vo.getListData();
        List<SgjsTechnicalManageInfo> addList = sgjsTechnicalManageInfoList.stream().filter(e -> StringUtils.isNotEmpty(e.getType()) && e.getType().equals("1")).collect(Collectors.toList());
        //新增
        int i=0;
        if(!CollectionUtils.isEmpty(addList)){
            for (SgjsTechnicalManageInfo sgjsTechnicalManageInfo : addList) {
                //sgjsTechnicalManageInfo.setId(IdWorker.createId());
                sgjsTechnicalManageInfo.setCreateUser(SecurityUtils.getUserName());
                sgjsTechnicalManageInfo.setCreateTime(DateUtils.getNowDate());
            }
            i=sgjsTechnicalManageInfoMapper.insertSgjsTechnicalManageInfoList(addList);
        }

        //编辑
        List<SgjsTechnicalManageInfo> updateList = sgjsTechnicalManageInfoList.stream().filter(e -> e.getType().equals("0")).collect(Collectors.toList());
        if(!CollectionUtils.isEmpty(updateList)){
            for (int j = 0; j < updateList.size(); j++) {
                updateList.get(j).setUpdateUser(SecurityUtils.getUserId().toString());
                updateList.get(j).setUpdateTime(DateUtils.getNowDate());
            }
            i=sgjsTechnicalManageInfoMapper.updateSgjsTechnicalManageInfoList(updateList);
        }
        sysDataToGm(sgjsTechnicalManageInfoList);
        return i;
    }

    /**
     * 同步总部数据
     *
     * @param list
     */
    private void sysDataToGm(List<SgjsTechnicalManageInfo> list) {
        long beginMills = System.currentTimeMillis();
        Integer status = 1;
        String errMsg = "";
        try{
            rocketMQTemplate.convertAndSend("sgjs_technical_group2:tenantSuccess", JSONObject.toJSONString(list));
        }catch (Exception e){
            e.printStackTrace();
            status = 0;
            errMsg = e.getMessage();
            throw e;
        }finally {
            //3、更新syncInfo
            String ids = list.stream().map(r->r.getId()+"").collect(Collectors.joining(","));
            SysSyncInfoLog log=new SysSyncInfoLog();
            log.setBusinessName("sgjs_technical_insert_info");
            log.setStatus(status);
            log.setFailMsg(errMsg);
            log.setPtVar1(ids);
            logger.error("sgjs_technical_insert_info同步失败【{}】,时间：【{}】",ids,System.currentTimeMillis()-beginMills);
            pmServiceApi.insertSyncLog(log);
        }
    }


    @Transactional
    public int updateSgjsTechnicalManageInfo(SgjsTechnicalManageInfo sgjsTechnicalManageInfo) {
        sgjsTechnicalManageInfo.setUpdateUser(SecurityUtils.getUserName());
        sgjsTechnicalManageInfo.setUpdateTime(DateUtils.getNowDate());
        return sgjsTechnicalManageInfoMapper.updateSgjsTechnicalManageInfo(sgjsTechnicalManageInfo);
    }

    @Transactional
    public int updateSgjsTechnicalManageInfoList(List<SgjsTechnicalManageInfo> sgjsTechnicalManageInfoList) {
        for (SgjsTechnicalManageInfo sgjsTechnicalManageInfo : sgjsTechnicalManageInfoList) {
            sgjsTechnicalManageInfo.setUpdateUser(SecurityUtils.getUserName());
            sgjsTechnicalManageInfo.setUpdateTime(DateUtils.getNowDate());
        }
        return sgjsTechnicalManageInfoMapper.updateSgjsTechnicalManageInfoList(sgjsTechnicalManageInfoList);
    }

    @Transactional
    public int deleteSgjsTechnicalManageInfo(SgjsTechnicalManageInfo sgjsTechnicalManageInfo) {
        sgjsTechnicalManageInfo.setUpdateUser(SecurityUtils.getUserName());
        sgjsTechnicalManageInfo.setUpdateTime(DateUtils.getNowDate());
        return sgjsTechnicalManageInfoMapper.deleteSgjsTechnicalManageInfo(sgjsTechnicalManageInfo);
    }

    @Transactional
    public int deleteSgjsTechnicalManageInfoByPks(List<Long> sgjsTechnicalManageInfoPkList) {
        return sgjsTechnicalManageInfoMapper.deleteSgjsTechnicalManageInfoByPks(sgjsTechnicalManageInfoPkList);
    }
}
