package com.hhwy.pm.qqch.preparation.quality.qqchFirstArticleEngineeringControl.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.impl.QqchModuleConfirmCaseServiceImpl;
import com.hhwy.pm.qqch.preparation.quality.qqchFirstArticleEngineeringControl.domain.QqchFirstArticleEngineeringControl;
import com.hhwy.pm.qqch.preparation.quality.qqchFirstArticleEngineeringControl.domain.vo.QqchFirstArticleEngineeringControlVo;
import com.hhwy.pm.qqch.preparation.quality.qqchFirstArticleEngineeringControl.mapper.QqchFirstArticleEngineeringControlMapper;
import com.hhwy.pm.qqch.preparation.quality.qqchFirstArticleEngineeringControl.service.IQqchFirstArticleEngineeringControlService;
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
 * @date 2023-08-04 17:06:12
 * @remark
 */
@Service
public class QqchFirstArticleEngineeringControlServiceImpl implements IQqchFirstArticleEngineeringControlService {

    @Autowired
    private QqchFirstArticleEngineeringControlMapper qqchFirstArticleEngineeringControlMapper;
    @Autowired
    private QqchModuleConfirmCaseServiceImpl qqchModuleConfirmCaseService;
    @Autowired
    private IQqchReviewService qqchReviewService;


    public QqchFirstArticleEngineeringControl getQqchFirstArticleEngineeringControl(QqchFirstArticleEngineeringControl qqchFirstArticleEngineeringControl) {
        return qqchFirstArticleEngineeringControlMapper.getQqchFirstArticleEngineeringControl(qqchFirstArticleEngineeringControl);
    }

    @Transactional
    public int insertQqchFirstArticleEngineeringControl(QqchFirstArticleEngineeringControl qqchFirstArticleEngineeringControl) {
        qqchFirstArticleEngineeringControl.setId(IdWorker.createId());
        qqchFirstArticleEngineeringControl.setCreateUser(SecurityUtils.getUserName());
        qqchFirstArticleEngineeringControl.setCreateTime(DateUtils.getNowDate());
        return qqchFirstArticleEngineeringControlMapper.insertQqchFirstArticleEngineeringControl(qqchFirstArticleEngineeringControl);
    }


    @Transactional
    public int updateQqchFirstArticleEngineeringControl(QqchFirstArticleEngineeringControl qqchFirstArticleEngineeringControl) {
        qqchFirstArticleEngineeringControl.setUpdateUser(SecurityUtils.getUserName());
        qqchFirstArticleEngineeringControl.setUpdateTime(DateUtils.getNowDate());
        return qqchFirstArticleEngineeringControlMapper.updateQqchFirstArticleEngineeringControl(qqchFirstArticleEngineeringControl);
    }

    @Transactional
    public int updateQqchFirstArticleEngineeringControlList(List<QqchFirstArticleEngineeringControl> qqchFirstArticleEngineeringControlList) {
        for (QqchFirstArticleEngineeringControl qqchFirstArticleEngineeringControl : qqchFirstArticleEngineeringControlList) {
            qqchFirstArticleEngineeringControl.setUpdateUser(SecurityUtils.getUserName());
            qqchFirstArticleEngineeringControl.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchFirstArticleEngineeringControlMapper.updateQqchFirstArticleEngineeringControlList(qqchFirstArticleEngineeringControlList);
    }

    @Transactional
    public int deleteQqchFirstArticleEngineeringControl(QqchFirstArticleEngineeringControl qqchFirstArticleEngineeringControl) {
//        qqchFirstArticleEngineeringControl.setUpdateUser(SecurityUtils.getUserName());
//        qqchFirstArticleEngineeringControl.setUpdateTime(DateUtils.getNowDate());
        return qqchFirstArticleEngineeringControlMapper.deleteQqchFirstArticleEngineeringControl(qqchFirstArticleEngineeringControl);
    }

    @Transactional
    public int deleteQqchFirstArticleEngineeringControlByPks(List<Long> qqchFirstArticleEngineeringControlPkList) {
        return qqchFirstArticleEngineeringControlMapper.deleteQqchFirstArticleEngineeringControlByPks(qqchFirstArticleEngineeringControlPkList);
    }

    /**
     *  列表接口
     * @param qqchFirstArticleEngineeringControl
     * @return
     */
    public QqchFirstArticleEngineeringControlVo getQqchFirstArticleEngineeringControlList(QqchFirstArticleEngineeringControl qqchFirstArticleEngineeringControl) {
        QqchFirstArticleEngineeringControlVo vo = new QqchFirstArticleEngineeringControlVo();

        BigDecimal version = qqchFirstArticleEngineeringControl.getVersion();
        version = VersionUtil.getVersion("qqch_first_article_engineering_control", version);

        qqchFirstArticleEngineeringControl.setVersion(version);
        List<QqchFirstArticleEngineeringControl> qqchFirstArticleEngineeringControlList = qqchFirstArticleEngineeringControlMapper.getQqchFirstArticleEngineeringControlList(qqchFirstArticleEngineeringControl);

        vo.setVersion(version);
        vo.setStageIdentity(qqchReviewService.getStage());
        vo.setQqchFirstArticleEngineeringControlList(qqchFirstArticleEngineeringControlList);
        return vo;
    }


    /**
     *  确认/提交/保存
     * @param vo
     */
    @Override
    @Transactional
    public void save(QqchFirstArticleEngineeringControlVo vo) {
        String buttonMark = vo.getButtonMark();
        ButtonMarkUtil.checkButtonMark(buttonMark);

        BigDecimal version = vo.getVersion();
        List<QqchFirstArticleEngineeringControl> qqchFirstArticleEngineeringControlList = vo.getQqchFirstArticleEngineeringControlList();
        if(CollectionUtils.isEmpty(qqchFirstArticleEngineeringControlList)){
            return;
        }else {
            //校验数据必填
            if("1".equals(vo.getButtonMark())||"2".equals(vo.getButtonMark())){//确认
                JyDetailsUtil.jyDetails(qqchFirstArticleEngineeringControlList, ValidationGroups.Save.class);
            }
        }

        this.insertQqchFirstArticleEngineeringControlList(qqchFirstArticleEngineeringControlList, version);

        //处理确认状态是确认
        if (ButtonMark.CONFIRM.equals(buttonMark)) {
            //插入确认记录
            String menuId = vo.getMenuId();
            String stageIdentity = vo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId, stageIdentity);
        }
    }

    public void insertQqchFirstArticleEngineeringControlList(List<QqchFirstArticleEngineeringControl> qqchFirstArticleEngineeringControlList,BigDecimal version) {
        //删除旧数据
        QqchFirstArticleEngineeringControl qqchFirstArticleEngineeringControl = new QqchFirstArticleEngineeringControl();
        qqchFirstArticleEngineeringControl.setVersion(version);
        qqchFirstArticleEngineeringControlMapper.deleteQqchFirstArticleEngineeringControl(qqchFirstArticleEngineeringControl);

        String valid = Valid.NO;
        if (version.compareTo(BigDecimal.ONE) == 0) {
            valid = Valid.YES;
        }
        for (QqchFirstArticleEngineeringControl firstArticleEngineeringControl : qqchFirstArticleEngineeringControlList) {
            firstArticleEngineeringControl.setId(IdWorker.createId());
            firstArticleEngineeringControl.setValid(valid);
            firstArticleEngineeringControl.setVersion(version);
            firstArticleEngineeringControl.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            firstArticleEngineeringControl.setCreateUserName(SecurityUtils.getSysUser().getNickName());
            firstArticleEngineeringControl.setCreateTime(DateUtils.getNowDate());
        }
        qqchFirstArticleEngineeringControlMapper.insertQqchFirstArticleEngineeringControlList(qqchFirstArticleEngineeringControlList);
    }

    public void insertList(List<QqchFirstArticleEngineeringControl> qqchFirstArticleEngineeringControlList,BigDecimal version) {
        String valid = Valid.NO;
        if (version.compareTo(BigDecimal.ONE) == 0) {
            valid = Valid.YES;
        }
        for (QqchFirstArticleEngineeringControl firstArticleEngineeringControl : qqchFirstArticleEngineeringControlList) {
            firstArticleEngineeringControl.setId(IdWorker.createId());
            firstArticleEngineeringControl.setValid(valid);
            firstArticleEngineeringControl.setVersion(version);
            firstArticleEngineeringControl.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            firstArticleEngineeringControl.setCreateUserName(SecurityUtils.getSysUser().getNickName());
            firstArticleEngineeringControl.setCreateTime(DateUtils.getNowDate());
        }
        qqchFirstArticleEngineeringControlMapper.insertQqchFirstArticleEngineeringControlList(qqchFirstArticleEngineeringControlList);
    }
}
