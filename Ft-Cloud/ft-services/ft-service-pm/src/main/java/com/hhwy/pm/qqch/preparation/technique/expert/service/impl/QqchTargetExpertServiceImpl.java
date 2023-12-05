package com.hhwy.pm.qqch.preparation.technique.expert.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.impl.QqchModuleConfirmCaseServiceImpl;
import com.hhwy.pm.qqch.preparation.technique.expert.domain.QqchTargetExpert;
import com.hhwy.pm.qqch.preparation.technique.expert.domain.vo.QqchTargetExpertVo;
import com.hhwy.pm.qqch.preparation.technique.expert.mapper.QqchTargetExpertMapper;
import com.hhwy.pm.qqch.preparation.technique.expert.service.IQqchTargetExpertService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.ButtonMarkUtil;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.pm.qyzs.speciallistOrg.qyzsSpeciallistLibrary.domain.QyzsSpeciallistLibrary;
import com.hhwy.pm.xmsl.project.domain.vo.ProjectBasicInfo;
import com.hhwy.pm.xmsl.project.service.IXmslProjectBasicInfoService;
import com.hhwy.utils.idworker.IdWorker;
import io.seata.common.util.CollectionUtils;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author han
 * @date 2023-07-25 10:56:35
 * @remark 内外部目标专家选择
 */
@Service
public class QqchTargetExpertServiceImpl implements IQqchTargetExpertService {

    @Autowired
    private QqchTargetExpertMapper qqchTargetExpertMapper;

    @Autowired
    private QqchModuleConfirmCaseServiceImpl qqchModuleConfirmCaseService;

    @Autowired
    private IQqchReviewService qqchReviewService;

    @Autowired
    private IXmslProjectBasicInfoService xmslProjectBasicInfoService;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;


    public QqchTargetExpert getQqchTargetExpert(QqchTargetExpert qqchTargetExpert) {
        return qqchTargetExpertMapper.getQqchTargetExpert(qqchTargetExpert);
    }

    public List<QqchTargetExpert> getQqchTargetExpertList(QqchTargetExpert qqchTargetExpert) {
        return qqchTargetExpertMapper.getQqchTargetExpertList(qqchTargetExpert);
    }

    @Transactional
    public int insertQqchTargetExpert(QqchTargetExpert qqchTargetExpert) {
        qqchTargetExpert.setId(IdWorker.createId());
        qqchTargetExpert.setCreateUser(SecurityUtils.getUserName());
        qqchTargetExpert.setCreateTime(DateUtils.getNowDate());
        return qqchTargetExpertMapper.insertQqchTargetExpert(qqchTargetExpert);
    }

    @Transactional
    public void insertQqchTargetExpertList(List<QqchTargetExpert> qqchTargetExpertList, BigDecimal version) {
        //删除旧数据
        QqchTargetExpert qqchTargetExpert = new QqchTargetExpert();
        qqchTargetExpert.setVersion(version);
        qqchTargetExpertMapper.deleteQqchTargetExpert(qqchTargetExpert);

        if(CollectionUtils.isEmpty(qqchTargetExpertList)){
            return;
        }
        String valid = Valid.NO;
        if(version.compareTo(BigDecimal.ONE) == 0){
            valid = Valid.YES;
        }
        for (QqchTargetExpert targetExpert : qqchTargetExpertList) {
            targetExpert.setId(IdWorker.createId());
            targetExpert.setValid(valid);
            targetExpert.setVersion(version);
            targetExpert.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            targetExpert.setCreateUserName(SecurityUtils.getUserName());
            targetExpert.setCreateTime(DateUtils.getNowDate());
        }
        qqchTargetExpertMapper.insertQqchTargetExpertList(qqchTargetExpertList);
    }

    @Transactional
    public int updateQqchTargetExpert(QqchTargetExpert qqchTargetExpert) {
        qqchTargetExpert.setUpdateUser(SecurityUtils.getUserName());
        qqchTargetExpert.setUpdateTime(DateUtils.getNowDate());
        return qqchTargetExpertMapper.updateQqchTargetExpert(qqchTargetExpert);
    }

    @Transactional
    public int updateQqchTargetExpertList(List<QqchTargetExpert> qqchTargetExpertList) {
        for (QqchTargetExpert qqchTargetExpert : qqchTargetExpertList) {
            qqchTargetExpert.setUpdateUser(SecurityUtils.getUserName());
            qqchTargetExpert.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchTargetExpertMapper.updateQqchTargetExpertList(qqchTargetExpertList);
    }

    @Transactional
    public int deleteQqchTargetExpert(QqchTargetExpert qqchTargetExpert) {
        qqchTargetExpert.setUpdateUser(SecurityUtils.getUserName());
        qqchTargetExpert.setUpdateTime(DateUtils.getNowDate());
        return qqchTargetExpertMapper.deleteQqchTargetExpert(qqchTargetExpert);
    }

    @Transactional
    public int deleteQqchTargetExpertByPks(List<Long> qqchTargetExpertPkList) {
        return qqchTargetExpertMapper.deleteQqchTargetExpertByPks(qqchTargetExpertPkList);
    }

    /**
     * 获取内外部目标专家选择Vo
     * @param qqchTargetExpert
     * @return
     */
    @Override
    public QqchTargetExpertVo getQqchTargetExpertVo(QqchTargetExpert qqchTargetExpert) {
        QqchTargetExpertVo qqchTargetExpertVo = new QqchTargetExpertVo();

        BigDecimal version = qqchTargetExpert.getVersion();
        version = VersionUtil.getVersion("qqch_target_expert",version);

        qqchTargetExpert.setVersion(version);
        List<QqchTargetExpert> qqchTargetExpertList = qqchTargetExpertMapper.getQqchTargetExpertList(qqchTargetExpert);

        qqchTargetExpertVo.setVersion(version);
        qqchTargetExpertVo.setStageIdentity(qqchReviewService.getStage());
        qqchTargetExpertVo.setList(qqchTargetExpertList);
        return qqchTargetExpertVo;
    }

    /**
     * 保存/确认/提交
     * @param qqchTargetExpertVo
     * @return
     */
    @Override
    @Transactional
    public void save(QqchTargetExpertVo qqchTargetExpertVo) {
        String buttonMark = qqchTargetExpertVo.getButtonMark();
        ButtonMarkUtil.checkButtonMark(buttonMark);

        BigDecimal version = qqchTargetExpertVo.getVersion();
        List<QqchTargetExpert> qqchTargetExpertList = qqchTargetExpertVo.getList();

        this.insertQqchTargetExpertList(qqchTargetExpertList,version);

        //处理确认状态是确认
        if(ButtonMark.CONFIRM.equals(buttonMark)){
            //插入确认记录
            String menuId = qqchTargetExpertVo.getMenuId();
            String stageIdentity = qqchTargetExpertVo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
    }

    @Override
    public void addToQyzsSpeciallistLibrary(List<QqchTargetExpert> list) {
        List<QqchTargetExpert> needPushList = list.stream().filter(o -> !"0".equals(o.getPtVar1())).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(needPushList)){
            return;
        }
        ProjectBasicInfo projectInfo = xmslProjectBasicInfoService.projectInfo();
        List<QyzsSpeciallistLibrary> speciallistLibraryList = new ArrayList<>();
        for (QqchTargetExpert expert : needPushList) {
            QyzsSpeciallistLibrary library = new QyzsSpeciallistLibrary();
            library.setId(IdWorker.createId());
            library.setSpeciallistCode(expert.getExpertCode());
            library.setSpeciallistName(expert.getName());
            library.setDepartment(expert.getUnit());
            library.setBusinessAreas(expert.getField());
            library.setProducts(expert.getProduct());
            library.setSpecialty(expert.getSpecialty());
            library.setDataSource(projectInfo.getProjectName());
            library.setPersonId(Math.toIntExact(SecurityUtils.getUserId()));
            library.setPersonName(SecurityUtils.getUserName());
            library.setEditTime(DateUtils.getNowDate());
            library.setRemark(expert.getRemark());
            library.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            library.setCreateUserName(SecurityUtils.getUserName());
            library.setCreateTime(DateUtils.getNowDate());
            speciallistLibraryList.add(library);
        }

        rocketMQTemplate.convertAndSend("qqch_target_expert:tenantSuccess",speciallistLibraryList);
    }
}
