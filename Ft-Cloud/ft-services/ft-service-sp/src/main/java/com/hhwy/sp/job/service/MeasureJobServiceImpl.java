package com.hhwy.sp.job.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.hhwy.common.core.exception.CustomException;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.tenant.utils.TenantDataSourceUtils;
import com.hhwy.domain.SysSyncInfoLog;
import com.hhwy.feign.service.PmServiceApi;
import com.hhwy.feign.service.SystemServiceApi;
import com.hhwy.sp.sgjsMeasure.sgjsEquipEntryRecord.sgjsEquipEntryRecord.domain.SgjsEquipEntryRecord;
import com.hhwy.sp.sgjsMeasure.sgjsEquipEntryRecord.sgjsEquipEntryRecord.service.ISgjsEquipEntryRecordService;
import com.hhwy.sp.sgjsMeasure.sgjsEquipEntryRecord.sgjsEquipEntryRecordInfo.domain.SgjsEquipEntryRecordInfo;
import com.hhwy.sp.sgjsMeasure.sgjsEquipEntryRecord.sgjsEquipEntryRecordInfo.service.ISgjsEquipEntryRecordInfoService;
import com.hhwy.sp.utils.syncThirdInterface.wushe.GetMaterialInfoInterface;
import com.hhwy.sp.utils.syncThirdInterface.wushe.vo.GetMaterialInfoVo;
import com.hhwy.system.api.domain.SysTenant;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 测量进场设备定时任务
 *
 * @author lcf
 * @data 2023-12-18
 */
@Service
public class MeasureJobServiceImpl {

    private Logger logger= LoggerFactory.getLogger(MeasureJobServiceImpl.class);
    @Autowired
    private SystemServiceApi systemServiceApi;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    @Autowired
    private PmServiceApi pmServiceApi;
    @Autowired
    private GetMaterialInfoInterface materialInfoInterface;
    @Autowired
    private ISgjsEquipEntryRecordService equipEntryRecordService;
    @Autowired
    private ISgjsEquipEntryRecordInfoService sgjsEquipEntryRecordInfoService;
    /**
     * 测量进场设备
     *
     * @return
     */
    @GetMapping("/getWuSheMeasureMaterialInfo")
    public void getWuSheMeasureMaterialInfo(){
        //切换到master
        String oldDataSource = DynamicDataSourceContextHolder.peek();
        DynamicDataSourceContextHolder.push("master");
        //获取所有租户
        List<SysTenant> tenantList = systemServiceApi.tenantList();
        try{
            for (SysTenant tenant : tenantList) {
                //切换租户
                String tenantKey = tenant.getTenantKey();//租户key就是项目编码
                String dataSource = TenantDataSourceUtils.getDataSourceNameByTenantKey(tenantKey);
                DynamicDataSourceContextHolder.push(dataSource);
                //获取测量设备进场记录所有数据
                List<SgjsEquipEntryRecord> list = equipEntryRecordService.selectList(new SgjsEquipEntryRecord());

                List<String> materialCodeList = list.stream().map(e -> e.getMaterialCode()).collect(Collectors.toList());
                if(CollectionUtils.isEmpty(materialCodeList)){
                    logger.error("该项目【{}】设备编码为空",tenantKey);
                    continue;
                }
                Map<String,Object> map=new HashMap<>();
                map.put("projectCode",tenantKey);
                map.put("materialCodes",materialCodeList);
                AjaxResult result = materialInfoInterface.syncMaterialInfo(map);
                if(!"200".equals(result.get("code").toString())){
                    return;
                }
                List<GetMaterialInfoVo> data = JSONArray.parseArray(result.get("data").toString(), GetMaterialInfoVo.class);
                if(CollectionUtils.isEmpty(data)){
                    logger.info("空了，下一个【{}】",JSONObject.toJSONString(data));
                    continue;
                }
                Map<String, List<GetMaterialInfoVo>> listMap = data.stream().collect(Collectors.groupingBy(e -> e.getCode() + e.getSource()));
                //数据处理并入库
                hanldeDataLogic(list,listMap);
            }
        }catch (Exception e){
            throw new CustomException(e.getMessage());
        }finally {
            DynamicDataSourceContextHolder.poll();
            DynamicDataSourceContextHolder.push(oldDataSource);
        }
    }

    /**
     * 数据处理并入库
     *
     * @param list
     * @param listMap
     */
    private void hanldeDataLogic(List<SgjsEquipEntryRecord> list, Map<String, List<GetMaterialInfoVo>> listMap) {
        List<SgjsEquipEntryRecordInfo> rstList=new ArrayList<>();
        for (SgjsEquipEntryRecord info:list) {
            String key=info.getMaterialCode()+info.getSource();
            List<GetMaterialInfoVo> voList = listMap.get(key);
            if(CollectionUtils.isEmpty(voList)){
                logger.info("空了。。。。。。");
                continue;
            }
            for (int i = 0; i < voList.size(); i++) {
                GetMaterialInfoVo infoVo = voList.get(i);
                SgjsEquipEntryRecordInfo recordInfo=new SgjsEquipEntryRecordInfo();
                recordInfo.setRecordId(info.getId());
                recordInfo.setManageCode(infoVo.getManagementcode());
                recordInfo.setCategoryName(infoVo.getTypeName());
                recordInfo.setCategoryCode(infoVo.getTypeCode());
                recordInfo.setMaterialName(infoVo.getName());
                recordInfo.setManufacturer(infoVo.getDeviceFrom());
                recordInfo.setMaterialSpec(infoVo.getSpec());
                recordInfo.setPower(infoVo.getMainPower());
                recordInfo.setSerialNum(infoVo.getMainNo());//主机系列号
                recordInfo.setBottomNo(infoVo.getBottomNo());
                String checkDate = infoVo.getCheckDate();
                if(StringUtils.isNotEmpty(checkDate)){
                    recordInfo.setEntryDate(DateUtils.dateTime("yyyy-MM-dd",checkDate));//进场日期
                }
                String exitDate = infoVo.getExitDate();
                if(StringUtils.isNotEmpty(exitDate)){

                    recordInfo.setExitDate(DateUtils.dateTime("yyyy-MM-dd",exitDate));//退场日期
                }
                recordInfo.setCurrentState(infoVo.getStatus());//当前状态
                recordInfo.setProjectId(info.getProjectId());
                recordInfo.setProjectName(info.getProjectName());
                rstList.add(recordInfo);
            }
        }
        if(CollectionUtils.isEmpty(rstList)){
            logger.error("未找到匹配数据。。。。。。。。。。。。");
            return;
        }
        //入库
        sgjsEquipEntryRecordInfoService.insertSgjsEquipEntryRecordInfoList(rstList);
        //同步总部
        syncDataToGm(rstList);
    }



    /**
     * 总部版同步
     *
     * @param list
     */
    private void syncDataToGm(List<SgjsEquipEntryRecordInfo> list) {
        long beginMills = System.currentTimeMillis();
        Integer status = 1;
        String errMsg = "";
        try{
            rocketMQTemplate.convertAndSend("sgjs_job_equip_record_info:tenantSuccess1", JSONObject.toJSONString(list));
        }catch (Exception e){
            e.printStackTrace();
            status = 0;
            errMsg = e.getMessage();
            logger.error("报错了【{}】",e.getMessage());
            throw e;
        }finally {
            //3、更新syncInfo
            SysSyncInfoLog log=new SysSyncInfoLog();
            log.setBusinessName("sgjs_job_equip_record_info");
            log.setStatus(status);
            log.setFailMsg(errMsg);
            log.setPtVar1(JSONObject.toJSONString(list));
            logger.error("sgjs_job_equip_record_info同步失败【{}】,时间：【{}】",JSONObject.toJSONString(list),System.currentTimeMillis()-beginMills);
            pmServiceApi.insertSyncLog(log);
        }
    }
}
