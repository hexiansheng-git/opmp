package com.hhwy.pm.qqch.qqchPerformInspection.service.impl;

import com.hhwy.common.core.exception.CustomException;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.enums.FlowEnum;
import com.hhwy.pm.common.FlowInfoSearchUtil;
import com.hhwy.pm.core.sync.service.ISysSyncInfoService;
import com.hhwy.pm.qqch.qqchPerformInspection.domain.QqchPerformInspection;
import com.hhwy.pm.qqch.qqchPerformInspection.domain.QqchPerformInspectionDetail;
import com.hhwy.pm.qqch.qqchPerformInspection.mapper.QqchPerformInspectionMapper;
import com.hhwy.pm.qqch.qqchPerformInspection.service.IQqchPerformInspectionDetailService;
import com.hhwy.pm.qqch.qqchPerformInspection.service.IQqchPerformInspectionService;
import com.hhwy.pm.qqch.qqchWorkPlan.domain.QqchWorkPlan;
import com.hhwy.pm.qqch.qqchWorkPlan.domain.QqchWorkPlanDetail;
import com.hhwy.pm.qqch.qqchWorkPlan.service.IQqchWorkPlanService;
import com.hhwy.pm.xmsl.project.domain.vo.ProjectInfoWithOther;
import com.hhwy.pm.xmsl.project.service.IXmslProjectBasicInfoService;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.objectUtil.ObjectNullUtil;
import com.hhwy.utils.tree.ListTreeUtil;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zqq
 * @date 2023-08-17 10:58:09
 * @remark
 */
@Service
public class QqchPerformInspectionServiceImpl implements IQqchPerformInspectionService {

    @Autowired
    private QqchPerformInspectionMapper qqchPerformInspectionMapper;
    @Autowired
    private IQqchPerformInspectionDetailService detailService;
    @Autowired
    private IQqchWorkPlanService workPlanService;
    @Autowired
    private ISysSyncInfoService sysSyncInfoService;
    @Autowired
    private IXmslProjectBasicInfoService projectBasicInfoService;
    @Autowired
    RocketMQTemplate rocketMQTemplate;

    public QqchPerformInspection getQqchPerformInspection(QqchPerformInspection qqchPerformInspection) {
        QqchPerformInspection inspection = qqchPerformInspectionMapper.getQqchPerformInspection(qqchPerformInspection);
        List<QqchPerformInspection> list = new ArrayList<>();
        list.add(inspection);
        FlowInfoSearchUtil.getFlowInfo(list, FlowEnum.QQCH_ZXJC);
        return inspection;
    }

    public List<QqchPerformInspection> getQqchPerformInspectionList(QqchPerformInspection qqchPerformInspection) {
        List<QqchPerformInspection> qqchPerformInspectionList = qqchPerformInspectionMapper.getQqchPerformInspectionList(qqchPerformInspection);
        FlowInfoSearchUtil.getFlowInfo(qqchPerformInspectionList, FlowEnum.QQCH_ZXJC);
        return qqchPerformInspectionList;
    }

    @Transactional
    public Long insertQqchPerformInspection(QqchPerformInspection qqchPerformInspection) {
        ProjectInfoWithOther projectInfoWithOther = projectBasicInfoService.getProjectInfoWithOther();
        String planEstablishDirector = projectInfoWithOther.getPlanEstablishDirector();
        qqchPerformInspection.setPtVar1(planEstablishDirector);
        qqchPerformInspection.setId(IdWorker.createId());
        qqchPerformInspection.setCreateUser(SecurityUtils.getUserName());
        qqchPerformInspection.setCreateUserName(SecurityUtils.getSysUser().getNickName());
        qqchPerformInspection.setCreateTime(DateUtils.getNowDate());
        this.setInitiator(qqchPerformInspection);
        qqchPerformInspection.setDeptId(SecurityUtils.getSysUser().getDeptId());
        List<QqchPerformInspectionDetail> detailList = qqchPerformInspection.getDetailList();
        if(ObjectNullUtil.isEmpty(detailList)){
            throw new CustomException("策划项信息不可为空");
        }
        List<QqchPerformInspectionDetail> batchAddList = handleDetailList(qqchPerformInspection, detailList);
        qqchPerformInspectionMapper.insertQqchPerformInspection(qqchPerformInspection);
        detailService.insertQqchPerformInspectionDetailList(batchAddList);

        //推送数据到总部
        sysSyncInfoService.pushQqchPerformInspection(qqchPerformInspection);
        return qqchPerformInspection.getId();
    }

    @Transactional
    public int insertQqchPerformInspectionList(List<QqchPerformInspection> qqchPerformInspectionList) {
        for (QqchPerformInspection qqchPerformInspection : qqchPerformInspectionList) {
            qqchPerformInspection.setId(IdWorker.createId());
            qqchPerformInspection.setCreateUser(SecurityUtils.getUserName());
            qqchPerformInspection.setCreateTime(DateUtils.getNowDate());
        }
        return qqchPerformInspectionMapper.insertQqchPerformInspectionList(qqchPerformInspectionList);
    }

    @Transactional
    public int updateQqchPerformInspection(QqchPerformInspection qqchPerformInspection) {
        qqchPerformInspection.setUpdateUser(SecurityUtils.getUserName());
        qqchPerformInspection.setUpdateTime(DateUtils.getNowDate());
        this.setInitiator(qqchPerformInspection);
        List<QqchPerformInspectionDetail> detailList = qqchPerformInspection.getDetailList();
        if(ObjectNullUtil.isEmpty(detailList)){
            throw new CustomException("策划项信息不可为空");
        }
        List<QqchPerformInspectionDetail> batchAddList = handleDetailList(qqchPerformInspection, detailList);
        //先删除旧的
        QqchPerformInspectionDetail detail = new QqchPerformInspectionDetail();
        detail.setInfoId(qqchPerformInspection.getId());
        detailService.deleteQqchPerformInspectionDetail(detail);
        //再添加新的
        detailService.insertQqchPerformInspectionDetailList(batchAddList);
        //修改主表
        int result = qqchPerformInspectionMapper.updateQqchPerformInspection(qqchPerformInspection);
        //推送数据到总部
        sysSyncInfoService.pushQqchPerformInspection(qqchPerformInspection);
        return result;
    }

    /**
     * 设置发起人
     * @param qqchPerformInspection
     */
    public void setInitiator(QqchPerformInspection qqchPerformInspection){
        String ptVar5 = qqchPerformInspection.getPtVar5();
        if("1".equals(ptVar5)){
            qqchPerformInspection.setPtVar2(SecurityUtils.getSysUser().getNickName());
        }
    }

    @Transactional
    public int updateQqchPerformInspectionList(List<QqchPerformInspection> qqchPerformInspectionList) {
        for (QqchPerformInspection qqchPerformInspection : qqchPerformInspectionList) {
            qqchPerformInspection.setUpdateUser(SecurityUtils.getUserName());
            qqchPerformInspection.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchPerformInspectionMapper.updateQqchPerformInspectionList(qqchPerformInspectionList);
    }

    @Transactional
    public int deleteQqchPerformInspection(QqchPerformInspection qqchPerformInspection) {
        //流程已完成的不可删除
        //暂时未加流程
        qqchPerformInspection.setDelUser(SecurityUtils.getUserName());
        qqchPerformInspection.setDelTime(DateUtils.getNowDate());
        int result = qqchPerformInspectionMapper.deleteQqchPerformInspection(qqchPerformInspection);
        //推送总部
        rocketMQTemplate.convertAndSend("qqch_performInspection:delete", qqchPerformInspection.getId()+"");
        return result;
    }

    @Transactional
    public int deleteQqchPerformInspectionByPks(List<Long> qqchPerformInspectionPkList) {
        return qqchPerformInspectionMapper.deleteQqchPerformInspectionByPks(qqchPerformInspectionPkList);
    }

    @Override
    public List<QqchPerformInspectionDetail> getChEditMenuList() {
        HashMap<String, String> map = new HashMap<>();
        map.put("type","1");
        QqchWorkPlan qqchWorkPlan = workPlanService.baseInfo(map);
        List<QqchWorkPlanDetail> detailList = qqchWorkPlan.getDetailList();

        detailList = ListTreeUtil.formatList(
                detailList,
                QqchWorkPlanDetail::setId,
                QqchWorkPlanDetail::setPid,
                QqchWorkPlanDetail::setSort,
                QqchWorkPlanDetail::getChildren,
                QqchWorkPlanDetail::setChildren);

        List<QqchPerformInspectionDetail> returnList = new ArrayList<>();
        for (QqchWorkPlanDetail qqchWorkPlanDetail : detailList) {
            QqchPerformInspectionDetail detail = new QqchPerformInspectionDetail();
            detail.setId(qqchWorkPlanDetail.getId());
            detail.setPid(qqchWorkPlanDetail.getPid());
            detail.setItemName(qqchWorkPlanDetail.getItemName());
            detail.setItemId(qqchWorkPlanDetail.getItemId());
            detail.setSort(qqchWorkPlanDetail.getSort());
            detail.setWorkExplain(qqchWorkPlanDetail.getWorkExplain());
            returnList.add(detail);
        }

        returnList = ListTreeUtil.formatTree(
                returnList,
                o -> o.getPid() == null,
                (r, n) -> r.getId().equals(n.getPid()),
                QqchPerformInspectionDetail::getChildren,
                QqchPerformInspectionDetail::setChildren);

        return returnList;
    }

    @Override
    public QqchPerformInspection detail(Long id) {
        QqchPerformInspection qqchPerformInspection = new QqchPerformInspection();
        qqchPerformInspection.setId(id);
        QqchPerformInspection temp = getQqchPerformInspection(qqchPerformInspection);

        QqchPerformInspectionDetail detail = new QqchPerformInspectionDetail();
        detail.setInfoId(id);
        List<QqchPerformInspectionDetail> detailList = detailService.getQqchPerformInspectionDetailList(detail);
        if(!ObjectNullUtil.isEmpty(detailList)){
            List<QqchPerformInspectionDetail> parentList = detailList.stream().filter(t -> ObjectNullUtil.isEmpty(t.getPid())).collect(Collectors.toList());
            Map<Long, List<QqchPerformInspectionDetail>> pidMap = detailList.stream().filter(t -> !ObjectNullUtil.isEmpty(t.getPid())).collect(Collectors.groupingBy(t -> t.getPid()));
            for (QqchPerformInspectionDetail qqchPerformInspectionDetail : parentList) {
                List<QqchPerformInspectionDetail> detailList1 = pidMap.get(qqchPerformInspectionDetail.getId());
                qqchPerformInspectionDetail.setChildren(detailList1);
            }
            temp.setDetailList(parentList);
        }

        return temp;
    }

    //处理策划项数据
    private List<QqchPerformInspectionDetail> handleDetailList(QqchPerformInspection qqchPerformInspection,List<QqchPerformInspectionDetail> detailList){
        List<QqchPerformInspectionDetail> tileList = ListTreeUtil.formatList(
                detailList,
                QqchPerformInspectionDetail::setId,
                QqchPerformInspectionDetail::setPid,
                QqchPerformInspectionDetail::setSort,
                QqchPerformInspectionDetail::getChildren,
                QqchPerformInspectionDetail::setChildren);
        for (QqchPerformInspectionDetail detail : tileList) {
            detail.setInfoId(qqchPerformInspection.getId());
        }
        return tileList;
    }

    
}
