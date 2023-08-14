package com.hhwy.pm.qqch.tax.qqchTaxInstallment.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.common.aspect.CompileAspect;
import com.hhwy.pm.qqch.common.aspect.CompileOptEnum;
import com.hhwy.pm.qqch.common.domain.CompileEntity;
import com.hhwy.pm.qqch.tax.qqchTaxInstallment.domain.QqchTaxInstallment;
import com.hhwy.pm.qqch.tax.qqchTaxInstallment.mapper.QqchTaxInstallmentMapper;
import com.hhwy.pm.qqch.tax.qqchTaxInstallment.service.IQqchTaxInstallmentService;
import com.hhwy.pm.qqch.tax.qqchTaxInstallment.vo.InstallmentVO;
import com.hhwy.pm.qqch.tax.qqchTaxStage.mapper.QqchTaxStageMapper;
import com.hhwy.pm.qqch.tax.qqchTaxStage.service.IQqchTaxStageService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author mls
 * @date 2023-08-14 00:02:28
 * @remark
 */
@Service
public class QqchTaxInstallmentServiceImpl implements IQqchTaxInstallmentService {

    @Autowired
    private QqchTaxInstallmentMapper qqchTaxInstallmentMapper;


    @Resource
    private IQqchTaxStageService stageService;

    @Resource
    private QqchTaxStageMapper qqchTaxStageMapper;

    private static final String TN = "qqch_tax_installment";


    public QqchTaxInstallment getQqchTaxInstallment(QqchTaxInstallment qqchTaxInstallment) {
        return qqchTaxInstallmentMapper.getQqchTaxInstallment(qqchTaxInstallment);
    }

    public List<QqchTaxInstallment> getQqchTaxInstallmentList(QqchTaxInstallment qqchTaxInstallment) {
        return qqchTaxInstallmentMapper.getQqchTaxInstallmentList(qqchTaxInstallment);
    }

    @Transactional
    public int insertQqchTaxInstallment(QqchTaxInstallment qqchTaxInstallment) {
        qqchTaxInstallment.setId(IdWorker.createId());
        qqchTaxInstallment.setCreateUser(SecurityUtils.getUserName());
        qqchTaxInstallment.setCreateTime(DateUtils.getNowDate());
        return qqchTaxInstallmentMapper.insertQqchTaxInstallment(qqchTaxInstallment);
    }

    @Transactional
    public int insertQqchTaxInstallmentList(List<QqchTaxInstallment> qqchTaxInstallmentList) {
        for (QqchTaxInstallment qqchTaxInstallment : qqchTaxInstallmentList) {
            qqchTaxInstallment.setId(IdWorker.createId());
            qqchTaxInstallment.setCreateUser(SecurityUtils.getUserName());
            qqchTaxInstallment.setCreateTime(DateUtils.getNowDate());
        }
        return qqchTaxInstallmentMapper.insertQqchTaxInstallmentList(qqchTaxInstallmentList);
    }

    @Transactional
    public int updateQqchTaxInstallment(QqchTaxInstallment qqchTaxInstallment) {
        qqchTaxInstallment.setUpdateUser(SecurityUtils.getUserName());
        qqchTaxInstallment.setUpdateTime(DateUtils.getNowDate());
        return qqchTaxInstallmentMapper.updateQqchTaxInstallment(qqchTaxInstallment);
    }

    @Transactional
    public int updateQqchTaxInstallmentList(List<QqchTaxInstallment> qqchTaxInstallmentList) {
        for (QqchTaxInstallment qqchTaxInstallment : qqchTaxInstallmentList) {
            qqchTaxInstallment.setUpdateUser(SecurityUtils.getUserName());
            qqchTaxInstallment.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchTaxInstallmentMapper.updateQqchTaxInstallmentList(qqchTaxInstallmentList);
    }

    @Transactional
    public int deleteQqchTaxInstallment(QqchTaxInstallment qqchTaxInstallment) {
        qqchTaxInstallment.setUpdateUser(SecurityUtils.getUserName());
        qqchTaxInstallment.setUpdateTime(DateUtils.getNowDate());
        return qqchTaxInstallmentMapper.deleteQqchTaxInstallment(qqchTaxInstallment);
    }

    @Transactional
    public int deleteQqchTaxInstallmentByPks(List<Long> qqchTaxInstallmentPkList) {
        return qqchTaxInstallmentMapper.deleteQqchTaxInstallmentByPks(qqchTaxInstallmentPkList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CompileAspect(type = CompileOptEnum.SAVE, tableName = TN)
    public void save(CompileEntity<QqchTaxInstallment> dto) {
        this.qqchTaxInstallmentMapper.insertQqchTaxInstallment(dto.dealSaveDto());
    }

    @Override
    public InstallmentVO refresh(CompileEntity<QqchTaxInstallment> dto) {
        InstallmentVO installmentVO = new InstallmentVO();
        List<Long> longs = qqchTaxStageMapper.selectNewestRecordId();
        if (CollectionUtils.isEmpty(longs)) return installmentVO;
        // 查询到最新的数据Id
        installmentVO.setInRecordId(longs.get(0));
        installmentVO.setCostRecordId(longs.size() == 2 ? longs.get(1) : null);


        return installmentVO;
    }


}
