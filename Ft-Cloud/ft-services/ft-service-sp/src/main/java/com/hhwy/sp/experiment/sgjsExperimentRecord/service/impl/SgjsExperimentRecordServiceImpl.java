package com.hhwy.sp.experiment.sgjsExperimentRecord.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.domain.SysSyncInfoLog;
import com.hhwy.feign.service.PmServiceApi;
import com.hhwy.sp.experiment.sgjsExperimentRecord.domain.SgjsExperimentRecord;
import com.hhwy.sp.experiment.sgjsExperimentRecord.mapper.SgjsExperimentRecordMapper;
import com.hhwy.sp.experiment.sgjsExperimentRecord.service.ISgjsExperimentRecordService;
import com.hhwy.sp.experiment.sgjsExperimentRecordInfo.domain.SgjsExperimentRecordInfo;
import com.hhwy.sp.experiment.sgjsExperimentRecordInfo.mapper.SgjsExperimentRecordInfoMapper;
import com.hhwy.sp.experiment.sgjsExperimentRecordInfoDetail.domain.SgjsExperimentRecordInfoDetail;
import com.hhwy.sp.experiment.sgjsExperimentRecordInfoDetail.mapper.SgjsExperimentRecordInfoDetailMapper;
import com.hhwy.sp.utils.syncThirdInterface.wushe.GetMaterialInfoInterface;
import com.hhwy.sp.utils.syncThirdInterface.wushe.vo.GetMaterialInfoVo;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author lcf--试验设备进场记录
 * @date 2023-12-11 15:03:30
 * @remark
 */
@Service
public class SgjsExperimentRecordServiceImpl implements ISgjsExperimentRecordService{

    @Autowired
    private SgjsExperimentRecordMapper sgjsExperimentRecordMapper;
    @Autowired
    private SgjsExperimentRecordInfoMapper infoMapper;
    @Autowired
    private SgjsExperimentRecordInfoDetailMapper detailMapper;
    @Autowired
    private PmServiceApi pmServiceApi;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    @Autowired
    private GetMaterialInfoInterface materialInfoInterface;


    private Logger logger= LoggerFactory.getLogger(SgjsExperimentRecordServiceImpl.class);


    public SgjsExperimentRecord getSgjsExperimentRecord(SgjsExperimentRecord sgjsExperimentRecord) {
        return sgjsExperimentRecordMapper.getSgjsExperimentRecord(sgjsExperimentRecord);
    }

    public List<SgjsExperimentRecord> getSgjsExperimentRecordList(SgjsExperimentRecord sgjsExperimentRecord) {
        List<SgjsExperimentRecord> list = sgjsExperimentRecordMapper.getSgjsExperimentRecordList(sgjsExperimentRecord);
        List<String> idList = list.stream().map(e -> e.getId() + "").collect(Collectors.toList());
        if(CollectionUtils.isEmpty(idList)){
            return list;
        }
        //根据主表id 查询子表信息
        List<SgjsExperimentRecordInfo> infoList = infoMapper.selectByIdList(idList);
        for (int i = 0; i < list.size(); i++) {
            String id = list.get(i).getId()+"";
            List<SgjsExperimentRecordInfo> iList = infoList.stream().filter(e -> String.valueOf(e.getRecordId()).equals(id)).collect(Collectors.toList());
            list.get(i).setInfoList(iList);
        }
        //根据子表查询 detail表
        List<String> infoIdList = infoList.stream().map(e -> e.getId()+"").collect(Collectors.toList());
        if(!CollectionUtils.isEmpty(infoIdList)){
            List<SgjsExperimentRecordInfoDetail> detailList=detailMapper.selectByInfoIdList(infoIdList);
            for (int i = 0; i < infoList.size(); i++) {
                String infoId = infoList.get(i).getId()+"";
                List<SgjsExperimentRecordInfoDetail> detaList = detailList.stream().filter(e -> StringUtils.valueOf(e.getInfoId()).equals(infoId)).collect(Collectors.toList());
                infoList.get(i).setDetailList(detaList);
            }
        }
        return list;
    }

    @Transactional
    public int insertSgjsExperimentRecord(SgjsExperimentRecord sgjsExperimentRecord) {
        sgjsExperimentRecord.setId(IdWorker.createId());
        sgjsExperimentRecord.setCreateUser(SecurityUtils.getUserName());
        sgjsExperimentRecord.setCreateTime(DateUtils.getNowDate());
        return sgjsExperimentRecordMapper.insertSgjsExperimentRecord(sgjsExperimentRecord);
    }

    @Transactional
    public int insertSgjsExperimentRecordList(List<SgjsExperimentRecord> sgjsExperimentRecordList) {
        for (SgjsExperimentRecord sgjsExperimentRecord : sgjsExperimentRecordList) {
            sgjsExperimentRecord.setId(IdWorker.createId());
            sgjsExperimentRecord.setCreateUser(SecurityUtils.getUserName());
            sgjsExperimentRecord.setCreateTime(DateUtils.getNowDate());
        }
        return sgjsExperimentRecordMapper.insertSgjsExperimentRecordList(sgjsExperimentRecordList);
    }

    @Transactional
    public int updateSgjsExperimentRecord(SgjsExperimentRecord sgjsExperimentRecord) {
        sgjsExperimentRecord.setUpdateUser(SecurityUtils.getUserName());
        sgjsExperimentRecord.setUpdateTime(DateUtils.getNowDate());
        return sgjsExperimentRecordMapper.updateSgjsExperimentRecord(sgjsExperimentRecord);
    }

    @Transactional
    public int updateSgjsExperimentRecordList(List<SgjsExperimentRecord> sgjsExperimentRecordList) {
        for (SgjsExperimentRecord sgjsExperimentRecord : sgjsExperimentRecordList) {
            sgjsExperimentRecord.setUpdateUser(SecurityUtils.getUserName());
            sgjsExperimentRecord.setUpdateTime(DateUtils.getNowDate());
        }
        return sgjsExperimentRecordMapper.updateSgjsExperimentRecordList(sgjsExperimentRecordList);
    }

    @Transactional
    public int deleteSgjsExperimentRecord(SgjsExperimentRecord sgjsExperimentRecord) {
        sgjsExperimentRecord.setUpdateUser(SecurityUtils.getUserName());
        sgjsExperimentRecord.setUpdateTime(DateUtils.getNowDate());
        return sgjsExperimentRecordMapper.deleteSgjsExperimentRecord(sgjsExperimentRecord);
    }

    @Transactional
    public int deleteSgjsExperimentRecordByPks(List<Long> sgjsExperimentRecordPkList) {
        return sgjsExperimentRecordMapper.deleteSgjsExperimentRecordByPks(sgjsExperimentRecordPkList);
    }

    @Override
    @Transactional
    public AjaxResult sync() {
        AjaxResult result = pmServiceApi.feignExperimentList();
        if(!result.get("code").toString().equals("200")){
            AjaxResult.error("同步异常");
        }
        JSONObject data = JSONObject.parseObject(JSONObject.toJSONString(result.get("data")));
        JSONArray array = JSONObject.parseArray(JSONObject.toJSONString(data.get("experimentList")));
        List<SgjsExperimentRecord> list=new ArrayList<>();
        Map<String, Object> prjInfo = pmServiceApi.getPrjInfo();
        for (int i = 0; i < array.size(); i++) {
            JSONObject object = JSONObject.parseObject(JSONObject.toJSONString(array.get(i)));
            SgjsExperimentRecord info=new SgjsExperimentRecord();
            info.setMaterialCode(ObjectUtils.toString(object.get("equCode")));
            info.setMaterialName(ObjectUtils.toString(object.get("equName")));
            info.setCategoryName(ObjectUtils.toString(object.get("equTypeName")));
            info.setMaterialSpec(ObjectUtils.toString(object.get("spec")));
            if(null!=object.get("source"))info.setSource(ObjectUtils.toString(object.get("source")));
            info.setNum(Integer.parseInt(object.get("reqNum").toString()));
            info.setEntryDate(ObjectUtils.toDate(object.get("reqInDate")));
            info.setCreateTime(DateUtils.getNowDate());
            info.setCreateUser(SecurityUtils.getUserId()+"");
            info.setId(IdWorker.createId());
            //info.setProjectId(ObjectUtils.toLong(object.get("projectId")));
            if(prjInfo.get("projectId") != null)info.setProjectId(Long.parseLong(prjInfo.get("projectId").toString()));
            info.setProjectName((String) prjInfo.get("projectName"));
            //同步数据id
            info.setPtVar5(ObjectUtils.toString(object.get("id")));
            list.add(info);
        }

        List<SgjsExperimentRecord> insertList =new ArrayList<>();

        //查询库中已有所有数据
        List<SgjsExperimentRecord> recordList = sgjsExperimentRecordMapper.getSgjsExperimentRecordList(new SgjsExperimentRecord());
        if(CollectionUtils.isEmpty(recordList)){ //库里没有同步的数据 直接插入
            if(!CollectionUtils.isEmpty(list)){
                sgjsExperimentRecordMapper.insertSgjsExperimentRecordList(list);
            }
            //数据同步总部
            if(!CollectionUtils.isEmpty(insertList)) {
                logger.info("源头数据。。。。。。【{}】",JSONObject.toJSONString(insertList));
                syncDataToGm(insertList);
            }
            return AjaxResult.success(list);
        }
        //库里有的 不做入库操作    没有的做入库操作  只同步库里没有的
        for (int i = 0; i < list.size(); i++) {
            String syncId = list.get(i).getPtVar5();
            if(StringUtils.isNotEmpty(syncId)){
                List<SgjsExperimentRecord> checkList = recordList.stream().filter(e ->syncId.equals(e.getPtVar5())).collect(Collectors.toList());
                if(CollectionUtils.isEmpty(checkList)){//空说明库里没有
                    insertList.add(list.get(i));
                }
            }
        }
        if(!CollectionUtils.isEmpty(insertList)){
            sgjsExperimentRecordMapper.insertSgjsExperimentRecordList(insertList);
            return AjaxResult.success(list);
        }
        //数据同步总部
        if(!CollectionUtils.isEmpty(insertList)) {
            logger.info("源头数据。。。。。。【{}】",JSONObject.toJSONString(insertList));
            syncDataToGm(insertList);
        }
        return AjaxResult.success("未同步到新数据！");
    }

    /**
     * 从前期策划来的数据直接同步总部版
     *
     * @param insertList
     */
    private void syncDataToGm(List<SgjsExperimentRecord> insertList) {
        Map<String,Object> map=new HashMap<>();
        map.put("type","1");
        map.put("data",insertList);
        long beginMills = System.currentTimeMillis();
        Integer status = 1;
        String errMsg = "";
        try{
            logger.info("数据源头【{}】",JSONObject.toJSONString(map));
            rocketMQTemplate.convertAndSend("sgjs_experiment_record:tenantSuccess1", JSONObject.toJSONString(map));
        }catch (Exception e){
            e.printStackTrace();
            status = 0;
            errMsg = e.getMessage();
            logger.error("报错了【{}】",e.getMessage());
            throw e;
        }finally {
            //3、更新syncInfo
            SysSyncInfoLog log=new SysSyncInfoLog();
            log.setBusinessName("sgjs_experiment_record");
            log.setStatus(status);
            log.setFailMsg(errMsg);
            log.setPtVar1(JSONObject.toJSONString(map));
            logger.error("sgjs_experiment_record同步失败【{}】,时间：【{}】",JSONObject.toJSONString(map),System.currentTimeMillis()-beginMills);
            pmServiceApi.insertSyncLog(log);
        }

    }

    @Override
    public AjaxResult syncWuShe(List<Map> listMap) {
        Map<String,Object> paramMap=new HashMap<>();
        paramMap.put("projectCode",listMap.get(0).get("projectCode"));
        List<Object> codeList = listMap.stream().map(e -> e.get("manageCode")).collect(Collectors.toList());
        paramMap.put("manageCodes",codeList);
        AjaxResult result = materialInfoInterface.syncMaterialInfo(paramMap);
        if(!result.get("code").toString().equals("200")){
            logger.error("同步物设出错！！！！！【{}】",JSONObject.toJSONString(result));
            return result;
        }
        List<GetMaterialInfoVo> dataList = JSONArray.parseArray(JSONObject.toJSONString(result.get("data")), GetMaterialInfoVo.class);
        Map<String, List<GetMaterialInfoVo>> map = dataList.stream().collect(Collectors.groupingBy(e -> e.getCode() + e.getSource()));
        List<SgjsExperimentRecordInfo> list=new ArrayList<>();
        for (int i = 0; i < listMap.size(); i++) {
            String source=listMap.get(i).get("source")+"";
            String manageCode=listMap.get(i).get("manageCode")+"";
            List<GetMaterialInfoVo> voList = map.get(manageCode + source);
            if(CollectionUtils.isEmpty(voList)){
                continue;
            }
            for (GetMaterialInfoVo vo:voList) {
                SgjsExperimentRecordInfo info=new SgjsExperimentRecordInfo();
                info.setManageCode(vo.getManagementcode());//管理编码
                info.setCategoryCode(vo.getTyptCode());//类别编码
                info.setCategoryName(vo.getName());//类别名称
                info.setMaterialName(vo.getName());//物资名称
//            info.setPower();
//            info.setSerialNum();
//            info.setBottomNo();
//            info.setProductDate();
//            info.setSizeMsg(vo.getSizeMsg());
//            info.setWeight();
                if(!StringUtils.isEmpty(vo.getOriginalValue())){
                    info.setOriginalValue(new BigDecimal(vo.getOriginalValue()));
                }
                //info.setResidualValue();//余值
                if(!StringUtils.isEmpty(vo.getCheckDate())){
                    info.setAcceptDate(DateUtils.dateTime("yyyy-MM-dd",vo.getCheckDate()));
                }
                info.setSource(vo.getSource());
                list.add(info);
            }

        }
        return result;
    }

    @Override
    public List<SgjsExperimentRecord> selectList(SgjsExperimentRecord sgjsExperimentRecord) {
        return sgjsExperimentRecordMapper.getSgjsExperimentRecordList(sgjsExperimentRecord);
    }

}
