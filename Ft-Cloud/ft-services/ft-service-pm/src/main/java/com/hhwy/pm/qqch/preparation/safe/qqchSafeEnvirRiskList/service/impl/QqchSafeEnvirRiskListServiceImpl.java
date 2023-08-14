package com.hhwy.pm.qqch.preparation.safe.qqchSafeEnvirRiskList.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.safe.qqchSafeEnvirRiskList.domain.QqchSafeEnvirRiskList;
import com.hhwy.pm.qqch.preparation.safe.qqchSafeEnvirRiskList.domain.QqchSafeEnvirRiskListDetail;
import com.hhwy.pm.qqch.preparation.safe.qqchSafeEnvirRiskList.mapper.QqchSafeEnvirRiskListMapper;
import com.hhwy.pm.qqch.preparation.safe.qqchSafeEnvirRiskList.service.IQqchSafeEnvirRiskListDetailService;
import com.hhwy.pm.qqch.preparation.safe.qqchSafeEnvirRiskList.service.IQqchSafeEnvirRiskListService;
import com.hhwy.pm.qqch.preparation.safe.qqchSafeEnvirRiskList.vo.QqchSafeEnvirRiskListVo;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.core.DateUtil;
import com.hhwy.utils.objectUtil.ObjectNullUtil;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.util.CollectionUtils;

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
    private IQqchReviewService qqchReviewService;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;


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
    public int insertQqchSafeEnvirRiskListList(QqchSafeEnvirRiskListVo voParam) {
        //清空数据库
        QqchSafeEnvirRiskList qqchSafeEnvirRiskList = new QqchSafeEnvirRiskList();
        qqchSafeEnvirRiskList.setVersion(voParam.getVersion());
        qqchSafeEnvirRiskListMapper.deleteQqchSafeEnvirRiskList(qqchSafeEnvirRiskList);
        //删除子表
        List<QqchSafeEnvirRiskList> infoList = qqchSafeEnvirRiskListMapper.getQqchSafeEnvirRiskListList(qqchSafeEnvirRiskList);
        if(!ObjectNullUtil.isEmpty(infoList)){
            List<Long> infoIdList = infoList.stream().map(t -> t.getId()).collect(Collectors.toList());
            detailService.deleteByInfoIds(infoIdList,String.valueOf(SecurityUtils.getUserId()),SecurityUtils.getUserName(), DateUtils.getNowDate());
        }

        if (!CollectionUtils.isEmpty(voParam.getList())) {
            List<QqchSafeEnvirRiskList> list = voParam.getList();

            ArrayList<QqchSafeEnvirRiskListDetail> addDetailList = new ArrayList<>();

            for (QqchSafeEnvirRiskList safeEnvirRiskList : list) {
                safeEnvirRiskList.setId(IdWorker.createId());
                safeEnvirRiskList.setVersion(voParam.getVersion());
                if (voParam.getVersion().compareTo(BigDecimal.ONE) == 0) {
                    safeEnvirRiskList.setValid(Valid.YES);
                }
                safeEnvirRiskList.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                safeEnvirRiskList.setCreateUserName(SecurityUtils.getUserName());
                safeEnvirRiskList.setCreateTime(DateUtils.getNowDate());
                List<QqchSafeEnvirRiskListDetail> detailList = safeEnvirRiskList.getDetailList();
                if(!ObjectNullUtil.isEmpty(detailList)){
                    for (QqchSafeEnvirRiskListDetail detail : detailList) {
                        detail.setId(IdWorker.createId());
                        detail.setInfoId(safeEnvirRiskList.getId());
                        detail.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                        detail.setCreateUserName(SecurityUtils.getUserName());
                        detail.setCreateTime(DateUtils.getNowDate());
                        addDetailList.add(detail);
                    }
                }
            }
            qqchSafeEnvirRiskListMapper.insertQqchSafeEnvirRiskListList(list);
            detailService.insertQqchSafeEnvirRiskListDetailList(addDetailList);
        }
        String buttonMark = voParam.getButtonMark();
        if (ButtonMark.CONFIRM.equals(buttonMark)) {
            // 插入确认状态
            qqchReviewService.updateFinishNum(voParam.getStageIdentity(), voParam.getModuleIdentity());
            qqchModuleConfirmCaseService.addConfirmRecord(voParam.getMenuId(), voParam.getStageIdentity());
        }
        return 1;
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
    public QqchSafeEnvirRiskListVo getList(BigDecimal version) {
        QqchSafeEnvirRiskListVo qqchSafeEnvirRiskListVo = new QqchSafeEnvirRiskListVo();
        version = VersionUtil.getVersion("qqch_safe_envir_risk_list", version);

        QqchSafeEnvirRiskList qqchSafeEnvirRiskList = new QqchSafeEnvirRiskList();
        qqchSafeEnvirRiskList.setVersion(version);
        List<QqchSafeEnvirRiskList> infoList = qqchSafeEnvirRiskListMapper.getQqchSafeEnvirRiskListList(qqchSafeEnvirRiskList);
        if(!ObjectNullUtil.isEmpty(infoList)){
            List<Long> infoIdList = infoList.stream().map(t -> t.getId()).collect(Collectors.toList());
            List<QqchSafeEnvirRiskListDetail> detailList = detailService.getQqchSafeEnvirRiskListDetailListByInfoId(infoIdList);
            if(!ObjectNullUtil.isEmpty(detailList)){
                Map<Long, List<QqchSafeEnvirRiskListDetail>> detailListMap = detailList.stream().collect(Collectors.groupingBy(t -> t.getInfoId()));
                for (QqchSafeEnvirRiskList safeEnvirRiskList : infoList) {
                    if(!ObjectNullUtil.isEmpty(detailListMap.get(safeEnvirRiskList.getId()))){
                        List<QqchSafeEnvirRiskListDetail> qqchSafeEnvirRiskListDetails = detailListMap.get(safeEnvirRiskList.getId());
                        List<QqchSafeEnvirRiskListDetail> parentList = detailList.stream().filter(t->{
                                            if(t.getPid()==0){
                                                return true;
                                            }
                                            return false;
                         }).collect(Collectors.toList());
                        Map<Long, List<QqchSafeEnvirRiskListDetail>> groupByPidMap = qqchSafeEnvirRiskListDetails.stream().collect(Collectors.groupingBy(t -> t.getPid()));
                        for (QqchSafeEnvirRiskListDetail detail : parentList) {
                            if(!ObjectNullUtil.isEmpty(groupByPidMap.get(detail.getId()))){
                                List<QqchSafeEnvirRiskListDetail> childrenList = groupByPidMap.get(detail.getId());
                                detail.setChildrenList(childrenList);
                            }
                        }
                        safeEnvirRiskList.setDetailList(parentList);
                    }
                }
            }
        }
        qqchSafeEnvirRiskListVo.setVersion(version);
        qqchSafeEnvirRiskListVo.setStageIdentity(qqchReviewService.getStage());
        qqchSafeEnvirRiskListVo.setList(infoList);
        return qqchSafeEnvirRiskListVo;
    }
}
