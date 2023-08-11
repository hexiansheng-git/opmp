package com.hhwy.pm.qqch.preparation.quality.qqchSafetyTrain.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.impl.QqchModuleConfirmCaseServiceImpl;
import com.hhwy.pm.qqch.preparation.quality.qqchSafetyTrain.domain.QqchSafetyTrain;
import com.hhwy.pm.qqch.preparation.quality.qqchSafetyTrain.domain.vo.QqchSafetyTrainVo;
import com.hhwy.pm.qqch.preparation.quality.qqchSafetyTrain.mapper.QqchSafetyTrainMapper;
import com.hhwy.pm.qqch.preparation.quality.qqchSafetyTrain.service.IQqchSafetyTrainService;
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
 * @date 2023-08-10 18:38:55
 * @remark 8.9 安全培训策划
 */
@Service
public class QqchSafetyTrainServiceImpl implements IQqchSafetyTrainService {

    @Autowired
    private QqchSafetyTrainMapper qqchSafetyTrainMapper;
    @Autowired
    private QqchModuleConfirmCaseServiceImpl qqchModuleConfirmCaseService;
    @Autowired
    private IQqchReviewService qqchReviewService;


    public QqchSafetyTrain getQqchSafetyTrain(QqchSafetyTrain qqchSafetyTrain) {
        return qqchSafetyTrainMapper.getQqchSafetyTrain(qqchSafetyTrain);
    }

    @Transactional
    public int insertQqchSafetyTrain(QqchSafetyTrain qqchSafetyTrain) {
        qqchSafetyTrain.setId(IdWorker.createId());
        qqchSafetyTrain.setCreateUser(SecurityUtils.getUserName());
        qqchSafetyTrain.setCreateTime(DateUtils.getNowDate());
        return qqchSafetyTrainMapper.insertQqchSafetyTrain(qqchSafetyTrain);
    }


    @Transactional
    public int updateQqchSafetyTrain(QqchSafetyTrain qqchSafetyTrain) {
        qqchSafetyTrain.setUpdateUser(SecurityUtils.getUserName());
        qqchSafetyTrain.setUpdateTime(DateUtils.getNowDate());
        return qqchSafetyTrainMapper.updateQqchSafetyTrain(qqchSafetyTrain);
    }

    @Transactional
    public int updateQqchSafetyTrainList(List<QqchSafetyTrain> qqchSafetyTrainList) {
        for (QqchSafetyTrain qqchSafetyTrain : qqchSafetyTrainList) {
            qqchSafetyTrain.setUpdateUser(SecurityUtils.getUserName());
            qqchSafetyTrain.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchSafetyTrainMapper.updateQqchSafetyTrainList(qqchSafetyTrainList);
    }

    @Transactional
    public int deleteQqchSafetyTrain(QqchSafetyTrain qqchSafetyTrain) {
        qqchSafetyTrain.setUpdateUser(SecurityUtils.getUserName());
        qqchSafetyTrain.setUpdateTime(DateUtils.getNowDate());
        return qqchSafetyTrainMapper.deleteQqchSafetyTrain(qqchSafetyTrain);
    }

    @Transactional
    public int deleteQqchSafetyTrainByPks(List<Long> qqchSafetyTrainPkList) {
        return qqchSafetyTrainMapper.deleteQqchSafetyTrainByPks(qqchSafetyTrainPkList);
    }

    /**
     *  列表接口
     * @param qqchSafetyTrain
     * @return
     */
    public QqchSafetyTrainVo getQqchSafetyTrainList(QqchSafetyTrain qqchSafetyTrain) {
        QqchSafetyTrainVo vo = new QqchSafetyTrainVo();

        BigDecimal version = qqchSafetyTrain.getVersion();
        version = VersionUtil.getVersion("qqch_safety_train", version);

        qqchSafetyTrain.setVersion(version);
        List<QqchSafetyTrain> qqchSafetyTrainList = qqchSafetyTrainMapper.getQqchSafetyTrainList(qqchSafetyTrain);

        vo.setVersion(version);
        vo.setStageIdentity(qqchReviewService.getStage());
        vo.setQqchSafetyTrainList(qqchSafetyTrainList);
        return vo;
    }

    /**
     *  保存/确认/提交
     * @param vo
     */
    @Override
    @Transactional
    public void save(QqchSafetyTrainVo vo) {
        String buttonMark = vo.getButtonMark();
        ButtonMarkUtil.checkButtonMark(buttonMark);

        BigDecimal version = vo.getVersion();
        List<QqchSafetyTrain> qqchSafetyTrainList = vo.getQqchSafetyTrainList();
        if(CollectionUtils.isEmpty(qqchSafetyTrainList)){
            return;
        }else {
            //校验数据必填
            if("1".equals(vo.getButtonMark())||"2".equals(vo.getButtonMark())){//确认
                JyDetailsUtil.jyDetails(qqchSafetyTrainList, ValidationGroups.Save.class);
            }
        }

        this.insertQqchSafetyTrainList(qqchSafetyTrainList, version);

        //处理确认状态是确认
        if (ButtonMark.CONFIRM.equals(buttonMark)) {
            //插入确认记录
            String menuId = vo.getMenuId();
            String stageIdentity = vo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId, stageIdentity);
        }
    }


    public void insertQqchSafetyTrainList(List<QqchSafetyTrain> qqchSafetyTrainList,BigDecimal version) {
        //删除旧数据
        QqchSafetyTrain qqchSafetyTrain1 = new QqchSafetyTrain();
        qqchSafetyTrain1.setVersion(version);
        qqchSafetyTrainMapper.deleteQqchSafetyTrain(qqchSafetyTrain1);

        String valid = Valid.NO;
        if (version.compareTo(BigDecimal.ONE) == 0) {
            valid = Valid.YES;
        }
        for (QqchSafetyTrain qqchSafetyTrain : qqchSafetyTrainList) {
            qqchSafetyTrain.setId(IdWorker.createId());
            qqchSafetyTrain.setValid(valid);
            qqchSafetyTrain.setVersion(version);
            qqchSafetyTrain.setCreateUser(SecurityUtils.getUserName());
            qqchSafetyTrain.setCreateTime(DateUtils.getNowDate());
        }
        qqchSafetyTrainMapper.insertQqchSafetyTrainList(qqchSafetyTrainList);
    }
}
