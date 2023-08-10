package com.hhwy.pm.qqch.preparation.technique.techArchivesManage.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.impl.QqchModuleConfirmCaseServiceImpl;
import com.hhwy.pm.qqch.preparation.technique.techArchivesManage.domain.QqchPigeonholeDutyDivision;
import com.hhwy.pm.qqch.preparation.technique.techArchivesManage.domain.vo.QqchPigeonholeDutyDivisionVo;
import com.hhwy.pm.qqch.preparation.technique.techArchivesManage.mapper.QqchPigeonholeDutyDivisionMapper;
import com.hhwy.pm.qqch.preparation.technique.techArchivesManage.service.IQqchPigeonholeDutyDivisionService;
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
 * @author han
 * @date 2023-07-25 10:48:27
 * @remark 技术档案归档责任分工
 */
@Service
public class QqchPigeonholeDutyDivisionServiceImpl implements IQqchPigeonholeDutyDivisionService {

    @Autowired
    private QqchPigeonholeDutyDivisionMapper qqchPigeonholeDutyDivisionMapper;

    @Autowired
    private QqchModuleConfirmCaseServiceImpl qqchModuleConfirmCaseService;

    @Autowired
    private IQqchReviewService qqchReviewService;


    public QqchPigeonholeDutyDivision getQqchPigeonholeDutyDivision(QqchPigeonholeDutyDivision qqchPigeonholeDutyDivision) {
        return qqchPigeonholeDutyDivisionMapper.getQqchPigeonholeDutyDivision(qqchPigeonholeDutyDivision);
    }

    public List<QqchPigeonholeDutyDivision> getQqchPigeonholeDutyDivisionList(QqchPigeonholeDutyDivision qqchPigeonholeDutyDivision) {
        return qqchPigeonholeDutyDivisionMapper.getQqchPigeonholeDutyDivisionList(qqchPigeonholeDutyDivision);
    }

    @Transactional
    public int insertQqchPigeonholeDutyDivision(QqchPigeonholeDutyDivision qqchPigeonholeDutyDivision) {
        qqchPigeonholeDutyDivision.setId(IdWorker.createId());
        qqchPigeonholeDutyDivision.setCreateUser(SecurityUtils.getUserName());
        qqchPigeonholeDutyDivision.setCreateTime(DateUtils.getNowDate());
        return qqchPigeonholeDutyDivisionMapper.insertQqchPigeonholeDutyDivision(qqchPigeonholeDutyDivision);
    }

    @Transactional
    public void insertQqchPigeonholeDutyDivisionList(List<QqchPigeonholeDutyDivision> qqchPigeonholeDutyDivisionList, BigDecimal version) {
        //删除旧数据
        QqchPigeonholeDutyDivision qqchPigeonholeDutyDivision = new QqchPigeonholeDutyDivision();
        qqchPigeonholeDutyDivision.setVersion(version);
        qqchPigeonholeDutyDivisionMapper.deleteQqchPigeonholeDutyDivision(qqchPigeonholeDutyDivision);

        if(CollectionUtils.isEmpty(qqchPigeonholeDutyDivisionList)){
            return;
        }
        String valid = Valid.NO;
        if(version.compareTo(BigDecimal.ONE) == 0){
            valid = Valid.YES;
        }
        for (QqchPigeonholeDutyDivision pigeonholeDutyDivision : qqchPigeonholeDutyDivisionList) {
            pigeonholeDutyDivision.setId(IdWorker.createId());
            pigeonholeDutyDivision.setValid(valid);
            pigeonholeDutyDivision.setVersion(version);
            pigeonholeDutyDivision.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            pigeonholeDutyDivision.setCreateUserName(SecurityUtils.getUserName());
            pigeonholeDutyDivision.setCreateTime(DateUtils.getNowDate());
        }
        qqchPigeonholeDutyDivisionMapper.insertQqchPigeonholeDutyDivisionList(qqchPigeonholeDutyDivisionList);
    }

    @Transactional
    public int updateQqchPigeonholeDutyDivision(QqchPigeonholeDutyDivision qqchPigeonholeDutyDivision) {
        qqchPigeonholeDutyDivision.setUpdateUser(SecurityUtils.getUserName());
        qqchPigeonholeDutyDivision.setUpdateTime(DateUtils.getNowDate());
        return qqchPigeonholeDutyDivisionMapper.updateQqchPigeonholeDutyDivision(qqchPigeonholeDutyDivision);
    }

    @Transactional
    public int updateQqchPigeonholeDutyDivisionList(List<QqchPigeonholeDutyDivision> qqchPigeonholeDutyDivisionList) {
        for (QqchPigeonholeDutyDivision qqchPigeonholeDutyDivision : qqchPigeonholeDutyDivisionList) {
            qqchPigeonholeDutyDivision.setUpdateUser(SecurityUtils.getUserName());
            qqchPigeonholeDutyDivision.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchPigeonholeDutyDivisionMapper.updateQqchPigeonholeDutyDivisionList(qqchPigeonholeDutyDivisionList);
    }

    @Transactional
    public int deleteQqchPigeonholeDutyDivision(QqchPigeonholeDutyDivision qqchPigeonholeDutyDivision) {
        qqchPigeonholeDutyDivision.setUpdateUser(SecurityUtils.getUserName());
        qqchPigeonholeDutyDivision.setUpdateTime(DateUtils.getNowDate());
        return qqchPigeonholeDutyDivisionMapper.deleteQqchPigeonholeDutyDivision(qqchPigeonholeDutyDivision);
    }

    @Transactional
    public int deleteQqchPigeonholeDutyDivisionByPks(List<Long> qqchPigeonholeDutyDivisionPkList) {
        return qqchPigeonholeDutyDivisionMapper.deleteQqchPigeonholeDutyDivisionByPks(qqchPigeonholeDutyDivisionPkList);
    }

    /**
     * 获取技术档案归档责任分工Vo
     * @param qqchPigeonholeDutyDivision
     * @return
     */
    @Override
    public QqchPigeonholeDutyDivisionVo getQqchPigeonholeDutyDivisionVo(QqchPigeonholeDutyDivision qqchPigeonholeDutyDivision) {
        QqchPigeonholeDutyDivisionVo qqchPigeonholeDutyDivisionVo = new QqchPigeonholeDutyDivisionVo();

        BigDecimal version = qqchPigeonholeDutyDivision.getVersion();
        version = VersionUtil.getVersion("qqch_pigeonhole_duty_division",version);

        qqchPigeonholeDutyDivision.setVersion(version);
        List<QqchPigeonholeDutyDivision> qqchPigeonholeDutyDivisionList = qqchPigeonholeDutyDivisionMapper.getQqchPigeonholeDutyDivisionList(qqchPigeonholeDutyDivision);

        qqchPigeonholeDutyDivisionVo.setVersion(version);
        qqchPigeonholeDutyDivisionVo.setStageIdentity(qqchReviewService.getStage());
        qqchPigeonholeDutyDivisionVo.setList(qqchPigeonholeDutyDivisionList);
        return qqchPigeonholeDutyDivisionVo;
    }

    /**
     *保存/确认/提交
     * @param qqchPigeonholeDutyDivisionVo
     * @return
     */
    @Override
    @Transactional
    public void save(QqchPigeonholeDutyDivisionVo qqchPigeonholeDutyDivisionVo) {
        String buttonMark = qqchPigeonholeDutyDivisionVo.getButtonMark();
        ButtonMarkUtil.checkButtonMark(buttonMark);

        BigDecimal version = qqchPigeonholeDutyDivisionVo.getVersion();
        List<QqchPigeonholeDutyDivision> qqchPigeonholeDutyDivisionList = qqchPigeonholeDutyDivisionVo.getList();

        //处理数据
        this.insertQqchPigeonholeDutyDivisionList(qqchPigeonholeDutyDivisionList,version);

        //处理确认状态是确认
        if(ButtonMark.CONFIRM.equals(buttonMark)){
            //插入确认记录
            String menuId = qqchPigeonholeDutyDivisionVo.getMenuId();
            String stageIdentity = qqchPigeonholeDutyDivisionVo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
    }
}
