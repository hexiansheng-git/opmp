package com.hhwy.pm.qqch.preparation.safe.qqchSafeEnvirRiskList.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
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
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
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
    public QqchSafeEnvirRiskListVo getList(SafeEnvirRiskListQueryVo queryVo) {
        QqchSafeEnvirRiskListVo safeEnvirRiskListVo = new QqchSafeEnvirRiskListVo();
        BigDecimal version = queryVo.getVersion();
        version = VersionUtil.getVersion("qqch_safe_envir_risk_list",version);

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
}
