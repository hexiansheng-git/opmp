package com.hhwy.pm.qqch.preparation.workPlanning.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.workPlanning.domain.QqchWorkPlanningBuildPlan;
import com.hhwy.pm.qqch.preparation.workPlanning.domain.QqchWorkPlanningPrjImg;
import com.hhwy.pm.qqch.preparation.workPlanning.mapper.QqchWorkPlanningPrjImgMapper;
import com.hhwy.pm.qqch.preparation.workPlanning.service.IQqchWorkPlanningPrjImgService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.myEnum.InitVersionConstant;
import com.hhwy.utils.objectUtil.ObjectNullUtil;
import com.zaxxer.hikari.metrics.IMetricsTracker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author zq
 * @date 2023-07-17 14:18:26
 * @remark 
 */
@Service
public class QqchWorkPlanningPrjImgServiceImpl implements IQqchWorkPlanningPrjImgService {

    @Autowired
    private QqchWorkPlanningPrjImgMapper qqchWorkPlanningPrjImgMapper;
    @Autowired
    private IQqchReviewService qqchReviewService;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;

    public QqchWorkPlanningPrjImg getQqchWorkPlanningPrjImg(QqchWorkPlanningPrjImg qqchWorkPlanningPrjImg) {
        return qqchWorkPlanningPrjImgMapper.getQqchWorkPlanningPrjImg(qqchWorkPlanningPrjImg);
    }

    public List<QqchWorkPlanningPrjImg> getQqchWorkPlanningPrjImgList(QqchWorkPlanningPrjImg qqchWorkPlanningPrjImg) {
        return qqchWorkPlanningPrjImgMapper.getQqchWorkPlanningPrjImgList(qqchWorkPlanningPrjImg);
    }

    @Transactional
    public int insertQqchWorkPlanningPrjImg(QqchWorkPlanningPrjImg qqchWorkPlanningPrjImg) {
        qqchWorkPlanningPrjImg.setId(IdWorker.createId());
        qqchWorkPlanningPrjImg.setCreateUser(SecurityUtils.getUserName());
        qqchWorkPlanningPrjImg.setCreateTime(DateUtils.getNowDate());
        qqchWorkPlanningPrjImg.setVersion(ObjectNullUtil.isEmpty(qqchWorkPlanningPrjImg.getVersion()) ? new BigDecimal(InitVersionConstant.INIT_VERSION) : qqchWorkPlanningPrjImg.getVersion());

        //判断是确认还是保存
        if("0".equals(qqchWorkPlanningPrjImg.getButtonMark())){//保存（判断是业务保存还是变更保存）
            if(qqchWorkPlanningPrjImg.getVersion().compareTo(new BigDecimal(InitVersionConstant.INIT_VERSION)) ==0){//业务保存
                qqchWorkPlanningPrjImg.setValid("1");
            }else{//变更保存
                qqchWorkPlanningPrjImg.setValid("0");
            }
        }else if("1".equals(qqchWorkPlanningPrjImg.getButtonMark())){//确认
            //新增一条确认记录
            qqchWorkPlanningPrjImg.setValid("1");
            qqchReviewService.updateFinishNum(qqchWorkPlanningPrjImg.getStageIdentity(),qqchWorkPlanningPrjImg.getModuleIdentity());
            qqchModuleConfirmCaseService.addConfirmRecord(qqchWorkPlanningPrjImg.getMenuId(),qqchWorkPlanningPrjImg.getStageIdentity());

        }else if("2".equals(qqchWorkPlanningPrjImg.getButtonMark())){//提交
            qqchWorkPlanningPrjImg.setValid("0");
        }else{
            throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error,"标识不符合规范");
        }
        //先删除旧的 再添加新的
        QqchWorkPlanningPrjImg temp = new QqchWorkPlanningPrjImg();
        temp.setVersion(qqchWorkPlanningPrjImg.getVersion());
        qqchWorkPlanningPrjImgMapper.deleteQqchWorkPlanningPrjImg(temp);

        qqchWorkPlanningPrjImgMapper.insertQqchWorkPlanningPrjImg(qqchWorkPlanningPrjImg);
        return 1;
    }

    @Transactional
    public int insertQqchWorkPlanningPrjImgList(List<QqchWorkPlanningPrjImg> qqchWorkPlanningPrjImgList) {
        for (QqchWorkPlanningPrjImg qqchWorkPlanningPrjImg : qqchWorkPlanningPrjImgList) {
            qqchWorkPlanningPrjImg.setId(IdWorker.createId());
            qqchWorkPlanningPrjImg.setCreateUser(SecurityUtils.getUserName());
            qqchWorkPlanningPrjImg.setCreateTime(DateUtils.getNowDate());
        }
        return qqchWorkPlanningPrjImgMapper.insertQqchWorkPlanningPrjImgList(qqchWorkPlanningPrjImgList);
    }

    @Transactional
    public int updateQqchWorkPlanningPrjImg(QqchWorkPlanningPrjImg qqchWorkPlanningPrjImg) {
        qqchWorkPlanningPrjImg.setUpdateUser(SecurityUtils.getUserName());
        qqchWorkPlanningPrjImg.setUpdateTime(DateUtils.getNowDate());
        return qqchWorkPlanningPrjImgMapper.updateQqchWorkPlanningPrjImg(qqchWorkPlanningPrjImg);
    }

            @Transactional
        public int updateQqchWorkPlanningPrjImgList(List<QqchWorkPlanningPrjImg> qqchWorkPlanningPrjImgList) {
            for (QqchWorkPlanningPrjImg qqchWorkPlanningPrjImg : qqchWorkPlanningPrjImgList) {
                qqchWorkPlanningPrjImg.setUpdateUser(SecurityUtils.getUserName());
                qqchWorkPlanningPrjImg.setUpdateTime(DateUtils.getNowDate());
            }
            return qqchWorkPlanningPrjImgMapper.updateQqchWorkPlanningPrjImgList(qqchWorkPlanningPrjImgList);
        }
    
    @Transactional
    public int deleteQqchWorkPlanningPrjImg(QqchWorkPlanningPrjImg qqchWorkPlanningPrjImg) {
        qqchWorkPlanningPrjImg.setUpdateUser(SecurityUtils.getUserName());
        qqchWorkPlanningPrjImg.setUpdateTime(DateUtils.getNowDate());
        return qqchWorkPlanningPrjImgMapper.deleteQqchWorkPlanningPrjImg(qqchWorkPlanningPrjImg);
    }

            @Transactional
        public int deleteQqchWorkPlanningPrjImgByPks(List<Long> qqchWorkPlanningPrjImgPkList) {
            return qqchWorkPlanningPrjImgMapper.deleteQqchWorkPlanningPrjImgByPks(qqchWorkPlanningPrjImgPkList);
        }

    @Override
    public QqchWorkPlanningPrjImg getQqchWorkPlanningPrjIsValid(QqchWorkPlanningPrjImg img) {
        return qqchWorkPlanningPrjImgMapper.getQqchWorkPlanningPrjIsValid(img);
    }

    @Override
    public QqchWorkPlanningPrjImg getQqchWorkPlanningPrjHistory(QqchWorkPlanningPrjImg img) {
        return qqchWorkPlanningPrjImgMapper.getQqchWorkPlanningPrjHistory(img);
    }

    @Override
    public void listener(Long businessId) {
        QqchWorkPlanningPrjImg img = new QqchWorkPlanningPrjImg();
        img.setId(businessId);
        img.setValid("1");
        qqchWorkPlanningPrjImgMapper.updateQqchWorkPlanningPrjImg(img);
    }
}
