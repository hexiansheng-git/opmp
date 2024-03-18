package com.hhwy.sp.job.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.hhwy.common.core.exception.CustomException;
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
                //根据主id查询  recordId---materialCode
                Map<Long, List<SgjsEquipEntryRecord>> recordIdMap = list.stream().collect(Collectors.groupingBy(e -> e.getId()));

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
                //数据处理并入库
                hanldeDataLogic(data,recordIdMap);
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
     * @param data
     * @param recordIdMap
     */
    private void hanldeDataLogic(List<GetMaterialInfoVo> data, Map<Long, List<SgjsEquipEntryRecord>> recordIdMap) {
        if(CollectionUtils.isEmpty(data)) return;
        List<SgjsEquipEntryRecordInfo> list=new ArrayList<>();
        for (GetMaterialInfoVo vo:data) {
            SgjsEquipEntryRecordInfo info=new SgjsEquipEntryRecordInfo();
            info.setManageCode(vo.getManagementcode());
            info.setMaterialName(vo.getName());
            String code = vo.getCode();//materialCode
            Long recordId=null;
            for (Map.Entry<Long, List<SgjsEquipEntryRecord>> entry : recordIdMap.entrySet()) {
                if (entry.getValue().get(0).getMaterialCode() == code) {
                    recordId = entry.getKey();
                    break; // 找到匹配的value后结束循环
                }
            }
            if(null==recordId){
                logger.error("奇怪竟然没找到表格左侧基础设备信息！！！！！！不合理");
                continue;
            }
            info.setRecordId(recordId);
            list.add(info);
        }
        if(!CollectionUtils.isEmpty(list)){
            sgjsEquipEntryRecordInfoService.insertSgjsEquipEntryRecordInfoList(list);
            //同步总部
            syncDataToGm(list);
        }

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
