package com.hhwy.pm.qqch.preparation.safe.risk.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.text.Convert;
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
import com.hhwy.utils.objectUtil.ObjectNullUtil;
import com.hhwy.utils.tree.ListTreeUtil;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.util.CollectionUtils;

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
        qqchSafeRiskListMapper.deleteQqchSafeRiskList(qqchSafeRiskList1);
        QqchSafeRiskList riskTemp = qqchSafeRiskListVo.getRiskTemp();
        riskTemp.setType(qqchSafeRiskListVo.getType());
        riskTemp.setVersion(qqchSafeRiskListVo.getVersion());
        riskTemp.setId(IdWorker.createId());
        if (qqchSafeRiskListVo.getVersion().compareTo(BigDecimal.ONE) == 0) {
            riskTemp.setValid(Valid.YES);
        }else{
            riskTemp.setValid(Valid.NO);
        }
        riskTemp.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
        riskTemp.setCreateUserName(SecurityUtils.getUserName());
        riskTemp.setCreateTime(DateUtils.getNowDate());
        qqchSafeRiskListMapper.insertQqchSafeRiskList(riskTemp);
        if (!CollectionUtils.isEmpty(riskTemp.getDetailList())) {
            List<QqchSafeRiskListDetail> detailList = riskTemp.getDetailList();
            for (QqchSafeRiskListDetail qqchSafeRiskListDetail : detailList) {
                qqchSafeRiskListDetail.setId(IdWorker.createId());
                qqchSafeRiskListDetail.setInfoId(riskTemp.getId());
                qqchSafeRiskListDetail.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                qqchSafeRiskListDetail.setCreateUserName(SecurityUtils.getUserName());
                qqchSafeRiskListDetail.setCreateTime(DateUtils.getNowDate());
            }
            qqchSafeRiskListDetailService.insertQqchSafeRiskListDetailList(detailList);
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
        QqchSafeRiskList temp = qqchSafeRiskListMapper.getQqchSafeRiskList(qqchSafeRiskList);
        if(!ObjectNullUtil.isEmpty(temp)){
            QqchSafeRiskListDetail qqchSafeRiskListDetail = new QqchSafeRiskListDetail();
            qqchSafeRiskListDetail.setInfoId(temp.getId());
            List<QqchSafeRiskListDetail> detailList = qqchSafeRiskListDetailService.getQqchSafeRiskListDetailList(qqchSafeRiskListDetail);
            if(!ObjectNullUtil.isEmpty(detailList)){
                List<QqchSafeRiskListDetail> parentList = detailList.stream().filter(t->{
                    if(t.getPid()==0){
                        return true;
                    }
                    return false;
                }).collect(Collectors.toList());
                Map<Long, List<QqchSafeRiskListDetail>> collect = detailList.stream().collect(Collectors.groupingBy(t -> t.getPid()));
                for (QqchSafeRiskListDetail safeRiskListDetail : parentList) {
                    if(!ObjectNullUtil.isEmpty(collect.get(safeRiskListDetail.getId()))){
                        safeRiskListDetail.setChildrenList(collect.get(safeRiskListDetail.getId()));
                    }
                }
                temp.setDetailList(parentList);
            }
        }
        qqchSafeRiskListVo.setRiskTemp(temp);
        qqchSafeRiskListVo.setVersion(version);
        qqchSafeRiskListVo.setStageIdentity(qqchReviewService.getStage());
        return qqchSafeRiskListVo;
    }
}
