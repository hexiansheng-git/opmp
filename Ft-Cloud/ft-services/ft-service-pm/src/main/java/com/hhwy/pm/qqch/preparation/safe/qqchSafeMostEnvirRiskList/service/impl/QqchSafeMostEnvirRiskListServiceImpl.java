package com.hhwy.pm.qqch.preparation.safe.qqchSafeMostEnvirRiskList.service.impl;

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
import com.hhwy.pm.qqch.preparation.safe.qqchSafeMostEnvirRiskList.domain.QqchSafeMostEnvirRiskListDetail;
import com.hhwy.pm.qqch.preparation.safe.qqchSafeMostEnvirRiskList.service.IQqchSafeMostEnvirRiskListDetailService;
import com.hhwy.pm.qqch.preparation.safe.qqchSafeMostEnvirRiskList.vo.QqchSafeMostEnvirRiskListVo;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.objectUtil.ObjectNullUtil;
import com.hhwy.utils.validation.BeanValidationResult;
import com.hhwy.utils.validation.JyDetailsUtil;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.utils.validation.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.pm.qqch.preparation.safe.qqchSafeMostEnvirRiskList.mapper.QqchSafeMostEnvirRiskListMapper;
import com.hhwy.pm.qqch.preparation.safe.qqchSafeMostEnvirRiskList.service.IQqchSafeMostEnvirRiskListService;
import com.hhwy.pm.qqch.preparation.safe.qqchSafeMostEnvirRiskList.domain.QqchSafeMostEnvirRiskList;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.util.CollectionUtils;

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
    public int insertQqchSafeMostEnvirRiskListList(QqchSafeMostEnvirRiskListVo voParam) {
        //清空数据库
        QqchSafeMostEnvirRiskList qqchSafeMostEnvirRiskList = new QqchSafeMostEnvirRiskList();
        qqchSafeMostEnvirRiskList.setVersion(voParam.getVersion());
        qqchSafeMostEnvirRiskListMapper.deleteQqchSafeMostEnvirRiskList(qqchSafeMostEnvirRiskList);
        //删除子表
        List<QqchSafeMostEnvirRiskList> infoList = qqchSafeMostEnvirRiskListMapper.getQqchSafeMostEnvirRiskListList(qqchSafeMostEnvirRiskList);
        if(!ObjectNullUtil.isEmpty(infoList)){
            List<Long> infoIdList = infoList.stream().map(t -> t.getId()).collect(Collectors.toList());
            detailService.deleteByInfoIds(infoIdList,String.valueOf(SecurityUtils.getUserId()), DateUtils.getNowDate());
        }

        if (!CollectionUtils.isEmpty(voParam.getList())) {
            List<QqchSafeMostEnvirRiskList> list = voParam.getList();

            ArrayList<QqchSafeMostEnvirRiskListDetail> addDetailList = new ArrayList<>();
            StringBuffer str = new StringBuffer("");//必填项校验
            for (QqchSafeMostEnvirRiskList safeMostEnvirRiskList : list) {
                safeMostEnvirRiskList.setId(IdWorker.createId());
                safeMostEnvirRiskList.setVersion(voParam.getVersion());
                if (voParam.getVersion().compareTo(BigDecimal.ONE) == 0) {
                    safeMostEnvirRiskList.setValid(Valid.YES);
                }
                safeMostEnvirRiskList.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                safeMostEnvirRiskList.setCreateUserName(SecurityUtils.getUserName());
                safeMostEnvirRiskList.setCreateTime(DateUtils.getNowDate());
                List<QqchSafeMostEnvirRiskListDetail> detailList = safeMostEnvirRiskList.getDetailList();
                if(!ObjectNullUtil.isEmpty(detailList)){
                    StringBuffer detailStr = new StringBuffer("");//必填项校验
                    for (QqchSafeMostEnvirRiskListDetail detail : detailList) {
                        //校验数据必填
                        if("1".equals(voParam.getButtonMark())||"2".equals(voParam.getButtonMark())){//确认
                            BeanValidationResult beanValidationResult = ValidationUtil.warpValidate(detail, ValidationGroups.Save.class);
                            if (!beanValidationResult.isSuccess()) {
                                List<BeanValidationResult.ErrorMessage> errorMessages = beanValidationResult.getErrorMessages();
                                for (BeanValidationResult.ErrorMessage errorMessage : errorMessages) {
                                    detailStr = detailStr.append(errorMessage.getMessage() + ",");
                                }
                            }
                        }
                        detail.setId(IdWorker.createId());
                        detail.setInfoId(safeMostEnvirRiskList.getId());
                        detail.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                        detail.setCreateUserName(SecurityUtils.getUserName());
                        detail.setCreateTime(DateUtils.getNowDate());
                        addDetailList.add(detail);
                    }
                    if (!"".equals(detailStr.toString())) {
                        str = new StringBuffer("wbs"+safeMostEnvirRiskList.getWbsCode()+":").append(detailStr+";");
                    }
                }
            }
            qqchSafeMostEnvirRiskListMapper.insertQqchSafeMostEnvirRiskListList(list);
            detailService.insertQqchSafeMostEnvirRiskListDetailList(addDetailList);
            String buttonMark = voParam.getButtonMark();
            if (ButtonMark.CONFIRM.equals(buttonMark)) {
                // 插入确认状态
                qqchReviewService.updateFinishNum(voParam.getStageIdentity(), voParam.getModuleIdentity());
                qqchModuleConfirmCaseService.addConfirmRecord(voParam.getMenuId(), voParam.getStageIdentity());
            }

            if (!"".equals(str.toString())) {
                log.error(str.toString());
                throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error, str.toString());
            }
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
    public QqchSafeMostEnvirRiskListVo getList(BigDecimal version) {
        QqchSafeMostEnvirRiskListVo qqchSafeMostEnvirRiskListVo = new QqchSafeMostEnvirRiskListVo();
        version = VersionUtil.getVersion("qqch_safe_most_envir_risk_list", version);
        QqchSafeMostEnvirRiskList qqchSafeMostEnvirRiskList = new QqchSafeMostEnvirRiskList();
        qqchSafeMostEnvirRiskList.setVersion(version);
        List<QqchSafeMostEnvirRiskList> infoList = qqchSafeMostEnvirRiskListMapper.getQqchSafeMostEnvirRiskListList(qqchSafeMostEnvirRiskList);
        if(!ObjectNullUtil.isEmpty(infoList)) {
            List<Long> infoIdList = infoList.stream().map(t -> t.getId()).collect(Collectors.toList());
            List<QqchSafeMostEnvirRiskListDetail> detailList =  detailService.getListByInfoIds(infoIdList);
            if(!ObjectNullUtil.isEmpty(detailList)){
                Map<Long, List<QqchSafeMostEnvirRiskListDetail>> detailListMap = detailList.stream().collect(Collectors.groupingBy(t -> t.getInfoId()));
                for (QqchSafeMostEnvirRiskList safeMostEnvirRiskList : infoList) {
                    if(!ObjectNullUtil.isEmpty(detailListMap.get(safeMostEnvirRiskList.getId()))){
                        List<QqchSafeMostEnvirRiskListDetail> qqchSafeMostEnvirRiskListDetails = detailListMap.get(safeMostEnvirRiskList.getId());
                        safeMostEnvirRiskList.setDetailList(qqchSafeMostEnvirRiskListDetails);
                    }
                }
            }
        }

        qqchSafeMostEnvirRiskListVo.setVersion(version);
        qqchSafeMostEnvirRiskListVo.setStageIdentity(qqchReviewService.getStage());
        qqchSafeMostEnvirRiskListVo.setList(infoList);
        return qqchSafeMostEnvirRiskListVo;
    }
}
