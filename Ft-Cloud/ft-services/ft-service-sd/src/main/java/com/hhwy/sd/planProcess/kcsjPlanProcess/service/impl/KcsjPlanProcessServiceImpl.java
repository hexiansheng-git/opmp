package com.hhwy.sd.planProcess.kcsjPlanProcess.service.impl;

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
import com.hhwy.sd.planProcess.kcsjPlanProcess.domain.KcsjWarnRecord;
import com.hhwy.sd.planProcess.kcsjPlanProcess.mapper.KcsjPlanProcessMapper;
import com.hhwy.sd.planProcess.kcsjPlanProcess.service.IKcsjPlanProcessService;
import com.hhwy.system.api.RemoteNoticeService;
import com.hhwy.system.api.domain.SysTenant;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.date.FtDateUtils;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.tree.TreeUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
    @Autowired
    private RemoteNoticeService remoteNoticeService;


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
            return 0;
        }
        int i = kcsjPlanProcessMapper.deleteKcsjPlanProcessByPks(collect);
        doSendGm();
        return i;
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
        List<KcsjWarnRecord> warnList=new ArrayList<>();
        try {
            for (SysTenant tenant : tenantList) {
                //切换租户
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
                    List<TWarn> sysWarnList=new ArrayList<>();
                    TWarn warn=new TWarn();
                    warn.setCreateTime(DateUtils.getNowDate());
                    warn.setTenantKey(tenantKey);
                    warn.setProjectName(tenant.getTenantName());
                    warn.setWarnItem(WarnItem.KCSJ_PLAN_PROCESS.getWarnItem());
                    warn.setWarnItemId(WarnItem.KCSJ_PLAN_PROCESS.getWarnItemId());
                    Date startDate = info.getPlanStartDate();
                    if(null==startDate){
                        continue;
                    }
                    //超期每2天提醒一次   2  4   6  请注意每2天  2天后
                    long twoDays = FtDateUtils.getDiffDays(DateUtils.getNowDate(),startDate);
                    long ltr=twoDays%2;
                    if(ltr==0) {

                        sysWarnList.add(warn);
                    }
                    //提前7天提醒   7天前的数据
                    long diffDays = FtDateUtils.getDiffDays(startDate, DateUtils.getNowDate());
                    if(diffDays==7){

                        sysWarnList.add(warn);
                    }
                    //2个条件满足一个就行  因为预警只预警一次  一个项目上
                    if(CollectionUtils.isNotEmpty(sysWarnList)){
                        systemServiceApi.insertTWarnList(sysWarnList);
                        logger.info("一个项目只发一次，发完就撤");
                        KcsjWarnRecord record=new KcsjWarnRecord();
                        record.setCreateTime(DateUtils.getNowDate());
                        record.setProjectCode(tenant.getTenantKey());
                        record.setProjectName(tenant.getTenantName());
                        record.setWarnTime(DateUtils.getNowDate());
                        record.setWarnSubject(WarnItem.KCSJ_PLAN_PROCESS.getWarnItem());
//                        record.setWarnContent();
//                        record.setWarnUser();
//                        record.setWarnUserId();
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
    private void syncToGm(List<KcsjWarnRecord> warnList) {
        try{
            rocketMQTemplate.convertAndSend("kcsj_plan_process_warn:tenantSuccess", JSONObject.toJSONString(warnList));
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
        ProjectDto projectDto = pmServiceApi.getProjectDto();
        String projectCode = projectDto.getProjectCode();
        KcsjPlanProcess planProcess = new KcsjPlanProcess();
        List<KcsjPlanProcess> kcsjEquipEntryRecordList = kcsjPlanProcessMapper.getKcsjPlanProcessList(planProcess);
        kcsjEquipEntryRecordList.forEach(p -> p.setPtVar5(projectCode));
        rocketMQTemplate.convertAndSend("kcsj_plan_process:tenantSuccess", kcsjEquipEntryRecordList);
    }
}
