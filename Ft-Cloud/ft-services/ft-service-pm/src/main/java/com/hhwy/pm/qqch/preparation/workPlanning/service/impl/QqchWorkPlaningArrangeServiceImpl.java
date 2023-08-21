package com.hhwy.pm.qqch.preparation.workPlanning.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.common.mapper.CommonMapper;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.workPlanning.domain.QqchWorkPlaningArrange;
import com.hhwy.pm.qqch.preparation.workPlanning.domain.QqchWorkPlaningArrangeVo;
import com.hhwy.pm.qqch.preparation.workPlanning.domain.QqchWorkPlanningBuildPlan;
import com.hhwy.pm.qqch.preparation.workPlanning.mapper.QqchWorkPlaningArrangeMapper;
import com.hhwy.pm.qqch.preparation.workPlanning.service.IQqchWorkPlaningArrangeService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.myEnum.InitVersionConstant;
import com.hhwy.utils.objectUtil.ObjectNullUtil;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author zq
 * @date 2023-07-19 11:49:17
 * @remark 
 */
@Service
public class QqchWorkPlaningArrangeServiceImpl implements IQqchWorkPlaningArrangeService {

    @Autowired
    private QqchWorkPlaningArrangeMapper qqchWorkPlaningArrangeMapper;
    @Autowired
    private CommonMapper commonMapper;
    @Autowired
    private IQqchReviewService qqchReviewService;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;
    @Autowired
    private IQqchReviewService iQqchReviewService;
    public QqchWorkPlaningArrange getQqchWorkPlaningArrange(QqchWorkPlaningArrange qqchWorkPlaningArrange) {
        return qqchWorkPlaningArrangeMapper.getQqchWorkPlaningArrange(qqchWorkPlaningArrange);
    }

    public List<QqchWorkPlaningArrange> getQqchWorkPlaningArrangeList(QqchWorkPlaningArrange qqchWorkPlaningArrange) {
        return qqchWorkPlaningArrangeMapper.getQqchWorkPlaningArrangeList(qqchWorkPlaningArrange);
    }

    @Transactional
    public int insertQqchWorkPlaningArrange(QqchWorkPlaningArrange qqchWorkPlaningArrange) {
        qqchWorkPlaningArrange.setId(IdWorker.createId());
        qqchWorkPlaningArrange.setCreateUser(SecurityUtils.getUserName());
        qqchWorkPlaningArrange.setCreateTime(DateUtils.getNowDate());
        return qqchWorkPlaningArrangeMapper.insertQqchWorkPlaningArrange(qqchWorkPlaningArrange);
    }

    @Transactional
    public int insertQqchWorkPlaningArrangeList(QqchWorkPlaningArrangeVo qqchWorkPlaningArrangeVo) {
        List<QqchWorkPlaningArrange> qqchWorkPlaningArrangeList = new ArrayList<>();
        //判断是确认还是保存
        if("0".equals(qqchWorkPlaningArrangeVo.getButtonMark())){

            String valid = "1";
            if(new BigDecimal(InitVersionConstant.INIT_VERSION).compareTo(qqchWorkPlaningArrangeVo.getVersion()) != 0){
                valid = "0";
            }
            qqchWorkPlaningArrangeList = qqchWorkPlaningArrangeVo.getDataList();
            if(!ObjectNullUtil.isEmpty(qqchWorkPlaningArrangeList)){
                for (QqchWorkPlaningArrange qqchWorkPlaningArrange : qqchWorkPlaningArrangeList) {
                    qqchWorkPlaningArrange.setId(IdWorker.createId());
                    qqchWorkPlaningArrange.setCreateUser(SecurityUtils.getUserName());
                    qqchWorkPlaningArrange.setCreateTime(DateUtils.getNowDate());
                    qqchWorkPlaningArrange.setValid(valid);
                    qqchWorkPlaningArrange.setVersion(ObjectNullUtil.isEmpty(qqchWorkPlaningArrangeVo.getVersion()) ? new BigDecimal(InitVersionConstant.INIT_VERSION) : qqchWorkPlaningArrangeVo.getVersion());
                }
            }
//            else{
//                throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error,"便道部署不可为空");
//            }
        }else if("1".equals(qqchWorkPlaningArrangeVo.getButtonMark())){//确认
            //确认
            //新增一条确认记录
            qqchWorkPlaningArrangeList = qqchWorkPlaningArrangeVo.getDataList();
            if(!ObjectNullUtil.isEmpty(qqchWorkPlaningArrangeList)){
                for (QqchWorkPlaningArrange qqchWorkPlaningArrange : qqchWorkPlaningArrangeList) {
                    qqchWorkPlaningArrange.setId(IdWorker.createId());
                    qqchWorkPlaningArrange.setCreateUser(SecurityUtils.getUserName());
                    qqchWorkPlaningArrange.setCreateTime(DateUtils.getNowDate());
                    qqchWorkPlaningArrange.setValid("1");
                    qqchWorkPlaningArrange.setVersion(ObjectNullUtil.isEmpty(qqchWorkPlaningArrangeVo.getVersion()) ? new BigDecimal(InitVersionConstant.INIT_VERSION) : qqchWorkPlaningArrangeVo.getVersion());
                }
                qqchModuleConfirmCaseService.addConfirmRecord(qqchWorkPlaningArrangeVo.getMenuId(),qqchWorkPlaningArrangeVo.getStageIdentity());
                qqchReviewService.updateFinishNum(qqchWorkPlaningArrangeVo.getStageIdentity(),qqchWorkPlaningArrangeVo.getMenuId());
            }
//            else{
//                throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error,"便道部署不可为空");
//            }
        }else if("2".equals(qqchWorkPlaningArrangeVo.getButtonMark())){//提交
            qqchWorkPlaningArrangeList = qqchWorkPlaningArrangeVo.getDataList();
            if(!ObjectNullUtil.isEmpty(qqchWorkPlaningArrangeList)){
                for (QqchWorkPlaningArrange qqchWorkPlaningArrange : qqchWorkPlaningArrangeList) {
                    qqchWorkPlaningArrange.setId(IdWorker.createId());
                    qqchWorkPlaningArrange.setCreateUser(SecurityUtils.getUserName());
                    qqchWorkPlaningArrange.setCreateTime(DateUtils.getNowDate());
                    qqchWorkPlaningArrange.setValid("0");
                    qqchWorkPlaningArrange.setVersion(ObjectNullUtil.isEmpty(qqchWorkPlaningArrangeVo.getVersion()) ? new BigDecimal(InitVersionConstant.INIT_VERSION) : qqchWorkPlaningArrangeVo.getVersion());
                }
            }
//            else{
//                throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error,"便道部署不可为空");
//            }
        }else{
            throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error,"标识不符合规范");
        }
        //先删除旧的 再添加新的
        QqchWorkPlaningArrange temp = new QqchWorkPlaningArrange();
        temp.setVersion(qqchWorkPlaningArrangeVo.getVersion());
        qqchWorkPlaningArrangeMapper.deleteQqchWorkPlaningArrange(temp);
        if(!ObjectNullUtil.isEmpty(qqchWorkPlaningArrangeList)){
            qqchWorkPlaningArrangeMapper.insertQqchWorkPlaningArrangeList(qqchWorkPlaningArrangeList);
        }
        return 1;
    }

    @Transactional
    public int updateQqchWorkPlaningArrange(QqchWorkPlaningArrange qqchWorkPlaningArrange) {
        qqchWorkPlaningArrange.setUpdateUser(SecurityUtils.getUserName());
        qqchWorkPlaningArrange.setUpdateTime(DateUtils.getNowDate());
        return qqchWorkPlaningArrangeMapper.updateQqchWorkPlaningArrange(qqchWorkPlaningArrange);
    }

            @Transactional
        public int updateQqchWorkPlaningArrangeList(List<QqchWorkPlaningArrange> qqchWorkPlaningArrangeList) {
            for (QqchWorkPlaningArrange qqchWorkPlaningArrange : qqchWorkPlaningArrangeList) {
                qqchWorkPlaningArrange.setUpdateUser(SecurityUtils.getUserName());
                qqchWorkPlaningArrange.setUpdateTime(DateUtils.getNowDate());
            }
            return qqchWorkPlaningArrangeMapper.updateQqchWorkPlaningArrangeList(qqchWorkPlaningArrangeList);
        }
    
    @Transactional
    public int deleteQqchWorkPlaningArrange(QqchWorkPlaningArrange qqchWorkPlaningArrange) {
        qqchWorkPlaningArrange.setUpdateUser(SecurityUtils.getUserName());
        qqchWorkPlaningArrange.setUpdateTime(DateUtils.getNowDate());
        return qqchWorkPlaningArrangeMapper.deleteQqchWorkPlaningArrange(qqchWorkPlaningArrange);
    }

            @Transactional
        public int deleteQqchWorkPlaningArrangeByPks(List<Long> qqchWorkPlaningArrangePkList) {
            return qqchWorkPlaningArrangeMapper.deleteQqchWorkPlaningArrangeByPks(qqchWorkPlaningArrangePkList);
        }

    @Override
    public List<QqchWorkPlaningArrange> getMaxVVData(QqchWorkPlaningArrange arrangeVo) {
        arrangeVo.setValid("1");
        BigDecimal version = commonMapper.selectMaxVersion("qqch_work_planing_arrange");
        arrangeVo.setVersion(version);
        List<QqchWorkPlaningArrange> qqchWorkPlaningArrangeList = getQqchWorkPlaningArrangeList(arrangeVo);
        return qqchWorkPlaningArrangeList;
    }

    @Override
    public List<QqchWorkPlaningArrange> getQqchWorkPlaningArrangeListHistory(QqchWorkPlaningArrange arrangeVo) {
        return null;
    }

    @Override
    public QqchWorkPlaningArrangeVo detail(QqchWorkPlaningArrange arrange) {
        List<QqchWorkPlaningArrange> qqchWorkPlaningArrangeList = null;
        if(ObjectNullUtil.isEmpty(arrange.getVersion())){//直接版本号最大且有效版本
            qqchWorkPlaningArrangeList = getMaxVVData(arrange);
        }else{//历史版本的详情
            qqchWorkPlaningArrangeList = getQqchWorkPlaningArrangeList(arrange);
        }
        QqchWorkPlaningArrangeVo qqchWorkPlaningArrangeVo = new QqchWorkPlaningArrangeVo();
        qqchWorkPlaningArrangeVo.setDataList(qqchWorkPlaningArrangeList);
        if(ObjectNullUtil.isEmpty(qqchWorkPlaningArrangeList)){
            qqchWorkPlaningArrangeVo.setVersion(new BigDecimal(InitVersionConstant.INIT_VERSION));
        }else{
            qqchWorkPlaningArrangeVo.setVersion(qqchWorkPlaningArrangeList.get(0).getVersion());
        }
        //查询阶段
        String stage = iQqchReviewService.getStage();
        qqchWorkPlaningArrangeVo.setStageIdentity(stage);
        return qqchWorkPlaningArrangeVo;
    }
}
