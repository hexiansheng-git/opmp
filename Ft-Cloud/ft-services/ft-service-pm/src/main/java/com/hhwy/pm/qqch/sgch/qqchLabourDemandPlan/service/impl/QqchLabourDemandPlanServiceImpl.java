package com.hhwy.pm.qqch.sgch.qqchLabourDemandPlan.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.common.mapper.CommonMapper;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.sgch.qqchLabourDemandPlan.domain.QqchLabourDemandPlan;
import com.hhwy.pm.qqch.sgch.qqchLabourDemandPlan.domain.vo.QqchLabourDemandPlanDto;
import com.hhwy.pm.qqch.sgch.qqchLabourDemandPlan.domain.vo.QqchLabourDemandPlanVo;
import com.hhwy.pm.qqch.sgch.qqchLabourDemandPlan.mapper.QqchLabourDemandPlanMapper;
import com.hhwy.pm.qqch.sgch.qqchLabourDemandPlan.service.IQqchLabourDemandPlanService;
import com.hhwy.pm.qqch.sgch.qqchconst.domain.QqchConst;
import com.hhwy.pm.qqch.sgch.qqchconst.mapper.QqchConstMapper;
import com.hhwy.pm.qqch.sgch.qqchconst.service.IQqchConstService;
import com.hhwy.pm.qqch.sgch.qqchconst.service.IQqchConstStaffPlanService;
import com.hhwy.pm.qqch.utils.ButtonMarkUtil;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.date.Getclasspath;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.tree.TreeUtil;
import io.seata.common.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author ldd
 * @date 2023-07-31 16:38:26
 * @remark
 */
@Service
public class QqchLabourDemandPlanServiceImpl implements IQqchLabourDemandPlanService{

    @Autowired
    private QqchLabourDemandPlanMapper qqchLabourDemandPlanMapper;
    @Autowired
    private IQqchReviewService qqchReviewService;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;
    @Autowired
    private IQqchConstService qqchConstService;
    @Autowired
    private CommonMapper commonMapper;
    @Autowired
    private QqchConstMapper qqchConstMapper;
    @Autowired
    private IQqchConstStaffPlanService qqchConstStaffPlanService;


    public QqchLabourDemandPlan getQqchLabourDemandPlan(QqchLabourDemandPlan qqchLabourDemandPlan) {
        return qqchLabourDemandPlanMapper.getQqchLabourDemandPlan(qqchLabourDemandPlan);
    }



    @Transactional
    public int insertQqchLabourDemandPlan(QqchLabourDemandPlan qqchLabourDemandPlan) {
        qqchLabourDemandPlan.setId(IdWorker.createId());
        qqchLabourDemandPlan.setCreateUser(SecurityUtils.getUserName());
        qqchLabourDemandPlan.setCreateTime(DateUtils.getNowDate());
        return qqchLabourDemandPlanMapper.insertQqchLabourDemandPlan(qqchLabourDemandPlan);
    }



    @Transactional
    public int updateQqchLabourDemandPlan(QqchLabourDemandPlan qqchLabourDemandPlan) {
        qqchLabourDemandPlan.setUpdateUser(SecurityUtils.getUserName());
        qqchLabourDemandPlan.setUpdateTime(DateUtils.getNowDate());
        return qqchLabourDemandPlanMapper.updateQqchLabourDemandPlan(qqchLabourDemandPlan);
    }

    @Transactional
    public int updateQqchLabourDemandPlanList(List<QqchLabourDemandPlan> qqchLabourDemandPlanList) {
        for (QqchLabourDemandPlan qqchLabourDemandPlan : qqchLabourDemandPlanList) {
            qqchLabourDemandPlan.setUpdateUser(SecurityUtils.getUserName());
            qqchLabourDemandPlan.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchLabourDemandPlanMapper.updateQqchLabourDemandPlanList(qqchLabourDemandPlanList);
    }

    @Transactional
    public int deleteQqchLabourDemandPlan(QqchLabourDemandPlan qqchLabourDemandPlan) {
        qqchLabourDemandPlan.setUpdateUser(SecurityUtils.getUserName());
        qqchLabourDemandPlan.setUpdateTime(DateUtils.getNowDate());
        return qqchLabourDemandPlanMapper.deleteQqchLabourDemandPlan(qqchLabourDemandPlan);
    }

    @Transactional
    public int deleteQqchLabourDemandPlanByPks(List<Long> qqchLabourDemandPlanPkList) {
        return qqchLabourDemandPlanMapper.deleteQqchLabourDemandPlanByPks(qqchLabourDemandPlanPkList);
    }


    /**
     *  列表接口
     * @param qqchLabourDemandPlan
     * @return
     */
    public QqchLabourDemandPlanVo getQqchLabourDemandPlanList(QqchLabourDemandPlan qqchLabourDemandPlan) {
        QqchLabourDemandPlanVo labourDemandPlanVo = new QqchLabourDemandPlanVo();
        BigDecimal version = qqchLabourDemandPlan.getVersion();
        version = VersionUtil.getVersion("qqch_labour_demand_plan", version);
        qqchLabourDemandPlan.setVersion(version);
        List<QqchLabourDemandPlan> qqchLabourDemandPlanList = qqchLabourDemandPlanMapper.getQqchLabourDemandPlanList(qqchLabourDemandPlan);
        //查询开始时间
        QqchLabourDemandPlan qqchLabourDemandPlan1 = qqchLabourDemandPlanMapper.getQqchLabourDemandPlan1(qqchLabourDemandPlan);
        if(qqchLabourDemandPlan1!=null){
            labourDemandPlanVo.setStartTime(new SimpleDateFormat("yyyy-MM").format(qqchLabourDemandPlan1.getEntryDate()));
        }
        QqchLabourDemandPlan qqchLabourDemandPlan2 = qqchLabourDemandPlanMapper.getQqchLabourDemandPlan2(qqchLabourDemandPlan);
        if(qqchLabourDemandPlan2!=null){
            labourDemandPlanVo.setEndTime(new SimpleDateFormat("yyyy-MM").format(qqchLabourDemandPlan2.getExitDate()));
        }
        //查询结束时间
        List<QqchLabourDemandPlan> treeList = TreeUtil.build(qqchLabourDemandPlanList, 0l);
        labourDemandPlanVo.setVersion(version);
        labourDemandPlanVo.setStageIdentity(qqchReviewService.getStage());
        labourDemandPlanVo.setQqchLabourDemandPlanList(treeList);
        return labourDemandPlanVo;
    }

    /**
     *  保存/确认/提交
     * @param qqchLabourDemandPlanVo
     */
    @Override
    @Transactional
    public void save(QqchLabourDemandPlanVo qqchLabourDemandPlanVo) {
        String buttonMark = qqchLabourDemandPlanVo.getButtonMark();
        ButtonMarkUtil.checkButtonMark(buttonMark);

        BigDecimal version = qqchLabourDemandPlanVo.getVersion();
        List<QqchLabourDemandPlan> qqchLabourDemandPlanList = qqchLabourDemandPlanVo.getQqchLabourDemandPlanList();

        this.insertQqchLabourDemandPlanList(qqchLabourDemandPlanList,version);

        //判断是否是确认
        if(ButtonMark.CONFIRM.equals(buttonMark)){
            //插入确认记录
            String menuId = qqchLabourDemandPlanVo.getMenuId();
            String stageIdentity = qqchLabourDemandPlanVo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
    }

    @Override
    public List<QqchLabourDemandPlanDto> selectCount(QqchLabourDemandPlan qqchLabourDemandPlan) {
        List<Date> dates = Getclasspath.getmous(qqchLabourDemandPlan.getStartTime(), qqchLabourDemandPlan.getEndTime());
        List<String> jobNames = qqchLabourDemandPlan.getJobNames();
        //思路：
        // 根据工种名称拿到的id 即为子集的pid
        QqchLabourDemandPlan qqchLabourDemandPlan1 = new QqchLabourDemandPlan();
        qqchLabourDemandPlan1.setJobNames(jobNames);
        List<QqchLabourDemandPlan> qqchLabourDemandPlanList = qqchLabourDemandPlanMapper.getQqchLabourDemandPlanList(qqchLabourDemandPlan1);

        List<QqchLabourDemandPlanDto> list = new ArrayList<>();
        for (QqchLabourDemandPlan labourDemandPlan : qqchLabourDemandPlanList) {
            qqchLabourDemandPlan.setPid(labourDemandPlan.getId());
            List<QqchLabourDemandPlanDto> list1= qqchLabourDemandPlanMapper.selectCount(qqchLabourDemandPlan);
            list.addAll(list1);
        }

        List<QqchLabourDemandPlanDto> list2 = new ArrayList<>();
        for (Date date : dates) {
            QqchLabourDemandPlanDto qqchLabourDemandPlanDto = new QqchLabourDemandPlanDto();
            qqchLabourDemandPlanDto.setTime(date);
            qqchLabourDemandPlanDto.setNum(new BigDecimal(0));
            list2.add(qqchLabourDemandPlanDto);
        }

        for (QqchLabourDemandPlanDto qqchLabourDemandPlanDto : list2) {
            for (QqchLabourDemandPlanDto labourDemandPlanDto : list) {
                String s = new SimpleDateFormat("yyyy-MM").format(qqchLabourDemandPlanDto.getTime());
                String s1 = new SimpleDateFormat("yyyy-MM").format(labourDemandPlanDto.getStartTime());
                String s2 = new SimpleDateFormat("yyyy-MM").format(labourDemandPlanDto.getEndTime());
                if(s.equals(s1)&s.equals(s2)){
                    qqchLabourDemandPlanDto.setNum(qqchLabourDemandPlanDto.getNum().add(labourDemandPlanDto.getNum()));
                }
            }
        }

        return list2;
    }


    @Override
    public List<String> getAllWorkType(QqchLabourDemandPlanVo qqchLabourDemandPlanVo) {
        QqchLabourDemandPlan qqchLabourDemandPlan = new QqchLabourDemandPlan();
        if(qqchLabourDemandPlanVo.getVersion()!=null){
            qqchLabourDemandPlan.setVersion(qqchLabourDemandPlanVo.getVersion());
        }
        qqchLabourDemandPlan.setPid(0l);
        List<QqchLabourDemandPlan> qqchLabourDemandPlanList = qqchLabourDemandPlanMapper.getQqchLabourDemandPlanList(qqchLabourDemandPlan);
        List<String> strings = qqchLabourDemandPlanList.stream().map(QqchLabourDemandPlan::getJobName).collect(Collectors.toList());
        return strings;
    }

    /**
     *  获取施工部署数据
     */
    @Override
    public QqchLabourDemandPlanVo sychData(QqchLabourDemandPlanVo vo) {
        //保存页面数据
        this.insertQqchLabourDemandPlanList(vo.getQqchLabourDemandPlanList(),vo.getVersion());
        //查询最新有效版本的数据
        BigDecimal version = commonMapper.selectMaxVersion("qqch_const");
        List<QqchConst> qqchConstList = qqchConstMapper.selectQqchConst(version);
        Map<String, List<QqchConst>> listMap = qqchConstList.stream().collect(Collectors.groupingBy(QqchConst::getOccupationName));
       //遍历封装好
       List<QqchLabourDemandPlan> arrayList = new ArrayList<>();
        Set<Map.Entry<String, List<QqchConst>>> entrySet = listMap.entrySet();
        for (Map.Entry<String, List<QqchConst>> entry : entrySet) {
            QqchLabourDemandPlan qqchLabourDemandPlan = new QqchLabourDemandPlan();
            qqchLabourDemandPlan.setJobName(entry.getKey());
            List<QqchLabourDemandPlan> list = new ArrayList<>();
            List<QqchConst> entryValue = entry.getValue();
            for (QqchConst qqchConst : entryValue) {
                QqchLabourDemandPlan qqchLabourDemandPlan1 = new QqchLabourDemandPlan();
                qqchLabourDemandPlan1.setOutId(qqchConst.getId());
                qqchLabourDemandPlan1.setWorkTeam(qqchConst.getConstDesc());
                list.add(qqchLabourDemandPlan1);
            }
            qqchLabourDemandPlan.setChildren(list);
            arrayList.add(qqchLabourDemandPlan);
        }
        //查询原来的数据做比对
        QqchLabourDemandPlan qqchLabourDemandPlan1 = new QqchLabourDemandPlan();
        version = VersionUtil.getVersion("qqch_labour_demand_plan", version);
        qqchLabourDemandPlan1.setVersion(version);
        List<QqchLabourDemandPlan> planList = qqchLabourDemandPlanMapper.getQqchLabourDemandPlanList(qqchLabourDemandPlan1);
        Map<Long, QqchLabourDemandPlan> map = planList.stream().collect(Collectors.toMap(QqchLabourDemandPlan::getOutId, Function.identity()));
        //qqchConstList为新数据 --- planList为旧数据
        for (QqchLabourDemandPlan qqchLabourDemandPlan : arrayList) {
            List<QqchLabourDemandPlan> children = qqchLabourDemandPlan.getChildren();
            for (QqchLabourDemandPlan child : children) {
                if(map.get(child.getOutId())!=null){
                    QqchLabourDemandPlan plan = map.get(child.getOutId());
                    child.setId(IdWorker.createId());
                    child.setOutId(child.getId());
                    if(plan.getChinaNum()!=null){
                        child.setChinaNum(plan.getChinaNum());
                    }
                    if(plan.getOutNum()!=null){
                        child.setOutNum(plan.getOutNum());
                    }
                    if(plan.getOutProportion()!=null){
                        child.setOutProportion(plan.getOutProportion());
                    }
                    if(plan.getEntryDate()!=null){
                        child.setEntryDate(plan.getEntryDate());
                    }
                    if(plan.getExitDate()!=null)
                        child.setExitDate(plan.getExitDate());
                    if(plan.getTotal()!=null){
                        child.setTotal(plan.getTotal());
                    }
                    child.setOutId(child.getId());
                    child.setCreateUser(SecurityUtils.getUserName());
                    child.setCreateTime(DateUtils.getNowDate());
                    child.setVersion(version);
                    child.setValid(Valid.YES);
                }
            }
        }
        if(CollectionUtils.isNotEmpty(arrayList)){
            this.insertQqchLabourDemandPlanList(arrayList, version);
        }
        return null;
    }


    public void insertQqchLabourDemandPlanList(List<QqchLabourDemandPlan> qqchLabourDemandPlanList,BigDecimal version) {
        //删除旧数据
        QqchLabourDemandPlan qqchLabourDemandPlan = new QqchLabourDemandPlan();
        qqchLabourDemandPlan.setVersion(version);
        qqchLabourDemandPlanMapper.deleteQqchLabourDemandPlan(qqchLabourDemandPlan);

        if(CollectionUtils.isEmpty(qqchLabourDemandPlanList)){
            return;
        }
        String valid = Valid.NO;
        if(version.compareTo(BigDecimal.ONE) == 0){
            valid = Valid.YES;
        }
        List<QqchLabourDemandPlan> configs = TreeUtil.treeToList(qqchLabourDemandPlanList);
        for (QqchLabourDemandPlan labourDemandPlan : configs) {
            labourDemandPlan.setValid(valid);
            labourDemandPlan.setVersion(version);
            labourDemandPlan.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            labourDemandPlan.setCreateUserName(SecurityUtils.getSysUser().getNickName());
            labourDemandPlan.setCreateTime(DateUtils.getNowDate());
            if(labourDemandPlan.getPid()==null){
                labourDemandPlan.setPid(0l);
            }
        }

        qqchLabourDemandPlanMapper.insertQqchLabourDemandPlanList(configs);
    }
}
