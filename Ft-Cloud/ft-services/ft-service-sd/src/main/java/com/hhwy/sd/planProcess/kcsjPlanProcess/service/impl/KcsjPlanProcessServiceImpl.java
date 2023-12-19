package com.hhwy.sd.planProcess.kcsjPlanProcess.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import cn.hutool.core.lang.tree.Tree;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.feign.service.PmServiceApi;
import com.hhwy.sd.organManage.util.StatisticsUtils;
import com.hhwy.sd.organManage.util.TreeCountUtils;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.tree.TreeUtil;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.sd.planProcess.kcsjPlanProcess.mapper.KcsjPlanProcessMapper;
import com.hhwy.sd.planProcess.kcsjPlanProcess.service.IKcsjPlanProcessService;
import com.hhwy.sd.planProcess.kcsjPlanProcess.domain.KcsjPlanProcess;
import com.hhwy.utils.idworker.IdWorker;

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
        return kcsjPlanProcessMapper.deleteKcsjPlanProcessByPks(collect);
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
    }

    public void deleteAllKcsjPlanProcess() {
        kcsjPlanProcessMapper.deleteAllKcsjPlanProcess();
    }
}
