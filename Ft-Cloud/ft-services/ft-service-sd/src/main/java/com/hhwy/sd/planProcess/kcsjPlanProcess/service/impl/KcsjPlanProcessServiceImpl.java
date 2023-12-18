package com.hhwy.sd.planProcess.kcsjPlanProcess.service.impl;

import java.util.List;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.security.util.SecurityUtils;
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


    public KcsjPlanProcess getKcsjPlanProcess(KcsjPlanProcess kcsjPlanProcess) {
        return kcsjPlanProcessMapper.getKcsjPlanProcess(kcsjPlanProcess);
    }

    public List<KcsjPlanProcess> getKcsjPlanProcessList(KcsjPlanProcess kcsjPlanProcess) {
        return kcsjPlanProcessMapper.getKcsjPlanProcessList(kcsjPlanProcess);
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
        for (KcsjPlanProcess kcsjPlanProcess : kcsjPlanProcessList) {
            kcsjPlanProcess.setUpdateUser(SecurityUtils.getUserName());
            kcsjPlanProcess.setUpdateTime(DateUtils.getNowDate());
        }
        return kcsjPlanProcessMapper.updateKcsjPlanProcessList(kcsjPlanProcessList);
    }

    @Transactional
    public int deleteKcsjPlanProcess(KcsjPlanProcess kcsjPlanProcess) {
        kcsjPlanProcess.setUpdateUser(SecurityUtils.getUserName());
        kcsjPlanProcess.setUpdateTime(DateUtils.getNowDate());
        return kcsjPlanProcessMapper.deleteKcsjPlanProcess(kcsjPlanProcess);
    }

    @Transactional
    public int deleteKcsjPlanProcessByPks(List<Long> kcsjPlanProcessPkList) {
        return kcsjPlanProcessMapper.deleteKcsjPlanProcessByPks(kcsjPlanProcessPkList);
    }
}
