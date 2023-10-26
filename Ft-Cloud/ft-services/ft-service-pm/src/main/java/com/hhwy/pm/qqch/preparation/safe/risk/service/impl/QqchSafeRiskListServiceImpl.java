package com.hhwy.pm.qqch.preparation.safe.risk.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.preparation.safe.risk.domain.QqchSafeRiskList;
import com.hhwy.pm.qqch.preparation.safe.risk.domain.QqchSafeRiskListDetail;
import com.hhwy.pm.qqch.preparation.safe.risk.domain.vo.QqchSafeRiskListVo;
import com.hhwy.pm.qqch.preparation.safe.risk.domain.vo.SafeRiskListQueryVo;
import com.hhwy.pm.qqch.preparation.safe.risk.mapper.QqchSafeRiskListDetailMapper;
import com.hhwy.pm.qqch.preparation.safe.risk.mapper.QqchSafeRiskListMapper;
import com.hhwy.pm.qqch.preparation.safe.risk.service.IQqchSafeRiskListDetailService;
import com.hhwy.pm.qqch.preparation.safe.risk.service.IQqchSafeRiskListService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.sgch.mainpl.domain.QqchMainPlanItem;
import com.hhwy.pm.qqch.sgch.mainpl.service.IQqchMainPlanItemService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.objectUtil.ObjectNullUtil;
import com.hhwy.utils.tree.ListTreeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

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
        //清空数据库表中数据
        QqchSafeRiskList delParam = new QqchSafeRiskList();
        delParam.setVersion(qqchSafeRiskListVo.getVersion());
        delParam.setType(qqchSafeRiskListVo.getType());
        delParam.setWbsId(qqchSafeRiskListVo.getWbsId());

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
        version = VersionUtil.getVersion("qqch_safe_risk_list",version);

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
        QqchMainPlanItem qqchMainPlanItem = mainPlanItemList.get(0);
        if(qqchMainPlanItem != null){
            info.setPlanStartDate(qqchMainPlanItem.getStartDate());
            info.setPlanEndDate(qqchMainPlanItem.getFinishDate());
            info.setPlanOverDate(qqchMainPlanItem.getStartDate());
        }

        qqchSafeRiskListVo.setSafeRiskList(info);
        qqchSafeRiskListVo.setVersion(version);
        qqchSafeRiskListVo.setStageIdentity(qqchReviewService.getStage());
        return qqchSafeRiskListVo;
    }
}
