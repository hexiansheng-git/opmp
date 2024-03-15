package com.hhwy.sp.sgjsMeasure.sgjsEquipEntryRecord.sgjsEquipEntryRecord.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.domain.SysSyncInfoLog;
import com.hhwy.feign.service.PmServiceApi;
import com.hhwy.sp.sgjsMeasure.sgjsEquipEntryRecord.sgjsEquipEntryRecord.domain.SgjsEquipEntryRecord;
import com.hhwy.sp.sgjsMeasure.sgjsEquipEntryRecord.sgjsEquipEntryRecord.mapper.SgjsEquipEntryRecordMapper;
import com.hhwy.sp.sgjsMeasure.sgjsEquipEntryRecord.sgjsEquipEntryRecord.service.ISgjsEquipEntryRecordService;
import com.hhwy.sp.sgjsMeasure.sgjsEquipEntryRecord.sgjsEquipEntryRecordInfo.domain.SgjsEquipEntryRecordInfo;
import com.hhwy.sp.sgjsMeasure.sgjsEquipEntryRecord.sgjsEquipEntryRecordInfo.mapper.SgjsEquipEntryRecordInfoMapper;
import com.hhwy.sp.sgjsMeasure.sgjsEquipEntryRecord.sgjsEquipEntryRecordInfoDetail.domain.SgjsEquipEntryRecordInfoDetail;
import com.hhwy.sp.sgjsMeasure.sgjsEquipEntryRecord.sgjsEquipEntryRecordInfoDetail.mapper.SgjsEquipEntryRecordInfoDetailMapper;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.date.FtDateUtils;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author lcf   测量管理--测试设备进场记录
 * @date 2023-12-08 10:47:00
 * @remark
 */
@Service
public class SgjsEquipEntryRecordServiceImpl implements ISgjsEquipEntryRecordService{

    @Autowired
    private SgjsEquipEntryRecordMapper sgjsEquipEntryRecordMapper;
    @Autowired
    private SgjsEquipEntryRecordInfoMapper infoMapper;
    @Autowired
    private SgjsEquipEntryRecordInfoDetailMapper detailMapper;
    @Autowired
    private PmServiceApi pmServiceApi;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    private Logger logger= LoggerFactory.getLogger(SgjsEquipEntryRecordServiceImpl.class);

    public SgjsEquipEntryRecord getSgjsEquipEntryRecord(SgjsEquipEntryRecord sgjsEquipEntryRecord) {
        return sgjsEquipEntryRecordMapper.getSgjsEquipEntryRecord(sgjsEquipEntryRecord);
    }

    public List<SgjsEquipEntryRecord> getSgjsEquipEntryRecordList(SgjsEquipEntryRecord sgjsEquipEntryRecord) {
        //筛选条件
        if(StringUtils.isNotEmpty(sgjsEquipEntryRecord.getEntryDateStr())){
            String actualDateStr = sgjsEquipEntryRecord.getEntryDateStr();
            String[] split = actualDateStr.split("~");
            String begin=split[0].replaceAll("(?:年|月|日)", "-");
            String end=split[1].replaceAll("(?:年|月|日)", "-");
            sgjsEquipEntryRecord.setEntryDateBegin(FtDateUtils.parseDate(begin));
            sgjsEquipEntryRecord.setEntryDateEnd(FtDateUtils.parseDate(end));
        }
        List<SgjsEquipEntryRecord> list = sgjsEquipEntryRecordMapper.getSgjsEquipEntryRecordList(sgjsEquipEntryRecord);
        if(CollectionUtils.isEmpty(list)){
            return null;
        }
        List<String> idList = list.stream().map(e -> e.getId() + "").collect(Collectors.toList());
        //根据id批量查询子表信息
        List<SgjsEquipEntryRecordInfo> infoList=infoMapper.selectByIdList(idList);
        for (int i = 0; i < list.size(); i++) {
            String id = list.get(i).getId()+"";
            List<SgjsEquipEntryRecordInfo> infos = infoList.stream().filter(e -> String.valueOf(e.getRecordId()).equals(id)).collect(Collectors.toList());
            list.get(i).setInfoList(infos);
        }
        //查询自检自校详情表数据
        List<String> infoIdList = infoList.stream().map(e -> e.getId() + "").collect(Collectors.toList());
        if(!CollectionUtils.isEmpty(infoIdList)){
            List<SgjsEquipEntryRecordInfoDetail> detailList= detailMapper.selectByInfoId(infoIdList);
            if(!CollectionUtils.isEmpty(detailList)){
                for (int i = 0; i < infoList.size(); i++) {
                    SgjsEquipEntryRecordInfo info = infoList.get(i);
                    String infoId=info.getId()+"";
                    List<SgjsEquipEntryRecordInfoDetail> detaList = detailList.stream().filter(e -> String.valueOf(e.getInfoId()).equals(infoId)).collect(Collectors.toList());
                    info.setDetailList(detaList);
                }
            }
        }
        return list;
    }

    @Transactional
    public int insertSgjsEquipEntryRecord(SgjsEquipEntryRecord sgjsEquipEntryRecord) {
        sgjsEquipEntryRecord.setId(IdWorker.createId());
        sgjsEquipEntryRecord.setCreateUser(SecurityUtils.getUserName());
        sgjsEquipEntryRecord.setCreateTime(DateUtils.getNowDate());
        return sgjsEquipEntryRecordMapper.insertSgjsEquipEntryRecord(sgjsEquipEntryRecord);
    }

    @Transactional
    public int insertSgjsEquipEntryRecordList(List<SgjsEquipEntryRecord> sgjsEquipEntryRecordList) {
        for (SgjsEquipEntryRecord sgjsEquipEntryRecord : sgjsEquipEntryRecordList) {
            sgjsEquipEntryRecord.setId(IdWorker.createId());
            sgjsEquipEntryRecord.setCreateUser(SecurityUtils.getUserName());
            sgjsEquipEntryRecord.setCreateTime(DateUtils.getNowDate());
        }
        return sgjsEquipEntryRecordMapper.insertSgjsEquipEntryRecordList(sgjsEquipEntryRecordList);
    }

    @Transactional
    public int updateSgjsEquipEntryRecord(SgjsEquipEntryRecord sgjsEquipEntryRecord) {
        sgjsEquipEntryRecord.setUpdateUser(SecurityUtils.getUserName());
        sgjsEquipEntryRecord.setUpdateTime(DateUtils.getNowDate());
        return sgjsEquipEntryRecordMapper.updateSgjsEquipEntryRecord(sgjsEquipEntryRecord);
    }

    @Transactional
    public int updateSgjsEquipEntryRecordList(List<SgjsEquipEntryRecord> sgjsEquipEntryRecordList) {
        for (SgjsEquipEntryRecord sgjsEquipEntryRecord : sgjsEquipEntryRecordList) {
            sgjsEquipEntryRecord.setUpdateUser(SecurityUtils.getUserName());
            sgjsEquipEntryRecord.setUpdateTime(DateUtils.getNowDate());
        }
        return sgjsEquipEntryRecordMapper.updateSgjsEquipEntryRecordList(sgjsEquipEntryRecordList);
    }

    @Transactional
    public int deleteSgjsEquipEntryRecord(SgjsEquipEntryRecord sgjsEquipEntryRecord) {
        sgjsEquipEntryRecord.setUpdateUser(SecurityUtils.getUserName());
        sgjsEquipEntryRecord.setUpdateTime(DateUtils.getNowDate());
        return sgjsEquipEntryRecordMapper.deleteSgjsEquipEntryRecord(sgjsEquipEntryRecord);
    }

    @Transactional
    public int deleteSgjsEquipEntryRecordByPks(List<Long> sgjsEquipEntryRecordPkList) {
        return sgjsEquipEntryRecordMapper.deleteSgjsEquipEntryRecordByPks(sgjsEquipEntryRecordPkList);
    }

    @Override
    public AjaxResult sync() {
        AjaxResult result = pmServiceApi.qqchMeasureExpEquList();
        List<SgjsEquipEntryRecord> list=new ArrayList<>();
        if(!result.get("code").toString().equals("200")){
            AjaxResult.error("同步异常");
        }
        Map<String, Object> prjInfo = pmServiceApi.getPrjInfo();

        JSONObject data = JSONObject.parseObject(JSONObject.toJSONString(result.get("data")));
        List<LinkedHashMap> map=(List<LinkedHashMap>)data.get("measureList");
        if(CollectionUtils.isEmpty(map)){
            return AjaxResult.error("同步转换异常");
        }
        List<SgjsEquipEntryRecord> dataList=new ArrayList<>();
        for (int i = 0; i < map.size(); i++) {
            SgjsEquipEntryRecord info=new SgjsEquipEntryRecord();
            JSONObject object = JSONObject.parseObject(JSONObject.toJSONString(map.get(i)));
            info.setMaterialCode(ObjectUtils.toString(object.get("equCode")));
            info.setMaterialName(ObjectUtils.toString(object.get("equName")));
            info.setCategoryName(ObjectUtils.toString(object.get("equTypeName")));
            info.setMaterialSpec(ObjectUtils.toString(object.get("spec")));
            info.setSource(ObjectUtils.toString(object.get("source")));
            info.setNum(ObjectUtils.toInteger(object.get("reqNum")));
            info.setEntryDate(ObjectUtils.toDate(object.get("reqInDate")));
            info.setCreateTime(DateUtils.getNowDate());
            info.setCreateUser(SecurityUtils.getUserId()+"");
            info.setId(IdWorker.createId());
            //info.setProjectId(ObjectUtils.toLong(object.get("projectId")));
            info.setPtVar5(ObjectUtils.toString(object.get("id")));
            if(prjInfo.get("projectId") != null)info.setProjectId(Long.parseLong(prjInfo.get("projectId").toString()));
            info.setProjectName((String) prjInfo.get("projectName"));
            dataList.add(info);
        }
        //查询库中已有所有数据
        List<SgjsEquipEntryRecord> insertList =new ArrayList<>();
        List<SgjsEquipEntryRecord> recordList = sgjsEquipEntryRecordMapper.getSgjsEquipEntryRecordList(new SgjsEquipEntryRecord());
        if(CollectionUtils.isEmpty(recordList)){
            if(!CollectionUtils.isEmpty(dataList)){
                sgjsEquipEntryRecordMapper.insertSgjsEquipEntryRecordList(dataList);
            }
            //同步总部版
            if(!CollectionUtils.isEmpty(insertList)){
                logger.info("源头数据。。。。。。【{}】",JSONObject.toJSONString(insertList));
                syncDataToGm(insertList);
            }
            return AjaxResult.success(dataList);
        }

        //库里有的 不做入库操作    没有的做入库操作  只同步库里没有的
        for (int i = 0; i < list.size(); i++) {
            String syncId = list.get(i).getPtVar5();
            if(StringUtils.isNotEmpty(syncId)){
                List<SgjsEquipEntryRecord> checkList = recordList.stream().filter(e -> e.getPtVar5().equals(syncId)).collect(Collectors.toList());
                if(CollectionUtils.isEmpty(checkList)){//空说明库里没有
                    insertList.add(list.get(i));
                }
            }

        }
        if(!CollectionUtils.isEmpty(insertList)){
            sgjsEquipEntryRecordMapper.insertSgjsEquipEntryRecordList(insertList);
            return AjaxResult.success(list);
        }
        //同步总部版
        if(!CollectionUtils.isEmpty(insertList)){
            logger.info("源头数据。。。。。。【{}】",JSONObject.toJSONString(insertList));
            syncDataToGm(insertList);
        }
        return AjaxResult.success("暂未同步到新数据！");
    }

    private void syncDataToGm(List<SgjsEquipEntryRecord> insertList) {
        Map<String,Object> map=new HashMap<>();
        map.put("type","1");
        map.put("data",insertList);
        long beginMills = System.currentTimeMillis();
        Integer status = 1;
        String errMsg = "";
        try{
            rocketMQTemplate.convertAndSend("sgjs_equip_entry_record:tenantSuccess1", JSONObject.toJSONString(map));
        }catch (Exception e){
            e.printStackTrace();
            status = 0;
            errMsg = e.getMessage();
            logger.error("报错了【{}】",e.getMessage());
            throw e;
        }finally {
            //3、更新syncInfo
            SysSyncInfoLog log=new SysSyncInfoLog();
            log.setBusinessName("sgjs_equip_entry_record");
            log.setStatus(status);
            log.setFailMsg(errMsg);
            log.setPtVar1(JSONObject.toJSONString(map));
            logger.error("sgjs_equip_entry_record同步失败【{}】,时间：【{}】",JSONObject.toJSONString(map),System.currentTimeMillis()-beginMills);
            pmServiceApi.insertSyncLog(log);
        }
    }

    @Override
    public List<SgjsEquipEntryRecord> selectList(SgjsEquipEntryRecord sgjsEquipEntryRecord) {
        return sgjsEquipEntryRecordMapper.getSgjsEquipEntryRecordList(sgjsEquipEntryRecord);
    }

}
