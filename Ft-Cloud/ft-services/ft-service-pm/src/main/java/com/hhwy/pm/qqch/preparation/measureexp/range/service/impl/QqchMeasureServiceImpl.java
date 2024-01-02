package com.hhwy.pm.qqch.preparation.measureexp.range.service.impl;

import com.hhwy.pm.qqch.common.domain.CompileEntity;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.measureexp.range.domain.QqchMeasureOrg;
import com.hhwy.pm.qqch.preparation.measureexp.range.dto.QqchMeasureExpDTO;
import com.hhwy.pm.qqch.preparation.measureexp.range.service.IQqchMeasureExpPersonService;
import com.hhwy.pm.qqch.preparation.measureexp.range.service.IQqchMeasureExpRangeService;
import com.hhwy.pm.qqch.preparation.measureexp.range.service.IQqchMeasureOrgService;
import com.hhwy.pm.qqch.preparation.measureexp.range.service.IQqchMeasureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class QqchMeasureServiceImpl implements IQqchMeasureService {


    @Resource
    private IQqchMeasureOrgService orgService;

    @Resource
    private IQqchMeasureExpRangeService measureExpRangeService;

    @Resource
    private IQqchMeasureExpPersonService personService;

    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;

    @Override
    @Transactional
    public void saveAll(QqchMeasureExpDTO expVO) {
        measureExpRangeService.saveTreeList(expVO);
        personService.saveList(expVO);
        QqchMeasureOrg org = expVO.getOrg();
        orgService.save(CompileEntity.dealSaveDto(expVO, org));

        String submitFlag = expVO.getSubmitFlag();
        if(ButtonMark.CONFIRM.equals(submitFlag)){
            //插入确认状态
            String menuId = expVO.getModuleIdentity();
            String stageIdentity = expVO.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
    }
}
