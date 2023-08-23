package com.hhwy.pm.qqch.preparation.safe.qqchCareerHealthRiskList.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.safe.qqchCareerHealthRiskList.domain.QqchCareerHealthRiskList;
import com.hhwy.pm.qqch.preparation.safe.qqchCareerHealthRiskList.domain.vo.QqchCareerHealthRiskListVo;
import com.hhwy.pm.qqch.preparation.safe.qqchCareerHealthRiskList.mapper.QqchCareerHealthRiskListMapper;
import com.hhwy.pm.qqch.preparation.safe.qqchCareerHealthRiskList.service.IQqchCareerHealthRiskListService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.ButtonMarkUtil;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.validation.JyDetailsUtil;
import com.hhwy.utils.validation.ValidationGroups;
import io.seata.common.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author ldd
 * @date 2023-08-09 15:01:27
 * @remark
 *
 * 8.7.1 职业健康风险清单
 */
@Service
public class QqchCareerHealthRiskListServiceImpl implements IQqchCareerHealthRiskListService {

    @Autowired
    private QqchCareerHealthRiskListMapper qqchCareerHealthRiskListMapper;
    @Autowired
    private IQqchReviewService qqchReviewService;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;


    public QqchCareerHealthRiskList getQqchCareerHealthRiskList(QqchCareerHealthRiskList qqchCareerHealthRiskList) {
        return qqchCareerHealthRiskListMapper.getQqchCareerHealthRiskList(qqchCareerHealthRiskList);
    }


    @Transactional
    public int insertQqchCareerHealthRiskList(QqchCareerHealthRiskList qqchCareerHealthRiskList) {
        qqchCareerHealthRiskList.setId(IdWorker.createId());
        qqchCareerHealthRiskList.setCreateUser(SecurityUtils.getUserName());
        qqchCareerHealthRiskList.setCreateTime(DateUtils.getNowDate());
        return qqchCareerHealthRiskListMapper.insertQqchCareerHealthRiskList(qqchCareerHealthRiskList);
    }


    @Transactional
    public int updateQqchCareerHealthRiskList(QqchCareerHealthRiskList qqchCareerHealthRiskList) {
        qqchCareerHealthRiskList.setUpdateUser(SecurityUtils.getUserName());
        qqchCareerHealthRiskList.setUpdateTime(DateUtils.getNowDate());
        return qqchCareerHealthRiskListMapper.updateQqchCareerHealthRiskList(qqchCareerHealthRiskList);
    }

    @Transactional
    public int updateQqchCareerHealthRiskListList(List<QqchCareerHealthRiskList> qqchCareerHealthRiskListList) {
        for (QqchCareerHealthRiskList qqchCareerHealthRiskList : qqchCareerHealthRiskListList) {
            qqchCareerHealthRiskList.setUpdateUser(SecurityUtils.getUserName());
            qqchCareerHealthRiskList.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchCareerHealthRiskListMapper.updateQqchCareerHealthRiskListList(qqchCareerHealthRiskListList);
    }

    @Transactional
    public int deleteQqchCareerHealthRiskList(QqchCareerHealthRiskList qqchCareerHealthRiskList) {
        qqchCareerHealthRiskList.setUpdateUser(SecurityUtils.getUserName());
        qqchCareerHealthRiskList.setUpdateTime(DateUtils.getNowDate());
        return qqchCareerHealthRiskListMapper.deleteQqchCareerHealthRiskList(qqchCareerHealthRiskList);
    }

    @Transactional
    public int deleteQqchCareerHealthRiskListByPks(List<Long> qqchCareerHealthRiskListPkList) {
        return qqchCareerHealthRiskListMapper.deleteQqchCareerHealthRiskListByPks(qqchCareerHealthRiskListPkList);
    }

    /**
     *  列表接口
     * @param qqchCareerHealthRiskList
     * @return
     */
    public QqchCareerHealthRiskListVo getQqchCareerHealthRiskListList(QqchCareerHealthRiskList qqchCareerHealthRiskList) {
        QqchCareerHealthRiskListVo vo = new QqchCareerHealthRiskListVo();
        BigDecimal version = VersionUtil.getVersion("qqch_career_health_risk_list", qqchCareerHealthRiskList.getVersion());
        qqchCareerHealthRiskList.setVersion(version);
        List<QqchCareerHealthRiskList> qqchCareerHealthRiskListList = qqchCareerHealthRiskListMapper.getQqchCareerHealthRiskListList(qqchCareerHealthRiskList);
        vo.setVersion(version);
        vo.setStageIdentity(qqchReviewService.getStage());
        vo.setQqchCareerHealthRiskListList(qqchCareerHealthRiskListList);
        return vo;
    }

    /**
     *  保存/确认/提交
     * @param vo
     */
    @Override
    @Transactional
    public void save(QqchCareerHealthRiskListVo vo) {
        String buttonMark = vo.getButtonMark();
        ButtonMarkUtil.checkButtonMark(buttonMark);

        BigDecimal version = vo.getVersion();
        List<QqchCareerHealthRiskList> qqchCareerHealthRiskListList = vo.getQqchCareerHealthRiskListList();
        this.insertQqchCareerHealthRiskListList(qqchCareerHealthRiskListList,version);

        if(CollectionUtils.isEmpty(qqchCareerHealthRiskListList)){
            return;
        }
        //判断是否是确认
        if(ButtonMark.CONFIRM.equals(buttonMark)){
            //校验数据必填
            JyDetailsUtil.jyDetails(qqchCareerHealthRiskListList, ValidationGroups.Save.class);
            //插入确认记录
            String menuId = vo.getMenuId();
            String stageIdentity = vo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
    }


    public int insertQqchCareerHealthRiskListList(List<QqchCareerHealthRiskList> qqchCareerHealthRiskListList,BigDecimal version) {
        //删除旧数据
        QqchCareerHealthRiskList qqchCareerHealthRiskList1 = new QqchCareerHealthRiskList();
        qqchCareerHealthRiskList1.setVersion(version);
        qqchCareerHealthRiskListMapper.deleteQqchCareerHealthRiskList(qqchCareerHealthRiskList1);

        if(CollectionUtils.isEmpty(qqchCareerHealthRiskListList)){
            return 0;
        }
        String valid = Valid.NO;
        if(version.compareTo(BigDecimal.ONE) == 0){
            valid = Valid.YES;
        }
        for (QqchCareerHealthRiskList qqchCareerHealthRiskList : qqchCareerHealthRiskListList) {
            qqchCareerHealthRiskList.setValid(valid);
            qqchCareerHealthRiskList.setVersion(version);
            qqchCareerHealthRiskList.setId(IdWorker.createId());
            qqchCareerHealthRiskList.setCreateUser(SecurityUtils.getUserName());
            qqchCareerHealthRiskList.setCreateTime(DateUtils.getNowDate());
        }
        return qqchCareerHealthRiskListMapper.insertQqchCareerHealthRiskListList(qqchCareerHealthRiskListList);
    }
}
