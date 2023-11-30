package com.hhwy.pm.qqch.preparation.quality.problem.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.gm.wbs.service.ITWbsService;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.quality.problem.domain.QqchQualityProblemControl;
import com.hhwy.pm.qqch.preparation.quality.problem.domain.QqchQualityProblemList;
import com.hhwy.pm.qqch.preparation.quality.problem.domain.vo.QqchQualityProblemControlVo;
import com.hhwy.pm.qqch.preparation.quality.problem.domain.vo.QqchQualityProblemListVo;
import com.hhwy.pm.qqch.preparation.quality.problem.mapper.QqchQualityProblemControlMapper;
import com.hhwy.pm.qqch.preparation.quality.problem.mapper.QqchQualityProblemListMapper;
import com.hhwy.pm.qqch.preparation.quality.problem.service.IQqchQualityProblemControlService;
import com.hhwy.pm.qqch.preparation.quality.problem.service.IQqchQualityProblemListService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.pm.qyzs.quality.qyzsQualityCommonProblem.domain.QyzsQualityCommonProblem;
import com.hhwy.pm.xmsl.project.domain.vo.ProjectBasicInfo;
import com.hhwy.pm.xmsl.project.service.IXmslProjectBasicInfoService;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhenglili
 * @date 2023-08-03 14:30:34
 * @remark 9.2.1 质量通病清单
 */
@Service
public class QqchQualityProblemListServiceImpl implements IQqchQualityProblemListService {

    @Autowired
    private QqchQualityProblemListMapper qqchQualityProblemListMapper;
    @Autowired
    private IQqchReviewService qqchReviewService;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;
    @Autowired
    private IQqchQualityProblemControlService qqchQualityProblemControlService;
    @Autowired
    private ITWbsService tWbsService;
    @Autowired
    private QqchQualityProblemControlMapper qqchQualityProblemControlMapper;
    @Autowired
    private IXmslProjectBasicInfoService xmslProjectBasicInfoService;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    /**
     * 列表
     *
     * @param version
     * @return
     */
    public QqchQualityProblemListVo getQqchQualityProblemListList(BigDecimal version) {
        QqchQualityProblemListVo vo = new QqchQualityProblemListVo();
        version = VersionUtil.getVersion("qqch_quality_problem_list", version);

        QqchQualityProblemList qryParam = new QqchQualityProblemList();
        qryParam.setVersion(version);
        List<QqchQualityProblemList> list = qqchQualityProblemListMapper.getQqchQualityProblemListList(qryParam);

        vo.setVersion(version);
        vo.setStageIdentity(qqchReviewService.getStage());
        vo.setList(list);
        return vo;
    }

    /**
     * 保存/确认/提交
     *
     * @param voParam
     * @return
     */
    @Transactional
    public void batchSave(QqchQualityProblemListVo voParam) {
        // 清空数据库表中数据
        QqchQualityProblemList deleteParam = new QqchQualityProblemList();
        deleteParam.setVersion(voParam.getVersion());
        qqchQualityProblemListMapper.deleteQqchQualityProblemList(deleteParam);

        // 质量通病控制措施集合
        List<QqchQualityProblemControl> controlList = new ArrayList<>();

        List<QqchQualityProblemList> list = voParam.getList();
        if (!CollectionUtils.isEmpty(list)) {
            for (QqchQualityProblemList qqchQualityProblemList : list) {
                if (StringUtils.isBlank(qqchQualityProblemList.getProblemCode())) {
                    qqchQualityProblemList.setProblemCode(UUID.randomUUID().toString().replaceAll("-", ""));
                }
                qqchQualityProblemList.setId(IdWorker.createId());
                qqchQualityProblemList.setVersion(voParam.getVersion());
                if (voParam.getVersion().compareTo(BigDecimal.ONE) == 0) {
                    qqchQualityProblemList.setValid(Valid.YES);
                }
                qqchQualityProblemList.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                qqchQualityProblemList.setCreateUserName(SecurityUtils.getUserName());
                qqchQualityProblemList.setCreateTime(DateUtils.getNowDate());

                QqchQualityProblemControl qqchQualityProblemControl = new QqchQualityProblemControl();
                qqchQualityProblemControl.setProblemCode(qqchQualityProblemList.getProblemCode());
                qqchQualityProblemControl.setProblemName(qqchQualityProblemList.getProblemName());
                qqchQualityProblemControl.setControlMeasures(qqchQualityProblemList.getControlMeasures());
                controlList.add(qqchQualityProblemControl);

            }
            // 质量通病清单入库
            qqchQualityProblemListMapper.insertQqchQualityProblemListList(list);

            QqchQualityProblemControlVo qqchQualityProblemControlVo = new QqchQualityProblemControlVo();
            qqchQualityProblemControlVo.setVersion(voParam.getVersion());
            qqchQualityProblemControlVo.setList(controlList);
            // 同步到质量通病控制措施
            qqchQualityProblemControlService.syncData(qqchQualityProblemControlVo);

        }

        String buttonMark = voParam.getButtonMark();
        if (ButtonMark.CONFIRM.equals(buttonMark)) {
            // 插入确认状态
            String menuId = voParam.getMenuId();
            String stageIdentity = voParam.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId, stageIdentity);
            this.pushQyzsQualityCommonProblem(voParam.getVersion());
        }
    }

    /**
     * 推送质量通病到总部版
     * @param version
     */
    public void pushQyzsQualityCommonProblem(BigDecimal version){
        Map<String,Object> map = new HashMap<>();

        //获取项目工程类型
        String defaultEngineeringType = tWbsService.getDefaultEngineeringType();
        //获取项目数据
        ProjectBasicInfo projectInfo = xmslProjectBasicInfoService.projectInfo();
        String projectName = projectInfo.getProjectName();

        //获取质量通病清单数据
        QqchQualityProblemList problemQuery = new QqchQualityProblemList();
        problemQuery.setVersion(version);
        List<QqchQualityProblemList> list = qqchQualityProblemListMapper.getQqchQualityProblemListList(problemQuery);

        if(StringUtils.isBlank(defaultEngineeringType) || CollectionUtils.isEmpty(list) || version == null){
            return;
        }

        // 查询质量通病控制措施数据
        QqchQualityProblemControl query = new QqchQualityProblemControl();
        query.setVersion(version);
        List<QqchQualityProblemControl> problemControlList = qqchQualityProblemControlMapper.getQqchQualityProblemControlList(query);
        Map<String, QqchQualityProblemControl> controlMap = problemControlList.stream().collect(Collectors.toMap(QqchQualityProblemControl::getProblemCode, o -> o));

        List<QyzsQualityCommonProblem> commonProblemList = new ArrayList<>();
        for (QqchQualityProblemList problem : list) {
            QyzsQualityCommonProblem commonProblem = new QyzsQualityCommonProblem();
            commonProblem.setId(IdWorker.createId());
            commonProblem.setProjectType(defaultEngineeringType);
            commonProblem.setWbsCode("");
            commonProblem.setProblemName(problem.getProblemName());
            commonProblem.setConsequence(problem.getPossibleResult());
            QqchQualityProblemControl control = controlMap.get(problem.getProblemCode());
            if(control != null){
                commonProblem.setMeasure(control.getControlMeasures());
            }
            commonProblem.setDataFrom(projectName);
            commonProblem.setEditer(SecurityUtils.getUserName());
            commonProblem.setEditDate(DateUtils.getNowDate());
            commonProblem.setCreateTime(DateUtils.getNowDate());
            commonProblem.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            commonProblem.setCreateUserName(SecurityUtils.getUserName());
            commonProblemList.add(commonProblem);
        }

        if(CollectionUtils.isEmpty(commonProblemList)){
            return;
        }

        map.put("projectType",defaultEngineeringType);
        map.put("wbsCode","");
        map.put("problemList",commonProblemList);
        rocketMQTemplate.convertAndSend("qyzs_quality_common_problem:tenantSuccess", map);
    }
}
