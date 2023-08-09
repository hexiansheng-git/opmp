package com.hhwy.pm.qqch.preparation.technique.techManagePlan.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.impl.QqchModuleConfirmCaseServiceImpl;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.QqchCraftDeclarePlan;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.vo.QqchCraftDeclarePlanExportVo;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.vo.QqchCraftDeclarePlanVo;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.mapper.QqchCraftDeclarePlanMapper;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.service.IQqchCraftDeclarePlanService;
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
 * @date 2023-07-25 10:40:02
 * @remark 工艺工法申报计划
 */
@Service
public class QqchCraftDeclarePlanServiceImpl implements IQqchCraftDeclarePlanService {

    @Autowired
    private QqchCraftDeclarePlanMapper qqchCraftDeclarePlanMapper;

    @Autowired
    private QqchModuleConfirmCaseServiceImpl qqchModuleConfirmCaseService;

    @Autowired
    private IQqchReviewService qqchReviewService;


    public QqchCraftDeclarePlan getQqchCraftDeclarePlan(QqchCraftDeclarePlan qqchCraftDeclarePlan) {
        return qqchCraftDeclarePlanMapper.getQqchCraftDeclarePlan(qqchCraftDeclarePlan);
    }

    public List<QqchCraftDeclarePlan> getQqchCraftDeclarePlanList(QqchCraftDeclarePlan qqchCraftDeclarePlan) {
        return qqchCraftDeclarePlanMapper.getQqchCraftDeclarePlanList(qqchCraftDeclarePlan);
    }

    @Transactional
    public int insertQqchCraftDeclarePlan(QqchCraftDeclarePlan qqchCraftDeclarePlan) {
        qqchCraftDeclarePlan.setId(IdWorker.createId());
        qqchCraftDeclarePlan.setCreateUser(SecurityUtils.getUserName());
        qqchCraftDeclarePlan.setCreateTime(DateUtils.getNowDate());
        return qqchCraftDeclarePlanMapper.insertQqchCraftDeclarePlan(qqchCraftDeclarePlan);
    }

    @Transactional
    public void insertQqchCraftDeclarePlanList(List<QqchCraftDeclarePlan> qqchCraftDeclarePlanList, BigDecimal version) {
        //删除旧数据
        QqchCraftDeclarePlan qqchCraftDeclarePlan = new QqchCraftDeclarePlan();
        qqchCraftDeclarePlan.setVersion(version);
        qqchCraftDeclarePlanMapper.deleteQqchCraftDeclarePlan(qqchCraftDeclarePlan);

        if(CollectionUtils.isEmpty(qqchCraftDeclarePlanList)){
            return;
        }
        int sort = 1;
        String valid = Valid.NO;
        if(version.compareTo(BigDecimal.ONE) == 0){
            valid = Valid.YES;
        }
        for (QqchCraftDeclarePlan craftDeclarePlan : qqchCraftDeclarePlanList) {
            craftDeclarePlan.setId(IdWorker.createId());
            craftDeclarePlan.setValid(valid);
            craftDeclarePlan.setVersion(version);
            craftDeclarePlan.setSort(sort++);
            craftDeclarePlan.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            craftDeclarePlan.setCreateUserName(SecurityUtils.getUserName());
            craftDeclarePlan.setCreateTime(DateUtils.getNowDate());
        }
        qqchCraftDeclarePlanMapper.insertQqchCraftDeclarePlanList(qqchCraftDeclarePlanList);
    }

    @Transactional
    public int updateQqchCraftDeclarePlan(QqchCraftDeclarePlan qqchCraftDeclarePlan) {
        qqchCraftDeclarePlan.setUpdateUser(SecurityUtils.getUserName());
        qqchCraftDeclarePlan.setUpdateTime(DateUtils.getNowDate());
        return qqchCraftDeclarePlanMapper.updateQqchCraftDeclarePlan(qqchCraftDeclarePlan);
    }

    @Transactional
    public int updateQqchCraftDeclarePlanList(List<QqchCraftDeclarePlan> qqchCraftDeclarePlanList) {
        for (QqchCraftDeclarePlan qqchCraftDeclarePlan : qqchCraftDeclarePlanList) {
            qqchCraftDeclarePlan.setUpdateUser(SecurityUtils.getUserName());
            qqchCraftDeclarePlan.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchCraftDeclarePlanMapper.updateQqchCraftDeclarePlanList(qqchCraftDeclarePlanList);
    }

    @Transactional
    public int deleteQqchCraftDeclarePlan(QqchCraftDeclarePlan qqchCraftDeclarePlan) {
        qqchCraftDeclarePlan.setUpdateUser(SecurityUtils.getUserName());
        qqchCraftDeclarePlan.setUpdateTime(DateUtils.getNowDate());
        return qqchCraftDeclarePlanMapper.deleteQqchCraftDeclarePlan(qqchCraftDeclarePlan);
    }

    @Transactional
    public int deleteQqchCraftDeclarePlanByPks(List<Long> qqchCraftDeclarePlanPkList) {
        return qqchCraftDeclarePlanMapper.deleteQqchCraftDeclarePlanByPks(qqchCraftDeclarePlanPkList);
    }

    /**
     * 获取工艺工法申报计划Vo
     * @param qqchCraftDeclarePlan
     * @return
     */
    @Override
    public QqchCraftDeclarePlanVo getQqchCraftDeclarePlanVo(QqchCraftDeclarePlan qqchCraftDeclarePlan) {
        QqchCraftDeclarePlanVo qqchCraftDeclarePlanVo = new QqchCraftDeclarePlanVo();

        BigDecimal version = qqchCraftDeclarePlan.getVersion();
        version = VersionUtil.getVersion("qqch_craft_declare_plan",version);

        qqchCraftDeclarePlan.setVersion(version);
        List<QqchCraftDeclarePlan> qqchCraftDeclarePlanList = qqchCraftDeclarePlanMapper.getQqchCraftDeclarePlanList(qqchCraftDeclarePlan);

        qqchCraftDeclarePlanVo.setVersion(version);
        qqchCraftDeclarePlanVo.setStageIdentity(qqchReviewService.getStage());
        qqchCraftDeclarePlanVo.setList(qqchCraftDeclarePlanList);
        return qqchCraftDeclarePlanVo;
    }

    /**
     * 保存/确认/提交
     * @param qqchCraftDeclarePlanVo
     * @return
     */
    @Override
    @Transactional
    public void save(QqchCraftDeclarePlanVo qqchCraftDeclarePlanVo) {
        String buttonMark = qqchCraftDeclarePlanVo.getButtonMark();
        ButtonMarkUtil.checkButtonMark(buttonMark);

        BigDecimal version = qqchCraftDeclarePlanVo.getVersion();
        List<QqchCraftDeclarePlan> qqchCraftDeclarePlanList = qqchCraftDeclarePlanVo.getList();

        this.insertQqchCraftDeclarePlanList(qqchCraftDeclarePlanList,version);

        //判断是否是确认
        if(ButtonMark.CONFIRM.equals(buttonMark)){
            //插入确认记录
            String menuId = qqchCraftDeclarePlanVo.getMenuId();
            String stageIdentity = qqchCraftDeclarePlanVo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
    }

    /**
     * 获取导出数据
     * @param qqchCraftDeclarePlan
     * @return
     */
    @Override
    public List<QqchCraftDeclarePlanExportVo> getQqchCraftDeclarePlanExportVoList(QqchCraftDeclarePlan qqchCraftDeclarePlan) {
        List<QqchCraftDeclarePlanExportVo> qqchCraftDeclarePlanExportVoList = new ArrayList<>();

        List<QqchCraftDeclarePlan> qqchCraftDeclarePlanList = qqchCraftDeclarePlanMapper.getQqchCraftDeclarePlanList(qqchCraftDeclarePlan);
        for (QqchCraftDeclarePlan craftDeclarePlan : qqchCraftDeclarePlanList) {
            QqchCraftDeclarePlanExportVo qqchCraftDeclarePlanExportVo = new QqchCraftDeclarePlanExportVo();
            BeanUtils.copyProperties(craftDeclarePlan,qqchCraftDeclarePlanExportVo);
            qqchCraftDeclarePlanExportVoList.add(qqchCraftDeclarePlanExportVo);
        }
        return qqchCraftDeclarePlanExportVoList;
    }
}
