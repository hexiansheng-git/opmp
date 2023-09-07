package com.hhwy.pm.qqch.preparation.safe.safeThreeTypePerson.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.safe.safeThreeTypePerson.domain.QqchSafeThreeTypePerson;
import com.hhwy.pm.qqch.preparation.safe.safeThreeTypePerson.mapper.QqchSafeThreeTypePersonMapper;
import com.hhwy.pm.qqch.preparation.safe.safeThreeTypePerson.service.IQqchSafeThreeTypePersonService;
import com.hhwy.pm.qqch.preparation.safe.safeThreeTypePerson.vo.QqchSafeThreeTypePersonVo;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.dict.DictUtil;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.objectUtil.ObjectNullUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zq
 * @date 2023-08-08 17:22:03
 * @remark
 */
@Service
public class QqchSafeThreeTypePersonServiceImpl implements IQqchSafeThreeTypePersonService {

    @Autowired
    private QqchSafeThreeTypePersonMapper qqchSafeThreeTypePersonMapper;
    @Autowired
    private IQqchReviewService qqchReviewService;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;

    public QqchSafeThreeTypePerson getQqchSafeThreeTypePerson(QqchSafeThreeTypePerson qqchSafeThreeTypePerson) {
        return qqchSafeThreeTypePersonMapper.getQqchSafeThreeTypePerson(qqchSafeThreeTypePerson);
    }

    public QqchSafeThreeTypePersonVo getQqchSafeThreeTypePersonList(QqchSafeThreeTypePerson qqchSafeThreeTypePerson) {
        ArrayList<QqchSafeThreeTypePerson> returnList = new ArrayList<>();
        QqchSafeThreeTypePersonVo person = new QqchSafeThreeTypePersonVo();
        BigDecimal version = qqchSafeThreeTypePerson.getVersion();
        version = VersionUtil.getVersion("qqch_safe_three_type_person",version);
        qqchSafeThreeTypePerson.setVersion(version);
        List<QqchSafeThreeTypePerson> qqchSafeThreeTypePersonList = qqchSafeThreeTypePersonMapper.getQqchSafeThreeTypePersonList(qqchSafeThreeTypePerson);
        //转树列表
        if(!ObjectNullUtil.isEmpty(qqchSafeThreeTypePersonList)){
            Map<String, List<QqchSafeThreeTypePerson>> dataListMap = qqchSafeThreeTypePersonList.stream().collect(Collectors.groupingBy(t -> t.getDuties()));
            LinkedHashMap<String, String> dutiesTypeMap = DictUtil.getDictData("duties_type");

            for (String key : dutiesTypeMap.keySet()) {
                QqchSafeThreeTypePerson parent = new QqchSafeThreeTypePerson();
                parent.setId(Long.parseLong(dutiesTypeMap.get(key)));
                parent.setDuties(key);
                List<QqchSafeThreeTypePerson> qqchSafeThreeTypePeople = dataListMap.get(key);
                if(!ObjectNullUtil.isEmpty(qqchSafeThreeTypePeople)){
                    for (QqchSafeThreeTypePerson safeThreeTypePerson : qqchSafeThreeTypePeople) {
                        safeThreeTypePerson.setPId(parent.getId());
                    }
                }
                parent.setChildren(qqchSafeThreeTypePeople);
                returnList.add(parent);
            }
            person.setList(returnList);
        }
        person.setVersion(version);
        person.setStageIdentity(qqchReviewService.getStage());
        return person;
    }

    @Transactional
    public int insertQqchSafeThreeTypePerson(QqchSafeThreeTypePerson qqchSafeThreeTypePerson) {
        qqchSafeThreeTypePerson.setId(IdWorker.createId());
        qqchSafeThreeTypePerson.setCreateUser(SecurityUtils.getUserName());
        qqchSafeThreeTypePerson.setCreateTime(DateUtils.getNowDate());
        return qqchSafeThreeTypePersonMapper.insertQqchSafeThreeTypePerson(qqchSafeThreeTypePerson);
    }

    @Transactional
    public int insertQqchSafeThreeTypePersonList(QqchSafeThreeTypePersonVo qqchSafeThreeTypePersonVo) {
        //清空数据库表中数据
        QqchSafeThreeTypePerson temp = new QqchSafeThreeTypePerson();
        temp.setVersion(qqchSafeThreeTypePersonVo.getVersion());
        qqchSafeThreeTypePersonMapper.deleteQqchSafeThreeTypePerson(temp);
        if (!CollectionUtils.isEmpty(qqchSafeThreeTypePersonVo.getList())) {
            List<QqchSafeThreeTypePerson> list = qqchSafeThreeTypePersonVo.getList();
            LinkedHashMap<String, String> dutiesTypeMap = DictUtil.getDictData("duties_type");
            for (QqchSafeThreeTypePerson person : list) {
                person.setId(IdWorker.createId());
                person.setVersion(qqchSafeThreeTypePersonVo.getVersion());
                if (qqchSafeThreeTypePersonVo.getVersion().compareTo(BigDecimal.ONE) == 0) {
                    person.setValid(Valid.YES);
                }else{
                    person.setValid(Valid.NO);
                }
                person.setDuties(dutiesTypeMap.get(person.getDuties()));
                person.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                person.setCreateUserName(SecurityUtils.getUserName());
                person.setCreateTime(DateUtils.getNowDate());
            }
            //新增
            qqchSafeThreeTypePersonMapper.insertQqchSafeThreeTypePersonList(list);
        }
        String buttonMark = qqchSafeThreeTypePersonVo.getButtonMark();
        if (ButtonMark.CONFIRM.equals(buttonMark)) {
            // 插入确认状态
            qqchReviewService.updateFinishNum(qqchSafeThreeTypePersonVo.getStageIdentity(), qqchSafeThreeTypePersonVo.getModuleIdentity());
            qqchModuleConfirmCaseService.addConfirmRecord(qqchSafeThreeTypePersonVo.getMenuId(), qqchSafeThreeTypePersonVo.getStageIdentity());
        }
        return 1;
    }

    @Transactional
    public int updateQqchSafeThreeTypePerson(QqchSafeThreeTypePerson qqchSafeThreeTypePerson) {
        qqchSafeThreeTypePerson.setUpdateUser(SecurityUtils.getUserName());
        qqchSafeThreeTypePerson.setUpdateTime(DateUtils.getNowDate());
        return qqchSafeThreeTypePersonMapper.updateQqchSafeThreeTypePerson(qqchSafeThreeTypePerson);
    }

    @Transactional
    public int updateQqchSafeThreeTypePersonList(List<QqchSafeThreeTypePerson> qqchSafeThreeTypePersonList) {
        for (QqchSafeThreeTypePerson qqchSafeThreeTypePerson : qqchSafeThreeTypePersonList) {
            qqchSafeThreeTypePerson.setUpdateUser(SecurityUtils.getUserName());
            qqchSafeThreeTypePerson.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchSafeThreeTypePersonMapper.updateQqchSafeThreeTypePersonList(qqchSafeThreeTypePersonList);
    }

    @Transactional
    public int deleteQqchSafeThreeTypePerson(QqchSafeThreeTypePerson qqchSafeThreeTypePerson) {
        qqchSafeThreeTypePerson.setUpdateUser(SecurityUtils.getUserName());
        qqchSafeThreeTypePerson.setUpdateTime(DateUtils.getNowDate());
        return qqchSafeThreeTypePersonMapper.deleteQqchSafeThreeTypePerson(qqchSafeThreeTypePerson);
    }

    @Transactional
    public int deleteQqchSafeThreeTypePersonByPks(List<Long> qqchSafeThreeTypePersonPkList) {
        return qqchSafeThreeTypePersonMapper.deleteQqchSafeThreeTypePersonByPks(qqchSafeThreeTypePersonPkList);
    }

    @Override
    public void batchRefresh(QqchSafeThreeTypePersonVo qqchSafeThreeTypePersonVo) {
        //仅仅修改最大版本切有效的人员证件信息
        BigDecimal version = VersionUtil.getVersion("qqch_safe_three_type_person", null);
        if (!CollectionUtils.isEmpty(qqchSafeThreeTypePersonVo.getList())) {
            List<QqchSafeThreeTypePerson> list = qqchSafeThreeTypePersonVo.getList();
            for (QqchSafeThreeTypePerson person : list) {
                person.setVersion(version);
                person.setUpdateTime(new Date());
            }
            qqchSafeThreeTypePersonMapper.batchRefresh(list,version);
        }
    }
}
