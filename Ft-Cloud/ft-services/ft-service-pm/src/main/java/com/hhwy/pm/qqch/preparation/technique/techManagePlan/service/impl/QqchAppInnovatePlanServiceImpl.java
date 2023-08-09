package com.hhwy.pm.qqch.preparation.technique.techManagePlan.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.impl.QqchModuleConfirmCaseServiceImpl;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.QqchAppInnovatePlan;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.vo.QqchAppInnovatePlanExportVo;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.vo.QqchAppInnovatePlanVo;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.mapper.QqchAppInnovatePlanMapper;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.service.IQqchAppInnovatePlanService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.ButtonMarkUtil;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.idworker.IdWorker;
import io.seata.common.util.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author han
 * @date 2023-07-25 10:39:47
 * @remark 四新应用及创新计划
 */
@Service
public class QqchAppInnovatePlanServiceImpl implements IQqchAppInnovatePlanService {

    @Autowired
    private QqchAppInnovatePlanMapper qqchAppInnovatePlanMapper;

    @Autowired
    private QqchModuleConfirmCaseServiceImpl qqchModuleConfirmCaseService;

    @Autowired
    private IQqchReviewService qqchReviewService;


    public QqchAppInnovatePlan getQqchAppInnovatePlan(QqchAppInnovatePlan qqchAppInnovatePlan) {
        return qqchAppInnovatePlanMapper.getQqchAppInnovatePlan(qqchAppInnovatePlan);
    }

    public List<QqchAppInnovatePlan> getQqchAppInnovatePlanList(QqchAppInnovatePlan qqchAppInnovatePlan) {
        return qqchAppInnovatePlanMapper.getQqchAppInnovatePlanList(qqchAppInnovatePlan);
    }

    @Transactional
    public int insertQqchAppInnovatePlan(QqchAppInnovatePlan qqchAppInnovatePlan) {
        qqchAppInnovatePlan.setId(IdWorker.createId());
        qqchAppInnovatePlan.setCreateUser(SecurityUtils.getUserName());
        qqchAppInnovatePlan.setCreateTime(DateUtils.getNowDate());
        return qqchAppInnovatePlanMapper.insertQqchAppInnovatePlan(qqchAppInnovatePlan);
    }

    @Transactional
    public void insertQqchAppInnovatePlanList(List<QqchAppInnovatePlan> qqchAppInnovatePlanList, BigDecimal version) {
        //删除旧数据
        QqchAppInnovatePlan qqchAppInnovatePlan = new QqchAppInnovatePlan();
        qqchAppInnovatePlan.setVersion(version);
        qqchAppInnovatePlanMapper.deleteQqchAppInnovatePlan(qqchAppInnovatePlan);

        if(CollectionUtils.isEmpty(qqchAppInnovatePlanList)){
            return;
        }
        int sort = 1;
        String valid = Valid.NO;
        if(version.compareTo(BigDecimal.ONE) == 0){
            valid = Valid.YES;
        }
        for (QqchAppInnovatePlan appInnovatePlan : qqchAppInnovatePlanList) {
            appInnovatePlan.setId(IdWorker.createId());
            appInnovatePlan.setValid(valid);
            appInnovatePlan.setVersion(version);
            appInnovatePlan.setSort(sort++);
            appInnovatePlan.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            appInnovatePlan.setCreateUserName(SecurityUtils.getUserName());
            appInnovatePlan.setCreateTime(DateUtils.getNowDate());
        }
        qqchAppInnovatePlanMapper.insertQqchAppInnovatePlanList(qqchAppInnovatePlanList);
    }

    @Transactional
    public int updateQqchAppInnovatePlan(QqchAppInnovatePlan qqchAppInnovatePlan) {
        qqchAppInnovatePlan.setUpdateUser(SecurityUtils.getUserName());
        qqchAppInnovatePlan.setUpdateTime(DateUtils.getNowDate());
        return qqchAppInnovatePlanMapper.updateQqchAppInnovatePlan(qqchAppInnovatePlan);
    }

    @Transactional
    public int updateQqchAppInnovatePlanList(List<QqchAppInnovatePlan> qqchAppInnovatePlanList) {
        for (QqchAppInnovatePlan qqchAppInnovatePlan : qqchAppInnovatePlanList) {
            qqchAppInnovatePlan.setUpdateUser(SecurityUtils.getUserName());
            qqchAppInnovatePlan.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchAppInnovatePlanMapper.updateQqchAppInnovatePlanList(qqchAppInnovatePlanList);
    }

    @Transactional
    public int deleteQqchAppInnovatePlan(QqchAppInnovatePlan qqchAppInnovatePlan) {
        qqchAppInnovatePlan.setUpdateUser(SecurityUtils.getUserName());
        qqchAppInnovatePlan.setUpdateTime(DateUtils.getNowDate());
        return qqchAppInnovatePlanMapper.deleteQqchAppInnovatePlan(qqchAppInnovatePlan);
    }

    @Transactional
    public int deleteQqchAppInnovatePlanByPks(List<Long> qqchAppInnovatePlanPkList) {
        return qqchAppInnovatePlanMapper.deleteQqchAppInnovatePlanByPks(qqchAppInnovatePlanPkList);
    }

    /**
     * 获取四新应用及创新计划Vo
     * @param qqchAppInnovatePlan
     * @return
     */
    @Override
    public QqchAppInnovatePlanVo getQqchAppInnovatePlanVo(QqchAppInnovatePlan qqchAppInnovatePlan) {
        QqchAppInnovatePlanVo qqchAppInnovatePlanVo = new QqchAppInnovatePlanVo();

        BigDecimal version = qqchAppInnovatePlan.getVersion();
        version = VersionUtil.getVersion("qqch_app_innovate_plan",version);

        qqchAppInnovatePlan.setVersion(version);
        List<QqchAppInnovatePlan> qqchAppInnovatePlanList = qqchAppInnovatePlanMapper.getQqchAppInnovatePlanList(qqchAppInnovatePlan);

        qqchAppInnovatePlanVo.setVersion(version);
        qqchAppInnovatePlanVo.setStageIdentity(qqchReviewService.getStage());
        qqchAppInnovatePlanVo.setList(qqchAppInnovatePlanList);
        return qqchAppInnovatePlanVo;
    }

    /**
     * 保存/确认/提交
     * @param qqchAppInnovatePlanVo
     * @return
     */
    @Override
    @Transactional
    public void save(QqchAppInnovatePlanVo qqchAppInnovatePlanVo) {
        String buttonMark = qqchAppInnovatePlanVo.getButtonMark();
        ButtonMarkUtil.checkButtonMark(buttonMark);

        BigDecimal version = qqchAppInnovatePlanVo.getVersion();
        List<QqchAppInnovatePlan> qqchAppInnovatePlanList = qqchAppInnovatePlanVo.getList();

        this.insertQqchAppInnovatePlanList(qqchAppInnovatePlanList,version);

        //判断是否是确认
        if(ButtonMark.CONFIRM.equals(buttonMark)){
            //插入确认记录
            String menuId = qqchAppInnovatePlanVo.getMenuId();
            String stageIdentity = qqchAppInnovatePlanVo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
    }

    /**
     * 获取导出数据
     * @param qqchAppInnovatePlan
     */
    @Override
    public List<QqchAppInnovatePlanExportVo> getQqchAppInnovatePlanExportVoList(QqchAppInnovatePlan qqchAppInnovatePlan) {
        List<QqchAppInnovatePlanExportVo> qqchAppInnovatePlanExportVoList = new ArrayList<>();

        List<QqchAppInnovatePlan> qqchAppInnovatePlanList = qqchAppInnovatePlanMapper.getQqchAppInnovatePlanList(qqchAppInnovatePlan);
        for (QqchAppInnovatePlan appInnovatePlan : qqchAppInnovatePlanList) {
            QqchAppInnovatePlanExportVo qqchAppInnovatePlanExportVo = new QqchAppInnovatePlanExportVo();
            BeanUtils.copyProperties(appInnovatePlan,qqchAppInnovatePlanExportVo);
            qqchAppInnovatePlanExportVoList.add(qqchAppInnovatePlanExportVo);
        }
        return qqchAppInnovatePlanExportVoList;
    }
}
