package com.hhwy.sd.planProcess.kcsjPlanProcess.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.hhwy.common.core.exception.CustomException;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.common.tenant.utils.TenantDataSourceUtils;
import com.hhwy.constant.WarnItem;
import com.hhwy.domain.base.project.ProjectDto;
import com.hhwy.domain.base.system.warn.TWarn;
import com.hhwy.feign.service.PmServiceApi;
import com.hhwy.feign.service.SystemServiceApi;
import com.hhwy.sd.organManage.util.StatisticsUtils;
import com.hhwy.sd.organManage.util.TreeCountUtils;
import com.hhwy.sd.planProcess.kcsjPlanProcess.domain.KcsjPlanProcess;
import com.hhwy.sd.planProcess.kcsjPlanProcess.domain.KcsjWarnConfig;
import com.hhwy.sd.planProcess.kcsjPlanProcess.domain.KcsjWarnRecord;
import com.hhwy.sd.planProcess.kcsjPlanProcess.mapper.KcsjPlanProcessMapper;
import com.hhwy.sd.planProcess.kcsjPlanProcess.service.IKcsjPlanProcessService;
import com.hhwy.sd.utils.http.WarnCommonBusiness;
import com.hhwy.system.api.domain.SysTenant;
import com.hhwy.system.api.domain.SysUser;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.date.FtDateUtils;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.tree.TreeUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.math3.distribution.MixtureMultivariateNormalDistribution;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.hhwy.constant.WarnItem.KCSJ_PLAN_PROCESS;

/**
 * @author cjh
 * @date 2023-12-18 11:13:27
 * @remark
 */
@Service
public class KcsjPlanProcessServiceImpl implements IKcsjPlanProcessService {

    @Autowired
    private KcsjPlanProcessMapper kcsjPlanProcessMapper;
    @Autowired
    private PmServiceApi pmServiceApi;
    @Autowired
    private SystemServiceApi systemServiceApi;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Value("${warn.planProgressIUrl}")
    private String warnUrl;
    @Value("${gm.url}")
    private String gmUrl;


    private Logger logger= LoggerFactory.getLogger(KcsjPlanProcessServiceImpl.class);

    public KcsjPlanProcess getKcsjPlanProcess(KcsjPlanProcess kcsjPlanProcess) {
        return kcsjPlanProcessMapper.getKcsjPlanProcess(kcsjPlanProcess);
    }

    public List<KcsjPlanProcess> getKcsjPlanProcessList(KcsjPlanProcess kcsjPlanProcess) {
        Long pid = kcsjPlanProcess.getPid();
        List<KcsjPlanProcess> allList = kcsjPlanProcessMapper.getKcsjPlanProcessList(new KcsjPlanProcess());
        List<KcsjPlanProcess> kcsjPlanProcessList = kcsjPlanProcessMapper.getKcsjPlanProcessList(kcsjPlanProcess);
        if(CollectionUtils.isNotEmpty(allList) && CollectionUtils.isNotEmpty(kcsjPlanProcessList)) {
            if(allList.size() == kcsjPlanProcessList.size()) {
                return TreeUtil.build(kcsjPlanProcessList, pid);
            }
        }
        if(CollectionUtils.isEmpty(kcsjPlanProcessList)) {
            return kcsjPlanProcessList;
        }
        TreeCountUtils<KcsjPlanProcess> treeCountUtils = new TreeCountUtils<>();
        return TreeUtil.build(treeCountUtils.queryTree(allList, kcsjPlanProcessList, pid), pid);
    }

    @Transactional
    public int insertKcsjPlanProcess(KcsjPlanProcess kcsjPlanProcess) {
        kcsjPlanProcess.setId(IdWorker.createId());
        kcsjPlanProcess.setCreateUser(SecurityUtils.getUserName());
        kcsjPlanProcess.setCreateTime(DateUtils.getNowDate());
        return kcsjPlanProcessMapper.insertKcsjPlanProcess(kcsjPlanProcess);
    }

    @Transactional
    public int insertKcsjPlanProcessList(List<KcsjPlanProcess> kcsjPlanProcessList) {
        for (KcsjPlanProcess kcsjPlanProcess : kcsjPlanProcessList) {
            kcsjPlanProcess.setId(IdWorker.createId());
            kcsjPlanProcess.setCreateUser(SecurityUtils.getUserName());
            kcsjPlanProcess.setCreateTime(DateUtils.getNowDate());
        }
        return kcsjPlanProcessMapper.insertKcsjPlanProcessList(kcsjPlanProcessList);
    }

    @Transactional
    public int updateKcsjPlanProcess(KcsjPlanProcess kcsjPlanProcess) {
        kcsjPlanProcess.setUpdateUser(SecurityUtils.getUserName());
        kcsjPlanProcess.setUpdateTime(DateUtils.getNowDate());
        return kcsjPlanProcessMapper.updateKcsjPlanProcess(kcsjPlanProcess);
    }

    @Transactional
    public int updateKcsjPlanProcessList(List<KcsjPlanProcess> kcsjPlanProcessList) {
        if(CollectionUtils.isEmpty(kcsjPlanProcessList)) {
            return 0;
        }
        List<KcsjPlanProcess> kcsjPlanProcesses = TreeUtil.treeToListSupplyId(kcsjPlanProcessList);
        List<KcsjPlanProcess> addList = new ArrayList<>();
        List<KcsjPlanProcess> updateList = new ArrayList<>();
        for (KcsjPlanProcess kcsjPlanProcess : kcsjPlanProcesses) {
            if("1".equals(kcsjPlanProcess.getIsAdd())) {
                kcsjPlanProcess.setCreateUser(SecurityUtils.getSysUser().getNickName());
                kcsjPlanProcess.setCreateTime(DateUtils.getNowDate());
                ProjectDto projectDto = pmServiceApi.getProjectDto();
                kcsjPlanProcess.setProjectId(projectDto.getProjectId());
                kcsjPlanProcess.setProjectName(projectDto.getProjectName());
                kcsjPlanProcess.setRegionId(projectDto.getRegionId());
                kcsjPlanProcess.setRegionName(projectDto.getRegionName());
                addList.add(kcsjPlanProcess);
            } else {
                kcsjPlanProcess.setUpdateUser(SecurityUtils.getSysUser().getNickName());
                kcsjPlanProcess.setUpdateTime(DateUtils.getNowDate());
                updateList.add(kcsjPlanProcess);
            }
        }
        int i = 0;
        if(CollectionUtils.isNotEmpty(addList)) {
            i += kcsjPlanProcessMapper.insertKcsjPlanProcessList(addList);
        }
        if(CollectionUtils.isNotEmpty(updateList)) {
            i += kcsjPlanProcessMapper.updateKcsjPlanProcessList(updateList);
        }
        doSendGm();
        return i;
    }

    @Transactional
    public int deleteKcsjPlanProcess(KcsjPlanProcess kcsjPlanProcess) {
        return kcsjPlanProcessMapper.deleteKcsjPlanProcess(kcsjPlanProcess);
    }

    @Transactional
    public int deleteKcsjPlanProcessByPks(List<Long> kcsjPlanProcessPkList) {
        List<KcsjPlanProcess> kcsjPlanProcessList = kcsjPlanProcessMapper.getKcsjPlanProcessList(new KcsjPlanProcess());
//            checkPlan(kcsjPlanProcessList);
//            return 0;
//        }
        List<Long> needDeleteIds = new ArrayList<>();
        needDeleteIds.addAll(kcsjPlanProcessPkList);
        if(CollectionUtils.isNotEmpty(kcsjPlanProcessList) && CollectionUtils.isNotEmpty(kcsjPlanProcessPkList)) {
            for (Long id: kcsjPlanProcessPkList) {
                List<KcsjPlanProcess> build = TreeUtil.build(kcsjPlanProcessList, id);
                if(CollectionUtils.isEmpty(build)) {
                    continue;
                }
                List<KcsjPlanProcess> kcsjPlanProcesses = TreeUtil.treeToListWithoutId(build);
                if(CollectionUtils.isEmpty(kcsjPlanProcesses)) {
                    continue;
                }
                for (KcsjPlanProcess kcsjPlanProcess: kcsjPlanProcesses) {
                    needDeleteIds.add(kcsjPlanProcess.getId());
                }
            }
        }
        List<Long> collect = needDeleteIds.stream().distinct().collect(Collectors.toList());
        if(CollectionUtils.isEmpty(collect)) {
            checkPlan();
            return 0;
        }
        int i = kcsjPlanProcessMapper.deleteKcsjPlanProcessByPks(collect);
        checkPlan();
        doSendGm();
        return i;
    }

    //校验子级计划开始时间、结束时间必须在父级开始、结束时间范围内
    //实际开始日期、实际结束日期 是当前日期之前；结束日期在开始日期之后
    private void checkPlan(){
        List<KcsjPlanProcess> list = kcsjPlanProcessMapper.getKcsjPlanProcessList(new KcsjPlanProcess());
        if(CollectionUtils.isEmpty(list))
            return ;
        Date now = new Date();
        StringBuilder sb = new StringBuilder();
        Map<String,Date> minMaxDateMap = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            KcsjPlanProcess temp = list.get(i);
            String workCode = ObjectUtils.nvlString(temp.getWorkCode());
            String workName = ObjectUtils.nvlString(temp.getWorkName());
            if(temp.getPlanStartDate() != null && temp.getPlanEndDate() != null && temp.getPlanEndDate().before(temp.getPlanStartDate()))
                sb.append(String.format("作业代码[%s]作业名称[%s]的计划结束时间不能早于计划开始时间\n",workCode,workName ));
            if(temp.getActStartDate() != null && temp.getActEndDate() != null && temp.getActEndDate().before(temp.getActStartDate()))
                sb.append(String.format("作业代码[%s]作业名称[%s]的实际结束时间不能早于实际开始时间\n",workCode,workName ));
            if(temp.getActStartDate() != null && temp.getActStartDate().after(now))
                sb.append(String.format("作业代码[%s]作业名称[%s]的实际开始时间不能晚于当前时间\n",workCode,workName ));
            if(temp.getActEndDate() != null && temp.getActEndDate().after(now))
                sb.append(String.format("作业代码[%s]作业名称[%s]的实际结束时间不能晚于当前时间\n",workCode,workName ));
            //统计父级的最小发起日期，最大结束日期
            if(temp.getPid() == null || temp.getPid() < 1L)
                continue;
            Date min = minMaxDateMap.get(temp.getPid()+"_min");
            if(min == null){
                minMaxDateMap.put(temp.getPid()+"_min", temp.getPlanStartDate());
                minMaxDateMap.put(temp.getPid()+"_max", temp.getPlanEndDate());
            }else{
                if(min == null || temp.getPlanStartDate().before(min)){
                    minMaxDateMap.put(temp.getPid()+"_min", temp.getPlanStartDate());
                }
                Date max = minMaxDateMap.get(temp.getPid()+"_max");
                if(max == null || temp.getPlanEndDate().after(max)){
                    minMaxDateMap.put(temp.getPid()+"_max", temp.getPlanEndDate());
                }
            }
            Date actMin = minMaxDateMap.get(temp.getPid()+"_actMin");
            if(actMin == null){
                minMaxDateMap.put(temp.getPid()+"_actMin", temp.getActStartDate());
                minMaxDateMap.put(temp.getPid()+"_actMax", temp.getActEndDate());
            }else{
                if(actMin == null || temp.getActStartDate().before(actMin)){
                    minMaxDateMap.put(temp.getPid()+"_actMin", temp.getActStartDate());
                }
                Date max = minMaxDateMap.get(temp.getPid()+"_actMax");
                if(max == null || temp.getActEndDate().after(max)){
                    minMaxDateMap.put(temp.getPid()+"_actMax", temp.getActEndDate());
                }
            }
        }
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < list.size(); i++) {
            KcsjPlanProcess temp = list.get(i);
            String workCode = ObjectUtils.nvlString(temp.getWorkCode());
            String workName = ObjectUtils.nvlString(temp.getWorkName());
            Date min = minMaxDateMap.get(temp.getId()+"_min");
            if(min != null && temp.getPlanStartDate() != null && temp.getPlanStartDate().after(min)){
                sb.append(String.format("作业代码[%s]作业名称[%s]的计划开始时间[%s]不能晚于子级开始时间[%s]\n"
                        ,workCode,workName,fmt.format(temp.getPlanStartDate()),fmt.format(min)));
            }
            Date max = minMaxDateMap.get(temp.getId()+"_max");
            if(max != null && temp.getPlanEndDate() != null && temp.getPlanEndDate().before(max)){
                sb.append(String.format("作业代码[%s]作业名称[%s]的计划结束时间[%s]不能早于子级结束时间[%s]\n"
                        ,workCode,workName,fmt.format(temp.getPlanEndDate()),fmt.format(max)));
            }
            Date actMin = minMaxDateMap.get(temp.getId()+"_actMin");
            if(actMin != null && temp.getActStartDate() != null && temp.getActStartDate().after(actMin)){
                sb.append(String.format("作业代码[%s]作业名称[%s]的实际开始时间[%s]不能晚于子级开始时间[%s]\n"
                        ,workCode,workName,fmt.format(temp.getActStartDate()),fmt.format(actMin)));
            }
            Date actMax = minMaxDateMap.get(temp.getId()+"_actMax");
            if(actMax != null && temp.getActEndDate() != null && temp.getActEndDate().before(actMax)){
                sb.append(String.format("作业代码[%s]作业名称[%s]的实际结束时间[%s]不能早于子级结束时间[%s]\n"
                        ,workCode,workName,fmt.format(temp.getActEndDate()),fmt.format(actMax)));
            }
        }
        Assert.isTrue(sb.length() < 1, sb.toString());
    } 

    /**
     * 同步前期策划工作计划
     */
    @Override
    @Transactional
    public void sync() {
        AjaxResult result = pmServiceApi.getData();
        if(!result.get("code").toString().equals("200")){
            AjaxResult.error("同步异常");
        }
        JSONArray array = JSONObject.parseArray(JSONObject.toJSONString(result.get("data")));
        if(CollectionUtils.isEmpty(array)) {
            AjaxResult.error("同步异常!");
        }
        List<KcsjPlanProcess> existList = kcsjPlanProcessMapper.getKcsjPlanProcessList(new KcsjPlanProcess());
        deleteAllKcsjPlanProcess();
        List<KcsjPlanProcess> kcsjPlanProcessList = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            JSONObject object = JSONObject.parseObject(JSONObject.toJSONString(array.get(i)));
            KcsjPlanProcess kcsjPlanProcess = new KcsjPlanProcess();
            if(object.get("id") != null) kcsjPlanProcess.setId(Long.valueOf(ObjectUtils.toString(object.get("id"))));
            if(object.get("pid") != null) {
                Long pid = Long.valueOf(ObjectUtils.toString(object.get("pid")));
                if(pid == 0) pid = null;
                kcsjPlanProcess.setPid(pid);
            }
            String workCode = ObjectUtils.toString(object.get("planWbsCode"));
            kcsjPlanProcess.setWorkCode(workCode);
            kcsjPlanProcess.setWorkName(ObjectUtils.toString(object.get("planWbsName")));
            kcsjPlanProcess.setWorkContent(ObjectUtils.toString(object.get("workContent")));
            kcsjPlanProcess.setUnit(ObjectUtils.toString(object.get("unit")));
            kcsjPlanProcess.setQuantity(ObjectUtils.toString(object.get("workNum")));
            Date startTime = null;
            Date endTime = null;
            try {
                if(object.get("startTime") != null) {
                    startTime = DateUtils.parseDate(ObjectUtils.toString(object.get("startTime")), "yyyy-MM-dd");
                    kcsjPlanProcess.setPlanStartDate(startTime);
                }

                if(object.get("endTime") != null) {
                    endTime = DateUtils.parseDate(ObjectUtils.toString(object.get("endTime")), "yyyy-MM-dd");
                    kcsjPlanProcess.setPlanEndDate(endTime);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            kcsjPlanProcess.setPlanDuration(StatisticsUtils.getDaysByRangeDate(startTime, endTime));
            kcsjPlanProcess.setIsAdd("1");
            if(CollectionUtils.isNotEmpty(existList)) {
                KcsjPlanProcess existVO = existList.stream().filter(vo -> workCode.equals(vo.getWorkCode())).findFirst().orElse(null);
                if(existVO != null) {
                    kcsjPlanProcess.setActStartDate(existVO.getActStartDate());
                    kcsjPlanProcess.setActEndDate(existVO.getActEndDate());
                    kcsjPlanProcess.setActDuration(existVO.getActDuration());
                }
            }
            kcsjPlanProcessList.add(kcsjPlanProcess);
        }
        updateKcsjPlanProcessList(TreeUtil.build(kcsjPlanProcessList, null));
        doSendGm();
    }

    /**
     * 勘察设计--计划进度 预警消息发送
     *
     * @author lcf
     * @date 2024-04-01
     * @return
     */
    @Override
    public AjaxResult jobPlanProcess() {
        //切换到master
        String oldDataSource = DynamicDataSourceContextHolder.peek();
        DynamicDataSourceContextHolder.push("master");
        //获取所有租户
        List<SysTenant> tenantList = systemServiceApi.tenantList();
        //存放所有租户的消息
        List<KcsjWarnConfig> warnList=new ArrayList<>();
        //从总部找预警接收角色 和预警消息内容
        String url = gmUrl + "/gm/sgjsWarnConfig?warnSubject={warnSubject}";
        KcsjWarnConfig warnConfigRst = WarnCommonBusiness.getSgjsWarnConfig(url, WarnItem.KCSJ_PLAN_PROCESS.getWarnItem());
        if(null==warnConfigRst){
            return AjaxResult.error("未找到总部版预警配置信息");
        }
        try {
            for (SysTenant tenant : tenantList) {
                String tenantKey = tenant.getTenantKey();
                String dataSource = TenantDataSourceUtils.getDataSourceNameByTenantKey(tenantKey);
                DynamicDataSourceContextHolder.push(dataSource);
                //过滤出实际开始日期为空的  不为空就不预警了
                List<KcsjPlanProcess> list = kcsjPlanProcessMapper.selectByDate();
                if(CollectionUtils.isEmpty(list)){
                    logger.info("该租户【{}】数据为空，暂不执行",tenant.getTenantKey());
                    continue;
                }
                for (KcsjPlanProcess info:list) {
                    //发个消息
                    Date startDate = info.getPlanStartDate();
                    if(null==startDate){
                        continue;
                    }
                    //超期每2天提醒一次   2  4   6  请注意每2天  2天后
                    long twoDays = FtDateUtils.getDiffDays(DateUtils.getNowDate(),startDate);
                    long ltr=twoDays%2;
                    //提前7天提醒   7天前的数据
                    long diffDays = FtDateUtils.getDiffDays(startDate, DateUtils.getNowDate());
                    //2个条件满足一个就行  因为预警只预警一次  一个项目上
                    if((ltr==0 && twoDays>0) || diffDays==7) {
                        TWarn warn=new TWarn();
                        warn.setCreateTime(DateUtils.getNowDate());
                        warn.setTenantKey(tenantKey);
                        warn.setProjectName(tenant.getTenantName());
                        warn.setWarnItem(WarnItem.KCSJ_PLAN_PROCESS.getWarnItem());
                        warn.setWarnItemId(WarnItem.KCSJ_PLAN_PROCESS.getWarnItemId());
                        warn.setWarnScopeType("3");
                        warn.setWarnScope(warnConfigRst.getWarnObject());
                        String warnContent = WarnCommonBusiness.warnMessageHandle(warnConfigRst.getWarnMassage(), tenant.getTenantName(), warnConfigRst.getWarnSubject(), warnConfigRst.getWarnRule());
                        warn.setWarnContent(warnContent);
                        warn.setWarnUrl(warnUrl);
                        warn.setTenantKey(tenantKey);
                        systemServiceApi.addWarnNonGm(warn);
                        logger.info("一个项目只发一次，发完就撤");
                        //总部数据处理
                        KcsjWarnConfig config=new KcsjWarnConfig();
                        config.setCreateTime(DateUtils.getNowDate());
                        config.setWarnSubject(WarnItem.KCSJ_PLAN_PROCESS.getWarnItem());
                        config.setPtVar1(WarnItem.KCSJ_PLAN_PROCESS.getWarnItemId());
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
     * 同步总部
     *
     * @param warnList
     */
    private void syncToGm(List<KcsjWarnConfig> warnList) {
        if(CollectionUtils.isEmpty(warnList)){
            logger.info("空了，哪来回哪去！！！！");
            return;
        }
        List<KcsjWarnRecord> rstList=new ArrayList<>();
        Map<String, List<KcsjWarnConfig>> map = warnList.stream().collect(Collectors.groupingBy(e -> e.getPrjCode()));
        for (Map.Entry<String, List<KcsjWarnConfig>> info:map.entrySet()) {
            List<KcsjWarnConfig> valueList = info.getValue();
            if(CollectionUtils.isEmpty(valueList))continue;
            //一个项目一条预警信息
            String warnObjectId = valueList.get(0).getWarnObjectId();
            KcsjWarnConfig sgjsWarnConfig=new KcsjWarnConfig();
            sgjsWarnConfig.setWarnObjectId(warnObjectId);
            List<SysUser> sysUsers = WarnCommonBusiness.getSysUsers(sgjsWarnConfig);
            if (CollUtil.isEmpty(sysUsers)) continue;
            sysUsers.forEach(e->{
                KcsjWarnRecord record=new KcsjWarnRecord();
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
            rocketMQTemplate.convertAndSend("kcsj_plan_process_warn:tenantSuccess", JSONObject.toJSONString(rstList));
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }


    public static void main(String[] args) throws ParseException {
        SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
        Date parse = sd.parse("2024-04-03");
        Date nowDate = DateUtils.getNowDate();
        Long days = FtDateUtils.getDiffDays(parse,nowDate);
        System.out.println(-8%2+"脑子宕机了。。。。。"+days);
    }
    
    
    public void deleteAllKcsjPlanProcess() {
        kcsjPlanProcessMapper.deleteAllKcsjPlanProcess();
    }

    //数据推送总部版
    public void doSendGm(){
        List<KcsjPlanProcess> kcsjEquipEntryRecordList = kcsjPlanProcessMapper.getKcsjPlanProcessList(new KcsjPlanProcess());
        if (CollUtil.isEmpty(kcsjEquipEntryRecordList)) {
            KcsjPlanProcess kcsjPlanProcess = new KcsjPlanProcess();
            kcsjEquipEntryRecordList.add(kcsjPlanProcess);
        }
        String projectCode = SecurityUtils.getTenantKey();
        kcsjEquipEntryRecordList.forEach(p -> {
            p.setPtVar5(projectCode);
        });
        rocketMQTemplate.convertAndSend("kcsj_plan_process:tenantSuccess", kcsjEquipEntryRecordList);
    }

}
