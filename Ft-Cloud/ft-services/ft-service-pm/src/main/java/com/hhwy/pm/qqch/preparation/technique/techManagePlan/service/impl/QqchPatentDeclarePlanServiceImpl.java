package com.hhwy.pm.qqch.preparation.technique.techManagePlan.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.impl.QqchModuleConfirmCaseServiceImpl;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.QqchPatentDeclarePlan;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.vo.QqchPatentDeclarePlanExportVo;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.vo.QqchPatentDeclarePlanVo;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.mapper.QqchPatentDeclarePlanMapper;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.service.IQqchPatentDeclarePlanService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.ButtonMarkUtil;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.tree.ListTreeUtil;
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
 * @date 2023-07-25 10:40:39
 * @remark 专利申报计划
 */
@Service
public class QqchPatentDeclarePlanServiceImpl implements IQqchPatentDeclarePlanService {

    @Autowired
    private QqchPatentDeclarePlanMapper qqchPatentDeclarePlanMapper;

    @Autowired
    private QqchModuleConfirmCaseServiceImpl qqchModuleConfirmCaseService;

    @Autowired
    private IQqchReviewService qqchReviewService;


    public QqchPatentDeclarePlan getQqchPatentDeclarePlan(QqchPatentDeclarePlan qqchPatentDeclarePlan) {
        return qqchPatentDeclarePlanMapper.getQqchPatentDeclarePlan(qqchPatentDeclarePlan);
    }

    public List<QqchPatentDeclarePlan> getQqchPatentDeclarePlanList(QqchPatentDeclarePlan qqchPatentDeclarePlan) {
        return qqchPatentDeclarePlanMapper.getQqchPatentDeclarePlanList(qqchPatentDeclarePlan);
    }

    @Transactional
    public int insertQqchPatentDeclarePlan(QqchPatentDeclarePlan qqchPatentDeclarePlan) {
        qqchPatentDeclarePlan.setId(IdWorker.createId());
        qqchPatentDeclarePlan.setCreateUser(SecurityUtils.getUserName());
        qqchPatentDeclarePlan.setCreateTime(DateUtils.getNowDate());
        return qqchPatentDeclarePlanMapper.insertQqchPatentDeclarePlan(qqchPatentDeclarePlan);
    }

    @Transactional
    public void insertQqchPatentDeclarePlanList(List<QqchPatentDeclarePlan> qqchPatentDeclarePlanList, BigDecimal version) {
        //删除旧数据
        QqchPatentDeclarePlan qqchPatentDeclarePlan = new QqchPatentDeclarePlan();
        qqchPatentDeclarePlan.setVersion(version);
        qqchPatentDeclarePlanMapper.deleteQqchPatentDeclarePlan(qqchPatentDeclarePlan);

        if(CollectionUtils.isEmpty(qqchPatentDeclarePlanList)){
            return;
        }
        int sort = 1;
        String valid = Valid.NO;
        if(version.compareTo(BigDecimal.ONE) == 0){
            valid = Valid.YES;
        }
        for (QqchPatentDeclarePlan patentDeclarePlan : qqchPatentDeclarePlanList) {
            patentDeclarePlan.setId(IdWorker.createId());
            patentDeclarePlan.setValid(valid);
            patentDeclarePlan.setVersion(version);
            patentDeclarePlan.setSort(sort++);
            patentDeclarePlan.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            patentDeclarePlan.setCreateUserName(SecurityUtils.getUserName());
            patentDeclarePlan.setCreateTime(DateUtils.getNowDate());
        }
        qqchPatentDeclarePlanMapper.insertQqchPatentDeclarePlanList(qqchPatentDeclarePlanList);
    }

    @Transactional
    public int updateQqchPatentDeclarePlan(QqchPatentDeclarePlan qqchPatentDeclarePlan) {
        qqchPatentDeclarePlan.setUpdateUser(SecurityUtils.getUserName());
        qqchPatentDeclarePlan.setUpdateTime(DateUtils.getNowDate());
        return qqchPatentDeclarePlanMapper.updateQqchPatentDeclarePlan(qqchPatentDeclarePlan);
    }

    @Transactional
    public int updateQqchPatentDeclarePlanList(List<QqchPatentDeclarePlan> qqchPatentDeclarePlanList) {
        for (QqchPatentDeclarePlan qqchPatentDeclarePlan : qqchPatentDeclarePlanList) {
            qqchPatentDeclarePlan.setUpdateUser(SecurityUtils.getUserName());
            qqchPatentDeclarePlan.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchPatentDeclarePlanMapper.updateQqchPatentDeclarePlanList(qqchPatentDeclarePlanList);
    }

    @Transactional
    public int deleteQqchPatentDeclarePlan(QqchPatentDeclarePlan qqchPatentDeclarePlan) {
        qqchPatentDeclarePlan.setUpdateUser(SecurityUtils.getUserName());
        qqchPatentDeclarePlan.setUpdateTime(DateUtils.getNowDate());
        return qqchPatentDeclarePlanMapper.deleteQqchPatentDeclarePlan(qqchPatentDeclarePlan);
    }

    @Transactional
    public int deleteQqchPatentDeclarePlanByPks(List<Long> qqchPatentDeclarePlanPkList) {
        return qqchPatentDeclarePlanMapper.deleteQqchPatentDeclarePlanByPks(qqchPatentDeclarePlanPkList);
    }

    /**
     * 获取专利申报计划Vo
     * @param qqchPatentDeclarePlan
     * @return
     */
    @Override
    public QqchPatentDeclarePlanVo getQqchPatentDeclarePlanVo(QqchPatentDeclarePlan qqchPatentDeclarePlan) {
        QqchPatentDeclarePlanVo qqchPatentDeclarePlanVo = new QqchPatentDeclarePlanVo();

        BigDecimal version = qqchPatentDeclarePlan.getVersion();
        version = VersionUtil.getVersion("qqch_patent_declare_plan",version);

        qqchPatentDeclarePlan.setVersion(version);
        List<QqchPatentDeclarePlan> qqchPatentDeclarePlanList = qqchPatentDeclarePlanMapper.getQqchPatentDeclarePlanList(qqchPatentDeclarePlan);

        qqchPatentDeclarePlanVo.setVersion(version);
        qqchPatentDeclarePlanVo.setStageIdentity(qqchReviewService.getStage());
        qqchPatentDeclarePlanVo.setList(qqchPatentDeclarePlanList);
        return qqchPatentDeclarePlanVo;
    }

    /**
     * 保存/确认/提交
     * @param qqchPatentDeclarePlanVo
     * @return
     */
    @Override
    @Transactional
    public void save(QqchPatentDeclarePlanVo qqchPatentDeclarePlanVo) {
        String buttonMark = qqchPatentDeclarePlanVo.getButtonMark();
        ButtonMarkUtil.checkButtonMark(buttonMark);

        BigDecimal version = qqchPatentDeclarePlanVo.getVersion();
        List<QqchPatentDeclarePlan> qqchPatentDeclarePlanList = qqchPatentDeclarePlanVo.getList();

        //处理数据
        this.insertQqchPatentDeclarePlanList(qqchPatentDeclarePlanList,version);

        //判断是否是确认
        if(ButtonMark.CONFIRM.equals(buttonMark)){
            //插入确认记录
            String menuId = qqchPatentDeclarePlanVo.getMenuId();
            String stageIdentity = qqchPatentDeclarePlanVo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
    }

    /**
     * 获取导出数据
     * @param qqchPatentDeclarePlan
     * @return
     */
    @Override
    public List<QqchPatentDeclarePlanExportVo> getQqchPatentDeclarePlanExportVoList(QqchPatentDeclarePlan qqchPatentDeclarePlan) {
        List<QqchPatentDeclarePlanExportVo> qqchPatentDeclarePlanExportVoList = new ArrayList<>();

        List<QqchPatentDeclarePlan> qqchPatentDeclarePlanList = qqchPatentDeclarePlanMapper.getQqchPatentDeclarePlanList(qqchPatentDeclarePlan);
        for (QqchPatentDeclarePlan patentDeclarePlan : qqchPatentDeclarePlanList) {
            QqchPatentDeclarePlanExportVo qqchPatentDeclarePlanExportVo = new QqchPatentDeclarePlanExportVo();
            BeanUtils.copyProperties(patentDeclarePlan,qqchPatentDeclarePlanExportVo);
            qqchPatentDeclarePlanExportVoList.add(qqchPatentDeclarePlanExportVo);
        }
        ListTreeUtil.preserveSerialNumber(qqchPatentDeclarePlanExportVoList, QqchPatentDeclarePlanExportVo::setSerialNumber);
        return qqchPatentDeclarePlanExportVoList;
    }
}
