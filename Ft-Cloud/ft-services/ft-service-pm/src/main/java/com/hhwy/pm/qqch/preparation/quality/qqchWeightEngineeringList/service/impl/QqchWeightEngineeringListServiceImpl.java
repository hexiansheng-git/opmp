package com.hhwy.pm.qqch.preparation.quality.qqchWeightEngineeringList.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.impl.QqchModuleConfirmCaseServiceImpl;
import com.hhwy.pm.qqch.preparation.quality.qqchWeightEngineeringList.domain.QqchWeightEngineeringList;
import com.hhwy.pm.qqch.preparation.quality.qqchWeightEngineeringList.domain.vo.QqchWeightEngineeringListVo;
import com.hhwy.pm.qqch.preparation.quality.qqchWeightEngineeringList.mapper.QqchWeightEngineeringListMapper;
import com.hhwy.pm.qqch.preparation.quality.qqchWeightEngineeringList.service.IQqchWeightEngineeringListService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.ButtonMarkUtil;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.idworker.IdWorker;
import io.seata.common.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author ldd
 * @date 2023-08-04 14:24:06
 * @remark
 */
@Service
public class QqchWeightEngineeringListServiceImpl implements IQqchWeightEngineeringListService {

    @Autowired
    private QqchWeightEngineeringListMapper qqchWeightEngineeringListMapper;
    @Autowired
    private QqchModuleConfirmCaseServiceImpl qqchModuleConfirmCaseService;
    @Autowired
    private IQqchReviewService qqchReviewService;


    public QqchWeightEngineeringList getQqchWeightEngineeringList(QqchWeightEngineeringList qqchWeightEngineeringList) {
        return qqchWeightEngineeringListMapper.getQqchWeightEngineeringList(qqchWeightEngineeringList);
    }

    @Transactional
    public int insertQqchWeightEngineeringList(QqchWeightEngineeringList qqchWeightEngineeringList) {
        qqchWeightEngineeringList.setId(IdWorker.createId());
        qqchWeightEngineeringList.setCreateUser(SecurityUtils.getUserName());
        qqchWeightEngineeringList.setCreateTime(DateUtils.getNowDate());
        return qqchWeightEngineeringListMapper.insertQqchWeightEngineeringList(qqchWeightEngineeringList);
    }



    @Transactional
    public int updateQqchWeightEngineeringList(QqchWeightEngineeringList qqchWeightEngineeringList) {
        qqchWeightEngineeringList.setUpdateUser(SecurityUtils.getUserName());
        qqchWeightEngineeringList.setUpdateTime(DateUtils.getNowDate());
        return qqchWeightEngineeringListMapper.updateQqchWeightEngineeringList(qqchWeightEngineeringList);
    }

    @Transactional
    public int updateQqchWeightEngineeringListList(List<QqchWeightEngineeringList> qqchWeightEngineeringListList) {
        for (QqchWeightEngineeringList qqchWeightEngineeringList : qqchWeightEngineeringListList) {
            qqchWeightEngineeringList.setUpdateUser(SecurityUtils.getUserName());
            qqchWeightEngineeringList.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchWeightEngineeringListMapper.updateQqchWeightEngineeringListList(qqchWeightEngineeringListList);
    }

    @Transactional
    public int deleteQqchWeightEngineeringList(QqchWeightEngineeringList qqchWeightEngineeringList) {
        qqchWeightEngineeringList.setUpdateUser(SecurityUtils.getUserName());
        qqchWeightEngineeringList.setUpdateTime(DateUtils.getNowDate());
        return qqchWeightEngineeringListMapper.deleteQqchWeightEngineeringList(qqchWeightEngineeringList);
    }

    @Transactional
    public int deleteQqchWeightEngineeringListByPks(List<Long> qqchWeightEngineeringListPkList) {
        return qqchWeightEngineeringListMapper.deleteQqchWeightEngineeringListByPks(qqchWeightEngineeringListPkList);
    }



    /**
     * 列表接口
     */
    public QqchWeightEngineeringListVo getQqchWeightEngineeringListList(QqchWeightEngineeringList qqchWeightEngineeringList) {
        QqchWeightEngineeringListVo vo = new QqchWeightEngineeringListVo();

        BigDecimal version = qqchWeightEngineeringList.getVersion();
        version = VersionUtil.getVersion("qqch_weight_engineering_list", version);

        qqchWeightEngineeringList.setVersion(version);
        List<QqchWeightEngineeringList> qqchWeightEngineeringListList = qqchWeightEngineeringListMapper.getQqchWeightEngineeringListList(qqchWeightEngineeringList);

        vo.setVersion(version);
        vo.setStageIdentity(qqchReviewService.getStage());
        vo.setQqchWeightEngineeringListList(qqchWeightEngineeringListList);
        return vo;
    }

    /**
     *  保存/确认/提交
     * @param vo
     */
    @Override
    @Transactional
    public void save(QqchWeightEngineeringListVo vo) {
        String buttonMark = vo.getButtonMark();
        ButtonMarkUtil.checkButtonMark(buttonMark);

        BigDecimal version = vo.getVersion();
        List<QqchWeightEngineeringList> qqchWeightEngineeringListList = vo.getQqchWeightEngineeringListList();

        this.insertQqchWeightEngineeringListList(qqchWeightEngineeringListList, version);

        //处理确认状态是确认
        if (ButtonMark.CONFIRM.equals(buttonMark)) {
            //插入确认记录
            String menuId = vo.getMenuId();
            String stageIdentity = vo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId, stageIdentity);
        }
    }


    public void insertQqchWeightEngineeringListList(List<QqchWeightEngineeringList> qqchWeightEngineeringListList,BigDecimal version) {
        //删除旧数据
        QqchWeightEngineeringList qqchWeightEngineeringList = new QqchWeightEngineeringList();
        qqchWeightEngineeringList.setVersion(version);
        qqchWeightEngineeringListMapper.deleteQqchWeightEngineeringList(qqchWeightEngineeringList);
        if (CollectionUtils.isEmpty(qqchWeightEngineeringListList)) {
            return;
        }
        String valid = Valid.NO;
        if (version.compareTo(BigDecimal.ONE) == 0) {
            valid = Valid.YES;
        }
        for (QqchWeightEngineeringList engineeringList : qqchWeightEngineeringListList) {
            engineeringList.setId(IdWorker.createId());
            engineeringList.setValid(valid);
            engineeringList.setVersion(version);
            engineeringList.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            engineeringList.setCreateUserName(SecurityUtils.getUserName());
            engineeringList.setCreateTime(DateUtils.getNowDate());
        }
        qqchWeightEngineeringListMapper.insertQqchWeightEngineeringListList(qqchWeightEngineeringListList);
    }

}
