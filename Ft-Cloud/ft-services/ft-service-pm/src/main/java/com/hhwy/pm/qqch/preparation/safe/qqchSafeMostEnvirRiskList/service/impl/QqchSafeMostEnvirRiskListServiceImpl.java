package com.hhwy.pm.qqch.preparation.safe.qqchSafeMostEnvirRiskList.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.safe.qqchSafeMostEnvirRiskList.domain.QqchSafeMostEnvirRiskList;
import com.hhwy.pm.qqch.preparation.safe.qqchSafeMostEnvirRiskList.domain.QqchSafeMostEnvirRiskListDetail;
import com.hhwy.pm.qqch.preparation.safe.qqchSafeMostEnvirRiskList.domain.vo.QqchSafeMostEnvirRiskListVo;
import com.hhwy.pm.qqch.preparation.safe.qqchSafeMostEnvirRiskList.domain.vo.SafeMostEnvirRiskListQueryVo;
import com.hhwy.pm.qqch.preparation.safe.qqchSafeMostEnvirRiskList.mapper.QqchSafeMostEnvirRiskListDetailMapper;
import com.hhwy.pm.qqch.preparation.safe.qqchSafeMostEnvirRiskList.mapper.QqchSafeMostEnvirRiskListMapper;
import com.hhwy.pm.qqch.preparation.safe.qqchSafeMostEnvirRiskList.service.IQqchSafeMostEnvirRiskListDetailService;
import com.hhwy.pm.qqch.preparation.safe.qqchSafeMostEnvirRiskList.service.IQqchSafeMostEnvirRiskListService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.objectUtil.ObjectNullUtil;
import com.hhwy.utils.validation.JyDetailsUtil;
import com.hhwy.utils.validation.ValidationGroups;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author zq
 * @date 2023-08-14 14:04:02
 * @remark
 */
@Service
public class QqchSafeMostEnvirRiskListServiceImpl implements IQqchSafeMostEnvirRiskListService {
    private static final Logger log = LoggerFactory.getLogger(QqchSafeMostEnvirRiskListServiceImpl.class);

    @Autowired
    private QqchSafeMostEnvirRiskListMapper qqchSafeMostEnvirRiskListMapper;
    @Autowired
    private IQqchSafeMostEnvirRiskListDetailService detailService;
    @Autowired
    private QqchSafeMostEnvirRiskListDetailMapper detailMapper;
    @Autowired
    private IQqchReviewService qqchReviewService;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;

    public QqchSafeMostEnvirRiskList getQqchSafeMostEnvirRiskList(QqchSafeMostEnvirRiskList qqchSafeMostEnvirRiskList) {
        return qqchSafeMostEnvirRiskListMapper.getQqchSafeMostEnvirRiskList(qqchSafeMostEnvirRiskList);
    }

    public List<QqchSafeMostEnvirRiskList> getQqchSafeMostEnvirRiskListList(QqchSafeMostEnvirRiskList qqchSafeMostEnvirRiskList) {
        return qqchSafeMostEnvirRiskListMapper.getQqchSafeMostEnvirRiskListList(qqchSafeMostEnvirRiskList);
    }

    @Transactional
    public int insertQqchSafeMostEnvirRiskList(QqchSafeMostEnvirRiskList qqchSafeMostEnvirRiskList) {
        qqchSafeMostEnvirRiskList.setId(IdWorker.createId());
        qqchSafeMostEnvirRiskList.setCreateUser(SecurityUtils.getUserName());
        qqchSafeMostEnvirRiskList.setCreateTime(DateUtils.getNowDate());
        return qqchSafeMostEnvirRiskListMapper.insertQqchSafeMostEnvirRiskList(qqchSafeMostEnvirRiskList);
    }

    @Transactional
    public int insertQqchSafeMostEnvirRiskListList(QqchSafeMostEnvirRiskListVo mostEnvirRiskListVo) {
        String isEdit = mostEnvirRiskListVo.getIsEdit();
        if(!"1".equals(isEdit)){
            return 1;
        }
        QqchSafeMostEnvirRiskList info = mostEnvirRiskListVo.getQqchSafeMostEnvirRiskList();
        Long infoId = info.getId();
        if(infoId == null){
            //新增
            infoId = IdWorker.createId();
            info.setId(infoId);
            info.setWbsId(mostEnvirRiskListVo.getWbsId());
            info.setWbsCode(mostEnvirRiskListVo.getWbsCode());
            info.setVersion(mostEnvirRiskListVo.getVersion());
            if (mostEnvirRiskListVo.getVersion().compareTo(BigDecimal.ONE) == 0) {
                info.setValid(Valid.YES);
            }
            info.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            info.setCreateUserName(SecurityUtils.getUserName());
            info.setCreateTime(DateUtils.getNowDate());
            qqchSafeMostEnvirRiskListMapper.insertQqchSafeMostEnvirRiskList(info);
        }else {
            //修改
            info.setUpdateUser(String.valueOf(SecurityUtils.getUserId()));
            info.setUpdateTime(DateUtils.getNowDate());
            qqchSafeMostEnvirRiskListMapper.updateQqchSafeMostEnvirRiskList(info);
            //删除子表
            detailService.deleteByInfoId(infoId,String.valueOf(SecurityUtils.getUserId()), DateUtils.getNowDate());
        }

        String buttonMark = mostEnvirRiskListVo.getButtonMark();

        List<QqchSafeMostEnvirRiskListDetail> detailList = info.getDetailList();
        //校验必填
        if("1".equals(buttonMark) || "2".equals(buttonMark)){
            JyDetailsUtil.jyDetails(detailList,ValidationGroups.Save.class);
        }
        for (QqchSafeMostEnvirRiskListDetail mostEnvirRiskListDetail : detailList) {
            mostEnvirRiskListDetail.setId(IdWorker.createId());
            mostEnvirRiskListDetail.setInfoId(info.getId());
            mostEnvirRiskListDetail.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            mostEnvirRiskListDetail.setCreateUserName(SecurityUtils.getUserName());
            mostEnvirRiskListDetail.setCreateTime(DateUtils.getNowDate());
        }
        if(!ObjectNullUtil.isEmpty(detailList)){
            detailMapper.insertQqchSafeMostEnvirRiskListDetailList(detailList);
        }

        if (ButtonMark.CONFIRM.equals(buttonMark)) {
            // 插入确认状态
            qqchModuleConfirmCaseService.addConfirmRecord(mostEnvirRiskListVo.getMenuId(), mostEnvirRiskListVo.getStageIdentity());
        }
        return 1;
    }

    @Transactional
    public int updateQqchSafeMostEnvirRiskList(QqchSafeMostEnvirRiskList qqchSafeMostEnvirRiskList) {
        qqchSafeMostEnvirRiskList.setUpdateUser(SecurityUtils.getUserName());
        qqchSafeMostEnvirRiskList.setUpdateTime(DateUtils.getNowDate());
        return qqchSafeMostEnvirRiskListMapper.updateQqchSafeMostEnvirRiskList(qqchSafeMostEnvirRiskList);
    }

    @Transactional
    public int updateQqchSafeMostEnvirRiskListList(List<QqchSafeMostEnvirRiskList> qqchSafeMostEnvirRiskListList) {
        for (QqchSafeMostEnvirRiskList qqchSafeMostEnvirRiskList : qqchSafeMostEnvirRiskListList) {
            qqchSafeMostEnvirRiskList.setUpdateUser(SecurityUtils.getUserName());
            qqchSafeMostEnvirRiskList.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchSafeMostEnvirRiskListMapper.updateQqchSafeMostEnvirRiskListList(qqchSafeMostEnvirRiskListList);
    }

    @Transactional
    public int deleteQqchSafeMostEnvirRiskList(QqchSafeMostEnvirRiskList qqchSafeMostEnvirRiskList) {
        qqchSafeMostEnvirRiskList.setUpdateUser(SecurityUtils.getUserName());
        qqchSafeMostEnvirRiskList.setUpdateTime(DateUtils.getNowDate());
        return qqchSafeMostEnvirRiskListMapper.deleteQqchSafeMostEnvirRiskList(qqchSafeMostEnvirRiskList);
    }

    @Transactional
    public int deleteQqchSafeMostEnvirRiskListByPks(List<Long> qqchSafeMostEnvirRiskListPkList) {
        return qqchSafeMostEnvirRiskListMapper.deleteQqchSafeMostEnvirRiskListByPks(qqchSafeMostEnvirRiskListPkList);
    }

    @Override
    public QqchSafeMostEnvirRiskListVo getList(SafeMostEnvirRiskListQueryVo queryVo) {
        QqchSafeMostEnvirRiskListVo safeMostEnvirRiskListVo = new QqchSafeMostEnvirRiskListVo();
        BigDecimal version = queryVo.getVersion();
        version = VersionUtil.getVersion("qqch_safe_most_envir_risk_list",version);

        QqchSafeMostEnvirRiskList safeMostEnvirRiskList = new QqchSafeMostEnvirRiskList();
        safeMostEnvirRiskList.setVersion(version);
        safeMostEnvirRiskList.setWbsId(queryVo.getWbsId());
        QqchSafeMostEnvirRiskList info = qqchSafeMostEnvirRiskListMapper.getQqchSafeMostEnvirRiskList(safeMostEnvirRiskList);
        if(info != null){
            Long infoId = info.getId();

            QqchSafeMostEnvirRiskListDetail safeMostEnvirRiskListDetail = new QqchSafeMostEnvirRiskListDetail();
            safeMostEnvirRiskListDetail.setInfoId(infoId);
            List<QqchSafeMostEnvirRiskListDetail> detailList = detailService.getQqchSafeMostEnvirRiskListDetailList(safeMostEnvirRiskListDetail);

            info.setDetailList(detailList);
        }else {
            info = new QqchSafeMostEnvirRiskList();
        }

        safeMostEnvirRiskListVo.setQqchSafeMostEnvirRiskList(info);
        safeMostEnvirRiskListVo.setVersion(version);
        safeMostEnvirRiskListVo.setStageIdentity(qqchReviewService.getStage());
        return safeMostEnvirRiskListVo;
    }
}
