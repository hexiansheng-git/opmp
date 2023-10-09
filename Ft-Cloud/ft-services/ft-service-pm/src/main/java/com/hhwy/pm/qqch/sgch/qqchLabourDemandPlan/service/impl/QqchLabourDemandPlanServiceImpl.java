package com.hhwy.pm.qqch.sgch.qqchLabourDemandPlan.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.common.mapper.CommonMapper;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.sgch.qqchLabourDemandPlan.domain.QqchLabourDemandPlan;
import com.hhwy.pm.qqch.sgch.qqchLabourDemandPlan.domain.vo.QqchLabourDemandPlanDto;
import com.hhwy.pm.qqch.sgch.qqchLabourDemandPlan.domain.vo.QqchLabourDemandPlanResult;
import com.hhwy.pm.qqch.sgch.qqchLabourDemandPlan.domain.vo.QqchLabourDemandPlanVo;
import com.hhwy.pm.qqch.sgch.qqchLabourDemandPlan.mapper.QqchLabourDemandPlanMapper;
import com.hhwy.pm.qqch.sgch.qqchLabourDemandPlan.service.IQqchLabourDemandPlanService;
import com.hhwy.pm.qqch.sgch.qqchconst.domain.QqchConst;
import com.hhwy.pm.qqch.sgch.qqchconst.domain.QqchConstStaffPlanResult;
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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;

/**
 * @author ldd
 * @date 2023-07-31 16:38:26
 * @remark
 */
@Service
public class QqchLabourDemandPlanServiceImpl implements IQqchLabourDemandPlanService {

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
//        qqchLabourDemandPlan.setUpdateUser(SecurityUtils.getUserName());
//        qqchLabourDemandPlan.setUpdateTime(DateUtils.getNowDate());
        return qqchLabourDemandPlanMapper.deleteQqchLabourDemandPlan(qqchLabourDemandPlan);
    }

    @Transactional
    public int deleteQqchLabourDemandPlanByPks(List<Long> qqchLabourDemandPlanPkList) {
        return qqchLabourDemandPlanMapper.deleteQqchLabourDemandPlanByPks(qqchLabourDemandPlanPkList);
    }

    @Override
    public Map<String, Integer> personNumCalc(QqchLabourDemandPlan qqchLabourDemandPlanParam) {
        return qqchLabourDemandPlanMapper.personNumCalc(qqchLabourDemandPlanParam);
    }

    /**
     * 列表接口
     * @param qqchLabourDemandPlan
     * @return
     */
    public QqchLabourDemandPlanVo getQqchLabourDemandPlanList(QqchLabourDemandPlan qqchLabourDemandPlan) {
        QqchLabourDemandPlanVo labourDemandPlanVo = new QqchLabourDemandPlanVo();
        BigDecimal version = qqchLabourDemandPlan.getVersion();
        version = VersionUtil.getVersion("qqch_labour_demand_plan", version);
        qqchLabourDemandPlan.setVersion(version);
        List<QqchLabourDemandPlan> qqchLabourDemandPlanList = qqchLabourDemandPlanMapper.getQqchLabourDemandPlanList(qqchLabourDemandPlan);
        if (CollectionUtils.isNotEmpty(qqchLabourDemandPlanList)) {
            //查询开始时间
            QqchLabourDemandPlan qqchLabourDemandPlan1 = qqchLabourDemandPlanMapper.getQqchLabourDemandPlan1(qqchLabourDemandPlan);
            if (qqchLabourDemandPlan1 != null && qqchLabourDemandPlan1.getEntryDate() != null) {
                labourDemandPlanVo.setStartTime(new SimpleDateFormat("yyyy-MM").format(qqchLabourDemandPlan1.getEntryDate()));
            }
            //查询结束时间
            QqchLabourDemandPlan qqchLabourDemandPlan2 = qqchLabourDemandPlanMapper.getQqchLabourDemandPlan2(qqchLabourDemandPlan);
            if (qqchLabourDemandPlan2 != null && qqchLabourDemandPlan2.getExitDate() != null) {
                labourDemandPlanVo.setEndTime(new SimpleDateFormat("yyyy-MM").format(qqchLabourDemandPlan2.getExitDate()));
            }
            qqchLabourDemandPlanList.stream().filter(p -> p.getPid()!=null && p.getPid() != 0).forEach(f -> f.setJobName(""));
        }
        List<QqchLabourDemandPlan> treeList = TreeUtil.build(qqchLabourDemandPlanList, 0l);
        labourDemandPlanVo.setVersion(version);
        labourDemandPlanVo.setStageIdentity(qqchReviewService.getStage());
        labourDemandPlanVo.setQqchLabourDemandPlanList(treeList);
        return labourDemandPlanVo;
    }

    /**
     * 保存/确认/提交
     * @param qqchLabourDemandPlanVo
     */
    @Override
    @Transactional
    public void save(QqchLabourDemandPlanVo qqchLabourDemandPlanVo) {
        String buttonMark = qqchLabourDemandPlanVo.getButtonMark();
        ButtonMarkUtil.checkButtonMark(buttonMark);

        BigDecimal version = qqchLabourDemandPlanVo.getVersion();
        List<QqchLabourDemandPlan> qqchLabourDemandPlanList = qqchLabourDemandPlanVo.getQqchLabourDemandPlanList();

        this.insertQqchLabourDemandPlanList(qqchLabourDemandPlanList, version);

        //判断是否是确认
        if (ButtonMark.CONFIRM.equals(buttonMark)) {
            //插入确认记录
            String menuId = qqchLabourDemandPlanVo.getMenuId();
            String stageIdentity = qqchLabourDemandPlanVo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId, stageIdentity);
        }
    }

    @Override
    public List<QqchLabourDemandPlanDto> selectCount(QqchLabourDemandPlan qqchLabourDemandPlan) {
        List<Date> dates = Getclasspath.getmous(qqchLabourDemandPlan.getStartTime(), qqchLabourDemandPlan.getEndTime());
        List<String> jobNames = qqchLabourDemandPlan.getJobNames();
        //思路：
        // 根据工种名称拿到的id 即为子集的pid
        QqchLabourDemandPlan qqchLabourDemandPlan1 = new QqchLabourDemandPlan();
        if (!jobNames.contains("全部工种")){
            qqchLabourDemandPlan1.setJobNames(jobNames);
        }
        List<QqchLabourDemandPlan> qqchLabourDemandPlanList = qqchLabourDemandPlanMapper.getQqchLabourDemandPlanList(qqchLabourDemandPlan1);

        List<QqchLabourDemandPlanDto> list = new ArrayList<>();
        for (QqchLabourDemandPlan labourDemandPlan : qqchLabourDemandPlanList) {
            qqchLabourDemandPlan.setPid(labourDemandPlan.getId());
            List<QqchLabourDemandPlanDto> list1 = qqchLabourDemandPlanMapper.selectCount(qqchLabourDemandPlan);
            list.addAll(list1);
        }

        List<QqchLabourDemandPlanDto> list2 = new ArrayList<>();
        for (Date date : dates) {
            QqchLabourDemandPlanDto qqchLabourDemandPlanDto = new QqchLabourDemandPlanDto();
            qqchLabourDemandPlanDto.setTime(date);
            qqchLabourDemandPlanDto.setStartTime(DateUtil.beginOfMonth(date));
            qqchLabourDemandPlanDto.setEndTime(DateUtil.endOfMonth(date));
            qqchLabourDemandPlanDto.setNum(new BigDecimal(0));
            list2.add(qqchLabourDemandPlanDto);
        }

        for (QqchLabourDemandPlanDto qqchLabourDemandPlanDto : list2) {
            Date startTime = qqchLabourDemandPlanDto.getStartTime();
            Date endTime = qqchLabourDemandPlanDto.getEndTime();
            for (QqchLabourDemandPlanDto labourDemandPlanDto : list) {
                Date startTime1 = labourDemandPlanDto.getStartTime();
                Date endTime1 = labourDemandPlanDto.getEndTime();
                boolean in = DateUtil.isIn(startTime1, startTime, endTime);
                boolean in1 = DateUtil.isIn(endTime1, startTime, endTime);
                if (in || in1) {
                    qqchLabourDemandPlanDto.setNum(qqchLabourDemandPlanDto.getNum().add(labourDemandPlanDto.getNum()));
                }
            }
        }

        return list2;
    }


    @Override
    public List<String> getAllWorkType(QqchLabourDemandPlanVo qqchLabourDemandPlanVo) {
        QqchLabourDemandPlan qqchLabourDemandPlan = new QqchLabourDemandPlan();
        if (qqchLabourDemandPlanVo.getVersion() != null) {
            qqchLabourDemandPlan.setVersion(qqchLabourDemandPlanVo.getVersion());
        }
        qqchLabourDemandPlan.setPid(0l);
        List<QqchLabourDemandPlan> qqchLabourDemandPlanList = qqchLabourDemandPlanMapper.getQqchLabourDemandPlanList(qqchLabourDemandPlan);
        List<String> strings = qqchLabourDemandPlanList.stream().map(QqchLabourDemandPlan::getJobName).collect(Collectors.toList());
        return strings;
    }

    /**
     * 获取施工部署数据
     */
    @Override
    public QqchLabourDemandPlanVo sychData(QqchLabourDemandPlanVo vo) {
        //1.保存页面数据
        this.insertQqchLabourDemandPlanList(vo.getQqchLabourDemandPlanList(), vo.getVersion());
        //查询最新有效版本的施工数据
        BigDecimal constVersion = commonMapper.selectMaxVersion("qqch_const");
        //2.查询1.3人员策划数据
        List<QqchConstStaffPlanResult> qqchConstList = qqchConstMapper.selectQqchConst(constVersion);
        if (CollectionUtils.isEmpty(qqchConstList)) {
            //1.3人员策划为空，则清空1.5.2数据
            //1.3人员策划为空，则不做任何操作 20230927
//            QqchLabourDemandPlan param = new QqchLabourDemandPlan();
//            param.setVersion(constVersion);
//            this.deleteQqchLabourDemandPlan(param);
            QqchLabourDemandPlan qqchLabourDemandPlan = new QqchLabourDemandPlan();
            qqchLabourDemandPlan.setVersion(vo.getVersion());
            return this.getQqchLabourDemandPlanList(qqchLabourDemandPlan);
        }

        //查询1.5.2原有数据
        QqchLabourDemandPlan param = new QqchLabourDemandPlan();
        BigDecimal labourVersion = VersionUtil.getVersion("qqch_labour_demand_plan", constVersion);
        if (labourVersion == null) {
            labourVersion = constVersion;
        }
        param.setVersion(labourVersion);
        List<QqchLabourDemandPlan> orginList = qqchLabourDemandPlanMapper.getQqchLabourDemandPlanList(param);
        if (CollectionUtils.isEmpty(orginList)){
            //如果1.5.2数据为空，则直接入库新数据
            List<QqchLabourDemandPlanResult> qqchLabourDemandPlanResults = BeanUtil.copyToList(qqchConstList, QqchLabourDemandPlanResult.class);
            List<QqchLabourDemandPlan> arrayList = this.toTreeList(qqchLabourDemandPlanResults);
            this.insertQqchLabourDemandPlanList(arrayList, labourVersion);
            QqchLabourDemandPlan qqchLabourDemandPlan = new QqchLabourDemandPlan();
            qqchLabourDemandPlan.setVersion(vo.getVersion());
            return this.getQqchLabourDemandPlanList(qqchLabourDemandPlan);
        }

        List<QqchLabourDemandPlanResult> saveList = new ArrayList<>();
        List<Long> delList = new ArrayList<>();

        //遍历1.5.2数据，判断在1.3中是否存在，存在则修改，不存在说明1.3已删除1.5.2也同步删除
        //1.3数据 按id分组
        Map<Long, List<QqchConstStaffPlanResult>> mapId13 = qqchConstList.stream()
                    .collect(Collectors.groupingBy(QqchConstStaffPlanResult::getId));
        for (QqchLabourDemandPlan qqchLabourDemandPlan : orginList) {
            Long outId = qqchLabourDemandPlan.getOutId();
            if (mapId13.containsKey(outId)){
                //已存在的数据，判断入场和离场时间是否已填写，未填下走添加逻辑，已填写需要把数据保留
                List<QqchLabourDemandPlanResult> qqchLabourDemandPlanResults = BeanUtil.copyToList(mapId13.get(outId), QqchLabourDemandPlanResult.class);
                if (qqchLabourDemandPlan.getEntryDate() != null || qqchLabourDemandPlan.getExitDate() != null){
                    qqchLabourDemandPlanResults.forEach(p -> {
                        p.setEntryDate(qqchLabourDemandPlan.getEntryDate());
                        p.setExitDate(qqchLabourDemandPlan.getExitDate());
                    });
                }
                saveList.addAll(qqchLabourDemandPlanResults);
            }else {
                //删除
                delList.add(qqchLabourDemandPlan.getId());
            }
        }

        //遍历1.3数据，添加1.5.2中不包含的数据
        //1.5.2数据 按out id分组
        Map<Long, QqchLabourDemandPlan> map152 = orginList.stream()
                .filter(p -> p.getPid()!=0)
                .collect(Collectors.toMap(QqchLabourDemandPlan::getOutId, Function.identity()));
        List<QqchLabourDemandPlanResult> qqchLabourDemandPlanResults = BeanUtil.copyToList(qqchConstList, QqchLabourDemandPlanResult.class);
        for (QqchLabourDemandPlanResult result : qqchLabourDemandPlanResults) {
            Long id = result.getId();
            if (map152.containsKey(id)){
                continue;
            }
            saveList.add(result);
        }
        if (CollectionUtils.isNotEmpty(delList)) {
            qqchLabourDemandPlanMapper.deleteQqchLabourDemandPlanByPks(delList);
        }

        if (CollectionUtils.isNotEmpty(saveList)) {
            //列表结构转换
            List<QqchLabourDemandPlan> arrayList = this.toTreeList(saveList);
            this.insertQqchLabourDemandPlanList(arrayList, labourVersion);
        }
        QqchLabourDemandPlan qqchLabourDemandPlan = new QqchLabourDemandPlan();
        qqchLabourDemandPlan.setVersion(vo.getVersion());
        return this.getQqchLabourDemandPlanList(qqchLabourDemandPlan);
    }


    @Transactional(propagation = REQUIRED)
    public void insertQqchLabourDemandPlanList(List<QqchLabourDemandPlan> qqchLabourDemandPlanList, BigDecimal version) {
        if (CollectionUtils.isEmpty(qqchLabourDemandPlanList)) {
            return;
        }
        //删除旧数据
        QqchLabourDemandPlan qqchLabourDemandPlan = new QqchLabourDemandPlan();
        qqchLabourDemandPlan.setVersion(version);
        qqchLabourDemandPlanMapper.deleteQqchLabourDemandPlan(qqchLabourDemandPlan);

        String valid = Valid.NO;
        if (version.compareTo(BigDecimal.ONE) == 0) {
            valid = Valid.YES;
        }
        List<QqchLabourDemandPlan> configs = TreeUtil.treeToList(qqchLabourDemandPlanList);
        for (QqchLabourDemandPlan labourDemandPlan : configs) {
            labourDemandPlan.setValid(valid);
            labourDemandPlan.setVersion(version);
            labourDemandPlan.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            labourDemandPlan.setCreateUserName(SecurityUtils.getSysUser().getNickName());
            labourDemandPlan.setCreateTime(DateUtils.getNowDate());
            if (labourDemandPlan.getPid() == null) {
                labourDemandPlan.setPid(0l);
            }
        }
        qqchLabourDemandPlanMapper.insertQqchLabourDemandPlanList(configs);
    }

    private List<QqchLabourDemandPlan> toTreeList(List<QqchLabourDemandPlanResult> qqchConstList){
        //按工种名称分组
        Map<String, List<QqchLabourDemandPlanResult>> listMap = qqchConstList.stream().collect(Collectors.groupingBy(QqchConstStaffPlanResult::getOccupationName));
        //遍历封装好
        List<QqchLabourDemandPlan> arrayList = new ArrayList<>();
        Set<Map.Entry<String, List<QqchLabourDemandPlanResult>>> entrySet = listMap.entrySet();
        //按工种名称遍历
        for (Map.Entry<String, List<QqchLabourDemandPlanResult>> entry : entrySet) {
            QqchLabourDemandPlan qqchLabourDemandPlan = new QqchLabourDemandPlan();
            qqchLabourDemandPlan.setJobName(entry.getKey());
            List<QqchLabourDemandPlan> list = new ArrayList<>();
            List<QqchLabourDemandPlanResult> entryValue = entry.getValue();
            for (QqchLabourDemandPlanResult result : entryValue) {
                QqchLabourDemandPlan qqchLabourDemandPlan1 = new QqchLabourDemandPlan();
                qqchLabourDemandPlan1.setOutId(result.getId());
                qqchLabourDemandPlan1.setOccupationCode(StringUtils.isEmpty(result.getOccupationCode())?null:result.getOccupationCode());
                qqchLabourDemandPlan1.setJobName(StringUtils.isEmpty(result.getOccupationName())?null:result.getOccupationName());
                qqchLabourDemandPlan1.setWorkTeam(StringUtils.isEmpty(result.getConstDesc())?null:result.getConstDesc());
                qqchLabourDemandPlan1.setWorkContent(StringUtils.isEmpty(result.getConstContent())?null:result.getConstContent());
                qqchLabourDemandPlan1.setSiteDays(result.getSiteDays());
                Integer chinaNum = result.getChineseSideCount()==null?0:result.getChineseSideCount();
                qqchLabourDemandPlan1.setChinaNum(BigDecimal.valueOf(chinaNum));
                Integer outNum = result.getLocalCount()==null?0:result.getLocalCount();
                qqchLabourDemandPlan1.setOutNum(BigDecimal.valueOf(outNum));
                Integer total = result.getTotalCount()==null?0:result.getTotalCount();
                qqchLabourDemandPlan1.setTotal(BigDecimal.valueOf(total));
                if (total > 0){
                    BigDecimal rate = BigDecimal.valueOf(result.getLocalCount() * 100 / total);
                    qqchLabourDemandPlan1.setOutProportion(rate);
                }
                qqchLabourDemandPlan1.setEntryDate(result.getEntryDate());
                qqchLabourDemandPlan1.setExitDate(result.getExitDate());
                list.add(qqchLabourDemandPlan1);
            }
            qqchLabourDemandPlan.setChildren(list);
            qqchLabourDemandPlan.setChinaNum(list.stream().map(p -> p.getChinaNum()).reduce(BigDecimal.ZERO, BigDecimal::add));
            qqchLabourDemandPlan.setOutNum(list.stream().map(p -> p.getOutNum()).reduce(BigDecimal.ZERO, BigDecimal::add));
            qqchLabourDemandPlan.setTotal(list.stream().map(p -> p.getTotal()).reduce(BigDecimal.ZERO, BigDecimal::add));
            if (qqchLabourDemandPlan.getTotal().compareTo(BigDecimal.ZERO) > 0) {
                qqchLabourDemandPlan.setOutProportion(qqchLabourDemandPlan.getOutNum().multiply(new BigDecimal("100")).divide(qqchLabourDemandPlan.getTotal(), 2, RoundingMode.UP));
            }
            arrayList.add(qqchLabourDemandPlan);
        }
        return arrayList;
    }
}
