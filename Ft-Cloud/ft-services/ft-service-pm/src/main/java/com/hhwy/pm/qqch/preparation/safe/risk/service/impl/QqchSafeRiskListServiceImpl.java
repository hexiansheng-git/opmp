package com.hhwy.pm.qqch.preparation.safe.risk.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.common.service.CommonServiceUtil;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.safe.risk.domain.QqchSafeRiskList;
import com.hhwy.pm.qqch.preparation.safe.risk.domain.QqchSafeRiskListDetail;
import com.hhwy.pm.qqch.preparation.safe.risk.domain.vo.QqchSafeRiskListVo;
import com.hhwy.pm.qqch.preparation.safe.risk.domain.vo.SafeRiskAssembleDataVo;
import com.hhwy.pm.qqch.preparation.safe.risk.domain.vo.SafeRiskListQueryVo;
import com.hhwy.pm.qqch.preparation.safe.risk.mapper.QqchSafeRiskListDetailMapper;
import com.hhwy.pm.qqch.preparation.safe.risk.mapper.QqchSafeRiskListMapper;
import com.hhwy.pm.qqch.preparation.safe.risk.service.IQqchSafeRiskListDetailService;
import com.hhwy.pm.qqch.preparation.safe.risk.service.IQqchSafeRiskListService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.sgch.mainpl.domain.QqchMainPlanItem;
import com.hhwy.pm.qqch.sgch.mainpl.service.IQqchMainPlanItemService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.pm.qyzs.safe.qyzsSafeSafeRisk.domain.QyzsSafeSafeRisk;
import com.hhwy.pm.qyzs.safe.qyzsSafeSafeRisk.service.IQyzsSafeSafeRiskService;
import com.hhwy.pm.xmsl.wbs.WbsRedisUtils;
import com.hhwy.pm.xmsl.wbs.domain.XmslWbs;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.objectUtil.ObjectNullUtil;
import com.hhwy.utils.tree.ListTreeUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author zq
 * @date 2023-08-11 13:41:25
 * @remark
 */
@Service
public class QqchSafeRiskListServiceImpl implements IQqchSafeRiskListService {

    @Autowired
    private QqchSafeRiskListMapper qqchSafeRiskListMapper;
    @Autowired
    private IQqchSafeRiskListDetailService qqchSafeRiskListDetailService;
    @Autowired
    private QqchSafeRiskListDetailMapper qqchSafeRiskListDetailMapper;
    @Autowired
    private IQqchReviewService qqchReviewService;
    @Autowired
    private IQqchMainPlanItemService qqchMainPlanItemService;

    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;

    @Autowired
    private IQyzsSafeSafeRiskService qyzsSafeSafeRiskService;

    private static final String TN = "qqch_safe_risk_list";


    public QqchSafeRiskList getQqchSafeRiskList(QqchSafeRiskList qqchSafeRiskList) {
        return qqchSafeRiskListMapper.getQqchSafeRiskList(qqchSafeRiskList);
    }

    public List<QqchSafeRiskList> getQqchSafeRiskListList(QqchSafeRiskList qqchSafeRiskList) {
        return qqchSafeRiskListMapper.getQqchSafeRiskListList(qqchSafeRiskList);
    }

    @Transactional
    public int insertQqchSafeRiskList(QqchSafeRiskList qqchSafeRiskList) {
        qqchSafeRiskList.setId(IdWorker.createId());
        qqchSafeRiskList.setCreateUser(SecurityUtils.getUserName());
        qqchSafeRiskList.setCreateTime(DateUtils.getNowDate());
        return qqchSafeRiskListMapper.insertQqchSafeRiskList(qqchSafeRiskList);
    }

    @Transactional
    public int insertQqchSafeRiskListList(QqchSafeRiskListVo qqchSafeRiskListVo) {
        String isEdit = qqchSafeRiskListVo.getIsEdit();
        if(!"1".equals(isEdit)){
            return 1;
        }
        QqchSafeRiskList info = qqchSafeRiskListVo.getSafeRiskList();
        Long infoId = info.getId();
        if(infoId == null){
            //新增
            infoId = IdWorker.createId();
            info.setId(infoId);
            info.setWbsId(qqchSafeRiskListVo.getWbsId());
            info.setVersion(qqchSafeRiskListVo.getVersion());
            if (qqchSafeRiskListVo.getVersion().compareTo(BigDecimal.ONE) == 0) {
                info.setValid(Valid.YES);
            }
            info.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            info.setCreateUserName(SecurityUtils.getUserName());
            info.setCreateTime(DateUtils.getNowDate());
            info.setType(qqchSafeRiskListVo.getType());

            qqchSafeRiskListMapper.insertQqchSafeRiskList(info);
        }else {
            //修改
            info.setUpdateUser(String.valueOf(SecurityUtils.getUserId()));
            info.setUpdateTime(DateUtils.getNowDate());
            qqchSafeRiskListMapper.updateQqchSafeRiskList(info);
            //删除子表
            qqchSafeRiskListDetailService.deleteByInfoId(infoId,String.valueOf(SecurityUtils.getUserId()),SecurityUtils.getUserName(), DateUtils.getNowDate());
        }

        List<QqchSafeRiskListDetail> detailList = info.getDetailList();
        if(!ObjectNullUtil.isEmpty(detailList)){
            detailList = ListTreeUtil.formatList(
                    detailList,
                    QqchSafeRiskListDetail::setId,
                    QqchSafeRiskListDetail::setPid,
                    QqchSafeRiskListDetail::setSort,
                    QqchSafeRiskListDetail::getChildren,
                    QqchSafeRiskListDetail::setChildren);
            for (QqchSafeRiskListDetail qqchSafeRiskListDetail : detailList) {
                qqchSafeRiskListDetail.setInfoId(info.getId());
                qqchSafeRiskListDetail.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                qqchSafeRiskListDetail.setCreateUserName(SecurityUtils.getUserName());
                qqchSafeRiskListDetail.setCreateTime(DateUtils.getNowDate());
            }
        }
        if(!ObjectNullUtil.isEmpty(detailList)){
            qqchSafeRiskListDetailMapper.insertQqchSafeRiskListDetailList(detailList);
        }

        String buttonMark = qqchSafeRiskListVo.getButtonMark();
        if (ButtonMark.CONFIRM.equals(buttonMark)) {
            // 插入确认状态
            qqchModuleConfirmCaseService.addConfirmRecord(qqchSafeRiskListVo.getMenuId(), qqchSafeRiskListVo.getStageIdentity());
        }
        return 1;
    }

    @Transactional
    public int updateQqchSafeRiskList(QqchSafeRiskList qqchSafeRiskList) {
        qqchSafeRiskList.setUpdateUser(SecurityUtils.getUserName());
        qqchSafeRiskList.setUpdateTime(DateUtils.getNowDate());
        return qqchSafeRiskListMapper.updateQqchSafeRiskList(qqchSafeRiskList);
    }

    @Transactional
    public int updateQqchSafeRiskListList(List<QqchSafeRiskList> qqchSafeRiskListList) {
        for (QqchSafeRiskList qqchSafeRiskList : qqchSafeRiskListList) {
            qqchSafeRiskList.setUpdateUser(SecurityUtils.getUserName());
            qqchSafeRiskList.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchSafeRiskListMapper.updateQqchSafeRiskListList(qqchSafeRiskListList);
    }

    @Transactional
    public int deleteQqchSafeRiskList(QqchSafeRiskList qqchSafeRiskList) {
        qqchSafeRiskList.setUpdateUser(SecurityUtils.getUserName());
        qqchSafeRiskList.setUpdateTime(DateUtils.getNowDate());
        return qqchSafeRiskListMapper.deleteQqchSafeRiskList(qqchSafeRiskList);
    }

    @Transactional
    public int deleteQqchSafeRiskListByPks(List<Long> qqchSafeRiskListPkList) {
        return qqchSafeRiskListMapper.deleteQqchSafeRiskListByPks(qqchSafeRiskListPkList);
    }

    @Override
    public QqchSafeRiskListVo getList(SafeRiskListQueryVo queryVo) {
        QqchSafeRiskListVo qqchSafeRiskListVo = new QqchSafeRiskListVo();
        BigDecimal version = queryVo.getVersion();
        this.checkExistsData(version,queryVo.getType());
        version = VersionUtil.getVersion(TN,version);

        QqchSafeRiskList qqchSafeRiskList = new QqchSafeRiskList();
        qqchSafeRiskList.setVersion(version);
        qqchSafeRiskList.setType(queryVo.getType());
        qqchSafeRiskList.setWbsId(queryVo.getWbsId());
        QqchSafeRiskList info = qqchSafeRiskListMapper.getQqchSafeRiskList(qqchSafeRiskList);
        if(info != null){
            Long infoId = info.getId();

            QqchSafeRiskListDetail qqchSafeRiskListDetail = new QqchSafeRiskListDetail();
            qqchSafeRiskListDetail.setInfoId(infoId);
            List<QqchSafeRiskListDetail> detailList = qqchSafeRiskListDetailService.getQqchSafeRiskListDetailList(qqchSafeRiskListDetail);

            //转树列表
            List<QqchSafeRiskListDetail> treeList = ListTreeUtil.formatTree(
                    detailList,
                    o -> o.getPid() == null,
                    (r, n) -> r.getId().equals(n.getPid()),
                    QqchSafeRiskListDetail::getChildren,
                    QqchSafeRiskListDetail::setChildren);

            info.setDetailList(treeList);
        }else {
            info = new QqchSafeRiskList();
        }

        //获取p6计划数据
        String wbsCode = queryVo.getWbsCode();
        List<QqchMainPlanItem> mainPlanItemList = qqchMainPlanItemService.getListByItemCodes(wbsCode);
        if(CollectionUtils.isNotEmpty(mainPlanItemList)){
            QqchMainPlanItem qqchMainPlanItem = mainPlanItemList.get(0);
            if(qqchMainPlanItem != null){
                info.setPlanStartDate(qqchMainPlanItem.getStartDate());
                info.setPlanEndDate(qqchMainPlanItem.getFinishDate());
                info.setPlanOverDate(qqchMainPlanItem.getStartDate());
            }
        }

        qqchSafeRiskListVo.setSafeRiskList(info);
        qqchSafeRiskListVo.setVersion(version);
        qqchSafeRiskListVo.setStageIdentity(qqchReviewService.getStage());
        return qqchSafeRiskListVo;
    }

    public void checkExistsData(BigDecimal version, String type){
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
        QqchSafeRiskList qqchSafeRiskList = new QqchSafeRiskList();
        qqchSafeRiskList.setVersion(version);
        qqchSafeRiskList.setType(type);
        List<QqchSafeRiskList> qqchSafeRiskListList = qqchSafeRiskListMapper.getQqchSafeRiskListList(qqchSafeRiskList);
        if(CollectionUtils.isEmpty(qqchSafeRiskListList)){
            return;
        }
        String infoIds = qqchSafeRiskListList.stream().map(o -> o.getId().toString()).collect(Collectors.joining(","));
        List<QqchSafeRiskListDetail> detailList = qqchSafeRiskListDetailMapper.getListByInfoIds(infoIds);
        Map<Long, List<QqchSafeRiskListDetail>> detailMap = null;
        if(CollectionUtils.isNotEmpty(detailList)){
            //转树列表
            detailList = ListTreeUtil.formatTree(
                    detailList,
                    o -> o.getPid() == null,
                    (r, n) -> r.getId().equals(n.getPid()),
                    QqchSafeRiskListDetail::getChildren,
                    QqchSafeRiskListDetail::setChildren);

            //转线性列表
            detailList = ListTreeUtil.formatList(
                    detailList,
                    QqchSafeRiskListDetail::setId,
                    QqchSafeRiskListDetail::setPid,
                    QqchSafeRiskListDetail::setSort,
                    QqchSafeRiskListDetail::getChildren,
                    QqchSafeRiskListDetail::setChildren);
            detailMap = detailList.stream().collect(Collectors.groupingBy(QqchSafeRiskListDetail::getInfoId));
        }

        List<QqchSafeRiskListDetail> insertList = new ArrayList<>();
        for (QqchSafeRiskList safeRiskList : qqchSafeRiskListList) {
            Long oldId = safeRiskList.getId();
            Long id = IdWorker.createId();
            safeRiskList.setId(id);
            safeRiskList.setVersion(version);
            safeRiskList.setValid(Valid.NO);
            safeRiskList.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            safeRiskList.setCreateUserName(SecurityUtils.getUserName());
            safeRiskList.setCreateTime(DateUtils.getNowDate());

            if(detailMap != null){
                List<QqchSafeRiskListDetail> details = detailMap.get(oldId);
                if(CollectionUtils.isNotEmpty(details)){
                    for (QqchSafeRiskListDetail detail : details) {
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
        qqchSafeRiskListMapper.insertQqchSafeRiskListList(qqchSafeRiskListList);
        //插入子表数据
        qqchSafeRiskListDetailMapper.insertQqchSafeRiskListDetailList(insertList);
    }

    @Override
    public List<QqchSafeRiskListDetail> assembleData(SafeRiskAssembleDataVo assembleDataVo) {
        List<QqchSafeRiskListDetail> detailList = assembleDataVo.getDetailList();
        List<QyzsSafeSafeRisk> safeRiskList = assembleDataVo.getSafeSafeRiskList();
        if(CollectionUtils.isEmpty(safeRiskList)){
            return detailList;
        }

        for (QyzsSafeSafeRisk risk : safeRiskList) {
            risk.setChildren(null);
        }
        QyzsSafeSafeRisk query = new QyzsSafeSafeRisk();
        query.setWbsCode(assembleDataVo.getWbsCode());
        List<QyzsSafeSafeRisk> allList = qyzsSafeSafeRiskService.getCommonListBy(query);
        //获取选中数据的父子级集合
        safeRiskList = ListTreeUtil.getRelevancyListBySublist(safeRiskList, allList, QyzsSafeSafeRisk::getId,QyzsSafeSafeRisk::getPid);

        List<QqchSafeRiskListDetail> tempList = new ArrayList<>();
        for (QyzsSafeSafeRisk risk : safeRiskList) {
            QqchSafeRiskListDetail detail = new QqchSafeRiskListDetail();
            detail.setId(risk.getId());
            detail.setPid(risk.getPid());
            detail.setWorkType(risk.getWorkType());
            detail.setWorkUnit(risk.getWorkUnit());
            detail.setDangerThing(risk.getRiskEvent());
            detail.setPossibleResult(risk.getPossibleConsequence());
            detail.setRiskLevel(risk.getRiskLevel());
            detail.setRiskControWay(risk.getRiskControlMeasure());
            detail.setPtVar1(risk.getId().toString());
            detail.setPtVar2("0");
            tempList.add(detail);
        }

        if(CollectionUtils.isEmpty(detailList)){
            //转树列表
            return ListTreeUtil.formatTree(
                    tempList,
                    o -> o.getPid() == null,
                    (r, n) -> r.getId().equals(n.getPid()),
                    QqchSafeRiskListDetail::getChildren,
                    QqchSafeRiskListDetail::setChildren);
        }


        //合并
        detailList = ListTreeUtil.formatList(
                detailList,
                QqchSafeRiskListDetail::setId,
                QqchSafeRiskListDetail::setPid,
                QqchSafeRiskListDetail::getChildren,
                QqchSafeRiskListDetail::setChildren);

        Map<String, QqchSafeRiskListDetail> repositoryMap = detailList.stream().filter(o -> StringUtils.isNotBlank(o.getPtVar1())).collect(Collectors.toMap(QqchSafeRiskListDetail::getPtVar1, o -> o));

        for (QqchSafeRiskListDetail detail : tempList) {
            Long id = detail.getId();
            if(repositoryMap.containsKey(id.toString())){
                continue;
            }
            Long pid = detail.getPid();
            if (pid != null && repositoryMap.containsKey(pid.toString())) {
                QqchSafeRiskListDetail safeRiskListDetail = repositoryMap.get(pid.toString());
                detail.setPid(safeRiskListDetail.getId());
            }
            detailList.add(detail);
        }

        detailList = ListTreeUtil.formatTree(
                detailList,
                o -> o.getPid() == null,
                (r, n) -> r.getId().equals(n.getPid()),
                QqchSafeRiskListDetail::getChildren,
                QqchSafeRiskListDetail::setChildren);
        return detailList;
    }

    @Override
    @Transactional
    public void syncData() {
        //删除所有子表旧数据
        qqchSafeRiskListDetailMapper.deleteAll();

        //插入数据
        List<QyzsSafeSafeRisk> riskList = qyzsSafeSafeRiskService.getCommonListBy(new QyzsSafeSafeRisk());
        if(CollectionUtils.isEmpty(riskList)){
            return;
        }
        List<QyzsSafeSafeRisk> riskMiddleList = riskList.stream().filter(o -> StringUtils.isNotBlank(o.getWbsCode())).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(riskMiddleList)){
            return;
        }
        List<XmslWbs> wbsList = WbsRedisUtils.allWbs();
        if(CollectionUtils.isEmpty(wbsList)){
            return;
        }
        List<XmslWbs> wbsMiddleList = wbsList.stream().filter(o -> o.getHaveChildren() == 0).filter(o -> StringUtils.isNotBlank(o.getStandardCode())).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(wbsMiddleList)){
            return;
        }

        Map<String, List<XmslWbs>> wbsStandardMap = wbsMiddleList.stream().collect(Collectors.groupingBy(XmslWbs::getStandardCode));
        //获取所有主表数据
        BigDecimal version = VersionUtil.getVersion(TN,null);
        Map<String, QqchSafeRiskList> masterMap = this.getMasterMap(version);
        List<QqchSafeRiskList> insterList = new ArrayList<>();

        List<QqchSafeRiskListDetail> detailList = new ArrayList<>();
        Map<String, List<QyzsSafeSafeRisk>> qyzsRiskMap = riskMiddleList.stream().collect(Collectors.groupingBy(QyzsSafeSafeRisk::getWbsCode));
        for (Map.Entry<String, List<QyzsSafeSafeRisk>> entry : qyzsRiskMap.entrySet()) {
            String key = entry.getKey();
            List<QyzsSafeSafeRisk> value = entry.getValue();
            if (!wbsStandardMap.containsKey(key)) {
                continue;
            }
            List<XmslWbs> xmslWbsList = wbsStandardMap.get(key);
            for (XmslWbs wbs : xmslWbsList) {
                String id = wbs.getId();
                QqchSafeRiskList master;
                if (masterMap.containsKey(id)) {
                    master = masterMap.get(id);
                } else {
                    master = new QqchSafeRiskList();
                    master.setId(IdWorker.createId());
                    master.setWbsId(wbs.getId());
                    master.setType("0");
                    master.setVersion(version);
                    insterList.add(master);
                }

                for (QyzsSafeSafeRisk risk : value) {
                    QqchSafeRiskListDetail detail = new QqchSafeRiskListDetail();
                    detail.setId(risk.getId());
                    detail.setPid(risk.getPid());
                    detail.setInfoId(master.getId());
                    detail.setWorkType(risk.getWorkType());
                    detail.setWorkUnit(risk.getWorkUnit());
                    detail.setDangerThing(risk.getRiskEvent());
                    detail.setPossibleResult(risk.getPossibleConsequence());
                    detail.setRiskLevel(risk.getRiskLevel());
                    detail.setRiskControWay(risk.getRiskControlMeasure());
                    detail.setPtVar2("0");
                    detailList.add(detail);
                }
            }
        }

        if(CollectionUtils.isNotEmpty(insterList)){
            qqchSafeRiskListMapper.insertQqchSafeRiskListList(insterList);
        }

        if(CollectionUtils.isNotEmpty(detailList)){
            //转树列表
            List<QqchSafeRiskListDetail> treeList = ListTreeUtil.formatTree(
                    detailList,
                    o -> o.getPid() == null,
                    (r, n) -> r.getId().equals(n.getPid()),
                    QqchSafeRiskListDetail::getChildren,
                    QqchSafeRiskListDetail::setChildren);
            // 树转list
            List<QqchSafeRiskListDetail> insertList = ListTreeUtil.formatList(
                    treeList,
                    QqchSafeRiskListDetail::setId,
                    QqchSafeRiskListDetail::setPid,
                    QqchSafeRiskListDetail::setSort,
                    QqchSafeRiskListDetail::getChildren,
                    QqchSafeRiskListDetail::setChildren);
            qqchSafeRiskListDetailMapper.insertQqchSafeRiskListDetailList(insertList);
        }
    }

    private Map<String, QqchSafeRiskList> getMasterMap(BigDecimal version){
        QqchSafeRiskList query = new QqchSafeRiskList();
        query.setVersion(version);
        query.setType("0");
        List<QqchSafeRiskList> masterList = qqchSafeRiskListMapper.getQqchSafeRiskListList(query);
        Map<String, QqchSafeRiskList> masterMap = masterList.stream().filter(o -> StringUtils.isNotBlank(o.getWbsId())).collect(Collectors.toMap(QqchSafeRiskList::getWbsId, Function.identity()));
        return masterMap;
    }

    @Override
    public void initData() {
        //查询当前是否存在数据
        BigDecimal version = VersionUtil.getVersion(TN,null);
        int count = qqchSafeRiskListMapper.getCount(version,"0");
        if(count > 0){
            return;
        }
        this.syncData();
    }
}
