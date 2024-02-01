package com.hhwy.pm.qqch.preparation.measureexp.range.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.constant.PmConstant;
import com.hhwy.pm.qqch.common.aspect.CompileAspect;
import com.hhwy.pm.qqch.common.aspect.CompileOptEnum;
import com.hhwy.pm.qqch.common.domain.CompileEntity;
import com.hhwy.pm.qqch.preparation.measureexp.range.domain.QqchMeasureExpPerson;
import com.hhwy.pm.qqch.preparation.measureexp.range.dto.QqchMeasureExpDTO;
import com.hhwy.pm.qqch.preparation.measureexp.range.mapper.QqchMeasureExpPersonMapper;
import com.hhwy.pm.qqch.preparation.measureexp.range.service.IQqchMeasureExpPersonService;
import com.hhwy.pm.qqch.sgch.qqchLabourDemandPlan.domain.QqchLabourDemandPlan;
import com.hhwy.pm.qqch.sgch.qqchLabourDemandPlan.mapper.QqchLabourDemandPlanMapper;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.EntityUtils;
import com.hhwy.utils.bigDecimalUtils.BigDecimalUtils;
import com.hhwy.utils.date.FtDateUtils;
import com.hhwy.utils.dict.DictUtil;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author mls
 * @date 2023-07-25 18:01:30
 * @remark
 */
@Service
public class QqchMeasureExpPersonServiceImpl implements IQqchMeasureExpPersonService {

    private static final String TN = "qqch_measure_exp_person";

    @Resource
    private QqchMeasureExpPersonMapper qqchMeasureExpPersonMapper;

    @Resource
    private QqchLabourDemandPlanMapper labourDemandPlanMapper;


    public QqchMeasureExpPerson getQqchMeasureExpPerson(QqchMeasureExpPerson qqchMeasureExpPerson) {
        return qqchMeasureExpPersonMapper.getQqchMeasureExpPerson(qqchMeasureExpPerson);
    }

    public List<QqchMeasureExpPerson> getQqchMeasureExpPersonList(QqchMeasureExpPerson qqchMeasureExpPerson) {
        return qqchMeasureExpPersonMapper.getQqchMeasureExpPersonList(qqchMeasureExpPerson);
    }

    @Transactional(rollbackFor = Exception.class)
    public int insertQqchMeasureExpPerson(QqchMeasureExpPerson qqchMeasureExpPerson) {
        qqchMeasureExpPerson.setId(IdWorker.createId());
        qqchMeasureExpPerson.setCreateUser(SecurityUtils.getUserName());
        qqchMeasureExpPerson.setCreateTime(DateUtils.getNowDate());
        return qqchMeasureExpPersonMapper.insertQqchMeasureExpPerson(qqchMeasureExpPerson);
    }

    @Transactional(rollbackFor = Exception.class)
    public int insertQqchMeasureExpPersonList(List<QqchMeasureExpPerson> qqchMeasureExpPersonList) {
        if (CollectionUtils.isEmpty(qqchMeasureExpPersonList)) return 0;
        for (QqchMeasureExpPerson qqchMeasureExpPerson : qqchMeasureExpPersonList) {
            qqchMeasureExpPerson.setId(IdWorker.createId());
            qqchMeasureExpPerson.setCreateUser(SecurityUtils.getUserName());
            qqchMeasureExpPerson.setCreateTime(DateUtils.getNowDate());
        }
        return qqchMeasureExpPersonMapper.insertQqchMeasureExpPersonList(qqchMeasureExpPersonList);
    }

    @Transactional(rollbackFor = Exception.class)
    public int updateQqchMeasureExpPerson(QqchMeasureExpPerson qqchMeasureExpPerson) {
        qqchMeasureExpPerson.setUpdateUser(SecurityUtils.getUserName());
        qqchMeasureExpPerson.setUpdateTime(DateUtils.getNowDate());
        return qqchMeasureExpPersonMapper.updateQqchMeasureExpPerson(qqchMeasureExpPerson);
    }

    @Transactional(rollbackFor = Exception.class)
    public int updateQqchMeasureExpPersonList(List<QqchMeasureExpPerson> qqchMeasureExpPersonList) {
        for (QqchMeasureExpPerson qqchMeasureExpPerson : qqchMeasureExpPersonList) {
            qqchMeasureExpPerson.setUpdateUser(SecurityUtils.getUserName());
            qqchMeasureExpPerson.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchMeasureExpPersonMapper.updateQqchMeasureExpPersonList(qqchMeasureExpPersonList);
    }

    @Transactional(rollbackFor = Exception.class)
    public int deleteQqchMeasureExpPerson(QqchMeasureExpPerson qqchMeasureExpPerson) {
        qqchMeasureExpPerson.setUpdateUser(SecurityUtils.getUserName());
        qqchMeasureExpPerson.setUpdateTime(DateUtils.getNowDate());
        return qqchMeasureExpPersonMapper.deleteQqchMeasureExpPerson(qqchMeasureExpPerson);
    }

    @Transactional(rollbackFor = Exception.class)
    public int deleteQqchMeasureExpPersonByPks(List<Long> qqchMeasureExpPersonPkList) {
        return qqchMeasureExpPersonMapper.deleteQqchMeasureExpPersonByPks(qqchMeasureExpPersonPkList);
    }

    @Override
    @CompileAspect(type = CompileOptEnum.LIST, tableName = TN)
    public List<QqchMeasureExpPerson> getQqchMeasureExpPersonListByVersionCode(QqchMeasureExpPerson qqchMeasureExpPerson) {
        String dataType = qqchMeasureExpPerson.getDataType();
        List<QqchMeasureExpPerson> qqchMeasureExpPersonList = this.getQqchMeasureExpPersonList(qqchMeasureExpPerson);
        //20240126 改为实时获取
        String dictType = PmConstant.ONE.equals(dataType) ? "teamCode" : "exp_teamCode";

        LinkedHashMap<String, String> eamCode = DictUtil.getDictDataName(dictType);
        ArrayList<QqchMeasureExpPerson> objects = new ArrayList<>();
        ArrayList<String> teamNameSet = new ArrayList<>(eamCode.values());
        Map<String, QqchLabourDemandPlan> teamMap = this.getTeamInfo(teamNameSet);
        eamCode.forEach((teamCode, teamName) -> {
            QqchMeasureExpPerson person = new QqchMeasureExpPerson();
            person.setDataType(qqchMeasureExpPerson.getDataType());
            person.setPositionCode(teamCode);
            person.setPositionName(teamName);

            QqchLabourDemandPlan qqchLabourDemandPlan = teamMap.get(teamName) == null ? new QqchLabourDemandPlan() : teamMap.get(teamName);
            person.setCnNum(qqchLabourDemandPlan.getChinaNum());
            person.setLocalNum(qqchLabourDemandPlan.getOutNum());
            person.setPlanInDate(qqchLabourDemandPlan.getEntryDate());
            objects.add(person);
        });
        if(CollectionUtils.isEmpty(qqchMeasureExpPersonList))
            return objects;
        //将从1.5.2获取到的中方、属地化数量、计划进场日期覆盖
        Map<String,QqchMeasureExpPerson> latestPersonMap = objects.stream().collect(Collectors.toMap(r->r.getPositionName(),r->r));
        for (int i = 0; i < qqchMeasureExpPersonList.size(); i++) {
            QqchMeasureExpPerson temp = qqchMeasureExpPersonList.get(i);
            QqchMeasureExpPerson latestPerson = latestPersonMap.get(temp.getPositionName());
            if(latestPerson == null)
                continue;
            temp.setCnNum(latestPerson.getCnNum());
            temp.setLocalNum(latestPerson.getLocalNum());
            temp.setPlanInDate(latestPerson.getPlanInDate());
        }
        return qqchMeasureExpPersonList;
    }

    private Map<String, QqchLabourDemandPlan> getTeamInfo(List<String> teamNameSet) {
        QqchLabourDemandPlan where = new QqchLabourDemandPlan();
        where.setJobNames(teamNameSet);
//        where.setValid("1");
        where.setPid(0L);
        where.setVersion(VersionUtil.getVersion("qqch_labour_demand_plan", null));
        List<QqchLabourDemandPlan> qqchLabourDemandPlanList = labourDemandPlanMapper.getChildInfoList(where);
        
        Map<String, List<QqchLabourDemandPlan>> map = qqchLabourDemandPlanList.stream().filter(i -> StringUtils.isNotEmpty(i.getPname()))
                .collect(Collectors.groupingBy(QqchLabourDemandPlan::getPname));
        HashMap<String, QqchLabourDemandPlan> res = new HashMap<>();
        for (String k : map.keySet()) {
            List<QqchLabourDemandPlan> dbList = map.get(k);

            QqchLabourDemandPlan r = new QqchLabourDemandPlan();
            for (QqchLabourDemandPlan db : dbList) {
                r.setJobName(k);
                r.setChinaNum(BigDecimalUtils.sum(db.getChinaNum(), r.getChinaNum()));
                r.setOutNum(BigDecimalUtils.sum(db.getOutNum(), r.getOutNum()));
                r.setEntryDate(FtDateUtils.min(db.getEntryDate(),r.getEntryDate()));
            }
            res.put(k, r);
        }

        return res;
    }

    @Override
    public void saveList(QqchMeasureExpDTO expVO) {
        //删除旧数据
        QqchMeasureExpPerson delParam = new QqchMeasureExpPerson();
        delParam.setVersion(expVO.getVersion());
        delParam.setDataType(expVO.getDataType());
        qqchMeasureExpPersonMapper.deleteQqchMeasureExpPerson(delParam);

        List<QqchMeasureExpPerson> qqchMeasureExpPeople = CompileEntity.dealSaveDto(expVO, expVO.getPersonList());
        if (CollectionUtils.isEmpty(qqchMeasureExpPeople)) return;
        for (QqchMeasureExpPerson qqchMeasureExpPerson : qqchMeasureExpPeople) {
            qqchMeasureExpPerson.setId(IdWorker.createId());
            qqchMeasureExpPerson.setDataType(expVO.getDataType());
        }
        EntityUtils.setCreateUpdateInfo(qqchMeasureExpPeople);
        this.qqchMeasureExpPersonMapper.insertQqchMeasureExpPersonList(qqchMeasureExpPeople);
    }
}
