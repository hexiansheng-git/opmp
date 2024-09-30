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
import com.hhwy.sp.experiment.sgjsExperimentRecord.domain.SgjsExperimentRecord;
import com.hhwy.sp.experiment.sgjsExperimentRecord.service.ISgjsExperimentRecordService;
import com.hhwy.sp.experiment.sgjsExperimentRecordInfo.domain.SgjsExperimentRecordInfo;
import com.hhwy.sp.experiment.sgjsExperimentRecordInfo.service.ISgjsExperimentRecordInfoService;
import com.hhwy.sp.utils.syncThirdInterface.wushe.GetMaterialInfoInterface;
import com.hhwy.sp.utils.syncThirdInterface.wushe.vo.GetMaterialInfoVo;
import com.hhwy.system.api.domain.SysTenant;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 试验进场设备定时任务
 *
 * @author lcf
 * @data 2023-12-18
 */
@Service
public class ExperimentJobServiceImpl {

    private Logger logger= LoggerFactory.getLogger(ExperimentJobServiceImpl.class);

    @Autowired
    private SystemServiceApi systemServiceApi;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    @Autowired
    private PmServiceApi pmServiceApi;
    @Autowired
    private GetMaterialInfoInterface materialInfoInterface;
    @Autowired
    private ISgjsExperimentRecordService sgjsExperimentRecordService;
    @Autowired
    private ISgjsExperimentRecordInfoService iSgjsExperimentRecordInfoService;

    public void getWuSheMaterialRecord(){
        //切换到master
        String oldDataSource = DynamicDataSourceContextHolder.peek();
        DynamicDataSourceContextHolder.push("master");
        //获取所有租户
        List<SysTenant> tenantList = systemServiceApi.tenantList();
        try {
            for (SysTenant tenant : tenantList) {
                //切换租户
                String tenantKey = tenant.getTenantKey();//租户key就是项目编码
                String dataSource = TenantDataSourceUtils.getDataSourceNameByTenantKey(tenantKey);
                DynamicDataSourceContextHolder.push(dataSource);
                //获取试验设备进场记录所有数据 主表id
                List<SgjsExperimentRecord> list = sgjsExperimentRecordService.selectList(new SgjsExperimentRecord());
                //根据项目编码和设备编码查询物设系统设备进场记录
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
                Map<String, List<GetMaterialInfoVo>> wuSheDataMap = data.stream().collect(Collectors.groupingBy(e -> e.getCode() + e.getName()));
                //数据处理并入库
                hanldeDataLogic(list,wuSheDataMap);
            }
        }catch (Exception e){
            logger.error("ExperimentJobServiceImpl定时任务报错了---->【{}】",JSONObject.toJSONString(tenantList));
            throw new CustomException(e.getMessage());
        }finally {
            DynamicDataSourceContextHolder.poll();
            DynamicDataSourceContextHolder.push(oldDataSource);
        }
    }

    /**
     * 数据处理并入库
     * 根据设备编码和来源进行数据反选匹配
     *
     * @param wuSheDataMap 物设返回结果
     * @param list 左表数据
     */
    private void hanldeDataLogic(List<SgjsExperimentRecord> list, Map<String, List<GetMaterialInfoVo>> wuSheDataMap) {
        List<SgjsExperimentRecordInfo> rstList=new ArrayList<>();
        for (SgjsExperimentRecord info:list) {
            String key=info.getMaterialCode()+info.getSource();
            List<GetMaterialInfoVo> voList = wuSheDataMap.get(key);
            for (GetMaterialInfoVo infoVo :voList){
                SgjsExperimentRecordInfo recordInfo=new SgjsExperimentRecordInfo();
                recordInfo.setSource(infoVo.getSource());
                recordInfo.setRecordId(info.getId());
                recordInfo.setMaterialName(infoVo.getName());
                recordInfo.setManageCode(infoVo.getManagementcode());
                recordInfo.setWeight(infoVo.getWeight());//自重
                recordInfo.setManufacturer(infoVo.getDeviceFrom());//厂商
                recordInfo.setMaterialSpec(infoVo.getSpec());//型号
                recordInfo.setCategoryCode(infoVo.getTypeCode());//类别编码
                recordInfo.setCategoryName(infoVo.getTypeName());//类别名称
                recordInfo.setSizeMsg(infoVo.getSizeMsg());//外形尺寸
                String originalValue = infoVo.getOriginalValue();
                if(StringUtils.isNotEmpty(originalValue)){
                    recordInfo.setOriginalValue(new BigDecimal(originalValue));//原值
                }
                //recordInfo.setResidualValue();//余值
                String checkDate = infoVo.getCheckDate();
                if(StringUtils.isNotEmpty(checkDate)){
                    Date date = DateUtils.dateTime("yyyy-MM-dd", checkDate);
                    recordInfo.setAcceptDate(date);//验收日期
                    recordInfo.setEntryDate(date);//实际进场日期
                }
                String exitDate = infoVo.getExitDate();
                if(StringUtils.isNotEmpty(exitDate)){
                    Date date = DateUtils.dateTime("yyyy-MM-dd", exitDate);
                    recordInfo.setExitDate(date);//退场日期
                }
                recordInfo.setSource(infoVo.getSource());//来源
                recordInfo.setCurrentState(infoVo.getStatus());//当前状态
                recordInfo.setProjectId(info.getProjectId());
                recordInfo.setProjectName(info.getProjectName());
                rstList.add(recordInfo);
            }
        }
        if(CollectionUtils.isEmpty(rstList)){
            logger.error("空了未找到匹配数据");
            return;
        }
        iSgjsExperimentRecordInfoService.insertSgjsExperimentRecordInfoList(rstList);
        //同步总部
        syncDataToGm(rstList);
    }


    /**
     * 总部版同步
     *
     * @param list
     */
    private void syncDataToGm(List<SgjsExperimentRecordInfo> list) {
        long beginMills = System.currentTimeMillis();
        Integer status = 1;
        String errMsg = "";
        try{
            rocketMQTemplate.convertAndSend("sgjs_job_experiment_record_info:tenantSuccess1", JSONObject.toJSONString(list));
        }catch (Exception e){
            e.printStackTrace();
            status = 0;
            errMsg = e.getMessage();
            logger.error("报错了【{}】",e.getMessage());
            throw e;
        }finally {
            //3、更新syncInfo
            SysSyncInfoLog log=new SysSyncInfoLog();
            log.setBusinessName("sgjs_job_experiment_record_info");
            log.setStatus(status);
            log.setFailMsg(errMsg);
            log.setPtVar1(JSONObject.toJSONString(list));
            logger.error("sgjs_job_experiment_record_info同步失败【{}】,时间：【{}】",JSONObject.toJSONString(list),System.currentTimeMillis()-beginMills);
            pmServiceApi.insertSyncLog(log);
        }
    }
}
