package com.hhwy.pm.qqch.preparation.quality.qqchQualityRiskList.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.impl.QqchModuleConfirmCaseServiceImpl;
import com.hhwy.pm.qqch.preparation.quality.qqchQualityRiskList.domain.QqchQualityRiskList;
import com.hhwy.pm.qqch.preparation.quality.qqchQualityRiskList.domain.vo.QqchQualityRiskListVo;
import com.hhwy.pm.qqch.preparation.quality.qqchQualityRiskList.mapper.QqchQualityRiskListMapper;
import com.hhwy.pm.qqch.preparation.quality.qqchQualityRiskList.service.IQqchQualityRiskListService;
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
 * @date 2023-08-04 10:02:39
 * @remark  9.3.1 质量风险清单
 */
@Service
public class QqchQualityRiskListServiceImpl implements IQqchQualityRiskListService {

    @Autowired
    private QqchQualityRiskListMapper qqchQualityRiskListMapper;
    @Autowired
    private QqchModuleConfirmCaseServiceImpl qqchModuleConfirmCaseService;
    @Autowired
    private IQqchReviewService qqchReviewService;


    public QqchQualityRiskList getQqchQualityRiskList(QqchQualityRiskList qqchQualityRiskList) {
        return qqchQualityRiskListMapper.getQqchQualityRiskList(qqchQualityRiskList);
    }


    @Transactional
    public int insertQqchQualityRiskList(QqchQualityRiskList qqchQualityRiskList) {
        qqchQualityRiskList.setId(IdWorker.createId());
        qqchQualityRiskList.setCreateUser(SecurityUtils.getUserName());
        qqchQualityRiskList.setCreateTime(DateUtils.getNowDate());
        return qqchQualityRiskListMapper.insertQqchQualityRiskList(qqchQualityRiskList);
    }


    @Transactional
    public int updateQqchQualityRiskList(QqchQualityRiskList qqchQualityRiskList) {
        qqchQualityRiskList.setUpdateUser(SecurityUtils.getUserName());
        qqchQualityRiskList.setUpdateTime(DateUtils.getNowDate());
        return qqchQualityRiskListMapper.updateQqchQualityRiskList(qqchQualityRiskList);
    }

    @Transactional
    public int updateQqchQualityRiskListList(List<QqchQualityRiskList> qqchQualityRiskListList) {
        for (QqchQualityRiskList qqchQualityRiskList : qqchQualityRiskListList) {
            qqchQualityRiskList.setUpdateUser(SecurityUtils.getUserName());
            qqchQualityRiskList.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchQualityRiskListMapper.updateQqchQualityRiskListList(qqchQualityRiskListList);
    }

    @Transactional
    public int deleteQqchQualityRiskList(QqchQualityRiskList qqchQualityRiskList) {
        qqchQualityRiskList.setUpdateUser(SecurityUtils.getUserName());
        qqchQualityRiskList.setUpdateTime(DateUtils.getNowDate());
        return qqchQualityRiskListMapper.deleteQqchQualityRiskList(qqchQualityRiskList);
    }

    @Transactional
    public int deleteQqchQualityRiskListByPks(List<Long> qqchQualityRiskListPkList) {
        return qqchQualityRiskListMapper.deleteQqchQualityRiskListByPks(qqchQualityRiskListPkList);
    }


    /**
     * 列表接口
     *
     * @param qqchQualityRiskList
     * @return
     */
    @Override
    public QqchQualityRiskListVo getQqchQualityRiskListList(QqchQualityRiskList qqchQualityRiskList) {
        QqchQualityRiskListVo vo = new QqchQualityRiskListVo();

        BigDecimal version = qqchQualityRiskList.getVersion();
        version = VersionUtil.getVersion("qqch_quality_risk_list", version);

        qqchQualityRiskList.setVersion(version);
        List<QqchQualityRiskList> qqchQualityRiskListList = qqchQualityRiskListMapper.getQqchQualityRiskListList(qqchQualityRiskList);

        vo.setVersion(version);
        vo.setStageIdentity(qqchReviewService.getStage());
        vo.setQqchQualityRiskListList(qqchQualityRiskListList);
        return vo;
    }


    @Override
    @Transactional
    public void save(QqchQualityRiskListVo vo) {
        String buttonMark = vo.getButtonMark();
        ButtonMarkUtil.checkButtonMark(buttonMark);

        BigDecimal version = vo.getVersion();
        List<QqchQualityRiskList> qqchQualityRiskListList = vo.getQqchQualityRiskListList();

        this.insertQqchQualityRiskListList(qqchQualityRiskListList, version);

        //处理确认状态是确认
        if (ButtonMark.CONFIRM.equals(buttonMark)) {
            //插入确认记录
            String menuId = vo.getMenuId();
            String stageIdentity = vo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId, stageIdentity);
        }
    }


    public void insertQqchQualityRiskListList(List<QqchQualityRiskList> qqchQualityRiskListList, BigDecimal version) {
        //删除旧数据
        QqchQualityRiskList qqchQualityRiskList = new QqchQualityRiskList();
        qqchQualityRiskList.setVersion(version);
        qqchQualityRiskListMapper.deleteQqchQualityRiskList(qqchQualityRiskList);

        if (CollectionUtils.isEmpty(qqchQualityRiskListList)) {
            return;
        }
        String valid = Valid.NO;
        if (version.compareTo(BigDecimal.ONE) == 0) {
            valid = Valid.YES;
        }
        for (QqchQualityRiskList riskList : qqchQualityRiskListList) {
            riskList.setId(IdWorker.createId());
            riskList.setValid(valid);
            riskList.setVersion(version);
            riskList.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            riskList.setCreateUserName(SecurityUtils.getUserName());
            riskList.setCreateTime(DateUtils.getNowDate());
            // TODO: 2023/8/4
            // 判断是否入库，如果为空，这说明数据本身就是库里的不存在改操作，
            //如果为1 则判断库里是否有，没有则入库风险库
            //如果为0 则不入库
            if(riskList.getWarehousing()!=null){
                if(riskList.getWarehousing().equals("1")){
                    //入库
                }
            }

        }
        qqchQualityRiskListMapper.insertQqchQualityRiskListList(qqchQualityRiskListList);
    }
}