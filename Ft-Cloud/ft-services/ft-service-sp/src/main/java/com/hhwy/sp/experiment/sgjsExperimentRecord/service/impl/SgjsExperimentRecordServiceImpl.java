package com.hhwy.sp.experiment.sgjsExperimentRecord.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.util.SecurityUtils;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
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
    private GetMaterialInfoInterface materialInfoInterface;


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
        List<SgjsExperimentRecordInfoDetail> detailList=detailMapper.selectByInfoIdList(infoIdList);
        for (int i = 0; i < infoList.size(); i++) {
            String infoId = infoList.get(i).getId()+"";
            List<SgjsExperimentRecordInfoDetail> detaList = detailList.stream().filter(e -> StringUtils.valueOf(e.getInfoId()).equals(infoId)).collect(Collectors.toList());
            infoList.get(i).setDetailList(detaList);
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
        for (int i = 0; i < array.size(); i++) {
            JSONObject object = JSONObject.parseObject(JSONObject.toJSONString(array.get(i)));
            SgjsExperimentRecord info=new SgjsExperimentRecord();
            info.setMaterialCode(ObjectUtils.toString(object.get("equCode")));
            info.setMaterialName(ObjectUtils.toString(object.get("equName")));
            info.setCategoryName(ObjectUtils.toString(object.get("equTypeName")));
            info.setMaterialSpec(ObjectUtils.toString(object.get("spec")));
            info.setSource(ObjectUtils.toString(object.get("source")));
            info.setNum(Integer.parseInt(object.get("reqNum").toString()));
            info.setEntryDate(ObjectUtils.toDate(object.get("reqInDate")));
            info.setCreateTime(DateUtils.getNowDate());
            info.setCreateUser(SecurityUtils.getUserId()+"");
            info.setId(IdWorker.createId());
            info.setProjectId(ObjectUtils.toLong(object.get("projectId")));
            //同步数据id
            info.setPtVar5(ObjectUtils.toString(object.get("id")));
            list.add(info);
        }
        //查询库中已有所有数据
        List<SgjsExperimentRecord> recordList = sgjsExperimentRecordMapper.getSgjsExperimentRecordList(new SgjsExperimentRecord());
        if(CollectionUtils.isEmpty(recordList)){ //库里没有同步的数据 直接插入
            if(!CollectionUtils.isEmpty(list)){
                sgjsExperimentRecordMapper.insertSgjsExperimentRecordList(list);
            }
            return AjaxResult.success(list);
        }
        //库里有的 不做入库操作    没有的做入库操作  只同步库里没有的
        List<SgjsExperimentRecord> insertList =new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            String syncId = list.get(i).getPtVar5();
            List<SgjsExperimentRecord> checkList = recordList.stream().filter(e -> e.getPtVar5().equals(syncId)).collect(Collectors.toList());
            if(CollectionUtils.isEmpty(checkList)){//空说明库里没有
                insertList.add(list.get(i));
            }
        }
        if(!CollectionUtils.isEmpty(insertList)){
            sgjsExperimentRecordMapper.insertSgjsExperimentRecordList(insertList);
            return AjaxResult.success(list);
        }
        return AjaxResult.success("未同步到新数据！");
    }

    @Override
    public AjaxResult syncWuShe(Map<String, Object> map) {
        String projectId = ObjectUtils.toString(map.get("projectCode"));
        if(StringUtils.isEmpty(projectId)) {
            return AjaxResult.error("项目编码不能为空");
        }
        AjaxResult result = materialInfoInterface.syncMaterialInfo(map);
        if(!result.get("code").toString().equals("200")){
            return result;
        }
        List<GetMaterialInfoVo> dataList = JSONArray.parseArray(JSONObject.toJSONString(result.get("data")), GetMaterialInfoVo.class);
        List<SgjsExperimentRecordInfo> list=new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            SgjsExperimentRecordInfo info=new SgjsExperimentRecordInfo();
            GetMaterialInfoVo vo = dataList.get(i);
            info.setManageCode(ObjectUtils.toString(vo.getManageCode()));//设备管理编码
            info.setCategoryName(ObjectUtils.toString(vo.getCategoryName()));
            info.setCategoryCode(ObjectUtils.toString(vo.getCategoryCode()));
            info.setMaterialName(ObjectUtils.toString(vo.getMaterialName()));
            info.setManufacturer(ObjectUtils.toString(vo.getCountryFactory()));
            info.setPower(ObjectUtils.toString(vo.getMEnginePower()));
            info.setBottomNo(ObjectUtils.toString(vo.getChassisNo()));
            info.setProductDate(ObjectUtils.toDate(vo.getMProduceDate()));
            info.setSizeMsg(ObjectUtils.toString(vo.getSizeMsg()));
            info.setWeight(ObjectUtils.toString(vo.getTheWeight()));
            info.setOriginalValue(ObjectUtils.toDecimal(vo.getOriginalValue()));
            info.setAcceptDate(ObjectUtils.toDate(vo.getCheckDate()));
            info.setEntryDate(ObjectUtils.toDate(vo.getCheckDate()));
            info.setExitDate(ObjectUtils.toDate(vo.getExitDate()));
            info.setSource(ObjectUtils.toString(vo.getSource()));
            list.add(info);
        }
        return result;
    }

}
