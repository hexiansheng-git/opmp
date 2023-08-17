package com.hhwy.pm.qqch.qqchPerformInspection.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSON;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.web.domain.BaseEntity;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.qqchPerformInspection.domain.QqchPerformInspectionDetail;
import com.hhwy.pm.qqch.qqchPerformInspection.service.IQqchPerformInspectionDetailService;
import com.hhwy.pm.qqch.qqchWorkPlan.domain.QqchWorkPlan;
import com.hhwy.pm.qqch.qqchWorkPlan.domain.QqchWorkPlanDetail;
import com.hhwy.pm.qqch.qqchWorkPlan.service.IQqchWorkPlanService;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.objectUtil.ObjectNullUtil;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.pm.qqch.qqchPerformInspection.mapper.QqchPerformInspectionMapper;
import com.hhwy.pm.qqch.qqchPerformInspection.service.IQqchPerformInspectionService;
import com.hhwy.pm.qqch.qqchPerformInspection.domain.QqchPerformInspection;
import com.hhwy.utils.idworker.IdWorker;

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


    public QqchPerformInspection getQqchPerformInspection(QqchPerformInspection qqchPerformInspection) {
        return qqchPerformInspectionMapper.getQqchPerformInspection(qqchPerformInspection);
    }

    public List<QqchPerformInspection> getQqchPerformInspectionList(QqchPerformInspection qqchPerformInspection) {
        return qqchPerformInspectionMapper.getQqchPerformInspectionList(qqchPerformInspection);
    }

    @Transactional
    public int insertQqchPerformInspection(QqchPerformInspection qqchPerformInspection) {
        qqchPerformInspection.setId(IdWorker.createId());
        qqchPerformInspection.setCreateUser(SecurityUtils.getUserName());
        qqchPerformInspection.setCreateUserName(SecurityUtils.getSysUser().getNickName());
        qqchPerformInspection.setCreateTime(DateUtils.getNowDate());
        qqchPerformInspection.setDeptId(SecurityUtils.getSysUser().getDeptId());
        List<QqchPerformInspectionDetail> detailList = qqchPerformInspection.getDetailList();
        if(ObjectNullUtil.isEmpty(detailList)){
            throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error,"策划项信息不可为空");
        }
        List<QqchPerformInspectionDetail> batchAddList = handleDetailList(qqchPerformInspection, detailList);
        qqchPerformInspectionMapper.insertQqchPerformInspection(qqchPerformInspection);
        detailService.insertQqchPerformInspectionDetailList(batchAddList);
        return 1;
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
        List<QqchPerformInspectionDetail> detailList = qqchPerformInspection.getDetailList();
        if(ObjectNullUtil.isEmpty(detailList)){
            throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error,"策划项信息不可为空");
        }
        List<QqchPerformInspectionDetail> batchAddList = handleDetailList(qqchPerformInspection, detailList);
        //先删除旧的
        QqchPerformInspectionDetail detail = new QqchPerformInspectionDetail();
        detail.setInfoId(qqchPerformInspection.getId());
        detailService.deleteQqchPerformInspectionDetail(detail);
        //再添加新的
        detailService.insertQqchPerformInspectionDetailList(batchAddList);
        //修改主表
        return qqchPerformInspectionMapper.updateQqchPerformInspection(qqchPerformInspection);
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
        qqchPerformInspection.setUpdateUser(SecurityUtils.getUserName());
        qqchPerformInspection.setUpdateTime(DateUtils.getNowDate());
        return qqchPerformInspectionMapper.deleteQqchPerformInspection(qqchPerformInspection);
    }

    @Transactional
    public int deleteQqchPerformInspectionByPks(List<Long> qqchPerformInspectionPkList) {
        return qqchPerformInspectionMapper.deleteQqchPerformInspectionByPks(qqchPerformInspectionPkList);
    }

    @Override
    public List<QqchPerformInspectionDetail> getChEditMenuList() {
        HashMap<String, String> map = new HashMap<>();
        map.put("type","1");
        BaseEntity baseEntity = workPlanService.baseInfo(map);
        QqchWorkPlan qqchWorkPlan = JSON.parseObject(JSON.toJSONString(baseEntity), QqchWorkPlan.class);
        List<QqchWorkPlanDetail> detailList = qqchWorkPlan.getDetailList();

        ArrayList<QqchPerformInspectionDetail> returnList = new ArrayList<>();
        for (QqchWorkPlanDetail qqchWorkPlanDetail : detailList) {
            QqchPerformInspectionDetail detail = new QqchPerformInspectionDetail();
            detail.setId(qqchWorkPlanDetail.getId());
            detail.setItemName(qqchWorkPlanDetail.getItemName());
            detail.setItemId(qqchWorkPlanDetail.getItemId());
            detail.setSort(qqchWorkPlanDetail.getSort());
            detail.setWorkExplain(qqchWorkPlanDetail.getWorkExplain());
            List<QqchPerformInspectionDetail> childrenList = new ArrayList<>();
            List<QqchWorkPlanDetail> children = qqchWorkPlanDetail.getChildren();
            if(!ObjectNullUtil.isEmpty(children)){
                for (QqchWorkPlanDetail child : children) {
                    QqchPerformInspectionDetail childDetail = new QqchPerformInspectionDetail();
                    childDetail.setId(child.getId());
                    childDetail.setPid(child.getPid());
                    childDetail.setItemName(child.getItemName());
                    childDetail.setItemId(child.getItemId());
                    childDetail.setSort(child.getSort());
                    childDetail.setWorkExplain(child.getWorkExplain());
                    childrenList.add(childDetail);
                }
            }
            detail.setChildrenList(childrenList);
            returnList.add(detail);
        }
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
                qqchPerformInspectionDetail.setChildrenList(detailList1);
            }
            qqchPerformInspection.setDetailList(parentList);
        }

        return qqchPerformInspection;
    }

    //处理策划项数据
    private List<QqchPerformInspectionDetail> handleDetailList(QqchPerformInspection qqchPerformInspection,List<QqchPerformInspectionDetail> detailList){
        ArrayList<QqchPerformInspectionDetail> batchAddList = new ArrayList<>();
        for (QqchPerformInspectionDetail detail : detailList) {
            detail.setInfoId(qqchPerformInspection.getId());
            detail.setId(IdWorker.createId());
            List<QqchPerformInspectionDetail> childrenList = detail.getChildrenList();
            if(!ObjectNullUtil.isEmpty(childrenList)){
                for (QqchPerformInspectionDetail qqchPerformInspectionDetail : childrenList) {
                    QqchPerformInspectionDetail detail1 = new QqchPerformInspectionDetail();
                    detail1.setId(IdWorker.createId());
                    detail1.setPid(detail.getId());
                    detail1.setInfoId(qqchPerformInspection.getId());
                    detail1.setItemId(qqchPerformInspectionDetail.getItemId());
                    detail1.setItemName(qqchPerformInspectionDetail.getItemName());
                    detail1.setSort(qqchPerformInspectionDetail.getSort());
                    detail1.setWorkExplain(qqchPerformInspectionDetail.getWorkExplain());
                    detail1.setEditor(qqchPerformInspectionDetail.getEditor());
                    detail1.setPerformInspection(qqchPerformInspectionDetail.getPerformInspection());
                    detail1.setInspectionPerson(qqchPerformInspectionDetail.getInspectionPerson());
                    detail1.setInspectionPersonName(qqchPerformInspectionDetail.getInspectionPersonName());
                    detail1.setRemark(qqchPerformInspectionDetail.getRemark());
                    batchAddList.add(detail1);
                }
            }
            batchAddList.add(detail);
        }
        return batchAddList;
    }
}
