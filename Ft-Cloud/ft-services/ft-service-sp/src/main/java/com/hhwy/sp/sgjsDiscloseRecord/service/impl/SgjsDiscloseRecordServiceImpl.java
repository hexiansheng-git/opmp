package com.hhwy.sp.sgjsDiscloseRecord.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.hhwy.common.core.exception.CustomException;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.common.tenant.utils.TenantDataSourceUtils;
import com.hhwy.constant.WarnItem;
import com.hhwy.domain.base.system.warn.TWarn;
import com.hhwy.domain.log.SysSyncLog;
import com.hhwy.enums.FlowEnum;
import com.hhwy.feign.service.ILogServiceApi;
import com.hhwy.feign.service.SystemServiceApi;
import com.hhwy.sp.buildSchemeManage.sgjsBuildScheme.domain.SgjsWarnConfig;
import com.hhwy.sp.common.FlowInfoSearchUtil;
import com.hhwy.sp.common.constant.DataCurrentState;
import com.hhwy.sp.common.warn.CommonBusiness;
import com.hhwy.sp.common.warn.SgjsWarnRecord;
import com.hhwy.sp.sgjsDiscloseRecord.domain.SgjsDiscloseRecord;
import com.hhwy.sp.sgjsDiscloseRecord.domain.vo.DiscloseRecordPushVo;
import com.hhwy.sp.sgjsDiscloseRecord.mapper.SgjsDiscloseRecordMapper;
import com.hhwy.sp.sgjsDiscloseRecord.service.ISgjsDiscloseRecordService;
import com.hhwy.sp.sync.mq.service.ISysSyncInfoService4Sp;
import com.hhwy.system.api.domain.SysTenant;
import com.hhwy.system.api.domain.SysUser;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.date.FtDateUtils;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author cjh
 * @date 2023-11-23 14:47:22
 * @remark
 */
@Service
public class SgjsDiscloseRecordServiceImpl implements ISgjsDiscloseRecordService {

    private static final Logger logger = LoggerFactory.getLogger(SgjsDiscloseRecordServiceImpl.class);
    @Value("${gm.url}")
    private String gmUrl;
    @Value("${warn.disCloseRecordUrl}")
    private String warnUrl;
    @Autowired
    private SgjsDiscloseRecordMapper sgjsDiscloseRecordMapper;
    @Autowired
    private ISysSyncInfoService4Sp syncInfoService;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    @Autowired
    private SystemServiceApi systemServiceApi;
    @Autowired
    private ILogServiceApi logServiceApi;



    public SgjsDiscloseRecord getSgjsDiscloseRecord(SgjsDiscloseRecord sgjsDiscloseRecord) {
        return sgjsDiscloseRecordMapper.getSgjsDiscloseRecord(sgjsDiscloseRecord);
    }

    public List<SgjsDiscloseRecord> getSgjsDiscloseRecordList(SgjsDiscloseRecord sgjsDiscloseRecord) {
        String dataType = sgjsDiscloseRecord.getDataType();
        if(StringUtils.isEmpty(dataType)) {
            return new ArrayList<>();
        }
        return sgjsDiscloseRecordMapper.getSgjsDiscloseRecordList(sgjsDiscloseRecord);
    }

    @Transactional
    public int insertSgjsDiscloseRecord(SgjsDiscloseRecord sgjsDiscloseRecord) {
        sgjsDiscloseRecord.setId(IdWorker.createId());
        sgjsDiscloseRecord.setCreateTime(DateUtils.getNowDate());
        //施工方案编号  方案推送过来的编号，用方案编号+FAJD﹔
        //新增的交底编号001+项目编码+XZJD,按增加顺序排
        if(sgjsDiscloseRecord.getPtVar1().equals("1")) {// 0 新增 1 同步
            String no = sgjsDiscloseRecord.getPtVar5() + DataCurrentState.DIS_SYNC;
            sgjsDiscloseRecord.setPtVar5(no);//交底编号
            sgjsDiscloseRecord.setDiscloseLevel("2");//交底等级 默认二级不可修改
            sgjsDiscloseRecord.setDiscloseName(sgjsDiscloseRecord.getDiscloseName()+DataCurrentState.DIS_NAME);//交底名称
            logger.info("施工方案清单推送一二级方案安全交底req:【{}】",JSONObject.toJSONString(sgjsDiscloseRecord));
        }else{
            int count = sgjsDiscloseRecordMapper.selectCount(new SgjsDiscloseRecord());
            String s = StringUtils.leftPad(count + 1 + "", 3, "0");
            String pjCode = SecurityUtils.getTenantKey();
            String no=s+pjCode+ DataCurrentState.DIS_ADD;
            sgjsDiscloseRecord.setPtVar5(no);
        }
        sgjsDiscloseRecord.setDataType(DataCurrentState.ONE_OR_TWO);
        int i = sgjsDiscloseRecordMapper.insertSgjsDiscloseRecord(sgjsDiscloseRecord);
        //同步日志记录
        if(sgjsDiscloseRecord.getPtVar1().equals("1")){
            logServiceApi.insertSysSyncLog(buildLog(JSONObject.toJSONString(sgjsDiscloseRecord),i+"",i+""));
        }
        //同步总部版
        Map<String,Object> map=new HashMap<>();
        map.put("type","0");//0新增1删除2修改
        map.put("data",sgjsDiscloseRecord);
        pushSyncData(map);
        return i;
    }

    /**
     * 日志记录赋值
     *
     * @param req 请求参数
     * @param res 响应结果
     * @param msg 结果描述
     * @return
     */
    private SysSyncLog buildLog(String req,String res,String msg){
        SysSyncLog syncLog = new SysSyncLog();
        syncLog.setId(IdWorker.createId());
        syncLog.setCreateUser(SecurityUtils.getUserName());
        syncLog.setCreateTime(new Date());
        syncLog.setDelFlag("0");
        syncLog.setInterfaceName("施工方案评审同步【一二级方案安全交底】");
        syncLog.setFunName("施工方案评审同步【一二级方案安全交底】");
        syncLog.setReq(req);
        syncLog.setRes(res);
        syncLog.setStatus(StringUtils.isBlank(msg)?"0":"1");
        syncLog.setRemark(ObjectUtils.nvlString(msg));
        syncLog.setPtVar2(SecurityUtils.getTenantKey());
        syncLog.setUpdateTime(DateUtils.getNowDate());
        syncLog.setUpdateUser(SecurityUtils.getUserName());
        return syncLog;
    }


    /**
     * 同步总部版
     *
     * @param map 同步传参
     * @data 2024-08-27
     */
    public void pushSyncData(Map<String,Object> map){
        logger.info("map传参:【{}】",JSONObject.toJSONString(map));
        rocketMQTemplate.convertAndSend("sgjs_disclose_record_new:tenantSuccess", JSONObject.toJSONString(map));
    }


    @Transactional
    public int insertSgjsDiscloseRecordList(List<SgjsDiscloseRecord> sgjsDiscloseRecordList) {
        if(CollectionUtils.isEmpty(sgjsDiscloseRecordList)) {
            return 0;
        }
        for (SgjsDiscloseRecord sgjsDiscloseRecord : sgjsDiscloseRecordList) {
            sgjsDiscloseRecord.setId(IdWorker.createId());
            sgjsDiscloseRecord.setCreateUser(SecurityUtils.getSysUser().getNickName());
            sgjsDiscloseRecord.setCreateTime(DateUtils.getNowDate());
        }
        return sgjsDiscloseRecordMapper.insertSgjsDiscloseRecordList(sgjsDiscloseRecordList);
    }

    @Transactional
    public int updateSgjsDiscloseRecord(SgjsDiscloseRecord sgjsDiscloseRecord) {
        sgjsDiscloseRecord.setUpdateUser(SecurityUtils.getSysUser().getNickName());
        sgjsDiscloseRecord.setUpdateTime(DateUtils.getNowDate());
        Map<String,Object> map=new HashMap<>();
        if(sgjsDiscloseRecord.getType().equals("1")){//删除
            SgjsDiscloseRecord info=sgjsDiscloseRecordMapper.getById(sgjsDiscloseRecord.getId());
            String ptVar1 = info.getPtVar1();//数量来源  0新增 1同步  同步不允许删除
            if(StringUtils.isNotEmpty(ptVar1) && ptVar1.equals("1")){
                logger.info("该数据不允许删除");
                return -1;
            }
            map.put("type","1");//0新增1删除2修改
            sgjsDiscloseRecord.setDelFlag("1");
        }else{
            map.put("type","2");//0新增1删除2修改
        }
        map.put("data",sgjsDiscloseRecord);
        int i = sgjsDiscloseRecordMapper.updateSgjsDiscloseRecord(sgjsDiscloseRecord);
        pushSyncData(map);
        return i;
    }

    @Transactional
    public int updateSgjsDiscloseRecordList(List<SgjsDiscloseRecord> sgjsDiscloseRecordList) {
        if(CollectionUtils.isEmpty(sgjsDiscloseRecordList)) {
            return 0;
        }
        String dataType = sgjsDiscloseRecordList.get(0).getDataType();
        List<SgjsDiscloseRecord> addList = new ArrayList<>();
        List<SgjsDiscloseRecord> updateList = new ArrayList<>();

        for (SgjsDiscloseRecord sgjsDiscloseRecord : sgjsDiscloseRecordList) {
            sgjsDiscloseRecord.setDelFlag("0");
            if("1".equals(sgjsDiscloseRecord.getIsAdd())) {
                addList.add(sgjsDiscloseRecord);
            } else {
                sgjsDiscloseRecord.setUpdateUser(SecurityUtils.getSysUser().getNickName());
                sgjsDiscloseRecord.setUpdateTime(DateUtils.getNowDate());
                updateList.add(sgjsDiscloseRecord);
            }
        }
        int i = 0;
        if(CollectionUtils.isNotEmpty(addList)) {
            i = i + insertSgjsDiscloseRecordList(addList);
        }
        if(CollectionUtils.isNotEmpty(updateList)) {
            i = i + sgjsDiscloseRecordMapper.updateSgjsDiscloseRecordList(updateList);
        }
        pushDataToHeadquarters(dataType);
        return i;
    }

    /**
     * 推送数据到总部
     * @param
     */
    public void pushDataToHeadquarters(String dataType) {
        if(StringUtils.isEmpty(dataType)) {
            return;
        }
        SgjsDiscloseRecord sgjsDiscloseRecord = new SgjsDiscloseRecord();
        sgjsDiscloseRecord.setDataType(dataType);
        List<SgjsDiscloseRecord> sgjsDiscloseRecordList = getSgjsDiscloseRecordList(sgjsDiscloseRecord);
        DiscloseRecordPushVo pushVo = new DiscloseRecordPushVo();
        pushVo.setDataType(dataType);
        pushVo.setRecordList(sgjsDiscloseRecordList);
        syncInfoService.pushSgjsDiscloseRecord(pushVo);
    }

    @Transactional
    public int deleteSgjsDiscloseRecord(SgjsDiscloseRecord sgjsDiscloseRecord) {
        return sgjsDiscloseRecordMapper.deleteSgjsDiscloseRecord(sgjsDiscloseRecord);
    }

    @Transactional
    public int deleteSgjsDiscloseRecordByPks(List<Long> sgjsDiscloseRecordPkList) {
        int i = sgjsDiscloseRecordMapper.deleteSgjsDiscloseRecordByPks(sgjsDiscloseRecordPkList);
        if(i > 0) {
            List<SgjsDiscloseRecord> sgjsDiscloseRecordList = sgjsDiscloseRecordMapper.getSgjsDiscloseRecordListByIds(sgjsDiscloseRecordPkList);
            if(CollectionUtils.isNotEmpty(sgjsDiscloseRecordList)){
                String dataType = sgjsDiscloseRecordList.get(0).getDataType();
                pushDataToHeadquarters(dataType);
            }
        }

        return i;
    }

    public List<SgjsDiscloseRecord> getSgjsDiscloseRecordListByNames(List<String> discloseNames) {
        return sgjsDiscloseRecordMapper.getSgjsDiscloseRecordListByNames(discloseNames);
    }

    @Override
    public int importData(List<SgjsDiscloseRecord> sgjsDiscloseRecordList, String dataType) {
        if(CollectionUtils.isEmpty(sgjsDiscloseRecordList)) {
            return 0;
        }
        List<String> discloseNames = sgjsDiscloseRecordList.stream().map(SgjsDiscloseRecord::getDiscloseName).collect(Collectors.toList());
        List<SgjsDiscloseRecord> exists = getSgjsDiscloseRecordListByNames(discloseNames);
        int count = sgjsDiscloseRecordMapper.selectCount(new SgjsDiscloseRecord());
        for (SgjsDiscloseRecord sgjsDiscloseRecord : sgjsDiscloseRecordList) {
            sgjsDiscloseRecord.setDataType(dataType);
            String discloseName = sgjsDiscloseRecord.getDiscloseName();
            sgjsDiscloseRecord.setIsAdd("1");
            String discloseLevel = sgjsDiscloseRecord.getDiscloseLevel();
            if("一级交底".equals(discloseLevel)) {
                discloseLevel = "1";
            }
            if("二级交底".equals(discloseLevel)) {
                discloseLevel = "2";
            }
            if("三级交底".equals(discloseLevel)) {
                discloseLevel = "3";
            }
            //交底编号设置
            String s = StringUtils.leftPad(count + 2 + "", 3, "0");
            String pjCode = SecurityUtils.getTenantKey();
            String no=s+pjCode+ DataCurrentState.DIS_ADD;
            sgjsDiscloseRecord.setPtVar5(no);
            sgjsDiscloseRecord.setDiscloseLevel(discloseLevel);
            if(CollectionUtils.isNotEmpty(exists)) {
                SgjsDiscloseRecord sgjsDiscloseRecord1 = exists.stream().filter(vo -> discloseName.equals(vo.getDiscloseName())).findFirst().orElse(null);
                if(sgjsDiscloseRecord1 != null) {
                    sgjsDiscloseRecord.setId(sgjsDiscloseRecord1.getId());
                    sgjsDiscloseRecord.setFileGroupId(sgjsDiscloseRecord1.getFileGroupId());
                    sgjsDiscloseRecord.setIsAdd(null);
                }
            }
        }

        return updateSgjsDiscloseRecordList(sgjsDiscloseRecordList);
    }

    @Override
    public void disCloseWarn() {
        logger.info("-----------------------预警开始-----------------------");
        //从总部获取预警配置信息
        String url = gmUrl + "/gm/sgjsWarnConfig/list?warnSubject={warnSubject}";
        SgjsWarnConfig sgjsWarnConfig = CommonBusiness.getSgjsWarnConfig(url, "一级、二级方案交底");
        logger.info("sgjsWarnConfig--->【{}】",JSONObject.toJSONString(sgjsWarnConfig));
        if (null == sgjsWarnConfig) {
            logger.error("获取一二级交底数据异常");
            return;
        }
        String oldDataSource = DynamicDataSourceContextHolder.peek();
        DynamicDataSourceContextHolder.push("master");
        try {
            //获取所有租户
            List<SysTenant> tenantList = systemServiceApi.tenantList();
            //逻辑处理
            handelLogic(tenantList, sgjsWarnConfig);
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        } finally {
            DynamicDataSourceContextHolder.poll();
            DynamicDataSourceContextHolder.push(oldDataSource);
        }
        logger.info("-----------------------预警结束-----------------------");
    }

    /**
     * 功能描述: 所有租户发送预警
     *
     * @param tenantList
     * @param sgjsWarnConfig
     */
    private void handelLogic(List<SysTenant> tenantList, SgjsWarnConfig sgjsWarnConfig) {
        logger.info("tenantList--->【{}】,sgjsWarnConfig--->【{}】",JSONObject.toJSONString(tenantList),JSONObject.toJSONString(sgjsWarnConfig));
        //存放所有租户的消息
        List<SgjsWarnConfig> warnList=new ArrayList<>();
        for (SysTenant tenant : tenantList) {
            DynamicDataSourceContextHolder.push(TenantDataSourceUtils.getDataSourceNameByTenantKey(tenant.getTenantKey()));
            //查询 创建日期 大于30日的  发送预警
            List<SgjsDiscloseRecord> list = sgjsDiscloseRecordMapper.selectData();
            if(CollectionUtils.isEmpty(list)){
                continue;
            }
            String[] roleKeys = StrUtil.splitToArray(sgjsWarnConfig.getWarnObjectId(), ",");
            List<SysUser> allSysUsers = CommonBusiness.getSysUsers(roleKeys, tenant.getTenantKey());
            logger.info("allSysUsers------>【{}】",allSysUsers);
            if (CollUtil.isEmpty(allSysUsers)) {
                logger.info("根据角色获取用户, 无数据，总部配置：{} --- 租户：{}", JSON.toJSONString(sgjsWarnConfig), tenant.getTenantName());
                continue;
            }
            List<SysUser> sysUsers = allSysUsers.stream().filter(p -> StrUtil.isNotBlank(p.getTenantKey()) && p.getTenantKey().equals(tenant.getTenantKey())).collect(Collectors.toList());
            if (CollUtil.isEmpty(sysUsers)) {
                logger.info("根据租户过滤后, 无数据，{} --- 租户：{}", JSON.toJSONString(allSysUsers), tenant.getTenantName());
                continue;
            }
            for (int i = 0; i < list.size(); i++) {
                //超过30天  每2天提醒一次
                Date createTime = list.get(i).getCreateTime();
                long twoDays = FtDateUtils.getDiffDays(createTime,DateUtils.getNowDate());
                long ltr=twoDays%2;
                if(ltr==0 && twoDays>0){
                    TWarn warn=new TWarn();
                    warn.setCreateTime(DateUtils.getNowDate());
                    warn.setTenantKey(tenant.getTenantKey());
                    warn.setProjectName(tenant.getTenantName());
                    warn.setWarnItem(WarnItem.SGJS_DISCLOSE_RECORD.getWarnItem());
                    warn.setWarnItemId(WarnItem.SGJS_DISCLOSE_RECORD.getWarnItemId());
                    warn.setWarnScopeType("3");
                    warn.setWarnScope(sgjsWarnConfig.getWarnObject());
                    String warnContent = CommonBusiness.warnMessageHandle(sgjsWarnConfig.getWarnMassage(), tenant.getTenantName(), sgjsWarnConfig.getWarnSubject(), sgjsWarnConfig.getWarnRule());
                    logger.info("wwww--->【{}】",warnContent);
                    warn.setWarnContent(warnContent);
                    warn.setWarnUrl(warnUrl);
                    warn.setTenantKey(tenant.getTenantKey());
                    systemServiceApi.addWarnNonGm(warn);
                    //总部数据处理
                    SgjsWarnConfig config=new SgjsWarnConfig();
                    config.setCreateTime(DateUtils.getNowDate());
                    config.setWarnSubject(WarnItem.SGJS_DISCLOSE_RECORD.getWarnItem());
                    config.setPtVar1(WarnItem.SGJS_DISCLOSE_RECORD.getWarnItemId());
                    config.setWarnObjectId(sgjsWarnConfig.getWarnObjectId());
                    config.setPrjCode(tenant.getTenantKey());
                    config.setPrjName(tenant.getTenantName());
                    config.setWarnMassage(sgjsWarnConfig.getWarnMassage());
                    config.setPtVar1(warnContent);
                    config.setTenantKey(tenant.getTenantKey());
                    warnList.add(config);
                }
            }

        }

        //同步总部数据
        syncToGm(warnList,sgjsWarnConfig);
    }

    @Override
    public AjaxResult disCloseRecordListener(Long id,String status) {
        if(null==id) return AjaxResult.error("空了---->【{}】",id);
        //一、修改流程状态
        SgjsDiscloseRecord info=new SgjsDiscloseRecord();
        info.setId(id);
        info.setPtVar3(status);
        info.setUpdateTime(DateUtils.getNowDate());
        info.setUpdateUser(SecurityUtils.getUserName());
        sgjsDiscloseRecordMapper.updateSgjsDiscloseRecord(info);
        //二、总部推送
        Map<String,Object> map=new HashMap<>();
        map.put("type","2");//0新增1删除2修改
        map.put("data",info);
        pushSyncData(map);
        return AjaxResult.success();
    }

    @Override
    public SgjsDiscloseRecord detail(SgjsDiscloseRecord sgjsDiscloseRecord) {
        Long id = sgjsDiscloseRecord.getId();
        if (null==id)return null;
        SgjsDiscloseRecord info=sgjsDiscloseRecordMapper.getById(id);
        FlowInfoSearchUtil.getFlowInfo(info, FlowEnum.SGJS_DISCLOSE_RECORD);
        info.setBusinessId(id+"");
        return info;
    }

    /**
     * 有id新增
     * 没有id修改
     *
     * @param sgjsDiscloseRecord
     * @return
     */
    @Override
    public void handleAdd(SgjsDiscloseRecord sgjsDiscloseRecord) {
        if(null==sgjsDiscloseRecord.getId()){
            insertSgjsDiscloseRecord(sgjsDiscloseRecord);
        }else{
            updateSgjsDiscloseRecord(sgjsDiscloseRecord);
        }
    }


    private void syncToGm(List<SgjsWarnConfig> warnList, SgjsWarnConfig sgjsWarnConfig) {
        logger.info("warnList--->【{}】",JSONObject.toJSONString(warnList));
        if(CollectionUtils.isEmpty(warnList)){
            logger.info("空了，哪来回哪去！！！！");
            return;
        }
        List<SgjsWarnRecord> rstList=new ArrayList<>();
        Map<String, List<SgjsWarnConfig>> map = warnList.stream().collect(Collectors.groupingBy(e -> e.getTenantKey()));
        //一个项目一条预警信息 在总部    所有项目对应的都是同一个角色
        String warnObjectId = sgjsWarnConfig.getWarnObjectId();
        //查出该角色下所有项目用户  根据项目编码过滤
        List<SysUser> sysUsers = CommonBusiness.getSysUsers(new String[]{warnObjectId}, null);
        logger.info("sysUsers--->【{}】",JSONObject.toJSONString(sysUsers));
        for (Map.Entry<String, List<SgjsWarnConfig>> info:map.entrySet()) {
            List<SgjsWarnConfig> valueList = info.getValue();
            if(CollectionUtils.isEmpty(valueList))continue;
            if (CollectionUtils.isEmpty(sysUsers)) continue;
            //根据租户信息 处理发送的预警消息
            List<SysUser> userList = sysUsers.stream().filter(e -> e.getTenantKey().equals(info.getKey())).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(userList)) continue;
            userList.forEach(e->{
                SgjsWarnRecord record=new SgjsWarnRecord();
                record.setProjectCode(valueList.get(0).getPrjCode());
                record.setProjectName(valueList.get(0).getPrjName());
                record.setWarnContent(valueList.get(0).getPtVar1());
                record.setWarnUserId(e.getUserId()+"");
                record.setWarnUser(e.getNickName());
                record.setWarnSubject(valueList.get(0).getWarnSubject());
                record.setWarnTime(DateUtils.getNowDate());
                record.setStatus("1");
                record.setPtVar1(valueList.get(0).getId()+"");
                rstList.add(record);
            });
        }
        try{
            logger.info("总部同步---->【{}】",JSONObject.toJSONString(rstList));
            rocketMQTemplate.convertAndSend("sgjs_disclose_record_warn:tenantSuccess", JSONObject.toJSONString(rstList));
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }
}
