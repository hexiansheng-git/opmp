package com.hhwy.pm.qqch.sgch.qqchconst.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.common.aspect.CompileAspect;
import com.hhwy.pm.qqch.common.aspect.CompileOptEnum;
import com.hhwy.pm.qqch.common.domain.CompileEntity;
import com.hhwy.pm.qqch.sgch.qqchconst.domain.*;
import com.hhwy.pm.qqch.sgch.qqchconst.mapper.QqchConstMapper;
import com.hhwy.pm.qqch.sgch.qqchconst.service.IQqchConstFacilityPlanService;
import com.hhwy.pm.qqch.sgch.qqchconst.service.IQqchConstJobService;
import com.hhwy.pm.qqch.sgch.qqchconst.service.IQqchConstService;
import com.hhwy.pm.qqch.sgch.qqchconst.service.IQqchConstStaffPlanService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractInfo;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractList;
import com.hhwy.pm.xmsl.contractInfo.service.IXmslContractInfoService;
import com.hhwy.pm.xmsl.contractInfo.service.IXmslContractListService;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.tree.ListTreeUtil;
import com.hhwy.utils.tree.TreeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
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

    @Autowired
    private IXmslContractInfoService xmslContractInfoService;

    @Autowired
    private IXmslContractListService xmslContractListService;


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
    public void save(List<QqchConst> qqchConsts, CompileEntity<List<QqchConst>> dtoList) {

        List<QqchConstJob> iJobList = new ArrayList<>();
        List<QqchConstStaffPlan> iStaffList = new ArrayList<>();
        List<QqchConstFacilityPlan> iFacList = new ArrayList<>();


        for (QqchConst cons : qqchConsts) {
            Long id = cons.getId();
            if (cons.getRelevancy() == null) {
                cons.setRelevancy(IdWorker.createId());
            }
            // 工作内容
            List<QqchConstJob> jobList = cons.getJobList();
            // 人员策划
            List<QqchConstStaffPlan> staffList = cons.getStaffList();
            // 设备策划
            List<QqchConstFacilityPlan> facilityPlanList = cons.getFacilityPlanList();

            // 给子表数据赋值
            if (!CollectionUtils.isEmpty(jobList)) {
                List<QqchConstJob> qqchConstJobList = TreeUtil.treeToListWithoutNewId(jobList);
                for (QqchConstJob qqchConstJob : qqchConstJobList) {
                    qqchConstJob = CompileEntity.dealSaveDtoWithoutTree(cons, qqchConstJob);
                    qqchConstJob.setMasterId(id);
                }
                iJobList.addAll(qqchConstJobList);
            }
            if (!CollectionUtils.isEmpty(staffList)) {
                for (QqchConstStaffPlan qqchConstStaffPlan : staffList) {
                    qqchConstStaffPlan = CompileEntity.dealSaveDtoWithoutTree(cons, qqchConstStaffPlan);
                    qqchConstStaffPlan.setMasterId(id);
                    if (qqchConstStaffPlan.getId() == null) qqchConstStaffPlan.setId(IdWorker.createId());
                }
                iStaffList.addAll(staffList);
            }
            if (!CollectionUtils.isEmpty(facilityPlanList)) {
                for (QqchConstFacilityPlan qqchConstFacilityPlan : facilityPlanList) {
                    qqchConstFacilityPlan = CompileEntity.dealSaveDtoWithoutTree(cons, qqchConstFacilityPlan);
                    qqchConstFacilityPlan.setMasterId(id);
                    if (qqchConstFacilityPlan.getId() == null) qqchConstFacilityPlan.setId(IdWorker.createId());
                }
                iFacList.addAll(facilityPlanList);
            }
        }

        this.qqchConstMapper.insertQqchConstList(qqchConsts);

        // 保存
        jobService.saveList(CompileEntity.dealSaveDtoWithoutTree(dtoList, iJobList));
        staffPlanService.saveList(CompileEntity.dealSaveDtoWithoutTree(dtoList, iStaffList));
        facilityPlanService.saveList(CompileEntity.dealSaveDtoWithoutTree(dtoList, iFacList));


    }

    @Override
    @CompileAspect(type = CompileOptEnum.LIST, tableName = TN)
    public CompileEntity<List<QqchConst>> list(QqchConst dto) {
        List<QqchConst> qqchConstList = this.qqchConstMapper.getQqchConstList(dto);
        List<QqchConstJob> jobList = this.jobService.list(CompileEntity.dealListDto(dto.getVersion(), new QqchConstJob()));
        Map<Long, List<QqchConstJob>> jobListMap = jobList.stream().collect(Collectors.groupingBy(QqchConstJob::getMasterId));

        List<QqchConstStaffPlan> staffPlanList = this.staffPlanService.list(CompileEntity.dealListDto(dto.getVersion(), new QqchConstStaffPlan()));
        Map<Long, List<QqchConstStaffPlan>> staffPlanListMap = staffPlanList.stream().collect(Collectors.groupingBy(QqchConstStaffPlan::getMasterId));

        List<QqchConstFacilityPlan> facilityPlanList = this.facilityPlanService.list(CompileEntity.dealListDto(dto.getVersion(), new QqchConstFacilityPlan()));
        Map<Long, List<QqchConstFacilityPlan>> facilityPlanListMap = facilityPlanList.stream().collect(Collectors.groupingBy(QqchConstFacilityPlan::getMasterId));

        for (QqchConst qqchConst : qqchConstList) {
            List<QqchConstJob> jobRes = jobListMap.get(qqchConst.getId());
            List<QqchConstJob> build = TreeUtil.build(jobRes, -1L);
            qqchConst.setJobList(build);
            List<QqchConstStaffPlan> staffLRes = staffPlanListMap.get(qqchConst.getId());
            qqchConst.setStaffList(staffLRes);
            List<QqchConstFacilityPlan> facilityPlansRes = facilityPlanListMap.get(qqchConst.getId());
            qqchConst.setFacilityPlanList(facilityPlansRes);

        }

        CompileEntity<List<QqchConst>> compileEntity = new CompileEntity<List<QqchConst>>();

        List<QqchConst> build = TreeUtil.build(qqchConstList, null);
        compileEntity.setVersion(new BigDecimal("1.0"));
        compileEntity.setDto(build);
        compileEntity.setModuleIdentity("133");

        return compileEntity;
    }

    @Override
    public List<QqchConstStaffPlanResult> selectQqchConst(BigDecimal version) {
        return qqchConstMapper.selectQqchConst(version);
    }

    /**
     * 获取最新版本数据
     *
     * @return
     */
    public List<QqchConst> getMaxVersionValidConstList() {
        BigDecimal version = VersionUtil.getVersion(TN, null);
        QqchConst query = new QqchConst();
        query.setVersion(version);
        //版本全量数据
        List<QqchConst> allList = qqchConstMapper.getQqchConstList(query);
        this.setIncome(allList, version);
        return allList;
    }

    /**
     * 4.2弹窗
     *
     * @return
     */
    @Override
    public List<QqchConst> popUpWindows(QqchConst qqchConst) {
        List<QqchConst> resultList;

        BigDecimal version = VersionUtil.getVersion(TN, null);
        QqchConst query = new QqchConst();
        query.setVersion(version);
        //版本全量数据
        List<QqchConst> allList = qqchConstMapper.getQqchConstList(query);

        String constName = qqchConst.getConstName();
        String constContent = qqchConst.getConstContent();
        if (StringUtils.isNotBlank(constName) || StringUtils.isNotBlank(constContent)) {
            query.setConstName(constName);
            query.setConstContent(constContent);

            List<QqchConst> subList = qqchConstMapper.getQqchConstList(query);

            resultList = ListTreeUtil.getUpListBySublist(subList, allList, QqchConst::getId, QqchConst::getPid);
        } else {
            resultList = allList;
        }

        this.setIncome(resultList, version);
        resultList = ListTreeUtil.formatTree(
                resultList,
                o -> o.getPid() == null,
                (r, n) -> r.getId().equals(n.getPid()),
                QqchConst::getChildren, QqchConst::setChildren);
        return resultList;
    }

    /**
     * 设置分包收入和总产值占比
     *
     * @param constList
     */
    public void setIncome(List<QqchConst> constList, BigDecimal version) {
        /*获取最新生效版本的合同信息*/
        XmslContractInfo contractInfo = xmslContractInfoService.getValidMaxVersionContractInfo();
        BigDecimal contractAmount = BigDecimal.ZERO;
        if (contractInfo != null) {
            //有效合同金额
            contractAmount = contractInfo.getEffectiveAmout();
        }

        /*获取最新生效版本的主合同清单*/
        List<XmslContractList> inventoryList = xmslContractListService.getValidMaxVersionContractInventoryList();
        Map<String, XmslContractList> inventoryMap = inventoryList.stream().collect(Collectors.toMap(XmslContractList::getCode, i -> i, (key1, key2) -> key2));

        /*获取版本工作能容数据*/
        QqchConstJob constJob = new QqchConstJob();
        constJob.setVersion(version);
        List<QqchConstJob> jobList = jobService.getQqchConstJobList(constJob);
        Map<Long, List<QqchConstJob>> jobListMap = jobList.stream().collect(Collectors.groupingBy(QqchConstJob::getMasterId));


        for (QqchConst qqchConst : constList) {
            /*分包收入*/
            BigDecimal subpackageIncome = BigDecimal.ZERO;
            /*总产值占比*/
            BigDecimal totalOutputValueProportion = BigDecimal.ZERO;

            List<QqchConstJob> jobs = jobListMap.get(qqchConst.getId());

            if (!CollectionUtils.isEmpty(jobs)) {
                for (QqchConstJob job : jobs) {
                    BigDecimal checkedNum = job.getCheckedNum();
                    XmslContractList contractList = inventoryMap.get(job.getItemCode());
                    BigDecimal winUnitPrice = BigDecimal.ZERO;
                    if (contractList != null) {
                        winUnitPrice = contractList.getWinUnitPrice();
                    }

                    if (checkedNum != null && winUnitPrice != null) {
                        subpackageIncome = subpackageIncome.add(checkedNum.multiply(winUnitPrice));
                    }
                }
            }

            if (contractAmount != null && contractAmount.compareTo(BigDecimal.ZERO) != 0) {
                totalOutputValueProportion = subpackageIncome.divide(contractAmount, 2, RoundingMode.HALF_UP);
            }

            qqchConst.setSubpackageIncome(subpackageIncome);
            qqchConst.setTotalOutputValueProportion(totalOutputValueProportion);
        }
    }
}
