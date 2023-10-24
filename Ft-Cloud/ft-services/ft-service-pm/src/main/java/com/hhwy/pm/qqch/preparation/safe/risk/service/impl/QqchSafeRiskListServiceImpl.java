package com.hhwy.pm.qqch.preparation.safe.risk.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.safe.risk.domain.QqchSafeRiskList;
import com.hhwy.pm.qqch.preparation.safe.risk.domain.QqchSafeRiskListDetail;
import com.hhwy.pm.qqch.preparation.safe.risk.domain.vo.QqchSafeRiskListVo;
import com.hhwy.pm.qqch.preparation.safe.risk.mapper.QqchSafeRiskListMapper;
import com.hhwy.pm.qqch.preparation.safe.risk.service.IQqchSafeRiskListDetailService;
import com.hhwy.pm.qqch.preparation.safe.risk.service.IQqchSafeRiskListService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.objectUtil.ObjectNullUtil;
import com.hhwy.utils.tree.ListTreeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    private IQqchReviewService qqchReviewService;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;

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
        //清空数据库表中数据
        QqchSafeRiskList qqchSafeRiskList1 = new QqchSafeRiskList();
        qqchSafeRiskList1.setVersion(qqchSafeRiskListVo.getVersion());
        qqchSafeRiskList1.setType(qqchSafeRiskListVo.getType());

        //删除子表
        List<QqchSafeRiskList> infoList = qqchSafeRiskListMapper.getQqchSafeRiskListList(qqchSafeRiskList1);
        if(!ObjectNullUtil.isEmpty(infoList)){
            List<Long> infoIdList = infoList.stream().map(t -> t.getId()).collect(Collectors.toList());
            qqchSafeRiskListDetailService.deleteByInfoIds(infoIdList,String.valueOf(SecurityUtils.getUserId()),SecurityUtils.getUserName(), DateUtils.getNowDate());
        }

        qqchSafeRiskListMapper.deleteQqchSafeRiskList(qqchSafeRiskList1);

        List<QqchSafeRiskList> list = qqchSafeRiskListVo.getList();
        if(!ObjectNullUtil.isEmpty(list)){
            ArrayList<QqchSafeRiskListDetail> addDetailList = new ArrayList<>();
            for (QqchSafeRiskList qqchSafeRiskList : list) {
                qqchSafeRiskList.setId(IdWorker.createId());
                qqchSafeRiskList.setVersion(qqchSafeRiskListVo.getVersion());
                if (qqchSafeRiskListVo.getVersion().compareTo(BigDecimal.ONE) == 0) {
                    qqchSafeRiskList.setValid(Valid.YES);
                }
                qqchSafeRiskList.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                qqchSafeRiskList.setCreateUserName(SecurityUtils.getUserName());
                qqchSafeRiskList.setCreateTime(DateUtils.getNowDate());
                qqchSafeRiskList.setType(qqchSafeRiskListVo.getType());
                List<QqchSafeRiskListDetail> detailList = qqchSafeRiskList.getDetailList();
                if(!ObjectNullUtil.isEmpty(detailList)){
                    detailList = ListTreeUtil.formatList(
                            detailList,
                            QqchSafeRiskListDetail::setId,
                            QqchSafeRiskListDetail::setPid,
                            QqchSafeRiskListDetail::setSort,
                            QqchSafeRiskListDetail::getChildren,
                            QqchSafeRiskListDetail::setChildren);
                    for (QqchSafeRiskListDetail qqchSafeRiskListDetail : detailList) {
                        qqchSafeRiskListDetail.setInfoId(qqchSafeRiskList.getId());
                        qqchSafeRiskListDetail.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                        qqchSafeRiskListDetail.setCreateUserName(SecurityUtils.getUserName());
                        qqchSafeRiskListDetail.setCreateTime(DateUtils.getNowDate());
                        addDetailList.add(qqchSafeRiskListDetail);
                    }
                }
            }
            qqchSafeRiskListMapper.insertQqchSafeRiskListList(list);
            if(!ObjectNullUtil.isEmpty(addDetailList)){
                qqchSafeRiskListDetailService.insertQqchSafeRiskListDetailList(addDetailList);
            }
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
    public QqchSafeRiskListVo getList(QqchSafeRiskListVo qqchSafeRiskListVo) {
        BigDecimal version = qqchSafeRiskListVo.getVersion();
        if(ObjectNullUtil.isEmpty(version)){/*查询当前最大有效版本*/
            version = qqchSafeRiskListMapper.selectMaxVersion(qqchSafeRiskListVo);
        }else{/*查询当前最接近（小于等于）指定版本的版本号*/
            version = qqchSafeRiskListMapper.selectLessOrEqualAssignVersion(qqchSafeRiskListVo);
        }
        QqchSafeRiskList qqchSafeRiskList = new QqchSafeRiskList();
        qqchSafeRiskList.setVersion(version);
        qqchSafeRiskList.setType(qqchSafeRiskListVo.getType());
        List<QqchSafeRiskList> infoList = qqchSafeRiskListMapper.getQqchSafeRiskListList(qqchSafeRiskList);
        if(!ObjectNullUtil.isEmpty(infoList)){
            List<Long> infoIdList = infoList.stream().map(t -> t.getId()).collect(Collectors.toList());
            QqchSafeRiskListDetail qqchSafeRiskListDetail = new QqchSafeRiskListDetail();
            qqchSafeRiskListDetail.setInfoIdList(infoIdList);
            List<QqchSafeRiskListDetail> detailList = qqchSafeRiskListDetailService.getQqchSafeRiskListDetailList(qqchSafeRiskListDetail);
            Map<Long, List<QqchSafeRiskListDetail>> detailListMap = detailList.stream().collect(Collectors.groupingBy(t -> t.getInfoId()));
            for (QqchSafeRiskList safeRiskList : infoList) {
                if(!ObjectNullUtil.isEmpty(detailListMap.get(safeRiskList.getId()))){
                    List<QqchSafeRiskListDetail> qqchSafeEnvirRiskListDetails = detailListMap.get(safeRiskList.getId());
                    List<QqchSafeRiskListDetail> parentList = qqchSafeEnvirRiskListDetails.stream().filter(t -> {
                        if ((t.getPid() == Long.parseLong("0"))) {
                            return true;
                        }
                        return false;
                    }).collect(Collectors.toList());
                    Map<Long, List<QqchSafeRiskListDetail>> groupByPidMap = qqchSafeEnvirRiskListDetails.stream().collect(Collectors.groupingBy(t -> t.getPid()));
                    for (QqchSafeRiskListDetail detail : parentList) {
                        if(!ObjectNullUtil.isEmpty(groupByPidMap.get(detail.getId()))){
                            List<QqchSafeRiskListDetail> childrenList = groupByPidMap.get(detail.getId());
                            detail.setChildren(childrenList);
                        }
                    }
                    safeRiskList.setDetailList(parentList);
                }

            }
        }
        qqchSafeRiskListVo.setList(infoList);
        qqchSafeRiskListVo.setVersion(version);
        qqchSafeRiskListVo.setStageIdentity(qqchReviewService.getStage());
        return qqchSafeRiskListVo;
    }
}
