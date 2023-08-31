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
        qqchLabourDemandPlan.setUpdateUser(SecurityUtils.getUserName());
        qqchLabourDemandPlan.setUpdateTime(DateUtils.getNowDate());
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
        qqchLabourDemandPlan1.setJobNames(jobNames);
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
            qqchLabourDemandPlanDto.setNum(new BigDecimal(0));
            list2.add(qqchLabourDemandPlanDto);
        }

        for (QqchLabourDemandPlanDto qqchLabourDemandPlanDto : list2) {
            for (QqchLabourDemandPlanDto labourDemandPlanDto : list) {
                String s = new SimpleDateFormat("yyyy-MM").format(qqchLabourDemandPlanDto.getTime());
                String s1 = new SimpleDateFormat("yyyy-MM").format(labourDemandPlanDto.getStartTime());
                String s2 = new SimpleDateFormat("yyyy-MM").format(labourDemandPlanDto.getEndTime());
                if (s.equals(s1) & s.equals(s2)) {
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
            QqchLabourDemandPlan param = new QqchLabourDemandPlan();
            param.setVersion(constVersion);
            this.deleteQqchLabourDemandPlan(param);
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
            List<QqchLabourDemandPlan> arrayList = this.toTreeList(qqchConstList);
            this.insertQqchLabourDemandPlanList(arrayList, labourVersion);
            QqchLabourDemandPlan qqchLabourDemandPlan = new QqchLabourDemandPlan();
            qqchLabourDemandPlan.setVersion(vo.getVersion());
            return this.getQqchLabourDemandPlanList(qqchLabourDemandPlan);
        }

        List<QqchLabourDemandPlan> saveList = new ArrayList<>();
        List<Long> delList = new ArrayList<>();

        //遍历1.5.2数据，删除1.3中不包含的数据，修改包含的数据
        //1.3数据 按id分组
        Map<Long, List<QqchConstStaffPlanResult>> mapId13 = qqchConstList.stream()
                    .collect(Collectors.groupingBy(QqchConstStaffPlanResult::getId));
        for (QqchLabourDemandPlan qqchLabourDemandPlan : orginList) {
            Long outId = qqchLabourDemandPlan.getOutId();
            if (mapId13.containsKey(outId)){
                //修改
                List<QqchConstStaffPlanResult> qqchConstStaffPlanResults = mapId13.get(outId);
                for (QqchConstStaffPlanResult result : qqchConstStaffPlanResults) {
                    QqchLabourDemandPlan qqchLabourDemandPlan1 = new QqchLabourDemandPlan();
                    qqchLabourDemandPlan1.setOutId(result.getId());
                    qqchLabourDemandPlan1.setOccupationCode(result.getOccupationCode());
                    qqchLabourDemandPlan1.setJobName(result.getOccupationName());
                    qqchLabourDemandPlan1.setWorkTeam(result.getConstDesc());
                    qqchLabourDemandPlan1.setChinaNum(BigDecimal.valueOf(result.getChineseSideCount()));
                    qqchLabourDemandPlan1.setOutNum(BigDecimal.valueOf(result.getLocalCount()));
                    qqchLabourDemandPlan1.setTotal(BigDecimal.valueOf(result.getTotalCount()));
                    BigDecimal rate = BigDecimal.valueOf(result.getLocalCount() / result.getTotalCount());
                    qqchLabourDemandPlan1.setOutProportion(rate.setScale(2, RoundingMode.HALF_UP));
                    saveList.add(qqchLabourDemandPlan1);
                }
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
        for (QqchConstStaffPlanResult result : qqchConstList) {
            Long id = result.getId();
            if (map152.containsKey(id)){
                continue;
            }
            QqchLabourDemandPlan qqchLabourDemandPlan1 = new QqchLabourDemandPlan();
            qqchLabourDemandPlan1.setOutId(result.getId());
            qqchLabourDemandPlan1.setOccupationCode(result.getOccupationCode());
            qqchLabourDemandPlan1.setJobName(result.getOccupationName());
            qqchLabourDemandPlan1.setWorkTeam(result.getConstDesc());
            qqchLabourDemandPlan1.setChinaNum(BigDecimal.valueOf(result.getChineseSideCount()));
            qqchLabourDemandPlan1.setOutNum(BigDecimal.valueOf(result.getLocalCount()));
            qqchLabourDemandPlan1.setTotal(BigDecimal.valueOf(result.getTotalCount()));
            BigDecimal rate = BigDecimal.valueOf(result.getLocalCount() / result.getTotalCount());
            qqchLabourDemandPlan1.setOutProportion(rate.setScale(2, RoundingMode.HALF_UP));
            saveList.add(qqchLabourDemandPlan1);
        }
        if (CollectionUtils.isNotEmpty(delList)) {
            qqchLabourDemandPlanMapper.deleteQqchLabourDemandPlanByPks(delList);
        }

        if (CollectionUtils.isNotEmpty(saveList)) {
            //1.3人员策划 按工种名称分组
            Map<String, List<QqchLabourDemandPlan>> map13 = saveList.stream().collect(Collectors.groupingBy(QqchLabourDemandPlan::getJobName));
            Set<Map.Entry<String, List<QqchLabourDemandPlan>>> entrySet = map13.entrySet();
            //按工种名称遍历，封装1.5.2入库数据
            List<QqchLabourDemandPlan> arrayList = new ArrayList<>();
            for (Map.Entry<String, List<QqchLabourDemandPlan>> entry : entrySet) {
                QqchLabourDemandPlan qqchLabourDemandPlan = new QqchLabourDemandPlan();
                qqchLabourDemandPlan.setJobName(entry.getKey());
                List<QqchLabourDemandPlan> entryValue = entry.getValue();
                qqchLabourDemandPlan.setChildren(entryValue);
                arrayList.add(qqchLabourDemandPlan);
            }
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

    private List<QqchLabourDemandPlan> toTreeList(List<QqchConstStaffPlanResult> qqchConstList){
        //按工种名称分组
        Map<String, List<QqchConstStaffPlanResult>> listMap = qqchConstList.stream().collect(Collectors.groupingBy(QqchConstStaffPlanResult::getOccupationName));
        //遍历封装好
        List<QqchLabourDemandPlan> arrayList = new ArrayList<>();
        Set<Map.Entry<String, List<QqchConstStaffPlanResult>>> entrySet = listMap.entrySet();
        //按工种名称遍历
        for (Map.Entry<String, List<QqchConstStaffPlanResult>> entry : entrySet) {
            QqchLabourDemandPlan qqchLabourDemandPlan = new QqchLabourDemandPlan();
            qqchLabourDemandPlan.setJobName(entry.getKey());
            List<QqchLabourDemandPlan> list = new ArrayList<>();
            List<QqchConstStaffPlanResult> entryValue = entry.getValue();
            for (QqchConstStaffPlanResult result : entryValue) {
                QqchLabourDemandPlan qqchLabourDemandPlan1 = new QqchLabourDemandPlan();
                qqchLabourDemandPlan1.setOutId(result.getId());
                qqchLabourDemandPlan1.setOccupationCode(result.getOccupationCode());
                qqchLabourDemandPlan1.setJobName(result.getOccupationName());
                qqchLabourDemandPlan1.setWorkTeam(result.getConstDesc());
                qqchLabourDemandPlan1.setChinaNum(BigDecimal.valueOf(result.getChineseSideCount()));
                qqchLabourDemandPlan1.setOutNum(BigDecimal.valueOf(result.getLocalCount()));
                qqchLabourDemandPlan1.setTotal(BigDecimal.valueOf(result.getTotalCount()));
                BigDecimal rate = BigDecimal.valueOf(result.getLocalCount() / result.getTotalCount());
                qqchLabourDemandPlan1.setOutProportion(rate.setScale(2, RoundingMode.HALF_UP));
                list.add(qqchLabourDemandPlan1);
            }
            qqchLabourDemandPlan.setChildren(list);
            arrayList.add(qqchLabourDemandPlan);
        }
        return arrayList;
    }
}
