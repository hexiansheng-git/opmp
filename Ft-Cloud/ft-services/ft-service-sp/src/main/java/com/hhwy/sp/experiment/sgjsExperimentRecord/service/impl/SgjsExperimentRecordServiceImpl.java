package com.hhwy.sp.experiment.sgjsExperimentRecord.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.hhwy.common.core.exception.CustomException;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.constant.WarnItem;
import com.hhwy.domain.SysSyncInfoLog;
import com.hhwy.domain.base.system.warn.TWarn;
import com.hhwy.feign.service.PmServiceApi;
import com.hhwy.feign.service.SystemServiceApi;
import com.hhwy.sp.buildSchemeManage.sgjsBuildScheme.domain.SgjsWarnConfig;
import com.hhwy.sp.common.warn.CommonBusiness;
import com.hhwy.sp.common.warn.SgjsWarnRecord;
import com.hhwy.sp.experiment.sgjsExperimentRecord.domain.SgjsExperimentRecord;
import com.hhwy.sp.experiment.sgjsExperimentRecord.mapper.SgjsExperimentRecordMapper;
import com.hhwy.sp.experiment.sgjsExperimentRecord.service.ISgjsExperimentRecordService;
import com.hhwy.sp.experiment.sgjsExperimentRecordInfo.domain.SgjsExperimentRecordInfo;
import com.hhwy.sp.experiment.sgjsExperimentRecordInfo.mapper.SgjsExperimentRecordInfoMapper;
import com.hhwy.sp.experiment.sgjsExperimentRecordInfoDetail.domain.SgjsExperimentRecordInfoDetail;
import com.hhwy.sp.experiment.sgjsExperimentRecordInfoDetail.mapper.SgjsExperimentRecordInfoDetailMapper;
import com.hhwy.sp.utils.syncThirdInterface.wushe.GetMaterialInfoInterface;
import com.hhwy.sp.utils.syncThirdInterface.wushe.vo.GetMaterialInfoVo;
import com.hhwy.system.api.domain.SysTenant;
import com.hhwy.system.api.domain.SysUser;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.date.FtDateUtils;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
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
    private SystemServiceApi systemServiceApi;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    @Autowired
    private GetMaterialInfoInterface materialInfoInterface;
    @Value("${gm.url}")
    private String gmUrl;
    @Value("${warn.experimentUrl}")
    private String warnUrl;


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
            if(!CollectionUtils.isEmpty(list)) {
                logger.info("源头数据。。。。。。【{}】",JSONObject.toJSONString(list));
                syncDataToGm(list);
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
        List<Object> codeList = listMap.stream().map(e -> e.get("materialCode")).collect(Collectors.toList());
        paramMap.put("materialCodes",codeList);
        AjaxResult result = materialInfoInterface.syncMaterialInfo(paramMap);
        if(!result.get("code").toString().equals("200")){
            logger.error("同步物设出错！！！！！【{}】",JSONObject.toJSONString(result));
            return result;
        }
        List<GetMaterialInfoVo> dataList = JSONArray.parseArray(JSONObject.toJSONString(result.get("data")), GetMaterialInfoVo.class);
        if(CollectionUtils.isEmpty(dataList)){
            logger.info("暂未同步到数据！！！！！【{}】",JSONObject.toJSONString(dataList));
            return AjaxResult.success("暂未同步到数据！！！！！");
        }
        //过滤source  不为空
        List<GetMaterialInfoVo> infoVoList = dataList.stream().filter(e -> StringUtils.isNotEmpty(e.getSource())).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(infoVoList)){
            logger.info("source全空了。。。。。【{}】",JSONObject.toJSONString(infoVoList));
            return AjaxResult.success();
        }
        Map<String, List<GetMaterialInfoVo>> map = infoVoList.stream().collect(Collectors.groupingBy(e -> e.getCode() + e.getSource()));
        List<SgjsExperimentRecordInfo> list=new ArrayList<>();
        for (int i = 0; i < listMap.size(); i++) {
            String source=listMap.get(i).get("source")+"";
            String manageCode=listMap.get(i).get("manageCode")+"";
            String recordId=listMap.get(i).get("recordId")+"";
            List<GetMaterialInfoVo> voList = map.get(manageCode + source);
            if(CollectionUtils.isEmpty(voList)){
                continue;
            }
            for (GetMaterialInfoVo vo:voList) {
                SgjsExperimentRecordInfo info=new SgjsExperimentRecordInfo();
                info.setManageCode(vo.getManagementcode());//管理编码
                info.setCategoryCode(vo.getTypeCode());//类别编码
                info.setCategoryName(vo.getName());//类别名称
                info.setMaterialName(vo.getName());//物资名称
                info.setPower(vo.getMainPower());//主机功率
                info.setSerialNum(vo.getMainNo());//主机系列号
                info.setBottomNo(vo.getBottomNo());//底盘系列号
                //info.setProductDate();
                info.setSizeMsg(vo.getSizeMsg());
                info.setWeight(vo.getWeight());
                if(!StringUtils.isEmpty(vo.getOriginalValue())){
                    info.setOriginalValue(new BigDecimal(vo.getOriginalValue()));
                }
                //info.setResidualValue();//余值
                if(!StringUtils.isEmpty(vo.getCheckDate())){
                    info.setAcceptDate(DateUtils.dateTime("yyyy-MM-dd",vo.getCheckDate()));
                }
                info.setSource(vo.getSource());
                if(StringUtils.isNotEmpty(recordId)){
                    info.setRecordId(Long.parseLong(recordId));
                }
                list.add(info);
            }

        }
        //管理编号+实际进场日期 去重
        if(!CollectionUtils.isEmpty(list)){
            // 列表对象中，多个字段校验，去重后生成新的列表
            list = list.stream().collect(
                    Collectors.collectingAndThen(Collectors.toCollection(
                            () -> new TreeSet<>(Comparator.comparing(
                                    o -> o.getManageCode()+ o.getEntryDate())
                            )), ArrayList::new));
        }
        return AjaxResult.success(list);
    }

    @Override
    public List<SgjsExperimentRecord> selectList(SgjsExperimentRecord sgjsExperimentRecord) {
        return sgjsExperimentRecordMapper.getSgjsExperimentRecordList(sgjsExperimentRecord);
    }

    @Override
    public AjaxResult experimentRecordJob() {
        //切换到master
        String oldDataSource = DynamicDataSourceContextHolder.peek();
        DynamicDataSourceContextHolder.push("master");
        //获取所有租户
        List<SysTenant> tenantList = systemServiceApi.tenantList();
        //存放所有租户的消息
        List<SgjsWarnConfig> warnList=new ArrayList<>();
        //从总部找预警接收角色 和预警消息内容
//        String url = gmUrl + "/gm/sgjsWarnConfig?warnSubject={warnSubject}";
//        SgjsWarnConfig warnConfigRst = CommonBusiness.getSgjsWarnConfig(url, KCSJ_PLAN_PROCESS.getWarnItem());
        SgjsWarnConfig warnConfigRst =new SgjsWarnConfig();
        warnConfigRst.setWarnObject("普通角色,项目角色");
        warnConfigRst.setWarnObjectId("common,project");
        warnConfigRst.setWarnMassage("你好,消息体！！！");
        warnConfigRst.setWarnRule("111");
        warnConfigRst.setWarnSubject("施工计划--设备台账进场记录");
        if(null==warnConfigRst){
            return AjaxResult.error("未找到总部版预警配置信息");
        }
        try {
            for (SysTenant tenant : tenantList) {
                //切换租户
                String tenantKey = tenant.getTenantKey();
//                String dataSource = TenantDataSourceUtils.getDataSourceNameByTenantKey(tenantKey);
//                DynamicDataSourceContextHolder.push(dataSource);

                List<SgjsExperimentRecordInfo> list = infoMapper.selectByDate();
                if(CollectionUtils.isEmpty(list)){
                    logger.info("该租户【{}】数据为空，暂不执行",tenant.getTenantKey());
                    continue;
                }
                for (SgjsExperimentRecordInfo info:list) {
                    //超过下次检验标定日期 7天后 提醒
                    Date checkDate = info.getNextCheckDate();
                    //超时 延迟7天后 提醒
                    long diffDays = FtDateUtils.getDiffDays(checkDate,DateUtils.getNowDate());//-7
                    if(diffDays==-7){
                        TWarn warn=new TWarn();
                        warn.setCreateTime(DateUtils.getNowDate());
                        warn.setTenantKey(tenantKey);
                        warn.setProjectName(tenant.getTenantName());
                        warn.setWarnItem(WarnItem.SGJS_EXPERIMENT_WARN.getWarnItem());
                        warn.setWarnItemId(WarnItem.SGJS_EXPERIMENT_WARN.getWarnItemId());
                        warn.setWarnScopeType("3");
                        warn.setWarnScope(warnConfigRst.getWarnObject());
                        String warnContent = CommonBusiness.warnMessageHandle(warnConfigRst.getWarnMassage(), tenant.getTenantName(), warnConfigRst.getWarnSubject(), warnConfigRst.getWarnRule());
                        warn.setWarnContent(warnContent);
                        warn.setWarnUrl(warnUrl);
                        warn.setTenantKey(tenantKey);
                        systemServiceApi.addWarnNonGm(warn);
                        logger.info("一个项目只发一次，发完就撤");
                        //总部数据处理
                        SgjsWarnConfig config=new SgjsWarnConfig();
                        config.setCreateTime(DateUtils.getNowDate());
                        config.setWarnSubject(WarnItem.SGJS_EXPERIMENT_WARN.getWarnItem());
                        config.setPtVar1(WarnItem.SGJS_EXPERIMENT_WARN.getWarnItemId());
                        config.setWarnObjectId(warnConfigRst.getWarnObjectId());
                        config.setPrjCode(tenantKey);
                        config.setPrjName(tenant.getTenantName());
                        config.setWarnMassage(warnConfigRst.getWarnMassage());
                        warnList.add(config);
                        break;
                    }

                }
            }

            //同步总部数据
            syncToGm(warnList);
        }catch (Exception e){
            throw new CustomException(e.getMessage());
        }finally {
            DynamicDataSourceContextHolder.poll();
            DynamicDataSourceContextHolder.push(oldDataSource);
        }

        return AjaxResult.success();
    }

    /**
     * 同步总部版数据
     *
     * @param warnList
     */
    private void syncToGm(List<SgjsWarnConfig> warnList) {
        if(CollectionUtils.isEmpty(warnList)){
            logger.info("空了，哪来回哪去！！！！");
            return;
        }
        List<SgjsWarnRecord> rstList=new ArrayList<>();
        Map<String, List<SgjsWarnConfig>> map = warnList.stream().collect(Collectors.groupingBy(e -> e.getPrjCode()));
        for (Map.Entry<String, List<SgjsWarnConfig>> info:map.entrySet()) {
            List<SgjsWarnConfig> valueList = info.getValue();
            if(CollectionUtils.isEmpty(valueList))continue;
            //一个项目一条预警信息
            String warnObjectId = valueList.get(0).getWarnObjectId();
            SgjsWarnConfig sgjsWarnConfig=new SgjsWarnConfig();
            sgjsWarnConfig.setWarnObjectId(warnObjectId);
            List<SysUser> sysUsers = CommonBusiness.getSysUsers(sgjsWarnConfig);
            if (CollUtil.isEmpty(sysUsers)) continue;
            sysUsers.forEach(e->{
                SgjsWarnRecord record=new SgjsWarnRecord();
                record.setProjectCode(valueList.get(0).getPrjCode());
                record.setProjectName(valueList.get(0).getPrjName());
                record.setWarnContent(valueList.get(0).getWarnMassage());
                record.setWarnUserId(e.getUserId()+"");
                record.setWarnUser(e.getUserName());
                record.setWarnSubject(valueList.get(0).getWarnSubject());
                record.setWarnTime(DateUtils.getNowDate());
                record.setStatus("1");
                rstList.add(record);
            });
        }
        try{
            rocketMQTemplate.convertAndSend("sgjs_experiment_record_warn:tenantSuccess", JSONObject.toJSONString(warnList));
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }

}
