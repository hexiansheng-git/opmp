package com.hhwy.pm.qqch.sgch.qqchconst.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.common.aspect.CompileAspect;
import com.hhwy.pm.qqch.common.aspect.CompileOptEnum;
import com.hhwy.pm.qqch.common.domain.CompileDTO;
import com.hhwy.pm.qqch.sgch.qqchconst.domain.QqchConst;
import com.hhwy.pm.qqch.sgch.qqchconst.domain.QqchConstFacilityPlan;
import com.hhwy.pm.qqch.sgch.qqchconst.domain.QqchConstJob;
import com.hhwy.pm.qqch.sgch.qqchconst.domain.QqchConstStaffPlan;
import com.hhwy.pm.qqch.sgch.qqchconst.mapper.QqchConstMapper;
import com.hhwy.pm.qqch.sgch.qqchconst.service.IQqchConstFacilityPlanService;
import com.hhwy.pm.qqch.sgch.qqchconst.service.IQqchConstJobService;
import com.hhwy.pm.qqch.sgch.qqchconst.service.IQqchConstService;
import com.hhwy.pm.qqch.sgch.qqchconst.service.IQqchConstStaffPlanService;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.tree.TreeUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author mls
 * @date 2023-08-03 16:08:05
 * @remark
 */
@Service
public class QqchConstServiceImpl implements IQqchConstService {

    @Resource
    private QqchConstMapper qqchConstMapper;

    @Resource
    private IQqchConstJobService jobService;

    @Resource
    private IQqchConstStaffPlanService staffPlanService;

    @Resource
    private IQqchConstFacilityPlanService facilityPlanService;


    private static final String TN = "qqch_const";


    public QqchConst getQqchConst(QqchConst qqchConst) {
        return qqchConstMapper.getQqchConst(qqchConst);
    }

    public List<QqchConst> getQqchConstList(QqchConst qqchConst) {
        return qqchConstMapper.getQqchConstList(qqchConst);
    }

    @Transactional(rollbackFor = Exception.class)
    public int insertQqchConst(QqchConst qqchConst) {
        qqchConst.setId(IdWorker.createId());
        qqchConst.setCreateUser(SecurityUtils.getUserName());
        qqchConst.setCreateTime(DateUtils.getNowDate());
        return qqchConstMapper.insertQqchConst(qqchConst);
    }

    @Transactional(rollbackFor = Exception.class)
    public int insertQqchConstList(List<QqchConst> qqchConstList) {
        for (QqchConst qqchConst : qqchConstList) {
            qqchConst.setId(IdWorker.createId());
            qqchConst.setCreateUser(SecurityUtils.getUserName());
            qqchConst.setCreateTime(DateUtils.getNowDate());
        }
        return qqchConstMapper.insertQqchConstList(qqchConstList);
    }

    @Transactional(rollbackFor = Exception.class)
    public int updateQqchConst(QqchConst qqchConst) {
        qqchConst.setUpdateUser(SecurityUtils.getUserName());
        qqchConst.setUpdateTime(DateUtils.getNowDate());
        return qqchConstMapper.updateQqchConst(qqchConst);
    }

    @Transactional(rollbackFor = Exception.class)
    public int updateQqchConstList(List<QqchConst> qqchConstList) {
        for (QqchConst qqchConst : qqchConstList) {
            qqchConst.setUpdateUser(SecurityUtils.getUserName());
            qqchConst.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchConstMapper.updateQqchConstList(qqchConstList);
    }

    @Transactional(rollbackFor = Exception.class)
    public int deleteQqchConst(QqchConst qqchConst) {
        qqchConst.setUpdateUser(SecurityUtils.getUserName());
        qqchConst.setUpdateTime(DateUtils.getNowDate());
        return qqchConstMapper.deleteQqchConst(qqchConst);
    }

    @Transactional(rollbackFor = Exception.class)
    public int deleteQqchConstByPks(List<Long> qqchConstPkList) {
        return qqchConstMapper.deleteQqchConstByPks(qqchConstPkList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CompileAspect(type = CompileOptEnum.SAVE_LIST, tableName = TN)
    public void save(List<QqchConst> qqchConsts) {

        List<QqchConstJob> iJobList = new ArrayList<>();
        List<QqchConstStaffPlan> iStaffList = new ArrayList<>();
        List<QqchConstFacilityPlan> iFacList = new ArrayList<>();


        for (QqchConst cons : qqchConsts) {
            Long id = cons.getId();
            // 工作内容
            List<QqchConstJob> jobList = cons.getJobList();
            // 人员策划
            List<QqchConstStaffPlan> staffList = cons.getStaffList();
            // 设备策划
            List<QqchConstFacilityPlan> facilityPlanList = cons.getFacilityPlanList();

            // 给子表数据赋值
            if (!CollectionUtils.isEmpty(jobList)) {
                List<QqchConstJob> qqchConstJobList = TreeUtil.treeToList(jobList);
                for (QqchConstJob qqchConstJob : qqchConstJobList) {
                    qqchConstJob = CompileDTO.dealSaveDto(cons.getVersion(), cons.getSubmitFlag(), qqchConstJob);
                    qqchConstJob.setMasterId(id);
                }
                iJobList.addAll(qqchConstJobList);
            }
            if (!CollectionUtils.isEmpty(staffList)) {
                for (QqchConstStaffPlan qqchConstStaffPlan : staffList) {
                    qqchConstStaffPlan = CompileDTO.dealSaveDto(cons.getVersion(), cons.getSubmitFlag(), qqchConstStaffPlan);
                    qqchConstStaffPlan.setMasterId(id);
                    qqchConstStaffPlan.setId(IdWorker.createId());
                }
                iStaffList.addAll(staffList);
            }
            if (!CollectionUtils.isEmpty(facilityPlanList)) {
                for (QqchConstFacilityPlan qqchConstFacilityPlan : facilityPlanList) {
                    qqchConstFacilityPlan = CompileDTO.dealSaveDto(cons.getVersion(), cons.getSubmitFlag(), qqchConstFacilityPlan);
                    qqchConstFacilityPlan.setMasterId(id);
                    qqchConstFacilityPlan.setId(IdWorker.createId());
                }
                iFacList.addAll(facilityPlanList);
            }
        }

        this.qqchConstMapper.insertQqchConstList(qqchConsts);

        // 保存
        jobService.saveList(iJobList);
        staffPlanService.saveList(iStaffList);
        facilityPlanService.saveList(iFacList);


    }

    @Override
    @CompileAspect(type = CompileOptEnum.LIST, tableName = TN)
    public CompileDTO<List<QqchConst>> list(QqchConst dto) {
        List<QqchConst> qqchConstList = this.qqchConstMapper.getQqchConstList(dto);
        List<QqchConstJob> jobList = this.jobService.list(CompileDTO.dealListDto(dto.getVersion(), new QqchConstJob()));
        Map<Long, List<QqchConstJob>> jobListMap = jobList.stream().collect(Collectors.groupingBy(QqchConstJob::getMasterId));

        List<QqchConstStaffPlan> staffPlanList = this.staffPlanService.list(CompileDTO.dealListDto(dto.getVersion(), new QqchConstStaffPlan()));
        Map<Long, List<QqchConstStaffPlan>> staffPlanListMap = staffPlanList.stream().collect(Collectors.groupingBy(QqchConstStaffPlan::getMasterId));

        List<QqchConstFacilityPlan> facilityPlanList = this.facilityPlanService.list(CompileDTO.dealListDto(dto.getVersion(), new QqchConstFacilityPlan()));
        Map<Long, List<QqchConstFacilityPlan>> facilityPlanListMap = facilityPlanList.stream().collect(Collectors.groupingBy(QqchConstFacilityPlan::getMasterId));

        for (QqchConst qqchConst : qqchConstList) {
            List<QqchConstJob> jobRes = jobListMap.get(qqchConst.getId());
            List<QqchConstJob> build = TreeUtil.build(jobRes, null);
            qqchConst.setJobList(build);
            List<QqchConstStaffPlan> staffLRes = staffPlanListMap.get(qqchConst.getId());
            qqchConst.setStaffList(staffLRes);
            List<QqchConstFacilityPlan> facilityPlansRes = facilityPlanListMap.get(qqchConst.getId());
            qqchConst.setFacilityPlanList(facilityPlansRes);

        }

        CompileDTO<List<QqchConst>> compileDTO = new CompileDTO<List<QqchConst>>();

        List<QqchConst> build = TreeUtil.build(qqchConstList, null);
        compileDTO.setVersion(new BigDecimal("1.0"));
        compileDTO.setDto(build);
        compileDTO.setStageIdentity("1");
        compileDTO.setModuleIdentity("133");
        
        return compileDTO;
    }
}
