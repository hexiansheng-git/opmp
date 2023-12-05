package com.hhwy.pm.qqch.preparation.safe.qqchSafeEnvirRiskList.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.common.service.CommonServiceUtil;
import com.hhwy.pm.gm.wbs.service.ITWbsService;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.safe.qqchSafeEnvirRiskList.domain.QqchSafeEnvirRiskList;
import com.hhwy.pm.qqch.preparation.safe.qqchSafeEnvirRiskList.domain.QqchSafeEnvirRiskListDetail;
import com.hhwy.pm.qqch.preparation.safe.qqchSafeEnvirRiskList.domain.vo.QqchSafeEnvirRiskListVo;
import com.hhwy.pm.qqch.preparation.safe.qqchSafeEnvirRiskList.domain.vo.SafeEnvirRiskListQueryVo;
import com.hhwy.pm.qqch.preparation.safe.qqchSafeEnvirRiskList.mapper.QqchSafeEnvirRiskListDetailMapper;
import com.hhwy.pm.qqch.preparation.safe.qqchSafeEnvirRiskList.mapper.QqchSafeEnvirRiskListMapper;
import com.hhwy.pm.qqch.preparation.safe.qqchSafeEnvirRiskList.service.IQqchSafeEnvirRiskListDetailService;
import com.hhwy.pm.qqch.preparation.safe.qqchSafeEnvirRiskList.service.IQqchSafeEnvirRiskListService;
import com.hhwy.pm.qqch.preparation.survey.extend.domain.EnvReport;
import com.hhwy.pm.qqch.preparation.survey.extend.service.IQqchPreparationSurveyExtendService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.pm.qyzs.safe.qyzsSafeEnvRiskProc.domain.QyzsSafeEnvRiskProc;
import com.hhwy.pm.xmsl.project.domain.vo.ProjectBasicInfo;
import com.hhwy.pm.xmsl.project.service.IXmslProjectBasicInfoService;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.objectUtil.ObjectNullUtil;
import com.hhwy.utils.tree.ListTreeUtil;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zq
 * @date 2023-08-14 13:56:17
 * @remark
 */
@Service
public class QqchSafeEnvirRiskListServiceImpl implements IQqchSafeEnvirRiskListService {

    @Autowired
    private QqchSafeEnvirRiskListMapper qqchSafeEnvirRiskListMapper;
    @Autowired
    private IQqchSafeEnvirRiskListDetailService detailService;
    @Autowired
    private QqchSafeEnvirRiskListDetailMapper detailMapper;
    @Autowired
    private IQqchReviewService qqchReviewService;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;
    @Autowired
    private IQqchPreparationSurveyExtendService qqchPreparationSurveyExtendService;
    @Autowired
    private IXmslProjectBasicInfoService xmslProjectBasicInfoService;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    @Autowired
    private ITWbsService tWbsService;

    private static final String TN = "qqch_safe_envir_risk_list";


    public QqchSafeEnvirRiskList getQqchSafeEnvirRiskList(QqchSafeEnvirRiskList qqchSafeEnvirRiskList) {
        return qqchSafeEnvirRiskListMapper.getQqchSafeEnvirRiskList(qqchSafeEnvirRiskList);
    }

    public List<QqchSafeEnvirRiskList> getQqchSafeEnvirRiskListList(QqchSafeEnvirRiskList qqchSafeEnvirRiskList) {
        return qqchSafeEnvirRiskListMapper.getQqchSafeEnvirRiskListList(qqchSafeEnvirRiskList);
    }

    @Transactional
    public int insertQqchSafeEnvirRiskList(QqchSafeEnvirRiskList qqchSafeEnvirRiskList) {
        qqchSafeEnvirRiskList.setId(IdWorker.createId());
        qqchSafeEnvirRiskList.setCreateUser(SecurityUtils.getUserName());
        qqchSafeEnvirRiskList.setCreateTime(DateUtils.getNowDate());
        return qqchSafeEnvirRiskListMapper.insertQqchSafeEnvirRiskList(qqchSafeEnvirRiskList);
    }

    @Transactional
    public int insertQqchSafeEnvirRiskListList(QqchSafeEnvirRiskListVo safeEnvirRiskListVo) {
        String isEdit = safeEnvirRiskListVo.getIsEdit();
        if(!"1".equals(isEdit)){
            return 1;
        }
        QqchSafeEnvirRiskList info = safeEnvirRiskListVo.getQqchSafeEnvirRiskList();
        Long infoId = info.getId();
        if(infoId == null){
            //新增
            infoId = IdWorker.createId();
            info.setId(infoId);
            info.setWbsId(safeEnvirRiskListVo.getWbsId());
            info.setWbsCode(safeEnvirRiskListVo.getWbsCode());
            info.setVersion(safeEnvirRiskListVo.getVersion());
            if (safeEnvirRiskListVo.getVersion().compareTo(BigDecimal.ONE) == 0) {
                info.setValid(Valid.YES);
            }
            info.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            info.setCreateUserName(SecurityUtils.getUserName());
            info.setCreateTime(DateUtils.getNowDate());
            qqchSafeEnvirRiskListMapper.insertQqchSafeEnvirRiskList(info);
        }else {
            //修改
            info.setUpdateUser(String.valueOf(SecurityUtils.getUserId()));
            info.setUpdateTime(DateUtils.getNowDate());
            qqchSafeEnvirRiskListMapper.updateQqchSafeEnvirRiskList(info);
            //删除子表
            detailService.deleteByInfoId(infoId,String.valueOf(SecurityUtils.getUserId()), DateUtils.getNowDate());
        }

        List<QqchSafeEnvirRiskListDetail> detailList = info.getDetailList();
        if(!ObjectNullUtil.isEmpty(detailList)){
            detailList = ListTreeUtil.formatList(
                    detailList,
                    QqchSafeEnvirRiskListDetail::setId,
                    QqchSafeEnvirRiskListDetail::setPid,
                    QqchSafeEnvirRiskListDetail::getChildren,
                    QqchSafeEnvirRiskListDetail::setChildren);
            for (QqchSafeEnvirRiskListDetail safeEnvirRiskListDetail : detailList) {
                safeEnvirRiskListDetail.setInfoId(info.getId());
                safeEnvirRiskListDetail.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                safeEnvirRiskListDetail.setCreateUserName(SecurityUtils.getUserName());
                safeEnvirRiskListDetail.setCreateTime(DateUtils.getNowDate());
            }
        }
        if(!ObjectNullUtil.isEmpty(detailList)){
            detailMapper.insertQqchSafeEnvirRiskListDetailList(detailList);
        }

        String buttonMark = safeEnvirRiskListVo.getButtonMark();
        if (ButtonMark.CONFIRM.equals(buttonMark)) {
            // 插入确认状态
            qqchModuleConfirmCaseService.addConfirmRecord(safeEnvirRiskListVo.getMenuId(), safeEnvirRiskListVo.getStageIdentity());
            //推送环评报告
            this.pushSafeEiaReport(safeEnvirRiskListVo.getVersion());
            this.pushQyzsSafeEnvRiskProc(safeEnvirRiskListVo.getVersion());
        }
        return 1;
    }

    /**
     * 推送环评报告
     * @param version
     */
    public void pushSafeEiaReport(BigDecimal version){
        //获取环评报告
        EnvReport envReport = qqchPreparationSurveyExtendService.getEnvReport(version);
        if(envReport == null){
            return;
        }
        ProjectBasicInfo projectInfo = xmslProjectBasicInfoService.projectInfo();
        Map<String,Object> map = new HashMap<>();
        map.put("projectName",projectInfo.getProjectName());
        map.put("projectId",projectInfo.getProjectId());
        map.put("fileGroupId",envReport.getFileGroupId());
        map.put("uploadUser",envReport.getUploadUser());
        map.put("uploadTime",envReport.getUploadTime());
        rocketMQTemplate.convertAndSend("qyzs_safe_eia_report:tenantSuccess", map);
    }

    /**
     * 推送风险管控数据到总部版
     * @param version
     */
    public void pushQyzsSafeEnvRiskProc(BigDecimal version){
        QqchSafeEnvirRiskList safeRiskList = new QqchSafeEnvirRiskList();
        safeRiskList.setVersion(version);
        List<QqchSafeEnvirRiskList> qqchSafeEnvirRiskListList = qqchSafeEnvirRiskListMapper.getQqchSafeEnvirRiskListList(safeRiskList);
        if(CollectionUtils.isEmpty(qqchSafeEnvirRiskListList)){
            return;
        }
        String projectType = tWbsService.getDefaultEngineeringType();
        if(StringUtils.isBlank(projectType)){
            return;
        }
        List<Long> idList = qqchSafeEnvirRiskListList.stream().map(QqchSafeEnvirRiskList::getId).collect(Collectors.toList());
        List<QqchSafeEnvirRiskListDetail> detailList = detailService.getDetailListByInfoIdList(idList);
        if(CollectionUtils.isEmpty(detailList)){
            return;
        }
        Map<Long, QqchSafeEnvirRiskListDetail> detailMap = detailList.stream().collect(Collectors.toMap(QqchSafeEnvirRiskListDetail::getId, o -> o));
        List<QqchSafeEnvirRiskListDetail> pushList = detailList.stream().filter(o -> !"0".equals(o.getPtVar2())).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(pushList)){
            return;
        }

        //TODO 根据项目wbs获取关联的标准wbs编码

        List<QyzsSafeEnvRiskProc> procList = new ArrayList<>();
        for (QqchSafeEnvirRiskListDetail detail : pushList) {
            QyzsSafeEnvRiskProc proc = new QyzsSafeEnvRiskProc();
            proc.setId(detail.getId());
            proc.setPid(detail.getPid());
            proc.setProcName(detail.getProProcess());
            proc.setEnvEffect(detail.getEnvriReason());
            proc.setOccurrence(detail.getFrequency());
            proc.setIsMajor(detail.getIsMostReason());
            //TODO 措施项
            Long pid = detail.getPid();
            if(pid != null){
                QqchSafeEnvirRiskListDetail parent = detailMap.get(pid);
                if(parent != null && StringUtils.isNotBlank(parent.getPtVar1())){
                    proc.setPid(Long.valueOf(parent.getPtVar1()));
                }
            }
            detail.setPtVar1(String.valueOf(detail.getId()));
            detail.setPtVar2("0");
            procList.add(proc);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("projectType",projectType);
        map.put("procList",procList);
        rocketMQTemplate.convertAndSend("qyzs_safe_env_risk_proc:tenantSuccess", map);
    }

    @Transactional
    public int updateQqchSafeEnvirRiskList(QqchSafeEnvirRiskList qqchSafeEnvirRiskList) {
        qqchSafeEnvirRiskList.setUpdateUser(SecurityUtils.getUserName());
        qqchSafeEnvirRiskList.setUpdateTime(DateUtils.getNowDate());
        return qqchSafeEnvirRiskListMapper.updateQqchSafeEnvirRiskList(qqchSafeEnvirRiskList);
    }

    @Transactional
    public int updateQqchSafeEnvirRiskListList(List<QqchSafeEnvirRiskList> qqchSafeEnvirRiskListList) {
        for (QqchSafeEnvirRiskList qqchSafeEnvirRiskList : qqchSafeEnvirRiskListList) {
            qqchSafeEnvirRiskList.setUpdateUser(SecurityUtils.getUserName());
            qqchSafeEnvirRiskList.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchSafeEnvirRiskListMapper.updateQqchSafeEnvirRiskListList(qqchSafeEnvirRiskListList);
    }

    @Transactional
    public int deleteQqchSafeEnvirRiskList(QqchSafeEnvirRiskList qqchSafeEnvirRiskList) {
        qqchSafeEnvirRiskList.setUpdateUser(SecurityUtils.getUserName());
        qqchSafeEnvirRiskList.setUpdateTime(DateUtils.getNowDate());
        return qqchSafeEnvirRiskListMapper.deleteQqchSafeEnvirRiskList(qqchSafeEnvirRiskList);
    }

    @Transactional
    public int deleteQqchSafeEnvirRiskListByPks(List<Long> qqchSafeEnvirRiskListPkList) {
        return qqchSafeEnvirRiskListMapper.deleteQqchSafeEnvirRiskListByPks(qqchSafeEnvirRiskListPkList);
    }

    @Override
    public QqchSafeEnvirRiskListVo getList(SafeEnvirRiskListQueryVo queryVo) {
        QqchSafeEnvirRiskListVo safeEnvirRiskListVo = new QqchSafeEnvirRiskListVo();
        BigDecimal version = queryVo.getVersion();
        this.checkExistsData(version);
        version = VersionUtil.getVersion(TN,version);

        QqchSafeEnvirRiskList safeRiskList = new QqchSafeEnvirRiskList();
        safeRiskList.setVersion(version);
        safeRiskList.setWbsId(queryVo.getWbsId());
        QqchSafeEnvirRiskList info = qqchSafeEnvirRiskListMapper.getQqchSafeEnvirRiskList(safeRiskList);
        if(info != null){
            Long infoId = info.getId();

            QqchSafeEnvirRiskListDetail safeEnvirRiskListDetail = new QqchSafeEnvirRiskListDetail();
            safeEnvirRiskListDetail.setInfoId(infoId);
            List<QqchSafeEnvirRiskListDetail> detailList = detailService.getQqchSafeEnvirRiskListDetailList(safeEnvirRiskListDetail);

            //转树列表
            List<QqchSafeEnvirRiskListDetail> treeList = ListTreeUtil.formatTree(
                    detailList,
                    o -> o.getPid() == null,
                    (r, n) -> r.getId().equals(n.getPid()),
                    QqchSafeEnvirRiskListDetail::getChildren,
                    QqchSafeEnvirRiskListDetail::setChildren);

            info.setDetailList(treeList);
        }else {
            info = new QqchSafeEnvirRiskList();
        }

        safeEnvirRiskListVo.setQqchSafeEnvirRiskList(info);
        safeEnvirRiskListVo.setVersion(version);
        safeEnvirRiskListVo.setStageIdentity(qqchReviewService.getStage());
        return safeEnvirRiskListVo;
    }

    public void checkExistsData(BigDecimal version){
        if(version == null){
            return;
        }
        boolean exists = CommonServiceUtil.checkExistsByVersion(TN, version);
        if(exists){
            return;
        }
        BigDecimal oldVersion = VersionUtil.getVersion(TN,version);
        if(oldVersion.equals(version)){
            return;
        }

        //查询主子表数据
        QqchSafeEnvirRiskList listQuery = new QqchSafeEnvirRiskList();
        listQuery.setVersion(version);
        List<QqchSafeEnvirRiskList> listList = qqchSafeEnvirRiskListMapper.getQqchSafeEnvirRiskListList(listQuery);
        if(CollectionUtils.isEmpty(listList)){
            return;
        }
        String infoIds = listList.stream().map(o -> o.getId().toString()).collect(Collectors.joining(","));
        List<QqchSafeEnvirRiskListDetail> detailList = detailMapper.getDetailListByInfoIds(infoIds);

        Map<Long, List<QqchSafeEnvirRiskListDetail>> detailMap = null;
        if(!CollectionUtils.isEmpty(detailList)){
            //转树列表
            detailList = ListTreeUtil.formatTree(
                    detailList,
                    o -> o.getPid() == null,
                    (r, n) -> r.getId().equals(n.getPid()),
                    QqchSafeEnvirRiskListDetail::getChildren,
                    QqchSafeEnvirRiskListDetail::setChildren);

            //转线性列表
            detailList = ListTreeUtil.formatList(
                    detailList,
                    QqchSafeEnvirRiskListDetail::setId,
                    QqchSafeEnvirRiskListDetail::setPid,
                    QqchSafeEnvirRiskListDetail::getChildren,
                    QqchSafeEnvirRiskListDetail::setChildren);
            detailMap = detailList.stream().collect(Collectors.groupingBy(QqchSafeEnvirRiskListDetail::getInfoId));
        }

        List<QqchSafeEnvirRiskListDetail> insertList = new ArrayList<>();
        for (QqchSafeEnvirRiskList riskList : listList) {
            Long oldId = riskList.getId();
            Long id = IdWorker.createId();
            riskList.setId(id);
            riskList.setVersion(version);
            riskList.setValid(Valid.NO);
            riskList.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            riskList.setCreateUserName(SecurityUtils.getUserName());
            riskList.setCreateTime(DateUtils.getNowDate());

            if(detailMap != null){
                List<QqchSafeEnvirRiskListDetail> details = detailMap.get(oldId);
                if(!CollectionUtils.isEmpty(details)){
                    for (QqchSafeEnvirRiskListDetail detail : details) {
                        detail.setInfoId(id);
                        detail.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                        detail.setCreateUserName(SecurityUtils.getUserName());
                        detail.setCreateTime(DateUtils.getNowDate());
                    }
                    insertList.addAll(details);
                }
            }
        }

        //插入主表数据
        qqchSafeEnvirRiskListMapper.insertQqchSafeEnvirRiskListList(listList);
        //插入子表数据
        if(!CollectionUtils.isEmpty(insertList)){
            detailMapper.insertQqchSafeEnvirRiskListDetailList(insertList);
        }
    }
}
