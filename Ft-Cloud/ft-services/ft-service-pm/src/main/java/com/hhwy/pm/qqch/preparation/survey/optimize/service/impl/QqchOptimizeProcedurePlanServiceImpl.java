package com.hhwy.pm.qqch.preparation.survey.optimize.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.constant.DictType;
import com.hhwy.feign.service.SystemServiceApi;
import com.hhwy.pm.common.mapper.CommonMapper;
import com.hhwy.pm.qqch.module.contant.ModuleIdentity;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.preparation.survey.extend.domain.QqchPreparationSurveyExtend;
import com.hhwy.pm.qqch.preparation.survey.extend.mapper.QqchPreparationSurveyExtendMapper;
import com.hhwy.pm.qqch.preparation.survey.extend.service.impl.QqchPreparationSurveyExtendServiceImpl;
import com.hhwy.pm.qqch.preparation.survey.optimize.domain.QqchOptimizeProcedurePlan;
import com.hhwy.pm.qqch.preparation.survey.optimize.domain.vo.QqchOptimizeProcedurePlanVo;
import com.hhwy.pm.qqch.preparation.survey.optimize.mapper.QqchOptimizeProcedurePlanMapper;
import com.hhwy.pm.qqch.preparation.survey.optimize.service.IQqchOptimizeProcedurePlanService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.util.CollectionUtils;

/**
 * @author han
 * @date 2023-07-07 18:35:53
 * @remark 优化程序策划
 */
@Service
public class QqchOptimizeProcedurePlanServiceImpl implements IQqchOptimizeProcedurePlanService {

    @Autowired
    private QqchOptimizeProcedurePlanMapper qqchOptimizeProcedurePlanMapper;

    @Autowired
    private QqchPreparationSurveyExtendServiceImpl qqchPreparationSurveyExtendService;

    @Autowired
    private SystemServiceApi systemServiceApi;

    @Autowired
    private CommonMapper commonMapper;


    /**
     * 获取优化程序策划集合
     * @return
     */
    @Override
    public QqchOptimizeProcedurePlanVo getQqchOptimizeProcedurePlanVo() {
        QqchOptimizeProcedurePlanVo qqchOptimizeProcedurePlanVo = new QqchOptimizeProcedurePlanVo();

        BigDecimal version = commonMapper.selectMaxVersion("qqch_optimize_procedure_plan");
        qqchOptimizeProcedurePlanVo.setVersion(version);

        List<QqchOptimizeProcedurePlan> qqchOptimizeProcedurePlanList = qqchOptimizeProcedurePlanMapper.getQqchOptimizeProcedurePlanList(version);
        if(CollectionUtils.isEmpty(qqchOptimizeProcedurePlanList)){
            //数据库中没有数据，需要初始化
            qqchOptimizeProcedurePlanList = this.getInitializeData();
        }
        qqchOptimizeProcedurePlanVo.setQqchOptimizeProcedurePlanList(qqchOptimizeProcedurePlanList);

        //获取附件组id（页面标识和版本号控制）
        QqchPreparationSurveyExtend qqchPreparationSurveyExtend = qqchPreparationSurveyExtendService.getQqchPreparationSurveyExtend(ModuleIdentity.OPTIMIZE_PROCEDURE_PLAN, version);
        if(qqchPreparationSurveyExtend != null){
            qqchOptimizeProcedurePlanVo.setFileGroupId(qqchPreparationSurveyExtend.getFileGroupId());
        }

        //TODO 获取确认状态

        return qqchOptimizeProcedurePlanVo;
    }

    /**
     * 获取初始化数据
     * @return
     */
    public List<QqchOptimizeProcedurePlan> getInitializeData() {
        List<QqchOptimizeProcedurePlan> qqchOptimizeProcedurePlanList = new ArrayList<>();
        QqchOptimizeProcedurePlan data1 = new QqchOptimizeProcedurePlan();
        data1.setLinkName("设计优化小组建立");
        data1.setWorkContent("确定优化意向，判断决策优化点");

        QqchOptimizeProcedurePlan data2 = new QqchOptimizeProcedurePlan();
        data2.setLinkName("设计优化提出及资料准备");
        data2.setWorkContent("提出申请，整理优化资料，提请专家论证");

        QqchOptimizeProcedurePlan data3 = new QqchOptimizeProcedurePlan();
        data3.setLinkName("设计优化论证评审");
        data3.setWorkContent("专家论证，评审技术可行性、经济合理性");

        QqchOptimizeProcedurePlan data4 = new QqchOptimizeProcedurePlan();
        data4.setLinkName("设计优化落实");
        data4.setWorkContent("跟踪落实，推动落地");

        qqchOptimizeProcedurePlanList.add(data1);
        qqchOptimizeProcedurePlanList.add(data2);
        qqchOptimizeProcedurePlanList.add(data3);
        qqchOptimizeProcedurePlanList.add(data4);
        return qqchOptimizeProcedurePlanList;
    }

    /**
     * 保存
     * @param qqchOptimizeProcedurePlanVo
     * @return
     */
    @Override
    @Transactional
    public void save(QqchOptimizeProcedurePlanVo qqchOptimizeProcedurePlanVo) {
        //删除旧数据
        QqchOptimizeProcedurePlan qqchOptimizeProcedurePlan = new QqchOptimizeProcedurePlan();
        qqchOptimizeProcedurePlan.setVersion(qqchOptimizeProcedurePlanVo.getVersion());
        qqchOptimizeProcedurePlanMapper.deleteQqchOptimizeProcedurePlan(qqchOptimizeProcedurePlan);

        //维护附件
        String fileGroupId = qqchOptimizeProcedurePlanVo.getFileGroupId();
        QqchPreparationSurveyExtend qqchPreparationSurveyExtend = qqchPreparationSurveyExtendService.getQqchPreparationSurveyExtend(ModuleIdentity.OPTIMIZE_PROCEDURE_PLAN, qqchOptimizeProcedurePlanVo.getVersion());
        if(qqchPreparationSurveyExtend == null){
            qqchPreparationSurveyExtend = new QqchPreparationSurveyExtend();
            qqchOptimizeProcedurePlanVo.setFileGroupId(fileGroupId);
            qqchPreparationSurveyExtend.setVersion(qqchOptimizeProcedurePlanVo.getVersion());
            qqchPreparationSurveyExtend.setValid(Valid.YES);
            qqchPreparationSurveyExtendService.insertQqchPreparationSurveyExtend(qqchPreparationSurveyExtend);
        }else {
            qqchPreparationSurveyExtendService.updateQqchPreparationSurveyExtend(qqchPreparationSurveyExtend);
        }

        //插入新数据
        this.insertQqchOptimizeProcedurePlanList(qqchOptimizeProcedurePlanVo.getQqchOptimizeProcedurePlanList(),qqchOptimizeProcedurePlanVo.getVersion());
    }

    /**
     * 确认
     * @param qqchOptimizeProcedurePlanVo
     * @return
     */
    @Override
    @Transactional
    public void confirm(QqchOptimizeProcedurePlanVo qqchOptimizeProcedurePlanVo) {
        this.save(qqchOptimizeProcedurePlanVo);

        //TODO 修改确认状态
    }

    /**
     * 批量插入
     * @param qqchOptimizeProcedurePlanList
     * @return
     */
    @Transactional
    public void insertQqchOptimizeProcedurePlanList(List<QqchOptimizeProcedurePlan> qqchOptimizeProcedurePlanList, BigDecimal version) {
        for (QqchOptimizeProcedurePlan qqchOptimizeProcedurePlan : qqchOptimizeProcedurePlanList) {
            qqchOptimizeProcedurePlan.setId(IdWorker.createId());
            qqchOptimizeProcedurePlan.setVersion(version);
            qqchOptimizeProcedurePlan.setValid(Valid.YES);
            qqchOptimizeProcedurePlan.setCreateUser(StringUtils.valueOf(SecurityUtils.getUserId()));
            qqchOptimizeProcedurePlan.setCreateUserName(SecurityUtils.getUserName());
            qqchOptimizeProcedurePlan.setCreateTime(DateUtils.getNowDate());
        }
        qqchOptimizeProcedurePlanMapper.insertQqchOptimizeProcedurePlanList(qqchOptimizeProcedurePlanList);
    }
}
